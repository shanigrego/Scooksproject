package com.example.scooksproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RecipeBookFragment extends Fragment {

    private FloatingActionButton myRecpiesButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_book_activity, null);
        myRecpiesButton = view.findViewById(R.id.myRecipeFloatingBtn);
        myRecpiesButton.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = new MyRecipiesFragment();
            @Override
            public void onClick(View v) {
                ((HomePageActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).addToBackStack("back").commit();
            }
        });
        return view;
    }
}
