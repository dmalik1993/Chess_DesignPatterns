package pieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import chess.Cell;

public class RookPossibleMoves implements CalculatePossibleMoves, Serializable {
	public RookPossibleMoves (Piece piece){
		this.piece = piece;
		
	}
	public List<Cell> possibleMoves = new ArrayList<Cell>();
	Piece piece;
	@Override

	public List<Cell> getPossibleMoves(Cell[][] state, int x, int y) {
		possibleMoves.clear();
		int tempx = x - 1;
		while (tempx >= 0) {
			if (!moveHorizontal(tempx, y, state)) {
				break;
			}
			tempx--;
		}
		
		tempx = x + 1;
		while (tempx < 8) {
			if (!moveHorizontal(tempx, y, state)) {
				break;
			}
			tempx++;
		}
		
		int tempy = y - 1;
		while (tempy >= 0) {
			if (!moveVertical(x, tempy, state)) {
				break;
			}
			tempy--;
		}
		
		tempy = y + 1;
		while (tempy < 8) {
			if (!moveVertical(x, tempy, state)) {
				break;
			}
			tempy++;
		}
		
		return possibleMoves;
	}

	public boolean moveHorizontal(int horizontalAxis, int verticalAxis, Cell[][] state) {
		if (state[horizontalAxis][verticalAxis].getpiece() == null) {
			possibleMoves.add(state[horizontalAxis][verticalAxis]);

		} else if (state[horizontalAxis][verticalAxis].getpiece().getcolor() == this.piece.getcolor())
			return false;
		else {
			possibleMoves.add(state[horizontalAxis][verticalAxis]);
			return false;
		}
		
		return true;
	}

	public boolean moveVertical(int horizontalAxis, int verticalAxis, Cell[][] state) {
		if (state[horizontalAxis][verticalAxis].getpiece() == null) {
			possibleMoves.add(state[horizontalAxis][verticalAxis]);
		} else if (state[horizontalAxis][verticalAxis].getpiece().getcolor() == this.piece.getcolor())
			return false;
		else {
			possibleMoves.add(state[horizontalAxis][verticalAxis]);
			return false;
		}
		
		return true;
	}
}
