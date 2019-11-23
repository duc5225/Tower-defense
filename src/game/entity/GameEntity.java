package game.entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameEntity {
    protected Image image;
    protected ImageView imageView;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void init(){
        this.getImageView().setTranslateX(-100);
        this.getImageView().setTranslateY(-100);
    }
}
