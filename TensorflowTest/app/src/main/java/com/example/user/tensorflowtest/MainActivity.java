package com.example.user.tensorflowtest;

import android.app.Activity;
import android.content.Context;
import android.content.Entity;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Bundle;
import android.util.Log;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextSelection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    public static final String TAG = "TextClassificationText";
    private Handler handler= new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextClassificationManager tcm = (TextClassificationManager)getSystemService(TextClassificationManager.class);
        final TextClassifier tc = tcm.getTextClassifier();

        final String phoneNumber = "090-1111-1111";
        final String url = "https://www.google.co.jp/";
        final String email = "testes@gmail.com";
        final String address = "Google Inc, 12396 Grant St, Thornton, CO 80241 USA";
        final String address2 = "Mr. John Brown, 2352 E. Evans Ave., Denver Colo 80210-3310, USA";
        final String address3 = "東京都新宿区西新宿２丁目８−１";
        final String other = "This is an apple";
        final String other2 = "Please call at " + phoneNumber;
        final List<String> texts = new ArrayList<>(Arrays.asList(phoneNumber, url, email, address, address2, address3, other, other2));

        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run()");
                // TextClassification#classifyTextの検証
                for (String text : texts) {
                    TextClassification result = tc.classifyText(text, 0, text.length(), LocaleList.getDefault());
                    int entCount = result.getEntityCount();
                    Log.d(TAG, "********************************");
                    Log.d(TAG, "Text : " + text);
                    Log.d(TAG, "EntityCount : " + String.valueOf(entCount));
                    for (int i = 0; i < entCount; i++) {
                        String ent = result.getEntity(i);
                        Log.d(TAG, "Entity : " + ent + ", Precision : " + result.getConfidenceScore(ent) + ", Classified Text : " + result.getText());
                    }
                }
                Log.d(TAG, " ");
                // TextSelection.suggestSelectionの検証
                for (String text : texts) {
                    TextSelection result = tc.suggestSelection(text, 0, text.length(), LocaleList.getDefault());
                    int entCount = result.getEntityCount();
                    Log.d(TAG, "********************************");
                    Log.d(TAG, "Text : " + text);
                    Log.d(TAG, "EntityCount : " + String.valueOf(entCount));
                    for (int i = 0; i < entCount; i++) {
                        String ent = result.getEntity(i);
                        Log.d(TAG, "Telected Text : " + text.substring(result.getSelectionStartIndex(), result.getSelectionEndIndex()));
                        Log.d(TAG, "Entity : " + ent + ", Precision : " + result.getConfidenceScore(ent));
                    }
                }
            }
        });
    }
}
