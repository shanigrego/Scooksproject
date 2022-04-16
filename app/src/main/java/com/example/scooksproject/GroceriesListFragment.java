package com.example.scooksproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GroceriesListFragment extends Fragment {

    private static ListView listView;
    private static ArrayList<String> items;
    private static GroceriesListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.groceries_list_fragment, null);
        listView = view.findViewById(R.id.groceriesListView);
        items = new ArrayList<>();
        adapter = new GroceriesListAdapter(getContext(), items);
        addItem("שמן");
        addItem("ביצים");
        addItem("מלפפון חמוץ גודל M");
        listView.setAdapter(adapter);

        return view;
    }

    public static void addItem(String str) {
        items.add(str);
        adapter.notifyDataSetChanged();
    }

    public static void removeItem(int index) {
        items.remove(index);
        adapter.notifyDataSetChanged();
    }
}

