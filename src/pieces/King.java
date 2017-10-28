package pieces;

import java.util.List;

import chess.Cell;

public class King extends Piece{
	
	private int x,y,tempx , tempy; //Extra variables for King class to keep a track of king's position
	
	//King Constructor
	public King(String i,String p,int c,int x,int y)
	{
		setx(x);
		sety(y);
		setId(i);
		setPath(p);
		setColor(c);
	}
	
	//general value access functions
	public void setx(int x)
	{
		this.x=x;
	}
	public void sety(int y)
	{
		this.y=y;
	}
	public int getx()
	{
		return x;
	}
	public int gety()
	{
		return y;
	}
	//Move Function for King Overridden from Pieces
	public List<Cell> move(Cell state[][],int x,int y)
	{
		//King can move only one step. So all the adjacent 8 cells have been considered.
		possiblemoves.clear();
		int posx[]={x,x,x+1,x+1,x+1,x-1,x-1,x-1};
		int posy[]={y-1,y+1,y-1,y,y+1,y-1,y,y+1};
		for(int i=0;i<8;i++)
		{
			boolean horizontalMoveAvailable = (posx[i] >= 0) && (posx[i] < 8);
			boolean verticalMoveAvailable = (posy[i] >= 0) && (posy[i] < 8);

			if(horizontalMoveAvailable && verticalMoveAvailable)
			{
				if((state[posx[i]][posy[i]].getpiece()==null||state[posx[i]][posy[i]].getpiece().getcolor()!=this.getcolor()))
					possiblemoves.add(state[posx[i]][posy[i]]);
			}
		}
		return possiblemoves;
	}
	
	public int isIndangerXandY(int x,int y, Cell[][] state )
	{
		if(state[x][y].getpiece()==null)
			return 2;
		else if(state[x][y].getpiece().getcolor()==this.getcolor())
			return 0;
		else
		{
			if ((state[x][y].getpiece() instanceof Rook) || (state[x][y].getpiece() instanceof Queen))
				return 1;
			else
				return 0;
		}
	}
	
	public int isIndangerDiagonal(boolean x,boolean y, Cell[][] state )
	{
		if(state[tempx][tempy].getpiece()==null)
		{
			if(x && !y){
			tempx++;
			tempy--;
			}
			if(!x && y)
			{
				
				tempx--;
				tempy++;
			}
			if (!x && !y)
			{
				tempx--;
				tempy--;
				
			}
			if(x && y)
			{
				
				tempx++;
				tempy++;
			}
			
			return 9;
		}
		else if(state[tempx][tempy].getpiece().getcolor()==this.getcolor())
			return 0;
		else
		{
			if (state[tempx][tempy].getpiece() instanceof Bishop || state[tempx][tempy].getpiece() instanceof Queen)
				return 1;
			else
				return 0;
		}
	}
	
	public boolean isIndangerFromOpposition(int[] posx,int[] posy, Cell[][] state , boolean king )
	{
		
		
		
		boolean danger = false;
		for(int i=0;i<8;i++)
		{
			if((posx[i]>=0&&posx[i]<8&&posy[i]>=0&&posy[i]<8))
				if(state[posx[i]][posy[i]].getpiece()!=null && state[posx[i]][posy[i]].getpiece().getcolor()!=this.getcolor())
				{
					if(king && (state[posx[i]][posy[i]].getpiece() instanceof Knight))
					{danger = true;}
					if(!king && (state[posx[i]][posy[i]].getpiece() instanceof King))
					{danger = true;}
				}
	      }
		return danger;
	}
	
