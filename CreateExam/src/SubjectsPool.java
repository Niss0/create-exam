import java.io.Serializable;
import java.util.Scanner;

public class SubjectsPool implements Serializable {

	// members
	public static final int POOL_SIZE = 20;
	private DataPool[] subjectsPool = new DataPool[POOL_SIZE];
	private Question[] questionsPool = new Question[POOL_SIZE];
	private Answer[] answersPool = new Answer[POOL_SIZE];
	private int numOfSubjects;

	// setters 
	public void setNumOfSubjects(int numOfSubjects) {
		this.numOfSubjects = numOfSubjects;
	}
	
	// getters
	public int getNumOfSubjects() {
		return numOfSubjects;
	}

	public DataPool[] getSubjecstPool() {
		return subjectsPool;
	}

	public DataPool getDataPool(int index) {
		return subjectsPool[index];
	}

	// methods
	// this method return a number, indicating which subject the user has chosen
	public int selectSubject(int numOfSubjects) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter subject's number you want: ");
		for (int i = 0; i < numOfSubjects; i++) { // print all the subjects
			System.out.printf("%d) %s", i + 1, subjectsPool[i].getSubject() + "\n");
		}
		System.out.println(numOfSubjects + 1 + ") Add new Subject"); 
		int num = s.nextInt();

