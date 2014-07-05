import java.util.ArrayList;
import java.util.Random;


public class Computer {
	
	private int size = 0;
	public static final int RANDOM  = 0;
	public static final int GREEDY = 1;
	private Random randomGenerator = new Random();
	private ComputerCallBack model= null;
	private int level = 0;
	Computer (int size, ComputerCallBack model ){
		this.size = size;
		this.model = model;
	}
	
	public void setLevel(String level) {
		if (level.equalsIgnoreCase("Random")) this.level = RANDOM;
		else if (level.equalsIgnoreCase("Greedy")) this.level = GREEDY;
	}

	public Cell getNextMove(int board[][]){
		switch(level){
		case GREEDY: return greedy(board);
		default: return random(board);
		}
	}
	
	public Cell greedy(int board[][]){
		ArrayList<Cell> possibleMoves = new ArrayList<Cell>();
		int points =0;
		Cell shouldBlock = null;//if we can win then dont block
		int currentPoint = 0;
		int user = 0;
		int pc = 0 ;
		if ( Util.STARTING_PIECE  == Util.X_INDEX){
			user = Util.X;
			pc = Util.O;
		}else {
			user = Util.O;
			pc = Util.X;
		}
		for (int i = 0; i < board.length; ++i){
			for (int j = 0; j < board.length; ++j){
				if (board[i][j] != 0 )continue;
				board[i][j] = pc;// Util.O;
				//place on a board that gives the max points
				currentPoint = model.getPoint(i,j); 
				//found a move that gives more points
				if (currentPoint > points ){
					if (currentPoint >= model.getWinConstaint()){
						board[i][j] = Util.EMPTY_PIECE;
						model.finishLookAhead();
						return new Cell(i,j);
					}
					possibleMoves.clear();
					points = currentPoint;
					possibleMoves.add(new Cell(i,j));
				}
				//if we get a same point just add it
				else if (currentPoint == points){
					possibleMoves.add(new Cell(i,j));
				}
				
				//check if use is able to win, if yes then block
				board[i][j] = user;//Util.X;
				currentPoint = model.getPoint(i,j); 
				//x piece is negative
				if (currentPoint >= model.getWinConstaint()){
					//block the user
					model.finishLookAhead();
					shouldBlock  = new Cell(i,j);
				}
				board[i][j] = Util.EMPTY_PIECE;
				
			}
		}
		
		if (possibleMoves.size() == 0){
			System.out.println("something is wrong with the game aborting!");
			System.exit(1);
		}
		if (shouldBlock != null){
			return shouldBlock;
		}
		return possibleMoves.get(randomGenerator.nextInt(possibleMoves.size()));
	}
	
	public Cell random (int board[][]){
		int r = randomGenerator.nextInt(size);
		int c = randomGenerator.nextInt(size);
		while (board[r][c]!=0){
			r = randomGenerator.nextInt(size);
			c = randomGenerator.nextInt(size);
		}
		return new Cell (r,c);
	}
	
	public void setSize(int size){
		this.size=size;
	}

}
