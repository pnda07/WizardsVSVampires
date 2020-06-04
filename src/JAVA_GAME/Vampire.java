package JAVA_GAME;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Vampire extends Zombie {
	private static final int HEALTH = 100;
	private static final int COOLDOWN = 30;
	private static final int SPEED = -3;
	private static final int ATTACKDAMAGE = 5;
	private static final int HEAL_AMOUNT = 5;
	
	private static final BufferedImage image;

	static {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("src/JAVA_GAME/Actor-Icons/vampire.png"));

		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
		image = tempImage;
	}

	public Vampire(Point2D.Double position) {
		super(position, new Point2D.Double(image.getWidth(), image.getHeight()), image, HEALTH, COOLDOWN, SPEED,
				ATTACKDAMAGE);
	}

	@Override
	public void attack(Actor other) {
		/**
		 * Add lifesteal to the vampire
		 * @param other
		 */
		if (this != other && this.isCollidingOther(other)) {
			if (this.readyForAction()) {
				other.changeHealth(-attackDamage);
				this.changeHealth(HEAL_AMOUNT);
				this.resetCoolDown();
			}
		}
	}
}
