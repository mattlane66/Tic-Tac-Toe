package com.example.mattlane.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameBoard gameBoard = new GameBoard(this);
        setContentView(gameBoard);

    }
}
