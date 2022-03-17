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
//                    }
//
//                });
//
//    }

    public void setSixWordMakeWordList(DataSourceCallback<Result> callback) {
        Map<String, List<String>> sixWordDictionary = new HashMap<>();
        db.collection("word")
                .document("dictionary")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        List<String> allList = (List) document.getData().get("all");
                        List<String> sixList = (List) document.getData().get("6");

                        List<String> confirmWordsList = new ArrayList<>();
                        String word = "";
                        for(int i=0;i<sixList.size();i++){
                            word= sixList.get(i);
                            for (int j = 0; j < allList.size(); j++) {
                                if (isAnswer(word, allList.get(j))) {
                                    confirmWordsList.add(allList.get(j));
                                }
                            }
                            sixWordDictionary.put(word,confirmWordsList);
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

                    }
                });
    }
//                        List<String> confirmWordsList = new ArrayList<>();
////


//                        for (int i = 0; i < sixList.size(); i++) {
//                            Log.d("fff", "setSixWordMakeWordList: " + sixList.get(i));
//                            char[] selectSixWordAlphabet = sixList.get(i).toCharArray();
//                            //여기 밑에 한줄 지우기
////                            String[] selectSixWordAlphabet = {"a", "c", "b", "e", "f", "s"};
//                            Arrays.sort(selectSixWordAlphabet); // 오름차순 정렬
//                            for (int j = 0; j < allList.size(); j++) {
//                                char[] selectAllWordAlphabet = allList.get(j).toCharArray();
//                                //여기 밑에 한줄 지우기
////                                String[] selectAllWordAlphabet = {"a", "s", "b"};
//                                Arrays.sort(selectAllWordAlphabet); // 오름차순 정렬
//                                boolean check = true;
//                                int n;
//
//
//                                for (int m = 0; m < selectAllWordAlphabet.length; m++) {
//                                    for (n = 0; n < selectSixWordAlphabet.length; n++) {
//                                        if (selectSixWordAlphabet[n] == selectAllWordAlphabet[m]) {
//                                            m++;
//                                        } else {
//                                            if (n == selectSixWordAlphabet.length - 1) {
//                                                break;
//                                            }
//                                        }
//                                    }
//                                    if (n == selectSixWordAlphabet.length - 1) {
//                                        check = false;
//                                        n = 1;
//                                        break;
//                                    }
//                                }
//                                if (check == true) {
//                                    Log.d("asdfasdfasdf", "setSixWordMakeWordList: " + allList.get(j));
//                                    confirmWordsList.add(allList.get(j));
//                                    Log.d("asdfasdfasdf", "setSixWordMakeWordList: " + confirmWordsList);
//                                }
////                                Log.d("asdfasdfasdf", "setSixWordMakeWordList: "+confirmWordsList);
//                            }
////                            Log.d("asdfasdfasdf", "setSixWordMakeWordList: "+confirmWordsList);
//
//                            //두개 배열 비교해서 allword가 sixword안에 다 포함되어 있으면 새로운 list에 추가
//                            //그리고 document이름을 sixword이름으로 해서 list 올려? (여기서 set을 사용하는건가)
////                            db.collection("word")
////                                    .document(sixList.get(i))
////                                    .set(confirmWordsList)
////                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
////                                        @Override
////                                        public void onSuccess(Void unused) {
////                                            Log.d("FirebaseDatasource setSixWordMakeWordList", "setSixWordMakeWordList Success");
////                                            callback.onComplete(new Result.Success<String>("Success"));
////                                        }
////                                    });
//                        }
//                        Log.d("FirebaseDatasource setSixWordMakeWordList", "setSixWordMakeWordList finish");
//                        callback.onComplete(new Result.Success<String>("Success"));
//                    } else {
//                        Log.d("FirebaseDatasource setSixWordMakeWordList", "setSixWordMakeWordList not finish");
//                        callback.onComplete(new Result.Error(new Exception()));
//                    }
//                });
//}

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
