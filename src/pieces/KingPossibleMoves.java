package pieces;

import java.util.ArrayList;
import java.util.List;

import chess.Cell;

public class KingPossibleMoves implements CalculatePossibleMoves {
	public KingPossibleMoves (Piece piece){
		this.piece = piece;
		
	}
	public List<Cell> possibleMoves = new ArrayList<Cell>();
	Piece piece;
	@Override
	public List<Cell> getPossibleMoves(Cell[][] state, int x, int y) {
		// King can move only one step. So all the adjacent 8 cells have been
		// considered.
		possibleMoves.clear();
		int[] posx = { x, x, x + 1, x + 1, x + 1, x - 1, x - 1, x - 1 };
		int[] posy = { y - 1, y + 1, y - 1, y, y + 1, y - 1, y, y + 1 };
		for (int i = 0; i < 8; i++) {
			boolean horizontalMoveAvailable = (posx[i] >= 0) && (posx[i] < 8);
			boolean verticalMoveAvailable = (posy[i] >= 0) && (posy[i] < 8);

			if (horizontalMoveAvailable && verticalMoveAvailable && chechPossibleMoves(i, state, posx, posy)) {
				possibleMoves.add(state[posx[i]][posy[i]]);
			}
		}
		return possibleMoves;
	}
	public boolean chechPossibleMoves(int i, Cell[][] state, int[] posx, int[] posy) {
		return (state[posx[i]][posy[i]].getpiece() == null
				|| state[posx[i]][posy[i]].getpiece().getcolor() != this.piece.getcolor());
	}

}
