package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.scooksproject.logics.Algorithm;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GroceriesListFragment extends Fragment {

    private static List<Recipe> items;
    @SuppressLint("StaticFieldLeak")
    private static GroceriesListAllRecipesListAdapter adapter;
    private static ListView listView;
    private Context context = getContext();
    private static boolean mealStarted = false;
    private TextView groceriesListNotChosen;

    public static void setMealStarted(boolean mealStarted) {
        GroceriesListFragment.mealStarted = mealStarted;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.groceries_list_fragment, null);
        listView = view.findViewById(R.id.groceriesListView);
        items = Algorithm.getGroceriesListFragment();
        groceriesListNotChosen = view.findViewById(R.id.groceriesListNotChosen);
//        if(items != null) {
//            adapter = new GroceriesListAllRecipesListAdapter(context, items);
//            listView.setAdapter(adapter);
//        }
        if(mealStarted)
            setItems();
        if(!items.isEmpty())
            groceriesListNotChosen.setVisibility(View.INVISIBLE);

        return view;
    }

//    public static void addItem(Recipe recipe) {
//        items.add(recipe);
//        adapter.notifyDataSetChanged();
//    }

    public static void setItems() {
        items = Algorithm.getGroceriesListFragment();
        adapter = new GroceriesListAllRecipesListAdapter(listView.getContext(), items);
        listView.setAdapter(adapter);
    }

    public static void clearGroceriesList(){
        if(items != null) {
            items.clear();
            listView.setAdapter(adapter);
        }
    }
}

