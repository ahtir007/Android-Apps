package pk.edu.fccollege.fccmailclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    int check;
    public static  String logned_in_user;
    public  static ArrayList<icon_name_id> array_of_user_with_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();



        Button btn_login= findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_username = findViewById(R.id.login_tv_user_name);
                EditText et_password = findViewById(R.id.login_tv_password);
                String name = et_username.getText().toString();
                String password= et_password.getText().toString();

                if (name.trim().length()==0){
                    Toast.makeText(getBaseContext(),"Enter User name", Toast.LENGTH_SHORT).show();
                }
                if (password.trim().length()==0){
                    Toast.makeText(getBaseContext(),"Enter Password", Toast.LENGTH_SHORT).show();
                }
                if ((name.trim().length())>0  && (password.trim().length())>0 ){

                    try {

                        check =read_from_file(name,password);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (check==0){
                        Toast.makeText(getBaseContext(),"username/password incorrect", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        array_of_user_with_id= new ArrayList<>();
                        //addfrag();
                        try {
                            poplate_array_id();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        Intent data = new Intent(getApplicationContext(),Folder_selection_fragment.class);
                        logned_in_user=name;
                        startActivity(data);
                    }

                }
            }
        });

        Button btn_register= findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(getApplicationContext(),register_screen.class);
                startActivity(data);
            }
        });

    }//on_create


    void poplate_array_id() throws FileNotFoundException {
        File directory = getFilesDir();
        File file = new File(directory, "user-db.txt");

        Scanner scanner = new Scanner(file);
        int count = 0;
        String array_name = "";
        String id = "";
        while (scanner.hasNextLine()) {
            while (true) {

                    String line = scanner.nextLine();

                    if (count == 0) {
                        array_name = line;
                        count += 1;
                        break;
                    }
                    if (count == 1) {
                        count += 1;
                        break;

                    }
                    if (count == 2) {
                        id = line;
                        array_of_user_with_id.add(new icon_name_id(array_name, Integer.valueOf(id)));
                        count = 0;
                        break;
                    }



            }
        }
    }


    int read_from_file(String name, String password) throws FileNotFoundException {
        File directory = getFilesDir();
        File file = new File(directory,"user-db.txt");

        Scanner scanner = new Scanner(file);

        int count=0;
        String found_name="";

        String found_password="";


        while(scanner.hasNextLine()){
            while (true){


                if (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    if (count==0){
                        if (line.matches(name)){
                            found_name=line;
                            count+=1;
                            break;
                        }
                    }
                    if (count==1){
                        if (line.matches(password)){
                            found_password=line;
                            count+=1;
                            break;
                        }
                    }
                    if (count==2){

                        count=0;
                        break;
                    }

                }
                else {
                    break;
                }
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