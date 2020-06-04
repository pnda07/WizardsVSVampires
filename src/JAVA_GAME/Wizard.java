package JAVA_GAME;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wizard extends Plant{
	private static final int HEALTH = 20;
	private static final int COOLDOWN = 70;
	private static final int ATTACKDAMAGE = 20;
	private static final int ATTACKRANGE = 128;
	
	private static final BufferedImage image;

	static {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("src/JAVA_GAME/Actor-Icons/wizard.png"));

		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
		image = tempImage;
	}

	public Wizard(Point2D.Double position) {
		super(position, new Point2D.Double(image.getWidth(), image.getHeight()), image, HEALTH, COOLDOWN,
				ATTACKDAMAGE);
	}

	public boolean isInRange(Actor other) {
		// See if this rectangle is above the other
		if (this.getPosition().getY() + this.getHitbox().getY() < other.getPosition().getY())
			return false;
		// See if this rectangle is below the other
		if (this.getPosition().getY() > other.getPosition().getY() + other.getHitbox().getY())
			return false;
		// See if this rectangle is left of the other
		if (this.getPosition().getX() + this.getHitbox().getX() + ATTACKRANGE < other.getPosition().getX())
			return false;
		// See if this rectangle is right of the other
		if (this.getPosition().getX() - ATTACKRANGE > other.getPosition().getX() + other.getHitbox().getX())
			return false;
		
		// If it is not above or below or left or right of the other, it is colliding.
		return true;
	}



	@Override
	public void attack(Actor other) {
		/**
		 * Overriding the attack to be ranged using isInRange
		 * @param other
		 */
		if(other instanceof Zombie)
			if(this.isInRange(other) && this != other)
				if(this.readyForAction()) {
					other.changeHealth(-attackDamage);
					this.resetCoolDown();
				}
		
	}

	

}
