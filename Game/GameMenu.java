package Game;

import Game.StartGame;
import Game.View.DefinitionProcess;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;

/** Represents a Menu GUI for Boggle.
 * Includes 3 submenus Menu, Settings, and Load Menu
 */

public class GameMenu extends Application{
    Stage stage;
    Button startButton, loadButton, settingsButton; //buttons for functions
    Button PlayerButton, SizeButton,  settingsButton2; //buttons for functions
    Button loadButton2, settingsButton3; //buttons for functions

    Slider timeSlider = new Slider(10, 120, 60);
    Slider roundsSlider = new Slider(1, 10, 5);

    Label ModeLabel = new Label("BOGGLE!");
    Label ModeLabel2 = new Label("Settings");
    Label ModeLabel3 = new Label("Load Menu");

    Label timeCaption = new Label("Time Per Round:");
    Label roundsCaption = new Label("Number of Rounds:");
    Label sizeCaption = new Label("Size of Grid:");
    Label playerCaption = new Label("Opponent:");

    Label timeValueLabel = new Label("60 Seconds");
    Label roundsValueLabel = new Label("5 Rounds");

    GridPane gridPane;
    GridPane subGridPane;

    private int size = 4;
    private boolean human = true;
    private int timeValue = 60;
    private int roundsValue = 5;

    /**
     * Main method
     *
     * @param args agument, if any
     */

