package pieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import chess.Cell;

/**
 * This is the Piece Class. It is an abstract class from which all the actual
 * pieces are inherited. It defines all the function common to all the pieces
 * The move() function an abstract function that has to be overridden in all the
 * inherited class It implements Cloneable interface as a copy of the piece is
 * required very often
 */
public abstract class Piece implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7992367302707280881L;
	private int color;
	private String id = null;
	private String path;
	
	protected List<Cell> possibleMoves = new ArrayList<Cell>(); 
	public abstract List<Cell> getPossibleMoves(Cell[][] pos, int x, int y);

	public void setId(String id) {
		this.id = id;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setColor(int c) {
		this.color = c;
	}

	public String getPath() {
		return path;
	}

	public String getId() {
		return id;
	}

	public int getcolor() {
		return this.color;
	}

	public Piece getcopy() throws CloneNotSupportedException {
		return (Piece) this.clone();
	}
}