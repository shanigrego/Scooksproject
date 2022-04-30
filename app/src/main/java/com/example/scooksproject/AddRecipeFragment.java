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
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.scooksproject.Exceptions.NoNumberBeforeHoursException;
import com.example.scooksproject.Exceptions.NoNumberBeforeMinutesException;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class AddRecipeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private EditText recipeName;
    private androidx.appcompat.widget.AppCompatButton makingTimeBtn;
    private androidx.appcompat.widget.AppCompatButton preperationTimeBtn;
    private androidx.appcompat.widget.AppCompatButton levelBtn;
    private int makingTimeHour, makingTimeMinute;
    private AddRecipeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.insertion_single_recipe, null);
        HomePageActivity.hideBottomNavigationBar();
        initComponents(view);
        return view;
    }

    private void submitRecipeFunc() {
        String recipeNameStr = recipeName.getText().toString();
        if (!checkRecipeName(recipeNameStr))
            return;
        String preperationTimeStr= preperationTimeBtn.getText().toString();
        String preparationTimeStr = makingTimeBtn.getText().toString();
        String difficulty = levelBtn.getText().toString();
        List<String> recipeInstructionsStr = RecipeInstructionsFragment.getItems();
        List<Ingredient> ingredients = IngredientsFragment.getIngredients();
        int workTime=RecipeParser.getTimeOfWork(preperationTimeStr);
        List<Instruction> recipeInstructions= null;
        try {
            recipeInstructions = RecipeParser.convertListStringToInstructionList(recipeInstructionsStr,workTime);
        } catch (NoNumberBeforeMinutesException | NoNumberBeforeHoursException e) {
            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        int totalFreeTime=RecipeParser.getFreeTime(recipeInstructions);
        int preparationTime=RecipeParser.getPreparationTime(recipeInstructions);

        Recipe recipe = new Recipe(recipeNameStr, preperationTimeStr, preparationTimeStr, difficulty, ingredients, recipeInstructionsStr, null,recipeInstructions,workTime,totalFreeTime,preparationTime);
        //DataBase.getInstance().uploadRecipe(recipe);
        StorageManager.WriteToFile("MyOwnRecipes.txt",recipe,getContext().getFilesDir(), true);
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
        Button addIngredientsBtn = view.findViewById(R.id.addIngredientsBtn);
        Button addWorkingProccessBtn = view.findViewById(R.id.addWorkingProccessBtn);
        Button submitRecipe = view.findViewById(R.id.submitRecipeBtn);
        ImageView backBtn = view.findViewById(R.id.backBtn);
        addWorkingProccessBtn = view.findViewById(R.id.addWorkingProccessBtn);


        addWorkingProccessBtn.setOnClickListener(new View.OnClickListener() {
            final RecipeInstructionsFragment recipeInstructionsFragment = new RecipeInstructionsFragment();

            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, recipeInstructionsFragment).addToBackStack("tag").commit();
            }
        });

        submitRecipe.setOnClickListener(v -> submitRecipeFunc());

        addIngredientsBtn.setOnClickListener(new View.OnClickListener() {

            final Fragment fragment = new IngredientsFragment();

            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
            }
        });

        //Recipe Name initialization
        recipeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setRecipeName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
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
                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
            }
        });
    }

//    public void showPopupMenu(View v) {
//        PopupMenu popupMenu = new PopupMenu(getContext(), v);
//        popupMenu.setOnMenuItemClickListener(this);
//        popupMenu.inflate(R.menu.popup_menu_amount);
//        popupMenu.show();
//        popupMenu.setOnMenuItemClickListener(item -> false);
//    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
