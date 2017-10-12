package edu.neu.madcourse.dishasoni.tictactoe;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dishasoni on 10/12/17.
 */

public class Stage1Game extends GameBoard {

    public static String[] selectedWords = {"", "", "", "", "", "", "", "", ""};

    ArrayList<ArrayList<Integer>> indexes = new ArrayList<>(9);


    public Stage1Game() {
        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> n = new ArrayList<>();
            indexes.add(n);
        }
    }

    @Override
    public boolean isValidMove(int i, int j) {
        Log.d("isValid Move", "i " + i + " j " + j);
        ArrayList<Integer> arr = indexes.get(i);
        Log.d("isvalidMove array", arr.toString());
        if (arr.size() == 0)
            return true;
        else {
            int prev = arr.get(arr.size() - 1);
            Log.d("isvalidMove else", "" + prev);
            List<Integer> a = findNextMove(prev);
            Log.d("isvalidMove list", "" + a.toString());
            return findNextMove(prev).contains(j);
        }
    }

    @Override
    public void add(int fLarge, int fSmall, String c) {
        selectedWords[fLarge] = selectedWords[fLarge].concat(c);
        indexes.get(fLarge).add(fSmall);
    }

    @Override
    public ArrayList<ArrayList<Integer>> getBoard(){
        return indexes;
    }

    @Override
    public String[] getSelectedWords(){
        return selectedWords;
    }


    public boolean remove(int fLarge, int fSmall) {
        ArrayList<Integer> l = indexes.get(fLarge);
        if (l.get(l.size() - 1) == fSmall) {
            l.remove(l.size() - 1);
            if (indexes.get(fLarge).size() == 0) {
                selectedWords[fLarge] = "";
            } else {
                StringBuilder sb = new StringBuilder(selectedWords[fLarge]);
                selectedWords[fLarge] = sb.substring(0, indexes.get(fLarge).size() - 1);
            }
            return true;
        }
        return false;
    }




//    public String toString() {
//        indexes.se
//    }
}
