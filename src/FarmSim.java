import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import plantables.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.geometry.Pos.*;

/**
 * This class represents the farming sim application.
 *
 * @author Katherine Shambaugh
 * @version 1.0
 */
public class FarmSim extends Application {
    /**
     * The opening image.
     */
    private static final Image OPEN_PIC = new Image("images/OpenPic.png");

    /**
     * The opening imageView to display.
     */
    private static final ImageView WELCOME_VIEW = new ImageView(OPEN_PIC);

    /**
     * The label for current money held.
     */
    private static final Label MONEY_LABEL = new Label();

    /**
     * Button to go to config screen.
     */
    private static final Button START = new Button("Start");

    /**
     * Button to go to game screen.
     */
    private static final Button GO = new Button("Go!");

    /**
     * Text field to enter player's name.
     */
    private static final TextField NAME = new TextField();

    /**
     * Label to show money.
     */
    private static final Label MONEY_TITLE = new Label("Money:");

    /**
     * Button to go to market.
     */
    private static final Button MARKET = new Button("Market");

    /**
     * Button to go to the next day.
     */
    private static final Button NEXT_DAY = new Button("Next Day");

    /**
     * Button to go back to the farm.
     */
    private static final Button BACK_TO_FARM = new Button("Back to Farm");

    /**
     * Button to buy from market.
     */
    private static final Button BUY_BTN = new Button("Buy");

    /**
     * Button to open player inventory.
     */
    private static final Button INVENTORY = new Button("Inventory");

    /**
     * An array to hold starting plots.
     */
    private static final Plot[] PLOTS = new Plot[10];

    /**
     * The pane to hold game controls.
     */
    private static final BorderPane GAME_PANE = new BorderPane();

    /**
     * Vertical box to hold game content.
     */
    private static final VBox CONTENT = new VBox();

    /**
     * Horizontal box for first row of plots.
     */
    private static final HBox ROW_ONE = new HBox();

    /**
     * Horizontal box for second row of plots.
     */
    private static final HBox ROW_TWO = new HBox();

    /**
     * The scene to hold the game pane.
     */
    private static final Scene GAME_SCENE = new Scene(GAME_PANE, 660, 505);

    /**
     * Keeps track of seed player selects.
     */
    private static final String[] SELECTED_SEED = new String[1];

    /**
     * Current player.
     */
    private static final Player PLAYER = new Player();

    /**
     * Market seller.
     */
    private static final Player SELLER = new Player();

    /**
     * Random object to create variance
     * in sales.
     */
    private static final Random RAND = new Random();

    /**
     * Inventory label for
     * inventory window.
     */
    private static final Label INVENT_TEXT = new Label("Inventory");

    /**
     * The vertical box for inventory.
     */
    private static final ArrayList<Plantable> WARES = new ArrayList<>();

    /**
     * Inventory listView.
     */
    private static ListView<Plantable> inventView;

    /**
     * Display of player money.
     */
    private static Integer money;

    /**
     * Current date in game.
     */
    private static int day = 1;

    /**
     * Stage to display game.
     */
    private static Stage stageRef;

    /**
     * Keeps track of first time plots are created.
     */
    private static boolean first = true;

    /**
     * The pane to hold market controls.
     */
    private static BorderPane marketPane;

    /**
     * The listView holding the seller's wares.
     */
    private static ListView<Plantable> buyList;

    /**
     * Random multiplier to get
     * sale and buy prices in market.
     */
    private static float randMul;

    /**
     * The boolean if to seed.
     */
    private static boolean seedOn;

    /**
     * The vertical box for inventory.
     */
    private static VBox inventVBox;

    /**
     * The boolean deciding if to seed.
     */
    private static boolean remade;

    /**
     * String holding the current weather.
     */
    private static String currentWeather;

    /**
     * This launches the application.
     *
     * @param args a String array of arguments
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }

    /**
     * This method overrides the application start method.
     *
     * @param primaryStage the stage for the application
     */
    @Override
    public void start(final Stage primaryStage) {
        stageRef = primaryStage;
        BorderPane pane = new BorderPane();
        Scene openScene = new Scene(pane, 660, 500);
        openScene.getStylesheets().add("styles/openStyle.css");
        makeOpenScene(pane);
        goToConfig();
        goToGame();
        primaryStage.setTitle("Farm Life");
        load(primaryStage, openScene);
    }

