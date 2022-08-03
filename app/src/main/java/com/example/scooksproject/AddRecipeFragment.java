package com.example.scooksproject;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.scooksproject.Exceptions.NoNumberBeforeHoursException;
import com.example.scooksproject.Exceptions.NoNumberBeforeMinutesException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class AddRecipeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private EditText recipeName;
    private androidx.appcompat.widget.AppCompatButton makingTimeBtn;
    private androidx.appcompat.widget.AppCompatButton preperationTimeBtn;
    private androidx.appcompat.widget.AppCompatButton levelBtn;
    private int makingTimeHour, makingTimeMinute;
    private AddRecipeViewModel viewModel;
    private static ListView ingredientsListView;
    private static IngredientListViewAdapter ingredientsAdapter;
    private static LinkedList<Ingredient> ingredients;
    private static ListView instructionsListView;
    private static ArrayList<String> instructions;
    private static RecipeInstructionsListAdapter instructionsAdapter;
    private TextView addStepTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.insertion_single_recipe, null);
        HomePageActivity.hideBottomNavigationBar();
        initComponents(view);

        TextView addIngredient = view.findViewById(R.id.addSingleIngredientBtn);
        addIngredient.setOnClickListener(v -> addIngredient(""));

        return view;
    }

    private void submitRecipeFunc() {
        String recipeNameStr = recipeName.getText().toString();
        if (!checkRecipeName(recipeNameStr))
            return;
        String preperationTimeStr = preperationTimeBtn.getText().toString();
        String preparationTimeStr = makingTimeBtn.getText().toString();
        String difficulty = levelBtn.getText().toString();
        List<String> recipeInstructionsStr = RecipeInstructionsFragment.getInstructions();
        int workTime = RecipeParser.getTimeOfWork(preperationTimeStr);
        List<Instruction> recipeInstructions = null;
        try {
            recipeInstructions = RecipeParser.convertListStringToInstructionList(recipeInstructionsStr, workTime);
        } catch (NoNumberBeforeMinutesException | NoNumberBeforeHoursException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        int totalFreeTime = RecipeParser.getFreeTime(recipeInstructions);
        int preparationTime = RecipeParser.getPreparationTime(recipeInstructions);

        checkRecipeInstructions();
        Recipe recipe = new Recipe(recipeNameStr, preperationTimeStr, preparationTimeStr, difficulty, ingredients, recipeInstructionsStr, null, recipeInstructions, workTime, totalFreeTime, preparationTime);
        //DataBase.getInstance().uploadRecipe(recipe);

        StorageManager.WriteToFile("MyOwnRecipes.txt", recipe, getContext().getFilesDir(), true);
    }

    private void checkRecipeInstructions(){
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
                        .setPositiveButton("למחוק", (dialog, which) -> removeInstruction(finalIndex - 1))

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("לערוך", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            index++;
        }
    }

    private boolean checkRecipeName(String name) {
        String enterRecipeName = "נא להכניס שם מתכון!";
        if (name.isEmpty()) {
            Toast.makeText(getContext(), enterRecipeName, Toast.LENGTH_SHORT).show();
            return false;
        }
        //check for existence in database
        return true;
    }


    private void initComponents(View view) {
        viewModel = new ViewModelProvider(requireActivity()).get(AddRecipeViewModel.class);
        recipeName = view.findViewById(R.id.recipeNameET);
        makingTimeBtn = view.findViewById(R.id.makingTimeET);
        preperationTimeBtn = view.findViewById(R.id.preperationTimeET);
        levelBtn = view.findViewById(R.id.levelEV);
        ingredientsListView = view.findViewById(R.id.ingredientsListView);
        instructionsListView = view.findViewById(R.id.recipeStepsListView);

        if (instructions == null) {
            instructions = new ArrayList<>();
            addInstruction("");
            addInstruction("");
            addInstruction("");
        }
        instructionsAdapter = new RecipeInstructionsListAdapter(getContext(), instructions, false);
        instructionsListView.setAdapter(instructionsAdapter);
        addStepTV = view.findViewById(R.id.addStep);
        addStepTV.setOnClickListener(v -> addInstruction(""/*txtDetails.getText().toString()*/));

        if (ingredients == null)
            ingredients = new LinkedList<>();
        ingredientsAdapter = new IngredientListViewAdapter(getContext(), ingredients, false);
        ingredientsListView.setAdapter(ingredientsAdapter);

//        Button addIngredientsBtn = view.findViewById(R.id.addIngredientsBtn);
//        Button addWorkingProcessBtn = view.findViewById(R.id.addWorkingProccessBtn);
        Button submitRecipe = view.findViewById(R.id.submitRecipeBtn);
        ImageView backBtn = view.findViewById(R.id.backBtn);


//        addWorkingProcessBtn.setOnClickListener(new View.OnClickListener() {
//            final RecipeInstructionsFragment recipeInstructionsFragment = new RecipeInstructionsFragment();
//
//            @Override
//            public void onClick(View v) {
//                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, recipeInstructionsFragment).addToBackStack("tag").commit();
//            }
//        });

        submitRecipe.setOnClickListener(v -> submitRecipeFunc());

//        addIngredientsBtn.setOnClickListener(new View.OnClickListener() {
//
//            final Fragment fragment = new IngredientsFragment();
//
//            @Override
//            public void onClick(View v) {
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
//            }
//        });

        //Recipe Name initialization
        recipeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setRecipeName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        recipeName.setText(viewModel.getRecipeName());

        //Level Button initialization
        levelBtn.setText(viewModel.getDifficulty());
        levelBtn.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.easy:
                        viewModel.setDifficulty("כל אחד יכול");
                        break;
                    case R.id.medium:
                        viewModel.setDifficulty("בינוני");
                        break;
                    case R.id.hard:
                        viewModel.setDifficulty("נדרשת מיומנות");
                        break;
                }
                levelBtn.setText(viewModel.getDifficulty());
                return true;
            });
            popupMenu.inflate(R.menu.popup_menu_difficulty);
            popupMenu.show();
        });

        //Making Time Btn initialization
        makingTimeBtn.setText(viewModel.getMakingTime());
        makingTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                makingTimeHour = hourOfDay;
                                makingTimeMinute = minute;
                                String time = hourOfDay + ":" + minute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
