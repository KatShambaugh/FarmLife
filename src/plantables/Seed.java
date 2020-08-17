package plantables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a seed object.
 *
 * @author katherineshambaugh
 * @version 1.0
 */
public class Seed extends Plantable {
    /**
     * The imageView of a seed.
     */
    private final ImageView imgView;

    /**
     * Constructs a seed object.
     *
     * @param type the type of seed to construct
     */
    public Seed(final String type) {
        super(type, 30, 1, 0, 0);
        if (type.equals("Carrot")) {
            this.setBuyPrice(15);
        } else if (type.equals("Watermelon")) {
            this.setBuyPrice(30);
        } else {
            this.setBuyPrice(25);
        }
        String filename = "images/" + type + " seed.png";
        Image img = new Image(filename);
        imgView = new ImageView(img);
    }

    /**
     * Returns the image of the seed.
     *
     * @return the seed ImageView
     */
    @Override
    public ImageView getImgView() {
        return imgView;
    }

    /**
     * Returns the type of seed.
     *
     * @return a String containing type
     */
    @Override
    public String getType() {
        return super.getType() + " seed";
    }


    /**
     * Returns the current percentage of growth;
     * identifies state of plant growth.
     *
     * @return the percentage of plant growth
     */
    @Override
    public float state() {
        return 0;
    }
}
