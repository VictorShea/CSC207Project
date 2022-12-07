package Game.View;

import Game.GameMenu;
import Game.Model.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class View {
    Timeline timeLine;
    GameController model;
    Stage stage;
    BorderPane borderPane;
    Canvas canvas;
    TextField wordDisplay;
    GraphicsContext gc; //linked to canvas
    List<TupleInt> selectedPoint;
    Label ScoreLabel;
    Label timerLabel;
    int time;
    int size = 5;
    final int blockSize = 100;
    Label roundLabel;
    Label PlayerNameLabel;
    ListView<String> WordList;
    TupleInt Current;
    GameMenu menu;
    Popup wordDefinition;
    Boolean gameClosed;
    Label wordDefinitionLabel;
    String CurrentWord;
    String ActiveWord;
    Label wordDefinitionTitle;
    Boolean Voice;

    /**
     * Intialize all basic variable and object behaviour
     * @param stage shown
     * @param model data for the game
     * @param menu menu class to reopen when game end
     */
        //intialized all variable
    public View(Stage stage, GameController model, GameMenu menu, Boolean voice, String name, String name2) {
        gameClosed = false;
        this.menu = menu;
        Current = new TupleInt(-1, -1);
        selectedPoint = new LinkedList<>();
        Voice = voice;
        this.stage = stage;

        this.model = model;
        this.size = model.size;
        ActiveWord = "";
        this.stage.setTitle("CSC207 Project");

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        //create canvas
        canvas = new Canvas(size * blockSize, size * blockSize);
        canvas.setId("Canvas");

        gc = canvas.getGraphicsContext2D();


        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent k) {
                //process if user release (word inputted)
                if(model.inputWord(getSelectedPointWord())){
                    redraw(Color.GREEN);
                    PauseTransition pause = new PauseTransition(
                            Duration.seconds(0.4)
                    );
                    pause.setOnFinished(event -> {
                        redraw();
                    });
                    pause.play();
                } else{
                    redraw(Color.RED);
                    PauseTransition pause = new PauseTransition(
                            Duration.seconds(0.4)
                    );
                    pause.setOnFinished(event -> {
                        redraw();
                    });
                    pause.play();
                }
                UpdateWordDIsplay();
                selectedPoint.clear();
                wordDisplay.setText("");
                ScoreLabel.setText("Point: " + model.getPoints());
            }
        });
        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent k) {
                //play sound
                int loc_x = (int)(k.getX() / blockSize);
                int loc_y = (int)(k.getY() / blockSize);
                System.out.println(loc_x + " " + loc_y);
                if(Voice & (Current.x != loc_x || Current.y != loc_y)){
                    System.out.println(model.grid.getStrAt(loc_x, loc_y));
                    Converter.playSound(model.grid.getStrAt(loc_x, loc_y));
                }
                Current = new TupleInt(loc_x, loc_y);

            }
        });

        canvas.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //reset check on play sound if user exit the canvas
                Current = new TupleInt(-1, -1);
            }
        });

        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent k) {
                //add buttons to selected when hold
                int loc_x = (int)(k.getX() / blockSize);
                int loc_y = (int)(k.getY() / blockSize);
                try{
                    model.grid.getStrAt(loc_x, loc_y);
                } catch (Exception e){
                    return;
                }

                if(Voice & (Current.x != loc_x || Current.y != loc_y)){
                    System.out.println(model.grid.getStrAt(loc_x, loc_y));
                    Converter.playSound(model.grid.getStrAt(loc_x, loc_y));
                }
                Current = new TupleInt(loc_x, loc_y);


                if (loc_x < 0 || loc_x >= size || loc_y < 0 || loc_y >= size){
                    return;
                }
                if (k.getX() - (blockSize * loc_x) < 10 || k.getX() - (blockSize * loc_x) > (blockSize - 10)){
                    return;
                }
                if (k.getY() - (blockSize * loc_y) < 10 || k.getY() - (blockSize * loc_y) > (blockSize - 10)){
                    return;
                }
                if (selectedPoint.size() > 0 && (Math.abs(selectedPoint.get(selectedPoint.size() - 1).x - loc_x) > 1
                        || Math.abs(selectedPoint.get(selectedPoint.size() - 1).y - loc_y) > 1)){
                    return;
                }
                int index = selectedPointContain(loc_x, loc_y);
                if(index == -1){
                    selectedPoint.add(new TupleInt(loc_x, loc_y));
                    redraw();
                    wordDisplay.setText(wordDisplay.getText() + model.grid.getStrAt(loc_x, loc_y));
                }
                else if (index != selectedPoint.size() - 1){
                    System.out.println("ads");
                    selectedPoint = selectedPoint.subList(0, index);
                    redraw();
                    wordDisplay.setText(getSelectedPointWord());
                }

            }
        });
        wordDisplay = new TextField();
        BorderPane display = new BorderPane();
        ScoreLabel = new Label("Point: 0");
        ScoreLabel.setFont(new Font(30));
        ScoreLabel.setId("ScoreLabel");
        ScoreLabel.setTextFill(Color.RED);

        timerLabel = new Label(Integer.toString(time));
        timerLabel.setFont(new Font(30));
        timerLabel.setId("TimerLabel");
        timerLabel.setTextFill(Color.RED);

        display.setTop(wordDisplay);
        display.setLeft(ScoreLabel);
        display.setRight(timerLabel);

        BorderPane SecondaryDisplay = new BorderPane();
        roundLabel = new Label();
        PlayerNameLabel = new Label();
        UpdateRoundLabel();
        roundLabel.setFont(new Font(30));
        roundLabel.setTextFill(Color.RED);
        PlayerNameLabel.setFont(new Font(30));
        PlayerNameLabel.setTextFill(Color.RED);

        SecondaryDisplay.setRight(roundLabel);
        SecondaryDisplay.setLeft(PlayerNameLabel);

        VBox MainDisplay = new VBox(SecondaryDisplay, display);

        wordDisplay.setEditable(false);

        WordList =new ListView<>();
        wordDefinition = new Popup();
        wordDefinitionLabel = new Label("asd\nasd\nasd");
        wordDefinitionTitle = new Label("asdsadas");
        wordDefinitionTitle.setFont(new Font(20));
        wordDefinitionTitle.setTextFill(Color.WHITE);
        wordDefinitionTitle.setStyle("-fx-background-color: Black;");

        wordDefinitionLabel.setTextFill(Color.WHITE);
        wordDefinitionLabel.setStyle("-fx-background-color: Black;");
        VBox temp = new VBox(wordDefinitionTitle, wordDefinitionLabel);
        temp.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        temp.setStyle("-fx-border-color: cyan");
        wordDefinition.getContent().add(temp);
        wordDefinition.setAnchorX(100);
        wordDefinition.setAnchorY(100);

        WordList.setCellFactory(lv -> {
            //change cell to now create definition popup when hovered
            ListCell<String> cell = new ListCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            };
            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                if (isNowHovered && ! cell.isEmpty()) {
                    wordDefinition.show(stage);
                    CurrentWord = cell.getText();
                    UpdatePopup();
                } else {
                    wordDefinition.hide();
                }
            });
            cell.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    wordDefinition.setAnchorX(mouseEvent.getScreenX() - wordDefinition.getWidth() - 3);
                    wordDefinition.setAnchorY(mouseEvent.getSceneY() + (wordDefinition.getHeight() / 3));
                }
            });
            return cell;
        });

        WordList.getItems().add("abashed");
        WordList.getItems().add("zombie");
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(canvas);
        mainPane.setTop(MainDisplay);
        mainPane.setPadding(new Insets(10, 20, 10, 20));

        borderPane.setCenter(mainPane);
        borderPane.setRight(WordList);
        Scene scene = new Scene(borderPane, size * blockSize + 200, size * blockSize + 150);
        this.stage.setScene(scene);
        this.stage.show();



        this.model.playRound();
        System.out.println("asd");
        time = model.timer;
        timeLine = new Timeline(new KeyFrame(Duration.seconds(1), e -> Timer()));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();

        redraw();
        
    }

    /**
     * Update popup, move to current mouse location and update content to follow current active word.
     */
    private void UpdatePopup(){
        //update popup
        if(CurrentWord.equals(ActiveWord)){
            return;
        }
        ActiveWord = CurrentWord;
        wordDefinitionTitle.setText(CurrentWord.toUpperCase());
        StringBuilder out = new StringBuilder();
        HashMap<String, List<String>> definition = DefinitionProcess.get_defintion(CurrentWord);
        if (definition == null){
            out = new StringBuilder("Definition not found");
        }
        else{
            for(String type: definition.keySet()){
                out.append("\n").append(type);
                for(String def: definition.get(type)){
                    out.append("\n     ").append(def);
                }
            }
        }
        wordDefinitionLabel.setText(out.substring(1));
    }

    /**
     * update display to match model
     */
    private void UpdateWordDIsplay(){
        WordList.getItems().clear();
        WordList.getItems().addAll(model.wordlist());
    }

    /**
     * update round to match model
     */
    private void UpdateRoundLabel(){
        roundLabel.setText(Integer.toString(model.curRounds) + "/" + Integer.toString(model.rounds));
        PlayerNameLabel.setText(model.getPlayerName());
    }

    /**
     * reduce time by 1 and notify model if timer ran out
     */
    private void Timer(){
        time += -1;
        timerLabel.setText(Integer.toString(time));
        System.out.println(time);
        if(time <= 0){
            int state = model.timer();
            if (state == 2){
                System.out.println("asas,dadkjasdd");
                timeLine.stop();
                showRoundEndMenu();
                EndGameActual();            }
            if (state == 1){
                timeLine.stop();
                showRoundEndMenu();
            }
            else{
                time = model.timer;
            }
            UpdateRoundLabel();
        }
    }

    /**
     * convert the list of selected cells to a word
     * @return word currently selected
     */
    private String getSelectedPointWord(){
        StringBuilder out = new StringBuilder();
        for (TupleInt point: selectedPoint ){
            out.append(model.grid.getStrAt(point.x, point.y));
        }
        return  (out.toString());

    }

    /**
     * start new round
     */
    public void startRound(){
        if(gameClosed){
            return;
        }
        selectedPoint.clear();
        model.playRound();
        time = model.timer;
        UpdateRoundLabel();
        UpdateWordDIsplay();
        timeLine.playFromStart();
        redraw();
    }

    /**
     * end round while showing round end popup
     */
    private void showRoundEndMenu(){
        timeLine.stop();
        new roundEndPopup(this);
        System.out.println("test");
    }
    /**
     * End the game by opening the menu while sending score
     */
    private void EndGameActual(){
        gameClosed = true;
        timeLine.stop();
        HashMap<String, Integer> score = new HashMap<String, Integer>();
        score.put(model.getPlayerName(0), model.getPoints(0));
        if (model.gamemode != GameController.Gamemode.Computer){
            score.put(model.getPlayerName(1), model.getPoints(1));
        }

        menu.openMenu(score);
    }
    /**
     * End the game by opening the menu
     */
    private void EndGame(){
        gameClosed = true;
        timeLine.stop();
        menu.openMenu();
    }

    /**
     * return if selected point contain the current cell
     * @param x axis of the cell
     * @param y axis of the cell
     * @return true iff the cell is selected
     */
    private int selectedPointContain(int x, int y){
        for(int i = 0; i < selectedPoint.size(); i++){
            if (selectedPoint.get(i).x == x && selectedPoint.get(i).y == y){
                return i;
            }
        }
        return -1;
    }

    /**
     * default redraw canvas with the selected point colored yellow
     */
    private void redraw(){
        redraw(Color.YELLOW);
    }

    /**
     * redraw canvas with selected point colored as input
     * @param colorSelected color of selected point
     */
    private void redraw(Color colorSelected){
        int shift = 10;
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, size * blockSize, size * blockSize);
        gc.setTextAlign(TextAlignment.CENTER);

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++) {
                gc.setFill(Color.rgb(192, 223, 161));
                gc.fillRect((blockSize * i) + shift, (blockSize * j) + shift, blockSize - (2 * shift), blockSize - (2 * shift));


                if(selectedPointContain(i, j) != -1){
                    gc.setFill(colorSelected);
                    gc.fillRect((blockSize * i) + shift + 5, (blockSize * j) + shift + 5, blockSize - (2 * shift) - 10, blockSize - (2 * shift) - 10);
                    gc.setStroke(Color.BLACK);
                    gc.strokeText(model.grid.getStrAt(i, j), blockSize * i + (blockSize / 2), blockSize * j + (blockSize / 2));

                }
                else {
                    gc.setFill(Color.BLACK);
                    gc.fillRect((blockSize * i) + shift + 5, (blockSize * j) + shift + 5, blockSize - (2 * shift) - 10, blockSize - (2 * shift) - 10);
                    gc.setStroke(Color.WHITE);
                    gc.strokeText(model.grid.getStrAt(i, j), blockSize * i + (blockSize / 2), blockSize * j + (blockSize / 2));

                }
            }
        }
    }
}
