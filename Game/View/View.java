package Game.View;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.util.LinkedList;
import java.util.List;
import Game.View.Converter;
import Game.Model.GameController;
import javafx.util.Duration;

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
    public View(Stage stage, GameController model) {
        Current = new TupleInt(-1, -1);
        selectedPoint = new LinkedList<>();

        this.stage = stage;

        this.model = model;
        this.size = model.size;

        this.stage.setTitle("CSC207 Project");

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //create canvas
        canvas = new Canvas(size * blockSize, size * blockSize);
        canvas.setId("Canvas");

        gc = canvas.getGraphicsContext2D();

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent k) {
                model.inputWord(getSelectedPointWord());
                UpdateWordDIsplay();
                selectedPoint.clear();
                redraw();
                wordDisplay.setText("");
            }
        });
        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent k) {
                int loc_x = (int)(k.getX() / blockSize);
                int loc_y = (int)(k.getY() / blockSize);
                if(Current.x != loc_x && Current.y != loc_y){
                    Converter.playSound(model.grid.getStrAt(loc_x, loc_y));
                }
                Current = new TupleInt(loc_x, loc_y);

            }
        });
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent k) {
                int loc_x = (int)(k.getX() / blockSize);
                int loc_y = (int)(k.getY() / blockSize);
                if(Current.x != loc_x && Current.y != loc_y){
                    Converter.playSound(model.grid.getStrAt(loc_x, loc_y));
                }

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
        WordList.getItems().add("Test");

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

    private void UpdateWordDIsplay(){
        WordList.getItems().clear();
        WordList.getItems().addAll(model.wordlist());
    }

    private void UpdateRoundLabel(){
        roundLabel.setText(Integer.toString(model.curRounds) + "/" + Integer.toString(model.rounds));
        PlayerNameLabel.setText(model.getPlayerName());
    }

    private void Timer(){
        time += -1;
        timerLabel.setText(Integer.toString(time));
        System.out.println(time);
        if(time <= 0){
            int state = model.timer();
            if (state == 2){
                timeLine.stop();
                EndGame();
            }
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

    private String getSelectedPointWord(){
        StringBuilder out = new StringBuilder();
        for (TupleInt point: selectedPoint ){
            out.append(model.grid.getStrAt(point.x, point.y));
        }
        return  (out.toString());

    }

    public void startRound(){
        selectedPoint.clear();
        model.playRound();
        time = model.timer;
        UpdateRoundLabel();
        UpdateWordDIsplay();
        timeLine.playFromStart();
        redraw();
    }

    private void showRoundEndMenu(){
        timeLine.stop();
        new roundEndPopup(this);
        System.out.println("test");
    }

    private void EndGame(){

    }

    private int selectedPointContain(int x, int y){
        for(int i = 0; i < selectedPoint.size(); i++){
            if (selectedPoint.get(i).x == x && selectedPoint.get(i).y == y){
                return i;
            }
        }
        return -1;
    }

    private void redraw(){
        int shift = 5;
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, size * blockSize, size * blockSize);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.RED);

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++) {
                if(selectedPointContain(i, j) != -1){
                    gc.setFill(Color.GREEN);
                }
                else {gc.setFill(Color.RED);}
                gc.fillRect((blockSize * i) + shift, (blockSize * j) + shift, blockSize - (2 * shift), blockSize - (2 * shift));
                gc.strokeText(model.grid.getStrAt(i, j), blockSize * i + (blockSize / 2), blockSize * j + (blockSize / 2));

            }
        }
    }

}
