package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

public class FinalRecipeFragment extends Fragment {

//    private ListView instructionsListView;
    private Recipe currentRecipe;
    private ArrayAdapter<Instruction> adapter;
//    SeekBar seekBar;
    List<String> list0;
    private VerticalStepView mStepView;
    private TextView finalLevelTV;
    private TextView finalPreparationTimeTV;
    private TextView finalMakingTimeTV;
    private int currentStep;
    private static boolean startedMeal = false;
    public static View currView;

    @Override
    public void onPause() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            HomePageActivity.getChefButton().setImageDrawable(getResources().getDrawable(R.drawable.chef_white, getContext().getTheme()));
        } else {
            HomePageActivity.getChefButton().setImageDrawable(getResources().getDrawable(R.drawable.chef_white));
        }
        HomePageActivity.chefButtonStartedMealFunctionality(getParentFragmentManager());
        HomePageActivity.getBottomNavigationView().getMenu().setGroupCheckable(0, true, true);
        super.onPause();
    }

    @Override
    public void onResume() {
//        inflater.inflate(R.layout.final_recipe_fragment, null);
        HomePageActivity.getChefButton().setImageDrawable(getResources().getDrawable(R.drawable.check));
        chefButtonInFragmentFunctionality();
        HomePageActivity.getBottomNavigationView().getMenu().setGroupCheckable(0, false, true);
        super.onResume();
    }

    private NonScrollListView finalInstructionsListView;
    private FinalRecipeInstructionsListViewAdapter instructionsAdapter;

    public FinalRecipeFragment(Recipe recipe){
        this.currentRecipe = recipe;
    }

    public FinalRecipeFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!startedMeal) {
            View view = inflater.inflate(R.layout.final_recipe_fragment, null);
            initComponents(view);
//        adapter = new FinalRecipeListViewAdapter(getContext(), currentRecipe.getRecipeInstructions());
//        instructionsListView.setAdapter(adapter);
            currentStep = 0;
            startedMeal = true;
            currView = view;
            return view;
        }
        else{
            return currView;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initComponents(View view){
        mStepView = (VerticalStepView) view.findViewById(R.id.step_view0);
//        instructionsListView = view.findViewById(R.id.finalRecipeInstructionsList);
        finalLevelTV = view.findViewById(R.id.finalLevelTV);
        finalMakingTimeTV = view.findViewById(R.id.finalMakingTimeTV);
        finalPreparationTimeTV = view.findViewById(R.id.finalPreperationTimeTV);
        finalInstructionsListView = view.findViewById(R.id.finalInstructionsListView);
        if(!startedMeal)
            instructionsAdapter = new FinalRecipeInstructionsListViewAdapter(getContext(), currentRecipe.getRecipeInstructions());
        finalInstructionsListView.setAdapter(instructionsAdapter);
        list0 = new ArrayList<>();

        HomePageActivity.getChefButton().setImageDrawable(getResources().getDrawable(R.drawable.check));

        chefButtonInFragmentFunctionality();

        int stepNum = 1;
        for(String instructionStr : currentRecipe.getRecipeInstructionsStr()){
            list0.add("שלב " + stepNum);
            stepNum++;
        }

        finalLevelTV.setText(currentRecipe.getDifficultLevel());
        finalMakingTimeTV.setText(currentRecipe.getTimeOfWorkNeededStr());
        finalPreparationTimeTV.setText(currentRecipe.getPreparationTime() + " דקות ");

        mStepView.setStepsViewIndicatorComplectingPosition(currentStep)//设置完成的步数
                .reverseDraw(false)//default is true
                .setStepViewTexts(list0)
                .setLinePaddingProportion(3f)//indicator
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.pink))//StepsViewIndicator
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.teal_200))//StepsViewIndicator
                .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.black))//StepsView text
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.blurpul))//StepsView text
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), com.baoyachi.stepview.R.drawable.default_icon))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(getContext().getDrawable(R.drawable.circle_no_dashed))//StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), com.baoyachi.stepview.R.drawable.default_icon));//设置StepsViewIndicator AttentionIcon

        mStepView.setScrollBarSize(40);

    }

    private void chefButtonInFragmentFunctionality(){
        HomePageActivity.getChefButton().setOnClickListener(v -> {
            currentStep++;
            mStepView = (VerticalStepView) currView.findViewById(R.id.step_view0);
            mStepView.setStepsViewIndicatorComplectingPosition(currentStep);
        });

    }

    public static void showAlarmPicker(TimePickerDialog timePickerDialog){
        System.out.println("time picker invoked");
        timePickerDialog.show();
    }
}
