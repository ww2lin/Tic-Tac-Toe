import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

public class Model extends Observable implements ComputerCallBack{	
	//IView view;
	private int boardSize = 3;
	private int winConstraint=3;
	private int gameboard[][] = new int[boardSize][boardSize];
	private int turn =0;
	private int row=0,col=0;
	private GameState state = new GameState();
	private Computer ai = new Computer (boardSize,this);
	private boolean computer = false;
	private int pcScore=0;
	public String getSymbolByTurn(){
			String sym = Util.Symbol[Util.turnIndex];
			Util.turnIndex = (Util.turnIndex % 2)+1;
			return sym;
	}
	
	//check if the move is valid
	//valid - place x/o
	//else update the invalid message
	public void actionPerformedAtIndex(int r, int c) {
		if (getState().isGameOver() || getState().getState() == GameState.SELECTING_PLAYER){
			return;
		}
		else if(gameboard[r][c] != 0) {
			updateGameState(GameState.INVALID_MOVE);
			return; //after returning the view wont get redrawn
		} 
		row = r;
		col = c;
		//1 maps to x
		//and 2 maps to O
		if (Util.turnIndex == 1) gameboard[row][col] = Util.X;
		else gameboard[row][col] = Util.O;
		checkGameProgress(r,c);
	} 	
	

	//if new game is pressed
	public void newGame(){
		turn = 0;
		for (int i=0; i< gameboard.length; ++i ){
			for (int j=0; j< gameboard.length; ++j ){
				gameboard[i][j] = GameState.EMPTY;
			}
		}
		//order matters here
		//since we do not want the restart state
		//over writing the selecting_player state
		updateGameState(GameState.RESTART);
		getState().restart();
	}
	
	//check if anyone wins
	public void checkGameProgress(int r,int c){
		++turn;
		updateGameState(GameState.VALID_MOVE);
		int gameState = isGameOver(r,c);
		if (gameState < 0) updateGameState(GameState.X_WINS);
		else if (gameState > 0) updateGameState(GameState.O_WINS);
		else if (turn == boardSize*boardSize) updateGameState(GameState.TIE);
		else if (computer && Util.turnIndex != Util.STARTING_PIECE && state.getWiningLine().size() < winConstraint) {
			Cell pc = ai.getNextMove(gameboard);
			actionPerformedAtIndex(pc.row,pc.col);
		}

	}
	
	//a generic notify Observers method
	//used for updating the view base on the game state
	public void updateGameState(int nextState){
		getState().setState(nextState);
		setChanged();
		notifyObservers();
	}
	
	//use to switch the board slider 
	public void boardSizeSlider(ChangeEvent e) {
		if (getState().getState() == GameState.SELECTING_PLAYER || getState().getState() == GameState.SELECTED_PLAYER){
			JSlider jboardSize = (JSlider) e.getSource();
			boardSize = jboardSize.getValue();
			gameboard = new int[boardSize][boardSize];
			int oldState = getState().getState();
			updateGameState(GameState.RESIZE_BOARD);
			updateGameState(oldState);
			ai.setSize(boardSize);
		}
	}
	
	//use to switch to number of piece to win
	public void BoardWiningSlider(ActionEvent e) {
		if (getState().getState() == GameState.SELECTING_PLAYER || getState().getState() == GameState.SELECTED_PLAYER){
			JComboBox jwinConstraint = (JComboBox ) e.getSource();
			winConstraint = Integer.parseInt((String)jwinConstraint.getSelectedItem());
		}
		
	}
	
	public void setAiMode(ActionEvent e) {
		if (getState().getState() == GameState.SELECTING_PLAYER || getState().getState() == GameState.SELECTED_PLAYER){
			computer = true;
			JComboBox selected = (JComboBox) e.getSource();
			String mode = (String)selected.getSelectedItem();
			if (mode.equalsIgnoreCase("Two Player")) computer = false;
			else ai.setLevel(mode);
			
		}
	}
	//the starting place to put on the board	
	public void setStartingX(){
		if (getState().getState() == GameState.SELECTING_PLAYER){
			Util.turnIndex = Util.X_INDEX;
			Util.STARTING_PIECE = Util.X_INDEX;
			updateGameState(GameState.SELECTED_PLAYER);
		}
	}
	public void setStartingY(){
		if (getState().getState() == GameState.SELECTING_PLAYER){
			Util.turnIndex = Util.O_INDEX;
			Util.STARTING_PIECE = Util.O_INDEX;
			updateGameState(GameState.SELECTED_PLAYER);
		}
	}
	
