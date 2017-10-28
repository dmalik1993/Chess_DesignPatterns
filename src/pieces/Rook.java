package pieces;

import java.util.ArrayList;

import chess.Cell;

/**
 * This is the Rook class inherited from abstract Piece class
 *
 */
public class Rook extends Piece{
	
	//Constructor
	public Rook(String i,String p,int c)
	{
		setId(i);
		setPath(p);
		setColor(c);
	}
	
	//Move function defined
	public ArrayList<Cell> move(Cell state[][],int x,int y)
	{
		//Rook can move only horizontally or vertically
		possibleMoves.clear();
		int tempx=x-1;
		while(tempx>=0)
		{
			if(state[tempx][y].getpiece()==null)
				possibleMoves.add(state[tempx][y]);
			else if(state[tempx][y].getpiece().getcolor()==this.getcolor())
				break;
			else
			{
				possibleMoves.add(state[tempx][y]);
				break;
			}
			tempx--;
		}
		tempx=x+1;
		while(tempx<8)
		{
			if(state[tempx][y].getpiece()==null)
				possibleMoves.add(state[tempx][y]);
			else if(state[tempx][y].getpiece().getcolor()==this.getcolor())
				break;
			else
			{
				possibleMoves.add(state[tempx][y]);
				break;
			}
			tempx++;
		}
		int tempy=y-1;
		while(tempy>=0)
		{
			if(state[x][tempy].getpiece()==null)
				possibleMoves.add(state[x][tempy]);
			else if(state[x][tempy].getpiece().getcolor()==this.getcolor())
				break;
			else
			{
				possibleMoves.add(state[x][tempy]);
				break;
			}
			tempy--;
		}
		tempy=y+1;
		while(tempy<8)
		{
			if(state[x][tempy].getpiece()==null)
				possibleMoves.add(state[x][tempy]);
			else if(state[x][tempy].getpiece().getcolor()==this.getcolor())
				break;
			else
			{
				possibleMoves.add(state[x][tempy]);
				break;
			}
			tempy++;
		}
		return possibleMoves;
	}
}
