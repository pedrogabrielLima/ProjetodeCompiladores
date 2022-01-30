import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

class Main {

  static StringBuffer dest = new StringBuffer();
	static String primaryKeyType = new String();

	private static String ViewsetFormatName = "viewsets.py";
	private static String SerializerFormatName = "serializers.py";
	private static String ModelFormatName = "models.py";

  static void format_string(String fmt, Object... args) {
    for (int c = 0; c < args.length; c++){
			args[c] = Utils.get_token_value(args[c]);
    }    
    dest.append(String.format(fmt, args));
  }

	// Create the default imports declarations
  public static void GenerateModelDefaultImports() {
		format_string("from django.db import models\n\n");
  }

	public static void GenerateViewsetDefaultImports(CompiladorParser.StartContext prog){
		format_string("from rest_framework.decorators import action\n");
		format_string("from rest_framework.response import Response\n");
		format_string("from rest_framework import permissions\n\n");
		format_string("from .api.serializers import %sSerializer\n\n", prog.tableNome);
		format_string("from .models import %s\n\n", prog.tableNome);
	}

	public static void GenerateSerializerDefaultImports(CompiladorParser.StartContext prog){
		format_string("from rest_framework.serializers import ModelSerializer\n\n");
		format_string("from .models import %s\n\n", prog.tableNome);
	}

	// Create body definitions
  public static void GenerateModelBody(CompiladorParser.StartContext prog){
    format_string("class %s(models.Model):\n", prog.tableNome);
    CreateEntityBodyDeclaration(prog);
  }

	public static void GenerateViewsetBody(CompiladorParser.StartContext prog){
		format_string("class %sViewSet(viewsets.ModelViewSet):\n", prog.tableNome);
		format_string("\tqueryset = %s.objects.all()\n", prog.tableNome);
		format_string("\tserializer_class = %sSerializer\n", prog.tableNome);
	}

	public static void GenerateSerializerBody(CompiladorParser.StartContext prog){
		format_string("class %sSerializer(ModelSerializer):\n", prog.tableNome);
		format_string("\tclass Meta:\n");
		format_string("\t\tmodel = %s\n", prog.tableNome);
		format_string("\t\tfields = '__all__'");
	}

	// Creates the classes
	public static void CreateModelClass(CompiladorParser.StartContext prog){
		GenerateModelDefaultImports();
		GenerateModelBody(prog);
	}

	public static void CreateSerializerClass(CompiladorParser.StartContext prog){
		GenerateSerializerDefaultImports(prog);
		GenerateSerializerBody(prog);
	}

	public static void CreateViewsetClass(CompiladorParser.StartContext prog){
		GenerateViewsetDefaultImports(prog);
		GenerateViewsetBody(prog);
	}

  public static void CreateEntityBodyDeclaration(CompiladorParser.StartContext prog) {
    CompiladorParser.ColunasContext colunas = prog.colunas();
    List<TerminalNode> typeList = colunas.DATATYPE();
    List<TerminalNode> columnNameList = colunas.ID();

    for (int i = 0; i < typeList.size(); i++) {

			String is_unique = colunas.pknome.getText().equals(columnNameList.get(i).getText()) ? "primary_key=True" : "";
			String columnName = columnNameList.get(i).getText().toLowerCase();

      switch (typeList.get(i).getText()) {
        case "INT":
          format_string("\t%s = models.IntegerField(%s)\n", columnName, is_unique);
          break;
        case "VARCHAR":
        case "TEXT":
          format_string("\t%s = models.CharField(%s)\n", columnName, is_unique);
          break;
        case "DATE":
          format_string("\t%s = models.DateTimeField('%s')\n", columnName, columnName);
          break;
        case "BOOL":
          format_string("\t%s = models.BooleanField(default=False)\n", columnName);
          break;
        case "DECIMAL":
					format_string("\t%s = models.DecimalField(max_digits=5, decimal_places=2)\n", columnName);
					break;
        case "FLOAT":
          format_string("\t%s = models.FloatField()\n", columnName);
					break;
        default:
          break;
      }
    }
  }

	public static void ConvertSQLToPython(CompiladorParser.StartContext prog) {
		// Creates the model class
		CreateModelClass(prog);		
		FileManager.SaveToFile(dest.toString(), ModelFormatName);
		// Creates the serializer class
		 dest = new StringBuffer();
		CreateSerializerClass(prog);
		 FileManager.SaveToFile(dest.toString(), SerializerFormatName);
		// Creates the viewset class to basic crud
		dest = new StringBuffer();
		CreateViewsetClass(prog);
		FileManager.SaveToFile(dest.toString(), ViewsetFormatName);
  }

  public static void main(String args[]) throws Exception {
    CharStream src = CharStreams.fromFileName(args[0]);
    CompiladorLexer lexer = new CompiladorLexer(src);
    TokenStream tkStream = new CommonTokenStream(lexer);
    CompiladorParser parser = new CompiladorParser(tkStream);

    CompiladorParser.StartContext ctx = parser.start();
    if (parser.getNumberOfSyntaxErrors() > 0) {
      System.err.println(parser.getNumberOfSyntaxErrors() + " Erros foram encontrados\n");
    }
    ConvertSQLToPython(ctx);
  }
}
