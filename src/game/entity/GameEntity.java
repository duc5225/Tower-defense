package game.entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameEntity {
    protected Image image;
    protected ImageView imageView;

    //
    protected void initImgViewPos() {
        this.imageView.setTranslateX(-100);
        this.imageView.setTranslateY(-100);
    }
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
}
