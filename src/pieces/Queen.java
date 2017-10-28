package pieces;

import java.util.List;

import chess.Cell;

/**
 * This is the Queen Class inherited from the abstract Piece class
 *
 */
public class Queen extends Piece{
	
	//Constructors
	public Queen(String i,String p,int c)
	{
		setId(i);
		setPath(p);
		setColor(c);
	}
	
	public boolean calculateMoveHorizontal(int horizontalAxis,int verticalAxis, Cell[][] state )
	{

		if(state[horizontalAxis][verticalAxis].getpiece()==null)
			possiblemoves.add(state[horizontalAxis][verticalAxis]);
		else if(state[horizontalAxis][verticalAxis].getpiece().getcolor()==this.getcolor())
			return false;
		else
		{
			possiblemoves.add(state[horizontalAxis][verticalAxis]);
			return false;
		}
		return true;

	}
	
	public boolean calculateMoveDiagonal(int horizontalAxis,int verticalAxis, Cell[][] state )
	{

		if(state[horizontalAxis][verticalAxis].getpiece()==null)
			possiblemoves.add(state[horizontalAxis][verticalAxis]);
		else if(state[horizontalAxis][verticalAxis].getpiece().getcolor()==this.getcolor())
			return false;
		else
		{
			possiblemoves.add(state[horizontalAxis][verticalAxis]);
			return false;
		}
		return true;

	}
	
	//Move Function Defined
	public List<Cell> move(Cell[][] state,int x,int y)
	{
		//Queen has most number of possible moves
		//Queen can move any number of steps in all 8 direction
		//The possible moves of queen is a combination of Rook and Bishop
		possiblemoves.clear();
		
		//Checking possible moves in vertical direction
		int tempx=x-1;
		while(tempx>=0)
		{
			if(!calculateMoveHorizontal(tempx, y, state))
			{ 
				break;
			}
			tempx--;
		}
		
		tempx=x+1;
		while(tempx<8)
		{
			if(!calculateMoveHorizontal(tempx, y, state))
			{ 
				break;
			}	tempx++;
		}
		
		
		//Checking possible moves in horizontal Direction
		int tempy=y-1;
		while(tempy>=0)
		{
			if(!calculateMoveHorizontal(x, tempy, state))
			{ 
				break;
			}
			tempy--;
		}
		tempy=y+1;
		while(tempy<8)
		{
			if(!calculateMoveHorizontal(x, tempy, state))
			{ 
				break;
			}	
			tempy++;
		}
		
		//Checking for possible moves in diagonal direction
		tempx=x+1;tempy=y-1;
		while(tempx<8&&tempy>=0)
		{
			if(!calculateMoveDiagonal(tempx, tempy, state))
			{ 
				break;
			}
			tempx++;
			tempy--;
		}
		tempx=x-1;tempy=y+1;
		while(tempx>=0&&tempy<8)
		{
			if(!calculateMoveDiagonal(tempx, tempy, state))
			{ 
				break;
			}
			tempx--;
			tempy++;
		}
		tempx=x-1;tempy=y-1;
		while(tempx>=0&&tempy>=0)
		{
			if(!calculateMoveDiagonal(tempx, tempy, state))
			{ 
				break;
			}
			tempx--;
			tempy--;
		}
		tempx=x+1;tempy=y+1;
		while(tempx<8&&tempy<8)
		{
			if(!calculateMoveDiagonal(tempx, tempy, state))
			{ 
				break;
			}
			tempx++;
			tempy++;
		}
		return possiblemoves;
	}
}