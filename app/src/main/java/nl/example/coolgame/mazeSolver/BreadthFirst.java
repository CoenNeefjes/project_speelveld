package nl.example.coolgame.mazeSolver;

import android.util.ArraySet;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import nl.example.coolgame.objects.Wall;
import nl.saxion.act.playground.model.GameBoard;

public class BreadthFirst {

    private final int STARTING_X = 0;
    private final int STARTING_Y = 0;

    private GameBoard gameBoard;
    private Queue<Wall> queue;
    private Set<Wall> visited;

    public BreadthFirst(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        queue = new ArrayDeque<>();
        visited = new HashSet<>();
    }

    public void getShortestPath(){
        // Keep track of current tile
        Wall currentTile;

        // Add starting point to the queue
        queue.add((Wall) gameBoard.getObject(STARTING_X, STARTING_Y));

        while (!queue.isEmpty()){
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
                    }
                }
            }
            if (currentX+1 < gameBoard.getWidth()){ // Check if we can go right
                if (!currentTile.hasWall("right")){ // Check if there is no wall in the way
                    Wall neighbour = (Wall) gameBoard.getObject(currentX+1, currentY);
                    if (visited.contains(neighbour)){ // Check if we did not visit this tile before
                        queue.add(neighbour);
                    }
                }
            }
            if (currentY-1 >= 0){ // Check if we can go up
                if (!currentTile.hasWall("up")){ // Check if there is no wall in the way
                    Wall neighbour = (Wall) gameBoard.getObject(currentX, currentY-1);
                    if (visited.contains(neighbour)){ // Check if we did not visit this tile before
                        queue.add(neighbour);
                    }
                }
            }
            if (currentY+1 >= 0){ // Check if we can go down
                if (!currentTile.hasWall("down")){ // Check if there is no wall in the way
                    Wall neighbour = (Wall) gameBoard.getObject(currentX, currentY+1);
                    if (visited.contains(neighbour)){ // Check if we did not visit this tile before
                        queue.add(neighbour);
                    }
                }
            }

            // Add the current tile to the visited set
            visited.add(currentTile);
        }
    }
}
