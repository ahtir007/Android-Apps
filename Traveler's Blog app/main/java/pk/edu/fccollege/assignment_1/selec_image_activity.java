package pk.edu.fccollege.assignment_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class selec_image_activity extends AppCompatActivity {

    //private Array_adapter_select_image adapter;
    private ArrayList<Integer> imagne_id;
    private Recycle_view_adapter aadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_pic_activity_xml);

        Intent data = getIntent();
        if (data== null) finish();




        imagne_id = new ArrayList<Integer>();
        fill_array_with_image_id();


        RecyclerView rl = findViewById(R.id.recycleview);
        this.aadapter = new Recycle_view_adapter(imagne_id,this);
        rl.setAdapter(aadapter);
        rl.setLayoutManager(new LinearLayoutManager(this));


       aadapter.setOnItemClickListener(new Recycle_view_adapter.OnItemClickListener() {
           @Override
           public void onItemClick(int position, View view) {
               int id_resid=imagne_id.get(position);

               Intent data = new Intent();
               data.putExtra(new_blog_entry_window_activity.KEY_IMG_ID,id_resid);
               setResult(new_blog_entry_window_activity.REQUEST_UPDATE,data);
               finish();
           }
       });



    }

    void fill_array_with_image_id(){
        imagne_id.add(R.drawable.slum_lahore);
        imagne_id.add(R.drawable.greater_iqbal_park);
        imagne_id.add(R.drawable.gwalior_fort);
        imagne_id.add(R.drawable.gypsy_hill_place);
        imagne_id.add(R.drawable.hazuri_bagh);
        imagne_id.add(R.drawable.hill_station);
        imagne_id.add(R.drawable.himalayan);
        imagne_id.add(R.drawable.hunza_valley);
        imagne_id.add(R.drawable.karakoram);
        imagne_id.add(R.drawable.nanga_parbat);
        imagne_id.add(R.drawable.pakistan_monument);
        imagne_id.add(R.drawable.port_arthur);
        imagne_id.add(R.drawable.saif_ul_maluk_lake);
        imagne_id.add(R.drawable.saiful_maluk_national_park);
        imagne_id.add(R.drawable.shogran);
        imagne_id.add(R.drawable.slum_lahore);
        imagne_id.add(R.drawable.sweet_rice_islamadad);
        imagne_id.add(R.drawable.taka_tak_multan);




    }




}
