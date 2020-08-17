package plantables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a watermelon object.
 *
 * @author jpb6
 * @version 1.0
 */
public class Watermelon extends Plantable {
    /**
     * The number of days a watermelon takes
     * to grow to a harvest-able state.
     */
    private static final int GROW_DAYS = 10;

    /**
     * The imageView of a watermelon.
     */
    private final ImageView imgView = new ImageView(new
            Image("images/Watermelon.png"));

    /**
     * Constructs a watermelon object.
     */
    public Watermelon() {
        super("Watermelon", 300, 200, GROW_DAYS, .5);
    }

    /**
     * Returns the image of the Watermelon.
     *
     * @return the watermelon ImageView
     */
    @Override
    public ImageView getImgView() {
        return imgView;
    }
}
