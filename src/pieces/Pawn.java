package pieces;

import java.util.ArrayList;
import java.util.List;

import chess.Cell;

/**
 * This is the Pawn Class inherited from the piece
 *
 */
public class Pawn extends Piece{

	//COnstructors
	public Pawn(String i,String p,int c)
	{
		setId(i);
		setPath(p);
		setColor(c);
	}
	public List<Cell> calculateMove(int horizontalAxis, int verticalAxis, int horizontalAxisShift, int deltaHorizontalAxis, boolean colorCheck, Cell[][] state){
		List<Cell> moves = new ArrayList<Cell>();
		
		
		if(state[horizontalAxisShift][verticalAxis].getpiece()==null)
		{
			moves.add(state[horizontalAxisShift][verticalAxis]);
			if((horizontalAxis==6 && colorCheck) || (horizontalAxis==1 && !colorCheck))
			{
				if(state[deltaHorizontalAxis][verticalAxis].getpiece()==null)
					moves.add(state[deltaHorizontalAxis][verticalAxis]);
			}
		}
		if((verticalAxis>0)&&(state[horizontalAxisShift][verticalAxis-1].getpiece()!=null)&&(state[horizontalAxisShift][verticalAxis-1].getpiece().getcolor()!=this.getcolor()))
			moves.add(state[horizontalAxisShift][verticalAxis-1]);
		if((verticalAxis<7)&&(state[horizontalAxisShift][verticalAxis+1].getpiece()!=null)&&(state[horizontalAxisShift][verticalAxis+1].getpiece().getcolor()!=this.getcolor()))
			moves.add(state[horizontalAxisShift][verticalAxis+1]);
		
		return moves;
	}
	//Move Function Overridden
	public List<Cell> move(Cell[][] state,int x,int y)
	{
		//Pawn can move only one step except the first chance when it may move 2 steps
		//It can move in a diagonal fashion only for attacking a piece of opposite color
		//It cannot move backward or move forward to attact a piece

		possiblemoves.clear();
		if(getcolor()==0)
		{
			if(x==0)
				return possiblemoves;
//			if(state[x-1][y].getpiece()==null)
//			{
//				possiblemoves.add(state[x-1][y]);
//				if(x==6)
//				{
//					if(state[4][y].getpiece()==null)
//						possiblemoves.add(state[4][y]);
//				}
//			}
//			if((y>0)&&(state[x-1][y-1].getpiece()!=null)&&(state[x-1][y-1].getpiece().getcolor()!=this.getcolor()))
//				possiblemoves.add(state[x-1][y-1]);
//			if((y<7)&&(state[x-1][y+1].getpiece()!=null)&&(state[x-1][y+1].getpiece().getcolor()!=this.getcolor()))
//				possiblemoves.add(state[x-1][y+1]);
//			
			possiblemoves = calculateMove(x, y, x-1 , 4, true, state);
		}
		
		
		else
		{
			if(x==8)
				return possiblemoves;
//			if(state[x+1][y].getpiece()==null)
//			{
//				possiblemoves.add(state[x+1][y]);
//				if(x==1)
//				{
//					if(state[3][y].getpiece()==null)
//						possiblemoves.add(state[3][y]);
//				}
//			}
//
//
//			if((y>0)&&(state[x+1][y-1].getpiece()!=null)&&(state[x+1][y-1].getpiece().getcolor()!=this.getcolor()))
//				possiblemoves.add(state[x+1][y-1]);
//			if((y<7)&&(state[x+1][y+1].getpiece()!=null)&&(state[x+1][y+1].getpiece().getcolor()!=this.getcolor()))
//				possiblemoves.add(state[x+1][y+1]);
//			
			possiblemoves = calculateMove(x, y, x+1 ,3, false, state);
		}
		return possiblemoves;
	}
}
