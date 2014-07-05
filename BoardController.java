// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/
// (C) Joseph Mack 2011, jmack (at) wm7d (dot) net, released under GPL v3 (or any later version)

import java.awt.event.*;
public class BoardController {

	private static Model model;
	//public Cell[][] cell = new Cell[Util.boardSize][Util.boardSize];
	BoardController(Model model) {
		BoardController.model = model;

	}

	public static class Cell implements ActionListener {

		private int row = 0, col = 0;

		Cell(int r, int c) {
			row = r;
			col = c;
		}

		public void actionPerformed(ActionEvent e) {
			model.actionPerformedAtIndex(row, col);
		}

		public void setRowCol(int r, int c) {
			row = r;
			col = c;
		}
	}
}