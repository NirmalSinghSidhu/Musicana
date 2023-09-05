package com.nirmalsingh.musicana;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView titleOfSong,currentTime,totalTime;
    ImageView prevBtn,nextBtn,pausePlayBtn,playIcon;
    SeekBar seekBar;
      ArrayList<AudioModel> songList;
      AudioModel currentSong;

      MediaPlayer mediaPlayer=MediaPlayerSongs.getInstance();
       int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleOfSong=findViewById(R.id.song_title);
        currentTime=findViewById(R.id.current_time);
        totalTime=findViewById(R.id.total_time);
        prevBtn=findViewById(R.id.previous);
        nextBtn=findViewById(R.id.next);
        pausePlayBtn=findViewById(R.id.play_pause);
        playIcon=findViewById(R.id.playingIcon);
        seekBar=findViewById(R.id.seek_bar);

        titleOfSong.setSelected(true);
        songList =(ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");
        setResourcesWithMusic();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void run() {
                if(mediaPlayer!=null)
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTime.setText(convertToMilliSeconds(mediaPlayer.getCurrentPosition()+""));

                    if(mediaPlayer.isPlaying()){
                        pausePlayBtn.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                        playIcon.setRotation(x++);
                    }else{
                        pausePlayBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                        playIcon.setRotation(x);
                    }
                   new Handler().postDelayed(this,100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if(mediaPlayer!=null && b){
                        mediaPlayer.seekTo(i);
                    }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    void setResourcesWithMusic(){
        currentSong=songList.get(MediaPlayerSongs.currentIndex);
        titleOfSong.setText(currentSong.getTitle());

        totalTime.setText(convertToMilliSeconds(currentSong.getDuration()));

        pausePlayBtn.setOnClickListener(v-> pausePlay());
        nextBtn.setOnClickListener(v-> playNextSong());
        prevBtn.setOnClickListener(v-> playPreviousSong());
        playMusic();

    }

    private void playMusic(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void playNextSong(){
        if(MediaPlayerSongs.currentIndex==songList.size()-1) return;
        MediaPlayerSongs.currentIndex+=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }
    private void playPreviousSong(){
        if(MediaPlayerSongs.currentIndex==0) return;
        MediaPlayerSongs.currentIndex-=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }
    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }

    @SuppressLint("DefaultLocale")
    public static String convertToMilliSeconds(String duration){
        Long milli=Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milli)%TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milli)%TimeUnit.MINUTES.toSeconds(1));
    }
}