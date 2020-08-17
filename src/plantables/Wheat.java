package plantables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a wheat object.
 *
 * @author jpb6
 * @version 1.0
 */
public class Wheat extends Plantable {
    /**
     * The number of days wheat takes
     * to grow to a harvest-able state.
     */
    private static final int GROW_DAYS = 8;

    /**
     * The imageView of wheat.
     */
    private final ImageView imgView = new ImageView(new
            Image("images/Wheat.png"));

    /**
     * Constructs a wheat object.
     */
    public Wheat() {
        super("Wheat", 200, 100, GROW_DAYS, .5);
    }

    /**
     * Returns the image of the Wheat.
     *
     * @return the wheat ImageView
     */
    @Override
    public ImageView getImgView() {
        return imgView;
    }
}
