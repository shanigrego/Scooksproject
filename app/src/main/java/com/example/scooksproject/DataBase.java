package com.example.scooksproject;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class DataBase {

    private static DataBase obj;
    private static DatabaseReference databaseReference;
    private FirebaseDatabase db;
    private FirebaseStorage st;
    private StorageReference mStorgaeRef;
    private static ArrayList<Recipe> allRecipes;

    private DataBase() {
        db = FirebaseDatabase.getInstance();
        st = FirebaseStorage.getInstance();
        mStorgaeRef = st.getReference("Uploads");
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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for (DataSnapshot dataSanpshot : dataSnapshots) {
                    allRecipes.add(dataSnapshot.child(dataSanpshot.getKey()).getValue(Recipe.class));
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
    }
}
