package JAVA_GAME;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Battleground extends JPanel implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private Timer timer;
	private int timeTracker = 0;
	
	
	protected ArrayList<Actor> actors; // Plants and zombies all go in here


	int numRows;
	int numCols;
	int cellSize;

	private int mana = 500;
	protected static String plantType;
	int difficulty = 1;
	int level = 1;
	//JLABEL TEST
	public JLabel ManaCounter = new JLabel("Mana available: " + getMana(), SwingConstants.CENTER);

	public Battleground() {
		super();
		// Adding MouseListener
		this.addMouseListener(this);

		// Define some quantities of the scene
		numRows = 5;
		numCols = 14;
		cellSize = 75;

		setPreferredSize(new Dimension(20 + numCols * cellSize, 20 + numRows * cellSize));
		this.setBackground(Color.decode("#50C878"));

		plantType = "warrior"; // default summoning creature
		

		// Store all the plants and zombies in here.
		actors = new ArrayList<>();
		
		//Adding starting ally lineup
		for (int i = 0; i < 5; i++) {
			Plant plant = new Plant(new Point2D.Double(20 +(75 * 3), (20 + (75 * i))));
			actors.add(plant);
		}
		//Adding starting enemy lineup
		for (int i = 0; i < 5; i++) {
			Zombie zombie = new Zombie(new Point2D.Double(20 + numCols * cellSize, (20 + (75 * i))));
			actors.add(zombie);
		}

		timer = new Timer(30, this);
		timer.start();
	}
	
	
	/**
	 * Changes mana amount.
	 * @param plusMana By how much mana changed.
	 */
	public void setMana(int plusMana) {
		if(mana + plusMana >= 0)
			mana += plusMana;
		else
			mana = 0;
	}
	
	public int getMana() {
		return mana;
	}
	

	/**
	 * Sets the selected plant type. You can choose standard villager or wizard.
	 * 
	 * @param plant
	 */
	public static void setplantType(String plant) {
		plantType = plant;
	}

	/***
	 * Implement the paint method to draw the plants
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw the lines for each lane
		for (int i = 0; i < (numRows - 1); i++) {
			g.drawLine(
				0, 10 + cellSize * (i + 1), // start point
				numCols * cellSize + (cellSize / 2), 10 + cellSize * (i + 1)); // end point
		}
		for (Actor actor : actors) {
			actor.draw(g, 0);
			actor.drawHealthBar(g);
		}
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timeTracker += 1;
		
		if(timeTracker % 360 == 0) {
			level +=1;
			System.out.println("LEVEL " + level);
			difficulty += 1;
		}
		//
		setMana(1);
		ManaCounter.setText("Mana available: " + getMana() +"\n LEVEL " + level);
		
		//Random generator
		Random rand = new Random();
		//Adding zombies by random
		if(rand.nextInt(500) < difficulty ) {
			int col = rand.nextInt(5);

			double x = 20 + numCols * cellSize;
			double y = (20 + (75 * col));
			Zombie zombie1 = new Zombie(new Point2D.Double(x, y ));
			actors.add(zombie1);
		}
		
		//Adding zombies by random
		if(rand.nextInt(2000) < difficulty ) {
			int col = rand.nextInt(5);
		
			double x = 20 + numCols * cellSize;
			double y = (20 + (75 * col));
			Vampire vampire = new Vampire(new Point2D.Double(x, y ));
			actors.add(vampire);
		}
		
		//
		for (Actor actor : actors) {
			actor.update();
			double xCoordinate = actor.getPosition().getX();
			if(actor instanceof Zombie && xCoordinate < 0) {
				System.out.println("Game stopped");			
				timer.stop();
			}
		}

		// Try to attack
		for (Actor zombie : actors) {
			for (Actor other : actors) {
				zombie.attack(other);
			}
		}

		// Remove plants and zombies with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive())
				nextTurnActors.add(actor);
			else
				actor.removeAction(actors); // any special effect or whatever on removal
		}
		actors = nextTurnActors;

		// Check for collisions between zombies and plants and set collision status
		for (Actor zombie : actors) {
			for (Actor other : actors) {
				zombie.setCollisionStatus(other);
			}
		}

		// Move the actors.
		for (Actor actor : actors) {
			actor.move();
		}

		// Redraw the new scene
		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		boolean actorExists = false;
		for (Actor actor : actors)
			if (actor.isCollidingPoint(new Point2D.Double(x, y)))
				actorExists = true;
		
		if (!actorExists) {
			int col = (x) / 75;
			int row = (y) / 75;

			int newX = 20 + (75 * col);
			int newY = 20 + (75 * row);

			if (Battleground.plantType == "wizard" && mana>=200) {
				Wizard wizard = new Wizard(new Point2D.Double(newX, newY));
				actors.add(wizard);
				setMana(-200);
			}
			if (Battleground.plantType == "warrior" && mana>=100) {
				Plant warrior = new Plant(new Point2D.Double(newX, newY));
				actors.add(warrior);
				//Update Mana
				setMana(-100);
			}
		}
		else {
			System.out.println("Can't place it here");
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
