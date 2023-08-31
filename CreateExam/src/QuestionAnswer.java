import java.io.Serializable;

public class QuestionAnswer implements Serializable {
	
	//member variables
	private String answer;
	private boolean isTrue;
	
	//c'tors
	public QuestionAnswer(String answer, boolean isTrue) {
		this.answer = answer;
		this.isTrue = isTrue;
	}
	
	//setter
	public boolean setAnswer(String answer) {
		if(answer != null) {
			this.answer = answer;
			return true;
		}
		return false;
	}
			
	public boolean setIsTrue() {
		this.isTrue = isTrue;
		return true;
	}
	
	//getters
	public String getAnswer() {
		return answer;
	}
	
	public boolean getIsTrue() {
		return isTrue;
	}
	

}
