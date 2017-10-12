package edu.neu.madcourse.dishasoni.tictactoe;

/**
 * Created by dishasoni on 10/12/17.
 */

public interface GameBoard {

    boolean isValidMove(int i, int j);

    void add(int i, int j, char c);

    boolean remove(int i, int j);




}
