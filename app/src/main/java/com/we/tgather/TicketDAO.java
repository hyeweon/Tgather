package com.we.tgather;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// 티켓 저장 DB를 위한 Data Access Object
@Dao
public interface TicketDAO {
    @Query("SELECT * FROM MyTickets")
    LiveData<List<MyTickets>> getAll();         // LiveData를 사용하여 변화가 있을 때마다 observe

    @Query(("SELECT * FROM MyTickets where ticket_date=:date"))
    List<MyTickets> findByDate(String date);    // 캘린더뷰에서 특정 날짜의 티켓을 찾기 위한 Query

    @Query(("SELECT * FROM MyTickets where ticketID=:ID"))
    List<MyTickets> findByID(int ID);

    @Insert
    void insert(MyTickets mytickets);           // DB에 티켓 추가

    @Update
    void update(MyTickets mytickets);           // 티켓 수정

    @Delete
    void delete(MyTickets mytickets);           // DB에서 티켓 삭제
}
