package pk.edu.fccollege.fccmailclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class register_screen extends AppCompatActivity {
    int check;
    ImageView user_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        getSupportActionBar().hide();

        user_pic = findViewById(R.id.register_imagine_View);
         user_pic.setTag(R.drawable.generic_male_pic);

        Spinner spinner = findViewById(R.id.register_spinner);
        spinner.setPrompt("Choose picture");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    user_pic.setImageResource(R.drawable.generic_male_pic);
                    user_pic.setTag(R.drawable.generic_male_pic);
                }
                if(position==1){
                    user_pic.setImageResource(R.drawable.generic_female_pic);
                    user_pic.setTag(R.drawable.generic_female_pic);
                }
                if(position==2){
                    user_pic.setImageResource(R.drawable.cat_pic);
                    user_pic.setTag(R.drawable.cat_pic);
                }
                if(position==3){
                    user_pic.setImageResource(R.drawable.display_pic_1);
                    user_pic.setTag(R.drawable.display_pic_1);
                }
                if(position==4){
                    user_pic.setImageResource(R.drawable.display_pic_2);
                    user_pic.setTag(R.drawable.display_pic_2);
                }
                if(position==5){
                    user_pic.setImageResource(R.drawable.display_pic_3);
                    user_pic.setTag(R.drawable.display_pic_3);
                }
                if(position==6){
                    user_pic.setImageResource(R.drawable.display_pic_4);
                    user_pic.setTag(R.drawable.display_pic_4);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button btn_register= findViewById(R.id.register_btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_username = findViewById(R.id.register_username);
                EditText et_password = findViewById(R.id.register_password);
                EditText et_confirm_password = findViewById(R.id.register_tv_confrim_password);

                String name = et_username.getText().toString();
                String password= et_password.getText().toString();
                String re_password= et_confirm_password.getText().toString();



                if (name.trim().length()==0){
                    Toast.makeText(getBaseContext(),"Enter User name", Toast.LENGTH_SHORT).show();
                }

                if (password.compareTo(re_password)!=0){
                    Toast.makeText(getBaseContext(),"Password not match", Toast.LENGTH_SHORT).show();
                }
                if ((name.trim().length())>0  && (password.matches(re_password))){

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
                        File directory = getFilesDir();
                        File file = new File(directory,name+"-mb.txt");
                        try {
                            FileWriter fw = new FileWriter(file, true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getBaseContext(),"Registered", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }
        });












    }//on_create

    int check_existing_user(String name) throws FileNotFoundException {
        File directory = getFilesDir();
        File file = new File(directory,"user-db.txt");

        Scanner scanner = new Scanner(file);

        int count=0;
        String found_name="";
        while(scanner.hasNextLine()){

            while (true){
                String line = scanner.nextLine();
                if (count==0){
                    if (line.matches(name)){
                        found_name=line;
                        count+=1;

                        break;
                    }
                    count+=1;
                    break;
                }

                if (count==1){
                        count+=1;
                        break;
                }
                if (count==2){
                    count=0;
                    break;
                }


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
        File file = new File(directory,"user-db.txt");

        //FileOutputStream fw = new FileOutputStream(file);
        FileWriter fw = new FileWriter(file, true);

        BufferedWriter write_file = new BufferedWriter(fw);
        write_file.write(name);
        write_file.newLine();
        write_file.write(password);
        write_file.newLine();
        write_file.write(user_pic.getTag().toString());
        write_file.newLine();
        write_file.close();



    }
}






