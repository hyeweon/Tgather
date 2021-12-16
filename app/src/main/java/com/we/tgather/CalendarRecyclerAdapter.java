package com.we.tgather;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

// 캘린더뷰 Recycler View Adapter
public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.ViewHolder> {

    private ArrayList<GregorianCalendar> calendarList = new ArrayList<>();
    private GregorianCalendar calendar;

    @NonNull
    @Override
    public CalendarRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(calendarList.get(position));
    }

    public void setCalendarList(int year, int month, int day){
        // year month day로 현재 연월일을 받아서 RecyclerView 구성
        calendar = new GregorianCalendar(year, month, day);

        // 이번 달의 1일이 무슨 요일인지
        GregorianCalendar initCalendar = new GregorianCalendar(year, month, 1);
        int dayOfWeek = initCalendar.get(Calendar.DAY_OF_WEEK);

        // 이번 달이 몇일인지
        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 1일이 되기 전까지
        for(int i = 1; i < dayOfWeek; i++){
            calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH - 1), i));
        }
        // 이번 달의 1일 ~ 마지막일
        for(int i = 1; i <= max; i++){
            calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), i));
        }
        // 남은 칸 (총 6주)
        for(int i = 0; i <= 7*6 - dayOfWeek - max; i++){
            calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH + 1), i));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return calendarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayNum, firstTicket, secondTicket;

        TicketDB db;
        TicketDAO ticketDAO;

        String date;
        List<MyTickets> dayTickets;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 캘린더의 내용을 보여줄 TextView를 id로 불러오기
            dayNum = (TextView) itemView.findViewById(R.id.itemNum);
            firstTicket = (TextView) itemView.findViewById(R.id.firstTicket);
            secondTicket = (TextView) itemView.findViewById(R.id.secondTicket);

            // DB 호출
            db = TicketDB.getAppDatabase(itemView.getContext());
            // DAO 설정
            ticketDAO = db.ticketDAO();
        }

        void onBind(GregorianCalendar item){
            // 이번 달의 날짜만 표시
            if(item.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
                dayNum.setText(String.valueOf(item.get(Calendar.DAY_OF_MONTH)));
                if (item.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)){
                    dayNum.setTextColor(0xAA2994B2);            // 오늘 날짜 파란색으로 설정
                    dayNum.setTypeface(Typeface.DEFAULT_BOLD);  // 오늘 날짜 굵게 표시
                }

                // Query를 사용하기 위한 date String
                date = String.format("%d년 %d월 %d일", item.get(Calendar.YEAR), item.get(Calendar.MONTH)+1, item.get(Calendar.DAY_OF_MONTH));
                // 해당 날짜의 티켓 찾기
                dayTickets = ticketDAO.findByDate(date);
                if(! dayTickets.isEmpty()){     // 티켓이 있다면
                    firstTicket.setText(dayTickets.get(0).time + "\n" + dayTickets.get(0).title);
                    firstTicket.setVisibility(View.VISIBLE);
                    if(dayTickets.size()>1){    // 티켓 2개까지 노출
                        secondTicket.setText(dayTickets.get(1).time + "\n" + dayTickets.get(1).title);
                        secondTicket.setVisibility(View.VISIBLE);
                    }
                }
            }
            else
                dayNum.setVisibility(View.INVISIBLE);
        }
    }
}
