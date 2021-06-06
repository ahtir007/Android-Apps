package pk.edu.fccollege.assignment_1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class description_view_activity extends AppCompatActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==blog_entery_acitivity.REQUEST_UPDATED_BLOG){
            if(data!=null){
                Log.d("tag","check null data "+requestCode);
                setResult(blog_entery_acitivity.REQUEST_UPDATED_BLOG,data);
                finish();
            }
            else{
                finish();
            }
        }




    }


    private ImageView enlarge_pic;
    private Button btn_update;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_view_layout);

        Intent data = getIntent();

        TextView tv_title = findViewById(R.id.tv_description_place);
        TextView tv_author = findViewById(R.id.tv_description_author);
        TextView tv_short_des = findViewById(R.id.tv_description_short_des);
        TextView tv_long_des = findViewById(R.id.tv_description_long_des);
        RatingBar ratingBar = findViewById(R.id.ratingBar2);
        ImageView img = findViewById(R.id.img_description_layout);

        //Log.d("tag",""+data.getIntExtra("rating",0));

        tv_title.setText(data.getStringExtra("title"));
        tv_author.setText(data.getStringExtra("author"));
        tv_short_des.setText(data.getStringExtra("short_des"));
        tv_long_des.setText(data.getStringExtra("long_des"));
        tv_long_des.setMovementMethod(new ScrollingMovementMethod());
        ratingBar.setRating(data.getFloatExtra("rating",0));
        img.setImageResource(data.getIntExtra("pic_id",0));

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MYPREFS, Context.MODE_PRIVATE);
        String name =sharedPreferences.getString(MainActivity.User_KEY,"");

         btn_update = findViewById(R.id.btn_description_layout);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.person_who_access.matches("guest")){
                    Toast.makeText(getBaseContext(),"Guest Mode", Toast.LENGTH_SHORT).show();
                }
                else if(name.matches(data.getStringExtra("author"))){
                    SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MYPREFS, Context.MODE_PRIVATE);
                    //int moddy = sharedPreferences.getInt(MainActivity.KEY_mode,0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(MainActivity.KEY_mode,1);
                    editor.commit();

                    Intent update_data= new Intent(getApplicationContext(),new_blog_entry_window_activity.class);
                    update_data.putExtra("title",data.getStringExtra("title"));
                    update_data.putExtra("author",data.getStringExtra("author"));
                    update_data.putExtra("short_des",data.getStringExtra("short_des"));
                    update_data.putExtra("long_des",data.getStringExtra("long_des"));
                    update_data.putExtra("pic_id",data.getIntExtra("pic_id",0));
                    update_data.putExtra("rating",ratingBar.getRating());
                    update_data.putExtra("position",data.getIntExtra("position",0));
                    startActivityForResult(update_data,blog_entery_acitivity.REQUEST_UPDATED_BLOG);

                }
                else{

                    Toast.makeText(getBaseContext(),"Cannot change another Author entry", Toast.LENGTH_SHORT).show();



                }


            }
        });
        if(MainActivity.person_who_access.contains("guest")){
            ratingBar.setIsIndicator(true);
        }
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int position_array= data.getIntExtra("position",0);
                //ArrayList<Class_data_blog_entries> rating_only = blog_entery_acitivity.blogs_data;
                Class_data_blog_entries change_rate_only = blog_entery_acitivity.blogs_data.get(position_array);
                change_rate_only.setRating(rating);
                ratingBar.setRating(rating);

                try {
                    update_file_for_modified_data();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        enlarge_pic= findViewById(R.id.expanded_image);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 big_image(data);
                Toast.makeText(getBaseContext(),"Click on enlarged image to close it ", Toast.LENGTH_SHORT).show();
            }
        });

       enlarge_pic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               enlarge_pic.setVisibility(View.GONE);
               btn_update.setVisibility(View.VISIBLE);


           }
       });

    }//oncreate data.getIntExtra("pic_id",0)

    void big_image(Intent data){
        enlarge_pic.setImageResource(data.getIntExtra("pic_id",0));
        enlarge_pic.setVisibility(View.VISIBLE);
        btn_update.setVisibility(View.GONE);
    }






    void update_file_for_modified_data() throws IOException {
        File directory = getFilesDir();
        File file = new File(directory,"blog-db.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter updated_text = new BufferedWriter(fw);

        for (Class_data_blog_entries data :blog_entery_acitivity.blogs_data){
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



}
