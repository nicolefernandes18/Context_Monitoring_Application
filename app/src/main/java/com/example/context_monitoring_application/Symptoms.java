package com.example.context_monitoring_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.Button;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class Symptoms extends AppCompatActivity {
    String[] symptoms = {"Cough", "Diarrhea", "Feeling tired", "Fever", "Headache", "Loss of Smell or Taste", "Muscle Ache", "Nausea", "Shortness of breath", "Sore Throat"};
    Set<String> symptomList = new HashSet<>(Arrays.asList("heart_rate", "resp_rate", "cough", "diarrhea", "feeling_tired", "fever", "headache", "loss_of_smell_or_taste", "muscle_ache", "nausea", "shortness_of_breath", "sore_throat"));
    private HashMap<String, Float> healthRating = new HashMap<>();

    DatabaseManager dbManager;

    AutoCompleteTextView autoCompleteTxt;

    private String symptom;
    private Button uploadBtn;
    private RatingBar ratingBar;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        dbManager = new DatabaseManager(this);

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        ratingBar = findViewById(R.id.ratingBar);

        uploadBtn = findViewById(R.id.uploadSym);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_symptom, symptoms);

        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                symptom = adapterView.getItemAtPosition(i).toString();
                symptom = convertToDBColumn(symptom);

                ratingBar.setOnRatingBarChangeListener(null);

                ratingBar.setRating(0);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        float rating = ratingBar.getRating();
                        Toast.makeText(getApplicationContext(), symptom + " " + rating, Toast.LENGTH_SHORT).show();
                        healthRating.put(symptom, rating);

                    }
                });

            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                try{
                    dbManager.open();
                }catch (Exception e){
                    e.printStackTrace();
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);

                for(String symList: symptomList){
                    float rating = 0;

                    if(healthRating.containsKey(symList)){
                        rating = healthRating.get(symList);
                    }
                    contentValues.put(symList, rating);
                }

                dbManager.insert(contentValues);
                Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_SHORT).show();


                dbManager.close();

                healthRating.clear();


            }
        });
    }

    public String convertToDBColumn(String symptomOriginal){
        return symptomOriginal.toLowerCase().replace(" ","_");
    }
}