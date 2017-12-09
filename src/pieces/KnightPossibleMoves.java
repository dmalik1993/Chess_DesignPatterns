package pieces;

import java.util.ArrayList;
import java.util.List;

import chess.Cell;

public class KnightPossibleMoves implements CalculatePossibleMoves {
	public KnightPossibleMoves (Piece piece){
		this.piece = piece;
		
	}
	public List<Cell> possibleMoves = new ArrayList<Cell>();
	Piece piece;
	
	public List<Cell> getPossibleMoves(Cell[][] state, int x, int y) {
		possibleMoves.clear();
		int[] posx = { x + 1, x + 1, x + 2, x + 2, x - 1, x - 1, x - 2, x - 2 };
		int[] posy = { y - 2, y + 2, y - 1, y + 1, y - 2, y + 2, y - 1, y + 1 };
		for (int i = 0; i < 8; i++) {
			boolean horizontalMoveAvailable = (posx[i] >= 0) && (posx[i] < 8);
			boolean verticalMoveAvailable = (posy[i] >= 0) && (posy[i] < 8);

			if (horizontalMoveAvailable && verticalMoveAvailable && (state[posx[i]][posy[i]].getpiece() == null
					|| state[posx[i]][posy[i]].getpiece().getcolor() != this.piece.getcolor()))
				possibleMoves.add(state[posx[i]][posy[i]]);

		}
		return possibleMoves;
	}
}
