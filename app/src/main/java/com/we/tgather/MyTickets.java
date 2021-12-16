package com.we.tgather;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

// 티켓 저장 DB의 Entity 설정
@Entity
public class MyTickets {
    // 각 티켓의 식별을 위한 ID (PrimaryKey)
    @PrimaryKey(autoGenerate = true)
    public int ticketID;

    // 티켓 공연명
    @ColumnInfo(name="ticket_title")
    public String title;

    // 티켓 날짜
    @ColumnInfo(name = "ticket_date")
    public String date;

    // 티켓 시간
    @ColumnInfo(name = "ticket_time")
    public String time;

    // 티켓 좌석
    @ColumnInfo(name="ticket_seat")
    public String seat;

    // 티켓 캐스트
    @ColumnInfo(name="ticket_cast")
    public String cast;

    // 티켓 공연 리뷰
    @ColumnInfo(name="ticket_review")
    public String review;

    // 티켓 생성자
    public MyTickets(String title, String date, String time, String seat, String cast, String review) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.seat = seat;
        this.cast = cast;
        this.review = review;
    }

    // 각 Attribute에 접근할 때 사용하는 함수
    public int getID() {
        return ticketID;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getTitle() {
        return title;
    }
    public String getSeat() {
        return seat;
    }
    public String getCast() {
        return cast;
    }
    public String getReview() {
        return review;
    }
}
