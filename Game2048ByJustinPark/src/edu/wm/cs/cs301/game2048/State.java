package edu.wm.cs.cs301.game2048;
import java.util.Random;

public class State implements GameState {
	
	int[][] board = new int[4][4];
	
	public State() {
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				setValue(x, y, 0);
			}
		}
	}
	
	public State(GameState original) {
		if(original == null) {
			for(int x=0; x<4; x++) {
				for(int y=0; y<4; y++) {
					setValue(x, y, 0);
				}
			}
		}
		else {
			for(int x=0; x<4; x++) {
				for(int y=0; y<4; y++) {
					setValue(x, y, original.getValue(x, y));
				}
			}
		}
	}

	@Override
	public int getValue(int xCoordinate, int yCoordinate) {
		return board[xCoordinate][yCoordinate];
	}

	@Override
	public void setValue(int xCoordinate, int yCoordinate, int value) {
		board[xCoordinate][yCoordinate] = value;
	}

	@Override
	public void setEmptyBoard() {
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				setValue(x, y, 0);
			}
		}
	}

	@Override
	public boolean addTile() {
		int count = 0;
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				if(getValue(x, y) == 0) {
					count++;
				}
			}
		}
		if(count == 0) {
			return false;
		}
		Random rand = new Random();
		int index = rand.nextInt(count);
		int tileNum = rand.nextInt(2);
		if(tileNum == 0) {
			tileNum = 2;
		}
		else {
			tileNum = 4;
		}
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				if(board[x][y] == 0) {
					if(index == 0) {
						board[x][y] = tileNum;
						index--;
					}
					else {
						index--;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean isFull() {
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				if(board[x][y] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean mergeLeft(int xCoor, int yCoor) {
		int value = getValue(xCoor, yCoor);
		while(xCoor != 0) {
			if(getValue(xCoor - 1, yCoor) == value) {
				return true;
			}
			else if(getValue(xCoor - 1, yCoor) != 0){
				return false;
			}
			xCoor--;
		}
		return false;
	}
	
	public boolean mergeRight(int xCoor, int yCoor) {
		int value = getValue(xCoor, yCoor);
		while(xCoor != 3) {
			if(getValue(xCoor + 1, yCoor) == value) {
				return true;
			}
			else if(getValue(xCoor + 1, yCoor) != 0) {
				return false;
			}
			xCoor++;
		}
		return false;
	}
	
	public boolean mergeUp(int xCoor, int yCoor) {
		int value = getValue(xCoor, yCoor);
		while(yCoor != 0) {
			if(getValue(xCoor, yCoor - 1) == value) {
				return true;
			}
			else if(getValue(xCoor, yCoor - 1) != 0) {
				return false;
			}
			yCoor--;
		}
		return false;
	}
	
	public boolean mergeDown(int xCoor, int yCoor) {
		int value = getValue(xCoor, yCoor);
		while(yCoor != 3) {
			if(getValue(xCoor, yCoor + 1) == value) {
				return true;
			}
			else if(getValue(xCoor, yCoor + 1) != 0) {
				return false;
			}
			yCoor++;
		}
		return true;
	}
	
	@Override
	public boolean canMerge() {
		int value = 0;
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				
			}
		}
		return false;
	}

	@Override
	public boolean reachedThreshold() {
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				if(getValue(x,y) >= 2048) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int left() {
		int extraPoints = 0;
		int value = 0;
		for(int col=3; col>=0; col--) {
			int mover = 0;
			for(int row=0; row<=3; row++) {
				if(getValue(row, col) == 0) {
					mover += 1;
				}
				else {
					value = getValue(row, col);
					setValue(row, col, 0);
					setValue(row - mover, col, value);
					if(row - mover > 0) {
						if(getValue(row - mover, col) == getValue(row - mover - 1, col)) {
							setValue(row - mover, col, 0);
							setValue(row - mover - 1, col, value + value);
							extraPoints += value + value;
							mover += 1;
						}
					}
				}
			}
		}
		return extraPoints;
	}

	@Override
	public int right() {
		int extraPoints = 0;
		int value = 0;
		for(int col=3; col>=0; col--) {
			int mover = 0;
			for(int row=3; row>=0; row--) {
				if(getValue(row, col) == 0) {
					mover += 1;
				}
				else {
					value = getValue(row, col);
					setValue(row, col, 0);
					setValue(row + mover, col, value);
					if(row + mover < 3) {
						if(getValue(row + mover, col) == getValue(row + mover + 1, col)) {
							setValue(row + mover, col, 0);
							setValue(row + mover + 1, col, value + value);
							extraPoints += value + value;
							mover += 1;
						}
					}
				}
			}
		}
		return extraPoints;
	}

	@Override
	public int down() {
		int extraPoints = 0;
		int value = 0;
		for(int row=3; row>=0; row--) {
			int mover = 0;
			for(int col=3; col>=0; col--) {
				if(getValue(row, col) == 0) {
					mover += 1;
				}
				else {
					value = getValue(row, col);
					setValue(row, col, 0);
					setValue(row, col + mover, value);
					if(col + mover < 3) {
						if(getValue(row, col + mover) == getValue(row, col + mover + 1)) {
							setValue(row, col + mover, 0);
							setValue(row, col + mover + 1, value + value);
							extraPoints += value + value;
							mover += 1;
						}
					}
				}
			}
		}
		return extraPoints;
	}

	@Override
	public int up() {
		int extraPoints = 0;
		int value = 0;
		for(int row=0; row<=3; row++) {
			int mover = 0;
			for(int col=0; col<=3; col++) {
				if(getValue(row, col) == 0) {
					mover += 1;
				}
				else {
					value = getValue(row, col);
					setValue(row, col, 0);
					setValue(row, col - mover, value);
					if(col - mover > 0) {
						if(getValue(row, col - mover) == getValue(row, col - mover - 1)) {
							setValue(row, col - mover, 0);
							setValue(row, col - mover - 1, value + value);
							extraPoints += value + value;
							mover += 1;
						}
					}
				}
			}
		}
		return extraPoints;
	}

}