//                                try {
//                                    Date date = f24Hours.parse(time);
//                                    SimpleDateFormat f12Hours = new SimpleDateFormat("HH:mm");
                                if (makingTimeMinute == 0) {
                                    makingTimeBtn.setText(makingTimeHour + " שעות");
                                    if (makingTimeHour == 2) {
                                        makingTimeBtn.setText("שעתיים ו" + makingTimeMinute + " דקות");
                                    }
                                } else
                                    makingTimeBtn.setText(makingTimeHour + " שעות ו" + makingTimeMinute + " דקות");
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, makingTimeHour, makingTimeMinute);
                                viewModel.setMakingTime(makingTimeBtn.getText().toString());
                            }
                        }, 12, 0, true);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(makingTimeHour, makingTimeMinute);
                timePickerDialog.show();
            }
        });

        //Preparation Time Btn initialization
        preperationTimeBtn.setText(viewModel.getPreparationTime());
        preperationTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                makingTimeHour = hourOfDay;
                                makingTimeMinute = minute;
                                String time = hourOfDay + ":" + minute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("HH:mm");
                                    if (makingTimeMinute == 0) {
                                        preperationTimeBtn.setText(makingTimeHour + " שעות");
                                        if (makingTimeHour == 2) {
                                            preperationTimeBtn.setText("שעתיים ו" + makingTimeMinute + " דקות");
                                        }
                                    } else if (makingTimeHour == 2) {
                                        preperationTimeBtn.setText("שעתיים ו" + makingTimeMinute + " דקות");
                                    } else
                                        preperationTimeBtn.setText(makingTimeHour + " שעות ו" + makingTimeMinute + " דקות");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, makingTimeHour, makingTimeMinute);
                                viewModel.setPreparationTime(preperationTimeBtn.getText().toString());
                            }
                        }, 12, 0, true);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(makingTimeHour, makingTimeMinute);
                timePickerDialog.show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            final MyRecipiesFragment fragment = new MyRecipiesFragment();

            @Override
            public void onClick(View v) {
                saveDataInstructions();
                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
            }
        });
    }

    public static void addIngredient(String str) {
        ingredients.add(new Ingredient(
                str,
                0,
                "גרם"
        ));
        saveData();
        ingredientsListView.setAdapter(ingredientsAdapter);
        ingredientsListView.getLayoutParams().height += 69;
    }

    public static void removeIngredient(int index) {
        ingredients.remove(index);
        ingredientsListView.setAdapter(ingredientsAdapter);
        ingredientsListView.getLayoutParams().height -= 69;
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

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        return false;
//    }

    private static void saveData() {
        View view1;
        EditText ingName, ingAmount;
        Button ingMeasureUnit;
        for (int i = 0; i < ingredientsListView.getCount(); i++) {
            view1 = ingredientsListView.getChildAt(i);
            ingName = view1.findViewById(R.id.nameIngredient);
            if (!ingName.getText().toString().isEmpty()) {
                ingAmount = view1.findViewById(R.id.amountIngredient);
                ingMeasureUnit = view1.findViewById(R.id.measureUnitIngredient);
                ingredients.get(i).setName(ingName.getText().toString());
                ingredients.get(i).setMeasureUnit(ingMeasureUnit.getText().toString());
                if (ingAmount.getText().toString().isEmpty())
                    ingredients.get(i).setAmount(0);
                else
                    ingredients.get(i).setAmount(Double.parseDouble(ingAmount.getText().toString()));
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}

//    public void showPopupMenu(View v) {
//        PopupMenu popupMenu = new PopupMenu(getContext(), v);
//        popupMenu.setOnMenuItemClickListener(this);
//        popupMenu.inflate(R.menu.popup_menu_amount);
//        popupMenu.show();
//        popupMenu.setOnMenuItemClickListener(item -> false);
//    }
