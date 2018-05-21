package nl.example.coolgame;

import nl.saxion.act.playground.model.GameBoard;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The main activity.
 *
 * @author Paul de Groot
 * @author Jan Stroet
 */
public class MainActivity extends Activity implements View.OnTouchListener{
    private CoolGame game;
    private CoolGameBoardView gameView;
    private TextView scoreLabel;

    // This is for drawing
    private Paint paint;

    // Make a copy of the level
    private Bitmap[][][] boardCopy;

    // Keep track of when the player is allowed to draw
    boolean allowedToDraw = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Load main.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find some of the user interface elements
        gameView = (CoolGameBoardView) findViewById(R.id.game);
        scoreLabel = (TextView) findViewById(R.id.scoreTextView);

        // Create the game object. This contains all data and functionality
        // belonging to the game
        game = new CoolGame(this);

        // Tell the game board view which game board to show
        GameBoard board = game.getGameBoard();
        gameView.setGameBoard(board);
        gameView.setFixedGridSize(board.getWidth(), board.getHeight());

        // Do something when user clicks new game
        registerNewGameButton();

        // Tell user to start the game
        Toast.makeText(getApplicationContext(), "Lets start",
                Toast.LENGTH_SHORT).show();

        // Make this the onTouchListener for the game
        gameView.setOnTouchListener(this);

        // Create paint to draw line with
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);

    }

    /**
     * Set a new score on the score label
     *
     * @param newScore  The new score.
     */
    public void updateScoreLabel(int newScore) {
        scoreLabel.setText("Score: " + newScore);
    }

    /**
     * Returns the view on the game board.
     */
    public CoolGameBoardView getGameBoardView() {
        return gameView;
    }

    /**
     * Install a listener to the 'New game'-button so that it starts a new
     * game when clicked.
     */
    private void registerNewGameButton() {
        // Find the 'New Game'-button in the activity
        final Button button1 = (Button) findViewById(R.id.newGameButton);

        // Add a click listener to the button that calls initNewGame()
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newLevel();
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Point touchedPoint = gameView.getTouchedTilePosition(motionEvent);
                // Make a copy of the board for resetting purposes
                boardCopy = gameView.getGridCopy();
                // Check if we started drawing on the start tile
                if (touchedPoint.x == 0 && touchedPoint.y == 0){
                    // Set values of starting position
                    gameView.setInitialTouch(motionEvent);
                    // Make sure the player can draw
                    allowedToDraw = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (allowedToDraw){
                    if (!gameView.drawLine(motionEvent, paint)){
                        // Reset line when drawn over a wall
                        gameView.setGrid(boardCopy);
                        // Make sure the player cant draw anymore
                        allowedToDraw = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //TODO: Check if we let go on the ending tile, then we won
                // Reset line
                gameView.setGrid(boardCopy);
                // Make sure the player can only draw when the start tile is first touched
                allowedToDraw = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    private void newLevel(){
        game.initNewGame();
        boardCopy = gameView.getGridCopy();
    }

}

//TODO: solve the mazes with algorithm to determine difficulty
//TODO: place start and end nodes of maze
