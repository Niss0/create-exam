
public class TestException extends Exception{
	
	public TestException(String msg) {
		super(msg);
	}
	
	public TestException() {
		super("General TestException: Faild creating the test");
	}
	
	
}
