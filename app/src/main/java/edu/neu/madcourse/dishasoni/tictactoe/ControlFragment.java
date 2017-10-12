/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
 ***/
package edu.neu.madcourse.dishasoni.tictactoe;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.Resources;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import edu.neu.madcourse.dishasoni.DictionaryActivity;
import edu.neu.madcourse.dishasoni.MainActivity;
import edu.neu.madcourse.dishasoni.R;

import static android.content.Context.MODE_PRIVATE;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.KEY_RESTORE;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.PREF_RESTORE;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.calculateScore;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.mMediaPlayer;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.totalScorePhase1;
import static edu.neu.madcourse.dishasoni.tictactoe.GameFragment.selectedWords;


public class ControlFragment extends Fragment {


    static AlertDialog alertBox1;
    static AlertDialog alertBox2;
    static String line = "";
    String currentFile = "";
    final static TreeSet<String> searchWordInFile = new TreeSet<String>();
    static List<String> foundWords = new ArrayList<String>();
    static List<String> notFoundWords = new ArrayList<String>();
    public static CountDownTimer countDownTimer;

     long remainingTimePhase1 = 0;
     long remainingTimePhase2 = 0;
    static long timeInterval = 60000;
  //   static boolean clickedCheckWord = false;
    int countClicks = 0;
    public boolean isRestore;
    private String gameData = "";
     boolean isPaused = false;
    boolean phaseTwo = false;
   static long remainingTime = 0;

    GameFragment gf;

    public void setGameFragement(GameFragment gf){
        this.gf = gf;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView =
                inflater.inflate(R.layout.fragment_control, container, false);
        Bundle b = this.getActivity().getIntent().getExtras();
        if (b != null) {
            gameData = b.getString("gameData");
            isRestore = b.getBoolean(GameActivity.KEY_RESTORE);

        }

        View main = rootView.findViewById(R.id.home);
       final View pause = rootView.findViewById(R.id.pause);
        View restart = rootView.findViewById(R.id.restart);
        View checkWords = rootView.findViewById(R.id.checkWord);
        final View mute = rootView.findViewById(R.id.mute);
        final TextView mTextField = (TextView) rootView.findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                remainingTime = (millisUntilFinished);
                if (millisUntilFinished < 20000)
                    mTextField.setBackgroundColor(getResources().getColor(R.color.red_color));

            }

