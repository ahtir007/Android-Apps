package pk.edu.fccollege.fccmailclient;

import android.content.Context;
import android.content.res.Configuration;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class frag_compose_email extends Fragment {

    FragmentManager fragmentManager;

    int check;
    Context context;


    public frag_compose_email() {
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
        //Log.d("tag", "Found fragment: ");

        if (item.getTitle().toString().matches("Logout")){
            ((Folder_selection_fragment)context).finishAndRemoveTask();
        }
        if (item.getTitle().toString().matches("Compose Mail")){
            int orientation = getResources().getConfiguration().orientation;
            if(orientation== Configuration.ORIENTATION_PORTRAIT){
                int count=0;
                for(int i = 0; i<fragmentManager.getBackStackEntryCount(); i++){
                    if(fragmentManager.getBackStackEntryAt(i).getName().matches("compose email")){
                        count+=1;
                    }
                }
                if(count==1){
                    Toast.makeText(getActivity().getApplicationContext(),"Already in compose email screen", Toast.LENGTH_SHORT).show();
                }
                else{
                    Fragment one= new frag_compose_email();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.alwayscganging,one);
                    transaction.addToBackStack("compose email");
                    transaction.commit();
                }
            }
            else{//landscape
                //FragmentManager f=getParentFragmentManager();
                     //  f = f.findFragmentById(R.id.right_layout).getParentFragmentManager();

            }

        }

        return true;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        fragmentManager=getParentFragmentManager();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_compose_email,container,false);


        Bundle data_get_from_open_email=this.getArguments();
        if(data_get_from_open_email!=null){
            EditText et_user = view.findViewById(R.id.compose_email_username);
            et_user.setText(data_get_from_open_email.getString(INBOX_Frag.KEY_name));
            if(data_get_from_open_email.getString(INBOX_Frag.KEY_sub)!=null){
                EditText et_sub = view.findViewById(R.id.compose_email_subject);
                et_sub.setText(data_get_from_open_email.getString(INBOX_Frag.KEY_sub));
            }
            if(data_get_from_open_email.getString(INBOX_Frag.KEY_des)!=null){
                EditText et_des = view.findViewById(R.id.compose_email_description_text);
                et_des.setText(data_get_from_open_email.getString(INBOX_Frag.KEY_des));
            }
        }




        Button btn_send= view.findViewById(R.id.compose_email_btn_send);
        Button btn_draft= view.findViewById(R.id.compose_email_btn_draft);
        Button btn_savedraft= view.findViewById(R.id.compose_email_btn_save_draft2);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText et_user_name= view.findViewById(R.id.compose_email_username);
                EditText et_subject = view.findViewById(R.id.compose_email_subject);
                EditText et_description = view.findViewById(R.id.compose_email_description_text);

                String Username= et_user_name.getText().toString();
                String subject= et_subject.getText().toString();
                String description = et_description.getText().toString();
                if(Username.toString().length()==0)
                {
                    Toast.makeText(getActivity().getApplicationContext(),"enter user name", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        check=read_from_file(Username);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    if(check==0){
                        Toast.makeText(getActivity().getApplicationContext(),"username not exit", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            add_to_user_sent(Username,subject,description.replace("\n", ""),"unread","sent");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            send_to_user(MainActivity.logned_in_user,Username,subject,description.replace("\n", ""),"unread","inbox");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(data_get_from_open_email!=null){
                            if(data_get_from_open_email.getString("which_folder")!=null){
                                if(data_get_from_open_email.getString("which_folder").matches("coming_from_draft")){
                                    Bundle data_to_send_back_draft_frag= new Bundle();
                                    data_to_send_back_draft_frag.putInt("index",data_get_from_open_email.getInt("index"));

                                    fragmentManager.setFragmentResult("requestKey", data_to_send_back_draft_frag);
                                }
                            }
                        }


                        fragmentManager.popBackStackImmediate();

                    }
                }


            }
        });
        if(data_get_from_open_email!=null){
            if(data_get_from_open_email.getString("which_folder")!=null){
                btn_draft.setVisibility(View.INVISIBLE);
                btn_savedraft.setVisibility(View.VISIBLE);
            }
        }
        btn_savedraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_user_name= view.findViewById(R.id.compose_email_username);
                EditText et_subject = view.findViewById(R.id.compose_email_subject);
                EditText et_description = view.findViewById(R.id.compose_email_description_text);

                String Username= et_user_name.getText().toString();
                String subject= et_subject.getText().toString();
                String description = et_description.getText().toString();
                if(Username.toString().length()==0)
                {
                    Toast.makeText(getActivity().getApplicationContext(),"enter user name", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        check=read_from_file(Username);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    if(check==0){
                        Toast.makeText(getActivity().getApplicationContext(),"username not exit", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            add_to_user_sent(Username,subject,description.replace("\n", ""),"unread","sent");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            send_to_user(MainActivity.logned_in_user,Username,subject,description.replace("\n", ""),"unread","inbox");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(data_get_from_open_email!=null){
                            if(data_get_from_open_email.getString("which_folder")!=null){
                                if(data_get_from_open_email.getString("which_folder").matches("coming_from_draft")){
                                    Bundle data_to_send_back_draft_frag= new Bundle();
                                    Bundle data_to_send_back_save_draft_frag= new Bundle();
                                    data_to_send_back_save_draft_frag.putString("name",Username);
                                    data_to_send_back_save_draft_frag.putString("sub",subject);
                                    data_to_send_back_save_draft_frag.putString("des",description);
                                    data_to_send_back_save_draft_frag.putInt("position",data_get_from_open_email.getInt("index"));

                                    fragmentManager.setFragmentResult("save draft", data_to_send_back_save_draft_frag);
                                }
                            }
                        }


                        fragmentManager.popBackStackImmediate();

                    }
                }




                //fragmentManager.popBackStackImmediate();
            }
        });




        btn_draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_user_name= view.findViewById(R.id.compose_email_username);
                EditText et_subject = view.findViewById(R.id.compose_email_subject);
                EditText et_description = view.findViewById(R.id.compose_email_description_text);

                String Username= et_user_name.getText().toString();
                String subject= et_subject.getText().toString();
                String description = et_description.getText().toString();
                if(Username.toString().length()==0)
                {
                    Toast.makeText(getActivity().getApplicationContext(),"enter user name", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        check=read_from_file(Username);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(check==0){
                        Toast.makeText(getActivity().getApplicationContext(),"username not exit", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            add_to_user_sent(Username, subject, description, "unread", "draft");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fragmentManager.popBackStackImmediate();
                    }

                }


            }
        });
        ImageView arrow_back = view.findViewById(R.id.compose_email_image_view);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orientation = getResources().getConfiguration().orientation;
                if(orientation== Configuration.ORIENTATION_PORTRAIT){
                    fragmentManager.popBackStack();
                }
                else{
                    for(int i = 0; i<fragmentManager.getBackStackEntryCount(); i++) {
                        //Log.d("tag",i+" "+fragmentManager.getBackStackEntryAt(i).getName());

                        if (fragmentManager.getBackStackEntryAt(i).getName().matches("compose email")) {
                            fragmentManager.popBackStackImmediate();
                            //fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.right_layout)).commit();
                            //Log.d("tag",i+" "+fragmentManager.getBackStackEntryAt(i).getName()+" compose email back");
                        }
                    }
                    //fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.right_layout)).commit();
                    //fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.right_layout)).commit();
                    //fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.right_layout)).commit();
                    //fragmentManager.popBackStack();
                    //fragmentManager.popBackStack();
                    //fragmentManager_rigth_side.popBackStack();
                    //fragmentManager_rigth_side.popBackStack();
                }

            }
        });



        return view;
    }

    void send_to_user(String actual_sender,String Username,String subject,String description,String status,String folder) throws IOException {
        File directory = getActivity().getFilesDir();
        File file = new File(directory,Username+"-mb.txt");
        FileWriter fw = new FileWriter(file, true);

        BufferedWriter write_file = new BufferedWriter(fw);
        write_file.write(actual_sender);
        write_file.newLine();
        write_file.write(subject);
        write_file.newLine();
        write_file.write(description);
        write_file.newLine();
        write_file.write(status);
        write_file.newLine();
        write_file.write(folder);
        write_file.newLine();
        write_file.close();
    }

    void add_to_user_sent(String Username,String subject,String description,String status,String folder) throws IOException {
        File directory = getActivity().getFilesDir();
        File file = new File(directory,MainActivity.logned_in_user+"-mb.txt");
        FileWriter fw = new FileWriter(file, true);

        BufferedWriter write_file = new BufferedWriter(fw);
        write_file.write(Username);
        write_file.newLine();
        write_file.write(subject);
        write_file.newLine();
        write_file.write(description);
        write_file.newLine();
        write_file.write(status);
        write_file.newLine();
        write_file.write(folder);
        write_file.newLine();
        write_file.close();
    }





    int read_from_file(String name) throws FileNotFoundException {
        File directory = getActivity().getFilesDir();
        File file = new File(directory,"user-db.txt");

        Scanner scanner = new Scanner(file);

        int count=0;
        String found_name="";

        while(scanner.hasNextLine()){
            while (true){
                if (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    if (count==0){
                        if (line.matches(name)){
                            found_name=line;
                            count+=1;
                            break;
                        }
                    }
                    if (count==1){


                            count+=1;
                            break;

                    }
                    if (count==2){


                        count=0;
                        break;
                    }

                }
                else {
                    break;
                }
            }
        }

        if ((found_name.matches(name))){
            if (name.length()==0){
                return 0;
            }
            else{
                return 1;
            }
        }
        else{

            return 0;
        }


    }
}