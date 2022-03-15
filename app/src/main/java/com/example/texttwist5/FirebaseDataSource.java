package com.example.texttwist5;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class FirebaseDataSource {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void setDictionary(InputStream file, DataSourceCallback<Result> callback) {
        Map<String, List<String>> user = new HashMap<>();
        List<String> wordDataAll = new ArrayList<>();
        List<String> wordData6 = new ArrayList<>();

        Scanner scanner;

        {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                if (str.length() == 6) {
                    wordData6.add(str);
                } else if (str.length() >= 3 && str.length() <= 6) {
                    wordDataAll.add(str);
                }
                user.put("all", wordDataAll);
                user.put(String.valueOf(6), wordData6);
            }
            db.collection("word")
                    .document("dictionary")
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("setFinish", "instance initializer: asdfasdfasdfasdf");
                            callback.onComplete(new Result.Success<String>("Success"));
                        }
                    });
        }
    }

    public void setSixWordMakeWordList(DataSourceCallback<Result> callback){
        db.collection("word")
                .document("dictionary")
                .get()
                .addOnCompleteListener(task ->{
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        List<String> allList = (List) document.getData().get("all");
                        List<String> sixList = (List) document.getData().get("6");


                        Log.d("FirebaseDatasource getDictionary", "getDictionary finish");
                        callback.onComplete(new Result.Success<String>("Success"));
                    } else {
                        Log.d("FirebaseDatasource getDictionary", "getDictionary not finish");
                        callback.onComplete(new Result.Error(new Exception()));
                    }
                });
    }



    public void getDictionary(String type,DataSourceCallback<Result> callback) {
        db.collection("word")
                .document("dictionary")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        List<String> list = (List) document.getData().get(type);
                        Log.d("FirebaseDatasource getDictionary", "getDictionary finish");
                        callback.onComplete(new Result.Success<List<String>>(list));
                    } else {
                        Log.d("FirebaseDatasource getDictionary", "getDictionary not finish");
                        callback.onComplete(new Result.Error(new Exception()));
                    }
                });
    }


    public interface DataSourceCallback<T> {
        void onComplete(T result);
    }
}
