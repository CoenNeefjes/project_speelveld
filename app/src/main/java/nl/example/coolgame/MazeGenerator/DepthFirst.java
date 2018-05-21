package nl.example.coolgame.MazeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import nl.example.coolgame.objects.Wall;
import nl.saxion.act.playground.model.GameBoard;

/**
 * Implementation of a depth-first random maze generation algorithm
 * - start from a random tile
 * - select a neighbouring tile that has not yet been visited
 * - remove the wall between the tiles and add the neighbour to the stack
 * - repeat this process for the neighbour
 * - if there is a dead end, start backtracking using the stack
 *
 * @author Coen Neefjes
 */
public class DepthFirst {

    private GameBoard gameBoard;
    private int boardWidth;
    private int boardHeight;

    private List<Wall> visitedTiles = new ArrayList<>();
    private Stack<Wall> backTrackStack = new Stack<>();

    public DepthFirst(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        boardWidth = this.gameBoard.getWidth();
        boardHeight = this.gameBoard.getHeight();
    }

    public void generateMaze(){
        Wall currentTile;
        Wall neighbour = null;

        // Fill the board with walls
        fillWalls();

        // Select a random first tile
        int randomX = ThreadLocalRandom.current().nextInt(0, boardWidth);
        int randomY = ThreadLocalRandom.current().nextInt(0, boardHeight);
        currentTile = (Wall) gameBoard.getObject(randomX, randomY);

        // Add the starting tile to the stack and list
        addTile(currentTile);

        // The maze is done after the stack is empty
        while (!backTrackStack.empty()) {
            // Select a random neighbour
            boolean selected = false;
            boolean direction1 = false;
            boolean direction2 = false;
            boolean direction3 = false;
            boolean direction4 = false;
            while (!selected){
                int direction = ThreadLocalRandom.current().nextInt(1, 4 + 1);
                switch (direction){
                    case 1: //Left
                        direction1 = true;
                        if (currentTile.getPositionX()-1 >= 0){
                            neighbour = (Wall) gameBoard.getObject(currentTile.getPositionX()-1, currentTile.getPositionY());
                            // Select this neighbour if it is deemed valid
                            selected = isValidNeighbour(currentTile, neighbour, "left", "right");
                        }
                        break;
                    case 2: //Right
                        direction2 = true;
                        if (currentTile.getPositionX()+1 < boardWidth){
                            neighbour = (Wall) gameBoard.getObject(currentTile.getPositionX()+1, currentTile.getPositionY());
                            // Select this neighbour if it is deemed valid
                            selected = isValidNeighbour(currentTile, neighbour, "right", "left");
                        }
                        break;
                    case 3: //Up
                        direction3 = true;
                        if (currentTile.getPositionY()-1 >= 0){
                            neighbour = (Wall) gameBoard.getObject(currentTile.getPositionX(), currentTile.getPositionY()-1);
                            // Select this neighbour if it is deemed valid
                            selected = isValidNeighbour(currentTile, neighbour, "up", "down");
                        }
                        break;
                    case 4: //Down
                        direction4 = true;
                        if (currentTile.getPositionY()+1 < boardHeight){
                            neighbour = (Wall) gameBoard.getObject(currentTile.getPositionX(), currentTile.getPositionY()+1);
                            // Select this neighbour if it is deemed valid
                            selected = isValidNeighbour(currentTile, neighbour, "down", "up");
                        }
                        break;
                }
                // Check if all directions have been checked for valid neighbours and none of them was selected as valid
                if (direction1 && direction2 && direction3 && direction4 && !selected){
                    // Start backtracking
                    backTrackStack.pop();
                    if (!backTrackStack.isEmpty()){
                        currentTile = backTrackStack.peek();
                    }
                    // Make it clear no valid neighbour was found
                    neighbour = null;
                    // Break the loop
                    selected = true;
                }
            }
            // If a valid neighbour is found, make it the current tile
            if (neighbour != null){
                currentTile = neighbour;
                neighbour = null;
            }
        }
    }

    private void addTile(Wall wall){
        visitedTiles.add(wall);
        backTrackStack.push(wall);
    }

    private void fillWalls(){
        for (int x=0; x<boardWidth; x++){
            for (int y=0; y<boardHeight; y++){
                gameBoard.addGameObject(new Wall(), x, y);
            }
        }
    }

    private boolean isValidNeighbour(Wall currentTile, Wall neighbour, String destroyWall1, String destroyWall2){
        // Check if the neighbour has not yet been visited
        if (!visitedTiles.contains(neighbour)){
            // Add neighbour to the list and stack
            addTile(neighbour);
            // Destroy the wall
            currentTile.destroyWall(destroyWall1);
            neighbour.destroyWall(destroyWall2);
            // Set selected to true
            return true;
        } else {
            return false;
        }
    }

}
