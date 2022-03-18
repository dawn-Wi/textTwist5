package com.example.texttwist5;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {
    private static volatile GameRepository INSTANCE = new GameRepository();

    public static GameRepository getINSTANCE() {
        return INSTANCE;
    }

    private FirebaseDataSource firebaseDataSource = new FirebaseDataSource();
    private List<String> sixWordsList;
    private List<String> allWordsList;

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
    public void checkSixWordMakeList(String word,final FirebaseDataSource.DataSourceCallback<String> callback){
        firebaseDataSource.checkSixWordMakeList(word,result -> {
            if(result instanceof Result.Success){
                callback.onComplete("Success");
            }
        });
    }

//    public void setSixWordMakeWordList(String word,final FirebaseDataSource.DataSourceCallback<String> callback){
//        firebaseDataSource.setSixWordMakeWordList(word,result -> {
//            if(result instanceof Result.Success){
//                callback.onComplete("Success");
//            }
//        });
//    }

    public void loadDictionary(String type, final GameRepositoryCallback<Result> callback) {
        firebaseDataSource.getDictionary(type, result -> {
            if (result instanceof Result.Success) {
                callback.onComplete(result);
            }
        });
    }

    public List<String> getAllWordsList() {
        if(allWordsList==null){
            allWordsList = new ArrayList<>();
            loadDictionary("all", new GameRepositoryCallback<Result>() {
                @Override
                public void onComplete(Result result) {
                    allWordsList = ((Result.Success<List<String>>)result).getData();
                }
            });
        }
        return allWordsList;
    }

    public void setDataSource(FirebaseDataSource ds) {
        this.firebaseDataSource = ds;
    }

    public interface GameRepositoryCallback<T> {
        void onComplete(Result result);
    }
}
