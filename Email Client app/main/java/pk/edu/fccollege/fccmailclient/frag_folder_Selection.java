package pk.edu.fccollege.fccmailclient;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

class icon_name_id{
    String name;
    int id;

    public icon_name_id(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
public class frag_folder_Selection extends Fragment {
    private ArrayList<icon_name_id> icons;
    ARRAY_ADAPTER_folder_segment adapter;
    Context context;
    FragmentManager manager;



    public frag_folder_Selection() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getContext();
        manager=getParentFragmentManager();

        add_to_array();

        adapter = new ARRAY_ADAPTER_folder_segment(getActivity(), R.layout.custome_listview_for_folder_segment, icons);





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
        //Log.d("tag", "Found fragment: ");

        if (item.getItemId()==R.id.mi_logout){
            for(int i = 0; i<manager.getBackStackEntryCount(); i++){
                manager.popBackStackImmediate();
                }

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
            else{
                //Log.d("tag","ddddddddsdsdsds");
                int count=0;
                for(int i = 0; i<manager.getBackStackEntryCount(); i++){
                    //Log.d("tag",manager.getBackStackEntryAt(i).getName()+" dddd "+i);
                    if(manager.getBackStackEntryAt(i).getName().matches("compose email")){
                        count+=1;
                    }
                }
                if(count>0){
                    Toast.makeText(getActivity().getApplicationContext(),"Already in compose email screen", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("tag","times called");
                    Fragment one= new frag_compose_email();
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.replace(R.id.right_layout,one);
                    transaction.addToBackStack("compose email");
                    transaction.commit();
                }
                return true;
            }


        }

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_frag_folder__selection,container,false);

        ((Folder_selection_fragment)context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        ListView folder_list_view = view.findViewById(R.id.folder_listview);
        folder_list_view.setAdapter(adapter);

        folder_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("tag","hola");
                if(position==0){
                    int orientation = getResources().getConfiguration().orientation;
                    if(orientation== Configuration.ORIENTATION_PORTRAIT){
                        Fragment one= new INBOX_Frag();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.alwayscganging,one);
                        transaction.addToBackStack("inbox");
                        transaction.commit();
                    }
                    else{
                        Fragment one= new INBOX_Frag();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.left_layout,one);
                        transaction.addToBackStack("inbox");
                        transaction.commit();
                    }


                }
                if(position==1){
                    int orientation = getResources().getConfiguration().orientation;
                    if(orientation== Configuration.ORIENTATION_PORTRAIT){
                        Fragment one= new DRAFT_frag();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.alwayscganging,one);
                        transaction.addToBackStack("draft");
                        transaction.commit();
                    }
                    else{
                        Fragment one= new DRAFT_frag();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.left_layout,one);
                        transaction.addToBackStack("draft");
                        transaction.commit();
                    }


                }
                if(position==2){
                    int orientation = getResources().getConfiguration().orientation;
                    if(orientation== Configuration.ORIENTATION_PORTRAIT){
                        Fragment one= new SENT_frag();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.alwayscganging,one);
                        transaction.addToBackStack("send");
                        transaction.commit();
                    }
                    else{
                        Fragment one= new SENT_frag();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.left_layout,one);
                        transaction.addToBackStack("send");
                        transaction.commit();
                    }

                }
                if(position==3){
                    int orientation = getResources().getConfiguration().orientation;
                    if(orientation== Configuration.ORIENTATION_PORTRAIT){
                        Fragment one= new TRASH_frag();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.alwayscganging,one);
                        transaction.addToBackStack("trash");
                        transaction.commit();
                    }
                    else{
                        Fragment one= new TRASH_frag();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.left_layout,one);
                        transaction.addToBackStack("trash");
                        transaction.commit();
                    }

                }
                if(position==4){
                    int orientation = getResources().getConfiguration().orientation;
                    if(orientation== Configuration.ORIENTATION_PORTRAIT){
                        Fragment one= new SPAM_frag();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.alwayscganging,one);
                        transaction.addToBackStack("spam");
                        transaction.commit();
                    }
                    else{
                        Fragment one= new SPAM_frag();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.left_layout,one);
                        transaction.addToBackStack("spam");
                        transaction.commit();
                    }

                }
            }
        });










        return view;
    }

    void add_to_array(){
        icons = new ArrayList<icon_name_id>();

        icons.add(new icon_name_id(getResources().getString(R.string.inbox).toString(),R.drawable.ic_baseline_inbox_24));
        icons.add(new icon_name_id(getResources().getString(R.string.draft).toString(),R.drawable.ic_baseline_drafts_24));
        icons.add(new icon_name_id(getResources().getString(R.string.send).toString(),R.drawable.ic_baseline_send_24));
        icons.add(new icon_name_id(getResources().getString(R.string.trash).toString(),R.drawable.ic_baseline_restore_from_trash_24));
        icons.add(new icon_name_id(getResources().getString(R.string.spam).toString(),R.drawable.ic_baseline_cancel_presentation_24));
    }



}