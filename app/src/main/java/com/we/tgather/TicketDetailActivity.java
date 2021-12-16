package com.we.tgather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

// 저장한 티켓의 상세보기를 위한 Activity
public class TicketDetailActivity extends AppCompatActivity {

    private TextView mTicketTitle;
    private TextView mTicketDate;
    private TextView mTicketTime;
    private TextView mTicketSeat;
    private TextView mTicketCast;
    private TextView mTicketReview;

    private String date;
    private String time;

    TicketDB db;
    int ticketID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Intent로 클릭한 티켓의 정보 받아오기
        Intent intent = getIntent();
        String ticketTitle = intent.getStringExtra("title");
        String ticketDate = intent.getStringExtra("date");
        String ticketTime = intent.getStringExtra("time");
        String ticketSeat = intent.getStringExtra("seat");
        String ticketCast = intent.getStringExtra("cast");
        String ticketReview = intent.getStringExtra("review");
        ticketID = intent.getIntExtra("ID",0);

        setContentView(R.layout.activity_ticket_detail);

        // 티켓의 정보를 보여줄 TextView를 id로 불러오기
        mTicketTitle = findViewById(R.id.ticketTitle);
        mTicketDate = findViewById(R.id.ticketDate);
        mTicketTime = findViewById(R.id.ticketTime);
        mTicketSeat = findViewById(R.id.ticketSeat);
        mTicketCast = findViewById(R.id.ticketCast);
        mTicketReview = findViewById(R.id.ticketReview);
        // 티켓의 정보가 보이게 설정
        mTicketTitle.setText(ticketTitle);
        mTicketDate.setText(ticketDate);
        mTicketTime.setText(ticketTime);
        mTicketSeat.setText(ticketSeat);
        mTicketCast.setText(ticketCast);
        mTicketReview.setText(ticketReview);

        // DB 호출
        db = TicketDB.getAppDatabase(this);
    }

    public void mOnClick(View v){
        switch (v.getId()){
            // X 버튼을 눌렀을 때 뒤로가기
            case R.id.btnClose:
                onBackPressed();
                break;
            // Delete 버튼을 눌렀을 때 티켓 삭제하기
            case R.id.btnDelete:
                new TicketDetailActivity.DeleteAsyncTask(db.ticketDAO()).execute(ticketID); //AsyncTask 호출
                onBackPressed();                                                            // 이전 화면으로
                break;
        }
    }

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public static class DeleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private TicketDAO ticketDAO;

        public  DeleteAsyncTask(TicketDAO ticketDAO){
            this.ticketDAO = ticketDAO;
        }

        @Override
        protected Void doInBackground(Integer... id) {
            List<MyTickets> deleteTicket = ticketDAO.findByID(id[0]);   // PrimaryKey로 티켓 찾기
            ticketDAO.delete(deleteTicket.get(0));                      // 티켓 삭제
            return null;
        }
    }
}