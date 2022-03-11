package com.example.texttwist5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<Integer,Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        List<String> wordData3 = new ArrayList<>();
//        List<String> wordData4 = new ArrayList<>();
//        List<String> wordData5 = new ArrayList<>();
//        List<String> wordData6 = new ArrayList<>();

//        Scanner scanner = null;
//        try {
//
//            scanner = new Scanner(getAssets().open("word.txt"));
//            while (scanner.hasNext()) {
//                String str = scanner.nextLine();
//                if(str.length()==3){
//                    wordData3.add(str);
//                }
//                else if(str.length()==4){
//                    wordData4.add(str);
//                }
//                else if(str.length()==5){
//                    wordData5.add(str);
//                }
//                else if(str.length()==6){
//                    wordData6.add(str);
//                }
//                user.put(3,wordData3);
//                user.put(4,wordData4);
//                user.put(5,wordData5);
//                user.put(6,wordData6);
////                setWordMap(user);
//
//
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//    }
//    public void setWordMap(Map<Integer,Object> user){
//        this.user = user;
      }
//    public Map<Integer, Object> getWordMap(){return user;}
}