package chess;

import java.io.Serializable;

import javax.swing.JFrame;

public class Main extends JFrame implements Serializable {
	//private static Main chessBoard;
	private static ContentLayout contentLayout;
	
	public static void main(String[] args) {
		contentLayout = Main.getSingleInstance();
		contentLayout.createGame();
		contentLayout.setVisible(true);
		contentLayout.setResizable(false);
	}

	private Main() {
		
	}

	public static ContentLayout getSingleInstance() {
		if (contentLayout == null) {
			contentLayout = new ContentLayout();
		}
		return contentLayout;
	}

	
}
