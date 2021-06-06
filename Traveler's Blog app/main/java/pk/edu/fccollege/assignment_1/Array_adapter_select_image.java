package pk.edu.fccollege.assignment_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Array_adapter_select_image extends ArrayAdapter {

    private ArrayList<Integer> imagne_id;
    private Context mycontext;
    private int resId;
    private Array_adapter_select_image adapter;


    public Array_adapter_select_image(@NonNull Context context, int resource, ArrayList<Integer> list) {
        super(context, resource,list);
        imagne_id = list;
        mycontext = context;
        resId = resource;
        adapter = this;



    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater)mycontext.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.adap_screen_pic_activity_layout, null);


        int img_resid = imagne_id.get(position);

        ImageView img = view.findViewById(R.id.recycleview);
        img.setImageResource(img_resid);

        return view;
    }




}
