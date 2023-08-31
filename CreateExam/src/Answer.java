import java.io.Serializable;

public class Answer implements Serializable{
	
	//members variables 
	private String answer;
	
	//c'tors
	public Answer(String answer) {
		this.answer = answer;
	}
	
	//methods
	public String getAnswer() {
		return answer;
	}

}
