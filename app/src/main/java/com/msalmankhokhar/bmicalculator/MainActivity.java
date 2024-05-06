package com.msalmankhokhar.bmicalculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView result = findViewById(R.id.result);
        EditText weightInput = findViewById(R.id.weightInput);
        EditText heightInput = findViewById(R.id.heightInput);
        Button submitButton = findViewById(R.id.submitBtn);

        Toast toast = Toast.makeText(getApplicationContext(), "Please enter your height and weight", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onClick(View v) {

                String weight_str = weightInput.getText().toString();
                String height_str = heightInput.getText().toString();

                if ( !weight_str.isEmpty() && !height_str.isEmpty() ){
                    int weight = Integer.parseInt(weight_str);
                    double height = Integer.parseInt(height_str) / 100.0;

                    weightInput.clearFocus();
                    heightInput.clearFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(weightInput.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(heightInput.getWindowToken(), 0);

                    double bmi = weight / (height * height);

                    if (bmi < 18.5) {
                        result.setText(String.format("Oops! You are under-weight and your BMI is %.2f", bmi));
                        result.setTextColor(Color.parseColor("red"));
                    } else if (bmi >= 18.5 && bmi <= 24.9) {
                        result.setText(String.format("Congrats! You are healthy and your BMI is %.2f", bmi));
                        result.setTextColor(Color.parseColor("#3C9C02"));
                    } else if (bmi >= 25.0 && bmi <= 29.9) {
                        result.setText(String.format("Oops! You are over-weight and your BMI is %.2f", bmi));
                        result.setTextColor(Color.parseColor("orange"));
                    } else if (bmi >= 30.0) {
                        result.setText(String.format("Oops! You are obese and your BMI is %.2f and height is %.2f and weight is %d", bmi, height, weight));
                        result.setTextColor(Color.parseColor("red"));
                    }
                } else{
                    if (weight_str.isEmpty()){
                        weightInput.setHintTextColor(Color.parseColor("red"));
                    }if (height_str.isEmpty()){
                        heightInput.setHintTextColor(Color.parseColor("red"));
                    }
                    result.setText("Result shows here");
                    toast.show();
                }
            }
        });
    }
}