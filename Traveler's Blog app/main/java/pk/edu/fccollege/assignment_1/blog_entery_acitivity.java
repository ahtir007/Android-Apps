package pk.edu.fccollege.assignment_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View.OnTouchListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static pk.edu.fccollege.assignment_1.MainActivity.MYPREFS;

public class blog_entery_acitivity extends AppCompatActivity {

    public static ArrayList<Class_data_blog_entries> blogs_data;
    Array_adapter_blog_entries adapter;
    int c=0;

    public  static  final String KEY_blog_place="place";
    public  static  final String KEY_blog_short_des="plassce";
    //private  static  final String KEY__blog_author_name="place";
    public  static  final String KEY_blog_long_des="pladsdsdsdce";
    public static  final String KEY_blog_title="dwsdsdsd";
    public static final int REQUEST_UPDATE_BLOG= 501;
    public static final int REQUEST_UPDATED_BLOG=502;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_entry_window);

       // Intent data = getIntent();
        //if (data== null) finish();


         blogs_data = new ArrayList<Class_data_blog_entries>();
        try {
            read_from_file_and_fill();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        adapter = new Array_adapter_blog_entries(this, R.layout.adap_for_listview_blog_enteries, blogs_data);

        ListView list= findViewById(R.id.blog_list_view);
        list.setAdapter(adapter);






        list.setItemsCanFocus(true);


        //rating bar click listener

        adapter.setOnItemClickListener(new Array_adapter_blog_entries.setOnrange() {
            @Override
            public void onRatingChanged(RatingBar ratingBarr, float ratingr, boolean fromUserr, int positionn) {
                Class_data_blog_entries rating_only= blogs_data.get(positionn);

                rating_only.setRating(ratingr);
                try {
                    update_file_for_modified_data();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),"Clicked", Toast.LENGTH_LONG).show();



                Intent data= new Intent(getApplicationContext(),description_view_activity.class);
                Class_data_blog_entries stuff = blogs_data.get(position);
                String title = stuff.getHeading();
                String author = stuff.getAuthor();
                String short_des = stuff.getShort_des();
                String long_des = stuff.getLong_des();
                float rating = stuff.getRating();


                get_item photoid_name = adapter.gt_name_and_pic_id(title);

                //Intent data_for_des_activity = new Intent(getApplicationContext(),description_view_activity.class);
                data.putExtra("title",title);
                data.putExtra("author",author);
                data.putExtra("short_des",short_des);
                data.putExtra("long_des",long_des);
                data.putExtra("pic_id",photoid_name.id);
                data.putExtra("rating",rating);
                data.putExtra("position",position);
                //data.putExtra("array",blogs_data);
                startActivityForResult(data,REQUEST_UPDATED_BLOG);


            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MYPREFS, Context.MODE_PRIVATE);
                String name =sharedPreferences.getString(MainActivity.User_KEY,"");

                Class_data_blog_entries names =blogs_data.get(position);
                if(MainActivity.person_who_access.matches("guest")){
                    Toast.makeText(getBaseContext(),"Guest Mode", Toast.LENGTH_SHORT).show();
                }
                else if(name.matches(names.getAuthor())){
                    Class_data_blog_entries e = blogs_data.get(position);

                    adapter.remove(e);
                    try {
                        update_file_for_modified_data();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getBaseContext(),"Cannot delete another author entry", Toast.LENGTH_SHORT).show();
                }




                return false;
            }
        });

        //RatingBar ratingBar =findViewById(R.id.ratingBar);
        //ratingBar.setOnRatingBarChangeListener(new V);



        Button btn_logout = findViewById(R.id.btn_blog_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.person_who_access.matches("guest")){
                    Toast.makeText(getBaseContext(),"Guest Mode", Toast.LENGTH_SHORT).show();
                }
                else{
                    finish();
                }

            }
        });

        Button btn_new_data = findViewById(R.id.btn_blognew);
        btn_new_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.person_who_access.matches("guest")){
                    Toast.makeText(getBaseContext(),"Guest Mode", Toast.LENGTH_SHORT).show();
                }
                else{

                    SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MYPREFS, Context.MODE_PRIVATE);
                    //int moddy = sharedPreferences.getInt(MainActivity.KEY_mode,0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(MainActivity.KEY_mode,0);
                        editor.commit();


                    //editor.putString(MainActivity.User_KEY,name);




                    Intent intent = new Intent(getApplicationContext(),new_blog_entry_window_activity.class);
                    intent.putExtra(KEY_blog_place,"2");
                    intent.putExtra(KEY_blog_short_des,"2");
                    intent.putExtra(KEY_blog_long_des,"2");
                    intent.putExtra(KEY_blog_title,"ww");
                    //intent.putExtra("rating",ratingg);
                    startActivityForResult(intent,REQUEST_UPDATE_BLOG);
                }



            }
        });





    }

    void read_from_file_and_fill() throws FileNotFoundException {
        File directory = getFilesDir();
        File file = new File(directory,"blog-db.txt");
        if (file.length()==0){
            int i=0;
        }
        else{
            String place="";
            String short_des="";
            String author_name="";
            float star_rating=0;
            String long_des="";

            Scanner scanner = new Scanner(file);
            int count=0;
            while(scanner.hasNextLine()){
                while (true){
                    String line = scanner.nextLine();
                    if (count==0){
                        place=line;
                        count+=1;
                        break;
                    }
                    if (count==1){
                        short_des=line;
                        count+=1;
                        break;

                    }
                    if (count==2){
                        author_name=line;
                        count+=1;
                        break;
                    }
                    if (count==3){
                        star_rating=Float.valueOf(line);
                        count+=1;
                        break;
                    }
                    if (count==4){
                        long_des=line;
                        count=0;
                        blogs_data.add(new Class_data_blog_entries(place,author_name,short_des,long_des,place,star_rating,0));
                        break;
                    }
                }

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("tag","hey data");
        if(requestCode==REQUEST_UPDATED_BLOG){
            if(data!=null){
                //Log.d("tag","updating data");
                //int getting_position = ;
                Class_data_blog_entries data_to_be_updated = blogs_data.get(data.getIntExtra("position",0));
                data_to_be_updated.setHeading(data.getStringExtra(blog_entery_acitivity.KEY_blog_title));
                data_to_be_updated.setPhoto_name(data.getStringExtra(blog_entery_acitivity.KEY_blog_title));
                data_to_be_updated.setShort_des(data.getStringExtra(blog_entery_acitivity.KEY_blog_short_des));
                data_to_be_updated.setLong_des(data.getStringExtra(blog_entery_acitivity.KEY_blog_long_des));
                data_to_be_updated.setRating(data.getFloatExtra("rating",0.0f));
                try {
                    update_file_for_modified_data();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
            }
            else{
                adapter.notifyDataSetChanged();
            }

        }
        if (requestCode==REQUEST_UPDATE_BLOG){
            if(data!=null){
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MYPREFS, Context.MODE_PRIVATE);

                String name_place = data.getStringExtra(KEY_blog_place);
                String title_of_place = data.getStringExtra(KEY_blog_title);
                String autor_name=sharedPreferences.getString(MainActivity.User_KEY,"");
                String blog_short_des = data.getStringExtra(KEY_blog_short_des);
                String blog_long_des = data.getStringExtra(KEY_blog_long_des);
                try {
                    write_to_file(name_place,title_of_place,autor_name,blog_short_des,blog_long_des);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Class_data_blog_entries newemp = new Class_data_blog_entries(name_place,autor_name,blog_short_des,
                        blog_long_des,name_place,0,0);
                adapter.add(newemp);

            }

        }




    }
    void update_file_for_modified_data() throws IOException {
        File directory = getFilesDir();
        File file = new File(directory,"blog-db.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter updated_text = new BufferedWriter(fw);

        for (Class_data_blog_entries data :blogs_data){
            updated_text.write(data.getHeading());
            updated_text.newLine();
            updated_text.write(data.getShort_des());
            updated_text.newLine();
            updated_text.write(data.getAuthor());
            updated_text.newLine();
            updated_text.write(String.valueOf(data.getRating()));
            updated_text.newLine();
            updated_text.write(data.getLong_des());
            updated_text.newLine();

        }
        updated_text.close();
    }





    void write_to_file(String name_place,String title_of_place,String autor_name,String blog_short_des,
                       String blog_long_des) throws IOException {
        File directory = getFilesDir();
        Log.d("tag",""+directory);
        File file = new File(directory,"blog-db.txt");

        //FileOutputStream fw = new FileOutputStream(file);
        FileWriter fw = new FileWriter(file, true);

        BufferedWriter write_file = new BufferedWriter(fw);
        write_file.write(name_place);
        write_file.newLine();
        write_file.write(blog_short_des);
        write_file.newLine();
        write_file.write(autor_name);
        write_file.newLine();
        write_file.write("0.0");
        write_file.newLine();
        write_file.write(blog_long_des);
        write_file.newLine();
        write_file.close();

    }


}
