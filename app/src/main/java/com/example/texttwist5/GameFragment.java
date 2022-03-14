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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFragment extends Fragment {
    GameViewModel gameViewModel;
    TextView game_tv_timer;
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
    ArrayList<String> chosenLetterList = new ArrayList<>();
    List<String> sixWordsList;
    char arr[];


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

//        한번만 firestore에 저장하면 되는 코드
//        try {
//            gameViewModel.setDictionary(requireActivity().getAssets().open("word.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        gameViewModel.getAllWordsList();
        sixWordsList = gameViewModel.getSixWordsList();

        gameViewModel.randomSelectSixWord();
        arr = gameViewModel.getRandomShuffleSixWord();

        String conversionTime = "000200";
        countDown(conversionTime);

        alphabet1.setText(String.valueOf(arr[0]));
        alphabet2.setText(String.valueOf(arr[1]));
        alphabet3.setText(String.valueOf(arr[2]));
        alphabet4.setText(String.valueOf(arr[3]));
        alphabet5.setText(String.valueOf(arr[4]));
        alphabet6.setText(String.valueOf(arr[5]));

        select_bt_alphabet1.setVisibility(View.INVISIBLE);
        select_bt_alphabet2.setVisibility(View.INVISIBLE);
        select_bt_alphabet3.setVisibility(View.INVISIBLE);
        select_bt_alphabet4.setVisibility(View.INVISIBLE);
        select_bt_alphabet5.setVisibility(View.INVISIBLE);
        select_bt_alphabet6.setVisibility(View.INVISIBLE);


        alphabet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(arr[0]));
                alphabet1.setVisibility(View.INVISIBLE);
            }
        });
        alphabet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(arr[1]));
                alphabet2.setVisibility(View.INVISIBLE);
            }
        });
        alphabet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(arr[2]));
                alphabet3.setVisibility(View.INVISIBLE);
            }
        });
        alphabet4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(arr[3]));
                alphabet4.setVisibility(View.INVISIBLE);
            }
        });
        alphabet5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(arr[4]));
                alphabet5.setVisibility(View.INVISIBLE);
            }
        });
        alphabet6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenLetterList.add(String.valueOf(arr[5]));
                alphabet6.setVisibility(View.INVISIBLE);
            }
        });

        game_bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("asdfasdfasdf", "onViewCreated: " + chosenLetterList);
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
}