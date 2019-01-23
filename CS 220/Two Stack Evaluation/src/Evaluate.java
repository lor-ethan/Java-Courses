
// Evaluate.java
// Takes a text file of integer expressions, evaluates each expression using linked list stacks
// and prints each expression with its solution.
// Note to professor: Coded to take multi-digit unsigned integers.
// Ethan Lor

import java.io.*;
import java.util.*;
import javax.swing.*;

public class Evaluate {

	private StackList<Integer> operandStack = new StackList<Integer>();
	private StackList<Character> operatorStack = new StackList<Character>();

	private void evaluate() {
		final String HOME_DIRECTORY = System.getProperty("user.home");

		JFileChooser chooser = new JFileChooser(HOME_DIRECTORY + "/Desktop");
		int retVal = chooser.showOpenDialog(null);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			String file = f.getAbsolutePath();

			try {
				FileInputStream fis = new FileInputStream(file);
				Scanner scan = new Scanner(fis);
				while (scan.hasNext()) {
					String line = scan.nextLine();
					System.out.println(line + " == " + checkString(line));
				}
				scan.close();
				fis.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private int checkString(String line) throws Exception {
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c >= '0' && c <= '9') {
				int num = (int) (c - '0');
				boolean numEnd = false;
				while (numEnd == false && i < line.length() - 1) {
					if (line.charAt(i + 1) >= '0' && line.charAt(i + 1) <= '9') {
						num = (num * 10) + (int) (line.charAt(i + 1) - '0');
						i++;
					} else {
						numEnd = true;
					}
				}
				operandStack.push(num);
			} else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == '>') {
				if (operatorStack.isEmpty() || checkPrecedence(operatorStack, c)) {
					operatorStack.push(c);
				} else {
					char operator = operatorStack.pop();
					int b = operandStack.pop();
					int a = operandStack.pop();
					operandStack.push(doArithmetic(operator, a, b));
					operatorStack.push(c);
				}
			} else if (c == '(' || c == ')') {
				if (c == '(') {
					operatorStack.push(c);
				}

				if (c == ')') {
					while (operatorStack.top() != '(') {
						char operator = operatorStack.pop();
						int b = operandStack.pop();
						int a = operandStack.pop();
						operandStack.push(doArithmetic(operator, a, b));
					}
					operatorStack.pop();
				}
			} else {
				throw new RuntimeException("Error, Unexpected Character");
			}
		}
		return popStacks();
	}

	private boolean checkPrecedence(StackList<Character> list, char c) {
		char top = list.top();

		if (list.isEmpty()) {
			return true;
		} else if (c == '+' || c == '-') {
			if (top == '(') {
				return true;
			} else {
				return false;
			}
		} else if ((c == '*') || (c == '/')) {
			if ((top == '<') || (top == '>') || (top == '*') || (top == '/')) {
				return false;
			} else {
				return true;
			}
		} else if ((c == '<') || (c == '>')) {
			if ((top == '<') || (top == '>')) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private int doArithmetic(char operator, int a, int b) {
		int solution;

		if (operator == '+') {
			solution = a + b;
		} else if (operator == '-') {
			solution = a - b;
		} else if (operator == '*') {
			solution = a * b;
		} else if (operator == '/') {
			solution = a / b;
		} else if (operator == '<') {
			solution = Math.min(a, b);
		} else {
			solution = Math.max(a, b);
		}
		return solution;
	}

	private int popStacks() {
		int solution = 0;
		while (!operatorStack.isEmpty()) {
			char operator = operatorStack.pop();
			int b = operandStack.pop();
			int a = operandStack.pop();
			solution = doArithmetic(operator, a, b);
			if (!operandStack.isEmpty()) {
				operandStack.push(solution);
			}
		}
		return solution;
	}

	public static void main(String[] args) {
		Evaluate eval = new Evaluate();
		eval.evaluate();
	}
}

// StackList class that creates generic linked lists and utilizes them as stacks.
class StackList<T> {

	private LinkedList<T> list = new LinkedList<T>();

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void push(T data) {
		list.addFirst(data);
	}

	public T pop() {
		if (list.isEmpty()) {
			throw new RuntimeException("Error, Tried to top/peek an empty stack.");
		}
		return list.removeFirst();
	}

	public T pull() {
		return pop();
	}

	public T top() {
		if (list.isEmpty()) {
			throw new RuntimeException("Error, Tried to top/peek an empty stack.");
		}
		return list.getFirst();
	}

	public T peek() {
		return top();
	}
}