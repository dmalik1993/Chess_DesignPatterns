package pieces;

import java.io.Serializable;

/**
 * This is the Rook class inherited from abstract Piece class
 *
 */
public class Rook extends Piece implements Serializable{

	private static final long serialVersionUID = -4831661029943412916L;

	public Rook(String i, String p, int c) {
		setId(i);
		setPath(p);
		setColor(c);
		calculatePossibleMoves = new RookPossibleMoves(this);
	}

	
}