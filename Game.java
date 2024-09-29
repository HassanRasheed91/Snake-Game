import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game {

	public static void main(String[] args) {
		int boardWidth = 600;
		int boardHeight = boardWidth;

		// Create JFrame for the Snake Game
		JFrame frame = new JFrame("Snake Game");
		frame.setVisible(true);
		frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);  // Center the window on the screen
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Ask the player to choose a difficulty level: Easy, Medium, or Hard
		String[] options = {"Easy", "Medium", "Hard"};
		int difficulty = JOptionPane.showOptionDialog(
				frame, "Select Difficulty Level", "Snake Game",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, options, options[0]);

		// Set game speed based on the selected difficulty
		int speed = 150;  // Default speed for Easy
		if (difficulty == 1) speed = 100;  // Medium speed
		else if (difficulty == 2) speed = 50;  // Hard speed

		SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight, speed);
		frame.add(snakeGame);
		frame.pack();  // Adjusts the window size based on the components inside it
		snakeGame.requestFocus();  // Ensures the game receives keyboard input
	}
}
