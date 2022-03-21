package com.example.texttwist5;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

public class GameViewModel extends ViewModel {
    private GameRepository gameRepository = GameRepository.getINSTANCE();
    private final MutableLiveData<Boolean> dictionaryLoaded = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> answerLoaded = new MutableLiveData<>(false);
    private String randomSelectSixWord;
    char randomSixWordAlphabetArrList[] = new char[6];


    private List<String> sixWordsList;
    private List<String> allWordsList;
    private HashSet<String> hashSet;

    public void setDictionary(InputStream file) {
        gameRepository.setDictionary(file, result -> {
            if (result.equals("Success")) {
                dictionaryLoaded.postValue(true);
                Log.d("gameViewModel finish", "setDictionary: success");
            } else {
                dictionaryLoaded.postValue(false);
            }
        });
    }

    //    public void test(String word){
//        gameRepository.test(word,result -> {
//            if (result.equals("Success")) {
//                Log.d("gameViewModel finish", "test: success");
//            }
//        });
//    }
    public void checkSixWordMakeList(String word) {
        gameRepository.checkSixWordMakeList(word, result -> {
            if (result.equals("Success")) {
                Log.d("gameViewModel checkSixWordMakeList", "checkSixWordMakeList: Success");
            } else {
                Log.d("gameViewModel checkSixWordMakeList", "checkSixWordMakeList: fail");
            }
        });
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
                    dictionaryLoaded.setValue(true);
                }

            }
        });
        gameRepository.loadDictionary("6", new GameRepository.GameRepositoryCallback<Result>() {
            @Override
            public void onComplete(Result result) {
                sixWordsList = ((Result.Success<List<String>>) result).getData();
                if (allWordsList != null) {
                    dictionaryLoaded.setValue(true);
                }
            }
        });
    }

    public String selectRandomSixWord() {
        double randomValue = Math.random();
        int ran = (int) (randomValue * sixWordsList.size() - 1);
        randomSelectSixWord = sixWordsList.get(ran);
        char arr[] = new char[randomSelectSixWord.length()];
        for (int i = 0; i < randomSelectSixWord.length(); i++) {
            arr[i] = randomSelectSixWord.charAt(i);
        }
        System.out.println(arr);
        shuffle(arr, 6);
        for (int i = 0; i < arr.length; i++) {
            randomSixWordAlphabetArrList[i] = arr[i];
            System.out.println(randomSixWordAlphabetArrList[i]);
        }
        return randomSelectSixWord;
    }


    public void shuffle(char[] array, int count) {
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

    public void loadAnswers(String word) {
        gameRepository.loadAnswers(word, new GameRepository.GameRepositoryCallback<Result>() {
            @Override
            public void onComplete(Result result) {
                hashSet = ((Result.Success<HashSet<String>>) result).getData();
                answerLoaded.setValue(true);
            }
        });
    }

    public boolean checkAnswer(String word){
        return hashSet.contains(word);
    }

    public List<String> getSixWordsList() {
        return sixWordsList;
    }

    public char[] getRandomShuffleSixWord() {
        return randomSixWordAlphabetArrList;
    }

    public LiveData<Boolean> isDictionaryLoaded() {
        return dictionaryLoaded;
    }

    public LiveData<Boolean> isAnswerLoaded(){
        return answerLoaded;
    }


}
