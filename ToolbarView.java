import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ToolbarView extends JPanel implements Observer {
	
	private Model model;
	private JLabel msg = new JLabel();
	private JButton newGameButton = new JButton();
	ToolbarView(Model model, ToolbarController controller){
		newGameButton.setText("New Game");
		newGameButton.addActionListener(controller);
		
		msg.setText("Select which player starts");
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(newGameButton);
		
		add(buttonPanel);
		this.add(Box.createGlue());
		
		JPanel msgPanel = new JPanel();
		msgPanel.add(msg);
		add(msgPanel);
		
		// set the model 
		this.model = model;

	}

	@Override
	public void update(Observable o, Object arg) {
		GameState state = model.getState();
		if (state.getState() == GameState.X_WINS){
			msg.setText("X Wins");
		}
		else if (state.getState() == GameState.O_WINS){
			msg.setText("O Wins");
		}
		else if (state.getState() == GameState.TIE){
			msg.setText("Game over, no winner");
		}
		else if (state.getState() == GameState.INVALID_MOVE){
			msg.setText("Illegal move");
		}
		else if (state.getState() == GameState.RESTART){
			msg.setText("Select which player starts");
		}
		else if (state.getState() == GameState.SELECTED_PLAYER){
			msg.setText("Change which player starts, or make first move.");
		}
		else if(state.getState() == GameState.VALID_MOVE){
			msg.setText(model.getTurn()+" moves");
		}
	}
}
