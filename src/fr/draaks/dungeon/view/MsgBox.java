package fr.draaks.dungeon.view;

import java.io.IOException;

import fr.draaks.dungeon.MainRoot;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MsgBox extends MainRoot {
	
	MainRoot Main;
	@FXML ImageView Loading;
	@FXML Label Titre;
	@FXML Label Message;
	
	public MsgBox(MainRoot Main, String Titre, String Message, boolean Chargement, int duration) throws IOException, InterruptedException {
		this.Main = Main;
		this.Titre.setText(Titre);
		this.Message.setText(Message);
		this.Loading.setVisible(Chargement);
		Main.primaryStage.setOpacity(0.7);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Reconnexion.fxml"));
	    Parent root1 = (Parent) fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.initStyle(StageStyle.UNDECORATED);
	    stage.setTitle("ABC");
	    stage.setScene(new Scene(root1));  
	    stage.show();
	    String path = MainRoot.class.getResource("bib/menu/sounds/alert.mp3").toString();
		Media media = new Media(path);
		MediaPlayer soundClick = new MediaPlayer(media);
		soundClick.play();
		Thread.sleep(duration);
		stage.close();
	}

}
