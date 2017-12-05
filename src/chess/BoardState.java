package chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

import javax.swing.JPanel;

import pieces.King;
import pieces.Piece;

public class BoardState implements MouseListener, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3491934661431520753L;
	private Board chessBoard;
	private List<Player> playerDetail;
	private static transient Main mainClassRef;

	public BoardState(JPanel boardPanel, Main ref) {
		mainClassRef = ref;
		chessBoard = new Board();

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
	
	public BoardState(JPanel boardPanel, Main ref, BoardState savedState) {
		BoardState.mainClassRef = ref;
		chessBoard = new Board(savedState.chessBoard);

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
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		BoardState newBoardState = new BoardState(this);
		return newBoardState;
	}

	public King getKing(int color) {
		if (color == 0)
			return chessBoard.getWhiteKing();
		else
			return chessBoard.getBlackKing();
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
		mainClassRef.performAction((Cell) arg0.getSource());
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

}
