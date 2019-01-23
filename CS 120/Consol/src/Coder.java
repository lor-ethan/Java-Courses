
/**
* This program encodes and decodes a string.
*
* @author Ethan Lor
*
*/

import java.util.Scanner;
import java.util.Random;

public class Coder {
	/**
	 * This class randomly encodes and decodes the first and last letters of sections of a
	 * string based on its division by 3.
	 * 
	 * @param args Not used.
	 *        
	 * 
	 */

	public static void main(String[] args) {

		// Creating and assignment of random variables.
		// Could have used only one random variable, but wanted to be more random.
		Random ran1, ran2, ran3, ran4, ran5, ran6;
		ran1 = new Random();
		int ranNum1 = ran1.nextInt(10) + 1;
		ran2 = new Random();
		int ranNum2 = ran2.nextInt(10) + 1;
		ran3 = new Random();
		int ranNum3 = ran3.nextInt(10) + 1;
		ran4 = new Random();
		int ranNum4 = ran4.nextInt(10) + 1;
		ran5 = new Random();
		int ranNum5 = ran5.nextInt(10) + 1;
		ran6 = new Random();
		int ranNum6 = ran6.nextInt(10) + 1;

		// Methods to scan and assign user input to variable.
		System.out.print("Please enter a String: ");
		Scanner scanText = new Scanner(System.in);
		String text = scanText.nextLine();
		System.out.println("-----------------------------------------");

		// String text is to be divided into three parts/groups.
		int divFactor = (text.length() / 3);

		// Primitives to pars strings and change first and last characters to "encode" strings.
		String textString1 = (text.substring(0, divFactor));
		char string1First = (textString1.charAt(0));
		char string1Last = (textString1.charAt(divFactor - 1));
		char string1FirstChange = (char) (string1First + ranNum1);
		char string1LastChange = (char) (string1Last + ranNum2);
		String stringA = (string1FirstChange + textString1.substring(1, divFactor) + string1LastChange);

		String textString2 = (text.substring(divFactor, divFactor * 2));
		char string2First = (textString2.charAt(0));
		char string2Last = (textString2.charAt(divFactor - 1));
		char string2FirstChange = (char) (string2First + ranNum3);
		char string2LastChange = (char) (string2Last + ranNum4);
		String stringB = (string2FirstChange + textString2.substring(1, divFactor - 1) + string2LastChange);

		String textString3 = (text.substring(divFactor * 2));
		char string3First = (textString3.charAt(0));
		char string3Last = (textString3.charAt((textString3.length()) - 1));
		char string3FirstChange = (char) (string3First + ranNum5);
		char string3LastChange = (char) (string3Last + ranNum6);
		String stringC = (string3FirstChange + textString3.substring(1, textString3.length() - 1) + string3LastChange);

		// Assign expression to variable to concatenate string parts in desired order.
		// Print out string.
		String stringEncoded = (stringC + stringA + stringB);
		System.out.println("Encoded String: " + stringEncoded);

		// Did not need to find dividing factor again, but like in 
		// real encoding/"encryption" the other computer should have to calculate it.
		String encodedString = stringEncoded;
		int divFactor2 = (encodedString.length() / 3);
		int firstStringLength = (encodedString.length() - (divFactor2 * 2));

		// Primitives to pars strings and change back characters to "decode" strings.
		String encodedText1 = (encodedString.substring(0, firstStringLength));
		char encoded1First = (encodedText1.charAt(0));
		char encoded1Last = (encodedText1.charAt((encodedText1.length()) - 1));
		char encoded1FirstChange = (char) (encoded1First - ranNum5);
		char encoded1LastChange = (char) (encoded1Last - ranNum6);
		String encodedA = (encoded1FirstChange + encodedText1.substring(1, encodedText1.length() - 1)
				+ encoded1LastChange);

		String encodedText2 = (encodedString.substring(firstStringLength, firstStringLength + divFactor2));
		char encoded2First = (encodedText2.charAt(0));
		char encoded2Last = (encodedText2.charAt(divFactor - 1));
		char encoded2FirstChange = (char) (encoded2First - ranNum1);
		char encoded2LastChange = (char) (encoded2Last - ranNum2);
		String encodedB = (encoded2FirstChange + encodedText2.substring(1, divFactor2 - 1) + encoded2LastChange);

		String encodedText3 = (encodedString.substring((firstStringLength + divFactor2),
				firstStringLength + divFactor2 * 2));
		char encoded3First = (encodedText3.charAt(0));
		char encoded3Last = (encodedText3.charAt(divFactor - 1));
		char encoded3FirstChange = (char) (encoded3First - ranNum3);
		char encoded3LastChange = (char) (encoded3Last - ranNum4);
		String encodedC = (encoded3FirstChange + encodedText3.substring(1, divFactor2 - 1) + encoded3LastChange);

		// Assign expression to variable to concatenate string parts back to correct order.
		// Print out string. 
		String stringDecoded = (encodedB + encodedC + encodedA);
		System.out.println("Decoded string: " + stringDecoded);
		
		// Close scanner
		scanText.close();

	}

}
