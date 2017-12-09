package pieces;

import java.io.Serializable;

/**
 * This is the Knight Class inherited from the Piece abstract class
 * 
 *
 */
public class Knight extends Piece implements Serializable{

	private static final long serialVersionUID = 8528067482701744435L;

	public Knight(String i, String p, int c) {
		setId(i);
		setPath(p);
		setColor(c);
		calculatePossibleMoves = new KnightPossibleMoves(this);
	}


}