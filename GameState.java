import java.util.ArrayList;


public class GameState {
	public final static int EMPTY = 0;
	public final static int VALID_MOVE = 1;
	public final static int INVALID_MOVE = 2;
	
	public final static int SELECTING_PLAYER = 3;
	public final static int SELECTED_PLAYER = 4;
	
	public final static int TIE = 10;
	public final static int X_WINS = 11;
	public final static int O_WINS = 12;
	
	public final static int HORIZONTAL = 15;
	public final static int VERTICAL = 16;
	public final static int DIAGONAL = 17;
	public final static int REVERSE_DIAGONAL = 18;
	
	
	
	public final static int RESTART = 20;
	public final static int RESIZE_BOARD = 21;
	
	private int currentState = SELECTING_PLAYER;
	private ArrayList<Cell> winingLine = new ArrayList<Cell>();
	private boolean gameOver = false;
	public void restart(){
		currentState = SELECTING_PLAYER;
		winingLine.clear();
		gameOver = false;
	}
	
	public void setState(int state){
		currentState = state;
		if (currentState == TIE){
			gameOver = true;
		}
	}
	
	public int getState(){
		return currentState;
	}

	public void setWiningLine(ArrayList<Cell> winingLine) {
		//if we set the wining line then the game ends
		this.winingLine = winingLine;
		gameOver = true;
	}
	public void resetLookAhead(){
		gameOver = false;
		winingLine.clear();
	}
	public ArrayList<Cell> getWiningLine() {
		return winingLine;
	}
	public boolean isGameOver(){
		return gameOver;
	}
}
