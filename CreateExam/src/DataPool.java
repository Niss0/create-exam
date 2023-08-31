import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class DataPool implements Serializable {
	
	// members
	public static int POOL_SIZE = 100;
	private Question[] questionsPool = new Question[POOL_SIZE];
	private Answer[] answersPool = new Answer[POOL_SIZE];
	private String subject;
	private int numOfQuestions = 0;
	private int numOfAnswers = 0;
	
	// c'tor
	public DataPool(String subject) {
		this.subject = subject;
	}
	
	
	public DataPool() {
	}

	// setters
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	// getters
	public Question[] getQuestionsPool() {
		return questionsPool;
	}
	
	public Answer[] getAnswersPool() {
		return answersPool;
	}

	public int getNumOfQuestions() {
		return numOfQuestions;
	}

	public int getNumOfAnswers() {
		return numOfQuestions;
	}
	
	public String getSubject() {
		return subject;
	}
	
	// setters
	public void setNumOfQuestions(int numOfQuestions) {
		this.numOfQuestions = numOfQuestions;
	}
	
	public void setNumOfAnswers(int numOfAnswers) {
		this.numOfAnswers = numOfAnswers;
	}

	
	
	// methods
	public void addNumOfQuestionsByOne() {
		numOfQuestions++;
	}
	
	public void addNumOfAnswersByOne() {
		numOfAnswers++;
	}
	
	public void subNumOfQuestionsByOne() {
		numOfQuestions--;
	}
	
	public void subNumOfAnswersByOne() {
		numOfAnswers--;
	}


}
