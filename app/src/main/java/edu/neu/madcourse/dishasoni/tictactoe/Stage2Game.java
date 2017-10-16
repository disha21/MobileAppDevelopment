package edu.neu.madcourse.dishasoni.tictactoe;

import java.util.ArrayList;

/**
 * Created by dishasoni on 10/12/17.
 */

public class Stage2Game extends GameBoard {

    class Pair{

        int fLarge;
        int fSmall;

        public Pair(int i , int j){
            this.fLarge = i;
            this.fSmall = j;
        }
    }


    String word;
    ArrayList<Pair> indexes = new ArrayList<>();

    public Stage2Game(){
        word = "";
    }


    @Override
    public boolean isValidMove(int i, int j) {
        if(indexes.size() == 0)
            return true;
        else{
            Pair p = indexes.get(indexes.size() -1);
            return findNextMove(p.fLarge).contains(i);
        }
    }

    @Override
    public void add(int i, int j, String c) {
        word = word.concat(c);
        indexes.add(new Pair(i,j));
    }

    @Override
    public boolean remove(int i, int j) {
        Pair p = indexes.get(indexes.size() -1);
        if(p.fLarge == i && p.fSmall == j){
            indexes.remove(indexes.size() -1);
            if(indexes.size() == 0 ){
                word = "";
            } else {
                StringBuilder sb = new StringBuilder(word);
                word = sb.substring(0, indexes.size());
            }
            return true;

        } else {
            return false;
        }
    }

    @Override
    public ArrayList<ArrayList<Integer>> getBoard() {
        return null;
    }

    @Override
    public String[] getSelectedWords() {
        String[] st = new String[1];
        st[0] = word;
        return st;
    }
}
