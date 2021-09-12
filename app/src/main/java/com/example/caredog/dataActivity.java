package com.example.caredog;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class dataActivity extends AppCompatActivity {
    private TextView textView_date;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    ArrayList<Integer> jsonList1 = new ArrayList<>(); // ArrayList 선언
    ArrayList<String> labelList1 = new ArrayList<>(); // ArrayList 선언
    ArrayList<Integer> jsonList2 = new ArrayList<>(); // ArrayList 선언
    ArrayList<String> labelList2 = new ArrayList<>(); // ArrayList 선언
    BarChart barChart1;
    BarChart barChart2;

    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        this.InitializeView();
        this.InitializeListener();

        //현재 시간 정보 불러옴
        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        year = Integer.parseInt(yearFormat.format(currentTime));
        month = Integer.parseInt(monthFormat.format(currentTime));
        day = Integer.parseInt(dayFormat.format(currentTime));

        //현재 시간으로 텍스트 설정
        textView_date.setText(year + "년 " + month + "월 " + day + "일");

        //걸음수 차트 선언
        barChart1 = (BarChart) findViewById(R.id.walk_chart);
        jsonList1 = dataInitSetting(jsonList1);
        graphInitSetting("walk_chart", labelList1, jsonList1, barChart1); //그래프 기본 세팅

        //평균 걸음수
        TextView Avg_walk = (TextView) findViewById(R.id.text_walk);
        Avg_walk.setText(Average(jsonList1));

        //이동거리 차트 선언
        barChart2 = (BarChart) findViewById(R.id.distance_chart);
        jsonList2 = dataInitSetting(jsonList2);
        graphInitSetting("distance_chart", labelList2, jsonList2, barChart2);

        //평균 걸음수
        TextView Avg_distance = (TextView) findViewById(R.id.text_distance);
        Avg_distance.setText(Average(jsonList2));

        //뒤로가기 버튼을 누르면 메인 화면으로 이동
        ImageButton data_back = (ImageButton) findViewById(R.id.data_back);
        data_back.setOnClickListener(view -> {
            finish();
        });

        ImageButton calender = (ImageButton) findViewById(R.id.calender);
        calender.setOnClickListener(this::OnClickHandler);
    }

    /*
    /DatePickerDialog 함수
     */

    public void InitializeView() {
        textView_date = (TextView) findViewById(R.id.textView_date);
    }

    public void InitializeListener() {
        callbackMethod = (view, year, monthOfYear, dayOfMonth) -> {
            monthOfYear = monthOfYear + 1; //0~11로 설정되어있어 1 더함
            textView_date.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth + "일"); //며칠 ~ 며칠로 설정하기 어려워서 일단 선택 당일로 텍스트 변경
        };
    }

    public void OnClickHandler(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, year, month - 1, day);

        dialog.show();
    }

    /*
    /차트 함수
     */

    //데이터 삽입 함수
    public ArrayList dataInitSetting(ArrayList<Integer> data) {
        data.add(1000);
        data.add(2000);
        data.add(3000);
        data.add(4000);
        data.add(5000);
        data.add(6000);
        data.add(7000);

        return data;
    }

    //차트 기본 세팅
    public void graphInitSetting(String chart, ArrayList<String> label, ArrayList<Integer> list, BarChart barChart) {

        //x축
        label.add("일");
        label.add("월");
        label.add("화");
        label.add("수");
        label.add("목");
        label.add("금");
        label.add("토");


        BarChartGraph(chart, barChart, label, list);
        barChart.setTouchEnabled(false);
        barChart.setAutoScaleMinMaxEnabled(true);
        barChart.getAxisRight().setAxisMaxValue(10000);
        barChart.getAxisLeft().setAxisMaxValue(10000);
    }

    //그래프 함수
    private void BarChartGraph(String chart, BarChart barChart, ArrayList<String> labelList, ArrayList<Integer> valList) {
        // BarChart 메소드
        BarDataSet depenses;

        //데이터 값을 BarEntry형으로 enries에 저장
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < valList.size(); i++) {
            entries.add(new BarEntry((Integer) valList.get(i), i));
        }

        //데이터 단위
        if (chart.equals("walk_chart"))
            depenses = new BarDataSet(entries, "걸음수"); // 변수로 받아서 넣어줘도 됨
        else
            depenses = new BarDataSet(entries, "이동거리");

        depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
        barChart.setDescription(" ");

        //x축 이름 labels에 저장
        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i < labelList.size(); i++) {
            labels.add((String) labelList.get(i));
        }
        //데이터 선언
        BarData data = new BarData(labels, depenses); // 라이브러리 v3.x 사용하면 에러 발생함

        //차트 색
        depenses.setColor(Color.parseColor("#9d3cff"));
        barChart.setData(data);
        barChart.invalidate();
    }

    //평규 걸음수 계산 함수
    private String Average(ArrayList<Integer> list) {

        int average=0;
        String avg;

        for(int i=0; i<list.size(); i++) {
            average = average + list.get(i);
        }
        average = average / list.size();
        avg = String.valueOf(average);
        return avg;
    }
}
