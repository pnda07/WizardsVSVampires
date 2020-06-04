package JAVA_GAME;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Plant extends Actor {
	private static final int HEALTH = 80;
	private static final int COOLDOWN = 20;
	private static final int ATTACKDAMAGE = 10;
	
	private static final BufferedImage image;

	static {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("src/JAVA_GAME/Actor-Icons/warrior.png"));

		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
		image = tempImage;
	}
	public Plant(Point2D.Double startingPosition, Point2D.Double initHitbox, BufferedImage img, int health, int coolDown, int attackDamage) {
		super(startingPosition, initHitbox, img, health, coolDown, 0, attackDamage);
	}

	public Plant(Point2D.Double startingPosition) {
		super(startingPosition, new Point2D.Double(image.getWidth(), image.getHeight()), image, HEALTH,COOLDOWN,0,ATTACKDAMAGE);
	}
	/**
	 * An attack means the two hitboxes are overlapping and the
	 * Actor is ready to attack again (based on its cooldown).
	 * 
	 * Plants only attack Zombies.
	 * 
	 * @param other
	 */
	@Override
	public void attack(Actor other) {
		if (other instanceof Zombie)
			super.attack(other);
	}
	@Override
	public void drawHealthBar(Graphics g) {
		Point2D.Double pos = this.getPosition();
		Point2D.Double box = this.getHitbox();
	    g.setColor(Color.BLACK);  
		g.drawRect((int)pos.getX(),(int) pos.getY() - 10, (int)box.getX(), 5);  
	    g.setColor(Color.GREEN);  
		g.fillRect((int)pos.getX(),(int) pos.getY() - 10, (int)(box.getX() * this.getHealth() / (double)this.getFullHealth()), 5);  
	}
}
