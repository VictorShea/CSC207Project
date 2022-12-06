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
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/** Represents a Menu GUI for Boggle.
 * Includes 3 submenus Menu, Settings, and Load Menu
 */

public class GameMenu extends Application{
    Stage stage;
    Button startButton, loadButton, settingsButton, leaderboardButton; //buttons for functions in Menu
    Button settingsButton2; //buttons for functions in Settings
    Button loadButton2, settingsButton3; //buttons for functions Load Menu
    Button settingsButton4; //buttons for functions in Leaderboard
    Slider timeSlider = new Slider(10, 120, 60);
    Slider roundsSlider = new Slider(1, 10, 5);

    TextField nameDisplay = new TextField("Player 1");
    TextField nameDisplay2 = new TextField("Player 2");

    Button PlayerButton = new Button("Human");
    Button SizeButton = new Button("4x4");
    Button VoiceButton = new Button("Off");
    Button ContrastButton = new Button("Off");
    Label ModeLabel = new Label("BOGGLE!");
    Label ModeLabel2 = new Label("Settings");
    Label ModeLabel3 = new Label("Load Menu");
    Label ModeLabel4 = new Label("Leaderboard");

    Label playerLabel = new Label("Player 1 Name:");
    Label playerLabel2 = new Label("Player 2 Name:");
    Label timeCaption = new Label("Time Per Round:");
    Label roundsCaption = new Label("Number of Rounds:");
    Label sizeCaption = new Label("Size of Grid:");
    Label playerCaption = new Label("Opponent:");
    Label voiceCaption = new Label("Voice Accessibility:");
    Label contrastCaption = new Label("Contrast Accessibility:");

    Label timeValueLabel = new Label("60 Seconds");
    Label roundsValueLabel = new Label("5 Rounds");

    GridPane gridPane;
    GridPane subGridPane;

    private int size = 4;
    private boolean human = true;
    private boolean colorContrast = false;
    private boolean voice = false;
    private int timeValue = 60;
    private int roundsValue = 5;
    private String name = nameDisplay.getText();
    private String name2 = nameDisplay2.getText();

    ArrayList<String> listViewKeys = new ArrayList<String>();
    ArrayList<Integer> listViewItems = new ArrayList<Integer>();


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
     * saveLeaderboard method. Stores the current leaderboard in user files.
     */

    public void saveLeaderboard() throws IOException {
        FileWriter f = new FileWriter("Leaderboard.txt");
        for(int i = 0; i < listViewKeys.size(); i++){
            f.write(listViewKeys.get(i) + "/a/" + listViewItems.get(i) + "\n");
        }
        f.close();
    }

    /**
     * saveLeaderboard method. Stores the current leaderboard in user files.
     */
    public void loadLeaderboard() throws IOException {
        FileReader f = new FileReader("Leaderboard.txt");
        listViewItems.clear();
        listViewKeys.clear();
        BufferedReader fread = new BufferedReader(f);
        String line = "";
        while ((line = fread.readLine()) != null){
            listViewKeys.add(line.split("/a/")[0]);
            listViewItems.add(Integer.valueOf(line.split("/a/")[1]));
        }
        fread.close();
        f.close();
    }

