package edu.neu.madcourse.dishasoni;

import android.content.res.Resources;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class DictionaryActivity extends AppCompatActivity {

    String currentFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        this.setTitle("Test Dictionary");
        final TreeSet<String> searchWordFile = new TreeSet<String>();
        EditText searchWord = (EditText) findViewById(R.id.wordInput);
        final TextView displayWords = (TextView) findViewById(R.id.ViewWords);

        searchWord.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String word  =  s.toString().toLowerCase();
                if (word.length() >= 3) {
                    String prefix = word.substring(0, 3);
                    if (currentFile != null & prefix == currentFile) {
                        if (searchWordFile.contains(word)) {
                            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 150);
                            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,250);
                            displayWords.setText(word.concat("\n" + displayWords.getText().toString()));
                        }
                    } else {
                        searchWordFile.clear();
                        try {
                            String line = "";
                            Resources myResources = getResources();
                            InputStream file = getResources().openRawResource(
                                    getResources().getIdentifier(prefix.concat("_word"),
                                            "raw", getPackageName()));
                            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
                            while ((line = reader.readLine()) != null) {
                                searchWordFile.add(line.toLowerCase());
                            }
                            file.close();
                            currentFile = prefix;
                        } catch (Exception e) {
                            //log the exception.
                            e.printStackTrace();

                        }
                        if (searchWordFile.contains(word)) {
                            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 150);
                            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,250);
                            displayWords.setText(word.concat("\n" + displayWords.getText().toString()));

                        }

                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void clearAll(View view) {
        EditText searchWord = (EditText) findViewById(R.id.wordInput);
        TextView displayWords = (TextView) findViewById(R.id.ViewWords);
        displayWords.setText("");
        searchWord.setText("");
    }

}
