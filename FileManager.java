import java.io.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

class FileManager {

	static String folderName = "output/";

	public static void SaveToFile(String content, String filename){
		try {
      FileWriter myWriter = new FileWriter(folderName + filename);
      myWriter.write(content);
			System.out.println(String.format("Successfully wrote to the file %s.", filename));
      myWriter.close();
    } catch (IOException e) {
			System.out.println("An error occurred.");
      	e.printStackTrace();
    }
	}

}