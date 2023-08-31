import java.io.Serializable;

public class OpenQuestion extends Question implements Serializable {
	
	private String answer;
	
	public OpenQuestion(String question, String answer, int difficultyIndex) {
		super(question, difficultyIndex);
		this.answer = answer;
	}
	
	public OpenQuestion(String question, String answer) {
		super(question);
		this.answer = answer;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
