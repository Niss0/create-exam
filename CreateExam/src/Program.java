import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Program {

	public static Scanner s = new Scanner(System.in);
	public static int choice;
	public static int anotherChoice;

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		SubjectsPool subjectsPool = new SubjectsPool();
		
//		subjectsPool.initializeDataPool(subjectsPool.getSubjecstPool());
//		subjectsPool.initializeAnswerPool(subjectsPool.getSubjecstPool());
//		subjectsPool.initializeQuestionPool(subjectsPool.getSubjecstPool());
		
		subjectsPool = readFromBinaryFile();
		
		int i = subjectsPool.selectSubject(subjectsPool.getNumOfSubjects()); // get the index of the subject 
		if(i == subjectsPool.getNumOfSubjects()) // if select "Add new Subject" we need to add 1 to subjects' counter
			subjectsPool.setNumOfSubjects(i+1);
		
		DataPool data = subjectsPool.getDataPool(i); // subject's DataPool
		
		
		try {
			menu(data, subjectsPool);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// methods

	// menu for the user
	public static void menu(DataPool data, SubjectsPool sp) throws Exception {
		boolean done = false;
		System.out.println("Welcome to Test creation program!");
		
//		subjectSelectionMenu();
//		DataPool currSubject = subjects[choice];
		
		
		while (!done) {
			System.out.println("\nWhat would you like to do? (1-9)");
			System.out.println("1) Displaying all the questions from DataBase and their answers");
			System.out.println("2) Add new answer to DataBase");
			System.out.println("3) Add new answer from DataBase to a question");
			System.out.println("4) Add new question to DataBase ");
			System.out.println("5) Delete an answer to a question from the DataBase");
			System.out.println("6) Delete a question from the DataBase ");
			System.out.println("7) Create Exam manually");
			System.out.println("8) Create Exam automaticlly");
			System.out.println("9) Exit");
			choice = s.nextInt();
			String flush = s.nextLine();

			switch (choice) {
			case 1:
				if (data.getNumOfQuestions() == 0)
					System.out.println("There are no question in DataBase");
				else
					showAllQuestionsAndAnswers(data);
				break;
			case 2:
				if (addAnswerToDB(data))
					System.out.println("The answer has been successfully added to the DataBase!");
				else
					System.out.println("The answer has not been added to the DataBase");
				break;

			case 3:
				addAnswerToQuestionFromDB(data);
				break;

			case 4:
				if (addQuestionToDB(data))
					System.out.println("The question has been successfully added to the DataBase!");
				else
					System.out.println("The question has not been added to the DataBase");
				break;

			case 5:
				deleteAnswerFromQuestion(data);
				break;

			case 6:
				deleteQuestionFromDB(data);
				System.out.println("The question has been successfuly deleted from DataBase!");
				break;
			case 7:
				ManualExam manualTest = new ManualExam(data);
				manualTest.createExam(data);
				manualTest.createTestFile();
				System.out.println("Test created successfully!");
				manualTest.createSolutionFile();
				System.out.println("Solution created successfully!");
				break;
			case 8:
				AutomaticExam autoTest = new AutomaticExam(data);
				autoTest.createExam(data);
				autoTest.createTestFile(data.getQuestionsPool(), autoTest.numOfQuestions);
				System.out.println("Test has been created successfully!");
				autoTest.createSolutionFile(data.getQuestionsPool(), autoTest.numOfQuestions);
				System.out.println("Solution has been created successfully!");

				break;
			case 9:
				writeToBinaryFile(sp);
				done = true;
				System.out.println("Bye :)");
				break;

			default:
				System.out.println("Invalid input. Choose number between 1-8.");
			}
		}
	}

	// this method add a question to DataBase
	public static boolean addQuestionToDB(DataPool data) {
		System.out.println("Choose the type of question you want: (1/2) \n 1) Closed question \n 2) Open question");
		choice = s.nextInt();
		boolean valid = false;
		while (!valid) {
			System.out.println("Enter the question: ");
			s.nextLine(); // flush
			String question = s.nextLine();
			System.out.println("Choose Question's difficulty: (1-3) ");
			Question.eDifficulty[] allDifficulties = Question.eDifficulty.values();
			for(int i = 0; i < allDifficulties.length; i++) {
				System.out.println((allDifficulties[i].ordinal()+1) + " for " + allDifficulties[i].name());
			}
			anotherChoice = s.nextInt();
			s.nextLine(); // flush
			
			
			if (data.getNumOfQuestions() < data.POOL_SIZE) {
				switch (choice) {
				case 1:
					ClosedQuestion cq = new ClosedQuestion(question, anotherChoice); // create closed question
					data.getQuestionsPool()[data.getNumOfQuestions()] = cq;
					data.addNumOfQuestionsByOne();
					valid = true;
					break;
				case 2:
					System.out.println("Enter the answer: ");
					String answer = s.nextLine();
					OpenQuestion oq = new OpenQuestion(question, answer, anotherChoice); // create open question
					data.getQuestionsPool()[data.getNumOfQuestions()] = oq;
					data.addNumOfQuestionsByOne();
					valid = true;
					break;
				default:
					System.out.println("Invalid input. Choose number bewtween 1-2: ");
					anotherChoice = s.nextInt();
				}
			}

		}
		return valid;
	}

	// this method add an answer to DataBase
	public static boolean addAnswerToDB(DataPool data) {
		System.out.println("Enter the answer: ");
		String answer = s.nextLine();
		Answer ans = new Answer(answer); // create answer
		if (data.getNumOfAnswers() < data.POOL_SIZE) { // check if valid
			data.getAnswersPool()[data.getNumOfAnswers()] = ans;
			data.addNumOfAnswersByOne();
			return true;
		}
		return false;
	}

	// this method print all the questions from DataBase
	public static void showAllQuestions(DataPool data) {
		for (int i = 0; i < data.getNumOfQuestions(); i++) {
			System.out.printf("%d) %s \n", i + 1, data.getQuestionsPool()[i].getQuestion()); // print the question
		}
		System.out.println();

	}

	// this method print all the questions and their answers
	public static void showAllQuestionsAndAnswers(DataPool data) {
		for (int i = 0; i < data.getNumOfQuestions(); i++) {
			System.out.printf("%d) %s ", i + 1, data.getQuestionsPool()[i].getQuestion()); // print the question
//			System.out.print("ID: " + data.getQuestionsPool()[i].getID() + " ");
			System.out.println(data.getQuestionsPool()[i]); // print toString of question ---> the difficulty
			if (data.getQuestionsPool()[i] instanceof ClosedQuestion)
				((ClosedQuestion) data.getQuestionsPool()[i]).printAnswers(); // print all question's answers
			else if (data.getQuestionsPool()[i] instanceof OpenQuestion)
				System.out.println(((OpenQuestion) data.getQuestionsPool()[i]).getAnswer() + "\n");
		}
		System.out.println();

	}

	// this method print all answers from DataBase
	public static void showAllAnswers(DataPool data) {
		for (int i = 0; i < data.getNumOfAnswers(); i++) {
			System.out.printf("   %d) %s \n", i + 1, data.getAnswersPool()[i].getAnswer());
		}

	}

	// this method add an answer to a question in DataBase
	public static void addAnswerToQuestionFromDB(DataPool data) {
		if (data.getNumOfQuestions() == 0)
			System.out.println("There is no qustions in DataBase");
		else {
			System.out.printf("Choose the question's number you want to add to it: (1-%d)\n", data.getNumOfQuestions());
			showAllQuestions(data);
			choice = s.nextInt();
			while (choice < 1 || choice > data.getNumOfQuestions()) { // while loop that will run if we get invalid input
				System.out.printf("Invalid input! \nChoose the question's number you want to add to it: (1-%d)\n",
						data.getNumOfQuestions());
				showAllQuestions(data);
				choice = s.nextInt();
			}
			if (data.getQuestionsPool()[choice - 1] instanceof OpenQuestion) {
				System.out.println("This is an open question, there is only one answer.");
			} else if (data.getQuestionsPool()[choice - 1] instanceof ClosedQuestion) {
				if (data.getNumOfAnswers() == 0)
					System.out.println("There are no answers in DataBase");
				else {
					System.out.printf("Choose the answer's number you want to add: (1-%d) \n", data.getNumOfAnswers());
					showAllAnswers(data);
					int answerIndex = s.nextInt();
					while (answerIndex < 1 || answerIndex > data.getNumOfAnswers()) { // while loop that will run if we get
																			// invalid
																			// input
						System.out.printf("Invalid input! \nChoose the answer's number you want to add: (1-%d) \n",
								data.getNumOfAnswers());
						answerIndex = s.nextInt();
					}
					System.out.println("Is this answer correct? (true/false)");
					String correct = s.next();
					while (!(correct.equals("true") || correct.equals("false"))) { // while loop that will run if we get
																					// invalid input
						System.out.println("Invalid input. Is this answer correct? (true/false)");
						correct = s.next();
					}
					boolean isTrue = false;
					if (correct.equals("true")) // check if the string is equal to true, is so change isTrue.
						isTrue = true;
					QuestionAnswer currAnswer = new QuestionAnswer(data.getAnswersPool()[answerIndex - 1].getAnswer(), isTrue);
					((ClosedQuestion) data.getQuestionsPool()[choice - 1]).addAnswer(currAnswer);
					System.out.println("The answer has been successfully added to the question");
				}
			}
		}
	}

	// replace the question we want to delete, with the last question in DB, reduce
	// numOfQuestion by 1
	public static void deleteQuestionFromDB(DataPool data) {
		if (data.getNumOfQuestions() == 0) {
			System.out.println("There is no qustions in DataBase");
		} else {
			System.out.printf("Choose the question's number you want to delete: (1-%d)\n", data.getNumOfQuestions());
			showAllQuestions(data);
			choice = s.nextInt();
			while (choice > data.getNumOfQuestions() || choice < 1) { // while loop that will run if we get invalid input
				System.out.printf("Invalid input! \nChoose the question's number you want to delete: (1-%d)\n",
						data.getNumOfQuestions());
				showAllQuestions(data);
				choice = s.nextInt();
			}
			// swap between the last question with the question we want to delete
			Question temp = data.getQuestionsPool()[data.getNumOfQuestions() - 1];
			data.getQuestionsPool()[choice - 1] = temp;
			data.getQuestionsPool()[data.getNumOfQuestions() - 1] = null;
			data.subNumOfQuestionsByOne();
		}
	}

	// replace the answer we want to delete (from specific question), with the last
	// answer in DB, reduce numOfAnswer by 1
	public static void deleteAnswerFromQuestion(DataPool data) {
		System.out.printf("Choose the question's number you want to delete from: (1-%d)\n", data.getNumOfQuestions());
		showAllQuestions(data);
		choice = s.nextInt();
		s.nextLine(); // flush 
		while (choice > data.getNumOfQuestions() || choice < 1) { // while loop that will run if we get invalid input
			System.out.printf("Invalid input!\nChoose the question's number you want to delete: (1-%d)\n",
					data.getNumOfQuestions());
			showAllQuestions(data);
			choice = s.nextInt();
		}
		if (data.getQuestionsPool()[choice - 1] instanceof OpenQuestion) {
			OpenQuestion currQuestion = (OpenQuestion) data.getQuestionsPool()[choice - 1];
			System.out.println("Please enter new answer:");
			currQuestion.setAnswer(s.nextLine());
		} else if (data.getQuestionsPool()[choice - 1] instanceof ClosedQuestion) {
			ClosedQuestion currQuestion = (ClosedQuestion) data.getQuestionsPool()[choice - 1];
			if (currQuestion.getNumOfAnswers() == 0)
				System.out.println("There are no answers");
			else {
				System.out.printf("Choose the answer's number you want to delete (1-%d) \n",
						currQuestion.getNumOfAnswers());
				currQuestion.printAnswers();
				anotherChoice = s.nextInt();
				while (anotherChoice > currQuestion.getNumOfAnswers() || anotherChoice < 1) { // while loop
																								// that will run
																								// if we get
																								// invalid input
					System.out.printf("Invalid input! \nChoose the answer's number you want to delete:(1-%d)\n",
							currQuestion.getNumOfAnswers());
					currQuestion.printAnswers();
					anotherChoice = s.nextInt();
				}
				currQuestion.deleteAnswerFromQuestionHelper(anotherChoice - 1); // method from Question class
																				// that get an index and
																				// delete the answer in that
																				// index from question's
																				// array
				System.out.println("The answer has been successfully deleted from the question");
			}
		}

	}
	
	// this method write to a binary file
	public static void writeToBinaryFile(SubjectsPool data) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("dataPool.bin"));
		writer.writeObject(data);
		writer.close();
		System.out.println("DataPool successfully written to binary file.");

	}
	
	// this method read a binary file and save it in object data
	public static SubjectsPool readFromBinaryFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		  try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream("dataPool.bin"))) {
			  SubjectsPool data = (SubjectsPool) reader.readObject();
	            reader.close();
	            System.out.println("DataPool successfully read from binary file.");
	            return data;
	        } catch (FileNotFoundException e) {
	            System.err.println("The binary file was not found: " + e.getMessage());
	        } catch (IOException e) {
	            System.err.println("An error occurred while reading from the binary file: " + e.getMessage());
	        } catch (ClassNotFoundException e) {
	            System.err.println("The class of the serialized object could not be found: " + e.getMessage());
	        }
		return null;
		
	}

}