	//Function to check if king is under threat
	//It checks whether there is any piece of opposite color that can attack king for a given board state
	public boolean isindanger(Cell[][] state)
    {
		int checkDangerXandY,checkDangerDiagonal;
		//Checking for attack from left,right,up and down
    	for(int i=x+1;i<8;i++)
    	{
    		checkDangerXandY = isIndangerXandY(i, y, state) ;
    		if(checkDangerXandY == 0)
    		{
    			break;
    		}
    		else if(checkDangerXandY == 1) 
    		{
    			return true;
    		}
    		else if(checkDangerXandY == 2) 
    		{
    			continue;
    		}
    	}
    	for(int i=x-1;i>=0;i--)
    	{
    		checkDangerXandY = isIndangerXandY(i, y, state) ;
    		if(checkDangerXandY == 0)
    		{
    			break;
    		}
    		else if(checkDangerXandY == 1) 
    		{
    			return true;
    		}
    		else if(checkDangerXandY == 2) 
    		{
    			continue;
    		}}
    	for(int i=y+1;i<8;i++)
    	{
    		checkDangerXandY = isIndangerXandY(x, i, state) ;
    		if(checkDangerXandY == 0)
    		{
    			break;
    		}
    		else if(checkDangerXandY == 1) 
    		{
    			return true;
    		}
    		else if(checkDangerXandY == 2) 
    		{
    			continue;
    		}
    	}
    	for(int i=y-1;i>=0;i--)
    	{checkDangerXandY = isIndangerXandY(x, i, state) ;
		if(checkDangerXandY == 0)
		{
			break;
		}
		else if(checkDangerXandY == 1) 
		{
			return true;
		}
		else if(checkDangerXandY == 2) 
		{
			continue;
		}}
    	
    	//checking for attack from diagonal direction
    	 tempx=x+1;tempy=y-1;
		while(tempx<8&&tempy>=0)
		{
			checkDangerDiagonal = isIndangerDiagonal(true , false , state);
			if(checkDangerDiagonal == 0)
    		{
    			break;
    		}
    		else if(checkDangerDiagonal == 1) 
    		{
    			return true;
    		}
		}
		tempx=x-1;tempy=y+1;
		while(tempx>=0&&tempy<8)
		{
			checkDangerDiagonal = isIndangerDiagonal(false,true, state);
			if(checkDangerDiagonal == 0)
    		{
    			break;
    		}
    		else if(checkDangerDiagonal == 1) 
    		{
    			return true;
    		}
		}
		tempx=x-1;tempy=y-1;
		while(tempx>=0&&tempy>=0)
		{
			checkDangerDiagonal = isIndangerDiagonal(false,false, state);
			if(checkDangerDiagonal == 0)
    		{
    			break;
    		}
    		else if(checkDangerDiagonal == 1) 
    		{
    			return true;
    		}
		}
		tempx=x+1;tempy=y+1;
		while(tempx<8&&tempy<8)
		{
			checkDangerDiagonal = isIndangerDiagonal(true,true, state);
			if(checkDangerDiagonal == 0)
    		{
    			break;
    		}
    		else if(checkDangerDiagonal == 1) 
    		{
    			return true;
    		}
		}
		
		//Checking for attack from the Knight of opposite color
		int posx[]={x+1,x+1,x+2,x+2,x-1,x-1,x-2,x-2};
		int posy[]={y-2,y+2,y-1,y+1,y-2,y+2,y-1,y+1};
		if (isIndangerFromOpposition(posx, posy, state , true))
		{
			return true;
		}
		
		
		//Checking for attack from the Pawn of opposite color
		int pox[]={x+1,x+1,x+1,x,x,x-1,x-1,x-1};
		int poy[]={y-1,y+1,y,y+1,y-1,y+1,y-1,y};
		if (isIndangerFromOpposition(pox, poy, state , false))
		{
			return true;
		}
		if(getcolor()==0)
		{
			if(x>0&&y>0&&state[x-1][y-1].getpiece()!=null&&state[x-1][y-1].getpiece().getcolor()==1&&(state[x-1][y-1].getpiece() instanceof Pawn))
				return true;
			if(x>0&&y<7&&state[x-1][y+1].getpiece()!=null&&state[x-1][y+1].getpiece().getcolor()==1&&(state[x-1][y+1].getpiece() instanceof Pawn))
				return true;
		}
		else
		{
			if(x<7&&y>0&&state[x+1][y-1].getpiece()!=null&&state[x+1][y-1].getpiece().getcolor()==0&&(state[x+1][y-1].getpiece() instanceof Pawn))
				return true;
			if(x<7&&y<7&&state[x+1][y+1].getpiece()!=null&&state[x+1][y+1].getpiece().getcolor()==0&&(state[x+1][y+1].getpiece() instanceof Pawn))
				return true;
		}
    	return false;
    }
}