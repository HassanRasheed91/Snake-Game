import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
	private class Tile {
		int x, y;
		public Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	int boardWidth, boardHeight, tileSize = 25;
	Tile snakeHead;
	LinkedList<Tile> snakeBody;
	Tile food;
	Random random;
	int velocityX, velocityY, score = 0, highScore = 0;
	Timer gameLoop;
	int foodCount;
	boolean gameover = false, paused = false;
	boolean[][] obstacles;  // For storing obstacles
	String highScoreFile = "highscore.txt";  // File to store the high score
	JLabel pauseLabel;  // Pause label for the paused state

	public SnakeGame(int boardWidth, int boardHeight, int speed) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);

		snakeHead = new Tile(5, 5);
		snakeBody = new LinkedList<>();
		random = new Random();

		obstacles = new boolean[boardWidth / tileSize][boardHeight / tileSize];

		placeObstacles();  // Place random obstacles first
		placeFood();  // Place food after obstacles

		velocityX = 0;
		velocityY = 0;

		loadHighScore();  // Load high score from file

		// Initialize the pause label
		pauseLabel = new JLabel("Game Paused", SwingConstants.CENTER);
		pauseLabel.setForeground(Color.white);
		pauseLabel.setFont(new Font("Arial", Font.BOLD, 30));
		pauseLabel.setBounds(boardWidth / 2 - 100, boardHeight / 2 - 50, 200, 100);
		pauseLabel.setVisible(false);  // Initially hidden
		this.setLayout(null);
		this.add(pauseLabel);  // Add label to the panel

		gameLoop = new Timer(speed, this);
		gameLoop.start();
	}

	// Method to place obstacles
	public void placeObstacles() {
		for (int i = 0; i < 10; i++) {  // Place 10 obstacles
			int ox = random.nextInt(boardWidth / tileSize);
			int oy = random.nextInt(boardHeight / tileSize);
			if ((ox != snakeHead.x || oy != snakeHead.y) && !obstacles[ox][oy]) {
				obstacles[ox][oy] = true;  // Mark obstacle location
			}
		}
	}

	public void placeFood() {
		int fx, fy;
		do {
			fx = random.nextInt(boardWidth / tileSize);
			fy = random.nextInt(boardHeight / tileSize);
		} while (isOnSnake(fx, fy) || obstacles[fx][fy]);  // Ensure food is not placed on the snake or an obstacle
		food = new Tile(fx, fy);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		if (gameover) {
			drawGameOver(g);
		} else {
			// Draw snake head
			g.setColor(Color.green);
			g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

			// Draw snake body
			for (Tile part : snakeBody) {
				g.fillRect(part.x * tileSize, part.y * tileSize, tileSize, tileSize);
			}

			// Draw food
			if (isFood2()) {
				g.setColor(Color.blue);  // Enlarge food2 and change color
				g.fillRect(food.x * tileSize, food.y * tileSize, tileSize * 2, tileSize * 2);
			} else {
				g.setColor(Color.red);  // Normal food
				g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
			}

			// Draw obstacles
			g.setColor(Color.gray);
			for (int i = 0; i < obstacles.length; i++) {
				for (int j = 0; j < obstacles[i].length; j++) {
					if (obstacles[i][j]) {
						g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
					}
				}
			}

			// Score display
			g.setColor(Color.white);
			g.drawString("Score: " + score, 10, 10);
			g.drawString("High Score: " + highScore, 10, 25);

			// If paused, draw pause label
			if (paused) {
				pauseLabel.setVisible(true);  // Show the pause label
			} else {
				pauseLabel.setVisible(false);  // Hide the pause label
			}
		}
	}

	public void drawGameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 32));
		g.drawString("Game Over!", 180, 250);
		g.drawString("Press Space Bar to Restart!", 100, 310);
		g.setColor(Color.white);
		g.drawString("Score: " + score, 220, 130);
		g.drawString("High Score: " + highScore, 170, 190);
	}

	public boolean isFood2() {
		return foodCount % 5 == 0 && foodCount > 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!paused && !gameover) {
			move();  // Snake moves
			repaint();  // Redraw the game
		}
	}

	public void move() {
		if (checkFoodCollision()) {
			snakeBody.add(new Tile(food.x, food.y));  // Grow the snake
			placeFood();  // Place new food

			if (isFood2())
				score += 5;
			else
				score++;

			foodCount++;
		}

		// Move the snake's body
		for (int i = snakeBody.size() - 1; i >= 0; i--) {
			Tile snakePart = snakeBody.get(i);
			if (i == 0) {  // Move the first part of the body to the head's old position
				snakePart.x = snakeHead.x;
				snakePart.y = snakeHead.y;
			} else {  // Shift other parts forward
				Tile previous = snakeBody.get(i - 1);
				snakePart.x = previous.x;
				snakePart.y = previous.y;
			}
		}

		// Move the snake's head
		snakeHead.x += velocityX;
		snakeHead.y += velocityY;

		// Game over conditions
		if (snakeHead.x < 0 || snakeHead.x >= boardWidth / tileSize ||
				snakeHead.y < 0 || snakeHead.y >= boardHeight / tileSize ||
				isSnakeCollision() || isObstacleCollision()) {
			gameover = true;
			updateHighScore();  // Update the high score when game is over
		}
	}

	public boolean checkFoodCollision() {
		return snakeHead.x == food.x && snakeHead.y == food.y;
	}

	public boolean isSnakeCollision() {
		for (Tile part : snakeBody) {
			if (snakeHead.x == part.x && snakeHead.y == part.y) {
				return true;
			}
		}
		return false;
	}

	public boolean isObstacleCollision() {
		return obstacles[snakeHead.x][snakeHead.y];
	}

	public void updateHighScore() {
		if (score > highScore) {
			highScore = score;
			saveHighScore();  // Save the new high score to the file
		}
	}

	public void saveHighScore() {
		try (PrintWriter writer = new PrintWriter(new FileWriter(highScoreFile))) {
			writer.println(highScore);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadHighScore() {
		try (BufferedReader reader = new BufferedReader(new FileReader(highScoreFile))) {
			String line = reader.readLine();
			if (line != null) {
				highScore = Integer.parseInt(line);
			}
		} catch (IOException e) {
			System.out.println("High score file not found, starting with a high score of 0.");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameover && e.getKeyCode() == KeyEvent.VK_SPACE) {
			restart();
		}

		if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1 && !paused) {
			velocityX = 0;
			velocityY = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1 && !paused) {
			velocityX = 0;
			velocityY = 1;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1 && !paused) {
			velocityX = -1;
			velocityY = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1 && !paused) {
			velocityX = 1;
			velocityY = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_P) {  // Pause/Resume toggle
			paused = !paused;
			repaint();  // Trigger a repaint to show/hide the pause label
		}
	}

	public void restart() {
		snakeHead = new Tile(5, 5);
		snakeBody.clear();
		velocityX = 0;
		velocityY = 0;
		score = 0;
		foodCount = 0;
		placeObstacles();  // Re-generate obstacles
		placeFood();  // Place new food
		gameover = false;
		paused = false;  // Ensure the game is not paused after restart
	}

	public boolean isOnSnake(int x, int y) {
		if (snakeHead.x == x && snakeHead.y == y)
			return true;
		for (Tile part : snakeBody) {
			if (part.x == x && part.y == y)
				return true;
		}
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}


}
