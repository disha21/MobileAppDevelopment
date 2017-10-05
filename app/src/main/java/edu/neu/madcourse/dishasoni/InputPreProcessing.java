package edu.neu.madcourse.dishasoni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dishasoni on 10/4/17.
 */

public class InputPreProcessing {

    private static String FILENAME ="/Users/dishasoni/AndroidStudioProjects/Hello-MAD/app/src/main/res/raw/wordlist.txt";

    public static void main(String[] args) {
        BufferedReader br = null;
        FileReader fr = null;
        BufferedWriter bw = null;
        FileWriter fw = null;
        String sCurrentLine = "";
        String startWord = "";
        try {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            String firstLine = br.readLine();
            //System.out.println("firstLine" + firstLine);
            startWord = firstLine.substring(0,3);
            fw = new FileWriter("/Users/dishasoni/AndroidStudioProjects/Hello-MAD/app/src/main/res/raw/" + startWord + "_word.txt");
            bw = new BufferedWriter(fw);
            bw.write(firstLine + "\n" );
            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.startsWith(startWord)){
                    bw.write(sCurrentLine + "\n");
                    //System.out.println(sCurrentLine);
                }else{

                    bw.close();
                    fw.close();
                    startWord = sCurrentLine.substring(0,3);
                    fw = new FileWriter("/Users/dishasoni/AndroidStudioProjects/Hello-MAD/app/src/main/res/raw/" + startWord + "_word.txt");
                    bw = new BufferedWriter(fw);
                    bw.write(sCurrentLine + "\n");
                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

}


