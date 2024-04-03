package main;


import myenum.E_Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL[] soundUrl = new URL[30];


    public Sound() {
        // Initialisation des URLs de sons
        initSoundUrls();
    }

    private void initSoundUrls() {
        E_Sound[] sounds = E_Sound.values();
        for (int i = 0; i < sounds.length; i++) {
            soundUrl[i] = getClass().getResource("/Sound/" + sounds[i].toString() + ".wav");
        }
    }


    public void setFile(E_Sound sound) {
        try {
            // Récupère l'URL correspondant à l'énumération fournie
            URL soundURL = soundUrl[sound.ordinal()];
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
