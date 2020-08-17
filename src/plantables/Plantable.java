package plantables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents an abstract
 * plantable object.
 *
 * @author katherineshambaugh
 * @version 1.1
 */
public abstract class Plantable {
    /**
     * The base sell price of a Plant.
     */
    private final int salePrice;

    /**
     * The base buy price of a Plant.
     */
    private int buyPrice;

    /**
     * How many days the plant has been growing.
     */
    private int growthTime;

    /**
     * Type of the plant.
     */
    private final String type;

    /**
     * The number of days a plant takes
     * to grow to a harvest-able state.
     */
    private final int growDays;

    /**
     * (-INF, 1) = LOW.
     * [1, 2) = PERFECT.
     * [2, INF) = HIGH and dead.
     */
    private double waterLevel;

    /**
     * Constructor for a class that has custom imgView.
     *
     * @param type      type of plant
     * @param buyPrice  base buy price of plant
     * @param salePrice base sell price of plant
     * @param growDays  number of days to grow until mature plant
     * @param waterLevel the initial water level of plant
     */
    public Plantable(final String type, final int buyPrice,
                     final int salePrice, final int growDays,
                     final double waterLevel) {
        this.type = type;
        this.buyPrice = buyPrice;
        this.salePrice = salePrice;
        this.growDays = growDays;
        this.waterLevel = waterLevel;
    }

    /**
     * Returns the growth cycle length.
     *
     * @return number of days to grow fully
     */
    public int getGrowDays() {
        return growDays;
    }

    /**
     * Sets the growth time of the plant.
     *
     * @param growthTime the time it takes to grow
     */
    public void setGrowthTime(final int growthTime) {
        this.growthTime = growthTime;
    }

    /**
     * Increases the growthTime by one.
     */
    public void grow() {
        growthTime++;
    }

    /**
     * Returns a String containing
     * the type of plant.
     *
     * @return type of plant
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the base sale price.
     *
     * @return base sale price
     */
    public int getSalePrice() {
        return salePrice;
    }

    /**
     * Returns the base buy price.
     *
     * @return base buy price
     */
    public int getBuyPrice() {
        return buyPrice;
    }

    /**
     * Returns the base buy price.
     *
     * @param price new buy price
     */
    public void setBuyPrice(final int price) {
        this.buyPrice = price;
    }

    /**
     * Returns the current percentage of growth;
     * identifies state of plant growth.
     *
     * @return the percentage of plant growth
     */
    public float state() {
        return (float) growthTime / growDays;
    }

    /**
     * Returns the water level.
     *
     * @return waterLevel
     */
    public double getWaterLevel() {
        return waterLevel;
    }

    /**
     * Increases water level.
     *
     * @param x amount to increase water level by
     */
    public void addWater(final double x) {
        waterLevel += x;
    }

    /**
     * Returns the image of the Plant.
     *
     * @return the plant ImageView
     */
    public abstract ImageView getImgView();

    /**
     * Returns the harvest image of
     * the Plant.
     *
     * @return the plant Image
     */
    public Image getHarvestImage() {
        return new Image("images/" + this.type + "HarvestPlot.png");
    }
}
