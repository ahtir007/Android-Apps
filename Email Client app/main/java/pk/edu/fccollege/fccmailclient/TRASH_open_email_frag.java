package pk.edu.fccollege.fccmailclient;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class TRASH_open_email_frag extends Fragment {
    FragmentManager manager;
    Context context;

    public TRASH_open_email_frag() {
        // Required empty public constructor
    }
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item= menu.findItem(R.id.app_bar_search);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        ((Folder_selection_fragment)context).getMenuInflater().inflate(R.menu.action_bar,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.mi_logout){
            ((Folder_selection_fragment)context).finishAndRemoveTask();
        }
        if (item.getTitle().toString().matches("Compose Mail")){
            int orientation = getResources().getConfiguration().orientation;
            if(orientation== Configuration.ORIENTATION_PORTRAIT){
                int count=0;
                for(int i = 0; i<manager.getBackStackEntryCount(); i++){
                    if(manager.getBackStackEntryAt(i).getName().matches("compose email")){
                        count+=1;
                    }
                }
                if(count==1){
                    Toast.makeText(getActivity().getApplicationContext(),"Already in compose email screen", Toast.LENGTH_SHORT).show();
                }
                else{
                    Fragment one= new frag_compose_email();
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.replace(R.id.alwayscganging,one);
                    transaction.addToBackStack("compose email");
                    transaction.commit();
                }
            }
            else{//landscape
                //FragmentManager f=getParentFragmentManager();
                //  f = f.findFragmentById(R.id.right_layout).getParentFragmentManager();
                int count=0;
                for(int i = 0; i<manager.getBackStackEntryCount(); i++){
                    //Log.d("tag",""+f.getBackStackEntryAt(i).getName());
                    if(manager.getBackStackEntryAt(i).getName().matches("compose email")){
                        // Log.d("tag",""+manager.getBackStackEntryAt(i).getName());
                        count+=1;
                    }
                }
                if(count>0){
                    Toast.makeText(getActivity().getApplicationContext(),"Already in compose email screen", Toast.LENGTH_SHORT).show();
                }
                else{
                    Fragment one= new frag_compose_email();
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.replace(R.id.right_layout,one);
                    transaction.addToBackStack("compose email");
                    transaction.commit();
                }
            }

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager=getParentFragmentManager();
        context = getContext();
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_t_r_a_s_h_open_email_frag,container,false);

        Bundle data_get= this.getArguments();
        if(data_get!=null){
            String user_name = data_get.getString(INBOX_Frag.KEY_name);
            String subject = data_get.getString(INBOX_Frag.KEY_sub);
            String description = data_get.getString(INBOX_Frag.KEY_des);

            TextView et_user = view.findViewById(R.id.trash_frag_email_user);
            TextView et_subject = view.findViewById(R.id.trash_frag_email_subject);
            TextView et_des = view.findViewById(R.id.trash_frag_email_message);
            ImageView profile_pic = view.findViewById(R.id.trash_frag_email_image);

            for(icon_name_id e: MainActivity.array_of_user_with_id) {
                if (e.name.matches(user_name)) {
                    profile_pic.setImageResource(e.id);
                }
            }

            et_user.setText(user_name);
            et_subject.setText(subject);
            et_des.setText(description);
        }
        //Bundle data_get= this.getArguments();


        ImageView back= view.findViewById(R.id.trash_frag_email_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.popBackStack();
            }
        });




        return view;
    }
}