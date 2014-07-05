// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;	

public class Main{

	public static void main(String[] args){	
		JFrame frame = new JFrame("Tic-Tac-Toe");
		
		// create Model and initialize it
		Model model = new Model();
		BoardController boardController = new BoardController(model);
		ToolbarController toolbar = new ToolbarController(model);
		PlayerController playerController = new PlayerController(model);
		
		BoardView boardView = new BoardView(model, boardController);
		ToolbarView toolbarView = new ToolbarView(model,toolbar); 
		PlayerView playerView = new PlayerView(model,playerController);
		
		
		// tell Model about View. 
		model.addObserver(boardView);
		model.addObserver(toolbarView);
		model.addObserver(playerView);
		
		model.notifyObservers();
		// create the window
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(boardView,BorderLayout.CENTER);
		frame.getContentPane().add(toolbarView,BorderLayout.NORTH);
		frame.getContentPane().add(playerView,BorderLayout.SOUTH);
		frame.setPreferredSize(new Dimension(500,500));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} 
}
