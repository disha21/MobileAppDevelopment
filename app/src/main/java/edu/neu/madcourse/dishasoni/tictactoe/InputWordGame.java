package edu.neu.madcourse.dishasoni.tictactoe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dishasoni on 10/7/17.
 */

public class InputWordGame {

    private static String FILENAME ="/Users/dishasoni/AndroidStudioProjects/Hello-MAD/app/src/main/res/raw/wordlist.txt";

    public static void main(String[] args) {
        BufferedReader br = null;
        FileReader fr = null;
        BufferedWriter bw = null;
        FileWriter fw = null;
        String sCurrentLine = "";

        try {
            fw = new FileWriter("/Users/dishasoni/AndroidStudioProjects/Hello-MAD/app/src/main/res/raw/input_word_game.txt");
            bw = new BufferedWriter(fw);
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.trim().length()== 9){
                  //  System.out.println(sCurrentLine);
                    bw.write(sCurrentLine+"\n");


                }

            }

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

}
