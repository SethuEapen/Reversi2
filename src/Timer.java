import javax.swing.JLabel;

public class Timer extends JLabel implements Runnable {
	final int MENU_STATE = 0;
	final int GAME_STATE = 1;
	double startTime = System.currentTimeMillis();
	int tileElipsed;
	Reversi reversi = new Reversi();
	public Timer() {
		
	}
	
	public double getStartTime() {
		return startTime;
	}
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}