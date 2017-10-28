package pieces;

import java.util.List;

import chess.Cell;

public class King extends Piece {

	private int horizontalPos;
	private int verticalPos;
	private int tempHorizontalPos;
	private int tempVerticalPos;

	public King(String i, String p, int c, int x, int y) {
		setx(x);
		sety(y);
		setId(i);
		setPath(p);
		setColor(c);
	}

	public void setx(int x) {
		this.horizontalPos = x;
	}

	public void sety(int y) {
		this.verticalPos = y;
	}

	public int getx() {
		return horizontalPos;
	}

	public int gety() {
		return verticalPos;
	}

	public List<Cell> getPossibleMoves(Cell[][] state, int x, int y) {
		// King can move only one step. So all the adjacent 8 cells have been
		// considered.
		possibleMoves.clear();
		int[] posx = { x, x, x + 1, x + 1, x + 1, x - 1, x - 1, x - 1 };
		int[] posy = { y - 1, y + 1, y - 1, y, y + 1, y - 1, y, y + 1 };
		for (int i = 0; i < 8; i++) {
			boolean horizontalMoveAvailable = (posx[i] >= 0) && (posx[i] < 8);
			boolean verticalMoveAvailable = (posy[i] >= 0) && (posy[i] < 8);

			if (horizontalMoveAvailable && verticalMoveAvailable && chechPossibleMoves(i, state, posx, posy)) {
				possibleMoves.add(state[posx[i]][posy[i]]);
			}
		}
		return possibleMoves;
	}

	public boolean chechPossibleMoves(int i, Cell[][] state, int[] posx, int[] posy) {
		return (state[posx[i]][posy[i]].getpiece() == null
				|| state[posx[i]][posy[i]].getpiece().getcolor() != this.getcolor());
	}

	public int isIndangerXandY(int x, int y, Cell[][] state) {
		if (state[x][y].getpiece() == null)
			return 2;
		else if (state[x][y].getpiece().getcolor() == this.getcolor())
			return 0;
		else {
			if ((state[x][y].getpiece() instanceof Rook) || (state[x][y].getpiece() instanceof Queen))
				return 1;
			else
				return 0;
		}
	}

	public int isIndangerDiagonal(Cell[][] state) {

		if (state[tempHorizontalPos][tempVerticalPos].getpiece().getcolor() == this.getcolor())
			return 0;
		else {
			if (state[tempHorizontalPos][tempVerticalPos].getpiece() instanceof Bishop
					|| state[tempHorizontalPos][tempVerticalPos].getpiece() instanceof Queen)
				return 1;
			else
				return 0;
		}

	}

