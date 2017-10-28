package pieces;

import java.util.List;

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
	public List<Cell> move(Cell[][] state,int x,int y)
	{
		//Rook can move only horizontally or vertically
		possiblemoves.clear();
		int tempx=x-1;
		while(tempx>=0)
		{
			if(!moveHorizontal(tempx, y, state))
			{ 
				break;
			}
						tempx--;
		}
		tempx=x+1;
		while(tempx<8)
		{
			if(!moveHorizontal(tempx, y, state))
			{ 
				break;
			}
			tempx++;
		}
		int tempy=y-1;
		while(tempy>=0)
		{
			if(!moveVertical(x, tempy, state))
			{ 
				break;
			}
			tempy--;
		}
		tempy=y+1;
		while(tempy<8)
		{
			if(!moveVertical(x, tempy, state))
			{ 
				break;
			}
			tempy++;
		}
		return possiblemoves;
	}
	public boolean moveHorizontal(int horizontalAxis, int verticalAxis, Cell[][] state){
		if(state[horizontalAxis][verticalAxis].getpiece()==null)
		{
		possiblemoves.add(state[horizontalAxis][verticalAxis]);
		
		}
		else if(state[horizontalAxis][verticalAxis].getpiece().getcolor()==this.getcolor())
			return false;
		else
		{
			possiblemoves.add(state[horizontalAxis][verticalAxis]);
			return false;
		}
		return true;	
	}
	
	public boolean moveVertical(int horizontalAxis, int verticalAxis, Cell[][] state){
		if(state[horizontalAxis][verticalAxis].getpiece()==null)
		{
			possiblemoves.add(state[horizontalAxis][verticalAxis]);
		}
		else if(state[horizontalAxis][verticalAxis].getpiece().getcolor()==this.getcolor())
			return false;
		else
		{
			possiblemoves.add(state[horizontalAxis][verticalAxis]);
			return false;
		}
		return true;
        }
}