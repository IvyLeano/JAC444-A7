import java.util.Scanner;
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//to do: uservalidation
//exit option??
public class ConnectFour {
	String[][] m_connectFourGrid;
	String m_gameStatus;
	String m_currentPlayer;
	int m_column;
	int m_row;
	int m_turnCount;

	ConnectFour() {
		m_connectFourGrid = new String[6][7];
		m_gameStatus = "\0";
		m_currentPlayer = "\0";
		m_column = 0;
		m_row = 0;
		m_turnCount = 0;
	}

	// setter method
	// sets an index on the grid equal to "Y" or "R"
	void setConnectFourGrid(int i, int j, String player) {
		m_connectFourGrid[i][j] = player;
	}

	// sets the games status after every turn to: "player 1 won", "player 2 won",
	// "play again"...
	void setGameStatus(String currentStatus) {
		m_gameStatus = currentStatus;
	}

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
	// initializes m_connectFourGrid array
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
		while (getTurnCount() < 42 && checkGameStatus() == false) { // there are a maximum of 42 plays, for a 6 by 7
																	// grid
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					System.out.print(delimiter + getConnectFourGrid(i, j));
				}
				System.out.print(delimiter + "\n");
			}
			promptPlayerInput();
			checkGameStatus();
			setTurnCount();
			setPlayer();
		}
	}

	// prompts user for position play, stores row, col to object
	void promptPlayerInput() {
		Scanner in = new Scanner(System.in);
		System.out.print("Drop a " + getPlayer() + " disk at column(0-6): ");
		int col = in.nextInt();
		setColumn(col);

		getLowestRow(col);
		if(getLowestRow(col) == 7) {System.out.print("Select another column.\n");}
		else {setConnectFourGrid(getLowestRow(col), col, getPlayer()); }
		
	}
 int getLowestRow(int col) {
 	int column = 7;
	check :  for (int i = 5; i > 0; i--) {
		 if(getConnectFourGrid(i, col) == " ") {
			 column = i;
			 break check;
		 }
		}
 	return column;
 }
 
	boolean checkGameStatus() {
		return horizontalWin("R");// && !verticalWin() && !diagonalWin();
	}
	boolean horizontalWin(String player) {
		int count = 0; // counts up to 4, before reset
	
		boolean win = false;
		check: for (int i = 5; i > 0; i--) { // starting at the bottom row
			for (int j = 6; j >= 0; j--) {
				if (count == 4 || count == 4) {
					win = true;
					break check;
				}
				else if (m_connectFourGrid[i][j] == player) {
					count++;
				}
				else {
					count = 0;
				}	
			}
			count = 0;
		}
		return win;
	}
}
