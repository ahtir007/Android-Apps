package pk.edu.fccollege.fccmailclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class Folder_selection_fragment extends AppCompatActivity {
    FragmentManager manager;




    //FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_selection_fragment);

        int orientation = getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            androidx.fragment.app.FragmentManager fragmentManager= getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            frag_folder_Selection one = new frag_folder_Selection();
            fragmentTransaction.replace(R.id.alwayscganging,one);
            fragmentTransaction.commit();
        }
        else{
            androidx.fragment.app.FragmentManager fragmentManager= getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            frag_folder_Selection one = new frag_folder_Selection();
            fragmentTransaction.replace(R.id.left_layout,one);
            fragmentTransaction.commit();
        }






    }

}