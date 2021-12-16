package com.we.tgather;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// 저장한 티켓을 목록으로 보여주기 위한 Recycler View Adapter
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<MyTickets> myTicketsList;

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(myTicketsList.get(position));
    }

    public void setMyTicketsList(List<MyTickets> list){
        // DB에 있는 티켓 정보로 RecyclerView를 구성
        ArrayList<MyTickets> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        myTicketsList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myTicketsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,dateTime, seat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 티켓의 제목, 일시, 좌석을 보여줄 TextView를 id로 불러오기
            title = (TextView) itemView.findViewById(R.id.itemTitle);
            dateTime = (TextView) itemView.findViewById(R.id.itemDateTime);
            seat = (TextView) itemView.findViewById(R.id.itemSeat);

            // 클릭하면 각 티켓의 상세 보기가 가능하도록 OnClickListener 설정
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {         // 목록의 티켓을 누를 경우 실행
                        MyTickets item = myTicketsList.get(position) ;  // 어떤 티켓을 눌렀는지
                        String itemTitle = item.getTitle();             // 티켓의 제목 정보 가져오기
                        String itemDate = item.getDate();               // 티켓의 날짜 정보 가져오기
                        String itemTime = item.getTime();               // 티켓의 시간 정보 가져오기
                        String itemSeat = item.getSeat();               // 티켓의 좌석 정보 가져오기
                        String itemCast = item.getCast();               // 티켓의 캐스트 정보 가져오기
                        String itemReview = item.getReview();           // 티켓의 리뷰 정보 가져오기
                        int itemID = item.getID();                      // 티켓 삭제를 위한 ID 가져오기
                        // Intent로 해당 티켓의 정보 교환
                        intent = new Intent(v.getContext(), TicketDetailActivity.class);
                        intent.putExtra("title",itemTitle);
                        intent.putExtra("date",itemDate);
                        intent.putExtra("time",itemTime);
                        intent.putExtra("seat",itemSeat);
                        intent.putExtra("cast",itemCast);
                        intent.putExtra("review",itemReview);
                        intent.putExtra("ID",itemID);
                        v.getContext().startActivity(intent);           // Intent 호출
                    }
                }
            });
        }

        void onBind(MyTickets item){
            title.setText(item.getTitle());                             // 목록에 티켓 제목이 보이게 설정
            dateTime.setText(item.getDate() + "\t " + item.getTime());  // 목록에 티켓 일시가 보이게 설정
            seat.setText(item.getSeat());                               // 목록에 티켓 좌석이 보이게 설정
        }
    }
}
