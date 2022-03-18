package com.example.texttwist5;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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

    public void checkSixWordMakeList(String word, DataSourceCallback<Result> callback) {
        db.collection("word")
                .document(word)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                callback.onComplete(new Result.Success<String>("Success"));
                            } else {
                                setSixWordMakeWordList(word, callback);
                            }
                        } else {
                            Log.d("asdf", "onComplete: Failed");
                            callback.onComplete(new Result.Error(new Exception()));
                        }
                    }
                });
    }

    public void setSixWordMakeWordList(String word, DataSourceCallback<Result> callback) {
        Map<String, List<String>> sixWordDictionary = new HashMap<>();
        db.collection("word")
                .document("allDictionary")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        List<String> allList = (List) document.getData().get("all");
                        List<String> confirmWordsList3 = new ArrayList<>();
                        List<String> confirmWordsList4 = new ArrayList<>();
                        List<String> confirmWordsList5 = new ArrayList<>();
                        List<String> confirmWordsList6 = new ArrayList<>();
                        for (int i = 0; i < allList.size(); i++) {
                            if (isAnswer(word, allList.get(i))) {
                                if (allList.get(i).length() == 3) {
                                    confirmWordsList3.add(allList.get(i));
                                } else if (allList.get(i).length() == 4) {
                                    confirmWordsList4.add(allList.get(i));
                                } else if (allList.get(i).length() == 5) {
                                    confirmWordsList5.add(allList.get(i));
                                } else if (allList.get(i).length() == 6) {
                                    confirmWordsList6.add(allList.get(i));
                                }
                            }
                        }
                        sixWordDictionary.put("3", confirmWordsList3);
                        sixWordDictionary.put("4", confirmWordsList4);
                        sixWordDictionary.put("5", confirmWordsList5);
                        sixWordDictionary.put("6", confirmWordsList6);

                        db.collection("word")
                                .document(word)
                                .set(sixWordDictionary)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("FirebaseDatasource test", "test Success");
                                        callback.onComplete(new Result.Success<String>("Success"));
                                    }
                                });
                    }
                });
    }

    public boolean isAnswer(String base, String toTest) {
        Map<Character, Integer> baseMap = countLetters(base);
        Map<Character, Integer> toTestMap = countLetters(toTest);
        for (Character c : toTestMap.keySet()) {
            if (baseMap.containsKey(c)) {
                if (baseMap.get(c) < toTestMap.get(c)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public Map<Character, Integer> countLetters(String word) {
        Map<Character, Integer> toReturn = new HashMap<>();
        char[] charWords = word.toCharArray();
        for (int i = 0; i < charWords.length; i++) {
            if (toReturn.containsKey(charWords[i])) {
                int currValue = toReturn.get(charWords[i]);
                toReturn.put(charWords[i], currValue + 1);
            } else {
                toReturn.put(charWords[i], 1);
            }
        }
        return toReturn;
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



    public interface DataSourceCallback<T> {
        void onComplete(T result);
    }
}
