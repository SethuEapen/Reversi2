import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
// Credits Page
public class Credits {
	JFrame creditsFrame = new JFrame("CREDITS!");
	Container north = new Container();
	JTextField text = new JTextField("Picture: https://en.wikipedia.org/wiki/Reversi#/media/File:Othello_(Reversi)_board.jpg");
	
	public Credits() {
		creditsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		creditsFrame.setSize(500, 600);
		north.setLayout(new GridLayout(1, 1));
		north.add(text);
		creditsFrame.add(north);
		creditsFrame.setLocationRelativeTo(null);
		creditsFrame.setVisible(true);
		
	}
}