    /**
     * Loads the scene onto the stage
     * and shows the stage.
     *
     * @param primaryStage the stage for the application
     * @param scene        the current scene
     */
    public void load(final Stage primaryStage, final Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the opening screen.
     *
     * @param pane the pane to add controls to
     */
    private static void makeOpenScene(final BorderPane pane) {
        Label title = new Label("Welcome to your farm!");
        pane.setId("open-pane");
        BorderPane.setAlignment(title, TOP_CENTER);
        BorderPane.setAlignment(START, BOTTOM_CENTER);
        BorderPane.setMargin(START, new Insets(0, 0, 20, 0));
        BorderPane.setMargin(title, new Insets(10, 0, 0, 0));
        pane.setTop(title);
        pane.setCenter(WELCOME_VIEW);
        pane.setBottom(START);
    }

    /**
     * Handles button event to switch to
     * configuration screen.
     */
    public void goToConfig() {
        START.setOnAction(actionEvent -> {
            BorderPane pane = new BorderPane();
            Scene configScene = new Scene(pane, 660, 500);
            configScene.getStylesheets().add("styles/configStyle.css");
            makeConfigScene(pane);
            load(stageRef, configScene);
        });
    }

    /**
     * Creates the configuration screen.
     *
     * @param pane the pane to add controls to
     */
    private static void makeConfigScene(final BorderPane pane) {
        //Sets up middle content
        VBox content = new VBox();
        content.setAlignment(TOP_CENTER);
        HBox nameInput = new HBox();
        Label title = new Label("Choose your player!");
        BorderPane.setAlignment(title, TOP_CENTER);
        Label nameEnter = new Label("Name:");
        NAME.setPrefWidth(500);

        //Sets up difficulty buttons
        HBox diffButtons = new HBox();
        diffButtons.setAlignment(TOP_CENTER);
        Label diffTitle = new Label("Difficulty:");
        ToggleGroup diffGroup = new ToggleGroup();
        ToggleButton diffOne = new ToggleButton("Easy");
        diffOne.setToggleGroup(diffGroup);
        diffOne.setSelected(true);
        ToggleButton diffTwo = new ToggleButton("Medium");
        diffTwo.setToggleGroup(diffGroup);
        ToggleButton diffThree = new ToggleButton("Hard");
        diffThree.setToggleGroup(diffGroup);
        diffOne.setUserData(600);
        diffTwo.setUserData(400);
        diffThree.setUserData(200);

        //sets up starting money based on difficulty
        money = (Integer) diffGroup.getSelectedToggle().getUserData();
        MONEY_LABEL.setText(money.toString());
        diffGroup.selectedToggleProperty().addListener((observable, oldValue,
                                                        newValue) -> {
            if (diffGroup.getSelectedToggle() != null) {
                money = (Integer) diffGroup.getSelectedToggle().getUserData();
            } else {
                money = 600;
            }
            PLAYER.setMoney(money);
            MONEY_LABEL.setText(money.toString());
        });
        PLAYER.setMoney(money);

        //Sets up seed buttons
        HBox seedButtons = new HBox();
        seedButtons.setAlignment(TOP_CENTER);
        Label seedTitle = new Label("Seed:");
        ToggleGroup seedGroup = new ToggleGroup();
        ToggleButton seedOne = new ToggleButton("Carrot");
        seedOne.setToggleGroup(seedGroup);
        seedOne.setSelected(true);
        seedOne.setUserData("Carrot");
        ToggleButton seedTwo = new ToggleButton("Watermelon");
        seedTwo.setToggleGroup(seedGroup);
        seedTwo.setUserData("Watermelon");
        ToggleButton seedThree = new ToggleButton("Wheat");
        seedThree.setToggleGroup(seedGroup);
        seedThree.setUserData("Wheat");

        //records what seed was selected
        SELECTED_SEED[0] = (String) seedOne.getUserData();
        seedGroup.selectedToggleProperty().addListener(((observable, oldValue,
                                                         newValue) -> {
            if (seedGroup.getSelectedToggle() != null) {
                SELECTED_SEED[0] = (String)
                        seedGroup.getSelectedToggle().getUserData();
            }
        }));
        nameInput.getChildren().addAll(nameEnter, NAME);
        diffButtons.getChildren().addAll(diffTitle, diffOne, diffTwo,
                diffThree);
        seedButtons.getChildren().addAll(seedTitle, seedOne, seedTwo,
                seedThree);

        //Finalizes set up
        Image grow = new Image("images/Growing.png");
        ImageView growView = new ImageView(grow);
        content.getChildren().addAll(nameInput, diffButtons, seedButtons,
                growView);
        BorderPane.setAlignment(GO, BOTTOM_CENTER);
        BorderPane.setMargin(GO, new Insets(0, 0, 20, 0));
        pane.setTop(title);
        pane.setCenter(content);
        pane.setBottom(GO);
    }

    /**
     * Handles button event to switch to
     * game screen.
     */
    public void goToGame() {
        GO.setOnAction(actionEvent -> {
            //Only if a name is entered
            if ((NAME.getText() != null && !NAME.getText().isEmpty())) {
                PLAYER.setName(NAME.getText());
                GAME_SCENE.getStylesheets().add("styles/gameStyle.css");
                makeGameScene();
                seedOn = false;
                remade = false;
                randMul = RAND.nextFloat();
                load(stageRef, GAME_SCENE);
                addInitialSeed();
            } else {
                NAME.setPromptText("Name cannot be blank!");
                NAME.setOnMouseClicked(e -> NAME.setText(null));
            }
        });
    }

    /**
     * Adds the initial selection of seeds to inventory.
     */
    private static void addInitialSeed() {
        if (SELECTED_SEED[0].equals("Watermelon")) {
            PLAYER.getInventory().addSeed("Watermelon", 10);
        } else if (SELECTED_SEED[0].equals("Wheat")) {
            PLAYER.getInventory().addSeed("Wheat", 10);
        } else {
            PLAYER.getInventory().addSeed("Carrot", 10);
        }
    }

    /**
     * Creates the game screen.
     */
    private void makeGameScene() {
        CONTENT.getChildren().clear();
        ROW_ONE.getChildren().clear();
        ROW_TWO.getChildren().clear();

        //Sets up bottom money bar
        HBox moneyBar = new HBox();
        moneyBar.setId("money-bar");
        moneyBar.setAlignment(BOTTOM_LEFT);
        Region spacerFront = new Region();
        Region spacerMiddle = new Region();
        spacerFront.setPrefWidth(5);
        spacerMiddle.setPrefWidth(285);
        moneyBar.getChildren().addAll(spacerFront, INVENTORY,
                spacerMiddle, MONEY_TITLE, MONEY_LABEL);

        //Sets up top status bar
        HBox statusBar = new HBox();
        statusBar.setId("status-bar");
        Label date = new Label("Day: " + day);
        Label farmName = new Label(PLAYER.getName() + "'s Farm");
        Region spacerTopFront = new Region();
        Region spacerTopMiddle = new Region();
        spacerTopFront.setPrefWidth(15);
        spacerTopMiddle.setPrefWidth(100);
        statusBar.getChildren().addAll(date, spacerTopFront, NEXT_DAY,
                farmName, spacerTopMiddle, MARKET);

        //Sets up middle content
        CONTENT.setId("content");
        ROW_ONE.setId("plots");
        ROW_TWO.setId("plots");
        CONTENT.getChildren().addAll(ROW_ONE, ROW_TWO);

        //Sets up plots and weather
        harvest();
        if (!remade) {
            addWeather(statusBar);
        } else {
            statusBar.setStyle(currentWeather);
        }

        //Finishes set up
        first = false; //makes sure plots are not re-randomized
        goToInventory();
        goToMarket();
        goToNextDay();
        FarmSim.GAME_PANE.setTop(statusBar);
        FarmSim.GAME_PANE.setBottom(moneyBar);
        FarmSim.GAME_PANE.setCenter(CONTENT);
    }

    /**
     * Adds randomized weather that affects planted plants.
     *
     * @param statusBar HBox
    */
    private void addWeather(final HBox statusBar) {
        int rand = RAND.nextInt(6);
        switch (rand) {
        case 0: //weather is rainy, plants are watered
            statusBar.setStyle("-fx-background-image: "
                + "url(\"/images/RainTop.png\");");
            for (int i = 0; i < PLOTS.length; i++) {
                if (PLOTS[i].getPlant() != null) {
                    PLOTS[i].getPlant().addWater(.5);
                    //if rain kills plant
                    if (PLOTS[i].getPlant().getWaterLevel() >= 2) {
                        PLOTS[i].dead();
                        if (i <= 4) {
                            ROW_ONE.getChildren().remove(i);
                            ROW_ONE.getChildren().add(i,
                                        PLOTS[i].getPView());
                        } else {
                            ROW_TWO.getChildren().remove(i - 5);
                            ROW_TWO.getChildren().add(i - 5,
                                        PLOTS[i].getPView());
                        }
                        PLOTS[i].getPView().setOnMouseClicked(
                                    harvestingEventHandler(i));
                        PLOTS[i].getPView().setOnMouseEntered(
                                    cursorScythe(PLOTS[i]));
                        PLOTS[i].getPView().setOnMouseExited(
                                    cursorScythe(PLOTS[i]));
                    }
                }
            }
            currentWeather = "-fx-background-image: "
                + "url(\"/images/RainTop.png\");";
            break;
        case 1: //weather is stormy, random plant dies
            statusBar.setStyle("-fx-background-image: "
                + "url(\"/images/StormTop.png\");");
            int randPlant = RAND.nextInt(PLOTS.length);
            if (PLOTS[randPlant].getPlant() != null) {
                PLOTS[randPlant].dead();
                if (randPlant <= 4) {
                    ROW_ONE.getChildren().remove(randPlant);
                    ROW_ONE.getChildren().add(randPlant,
                        PLOTS[randPlant].getPView());
                } else {
                    ROW_TWO.getChildren().remove(randPlant - 5);
                    ROW_TWO.getChildren().add(randPlant - 5,
                        PLOTS[randPlant].getPView());
                }
                PLOTS[randPlant].getPView().setOnMouseClicked(
                            harvestingEventHandler(randPlant));
                PLOTS[randPlant].getPView().setOnMouseEntered(
                            cursorScythe(PLOTS[randPlant]));
                PLOTS[randPlant].getPView().setOnMouseExited(
                            cursorScythe(PLOTS[randPlant]));
            }
            currentWeather = "-fx-background-image: "
                + "url(\"/images/StormTop.png\");";
            break;
        case 2: //weather is direct sun, plants lose water
            statusBar.setStyle("-fx-background-image: "
                   + "url(\"/images/SunTop.png\");");
            for (int i = 0; i < PLOTS.length; i++) {
                if (PLOTS[i].getPlant() != null) {
                    PLOTS[i].getPlant().addWater(-.5);
                    // kills plant if waterLevels drops below 0 or if it
                    // matures and it has less than 1.0 water
                    if ((PLOTS[i].getPlant().getWaterLevel() < 0)
                            || ((PLOTS[i].getPlant().getWaterLevel() < 1)
                            && (PLOTS[i].getState() == 4))) {
                        PLOTS[i].dead();
                        if (i <= 4) {
                            ROW_ONE.getChildren().remove(i);
                            ROW_ONE.getChildren().add(i,
                                    PLOTS[i].getPView());
                        } else {
                            ROW_TWO.getChildren().remove(i - 5);
                            ROW_TWO.getChildren().add(i - 5,
                                    PLOTS[i].getPView());
                        }
                        PLOTS[i].getPView().setOnMouseClicked(
                                harvestingEventHandler(i));
                        PLOTS[i].getPView().setOnMouseEntered(
                                cursorScythe(PLOTS[i]));
                        PLOTS[i].getPView().setOnMouseExited(
                                cursorScythe(PLOTS[i]));
                    }
                }
            }
            currentWeather = "-fx-background-image: "
                   + "url(\"/images/SunTop.png\");";
            break;
        default: //weather is cloudy, nothing happens
            statusBar.setStyle("-fx-background-image: "
                   + "url(\"/images/GameTop.png\");");
            currentWeather = "-fx-background-image: "
                   + "url(\"/images/GameTop.png\");";
            break;
        }
    }

    /**
     * Goes to the next in-game day.
     */
    private void goToNextDay() {
        NEXT_DAY.setOnAction(actionEvent -> {
            day++;
            remade = false;
            for (Plot plot : PLOTS) {
                if (plot.getPlant() != null) {
                    plot.getPlant().grow();
                    float plotState = plot.getPlant().state();
                    if (plotState < 0.25) {
                        plot.seed();
                    } else if (plotState < 0.5) {
                        plot.immature();
                    } else if (plotState < 1) {
                        plot.growing();
                    } else {
                        plot.readyToHarvest();
                    }
                    if ((plot.getPlant().getWaterLevel() < 0)
                            || (plot.getPlant().getWaterLevel() >= 2)) {
                        plot.dead();
                    }
                }
            }
            makeGameScene();
            updateMarket();
            load(stageRef, GAME_SCENE);
        });
    }

    /**
     * Seeding functionality.
     * Clicking on the empty plot with the seed will
     * seed the plot.
     *
     * @param plant the seed to plant
     * @param index the index of plant in inventory
     */
    private static void seed(final Plantable plant, final int index) {
        if (seedOn) {
            for (int i = 0; i < PLOTS.length; i++) {
                int finalI = i;

                //changes cursor icon when hovered over a plot
                EventHandler<MouseEvent> crsChng = mouseEvent -> {
                    Image crs = plant.getImgView().getImage();
                    PLOTS[finalI].getPView().setCursor(new ImageCursor(crs));
                };

                EventHandler<MouseEvent> seedEventHandler =
                        seedEventHandler(plant, index, finalI);

                PLOTS[i].getPView().setOnMouseClicked(seedEventHandler);
                PLOTS[i].getPView().setOnMouseEntered(crsChng);
                //PLOTS[i].getPView().setOnMouseExited(cursorWater(PLOTS[i]));
            }
        }
    }

    /**
     * //handles clicking on plot.
     *
     * @param plant  plant being planted
     * @param index  place in inventory
     * @param finalI plot to plant it in
     * @return EventHandler
     */
    private static EventHandler<MouseEvent> seedEventHandler(final Plantable
                                                                     plant,
                                                             final int index,
                                                             final int finalI) {
        return new EventHandler<>() {
            @Override
            public void handle(final MouseEvent mouseEvent) {
                if (plant.getType().contains("seed") && PLAYER.getInvenList().contains(plant)) {
                    for (Plot p: PLOTS) {
                        Image crs = plant.getImgView().getImage();
                        p.getPView().setCursor(new ImageCursor(crs));
                    }
                    if (PLOTS[finalI].getState() == 1) {
                        String type = plant.getType();
                        if (type.contains("Carrot")) {
                            PLOTS[finalI].setPlant(new Carrot());
                        } else if (type.contains("Watermelon")) {
                            PLOTS[finalI].setPlant(new Watermelon());
                        } else {
                            PLOTS[finalI].setPlant(new Wheat());
                        }
                        PLOTS[finalI].seed();
                        try {
                            PLAYER.getInvenList().remove(index);
                        } catch (java.lang.IndexOutOfBoundsException e) {
                            System.out.println("No seed picked if");
                        }
                        updateInventory();
                        if (finalI <= 4) {
                            ROW_ONE.getChildren().remove(finalI);
                            ROW_ONE.getChildren().add(finalI,
                                    PLOTS[finalI].getPView());
                        } else {
                            ROW_TWO.getChildren().remove(finalI - 5);
                            ROW_TWO.getChildren().add(finalI - 5,
                                    PLOTS[finalI].getPView());
                        }
                        PLOTS[finalI].getPView().setOnMouseClicked(this);
                        PLOTS[finalI].getPView().setOnMouseEntered(
                                cursorWater(PLOTS[finalI]));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Cannot Plant");
                        alert.setHeaderText(null);
                        alert.setContentText("Plot is not empty!");
                        alert.showAndWait();
                    }
                } else if (plant.getType().contains("Fertil")) {
                    if (PLOTS[finalI].getPlant() != null) {
                        Plantable plantHold = PLOTS[finalI].getPlant();
                        Fertilizer.fertilize(plantHold);
                        try {
                            PLAYER.getInvenList().remove(index);
                        } catch (java.lang.IndexOutOfBoundsException e) {
                            System.out.println("No seed picked else");
                        }
                        updateInventory();
                        PLOTS[finalI].getPView().setOnMouseClicked(this);
                        PLOTS[finalI].getPView().setOnMouseEntered(
                                cursorWater(PLOTS[finalI]));
                    }
                }
                for (Plot p: PLOTS) {
                    p.getPView().setOnMouseEntered(cursorWater(p));
                }
            }
        };
    }

    /**
     * Harvesting functionality.
     * Clicking on the plot with the image of a matured plant will
     * remove the plant from the plot and put it directly in your inventory.
     */
    private static void harvest() {
        for (int i = 0; i < PLOTS.length; i++) {
            if (first) {
                PLOTS[i] = new Plot();
                randomizeDevelopment(i);
            }

            Image crs;
            if (PLOTS[i].getState() == 6 || PLOTS[i].getState() == 5) {
                crs = new Image("images/small_scythe.png");
            } else {
                crs = new Image("images/waterPot.png");
            }
            PLOTS[i].getPView().setCursor(new ImageCursor(crs));
            int finalI = i;

            //timers for watering handlers
            final Timer[] timer = {new Timer()};
            final TimerTask[] task = {new TimerTask() {
                @Override
                public void run() {
                    PLOTS[finalI].getPlant().addWater(.2);
                }
            }
            };

            EventHandler<MouseEvent> showWaterLevel =
                    showWaterLevel(PLOTS[finalI]);
            EventHandler<MouseEvent> waterPlant =
                    waterPlant(PLOTS[finalI], timer, task);
            EventHandler<MouseEvent> harvestingEventHandler =
                    harvestingEventHandler(finalI);
            EventHandler<MouseEvent> stopWatering =
                    stopWatering(PLOTS[finalI], timer, task, i,
                    harvestingEventHandler);

            if (i <= 4) {
                ROW_ONE.getChildren().add(PLOTS[i].getPView());
            } else {
                ROW_TWO.getChildren().add(PLOTS[i].getPView());
            }

            //Adding event handlers to plots
            if (PLOTS[i].getState() == 6 || PLOTS[i].getState() == 5) {
                PLOTS[i].getPView().setOnMouseEntered(cursorScythe(PLOTS[i]));
                PLOTS[i].getPView().setOnMouseExited(cursorScythe(PLOTS[i]));
            } else {
                PLOTS[i].getPView().setOnMouseEntered(cursorWater(PLOTS[i]));
                PLOTS[i].getPView().setOnMouseExited(cursorWater(PLOTS[i]));
            }
            PLOTS[i].getPView().setOnMouseClicked(harvestingEventHandler);
            PLOTS[i].getPView().addEventHandler(MouseEvent.ANY, showWaterLevel);
            PLOTS[i].getPView().setOnMousePressed(waterPlant);
            PLOTS[i].getPView().setOnMouseReleased(stopWatering);
        }
    }

    /**
     * Stops watering the plant and stops timer and task when mouse is released.
     * Also changes cursor icon back to the scythe
     *
     * @param plot      plot being watered
     * @param timer     timer
     * @param timerTask task for watering
     * @param i index of the plant
     * @param eH new eventHandler to switch to
     * @return EventHandler
     */
    private static EventHandler<MouseEvent> stopWatering(final Plot plot,
                                            final Timer[] timer,
                                            final TimerTask[] timerTask,
                                            final int i,
                                            final EventHandler<MouseEvent> eH) {
        return mouseEvent -> {
            if (plot.getState() > 1 && plot.getState() <= 4) {
                timerTask[0].cancel();
                timer[0].cancel();
                System.out.println(plot.getPlant().getWaterLevel());

                //kills plant if too much water
                if (plot.getPlant().getWaterLevel() >= 2) {
                    plot.getPlant().grow();
                    plot.dead();
                    if (i <= 4) {
                        ROW_ONE.getChildren().remove(i);
                        ROW_ONE.getChildren().add(i,
                                PLOTS[i].getPView());
                    } else {
                        ROW_TWO.getChildren().remove(i - 5);
                        ROW_TWO.getChildren().add(i - 5,
                                PLOTS[i].getPView());
                    }

                    //adds event handlers to updated plot image
                    PLOTS[i].getPView().setOnMouseClicked(eH);
                    PLOTS[i].getPView().setOnMouseEntered(
                            cursorScythe(PLOTS[i]));
                    PLOTS[i].getPView().setOnMouseExited(
                            cursorScythe(PLOTS[i]));
                }
            }
        };
    }

    /**
     * EventHandler for watering a plant while holding down the mouse button.
     * Amount of water is based on time the mouse is held down.
     *
     * @param plot  plot being watered
     * @param timer timer
     * @param task  task for watering
     * @return EventHandler
     */
    private static EventHandler<MouseEvent> waterPlant(final Plot plot,
                                            final Timer[] timer,
                                            final TimerTask[] task) {
        return mouseEvent -> {
            if (plot.getState() > 1 && plot.getState() <= 4) {
                Image crs = new Image("images/waterPot.png");
                plot.getPView().setCursor(new ImageCursor(crs));
                timer[0] = new Timer();
                task[0] = new TimerTask() {
                    @Override
                    public void run() {
                        plot.getPlant().addWater(.2);
                    }
                };
                //every quarter second add .2 water
                timer[0].scheduleAtFixedRate(task[0], 0, 250);
            }
        };
    }

    /**
     * handles clicking on a plot to harvest.
     *
     * @param finalI plot number
     * @return EventHandler
     */
    private static EventHandler<MouseEvent> harvestingEventHandler(final
                                                                   int finalI) {
        return new EventHandler<>() {
            @Override
            public void handle(final MouseEvent mouseEvent) {
                if (PLOTS[finalI].getState() == 6) { //removes mature plant
                    String type = PLOTS[finalI].getPlant().getType();
                    //add plant to inventory
                    if (type.equals("Carrot")) {
                        PLAYER.getInventory().addCarrot(1);
                    } else if (type.equals("Watermelon")) {
                        PLAYER.getInventory().addWatermelon(1);
                    } else {
                        PLAYER.getInventory().addWheat(1);
                    }
                    updateInventory();
                    //update farm UI
                    PLOTS[finalI].resetState();
                    if (finalI <= 4) {
                        ROW_ONE.getChildren().remove(finalI);
                        ROW_ONE.getChildren().add(finalI,
                                PLOTS[finalI].getPView());
                    } else {
                        ROW_TWO.getChildren().remove(finalI - 5);
                        ROW_TWO.getChildren().add(finalI - 5,
                                PLOTS[finalI].getPView());
                    }
                    PLOTS[finalI].getPView().setOnMouseClicked(this);
                } else if (PLOTS[finalI].getState() == 5) { //removes dead plant
                    PLOTS[finalI].resetState();
                    if (finalI <= 4) {
                        ROW_ONE.getChildren().remove(finalI);
                        ROW_ONE.getChildren().add(finalI,
                                PLOTS[finalI].getPView());
                    } else {
                        ROW_TWO.getChildren().remove(finalI - 5);
                        ROW_TWO.getChildren().add(finalI - 5,
                                PLOTS[finalI].getPView());
                    }
                    PLOTS[finalI].getPView().setOnMouseClicked(this);
                }
            }
        };
    }

    /**
     * Changes cursor icon to scythe.
     * @param plot plot
     * @return mouse event
     */
    private static EventHandler<MouseEvent> cursorScythe(final Plot plot) {
        return mouseEvent -> {
            Image crs = new Image("images/small_scythe.png");
            plot.getPView().setCursor(new ImageCursor(crs));
        };
    }

    /**
     * Changes the cursor icon to waterPot.
     * @param plot plot
     * @return mouse event
     */
    private static EventHandler<MouseEvent> cursorWater(final Plot plot) {
        return mouseEvent -> {
            Image crs = new Image("images/waterPot.png");
            plot.getPView().setCursor(new ImageCursor(crs));
        };
    }

    /**
     * Shows the waterLevel of a specific plot with a progressBar.
     * @param plot plot being checked
     * @return EventHandler
     */
    private static EventHandler<MouseEvent> showWaterLevel(final Plot plot) {
        return mouseEvent -> {
            if (plot.getPlant() != null) {
                Tooltip t = new Tooltip();
                t.setShowDelay(Duration.millis(15));
                ProgressBar pb = new ProgressBar(
                        plot.getPlant().getWaterLevel() / 2);
                t.setGraphic(pb);

                //adds water drop icon to progress bar
                byte[] emojiBytes = new byte[] {(byte) 0xF0, (byte) 0x9F,
                    (byte) 0x92, (byte) 0xA7};
                String emojiAsString = new String(emojiBytes,
                        StandardCharsets.UTF_8);
                t.setText(emojiAsString);
                t.setStyle("-fx-text-fill: aqua");
                t.setTextAlignment(TextAlignment.JUSTIFY);

                //changes color of bar depending on water level
                if (plot.getPlant().getWaterLevel() < 1) {
                    pb.setStyle("-fx-accent: red");
                } else if ((plot.getPlant().getWaterLevel() >= 1)
                        && (plot.getPlant().getWaterLevel() < 3)) {
                    pb.setStyle("-fx-accent: blue");
                } else {
                    pb.setStyle("-fx-accent: black");
                }
                Tooltip.install(plot.getPView(), t);
            }
        };
    }

    /**
     * Randomizes development of plot.
     * @param i int i
     */
    private static void randomizeDevelopment(final int i) {
        int rand = (int) (Math.random() * 4) + 1;
        int rand2 = (int) (Math.random() * 3);
        Plantable in;
        //determines what type of plant will be planted
        switch (rand2) {
        case 0:
            in = new Watermelon();
            break;
        case 1:
            in = new Wheat();
            break;
        default:
            in = new Carrot();
            break;
        }
        //determines what state of development it will be in
        switch (rand) {
        case 2:
            PLOTS[i].seed();
            PLOTS[i].setPlant(in);
            break;
        case 3:
            PLOTS[i].immature();
            PLOTS[i].setPlant(in);
            PLOTS[i].getPlant().setGrowthTime(in.getGrowDays() / 2);
            break;
        default:
            in.addWater(1.0);
            PLOTS[i].growing();
            PLOTS[i].setPlant(in);
            PLOTS[i].getPlant().setGrowthTime(in.getGrowDays());
            break;
        }
    }

    /**
     * This method navigates to the inventory scene.
     */
    private static void goToInventory() {
        INVENTORY.setOnAction(actionEvent -> {
            //Sets up inventory box
            final Stage inventoryWindow = new Stage();
            inventoryWindow.initModality(Modality.WINDOW_MODAL);
            inventoryWindow.setX(50);
            inventoryWindow.setY(160);
            inventVBox = new VBox();
            inventVBox.setAlignment(CENTER);
            inventVBox.setPadding(new Insets(10));
            updateInventory();
            Scene inventoryScene = new Scene(inventVBox, 350, 450);
            inventoryScene.getStylesheets().add("styles/inventStyle.css");
            inventoryWindow.setScene(inventoryScene);
            inventoryWindow.setAlwaysOnTop(true);
            inventoryWindow.show();

            //Handles actions when inventory box is closed
            inventoryWindow.setOnHidden(event -> {
                for (Plot plot : PLOTS) {
                    EventHandler<MouseEvent> crsChng = cursorScythe(plot);
                    plot.getPView().setOnMouseEntered(crsChng);
                    seedOn = false;
                    ROW_ONE.getChildren().clear();
                    ROW_TWO.getChildren().clear();
                    harvest();
                }
            });
        });
    }

    /**
     * This method updates the on-screen inventory.
     */
    public static void updateInventory() {
        if (inventVBox == null) {
            inventVBox = new VBox();
        }
        ArrayList<Plantable> holder = PLAYER.getInvenList();
        ObservableList<Plantable> names =
                FXCollections.observableArrayList(holder);
        inventView = new ListView<>(names);

        //Defines graphic and text in listView
        inventView.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(final Plantable item, final boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getType());
                    setGraphic(item.getImgView());
                }
            }
        });

        //Handles clicking on inventory cells
        inventView.setOnMouseClicked(event -> {
            Plantable item = inventView.getSelectionModel().getSelectedItem();
            String type = item.getType();
            if ((type.contains("seed")) || (type.contains("Fertil"))) {
                seedOn = true;
                seed(inventView.getSelectionModel().getSelectedItem(),
                        inventView.getSelectionModel().getSelectedIndex());
            } else {
                seedOn = false;
                ROW_ONE.getChildren().clear();
                ROW_TWO.getChildren().clear();
                harvest();
            }
        });

        if (!inventVBox.getChildren().isEmpty()) {
            inventVBox.getChildren().clear();
        }
        inventVBox.getChildren().addAll(INVENT_TEXT, inventView);
    }

    /**
     * Navigates to the market screen.
     */
    private void goToMarket() {
        MARKET.setOnAction(actionEvent -> {
            marketPane = new BorderPane();
            Scene marketScene = new Scene(marketPane, 660, 500);
            inventVBox = new VBox();
            updateInventory();
            marketScene.getStylesheets().add("styles/marketStyle.css");
            makeMarketScene(marketPane, remade);
            load(stageRef, marketScene);
        });
    }

    /**
     * Creates the market screen.
     *
     * @param pane the pane to add controls to
     * @param remade whether the scene is being remade
     */
    private void makeMarketScene(final BorderPane pane, final boolean remade) {
        //Sets up top
        HBox top = new HBox();
        top.setId("top");
        Region regionLeft = new Region();
        regionLeft.setPrefWidth(225);
        Label date = new Label("Day: " + day);
        top.getChildren().addAll(date, MONEY_TITLE, MONEY_LABEL, regionLeft,
                BACK_TO_FARM);

        //Sets up middle area
        HBox middle = new HBox();
        middle.setAlignment(CENTER);
        middle.setId("middle");
        VBox inventory = new VBox();
        inventory.setAlignment(CENTER);
        StackPane marketBuy = new StackPane();
        Label inventoryTitle = new Label("Inventory");
        updateInventory();
        Image marketImg = new Image("/images/Market.png");
        ImageView marketImgView = new ImageView(marketImg);
        inventView.setPrefSize(250, 250);
        inventory.getChildren().addAll(inventoryTitle, inventView);
        marketBuy.getChildren().addAll(marketImgView, BUY_BTN);
        middle.getChildren().addAll(marketBuy, inventory);

        //Randomizes initial seller wares
        if ((day == 1) && (!remade)) {
            WARES.clear();
            for (int i = 0; i < 10; i++) {
                int rand = RAND.nextInt(4);
                switch (rand) {
                case 0:
                    WARES.add(new Seed("Watermelon"));
                    break;
                case 1:
                    WARES.add(new Seed("Wheat"));
                    break;
                case 2:
                    WARES.add(new Fertilizer("Fertilizer"));
                    break;
                default:
                    WARES.add(new Seed("Carrot"));
                    break;
                }
            }
        }

        //Finalizes set up
        goBack();
        sell();
        buy();
        pane.setTop(top);
        pane.setCenter(middle);
    }

    /**
     * Updates the market with
     * the new prices and wares.
     */
    public void updateMarket() {
        WARES.clear();
        for (int i = 0; i < 10; i++) {
            int rand = RAND.nextInt(4);
            switch (rand) {
            case 0:
                WARES.add(new Seed("Watermelon"));
                break;
            case 1:
                WARES.add(new Seed("Wheat"));
                break;
            case 2:
                WARES.add(new Fertilizer("Fertilizer"));
                break;
            default:
                WARES.add(new Seed("Carrot"));
                break;
            }
        }
        randMul = RAND.nextFloat();
    }

    /**
     * Returns to game screen.
     */
    private void goBack() {
        BACK_TO_FARM.setOnAction(actionEvent -> {
            remade = true;
            makeGameScene();
            load(stageRef, GAME_SCENE);
        });
    }

    /**
     * Handles selling at the market.
     */
    private void sell() {
        inventView.setOnMouseClicked(event -> {
            //Sets up sell box
            final Stage sellBox = new Stage();
            sellBox.initModality(Modality.WINDOW_MODAL);
            VBox vbox = new VBox();
            String type = "";
            if (inventView.getSelectionModel().getSelectedItem() != null) {
                Plantable p = inventView.getSelectionModel().getSelectedItem();
                type = p.getType();
            }
            Label text = new Label("Do you want to sell a "
                    + type + " for");
            int index = inventView.getSelectionModel().getSelectedIndex();
            int sellPrice;
            if (type.contains("seed")) {
                sellPrice = PLAYER.getInventoryAt(index).getSalePrice();
            } else {
                sellPrice = (int) (PLAYER.getInventoryAt(index).getSalePrice()
                        * FarmSim.randMul);
            }
            Label price = new Label(sellPrice + " dollars?");
            HBox buttons = new HBox();
            Region spacer = new Region();
            spacer.setPrefWidth(25);
            Button yes = new Button("Yes");
            Button no = new Button("No");
            buttons.getChildren().addAll(yes, spacer, no);
            buttons.setAlignment(CENTER);
            vbox.setAlignment(CENTER);
            vbox.setPadding(new Insets(10));
            vbox.getChildren().addAll(text, price, buttons);
            Scene sellBoxScene = new Scene(vbox, 600, 300);
            sellBoxScene.getStylesheets().add("styles/marketStyle.css");
            sellBox.setScene(sellBoxScene);
            sellBox.show();

            //Handles pressing no button
            no.setOnAction(actionEvent -> sellBox.hide());

            //Handles pressing yes button and selling item
            yes.setOnAction(actionEvent -> {
                ArrayList<Plantable> holder = PLAYER.getInvenList();
                holder.remove(index);
                PLAYER.setInventoryList(holder);
                updateInventory();
                makeMarketScene(marketPane, remade);
                PLAYER.setMoney(PLAYER.getMoney() + sellPrice);
                MONEY_LABEL.setText(PLAYER.getMoney() + "");
                sellBox.hide();
            });
        });
    }

    /**
     * Handles buying at the market.
     */
    private void buy() {
        BUY_BTN.setOnMouseClicked(event -> {
            //Sets up buy box
            final Stage buyBox = new Stage();
            buyBox.initModality(Modality.WINDOW_MODAL);
            VBox vbox = new VBox();
            Label text = new Label("Items to Buy");
            vbox.setAlignment(CENTER);
            vbox.setPadding(new Insets(10));
            SELLER.setInventoryList(WARES);
            ObservableList<Plantable> names =
                    FXCollections.observableArrayList(WARES);
            buyList = new ListView<>(names);
            vbox.getChildren().addAll(text, buyList);
            Scene buyBoxScene = new Scene(vbox, 600, 300);
            buyBoxScene.getStylesheets().add("styles/marketStyle.css");
            buyBox.setScene(buyBoxScene);
            buyBox.show();

            //Handles buying goods from seller
            buyList.setOnMouseClicked(event2 -> {
                final Stage sellConfirm = new Stage();
                sellConfirm.initModality(Modality.WINDOW_MODAL);
                VBox contentBox = new VBox();
                Label question = new Label("");
                try {
                    Plantable p = buyList.getSelectionModel().getSelectedItem();
                    question = new Label("Do you want to buy a "
                            + p.getType()
                            + " for");
                } catch (java.lang.NullPointerException e) {
                    System.out.println("Object not picked");
                }
                int index = buyList.getSelectionModel().getSelectedIndex();
                int buyPrice = (int) (SELLER.getInventoryAt(index).getBuyPrice()
                        * FarmSim.randMul) + 10;
                Label price = new Label(buyPrice + " dollars?");
                HBox buttons = new HBox();
                Button yes = new Button("Yes");
                Button no = new Button("No");
                Region spacer = new Region();
                spacer.setPrefWidth(25);
                buttons.getChildren().addAll(yes, spacer, no);
                buttons.setAlignment(CENTER);
                contentBox.setAlignment(CENTER);
                contentBox.setPadding(new Insets(10));
                contentBox.getChildren().addAll(question, price, buttons);
                Scene sellConfirmScene = new Scene(contentBox, 600, 300);
                sellConfirmScene.getStylesheets().add("styles/marketStyle.css");
                sellConfirm.setScene(sellConfirmScene);
                sellConfirm.show();

                no.setOnAction(actionEvent -> sellConfirm.hide());

                yes.setOnAction(actionEvent -> {
                    if ((PLAYER.getMoney() >= buyPrice)
                            && (PLAYER.getInvenList().size()
                            < PLAYER.getInventory().getCapacity())) {
                        ArrayList<Plantable> playerHolder =
                                PLAYER.getInvenList();
                        ArrayList<Plantable> sellerHolder =
                                SELLER.getInvenList();
                        Plantable plantHold = sellerHolder.get(index);
                        playerHolder.add(plantHold);
                        PLAYER.setInventoryList(playerHolder);
                        sellerHolder.remove(index);
                        SELLER.setInventoryList(sellerHolder);
                        updateInventory();
                        makeMarketScene(marketPane, remade);
                        PLAYER.setMoney(PLAYER.getMoney() - buyPrice);
                        MONEY_LABEL.setText(PLAYER.getMoney() + "");
                        names.remove(index);
                        vbox.getChildren().clear();
                        vbox.getChildren().addAll(text, buyList);
                        sellConfirm.hide();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Not enough money or"
                                + " inventory space.");
                        alert.showAndWait();
                    }
                });
            });

            //Sets graphics and text for seller's ware list
            buyList.setCellFactory(lv -> new ListCell<>() {
                @Override
                public void updateItem(final Plantable item,
                                       final boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.getType());
                        setGraphic(item.getImgView());
                    }
                }
            });
        });
    }
}
