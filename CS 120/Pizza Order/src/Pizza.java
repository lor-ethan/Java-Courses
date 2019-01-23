
/**
 * This is a basic program for ordering pizzas.
 * 
 * @author Ethan Lor
 *
 */

import java.text.NumberFormat;
import java.util.Scanner;

public class Pizza {

	/**
	 * This class will take a basic pizza order, output an order record, and
	 * give the total cost.
	 * 
	 * @param args Not used.
	 *            
	 * 
	 */
	public static void main(String[] args) {

		// Variables that will be used through out main.
		double meat12 = 11.50;
		double meat16 = 15.50;
		double veg12 = 9.50;
		double veg16 = 13.50;
		double haw12 = 10.50;
		double haw16 = 14.50;
		int size = 0;
		int quantity = 0;
		double price = 0;

		// Using NumberFormat Class to format prices correctly.
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		// Initial prompt and request for input.
		System.out.println("Welcome to the Java and a Slice Pizza Parlor.");
		System.out.print("Please enter a name for our transaction records: ");

		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		System.out.println();
		System.out.println("Thank you, " + name + ".");
		System.out.println("Which type of pizza would you like?");
		System.out.println("-----------------------------------");
		System.out.println("\t" + "[M] Mega-Meaty");
		System.out.println("\t" + "[V] Very Vegetarian");
		System.out.println("\t" + "[H] Heaping Hawaiian");
		System.out.println("-----------------------------------");

		System.out.print("Enter pizza chioce: ");
		String type = scan.nextLine();
		type = type.toLowerCase();

		// Start of boolean methods.
		if (type.equals("m")) {
			type = ("Mega-Meaty");
			System.out.println("\n" + "Price list for pizza type: Mega-Meaty");
			System.out.println("-----------------------------------");
			System.out.println("\t" + "12-inch: $11.50");
			System.out.println("\t" + "16-inch: $15.50");
			System.out.println("-----------------------------------");
			System.out.println("\n" + "What size pizza would you like?");
			System.out.print("Enter size in inches: ");
			if (scan.hasNextInt()) {
				size = scan.nextInt();
				if (size == 12) {
					System.out.println("\n" + "How many pizzas would you like?");
					System.out.print("Enter number between 1 and 12: ");
					if (scan.hasNextInt()) {
						quantity = scan.nextInt();
						if (quantity > 0 && quantity < 13) {
							price = quantity * meat12;
							System.out.println("\n" + "-----------------------------------");
							System.out.println("Thank you, " + name + ". " + "Here is a record of your purchase.");
							System.out.println("-----------------------------------");
							System.out.println(quantity + " " + size + "-inch " + type + " pizzas.");
							System.out.println("-----------------------------------");
							System.out.println("Total cost: " + formatter.format(price));
							System.out.println("-----------------------------------");
						} else {
							System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
							System.out.print("Goodbye.");
						}
					} else {
						System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
						System.out.print("Goodbye.");
					}
				} else if (size == 16) {
					System.out.println("\n" + "How many pizzas would you like?");
					System.out.print("Enter number between 1 and 12: ");
					if (scan.hasNextInt()) {
						quantity = scan.nextInt();
						if (quantity > 0 && quantity < 13) {
							price = quantity * meat16;
							System.out.println("\n" + "-----------------------------------");
							System.out.println("Thank you, " + name + ". " + "Here is a record of your purchase.");
							System.out.println("-----------------------------------");
							System.out.println(quantity + " " + size + "-inch " + type + " pizzas.");
							System.out.println("-----------------------------------");
							System.out.println("Total cost: " + formatter.format(price));
							System.out.println("-----------------------------------");
						} else {
							System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
							System.out.print("Goodbye.");
						}
					} else {
						System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
						System.out.print("Goodbye.");
					}
				} else {
					System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
					System.out.print("Goodbye.");
				}
			} else {
				System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
				System.out.print("Goodbye.");
			}

			// Basically a repeat of the above boolean and nested boolean methods
			// per type of pizza.
		} else if (type.equals("v")) {
			type = ("Very Vegetarian");
			System.out.println("\n" + "Price list for pizza type: Very Vegetarian");
			System.out.println("-----------------------------------");
			System.out.println("\t" + "12-inch: $9.50");
			System.out.println("\t" + "16-inch: $13.50");
			System.out.println("-----------------------------------");
			System.out.println("\n" + "What size pizza would you like?");
			System.out.print("Enter size in inches: ");
			if (scan.hasNextInt()) {
				size = scan.nextInt();
				if (size == 12) {
					System.out.println("\n" + "How many pizzas would you like?");
					System.out.print("Enter number between 1 and 12: ");
					if (scan.hasNextInt()) {
						quantity = scan.nextInt();
						if (quantity > 0 && quantity < 13) {
							price = quantity * veg12;
							System.out.println("\n" + "-----------------------------------");
							System.out.println("Thank you, " + name + ". " + "Here is a record of your purchase.");
							System.out.println("-----------------------------------");
							System.out.println(quantity + " " + size + "-inch " + type + " pizzas.");
							System.out.println("-----------------------------------");
							System.out.println("Total cost: " + formatter.format(price));
							System.out.println("-----------------------------------");
						} else {
							System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
							System.out.print("Goodbye.");
						}
					} else {
						System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
						System.out.print("Goodbye.");
					}
				} else if (size == 16) {
					System.out.println("\n" + "How many pizzas would you like?");
					System.out.print("Enter number between 1 and 12: ");
					if (scan.hasNextInt()) {
						quantity = scan.nextInt();
						if (quantity > 0 && quantity < 13) {
							price = quantity * veg16;
							System.out.println("\n" + "-----------------------------------");
							System.out.println("Thank you, " + name + ". " + "Here is a record of your purchase.");
							System.out.println("-----------------------------------");
							System.out.println(quantity + " " + size + "-inch " + type + " pizzas.");
							System.out.println("-----------------------------------");
							System.out.println("Total cost: " + formatter.format(price));
							System.out.println("-----------------------------------");
						} else {
							System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
							System.out.print("Goodbye.");
						}
					} else {
						System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
						System.out.print("Goodbye.");
					}
				} else {
					System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
					System.out.print("Goodbye.");
				}
			} else {
				System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
				System.out.print("Goodbye.");
			}

		} else if (type.equals("h")) {
			type = ("Heaping Hawaiian");
			System.out.println("\n" + "Price list for pizza type: Heaping Hawaiian");
			System.out.println("-----------------------------------");
			System.out.println("\t" + "12-inch: $10.50");
			System.out.println("\t" + "16-inch: $14.50");
			System.out.println("-----------------------------------");
			System.out.println("\n" + "What size pizza would you like?");
			System.out.print("Enter size in inches: ");
			if (scan.hasNextInt()) {
				size = scan.nextInt();
				if (size == 12) {
					System.out.println("\n" + "How many pizzas would you like?");
					System.out.print("Enter number between 1 and 12: ");
					if (scan.hasNextInt()) {
						quantity = scan.nextInt();
						if (quantity > 0 && quantity < 13) {
							price = quantity * haw12;
							System.out.println("\n" + "-----------------------------------");
							System.out.println("Thank you, " + name + ". " + "Here is a record of your purchase.");
							System.out.println("-----------------------------------");
							System.out.println(quantity + " " + size + "-inch " + type + " pizzas.");
							System.out.println("-----------------------------------");
							System.out.println("Total cost: " + formatter.format(price));
							System.out.println("-----------------------------------");
						} else {
							System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
							System.out.print("Goodbye.");
						}

					} else {
						System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
						System.out.print("Goodbye.");
					}
				} else if (size == 16) {
					System.out.println("\n" + "How many pizzas would you like?");
					System.out.print("Enter number between 1 and 12: ");
					if (scan.hasNextInt()) {
						quantity = scan.nextInt();
						if (quantity > 0 && quantity < 13) {
							price = quantity * haw16;
							System.out.println("\n" + "-----------------------------------");
							System.out.println("Thank you, " + name + ". " + "Here is a record of your purchase.");
							System.out.println("-----------------------------------");
							System.out.println(quantity + " " + size + "-inch " + type + " pizzas.");
							System.out.println("-----------------------------------");
							System.out.println("Total cost: " + formatter.format(price));
							System.out.println("-----------------------------------");
						} else {
							System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
							System.out.print("Goodbye.");
						}

					} else {
						System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
						System.out.print("Goodbye.");
					}
				} else {
					System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
					System.out.print("Goodbye.");
				}
			} else {
				System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
				System.out.print("Goodbye.");
			}
		} else {
			System.out.println("\n" + "I'm sorry, " + name + ", but that is an invalid choice.");
			System.out.print("Goodbye.");
		}

		scan.close();

	}

}
