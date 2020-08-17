import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import plantables.Plantable;

/**
 * Represents a plot of land.
 *
 * @author katherineshambaugh
 * @version 1.0
 */
public class Plot {
    /**
     * The image of an empty plot.
     */
    private static final Image PLOT_IMG = new Image("/images/EmptyPlot.png");

    /**
     * The image view of an empty plot.
     */
    private ImageView pView;

    /**
     * The image of a seeded plot.
     */
    private static final Image SEEDED_IMG = new Image("/images/SeededPlot.png");

    /**
     * The image of a plot in the
     * first state of growth.
     */
    private static final Image STATE_ONE_IMG = new
            Image("/images/FirstGrowPlot.png");

    /**
     * The image of a plot in the
     * second state of growth.
     */
    private static final Image STATE_TWO_IMG = new
            Image("/images/SecondGrowPlot.png");

    /**
     * The imageView of a dead plot.
     */
    private static final Image DEAD_IMG = new Image("/images/DeadPlot.png");

    /**
     * The plant in the plot.
     */
    private Plantable plant;

    /**
     * What state the plot is in.
     * 1 = first stage, no seed
     * 2 = second stage, seeded
     * 3 = third stage, immature
     * 4 = fourth stage, growing
     * 5 = fifth stage, dead
     * 6 = sixth state, ready to harvest
     */
    private int state;

    /**
     * Constructs a plot.
     */
    public Plot() {
        pView = new ImageView(PLOT_IMG);
        state = 1;
        plant = null;
    }

    /**
     * Adds a Plant object to a plot.
     *
     * @param p the Plant to add
     */
    public void setPlant(final Plantable p) {
        plant = p;
    }

    /**
     * Gets the Plant in a plot.
     *
     * @return the Plant
     */
    public Plantable getPlant() {
        return plant;
    }

    /**
     * Resets the state of a plot.
     */
    public void resetState() {
        state = 1;
        pView = new ImageView(PLOT_IMG);
        plant = null;
    }

    /**
     * Returns the current plot image view.
     *
     * @return ImageView
     */
    public ImageView getPView() {
        return pView;
    }

    /**
     * Seeds the plot.
     */
    public void seed() {
        pView = new ImageView(SEEDED_IMG);
        state = 2;
    }

    /**
     * Shows immature plant state.
     */
    public void immature() {
        pView = new ImageView(STATE_ONE_IMG);
        state = 3;
    }

    /**
     * Shows growing plant state.
     */
    public void growing() {
        pView = new ImageView(STATE_TWO_IMG);
        state = 4;
    }

    /**
     * Shows ready to harvest state.
     */
    public void readyToHarvest() {
        pView = new ImageView(plant.getHarvestImage());
        state = 6;
    }

    /**
     * Shows dead plant state.
     */
    public void dead() {
        pView = new ImageView(DEAD_IMG);
        state = 5;
        plant = null;
    }

    /**
     * Returns state of plot.
     *
     * @return state
     */
    public int getState() {
        return state;
    }
}
