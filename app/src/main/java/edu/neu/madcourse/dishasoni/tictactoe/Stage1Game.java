package edu.neu.madcourse.dishasoni.tictactoe;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by dishasoni on 10/12/17.
 */

public class Stage1Game extends GameBoard {

    public static String[] selectedWords = {"", "", "", "", "", "", "", "", ""};
    List<List<Integer>> pattern = new ArrayList<List<Integer>>();
    public static String FILENAME ="/Users/dishasoni/AndroidStudioProjects/Hello-MAD/app/src/main/res/raw/input_word_game.txt";

    ArrayList<ArrayList<Integer>> indexes = new ArrayList<>(9);


    public Stage1Game() {
        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> n = new ArrayList<>();
            indexes.add(n);
        }
    }

//    public static void main(String[] args) {
//        List<String> output = new ArrayList<String>();
//        Stage1Game sg = new Stage1Game();
//        output = sg.generateWordList();
//        for (int i = 0; i <output.size(); i++) {
//            System.out.println(output.get(i));
//
//        }
//    }

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

    public List<Integer> findWordPattern() {
        pattern.add(Arrays.asList(0,1,2,5,8,7,4,3,6));
        pattern.add(Arrays.asList(1,5,2,4,0,3,6,7,8));
        pattern.add(Arrays.asList(2,5,8,7,6,3,4,0,1));
        pattern.add(Arrays.asList(5,2,1,4,8,7,6,3,0));
        pattern.add(Arrays.asList(4,0,3,1,2,5,8,7,6));
        pattern.add(Arrays.asList(3,6,4,7,8,5,2,1,0));
        Random r = new Random();
        List<Integer> randomPattern = pattern.get(r.nextInt(pattern.size()));

        return randomPattern;

    }

    public List<String> generateWordList(InputStream file){

        List<String> inputGridWords = new ArrayList<String>();
        List<String> outputGridWords = new ArrayList<String>();
        List<String> tempGridWords = new ArrayList<String>();
        BufferedReader br = null;
        FileReader fr = null;
        String sCurrentLine = "";

        try {
            br = new BufferedReader(new InputStreamReader(file));

            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.trim().length()== 9){
                    inputGridWords.add(sCurrentLine.trim());
                }

            }


        } catch (IOException e) {

            e.printStackTrace();

        }
        for (int i = 0; i < 9; i++) {
            Random r = new Random();
            String randomString = inputGridWords.get(r.nextInt(inputGridWords.size()));
            tempGridWords.add(randomString);
        }

        for (int i = 0; i < tempGridWords.size(); i++) {
                String word = getWord(tempGridWords.get(i));
            outputGridWords.add(word);
        }

        return outputGridWords;

    }

    public String getWord(String w){
        List<Integer> l = findWordPattern();
        char[] word = new char[9];
        for (int i = 0; i <w.length() ; i++) {
            for (int j = 0; j <l.size(); j++) {
                word[l.get(j)] = w.charAt(j);
            }

        }


        return String.valueOf(word);
    }


}
