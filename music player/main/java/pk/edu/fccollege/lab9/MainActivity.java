package pk.edu.fccollege.lab9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    class Sound{
        public String title;
        public int resId;
        Sound(String title, int resId){
            this.title = title;
            this.resId = resId;
        }
        @NonNull
        @Override
        public String toString() {
            return title;
        }
    }

    ArrayList<Sound> sounds;
    ArrayAdapter<Sound> adapter;
    String song_name;

    MediaPlayer mediaPlayer;

    public static final String MYPREFS = "com.example.myapp.song_stuff";
    public static final String mode_song ="shuffle_state";
    public static final String  last_played_song="played song";
    public  static final String KY_state_play_pause ="status play";

    int currentSong = 0;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        int state_of_music =sharedPreferences.getInt(KY_state_play_pause,-1);
        if (state_of_music==1){
            if(mediaPlayer != null){
                mediaPlayer.start();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        //int state_of_music =sharedPreferences.getInt(KY_state_play_pause,-1);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (mediaPlayer.isPlaying()){
            editor.putInt(KY_state_play_pause,1);
            editor.commit();
            mediaPlayer.pause();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        String mode =sharedPreferences.getString(mode_song,"normal");
        String name_of_song =sharedPreferences.getString(last_played_song," nothing");
        int state_of_music =sharedPreferences.getInt(KY_state_play_pause,-1);



        sounds = new ArrayList<>();
        sounds.add(new Sound("Gentle Rain Ambient Sounds", R.raw.gentle_night_rain));
        sounds.add(new Sound("Colorful Birds of the Rain Forest : Costa Rica", R.raw.colorful_birds_of_rainforest));
        sounds.add(new Sound("Rainforest Sound Effects", R.raw.rainforest_sound_effects));
        sounds.add(new Sound("Rain and Thunderstorm", R.raw.rain_and_thunderstorm));
        sounds.add(new Sound("Wind Ambient Sounds", R.raw.wind_sounds));
        sounds.add(new Sound("NASA: Sounds of the planets (some are scary)", R.raw.nasa_planet_sounds));
        sounds.add(new Sound("Thunder Clap (Warning: Loud)", R.raw.thunder_clap));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sounds);

        ListView soundsList = findViewById(R.id.lv_songs);
        soundsList.setAdapter(adapter);


        if (state_of_music==1){

            Log.d("tag","plays "+name_of_song);
            for(int i =0 ; i< sounds.size();i++){
                Sound song_in_array =sounds.get(i);
                if (song_in_array.toString().compareTo(name_of_song)==0){
                    song_name=song_in_array.toString();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), song_in_array.resId);
                    mediaPlayer.start();
                    next_song(mediaPlayer);
                    Log.d("tag","found musixc");
                    Toast.makeText(getBaseContext(), mode+" Mode selected and Playing last played song " + name_of_song, Toast.LENGTH_LONG).show();
                }
            }
        }
        else{
            Toast.makeText(getBaseContext(), mode+" Selected previously", Toast.LENGTH_LONG).show();
            //Log.d("tag",mode+" Selected previously");
        }
