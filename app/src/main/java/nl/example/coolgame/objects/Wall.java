package nl.example.coolgame.objects;

import nl.saxion.act.playground.model.GameBoard;
import nl.saxion.act.playground.model.GameObject;
import nl.saxion.act.playground.view.SpriteCache;

public class Wall extends GameObject {

    public static final String WALL_IMAGE = "Wall";
    public static final String WALL_IMAGE_NONE = "NoWall";

    public static final String WALL_IMAGE_RIGHT = "WallRight";
    public static final String WALL_IMAGE_RIGHT_DOWN = "WallRightDown";
    public static final String WALL_IMAGE_RIGHT_DOWN_LEFT = "WallRightDownLeft";

    public static final String WALL_IMAGE_LEFT = "WallLeft";
    public static final String WALL_IMAGE_LEFT_UP = "WallLeftUp";
    public static final String WALL_IMAGE_LEFT_UP_RIGHT = "WallLeftUpRight";

    public static final String WALL_IMAGE_UP = "WallUp";
    public static final String WALL_IMAGE_UP_RIGHT = "WallUpRight";
    public static final String WALL_IMAGE_UP_RIGHT_Down = "WallUpRightDown";

    public static final String WALL_IMAGE_DOWN = "WallDown";
    public static final String WALL_IMAGE_DOWN_LEFT = "WallDownLeft";
    public static final String WALL_IMAGE_DOWN_LEFT_UP = "WallDownLeftUp";

    public static final String WALL_IMAGE_UP_DOWN = "WallUpDown";
    public static final String WALL_IMAGE_LEFT_RIGHT = "WallLeftRight";

    private boolean hasWallUp;
    private boolean hasWallDown;
    private boolean hasWallLeft;
    private boolean hasWallRight;

    public Wall(){
        super();
        hasWallUp = true;
        hasWallDown = true;
        hasWallLeft = true;
        hasWallRight = true;
    }

    @Override
    public String getImageId() {
        // Fully walled
        if (hasWallUp && hasWallDown && hasWallLeft && hasWallRight){
            return WALL_IMAGE;
        }
        // Missing 1 wall
        if (hasWallUp && hasWallDown && hasWallLeft && !hasWallRight){
            return WALL_IMAGE_RIGHT;
        }
        if (hasWallUp && hasWallDown && !hasWallLeft && hasWallRight){
            return WALL_IMAGE_LEFT;
        }
        if (hasWallUp && !hasWallDown && hasWallLeft && hasWallRight){
            return WALL_IMAGE_DOWN;
        }
        if (!hasWallUp && hasWallDown && hasWallLeft && hasWallRight){
            return WALL_IMAGE_UP;
        }
        // Missing 2 walls
        if (hasWallUp && hasWallDown && !hasWallLeft && !hasWallRight){
            return WALL_IMAGE_LEFT_RIGHT;
        }
        if (hasWallUp && !hasWallDown && !hasWallLeft && hasWallRight){
            return WALL_IMAGE_DOWN_LEFT;
        }
        if (!hasWallUp && !hasWallDown && hasWallLeft && hasWallRight){
            return WALL_IMAGE_UP_DOWN;
        }
        if (!hasWallUp && hasWallDown && hasWallLeft && !hasWallRight){
            return WALL_IMAGE_UP_RIGHT;
        }
        if (!hasWallUp && hasWallDown && !hasWallLeft && hasWallRight){
            return WALL_IMAGE_LEFT_UP;
        }
        if (hasWallUp && !hasWallDown && hasWallLeft && !hasWallRight){
            return WALL_IMAGE_RIGHT_DOWN;
        }
        // Missing 3 walls
        if (hasWallUp && !hasWallDown && !hasWallLeft && !hasWallRight){
            return WALL_IMAGE_RIGHT_DOWN_LEFT;
        }
        if (!hasWallUp && !hasWallDown && !hasWallLeft && hasWallRight){
            return WALL_IMAGE_DOWN_LEFT_UP;
        }
        if (!hasWallUp && !hasWallDown && hasWallLeft && !hasWallRight){
            return WALL_IMAGE_UP_RIGHT_Down;
        }
        if (!hasWallUp && hasWallDown && !hasWallLeft && !hasWallRight){
            return WALL_IMAGE_LEFT_UP_RIGHT;
        }
        // Missing all walls
        if (!hasWallUp && !hasWallDown && !hasWallLeft && !hasWallRight){
            return WALL_IMAGE_NONE;
        }
        return null;
    }

    @Override
    public void onTouched(GameBoard gameBoard) {
        // Nothing
    }

    public void destroyWall(String direction){
        switch (direction){
            case "up":
                hasWallUp = false;
                break;
            case "down":
                hasWallDown = false;
                break;
            case "left":
                hasWallLeft = false;
                break;
            case "right":
                hasWallRight = false;
                break;
        }
    }

    public void addWall(String direction) {
        switch (direction){
            case "up":
                hasWallUp = true;
                break;
            case "down":
                hasWallDown = true;
                break;
            case "left":
                hasWallLeft = true;
                break;
            case "right":
                hasWallRight = true;
                break;
        }
    }

    public boolean hasWall(String direction) {
        boolean result = false;
        switch (direction){
            case "up":
                if (hasWallUp) { result = true; }
                break;
            case "down":
                if (hasWallDown) { result = true; }
                break;
            case "left":
                if (hasWallLeft) { result = true; }
                break;
            case "right":
                if (hasWallRight) { result = true; }
                break;
        }
        return result;
    }
}
