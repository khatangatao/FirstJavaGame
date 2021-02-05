import java.awt.*;

public class Sprite {
    private Image image;

    public Sprite(Image image) {
        this.image = image;
    }

//    получаем ширину картинки
    public int getWidth() {
        return image.getWidth(null);
    }

//    получаем высоту картинки
    public int getHeight() {
        return image.getHeight(null);
    }

//    рисуем картинку
    public void draw(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

}
