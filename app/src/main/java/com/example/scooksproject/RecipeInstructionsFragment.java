package com.example.scooksproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class RecipeInstructionsFragment extends Fragment {

    private static ListView instructionsListView;
    private static ArrayList<String> instructions;
    private static RecipeInstructionsListAdapter instructionsAdapter;
    private TextView addStepTV;
    private ImageView backArrow;
    private Button submitBtn;

    public static ArrayList<String> getInstructions() {
        return instructions;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_instructions_list, null);
        instructionsListView = view.findViewById(R.id.recipeStepsListView);
        submitBtn = view.findViewById(R.id.submitRecipeInstructions);
        HomePageActivity.hideBottomNavigationBar();
        if (instructions == null) {
            instructions = new ArrayList<>();
            addInstruction("");
            addInstruction("");
            addInstruction("");
        }
        instructionsAdapter = new RecipeInstructionsListAdapter(getContext(), instructions, false);
        instructionsListView.setAdapter(instructionsAdapter);
        addStepTV = view.findViewById(R.id.addStep);
        backArrow = view.findViewById(R.id.recipeInstructionsBackArraow);

        addStepTV.setOnClickListener(v -> RecipeInstructionsFragment.addInstruction(""/*txtDetails.getText().toString()*/));

        backArrow.setOnClickListener(v -> {
            saveDataInstructions();
            Fragment fragment = new AddRecipeFragment();
            getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
        });

        submitBtn.setOnClickListener(v -> {
            int index = 1;
            for (String description :
                    instructions) {
                if (description.isEmpty()) {
                    int finalIndex = index;
                    new AlertDialog.Builder(getContext())
                            .setTitle("תוכן שלב " + index + " ריק")
                            .setMessage("האם תרצה למחוק שלב זה או להמשיך לערוך?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("למחוק", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    removeInstruction(finalIndex - 1);
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton("לערוך", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                index++;
            }
        });
        return view;
    }

    public static void addInstruction(String str) {
        instructions.add(str);
        instructionsListView.setAdapter(instructionsAdapter);
    }

    public static void removeInstruction(int index) {
        instructions.remove(index);
        instructionsListView.setAdapter(instructionsAdapter);
    }

    private void saveDataInstructions(){
        View view1;
        EditText instructionsDetails;
        for (int i = 0; i < instructionsListView.getCount(); i++) {
            view1 = instructionsListView.getChildAt(i);
            instructionsDetails = view1.findViewById(R.id.txtDetails);
            instructions.set(i, instructionsDetails.getText().toString());
        }
    }
}
