package game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class Sound {
    private Media media;
    private MediaPlayer player;
    private String path;

    public Sound(String path) {
        this.path = Paths.get(path).toUri().toString();
    }

    public void play() {
        media = new Media(path);
        player = new MediaPlayer(media);
        player.setAutoPlay(true);
    }
    public void stop(){
        player.stop();
    }
    public void repeat(){
        player.setCycleCount(Integer.MAX_VALUE);
    }
} 