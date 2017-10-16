package edu.neu.madcourse.dishasoni.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by dishasoni on 10/12/17.
 */

public abstract class GameBoard {

    abstract boolean isValidMove(int i, int j);

    abstract void add(int i, int j, String c);

    abstract boolean remove(int i, int j);

    abstract ArrayList<ArrayList<Integer>> getBoard();

    abstract String[] getSelectedWords();


    public List<Integer> findNextMove(Integer num) {
        List<Integer> list1 = new ArrayList<Integer>();
        switch (num) {
            case -1:
                list1.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
                break;
            case 0:
                list1.addAll(Arrays.asList(1, 3, 4));
                break;
            case 1:
                list1.addAll(Arrays.asList(0, 2, 3, 4, 5));
                break;
            case 2:
                list1.addAll(Arrays.asList(1, 4, 5));
                break;
            case 3:
                list1.addAll(Arrays.asList(0, 1, 4, 6, 7));
                break;
            case 4:
                list1.addAll(Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8));
                break;
            case 5:
                list1.addAll(Arrays.asList(1, 2, 4, 7, 8));
                break;
            case 6:
                list1.addAll(Arrays.asList(3, 4, 7));
                break;
            case 7:
                list1.addAll(Arrays.asList(3, 4, 5, 6, 8));
                break;
            case 8:
                list1.addAll(Arrays.asList(4, 5, 7));
                break;

        }
        return list1;

    }




}