	//negative num x wins
	//positive num o wins
	//0 no one wins
	public int isGameOver(int r,int c){
		int horzSum=0;
		int vertSum=0;
		int diagonalSum=0;
		int rDiagonalSum =0;
		//pass points back if a player wins
		//and update the view based on these points
		ArrayList<Cell> horz = new ArrayList<Cell>();
		ArrayList<Cell> vert = new ArrayList<Cell>();
		ArrayList<Cell> diagonal = new ArrayList<Cell>();
		ArrayList<Cell> rDiagonal = new ArrayList<Cell>();
		
		//current piece on the board
		int p = gameboard[r][c]; 
		
		for (int i=0; i < boardSize; ++i){
			if (gameboard[r][i] == p){
				horzSum+=gameboard[r][i];
				horz.add(new Cell(r,i));	
			}
			else{
				horzSum = 0;
				horz.clear();
			}
			if (Math.abs(horzSum) >= winConstraint){
				break;
			}
		}
		
		for (int i=0; i < boardSize; ++i){
			if (gameboard[i][c] == p) {
				vertSum+=gameboard[i][c];
				vert.add(new Cell(i,c));
			}
			else {
				vertSum = 0;
				vert.clear();
			}
			if (Math.abs(vertSum) >= winConstraint){
				break;
			}
		}
		
		diagonalSum = p;
		rDiagonalSum = p;
		diagonal.add(new Cell(r,c));
		rDiagonal.add(new Cell(r,c));
		//diagonal
		for (int i = r+1, j= c+1; i < boardSize && j < boardSize; ++i,++j){
			if (gameboard[i][j] == p) {
				diagonalSum+=gameboard[i][j];
				diagonal.add(new Cell(i,j));
			}
			else {
				break;
			}
			if (Math.abs(diagonalSum) >= winConstraint){
				break;
			}
		}
		for (int i = r-1, j= c-1; i >= 0 && j >= 0; --i,--j){
			if (gameboard[i][j] == p) {
				diagonalSum+=gameboard[i][j];
				diagonal.add(new Cell(i,j));
			}
			else {
				break;
			}
			if (Math.abs(diagonalSum) >= winConstraint){
				break;
			}
		}
		//reverse diagonal
		for (int i = r+1, j= c-1; i < boardSize && j >=0; ++i,--j){
			if (gameboard[i][j] == p) {
				rDiagonalSum+=gameboard[i][j];
				rDiagonal.add(new Cell(i,j));
			}
			else {
				break;
			}
			if (Math.abs(rDiagonalSum) >= winConstraint){
				break;
			}
		}
		for (int i = r-1, j= c+1; i >= 0 && j < boardSize; --i,++j){
			if (gameboard[i][j] == p) {
				rDiagonalSum+=gameboard[i][j];
				rDiagonal.add(new Cell(i,j));
			}
			else {
				break;
			}
			if (Math.abs(rDiagonalSum) >= winConstraint){
				break;
			}
		}
		
		pcScore = Math.max(
				Math.max(Math.abs(horzSum), Math.abs(vertSum)),
				Math.max(Math.abs(diagonalSum), Math.abs(rDiagonalSum)));
		
		if (Math.abs(horzSum) >= winConstraint){
			getState().setWiningLine(horz);
			return horzSum;
		}
		else if (Math.abs(vertSum) >= winConstraint){
			getState().setWiningLine(vert);
			return vertSum;
		}
		else if (Math.abs(diagonalSum) >= winConstraint){
			getState().setWiningLine(diagonal);
			return diagonalSum;
		}
		else if (Math.abs(rDiagonalSum) >= winConstraint){
			getState().setWiningLine(rDiagonal);
			return rDiagonalSum;
		}
		return 0;
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public int getTurn(){
		return turn;
	}
	public GameState getState() {
		return state;
	}
	public int getBoardSize (){
		return boardSize;
	}

	public int getwinConstraint (){
		return winConstraint;
	}

	@Override
	public int getPoint(int r, int c) {
		isGameOver(r,c);
		state.resetLookAhead();
		return pcScore;
	}

	@Override
	public int getWinConstaint() {
		return winConstraint;
	}

	@Override
	public void finishLookAhead() {
		state.resetLookAhead();
	}
	

}
