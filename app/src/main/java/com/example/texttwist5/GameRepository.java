package com.example.texttwist5;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GameRepository {
    private static volatile GameRepository INSTANCE = new GameRepository();

    public static GameRepository getINSTANCE() {
        return INSTANCE;
    }

    private FirebaseDataSource firebaseDataSource = new FirebaseDataSource();
    private List<String> sixWordsList;
    private List<String> allWordsList;
    private HashSet<String> hashSet;

    public void setDictionary(InputStream file, final FirebaseDataSource.DataSourceCallback<String> callback) {
        firebaseDataSource.setDictionary(file, result -> {
            if (result instanceof Result.Success) {
                callback.onComplete("Success");
            }
        });
    }

    //    public void test(String word, final FirebaseDataSource.DataSourceCallback<String> callback){
//        firebaseDataSource.test(word,result -> {
//            if (result instanceof Result.Success) {
//                callback.onComplete("Success");
//            }
//        });
//    }
    public void checkSixWordMakeList(String word, final FirebaseDataSource.DataSourceCallback<String> callback) {
        firebaseDataSource.getAnswers(word, result -> {
            if (result instanceof Result.Success) {
                callback.onComplete("Success");
            }
        });
    }


    public void loadDictionary(String type, final GameRepositoryCallback<Result> callback) {
        firebaseDataSource.getDictionary(type, result -> {
            if (result instanceof Result.Success) {
                if(type.equals("all")){
                    allWordsList = ((Result.Success<List<String>>)result).getData();
                    callback.onComplete(result);
                }
                else if(type.equals("6")){
                    sixWordsList = ((Result.Success<List<String>>)result).getData();
                    callback.onComplete(result);
                }
            }
        });
    }

    public void loadAnswers(String word, GameRepositoryCallback<Result> callback) {
        firebaseDataSource.loadAnswers(word, result -> {
            if (result instanceof Result.Success) {
                //load했는데 answer이 null이면 makeAnswers를 해야해
                hashSet = ((Result.Success<HashSet<String>>) result).getData();
                callback.onComplete(result);
//                if(hashSet.size()==0){
//                    callback.onComplete(new Result.Success(makeAnswers(word)));
//                }
            }else {
                callback.onComplete(new Result.Success(makeAnswers(word)));
            }
        });
    }


    private HashSet<String> makeAnswers(String word) {
        Map<String, List<String>> answerMap = new HashMap<>();
        HashSet<String> wordSet = new HashSet<>();
        List<String> allList = allWordsList;
        List<String> confirmWordsList3 = new ArrayList<>();
        List<String> confirmWordsList4 = new ArrayList<>();
        List<String> confirmWordsList5 = new ArrayList<>();
        List<String> confirmWordsList6 = new ArrayList<>();
        for (int i = 0; i < allList.size(); i++) {
            if (AnswerChecker.isAnswer(word, allList.get(i))) {
                if (allList.get(i).length() == 3) {
                    confirmWordsList3.add(allList.get(i));
                    wordSet.add(allList.get(i));
                } else if (allList.get(i).length() == 4) {
                    confirmWordsList4.add(allList.get(i));
                    wordSet.add(allList.get(i));
                } else if (allList.get(i).length() == 5) {
                    confirmWordsList5.add(allList.get(i));
                    wordSet.add(allList.get(i));
                } else if (allList.get(i).length() == 6) {
                    confirmWordsList6.add(allList.get(i));
                    wordSet.add(allList.get(i));
                }
            }
        }
        answerMap.put("3", confirmWordsList3);
        answerMap.put("4", confirmWordsList4);
        answerMap.put("5", confirmWordsList5);
        answerMap.put("6", confirmWordsList6);
        saveAnswers(answerMap,word);
        return wordSet;
    }

    public void saveAnswers(Map<String, List<String>> answerMap, String word){
        firebaseDataSource.saveAnswers(answerMap, word, new FirebaseDataSource.DataSourceCallback<Result>() {
            @Override
            public void onComplete(Result result) {
                Log.d("repository saveAnswers", "onComplete: Success");
            }
        });
    }




    public void setDataSource(FirebaseDataSource ds) {
        this.firebaseDataSource = ds;
    }

    public interface GameRepositoryCallback<T> {
        void onComplete(Result result);
    }
}
