package chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import pieces.King;
import pieces.Piece;

public class BoardState implements MouseListener {
	private Board chessBoard;

	private Main mainClassRef;

	public BoardState(JPanel boardPanel, Main ref) {
		mainClassRef = ref;
		chessBoard = new Board();

		for (int i = 0; i < Board.ROWS; i++)
			for (int j = 0; j < Board.COLUMNS; j++) {
				Cell cell = chessBoard.getCell(i, j);
				if (cell == null) {
					System.out.println("Cell: " + i + " - " + j + " is NULL");
				}

				cell.addMouseListener(this);
				boardPanel.add(cell);
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		mainClassRef.makeMove((Cell) arg0.getSource());
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
