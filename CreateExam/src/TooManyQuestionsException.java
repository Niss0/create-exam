
public class TooManyQuestionsException extends TestException {
	
	public TooManyQuestionsException(int maxQuestion) {
		super("Error! You can only add up to " + maxQuestion + " questions");
	}
}
