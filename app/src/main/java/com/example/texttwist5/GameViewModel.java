package com.example.texttwist5;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GameViewModel extends ViewModel {

    private GameRepository gameRepository = GameRepository.getINSTANCE();

    private final MutableLiveData<Boolean> dictionaryLoaded = new MutableLiveData<>(false);
    private MutableLiveData<GameState> myState = new MutableLiveData<>();
    private MutableLiveData<String> timerText = new MutableLiveData<>();
    private MutableLiveData<Boolean> incorrectAnswerSubmitted = new MutableLiveData<Boolean>(false);
    private MutableLiveData<String> wordsRemainingText3 = new MutableLiveData<String>();
    private MutableLiveData<String> wordsRemainingText4 = new MutableLiveData<String>();
    private MutableLiveData<String> wordsRemainingText5 = new MutableLiveData<String>();
    private MutableLiveData<String> wordsRemainingText6 = new MutableLiveData<String>();
    private MutableLiveData<String> currDisplayedText = new MutableLiveData<String>();

    private List<String> sixWordsList;
    private List<String> allWordsList;
    private ArrayList<String> correct3Answers = new ArrayList<>();
    private ArrayList<String> correct4Answers = new ArrayList<>();
    private ArrayList<String> correct5Answers = new ArrayList<>();
    private ArrayList<String> correct6Answers = new ArrayList<>();
    private ArrayList<String> showAnother3Answers = new ArrayList<>();
    private HashSet<String> answerSet = new HashSet<>();
    private Map<String, List<String>> answerMap = new HashMap<>();
    private CountDownTimer countDownTimer = new CountDownTimer(CountDownTimerHelper.convertStringToLong("000200"), 1000) {
        @Override
        public void onTick(long l) {
            timerText.setValue(CountDownTimerHelper.convertLongToString(l));
        }

        @Override
        public void onFinish() {
            timerText.setValue("Finished");
            myState.setValue(GameState.FINISHED);
        }
    };
    private String selectedWord;
    private int answerCount3;
    private int answerCount4;
    private int answerCount5;
    private int answerCount6;

    //Splash ??? ?????? - Dictionary ????????????
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

    public void newGame() {
        myState.setValue(GameState.LOADING);
        selectedWord = getRandomSixWord();
        Log.d("GameViewModel selectedWord", "newGame: "+selectedWord);
        currDisplayedText.setValue(ShuffleHelper.shuffle(selectedWord));
        initCountDownTimer();
        clearData();
        loadAnswers(selectedWord);
    }

    public void pause() {
        myState.setValue(GameState.PAUSED);
        countDownTimer.cancel();
    }

    public void resume() {
        myState.setValue(GameState.PLAYING);
        continueTimer();
    }

    private void clearData() {
        correct3Answers.clear();
        correct4Answers.clear();
        correct5Answers.clear();
        correct6Answers.clear();
        wordsRemainingText3.setValue("");
        wordsRemainingText4.setValue("");
        wordsRemainingText5.setValue("");
        wordsRemainingText6.setValue("");
        answerSet.clear();
        answerMap = new HashMap<>();
    }

    public void checkAnswer(String word) {
        if (answerSet.contains(word)) {
            if (word.length() == 3) {
                correct3Answers.add(word);
                wordsRemainingText3.setValue((answerCount3 - correct3Answers.size()) + "/" + answerCount3);
            } else if (word.length() == 4) {
                correct4Answers.add(word);
                wordsRemainingText4.setValue((answerCount4 - correct4Answers.size()) + "/" + answerCount4);
            } else if (word.length() == 5) {
                correct5Answers.add(word);
                wordsRemainingText5.setValue((answerCount5 - correct5Answers.size()) + "/" + answerCount5);
            } else {
                correct6Answers.add(word);
                wordsRemainingText6.setValue((answerCount6 - correct6Answers.size()) + "/" + answerCount6);
            }
            answerSet.remove(word);
            if (answerSet.size() == 0) {
                myState.setValue(GameState.FINISHED);
            }
        } else {
            incorrectAnswerSubmitted.setValue(true);
        }
    }

    public Map<String, List<String>> anotherAnswer(){
        List<String> anotherAnswerList = new ArrayList(answerSet);
        Map<String, List<String>> anotherAnswerMap = new HashMap<>();
        List<String> another3Answer = new ArrayList<>();
        List<String> another4Answer = new ArrayList<>();
        List<String> another5Answer = new ArrayList<>();
        List<String> another6Answer = new ArrayList<>();
        for(int i=0;i<anotherAnswerList.size();i++){
            if(anotherAnswerList.get(i).length()==3){
                another3Answer.add(anotherAnswerList.get(i));
            }
            else if(anotherAnswerList.get(i).length()==4){
                another4Answer.add(anotherAnswerList.get(i));
            }
            else if(anotherAnswerList.get(i).length()==5){
                another5Answer.add(anotherAnswerList.get(i));
            }
            else if(anotherAnswerList.get(i).length()==6){
                another6Answer.add(anotherAnswerList.get(i));
            }
            anotherAnswerMap.put("3",another3Answer);
            anotherAnswerMap.put("4",another4Answer);
            anotherAnswerMap.put("5",another5Answer);
            anotherAnswerMap.put("6",another6Answer);
        }
        return anotherAnswerMap;
    }

    public List<String> getAllWordsList() {
        return allWordsList;
    }

    public LiveData<Boolean> isDictionaryLoaded() {
        return dictionaryLoaded;
    }

    public LiveData<GameState> getGameState() {
        return myState;
    }

    public LiveData<Boolean> isIncorrectAnswerSubmitted() {
        return incorrectAnswerSubmitted;
    }

    public LiveData<String> getWordsRemainingText3() {
        return wordsRemainingText3;
    }

    public LiveData<String> getWordsRemainingText4() {
        return wordsRemainingText4;
    }

    public LiveData<String> getWordsRemainingText5() {
        return wordsRemainingText5;
    }

    public LiveData<String> getWordsRemainingText6() {
        return wordsRemainingText6;
    }

    public LiveData<String> getTimerText() {
        return timerText;
    }

    public LiveData<String> getCurrDisplayedText() {
        return currDisplayedText;
    }

    public ArrayList<String> getCorrect3Answers() {
        return correct3Answers;
    }

    public ArrayList<String> getCorrect4Answers() {
        return correct4Answers;
    }

    public ArrayList<String> getCorrect5Answers() {
        return correct5Answers;
    }

    public ArrayList<String> getCorrect6Answers() {
        return correct6Answers;
    }

    //random?????? 6?????? ????????? return ??????
    private String getRandomSixWord() {
        double randomValue = Math.random();
        int ran = (int) (randomValue * sixWordsList.size() - 1);
        return sixWordsList.get(ran);
    }

    private void initCountDownTimer() {
        timerText.setValue("00:02:00");
        if (countDownTimer != null)
            countDownTimer.cancel();
        countDownTimer = new CountDownTimer(CountDownTimerHelper.convertStringToLong("000030"), 1000) {
            @Override
            public void onTick(long l) {
                timerText.setValue(CountDownTimerHelper.convertLongToString(l));
            }

            @Override
            public void onFinish() {
                timerText.setValue("Finished");
                myState.setValue(GameState.FINISHED);
            }
        };
    }

    private void continueTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = new CountDownTimer(CountDownTimerHelper.convertStringToLongContinueTimer(timerText.getValue().toString()), 1000) {
                @Override
                public void onTick(long l) {
                    timerText.setValue(CountDownTimerHelper.convertLongToString(l));
                }

                @Override
                public void onFinish() {
                    timerText.setValue("Finished");
                    myState.setValue(GameState.FINISHED);
                }
            };
            countDownTimer.start();
        }


    }

    private void loadAnswers(String word) {
        gameRepository.loadAnswers(word, new GameRepository.GameRepositoryCallback<Result>() {
            @Override
            public void onComplete(Result result) {
                answerMap = ((Result.Success<Map>) result).getData();
                for (Map.Entry<String, List<String>> entry : answerMap.entrySet()) {
                    answerSet.addAll(entry.getValue());
                    if (entry.getKey().equals("3"))
                        answerCount3 = entry.getValue().size();
                    else if (entry.getKey().equals("4"))
                        answerCount4 = entry.getValue().size();
                    else if (entry.getKey().equals("5"))
                        answerCount5 = entry.getValue().size();
                    else if (entry.getKey().equals("6"))
                        answerCount6 = entry.getValue().size();
                }
                myState.setValue(GameState.PLAYING);
                countDownTimer.start();
            }
        });
    }

    public enum GameState {
        LOADING,
        PLAYING,
        FINISHED,
        PAUSED
    }

/*
6?????? random ??????
?????? loading ??????
??????

 */

/*    public void setDictionary(InputStream file) {
        gameRepository.setDictionary(file, result -> {
            if (result.equals("Success")) {
                dictionaryLoaded.postValue(true);
                Log.d("gameViewModel finish", "setDictionary: success");
            } else {
                dictionaryLoaded.postValue(false);
            }
        });
    }*/
}
