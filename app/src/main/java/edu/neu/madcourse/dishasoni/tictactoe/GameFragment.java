/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
 ***/
package edu.neu.madcourse.dishasoni.tictactoe;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.res.Resources;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static android.content.Context.MODE_PRIVATE;
import static edu.neu.madcourse.dishasoni.tictactoe.ControlFragment.*;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.PREF_RESTORE;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.totalScorePhase1;


import edu.neu.madcourse.dishasoni.R;

public class GameFragment extends Fragment {
    static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,
            R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
            R.id.large9,};
    static private int mSmallIds[] = {R.id.small1, R.id.small2, R.id.small3,
            R.id.small4, R.id.small5, R.id.small6, R.id.small7, R.id.small8,
            R.id.small9,};
    private Handler mHandler = new Handler();
    private Tile mEntireBoard = new Tile(this);
    private Tile mLargeTiles[] = new Tile[9];
    private Tile mSmallTiles[][] = new Tile[9][9];
    private Tile.Owner mPlayer = Tile.Owner.UNSELECTED;
    private Set<Tile> mAvailable = new HashSet<Tile>();
    private int mSoundX, mSoundO, mSoundMiss, mSoundRewind;
    private SoundPool mSoundPool;
    private float mVolume = 1f;
    private int mLastLarge;
    private int mLastSmall;
    private List<String> words = new ArrayList<String>();
    private int[] lastMove = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    public static String selectedWord = "";
    public static String[] selectedWords = {"", "", "", "", "", "", "", "", ""};
    public boolean isRestore;
    ArrayList<ArrayList<Integer>> indexes = new ArrayList<>(9);
    Map<Integer, Boolean> map = new HashMap<>();
    String wordsPhase = "";
    public static Vibrator vibrator;

    private View rootView;

    private String gameData = "";
    // private ControlFragment controlFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        initGame();
        updateAllTiles();
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mSoundX = mSoundPool.load(getActivity(), R.raw.sergenious_movex, 1);
        mSoundO = mSoundPool.load(getActivity(), R.raw.sergenious_moveo, 1);
        mSoundMiss = mSoundPool.load(getActivity(), R.raw.erkanozan_miss, 1);
        mSoundRewind = mSoundPool.load(getActivity(), R.raw.joanne_rewind, 1);
        words.add("abatement");
        words.add("burroweed");
        words.add("cichorium");
        words.add("dartboard");
        words.add("fivescore");
        words.add("garrigues");
        words.add("haraucana");
        words.add("welcomely");
        words.add("indirubin");
        Bundle b = this.getActivity().getIntent().getExtras();
        if (b != null) {
            gameData = b.getString("gameData");
            isRestore = b.getBoolean(GameActivity.KEY_RESTORE);

        }
        initializeList();
    }

    private void clearAvailable() {
        mAvailable.clear();
    }

    private void addAvailable(Tile tile) {
        tile.animate();
        mAvailable.add(tile);
    }

    public boolean isAvailable(Tile tile) {
        return mAvailable.contains(tile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =
                inflater.inflate(R.layout.large_board, container, false);
        if (isRestore) {
            String gameData = this.getActivity().getPreferences(MODE_PRIVATE).getString(PREF_RESTORE, null);
            if (gameData != null && gameData != "") {
                putState(gameData);
            }

        }
        initViews(rootView);
        updateAllTiles();

        return rootView;
    }


    private void initializeList() {
        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> n = new ArrayList<>();
            indexes.add(n);
        }
    }

    private void addToList(int i, int j) {
        indexes.get(i).add(j);
    }

    private boolean removeFromList(int i, int j) {
        ArrayList<Integer> l = indexes.get(i);
        if (l.get(l.size() - 1) == j) {
            l.remove(l.size() - 1);
            return true;
        }
        return false;
    }

    private void initViews(View rootView) {
        mEntireBoard.setView(rootView);

        for (int large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);
            String word = words.get(large);
            mLargeTiles[large].setView(outer);
            for (int small = 0; small < 9; small++) {
                final Button inner = (Button) outer.findViewById(mSmallIds[small]);
                final String val = String.valueOf(word.charAt(small));
                inner.setText(val);
                final int fLarge = large;
                final int fSmall = small;
                final Tile smallTile = mSmallTiles[large][small];
                smallTile.setView(inner);
                if (isRestore) {
                    if (mSmallTiles[fLarge][fSmall].getOwner() == Tile.Owner.SELECTED) {
                        inner.setBackgroundColor(getResources().getColor(R.color.blue_color));
                    }
                    if(mSmallTiles[fLarge][fSmall].getOwner() == (Tile.Owner.DONE)){
                        inner.setText("");
                        inner.setBackgroundColor(getResources().getColor(R.color.gray_color));
                    }

                }

//

                inner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        smallTile.animate();

                        if (inner.isPressed()) {
                            if (mSmallTiles[fLarge][fSmall].getOwner() == Tile.Owner.SELECTED) {
                                if (removeFromList(fLarge, fSmall)) {
                                    inner.setBackgroundColor(getResources().getColor(R.color.yellow_color));
                                    mSmallTiles[fLarge][fSmall].setOwner(Tile.Owner.UNSELECTED);
                                    if (indexes.get(fLarge).size() == 0) {
                                        selectedWords[fLarge] = "";
                                    } else {

                                        StringBuilder sb = new StringBuilder(selectedWords[fLarge]);
                                        selectedWords[fLarge] = sb.substring(0, indexes.get(fLarge).size() - 1);

                                    }
                                }
                                Log.d("indexes removed", indexes.toString());

                            } else {
                                if(isValidMove(fLarge, fSmall)){
                                    vibrator.vibrate(50);
//                                    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 150);
//                                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,250);
                                    mLastLarge = fLarge;
                                    mLastSmall = fSmall;
                                    lastMove[mLastLarge] = mLastSmall;
                                    mSmallTiles[fLarge][fSmall].setOwner(Tile.Owner.SELECTED);
                                    selectedWords[fLarge] = selectedWords[fLarge] + val;
                                    addToList(fLarge, fSmall);
                                    Log.d("indexes", indexes.toString());
                                    inner.setBackgroundColor(getResources().getColor(R.color.blue_color));
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Left ,Right or Diagonal Only !", Toast.LENGTH_SHORT).show();
                                }
//                                if (makeMove(mLastLarge,mLastSmall,smallTile )) {
//
//
//                                }else{
//                                    Toast.makeText(getActivity().getApplicationContext(), "Left ,Right or Diagonal Only !", Toast.LENGTH_SHORT).show();
//                                }
                            }


                        }


                    }


                });


            }
        }
    }


