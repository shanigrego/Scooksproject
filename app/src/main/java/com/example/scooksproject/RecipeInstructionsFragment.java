package com.example.scooksproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RecipeInstructionsFragment extends Fragment {

    private static ListView listView;
    private static ArrayList<String> items;
    private static RecipeInstructionsListAdapter adapter;
    private TextView addStepTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_instructions_list, null);
        listView = view.findViewById(R.id.recipeStepsListView);
        items = new ArrayList<>();
        adapter = new RecipeInstructionsListAdapter(getContext(), items);
        addItem("בשל");
        addItem("הכן");
        addItem("הטפח");
        addItem("ערבב");
        listView.setAdapter(adapter);
        addStepTV = view.findViewById(R.id.addStep);

        addStepTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeInstructionsFragment.addItem("ddd"/*txtDetails.getText().toString()*/);
            }
        });
        return view;
    }

    public static void addItem(String str){
        items.add(str);
        listView.setAdapter(adapter);
    }

    public static void removeItem(int index){
        items.remove(index);
        listView.setAdapter(adapter);
    }
}
