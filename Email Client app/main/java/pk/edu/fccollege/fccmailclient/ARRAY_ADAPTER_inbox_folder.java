package pk.edu.fccollege.fccmailclient;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ARRAY_ADAPTER_inbox_folder extends ArrayAdapter {

    private ArrayList<email_body> inbox_array;
    private Context mycontext;
    private int resId;
    private ARRAY_ADAPTER_inbox_folder adapter;


    public ARRAY_ADAPTER_inbox_folder(@NonNull Context context, int resource,ArrayList<email_body> inbox_array) {
        super(context, resource,inbox_array);
        this.inbox_array = inbox_array;
        INBOX_Frag.inbox_array_modeifed=inbox_array;
        mycontext = context;
        resId = resource;
        adapter = this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater)mycontext.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(resId, null);


        email_body extract_email = INBOX_Frag.inbox_array_modeifed.get(position);
        ImageView user_pic = view.findViewById(R.id.inbox_imageView2);
        TextView user_name = view.findViewById(R.id.inbox_tv_user_name);
        TextView subject_email = view.findViewById(R.id.inbox_tv_email_subject);



        //Log.d("tag",extract_email.name);

        if(extract_email.folder.matches("inbox")){

            user_name.setText(extract_email.name);
            subject_email.setText(extract_email.email_sub);
            if(extract_email.status.matches("unread")){
                user_name.setTypeface(null, Typeface.BOLD);
                subject_email.setTypeface(null, Typeface.BOLD);
            }
            for(icon_name_id e: MainActivity.array_of_user_with_id){
                if(e.name.matches(extract_email.name)){
                    user_pic.setImageResource(e.id);
                }
            }
            return view;
        }



        //view.setVisibility(View.GONE);
        View v =new View(getContext());
        v.setBackgroundColor(000000);
        //v.setVisibility(View.INVISIBLE);

        return v;
        //return view;

    }
    @Override
    public int getCount() {
        return INBOX_Frag.inbox_array_modeifed.size();
    }

    @Override
    public Object getItem(int position) {
        return INBOX_Frag.inbox_array_modeifed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = inbox_array.size();
                    filterResults.values = inbox_array;

                }
                else{
                    ArrayList<email_body> data = new ArrayList<>();
                    String searching = constraint.toString();
                    Log.d("tag",searching);
                    for(email_body e:inbox_array) {
                        if (e.name.contains(searching)) {
                            data.add(e);
                        }
                    }
                    filterResults.count=data.size();
                    filterResults.values = data;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                INBOX_Frag.inbox_array_modeifed = (ArrayList<email_body>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
