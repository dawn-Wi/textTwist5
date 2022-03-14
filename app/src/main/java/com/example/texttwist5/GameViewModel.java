package com.example.texttwist5;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameViewModel extends ViewModel {
    private GameRepository gameRepository = GameRepository.getINSTANCE();
    private final MutableLiveData<Boolean> isLoaded = new MutableLiveData<>(false);

    private Map<Integer, List<String>> wordDictionary;

    public void setDictionary(InputStream file){
        gameRepository.setDictionary(file, result -> {
            if(result.equals("Success")){
                isLoaded.postValue(true);
                Log.d("gameViewModel finish", "setDictionary: success");
            }
            else{
                isLoaded.postValue(false);
            }
//            wordDictionary = ((Result.Success<Map<Integer,List<String>>>)result).getData();
        });
    }

    public Map<Integer,List<String>> getWordDictionary(){return wordDictionary;}

    public LiveData<Boolean> isLoaded(){return isLoaded;}

}
