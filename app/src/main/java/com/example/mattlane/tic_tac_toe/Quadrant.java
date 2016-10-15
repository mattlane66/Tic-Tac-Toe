package com.example.mattlane.tic_tac_toe;

/**
 * Created by mattlane on 3/27/16.
 */
public class Quadrant {
    int x;
    int y;


    float getRadius() {
        return .5f;
    }

    float getCenterX() {
       return x+.5f;
    }

    float getCenterY() {
        return y+.5f;
    }

    float getTopRightX() {
        return x+1f;
    }

    float getTopRightY() {
        return (float)y;
    }

    float getTopLeftX() {
        return (float)x;
    }

    float getTopLeftY() {
        return (float)y;
    }

    float getBottomRightX() {
        return x+1f;
    }

    float getBottomRightY() {
        return y+1f;
    }

    float getBottomLeftX() {
        return (float)x;
    }

    float getBottomLeftY() {
        return y+1f;
    }


}