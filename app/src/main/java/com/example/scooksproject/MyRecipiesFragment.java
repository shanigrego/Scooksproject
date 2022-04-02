package com.example.scooksproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyRecipiesFragment extends Fragment {

    private FloatingActionButton addRecipeBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_recipies, null);
        addRecipeBtn = view.findViewById(R.id.addRecipeBtn);

        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = new AddRecipeFragment();
            @Override
            public void onClick(View v) {
                ((HomePageActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
            }
        });
        return view;
    }
}
