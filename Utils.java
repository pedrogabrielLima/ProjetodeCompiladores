import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

class Utils {

	public static String GetTypeByString(String type) {
    switch (type) {
      case "INT":
        return "Long";
      case "VARCHAR":
      case "TEXT":
        return "String";
      case "DATE":
        return "Date";
      case "BOOL":
        return "Boolean";
      case "DECIMAL":
      case "FLOAT":
        return "Float";
      default:
        return "";
    }
  }

	public static Object get_token_value(Object token){
		if(token instanceof Token){
			return ((Token) token).getText();
		}
		return token;
	}
}