//    private void makeMove(int large, int small) {
//        mLastLarge = large;
//        mLastSmall = small;
//        Tile smallTile = mSmallTiles[large][small];
//        Tile largeTile = mLargeTiles[large];
//        smallTile.setOwner(mPlayer);
//        setAvailableFromLastMove(small);
//        Tile.Owner oldWinner = largeTile.getOwner();
//        Tile.Owner winner = largeTile.findWinner();
//        if (winner != oldWinner) {
//            largeTile.animate();
//            largeTile.setOwner(winner);
//        }
//        winner = mEntireBoard.findWinner();
//        mEntireBoard.setOwner(winner);
//        updateAllTiles();
//        if (winner != Tile.Owner.UNSELECTED) {
//            ((GameActivity) getActivity()).reportWinner(winner);
//        }
//    }

    private boolean isValidMove(int i, int j){
        Log.d("isValid Move" , "i " + i + " j " + j);
        ArrayList<Integer> arr = indexes.get(i);
        Log.d("isvalidMove array" , arr.toString());
        if(arr.size() == 0)
            return true;
        else {
            int prev = arr.get(arr.size() - 1);
            Log.d("isvalidMove else" , "" + prev);
            List<Integer> a = findNextMove(prev);
            Log.d("isvalidMove list" , "" + a.toString());
            return findNextMove(prev).contains(j);
        }
    }


    protected void hideGame() {
        mEntireBoard.getView().setVisibility(View.INVISIBLE);

    }

    protected void showGame() {
        mEntireBoard.getView().setVisibility(View.VISIBLE);
    }
    public void restartGame() {
        ControlFragment.foundWords.clear();
        ControlFragment.notFoundWords.clear();
        ControlFragment.searchWordInFile.clear();
        selectedWords = new String[]{"", "", "", "", "", "", "", "", ""};
        totalScorePhase1 = 0;
        initializeList();
        Intent intent = new Intent(getActivity(), GameActivity.class);
        getActivity().startActivity(intent);
        GameActivity.mMediaPlayer.stop();

    }


    public void initGame() {
        Log.d("UT3", "init game");
        mEntireBoard = new Tile(this);
        // Create all the tiles
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large] = new Tile(this);
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small] = new Tile(this);
            }
            mLargeTiles[large].setSubTiles(mSmallTiles[large]);
        }
        mEntireBoard.setSubTiles(mLargeTiles);

        // If the player moves first, set which spots are available
        mLastSmall = -1;
        mLastLarge = -1;
        setAvailableFromLastMove(mLastSmall);
    }

    private void setAvailableFromLastMove(int small) {
        clearAvailable();
        // Make all the tiles at the destination available
        if (small != -1) {
            for (int dest = 0; dest < 9; dest++) {
                Tile tile = mSmallTiles[small][dest];
                if (tile.getOwner() == Tile.Owner.SELECTED)
                    addAvailable(tile);
            }
        }
        // If there were none available, make all squares available
        if (mAvailable.isEmpty()) {
            setAllAvailable();
        }
    }

    private void setAllAvailable() {
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile tile = mSmallTiles[large][small];
                if (tile.getOwner() == Tile.Owner.SELECTED)
                    addAvailable(tile);
            }
        }
    }


    private void updateAllTiles() {

        mEntireBoard.updateDrawableState();
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large].updateDrawableState();
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small].updateDrawableState();

            }
        }


    }


    public void updateView() {
        Log.d("inside update view", "update view");
        for (int j = 0; j < 9; j++) {
            View outer = rootView.findViewById(mLargeIds[j]);
            ArrayList<Integer> ll = new ArrayList<Integer>();
            if (indexes.get(j).size() > 0) {
                ArrayList<Integer> l = indexes.get(j);
                for (int i = 0; i < 9; i++) {
                    if (l.contains(i)) {
                    } else {
                        ll.add(i);
                    }
                }
            }
            for (int k = 0; k < ll.size(); k++) {
                Log.d("id", ll.toString());
                mSmallTiles[j][ll.get(k)].setOwner(Tile.Owner.DONE);
                Button inner = (Button) outer.findViewById(mSmallIds[ll.get(k)]);
                inner.setText("");
                inner.setBackgroundColor(getResources().getColor(R.color.gray_color));

            }
        }


    }

    public void updateViewForPhase2(){
      //  mEntireBoard.updateDrawableState();
        for (int large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);
            for (int small = 0; small < 9; small++) {
                Button inner = (Button) outer.findViewById(mSmallIds[small]);
                if(mSmallTiles[large][small].getOwner() == Tile.Owner.SELECTED);
                mSmallTiles[large][small].setOwner(Tile.Owner.UNSELECTED);
                inner.setBackgroundColor(getResources().getColor(R.color.yellow_color));
            }
        }
    }


    /**
     * Create a string containing the state of the game.
     */
    public String getState() {
        StringBuilder builder = new StringBuilder();
        builder.append(mLastLarge);
        builder.append(',');
        builder.append(mLastSmall);
        builder.append(',');
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                builder.append(mSmallTiles[large][small].getOwner().name());
                builder.append(',');
            }
        }
        Log.d("out", builder.toString());
        return builder.toString();
    }

    /**
     * Restore the state of the game from the given string.
     */
    public void putState(String gameData) {
        String[] fields = gameData.split(",");
        int index = 0;
        mLastLarge = Integer.parseInt(fields[index++]);
        mLastSmall = Integer.parseInt(fields[index++]);
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile.Owner owner = Tile.Owner.valueOf(fields[index++]);
                mSmallTiles[large][small].setOwner(owner);

            }
        }
        //  setAvailableFromLastMove(mLastSmall);
        updateAllTiles();
         updateView();
    }



    public List<Integer> findNextMove(Integer num){
        List<Integer> list1 = new ArrayList<Integer>();
        switch (num){
            case -1:
                list1.addAll(Arrays.asList(0,1,2,3,4,5,6,7,8));
                break;
            case 0:
                list1.addAll(Arrays.asList(1,3,4));
                break;
            case 1:
                list1.addAll(Arrays.asList(0,2,3,4,5));
                break;
            case 2:
                list1.addAll(Arrays.asList(1,4,5));
                break;
            case 3:
                list1.addAll(Arrays.asList(0,1,4,6,7));
                break;
            case 4:
                list1.addAll(Arrays.asList(0,1,2,3,5,6,7,8));
                break;
            case 5:
                list1.addAll(Arrays.asList(1,2,4,7,8));
                break;
            case 6:
                list1.addAll(Arrays.asList(3,4,7));
                break;
            case 7:
                list1.addAll(Arrays.asList(3,4,5,6,8));
                break;
            case 8:
                list1.addAll(Arrays.asList(4,5,7));
                break;

        }
        return list1;

    }

    private boolean makeMove(int large, int small,Tile tile) {
        boolean canMove = true;
        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();
        if(small == -1 || large == -1){
            return canMove;
        }
        if(tile.getLargePos() != large){
            list2 = findNextMove(lastMove[tile.getLargePos()]);
            if(list2.contains(tile.getSmallPos())){
                return canMove;
            }else{
                return !canMove;
            }
        }else{
            list1 = findNextMove(small);
            if(list1.contains(tile.getSmallPos())){
                return canMove;
            }else{
                return false;
            }
        }
    }
}
