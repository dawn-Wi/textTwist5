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
import java.util.List;
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
    Button game_bt_pause;
    ArrayList<String> chosenLetterList = new ArrayList<>();
    char[] randomSixWordAlphabetArrList;

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
        //region findViewById
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
        game_bt_pause = view.findViewById(R.id.game_bt_pause);
        //endregion

        gameViewModel.newGame();

        //region OnClickListeners
        game_bt_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameViewModel.getGameState().getValue() == GameViewModel.GameState.PAUSED)
                    gameViewModel.resume();
                else
                    gameViewModel.pause();
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
                gameViewModel.checkAnswer(userAnswer);
                Log.d("DEBUG", "onClick: " + userAnswer);

                showAlphabetButtons();
                hideSelectedAlphabetButtons();
                chosenLetterList.clear();
                game_bt_twist.setEnabled(true);

            }
        });

        game_bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSelectedAlphabetButtons();
                showAlphabetButtons();
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
                gameViewModel.newGame();
            }
        });
        //endregion

        gameViewModel.isIncorrectAnswerSubmitted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isIncorrectAnswer) {
                if (isIncorrectAnswer)
                    Toast.makeText(getContext(), "바보", Toast.LENGTH_SHORT).show();
            }
        });

        gameViewModel.getWordsRemainingText3().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                StringBuilder sb = new StringBuilder(s);
                List<String> correctAnswersList = gameViewModel.getCorrect3Answers();
                for (String str : correctAnswersList) {
                    sb.append("\n" + str);
                }
                game_tv_correct3Answers.setText(sb.toString());
            }
        });

        gameViewModel.getWordsRemainingText4().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                StringBuilder sb = new StringBuilder(s);
                List<String> correctAnswersList = gameViewModel.getCorrect4Answers();
                for (String str : correctAnswersList) {
                    sb.append("\n" + str);
                }
                game_tv_correct4Answers.setText(sb.toString());
            }
        });

        gameViewModel.getWordsRemainingText5().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                StringBuilder sb = new StringBuilder(s);
                List<String> correctAnswersList = gameViewModel.getCorrect5Answers();
                for (String str : correctAnswersList) {
                    sb.append("\n" + str);
                }
                game_tv_correct5Answers.setText(sb.toString());
            }
        });

        gameViewModel.getWordsRemainingText6().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                StringBuilder sb = new StringBuilder(s);
                List<String> correctAnswersList = gameViewModel.getCorrect6Answers();
                for (String str : correctAnswersList) {
                    sb.append("\n" + str);
                }
                game_tv_correct6Answers.setText(sb.toString());
            }
        });

        gameViewModel.getTimerText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                game_tv_timer.setText(s);
            }
        });

        gameViewModel.getCurrDisplayedText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                randomSixWordAlphabetArrList = s.toCharArray();
                alphabet1.setText(String.valueOf(randomSixWordAlphabetArrList[0]));
                alphabet2.setText(String.valueOf(randomSixWordAlphabetArrList[1]));
                alphabet3.setText(String.valueOf(randomSixWordAlphabetArrList[2]));
                alphabet4.setText(String.valueOf(randomSixWordAlphabetArrList[3]));
                alphabet5.setText(String.valueOf(randomSixWordAlphabetArrList[4]));
                alphabet6.setText(String.valueOf(randomSixWordAlphabetArrList[5]));
            }
        });

        gameViewModel.getGameState().observe(getViewLifecycleOwner(), new Observer<GameViewModel.GameState>() {
            @Override
            public void onChanged(GameViewModel.GameState gameState) {
                if (gameState == GameViewModel.GameState.LOADING) {
                    hideAll();
                } else if (gameState == GameViewModel.GameState.PLAYING) {
                    showAll();
                    hideSelectedAlphabetButtons();
                } else if (gameState == GameViewModel.GameState.FINISHED) {
                    hideSelectedAlphabetButtons();
                    hideAlphabetButtons();
                    hideInputButtons();
                    showAnotherAnswers();
                    game_bt_retry.setVisibility(View.VISIBLE);
                } else if (gameState == GameViewModel.GameState.PAUSED) {
                    hideAll();
                    game_bt_pause.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void showAnotherAnswers() {
        Map<String, List<String>> anotherAnswerMap = gameViewModel.anotherAnswer();
        List<String> another3AnswersList = anotherAnswerMap.get("3");
        List<String> another4AnswersList = anotherAnswerMap.get("4");
        List<String> another5AnswersList = anotherAnswerMap.get("5");
        List<String> another6AnswersList = anotherAnswerMap.get("6");

        StringBuilder sb3 = new StringBuilder();
        for (String str : another3AnswersList) {
            sb3.append("\n" + str);
        }
        game_tv_correct3Answers.append("\n" + "<못 맞춘 답>" + sb3.toString());

        StringBuilder sb4 = new StringBuilder();
        for (String str : another4AnswersList) {
            sb4.append("\n" + str);
        }
        game_tv_correct4Answers.append("\n" + "<못 맞춘 답>" + sb4.toString());

        StringBuilder sb5 = new StringBuilder();
        for (String str : another5AnswersList) {
            sb5.append("\n" + str);
        }
        game_tv_correct5Answers.append("\n" + "<못 맞춘 답>" + sb5.toString());

        StringBuilder sb6 = new StringBuilder();
        for (String str : another6AnswersList) {
            sb6.append("\n" + str);
        }
        game_tv_correct6Answers.append("\n" + "<못 맞춘 답>" + sb6.toString());
    }

    private void showSelectedLetter() {
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

    private void showAll() {
        game_tv_timer.setVisibility(View.VISIBLE);
        showAnswers();
        showInputButtons();
        showAlphabetButtons();
        showSelectedAlphabetButtons();
    }

    private void hideAll() {
        game_tv_timer.setVisibility(View.INVISIBLE);
        hideAnswers();
        hideInputButtons();
        hideAlphabetButtons();
        hideSelectedAlphabetButtons();
    }

    private void hideAnswers() {
        game_tv_correct3Answers.setVisibility(View.INVISIBLE);
        game_tv_correct4Answers.setVisibility(View.INVISIBLE);
        game_tv_correct5Answers.setVisibility(View.INVISIBLE);
        game_tv_correct6Answers.setVisibility(View.INVISIBLE);
    }

    private void showAnswers() {
        game_tv_correct3Answers.setVisibility(View.VISIBLE);
        game_tv_correct4Answers.setVisibility(View.VISIBLE);
        game_tv_correct5Answers.setVisibility(View.VISIBLE);
        game_tv_correct6Answers.setVisibility(View.VISIBLE);
    }

    private void hideInputButtons() {
        game_bt_twist.setVisibility(View.INVISIBLE);
        game_bt_clear.setVisibility(View.INVISIBLE);
        game_bt_enter.setVisibility(View.INVISIBLE);
        game_bt_retry.setVisibility(View.INVISIBLE);
        game_bt_pause.setVisibility(View.INVISIBLE);
    }

    private void showInputButtons() {
        game_bt_twist.setVisibility(View.VISIBLE);
        game_bt_clear.setVisibility(View.VISIBLE);
        game_bt_enter.setVisibility(View.VISIBLE);
        game_bt_retry.setVisibility(View.VISIBLE);
        game_bt_pause.setVisibility(View.VISIBLE);
    }

    private void hideSelectedAlphabetButtons() {
        select_bt_alphabet1.setVisibility(View.INVISIBLE);
        select_bt_alphabet2.setVisibility(View.INVISIBLE);
        select_bt_alphabet3.setVisibility(View.INVISIBLE);
        select_bt_alphabet4.setVisibility(View.INVISIBLE);
        select_bt_alphabet5.setVisibility(View.INVISIBLE);
        select_bt_alphabet6.setVisibility(View.INVISIBLE);
    }

    private void showSelectedAlphabetButtons() {
        select_bt_alphabet1.setVisibility(View.VISIBLE);
        select_bt_alphabet2.setVisibility(View.VISIBLE);
        select_bt_alphabet3.setVisibility(View.VISIBLE);
        select_bt_alphabet4.setVisibility(View.VISIBLE);
        select_bt_alphabet5.setVisibility(View.VISIBLE);
        select_bt_alphabet6.setVisibility(View.VISIBLE);
    }

    private void hideAlphabetButtons() {
        alphabet1.setVisibility(View.INVISIBLE);
        alphabet2.setVisibility(View.INVISIBLE);
        alphabet3.setVisibility(View.INVISIBLE);
        alphabet4.setVisibility(View.INVISIBLE);
        alphabet5.setVisibility(View.INVISIBLE);
        alphabet6.setVisibility(View.INVISIBLE);
    }

    private void showAlphabetButtons() {
        alphabet1.setVisibility(View.VISIBLE);
        alphabet2.setVisibility(View.VISIBLE);
        alphabet3.setVisibility(View.VISIBLE);
        alphabet4.setVisibility(View.VISIBLE);
        alphabet5.setVisibility(View.VISIBLE);
        alphabet6.setVisibility(View.VISIBLE);
    }

    public void twist() {
        ShuffleHelper.shuffle(randomSixWordAlphabetArrList);
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
    /*gameViewModel.isAnswerLoaded().observe(requireActivity(), new Observer<Boolean>() { //로딩되면
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
        });*/
}