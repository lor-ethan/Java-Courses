
/**
* This program will calculate the surface area and volume of a cone.
*
* @author Ethan Lor
*
*/

import java.util.Scanner;

public class Conic {
	/**
	 * This class will calculate the surface area and volume of a cone in
	 * inches and meters.
	 * 
	 * @param args Not used.
	 *            
	 * 
	 * 
	 */

	public static void main(String[] args) {

		// Methods to assign variables and scan for user input to assign to those variables.
		System.out.print("Enter cone radius (inches): ");
		Scanner scanRadius = new Scanner(System.in);
		int radiusInches = scanRadius.nextInt();

		System.out.print("Enter cone height (feet):   ");
		Scanner scanHeight = new Scanner(System.in);
		int heightFeet = scanHeight.nextInt();

		// Declaring variables with conversions to be used later in calculations.
		double heightInches = heightFeet * 12;
		double radiusMeters = radiusInches / 39.3701;
		double heightMeters = heightFeet / 3.28084;

		//  Declaring variables with proper "expressions"/equations to compute user input.
		float areaInches = (float) (Math.PI * radiusInches
				* (Math.sqrt(Math.pow(radiusInches, 2) + (Math.pow(heightInches, 2)))));

		float areaMeters = (float) (Math.PI * radiusMeters
				* (Math.sqrt(Math.pow(radiusMeters, 2) + (Math.pow(heightMeters, 2)))));

		float volumeInches = ((float) (Math.PI * Math.pow(radiusInches, 2) * heightInches) / 3);

		float volumeMeters = ((float) (Math.PI * Math.pow(radiusMeters, 2) * heightMeters) / 3);

		// Print line break and screen outputs.
		System.out.println("----------------------------------------------------------------");

		System.out.println("Surface Area: " + areaInches + " square inches (" + areaMeters + " square meters)");
		
		System.out.println("Volume: " + volumeInches + " cubic inches (" + volumeMeters + " cubic meters)");

		// Close scanners
		scanHeight.close();
		scanRadius.close();
		
	}

}
