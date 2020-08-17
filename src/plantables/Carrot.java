package plantables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a carrot.
 *
 * @author katherineshambaugh
 * @version 1.0
 */
public class Carrot extends Plantable {
    /**
     * The number of days a carrot takes
     * to grow to a harvest-able state.
     */
    private static final int GROW_DAYS = 4;

    /**
     * The imageView of a carrot.
     */
    private final ImageView imgView = new ImageView(new
            Image("images/Carrot.png"));

    /**
     * Constructs a carrot object.
     */
    public Carrot() {
        super("Carrot", 250, 150, GROW_DAYS, .5);
    }

    /**
     * Returns the image of the Carrot.
     *
     * @return the carrot ImageView
     */
    @Override
    public ImageView getImgView() {
        return imgView;
    }
}
