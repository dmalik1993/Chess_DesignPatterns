package chess;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import pieces.King;
import pieces.Piece;
import utils.Constants;

public class ContentLayout extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Cell previousCell;
	private int currentTurn = 0;

	private SoundClass gameSound;
	
	private BoardState chessBoardState;
	private List<Cell> possibleDestinations = new ArrayList<Cell>();

	private Player whitePlayer, blackPlayer, selectedPlayer;

	private JPanel boardPanel, controlPanel, temporaryPanel, timeDisplayPanle, playerViewPanel, timePanel;
	private JPanel whitePlayerDetailsPanel, whitePlayerComboPanel, whitePlayerPanel;
	private JPanel themePanelDetail, themePanelCombo, themePanel;
	private JPanel savedGamesDetailsPanel, savedGamesComboPanel, savedGamesPanel;
	private JPanel blackPlayerDetailsPnale, blackPlayerComboPanel, blackPlayerPanel;

	private Player whitePlayerData = new Player();
	private Player blackPlayerData = new Player();

	private JSplitPane splitPane;
	private JLabel timeLabel, moveLabel, turnLabel;

	private Time timer;

	private boolean isSelected = false, end = false, isThemeSelected = false;;

	private Container content;
	private JComboBox<String> whitePlayerCombo, blackPlayerCombo, themeCombo;
	private JComboBox<String> savedGameCombo;

	private String gameTheme;
	private String gameThemeCode;

	private JScrollPane whitePlayerScrollPane, blackPlayerScrollPane, themeScrollPane;
	private JScrollPane savedGameScrollPane;
	private JSlider timeSlider;
	private BufferedImage image;

	private Button startButton, whitePlayerSelectButton, blackPlayerSelectButton, newWhitePlayerButton,
			newBlackPlayerButton, savedGamesSelectButton, themeSelectButton;
	
	private BoardState savedState = null;
	public static int timeRemaining = Constants.TOTAL_TIME_FOR_TURN;
	
	//Main contentLayout;

	public ContentLayout() {
		//contentLayout = ref;
	}
	
	public void createGame() {

		createChessBoardPanel();

		setContent(getContentPane());
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setTitle(Constants.CHESS_TITLE);
		getContent().setBackground(Color.black);

		createControlPanel();

		setSplitPane(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, temporaryPanel, controlPanel));

		getContent().add(getSplitPane());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(content,
						"You are about to exit. Do you want to save the current session of the game?", "Attention!",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					String gameName = JOptionPane.showInputDialog(content, "Enter game name");

					if (null == gameName || gameName.isEmpty()) {
						return;
					}

					SavedGame.updateSavedGamesList(gameName);
					List<Player> playersData = new ArrayList<Player>();
					playersData.add(whitePlayerData);
					playersData.add(blackPlayerData);

					getChessBoardState().setPlayerDetail(playersData);

					SavedGame.saveGameData(gameName, getChessBoardState());

					System.exit(0);
				}
			}
		});
	}
	
	private void createControlPanel() {

		String[] gamesList = SavedGame.fetchSavedGameList();
		controlPanel = new JPanel();
		content.setLayout(new BorderLayout());

		if (null != gamesList && gamesList.length > 0) {
			controlPanel.setLayout(new GridLayout(5, 3));
			createSavedGamesPanel();
			controlPanel.add(savedGamesPanel);
		} else {
			controlPanel.setLayout(new GridLayout(4, 3));
		}

		controlPanel.setBorder(BorderFactory.createTitledBorder(null, "Statistics", TitledBorder.TOP,
				TitledBorder.CENTER, new Font("Lucida Calligraphy", Font.PLAIN, 20), Color.ORANGE));

		createWhitePlayerPanel();
		createBlackPlayerPanel();
		createThemePanel();

		controlPanel.add(themePanel);
		controlPanel.add(whitePlayerPanel);
		controlPanel.add(blackPlayerPanel);

		initializeTimer();

		createTimePanel();
		controlPanel.add(timePanel);

		createTemporaryPanel();

		controlPanel.setMinimumSize(new Dimension(285, 700));
	}

	public void createChessBoardPanel() {
		boardPanel = new JPanel(new GridLayout(8, 8));
		boardPanel.setMinimumSize(new Dimension(800, 700));
		ImageIcon img = new ImageIcon(this.getClass().getResource("icon.png"));
		this.setIconImage(img.getImage());

		boardPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		boardPanel.setMinimumSize(new Dimension(800, 700));
	}

	private void createTemporaryPanel() {
		temporaryPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				try {
					image = ImageIO.read(this.getClass().getResource("clash.jpg"));
				} catch (IOException ex) {
				}

				g.drawImage(image, 0, 0, null);
			}
		};

		temporaryPanel.setMinimumSize(new Dimension(800, 700));
	}

	private void createTimePanel() {
		playerViewPanel = new JPanel(new FlowLayout());
		playerViewPanel.add(timeSlider);
		JLabel setTime = new JLabel("Set Timer(in mins):");

		startButton = new Button("Start");
		startButton.setBackground(Color.black);
		startButton.setForeground(Color.white);
		startButton.addActionListener(new StartButtonListener(this));
		startButton.setPreferredSize(new Dimension(120, 40));

		setTime.setFont(new Font("Arial", Font.BOLD, 16));
		timeLabel = new JLabel("Time Starts now", JLabel.CENTER);
		timeLabel.setFont(new Font("SERIF", Font.BOLD, 30));
		timeDisplayPanle = new JPanel(new FlowLayout());
		timePanel = new JPanel(new GridLayout(3, 3));
		timePanel.add(setTime);
		timePanel.add(playerViewPanel);
		timeDisplayPanle.add(startButton);
		timePanel.add(timeDisplayPanle);
		timer = new Time(timeLabel, this);
	}

	private void createBlackPlayerPanel() {
		blackPlayerPanel = new JPanel();
		blackPlayerDetailsPnale = new JPanel(new GridLayout(3, 3));
		blackPlayerComboPanel = new JPanel();

		blackPlayerPanel.setBorder(BorderFactory.createTitledBorder(null, "Black Player", TitledBorder.TOP,
				TitledBorder.CENTER, new Font("times new roman", Font.BOLD, 18), Color.BLUE));
		blackPlayerPanel.setLayout(new BorderLayout());

		blackPlayerCombo = new JComboBox<String>(getPlayerNames());

		blackPlayerScrollPane = new JScrollPane(blackPlayerCombo);

		blackPlayerSelectButton = new Button("Select");

		blackPlayerSelectButton.addActionListener(new PlayerSelectionListener(1, this));

		newBlackPlayerButton = new Button("New Player");

		newBlackPlayerButton.addActionListener(new AddNewPlayerListener(1, this));
		blackPlayerComboPanel.setLayout(new FlowLayout());
		blackPlayerComboPanel.add(blackPlayerScrollPane);
		blackPlayerComboPanel.add(blackPlayerSelectButton);
		blackPlayerComboPanel.add(newBlackPlayerButton);
		blackPlayerPanel.add(blackPlayerComboPanel, BorderLayout.NORTH);

		JPanel blackPlayerStats = new JPanel(new GridLayout(3, 3));
		blackPlayerStats.add(new JLabel("Name   :"));
		blackPlayerStats.add(new JLabel("Played :"));
		blackPlayerStats.add(new JLabel("Won    :"));

		blackPlayerPanel.add(blackPlayerStats, BorderLayout.WEST);
	}

	private void createWhitePlayerPanel() {
		whitePlayerDetailsPanel = new JPanel(new GridLayout(3, 3));

		whitePlayerPanel = new JPanel();
		whitePlayerComboPanel = new JPanel();
		whitePlayerPanel.setBorder(BorderFactory.createTitledBorder(null, "White Player", TitledBorder.TOP,
				TitledBorder.CENTER, new Font("times new roman", Font.BOLD, 18), Color.RED));
		whitePlayerPanel.setLayout(new BorderLayout());

		JPanel whitePlayerStats = new JPanel(new GridLayout(3, 3));
		whitePlayerStats.add(new JLabel("Name   :"));
		whitePlayerStats.add(new JLabel("Played :"));
		whitePlayerStats.add(new JLabel("Won    :"));

		whitePlayerCombo = new JComboBox<String>(getPlayerNames());

		whitePlayerScrollPane = new JScrollPane(whitePlayerCombo);
		whitePlayerComboPanel.setLayout(new FlowLayout());

		whitePlayerSelectButton = new Button("Select");
		whitePlayerSelectButton.addActionListener(new PlayerSelectionListener(0, this));
		newWhitePlayerButton = new Button("New Player");
		newWhitePlayerButton.addActionListener(new AddNewPlayerListener(0, this));

		whitePlayerComboPanel.add(whitePlayerScrollPane);
		whitePlayerPanel.add(whitePlayerComboPanel, BorderLayout.NORTH);
		whitePlayerComboPanel.add(whitePlayerSelectButton);
		whitePlayerComboPanel.add(newWhitePlayerButton);
		whitePlayerPanel.add(whitePlayerStats, BorderLayout.WEST);
	}

	
	private String[] getTheme() {
		List<String> themeNames = new ArrayList<String>();
		themeNames.add("Default");
		themeNames.add("Leipzig");
		themeNames.add("Merida");
		themeNames.add("Cheq");
		themeNames.add("Alpha");

		return themeNames.toArray(new String[themeNames.size()]);
	}

	private void createThemePanel() {
		themePanelDetail = new JPanel(new GridLayout(1, 3));
		themePanel = new JPanel();
		themePanelCombo = new JPanel();
		themePanel.setBorder(BorderFactory.createTitledBorder(null, "Theme", TitledBorder.TOP, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 18), Color.RED));
		themePanel.setLayout(new BorderLayout());

		JPanel themeStats = new JPanel(new GridLayout(1, 3));
		themeStats.add(new JLabel("Theme   :"));

		themeCombo = new JComboBox<String>(getTheme());

		themeScrollPane = new JScrollPane(themeCombo);
		themePanelCombo.setLayout(new FlowLayout());

		themeSelectButton = new Button("Select");
		themeSelectButton.addActionListener(new ThemeSelectionListener(this));

		themePanelCombo.add(themeScrollPane);
		themePanel.add(themePanelCombo, BorderLayout.NORTH);
		themePanelCombo.add(themeSelectButton);
		themePanel.add(themeStats, BorderLayout.WEST);
	}

	private void createSavedGamesPanel() {

		savedGamesDetailsPanel = new JPanel(new GridLayout(3, 3));

		savedGamesPanel = new JPanel();
		savedGamesComboPanel = new JPanel();
		savedGamesPanel.setBorder(BorderFactory.createTitledBorder(null, "Saved Games", TitledBorder.TOP,
				TitledBorder.CENTER, new Font("times new roman", Font.BOLD, 18), Color.RED));
		savedGamesPanel.setLayout(new BorderLayout());

		savedGameCombo = new JComboBox<String>(SavedGame.fetchSavedGameList());

		savedGameScrollPane = new JScrollPane(savedGameCombo);
		savedGamesComboPanel.setLayout(new FlowLayout());

		savedGamesSelectButton = new Button("Select");
		savedGamesSelectButton.addActionListener(new SavedGamesListener(this));

		savedGamesComboPanel.add(savedGameScrollPane);
		savedGamesPanel.add(savedGamesComboPanel, BorderLayout.NORTH);
		savedGamesComboPanel.add(savedGamesSelectButton);
	}

	private void initializeTimer() {
		timeSlider = new JSlider();
		timeSlider.setMinimum(1);
		timeSlider.setMaximum(15);
		timeSlider.setValue(1);
		timeSlider.setMajorTickSpacing(2);
		timeSlider.setPaintLabels(true);
		timeSlider.setPaintTicks(true);
		timeSlider.addChangeListener(new TimeChangeListener(this));
	}

	private String[] getPlayerNames() {
		ArrayList<Player> playersList = Player.fetchPlayersData();
		Iterator<Player> witr = playersList.iterator();
		ArrayList<String> playerNames = new ArrayList<String>();
		while (witr.hasNext())
			playerNames.add(witr.next().getName());

		return playerNames.toArray(new String[playerNames.size()]);
	}


	public void setPlayersComboBoxData(int color, String selectedPlayer) {

		JComboBox<String> otherPlayerNamesComboBox = (color == 0) ? blackPlayerCombo
				: whitePlayerCombo;

		ArrayList<Player> otherPlayersList = Player.fetchPlayersData();
		ArrayList<Player> currentPlayersList = Player.fetchPlayersData();
		if (otherPlayersList.isEmpty())
			return;

		JPanel playerDetails = (color == 0) ? whitePlayerDetailsPanel
				: blackPlayerDetailsPnale;
		JPanel playerPanel = (color == 0) ? whitePlayerPanel : blackPlayerPanel;

		if (isSelected == true)
			playerDetails.removeAll();

		String playerName = selectedPlayer;
		Iterator<Player> currentPlayerIterator = currentPlayersList.iterator();
		Iterator<Player> otherPlayerIterator = otherPlayersList.iterator();
		while (currentPlayerIterator.hasNext()) {
			Player player = currentPlayerIterator.next();
			if (player.getName().equals(playerName)) {
				setSelectedPlayer(player);
				break;
			}
		}

		while (otherPlayerIterator.hasNext()) {
			Player p = otherPlayerIterator.next();
			if (p.getName().equals(playerName)) {
				otherPlayersList.remove(p);
				break;
			}
		}

		if (getSelectedPlayer() == null)
			return;

		if (color == 0)
			setWhitePlayer(getSelectedPlayer());
		else
			setBlackPlayer(getSelectedPlayer());

		otherPlayerNamesComboBox.removeAllItems();
		for (Player s : otherPlayersList)
			otherPlayerNamesComboBox.addItem(s.getName());

		playerDetails.add(new JLabel(" " + getSelectedPlayer().getName()));
		playerDetails.add(new JLabel(" " + getSelectedPlayer().gamesPlayed()));
		playerDetails.add(new JLabel(" " + getSelectedPlayer().gamesWon()));

		playerPanel.revalidate();
		playerPanel.repaint();
		playerPanel.add(playerDetails);
		isSelected = true;

	}

	
	public void changeTurn() {
		King king = chessBoardState.getCurrentKing();
		Cell kingsCell = chessBoardState.getCell(king);
		if (kingsCell.isCheck()) {
			chessBoardState.switchTurn();
			endGame();
		}

		if (!possibleDestinations.isEmpty()) {
			clearDestinationList(possibleDestinations);
		}

		if (previousCell != null) {
			previousCell.removeSelection();
			previousCell = null;
		}

		chessBoardState.switchTurn();
		if (!end && timer != null) {
			timer.resetTimer();
			timer.startTimer();
			playerViewPanel.remove(turnLabel);

			turnLabel.setText(chessBoardState.getTurnLabel());
			playerViewPanel.add(turnLabel);
		}
	}

	private void clearDestinationList(List<Cell> destList) {
		ListIterator<Cell> it = destList.listIterator();
		while (it.hasNext())
			it.next().removeValidDestination();
	}

	private void highlightDestinations(List<Cell> destList) {
		ListIterator<Cell> destListIterator = destList.listIterator();
		while (destListIterator.hasNext())
			destListIterator.next().setAsValidDestination();
	}

	private boolean isCheckAfetrMove(Cell source, Cell target) {
		return isMoveAllowed(source, target, chessBoardState.getCurrentTurn());
	}

	private boolean isMoveAllowed(Cell source, Cell target, final int turn) {
		BoardState newboardstate = null;
		try {
			newboardstate = (BoardState) chessBoardState.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		if (newboardstate.getPiece(target) != null)
			newboardstate.getCell(target).removePiece();

		newboardstate.getCell(target).setPiece(newboardstate.getPiece(source));
		if (newboardstate.getPiece(target) instanceof King) {
			newboardstate.updateKing(turn, target);
		}

		newboardstate.getCell(source).removePiece();
		if (newboardstate.getKingByTurn(turn).isindanger(
				newboardstate.getChessBoard()) == true)
			return true;
		else
			return false;
	}

	private List<Cell> filterAllowedMoves(List<Cell> possibleMovesList,
			final Cell source) {
		return allowedCheckMoves(possibleMovesList, source,
				chessBoardState.getCurrentTurn());
	}

	private List<Cell> allowedCheckMoves(List<Cell> possibleMovesList,
			final Cell source, final int turn) {
		List<Cell> allowedMoves = new ArrayList<Cell>();
		ListIterator<Cell> possibleMovesIterator = possibleMovesList
				.listIterator();

		while (possibleMovesIterator.hasNext()) {
			Cell target = possibleMovesIterator.next();
			if (!isMoveAllowed(source, target, turn)) {
				allowedMoves.add(target);
			}
		}

		return allowedMoves;
	}
	
//	Iterator pattern used..
	
//	private List<Cell> allowedCheckMoves(List<Cell> possibleMovesList,
//			final Cell source, final int turn) {
//		List<Cell> allowedMoves = new ArrayList<Cell>();
//		ListIterator<Cell> possibleMovesIterator = possibleMovesList
//				.listIterator();
//
//		while (possibleMovesIterator.hasNext()) {
//			Cell target = possibleMovesIterator.next();
//			if (!isMoveAllowed(source, target, turn)) {
//				allowedMoves.add(target);
//			}
//		}
//
//		return allowedMoves;
//	}

	
	public boolean isCheckMate(int color) {
		List<Cell> possibleMovesForKing = new ArrayList<Cell>();
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.COLUMNS; j++) {
				if (chessBoardState.getPiece(i, j) != null
						&& chessBoardState.getPiece(i, j).getcolor() == color) {
					possibleMovesForKing.clear();
					possibleMovesForKing = chessBoardState.getPiece(i, j)
							.calculatePossibleMoves(
									chessBoardState.getChessBoard(), i, j);
					possibleMovesForKing = allowedCheckMoves(
							possibleMovesForKing,
							chessBoardState.getCell(i, j), color);
					if (possibleMovesForKing.size() != 0)
						return false;
				}
			}
		}

		return true;
	}

	private void endGame() {
		clearDestinationList(possibleDestinations);

		stopTimer();
		declareWinner();
		disposeGameLayout();
		end = true;

		createGame();
		chessBoardState = new BoardState(boardPanel, this);
		gameSound = new SoundClass();

		setVisible(true);
		setResizable(false);
	}

	@SuppressWarnings("deprecation")
	private void disposeGameLayout() {
		whitePlayerPanel.remove(whitePlayerDetailsPanel);
		blackPlayerPanel.remove(blackPlayerDetailsPnale);
		timeDisplayPanle.remove(timeLabel);

		timeDisplayPanle.add(startButton);
		playerViewPanel.remove(moveLabel);
		playerViewPanel.remove(turnLabel);
		playerViewPanel.revalidate();
		playerViewPanel.add(timeSlider);

		splitPane.remove(boardPanel);
		splitPane.add(temporaryPanel);
		newWhitePlayerButton.enable();
		newBlackPlayerButton.enable();
		whitePlayerSelectButton.enable();
		blackPlayerSelectButton.enable();

		themeSelectButton.enable();
	}

	@SuppressWarnings("deprecation")
	private void stopTimer() {
		timeDisplayPanle.disable();
		timer.countDownTimer.stop();
	}

	private void declareWinner() {
		String winner = null;
		if (previousCell != null)
			previousCell.removePiece();
		if (chessBoardState.getCurrentTurn() == 0) {
			getWhitePlayer().updateGamesWon();
			getWhitePlayer().updatePlayersData();
			winner = getWhitePlayer().getName();
		} else {
			getBlackPlayer().updateGamesWon();
			getBlackPlayer().updatePlayersData();
			winner = getBlackPlayer().getName();
		}
		gameSound.playWinSound(true);
		JOptionPane.showMessageDialog(boardPanel, "Checkmate!!!\n" + winner
				+ " wins");
	}

	public void performAction(Cell currentCell) {
		if (previousCell == null) {
			if (!showValidMoves(currentCell)) {
				return;
			}
		} else {
			performMove(currentCell);
		}

		if (currentCell.getpiece() != null
				&& currentCell.getpiece() instanceof King) {
			chessBoardState.updateKing(((King) currentCell.getpiece()),
					currentCell);
		}
	}

	private void performMove(Cell currentCell) {
		if (currentCell.getXIndex() == previousCell.getXIndex()
				&& currentCell.getYIndex() == previousCell.getYIndex()) {
			deSelectCell(currentCell);
			previousCell = null;
		} else if (currentCell.getpiece() == null
				|| previousCell.getpiece().getcolor() != currentCell.getpiece()
						.getcolor()) {
			if (currentCell.isValidDestination()) {
				movePiece(currentCell);
				tryCheckOtherKing();

				if (chessBoardState.getCurrentKing().isindanger(
						chessBoardState.getChessBoard()) == false) {
					chessBoardState.getCell(chessBoardState.getCurrentKing())
							.removeCheck();
				}

				if (currentCell.getpiece() instanceof King) {
					chessBoardState.updateKing(
							chessBoardState.getCurrentTurn(), currentCell);
				}

				changeTurn();
			}

			deSelectCell(previousCell);
			previousCell = null;

		} else if (previousCell.getpiece().getcolor() == currentCell.getpiece()
				.getcolor()) {
			deSelectCell(previousCell);
			if (!showValidMoves(currentCell)) {
				return;
			}
		}
	}

	private void tryCheckOtherKing() {
		if (chessBoardState.getAlternateKing().isindanger(
				chessBoardState.getChessBoard())) {
			chessBoardState.getCell(chessBoardState.getAlternateKing())
					.setCheck();
			if (isCheckMate(chessBoardState.getAlternateKing().getcolor())) {
				previousCell.removeSelection();
				if (previousCell.getpiece() != null)
					previousCell.removePiece();
				endGame();
			}
		}
	}

	private void movePiece(Cell currentCell) {
		if (currentCell.getpiece() != null)
			currentCell.removePiece();
		currentCell.setPiece(previousCell.getpiece());
		if (previousCell.isCheck())
			previousCell.removeCheck();
		previousCell.removePiece();

		Piece piece = currentCell.getpiece();
		gameSound.playSound(piece);
	}

	private void deSelectCell(Cell currentCell) {
		if (currentCell != null) {
			currentCell.removeSelection();
		}
		clearDestinationList(possibleDestinations);
		possibleDestinations.clear();
	}

	private boolean showValidMoves(Cell currentCell) {
		if (currentCell.getpiece() != null) {
			if (currentCell.getpiece().getcolor() != chessBoardState
					.getCurrentTurn())
				return false;
			currentCell.select();
			previousCell = currentCell;
			possibleDestinations.clear();
			possibleDestinations = currentCell.getpiece()
					.calculatePossibleMoves(chessBoardState.getChessBoard(),
							currentCell.getXIndex(), currentCell.getYIndex());
			if (currentCell.getpiece() instanceof King) {
				possibleDestinations = filterAllowedMoves(possibleDestinations,
						currentCell);
			} else {
				if (chessBoardState.getCell(chessBoardState.getCurrentKing())
						.isCheck())
					possibleDestinations = new ArrayList<Cell>(
							filterAllowedMoves(possibleDestinations,
									currentCell));
				else if (possibleDestinations.isEmpty() == false
						&& isCheckAfetrMove(currentCell,
								possibleDestinations.get(0)))
					possibleDestinations.clear();
			}
			highlightDestinations(possibleDestinations);
		}
		return true;
	}

	
	public Cell getPreviousCell() {
		return previousCell;
	}

	public void setPreviousCell(Cell previousCell) {
		this.previousCell = previousCell;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public BoardState getChessBoardState() {
		return chessBoardState;
	}

	public void setChessBoardState(BoardState chessBoardState) {
		this.chessBoardState = chessBoardState;
	}

	public List<Cell> getPossibleDestinations() {
		return possibleDestinations;
	}

	public void setPossibleDestinations(List<Cell> possibleDestinations) {
		this.possibleDestinations = possibleDestinations;
	}

	public Player getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(Player whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

	public Player getBlackPlayer() {
		return blackPlayer;
	}

	public void setBlackPlayer(Player blackPlayer) {
		this.blackPlayer = blackPlayer;
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}

	public JPanel getBoardPanel() {
		return boardPanel;
	}

	public void setBoardPanel(JPanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	public JPanel getControlPanel() {
		return controlPanel;
	}

	public void setControlPanel(JPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public JPanel getTemporaryPanel() {
		return temporaryPanel;
	}

	public void setTemporaryPanel(JPanel temporaryPanel) {
		this.temporaryPanel = temporaryPanel;
	}

	public JPanel getTimeDisplayPanle() {
		return timeDisplayPanle;
	}

	public void setTimeDisplayPanle(JPanel timeDisplayPanle) {
		this.timeDisplayPanle = timeDisplayPanle;
	}

	public JPanel getPlayerViewPanel() {
		return playerViewPanel;
	}

	public void setPlayerViewPanel(JPanel playerViewPanel) {
		this.playerViewPanel = playerViewPanel;
	}

	public JPanel getTimePanel() {
		return timePanel;
	}

	public void setTimePanel(JPanel timePanel) {
		this.timePanel = timePanel;
	}

	public JPanel getWhitePlayerDetailsPanel() {
		return whitePlayerDetailsPanel;
	}

	public void setWhitePlayerDetailsPanel(JPanel whitePlayerDetailsPanel) {
		this.whitePlayerDetailsPanel = whitePlayerDetailsPanel;
	}

	public JPanel getWhitePlayerComboPanel() {
		return whitePlayerComboPanel;
	}

	public void setWhitePlayerComboPanel(JPanel whitePlayerComboPanel) {
		this.whitePlayerComboPanel = whitePlayerComboPanel;
	}

	public JPanel getWhitePlayerPanel() {
		return whitePlayerPanel;
	}

	public void setWhitePlayerPanel(JPanel whitePlayerPanel) {
		this.whitePlayerPanel = whitePlayerPanel;
	}

	public JPanel getThemePanelDetail() {
		return themePanelDetail;
	}

	public void setThemePanelDetail(JPanel themePanelDetail) {
		this.themePanelDetail = themePanelDetail;
	}

	public JPanel getThemePanelCombo() {
		return themePanelCombo;
	}

	public void setThemePanelCombo(JPanel themePanelCombo) {
		this.themePanelCombo = themePanelCombo;
	}

	public JPanel getThemePanel() {
		return themePanel;
	}

	public void setThemePanel(JPanel themePanel) {
		this.themePanel = themePanel;
	}

	public JPanel getSavedGamesDetailsPanel() {
		return savedGamesDetailsPanel;
	}

	public void setSavedGamesDetailsPanel(JPanel savedGamesDetailsPanel) {
		this.savedGamesDetailsPanel = savedGamesDetailsPanel;
	}

	public JPanel getSavedGamesComboPanel() {
		return savedGamesComboPanel;
	}

	public void setSavedGamesComboPanel(JPanel savedGamesComboPanel) {
		this.savedGamesComboPanel = savedGamesComboPanel;
	}

	public JPanel getSavedGamesPanel() {
		return savedGamesPanel;
	}

	public void setSavedGamesPanel(JPanel savedGamesPanel) {
		this.savedGamesPanel = savedGamesPanel;
	}

	public JPanel getBlackPlayerDetailsPnale() {
		return blackPlayerDetailsPnale;
	}

	public void setBlackPlayerDetailsPnale(JPanel blackPlayerDetailsPnale) {
		this.blackPlayerDetailsPnale = blackPlayerDetailsPnale;
	}

	public JPanel getBlackPlayerComboPanel() {
		return blackPlayerComboPanel;
	}

	public void setBlackPlayerComboPanel(JPanel blackPlayerComboPanel) {
		this.blackPlayerComboPanel = blackPlayerComboPanel;
	}

	public JPanel getBlackPlayerPanel() {
		return blackPlayerPanel;
	}

	public void setBlackPlayerPanel(JPanel blackPlayerPanel) {
		this.blackPlayerPanel = blackPlayerPanel;
	}

	public Player getWhitePlayerData() {
		return whitePlayerData;
	}

	public void setWhitePlayerData(Player whitePlayerData) {
		this.whitePlayerData = whitePlayerData;
	}

	public Player getBlackPlayerData() {
		return blackPlayerData;
	}

	public void setBlackPlayerData(Player blackPlayerData) {
		this.blackPlayerData = blackPlayerData;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public JLabel getTimeLabel() {
		return timeLabel;
	}

	public void setTimeLabel(JLabel timeLabel) {
		this.timeLabel = timeLabel;
	}

	public JLabel getMoveLabel() {
		return moveLabel;
	}

	public void setMoveLabel(JLabel moveLabel) {
		this.moveLabel = moveLabel;
	}

	public JLabel getTurnLabel() {
		return turnLabel;
	}

	public void setTurnLabel(JLabel turnLabel) {
		this.turnLabel = turnLabel;
	}

	public Time getTimer() {
		return timer;
	}

	public void setTimer(Time timer) {
		this.timer = timer;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public boolean isThemeSelected() {
		return isThemeSelected;
	}

	public void setThemeSelected(boolean isThemeSelected) {
		this.isThemeSelected = isThemeSelected;
	}

	public Container getContent() {
		return content;
	}

	public void setContent(Container content) {
		this.content = content;
	}

	public JComboBox<String> getWhitePlayerCombo() {
		return whitePlayerCombo;
	}

	public void setWhitePlayerCombo(JComboBox<String> whitePlayerCombo) {
		this.whitePlayerCombo = whitePlayerCombo;
	}

	public JComboBox<String> getBlackPlayerCombo() {
		return blackPlayerCombo;
	}

	public void setBlackPlayerCombo(JComboBox<String> blackPlayerCombo) {
		this.blackPlayerCombo = blackPlayerCombo;
	}

	public JComboBox<String> getThemeCombo() {
		return themeCombo;
	}

	public void setThemeCombo(JComboBox<String> themeCombo) {
		this.themeCombo = themeCombo;
	}

	public JComboBox<String> getSavedGameCombo() {
		return savedGameCombo;
	}

	public void setSavedGameCombo(JComboBox<String> savedGameCombo) {
		this.savedGameCombo = savedGameCombo;
	}

	public String getGameTheme() {
		return gameTheme;
	}

	public void setGameTheme(String gameTheme) {
		this.gameTheme = gameTheme;
	}

	public String getGameThemeCode() {
		return gameThemeCode;
	}

	public void setGameThemeCode(String gameThemeCode) {
		this.gameThemeCode = gameThemeCode;
	}

	public JScrollPane getWhitePlayerScrollPane() {
		return whitePlayerScrollPane;
	}

	public void setWhitePlayerScrollPane(JScrollPane whitePlayerScrollPane) {
		this.whitePlayerScrollPane = whitePlayerScrollPane;
	}

	public JScrollPane getBlackPlayerScrollPane() {
		return blackPlayerScrollPane;
	}

	public void setBlackPlayerScrollPane(JScrollPane blackPlayerScrollPane) {
		this.blackPlayerScrollPane = blackPlayerScrollPane;
	}

	public JScrollPane getThemeScrollPane() {
		return themeScrollPane;
	}

	public void setThemeScrollPane(JScrollPane themeScrollPane) {
		this.themeScrollPane = themeScrollPane;
	}

	public JScrollPane getSavedGameScrollPane() {
		return savedGameScrollPane;
	}

	public void setSavedGameScrollPane(JScrollPane savedGameScrollPane) {
		this.savedGameScrollPane = savedGameScrollPane;
	}

	public JSlider getTimeSlider() {
		return timeSlider;
	}

	public void setTimeSlider(JSlider timeSlider) {
		this.timeSlider = timeSlider;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Button getStartButton() {
		return startButton;
	}

	public void setStartButton(Button startButton) {
		this.startButton = startButton;
	}

	public Button getWhitePlayerSelectButton() {
		return whitePlayerSelectButton;
	}

	public void setWhitePlayerSelectButton(Button whitePlayerSelectButton) {
		this.whitePlayerSelectButton = whitePlayerSelectButton;
	}

	public Button getBlackPlayerSelectButton() {
		return blackPlayerSelectButton;
	}

	public void setBlackPlayerSelectButton(Button blackPlayerSelectButton) {
		this.blackPlayerSelectButton = blackPlayerSelectButton;
	}

	public Button getNewWhitePlayerButton() {
		return newWhitePlayerButton;
	}

	public void setNewWhitePlayerButton(Button newWhitePlayerButton) {
		this.newWhitePlayerButton = newWhitePlayerButton;
	}

	public Button getNewBlackPlayerButton() {
		return newBlackPlayerButton;
	}

	public void setNewBlackPlayerButton(Button newBlackPlayerButton) {
		this.newBlackPlayerButton = newBlackPlayerButton;
	}

	public Button getSavedGamesSelectButton() {
		return savedGamesSelectButton;
	}

	public void setSavedGamesSelectButton(Button savedGamesSelectButton) {
		this.savedGamesSelectButton = savedGamesSelectButton;
	}

	public Button getThemeSelectButton() {
		return themeSelectButton;
	}

	public void setThemeSelectButton(Button themeSelectButton) {
		this.themeSelectButton = themeSelectButton;
	}

	public BoardState getSavedState() {
		return savedState;
	}

	public void setSavedState(BoardState savedState) {
		this.savedState = savedState;
	}

	public SoundClass getGameSound() {
		return gameSound;
	}

	public void setGameSound(SoundClass gameSound) {
		this.gameSound = gameSound;
	}	
	
	
	
}
