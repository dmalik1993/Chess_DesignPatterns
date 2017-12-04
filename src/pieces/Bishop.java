package pieces;

import java.io.Serializable;
import java.util.List;

import chess.Cell;

/**
 * This is the Bishop Class. The Move Function defines the basic rules for
 * movement of Bishop on a chess board
 * 
 *
 */
public class Bishop extends Piece implements Serializable{
	private static final long serialVersionUID = -4211275853742637587L;


	public Bishop(String i, String p, int c) {
		setId(i);
		setPath(p);
		setColor(c);
	}

	public List<Cell> getPossibleMoves(Cell[][] state, int x, int y) {

		possibleMoves.clear();

		moveDiagonalLeftDown(x + 1, y - 1, state);
		moveDiagonalRightUp(x - 1, y + 1, state);
		moveDiagonalLefUp(x - 1, y - 1, state);
		moveDiagonalRightDown(x + 1, y + 1, state);

		return possibleMoves;
	}

	public boolean calculateMove(int horizontalAxis, int verticalAxis, Cell[][] state) {

		if (state[horizontalAxis][verticalAxis].getpiece() == null) {
			possibleMoves.add(state[horizontalAxis][verticalAxis]);
		} else if (state[horizontalAxis][verticalAxis].getpiece().getcolor() == this.getcolor())
			return false;
		else {
			possibleMoves.add(state[horizontalAxis][verticalAxis]);
			return false;
		}
		return true;

	}

	public void moveDiagonalLeftDown(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempHorizontalAxis = horizontalAxis;
		int tempVerticalAxis = verticalAxis;
		while (tempHorizontalAxis < 8 && tempVerticalAxis >= 0) {
			if (!calculateMove(tempHorizontalAxis, tempVerticalAxis, state)) {
				break;
			}
			tempHorizontalAxis++;
			tempVerticalAxis--;
		}
	}

	public void moveDiagonalRightDown(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempHorizontalAxis = horizontalAxis;
		int tempVerticalAxis = verticalAxis;
		while (tempHorizontalAxis < 8 && tempVerticalAxis < 8) {
			if (!calculateMove(tempHorizontalAxis, tempVerticalAxis, state)) {
				break;
			}
			tempHorizontalAxis++;
			tempVerticalAxis++;
		}
	}

	public void moveDiagonalLefUp(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempHorizontalAxis = horizontalAxis;
		int tempVerticalAxis = verticalAxis;
		while (tempHorizontalAxis >= 0 && tempVerticalAxis >= 0) {
			if (!calculateMove(tempHorizontalAxis, tempVerticalAxis, state)) {
				break;
			}
			tempHorizontalAxis--;
			tempVerticalAxis--;
		}
	}

	public void moveDiagonalRightUp(int horizontalAxis, int verticalAxis, Cell[][] state) {
		int tempHorizontalAxis = horizontalAxis;
		int tempVerticalAxis = verticalAxis;
		while (tempHorizontalAxis >= 0 && tempVerticalAxis < 8) {
			if (!calculateMove(tempHorizontalAxis, tempVerticalAxis, state)) {
				break;
			}
			tempHorizontalAxis--;
			tempVerticalAxis++;
		}
	}

}