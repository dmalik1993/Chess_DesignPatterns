package pieces;

import java.io.Serializable;

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
		calculatePossibleMoves = new BishopPossibleMoves(this);
		
	}



	
}