    public static void main(String[] args) {
        try{
            DefinitionProcess.initializeDictionary();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Application.launch(args);
    }

    /**
     * Start method.  Control of application flow is dictated by JavaFX framework
     *
     * @param stage upon which to load GUI elements
     */

    public void start(Stage stage) {
        this.stage = stage;
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        this.stage.setTitle("BOGGLE!");

        var scene = new Scene(gridPane, 800, 800);
        this.stage.setScene(scene);

        setUpButtons();
        Menu();
    }

    /**
     * openMenu method. This method will be used to open up the menu again after the user finishes a game.
     */
    public void openMenu(){
        start(stage);
    }

    /**
     * setUpButtons method. Configures all Buttons, Sliders, and Labels.
     */

    private void setUpButtons() {

        //Configure the main gridpane to set the buttons on

        gridPane.setStyle("-fx-background-color: #121212;");

        //Configure main menu buttons and labels
        //Label: BOGGLE!
        //Buttons: Start Game, Load Game, Settings

        ModeLabel.setMinWidth(250);
        ModeLabel.setFont(new Font(100));
        ModeLabel.setStyle("-fx-text-fill: #e8e6e3");

        startButton = new Button("Start Game");
        startButton.setPrefSize(400, 50);
        startButton.setFont(new Font(12));
        startButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        loadButton = new Button("Load Game");
        loadButton.setPrefSize(400, 50);
        loadButton.setFont(new Font(12));
        loadButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        settingsButton = new Button("Settings");
        settingsButton.setPrefSize(400, 50);
        settingsButton.setFont(new Font(12));
        settingsButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //Configure settings menu labels, buttons, sliders, and a sub-GridPane
        //Sets the menu labels, buttons and sliders on the sub-GridPane
        //Labels: Settings, timeCaption, roundsCaption, playerCaption, timeValueLabel
        //Sliders: Rounds, Time
        //Buttons: Human/Computer, 4x4/5x5, Exit Settings

        ModeLabel2.setMinWidth(250);
        ModeLabel2.setFont(new Font(100));
        ModeLabel2.setStyle("-fx-text-fill: #e8e6e3");

        PlayerButton = new Button("Human");
        PlayerButton.setPrefSize(200, 50);
        PlayerButton.setFont(new Font(12));
        PlayerButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        SizeButton = new Button("4x4");
        SizeButton.setPrefSize(200, 50);
        SizeButton.setFont(new Font(12));
        SizeButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        settingsButton2 = new Button("Exit Settings");
        settingsButton2.setPrefSize(400, 50);
        settingsButton2.setFont(new Font(12));
        settingsButton2.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        subGridPane = new GridPane();
        subGridPane.setAlignment(Pos.CENTER);
        subGridPane.setStyle("-fx-background-color: #121212;");

        timeCaption.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(timeCaption, 0, 1);

        roundsCaption.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(roundsCaption, 0, 2);

        sizeCaption.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(sizeCaption, 0, 3);

        playerCaption.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(playerCaption, 0, 4);

        timeValueLabel.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(timeValueLabel, 2, 1);

        roundsValueLabel.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(roundsValueLabel, 2, 2);

        GridPane.setConstraints(timeSlider, 1, 1);
        GridPane.setMargin(timeSlider, new Insets(10, 10, 10, 10));

        GridPane.setConstraints(roundsSlider, 1, 2);
        GridPane.setMargin(roundsSlider, new Insets(10, 10, 10, 10));

        GridPane.setConstraints(SizeButton, 1, 3);
        GridPane.setMargin(SizeButton, new Insets(10, 10, 10, 120));

        GridPane.setMargin(PlayerButton, new Insets(10, 10, 10, 120));
        GridPane.setConstraints(PlayerButton, 1, 4);

        subGridPane.getChildren().addAll(timeCaption, roundsCaption, sizeCaption, playerCaption, timeValueLabel, roundsValueLabel, timeSlider, roundsSlider, SizeButton, PlayerButton);

        //Configure load menu buttons
        //Labels: Load Menu
        //Buttons: Load Save, Exit Load Menu

        ModeLabel3.setMinWidth(250);
        ModeLabel3.setFont(new Font(100));
        ModeLabel3.setStyle("-fx-text-fill: #e8e6e3");

        loadButton2 = new Button("Load Save");
        loadButton2.setPrefSize(400, 50);
        loadButton2.setFont(new Font(12));
        loadButton2.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        settingsButton3 = new Button("Exit Load Menu");
        settingsButton3.setPrefSize(400, 50);
        settingsButton3.setFont(new Font(12));
        settingsButton3.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

    }

    /**
     * Menu method. Initalizes the starting Menu with the labels and buttons configured for Menu.
     */
    private void Menu() {

        //Sets the title of the stage to "BOGGLE!" and clears the previous items on the pane for the current one.

        this.stage.setTitle("BOGGLE!");
        gridPane.getChildren().clear();

        //Adds the title label "BOGGLE!" to an Hbox TitleBox and then sets it in the gridPane.

        HBox TitleBox = new HBox(20, ModeLabel);
        TitleBox.setPadding(new Insets(200, 20, 100, 20));
        TitleBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(TitleBox, 1, 2);

        //Adds the startButton, loadButton, and settingsButton to a VBox vBox and then sets it in the gridPane.

        VBox vBox = new VBox(20, startButton, loadButton, settingsButton);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(vBox, 1, 3);

        //Tracks User inputs in the menu, checks if the user clicks startButton, loadButton, or settingsButton and
        //sends them to the appropriate sub menu.

        startButton.setOnAction(e -> {
            new StartGame(stage, size, timeValue, human, roundsValue, this);
            gridPane.requestFocus();
        });

        loadButton.setOnAction(e -> {
            LoadMenu();
            gridPane.requestFocus();
        });

        settingsButton.setOnAction(e -> {
            SettingsMenu();
            gridPane.requestFocus();
        });

        //sets the fade transitions for the menu components as it loads in.

        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setNode(vBox);
        fade.play();

        FadeTransition fade2 = new FadeTransition();
        fade2.setDuration(Duration.millis(1000));
        fade2.setFromValue(0);
        fade2.setToValue(10);
        fade2.setNode(TitleBox);
        fade2.play();

        //Adds the TitleBox and vBox to the gridPane and then shows it on the stage.

        gridPane.getChildren().addAll(TitleBox, vBox);
        stage.show();

    }

    /**
     * Settings Menu method. Initalizes the Settings Menu with the labels, buttons, and sliders configured for settings.
     */
    private void SettingsMenu() {

        //Sets the title of the stage to "Settings Menu" and clears the previous items on the pane for the current one.

        stage.setTitle("Settings Menu");
        gridPane.getChildren().clear();

        //Adds the title label "Settings" to an Hbox TitleBox and then sets it in the gridPane.

        HBox TitleBox = new HBox(20, ModeLabel2);
        TitleBox.setPadding(new Insets(200, 20, 20, 20));
        TitleBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(TitleBox, 1, 0);

        //Adds subGridPane which contains timeCaption, roundsCaption, sizeCaption, playerCaption, timeValueLabel,
        // roundsValueLabel, timeSlider, roundsSlider, SizeButton, and PlayerButton and then sets it in the gridPane.

        GridPane.setConstraints(subGridPane, 1, 1);

        //Adds settingsButton2 to a VBox vBox and then sets it in the gridPane.

        VBox vBox = new VBox(20, settingsButton2);
        vBox.setPadding(new Insets(70, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(vBox, 1, 2);

        //Tracks User inputs in the settings menu, checks if the user clicks any sliders or any buttons and sends them
        //to the appropriate sub menu.

        timeSlider.setOnMouseReleased(e -> {
            timeValue = (int) timeSlider.getValue();
            timeValueLabel.setText(timeValue + " Seconds");
            gridPane.requestFocus();
            stage.show();
        });

        roundsSlider.setOnMouseReleased(e -> {
            roundsValue = (int) roundsSlider.getValue();
            roundsValueLabel.setText(roundsValue + " Rounds");
            gridPane.requestFocus();
            stage.show();
        });

        settingsButton2.setOnAction(e -> {
            Menu();
            gridPane.requestFocus();
        });

        SizeButton.setOnAction(e -> {
            if(SizeButton.getText().equals("4x4")){
                size = 5;
                SizeButton.setText("5x5");
            }
            else if(SizeButton.getText().equals("5x5")){
                size = 4;
                SizeButton.setText("4x4");
            }
            gridPane.requestFocus();
            stage.show();
        });

        PlayerButton.setOnAction(e -> {
            if(PlayerButton.getText().equals("Human")){
                human = false;
                PlayerButton.setText("Computer");
            }
            else if(PlayerButton.getText().equals("Computer")){
                human = true;
                PlayerButton.setText("Human");
            }
            gridPane.requestFocus();
            stage.show();
        });

        //sets the fade transitions for the menu components as it loads in.

        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setNode(vBox);
        fade.play();

        FadeTransition fade2 = new FadeTransition();
        fade2.setDuration(Duration.millis(1000));
        fade2.setFromValue(0);
        fade2.setToValue(10);
        fade2.setNode(TitleBox);
        fade2.play();

        FadeTransition fade3 = new FadeTransition();
        fade3.setDuration(Duration.millis(1000));
        fade3.setFromValue(0);
        fade3.setToValue(10);
        fade3.setNode(subGridPane);
        fade3.play();

        //Adds the subGridTitleBox and vBox to the gridPane and then shows it on the stage.

        gridPane.getChildren().addAll(subGridPane, TitleBox, vBox);
        this.stage.show();
    }

    /**
     * LoadMenu method. Initalizes the Load Menu with the label and buttons configured for the Load Menu.
     */

    private void LoadMenu() {

        //Sets the title of the stage to "Load Menu" and clears the previous items on the pane for the current one.

        this.stage.setTitle("Load Menu");
        gridPane.getChildren().clear();

        //Adds the startButton, loadButton, and settingsButton to a VBox vBox and then sets it in the gridPane.

        HBox TitleBox = new HBox(20, ModeLabel3);
        TitleBox.setPadding(new Insets(200, 20, 20, 20));
        TitleBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(TitleBox, 0, 0);

        VBox vBox = new VBox(20, loadButton2, settingsButton3);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(vBox, 0, 1);

        //Tracks User inputs in the settings menu, checks if the user clicks any sliders or any buttons and sends them
        //to the appropriate sub menu.

        loadButton2.setOnAction(e -> {
            //LoadGame();
            this.gridPane.requestFocus();
            throw new java.lang.UnsupportedOperationException("Not supported yet.");
        });

        settingsButton3.setOnAction(e -> {
            Menu();
            gridPane.requestFocus();
        });

        //sets the fade transitions for the menu components as it loads in.

        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setNode(vBox);
        fade.play();

        FadeTransition fade2 = new FadeTransition();
        fade2.setDuration(Duration.millis(1000));
        fade2.setFromValue(0);
        fade2.setToValue(10);
        fade2.setNode(TitleBox);
        fade2.play();

        //Adds the TitleBox and vBox to the gridPane and then shows it on the stage.

        gridPane.getChildren().addAll(TitleBox, vBox);
        stage.show();
    }
}