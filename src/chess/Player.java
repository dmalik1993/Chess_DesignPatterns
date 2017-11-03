package chess;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * This is the Player Class It provides the functionality of keeping track of
 * all the users Objects of this class is updated and written in the Game's Data
 * Files after every Game
 *
 */
public class Player implements Serializable {

	private static final String TEMPORARY_DATA_FILE = "tempfile.dat";
	private static final String GAME_DATA_FILE = "chessgamedata.dat";
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer gamesPlayed;
	private Integer gamesWon;

	public Player(String name) {
		this.name = name.trim();
		gamesPlayed = Integer.valueOf(0);
		gamesWon = Integer.valueOf(0);
	}

	public String getName() {
		return name;
	}

	public Integer gamesPlayed() {
		return gamesPlayed;
	}

	public Integer gamesWon() {
		return gamesWon;
	}

	public Integer winPercentage() {
		return Integer.valueOf((gamesWon * 100) / gamesPlayed);
	}

	public void updateGamesPlayed() {
		gamesPlayed++;
	}

	public void updateGamesWon() {
		gamesWon++;
	}

	public static ArrayList<Player> fetchPlayersData() {
		ObjectInputStream inputStream = null;
		ArrayList<Player> playersList = new ArrayList<Player>();
		try {
			File inputfile = new File(System.getProperty("user.dir") + File.separator + GAME_DATA_FILE);
			inputStream = new ObjectInputStream(new FileInputStream(inputfile));
			try {
				while (true) {
					Player player = (Player) inputStream.readObject();
					playersList.add(player);
				}
			} catch (EOFException e) {
				inputStream.close();
			}

		} catch (FileNotFoundException e) {
			playersList.clear();
			return playersList;
		} catch (IOException e) {
			e.printStackTrace();
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e1) {
			}

			JOptionPane.showMessageDialog(null, "Unable to read the required Game files !!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Game Data File Corrupted !! Click Ok to Continue Builing New File");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return playersList;
	}

	public void updatePlayersData() {
		ObjectInputStream inputStream = null;
		ObjectOutputStream outputStream = null;
		File inputFile = null;
		File outputFile = null;
		try {
			inputFile = new File(System.getProperty("user.dir") + File.separator + GAME_DATA_FILE);
			outputFile = new File(System.getProperty("user.dir") + File.separator + TEMPORARY_DATA_FILE);
		} catch (SecurityException e) {
			JOptionPane.showMessageDialog(null, "Read-Write Permission Denied !! Program Cannot Start");
			System.exit(0);
		}
		boolean playerdonotexist;
		try {
			if (outputFile.exists() == false)
				outputFile.createNewFile();
			if (inputFile.exists() == false) {
				outputStream = new ObjectOutputStream(new FileOutputStream(outputFile, true));
				outputStream.writeObject(this);
			} else {
				inputStream = new ObjectInputStream(new FileInputStream(inputFile));
				outputStream = new ObjectOutputStream(new FileOutputStream(outputFile));
				playerdonotexist = true;
				try {
					while (true) {
						Player player = (Player) inputStream.readObject();
						if (player.getName().equals(getName())) {
							outputStream.writeObject(this);
							playerdonotexist = false;
						} else {
							outputStream.writeObject(player);
						}
					}
				} catch (EOFException e) {
					inputStream.close();
				}
				if (playerdonotexist)
					outputStream.writeObject(this);
			}
			inputFile.delete();
			outputStream.close();
			File newFile = new File(System.getProperty("user.dir") + File.separator + GAME_DATA_FILE);
			outputFile.renameTo(newFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to read/write the required Game files !! Press ok to continue");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Game Data File Corrupted !! Click Ok to Continue Builing New File");
		} catch (Exception e) {

		}
	}
}
