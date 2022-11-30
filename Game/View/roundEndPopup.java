package Game.View;

import com.sun.scenario.effect.impl.state.HVSeparableKernel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Font;


public class roundEndPopup {
    View view;
    final Stage dialog = new Stage();

    public roundEndPopup(View view)
    {
        String p1_name = view.model.getPlayerName(0);
        int p1_cur_score = view.model.getPoint(0);
        int p1_tot_score = view.model.getTotalScore(0);

        String p2_name = view.model.getPlayerName(1);
        int p2_cur_score = view.model.getPoint(1);
        int p2_tot_score = view.model.getTotalScore(1);

        this.view = view;

        dialog.setWidth(350);
        dialog.setHeight(300);

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(view.stage);
        HBox dialogHbox = new HBox(40);
        dialogHbox.setStyle("-fx-background-color: #121212;");
        Font font = new Font(20);
        VBox p1_display = new VBox();
        Label p1Name= new Label(p1_name);
        p1Name.setFont(font);
        p1Name.setTextFill(Color.WHITE);
        Label p1Score = new Label("Round Score: " + p1_cur_score);
        p1Score.setFont(font);
        p1Score.setTextFill(Color.WHITE);
        Label p1TutScore = new Label("Total Score: " + p1_tot_score);
        p1TutScore.setFont(font);
        p1TutScore.setTextFill(Color.WHITE);

        p1_display.getChildren().addAll(p1Name, p1Score, p1TutScore);

        VBox p2_display = new VBox();
        Label p2Name= new Label(p2_name);
        p2Name.setFont(font);
        p2Name.setTextFill(Color.WHITE);

        Label p2Score = new Label("Round Score: " + p2_cur_score);
        p2Score.setFont(font);
        p2Score.setTextFill(Color.WHITE);

        Label p2TutScore = new Label("Total Score: " + p2_tot_score);
        p2TutScore.setFont(font);
        p2TutScore.setTextFill(Color.WHITE);

        p2_display.getChildren().addAll(p2Name, p2Score, p2TutScore);


        dialogHbox.getChildren().addAll(p1_display, p2_display);

        Button Continue = new Button("Continue");

        BorderPane button_pane = new BorderPane();
        button_pane.setCenter(Continue);

        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(dialogHbox, button_pane);
        vBox.setStyle("-fx-background-color: #121212;");

        Continue.setOnAction(e -> close());


        Scene dialogScene = new Scene(vBox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(e -> close());
    }

    public void close(){
        dialog.close();
        view.startRound();
    }

}
