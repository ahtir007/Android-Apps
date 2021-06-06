package pk.edu.fccollege.fccmailclient;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class SENT_frag extends Fragment {
    private ArrayList<email_body> send_array;
    ARRAY_ADAPTER_send_folder adapter;
    FragmentManager manager;
    Context context;

    public static ArrayList<email_body> send_array_modified;

    email_body change_status;

    public SENT_frag() {
        // Required empty public constructor
    }



    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item= menu.findItem(R.id.app_bar_search);
        item.setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        ((Folder_selection_fragment)context).getMenuInflater().inflate(R.menu.action_bar,menu);
        MenuItem item= menu.findItem(R.id.app_bar_search);
        SearchView search = (SearchView) item.getActionView();
        search.setQueryHint("search emails");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });



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
        if (item.getItemId()==R.id.mi_compose_mail){
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        manager=getParentFragmentManager();
        send_array= new ArrayList<>();
        context = getContext();
        try {
            read_email_from_file();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        adapter = new ARRAY_ADAPTER_send_folder(getActivity(), R.layout.custom_listview_for_indox_folder, send_array);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_frag,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ListView send_list_view = (ListView)view.findViewById(R.id.send_frag_list_view);
        send_list_view.setDivider(null);
        send_list_view.setDividerHeight(0);
        send_list_view.setAdapter(adapter);


        send_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int orientation = getResources().getConfiguration().orientation;
                if(orientation== Configuration.ORIENTATION_PORTRAIT){

                    email_body modified_search = send_array_modified.get(position);
                    for (email_body e: send_array){
                        if(modified_search.name.matches(e.name) &&
                                (modified_search.email_sub.matches(e.email_sub)&&
                                        (modified_search.email_msg.matches(e.email_msg)&&
                                                (modified_search.status.matches(e.status)&&(modified_search.folder.matches(e.folder))))))
                        {
                            change_status = e;
                            break;
                        }
                    }
                    change_status.status="read";

                    adapter.notifyDataSetChanged();
                    try {
                        update_file();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bundle data= new Bundle();

                    data.putString(INBOX_Frag.KEY_name,send_array.get(position).name);
                    data.putString(INBOX_Frag.KEY_sub,send_array.get(position).email_sub);
                    data.putString(INBOX_Frag.KEY_des,send_array.get(position).email_msg);

                    Fragment one= new SEND_open_email_frag();
                    one.setArguments(data);
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.replace(R.id.alwayscganging,one);
                    transaction.addToBackStack("send mail open");
                    transaction.commit();
                }
                else{
                    email_body modified_search = send_array_modified.get(position);
                    for (email_body e: send_array){
                        if(modified_search.name.matches(e.name) &&
                                (modified_search.email_sub.matches(e.email_sub)&&
                                        (modified_search.email_msg.matches(e.email_msg)&&
                                                (modified_search.status.matches(e.status)&&(modified_search.folder.matches(e.folder))))))
                        {
                            change_status = e;
                            break;
                        }
                    }
                    change_status.status="read";

                    adapter.notifyDataSetChanged();
                    try {
                        update_file();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bundle data= new Bundle();

                    data.putString(INBOX_Frag.KEY_name,send_array.get(position).name);
                    data.putString(INBOX_Frag.KEY_sub,send_array.get(position).email_sub);
                    data.putString(INBOX_Frag.KEY_des,send_array.get(position).email_msg);

                    Fragment one= new SEND_open_email_frag();
                    one.setArguments(data);
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.replace(R.id.right_layout,one);
                    transaction.addToBackStack("send mail open");
                    transaction.commit();
                }

            }
        });
        send_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView user_name = view.findViewById(R.id.inbox_tv_user_name);
                TextView subject = view.findViewById(R.id.inbox_tv_email_subject);


                PopupMenu popupMenu= new PopupMenu(getActivity(),view);
                popupMenu.inflate(R.menu.popup_menu_listview);
                popupMenu.getMenu().findItem(R.id.mi_draft).setVisible(false);
                popupMenu.getMenu().findItem(R.id.mi_not_spam).setVisible(false);
                popupMenu.getMenu().findItem(R.id.mi_send_to_spam).setVisible(false);
                if(user_name.getTypeface().getStyle()== Typeface.BOLD && subject.getTypeface().getStyle()==Typeface.BOLD ){
                    // mark_as_unread.setVisible(false);
                    popupMenu.getMenu().findItem(R.id.mi_unread).setVisible(false);
                    //mark_as_read.setVisible(true);
                    popupMenu.getMenu().findItem(R.id.mi_read).setVisible(true);
                }
                else{
                    //mark_as_unread.setVisible(true);
                    popupMenu.getMenu().findItem(R.id.mi_unread).setVisible(true);
                    //mark_as_read.setVisible(false);
                    popupMenu.getMenu().findItem(R.id.mi_read).setVisible(false);
                }
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.mi_unread){
                            email_body modified_search = send_array_modified.get(position);
                            for (email_body e: send_array){
                                if(modified_search.name.matches(e.name) &&
                                        (modified_search.email_sub.matches(e.email_sub)&&
                                                (modified_search.email_msg.matches(e.email_msg)&&
                                                        (modified_search.status.matches(e.status)&&(modified_search.folder.matches(e.folder))))))
                                {
                                    change_status = e;
                                    break;
                                }
                            }
                            change_status.status="unread";

                            adapter.notifyDataSetChanged();
                            try {
                                update_file();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        if(item.getItemId()==R.id.mi_read){
                            email_body modified_search = send_array_modified.get(position);
                            for (email_body e: send_array){
                                if(modified_search.name.matches(e.name) &&
                                        (modified_search.email_sub.matches(e.email_sub)&&
                                                (modified_search.email_msg.matches(e.email_msg)&&
                                                        (modified_search.status.matches(e.status)&&(modified_search.folder.matches(e.folder))))))
                                {
                                    change_status = e;
                                    break;
                                }
                            }
                            change_status.status="read";

                            adapter.notifyDataSetChanged();
                            try {
                                update_file();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        if(item.getItemId()==R.id.mi_delete){
                            email_body modified_search = send_array_modified.get(position);
                            for (email_body e: send_array){
                                if(modified_search.name.matches(e.name) &&
                                        (modified_search.email_sub.matches(e.email_sub)&&
                                                (modified_search.email_msg.matches(e.email_msg)&&
                                                        (modified_search.status.matches(e.status)&&(modified_search.folder.matches(e.folder))))))
                                {
                                    change_status = e;
                                    send_array_modified.remove(position);
                                    adapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                            send_array.remove(position);

                            adapter.notifyDataSetChanged();
                            try {
                                update_file();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }




                        return false;
                    }
                });

                return false;
            }
        });

        ImageView arrow_back = view.findViewById(R.id.send_frag_back_arrow_imageview);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.popBackStack();
            }
        });


        return view;
    }
    void update_file() throws IOException {
        File directory = getActivity().getFilesDir();
        File file = new File(directory,MainActivity.logned_in_user+"-mb.txt");
        FileWriter fw = new FileWriter(file);

        BufferedWriter write_file = new BufferedWriter(fw);
        for (email_body e: send_array){
            write_file.write(e.name);
            write_file.newLine();
            write_file.write(e.email_sub);
            write_file.newLine();
            write_file.write(e.email_msg);
            write_file.newLine();
            write_file.write(e.status);
            write_file.newLine();
            write_file.write(e.folder);
            write_file.newLine();

        }
        write_file.close();
    }
    void read_email_from_file() throws FileNotFoundException {
        File directory = getActivity().getFilesDir();
        File file = new File(directory,MainActivity.logned_in_user+"-mb.txt");
        if (file.length()==0){
            int i=0;
        }
        else
        {
            String name="";
            String email_sub="";
            String email_msg="";
            String status="";
            String folder="";
            Scanner scanner = new Scanner(file);
            int count=0;
            while(scanner.hasNextLine()){
                while (true){
                    String line = scanner.nextLine();
                    if (count==0){
                        name=line;
                        count+=1;
                        break;
                    }
                    if (count==1){
                        email_sub=line;
                        count+=1;
                        break;

                    }
                    if (count==2){
                        email_msg=line;
                        count+=1;
                        break;
                    }
                    if (count==3){
                        status=line;
                        count+=1;
                        break;
                    }
                    if (count==4){
                        folder=line;
                        count=0;
                        send_array.add(new email_body(name,email_sub,email_msg,status,folder));
                        break;
                    }
                }

            }

        }
    }

}