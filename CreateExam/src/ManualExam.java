import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.InputMismatchException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Calendar;

public class ManualExam implements Examable{

	public static Scanner s = new Scanner(System.in);
	public static DataPool data = new DataPool();
	public static Program pro = new Program();
	public static final int MAX_QUESTION = 10;
	private static Question[] testQuestionsPool;
	public static int numOfQuestions;

	// c'tor
	public ManualExam(DataPool data) throws Exception {
		boolean isValid = false;
		while(!isValid) {
			try {
			System.out.printf("How many questions do you like to have on the test? (1-%d)\n", data.getNumOfQuestions());
			numOfQuestions = s.nextInt();
			if(numOfQuestions > MAX_QUESTION)
				throw new TooManyQuestionsException(MAX_QUESTION);
			else if(numOfQuestions < 1)
				throw new Exception("Error! You must choose at least one question");
			else if(numOfQuestions > data.getNumOfQuestions())
				throw new Exception("Error! There are only " + data.getNumOfAnswers() + " question in the data base");
			else
				isValid = true;
			}
			catch (InputMismatchException e) {
				s.nextLine(); // buffer
				System.out.println("Error! This is not a number");
			}
			catch (Exception e) {
				s.nextLine(); // buffer
				System.out.println(e.getMessage());
			}
		}
		testQuestionsPool = new Question[numOfQuestions];
	}
	
	// methods

	// this method add to testQuestionsPool[] the question we want in the test, and
	// their answers
	@Override
	public void createExam(DataPool data) throws Exception {
		boolean[] usedNumArray = new boolean[data.getNumOfQuestions()]; // check if we used a question
		int question;
		Question[] dbQuestionPool = data.getQuestionsPool(); // dbQuestionPool contains all the question from DataBase
		for (int i = 0; i < numOfQuestions; i++) {
			System.out.printf("Question %d/%d: Choose the question's number you want to add: (1-%d)\n", i+1,numOfQuestions, data.getNumOfQuestions());
			pro.showAllQuestions(data); // print all questions in DataBase

			question = s.nextInt();
			
			
			boolean isValid = false;
			while(!isValid) {
				try {
					if(dbQuestionPool[question - 1] instanceof ClosedQuestion) {
						if(((ClosedQuestion) dbQuestionPool[question - 1]).getNumOfAnswers() <= 3) 
							throw new NotEnoughAnswersException();
						else
							isValid = true;
					}
					else
						isValid = true;
					}
				catch(NotEnoughAnswersException e) {
					System.out.println(e.getMessage());
					System.out.printf("Question %d/%d: Choose the question's number you want to add: (1-%d)\n", i+1,numOfQuestions, data.getNumOfQuestions());
					pro.showAllQuestions(data); // print all questions in DataBase

					question = s.nextInt();
				}
				
				if(question > data.getNumOfQuestions() || question < 1) {
					System.out.printf("Invalid input. Question %d/%d: Choose the question's number you want to add: (1-%d)\n", i+1,numOfQuestions,
							data.getNumOfQuestions());
					question = s.nextInt();
				}
				
				if(usedNumArray[question-1] == false) // check if we the question was already added
					usedNumArray[question-1] = true;
				else {
					while(usedNumArray[question-1]) { // run until the user select new question
						System.out.println("This question already added to the test");
						System.out.printf("Question %d/%d: Choose the question's number you want to add: (1-%d)\n", i+1,numOfQuestions, data.getNumOfQuestions());
						pro.showAllQuestions(data);
						question = s.nextInt();
					}
					usedNumArray[question-1] = true; 
				}
				
			}	

			String questionString = dbQuestionPool[question - 1].getQuestion();
			if (dbQuestionPool[question - 1] instanceof OpenQuestion) {
				String answer = ((OpenQuestion) dbQuestionPool[question - 1]).getAnswer();
				OpenQuestion oq = new OpenQuestion(questionString, answer);
				testQuestionsPool[i] = oq; // add Question to test's array
			} else if (dbQuestionPool[question - 1] instanceof ClosedQuestion) {
				ClosedQuestion cq = new ClosedQuestion(questionString);
				testQuestionsPool[i] = cq; // add Question to test's array
				System.out.printf("How many answers do you want? (1-%d) \n",
						((ClosedQuestion) dbQuestionPool[question - 1]).getNumOfAnswers());
				int numOfAnswers = s.nextInt();
				while (numOfAnswers > ((ClosedQuestion) dbQuestionPool[question - 1]).getNumOfAnswers()
						|| numOfAnswers < 1) {
					System.out.printf("Invalid input. How many answers do you want? (1-%d)\n",
							((ClosedQuestion) dbQuestionPool[question - 1]).getNumOfAnswers());
					numOfAnswers = s.nextInt();
				}
				for (int j = 0; j < numOfAnswers; j++) {
					System.out.printf("Answer %d/%d: Choose the answer's number you want to add: \n", j+1, numOfAnswers);
					((ClosedQuestion) dbQuestionPool[question - 1]).printAnswers(); // print all question's answers
					int answer = s.nextInt();
					while (answer > ((ClosedQuestion) dbQuestionPool[question - 1]).getNumOfAnswers() || answer < 1) {
						System.out.printf("Invalid input. Answer %d/%d: Choose the answer's number you want to add: (1-%d)\n",
								j+1, numOfAnswers, ((ClosedQuestion) dbQuestionPool[question - 1]).getNumOfAnswers());
						answer = s.nextInt();
					}
					QuestionAnswer[] currAnswers = ((ClosedQuestion) dbQuestionPool[question - 1]).getAnswers(); // array
																													// of
																													// current
																													// question's
																													// answers
					((ClosedQuestion) testQuestionsPool[i]).addAnswer(currAnswers[answer - 1]); // add the answer the
																								// user choose to the
																								// question
				}
			}

		}
	}
	

