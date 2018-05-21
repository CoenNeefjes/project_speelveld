package nl.example.coolgame.MazeGenerator;

import java.util.concurrent.ThreadLocalRandom;

import nl.example.coolgame.objects.Wall;
import nl.saxion.act.playground.model.GameBoard;

/**
 * Implementation of a recursive random maze generation algorithm
 * - start with the maze's space with no walls, call this a chamber
 * - Divide the chamber with a randomly positioned horizontal and vertical wall
 * - Make a randomly chosen passage in 3 out of 4 walls in the chamber
 * - There are now 4 sub-chambers, repeat the process for each sub-chamber
 *
 * @author Coen Neefjes
 */
public class RecursiveDivision {

    private GameBoard gameBoard;
    private int boardWidth;
    private int boardHeight;

    public RecursiveDivision(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        boardWidth = this.gameBoard.getWidth();
        boardHeight = this.gameBoard.getHeight();
    }

    public void generateMaze(){
        // Fill the board with empty tiles
        fillBoard();

        // Start recursive maze generation
        chamber(0, boardWidth-1, 0, boardHeight-1);
    }

    private void chamber(int leftX, int rightX, int upperY, int lowerY){
        // Select a random place for the vertical line
        int randomX = ThreadLocalRandom.current().nextInt(leftX+1, rightX+1);
        for (int i=upperY; i<lowerY+1; i++){
            addWall(randomX, i, "left");
            addWall(randomX-1, i, "right");
        }

        // Select a random place for the horizontal line
        int randomY = ThreadLocalRandom.current().nextInt(upperY+1, lowerY+1);
        for (int i=leftX; i<rightX+1; i++){
            addWall(i, randomY, "up");
            addWall(i, randomY-1, "down");
        }

        // Choose a random wall to not make a hole in
        int wallNr = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        // Make holes in all walls but the chosen one
        if (wallNr != 1){ //Up
            int randomHoleY = ThreadLocalRandom.current().nextInt(upperY, randomY);
            destroyWall(randomX, randomHoleY, "left");
            destroyWall(randomX-1, randomHoleY, "right");
        }
        if (wallNr != 2){ //Down
            int randomHoleY = ThreadLocalRandom.current().nextInt(randomY, lowerY+1);
            destroyWall(randomX, randomHoleY, "left");
            destroyWall(randomX-1, randomHoleY, "right");
        }
        if (wallNr != 3){ //Right
            int randomHoleX = ThreadLocalRandom.current().nextInt(randomX, rightX+1);
            destroyWall(randomHoleX, randomY, "up");
            destroyWall(randomHoleX, randomY-1, "down");
        }
        if (wallNr != 4){ //Left
            int randomHoleX = ThreadLocalRandom.current().nextInt(leftX, randomX);
            destroyWall(randomHoleX, randomY, "up");
            destroyWall(randomHoleX, randomY-1, "down");
        }

        // Recursive call for the created chambers, if the created chamber is larger enough
        if ((randomX-1)-leftX >= 1 && (randomY-1)-upperY >= 1){
            chamber(leftX, randomX-1, upperY, randomY-1); //Upper left
        }
        if (rightX - randomX >= 1 && (randomY-1)-upperY >= 1){
            chamber(randomX, rightX, upperY,randomY-1); //Upper right
        }
        if ((randomX-1)-leftX >= 1 && lowerY-randomY >= 1){
            chamber(leftX, randomX-1, randomY, lowerY); //Lower left
        }
        if (rightX-randomX >= 1 && lowerY-randomY >= 1){
            chamber(randomX, rightX, randomY, lowerY); //Lower right
        }
    }

    // Fill the board with empty tiles
    private void fillBoard(){
        for (int x=0; x<boardWidth; x++){
            for (int y=0; y<boardHeight; y++){
                Wall wall = new Wall();
                if (x != 0){
                    wall.destroyWall("left");
                }
                if (x != boardWidth-1){
                    wall.destroyWall("right");
                }
                if (y != 0){
                    wall.destroyWall("up");
                }
                if (y != boardHeight-1){
                    wall.destroyWall("down");
                }
                gameBoard.addGameObject(wall, x, y);
            }
        }
    }

    private void addWall(int x, int y, String direction){
        Wall wall = (Wall) gameBoard.getObject(x,y);
        wall.addWall(direction);
    }

    private void destroyWall(int x, int y, String direction){
        Wall wall = (Wall) gameBoard.getObject(x,y);
        wall.destroyWall(direction);
    }
}
