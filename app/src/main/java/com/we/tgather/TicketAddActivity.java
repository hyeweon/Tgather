package com.we.tgather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

// 새로운 티켓을 저장하기 위한 Activity
public class TicketAddActivity extends AppCompatActivity {

    private EditText mTicketTitle;
    private EditText mTicketSeat;
    private EditText mTicketCast;
    private EditText mTicketReview;

    private String date;
    private String time;

    TicketDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_add);

        // 티켓의 정보를 입력받을 EditText를 id로 불러오기
        mTicketTitle = findViewById(R.id.ticketTitle);
        mTicketSeat = findViewById(R.id.ticketSeat);
        mTicketCast = findViewById(R.id.ticketCast);
        mTicketReview = findViewById(R.id.ticketReview);

        // DB 호출
        db = TicketDB.getAppDatabase(this);
    }

    public void mOnClick(View v){
        Calendar calendar = Calendar.getInstance(); // 현재 날짜 정보
        switch (v.getId()){
            case R.id.btnClose:         // X 버튼을 눌렀을 때 뒤로가기
                onBackPressed();
                break;
            case R.id.btnSave:          // Save 버튼을 눌렀을 때 티켓 저장
                if(mTicketTitle.getText().toString().trim().length() <= 0) {        // 공연명이 입력되지 않은 경우
                    Toast.makeText(TicketAddActivity.this, "공연명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    // DB에 새로운 티켓 추가를 위한 AsyncTask 호출
                    new InsertAsyncTask(db.ticketDAO())
                            .execute(new MyTickets(mTicketTitle.getText().toString(),date,time,mTicketSeat.getText().toString(),mTicketCast.getText().toString(),mTicketReview.getText().toString()));
                    onBackPressed();                                                // 이전 화면으로
                }
                break;
            case R.id.btnDate:          // DatePicker로 날짜 입력
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(this,onDateSetListener,year,month,day).show();
                break;
            case R.id.btnTime:          // TimePicker로 시간 입력
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(this,onTimeSetListener,hour,minute,false).show();
                break;
        }
    }

    public void updateDate(){
        Button mBtnDate = (Button) findViewById(R.id.btnDate);
        mBtnDate.setText(date);
    }

    public void updateTime(){
        Button mBtnTime = (Button) findViewById(R.id.btnTime);
        mBtnTime.setText(time);
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            date = String.format("%d년 %d월 %d일", i, i1+1, i2);
            updateDate();
        }
    };

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            time = String.format("%d시 %d분",i,i1);
            updateTime();
        }
    };

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public static class InsertAsyncTask extends AsyncTask<MyTickets, Void, Void> {
        private TicketDAO ticketDAO;

        public  InsertAsyncTask(TicketDAO ticketDAO){
            this.ticketDAO = ticketDAO;
        }

        @Override
        protected Void doInBackground(MyTickets... myTickets) {
            ticketDAO.insert(myTickets[0]); // DB에 새로운 티켓 추가
            return null;
        }
    }
}