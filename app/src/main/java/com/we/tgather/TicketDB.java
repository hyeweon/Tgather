package com.we.tgather;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// 티켓을 저장할 DB
@Database(entities = {MyTickets.class}, version = 2)
public abstract class TicketDB extends RoomDatabase {
    private static TicketDB INSTANCE = null;
    public abstract TicketDAO ticketDAO();

    // DB 생성
    public static TicketDB getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, TicketDB.class , "ticket_db")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();  // CalendarRecyclerAdapter의 MainThread에서 접근 가능하게 설정
        }
        return INSTANCE;
    }

    // DB 제거
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
