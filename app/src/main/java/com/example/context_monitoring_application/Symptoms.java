package com.example.context_monitoring_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class Symptoms extends AppCompatActivity {

    String[] symptoms = {"Cough", "Diarrhea", "Feeling tired", "Fever", "Headache", "Loss of Smell or Taste", "Muscle Ache", "Nausea", "Shortness of breath", "Sore Throat"};

    AutoCompleteTextView autoCompleteTxt;

    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_symptom, symptoms);

        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String symptom = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Symptom: "+symptom, Toast.LENGTH_SHORT).show();
            }
        });
    }
}