package com.example.mattlane.tic_tac_toe;

/**
 * Created by mattlane on 3/27/16.
 */
public class Move {

    int myMarker;
    Quadrant myQuadrant;

    public Move(int marker, Quadrant q) {
        this.myMarker = marker;
        this.myQuadrant = q;

    }

}
