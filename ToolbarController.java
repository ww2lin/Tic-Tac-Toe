import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ToolbarController implements ActionListener {
	private Model model;
	
	ToolbarController(Model model) {
		this.model = model;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.newGame();
		
	}

}
