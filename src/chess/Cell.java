package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pieces.Piece;

/**
 * This is the Cell Class. It is the token class of our GUI. There are total of
 * 64 cells that together makes up the Chess Board
 *
 */
public class Cell extends JPanel implements Cloneable, Serializable{

	private static final long serialVersionUID = 6785655684481256033L;
	private boolean validDestination;
	private JLabel contentLabel;
	private Piece piece;
	private int xCoordinate;
	private int yCoordinate;
	private boolean selected;
	private boolean isChecked;

	public Cell(final int xVlaue, final int yValue, final Piece piece) {
		super();
		this.setXIndex(xVlaue);
		this.setYIndex(yValue);
		selected = false;
		isChecked = false;

		setLayout(new BorderLayout());

		if ((xVlaue + yValue) % 2 == 0) {
			setBackground(new Color(113, 198, 113));
		} else {
			setBackground(Color.white);
		}

		if (piece != null) {
			setPiece(piece);
		}
	}

	public Cell(final Cell cell) throws CloneNotSupportedException {
		super();
		this.setXIndex(cell.getXIndex());
		this.setYIndex(cell.getYIndex());
		selected = false;
		isChecked = false;

		setLayout(new BorderLayout());
		if ((getXIndex() + getYIndex()) % 2 == 0) {
			setBackground(new Color(113, 198, 113));
		} else {
			setBackground(Color.white);
		}

		if (cell.getpiece() != null) {
			setPiece(cell.getpiece().getcopy());
		}
	}

	public final void setPiece(Piece pieceValue) {
		piece = pieceValue;
		ImageIcon icon = new ImageIcon(this.getClass().getResource(pieceValue.getPath()));
		contentLabel = new JLabel(icon);
		this.add(contentLabel);
	}

	public void removePiece() {
		piece = null;
		this.remove(contentLabel);
	}

	public Piece getpiece() {
		return this.piece;
	}

	public void select() {
		this.setBorder(BorderFactory.createLineBorder(Color.red, 6));
		this.selected = true;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void removeSelection() {
		this.setBorder(null);
		this.selected = false;
	}

	public void setAsValidDestination() {
		this.setBorder(BorderFactory.createLineBorder(Color.blue, 4));
		this.validDestination = true;
	}

	public void removeValidDestination() {
		this.setBorder(null);
		this.validDestination = false;
	}

	public boolean isValidDestination() {
		return this.validDestination;
	}

	public void setCheck() {
		this.setBackground(Color.RED);
		this.isChecked = true;
	}

	public void removeCheck() {
		this.setBorder(null);
		if ((getXIndex() + getYIndex()) % 2 == 0) {
			setBackground(new Color(113, 198, 113));
		} else {
			setBackground(Color.white);
		}
		
		this.isChecked = false;
	}

	public boolean isCheck() {
		return isChecked;
	}

	public final int getXIndex() {
		return xCoordinate;
	}

	void setXIndex(final int value) {
		this.xCoordinate = value;
	}

	public final int getYIndex() {
		return yCoordinate;
	}

	void setYIndex(final int value) {
		this.yCoordinate = value;
	}
}