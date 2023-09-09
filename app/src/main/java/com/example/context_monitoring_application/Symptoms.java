package com.example.context_monitoring_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
    Set<String> symptomList = new HashSet<>(Arrays.asList("cough", "diarrhea", "feeling_tired", "fever", "headache", "loss_of_smell_or_taste", "muscle_ache", "nausea", "shortness_of_breath", "sore_throat"));
    private HashMap<String, Float> healthRating = new HashMap<>();

    AutoCompleteTextView autoCompleteTxt;
    private String symptom;
    private Button uploadBtn;
    private RatingBar ratingBar;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

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
                        healthRating.put(symptom, rating);

                    }
                });

            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                try{
                    float cough = healthRating.get("cough")!= null ? healthRating.get("cough") : 0;
                    float diarrhea = healthRating.get("diarrhea")!= null ? healthRating.get("diarrhea") : 0;
                    float feeling_tired = healthRating.get("feeling_tired")!= null ? healthRating.get("feeling_tired") : 0;
                    float fever = healthRating.get("fever")!= null ? healthRating.get("fever") : 0;
                    float headache = healthRating.get("headache")!= null ? healthRating.get("headache") : 0;
                    float loss_of_smell_or_taste = healthRating.get("loss_of_smell_or_taste")!= null ? healthRating.get("loss_of_smell_or_taste") : 0;
                    float muscle_ache = healthRating.get("muscle_ache")!= null ? healthRating.get("muscle_ache") : 0;
                    float nausea = healthRating.get("nausea")!= null ? healthRating.get("nausea") : 0;
                    float shortness_of_breath = healthRating.get("shortness_of_breath")!= null ? healthRating.get("shortness_of_breath") : 0;
                    float sore_throat = healthRating.get("sore_throat")!= null ? healthRating.get("sore_throat") : 0;

                    int id = databaseHelper.getId();

                    databaseHelper.update(id, cough, diarrhea, feeling_tired, fever, headache, loss_of_smell_or_taste, muscle_ache, nausea, shortness_of_breath, sore_throat);
                    healthRating.clear();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



    public String convertToDBColumn(String symptomOriginal){
        return symptomOriginal.toLowerCase().replace(" ","_");
    }
}