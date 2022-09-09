package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TimersFragment extends Fragment {

    private static List<SingleTimer> items = new ArrayList<>();
    private static ListView timersListView;
    private static TextView noTimersTV;
    private static ArrayAdapter<SingleTimer> adapter;



    public static void addTimer(SingleTimer timer){
        items.add(timer);
    }

    public static void removeTimerByStepId(String id){
        for(SingleTimer singleTimer : items) {
                if(singleTimer.getIdByStep().equals(id))
                    items.remove(singleTimer);
        }
        timersListView.setAdapter(adapter);
    }

    public static void removeAllTimers(){
        items.clear();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timers_fragment, null);
        initComponents(view);
        return view;
    }

    private void initComponents(View view){
        timersListView = view.findViewById(R.id.timersListView);
        noTimersTV = view.findViewById(R.id.noTimersTV);
        if(adapter == null)
            adapter = new TimersListViewAdapter(getContext(), items);
        if(timersListView.getAdapter() == null)
            timersListView.setAdapter(adapter);

        if(!items.isEmpty()){
            noTimersTV.setVisibility(View.INVISIBLE);
        }
    }
}
