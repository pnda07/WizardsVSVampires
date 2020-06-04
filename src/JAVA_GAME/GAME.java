package JAVA_GAME;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GAME extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton wizardButton;
	private JButton warriorButton;

	private JButton quitButton;
	
	private static Battleground battleFrame;

	public GAME() {
		super("Adventurers vs Monsters");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//MAIN JPANEL CONTAINING THE GAME PANEL
		JPanel mainPanel = new JPanel(); // main panel
		BoxLayout boxlayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS); 

		mainPanel.setLayout(boxlayout);
		mainPanel.setBackground(Color.cyan);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		// CONTROL PANEL JPANEL
		//Make Control Panel and set background
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(Color.pink);

		//Make buttons
		wizardButton = new JButton("Wizard (200 mana, Ranged)");
		warriorButton = new JButton("Warrior (100 mana, Melee)");
		quitButton = new JButton("quit");

		//Add ActionListener to buttons
		wizardButton.addActionListener(this);
		warriorButton.addActionListener(this);

		quitButton.addActionListener(this);

		//Add buttons to control Jpanel
		controlPanel.add(wizardButton);
		controlPanel.add(warriorButton);
		controlPanel.add(quitButton);
		
		controlPanel.setPreferredSize(new Dimension(50, 50));
		
		//GAMEPLAY PANEL battleground
		battleFrame = new Battleground();
		
		//JLabel for the game
		JLabel screenTitle = new JLabel("Adventurers vs Monsters", SwingConstants.CENTER);
		screenTitle.setPreferredSize(new Dimension(50, 50));

		JLabel ManaCounter = battleFrame.ManaCounter;
		screenTitle.setPreferredSize(new Dimension(50, 50));
		
		//ADDING PANELS TO MAIN PANEL
		mainPanel.add(screenTitle);
		mainPanel.add(ManaCounter);
		mainPanel.add(controlPanel);
		mainPanel.add(battleFrame);

		//Adding the main pannel to the Jframe
		this.add(mainPanel);

		//Make the frame fit the window
		this.pack();
		//Show it
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GAME app = new GAME();
				app.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == wizardButton) 
			Battleground.setplantType("wizard");
			
		if(e.getSource() == warriorButton) 
			Battleground.setplantType("warrior");

		if(e.getSource() == quitButton)
			this.dispose();
	}
}
