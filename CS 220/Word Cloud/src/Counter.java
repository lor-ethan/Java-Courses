// SortComparison.java
// Creates a Counter object that stores a String and an integer
// Ethan Lor

public class Counter implements WordCounter{
	private String word;
	private int count;
	
	public Counter(String word, int count){
		this.word = word;
		this.count = count;
	}

	public int compareTo(Object o) {
		if(o instanceof Counter){
			return(word.compareTo(((Counter) o).getWord()));
		} else {
			return -1;
		}
	}
	
	public boolean equals(Object o){
		if(compareTo(o) == 0){
			return true;
		} else {
			return false;
		}
	}

	public String getWord() {
		return word;
	}

	public int getCount() {
		return count;
	}

	public void setWord(String word) {
		this.word = word;
		
	}

	public void setCount(int count) {
		this.count = count;
	}

}
