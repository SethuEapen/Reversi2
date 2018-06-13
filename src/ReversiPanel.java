import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
// Draws the boards and pieces.
@SuppressWarnings("serial")
public class ReversiPanel extends JPanel {
	int[][] cells;
	double width;
	double height;
	final int EMPTY_TILE = 2;
	final int BLACK_PEICE = 0;
	final int WHITE_PEICE = 1;	
	Color boardGreen = new Color(0, 50, 0);
	
	public ReversiPanel(int[][] cells) {
		this.cells = cells;	
	}
	// Draws board and white & black pieces. 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		width = this.getWidth() / cells[0].length;
		height = this.getHeight() / cells.length;
		g.setColor(boardGreen);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[0].length; column++) {
				if(cells[row][column] == BLACK_PEICE) {
					g.setColor(Color.BLACK);
					g.fillOval((int)Math.round(row * width), (int)Math.round(column * height), (int)(width), (int)(height));
				}
				if(cells[row][column] == WHITE_PEICE) {
					g.setColor(Color.WHITE);
					g.fillOval((int)Math.round(row * width), (int)Math.round(column * height), (int)(width), (int)(height));
				}
			}
		}
		
		//draw the grid
		g.setColor(Color.BLACK);
		
		for (int x = 0; x < cells[0].length + 1; x++) {
			g.drawLine((int)Math.round(x*width), 0, (int)Math.round(x*width), this.getHeight());
		}
		for (int y = 0; y < cells.length + 1; y++) {
			g.drawLine(0,(int)Math.round(y*height),this.getWidth(),(int)Math.round(y*height));
		}
	}

	
	
	

}