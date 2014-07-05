import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


public class PlayerView  extends JPanel implements Observer{

	private Model model;
	private JButton buttonX = new JButton();
	private JButton buttonO = new JButton();
	private JLabel labelAi = new JLabel("Computer:");
	private JComboBox checkBoxAi;
	JSlider boardSlider;
	JComboBox winingConstraint;
	PlayerView(Model model, PlayerController controller){
		this.model = model;
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JPanel leftMenu = new JPanel();
		leftMenu.setLayout(new BoxLayout(leftMenu,BoxLayout.Y_AXIS));
		
		JPanel buttonMenu = new JPanel();
		buttonMenu.setLayout(new BoxLayout(buttonMenu, BoxLayout.X_AXIS));
		
		buttonX.setText("X");
		buttonX.addActionListener(controller.startx);
		buttonX.setPreferredSize(new Dimension(50,25));
		
		buttonO.setText("O");
		buttonO.addActionListener(controller.starty);
		buttonO.setPreferredSize(new Dimension(50,25));
		
		add(labelAi);
		
		String[] ai_mode = {"Two Player","Random","Greedy"};
		checkBoxAi = new JComboBox<String>(ai_mode);
		add(checkBoxAi);
		
		add(Box.createGlue());
		
		JPanel panel1 = new JPanel();
		panel1.add(buttonX);
		buttonMenu.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.add(buttonO);
		buttonMenu.add(panel2);
		buttonMenu.add(Box.createGlue());
		
		leftMenu.add(buttonMenu);
		
		
		JPanel aiMenu = new JPanel();
		aiMenu.setLayout(new BoxLayout(aiMenu, BoxLayout.X_AXIS));
		
		checkBoxAi.addActionListener(controller.aiCheckBox);
		aiMenu.add(labelAi);
		aiMenu.add(checkBoxAi);
		
		
		leftMenu.add(aiMenu);
		add(leftMenu);
		
		
		JPanel gameBoardSliders = new JPanel();
		gameBoardSliders.setLayout(new BoxLayout(gameBoardSliders,BoxLayout.Y_AXIS));
		
		//add the board size slider
		JPanel gameBoardPanel = new JPanel();
		gameBoardPanel.setLayout(new BoxLayout(gameBoardPanel,BoxLayout.X_AXIS));
		
		JLabel boardSizeLabel = new JLabel();
		boardSizeLabel.setText("Board Size: ");
		
		boardSlider = new JSlider(Util.minBoardSize,Util.maxBoardSize,model.getBoardSize());
		//boardSlider petList = new JComboBox(petStrings);
		boardSlider.addChangeListener(controller.boardSizeSlider);
		boardSlider.setMajorTickSpacing(1);
		boardSlider.setPaintTicks(true);
		boardSlider.setPaintLabels(true);
		boardSlider.setPreferredSize(new Dimension(100,50));
		
		gameBoardPanel.add(boardSizeLabel);
		gameBoardPanel.add(boardSlider);
		//add it to the main panel
		gameBoardSliders.add(gameBoardPanel);
		
		
		//add the drop down menu for the win constraint
		JPanel winingRestrictionPanel = new JPanel();
		winingRestrictionPanel.setLayout(new BoxLayout(winingRestrictionPanel,BoxLayout.X_AXIS));
		
		String[] choices = new String[Util.maxBoardSize-Util.minBoardSize+1];
		
		for (int i = 0; i< choices.length; ++i) choices[i] = Integer.toString(Util.minBoardSize+i);
		
		JLabel winingRestrictionLabel= new JLabel();
		winingRestrictionLabel.setText("Win Rest.: ");
		
		winingConstraint=  new JComboBox<String> (choices);
		winingConstraint.addActionListener(controller.boardWiningSlider);
		
		winingRestrictionPanel.add(winingRestrictionLabel);
		winingRestrictionPanel.add(winingConstraint);
		
		//add it to the main panel
		gameBoardSliders.add(winingRestrictionPanel);
		
		add(gameBoardSliders);
		add(Box.createGlue());
		
	}
	@Override
	public void update(Observable o, Object arg) {
		GameState state = model.getState();
		//enable the slider/drop down menu if the game havent started yet
		if (state.getState() == GameState.RESTART || 
				state.getState() == GameState.SELECTING_PLAYER || state.getState() == GameState.SELECTED_PLAYER){
			boardSlider.setEnabled(true);
			boardSlider.setValue(model.getBoardSize());
			winingConstraint.setEnabled(true);
			checkBoxAi.setEnabled(true);
			buttonX.setEnabled(true);
			buttonO.setEnabled(true);
		}
		else{
			boardSlider.setEnabled(false);
			winingConstraint.setEnabled(false);
			checkBoxAi.setEnabled(false);
			buttonX.setEnabled(false);
			buttonO.setEnabled(false);
			
		}
	}

}
