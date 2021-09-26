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

	@Override
	public boolean canMerge() {
		int value = 0;
		int row = 0;
		int col = 0;
		int middle = 0;
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				if(getValue(x,y) != 0) {
					row = x;
					col = y;
					value = getValue(x,y);
					while(col < 3) {
						if(value == getValue(row, col+1)) {
							if(col - y == 0) {
								return true;
							}
							/*else {
								middle = col - y;
								while(middle != 0) {
									if(getValue(row, col - middle) == 0) {
										middle--;
									}
								}
							}*/
							col++;
						}
					}
					while(row < 3) {
						if(value == getValue(row+1, col)) {
							if(row - x == 0) {
								return true;
							}
							row++;
						}
					}
					
				}
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
		for(int x=3; x>0; x--) {
			for(int y=0; y<4; y++) {
				if(getValue(x, y) != 0) {
					value = getValue(x, y);
					if(getValue(x-1, y) == 0) {
						setValue(x, y, 0);
						setValue(x-1, y, value);
					}
					else {
						if(getValue(x, y) == getValue(x-1, y)) {
							setValue(x, y, 0);
							setValue(x-1, y, value + value);
							extraPoints += value + value;
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
		for(int x=0; x<4; x++) {
			for(int y=0; y<3; y++) {
				if(getValue(x, y) != 0) {
					value = getValue(x, y);
					if(getValue(x, y+1) == 0) {
						setValue(x, y, 0);
						setValue(x, y+1, value);
					}
					else {
						if(getValue(x, y+1) == getValue(x, y)) {
							setValue(x, y, 0);
							setValue(x, y+1, value + value);
							extraPoints += value + value;
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
		for(int x=0; x<4; x++) {
			for(int y=3; y>0; y--) {
				if(getValue(x, y) != 0) {
					value = getValue(x, y);
					if(getValue(x, y-1) == 0) {
						setValue(x, y, 0);
						setValue(x, y-1, value);
					}
					else {
						if(getValue(x, y-1) == getValue(x, y)) {
							setValue(x, y, 0);
							setValue(x, y-1, value + value);
							extraPoints += value + value;
						}
					}
				}
			}
		}
		return extraPoints;
	}

}
