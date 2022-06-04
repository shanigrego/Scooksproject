package com.example.scooksproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StorageManager {

    private static Set<Integer> cancelNotificationsList = new HashSet<>();

    public static void WriteToFile(String fileName, Recipe content, File path, boolean append) {
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName), append);
            ObjectOutputStream objectStream = new ObjectOutputStream(writer);
            objectStream.writeObject(content);
            objectStream.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void WriteToFile(String fileName, List<Recipe> recipes, File path, boolean append) {
        ObjectOutputStream objectStream = null;
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName), append);
            for (Recipe recipe :
                    recipes) {

                objectStream = new ObjectOutputStream(writer);
                objectStream.writeObject(recipe);
            }
            objectStream.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Recipe> ReadFromFile(String fileName, File path) {
        List<Recipe> list = new LinkedList<>();
        Recipe recipe = null;
        ObjectInputStream objectStream = null;
            try {

                FileInputStream reader = new FileInputStream(new File(path, fileName));
                while (reader.available() != 0) {
                    objectStream = new ObjectInputStream(reader);
                    recipe = (Recipe) objectStream.readObject();
                    list.add(recipe);
                }
                objectStream.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return list;
    }

    public static void unCancelNotification(int recipeStepNumber){
        cancelNotificationsList.remove(recipeStepNumber);
    }

    public static void cancelNotification(int recipeStepNumber){
        cancelNotificationsList.add(recipeStepNumber);
    }

    public static boolean isNotificationCanceled(String notificationID){
        if (cancelNotificationsList.contains(Integer.parseInt(notificationID))){
            cancelNotificationsList.remove(notificationID);
            return true;
        }
        return false;
    }
}