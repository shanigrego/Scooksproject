package com.example.scooksproject;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class AddRecipeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private EditText recipeName;
    private androidx.appcompat.widget.AppCompatButton makingTimeBtn;
    private androidx.appcompat.widget.AppCompatButton preperationTimeBtn;
    private androidx.appcompat.widget.AppCompatButton levelBtn;
    private Button addIngredientsBtn;
    private Button addWorkingProccessBtn;
    private Button submitRecipe;
    private TextView recipeNameErrorTV;
    private TextView otherErrorTV;
    private LinearLayout singleRecipeLinearLayout;
    private ScrollView scrollViewRecipeBook;
    private ImageView backBtn;
    int makingTimeHour, makingTimeMinute;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.insertion_single_recipe, null);
        initComponents(view);
        return view;
    }

    private void submitRecipeFunc() {
        String recipeNameStr = recipeName.getText().toString();
        if (!checkRecipeName(recipeNameStr))
            return;
        String preperationTime = preperationTimeBtn.getText().toString();
        String totalTime = makingTimeBtn.getText().toString();
        String difficulty = levelBtn.getText().toString();
        List<String> recipeInstructions = RecipeInstructionsFragment.getItems();
        List<Ingredient> ingredients = IngredientsFragment.getIngredients();

        Recipe recipe = new Recipe(recipeNameStr, preperationTime, totalTime, difficulty, ingredients, recipeInstructions);
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
        recipeName = view.findViewById(R.id.recipeNameET);
        recipeNameErrorTV = view.findViewById(R.id.recipeNameError);
        otherErrorTV = view.findViewById(R.id.otherErrors);
        makingTimeBtn = view.findViewById(R.id.makingTimeET);
        preperationTimeBtn = view.findViewById(R.id.preperationTimeET);
        levelBtn = view.findViewById(R.id.levelEV);
        addIngredientsBtn = view.findViewById(R.id.addIngredientsBtn);
        addWorkingProccessBtn = view.findViewById(R.id.addWorkingProccessBtn);
        submitRecipe = view.findViewById(R.id.submitRecipeBtn);
        singleRecipeLinearLayout = view.findViewById(R.id.singleRecipeLinearLayout);
        backBtn = view.findViewById(R.id.backBtn);
        addWorkingProccessBtn = view.findViewById(R.id.addWorkingProccessBtn);


        addWorkingProccessBtn.setOnClickListener(new View.OnClickListener() {
            RecipeInstructionsFragment recipeInstructionsFragment = new RecipeInstructionsFragment();

            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, recipeInstructionsFragment).addToBackStack("tag").commit();
            }
        });
        submitRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRecipeFunc();
            }
        });

        addIngredientsBtn.setOnClickListener(new View.OnClickListener() {

            Fragment fragment = new IngredientsFragment();

            //Intent intent = new Intent(AddRecipeFragment.this, IngredientsActivity.class);
            @Override
            public void onClick(View v) {
                ((HomePageActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
            }
        });

        levelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.easy:
                                levelBtn.setText("כל אחד יכול");
                                break;
                            case R.id.medium:
                                levelBtn.setText("בינוני");
                                break;
                            case R.id.hard:
                                levelBtn.setText("נדרשת מיומנות");
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.inflate(R.menu.popup_menu_difficulty);
                popupMenu.show();
            }
        });

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
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("HH:mm");
                                    if (makingTimeMinute == 0) {
                                        makingTimeBtn.setText(makingTimeHour + " שעות");
                                        if (makingTimeHour == 2) {
                                            preperationTimeBtn.setText("שעתיים ו" + makingTimeMinute + " דקות");
                                        }
                                    } else
                                        makingTimeBtn.setText(makingTimeHour + " שעות ו" + makingTimeMinute + " דקות");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, makingTimeHour, makingTimeMinute);

                            }
                        }, 12, 0, true);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(makingTimeHour, makingTimeMinute);
                timePickerDialog.show();
            }
        });

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

                            }
                        }, 12, 0, true);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(makingTimeHour, makingTimeMinute);
                timePickerDialog.show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            MyRecipiesFragment fragment = new MyRecipiesFragment();

            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
            }
        });
    }

    public void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_amount);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