		if (num == numOfSubjects + 1) { // if the user choose to add new subject
			addNewSubject(numOfSubjects);
			return numOfSubjects;
		}
		return num - 1;

	}

	// this method add new subject to our dataBase
	public void addNewSubject(int numOfSubjects) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the subject you want to add: ");
		String subject = s.nextLine();
		subjectsPool[numOfSubjects] = new DataPool(subject); 
	}
	
	// this method initialize the SubjectsPool to be DataPool
	public void initializeDataPool(DataPool[] data) {
		for(int i = 0; i < 3; i++) {
			data[i] = new DataPool();
		}
	}

	// this method initializes the questionPool with hard-coded questions and their
	// answers
	public void initializeQuestionPool(DataPool[] data) {
		// [History, Gaming, General Knowledge]

		// History's Questions
		data[0].getQuestionsPool()[0] = new OpenQuestion("Who was the first President of the United States?",
				"George Washington", 1);

		data[0].getQuestionsPool()[1] = new OpenQuestion("In what year was the state of Israel established?", "1948",
				1);
	
		data[0].getQuestionsPool()[2] = new OpenQuestion(
				"Who was the first female Prime Minister of the United Kingdom?", "Margaret Thatcher", 3);

		data[0].getQuestionsPool()[3] = new ClosedQuestion("When did World War II begin?", 2);
		((ClosedQuestion) data[0].getQuestionsPool()[3])
				.addAnswer(new QuestionAnswer(data[0].getAnswersPool()[3].getAnswer(), true));
		((ClosedQuestion) data[0].getQuestionsPool()[3])
				.addAnswer(new QuestionAnswer(data[0].getAnswersPool()[4].getAnswer(), false));
		((ClosedQuestion) data[0].getQuestionsPool()[3])
				.addAnswer(new QuestionAnswer(data[0].getAnswersPool()[5].getAnswer(), false));
		((ClosedQuestion) data[0].getQuestionsPool()[3])
				.addAnswer(new QuestionAnswer(data[0].getAnswersPool()[6].getAnswer(), false));

		data[0].getQuestionsPool()[4] = new ClosedQuestion("Who wrote the Declaration of Independence?", 2);
		((ClosedQuestion) data[0].getQuestionsPool()[4])
				.addAnswer(new QuestionAnswer(data[0].getAnswersPool()[7].getAnswer(), true));
		((ClosedQuestion) data[0].getQuestionsPool()[4])
				.addAnswer(new QuestionAnswer(data[0].getAnswersPool()[8].getAnswer(), false));
		((ClosedQuestion) data[0].getQuestionsPool()[4])
				.addAnswer(new QuestionAnswer(data[0].getAnswersPool()[9].getAnswer(), false));
		((ClosedQuestion) data[0].getQuestionsPool()[4])
				.addAnswer(new QuestionAnswer(data[0].getAnswersPool()[10].getAnswer(), false));

		data[0].setNumOfQuestions(5);
		data[0].setNumOfAnswers(11);
		data[0].setSubject("History");

		// Gaming's Questions

		data[1].getQuestionsPool()[0] = new ClosedQuestion("Who is the best champion in league of legends?", 3);
		((ClosedQuestion) data[1].getQuestionsPool()[0])
				.addAnswer(new QuestionAnswer(data[1].getAnswersPool()[0].getAnswer(), true));
		((ClosedQuestion) data[1].getQuestionsPool()[0])
				.addAnswer(new QuestionAnswer(data[1].getAnswersPool()[1].getAnswer(), false));
		((ClosedQuestion) data[1].getQuestionsPool()[0])
				.addAnswer(new QuestionAnswer(data[1].getAnswersPool()[2].getAnswer(), false));
		((ClosedQuestion) data[1].getQuestionsPool()[0])
				.addAnswer(new QuestionAnswer(data[1].getAnswersPool()[3].getAnswer(), true));
		((ClosedQuestion) data[1].getQuestionsPool()[0])
				.addAnswer(new QuestionAnswer(data[1].getAnswersPool()[4].getAnswer(), false));

		data[1].getQuestionsPool()[1] = new OpenQuestion("Who is the best Gwen player in the world?", "Niss0", 3);

		data[1].getQuestionsPool()[2] = new OpenQuestion("Who is the mid laner of T1?", "Faker", 1);

		data[1].getQuestionsPool()[3] = new ClosedQuestion("Which game is the best?", 1);
		((ClosedQuestion) data[1].getQuestionsPool()[3])
				.addAnswer(new QuestionAnswer(data[1].getAnswersPool()[7].getAnswer(), false));
		((ClosedQuestion) data[1].getQuestionsPool()[3])
				.addAnswer(new QuestionAnswer(data[1].getAnswersPool()[8].getAnswer(), false));
		((ClosedQuestion) data[1].getQuestionsPool()[3])
				.addAnswer(new QuestionAnswer(data[1].getAnswersPool()[9].getAnswer(), true));
		((ClosedQuestion) data[1].getQuestionsPool()[3])
				.addAnswer(new QuestionAnswer(data[1].getAnswersPool()[10].getAnswer(), false));

		data[1].setNumOfQuestions(4);
		data[1].setNumOfAnswers(11);
		data[1].setSubject("Gaming");

		// General Knowledge

		data[2].getQuestionsPool()[0] = new ClosedQuestion("What is the largest country in the world?", 2);
		((ClosedQuestion) data[2].getQuestionsPool()[0])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[0].getAnswer(), true));
		((ClosedQuestion) data[2].getQuestionsPool()[0])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[1].getAnswer(), false));
		((ClosedQuestion) data[2].getQuestionsPool()[0])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[2].getAnswer(), false));

		data[2].getQuestionsPool()[1] = new ClosedQuestion("Who is the best soccer player in the world?", 1);
		((ClosedQuestion) data[2].getQuestionsPool()[1])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[3].getAnswer(), false));
		((ClosedQuestion) data[2].getQuestionsPool()[1])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[4].getAnswer(), true));
		((ClosedQuestion) data[2].getQuestionsPool()[1])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[5].getAnswer(), false));
		((ClosedQuestion) data[2].getQuestionsPool()[1])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[6].getAnswer(), false));

		data[2].getQuestionsPool()[2] = new ClosedQuestion("Which animal is considered the king of the jungle?", 1);
		((ClosedQuestion) data[2].getQuestionsPool()[2])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[7].getAnswer(), false));
		((ClosedQuestion) data[2].getQuestionsPool()[2])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[8].getAnswer(), true));
		((ClosedQuestion) data[2].getQuestionsPool()[2])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[9].getAnswer(), false));
		((ClosedQuestion) data[2].getQuestionsPool()[2])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[10].getAnswer(), false));
		((ClosedQuestion) data[2].getQuestionsPool()[2])
				.addAnswer(new QuestionAnswer(data[2].getAnswersPool()[11].getAnswer(), false));

		data[2].getQuestionsPool()[3] = new OpenQuestion("Which city is known as the Big Apple?", "NYC", 2);

		data[2].setNumOfQuestions(4);
		data[2].setNumOfAnswers(13);
		data[2].setSubject("General Knowledge");
		
		numOfSubjects = 3;
	}
	

	// this method initializes the AnswersPool with hard-coded answers
	public void initializeAnswerPool(DataPool[] data) {
		data[0].getAnswersPool()[0] = new Answer("George Washington");
		data[0].getAnswersPool()[1] = new Answer("1948");
		data[0].getAnswersPool()[2] = new Answer("Margaret Thatcher");
		data[0].getAnswersPool()[3] = new Answer("1939");
		data[0].getAnswersPool()[4] = new Answer("1940");
		data[0].getAnswersPool()[5] = new Answer("1941");
		data[0].getAnswersPool()[6] = new Answer("1942");
		data[0].getAnswersPool()[7] = new Answer("Thomas Jefferson");
		data[0].getAnswersPool()[8] = new Answer("Nir Geron");
		data[0].getAnswersPool()[9] = new Answer("Shahar Nissim");
		data[0].getAnswersPool()[10] = new Answer("Maya Zigdon");

		data[1].getAnswersPool()[0] = new Answer("Sion");
		data[1].getAnswersPool()[1] = new Answer("Sylas");
		data[1].getAnswersPool()[2] = new Answer("Draven");
		data[1].getAnswersPool()[3] = new Answer("Irelia");
		data[1].getAnswersPool()[4] = new Answer("Viego");
		data[1].getAnswersPool()[5] = new Answer("Niss0");
		data[1].getAnswersPool()[6] = new Answer("Faker");
		data[1].getAnswersPool()[7] = new Answer("Counter Strike");
		data[1].getAnswersPool()[8] = new Answer("Maple Story");
		data[1].getAnswersPool()[9] = new Answer("League Of Legends");
		data[1].getAnswersPool()[10] = new Answer("Fortnite");

		data[2].getAnswersPool()[0] = new Answer("Russia");
		data[2].getAnswersPool()[1] = new Answer("Israel");
		data[2].getAnswersPool()[2] = new Answer("Spain");
		data[2].getAnswersPool()[3] = new Answer("Neymar jr");
		data[2].getAnswersPool()[4] = new Answer("Lionel Messi");
		data[2].getAnswersPool()[5] = new Answer("Crisitano Ronaldo");
		data[2].getAnswersPool()[6] = new Answer("Kylian Mbappe");
		data[2].getAnswersPool()[7] = new Answer("Monkey");
		data[2].getAnswersPool()[8] = new Answer("Lion");
		data[2].getAnswersPool()[9] = new Answer("Dog");
		data[2].getAnswersPool()[10] = new Answer("Ant");
		data[2].getAnswersPool()[11] = new Answer("Elephant");
		data[2].getAnswersPool()[12] = new Answer("NYC");

	}

}
