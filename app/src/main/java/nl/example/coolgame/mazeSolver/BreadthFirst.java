package nl.example.coolgame.mazeSolver;

import android.graphics.Point;
import android.util.ArraySet;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import nl.example.coolgame.objects.Wall;
import nl.saxion.act.playground.model.GameBoard;

/**
 * Implementation of a breadth-first maze solving algorithm
 * This class is not able to give a route,
 * it just calculates the distance to each tile from the starting point
 *
 * @author Coen Neefjes
 */
public class BreadthFirst {

    private final int STARTING_X = 0;
    private final int STARTING_Y = 0;

    private GameBoard gameBoard;
    private Queue<Wall> queue;
    private Set<Wall> visited;
    private Map<Wall, Integer> values;

    public BreadthFirst(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        queue = new ArrayDeque<>();
        visited = new HashSet<>();
        values = new HashMap<>();
        setTileValues();
    }

    public void setTileValues(){
        // Keep track of current tile
        Wall currentTile;

        // Keep track of the value we need to give the tiles
        int value = 0;

        // Add this value to the values map
        values.put((Wall) gameBoard.getObject(STARTING_X, STARTING_Y), value);

        // Add starting point to the queue
        queue.add((Wall) gameBoard.getObject(STARTING_X, STARTING_Y));

        while (!queue.isEmpty()){
            // Increment the value we need to give the tiles
            value++;

            // Get current tile by polling the queue
            currentTile = queue.poll();
            int currentX = currentTile.getPositionX();
            int currentY = currentTile.getPositionY();

            // Add its available neighbours to the queue
            if (currentX-1 >= 0){ // Check if we can go left
                if (!currentTile.hasWall("left")){ // Check if there is no wall in the way
                    Wall neighbour = (Wall) gameBoard.getObject(currentX-1, currentY);
                    if (visited.contains(neighbour)){ // Check if we did not visit this tile before
                        queue.add(neighbour);
                        values.put(neighbour, value);
                    }
                }
            }
            if (currentX+1 < gameBoard.getWidth()){ // Check if we can go right
                if (!currentTile.hasWall("right")){ // Check if there is no wall in the way
                    Wall neighbour = (Wall) gameBoard.getObject(currentX+1, currentY);
                    if (visited.contains(neighbour)){ // Check if we did not visit this tile before
                        queue.add(neighbour);
                        values.put(neighbour, value);
                    }
                }
            }
            if (currentY-1 >= 0){ // Check if we can go up
                if (!currentTile.hasWall("up")){ // Check if there is no wall in the way
                    Wall neighbour = (Wall) gameBoard.getObject(currentX, currentY-1);
                    if (visited.contains(neighbour)){ // Check if we did not visit this tile before
                        queue.add(neighbour);
                        values.put(neighbour, value);
                    }
                }
            }
            if (currentY+1 >= 0){ // Check if we can go down
                if (!currentTile.hasWall("down")){ // Check if there is no wall in the way
                    Wall neighbour = (Wall) gameBoard.getObject(currentX, currentY+1);
                    if (visited.contains(neighbour)){ // Check if we did not visit this tile before
                        queue.add(neighbour);
                        values.put(neighbour, value);
                    }
                }
            }

            // Add the current tile to the visited set
            visited.add(currentTile);
        }
    }

    public Wall getLargestValueWall(){
        Wall largestValueWall = (Wall) gameBoard.getObject(STARTING_X, STARTING_Y);
        for (Wall wall: visited) {
            if (values.get(wall) > values.get(largestValueWall)){
                largestValueWall = wall;
            }
        }
        return largestValueWall;
    }

    public int getValueofTile(Wall wall){
        return values.get(wall);
    }

    public int getLargestValue(){
        return values.get(getLargestValueWall());
    }

    public Point getLargestValueWallPoint(){
        Wall largestValueWall = getLargestValueWall();
        int x = largestValueWall.getPositionX();
        int y = largestValueWall.getPositionY();
        return new Point(x,y);
    }
}
