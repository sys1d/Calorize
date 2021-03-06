package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FoodDetail extends AppCompatActivity {

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;
    Button cancelButton;
    Button saveButton;
    String[] data;
    TextView index;

    Double DayEnergy;
    Double WeekEnergy;
    Double MonthEnergy;

    Integer dayDate;
    Integer weekDate;
    Integer monthDate;

    Integer preDayDate;
    Integer preWeekDate;
    Integer preMonthDate;
    Calendar calendar = Calendar.getInstance();

    public static SharedPreferences mPreferences;
    public static final String sharedPrefFile = "com.example.android.mainsharedprefs";

    public static final String DAY_DATE_KEY = "DAY DATE KEY";
    public static final String WEEK_DATE_KEY = "WEEK DATE KEY";
    public static final String MONTH_DATE_KEY = "MONTH DATE KEY";

    public static final String DAY_CONSUME_ENERGY_KEY= "DAY CONSUME ENERGY KEY";
    public static final String WEEK_CONSUME_ENERGY_KEY= "YEAR CONSUME ENERGY KEY";
    public static final String MONTH_CONSUME_ENERGY_KEY= "MONTH CONSUME ENERGY KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            data = getData();
        } catch (Exception e) {
            Toast.makeText(FoodDetail.this, e.toString(),Toast.LENGTH_LONG).show();
            finish();
        }
        setContentView(R.layout.food_detail);
        ImageView picture = findViewById(R.id.picture);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            int temp = bundle.getInt("picture");
            picture.setImageResource(temp);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setBackgroundColor(Color.rgb(218, 149, 82));
        toolbar.setTitle(data[0]);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
        cancelButton = findViewById(R.id.cancelButtonFD);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveButton = findViewById(R.id.saveButtonFD);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Toast.makeText(FoodDetail.this, data[0] + " Selected",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        index = findViewById(R.id.index);
        index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FoodDetail.this)
                        .setTitle("Health Level Index")
                        .setMessage("An index for indicating how healthy this food is.\nThe closer the sum of food eaten per meal to 100, the healthier the meal is.")
                        .setPositiveButton(android.R.string.yes, null).create().show();
            }
        });

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        dayDate= calendar.get(Calendar.DAY_OF_MONTH);
        weekDate=calendar.get(Calendar.DAY_OF_WEEK);
        monthDate=calendar.get(Calendar.MONTH)+1;

        if(mPreferences!=null){
            DayEnergy=Double.valueOf(mPreferences.getString(DAY_CONSUME_ENERGY_KEY,"0.0"));
            WeekEnergy=Double.valueOf(mPreferences.getString(WEEK_CONSUME_ENERGY_KEY,"0.0"));
            MonthEnergy=Double.valueOf(mPreferences.getString(MONTH_CONSUME_ENERGY_KEY,"0.0"));
        }else{
            DayEnergy=0.0; WeekEnergy=0.0; MonthEnergy=0.0;
        }

        dayDate = calendar.get(Calendar.DAY_OF_MONTH);
        weekDate = calendar.get(Calendar.DAY_OF_WEEK);
        monthDate = calendar.get(Calendar.MONTH);

        if (mPreferences != null) {
            preDayDate = mPreferences.getInt(DAY_DATE_KEY, -1);
            preWeekDate = mPreferences.getInt(WEEK_DATE_KEY, -1);
            preMonthDate = mPreferences.getInt(MONTH_DATE_KEY, -1);
        } else {
            preDayDate = -1;
            preWeekDate = -1;
            preMonthDate = -1;
        }

        if (dayDate != preDayDate) {
            DayEnergy = 0.0;
        }
        if (weekDate == 2 && weekDate != preWeekDate) {
            WeekEnergy = 0.0;
        } else if (preWeekDate == -1) {
            MonthEnergy = 0.0;
        }
        if (monthDate != preMonthDate) {
            MonthEnergy = 0.0;
        }

        barChart = findViewById(R.id.BarChart);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setLabelCount(4, true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setValueFormatter(new MyXAxisFormatter());
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setTextSize(10f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(150f);
        yAxis.setGranularity(150f);
        yAxis.setLabelCount(7, true);
        yAxis.setTextColor(Color.BLACK);
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        getEntries();
        barDataSet = new BarDataSet(barEntries, "Nutrients");
        barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(18f);

        barData = new BarData(barDataSet);
        barChart.setData(barData);
        // disable description
        Description description = barChart.getDescription();
        description.setEnabled(false);
        barChart.setDescription(description);
        barChart.animateY(1200);
    }

    private void getEntries() {
        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1.5f, Integer.valueOf(data[1])));
        barEntries.add(new BarEntry(3f, Integer.valueOf(data[2])));
        barEntries.add(new BarEntry(4.5f, Integer.valueOf(data[3])));
        barEntries.add(new BarEntry(6f, Integer.valueOf(data[4])));
    }

    class MyXAxisFormatter implements IAxisValueFormatter{
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (value <= 2f) return "Energy";
            if (value <= 4f) return "Protein";
            if (value <= 5f) return "Cholesterol";
            return "Fat";
        }
    }

    private void saveData(){
        Intent intent = getIntent();
        List<String> data = intent.getStringArrayListExtra(HealthySoupActivity.KEY);
        DayEnergy+=Double.valueOf(data.get(1));
        WeekEnergy+=Double.valueOf(data.get(1));
        MonthEnergy+=Double.valueOf(data.get(1));

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(DAY_CONSUME_ENERGY_KEY,DayEnergy+"");
        preferencesEditor.putString(WEEK_CONSUME_ENERGY_KEY,WeekEnergy+"");
        preferencesEditor.putString(MONTH_CONSUME_ENERGY_KEY,MonthEnergy+"");
        preferencesEditor.apply();
    }
    
    private String[] getData(){
        Intent intent = getIntent();
        List<String> dataGet = intent.getStringArrayListExtra("food");
        String[] foodDetail = dataGet.toArray(new String[0]);
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("data list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> data = gson.fromJson(json, type);
        User user = new User(Double.valueOf(data.get(2)),Double.valueOf(data.get(3)), data.get(5),Integer.valueOf(data.get(4)));
        String[] dataAdvised = user.advise();
        String[] dataString = new String[5];
        dataString[0] = foodDetail[0];
        dataString[1] = String.valueOf(Integer.valueOf((int) (Double.valueOf(foodDetail[1])*200/Double.valueOf(dataAdvised[0]))));
        dataString[2] = String.valueOf(Integer.valueOf((int) (Double.valueOf(foodDetail[2])*200/Double.valueOf(dataAdvised[1]))));
        dataString[3] = String.valueOf(Integer.valueOf((int) (Double.valueOf(foodDetail[3])*3000/Double.valueOf(dataAdvised[2]))));
        dataString[4] = String.valueOf(Integer.valueOf((int) (Double.valueOf(foodDetail[4])*100/Double.valueOf(dataAdvised[3]))));
        return dataString;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(DAY_DATE_KEY,dayDate);
        preferencesEditor.putInt(WEEK_DATE_KEY,weekDate);
        preferencesEditor.putInt(MONTH_DATE_KEY,monthDate);
        preferencesEditor.apply();
    }

    class User {
        private double height,weight;
        private String sex;
        private int year;
        User(double height,double weight,String sex,int year){
            this.height=height;
            this.weight=weight;
            this.sex=sex;
            this.year=year;
        }
        public String[] advise(){
            String[] result=new String[4];
            BigDecimal energy,protein,cholesterol,fat;

            if (this.sex=="male"){
                energy=new BigDecimal(66+13.7*this.weight+5*this.height-6.8*year);
                protein=new BigDecimal(1.1*this.weight);
            }else{
                energy=new BigDecimal(655+9.6*this.weight+1.8*this.height-4.7*year);
                protein=new BigDecimal(0.9*this.weight);
            }
            cholesterol=new BigDecimal(600);
            fat=energy.multiply(new BigDecimal(0.25*0.111));
            result[0]=energy.toString().substring(0,4);
            result[1]=protein.toString();
            result[2]=cholesterol.toString();
            result[3]=fat.toString().substring(0,4);
            return result;
        }
    }

}