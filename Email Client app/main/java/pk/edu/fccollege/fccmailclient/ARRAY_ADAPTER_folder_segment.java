package pk.edu.fccollege.fccmailclient;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ARRAY_ADAPTER_folder_segment extends ArrayAdapter {

    private ArrayList<icon_name_id> icons;
    private Context mycontext;
    private int resId;
    private ARRAY_ADAPTER_folder_segment adapter;
    public ARRAY_ADAPTER_folder_segment(@NonNull Context context, int resource, ArrayList<icon_name_id> list) {
        super(context, resource,list);

        icons = list;
        mycontext = context;
        resId = resource;
        adapter = this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflator = (LayoutInflater)mycontext.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);

        View view = inflator.inflate(resId, null);


        ImageView set_icon_image= view.findViewById(R.id.folder_imageView2);
        TextView icon_name = view.findViewById(R.id.folder_text_name);

        icon_name_id name_idd= icons.get(position);
        icon_name.setText(name_idd.name);
        set_icon_image.setImageResource(name_idd.id);


        return view;
    }
}

