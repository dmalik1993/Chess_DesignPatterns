package pieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import chess.Cell;

public class QueenPossibleMoves implements CalculatePossibleMoves, Serializable {
	public QueenPossibleMoves (Piece piece){
		this.piece = piece;
		
	}
	public List<Cell> possibleMoves = new ArrayList<Cell>();
	Piece piece;
	@Override
	public List<Cell> getPossibleMoves(Cell[][] state, int x, int y) {
		// Queen has most number of possible moves
		// Queen can move any number of steps in all 8 direction
		// The possible moves of queen is a combination of Rook and Bishop
		possibleMoves.clear();

		moveVerticalUp(x - 1, y, state);
		moveVerticalDown(x + 1, y, state);
		moveHorizontalLeft(x, y - 1, state);
		moveHorizontalRight(x, y + 1, state);
		moveLeftDiagonalDown(x + 1, y - 1, state);
		moveRightDiagonalUp(x - 1, y + 1, state);
		moveLeftDiagonalUp(x - 1, y - 1, state);
		moveRightDiagonalDown(x + 1, y + 1, state);

		return possibleMoves;
	}
	
	public boolean isMovementAllowedDiagonal(int horizontalAxis, int verticalAxis, Cell[][] state) {

		if (state[horizontalAxis][verticalAxis].getpiece() == null)
			possibleMoves.add(state[horizontalAxis][verticalAxis]);
		else if (state[horizontalAxis][verticalAxis].getpiece().getcolor() == this.piece.getcolor())
			return false;
		else {
			possibleMoves.add(state[horizontalAxis][verticalAxis]);
			return false;
		}
		return true;
	}
	
	public boolean isMovementAllowedHorizontalVertical(int horizontalAxis, int verticalAxis, Cell[][] state) {

		if (state[horizontalAxis][verticalAxis].getpiece() == null)
			possibleMoves.add(state[horizontalAxis][verticalAxis]);
		else if (state[horizontalAxis][verticalAxis].getpiece().getcolor() == this.piece.getcolor())
			return false;
		else {
			possibleMoves.add(state[horizontalAxis][verticalAxis]);
			return false;
		}
		return true;
	}
	public void moveVerticalUp(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempX = horizontalAxis;
		while (tempX >= 0) {
			if (!isMovementAllowedHorizontalVertical(tempX, verticalAxis, state)) {
				break;
			}
			tempX--;
		}
	}
	public void moveVerticalDown(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempX = horizontalAxis;
		while (tempX < 8) {
			if (!isMovementAllowedHorizontalVertical(tempX, verticalAxis, state)) {
				break;
			}
			tempX++;
		}
	}
	private void moveHorizontalRight(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempY = verticalAxis;
		while (tempY < 8) {
			if (!isMovementAllowedHorizontalVertical(horizontalAxis, tempY, state)) {
				break;
			}
			tempY++;
		}
	}

	private void moveHorizontalLeft(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempY = verticalAxis;

		while (tempY >= 0) {
			if (!isMovementAllowedHorizontalVertical(horizontalAxis, tempY, state)) {
				break;
			}
			tempY--;
		}

	}
	private void moveRightDiagonalUp(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempX = horizontalAxis;
		int tempY = verticalAxis;
		while (tempX >= 0 && tempY < 8) {
			if (!isMovementAllowedDiagonal(tempX, tempY, state)) {
				break;
			}
			tempX--;
			tempY++;
		}

	}

	private void moveLeftDiagonalDown(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempX = horizontalAxis;
		int tempY = verticalAxis;
		while (tempX < 8 && tempY >= 0) {
			if (!isMovementAllowedDiagonal(tempX, tempY, state)) {
				break;
			}
			tempX++;
			tempY--;
		}

	}
	private void moveRightDiagonalDown(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempX = horizontalAxis;
		int tempY = verticalAxis;
		while (tempX < 8 && tempY < 8) {
			if (!isMovementAllowedDiagonal(tempX, tempY, state)) {
				break;
			}
			tempX++;
			tempY++;
		}

	}

	private void moveLeftDiagonalUp(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempX = horizontalAxis;
		int tempY = verticalAxis;
		while (tempX >= 0 && tempY >= 0) {
			if (!isMovementAllowedDiagonal(tempX, tempY, state)) {
				break;
			}
			tempX--;
			tempY--;
		}

	}
}
