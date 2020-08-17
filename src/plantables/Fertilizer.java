package plantables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a fertilizer object.
 *
 * @author katherineshambaugh
 * @version 1.0
 */
public class Fertilizer extends Plantable {
    /**
     * The imageView of fertilizer.
     */
    private final ImageView imgView;

    /**
     * Constructs a seed object.
     *
     * @param type the type of seed to construct
     */
    public Fertilizer(final String type) {
        super(type, 30, 1, 0, 0);
        Image img = new Image("/images/Fertilizer.png");
        imgView = new ImageView(img);
    }

    /**
     * Returns the imageView of the
     * fertilizer.
     *
     * @return the fertilizer imageView
     */
    @Override
    public ImageView getImgView() {
        return imgView;
    }

    /**
     * Fertilizes the plant.
     * This decreases the final growth time
     * of the plant.
     *
     * @param plant the plant to fertilize
     */
    public static void fertilize(final Plantable plant) {
        plant.grow();
    }
}
