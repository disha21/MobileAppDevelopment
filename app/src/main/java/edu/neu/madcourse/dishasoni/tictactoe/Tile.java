/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
***/
package edu.neu.madcourse.dishasoni.tictactoe;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import edu.neu.madcourse.dishasoni.R;

public class Tile {

   public enum Owner {
      UNSELECTED,SELECTED,DONE
   }

   // These levels are defined in the drawable definitions
   private static final int LEVEL_LETTER_UNSELECTED = 0;
   private static final int LEVEL_LETTER_SELECTED = 1; // letter O
   private static final int LEVEL_DONE = 2;

   private final GameFragment mGame;
   private Owner mOwner = Owner.UNSELECTED;
   private View mView;
   private Tile mSubTiles[];
    private int largePos;
    private int smallPos;

   public Tile(GameFragment game) {
      this.mGame = game;
   }

   public View getView() {
      return mView;
   }

   public void setView(View view) {
      this.mView = view;
   }

   public Owner getOwner() {
      return mOwner;
   }

   public void setOwner(Owner owner) {
      this.mOwner = owner;
   }


   public Tile[] getSubTiles() {
      return mSubTiles;
   }

   public void setSubTiles(Tile[] subTiles) {
      this.mSubTiles = subTiles;
   }
   public int getLargePos(){
       return largePos;
   }
    public int getSmallPos(){
        return smallPos;
    }

    public void setlargePos(int large) {
        this.largePos = large;
    }

    public void setSmallPos(int small) {
        this.smallPos = small;
    }

   public void updateDrawableState() {
      if (mView == null) return;
      int level = getLevel();
      if (mView.getBackground() != null) {
         mView.getBackground().setLevel(level);
      }
   }

   private int getLevel() {
      int level = LEVEL_DONE;
      switch (mOwner) {
         case UNSELECTED:
            level = LEVEL_LETTER_UNSELECTED;
            break;
         case SELECTED: // letter O
            level = LEVEL_LETTER_SELECTED;
            break;
         case DONE:
            level = LEVEL_DONE;
            break;
      }
      return level;
   }



   public void animate() {
      Animator anim = AnimatorInflater.loadAnimator(mGame.getActivity(),
            R.animator.tictactoe);
      if (getView() != null) {
         anim.setTarget(getView());
         anim.start();
      }
   }

   private void countCaptures(int totalX[], int totalO[]) {
      int capturedX, capturedO;
      // Check the horizontal
      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 3; col++) {
            Owner owner = mSubTiles[3 * row + col].getOwner();

         }

      }
   }

   public Owner findWinner() {
      // If owner already calculated, return it
      if (getOwner() != Owner.DONE)
         return getOwner();

      return Owner.DONE;
   }

}
