import javax.swing.JLabel;

public class Timer extends JLabel implements Runnable {
	final int MENU_STATE = 0;
	final int GAME_STATE = 1;
	double startTime = System.currentTimeMillis();
	int tileElipsed;
	Reversi reversi = new Reversi();
	public Timer() {
		
	}
	
	@Override
	public void run() {//runnable method what happens when the program is run
		while(reversi.isRunning()) {
			tileElipsed = (int)(startTime - System.currentTimeMillis());
			this.setText(Integer.toString(tileElipsed));
		}
	}
	
	public double getStartTime() {
		return startTime;
	}
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}
	
}
