// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BoardView extends JPanel  implements Observer{
	
	// the view's main user interface
	private JButton[][] board;
	JPanel gameBoard = new JPanel();
	// the model that this view is showing
	private Model model;
	
	BoardView(Model model, BoardController controller) {
		this.setLayout(new BorderLayout());
		// set the model 
		this.model = model;
		resizeBoard(model.getBoardSize());
	} 

	@Override
	public void update(Observable arg0, Object arg1) {
		GameState state = model.getState();
		//check which state we in , and update the view
		if (state.getState() == GameState.VALID_MOVE){
			board[model.getRow()][model.getCol()].setText(model.getSymbolByTurn());
		}
		else if (state.getState() == GameState.RESTART){
			resetButtonText();
		}
		else if (state.getState() == GameState.RESIZE_BOARD){
			resizeBoard(model.getBoardSize());
		}
		else if (state.isGameOver()){
			handleGameOverState(state,model.getRow(),model.getCol(),model.getwinConstraint());
		}
	}
	
	private void resetButtonText(){
		for (int i=0; i< board.length; ++i ){
			for (int j=0; j< board.length; ++j ){
				board[i][j].setText("");
				board[i][j].setBackground(null);
				board[i][j].setEnabled(true);
			}
		}
	}
	
	private void resizeBoard(int size){
		board = new JButton[size][size];
		gameBoard.setEnabled(false);
		gameBoard.setVisible(false);
		gameBoard.removeAll();
		gameBoard = new JPanel();
		gameBoard.setBorder(new EmptyBorder(Util.PADDING, Util.PADDING, Util.PADDING, Util.PADDING));
		gameBoard.setLayout(new GridLayout(size,size));
		this.add(gameBoard, BorderLayout.CENTER);
		
		for (int i=0; i< board.length; ++i ){
			for (int j=0; j< board.length; ++j ){
				board[i][j] = new JButton();
				board[i][j].setMaximumSize(new Dimension(100, 50));
				board[i][j].setPreferredSize(new Dimension(100, 50));
				BoardController.Cell cell = new BoardController.Cell(i, j);
				board[i][j].addActionListener(cell);
				gameBoard.add(board[i][j]);
			}
		}
	}
	
	private void handleGameOverState(GameState state, int r, int c,int wc){
		for (int i=0; i< board.length; ++i ){
			if (state.getWiningLine().size() > 0 && i < wc){
			
				Cell cell = state.getWiningLine().get(i);
				board[cell.row][cell.col].setBackground(Color.GREEN);
			}
			for (int j=0; j< board.length; ++j ){
				board[i][j].setEnabled(false);
			}
		}
	}
} 
