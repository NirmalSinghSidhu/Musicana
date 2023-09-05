package com.nirmalsingh.musicana;

import android.media.MediaPlayer;

public class MediaPlayerSongs {
     static MediaPlayer mediaPlayer;
     public static MediaPlayer getInstance(){
         if(mediaPlayer==null){
             mediaPlayer=new MediaPlayer();
         }
         return mediaPlayer;
     }

     public static int currentIndex=-1;
}
