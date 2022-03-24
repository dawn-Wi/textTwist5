package com.example.texttwist5;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameFragment extends Fragment {
    GameViewModel gameViewModel;
    TextView game_tv_timer;
    TextView game_tv_correct3Answers;
    TextView game_tv_correct4Answers;
    TextView game_tv_correct5Answers;
    TextView game_tv_correct6Answers;
    Button alphabet1;
    Button alphabet2;
    Button alphabet3;
    Button alphabet4;
    Button alphabet5;
    Button alphabet6;
    Button select_bt_alphabet1;
    Button select_bt_alphabet2;
    Button select_bt_alphabet3;
    Button select_bt_alphabet4;
    Button select_bt_alphabet5;
    Button select_bt_alphabet6;
    Button game_bt_twist;
    Button game_bt_clear;
    Button game_bt_enter;
    Button game_bt_retry;
    ArrayList<String> chosenLetterList = new ArrayList<>();
    ArrayList<String> correct3Answers = new ArrayList<>();
    ArrayList<String> correct4Answers = new ArrayList<>();
    ArrayList<String> correct5Answers = new ArrayList<>();
    ArrayList<String> correct6Answers = new ArrayList<>();
    char randomSixWordAlphabetArrList[];
    Map<String, Integer> newCountList = new HashMap<>();
    Map<String, Integer> maxCountList = new HashMap<>();




    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        game_tv_timer = view.findViewById(R.id.game_tv_timer);
        game_tv_correct3Answers = view.findViewById(R.id.game_tv_correct3Answers);
        game_tv_correct4Answers = view.findViewById(R.id.game_tv_correct4Answers);
        game_tv_correct5Answers = view.findViewById(R.id.game_tv_correct5Answers);
        game_tv_correct6Answers = view.findViewById(R.id.game_tv_correct6Answers);
        alphabet1 = view.findViewById(R.id.alphabet1);
        alphabet2 = view.findViewById(R.id.alphabet2);
        alphabet3 = view.findViewById(R.id.alphabet3);
        alphabet4 = view.findViewById(R.id.alphabet4);
        alphabet5 = view.findViewById(R.id.alphabet5);
        alphabet6 = view.findViewById(R.id.alphabet6);
        select_bt_alphabet1 = view.findViewById(R.id.select_bt_alphabet1);
        select_bt_alphabet2 = view.findViewById(R.id.select_bt_alphabet2);
        select_bt_alphabet3 = view.findViewById(R.id.select_bt_alphabet3);
        select_bt_alphabet4 = view.findViewById(R.id.select_bt_alphabet4);
        select_bt_alphabet5 = view.findViewById(R.id.select_bt_alphabet5);
        select_bt_alphabet6 = view.findViewById(R.id.select_bt_alphabet6);
        game_bt_twist = view.findViewById(R.id.game_bt_twist);
        game_bt_clear = view.findViewById(R.id.game_bt_clear);
        game_bt_enter = view.findViewById(R.id.game_bt_enter);
        game_bt_retry = view.findViewById(R.id.game_bt_retry);

//        한번만 firestore에 저장하면 되는 코드
//        try {
//            gameViewModel.setDictionary(requireActivity().getAssets().open("word.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        String selectRandomSixWord = gameViewModel.selectRandomSixWord(); //6글자 중에 랜덤으로 단어 하나 선택
        gameViewModel.loadAnswers(selectRandomSixWord); //답지가져오기&없으면 만들기
        randomSixWordAlphabetArrList = gameViewModel.getRandomShuffleSixWord(); // 선택된 단어 랜덤으로 섞어서 배열에 저장



        gameViewModel.isAnswerLoaded().observe(requireActivity(), new Observer<Boolean>() { //로딩되면
            @Override
            public void onChanged(Boolean isLoaded) {
                if (isLoaded) {
                    String conversionTime = "000200";
                    countDown(conversionTime);

                    gameViewModel.loadCountSectionAnswers(selectRandomSixWord); // 3단어,4단어,5단어,6단어 갯수 map만들기
                    gameViewModel.isCountListLoaded().observe(requireActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean isLoaded) {
                            if(isLoaded){
                                newCountList = gameViewModel.countAnswersNumber(selectRandomSixWord);
                                maxCountList = gameViewModel.countAnswersNumber(selectRandomSixWord);

                                game_tv_correct3Answers.setText(maxCountList.get("3")+"/"+maxCountList.get("3"));
                                game_tv_correct4Answers.setText(maxCountList.get("4")+"/"+maxCountList.get("4"));
                                game_tv_correct5Answers.setText(maxCountList.get("5")+"/"+maxCountList.get("5"));
                                game_tv_correct6Answers.setText(maxCountList.get("6")+"/"+maxCountList.get("6"));

                                Log.d("ASdfasdfasdf", "onChanged: "+newCountList);
//                            showCorrectAnswers(finalUserAnswer);
                            }
                        }
                    });


                    alphabet1.setText(String.valueOf(randomSixWordAlphabetArrList[0]));
                    alphabet2.setText(String.valueOf(randomSixWordAlphabetArrList[1]));
                    alphabet3.setText(String.valueOf(randomSixWordAlphabetArrList[2]));
                    alphabet4.setText(String.valueOf(randomSixWordAlphabetArrList[3]));
                    alphabet5.setText(String.valueOf(randomSixWordAlphabetArrList[4]));
                    alphabet6.setText(String.valueOf(randomSixWordAlphabetArrList[5]));

                    select_bt_alphabet1.setVisibility(View.INVISIBLE);
                    select_bt_alphabet2.setVisibility(View.INVISIBLE);
                    select_bt_alphabet3.setVisibility(View.INVISIBLE);
                    select_bt_alphabet4.setVisibility(View.INVISIBLE);
                    select_bt_alphabet5.setVisibility(View.INVISIBLE);
                    select_bt_alphabet6.setVisibility(View.INVISIBLE);

                }
            }
        });

        alphabet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(randomSixWordAlphabetArrList[0]));
                alphabet1.setVisibility(View.INVISIBLE);
                game_bt_twist.setEnabled(false);
                showSelectedLetter();
            }
        });
        alphabet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(randomSixWordAlphabetArrList[1]));
                alphabet2.setVisibility(View.INVISIBLE);
                game_bt_twist.setEnabled(false);
                showSelectedLetter();
            }
        });
        alphabet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(randomSixWordAlphabetArrList[2]));
                alphabet3.setVisibility(View.INVISIBLE);
                game_bt_twist.setEnabled(false);
                showSelectedLetter();
            }
        });
        alphabet4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(randomSixWordAlphabetArrList[3]));
                alphabet4.setVisibility(View.INVISIBLE);
                game_bt_twist.setEnabled(false);
                showSelectedLetter();
            }
        });
        alphabet5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(randomSixWordAlphabetArrList[4]));
                alphabet5.setVisibility(View.INVISIBLE);
                game_bt_twist.setEnabled(false);
                showSelectedLetter();
            }
        });
        alphabet6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(randomSixWordAlphabetArrList[5]));
                alphabet6.setVisibility(View.INVISIBLE);
                game_bt_twist.setEnabled(false);
                showSelectedLetter();
            }
        });

        game_bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userAnswer = "";
                for (String chosenLetter : chosenLetterList) {
                    userAnswer += chosenLetter;
                }
                Log.d("DEBUG", "onClick: " + userAnswer);

