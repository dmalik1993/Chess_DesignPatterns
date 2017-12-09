package pieces;

import java.util.ArrayList;
import java.util.List;

import chess.Cell;

public class PawnPossibleMoves implements CalculatePossibleMoves {

	public PawnPossibleMoves (Piece piece){
		this.piece = piece;
		
	}

	Piece piece;
	public List<Cell> possibleMoves = new ArrayList<Cell>();
	public List<Cell> calculateMove(int horizontalAxis, int verticalAxis, int horizontalAxisShift,
			int deltaHorizontalAxis, boolean colorCheck, Cell[][] state) {
		List<Cell> moves = new ArrayList<Cell>();

		if (state[horizontalAxisShift][verticalAxis].getpiece() == null) {
			moves.add(state[horizontalAxisShift][verticalAxis]);
			if ((horizontalAxis == 6 && colorCheck) || (horizontalAxis == 1 && !colorCheck)
					&& state[deltaHorizontalAxis][verticalAxis].getpiece() == null) {
				moves.add(state[deltaHorizontalAxis][verticalAxis]);
			}
		}
		if ((verticalAxis > 0) && (state[horizontalAxisShift][verticalAxis - 1].getpiece() != null)
				&& (state[horizontalAxisShift][verticalAxis - 1].getpiece().getcolor() != this.piece.getcolor()))
			moves.add(state[horizontalAxisShift][verticalAxis - 1]);
		if ((verticalAxis < 7) && (state[horizontalAxisShift][verticalAxis + 1].getpiece() != null)
				&& (state[horizontalAxisShift][verticalAxis + 1].getpiece().getcolor() != this.piece.getcolor()))
			moves.add(state[horizontalAxisShift][verticalAxis + 1]);

		return moves;
	}

	public List<Cell> getPossibleMoves(Cell[][] state, int x, int y) {
		// Pawn can move only one step except the first chance when it may move
		// 2 steps
		// It can move in a diagonal fashion only for attacking a piece of
		// opposite color
		// It cannot move backward or move forward to attact a piece

		possibleMoves.clear();
		if (this.piece.getcolor() == 0) {
			if (x == 0)
				return possibleMoves;

			possibleMoves = calculateMove(x, y, x - 1, 4, true, state);
		}
		else {
			if (x == 8)
				return possibleMoves;

			possibleMoves = calculateMove(x, y, x + 1, 3, false, state);
		}
		
		return possibleMoves;
	}
	
	
}
