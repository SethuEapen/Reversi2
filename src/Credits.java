import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Credits {
	JFrame creditsFrame = new JFrame("CREDITS!");
	JTextArea text = new JTextArea();
	public Credits() {
		creditsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		creditsFrame.setSize(500, 600);
		creditsFrame.setLocationRelativeTo(null);
		creditsFrame.setVisible(true);
	}
}
