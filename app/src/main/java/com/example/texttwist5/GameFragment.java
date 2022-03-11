package com.example.texttwist5;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameFragment extends Fragment {
    TextView game_tv_timer;
    Button alphabet1;
    Button alphabet2;
    Button alphabet3;
    Button alphabet4;
    Button alphabet5;
    Button alphabet6;
    TextView game_tv_word1;
    TextView game_tv_word2;
    TextView game_tv_word3;
    TextView game_tv_word4;
    TextView game_tv_word5;
    TextView game_tv_word6;
    Button game_bt_twist;
    Button game_bt_clear;
    Button game_bt_enter;
    Random rnd= new Random();
    ArrayList<String> chosenLetterList = new ArrayList<>();

    private wordDictionary wordDictionary;

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
        alphabet1 = view.findViewById(R.id.alphabet1);
        alphabet2 = view.findViewById(R.id.alphabet2);
        alphabet3 = view.findViewById(R.id.alphabet3);
        alphabet4 = view.findViewById(R.id.alphabet4);
        alphabet5 = view.findViewById(R.id.alphabet5);
        alphabet6 = view.findViewById(R.id.alphabet6);
        game_tv_word1 = view.findViewById(R.id.game_tv_word1);
        game_tv_word2 = view.findViewById(R.id.game_tv_word2);
        game_tv_word3 = view.findViewById(R.id.game_tv_word3);
        game_tv_word4 = view.findViewById(R.id.game_tv_word4);
        game_tv_word5 = view.findViewById(R.id.game_tv_word5);
        game_tv_word6 = view.findViewById(R.id.game_tv_word6);
        game_bt_twist = view.findViewById(R.id.game_bt_twist);
        game_bt_clear = view.findViewById(R.id.game_bt_clear);
        game_bt_enter = view.findViewById(R.id.game_bt_enter);


        String conversionTime = "000200";
        countDown(conversionTime);

        final String random_name_1 = String.valueOf((char)((int)(rnd.nextInt(26))+97));
        final String random_name_2 = String.valueOf((char)((int)(rnd.nextInt(26))+97));
        final String random_name_3 = String.valueOf((char)((int)(rnd.nextInt(26))+97));
        final String random_name_4 = String.valueOf((char)((int)(rnd.nextInt(26))+97));
        final String random_name_5 = String.valueOf((char)((int)(rnd.nextInt(26))+97));
        final String random_name_6 = String.valueOf((char)((int)(rnd.nextInt(26))+97));
        alphabet1.setText(random_name_1);
        alphabet2.setText(random_name_2);
        alphabet3.setText(random_name_3);
        alphabet4.setText(random_name_4);
        alphabet5.setText(random_name_5);
        alphabet6.setText(random_name_6);

        alphabet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(random_name_1);
                alphabet1.setEnabled(false);
            }
        });
        alphabet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(random_name_2);

            }
        });
        alphabet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(random_name_3);
            }
        });
        alphabet4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(random_name_4);
            }
        });
        alphabet5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(random_name_5);
            }
        });
        alphabet6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(random_name_6);
            }
        });

        game_bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("asdfasdfasdf", "onViewCreated: "+ chosenLetterList);
                sixLetterWords();
            }
        });

        game_bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabet1.setEnabled(true);
                alphabet2.setEnabled(true);
                alphabet3.setEnabled(true);
                alphabet4.setEnabled(true);
                alphabet5.setEnabled(true);
                alphabet6.setEnabled(true);
                chosenLetterList.clear();

            }
        });


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
            }
        }.start();
    }

    public void sixLetterWords(){
        Map<Integer,Object> user = new HashMap<>();
        user = wordDictionary.getWordMap();
        Log.d("asd", "sixLetterWords: "+user.containsKey(3));
    }
}