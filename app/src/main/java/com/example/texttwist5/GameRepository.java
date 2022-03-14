package com.example.texttwist5;

import java.io.File;
import java.io.InputStream;

public class GameRepository {
    private static volatile GameRepository INSTANCE = new GameRepository();
    public static GameRepository getINSTANCE(){return INSTANCE;}
    private FirebaseDataSource firebaseDataSource = new FirebaseDataSource();

    public void setDictionary(InputStream file, final FirebaseDataSource.DataSourceCallback<String> callback){
        firebaseDataSource.setDictionary(file, result -> {
            if(result instanceof Result.Success){
                callback.onComplete("Success");
            }
        });
    }
    public void setDataSource(FirebaseDataSource ds){this.firebaseDataSource = ds;}
    public interface GameRepositoryCallback<T>{
        void onComplete(Result result);
    }
}
