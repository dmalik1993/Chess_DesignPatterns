package chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.List;

import javax.swing.JPanel;

import pieces.King;
import pieces.Piece;

public class BoardState implements MouseListener, Serializable {
	/**
	 * 
	 */

	private static final String BLACK_PLAYER = "Black";
	private static final String WHITE_PLAYER = "White";
	private static final long serialVersionUID = -3491934661431520753L;
	private Board chessBoard;
	private List<Player> playerDetail;
	//private static transient Main contentLayout;
	private static transient ContentLayout contLayoutRef;
	private int currentTurn;
	
	public BoardState(JPanel boardPanel, ContentLayout ref) {
		contLayoutRef = ref;
		BoardState savedState = contLayoutRef.getSavedState();
		if (savedState == null) {
			chessBoard = new Board(contLayoutRef.getGameThemeCode());
			currentTurn = 0;
		} else {
			chessBoard = new Board(savedState.chessBoard);
			currentTurn = savedState.getCurrentTurn();
		}
		
		for (int i = 0; i < Board.ROWS; i++)
			for (int j = 0; j < Board.COLUMNS; j++) {
				Cell cell = chessBoard.getCell(i, j);
				if (cell == null) {
					System.out.println("Cell: " + i + " - " + j + " is NULL");
				} else {
					cell.addMouseListener(this);
					boardPanel.add(cell);
				}
			}
	}
	
	private BoardState(BoardState oldBoardState) {
		chessBoard = new Board(oldBoardState.chessBoard);
		currentTurn = oldBoardState.getCurrentTurn();
		playerDetail = oldBoardState.getPlayerDetail();
		contLayoutRef = oldBoardState.contLayoutRef;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		BoardState newBoardState = new BoardState(this);
		return newBoardState;
	}

	public King getAlternateKing() {
		return (currentTurn == 1) ? chessBoard.getWhiteKing() : chessBoard.getBlackKing();
	}
	
	public King getCurrentKing() {
		return (currentTurn == 0) ? chessBoard.getWhiteKing() : chessBoard.getBlackKing();
	}

	public King getKingByTurn(int turn) {
		return (turn == 0) ? chessBoard.getWhiteKing() : chessBoard.getBlackKing();
	}
	
	public void updateKing(int color, int x, int y) {
		if (color == 0)
			chessBoard.updateWhiteKingLocation(x, y);
		else
			chessBoard.updateBlackKingLocation(x, y);
	}

	public void updateKing(int color, Cell newCell) {
		if (color == 0)
			chessBoard.updateWhiteKingLocation(newCell.getXIndex(), newCell.getYIndex());
		else
			chessBoard.updateBlackKingLocation(newCell.getXIndex(), newCell.getYIndex());
	}

	public void updateKing(King piece, Cell newCell) {
		piece.setx(newCell.getXIndex());
		piece.sety(newCell.getYIndex());
	}

	public Cell[][] getChessBoard() {
		return chessBoard.getBoard();
	}

	public void setChessBoard(int i, int j, Piece piece) {
		chessBoard.getCell(i, j).setPiece(piece);
	}

	public Cell getCell(King piece) {
		return chessBoard.getCell(piece.getx(), piece.gety());
	}

	public Cell getCell(Cell cell) {
		return chessBoard.getCell(cell.getXIndex(), cell.getYIndex());
	}

	public Cell getCell(int i, int j) {
		return chessBoard.getCell(i, j);
	}

	public Piece getPiece(int i, int j) {
		return chessBoard.getPiece(i, j);
	}

	public Piece getPiece(Cell cell) {
		return chessBoard.getPiece(cell.getXIndex(), cell.getYIndex());
	}

	public List<Player> getPlayerDetail() {
		return playerDetail;
	}

	public void setPlayerDetail(List<Player> playerDetail) {
		this.playerDetail = playerDetail;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		contLayoutRef.performAction((Cell) arg0.getSource());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void switchTurn() {
		this.currentTurn ^= 1;
	}

	public String getTurnLabel() {
		return currentTurn == 0 ? WHITE_PLAYER : BLACK_PLAYER;
	}
}
