package com.example.texttwist5;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.InputStream;
import java.util.List;

public class GameViewModel extends ViewModel {
    private GameRepository gameRepository = GameRepository.getINSTANCE();
    private final MutableLiveData<Boolean> isLoaded = new MutableLiveData<>(false);
    private String randomSelectSixWord;
    char randomSixWordAlphabetArrList[] = new char[6];

    private List<String> sixWordsList;
    private List<String> allWordsList;

    public void setDictionary(InputStream file) {
        gameRepository.setDictionary(file, result -> {
            if (result.equals("Success")) {
                isLoaded.postValue(true);
                Log.d("gameViewModel finish", "setDictionary: success");
            } else {
                isLoaded.postValue(false);
            }
        });
    }

    public void setSixWordMakeWordList(){

    }

    public List<String> getAllWordsList() {
        return allWordsList;
    }

    public void loadData() {
        gameRepository.loadDictionary("all", new GameRepository.GameRepositoryCallback<Result>() {
            @Override
            public void onComplete(Result result) {
                allWordsList = ((Result.Success<List<String>>) result).getData();
                if (sixWordsList != null) {
                    isLoaded.setValue(true);
                }

            }
        });
        gameRepository.loadDictionary("6", new GameRepository.GameRepositoryCallback<Result>() {
            @Override
            public void onComplete(Result result) {
                sixWordsList = ((Result.Success<List<String>>) result).getData();
                if (allWordsList != null) {
                    isLoaded.setValue(true);
                }
            }
        });
    }

    public void randomSelectSixWord(){
        double randomValue = Math.random();
        int ran = (int) (randomValue * sixWordsList.size() - 1);
        randomSelectSixWord = sixWordsList.get(ran);
        char arr[] = new char[randomSelectSixWord.length()];
        for (int i = 0; i < randomSelectSixWord.length(); i++) {
            arr[i] = randomSelectSixWord.charAt(i);
        }
        System.out.println(arr);
        shuffle(arr,6);
        for(int i=0;i<arr.length;i++){
            randomSixWordAlphabetArrList[i] = arr[i];
            System.out.println(randomSixWordAlphabetArrList[i]);
        }
    }


    public static void shuffle(char[] array, int count) {
        char temp, temp2;
        int randomNum1, randomNum2;

        for (int i = 0; i < count; i++) {
            randomNum1 = (int) (Math.random() * array.length);
            temp = array[randomNum1];
            randomNum2 = (int) ((Math.random() * array.length));
            temp2 = array[randomNum2];
            array[randomNum1] = temp2;
            array[randomNum2] = temp;
        }
    }

    public List<String> getSixWordsList() {return sixWordsList;}

    public char[] getRandomShuffleSixWord(){return randomSixWordAlphabetArrList;}

    public LiveData<Boolean> isLoaded() {return isLoaded;}

}
