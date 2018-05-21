package nl.example.coolgame.MazeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import nl.example.coolgame.objects.Wall;
import nl.saxion.act.playground.model.GameBoard;
import nl.saxion.act.playground.model.GameObject;

/**
 * Implementation of Prim's random maze generation algorithm
 * - start with a full grid of walls
 * - pick a wall at random, mark it as part of the maze, add its neighbours to the wall list
 * - while there are walls in the list:
 *      - pick a random wall from the list, if it connects to the maze:
 *          - destroy the wall disconnecting it to the maze
 *          - add neighbouring walls to the wall list
 *      - remove the wall from the list
 *
 * @author Coen Neefjes
 */
public class Prim {

    private GameBoard gameBoard;
    private int boardWidth;
    private int boardHeight;

    private List<Wall> walls = new ArrayList<>();
    private List<Wall> visited = new ArrayList<>();

    private String wallString = Wall.WALL_IMAGE;

    public Prim(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        boardWidth = this.gameBoard.getWidth();
        boardHeight = this.gameBoard.getHeight();
    }

    public void generateMaze(){
        // Keep track of the current tile
        Wall currentTile;

        int actions = 0;

        // Fill the board with walls
        fillWalls();

        // Select a random first tile
        int randomX = ThreadLocalRandom.current().nextInt(0, boardWidth);
        int randomY = ThreadLocalRandom.current().nextInt(0, boardHeight);
        currentTile = (Wall) gameBoard.getObject(randomX, randomY);

        // Set the current tile as visited
        visited.add(currentTile);

        // Add neighbours to the walls list
        addNeighbours(currentTile);

        while (!walls.isEmpty()){
            // Select random wall from list
            int randomWall = ThreadLocalRandom.current().nextInt(0, walls.size());
            currentTile = walls.get(randomWall);
            int currentX = currentTile.getPositionX();
            int currentY = currentTile.getPositionY();

            // Check if one of the neighbors is part of the maze
            boolean notFound = true;
            boolean direction1 = false;
            boolean direction2 = false;
            boolean direction3 = false;
            boolean direction4 = false;
            while (notFound) {
                int direction = ThreadLocalRandom.current().nextInt(1, 4 + 1);
                switch (direction) {
                    case 1: // Left
                        direction1 = true;
                        // Check if neighbour is part of the maze
                        if (currentX-1 >= 0 && visited.contains((Wall)gameBoard.getObject(currentX-1, currentY))){
                            notFound = false;
                            // Remove the wall
                            currentTile.destroyWall("left");
                            Wall neighbour = (Wall) gameBoard.getObject(currentX-1, currentY);
                            neighbour.destroyWall("right");
                            // Add neighbours
                            addNeighbours(currentTile);
                            // Add to visited list
                            visited.add(currentTile);
                        }
                        break;
                    case 2: // Right
                        direction2 = true;
                        // Check if neighbour is part of the maze
                        if (currentX+1 < boardWidth && visited.contains((Wall)gameBoard.getObject(currentX+1, currentY))){
                            notFound = false;
                            // Remove the wall
                            currentTile.destroyWall("right");
                            Wall neighbour = (Wall) gameBoard.getObject(currentX+1, currentY);
                            neighbour.destroyWall("left");
                            // Add neighbours
                            addNeighbours(currentTile);
                            // Add to visited list
                            visited.add(currentTile);
                        }
                        break;
                    case 3: // Up
                        direction3 = true;
                        // Check if neighbour is part of the maze
                        if (currentY-1 >= 0 && visited.contains((Wall)gameBoard.getObject(currentX, currentY-1))){
                            notFound = false;
                            // Remove the wall
                            currentTile.destroyWall("up");
                            Wall neighbour = (Wall) gameBoard.getObject(currentX, currentY-1);
                            neighbour.destroyWall("down");
                            // Add neighbours
                            addNeighbours(currentTile);
                            // Add to visited list
                            visited.add(currentTile);
                        }
                        break;
                    case 4: // Down
                        direction4 = true;
                        // Check if neighbour is part of the maze
                        if (currentY+1 < boardHeight && visited.contains((Wall)gameBoard.getObject(currentX, currentY+1))){
                            notFound = false;
                            // Remove the wall
                            currentTile.destroyWall("down");
                            Wall neighbour = (Wall) gameBoard.getObject(currentX, currentY+1);
                            neighbour.destroyWall("up");
                            // Add neighbours
                            addNeighbours(currentTile);
                            // Add to visited list
                            visited.add(currentTile);
                        }
                        break;
                }
                if (direction1 && direction2 && direction3 && direction4 && notFound){
                    // All neighbours are not maze
                    notFound = false;
                }
            }
            // Remove the wall from the list
            walls.remove(currentTile);
            actions++;
        }
        System.out.println("actions made: " + actions + ", should be: " + ((boardHeight*boardWidth)-1));
    }

    // Add all neighbours that are walls to the walls list
    private void addNeighbours(Wall currentTile){
        int currentX = currentTile.getPositionX();
        int currentY = currentTile.getPositionY();

        // Add right
        if (currentX+1 < boardWidth && gameBoard.getObject(currentX+1, currentY).getImageId().equals(wallString) && !walls.contains((Wall)gameBoard.getObject(currentX+1, currentY))){
            walls.add((Wall)gameBoard.getObject(currentX+1, currentY));
        }
        // Add left
        if (currentX-1 >= 0 && gameBoard.getObject(currentX-1, currentY).getImageId().equals(wallString) && !walls.contains((Wall)gameBoard.getObject(currentX-1, currentY))){
            walls.add((Wall)gameBoard.getObject(currentX-1, currentY));
        }
        // Add up
        if (currentY-1 >= 0 && gameBoard.getObject(currentX, currentY-1).getImageId().equals(wallString) && !walls.contains((Wall)gameBoard.getObject(currentX, currentY-1))){
            walls.add((Wall)gameBoard.getObject(currentX, currentY-1));
        }
        // Add down
        if (currentY+1 < boardHeight && gameBoard.getObject(currentX, currentY+1).getImageId().equals(wallString) && !walls.contains((Wall)gameBoard.getObject(currentX, currentY+1))){
            walls.add((Wall)gameBoard.getObject(currentX, currentY+1));
        }
    }

    private void fillWalls(){
        for (int x=0; x<boardWidth; x++){
            for (int y=0; y<boardHeight; y++){
                gameBoard.addGameObject(new Wall(), x, y);
            }
        }
    }
}