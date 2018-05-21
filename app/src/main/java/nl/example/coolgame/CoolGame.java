package nl.example.coolgame;

import android.graphics.Point;

import nl.example.coolgame.MazeGenerator.DepthFirst;
import nl.example.coolgame.MazeGenerator.Kruskal;
import nl.example.coolgame.MazeGenerator.Prim;
import nl.example.coolgame.MazeGenerator.RecursiveDivision;
import nl.example.coolgame.mazeSolver.BreadthFirst;
import nl.saxion.act.playground.model.Game;
import nl.saxion.act.playground.model.GameBoard;
import nl.example.coolgame.objects.Leaf;
import nl.example.coolgame.objects.Rock;
import nl.example.coolgame.objects.Wombat;

/**
 * Awesome game for the Speelveld-project.
 * 
 * @author Paul de Groot
 */
public class CoolGame extends Game {
	/** Tag used for log messages */
	public static final String TAG = "CoolGame";

	/** Reference to the main activity, so some labels can be updated. */
	private MainActivity activity;
	
	/** The number of leafs eaten. */
	private int score;

	/**
	 * Constructor.
	 * 
	 * @param activity  The main activity
	 */
	public CoolGame(MainActivity activity) {
		// Create a new game board and couple it to this game
		super(new CoolGameBoard());
		
		// Store reference to the main activity
		// This is used to update the score label
		this.activity = activity;

		// Reset the game
		initNewGame();
	}

	/**
	 * Starts a new game.
	 * Resets the score and places all objects in the right place.
	 */
	public void initNewGame() {
		// Set the score and update the label
		score = 0;
		activity.updateScoreLabel(score);

		// Create a gameboard
		GameBoard board = getGameBoard();
		board.removeAllObjects();

		// Fill the board using an algorithm
		DepthFirst depthFirst = new DepthFirst(board);
		depthFirst.generateMaze();

//		Prim prim = new Prim(board);
//		prim.generateMaze();

//		Kruskal kruskal = new Kruskal(board);
//		kruskal.generateMaze();

//		RecursiveDivision recursiveDivision = new RecursiveDivision(board);
//		recursiveDivision.generateMaze();

		// Set the starting tile
		//TODO: set the starting tile

		// Set the end tile
		BreadthFirst breadthFirst = new BreadthFirst(board);
		Point endTilePoint = breadthFirst.getLargestValueWallPoint();
		//TODO: set the end tile

		// Redraw the game view
		board.updateView();
	}

	/**
	 * Called by Wombat if it ate a leaf. Increases the score.
	 */
	public void increaseScore() {
		score++;
		activity.updateScoreLabel(score);
	}
}
