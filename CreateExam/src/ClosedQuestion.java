import java.io.Serializable;

public class ClosedQuestion extends Question implements Serializable{
	
	// members
	private static final int MAX_ANSWERS = 10;
	private int numOfAnswers;
	private QuestionAnswer[] answers; 
	
	// c'tor
	public ClosedQuestion(String question, int difficultyIndex) {
		super(question, difficultyIndex);
		this.answers = new QuestionAnswer[MAX_ANSWERS];
		this.numOfAnswers = 0;
	}
	
	public ClosedQuestion(String question) {
		super(question);
		this.answers = new QuestionAnswer[MAX_ANSWERS];
		this.numOfAnswers = 0;
	}
	
	public boolean setNumOfAnswers(int currAnswers) {
		if(currAnswers <= MAX_ANSWERS && currAnswers >= 0) { // check if currAnswers is valid
			numOfAnswers = currAnswers;
			return true;
		}
		return false;
	}
	
	public int getNumOfAnswers() {
		return numOfAnswers;
	}
	
	public QuestionAnswer[] getAnswers() {
		return answers;
	}
	
	// methods 
	
	/**
	 * this method add an answer to the array of answers
	 * @param answer: the answer we want to add
	 * @return: True or False if we were able to add the question successfully
	 */
	public boolean addAnswer(QuestionAnswer answer) {
		if(numOfAnswers < MAX_ANSWERS && answer.getAnswer() != null) {
			answers[numOfAnswers] = answer;
			numOfAnswers++;
			return true;
		}
		else 
			return false;
	}
	
	//this method print all the question's answers
	public void printAnswers() {
		for(int i = 0; i < numOfAnswers; i++) {
			System.out.printf("   %d) %s (%b)\n", i+1, answers[i].getAnswer(),answers[i].getIsTrue());
		}
		System.out.println();
	}
	
	/**
	 * this method delete a specific question's answer
	 * @param index: the index of the answer we want to delete
	 */
	public void deleteAnswerFromQuestionHelper(int index) {
		answers[index] = answers[numOfAnswers-1];
		answers[numOfAnswers-1] = null;
		numOfAnswers--;
	}
	

}
