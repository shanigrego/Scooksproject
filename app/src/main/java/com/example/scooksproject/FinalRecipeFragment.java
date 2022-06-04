package com.example.scooksproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Console;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class FinalRecipeFragment extends Fragment {

    private ListView instructionsListView;
    private Recipe currentRecipe;
    private ArrayAdapter<Instruction> adapter;

    public FinalRecipeFragment(Recipe recipe){
        this.currentRecipe = recipe;
    }

    public FinalRecipeFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.final_recipe_fragment, null);
        initComponents(view);
        adapter = new FinalRecipeListViewAdapter(getContext(), currentRecipe.getRecipeInstructions());
        instructionsListView.setAdapter(adapter);
        return view;
    }

    private void initComponents(View view){
        instructionsListView = view.findViewById(R.id.finalRecipeInstructionsList);

    }

    public static void showAlarmPicker(TimePickerDialog timePickerDialog){
        System.out.println("time picker invoked");
        timePickerDialog.show();
    }
}
