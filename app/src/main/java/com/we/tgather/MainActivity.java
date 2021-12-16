package com.we.tgather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Fragment ticketsFragment, calenderFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 하단 탭 설정
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        ticketsFragment = new TicketsFragment();        // Tickets 탭을 눌렀을 때 보여줄 티켓 목록 Fragment
        calenderFragment = new CalendarFragment();      // Calendar 탭을 눌렀을 때 보여줄 캘린더뷰 Fragment
        getSupportFragmentManager().beginTransaction().add(R.id.frame, ticketsFragment).commit(); // 초기화면의 Fragment 설정

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selected = null;
                int position = tab.getPosition();

                if (position == 0){                 // 첫 번째 탭 선택 시
                    selected = ticketsFragment;
                }else if (position == 1){           // 두 번째 탭 선택 시
                    selected = calenderFragment;
                }
                // 선택된 탭에 해당하는 Fragment 보여주기
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void mOnClick(View v){
        Intent intent;
        switch (v.getId()){
            // + 버튼을 눌렀을 때 새로운 티켓을 입력하는 Activity 실행
            case R.id.btnToTicketAdd:
                intent = new Intent(this, TicketAddActivity.class);
                startActivity(intent);
                break;
        }
    }
}