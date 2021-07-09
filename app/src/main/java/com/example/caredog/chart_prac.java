package com.example.caredog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class chart_prac extends Activity {

    ArrayList<Integer> jsonList = new ArrayList<>(); // ArrayList 선언
    ArrayList<String> labelList = new ArrayList<>(); // ArrayList 선언
    BarChart barChart;
    TextView minuteTextview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        barChart = (BarChart) findViewById(R.id.walk_chart);
        graphInitSetting();       //그래프 기본 세팅

        BarChartGraph(labelList, jsonList);
        barChart.setTouchEnabled(false); //확대 불가능
        barChart.getAxisRight().setAxisMaxValue(80);
        barChart.getAxisLeft().setAxisMaxValue(80);

    }
    public void graphInitSetting(){

        labelList.add("일");
        labelList.add("월");
        labelList.add("화");
        labelList.add("수");
        labelList.add("목");
        labelList.add("금");
        labelList.add("토");

        jsonList.add(10);
        jsonList.add(20);
        jsonList.add(30);
        jsonList.add(40);
        jsonList.add(50);
        jsonList.add(60);
        jsonList.add(70);


        BarChartGraph(labelList, jsonList);
        barChart.setTouchEnabled(false); //확대하지못하게 막아버림! 별로 안좋은 기능인 것 같아~
        //barChart.setRendererLeftYAxis();
//        barChart.setMaxVisibleValueCount(50);
//        barChart.setTop(50);
//        barChart.setBottom(0);
        barChart.setAutoScaleMinMaxEnabled(true);
        barChart.getAxisRight().setAxisMaxValue(80);
        barChart.getAxisLeft().setAxisMaxValue(80);
    }
    /**
     * 그래프함수
     */
    private void BarChartGraph(ArrayList<String> labelList, ArrayList<Integer> valList) {
        // BarChart 메소드

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < valList.size(); i++) {
            entries.add(new BarEntry((Integer) valList.get(i), i));
        }

        BarDataSet depenses = new BarDataSet(entries, "걸음수"); // 변수로 받아서 넣어줘도 됨
        depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
        barChart.setDescription(" ");

        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i < labelList.size(); i++) {
            labels.add((String) labelList.get(i));
        }

        int color[] = {Color.parseColor("#e4caff"),
                Color.parseColor("#cf9fff"),
                Color.parseColor("#b66cff"),
                Color.parseColor("#b35bff"),
                Color.parseColor("#9d3cff"),
                Color.parseColor("#8913ff"),
                Color.parseColor("#6f00dd")};
        BarData data = new BarData(labels, depenses); // 라이브러리 v3.x 사용하면 에러 발생함
        depenses.setColors(color);
        barChart.setData(data);
        barChart.animateXY(1000, 1000);
        barChart.invalidate();
    }

}