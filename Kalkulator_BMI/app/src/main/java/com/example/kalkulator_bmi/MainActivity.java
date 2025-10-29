package com.example.kalkulator_bmi;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String WAGA_KEY = "WAGA";
    private static final String WZROST_KEY = "WZROST";
    private EditText waga, wzrost;
    private Button btn_oblicz,btn_wyczysc;
    private TextView wynik,interpretacja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        waga = findViewById(R.id.waga);
        wzrost = findViewById(R.id.wzrost);

        btn_oblicz = findViewById(R.id.btn_oblicz);
        btn_wyczysc = findViewById(R.id.btn_wyczysc);

        wynik=findViewById(R.id.wynik);
        interpretacja=findViewById(R.id.intepretacja);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        String savedWaga = prefs.getString(WAGA_KEY, "Brak zapisanych danych.");
        String savedWzrost = prefs.getString(WZROST_KEY, "Brak zapisanych danych.");

        waga.setText(savedWaga);
        wzrost.setText(savedWzrost);
        btn_oblicz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

                editor.putString(WAGA_KEY,waga.getText().toString().trim());
                editor.putString(WZROST_KEY,wzrost.getText().toString().trim());

                editor.apply();

                calculateBmi();
            }
        });

        btn_wyczysc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waga.setText("");
                wzrost.setText("");

                wynik.setText(R.string.bmi_result_prefix);
                interpretacja.setText("");
            }
        });

    }

    private void calculateBmi(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        String waga_info, wzrost_info;

        waga_info = waga.getText().toString().trim();
        wzrost_info = wzrost.getText().toString().trim();

        if (wzrost_info.equals("") || waga_info.equals("")) {

            builder.setTitle("Błąd walidacji").setMessage(R.string.error_empty_fields).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();

        }else {

            Double wzrost_info_double, waga_info_double;

            wzrost_info_double = Double.valueOf(wzrost_info);
            waga_info_double = Double.valueOf(waga_info);

            if (wzrost_info_double <= 0 || waga_info_double <= 0) {
                builder.setTitle("Błąd walidacji").setMessage(R.string.error_zero_values).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
            }

            Double wynik_BMI=waga_info_double / Math.pow(wzrost_info_double / 100.0,2);

            if(wynik_BMI < 18.5){

                interpretacja.setText(R.string.bmi_underweight);

                interpretacja.setTextColor(getResources().getColor(R.color.status_bad,null));

            } else if (wynik_BMI >= 18.5 && wynik_BMI <= 24.9) {

                interpretacja.setText(R.string.bmi_normal);

                interpretacja.setTextColor(getResources().getColor(R.color.status_good,null));

            } else if(wynik_BMI >= 25.0 && wynik_BMI <= 29.9){

                interpretacja.setText(R.string.bmi_overweight);

                interpretacja.setTextColor(getResources().getColor(R.color.status_warning,null));

            } else if(wynik_BMI >= 30.0){

                interpretacja.setText(R.string.bmi_obesity);

                interpretacja.setTextColor(getResources().getColor(R.color.status_bad,null));

            }
            wynik.setText("Twoje BMI wynosi "+String.format("%.2f", wynik_BMI));

        }
    }
}