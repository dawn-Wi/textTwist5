package com.example.texttwist5;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FirebaseDataSource {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void setDictionary(InputStream file, DataSourceCallback<Result> callback){
        Map<String, List<String>> user = new HashMap<>();
        List<String> wordDataAll = new ArrayList<>();
        List<String> wordData6 = new ArrayList<>();

        Scanner scanner;

        {
            scanner = new Scanner(file);
            while(scanner.hasNext()){
                String str = scanner.nextLine();
                if(str.length()==6){
                    wordData6.add(str);
                }
                else if(str.length()>=3 && str.length()<=6){
                    wordDataAll.add(str);
                }
                user.put("all",wordDataAll);
                user.put(String.valueOf(6),wordData6);
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
    public void getDictionary(){

    }

    public interface DataSourceCallback<T>{
        void onComplete(T result);
    }
}
