/* This code allows the user to Play Reversi against either an AI or against another person. 
It only lets you play valid moves, and will keep track of the score and reset the board if a player has won 
Sethu Eapen, Ethan Wang
6/12/18 
Period 1
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Reversi implements ActionListener, MouseListener, Runnable {
	//Sets Basic Variables
	int clickWidth;
	int clickHeight;
	int state;
	int bscore;
	int wscore;
	double tileElipsed;
	final int x = 8;
	final int y = 8;
	final int MENU_STATE = 0;
	final int GAME_STATE = 1;
	final int EMPTY_TILE = 2;
	final int BLACK_PIECE = 0;
	final int WHITE_PIECE = 1;
	final int BLACK_TURN = 0;
	final int WHITE_TURN = 1;
	int turn = BLACK_TURN;
	double startTime = 0;
	final int NORTH = 0;
	final int WEST = 1;
	final int SOUTH = 2;
	final int EAST = 3;
	final int NORTH_WEST = 4;
	final int SOUTH_WEST = 5;
	final int SOUTH_EAST = 6;
	final int NORTH_EAST = 7;
	final int NULL_DIRECTION = 8;
	boolean finished = false;
	boolean finishable = false;
	boolean AIplayer;

	
	
	


	
	
	//Sets Parts of JFrame and Arrays Needed
	int[][] cells = new int[x][y];
	ArrayList<Integer> Xvalues = new ArrayList<Integer>();
	ArrayList<Integer> Yvalues = new ArrayList<Integer>();
	ArrayList<Integer> newXvalues = new ArrayList<Integer>();
	ArrayList<Integer> newYvalues = new ArrayList<Integer>();
	////Adds Values into JFrame
	JFrame frame = new JFrame("REVERSI!");
	ReversiPanel panel = new ReversiPanel(cells);
	MenuState Menu = new MenuState();
	Container Game = new Container();
	Container north = new Container();
	JButton back = new JButton("Back");
	JLabel time = new JLabel("Time");
	JTextField blackScore = new JTextField("Black Score: 0");
	JTextField whiteScore = new JTextField("White Score: 0");
	Dimension menuDimention = new Dimension(Menu.getWidth() + 16, Menu.getHeight() + 39);
	Dimension gameDimention = new Dimension(1000, 1000);
	Thread t = new Thread(this);
	
	public static void main(String[] args) {
		new Reversi();
	}	
		
	public Reversi() {
		//JFrame setup
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(menuDimention);
		//Game Container Setup
		Game.setLayout(new BorderLayout());	
		Game.add(panel, BorderLayout.CENTER);
		//North Container Setup
		north.setLayout(new GridLayout(1, 3));
		back.addActionListener(this);
		back.setBackground(Color.LIGHT_GRAY);
		north.add(back);
		north.add(blackScore);
		north.add(whiteScore);
		north.add(time);
		Game.add(north, BorderLayout.NORTH);
		//Panel Setup
		frame.add(Menu);
		state = MENU_STATE;
		Menu.addMouseListener(this);
		panel.addMouseListener(this);
		frame.setLocationRelativeTo(null);
		//set visible
		frame.setVisible(true);
		t.start();
	}
	

//Mouse Instances
	
	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent e) {
		// Home Menu, allows user to choose whether they want to play against AI or 2 person, lets them go to rules or credits. 
		clickWidth = e.getX();
		clickHeight = e.getY();
		if(state == MENU_STATE) {
			if(clickWidth <= 467 && clickWidth >= 159) {
				if(clickHeight <= 271 && clickHeight >= 219) {
					Object[] options = { "AI", "2nd PERSON" };//WHAT ARE THE OPTIONS FOR THE DIALOG BOX
					int option = JOptionPane.showOptionDialog(null, "DO YOU WANT TO PLAY AGAINST AN AI OR ANOTHER PERSON", "Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);//STORE VALUE IN INT OPTION		
					if(option == JOptionPane.YES_OPTION) {
						AIplayer = true;
					}
					else {
						AIplayer = false;
					}
					clearBoard();
					startTime = System.currentTimeMillis();
					frame.remove(Menu);
					frame.setSize(gameDimention);
					frame.add(Game);
					frame.setLocationRelativeTo(null);
					state = GAME_STATE;
				}
			}
			if(clickWidth <= 467 && clickWidth >= 159) {
				if(clickHeight <= 350 && clickHeight >= 298) {
					//Send to wikapidia
					Desktop d = Desktop.getDesktop();
					try {
						d.browse(new URI("https://en.wikipedia.org/wiki/Reversi"));
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
			if(clickWidth <= 467 && clickWidth >= 159) {
				if(clickHeight <= 430 && clickHeight >= 377) {
					new Credits();//How can I make the credits page close without closing the whole thing?
				}
			}
		}
		// Sets gameboard squares and allows black to make a move. Flips neccesary pieces and repaints the board. Checks to see if 
		// Game has ended, if it has, clears board. If there are no valid black moves, white goes again. 
		else if(state == GAME_STATE) {
			int x = clickWidth / (panel.getWidth() / cells[0].length);
			int y = clickHeight / (panel.getHeight() / cells.length);
			if(cells[x][y] == EMPTY_TILE) {
				if(turn == BLACK_TURN) {
					if(checkMove(BLACK_TURN, NULL_DIRECTION, x, y)) {
						if(clickHeight > back.getHeight()) {
							cells[x][y] = BLACK_PIECE;
							boardUpdate(BLACK_TURN, NULL_DIRECTION, x, y);
							flipPieces(BLACK_TURN);
							frame.repaint();
							checkScore();
							blackScore.setBackground(Color.WHITE);
							whiteScore.setBackground(Color.GREEN);
							if (checkGameEnd() == true) {
							    checkWin();
								clearBoard();
							}
							else {
								if(AIplayer == false) {
									if(checkWhiteMoves()) {
										turn = WHITE_TURN;
									}
									else {
										JOptionPane.showMessageDialog(frame, "NO VALID BLACK MOVES. WHITE GOES AGAIN!");
									}								}
								else {
									finishable = false;
									finished = false;
									AiMove();
									checkScore();
									if (checkGameEnd() == true) {
									    checkWin();
										clearBoard();
									}
								}
						
							}
						
						}
					}
					finishable = false;
					finished = false;
				}
				// Allows white to make a move. Flips neccesary pieces and repaints the board. Checks to see if 
				// Game has ended, if it has, clears board. If there are no valid white moves, black goes again. 
					else if(turn == WHITE_TURN) {
					if(checkMove(WHITE_TURN, NULL_DIRECTION, x, y)) {
						if(clickHeight > back.getHeight()) {
							cells[x][y] = WHITE_PIECE;
							boardUpdate(WHITE_TURN, NULL_DIRECTION, x, y);
							flipPieces(WHITE_TURN);
							frame.repaint();
							checkScore();
							if (checkGameEnd() == true) {
								checkWin();
								clearBoard();
							}
							else {
								if(checkBlackMoves()) {
									turn = BLACK_TURN;
								}
								else {
									JOptionPane.showMessageDialog(frame, "NO VALID BLACK MOVES. WHITE GOES AGAIN!");
								}
								blackScore.setBackground(Color.GREEN);
								whiteScore.setBackground(Color.WHITE);
							}
					
						}
					}
					finishable = false;
					finished = false;
				}
			}
		}
	}

//Buttons Pressed
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource().equals(back)) {//IF THE CPU BUTTON IS PRESSED
			 Object[] options = { "OK", "CANCEL" };//WHAT ARE THE OPTIONS FOR THE DIALOG BOX
			 int option = JOptionPane.showOptionDialog(null, "By pressing OK you will lose all your progress\n"
			 		+ "Click OK to continue", "Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);//STORE VALUE IN INT OPTION		
			 if(option == JOptionPane.YES_OPTION) {//WAS YES SELECTED?
				 frame.remove(Game);
				 frame.setSize(menuDimention);
				 frame.add(Menu);
				 frame.setLocationRelativeTo(null);
				 state = MENU_STATE;

			 }
		 }
	}
	
	@Override
	// Updates run time
	public void run() {
		startTime = System.currentTimeMillis();
		while(true) {
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tileElipsed = Math.round((System.currentTimeMillis() - startTime) / 10) / 100.0;
			time.setText("Time: " + tileElipsed);
			frame.repaint();
		}
	}
	// Checks to see who has won, pops up a pane saying who has won. 
	public void checkWin() {
		if (bscore > wscore) {
			JOptionPane.showMessageDialog(frame, "Black Wins!");
			startTime = System.currentTimeMillis();
		}
		if (bscore < wscore) {
			JOptionPane.showMessageDialog(frame, "White Wins!");
			startTime = System.currentTimeMillis();
		}
		if (bscore == wscore) {
			JOptionPane.showMessageDialog(frame, "Tie Game!");
			startTime = System.currentTimeMillis();
		}
	}
	// Updates the score everytime somebody makes a move. 
	public void checkScore() {
		bscore = 0;
		wscore = 0;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j] == WHITE_PIECE) {
					wscore++;
					whiteScore.setText("White Score: " + wscore);
				     
				}
				if (cells[i][j] == BLACK_PIECE) {
					bscore++;
					blackScore.setText("Black Score: " + bscore);
				}
			}
		}
	}
	// Resets Board to Original state. 
	public void clearBoard() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				cells[i][j] = EMPTY_TILE;
			}
		}
		cells[3][3] = WHITE_PIECE;
		cells[4][4] = WHITE_PIECE;	
		cells[3][4] = BLACK_PIECE;		
		cells[4][3] = BLACK_PIECE;
		bscore = 2;
		wscore = 2;		
		whiteScore.setText("White Score: " + wscore);
		blackScore.setText("Black Score: " + bscore);
		turn = BLACK_TURN;
		blackScore.setBackground(Color.GREEN);
		whiteScore.setBackground(Color.WHITE);

	}
	
	// Playing against AI, this determines where AI will move. 
	public void AiMove() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j] == EMPTY_TILE) {	
					if(checkMove(WHITE_TURN, NULL_DIRECTION, i, j)) {			
						cells[i][j] = WHITE_PIECE;
						boardUpdate(WHITE_TURN, NULL_DIRECTION, i, j);
						flipPieces(WHITE_TURN);
						blackScore.setBackground(Color.GREEN);
						whiteScore.setBackground(Color.WHITE);
						turn = BLACK_TURN;
						return;
					}
				}
			}
		}
		turn = BLACK_TURN;
	}
	// Checks if the Game has ended. 	
	public boolean checkGameEnd() {
		if(checkBlackMoves() && checkWhiteMoves()) {
			return false;
		}
		return true;
	}
	// Checks if Black has any valid moves to play. 
	public boolean checkBlackMoves() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j] == EMPTY_TILE) {	
					if(checkMove(BLACK_TURN, NULL_DIRECTION, i, j)) {			
						return true;
					}
				}
			}
		}

		return false;
	}
	// Checks if White has any valid moves to play. 
	public boolean checkWhiteMoves() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j] == EMPTY_TILE) {	
					if(checkMove(WHITE_TURN, NULL_DIRECTION, i, j)) {			
						return true;
					}
				}
			}
		}		

		return false;
	}
	Checks if a move that the user wants to play is valid. 
	public boolean checkMove(int player, int direction, int x, int y) {
		int Xnew;
		int Ynew;
		if(direction == NULL_DIRECTION) {
			for (int i = 0; i < 8; i++) {
				Xnew = newX(x, i);
				Ynew = newY(y, i);
				if(Xnew >= 0 && Ynew >= 0 && Xnew <= 7 && Ynew <= 7) {
					checkMove(player, i, Xnew, Ynew);
					finishable = false;
				}
			}
		}
		else {
			if(cells[x][y] == player) {
				if(finishable) {
					finished = true;
				}
			}
			else if(cells[x][y] != player && cells[x][y] != EMPTY_TILE) {
				Xnew = newX(x, direction);
				Ynew = newY(y, direction);
				if(Xnew >= 0 && Ynew >= 0 && Xnew <= 7 && Ynew <= 7) {
					finishable = true;
					checkMove(player, direction, Xnew, Ynew);
				}
			}
		}
		
		return finished;
	}
	// Stores an array of all X, Y values that need to be flipped. 
	public void boardUpdate(int player, int direction, int x, int y) {
		int Xnew;
		int Ynew;
		if(direction == NULL_DIRECTION) {
			for (int i = 0; i < 8; i++) {
				newXvalues.clear();
				newYvalues.clear();
				Xnew = newX(x, i);
				Ynew = newY(y, i);
				if(Xnew >= 0 && Ynew >= 0 && Xnew <= 7 && Ynew <= 7) {
					newXvalues.add(Xnew);
					newYvalues.add(Ynew);
					boardUpdate(player, i, Xnew, Ynew);
					finishable = false;
				}
			}
		}
		else {
			if(cells[x][y] == player) {
				if(finishable) {
					Xvalues.addAll(newXvalues);
					Yvalues.addAll(newYvalues);
		
				}
			}
			else if(cells[x][y] != player && cells[x][y] != EMPTY_TILE) {
				Xnew = newX(x, direction);
				Ynew = newY(y, direction);
				if(Xnew >= 0 && Ynew >= 0 && Xnew <= 7 && Ynew <= 7) {
					finishable = true;
					newXvalues.add(Xnew);
					newYvalues.add(Ynew);
					boardUpdate(player, direction, Xnew, Ynew);
				}
			}
		}
	}

	public void flipPieces(int player) {
		for (int i = 0; i < Xvalues.size(); i++) {
			cells[Xvalues.get(i)][Yvalues.get(i)] = player;
		}
		Xvalues.clear();
		Yvalues.clear();
	}
	
	@SuppressWarnings("null")
	// Stores values of X and Directions
	public int newX(int x, int direction) {
		if(direction == NORTH) {
			return x;
		}
		if(direction == WEST) {
			return x - 1;
		}
		if(direction == SOUTH) {
			return x;
		}
		if(direction == EAST) {
			return x + 1;
		}
		if(direction == NORTH_WEST) {
			return x - 1;
		}
		if(direction == SOUTH_WEST) {
			return x - 1;
		}
		if(direction == SOUTH_EAST) {
			return x + 1;
		}
		if(direction == NORTH_EAST) {
			return x + 1;
		}
		return (Integer) null;
	}
	// Getters and Setters, Storing Data of Y values and directions. 
	@SuppressWarnings("null")
	public int newY(int y, int direction) {
		if(direction == NORTH) {
			return y - 1;
		}
		if(direction == WEST) {
			return y;
		}
		if(direction == SOUTH) {
			return y + 1;
		}
		if(direction == EAST) {
			return y;
		}
		if(direction == NORTH_WEST) {
			return y - 1;
		}
		if(direction == SOUTH_WEST) {
			return y + 1;
		}
		if(direction == SOUTH_EAST) {
			return y + 1;
		}
		if(direction == NORTH_EAST) {
			return y - 1;
		}
		return (Integer) null;		
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
