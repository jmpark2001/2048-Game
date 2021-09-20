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
				board[x][y] = 0;
			}
		}
	}

	@Override
	public boolean addTile() {
		int count = 0;
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				if(board[x][y] == 0) {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reachedThreshold() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int left() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int right() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int down() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int up() {
		// TODO Auto-generated method stub
		return 0;
	}

}
