import java.io.Serializable;
import java.util.Scanner;

public abstract class Question implements Serializable{
	public static Scanner s = new Scanner(System.in);
	
	private static int counter;
	private String question;
	private int id;
	
	public enum eDifficulty { EASY, MEDIUM, HARD };
	
	private eDifficulty theDifficulty;
	
	

	/**
	 * this function is the constructor
	 * @param question: String with question we want to add
	 */
	public Question(String question, int difficultyIndex) {
		this.question = question;
		this.id = ++counter;
		setDifficulty(difficultyIndex);
	}
	
	public Question(String question) {
		this.question = question;
		this.id = ++counter;
	}
	

	//setters
	public boolean setQuestion(String q) {
		if(q.length() == 0)
			return false;
		else {
			question = q;
			return true;
		}
	}
	
	
	//getters
	public String getQuestion() {
		return question;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setDifficulty(int num) {
		boolean valid = false;
		while(!valid) {
			switch(num) {
			case 1:
				theDifficulty = eDifficulty.EASY;
				valid = true;
				break;
			case 2:
				theDifficulty = eDifficulty.MEDIUM;
				valid = true;
				break;
			case 3:
				theDifficulty = eDifficulty.HARD;
				valid = true;
				break;
			default:
				System.out.println("You need to enter number between 1-3 only. Try again: ");
				num = s.nextInt();
			}
		}	
	}
	
	public String toString() {
		return "(" + theDifficulty.name() + ")";
	}
}
