package pieces;

import java.io.Serializable;

/**
 * This is the Queen Class inherited from the abstract Piece class
 *
 */
public class Queen extends Piece implements Serializable{

	private static final long serialVersionUID = 3229077646533567816L;

	public Queen(String i, String p, int c) {
		setId(i);
		setPath(p);
		setColor(c);
		calculatePossibleMoves = new QueenPossibleMoves(this);
	}






}