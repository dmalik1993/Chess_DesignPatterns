package chess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SavedGame {
	
	@SuppressWarnings("unchecked")
	public static BoardState fetchSavedGamesData(String fileName){
		//ArrayList<Object> obj = new ArrayList<Object>();
		
		//Object abc = new Object();
		 BoardState chessBoardState;
		//Map<String, Object> objectMap = new ConcurrentHashMap<String, Object>();
		try {
	         FileInputStream fileIn = new FileInputStream(fileName +".ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);

	         //obj = (ArrayList<Object>)in.readObject();  
//	         Cell[][] board;
//	         Player whitePlayer;
//	         Player blackPlayer;
//	         
//	         
//	         whitePlayer = (Player)in.readObject();
//	         blackPlayer = (Player)in.readObject();
//	         board = (Cell[][])in.readObject();
//	        
	        
	         
	         chessBoardState = (BoardState)in.readObject();
	         
	         
	        // objectMap.putAll(in.readObject());
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         return null;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return null;
	      }
		
		return chessBoardState;
	}
	
	public static void saveGameData(String fileName, BoardState chessBoardState){
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream(fileName +".ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         
	         out.writeObject(chessBoardState);
	         
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in E:/employee.ser");
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
	
	public static String[] fetchSavedGameList() {
		List<String> savedGamesList = new ArrayList<String>();
		String fileName = "temp.txt";

		// This will reference one line at a time
		String line = null;
		BufferedReader bufferedReader = null;
		
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			bufferedReader = new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) {
				savedGamesList.add(line);
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
			savedGamesList.clear();
			return savedGamesList.toArray(new String[savedGamesList.size()]);
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		} finally {
			try {
				if(null != bufferedReader)
				bufferedReader.close();
				return savedGamesList.toArray(new String[savedGamesList.size()]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return savedGamesList.toArray(new String[savedGamesList.size()]);

	}

	public static void updateSavedGamesList(String gameName) {

		String fileName = "temp.txt";
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(fileName, true);

			FileReader fileReader = new FileReader(fileName);

			bufferedReader = new BufferedReader(fileReader);

			bufferedWriter = new BufferedWriter(fileWriter);

			if (null != bufferedReader.readLine()) {
				bufferedWriter.newLine();
			}
			// Always wrap FileWriter in BufferedWriter.

			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.write(gameName);

		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}