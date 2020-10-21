package com.example.converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonmtokm = (Button) findViewById(R.id.buttonmtokm);
        buttonmtokm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textBoxMiles = (EditText) findViewById(R.id.editm);
                EditText textBoxKm = (EditText) findViewById(R.id.editkm);
                double vMiles = Double.valueOf(textBoxMiles.getText().toString());
                double vKm = (vMiles /0.62137);
                DecimalFormat formatVal = new DecimalFormat("##.##");
                textBoxKm.setText(formatVal.format(vKm));
            }
        });
        Button buttonkmtom = (Button) findViewById(R.id.buttonkmtom);
        buttonkmtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textBoxMiles = (EditText) findViewById(R.id.editm);
                EditText textBoxKm = (EditText) findViewById(R.id.editkm);
                double vKm = Double.valueOf(textBoxKm.getText().toString());
                double vMiles = (vKm *0.621371);
                DecimalFormat formatVal = new DecimalFormat("##.##");
                textBoxMiles.setText(formatVal.format(vMiles));
            }
        });
    }
}