/*
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override

            public void onCompletion(MediaPlayer mp) {

                for (int i = 0; i < sounds.size(); i++) {
                    Sound song_in_array = sounds.get(i);
                    String song_name_in_array = song_in_array.toString();
                    if (song_name_in_array.compareTo(song_name) == 0) {
                        if (i == sounds.size() - 1) {
                            mediaPlayer.release();
                            Sound sound = sounds.get(0);
                            song_name = sound.toString();

                            mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                            mediaPlayer.start();
                            Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                            break;
                        } else if (i < sounds.size()) {
                            Sound sound = sounds.get(i + 1);
                            song_name = sound.toString();

                            mediaPlayer.release();
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                            mediaPlayer.start();
                            Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }
            }

            });
*/

        soundsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(mediaPlayer != null)
                    mediaPlayer.release();

                Sound sound = sounds.get(position);
                editor.putInt(KY_state_play_pause,1);
                //editor.putString(last_played_song,song_name);

                song_name=sound.toString();
                editor.putString(last_played_song,song_name);
                editor.commit();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                mediaPlayer.start();
                Toast.makeText(getBaseContext(),"Playing "+sound.toString(),Toast.LENGTH_LONG).show();
                next_song(mediaPlayer);
                /*
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        });
                    }
                }); */
            }
        });

        ImageView play = findViewById(R.id.iv_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
                String name_of_song =sharedPreferences.getString(last_played_song,"");
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Log.d("TAG", "Playing...");
                if(mediaPlayer != null){
                    mediaPlayer.start();
                    next_song(mediaPlayer);
                    //mediaPlayer.release();
                }
                else{
                    Sound sound = sounds.get(0);
                    song_name=sound.toString();
                    //editor.putString(name_of_song, song_name);
                    editor.putString(last_played_song,song_name);
                    editor.commit();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                    mediaPlayer.start();
                    next_song(mediaPlayer);
                    Toast.makeText(getBaseContext(),"Playing "+sound.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
        ImageView pause = findViewById(R.id.iv_pause);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KY_state_play_pause,-1);
                editor.putString(last_played_song,"");
                editor.commit();
                mediaPlayer.pause();
            }
        });

        ImageView next = findViewById(R.id.iv_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int array_size= (sounds.size());
                //String song_name_currently_playing = sounds.toString();
                SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
                String mode =sharedPreferences.getString(mode_song,"normal");
                String name_of_song =sharedPreferences.getString(last_played_song,"");
                SharedPreferences.Editor editor = sharedPreferences.edit();


                    if (mediaPlayer != null){
                        //Log.d("TAG", "next button clicked");
                        for(int i =0 ; i< sounds.size();i++){
                            Sound song_in_array =sounds.get(i);
                            String song_name_in_array= song_in_array.toString();


                            if (song_name_in_array.compareTo(song_name)==0){
                                editor.putInt(KY_state_play_pause,1);
                                editor.commit();
                                if (i==sounds.size()-1){
                                    mediaPlayer.release();
                                    Sound sound = sounds.get(0);
                                    song_name=sound.toString();
                                    editor.putString(last_played_song,song_name);
                                    editor.commit();
                                    mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                                    mediaPlayer.start();
                                    next_song(mediaPlayer);
                                    Toast.makeText(getBaseContext(),"Playing "+sound.toString(),Toast.LENGTH_LONG).show();
                                    break;
                                }
                                else if(i<sounds.size()){
                                    Sound sound = sounds.get(i+1);
                                    song_name=sound.toString();
                                    editor.putString(last_played_song,song_name);
                                    editor.commit();
                                    mediaPlayer.release();
                                    mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                                    mediaPlayer.start();
                                    next_song(mediaPlayer);
                                    Toast.makeText(getBaseContext(),"Playing "+sound.toString(),Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                        //



                        }
                }

            }
        });
        ImageView shuffle = findViewById(R.id.iv_next2);

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
                String name =sharedPreferences.getString(mode_song,"normal");
                int random = (int)(Math.random() * ((sounds.size()-1)+ 1) + 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (name.matches("normal")) {

                    editor.putString(mode_song, "shuffle");
                    Toast.makeText(getBaseContext(),"shuffle mode",Toast.LENGTH_LONG).show();
                    for (int i = 0; i < sounds.size(); i++) {
                        Sound song_in_array = sounds.get(i);
                        String song_name_in_array = song_in_array.toString();
                        if (song_name_in_array.compareTo(song_name) == 0) {
                            if (i == sounds.size() - 1) {
                                mediaPlayer.release();
                                Sound sound = sounds.get(random);
                                song_name = sound.toString();

                                mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                                mediaPlayer.start();
                                next_song(mediaPlayer);
                                Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                                break;
                            } else if (i < sounds.size()) {
                                Sound sound = sounds.get(random);
                                song_name = sound.toString();

                                mediaPlayer.release();
                                mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                                mediaPlayer.start();
                                next_song(mediaPlayer);
                                Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                    }
                    editor.commit();
                }
                else if(name.matches("shuffle")) {

                    editor.putString(mode_song, "normal");


                    Toast.makeText(getBaseContext(),"normal mode",Toast.LENGTH_LONG).show();
                    editor.commit();
                    for (int i = 0; i < sounds.size(); i++) {
                        Sound song_in_array = sounds.get(i);
                        String song_name_in_array = song_in_array.toString();
                        if (song_name_in_array.compareTo(song_name) == 0) {
                            if (i == sounds.size() - 1) {
                                mediaPlayer.release();
                                Sound sound = sounds.get(random);
                                song_name = sound.toString();

                                mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                                mediaPlayer.start();
                                Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                                break;
                            } else if (i < sounds.size()) {
                                Sound sound = sounds.get(random);
                                song_name = sound.toString();

                                mediaPlayer.release();
                                mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                                mediaPlayer.start();
                                Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                    }
                }

            }
        });

        ImageView prev = findViewById(R.id.iv_prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
                String mode =sharedPreferences.getString(mode_song,"normal");
                String name_of_song =sharedPreferences.getString(last_played_song,"");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (mediaPlayer != null){
                    for(int i =0 ; i< sounds.size();i++) {
                        Sound song_in_array = sounds.get(i);
                        String song_name_in_array = song_in_array.toString();

                            editor.putInt(KY_state_play_pause,1);
                            editor.commit();
                            if (song_name_in_array.compareTo(song_name) == 0) {
                                if (i == sounds.size() - 1) {
                                    mediaPlayer.release();
                                    Sound sound = sounds.get(i - 1);
                                    song_name = sound.toString();
                                    editor.putString(last_played_song,song_name);
                                    editor.commit();
                                    mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                                    mediaPlayer.start();
                                    next_song(mediaPlayer);
                                    Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                                    break;
                                }
                                if (i < sounds.size()) {
                                    if (i == 0) {
                                        mediaPlayer.release();
                                        Sound sound = sounds.get(sounds.size() - 1);
                                        song_name = sound.toString();
                                        editor.putString(last_played_song,song_name);
                                        mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                                        mediaPlayer.start();
                                        next_song(mediaPlayer);
                                        Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                                        editor.commit();
                                        break;
                                    }
                                    else{
                                        mediaPlayer.release();
                                        Sound sound = sounds.get(i - 1);
                                        song_name = sound.toString();
                                        editor.putString(last_played_song,song_name);
                                        mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                                        mediaPlayer.start();
                                        next_song(mediaPlayer);
                                        Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                                        editor.commit();
                                        break;
                                    }
                                }
                            }
                        //

                    }
                }
            }
        });





    }

    public void next_song(MediaPlayer mp){
       mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           @Override
           public void onCompletion(MediaPlayer mp) {

               for (int i = 0; i < sounds.size(); i++) {
                   Sound song_in_array = sounds.get(i);
                   String song_name_in_array = song_in_array.toString();
                   if (song_name_in_array.compareTo(song_name) == 0) {
                       if (i == sounds.size() - 1) {
                           mediaPlayer.release();
                           Sound sound = sounds.get(0);
                           song_name = sound.toString();

                           mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                           mediaPlayer.start();
                           Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                           break;
                       } else if (i < sounds.size()) {
                           Sound sound = sounds.get(i + 1);
                           song_name = sound.toString();

                           mediaPlayer.release();
                           mediaPlayer = MediaPlayer.create(getApplicationContext(), sound.resId);
                           mediaPlayer.start();
                           Toast.makeText(getBaseContext(), "Playing " + sound.toString(), Toast.LENGTH_LONG).show();
                           break;
                       }
                   }
               }

           }
       });

    }


}