package com.example.healthcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HealthySoupActivity extends AppCompatActivity {

    CustomImageButton porkSoupButton;
    CustomImageButton whiteRiceButton;
    CustomImageButton brownRiceButton;
    CustomImageButton vegetableButton;
    CustomImageButton tufoButton;
    CustomImageButton pigEarButton;
    CustomImageButton pigTongueButton;
    CustomImageButton chickenMeatButton;
    CustomImageButton chickenLiverButton;

    public static String KEY="";


    ArrayList<String> data=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthy_soup_layout);

        porkSoupButton=findViewById(R.id.porkSoupButton);
        whiteRiceButton=findViewById(R.id.whiteRiceButton);
        brownRiceButton=findViewById(R.id.brownRiceButton);
        vegetableButton=findViewById(R.id.vegetableButton);
        tufoButton=findViewById(R.id.tufoButton);
        pigEarButton=findViewById(R.id.pigEarButton);
        pigTongueButton=findViewById(R.id.pigTongueButton);
        chickenMeatButton=findViewById(R.id.chickenMeatButton);
        chickenLiverButton=findViewById(R.id.chickenLiverButton);

        porkSoupButton.setText("Pork Soup");
        porkSoupButton.setTextSize(80f);
        porkSoupButton.setColor(Color.BLACK);


        whiteRiceButton.setText("White Rice");
        whiteRiceButton.setTextSize(80f);
        whiteRiceButton.setColor(Color.BLACK);

        brownRiceButton.setText("Brown Rice");
        brownRiceButton.setColor(Color.BLACK);
        brownRiceButton.setTextSize(80f);

        vegetableButton.setText("Vegetable");
        vegetableButton.setTextSize(80f);
        vegetableButton.setColor(Color.BLACK);

        tufoButton.setText("Tufo");
        tufoButton.setColor(Color.BLACK);
        tufoButton.setTextSize(80f);

        pigEarButton.setText("Pig Ear");
        pigEarButton.setTextSize(80f);
        pigEarButton.setColor(Color.BLACK);

        pigTongueButton.setText("Pig Tongue");
        pigTongueButton.setColor(Color.BLACK);
        pigTongueButton.setTextSize(80f);

        chickenMeatButton.setText("Chicken Meat");
        chickenMeatButton.setTextSize(80f);
        chickenMeatButton.setColor(Color.BLACK);

        chickenLiverButton.setText("Chicken Liver");
        chickenLiverButton.setColor(Color.BLACK);
        chickenLiverButton.setTextSize(80f);



        porkSoupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthySoupActivity.this, FoodDetail.class);
                KEY="PORK SOUP KEY";
                data.clear();
                data.add("PORK SOUP");  // TO BE FILLED WITH ACTUAL VALUE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                data.add("144.41");
                data.add("18");
                data.add("6");
                data.add("41.36");
                Log.e("TAG",data.get(0));
                intent.putExtra(KEY,data);
                startActivity(intent);
            }
        });


    }


}