	public boolean isIndangerFromOpposition(int[] posx, int[] posy, Cell[][] state, boolean king) {

		boolean danger = false;
		for (int i = 0; i < 8; i++) {
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8)
					&& checkDangerFromOpposition(posx, posy, state, i)) {
				if (king && (state[posx[i]][posy[i]].getpiece() instanceof Knight)) {
					danger = true;
				}
				if (!king && (state[posx[i]][posy[i]].getpiece() instanceof King)) {
					danger = true;
				}

			}
		}
		return danger;
	}

	public boolean checkDangerFromOpposition(int[] posx, int[] posy, Cell[][] state, int i) {
		return (state[posx[i]][posy[i]].getpiece() != null
				&& state[posx[i]][posy[i]].getpiece().getcolor() != this.getcolor());
	}

	public boolean checkColor(Cell[][] state, int xx, int yy, int color) {

		return state[xx][yy].getpiece() != null && state[xx][yy].getpiece().getcolor() == color
				&& (state[xx][yy].getpiece() instanceof Pawn);

	}

	public boolean moveHorizontalDown(Cell[][] state) {
		int checkDangerXandY;
		for (int i = horizontalPos + 1; i < 8; i++) {
			checkDangerXandY = isIndangerXandY(i, verticalPos, state);
			if (checkDangerXandY == 0) {
				return false;
			} else if (checkDangerXandY == 1) {
				return true;
			} else if (checkDangerXandY == 2) {
				continue;

			}
		}
		return false;
	}

	public boolean moveHorizontalUp(Cell[][] state) {
		int checkDangerXandY;
		for (int i = horizontalPos - 1; i >= 0; i--) {
			checkDangerXandY = isIndangerXandY(i, verticalPos, state);
			if (checkDangerXandY == 0) {
				return false;
			} else if (checkDangerXandY == 1) {
				return true;
			} else if (checkDangerXandY == 2) {
				continue;

			}
		}
		return false;
	}

	public boolean moveVerticalDown(Cell[][] state) {
		int checkDangerXandY;
		for (int i = verticalPos + 1; i < 8; i++) {
			checkDangerXandY = isIndangerXandY(horizontalPos, i, state);
			if (checkDangerXandY == 0) {
				return false;
			} else if (checkDangerXandY == 1) {
				return true;
			} else if (checkDangerXandY == 2) {
				continue;

			}
		}
		return false;
	}

	public boolean moveVerticalUp(Cell[][] state) {
		int checkDangerXandY;
		for (int i = verticalPos - 1; i >= 0; i--) {
			checkDangerXandY = isIndangerXandY(horizontalPos, i, state);
			if (checkDangerXandY == 0) {
				return false;
			} else if (checkDangerXandY == 1) {
				return true;
			} else if (checkDangerXandY == 2) {
				continue;

			}
		}
		return false;
	}

	public boolean moveDiagonalLeftDown(Cell[][] state) {
		int checkDangerDiagonal;
		tempHorizontalPos = horizontalPos + 1;
		tempVerticalPos = verticalPos - 1;
		while (tempHorizontalPos < 8 && tempVerticalPos >= 0) {
			if (state[tempHorizontalPos][tempVerticalPos].getpiece() == null) {
				tempHorizontalPos++;
				tempVerticalPos--;
				checkDangerDiagonal = 9;
			} else {
				checkDangerDiagonal = isIndangerDiagonal(state);
			}
			if (checkDangerDiagonal == 0) {
				return false;
			} else if (checkDangerDiagonal == 1) {
				return true;
			}
		}
		return false;
	}

	public boolean moveDiagonalRightUp(Cell[][] state) {
		int checkDangerDiagonal;
		tempHorizontalPos = horizontalPos - 1;
		tempVerticalPos = verticalPos + 1;
		while (tempHorizontalPos >= 0 && tempVerticalPos < 8) {
			if (state[tempHorizontalPos][tempVerticalPos].getpiece() == null) {
				tempHorizontalPos--;
				tempVerticalPos++;
				checkDangerDiagonal = 9;
			} else {
				checkDangerDiagonal = isIndangerDiagonal(state);
			}
			if (checkDangerDiagonal == 0) {
				return false;
			} else if (checkDangerDiagonal == 1) {
				return true;
			}
		}
		return false;
	}

	public boolean moveDiagonalLefUp(Cell[][] state) {
		int checkDangerDiagonal;
		tempHorizontalPos = horizontalPos - 1;
		tempVerticalPos = verticalPos - 1;
		while (tempHorizontalPos >= 0 && tempVerticalPos >= 0) {
			if (state[tempHorizontalPos][tempVerticalPos].getpiece() == null) {
				tempHorizontalPos--;
				tempVerticalPos--;
				checkDangerDiagonal = 9;
			} else {
				checkDangerDiagonal = isIndangerDiagonal(state);
			}
			if (checkDangerDiagonal == 0) {
				break;
			} else if (checkDangerDiagonal == 1) {
				return true;
			}
		}
		return false;
	}

	public boolean moveDiagonalRightDown(Cell[][] state) {
		int checkDangerDiagonal;
		tempHorizontalPos = horizontalPos + 1;
		tempVerticalPos = verticalPos + 1;
		while (tempHorizontalPos < 8 && tempVerticalPos < 8) {
			if (state[tempHorizontalPos][tempVerticalPos].getpiece() == null) {
				tempHorizontalPos++;
				tempVerticalPos++;

				checkDangerDiagonal = 9;
			} else {
				checkDangerDiagonal = isIndangerDiagonal(state);
			}
			if (checkDangerDiagonal == 0) {
				break;
			} else if (checkDangerDiagonal == 1) {
				return true;
			}
		}
		return false;
	}

	public boolean isInDangerFromEnemyKnight(Cell[][] state) {
		int[] posx = { horizontalPos + 1, horizontalPos + 1, horizontalPos + 2, horizontalPos + 2, horizontalPos - 1,
				horizontalPos - 1, horizontalPos - 2, horizontalPos - 2 };
		int[] posy = { verticalPos - 2, verticalPos + 2, verticalPos - 1, verticalPos + 1, verticalPos - 2,
				verticalPos + 2, verticalPos - 1, verticalPos + 1 };

		return isIndangerFromOpposition(posx, posy, state, true);
	}

	public boolean isInDangerFromEnemyPawn(Cell[][] state) {
		int[] pox = { horizontalPos + 1, horizontalPos + 1, horizontalPos + 1, horizontalPos, horizontalPos,
				horizontalPos - 1, horizontalPos - 1, horizontalPos - 1 };
		int[] poy = { verticalPos - 1, verticalPos + 1, verticalPos, verticalPos + 1, verticalPos - 1, verticalPos + 1,
				verticalPos - 1, verticalPos };

		return isIndangerFromOpposition(pox, poy, state, false);
	}

	public boolean getColorFromHorizontalLeft(Cell[][] state) {
		if (horizontalPos > 0 && verticalPos > 0 && checkColor(state, horizontalPos - 1, verticalPos - 1, 1))
			return true;

		return (horizontalPos > 0 && verticalPos < 7 && checkColor(state, horizontalPos - 1, verticalPos + 1, 1));

	}

	public boolean getColorFromHorizontalRight(Cell[][] state) {
		if (horizontalPos < 7 && verticalPos > 0 && checkColor(state, horizontalPos + 1, verticalPos - 1, 0))
			return true;

		return (horizontalPos < 7 && verticalPos < 7 && checkColor(state, horizontalPos + 1, verticalPos + 1, 0));
	}

	public boolean isInDangerFromHorizontalandVertical(Cell[][] state) {
		if (moveHorizontalDown(state)) {
			return true;
		}
		if (moveHorizontalUp(state)) {
			return true;
		}
		if (moveVerticalDown(state)) {
			return true;
		}

		return moveVerticalUp(state);
	}

	public boolean isInDangerFromDiagonally(Cell[][] state) {
		if (moveDiagonalLeftDown(state)) {
			return true;
		}
		if (moveDiagonalRightUp(state)) {
			return true;
		}

		if (moveDiagonalLefUp(state)) {
			return true;
		}

		return moveDiagonalRightDown(state);

	}

	public boolean isindanger(Cell[][] state) {

		if (isInDangerFromHorizontalandVertical(state)) {
			return true;
		}

		if (isInDangerFromDiagonally(state)) {
			return true;
		}

		if (isInDangerFromEnemyKnight(state)) {
			return true;
		}
		if (isInDangerFromEnemyPawn(state)) {
			return true;
		}

		if (getcolor() == 0) {

			if (getColorFromHorizontalLeft(state)) {
				return true;
			}
		} else {

			if (getColorFromHorizontalRight(state)) {
				return true;
			}

		}
		return false;
	}
}