package com.example.texttwist5;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FirebaseDataSource {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void setDictionary(InputStream file, DataSourceCallback<Result> callback) {
        Map<String, List<String>> user = new HashMap<>();
        List<String> wordDataAll = new ArrayList<>();
        List<String> wordData6 = new ArrayList<>();

        List<String> list3 = new ArrayList<>();
        List<String> list4 = new ArrayList<>();
        List<String> list5 = new ArrayList<>();
        List<String> list6 = new ArrayList<>();

        Scanner scanner;

        scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            if (str.length() == 6) {
                wordData6.add(str);
            }
            if (str.length() >= 3 && str.length() <= 6) {
                wordDataAll.add(str);
            }
            user.put("all", wordDataAll);
//            user.put(String.valueOf(6), wordData6);
        }
        db.collection("word")
                .document("allDictionary")
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("setFinish", "instance initializer: asdfasdfasdfasdf");
                        callback.onComplete(new Result.Success<String>("Success"));
                    }
                });
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            if (str.length() == 6) {
                wordData6.add(str);
            }
            user.put(String.valueOf(6), wordData6);
        }
        db.collection("word")
                .document("sixDictionary")
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("setFinish", "instance initializer: asdfasdfasdfasdf");
                        callback.onComplete(new Result.Success<String>("Success"));
                    }
                });

    }

//    public void test(String word, DataSourceCallback<Result> callback) {
//        db.collection("word")
//                .document("dictionary")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        List<String> allList = (List) document.getData().get("all");
//                        List<String> confirmWordsList = new ArrayList<>();
//
//                        for (int i = 0; i < allList.size(); i++) {
//                            if (isAnswer(word, allList.get(i))) {
//                                confirmWordsList.add(allList.get(i));
//                            }
//                        }
//                        db.collection("word")
//                                .document(word)
//                                .set(confirmWordsList)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Log.d("FirebaseDatasource test", "test Success");
//                                        callback.onComplete(new Result.Success<String>("Success"));
//                                    }
//                                });
//
//                    }
//
//                });
//
//    }

    public void getAnswers(String word, DataSourceCallback<Result> callback) {
        db.collection("word")
                .document(word)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String, List<String>> answerMap = new HashMap<>();
                                List<String> confirmWordsList3 = (List<String>) document.getData().get("3");
                                List<String> confirmWordsList4 = (List<String>) document.getData().get("4");
                                List<String> confirmWordsList5 = (List<String>) document.getData().get("5");
                                List<String> confirmWordsList6 = (List<String>) document.getData().get("6");

                                answerMap.put("3", confirmWordsList3);
                                answerMap.put("4", confirmWordsList4);
                                answerMap.put("5", confirmWordsList5);
                                answerMap.put("6", confirmWordsList6);
                                callback.onComplete(new Result.Success<Map>(answerMap));
                            }
                        } else {
                            Log.d("asdf", "onComplete: Failed");
                            callback.onComplete(new Result.Error(new Exception()));
                        }
                    }
                });
    }

    public void saveAnswers(Map<String,List<String>> answerMap,String word,DataSourceCallback<Result> callback){
        db.collection("word")
                .document(word)
                .set(answerMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("FirebaseDatasource test", "test Success");
                        callback.onComplete(new Result.Success<String>("Success"));
                    }
                });
    }


    public void getDictionary(String type, DataSourceCallback<Result> callback) {
        switch (type) {
            case "all":
                db.collection("word")
                        .document("allDictionary")
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
                break;
            case "6":
                db.collection("word")
                        .document("sixDictionary")
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
                break;
        }

    }

    public void loadAnswers(String word, DataSourceCallback<Result> callback){
        db.collection("word")
                .document(word)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        HashSet<String> wordSet = new HashSet<>();
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            List<String> list3 = (List<String>) document.getData().get("3");
                            List<String> list4 = (List<String>) document.getData().get("4");
                            List<String> list5 = (List<String>) document.getData().get("5");
                            List<String> list6 = (List<String>) document.getData().get("6");
                            for(int i=0;i<list3.size();i++){
                                wordSet.add(list3.get(i));
                            }
                            for(int i=0;i<list4.size();i++){
                                wordSet.add(list4.get(i));
                            }
                            for(int i=0;i<list5.size();i++){
                                wordSet.add(list5.get(i));
                            }
                            for(int i=0;i<list6.size();i++){
                                wordSet.add(list6.get(i));
                            }
                            callback.onComplete(new Result.Success<HashSet<String>>(wordSet));
                        }else{
                            callback.onComplete(new Result.Error(new Exception()));
                        }

                    }
                });
    }

    public void loadCountSectionAnswers(String word, DataSourceCallback<Result> callback) {
        db.collection("word")
                .document(word)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String,Integer> listMap = new HashMap<>();
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<String> list3 = (List<String>) document.getData().get("3");
                            List<String> list4 = (List<String>) document.getData().get("4");
                            List<String> list5 = (List<String>) document.getData().get("5");
                            List<String> list6 = (List<String>) document.getData().get("6");
                            listMap.put("3",list3.size());
                            listMap.put("4",list4.size());
                            listMap.put("5",list5.size());
                            listMap.put("6",list6.size());
                            callback.onComplete(new Result.Success<Map<String, Integer>>(listMap));
                        }
                    }
                });
    }

    public interface DataSourceCallback<T> {
        void onComplete(T result);
    }
}
