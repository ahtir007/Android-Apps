package pk.edu.fccollege.assignment_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class new_blog_entry_window_activity extends AppCompatActivity {

    public static final String KEY_IMG_ID ="key_id";

    //public  static  final String KEY_
    get_item name_id;

    public static final int REQUEST_UPDATE= 500;
    TextView pic_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_blog_data_entry_activity);


        Intent update_data = getIntent();
        if (update_data!=null){
            TextView blog_title=findViewById(R.id.tv_new_place_wirte);
            EditText short_des= findViewById(R.id.tv_new_short_des);
            EditText long_des=findViewById(R.id.tv_new_long_des);
            pic_name=findViewById(R.id.tv_new_picname);
            //TextView pic_tiel= findViewById(R.id.tv_new_place_wirte);

            pic_name.setText(update_data.getStringExtra("title"));
            blog_title.setText(update_data.getStringExtra("title"));
            short_des.setText(update_data.getStringExtra("short_des"));
            long_des.setText(update_data.getStringExtra("long_des"));

        }
        else{

        }

        Button btn_chose = findViewById(R.id.btn_new_blog_choose);
        btn_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),selec_image_activity.class);
                intent.putExtra(KEY_IMG_ID,0);
                startActivityForResult(intent,REQUEST_UPDATE);
            }
        });

        Button btn_save = findViewById(R.id.btn_new_blog_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText short_des= findViewById(R.id.tv_new_short_des);
                TextView blog_title=findViewById(R.id.tv_new_place_wirte);
                EditText long_des=findViewById(R.id.tv_new_long_des);
                pic_name=findViewById(R.id.tv_new_picname);

                String pic__name= pic_name.getText().toString();
                String title= blog_title.getText().toString();
                String short__des= short_des.getText().toString();
                String long__des= long_des.getText().toString();
                if (pic__name.length()==0){
                    Toast.makeText(getBaseContext(),"Please Select Picture", Toast.LENGTH_LONG).show();
                }
                if (title.length()==0){
                    Toast.makeText(getBaseContext(),"Write Blog Title", Toast.LENGTH_LONG).show();
                }
                if (short__des.length()==0){
                    Toast.makeText(getBaseContext(),"Write Blog short description", Toast.LENGTH_LONG).show();
                }
                if (long__des.length()==0){
                    Toast.makeText(getBaseContext(),"Write Blog Long descrption", Toast.LENGTH_LONG).show();
                }
                if (pic__name.length()>0 && title.length()>0 && short__des.length()>0 && long__des.length()>0 ){

                    SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MYPREFS, Context.MODE_PRIVATE);
                    int moddy = sharedPreferences.getInt(MainActivity.KEY_mode,0);
                    //Log.d("tag","updating data"+moddy);
                    if (moddy==0){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(MainActivity.KEY_rating,0.0f);
                        Intent data = new Intent();
                        data.putExtra(blog_entery_acitivity.KEY_blog_place,pic__name);
                        data.putExtra(blog_entery_acitivity.KEY_blog_short_des,short__des);
                        data.putExtra(blog_entery_acitivity.KEY_blog_long_des,long__des);
                        data.putExtra(blog_entery_acitivity.KEY_blog_title,title);
                        setResult(blog_entery_acitivity.REQUEST_UPDATE_BLOG,data);
                        finish();
                        editor.commit();
                    }
                    else{
                        Intent updated_data = new Intent();
                        //Intent test = new Intent();
                        updated_data.putExtra(blog_entery_acitivity.KEY_blog_place,pic__name);
                        updated_data.putExtra(blog_entery_acitivity.KEY_blog_short_des,short__des);
                        updated_data.putExtra(blog_entery_acitivity.KEY_blog_long_des,long__des);
                        updated_data.putExtra(blog_entery_acitivity.KEY_blog_title,title);
                        updated_data.putExtra("position",update_data.getIntExtra("position",0));
                        updated_data.putExtra("rating",update_data.getFloatExtra("rating",0.0f));
                        updated_data.putExtra("author",update_data.getStringExtra("author"));
                        //Log.d("tag","updating data "+updated_data.getStringExtra("p"));
                        //test.putExtra("hey","hello");
                        setResult(blog_entery_acitivity.REQUEST_UPDATED_BLOG,updated_data);
                        finish();

                    }





                }



            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_UPDATE){
            if(data!=null){
                int got_img_id = data.getIntExtra(KEY_IMG_ID,-1);
                pic_name=findViewById(R.id.tv_new_picname);
                TextView blog_title=findViewById(R.id.tv_new_place_wirte);
                name_id = get_photo_and_name(got_img_id);
                pic_name.setText(name_id.name);
                blog_title.setText(name_id.name);
            }



        }
    }
    class get_item{
        String name;
        int id;

        public get_item(String name, int id) {
            this.name = name;
            this.id = id;
        }
    }


    get_item get_photo_and_name(int photo_id){

        if (R.drawable.chicken_karahi_lahore==photo_id){
            return new get_item("Chick Karahi Lahore",R.drawable.chicken_karahi_lahore);
        }
        if (R.drawable.greater_iqbal_park==photo_id){
            return new get_item("Iqbal Park Lahore",R.drawable.greater_iqbal_park);
        }
        if (R.drawable.gwalior_fort==photo_id){
            return new get_item("Gwalior fort",R.drawable.gwalior_fort);
        }
        if (R.drawable.gypsy_hill_place==photo_id){
            return new get_item("Gypsy Hill Place",R.drawable.gypsy_hill_place);
        }
        if (R.drawable.hazuri_bagh==photo_id){
            return new get_item("Hazuri Baghe",R.drawable.hazuri_bagh);
        }
        if (R.drawable.hill_station==photo_id){
            return new get_item("Hill Station Lahore",R.drawable.hill_station);
        }
        if (R.drawable.himalayan==photo_id){
            return new get_item("Himalayan",R.drawable.himalayan);
        }
        if (R.drawable.hunza_valley==photo_id){
            return new get_item("Hunza Valley",R.drawable.hunza_valley);
        }
        if (R.drawable.karakoram==photo_id){
            return new get_item("Karakorum",R.drawable.karakoram);
        }
        if (R.drawable.nanga_parbat==photo_id){
            return new get_item("Nanga Parbat",R.drawable.nanga_parbat);
        }
        if (R.drawable.pakistan_monument==photo_id){
            return new get_item("Pakistan Monument",R.drawable.pakistan_monument);
        }
        if (R.drawable.port_arthur==photo_id){
            return new get_item("Port Arthur",R.drawable.port_arthur);
        }
        if (R.drawable.saif_ul_maluk_lake==photo_id){
            return new get_item("Saif-Ul-Maluk Lake",R.drawable.saif_ul_maluk_lake);
        }
        if (R.drawable.saiful_maluk_national_park==photo_id){
            return new get_item("Saif-Ul-Maluk National Park",R.drawable.saiful_maluk_national_park);
        }
        if (R.drawable.shogran==photo_id){
            return new get_item("Shogran",R.drawable.shogran);
        }
        if (R.drawable.slum_lahore==photo_id){
            return new get_item("Slum lahore",R.drawable.slum_lahore);
        }
        if (R.drawable.sweet_rice_islamadad==photo_id){
            return new get_item("Sweet Rice Islamadad",R.drawable.sweet_rice_islamadad);
        }
        if (R.drawable.taka_tak_multan==photo_id){
            return new get_item("Taka Tak Multan",R.drawable.taka_tak_multan);
        }



        return null;
    }

}
