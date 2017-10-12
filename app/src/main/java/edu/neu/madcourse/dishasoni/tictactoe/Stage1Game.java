package edu.neu.madcourse.dishasoni.tictactoe;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dishasoni on 10/12/17.
 */

public class Stage1Game implements GameBoard {

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
    public void add(int fLarge, int fSmall, char c) {
        selectedWords[fLarge] = selectedWords[fLarge].concat(String.valueOf(c));
        indexes.get(fLarge).add(fSmall);
    }


    public boolean remove(int fLarge, int fSmall) {
        ArrayList<Integer> l = indexes.get(fLarge);
        if (l.get(l.size() - 1) == fSmall) {
            l.remove(l.size() - 1);
            StringBuilder sb = new StringBuilder(selectedWords[fLarge]);
            selectedWords[fLarge] = sb.substring(0, indexes.get(fLarge).size() - 1);
            return true;
        }
        return false;
    }


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
