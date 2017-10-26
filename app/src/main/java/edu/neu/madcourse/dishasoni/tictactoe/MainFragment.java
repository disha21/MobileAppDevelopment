/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
***/
package edu.neu.madcourse.dishasoni.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.madcourse.dishasoni.MainActivity;
import edu.neu.madcourse.dishasoni.R;
//import edu.neu.madcourse.dishasoni.tictactoe.fcm.MainFCMActivity;

import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.totalScorePhase1;
import static edu.neu.madcourse.dishasoni.tictactoe.Stage1Game.selectedWords;


public class MainFragment extends Fragment {

   private AlertDialog mDialog;


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View rootView =
            inflater.inflate(R.layout.fragment_main, container, false);
          // Handle buttons here...
          View newButton = rootView.findViewById(R.id.new_button);
          View continueButton = rootView.findViewById(R.id.continue_button);
          View aboutButton = rootView.findViewById(R.id.about_button);
          View ackButton = rootView.findViewById(R.id.ack_button);
          View insButton = rootView.findViewById(R.id.ins_button);
          View scoreButton = rootView.findViewById(R.id.score_button);
          View leaderButton = rootView.findViewById(R.id.leader_button);
      // View notificationButton  = rootView.findViewById(R.id.notification_button);

//       notificationButton.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               startActivity(new Intent(getActivity(), MainFCMActivity.class));
//           }
//       });

      newButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             selectedWords  = new String[]{"", "", "", "", "", "", "", "", ""};
             ControlFragment.foundWords.clear();
             ControlFragment.notFoundWords.clear();
             ControlFragment.searchWordInFile.clear();
             totalScorePhase1 = 0;

             if( ControlFragment.remainingTime > 0 ){

                 ControlFragment.countDownTimer.cancel();
             }
             ControlFragment.remainingTime = 0;

             Bundle bundle = getActivity().getIntent().getExtras();
             String user = bundle.getString("name", "");
             Intent intent = new Intent(getActivity(), GameActivity.class);
             intent.putExtra("name",user);
             getActivity().startActivity(intent);


         }
      });
      continueButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            intent.putExtra(GameActivity.KEY_RESTORE, true);
            getActivity().startActivity(intent);
         }
      });

       scoreButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Bundle bundle = getActivity().getIntent().getExtras();
               String user = bundle.getString("name", "");
               Intent intent = new Intent(getActivity(), ScoreBoardActivity.class);
               intent.putExtra("name",user);
               getActivity().startActivity(intent);
           }
       });

       leaderButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getActivity(), LeadeBoardActivity.class);
               getActivity().startActivity(intent);
           }
       });
      aboutButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.about_text);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.ok_label,
                  new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                        // nothing
                     }
                  });
            mDialog = builder.show();
         }
      });
      ackButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.ack_text);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.ok_label,
                    new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                          // nothing
                       }
                    });
            mDialog = builder.show();
         }
      });
      insButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.ins_text);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.ok_label,
                    new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                          // nothing
                       }
                    });
            mDialog = builder.show();
         }
      });
      return rootView;
   }

   @Override
   public void onPause() {
      super.onPause();

      // Get rid of the about dialog if it's still up
      if (mDialog != null)
         mDialog.dismiss();
   }
}
