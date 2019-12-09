package game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class Sound {
    private Media media;
    private MediaPlayer player;

    public Sound(String path) {
        String link = Paths.get(path).toUri().toString();
        media = new Media(link);
        player = new MediaPlayer(media);
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void play() {
        player.play();
    }

    public void stop() {
        player.stop();
    }

    public void setVolume(int volume) {
        final double MAX = 100;
        double targetVolume = volume / MAX;
        player.setVolume(targetVolume);
    }

    public void repeat() {
        player.setCycleCount(Integer.MAX_VALUE);
    }

} 