package nl.example.coolgame.MazeGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import nl.example.coolgame.objects.Wall;
import nl.saxion.act.playground.model.GameBoard;

/**
 * Implementation of Kruskal's random maze generation algorithm
 * - create a list of all walls, and create a set for each wall, each containing just that one wall
 * - for each wall, in a random order:
 *      - if the wall has a neighbour that is in another set:
 *          - remove the wall in between
 *          - join the sets of the 2 cells
 *
 * @author Coen Neefjes
 */
public class Kruskal {

    private GameBoard gameBoard;
    private int boardWidth;
    private int boardHeight;

    private List<Wall> walls = new ArrayList<>();
    private Set[] sets;

    public Kruskal(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        boardWidth = this.gameBoard.getWidth();
        boardHeight = this.gameBoard.getHeight();
    }

    public void generateMaze(){
        // Create a set array that contains all the sets
        sets = new Set[boardWidth*boardHeight];

        // Fill the field with walls
        fillWalls();

        // Randomize the list of walls
        Collections.shuffle(walls);

        // Repeat this algorithm for every wall in the list
        for (Wall wall : walls) {
            // Set current position
            int currentX = wall.getPositionX();
            int currentY = wall.getPositionY();

            // Choose a random neighbour
            Wall neighbour;
            boolean notSelected = true;
            boolean direction1 = false;
            boolean direction2 = false;
            boolean direction3 = false;
            boolean direction4 = false;
            while (notSelected){
                int direction = ThreadLocalRandom.current().nextInt(1, 4 + 1);
                switch (direction){
                    case 1: //Left
                        direction1 = true;
                        if (currentX-1 >= 0){
                            neighbour = (Wall) gameBoard.getObject(currentX-1, currentY);
                            // Check if they are in different sets
                            if (!inSameSet(wall, neighbour)){
                                // Destroy wall in between
                                wall.destroyWall("left");
                                neighbour.destroyWall("right");
                                // Join sets
                                joinSets(wall, neighbour);
                                // Break the loop
                                notSelected = false;
                            }
                        }
                        break;
                    case 2: //Right
                        direction2 = true;
                        if (currentX+1 < boardWidth){
                            neighbour = (Wall) gameBoard.getObject(currentX+1, currentY);
                            // Check if they are in different sets
                            if (!inSameSet(wall, neighbour)){
                                // Destroy wall in between
                                wall.destroyWall("right");
                                neighbour.destroyWall("left");
                                // Join sets
                                joinSets(wall, neighbour);
                                // Break the loop
                                notSelected = false;
                            }
                        }
                        break;
                    case 3: //Up
                        direction3 = true;
                        if (currentY-1 >= 0){
                            neighbour = (Wall) gameBoard.getObject(currentX, currentY-1);
                            // Check if they are in different sets
                            if (!inSameSet(wall, neighbour)){
                                // Destroy wall in between
                                wall.destroyWall("up");
                                neighbour.destroyWall("down");
                                // Join sets
                                joinSets(wall, neighbour);
                                // Break the loop
                                notSelected = false;
                            }
                        }
                        break;
                    case 4: //Down
                        direction4 = true;
                        if (currentY+1 < boardHeight){
                            neighbour = (Wall) gameBoard.getObject(currentX, currentY+1);
                            // Check if they are in different sets
                            if (!inSameSet(wall, neighbour)){
                                // Destroy wall in between
                                wall.destroyWall("down");
                                neighbour.destroyWall("up");
                                // Join sets
                                joinSets(wall, neighbour);
                                // Break the loop
                                notSelected = false;
                            }
                        }
                        break;
                }
                if (direction1 && direction2 && direction3 && direction4 && notSelected){
                    notSelected = false;
                    // No viable neighbours found
                }
            }
        }
        checkSets();
    }

    private void fillWalls(){
        int counter = 0;
        for (int x=0; x<boardWidth; x++){
            for (int y=0; y<boardHeight; y++){
                // Make the wall
                Wall wall = new Wall();
                // Add wall to the board
                gameBoard.addGameObject(wall, x, y);
                // Add wall to the wall list 4 times (so each individual side of the wall is in the list)
                walls.add(wall);
                walls.add(wall);
                walls.add(wall);
                walls.add(wall);
                // Make a set and add it to the set array
                Set<Wall> set = new HashSet<>();
                set.add(wall);
                sets[counter] = set;
                // Keep track of the amount of sets we already made
                counter++;
            }
        }
    }

    private int searchSetNr(Wall object){
        int result = -1;
        for(int i=0; i<boardHeight*boardWidth; i++){
            if (sets[i].contains(object)){
                result = i;
            }
        }
        return result;
    }

    private boolean inSameSet(Wall currentTile, Wall neighbour){
        if (searchSetNr(currentTile) == searchSetNr(neighbour)){
            return true;
        } else {
            return false;
        }
    }

    private String reverseDirection(String direction){
        String reverse = "";
        switch (direction){
            case "left":
                reverse = "right";
                break;
            case "right":
                reverse = "left";
                break;
            case "up":
                reverse = "down";
                break;
            case "down":
                reverse = "up";
        }
        return reverse;
    }

    private void joinSets(Wall currentTile, Wall neighbour){
        int set1 = searchSetNr(currentTile);
        int set2 = searchSetNr(neighbour);
        sets[set1].addAll(sets[set2]);
        sets[set2].clear();
    }

    // This method is purely for debugging purposes
    private void checkSets(){
        int i = 0;
        for (Set s: sets) {
            if (s.size() > 0){
                i++;
            }
        }
        if (i==1){
            System.out.println("Maze generated successfully!");
        } else {
            System.out.println("Faulty maze generated!");
        }
    }

}
