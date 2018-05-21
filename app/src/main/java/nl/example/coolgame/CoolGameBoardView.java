package nl.example.coolgame;

import nl.example.coolgame.objects.Wall;
import nl.saxion.act.playground.model.GameObject;
import nl.saxion.act.playground.view.GameBoardView;
import nl.example.coolgame.objects.Leaf;
import nl.example.coolgame.objects.Rock;
import nl.example.coolgame.objects.Wombat;
import nl.saxion.act.playground.view.SpriteCache;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * A view on the CoolGame game board.
 * 
 * @author Jan Stroet
 * @author Paul de Groot
 */
public class CoolGameBoardView extends GameBoardView {
	private static final String TAG = "GameView";

	/**
	 * Constructor.
	 */
	public CoolGameBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	/**
	 * Constructor.
	 */
	public CoolGameBoardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	/**
	 * Loads all images that will be used for the game.
	 */
	private void initGameView() {
		Log.d(TAG, "Loading all images");

		spriteCache.setContext(this.getContext());

		// Load the 'empty' cell bitmap
		spriteCache.loadTile("empty", R.drawable.cell);

		// Load the images for the GameObjects
		spriteCache.loadTile(Leaf.LEAF_IMAGE, R.drawable.leaf);
		spriteCache.loadTile(Rock.ROCK_IMAGE, R.drawable.rock);
		spriteCache.loadTile(Rock.RED_ROCK_IMAGE, R.drawable.rock2);
		spriteCache.loadTile(Wombat.WOMBAT_IMAGE, R.drawable.wombat);

		spriteCache.loadTile(Wall.WALL_IMAGE, R.drawable.wallemptymiddle);
		spriteCache.loadTile(Wall.WALL_IMAGE_NONE, R.drawable.nowall);

		spriteCache.loadTile(Wall.WALL_IMAGE_UP, R.drawable.wallup);
		spriteCache.loadTile(Wall.WALL_IMAGE_DOWN, R.drawable.walldown);
		spriteCache.loadTile(Wall.WALL_IMAGE_LEFT, R.drawable.wallleft);
		spriteCache.loadTile(Wall.WALL_IMAGE_RIGHT, R.drawable.wallright);

		spriteCache.loadTile(Wall.WALL_IMAGE_RIGHT_DOWN, R.drawable.wallrightdown);
		spriteCache.loadTile(Wall.WALL_IMAGE_DOWN_LEFT, R.drawable.walldownleft);
		spriteCache.loadTile(Wall.WALL_IMAGE_LEFT_UP, R.drawable.wallleftup);
		spriteCache.loadTile(Wall.WALL_IMAGE_UP_RIGHT, R.drawable.wallupright);
		spriteCache.loadTile(Wall.WALL_IMAGE_UP_DOWN, R.drawable.wallupdown);
		spriteCache.loadTile(Wall.WALL_IMAGE_LEFT_RIGHT, R.drawable.wallleftright);

		spriteCache.loadTile(Wall.WALL_IMAGE_RIGHT_DOWN_LEFT, R.drawable.wallrightdownleft);
		spriteCache.loadTile(Wall.WALL_IMAGE_DOWN_LEFT_UP, R.drawable.walldownleftup);
		spriteCache.loadTile(Wall.WALL_IMAGE_LEFT_UP_RIGHT, R.drawable.wallleftupright);
		spriteCache.loadTile(Wall.WALL_IMAGE_UP_RIGHT_Down, R.drawable.walluprightdown);
	}

	// Return the spriteCache
	public SpriteCache getSpriteCache(){
		return spriteCache;
	}
}
