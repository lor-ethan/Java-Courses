
/**
 * This program will draw the planet Neptune, its moon Proteus, and a space ship.
 * 
 * @author Ethan Lor
 * 
 */

import java.awt.Color;
import java.util.Random;

public class Space {
	/**
	 * This class draws the planet Neptune, its moon Proteus, and a space ship.
	 * 
	 * @param args Not Used.
	 * 
	 */

	public static void main(String[] args) {

		// Methods to create window and set properties.
		Window win = new Window();
		int winSize = 600;
		win.setSize(winSize, winSize);
		win.setBackground(Color.black);
		win.setTitle("Space, The Final Frontier.");

		// Following methods will set random sizing and create the planet and
		// moon.
		Oval planet, moon;

		Random randSize = new Random();
		int planetSize = randSize.nextInt((500 - 250) + 1) + 250;
		Random planetRandX = new Random();
		int xLocationP = planetRandX.nextInt(601 - planetSize);
		Random planetRandY = new Random();
		int yLocationP = planetRandY.nextInt(601 - planetSize);
		planet = new Oval(xLocationP, yLocationP, planetSize, planetSize);
		planet.setBackground(Color.cyan);
		win.add(planet);

		int moonSize = ((int) (0.11 * planetSize));
		Random moonRandX = new Random();
		int xLocationM = moonRandX.nextInt(601 - moonSize);
		Random moonRandY = new Random();
		int yLocationM = moonRandY.nextInt(601 - moonSize);
		moon = new Oval(xLocationM, yLocationM, moonSize, moonSize);
		moon.setBackground(Color.magenta);
		win.add(moon);

		// Following methods will create the top and middle part of the ship.
		Oval shipTop;
		shipTop = new Oval(400, 400, 50, 50);
		shipTop.setBackground(Color.blue);
		win.add(shipTop);

		Rectangle shipMiddle;
		shipMiddle = new Rectangle(400, 420, 50, 10);
		shipMiddle.setBackground(Color.orange);
		win.add(shipMiddle);

		// Following methods will create the red lights on ship.
		Oval dot1, dot2, dot3, dot4, dot5;

		dot1 = new Oval(400, 420, 10, 10);
		dot1.setBackground(Color.red);
		win.add(dot1);

		dot2 = new Oval(410, 420, 10, 10);
		dot2.setBackground(Color.red);
		win.add(dot2);

		dot3 = new Oval(420, 420, 10, 10);
		dot3.setBackground(Color.red);
		win.add(dot3);

		dot4 = new Oval(430, 420, 10, 10);
		dot4.setBackground(Color.red);
		win.add(dot4);

		dot5 = new Oval(440, 420, 10, 10);
		dot5.setBackground(Color.red);
		win.add(dot5);

		// Following methods creates the main body parts of the ship.
		Triangle bodyUpperL, bodyLowerL, bodyUpperR, bodyLowerR, upperBody, lowerBody;

		bodyUpperL = new Triangle(360, 430, 80, 20, 1);
		bodyUpperL.setBackground(Color.lightGray);
		win.add(bodyUpperL);

		bodyLowerL = new Triangle(360, 450, 80, 20, 0);
		bodyLowerL.setBackground(Color.lightGray);
		win.add(bodyLowerL);

		bodyUpperR = new Triangle(410, 430, 80, 20, 1);
		bodyUpperR.setBackground(Color.lightGray);
		win.add(bodyUpperR);

		bodyLowerR = new Triangle(410, 450, 80, 20, 0);
		bodyLowerR.setBackground(Color.lightGray);
		win.add(bodyLowerR);

		upperBody = new Triangle(400, 430, 50, 20, 0);
		upperBody.setBackground(Color.white);
		win.add(upperBody);

		lowerBody = new Triangle(400, 450, 50, 20, 1);
		lowerBody.setBackground(Color.gray);
		win.add(lowerBody);

		// Following methods will create the ship's antenna.
		Rectangle antenna;
		antenna = new Rectangle(423, 380, 5, 20);
		antenna.setBackground(Color.white);
		win.add(antenna);

		Oval antennaTop;
		antennaTop = new Oval(420, 375, 10, 10);
		antennaTop.setBackground(Color.yellow);
		win.add(antennaTop);

		// Following methods will create the blast marks.
		Rectangle blast1, blast2, blast3;

		blast1 = new Rectangle(360, 480, 2, 30);
		blast1.setBackground(Color.yellow);
		win.add(blast1);

		blast2 = new Rectangle(423, 500, 2, 30);
		blast2.setBackground(Color.yellow);
		win.add(blast2);

		blast3 = new Rectangle(490, 480, 2, 30);
		blast3.setBackground(Color.yellow);
		win.add(blast3);

	}

}
