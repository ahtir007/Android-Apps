package pk.edu.fccollege.assignment_1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class get_item{
     String name;
     int id;

    public get_item(String name, int id) {
        this.name = name;
        this.id = id;
    }
}



public class Array_adapter_blog_entries extends ArrayAdapter {
    private ArrayList<Class_data_blog_entries> blogs_data;
    private Context mycontext;
    private int resId;
    private Array_adapter_blog_entries adapter;
    private  setOnrange m;
    public RatingBar baar;

    public interface setOnrange {
        //void onItemClick(int position);
        void onRatingChanged(RatingBar ratingBarr, float ratingr, boolean fromUserr,int positionn);
    }
    public void setOnItemClickListener(setOnrange listener){
        m=listener;
    }

    public Array_adapter_blog_entries(@NonNull Context context, int resource, ArrayList<Class_data_blog_entries> list) {
        super(context, resource,list);

        blogs_data = list;
        mycontext = context;
        resId = resource;
        adapter = this;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater)mycontext.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(resId, null);



        TextView tv_heading = view.findViewById(R.id.tv_blog_location_name);
        TextView tv_short_des = view.findViewById(R.id.tv_blog_short_des);
        TextView tv_author = view.findViewById(R.id.tv_blog_author);
         baar = view.findViewById(R.id.ratingBar);

        ImageView img = view.findViewById(R.id.img_blog_img);

        Class_data_blog_entries entries = blogs_data.get(position);

        String location = entries.getPhoto_name();
        String author_name =entries.getAuthor();
        String short_des = entries.getShort_des();
        //String long_des = entries.getLong_des();
        float rating_no = entries.getRating();



       get_item item= gt_name_and_pic_id(location);

        tv_heading.setText(location);
        tv_short_des.setText(short_des);
        tv_author.setText(author_name);
        img.setImageResource(item.id);
        baar.setRating(rating_no);


       //baar.setNumStars(rating_no);


        //RatingBar bar =(RatingBar) findViewById(R.id.ratingBar);
        if(MainActivity.person_who_access.contains("guest")){
            baar.setIsIndicator(true);
        }
        else {
            baar.setIsIndicator(false);
        }

        baar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int positionn = position;
                m.onRatingChanged(ratingBar,rating,fromUser,positionn);

                //Log.d("tag",""+rating);
            }
        });



        /*
        id.setText("" + animal.getId());
        name.setText(animal.getName());
        species.setText(animal.getSpecies());
        img.setImageResource(GetImgResId(animal.getSpecies()));
*/
        return view;
    }




    get_item gt_name_and_pic_id(String name){

        if (name.contains("Chick Karahi Lahore")){
            return new get_item("Chick Karahi Lahore",R.drawable.chicken_karahi_lahore);
        }
        if (name.contains("Iqbal Park Lahore")){
            return new get_item("Iqbal Park Lahore",R.drawable.greater_iqbal_park);
        }
        if (name.contains("Gwalior fort")){
            return new get_item("Gwalior fort",R.drawable.gwalior_fort);
        }
        if (name.contains("Gypsy Hill Place")){
            return new get_item("Gypsy Hill Place",R.drawable.gypsy_hill_place);
        }
        if (name.contains("Hazuri Baghe")){
            return new get_item("Hazuri Baghe",R.drawable.hazuri_bagh);
        }
        if (name.contains("Hill Station Lahore")){
            return new get_item("Hill Station Lahore",R.drawable.hill_station);
        }
        if (name.contains("Himalayan")){
            return new get_item("Himalayan",R.drawable.himalayan);
        }
        if (name.contains("Hunza Valley")){
            return new get_item("Hunza Valley",R.drawable.hunza_valley);
        }
        if (name.contains("Karakorum")){
            return new get_item("Karakorum",R.drawable.karakoram);
        }
        if (name.contains("Nanga Parbat")){
            return new get_item("Nanga Parbat",R.drawable.nanga_parbat);
        }
        if (name.contains("Pakistan Monument")){
            return new get_item("Pakistan Monument",R.drawable.pakistan_monument);
        }
        if (name.contains("Port Arthur")){
            return new get_item("Port Arthur",R.drawable.port_arthur);
        }
        if (name.contains("Saif-Ul-Maluk Lake")){
            return new get_item("Saif-Ul-Maluk Lake",R.drawable.saif_ul_maluk_lake);
        }
        if (name.contains("Saif-Ul-Maluk National Park")){
            return new get_item("Saif-Ul-Maluk National Park",R.drawable.saiful_maluk_national_park);
        }
        if (name.contains("Shogran")){
            return new get_item("Shogran",R.drawable.shogran);
        }
        if (name.contains("Slum lahore")){
            return new get_item("Slum lahore",R.drawable.slum_lahore);
        }
        if (name.contains("Sweet Rice Islamabad")){
            return new get_item("Sweet Rice Islamabad",R.drawable.sweet_rice_islamadad);
        }
        if (name.contains("Taka Tak Multan")){
            return new get_item("Taka Tak Multan",R.drawable.taka_tak_multan);
        }


        return null;

    }






    /*
     get_item get_photo_and_name(String photo_id){

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


 */


}



