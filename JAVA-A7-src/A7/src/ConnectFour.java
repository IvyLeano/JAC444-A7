import java.util.Scanner;

public class ConnectFour {
	String[][] m_connectFourGrid; 	//the main grid
	String m_gameStatus;			//holds "Game over string, or Player R won..."
	String m_currentPlayer;			//holds the current player, of the current play
	int m_column;					//column and row are used to access the index on the grid
	int m_row;		
	int m_turnCount;				//tracks the number of turns played

	//default constructor
	ConnectFour() {
		m_connectFourGrid = new String[6][7];
		m_gameStatus = "\0";
		m_currentPlayer = "\0";
		m_column = 0;
		m_row = 0;
		m_turnCount = 0;
	}

	// setter methods
	// sets an index on the grid equal to "Y" or "R"
	void setConnectFourGrid(int i, int j, String player) {
		m_connectFourGrid[i][j] = player;
	}

	// sets the games status after game ends to "Game Over"
	void setGameStatus(String currentStatus) {
		m_gameStatus = currentStatus;
	}
	//sets player based on turn, if turn is evenly divisible by 2 then current player is set to "Y"
	void setPlayer() {
		if (getTurnCount() % 2 == 0) {
			m_currentPlayer = "Y";
		} else {
			m_currentPlayer = "R";
		}
	}

	void setColumn(int col) {
		m_column = col;
	}

	void setRow(int row) {
		m_row = row;
	}
	//turn count is used to determine active player and ensure the play does not exceed max tries
	//which is 42 plays on a 6 by 7 grid
	void setTurnCount() {
		m_turnCount++;
	}

	// getter method
	String getConnectFourGrid(int i, int j) {
		return m_connectFourGrid[i][j];
	}

	String getGameStatus() {
		return m_gameStatus;
	}

	String getPlayer() {
		return m_currentPlayer;
	}

	int getColumn() {
		return m_column;
	}

	int getRow() {
		return m_row;
	}

	int getTurnCount() {
		return m_turnCount;
	}

	// methods
	// initializes m_connectFourGrid array as empty
	void setGame() {
		setTurnCount();
		setPlayer();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				setConnectFourGrid(i, j, " ");
			}
		}
	}

	// returns the grid with current plays
	void playGame() {
		char delimiter = '|';
		int displayWin = 0;
		while (getTurnCount() <= 42 && displayWin != 1) { // there are a maximum of 42 plays
			System.out.print("\n");
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					System.out.print(delimiter + getConnectFourGrid(i, j));
				}
				System.out.print(delimiter + "\n");
			}
			if (checkGameStatus()) {
				displayWin++; // allows the final results to show before terminating
			} 
			else {
				promptPlayerInput();
			}

		}
		System.out.printf(getGameStatus());
	}

	// prompts user for position play, stores row, col to object
	void promptPlayerInput() {
		Scanner in = new Scanner(System.in);
		System.out.print("Drop a " + getPlayer() + " disk at column(0-6): ");
		try {
			int col = in.nextInt();

			if (validateInput(col)) { //validates user input
				setColumn(col);
				if (getLowestRow(col) == 7) {
					System.out.print("The column you selected is full. Select another column.\n\n");
				} else {
					setConnectFourGrid(getLowestRow(col), getColumn(), getPlayer());
					setTurnCount();
					setPlayer();
				}
			}
		} catch (Exception e) {
			System.out.print("Invalid input. Please enter a value between 0 and 6 \n\n");
		}
	}
	//retrieves the lowest empty row for the selected column, to ensure that the "R" or "Y"
	//is places at the very bottom of the grid
	int getLowestRow(int col) {
		int column = 7;
		check: for (int i = 5; i >= 0; i--) {
			if (getConnectFourGrid(i, col) == " ") {
				column = i;
				break check;
			}
		}
		return column;
	}
	//validates user input
	boolean validateInput(int input) {
		boolean valid = true;

		if (input < 0 || input > 6) {
			System.out.print("Invalid input. Please enter a value between 0 and 6\n\n");
			valid = false;
		}

		return valid;
	}
	//the below booleans validate all matches - horizontal, vertical and diagonal connect fours
	boolean checkGameStatus() {
		boolean horizontalWin = horizontalWin("R") || horizontalWin("Y");
		boolean verticalWin = verticalWin("R") || verticalWin("Y");
		boolean diagonalWin = diagonalWinLeft("R") || diagonalWinLeft("Y") || diagonalWinRight("R")
				|| diagonalWinRight("Y");
		return verticalWin || horizontalWin || diagonalWin;
	}
	//goes across each row to look for connect fours
	boolean horizontalWin(String player) {
		boolean win = false;
		int count = 0;
		check: for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (count == 4) {
					win = true;
					setGameStatus("Game Over!");
					break check;
				} else if (getConnectFourGrid(i, j) == player) {
					count++;
				} else {
					count = 0;
				}
			}
		}
		return win;
	}
	//goes down each column to look for vertical fours
	boolean verticalWin(String player) {
		boolean win = false;
		int count = 0;
		check: for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (count == 4) {
					win = true;
					setGameStatus("Game Over!");
					break check;
				} else if (getConnectFourGrid(j, i) == player) {
					count++;
				} else {
					count = 0;
				}
			}
		}
		return win;
	}
	//goes across each row, diagonally to search for matches (for right diagonals)
	boolean diagonalWinRight(String player) {
		boolean win = false;
		int r = 0;
		int c = 0;
		int count = 0;

		check: for (int col = 0; col < 4; col++) {
			check2: for (int row = 3; row < 6; row++) {
				if (col == 0) {
					r = row;
					c = col;
				} else {
					r = 5;
					c = col;
				}
				while (!win) {
					try {
						if (count == 4) {
							win = true;
							setGameStatus("Game Over!");
							break check;
						} else if (getConnectFourGrid(r, c) == player) {
							count++;
						} else {
							count = 0;
						}
						r--;
						c++;
					} catch (Exception e) {
						count = 0;
						if (col == 0) {
							continue check2;
						} else {
							continue check;
						}
					}
				}
			}
		}
		return win;
	}
	//goes across each row, diagonally to search for matches (for left diagonals)
	boolean diagonalWinLeft(String player) {
		boolean win = false;
		int r = 0;
		int c = 0;
		int count = 0;

		check: for (int col = 6; col > 2; col--) {
			check2: for (int row = 3; row < 6; row++) {
				if (col == 6) {
					r = row;
					c = col;
				} else {
					r = 5;
					c = col;
				}
				while (!win) {
					try {
						if (count == 4) {
							win = true;
							setGameStatus("Game Over!");
							break check;
						} else if (getConnectFourGrid(r, c) == player) {
							count++;
						} else {
							count = 0;
						}
						r--;
						c--;
					} catch (Exception e) {
						count = 0;
						if (col == 6) {
							continue check2;
						} else {
							continue check;
						}
					}
				}
			}
		}
		return win;
	}
}
