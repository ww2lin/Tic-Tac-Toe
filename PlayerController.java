import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PlayerController {

	private static Model model;
	public StartX startx= new StartX();
	public StartY starty= new StartY();
	public BoardSizeSlider boardSizeSlider = new BoardSizeSlider();
	public AiCheckBox aiCheckBox = new AiCheckBox();
	public BoardWiningSlider boardWiningSlider = new BoardWiningSlider();
	PlayerController(Model model) {
		PlayerController.model = model;

	}

	private class StartX implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.setStartingX();
		}

	}
	
	private class StartY implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.setStartingY();
		}
	}
	private class BoardSizeSlider implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			model.boardSizeSlider(e);
			
		}

	}
	private class BoardWiningSlider implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			model.BoardWiningSlider(e);
		}
	}
	private class AiCheckBox implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			model.setAiMode(e);
		}
	}
}