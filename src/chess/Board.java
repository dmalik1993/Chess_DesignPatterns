package chess;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Board {
	private Cell board[][];
	public static final int ROWS = 8;
	public static final int COLUMNS = 8;

	private transient Rook whiteRook01, whiteRook02, blackRook01, blackRook02;
	private transient Knight whiteKinght01, whiteKnight02, blackKnight01, blackKnight02;
	private transient Bishop whiteBishop01, whiteBishop02, blackBishop01, blackBishop02;
	private Pawn whitePawn[], blackPawn[];
	private Queen whiteQueen, blackQueen;
	private King whiteKing, blackKing;

	public Board() {

		initializePieces();

		initializeBoard();
	}

	private void initializeBoard() {
		board = new Cell[ROWS][COLUMNS];

		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++) {

				Piece piece = null;
				if (i == 0 && j == 0)
					piece = blackRook01;
				else if (i == 0 && j == 7)
					piece = blackRook02;
				else if (i == 7 && j == 0)
					piece = whiteRook01;
				else if (i == 7 && j == 7)
					piece = whiteRook02;
				else if (i == 0 && j == 1)
					piece = blackKnight01;
				else if (i == 0 && j == 6)
					piece = blackKnight02;
				else if (i == 7 && j == 1)
					piece = whiteKinght01;
				else if (i == 7 && j == 6)
					piece = whiteKnight02;
				else if (i == 0 && j == 2)
					piece = blackBishop01;
				else if (i == 0 && j == 5)
					piece = blackBishop02;
				else if (i == 7 && j == 2)
					piece = whiteBishop01;
				else if (i == 7 && j == 5)
					piece = whiteBishop02;
				else if (i == 0 && j == 3)
					piece = blackKing;
				else if (i == 0 && j == 4)
					piece = blackQueen;
				else if (i == 7 && j == 3)
					piece = whiteKing;
				else if (i == 7 && j == 4)
					piece = whiteQueen;
				else if (i == 1)
					piece = blackPawn[j];
				else if (i == 6)
					piece = whitePawn[j];

				Cell cell = new Cell(i, j, piece);
				board[i][j] = cell;
			}
	}

	public Board(Board oldBoard) {
		initializePieces();
		initializeBoard();

		for (int i = 0; i < Board.ROWS; i++)
			for (int j = 0; j < Board.COLUMNS; j++) {
				try {
					board[i][j] = new Cell(oldBoard.getCell(i, j));
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}

		copyPieces(oldBoard);
	}

	private void copyPieces(final Board copyFrom) {
		whiteRook01 = copyFrom.whiteRook01;
		whiteRook02 = copyFrom.whiteRook02;
		blackRook01 = copyFrom.blackRook01;
		blackRook02 = copyFrom.blackRook02;
		whiteKinght01 = copyFrom.whiteKinght01;
		whiteKnight02 = copyFrom.whiteKnight02;
		blackKnight01 = copyFrom.blackKnight01;
		blackKnight02 = copyFrom.blackKnight02;
		whiteBishop01 = copyFrom.whiteBishop01;
		whiteBishop02 = copyFrom.whiteBishop02;
		blackBishop01 = copyFrom.blackBishop01;
		blackBishop02 = copyFrom.blackBishop02;
		whiteQueen = copyFrom.whiteQueen;
		blackQueen = copyFrom.blackQueen;
		whiteKing = copyFrom.whiteKing;
		blackKing = copyFrom.blackKing;
		whitePawn = copyFrom.whitePawn;
		blackPawn = copyFrom.blackPawn;
	}

	public Cell[][] getBoard() {
		return board;
	}

	private void initializePieces() {
		initializeRooks();

		initializeKnights();

		initializeBishops();

		initializeQueens();

		initializeKings();

		initializePawns();
	}

	private void initializePawns() {
		whitePawn = new Pawn[8];
		blackPawn = new Pawn[8];
		for (int i = 0; i < 8; i++) {
			whitePawn[i] = new Pawn("WP0" + (i + 1), "White_Pawn.png", 0);
			blackPawn[i] = new Pawn("BP0" + (i + 1), "Black_Pawn.png", 1);
		}
	}

	private void initializeKings() {
		whiteKing = new King("WK", "White_King.png", 0, 7, 3);
		blackKing = new King("BK", "Black_King.png", 1, 0, 3);
	}

	private void initializeQueens() {
		whiteQueen = new Queen("WQ", "White_Queen.png", 0);
		blackQueen = new Queen("BQ", "Black_Queen.png", 1);
	}

	private void initializeBishops() {
		whiteBishop01 = new Bishop("WB01", "White_Bishop.png", 0);
		whiteBishop02 = new Bishop("WB02", "White_Bishop.png", 0);
		blackBishop01 = new Bishop("BB01", "Black_Bishop.png", 1);
		blackBishop02 = new Bishop("BB02", "Black_Bishop.png", 1);
	}

	private void initializeKnights() {
		whiteKinght01 = new Knight("WK01", "White_Knight.png", 0);
		whiteKnight02 = new Knight("WK02", "White_Knight.png", 0);
		blackKnight01 = new Knight("BK01", "Black_Knight.png", 1);
		blackKnight02 = new Knight("BK02", "Black_Knight.png", 1);
	}

	private void initializeRooks() {
		whiteRook01 = new Rook("WR01", "White_Rook.png", 0);
		whiteRook02 = new Rook("WR02", "White_Rook.png", 0);
		blackRook01 = new Rook("BR01", "Black_Rook.png", 1);
		blackRook02 = new Rook("BR02", "Black_Rook.png", 1);
	}

	public King getBlackKing() {
		return blackKing;
	}

	public King getWhiteKing() {
		return whiteKing;

	}

	public void updateWhiteKingLocation(int x, int y) {
		whiteKing.setx(x);
		whiteKing.sety(y);

	}

	public void updateBlackKingLocation(int x, int y) {
		blackKing.setx(x);
		blackKing.sety(y);

	}

	public Cell getCell(int xIndex, int yIndex) {
		if (xIndex < 0 || xIndex >= ROWS || yIndex < 0 || yIndex >= COLUMNS)
			return null;

		return board[xIndex][yIndex];
	}

	public Piece getPiece(int xIndex, int yIndex) {
		Cell cell = getCell(xIndex, yIndex);
		if (cell == null)
			return null;

		return cell.getpiece();
	}
}
