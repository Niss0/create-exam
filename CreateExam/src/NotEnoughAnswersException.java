
public class NotEnoughAnswersException extends TestException{
	
	public NotEnoughAnswersException() {
		super("Error! You need to select a question with more than 3 answers");
		
	}

}
