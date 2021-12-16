package com.we.tgather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    CalendarRecyclerAdapter mCalendarRecyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view에 inflate
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Adapter 초기화
        mCalendarRecyclerAdapter = new CalendarRecyclerAdapter();

        Calendar cal = Calendar.getInstance();                      // 현재 날짜 정보
        int year = cal.get(Calendar.YEAR);                          // 현재 연도
        int month = cal.get(Calendar.MONTH);                        // 현재 월
        int day = cal.get(Calendar.DAY_OF_MONTH);                   // 현재 일
        mCalendarRecyclerAdapter.setCalendarList(year, month, day); // 현재 연월일로 RecyclerView 설정

        // 캘린더 위에 현재 연월 표시
        TextView mCalendarTitle = (TextView) v.findViewById(R.id.calendarTitle);
        mCalendarTitle.setText(year + "년 " + (month+1) + "월");

        // RecyclerView 초기화
        RecyclerView mCalendarRecyclerView = (RecyclerView) v.findViewById(R.id.calendarRecyclerView);
        mCalendarRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(7,StaggeredGridLayoutManager.VERTICAL)); // Grid Layout 사용
        mCalendarRecyclerView.setAdapter(mCalendarRecyclerAdapter);

        return v;
    }
}