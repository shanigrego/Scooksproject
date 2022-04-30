package com.example.scooksproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;

public class RecipeInstructionsFragment extends Fragment {

    private static ListView listView;
    private static ArrayList<String> items;
    private static RecipeInstructionsListAdapter adapter;
    private TextView addStepTV;
    private ImageView backArrow;
    private Button submitBtn;

    public static ArrayList<String> getItems() {
        return items;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_instructions_list, null);
        listView = view.findViewById(R.id.recipeStepsListView);
        submitBtn = view.findViewById(R.id.submitRecipeInstructions);
        HomePageActivity.hideBottomNavigationBar();
        if (items == null) {
            items = new ArrayList<>();
            addItem("");
            addItem("");
            addItem("");
        }
        adapter = new RecipeInstructionsListAdapter(getContext(), items, false);
        listView.setAdapter(adapter);
        addStepTV = view.findViewById(R.id.addStep);
        backArrow = view.findViewById(R.id.recipeInstructionsBackArraow);

        addStepTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeInstructionsFragment.addItem(""/*txtDetails.getText().toString()*/);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Fragment fragment = new AddRecipeFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 1;
                for (String description :
                        items) {
                    if (description.isEmpty() || description == "") {
                        int finalIndex = index;
                        new AlertDialog.Builder(getContext())
                                .setTitle("תוכן שלב " + index + " ריק")
                                .setMessage("האם תרצה למחוק שלב זה או להמשיך לערוך?")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton("למחוק", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        removeItem(finalIndex - 1);
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton("לערוך", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    index++;
                }
            }
        });
        return view;
    }

    public static void addItem(String str) {
        items.add(str);
        listView.setAdapter(adapter);
    }

    public static void removeItem(int index) {
        items.remove(index);
        listView.setAdapter(adapter);
    }

    private void saveData(){
        View view1;
        EditText instructionsDetails;
        for (int i = 0; i < listView.getCount(); i++) {
            view1 = listView.getChildAt(i);
            instructionsDetails = view1.findViewById(R.id.txtDetails);
            items.set(i, instructionsDetails.getText().toString());
        }
    }
}
