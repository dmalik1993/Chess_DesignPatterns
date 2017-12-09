package pieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import chess.Cell;

/**
 * This is the Pawn Class inherited from the piece
 *
 */
public class Pawn extends Piece implements Serializable{

	private static final long serialVersionUID = -7140638813121891929L;

	public Pawn(String i, String p, int c) {
		setId(i);
		setPath(p);
		setColor(c);
		calculatePossibleMoves = new PawnPossibleMoves(this);
	}


}
