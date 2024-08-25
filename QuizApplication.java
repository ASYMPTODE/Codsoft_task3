import java.util.*;

class Question {
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public boolean isCorrect(int userAnswer) {
        return userAnswer == correctOptionIndex;
    }
}

class Quizz {
    private List<Question> questions;
    private int score;
    private Map<Question, Boolean> results;

    public Quizz(List<Question> questions) {
        this.questions = questions;
        this.score = 0;
        this.results = new HashMap<>();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            int userAnswer = getUserAnswer(scanner);
            boolean isCorrect = question.isCorrect(userAnswer - 1);
            results.put(question, isCorrect);
            if (isCorrect) {
                score++;
            }
        }
        displayResults();
        scanner.close();
    }

    private int getUserAnswer(Scanner scanner) {
        long startTime = System.currentTimeMillis();
        long timeLimit = 10000; // 10 seconds for each question
        while (true) {
            System.out.print("Your answer (1-4): ");
            if (scanner.hasNextInt()) {
                int answer = scanner.nextInt();
                if (answer >= 1 && answer <= 4) {
                    return answer;
                }
            } else {
                scanner.next(); // Clear invalid input
            }

            if (System.currentTimeMillis() - startTime > timeLimit) {
                System.out.println("\nTime's up!");
                return -1;
            }
            System.out.println("Invalid input. Please select a number between 1 and 4.");
        }
    }

    private void displayResults() {
        System.out.println("\nQuiz Results:");
        for (Map.Entry<Question, Boolean> entry : results.entrySet()) {
            Question question = entry.getKey();
            boolean isCorrect = entry.getValue();
            System.out.println(question.getQuestionText());
            System.out.println("Your answer: " + (isCorrect ? "Correct" : "Incorrect"));
        }
        System.out.println("Your final score: " + score + "/" + questions.size());
    }
}

public class QuizApplication {
    public static void main(String[] args) {
        List<Question> questions = Arrays.asList(
                new Question("What is the capital of France?", Arrays.asList("Berlin", "Paris", "Madrid", "Rome"), 1),
                new Question("Who wrote 'Hamlet'?",
                        Arrays.asList("Charles Dickens", "J.K. Rowling", "William Shakespeare", "Mark Twain"), 2),
                new Question("What is the chemical symbol for water?", Arrays.asList("O2", "H2O", "CO2", "HO2"), 1),
                new Question("Which planet is known as the Red Planet?",
                        Arrays.asList("Earth", "Mars", "Jupiter", "Saturn"), 1),
                new Question("What is the largest ocean on Earth?",
                        Arrays.asList("Indian Ocean", "Atlantic Ocean", "Arctic Ocean", "Pacific Ocean"), 3));

        Quizz quizz = new Quizz(questions);
        quizz.start();
    }
}
