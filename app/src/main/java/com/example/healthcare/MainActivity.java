package com.example.healthcare;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageButton profileButton;
    ArrayList<Integer> parentLayout = new ArrayList<Integer>();
    ArrayList<String> data = null;
    EditText nameUser, studentID, userHeight, userWeight, userAge;
    Button buttonOne, buttonTwo, saveButton, cancelButton, femaleButton, maleButton,buttonPlan,button_medal_and_photo;
    static Date date;
    int temp_0=0;

    @Override
    public void onBackPressed() {
        if (parentLayout.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Exit?")
                    .setMessage("Are you going to exit the app?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, (arg0, arg1) -> finish()).create().show();
        }
        else {
            changeLayout(parentLayout.get(parentLayout.size() - 1));
            parentLayout.remove(parentLayout.get(parentLayout.size() - 1));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Calorize");
        changeLayout(R.layout.activity_main);
        date=new java.util.Date();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.quit_settings) {
            MainActivity.this.onBackPressed();
        }

        if (id == R.id.setup_settings) {
            parentLayout.add(R.layout.activity_main);
            changeLayout(R.layout.profile);
        }

        if (id == R.id.credit){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Credit")
                    .setMessage("Authors:\n\nHe Haolan\nhttps://github.com/darthnoward\n\nXu Song\nhttps://github.com/XuSog\n\nWang Han\nhttps://github.com/We-here\n\nAdler\nhttps://github.com/Adler-p\n\nBaroness Placid\nhttps://github.com/placid-brain\n\n\nProject Repository:\nhttps://github.com/sys1d/Calorize")
                    .setPositiveButton(android.R.string.yes, null).create().show();
        }

        if (id == R.id.reset_settings) {
            data.clear();
            data.add("Name");
            data.add("Student ID");
            data.add("Height");
            data.add("Weight");
            data.add("Age");
            data.add("male");
            saveData();
            TextView view = findViewById(R.id.userName);
            view.setText(data.get(0));
            view = findViewById(R.id.studentIDBox);
            view.setText(data.get(1));
            SharedPreferences mPreferences;
            String sharedPrefFile = "com.example.android.mainsharedprefs";
            mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            String DAY_CONSUME_ENERGY_KEY= "DAY CONSUME ENERGY KEY";
            String WEEK_CONSUME_ENERGY_KEY= "YEAR CONSUME ENERGY KEY";
            String MONTH_CONSUME_ENERGY_KEY= "MONTH CONSUME ENERGY KEY";
            preferencesEditor.putString(DAY_CONSUME_ENERGY_KEY,"0");
            preferencesEditor.putString(WEEK_CONSUME_ENERGY_KEY,"0");
            preferencesEditor.putString(MONTH_CONSUME_ENERGY_KEY,"0");
            preferencesEditor.apply();
            SharedPreferences sharedPreferences = getSharedPreferences("medal data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("one", "0");
            Gson gson = new Gson();
            String json = gson.toJson(new ArrayList<String>());
            editor.putString("two", json);
            gson = new Gson();
            json = gson.toJson(new ArrayList<String>());
            editor.putString("three", json);
            editor.putString("four", "easter egg");
            editor.putString("five", "1");
            editor.putString("pic", "00");
            editor.commit();
            profileButton = findViewById(R.id.profilePic);
            profileButton.setBackgroundResource(R.drawable.p00);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void changeLayout(int layout){
        setContentView(layout);

        if (layout == R.layout.activity_main) {
            profileButton = findViewById(R.id.profilePic);
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentLayout.add(R.layout.activity_main);
                    changeLayout(R.layout.profile);
                }
            });

            SharedPreferences sharedPreferences = getSharedPreferences("medal data", MODE_PRIVATE);
            String s1 = sharedPreferences.getString("pic", "00");
            if (s1.equals("00")){
                profileButton.setBackgroundResource(R.drawable.p00);
            }
            if (s1.equals("01")){
                profileButton.setBackgroundResource(R.drawable.p01);
            }
            if (s1.equals("10")){
                profileButton.setBackgroundResource(R.drawable.p10);
            }
            if (s1.equals("11")){
                profileButton.setBackgroundResource(R.drawable.p11);
            }
            if (s1.equals("20")){
                profileButton.setBackgroundResource(R.drawable.p20);
            }
            if (s1.equals("21")){
                profileButton.setBackgroundResource(R.drawable.p21);
            }
            loadData();
            TextView view = findViewById(R.id.userName);
            view.setText(data.get(0));
            view = findViewById(R.id.studentIDBox);
            view.setText(data.get(1));

            buttonOne = findViewById(R.id.buttonOne);
            buttonOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(2).equals("Height") || data.get(3).equals("Weight") || data.get(4).equals("Age")){
                        Toast.makeText(MainActivity. this, "Please set up your information first",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(MainActivity.this, CanteenActivity.class);
                    startActivity(intent);
                }
            });

            buttonTwo = findViewById(R.id.buttonTwo);
            buttonTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(2).equals("Height") || data.get(3).equals("Weight") || data.get(4).equals("Age")){
                        Toast.makeText(MainActivity. this, "Please set up your information first",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(MainActivity.this, EnergyActivity.class);
                    startActivity(intent);
                }
            });

            buttonPlan = findViewById(R.id.plan);
            buttonPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(2).equals("Height") || data.get(3).equals("Weight") || data.get(4).equals("Age")){
                        Toast.makeText(MainActivity. this, "Please set up your information first",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(MainActivity.this, Plan.class);
                    startActivity(intent);
                }
            });

            button_medal_and_photo = findViewById(R.id.button_Medal_and_Photo);
            button_medal_and_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(2).equals("Height") || data.get(3).equals("Weight") || data.get(4).equals("Age")){
                        Toast.makeText(MainActivity. this, "Please set up your information first",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(MainActivity.this, Medal_and_photo.class);
                    startActivity(intent);
                }
            });

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setBackgroundColor(Color.rgb(218, 149, 82));
        }

        if ( layout == R.layout.profile) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setBackgroundColor(Color.rgb(218, 149, 82));
            toolbar.setTitle("Setup Information");
            toolbar.setNavigationIcon(R.drawable.back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { MainActivity.this.onBackPressed(); }
            });
            nameUser = findViewById(R.id.nameUser);
            studentID = findViewById(R.id.studentID);
            saveButton = findViewById(R.id.saveButtonId);
            cancelButton = findViewById(R.id.cancelButton);
            userHeight = findViewById(R.id.userHeight);
            userWeight = findViewById(R.id.userWeight);
            femaleButton = findViewById(R.id.femaleButton);
            maleButton = findViewById(R.id.maleButton);
            userAge = findViewById(R.id.userAge);
            final String[] gender = {"male"};
            userHeight.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
            userWeight.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
            userAge.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
            studentID.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

            if (!data.get(0).equals("Name")) nameUser.setText(data.get(0));
            if (!data.get(1).equals("Student ID")) studentID.setText(data.get(1));
            if (!data.get(2).equals("Height")) userHeight.setText(data.get(2));
            if (!data.get(3).equals("Weight")) userWeight.setText(data.get(3));
            if (!data.get(4).equals("Age")) userAge.setText(data.get(4));

            if (!data.get(5).equals(gender[0])) {
                femaleButton.setTextColor(Color.YELLOW);
                maleButton.setTextColor(Color.WHITE);
            }
            else {
                maleButton.setTextColor(Color.YELLOW);
                femaleButton.setTextColor(Color.WHITE);
            }

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity. this, "Changes discarded",Toast.LENGTH_SHORT).show();
                    MainActivity.this.onBackPressed();
                }
            });

            femaleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    femaleButton.setTextColor(Color.YELLOW);
                    maleButton.setTextColor(Color.WHITE);
                    gender[0] = "female";
                }
            });

            maleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    maleButton.setTextColor(Color.YELLOW);
                    femaleButton.setTextColor(Color.WHITE);
                    gender[0] = "male";
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.set(5,gender[0]);
                    if (! userHeight.getText().toString().isEmpty()) {
                        try{
                            int weight = Integer.parseInt(userHeight.getText().toString());
                            if (weight <= 0 || weight > 300) throw new Exception();
                            data.set(2, userHeight.getText().toString());
                        }
                        catch (Exception e){
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Invalid Data")
                                    .setMessage("Your height should be a number between 1 to 300.")
                                    .setPositiveButton(android.R.string.yes, (arg0, arg1) -> userHeight.setText(data.get(2))).create().show();
                            return ;
                        }
                    }
                    if (! userWeight.getText().toString().isEmpty()) {
                        try{
                            int weight = Integer.parseInt(userWeight.getText().toString());
                            if (weight <= 0 || weight > 125) throw new Exception();
                            data.set(3, userWeight.getText().toString());
                        }
                        catch (Exception e){
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Invalid Data")
                                    .setMessage("Your weight should be a number between 1 to 500.")
                                    .setPositiveButton(android.R.string.yes, (arg0, arg1) -> userWeight.setText(data.get(3))).create().show();
                            return ;
                        }
                    }
                    if (! userAge.getText().toString().isEmpty()) {
                        try{
                            int age = Integer.parseInt(userAge.getText().toString());
                            if (age <= 0 || age > 125) throw new Exception();
                            data.set(4, userAge.getText().toString());
                        }
                        catch (Exception e){
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Invalid Data")
                                    .setMessage("Your age should be a number between 1 to 125.")
                                    .setPositiveButton(android.R.string.yes, (arg0, arg1) -> userAge.setText(data.get(4))).create().show();
                            return ;
                        }
                    }
                    if (! userHeight.getText().toString().isEmpty()) data.set(2, userHeight.getText().toString());
                    if (! userWeight.getText().toString().isEmpty()) data.set(3, userWeight.getText().toString());
                    if (! userAge.getText().toString().isEmpty()) data.set(4, userAge.getText().toString());
                    if (! nameUser.getText().toString().isEmpty()) data.set(0, nameUser.getText().toString());
                    if (! studentID.getText().toString().isEmpty()) data.set(1, studentID.getText().toString());
                    saveData();
                    Toast.makeText(MainActivity. this, "Information saved",Toast.LENGTH_SHORT).show();
                    MainActivity.this.onBackPressed();
                }
            });
        }
    }

    @Override
    protected void onResume(){
        SharedPreferences sharedPreferences = getSharedPreferences("medal data", MODE_PRIVATE);
        String s1 = sharedPreferences.getString("pic", "00");
        if (s1.equals("00")){
            profileButton.setBackgroundResource(R.drawable.p00);
        }
        if (s1.equals("01")){
            profileButton.setBackgroundResource(R.drawable.p01);
        }
        if (s1.equals("10")){
            profileButton.setBackgroundResource(R.drawable.p10);
        }
        if (s1.equals("11")){
            profileButton.setBackgroundResource(R.drawable.p11);
        }
        if (s1.equals("20")){
            profileButton.setBackgroundResource(R.drawable.p20);
        }
        if (s1.equals("21")){
            profileButton.setBackgroundResource(R.drawable.p21);
        }
        super.onResume();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString("data list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("data list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        data = gson.fromJson(json, type);

        if (data == null || data.isEmpty()) {
            data = new ArrayList<String>();
            data.add("Name");
            data.add("Student ID");
            data.add("Height");
            data.add("Weight");
            data.add("Age");
            data.add("male");
        }
    }
    static Date get_Date(){
        return date;
    }
}