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
		Player tempplayer;
		ObjectInputStream input = null;
		ArrayList<Player> players = new ArrayList<Player>();
		try {
			File infile = new File(System.getProperty("user.dir") + File.separator + "chessgamedata.dat");
			input = new ObjectInputStream(new FileInputStream(infile));
			try {
				while (true) {
					tempplayer = (Player) input.readObject();
					players.add(tempplayer);
				}
			} catch (EOFException e) {
				input.close();
			}
		} catch (FileNotFoundException e) {
			players.clear();
			return players;
		} catch (IOException e) {
			e.printStackTrace();
			try {
				input.close();
			} catch (IOException e1) {
			}
			JOptionPane.showMessageDialog(null, "Unable to read the required Game files !!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Game Data File Corrupted !! Click Ok to Continue Builing New File");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return players;
	}

	public void updatePlayersData() {
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		File inputfile = null;
		File outputfile = null;
		try {
			inputfile = new File(System.getProperty("user.dir") + File.separator + "chessgamedata.dat");
			outputfile = new File(System.getProperty("user.dir") + File.separator + "tempfile.dat");
		} catch (SecurityException e) {
			JOptionPane.showMessageDialog(null, "Read-Write Permission Denied !! Program Cannot Start");
			System.exit(0);
		}
		boolean playerdonotexist;
		try {
			if (outputfile.exists() == false)
				outputfile.createNewFile();
			if (inputfile.exists() == false) {
				output = new ObjectOutputStream(new java.io.FileOutputStream(outputfile, true));
				output.writeObject(this);
			} else {
				input = new ObjectInputStream(new FileInputStream(inputfile));
				output = new ObjectOutputStream(new FileOutputStream(outputfile));
				playerdonotexist = true;
				try {
					while (true) {
						Player tempPlayer = (Player) input.readObject();
						if (tempPlayer.getName().equals(getName())) {
							output.writeObject(this);
							playerdonotexist = false;
						} else {
							output.writeObject(tempPlayer);
						}
					}
				} catch (EOFException e) {
					input.close();
				}
				if (playerdonotexist)
					output.writeObject(this);
			}
			inputfile.delete();
			output.close();
			File newf = new File(System.getProperty("user.dir") + File.separator + "chessgamedata.dat");
			if (outputfile.renameTo(newf) == false)
				System.out.println("File Renameing Unsuccessful");
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
