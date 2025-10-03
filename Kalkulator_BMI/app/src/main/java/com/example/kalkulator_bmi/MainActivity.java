package com.example.kalkulator_bmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText waga,wzrost;
    private Button btn_oblicz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        waga=findViewById(R.id.waga);
        wzrost=findViewById(R.id.wzrost);

        btn_oblicz=findViewById(R.id.btn_oblicz);

        String error_empty,error_values;

        error_empty=getResources().getString(R.string.error_empty_fields);

        error_values=getResources().getString(R.string.error_zero_values);
        btn_oblicz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String waga_info,wzrost_info;
                waga_info=waga.getText().toString().trim();

                wzrost_info=wzrost.getText().toString().trim();

                Double wzrost_info_double;

                //wzrost_info_double=Double.valueOf(wzrost_info);
                if(wzrost_info.equals("") || waga_info.equals("")){
                    Toast.makeText(MainActivity.this,error_empty, Toast.LENGTH_LONG).show();
                }
                /*if (wzrost_info_double <= 0) {
                    Toast.makeText(MainActivity.this,error_values, Toast.LENGTH_LONG).show();
                }*/

            }
        });

    }
}