            public void onFinish() {
                showResult();
                mTextField.setText("Times Up for Phase 1!");
                final AlertDialog.Builder alertPhase2 = new AlertDialog.Builder(getActivity());
                alertPhase2.setTitle("Moving to Phase 2");
                alertPhase2.setMessage("Phase 1 score = " + totalScorePhase1)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        gf.updateViewForPhase2();
                        countDownTimer =  new CountDownTimer(60000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                                remainingTime = (millisUntilFinished);
                                if (millisUntilFinished < 10000) {
                                    //  Toast.makeText(getActivity().getApplicationContext(), "Hurry phase 1 is about to end !", Toast.LENGTH_SHORT).show();
                                    mTextField.setBackgroundColor(getResources().getColor(R.color.red_color));
                                }
                            }
                            public void onFinish() {
                                if(!isPaused) {
                                    mTextField.setText("Times Up for Phase 2!");
                                    showResult();
                                    gf.hideGame();
                                    Intent intent = new Intent(getActivity(), GameActivity.class);
                                    getActivity().startActivity(intent);
                                }

                            }

                        }.start();

                    }
                });
                alertBox2 =  alertPhase2.show();


            }
        }.start();

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mute.isPressed()) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                        mute.setBackgroundColor(getResources().getColor(R.color.red_color));
                    } else {
                        mMediaPlayer.start();
                        mute.setBackgroundColor(getResources().getColor(R.color.gray_color));
                    }
                }
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GameActivity) getActivity()).restartGame();
            }
        });
        checkWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countClicks = countClicks+1;
                if(countClicks <= 9) {
                    GameActivity.checkWordScore();
                    Log.d("clicking done", "update view");
                    gf.updateView();
                }else {
                    countDownTimer.cancel();
                    phaseTwo = true;
                    final AlertDialog.Builder alertPhase2 = new AlertDialog.Builder(getActivity());

                    alertPhase2.setTitle("Moving to Phase 2");
                    alertPhase2.setMessage("Number of attempts to check a word exceeded" + "\n"
                            + "Phase 1 score = " + totalScorePhase1);
                    gf.updateViewForPhase2();
                    countDownTimer =     new CountDownTimer(60000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                            remainingTime = millisUntilFinished;
                            if (millisUntilFinished < 10000) {
                                mTextField.setBackgroundColor(getResources().getColor(R.color.red_color));
                               // Toast.makeText(getActivity().getApplicationContext(), "Hurry game is about to end !", Toast.LENGTH_SHORT).show();
                            }
                        }
                        public void onFinish() {
                            if(!isPaused) {
                                mTextField.setText("Times Up for Phase 2!");
                                showResult();
                            }

                        }

                    }.start();
                }


            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         if(isPaused){
             gf.showGame();

             Button pause = (Button) rootView.findViewById(R.id.pause);
             pause.setText("PAUSE");
             isPaused = false;
              Log.d("remainingTime" ,String.valueOf(remainingTime));
             countDownTimer =   new CountDownTimer(remainingTime, 1000) {
                     public void onTick(long millisUntilFinished) {
                         mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                         remainingTime = millisUntilFinished;
                         if (millisUntilFinished < 10000) {
                             mTextField.setBackgroundColor(getResources().getColor(R.color.red_color));
                             //   Toast.makeText(getActivity().getApplicationContext(), "Hurry game is about to end !", Toast.LENGTH_SHORT).show();
                         }
                     }

                     public void onFinish() {
                         mTextField.setText("Times Up");
                         showResult();

                     }

                 }.start();


         }else{

             countDownTimer.cancel();
             gf.hideGame();
             Button pause = (Button) rootView.findViewById(R.id.pause);
             pause.setText("RESUME");
             isPaused = true;

         }


            }
        });

        return rootView;
    }



    public void showResult() {
        int wrong = findIfWordsExists();
        int finalScore = calculateScore();

        finalScore = finalScore - wrong * 2;
        Log.d("finalScore:", String.valueOf(finalScore));
        String right = "Right words:";
        String wrongWords = "Wrong words:";
        for (int i = 0; i < foundWords.size(); i++) {
            right = foundWords.get(i) + ",";
        }
        for (int i = 0; i < notFoundWords.size(); i++) {
            wrongWords = notFoundWords.get(i) + ",";
        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Phase1 Score");
        alert.setMessage(wrongWords + "\n" + right + "\n" + "Your Current Score :" + finalScore + "\n" + "\n" +
                "Score Rules :" + "\n" + "\n" +
                "Words Starting with" +
                "1 point: E, A, I, O, N, R, T, L, S" + "\n" +
                "2 points: D, G " + "\n" +
                "3 points: B, C, M, P " + "\n" +
                "4 points: F, H, V, W, Y " + "\n" +
                "5 points: K; 8 points: J, X; " + "\n" +
                "10 points: Q, Z" + "\n" +
                 "20points: 9 letter word" + "\n"


        );
        alertBox1 = alert.show();
        mMediaPlayer.stop();
    }

    public int findIfWordsExists() {
        for (int i = 0; i < selectedWords.length; i++) {
            findWordsInFile(selectedWords[i].toString().toLowerCase());
        }
        int wrongWords = notFoundWords.size();

        Log.d("wrong  found", String.valueOf(wrongWords));


        if (foundWords.size() > 0) {

            for (String c : foundWords)
                Log.d("word found", c);
        } else
            Log.d("No valid words found", "NO");
        for (String c : notFoundWords)
            Log.d("word not found", c);
        return wrongWords;

    }

    public void findWordsInFile(String word) {
        if (word.length() >= 3) {
            String prefix = word.substring(0, 3);
            if (currentFile != null & prefix == currentFile) {
                if (searchWordInFile.contains(word)) {
                    foundWords.add(word);
                } else {
                    notFoundWords.add(word);
                }
            } else {
                searchWordInFile.clear();
                try {
                    String line = "";
                    Resources myResources = getResources();
                    InputStream file = getResources().openRawResource(
                            getResources().getIdentifier(prefix.concat("_word"),
                                    "raw", getActivity().getPackageName()));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(file));
                    while ((line = reader.readLine()) != null) {
                        searchWordInFile.add(line.toLowerCase());
                    }
                    file.close();
                    currentFile = prefix;
                } catch (Exception e) {
                    //log the exception.
                    e.printStackTrace();

                }
                if (searchWordInFile.contains(word)) {
                    foundWords.add(word);
                } else {
                    notFoundWords.add(word);
                }


            }
        }
    }
}



