package chess;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pieces.King;
import pieces.Piece;

/**
 * This is the Cell Class. It is the token class of our GUI. There are total of
 * 64 cells that together makes up the Chess Board
 *
 */
public class Cell extends JPanel implements Cloneable {

	private static final long serialVersionUID = 1L;
	private boolean isValidDestination;
	private JLabel contentLabel;
	private Piece piece;
	private int x;
	private int y;
	private boolean isSelected = false;
	private boolean isChecked = false;

	// Constructors
	public Cell(int x, int y, Piece p) {
		this.setXIndex(x);
		this.setYIndex(y);

		setLayout(new BorderLayout());

		if ((x + y) % 2 == 0)
			setBackground(new Color(113, 198, 113));

		else
			setBackground(Color.white);

		if (p != null)
			setPiece(p);
	}

	public Cell(Cell cell) throws CloneNotSupportedException {
		this.setXIndex(cell.getXIndex());
		this.setYIndex(cell.getYIndex());

		setLayout(new BorderLayout());
		if ((getXIndex() + getYIndex()) % 2 == 0)
			setBackground(new Color(113, 198, 113));
		else
			setBackground(Color.white);
		if (cell.getpiece() != null) {
			setPiece(cell.getpiece().getcopy());
		} else
			piece = null;
	}

	public void setPiece(Piece p) {
		piece = p;
		ImageIcon img = new javax.swing.ImageIcon(this.getClass().getResource(p.getPath()));
		contentLabel = new JLabel(img);
		this.add(contentLabel);
	}

	public void removePiece() {
		if (piece instanceof King) {
			piece = null;
			this.remove(contentLabel);
		} else {
			piece = null;
			this.remove(contentLabel);
		}
	}

	public Piece getpiece() {
		return this.piece;
	}

	public void select() {
		this.setBorder(BorderFactory.createLineBorder(Color.red, 6));
		this.isSelected = true;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void removeSelection() {
		this.setBorder(null);
		this.isSelected = false;
	}

	public void setAsValidDestination() {
		this.setBorder(BorderFactory.createLineBorder(Color.blue, 4));
		this.isValidDestination = true;
	}

	public void removeValidDestination() {
		this.setBorder(null);
		this.isValidDestination = false;
	}

	public boolean isValidDestination() {
		return this.isValidDestination;
	}

	public void setCheck() {
		this.setBackground(Color.RED);
		this.isChecked = true;
	}

	public void removeCheck() {
		this.setBorder(null);
		if ((getXIndex() + getYIndex()) % 2 == 0)
			setBackground(new Color(113, 198, 113));
		else
			setBackground(Color.white);
		this.isChecked = false;
	}

	public boolean isCheck() {
		return isChecked;
	}

	int getXIndex() {
		return x;
	}

	void setXIndex(int x) {
		this.x = x;
	}

	int getYIndex() {
		return y;
	}

	void setYIndex(int y) {
		this.y = y;
	}
}