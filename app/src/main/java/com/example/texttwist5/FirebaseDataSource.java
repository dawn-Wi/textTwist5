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

    public void setSixWordMakeWordList(DataSourceCallback<Result> callback) {
        db.collection("word")
                .document("dictionary")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        List<String> allList = (List) document.getData().get("all");
                        List<String> sixList = (List) document.getData().get("6");

                        for (int i = 0; i < sixList.size(); i++) {
                            char[] selectSixWordAlphabet = new char[sixList.get(i).length()];
                            for (int j = 0; j < selectSixWordAlphabet.length; j++) {
                                selectSixWordAlphabet[j] = sixList.get(i).charAt(j);//6글자 단어 하나에 들어간 알파벳을 배열에 넣기
                                Arrays.sort(selectSixWordAlphabet); // 오름차순 정렬
                            }
                            for (int j = 0; j < allList.size(); j++) {
                                char[] selectAllWordAlphabet = new char[allList.get(j).length()];
                                for(int k=0;k<selectAllWordAlphabet.length;k++){
                                    selectAllWordAlphabet[k] = allList.get(j).charAt(k); //모든글자 단어 처음부터 하나씩 알파벳 배열에 넣기
                                    Arrays.sort(selectAllWordAlphabet); // 오름차순 정렬
                                }
                                for(int m=0;m<selectAllWordAlphabet.length;m++){
                                    for(int n=0;n<selectSixWordAlphabet.length;n++){
                                        if(selectSixWordAlphabet[n] == selectAllWordAlphabet[m]){

                                        }
                                    }
                                }
                            }
                            //두개 배열 비교해서 allword가 sixword안에 다 포함되어 있으면 새로운 list에 추가
                            //그리고 document이름을 sixword이름으로 해서 list 올려? (여기서 set을 사용하는건가)

                        }


                        Log.d("FirebaseDatasource getDictionary", "getDictionary finish");
                        callback.onComplete(new Result.Success<String>("Success"));
                    } else {
                        Log.d("FirebaseDatasource getDictionary", "getDictionary not finish");
                        callback.onComplete(new Result.Error(new Exception()));
                    }
                });
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