	// this method creates a text file of the test with testQuestionsPool's data
	public static void createTestFile() throws FileNotFoundException {
		Date date = new Date();
		String pattern = "yyyy_MM_dd_hh_mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String fileName = "ManualExam_" + simpleDateFormat.format(date) + ".txt";
		File f = new File(fileName);
		PrintWriter pw = new PrintWriter(f);
		for (int i = 0; i < numOfQuestions; i++) {
			pw.println();
			pw.write(testQuestionsPool[i].getQuestion()); // add a question to the text file
			pw.write(" Question ID: " + testQuestionsPool[i].getID());
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
				pw.write("More than one correct answer \n");
				pw.write("There is no correct answer \n");
			}

		}

		pw.close();
	}

	// this method creates a text file of the solution of the text
	public static void createSolutionFile() throws FileNotFoundException {
		int correctAnswersCounter; // counter for checking how many correct answer there is in a question
		Date date = new Date();
		String pattern = "yyyy_MM_dd_hh_mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String fileName = "ManualSolution_" + simpleDateFormat.format(date) + ".txt";
		File f = new File(fileName);
		PrintWriter pw = new PrintWriter(f);
		for (int i = 0; i < numOfQuestions; i++) {
			correctAnswersCounter = 0;
			pw.println();
			pw.write(testQuestionsPool[i].getQuestion()); // add a question to the text file
			pw.write(" Question ID: "+testQuestionsPool[i].getID());
			pw.println();

			if (testQuestionsPool[i] instanceof OpenQuestion) {
				String answer = ((OpenQuestion) testQuestionsPool[i]).getAnswer();
				pw.write(answer);
				pw.println();
			} else if (testQuestionsPool[i] instanceof ClosedQuestion) {
				QuestionAnswer[] currAnswers = ((ClosedQuestion) testQuestionsPool[i]).getAnswers();
				for (int j = 0; j < ((ClosedQuestion) testQuestionsPool[i]).getNumOfAnswers(); j++) {
					pw.write(currAnswers[j].getAnswer() + " (" + currAnswers[j].getIsTrue() + ") \n"); // add an answer
																										// to the text
																										// file with the
																										// answer's
																										// correctness
					if (currAnswers[j].getIsTrue()) // check if the current answer is true
						correctAnswersCounter++;
				}
				if (correctAnswersCounter > 1) { // if there is more than 1 correct answer
					pw.write("More than one correct answer (true)\n");
					pw.write("There is no correct answer (false)\n");
				} else if (correctAnswersCounter == 0) { // if there is no correct answer
					pw.write("More than one correct answer (false)\n");
					pw.write("There is no correct answer (true)\n");
				} else {
					pw.write("More than one correct answer (false)\n");
					pw.write("There is no correct answer (false)\n");
				}
			}
		}
		
		pw.close();
	}

}
