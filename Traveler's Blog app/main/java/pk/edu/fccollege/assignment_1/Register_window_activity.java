package pk.edu.fccollege.assignment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;


public class Register_window_activity extends AppCompatActivity {
    int check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_new_user_layout);


        Button btn_register = findViewById(R.id.btn_register_window_button);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tv_name = findViewById(R.id.tv_register_window_user_name);
                EditText tv_password = findViewById(R.id.tv_register_window_password);
                EditText tv_reenter_password = findViewById(R.id.tv_register_window_reenter_password);
                EditText tv_email = findViewById(R.id.tv_register_window_email);
                String name = tv_name.getText().toString();
                String password= tv_password.getText().toString();
                String re_password= tv_reenter_password.getText().toString();
                String email = tv_email.getText().toString();

                if (name.trim().length()==0){
                    Toast.makeText(getBaseContext(),"Enter User name", Toast.LENGTH_SHORT).show();
                }
                if (email.trim().length()==0){
                    Toast.makeText(getBaseContext(),"Enter Email ", Toast.LENGTH_SHORT).show();
                }
                if (password.compareTo(re_password)!=0){
                    Toast.makeText(getBaseContext(),"Password not match", Toast.LENGTH_SHORT).show();
                }

                if ((name.trim().length())>0 && (email.trim().length())>0 && (password.matches(re_password))){

                    try {
                         check =check_existing_user(name);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (check==1){
                        Toast.makeText(getBaseContext(),"user already exist", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            write_data_to_file(name,password);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getBaseContext(),"Registered", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }





            }
        });

    }
    int check_existing_user(String name) throws FileNotFoundException {
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
                    found_password=line;
                    count+=1;

            }
            if (count==3){
                count=0;
            }
        }
        if ((found_name.matches(name))){
            if (name.length()==0){
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

    private  void write_data_to_file(String name, String password) throws IOException {
        File directory = getFilesDir();
        Log.d("tag",""+directory);
        File file = new File(directory,"user_db.txt");

        //FileOutputStream fw = new FileOutputStream(file);
        FileWriter fw = new FileWriter(file, true);

        BufferedWriter write_file = new BufferedWriter(fw);
        write_file.write(name);
        write_file.newLine();
        write_file.write(password);
        write_file.newLine();
        write_file.close();



    }
}