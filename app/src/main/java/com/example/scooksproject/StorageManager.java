package com.example.scooksproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class StorageManager {


    public static void WriteToFile(String fileName, Recipe content,File path)
    {
        try {
        FileOutputStream writer= new FileOutputStream(new File(path,fileName),true);
//        if(path.length()==0)
//        {
//            ObjectOutputStream objectStream= new ObjectOutputStream(writer);
//            objectStream.writeObject(content);
//            objectStream.close();
//        }
//        else{
//            MyObjectOutputStream objectStream= new MyObjectOutputStream(writer);
//            objectStream.writeObject(content);
//            objectStream.close();
//        }
        ObjectOutputStream objectStream= new ObjectOutputStream(writer);
        objectStream.writeObject(content);
        objectStream.close();
        writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Recipe> ReadFromFile(String fileName, File path)
    {
        List<Recipe> list= new LinkedList<>();
        Recipe recipe=null;
        try {
            FileInputStream reader= new FileInputStream(new File(path,fileName));
            ObjectInputStream objectStream= new ObjectInputStream(reader);
            recipe=(Recipe)objectStream.readObject();
            while(reader.available()!=0) {
                list.add(recipe);
                recipe = (Recipe) objectStream.readObject();
            }
            objectStream.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}