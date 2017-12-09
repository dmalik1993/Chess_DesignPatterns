package pieces;

import java.util.List;

import chess.Cell;

public interface CalculatePossibleMoves {
	
	public List<Cell> getPossibleMoves(Cell[][] pos, int x, int y);

	
}
