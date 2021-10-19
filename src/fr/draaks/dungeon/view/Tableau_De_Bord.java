package fr.draaks.dungeon.view;

import java.util.ArrayList;
import java.util.Collections;

import fr.draaks.dungeon.MainRoot;
import fr.draaks.dungeon.model.Donjon;
import fr.draaks.dungeon.model.Items;
import fr.draaks.dungeon.model.Personnage;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Tableau_De_Bord extends MainRoot {
	
	MainRoot Main;
	Personnage Joueur;
	@FXML Label Nom_Joueur;
	@FXML Label Niveau_Joueur;
	@FXML Label XP_Joueur;
	@FXML ImageView Joueur_Image;
	@FXML ImageView Action1;
	@FXML ImageView Action2;
	@FXML ImageView Action3;
	@FXML ImageView Action4;
	@FXML ImageView Action5;
	@FXML ImageView Action6;
	@FXML ImageView Action7;
	@FXML ImageView Action8;
	@FXML ImageView Action9;
	@FXML ImageView Action10;
	@FXML ImageView Item1;
	@FXML ImageView Item2;
	@FXML ImageView Item3;
	@FXML ImageView Item4;
	@FXML ImageView Item5;
	@FXML ImageView Item6;
	@FXML ImageView Item7;
	@FXML ImageView Item8;
	@FXML ImageView Item9;
	@FXML ImageView Item10;
	@FXML ImageView Item11;
	@FXML ImageView Item12;
	@FXML ImageView Item13;
	@FXML ImageView Item14;
	@FXML ImageView Item15;
	@FXML ImageView Item16;
	@FXML Label PotionO_qty;
	@FXML Label PotionR_qty;
	@FXML Label PotionV_qty;
	@FXML ImageView PotionR;
	@FXML ImageView PotionO;
	@FXML ImageView PotionV;
	@FXML Label PV_Joueur;
	@FXML Label PA_Joueur;
	@FXML ImageView Terminer;
	String type;
	ImageView[] Actions = new ImageView[10];
	Stage stage;
	GraphicsContext gc;
	Donjon dungeon;
	
	@SuppressWarnings("deprecation")
	public void initialiser() {
		Main.playBruitage("bib/menu/sounds/Inventaire_sound.mp3");
		this.Nom_Joueur.setText(this.Joueur.getName());
		this.Niveau_Joueur.setText(String.valueOf(this.Joueur.getLvl()));
		this.XP_Joueur.setText(String.valueOf(this.Joueur.getXp()));
		this.Joueur_Image.setImage(new Image(this.Joueur.getImg().impl_getUrl(), 300, 300, true, false));
		this.PA_Joueur.setText(String.valueOf(this.Joueur.getMana()));
		this.PV_Joueur.setText(String.valueOf(this.Joueur.getVie()));
		this.type = this.Joueur.getClass().getSimpleName();
		Tooltip.install(this.Terminer, Main.popup("Quitter la partie"));
		this.initialiserActions();
		int size = this.XP_Joueur.getText().length();
        this.XP_Joueur.setStyle("-fx-font-size: " + String.valueOf(68-(size*4)) + "px; -fx-font-weight:bold;");
		this.PotionR.setDisable(true);
		this.PotionV.setDisable(true);
		this.PotionV.setDisable(true);
	}
	
	public void setJoueur(Personnage Joueur) {
		this.Joueur = Joueur;
	}
	
	public void setParentController(MainRoot parentController) {
	    this.Main = parentController;
	}
	
	public void initialiserActions() { // on va définir ici l'ensemble des actions possibles pour le joueur
		String[] infosActions = new String[10];
		Actions[0] = this.Action1;
		Actions[1] = this.Action2;
		Actions[2] = this.Action3;
		Actions[3] = this.Action4;
		Actions[4] = this.Action5;
		Actions[5] = this.Action6;
		Actions[6] = this.Action7;
		Actions[7] = this.Action8;
		Actions[8] = this.Action9;
		Actions[9] = this.Action10;
		infosActions[0] = "Lance une attaque de corps à corps contre votre adversaire."
				+ "\n\nEffets sur vous :"
				+ "\n\tPA : -2"
				+ "\n\nEffets sur votre adversaire :"
				+ "\n\tPV : entre -1 et -9";
		switch (this.type) {
			case "Mage":
				infosActions[1] = "Lance un sort à votre adversaire."
						+ "\nCette attaque se lance à distance"
						+ "\n\nEffets sur vous :"
						+ "\n\tPA : -3"
						+ "\n\nEffets sur votre adversaire :"
						+ "\n\tPV : entre -5 et -15";
				infosActions[2] = "Jette une fiole empoisonnée, préparée par vos soins à votre adversaire."
						+ "\nCette attaque se lance à distance"
						+ "\n\nEffets sur vous :"
						+ "\n\tPA : -4"
						+ "\n\nEffets sur votre adversaire :"
						+ "\n\tPV : entre -9 et -16";
				infosActions[3] = "Lance une Fireball à votre adversaire."
						+ "\nCette attaque se lance à distance"
						+ "\n\nEffets sur vous :"
						+ "\n\tPA : -5"
						+ "\n\nEffets sur votre adversaire :"
						+ "\n\tPV : entre -10 et -20";
				break;
			case "Archer":
				infosActions[1] = "Lance une flèche simple à votre adversaire."
						+ "\nCette attaque se lance à distance"
						+ "\n\nEffets sur vous :"
						+ "\n\tPA : -3"
						+ "\n\nEffets sur votre adversaire :"
						+ "\n\tPV : entre -5 et -15";
				infosActions[2] = "Lance une flèche taillée dans la pierre à votre adversaire."
						+ "\nCette attaque se lance à distance"
						+ "\n\nEffets sur vous :"
						+ "\n\tPA : -4"
						+ "\n\nEffets sur votre adversaire :"
						+ "\n\tPV : entre -9 et -16";
				infosActions[3] = "Lance une flèche empoisonée affectant le coeur de votre adversaire."
						+ "\nCette attaque se lance à distance"
						+ "\n\nEffets sur vous :"
						+ "\n\tPA : -5"
						+ "\n\nEffets sur votre adversaire :"
						+ "\n\tPV : entre -10 et -20";
				break;
			case "Guerrier":
				infosActions[1] = "Lance un couteau à votre adversaire."
						+ "\nCette attaque se lance à distance"
						+ "\n\nEffets sur vous :"
						+ "\n\tPA : -3"
						+ "\n\nEffets sur votre adversaire :"
						+ "\n\tPV : entre -5 et -15";
				infosActions[2] = "Donne des coups de sabre dirigés par votre esprit à votre adversaire."
						+ "\nCette attaque se lance à distance"
						+ "\n\nEffets sur vous :"
						+ "\n\tPA : -4"
						+ "\n\nEffets sur votre adversaire :"
						+ "\n\tPV : entre -9 et -16";
				infosActions[3] = "Lance une épée enflammée vers votre adversaire."
						+ "\nCette attaque se lance à distance"
						+ "\n\nEffets sur vous :"
						+ "\n\tPA : -5"
						+ "\n\nEffets sur votre adversaire :"
						+ "\n\tPV : entre -10 et -20";
				break;
			default:
				break;
		}
		for (int i=1; i<5; i++) { // on va configurer les Tooltips, qui permettent d'afficher les infos d'une attaque à notre joueur
			Actions[i-1].setImage(new Image(Main.getClass().getResourceAsStream("bib/combat/" + this.type + "/Action" + String.valueOf(i) + ".png")));
			Tooltip.install(Actions[i-1], Main.popupImage(infosActions[i-1], "bib/combat/" + this.type + "/Action" + String.valueOf(i) + ".png"));
		}
		ArrayList<Items> items = this.Joueur.getInventaire();
		ArrayList<String> recherche = new ArrayList<String>();
		for (int i=0; i<items.size(); i++) {
			for (int j=0; j<items.get(i).getQuantity(); j++) {
				recherche.add(items.get(i).getName());
			}
		}
		this.PotionR_qty.setText(String.valueOf(Collections.frequency(recherche, "Potion'R")));
		this.PotionO_qty.setText(String.valueOf(Collections.frequency(recherche, "Potion'O")));
		this.PotionV_qty.setText(String.valueOf(Collections.frequency(recherche, "Potion'V")));
	}
	
	public void btnTerminer() throws Exception {
		Main.buttonClick();
		if (this.Joueur.isInDungeon()) {
			LancerAventure lancer = new LancerAventure();
			lancer.setParentController(Main);
			lancer.setStage(Main.primaryStage);
			lancer.runVillage(this.Joueur, this.gc);
		} else {
			Main.backgroundMusicInit("bib/menu/sounds/menu_sound.mp3");
			Menu menu = (Menu) Main.changerFenetre("view/Menu.fxml", new Menu());
			menu.setParentController(Main);
		}
	}
	
	public void btnContinuer() {
		Main.buttonClick();
		if (this.Joueur.isInDungeon()) {
			LancerAventure lancer = new LancerAventure();
			lancer.setParentController(Main);
			lancer.setStage(Main.primaryStage);
			lancer.runDungeon(this.dungeon, this.Joueur, this.gc);
		} else {
			LancerAventure lancer = new LancerAventure();
			lancer.setParentController(Main);
			lancer.setStage(Main.primaryStage);
			lancer.runVillage(this.Joueur, this.gc);
		}
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}

	public Personnage getJoueur() {
		return Joueur;
	}

	public Donjon getDungeon() {
		return dungeon;
	}

	public void setDungeon(Donjon dungeon) {
		this.dungeon = dungeon;
	}

}
