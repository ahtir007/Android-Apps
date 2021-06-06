package pk.edu.fccollege.assignment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    int found_not;

    SharedPreferences sharedPreferences;
    public static final String MYPREFS = "com.example.myapp.MYPREFS";
    public static final String User_KEY ="user_name";
    public static final String KEY_mode= "mode";
    public static final String KEY_rating= " rating";
    public static String person_who_access="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        String name =sharedPreferences.getString(User_KEY,"");
        int moddy = sharedPreferences.getInt(KEY_mode,0);
        float ratingg = sharedPreferences.getFloat(MainActivity.KEY_rating,0.0f);


        Button btn_register = findViewById(R.id.btn_main_window_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Register_window_activity.class);
                startActivity(intent);


            }
        });


        Button btn_login = findViewById(R.id.btn_main_window_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tv_name = findViewById(R.id.tv_main_window_username);
                EditText tv_pass = findViewById(R.id.tv_main_window_password);
                String name = tv_name.getText().toString();
                String password = tv_pass.getText().toString();



                try {
                     found_not= read_from_file(name,password);
                    Log.d("tag",""+found_not);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (found_not==1){
                    person_who_access="reader";
                    Toast.makeText(getBaseContext(),"User login", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),blog_entery_acitivity.class);
                    SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(User_KEY,name);
                    editor.commit();
                    startActivity(intent);
                }
                else{

                    Toast.makeText(getBaseContext(),"User not found", Toast.LENGTH_LONG).show();
                }


            }
        });


       Button btn_guest=findViewById(R.id.btn_main_window_guest);
       btn_guest.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               person_who_access="guest";
               Intent data = new Intent(getApplicationContext(),blog_entery_acitivity.class);
               startActivity(data);


               /*
               try {
                   update_rating_in_();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               */

           }
       });






    }


    int read_from_file(String name, String password) throws FileNotFoundException {
        File directory = getFilesDir();
        File file = new File(directory,"user_db.txt");

        Scanner scanner = new Scanner(file);

        int count=0;
        String found_name="";
        String found_password="";

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (count==0){
                if (line.matches(name)){
                    found_name=line;
                    count+=1;
                }
            }
            if (count==1){
                if (line.matches(password)){
                    found_password=line;
                    count+=1;
                }
            }
            if (count==3){
                count=0;
            }
        }
        if ((found_name.matches(name)) && (found_password.matches(password))){
            if (name.length()==0 || password.length()==0){
                return 0;
            }
            else{
                return 1;
            }
        }
        else{

            return 0;
        }


    }
}