//                String finalUserAnswer = userAnswer;
//                gameViewModel.isCountListLoaded().observe(requireActivity(), new Observer<Boolean>() {
//                    @Override
//                    public void onChanged(Boolean isLoaded) {
//                        if(isLoaded){
//                            newCountList = gameViewModel.countAnswersNumber(finalUserAnswer);
//                            Log.d("ASdfasdfasdf", "onChanged: "+newCountList);
////                            showCorrectAnswers(finalUserAnswer);
//                        }
//                    }
//                });


                if (gameViewModel.checkAnswer(userAnswer)) {
                    //값이 맞았을때
                    if (userAnswer.length() == 3) {
                        correct3Answers.add(userAnswer);
                        newCountList.put("3",newCountList.get("3")-1);
//                        minusValue(userAnswer);
                    } else if (userAnswer.length() == 4) {
                        correct4Answers.add(userAnswer);
                        newCountList.put("4",newCountList.get("4")-1);
//                        minusValue(userAnswer);
//                        correctAnswerCountMap.put("4",newCountList.get("4")-1);
                    } else if (userAnswer.length() == 5) {
                        correct5Answers.add(userAnswer);
                        newCountList.put("5",newCountList.get("5")-1);
//                        minusValue(userAnswer);
//                        correctAnswerCountMap.put("5",newCountList.get("5")-1);
                    } else if (userAnswer.length() == 6) {
                        correct6Answers.add(userAnswer);
                        newCountList.put("6",newCountList.get("6")-1);
//                        minusValue(userAnswer);
//                        correctAnswerCountMap.put("6",newCountList.get("6")-1);
                    }
                    showCorrectAnswers(userAnswer);
                    Log.d("DEBUG", "onClick: OK");
                } else {
                    //값이 틀렸을때
                    Toast.makeText(getContext(), "없어바보야", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", "onClick: NOT OK");
                }

                alphabet1.setVisibility(View.VISIBLE);
                alphabet2.setVisibility(View.VISIBLE);
                alphabet3.setVisibility(View.VISIBLE);
                alphabet4.setVisibility(View.VISIBLE);
                alphabet5.setVisibility(View.VISIBLE);
                alphabet6.setVisibility(View.VISIBLE);
                select_bt_alphabet1.setVisibility(View.INVISIBLE);
                select_bt_alphabet2.setVisibility(View.INVISIBLE);
                select_bt_alphabet3.setVisibility(View.INVISIBLE);
                select_bt_alphabet4.setVisibility(View.INVISIBLE);
                select_bt_alphabet5.setVisibility(View.INVISIBLE);
                select_bt_alphabet6.setVisibility(View.INVISIBLE);
                chosenLetterList.clear();
                game_bt_twist.setEnabled(true);

            }
        });

        game_bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabet1.setVisibility(View.VISIBLE);
                alphabet2.setVisibility(View.VISIBLE);
                alphabet3.setVisibility(View.VISIBLE);
                alphabet4.setVisibility(View.VISIBLE);
                alphabet5.setVisibility(View.VISIBLE);
                alphabet6.setVisibility(View.VISIBLE);
                select_bt_alphabet1.setVisibility(View.INVISIBLE);
                select_bt_alphabet2.setVisibility(View.INVISIBLE);
                select_bt_alphabet3.setVisibility(View.INVISIBLE);
                select_bt_alphabet4.setVisibility(View.INVISIBLE);
                select_bt_alphabet5.setVisibility(View.INVISIBLE);
                select_bt_alphabet6.setVisibility(View.INVISIBLE);
                chosenLetterList.clear();
                game_bt_twist.setEnabled(true);
            }
        });

        game_bt_twist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twist();
            }
        });

        game_bt_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectRandomSixWord = gameViewModel.selectRandomSixWord(); //6글자 중에 랜덤으로 단어 하나 선택
                gameViewModel.loadAnswers(selectRandomSixWord); //답지가져오기&없으면 만들기
                randomSixWordAlphabetArrList = gameViewModel.getRandomShuffleSixWord(); // 선택된 단어 랜덤으로 섞어서 배열에 저장
                countDown("000000");
            }
        });

    }

    public void showSelectedLetter() {
        String letter = chosenLetterList.get(chosenLetterList.size() - 1);
        switch (chosenLetterList.size()) {
            case 1:
                select_bt_alphabet1.setText(letter);
                select_bt_alphabet1.setVisibility(View.VISIBLE);
                break;
            case 2:
                select_bt_alphabet2.setText(letter);
                select_bt_alphabet2.setVisibility(View.VISIBLE);
                break;
            case 3:
                select_bt_alphabet3.setText(letter);
                select_bt_alphabet3.setVisibility(View.VISIBLE);
                break;
            case 4:
                select_bt_alphabet4.setText(letter);
                select_bt_alphabet4.setVisibility(View.VISIBLE);
                break;
            case 5:
                select_bt_alphabet5.setText(letter);
                select_bt_alphabet5.setVisibility(View.VISIBLE);
                break;
            case 6:
                select_bt_alphabet6.setText(letter);
                select_bt_alphabet6.setVisibility(View.VISIBLE);
                break;
        }

    }


    public void countDown(String time) {
        long conversionTime = 0;
        String getHour = time.substring(0, 2);
        String getMin = time.substring(2, 4);
        String getSecond = time.substring(4, 6);

        if (getHour.substring(0, 1) == "0") {
            getHour = getHour.substring(1, 2);
        }
        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }
        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }

        conversionTime = Long.valueOf(getHour) * 1000 * 3600 + Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;

        new CountDownTimer(conversionTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                String hour = String.valueOf(millisUntilFinished / (60 * 60 * 1000));
                long getMin = millisUntilFinished - (millisUntilFinished / (60 * 60 * 1000));
                String min = String.valueOf(getMin / (60 * 1000));
                String second = String.valueOf((getMin % (60 * 1000)) / 1000);
                String millis = String.valueOf((getMin % (60 * 1000)) % 1000);

                if (hour.length() == 1) {
                    hour = "0" + hour;
                }
                if (min.length() == 1) {
                    min = "0" + min;
                }
                if (second.length() == 1) {
                    second = "0" + second;
                }
                game_tv_timer.setText(hour + ":" + min + ":" + second);
            }

            @Override
            public void onFinish() {
                game_tv_timer.setText("finish");
                alphabet1.setEnabled(false);
                alphabet2.setEnabled(false);
                alphabet3.setEnabled(false);
                alphabet4.setEnabled(false);
                alphabet5.setEnabled(false);
                alphabet6.setEnabled(false);
                game_bt_twist.setEnabled(false);
                game_bt_clear.setEnabled(false);
                game_bt_enter.setEnabled(false);
            }
        }.start();
    }

    private void showCorrectAnswers(String userAnswer) {
        switch (userAnswer.length()) {
            case 3:
                String to3Show = "";
                for (String s : correct3Answers) {
                    to3Show += s + "\n";
                }
                game_tv_correct3Answers.setText(newCountList.get("3")+"/"+maxCountList.get("3")+"\n"+to3Show);
                break;
            case 4:
                String to4Show = "";
                for (String s : correct4Answers) {
                    to4Show += s + "\n";
                }
                game_tv_correct4Answers.setText(newCountList.get("4")+"/"+maxCountList.get("4")+"\n"+to4Show);
                break;
            case 5:
                String to5Show = "";
                for (String s : correct5Answers) {
                    to5Show += s + "\n";
                }
                game_tv_correct5Answers.setText(newCountList.get("5")+"/"+maxCountList.get("5")+"\n"+to5Show);
                break;
            case 6:
                String to6Show = "";
                for (String s : correct6Answers) {
                    to6Show += s + "\n";
                }
                game_tv_correct6Answers.setText(newCountList.get("6")+"/"+maxCountList.get("6")+"\n"+to6Show);
                break;
        }
    }

    public void twist() {
        gameViewModel.shuffle(randomSixWordAlphabetArrList, 6);
        for (int i = 0; i < randomSixWordAlphabetArrList.length; i++) {
            char[] twistArrList = new char[randomSixWordAlphabetArrList.length];
            twistArrList[i] = randomSixWordAlphabetArrList[i];
            randomSixWordAlphabetArrList[i] = twistArrList[i];
            System.out.println(randomSixWordAlphabetArrList[i]);
            alphabet1.setText(String.valueOf(randomSixWordAlphabetArrList[0]));
            alphabet2.setText(String.valueOf(randomSixWordAlphabetArrList[1]));
            alphabet3.setText(String.valueOf(randomSixWordAlphabetArrList[2]));
            alphabet4.setText(String.valueOf(randomSixWordAlphabetArrList[3]));
            alphabet5.setText(String.valueOf(randomSixWordAlphabetArrList[4]));
            alphabet6.setText(String.valueOf(randomSixWordAlphabetArrList[5]));
        }
    }
}