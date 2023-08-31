import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class AutomaticExam implements Examable {

	// members
	public static Scanner s = new Scanner(System.in);
	public DataPool data = new DataPool();
	public static final int MAX_QUESTION = 10;
	private Question[] validQuestionsPool = new Question[100];
	private Question[] testQuestionsPool;
	public int numOfQuestions;
	public int numOfValidQuestions;

	// c'tor
	public AutomaticExam(DataPool data) throws Exception {
		numOfValidQuestions = 0;
		createValidQuestionsPool(data);
		boolean isValid = false;
		while (!isValid) {
			try {
				System.out.printf("How many questions do you like to have on the test? (1-%d)\n", numOfValidQuestions);
				numOfQuestions = s.nextInt();
				if (numOfQuestions > numOfValidQuestions)
					throw new TooManyQuestionsException(numOfValidQuestions);
				else if (numOfQuestions < 1)
					throw new Exception("Error! You must choose at least one question");
				else
					isValid = true;
			} catch (InputMismatchException e) {
				s.nextLine(); // buffer
				System.out.println("Error! This is not a number");
			} catch (Exception e) {
				s.nextLine(); // buffer
				System.out.println(e.getMessage());
			}
		}
		testQuestionsPool = new Question[numOfQuestions];
	}

	// methods

	public boolean validQuestion(Question q) {
		int correctAnswers = 0;
		if(q instanceof OpenQuestion)
			return true;
		
		ClosedQuestion cq = (ClosedQuestion) q;
		QuestionAnswer[] answers = cq.getAnswers();

		if (cq.getNumOfAnswers() < 4) // check if there are less than 4 answers
			return false;

		for (int i = 0; i < cq.getNumOfAnswers(); i++) { // check correct answers' number
			if (answers[i].getIsTrue())
				correctAnswers++;
		}
		if (correctAnswers <= 1)
			return true;
		return false;
	}

	public void createValidQuestionsPool(DataPool data) {
		int numOfQuestions = data.getNumOfQuestions();
		for (int i = 0; i < numOfQuestions; i++) {
			if (validQuestion(data.getQuestionsPool()[i]))
				validQuestionsPool[numOfValidQuestions++] = data.getQuestionsPool()[i];
		}
	}

	@Override
	public void createExam(DataPool questionPool) throws Exception {
		Random rand = new Random();
		int range = numOfValidQuestions;
		boolean[] usedNumArray = new boolean[range]; // check if we used a question

		int j = 0;
		while (j < numOfQuestions) {
			int index = rand.nextInt(range);
			if (usedNumArray[index] == false) {
				usedNumArray[index] = true;
				testQuestionsPool[j++] = validQuestionsPool[index];
			}
		}

	}

	// this method creates a text file of the test with testQuestionsPool's data
	public static void createTestFile(Question[] testQuestionsPool, int numOfQuestions) throws FileNotFoundException {
		Date date = new Date();
		String pattern = "yyyy_MM_dd_hh_mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String fileName = "AutomaticExam_" + simpleDateFormat.format(date) + ".txt";
		File f = new File(fileName);
		PrintWriter pw = new PrintWriter(f);
		for (int i = 0; i < numOfQuestions; i++) {
			pw.println();
			pw.write(testQuestionsPool[i].getQuestion()); // add a question to the text file
			pw.write(" Question ID: " + testQuestionsPool[i].getID()); // add question's id

			pw.println();

			if (testQuestionsPool[i] instanceof OpenQuestion) {
				String answer = ((OpenQuestion) testQuestionsPool[i]).getAnswer();
				pw.write("_______________________________");
				pw.println();
			} else if (testQuestionsPool[i] instanceof ClosedQuestion) {
				QuestionAnswer[] currAnswers = ((ClosedQuestion) testQuestionsPool[i]).getAnswers();
				for (int j = 0; j < ((ClosedQuestion) testQuestionsPool[i]).getNumOfAnswers(); j++) {
					pw.write(currAnswers[j].getAnswer()); // add an answer to the text file
					pw.println();
				}
				pw.write("There is no correct answer \n");
			}

		}

		pw.close();
	}

	// this method creates a text file of the solution of the text
	public static void createSolutionFile(Question[] testQuestionsPool, int numOfQuestions) throws FileNotFoundException {
		int correctAnswersCounter; // counter for checking how many correct answer there is in a question
		Date date = new Date();
		String pattern = "yyyy_MM_dd_hh_mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String fileName = "AutomaticSolution_" + simpleDateFormat.format(date) + ".txt";
		File f = new File(fileName);
		PrintWriter pw = new PrintWriter(f);
		for (int i = 0; i < numOfQuestions; i++) {
			correctAnswersCounter = 0;
			pw.println();
			pw.write(testQuestionsPool[i].getQuestion()); // add a question to the text file
			pw.write(" Question ID: " + testQuestionsPool[i].getID()); // add question's id
			pw.println();

			if (testQuestionsPool[i] instanceof OpenQuestion) {
				String answer = ((OpenQuestion) testQuestionsPool[i]).getAnswer();
				pw.write(answer);
				pw.println();
			} else if (testQuestionsPool[i] instanceof ClosedQuestion) {
				QuestionAnswer[] currAnswers = ((ClosedQuestion) testQuestionsPool[i]).getAnswers();
				for (int j = 0; j < ((ClosedQuestion) testQuestionsPool[i]).getNumOfAnswers(); j++) {
					pw.write(currAnswers[j].getAnswer() + " (" + currAnswers[j].getIsTrue() + ") \n"); // add an answer to the next file with the answer's correctness																
					if (currAnswers[j].getIsTrue()) // check if the current answer is true
						correctAnswersCounter++;
				}	
				if (correctAnswersCounter == 0) { // if there is no correct answer
					pw.write("There is no correct answer (true)\n");
				} 
				else {
					pw.write("There is no correct answer (false)\n");
				}
			}
		}

		pw.close();

	} 

}
