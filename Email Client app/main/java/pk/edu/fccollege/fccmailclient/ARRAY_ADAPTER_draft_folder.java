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

public class ARRAY_ADAPTER_draft_folder extends ArrayAdapter {

    private ArrayList<email_body> draft_array;
    private Context mycontext;
    private int resId;
    private ARRAY_ADAPTER_draft_folder adapter;




    public ARRAY_ADAPTER_draft_folder(@NonNull Context context, int resource,ArrayList<email_body> draft_array) {
        super(context, resource,draft_array);
        this.draft_array = draft_array;
        DRAFT_frag.draft_array_modified=draft_array;
        mycontext = context;
        resId = resource;
        adapter = this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflator = (LayoutInflater)mycontext.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(resId, null);

        email_body extract_email = DRAFT_frag.draft_array_modified.get(position);
        ImageView user_pic = view.findViewById(R.id.inbox_imageView2);
        TextView user_name = view.findViewById(R.id.inbox_tv_user_name);
        TextView subject_email = view.findViewById(R.id.inbox_tv_email_subject);




        if(extract_email.folder.matches("draft")){

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

        View v =new View(getContext());
        v.setBackgroundColor(000000);
        return v;
    }
    @Override
    public int getCount() {
        return DRAFT_frag.draft_array_modified.size();
    }

    @Override
    public Object getItem(int position) {
        return DRAFT_frag.draft_array_modified.get(position);
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
                    filterResults.count = draft_array.size();
                    filterResults.values = draft_array;

                }
                else{
                    ArrayList<email_body> data = new ArrayList<>();
                    String searching = constraint.toString();
                    Log.d("tag",searching);
                    for(email_body e:draft_array) {
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

                DRAFT_frag.draft_array_modified = (ArrayList<email_body>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

}
