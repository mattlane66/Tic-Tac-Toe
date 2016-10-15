package com.example.mattlane.tic_tac_toe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mattlane on 2/28/16.
 */
public class GameBoard extends View {

    boolean gameover;

    int[][] matrix;
    int scale;
    Paint p;
    WinGroup[] WinGroups;
    Context context;

    Quadrant current;
    int marker;
    List<Move> moves;

    float x, y;

    public GameBoard(Context context) {
        super(context);
        this.gameover = false;
        this.context = context;
        matrix = new int[3][3];
        scale = 100;
        p = new Paint();
        p.setColor(Color.GREEN);
        marker = 0;
        moves = new ArrayList<>();
        WinGroups = new WinGroup[8];
        WinGroups[0] = new WinGroup(0,0,1,0,2,0);
        WinGroups[1] = new WinGroup(0,1,1,1,2,1);
        WinGroups[2] = new WinGroup(0,2,1,2,2,2);
        WinGroups[3] = new WinGroup(0,0,0,1,0,2);
        WinGroups[4] = new WinGroup(1,0,1,1,1,2);
        WinGroups[5] = new WinGroup(2,0,2,1,2,2);
        WinGroups[6] = new WinGroup(0,0,1,1,2,2);
        WinGroups[7] = new WinGroup(0,2,1,1,2,0);

        invalidate();
    }


    protected void onDraw(Canvas canvas) {

        // draw grid
        canvas.drawLine(1*scale, 0*scale, 1*scale, 3*scale, p);
        canvas.drawLine(2*scale, 0*scale, 2*scale, 3*scale, p);
        canvas.drawLine(0*scale, 1*scale, 3*scale, 1*scale, p);
        canvas.drawLine(0 * scale, 2 * scale, 3 * scale, 2 * scale, p);


        if (!moves.isEmpty()) {
            for (int i = 0; i< moves.size(); i++) {
                Move m = moves.get(i);
                if(m.myMarker == 0){
                    canvas.drawCircle(scale * m.myQuadrant.getCenterX(), scale * m.myQuadrant.getCenterY(), scale * m.myQuadrant.getRadius(), p);
                }else{
                    canvas.drawLine(scale * m.myQuadrant.getTopLeftX(), scale * m.myQuadrant.getTopLeftY(), scale * m.myQuadrant.getBottomRightX(), scale * m.myQuadrant.getBottomRightY(),p);
                    canvas.drawLine(scale * m.myQuadrant.getBottomLeftX(), scale * m.myQuadrant.getBottomLeftY(), scale * m.myQuadrant.getTopRightX(), scale * m.myQuadrant.getTopRightY(),p);
                    }
            }

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {



        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (gameover) {
                    CharSequence text = "GAME OVER BITCH";

                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    x = event.getX();
                    y = event.getY();

                    Quadrant q = coord2quad(x, y);
                    if (q != null) {


                        if (isMoveValid(moves, q)) {
                            System.out.println(q.x + ", " + q.y);
                            if (marker == 0) {
                                marker = 1;
                            } else if (marker == 1) {
                                marker = 0;
                            }

                            Move newMove = new Move(marker, q);

                            moves.add(newMove);
                            invalidate();

                            System.out.println("Winner: " + checkWinner());

                            if (checkWinner() == 1) {

                                CharSequence text = "X Wins!";

                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                gameover = true;
                            }

                            if (checkWinner() == 0) {

                                CharSequence text = "O Wins!";

                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                gameover = true;
                            }
                            if (checkWinner() == -1 && moves.size() == 9) {

                                CharSequence text = "Tie!";

                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                gameover = true;
                            }

                        }
                    }

                }

                System.out.println("press down");
                break;
            default: break;
        }

        invalidate();

        return true;
    }


    /***
     *
     * converts coordinates to quadrant coordinate
     *
     */
    public Quadrant coord2quad(float x, float y) {
        Quadrant quadrant = new Quadrant();
        x = x / scale;
        y = y / scale;

        if (0<=x && x<1){
            quadrant.x = 0;
        }else if (1<=x && x<2){
            quadrant.x = 1;
        }else if (2<=x && x<3){
            quadrant.x = 2;
        }else{
            System.out.println("error");
            return null;
        }


        if (0<=y && y<1){
            quadrant.y = 0;
        }else if (1<=y && y<2){
            quadrant.y = 1;
        }else if (2<=y && y<3){
            quadrant.y = 2;
        }else{
            System.out.println("error");
            return null;
        }

        return quadrant;
    }


    public boolean isMoveValid(List<Move> MovesSoFar, Quadrant newQuadrant) {

        for (int i = 0; i< MovesSoFar.size(); i++) {
            Move extractedmove = MovesSoFar.get(i);
            Quadrant extractedquadrant = extractedmove.myQuadrant;

            if (extractedquadrant.x == newQuadrant.x && extractedquadrant.y == newQuadrant.y) {
                return false;
            }
        }
        return true;
    }


    /**
     * Iterates through list of wingroups and checks list of moves for matching triplet.
     * Then checks that markers are all the same.
     * @return 0 if player 0 wins, 1 if player 1 wins, -1 if no win (if moves.length == 9 and no win, game ends in tie)
     * */
    public int checkWinner(){
       for (int i= 0; i< WinGroups.length; i++){
          WinGroup WG = WinGroups[i];

           // find all moves that match the wingroup WG
           List<Move> matched = new ArrayList<>();
           for (int j= 0; j< moves.size(); j++){

              Move m = moves.get(j);
              Quadrant q = m.myQuadrant;
              int myx =  q.x;
              int myy = q.y;


               if (myx == WG.x1 && myy == WG.y1){
                   matched.add(m);
               }
               else if (myx == WG.x2 && myy == WG.y2){
                   matched.add(m);
               }
               else if (myx == WG.x3 && myy == WG.y3){
                   matched.add(m);

               }



           }

           if (matched.size() == 3) {
               // check that the markers are consistent
               if (matched.get(0).myMarker == matched.get(1).myMarker && matched.get(0).myMarker == matched.get(2).myMarker){
                   return matched.get(0).myMarker;
               }
           }

       }

        return  -1;
    }



}