    /**
     * openMenu method. This method will be used to open up the menu again after the user finishes a game.
     */
    public void openMenu(){
        start(stage);
    }
    public void openMenu(HashMap<String, Integer> score){

        listViewKeys.addAll(score.keySet());
        listViewItems.addAll(score.values());

        ArrayList<String> tempListViewKeys = new ArrayList<String>();
        ArrayList<Integer> tempListViewItems = new ArrayList<Integer>();

        int size = listViewKeys.size();

        for (int i = 0; i < size; i++) {
            int maxNumPos = 0;
            for (int j = 0; j < listViewKeys.size(); j++) {
                if (listViewItems.get(maxNumPos) <= listViewItems.get(j)) {
                    maxNumPos = j;
                }
            }

            tempListViewKeys.add(listViewKeys.get(maxNumPos));
            tempListViewItems.add(listViewItems.get(maxNumPos));
            listViewKeys.remove(maxNumPos);
            listViewItems.remove(maxNumPos);
        }

        listViewKeys = tempListViewKeys;
        listViewItems = tempListViewItems;


        try{
            saveLeaderboard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        start(stage);
    }

    /**
     * setUpButtons method. Configures all Buttons, Sliders, and Labels.
     */

    private void setUpButtons() {
        try{
        loadLeaderboard();} catch (IOException e) {
            throw new RuntimeException(e);
        }

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

        leaderboardButton = new Button("Leaderboard");
        leaderboardButton.setPrefSize(400, 50);
        leaderboardButton.setFont(new Font(12));
        leaderboardButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //Configure settings menu labels, buttons, sliders, and a sub-GridPane
        //Sets the menu labels, buttons and sliders on the sub-GridPane
        //Labels: Settings, timeCaption, roundsCaption, playerCaption, contrastCaption, voiceCaption, timeValueLabel
        //Sliders: Rounds, Time
        //Buttons: Human/Computer, 4x4/5x5, Exit Settings, Voice: On/Off, Color Contrast: On/Off

        ModeLabel2.setMinWidth(250);
        ModeLabel2.setFont(new Font(100));
        ModeLabel2.setStyle("-fx-text-fill: #e8e6e3");

        PlayerButton.setPrefSize(200, 50);
        PlayerButton.setFont(new Font(12));
        PlayerButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        GridPane.setMargin(PlayerButton, new Insets(10, 10, 10, 120));
        GridPane.setConstraints(PlayerButton, 1, 5);

        SizeButton.setPrefSize(200, 50);
        SizeButton.setFont(new Font(12));
        SizeButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        GridPane.setConstraints(SizeButton, 1, 4);
        GridPane.setMargin(SizeButton, new Insets(10, 10, 10, 120));

        VoiceButton.setPrefSize(200, 50);
        VoiceButton.setFont(new Font(12));
        VoiceButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        GridPane.setMargin(VoiceButton, new Insets(10, 10, 10, 120));
        GridPane.setConstraints(VoiceButton, 1, 7);

        ContrastButton.setPrefSize(200, 50);
        ContrastButton.setFont(new Font(12));
        ContrastButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        GridPane.setMargin(ContrastButton, new Insets(10, 10, 10, 120));
        GridPane.setConstraints(ContrastButton, 1, 6);

        settingsButton2 = new Button("Exit Settings");
        settingsButton2.setPrefSize(400, 50);
        settingsButton2.setFont(new Font(12));
        settingsButton2.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        subGridPane = new GridPane();
        subGridPane.setAlignment(Pos.CENTER);
        subGridPane.setStyle("-fx-background-color: #121212;");

        timeCaption.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(timeCaption, 0, 2);

        roundsCaption.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(roundsCaption, 0, 3);

        sizeCaption.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(sizeCaption, 0, 4);

        playerCaption.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(playerCaption, 0, 5);

        contrastCaption.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(contrastCaption, 0, 6);

        voiceCaption.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(voiceCaption, 0, 7);

        timeValueLabel.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(timeValueLabel, 2, 2);

        roundsValueLabel.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(roundsValueLabel, 2, 3);

        playerLabel.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(playerLabel, 0, 0);

        playerLabel2.setStyle("-fx-text-fill: white;");
        GridPane.setConstraints(playerLabel2, 0, 1);

        GridPane.setConstraints(nameDisplay, 1, 0);
        GridPane.setMargin(nameDisplay, new Insets(10, 10, 10, 10));

        GridPane.setConstraints(nameDisplay2, 1, 1);
        GridPane.setMargin(nameDisplay2, new Insets(5, 10, 10, 10));

        GridPane.setConstraints(timeSlider, 1, 2);
        GridPane.setMargin(timeSlider, new Insets(10, 10, 10, 10));

        GridPane.setConstraints(roundsSlider, 1, 3);
        GridPane.setMargin(roundsSlider, new Insets(10, 10, 10, 10));

        subGridPane.getChildren().addAll(nameDisplay, timeCaption, roundsCaption, sizeCaption, playerCaption,
                timeValueLabel, roundsValueLabel, timeSlider, roundsSlider, SizeButton, PlayerButton, voiceCaption,
                contrastCaption, VoiceButton, ContrastButton, playerLabel);


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

        //Configure leaderboard menu buttons
        //Labels: Leaderboard Menu
        //Buttons: Exit Leaderboard

        ModeLabel4.setMinWidth(250);
        ModeLabel4.setFont(new Font(100));
        ModeLabel4.setStyle("-fx-text-fill: #e8e6e3");

        settingsButton4 = new Button("Exit Leaderboard");
        settingsButton4.setPrefSize(400, 50);
        settingsButton4.setFont(new Font(12));
        settingsButton4.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

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
        TitleBox.setPadding(new Insets(50, 20, 100, 20));
        TitleBox.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(TitleBox, 1, 2);

        //Adds the startButton, loadButton, and settingsButton to a VBox vBox and then sets it in the gridPane.

        VBox vBox = new VBox(20, startButton, loadButton, leaderboardButton, settingsButton);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(vBox, 1, 3);

        //Tracks User inputs in the menu, checks if the user clicks startButton, loadButton, or settingsButton and
        //sends them to the appropriate sub menu.

        startButton.setOnAction(e -> {
            new StartGame(stage, size, timeValue, human, roundsValue, this, voice, name, name2);
            System.out.println(voice);
            gridPane.requestFocus();
        });

        loadButton.setOnAction(e -> {
            LoadMenu();
            gridPane.requestFocus();
        });

        leaderboardButton.setOnAction(e -> {
           LeaderBoardMenu();
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
        TitleBox.setPadding(new Insets(100, 20, 20, 20));
        TitleBox.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(TitleBox, 1, 0);

        //Adds subGridPane which contains timeCaption, roundsCaption, sizeCaption, playerCaption, timeValueLabel,
        // roundsValueLabel, timeSlider, roundsSlider, SizeButton, and PlayerButton and then sets it in the gridPane.

        GridPane.setConstraints(subGridPane, 1, 1);

        //Adds settingsButton2 to a VBox vBox and then sets it in the gridPane.

        VBox vBox = new VBox(20, settingsButton2);
        vBox.setPadding(new Insets(30, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(vBox, 1, 2);

        //Tracks User inputs in the settings menu, checks if the user clicks any sliders or any buttons it alters the
        //text and stores the correct settings.

        subGridPane.getChildren().removeAll(playerLabel2, nameDisplay2);

        if(human == true){
            nameDisplay.setPromptText(name);
            subGridPane.getChildren().addAll(playerLabel2, nameDisplay2);
        }
        else{
            nameDisplay.setPromptText(name);
            nameDisplay2.setPromptText(name2);
            subGridPane.getChildren().removeAll(playerLabel2, nameDisplay2);
        }

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

        nameDisplay.setOnAction(e -> {
            System.out.println("asd");
            name = nameDisplay.getText();
        });
        nameDisplay.textProperty().addListener((observable, oldValue, newValue) -> {
            name = nameDisplay.getText();
        });
        nameDisplay2.textProperty().addListener((observable, oldValue, newValue) -> {
            name2 = nameDisplay2.getText();
        });
        nameDisplay2.setOnAction(e -> {
            name2 = nameDisplay2.getText();
        });

        VoiceButton.setOnAction(e -> {
            if(VoiceButton.getText().equals("On")){
                voice = false;
                VoiceButton.setText("Off");
            }
            else if(VoiceButton.getText().equals("Off")){
                voice = true;
                VoiceButton.setText("On");
            }
            gridPane.requestFocus();
            stage.show();
        });

        ContrastButton.setOnAction(e -> {
            System.out.println("work");
            if(ContrastButton.getText().equals("On")){
                colorContrast = false;
                ContrastButton.setText("Off");
            }
            else if(ContrastButton.getText().equals("Off")){
                colorContrast = true;
                ContrastButton.setText("On");
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
        stage.show();
    }

    /**
     * LoadMenu method. Initalizes the Load Menu with the label and buttons configured for the Load Menu.
     */

    private void LoadMenu() {

        //Sets the title of the stage to "Load Menu" and clears the previous items on the pane for the current one.

        this.stage.setTitle("Load Menu");
        gridPane.getChildren().clear();

        //Adds the startButton, loadButton, and settingsButton3 to a VBox vBox and then sets it in the gridPane.

        HBox TitleBox = new HBox(20, ModeLabel3);
        TitleBox.setPadding(new Insets(200, 20, 20, 20));
        TitleBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(TitleBox, 0, 0);

        VBox vBox = new VBox(20, loadButton2, settingsButton3);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(vBox, 0, 1);

        //Tracks User inputs in the load menu, checks if the user clicks any sliders or any buttons and sends them
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

    private void LeaderBoardMenu() {

        //Sets the title of the stage to "LeaderBoardMenu" and clears the previous items on the pane for the current one.

        stage.setTitle("Leaderboard Menu");
        gridPane.getChildren().clear();

        //Adds the ModeLabel4, listView, to their own HBoxes, settingsButton4 to a VBox vBox and then sets it in the gridPane.

        ListView listView = new ListView();

        System.out.println(listView.getItems());
        for(int i = 0 ; i < listViewItems.size(); i++){
            listView.getItems().add(listViewKeys.get(i) + " : " + listViewItems.get(i));
        }

        listView.setFixedCellSize(20);
        listView.setMaxWidth(1000);
        listView.setStyle("-fx-text-fill: #e8e6e3");

        HBox TitleBox = new HBox(20, ModeLabel4);
        TitleBox.setPadding(new Insets(200, 20, 20, 20));
        TitleBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(TitleBox, 1, 0);

        HBox TitleBox2 = new HBox(100, listView);
        TitleBox2.setPadding(new Insets(20, 20, 20, 20));
        listView.setPrefWidth(600);
        TitleBox2.setAlignment(Pos.CENTER);
        GridPane.setConstraints(TitleBox2, 1, 1);

        VBox vBox = new VBox(20, settingsButton4);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(vBox, 1, 2);

        //Tracks User inputs in the AND checks if the user clicks any sliders or any buttons and sends them
        //to the appropriate sub menu.

        settingsButton4.setOnAction(e -> {
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

        FadeTransition fade3 = new FadeTransition();
        fade3.setDuration(Duration.millis(1000));
        fade3.setFromValue(0);
        fade3.setToValue(10);
        fade3.setNode(TitleBox2);
        fade3.play();

        //Adds the TitleBox and vBox to the gridPane and then shows it on the stage.

        gridPane.getChildren().addAll(TitleBox, TitleBox2, vBox);
        stage.show();
    }

}