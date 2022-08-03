package com.example.scooksproject;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class DataBase {

    private static DataBase obj;
    private static DatabaseReference databaseReference;
    private FirebaseDatabase db;
    private FirebaseStorage st;
    private static StorageReference storageReference;
    private static ArrayList<Recipe> allRecipes;

    private DataBase() {
        db = FirebaseDatabase.getInstance();
        st = FirebaseStorage.getInstance();
        storageReference = st.getReference("Uploads");
        databaseReference = db.getReference("DB");
        allRecipes = new ArrayList<>();
    }

    public static DataBase getInstance() {
        if (obj == null) {
            synchronized (DataBase.class) {
                if (obj == null) {
                    obj = new DataBase();//instance will be created at request time
                }
            }
        }
        return obj;
    }

    public static ArrayList<Recipe> getAllRecipes() {
        if(allRecipes.isEmpty()){
            fetchRecipesFromDB();
        }
        return allRecipes;
    }

    public static ArrayList<Recipe> fetchRecipesFromDB() {
        final Recipe[] recipe = {null};
        final StorageReference[] fileReference = {null};
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for (DataSnapshot dataSanpshot : dataSnapshots) {
                    recipe[0] = dataSnapshot.child(dataSanpshot.getKey()).getValue(Recipe.class);
                    allRecipes.add(recipe[0]);
                    fileReference[0] = storageReference.child(recipe[0].getName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return allRecipes;
    }

    public void uploadRecipe(Recipe recipe) {
        databaseReference.child(recipe.getName()).setValue(recipe);
        StorageReference fileReference = storageReference.child(recipe.getName());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
    }
}
