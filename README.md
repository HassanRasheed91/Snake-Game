# Snake-Game
This project implements a classic Snake Game in Java using the Swing library. The game allows players to select a difficulty level and challenges them to grow the snake by consuming food while avoiding obstacles and self-collision.

Features
Difficulty Levels: Players can choose between Easy, Medium, and Hard difficulty levels, which adjust the speed of the snake.
Score Tracking: The game tracks the current score, and a high score is saved locally.
Obstacles: Random obstacles are generated on the board to make the game more challenging.
Game Controls: Players can control the snake using arrow keys, pause/resume the game, and restart after game over.
Graphical Interface: The game runs in a resizable JFrame, providing a user-friendly interface.

How to Run
Requirements: Java 8 or higher.
Steps:
Clone the repository.
Navigate to the directory containing the .java files.
Compile the files using javac Game.java SnakeGame.java.
Run the game using java Game.

Game Controls
Arrow Keys: Use the arrow keys (Up, Down, Left, Right) to control the movement of the snake.
Pause/Resume: Press P to pause or resume the game.
Restart: When the game is over, press Space to restart.
Files in the Project

Game.java:
Initializes the game by creating the game window (JFrame).
Allows the player to select a difficulty level (Easy, Medium, or Hard) and sets the game speed accordingly.
Starts the game by embedding the SnakeGame panel into the JFrame.

SnakeGame.java:
Implements the core game logic, including snake movement, food generation, obstacle handling, and game-over conditions.
Uses the Swing JPanel to render the game graphics and handle keyboard inputs.
Supports loading and saving the high score to a local file.
Difficulty Levels

The game offers three difficulty levels:
Easy: Slow snake speed (150ms delay between movements).
Medium: Moderate snake speed (100ms delay).
Hard: Fast snake speed (50ms delay).

How the Game Works
The snake starts with a single head and grows by eating food that appears randomly on the board.
Each time the snake eats food, its length increases by one tile, and the score increases.
If the snake hits the wall, collides with itself, or encounters an obstacle, the game ends.
The game saves the high score between sessions in a local file.

Future Enhancements
Add more difficulty levels or game modes (e.g., timed mode, survival mode).
Implement multiplayer mode to allow two snakes on the same board.
Improve graphics for a more modern game experience.
