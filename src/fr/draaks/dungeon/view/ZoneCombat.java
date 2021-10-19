// Je n'ai pas commenté toutes les animations présentes dans ce fichier
// Les animations font le charme de notre jeu, nous utilisons les librairies :
// FadeTransition, TranslateTransition et RotateTransition
// ATTENTION : cette classe est utilisé pour le Mode Multijoueur en ligne ET le Mode Histoire en local

package fr.draaks.dungeon.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import fr.draaks.dungeon.MainRoot;
import fr.draaks.dungeon.model.Donjon;
import fr.draaks.dungeon.model.Entity;
import fr.draaks.dungeon.model.Items;
import fr.draaks.dungeon.model.Personnage;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ZoneCombat extends MainRoot {
	
	private MainRoot Main;
	private Personnage Entity_Joueur;
	private Entity Entity_Ennemi;
	private String type;
	private String typeEnnemi;
	private Integer PA_Origine_Joueur;
	private Integer PA_Origine_Ennemi;
	private Integer PV_Origine_Joueur;
	private Integer PV_Origine_Ennemi;
	private String attaquant;
    String actionJ1 = "";
    String actionJ2 = "";
    String dateJ1 = "";
    String dateJ2 = "";
    boolean lancer=true;
    boolean online=false;
	String monReady;
	ResultSet resultat = null;
	ResultSet resultat2 = null;
	ArrayList<String> ActionsJ = new ArrayList<String>();
	ArrayList<String> ActionsE = new ArrayList<String>();
	ImageView[] Actions = new ImageView[10];
	FadeTransition heart_transition;
	TranslateTransition arrow;
	MediaPlayer heart;
	String Salle;
	String identifiant;
	String PVJ1;
	String PVJ2;
	int PVPerte;
	boolean actions_desactive=false;
	boolean passerTourStatut;
	boolean attaque;
	Stage stage;
	@FXML Label Titre;
	@FXML ImageView Terminer;
	@FXML ImageView Joueur;
	@FXML ImageView Ennemi;
	@FXML ImageView Pierre_Joueur;
	@FXML ImageView Pierre_Ennemi;
	@FXML Button Attaque1;
	@FXML Label PV_Ennemi;
	@FXML Label PV_Joueur;
	@FXML ImageView PVH_Joueur;
	@FXML ImageView PVH_Ennemi;
	@FXML Label PA_Ennemi;
	@FXML Label PA_Joueur;
	@FXML TextArea Evenements;
	@FXML ImageView PAI_Joueur;
	@FXML ImageView PAI_Ennemi;
	@FXML ImageView Item_Utilise;
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
	@FXML ImageView Passer;
	@FXML ImageView PotionR;
	@FXML ImageView PotionO;
	@FXML ImageView PotionV;
	@FXML ImageView Cercle;
	@FXML Label XP_obtenus;
	@FXML Label XP_string;
	@FXML Label PotionO_qty;
	@FXML Label PotionR_qty;
	@FXML Label PotionV_qty;
	Scene scene;
	Donjon dungeon;
	GraphicsContext gc;
	
	public ZoneCombat() {}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setDonjonGC(Donjon dungeon, GraphicsContext gc) {
		this.dungeon = dungeon;
		this.gc = gc;
	}
	
	// on donne l'objet de la fenêtre parente pour conserver le contrôle
	public void setParentController(MainRoot parentController) {
	    this.Main = parentController;
	}
	
	// on va initialiser ici le mode en ligne, les potions ne sont pas utilisables
	public void initialiserOnline(Entity Joueur1, Entity Joueur2, String image_J1, String image_J2) {
		this.Joueur.setImage(new Image(Main.getClass().getResourceAsStream(image_J1), 300, 300, true, false));
		this.Ennemi.setImage(new Image(Main.getClass().getResourceAsStream(image_J2), 300, 300, true, false));
		this.Ennemi.setScaleX(-1);
		this.Terminer.setOpacity(0.7);
		this.passerTourStatut = false;
		this.PVPerte = 0;
		this.attaque=false;
		this.Entity_Joueur = (Personnage) Joueur1;
		this.Entity_Ennemi = Joueur2;
		this.PA_Origine_Ennemi = this.Entity_Ennemi.getMana();
		this.PA_Origine_Joueur = this.Entity_Joueur.getMana();
		this.PV_Origine_Ennemi = this.Entity_Ennemi.getVie();
		this.PV_Origine_Joueur = this.Entity_Joueur.getVie();
		this.connecte.restart();
		this.connecte.setOnSucceeded(e -> {
			this.verifierTour.restart();
		});
		ArrayList<Items> inventaire = new ArrayList<Items>();
		inventaire.add(new Items(0, 0, "Potion'V", 0, 0, null));
		inventaire.add(new Items(0, 0, "Potion'R", 0, 0, null));
		inventaire.add(new Items(0, 0, "Potion'O", 0, 0, null));
		Tooltip.install(Terminer, Main.popup("Abandonner le combat"));
		Tooltip.install(Passer, Main.popup("Terminer mon tour"));
		Tooltip.install(PotionR, Main.popup("Potion non utilisable lors d'un défi"));
		Tooltip.install(PotionO, Main.popup("Potion non utilisable lors d'un défi"));
		Tooltip.install(PotionV, Main.popup("Potion non utilisable lors d'un défi"));
		this.Item_Utilise.setVisible(false);
		this.animationArrivee();
		this.actualiserInfos();
		this.Entity_Joueur.setInventaire(inventaire);
		this.type = Joueur1.getClass().getSimpleName();
		this.typeEnnemi = Joueur2.getClass().getSimpleName();
		this.initialiserActions();
		this.attaquant = "online";
		this.status("Le combat vient de commencer.\n");
		this.PA_Ennemi.setVisible(false);
		this.PAI_Ennemi.setVisible(false);
	}
	
	@SuppressWarnings("deprecation")
	// Méthode en expérimentation dans Java8 mais fonctionnel pour nous, on l'utilise ici à titre exceptionnel
	// La méthode a vu le jour dans Java11, on a donc conscience de ce que l'on utilise
	// on initialise ici un combat Joueur<>Mob
	public void initialiser(Entity Joueur, Entity Ennemi) {
		this.Joueur.setImage(new Image(Joueur.getImg().impl_getUrl(), 300, 300, true, false));
		this.Ennemi.setImage(new Image(Ennemi.getImg().impl_getUrl(), 300, 300, true, false));
		this.Ennemi.setScaleX(-1);
		this.Terminer.setOpacity(0.7);
		Tooltip.install(Terminer, Main.popup("Abandonner le combat"));
		Tooltip.install(Passer, Main.popup("Terminer mon tour"));
		Tooltip.install(PotionR, Main.popup("Utiliser une Potion'R"));
		Tooltip.install(PotionO, Main.popup("Utiliser une Potion'O"));
		Tooltip.install(PotionV, Main.popup("Utiliser une Potion'V"));
		this.Item_Utilise.setVisible(false);
		this.animationArrivee();
		this.Entity_Joueur = (Personnage) Joueur;
		this.Entity_Ennemi = Ennemi;
		this.PA_Origine_Ennemi = this.Entity_Ennemi.getMana();
		this.PA_Origine_Joueur = this.Entity_Joueur.getMana();
		this.PV_Origine_Ennemi = this.Entity_Ennemi.getVie();
		this.PV_Origine_Joueur = this.Entity_Joueur.getVie();
		this.actualiserInfos();
		this.type = Joueur.getClass().getSimpleName();
		this.typeEnnemi = Ennemi.getClass().getSimpleName();
		this.initialiserActions();
		this.status("Le combat vient de commencer.\n");
		this.actionDisabled(true);
	}
	
	// on va définir ici les actions de nos 3 personnages
	public void initialiserActions() {
		this.Item_Utilise.setTranslateX(0);
		this.Item_Utilise.setTranslateY(0);
		this.Item_Utilise.setTranslateZ(0);
		this.Item_Utilise.setRotate(0);
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
		for (int i=1; i<5; i++) {
			Actions[i-1].setImage(new Image(Main.getClass().getResourceAsStream("bib/combat/" + this.type + "/Action" + String.valueOf(i) + ".png")));
			Tooltip.install(Actions[i-1], Main.popupImage(infosActions[i-1], "bib/combat/" + this.type + "/Action" + String.valueOf(i) + ".png"));
		}
	}
	
	// cette méthode s'exécute si le joueur a perdu
	public void perdu() {
		this.Titre.setText(this.Entity_Ennemi.getName() + " vous a achevé.");
		Main.playBruitage("bib/combat/sounds/Perdu_sound.mp3");
		TranslateTransition translate = new TranslateTransition();   
        translate.setByX(-350);  
        translate.setByY(50);
        translate.setDuration(Duration.millis(750));  
        translate.setCycleCount(1); 
        translate.setNode(this.Pierre_Joueur);  
        translate.play();
        TranslateTransition translate2 = new TranslateTransition();   
        translate2.setByX(-350);  
        translate2.setByY(50);
        translate2.setDuration(Duration.millis(750));  
        translate2.setCycleCount(1); 
        translate2.setNode(this.Joueur);  
        translate2.play();
		TranslateTransition translate3 = new TranslateTransition();   
        translate3.setByX(-350);  
        translate3.setByY(50);
        translate3.setDuration(Duration.millis(2000));  
        translate3.setCycleCount(1); 
        translate3.setNode(this.Pierre_Ennemi);  
        translate3.play();
        TranslateTransition translate4 = new TranslateTransition();   
        translate4.setByX(-350);  
        translate4.setByY(50);
        translate4.setDuration(Duration.millis(2000));  
        translate4.setCycleCount(1); 
        translate4.setNode(this.Ennemi);  
        translate4.play();
	}
	
	// si le joueur a gagné la partie
	public void victoire() {
		this.Titre.setText("Vous avez vaincu " + this.Entity_Ennemi.getName() + " !");
		Main.backgroundMusicInit("bib/menu/sounds/menu_sound.mp3");
		TranslateTransition translate = new TranslateTransition();   
        translate.setByX(350);  
        translate.setByY(-50);
        translate.setDuration(Duration.millis(1250));  
        translate.setCycleCount(1); 
        translate.setNode(this.Pierre_Ennemi);  
        translate.play();
        TranslateTransition translate2 = new TranslateTransition();   
        translate2.setByX(350);  
        translate2.setByY(-50);
        translate2.setDuration(Duration.millis(1250));  
        translate2.setCycleCount(1); 
        translate2.setNode(this.Ennemi);  
        translate2.play();
		TranslateTransition translate3 = new TranslateTransition();   
        translate3.setByX(350);  
        translate3.setByY(-50);
        translate3.setDuration(Duration.millis(2500));  
        translate3.setCycleCount(1); 
        translate3.setNode(this.Pierre_Joueur);  
        translate3.play();
        TranslateTransition translate4 = new TranslateTransition();   
        translate4.setByX(350);  
        translate4.setByY(-50);
        translate4.setDuration(Duration.millis(2500));  
        translate4.setCycleCount(1); 
        translate4.setNode(this.Joueur);  
        translate4.play();
        translate2.setOnFinished(event -> {
        	Main.playBruitage("bib/combat/sounds/Victoire_sound.mp3");
        	this.Cercle.setVisible(true);
        	this.XP_obtenus.setVisible(true);
        	this.XP_obtenus.setOpacity(0);
        	RotateTransition rotate = new RotateTransition();
            rotate.setAxis(Rotate.Z_AXIS);
            rotate.setByAngle(720);
            rotate.setDuration(Duration.millis(3000));
            rotate.setAutoReverse(true);
            rotate.setNode(this.Cercle);
            rotate.play();
        	FadeTransition fondu = new FadeTransition(Duration.millis(2000), this.XP_obtenus);
			fondu.setFromValue(0);
			fondu.setToValue(1.0);
			fondu.play();
			fondu.setOnFinished(e -> {
				// on redéfinit les XP du joueur
				int XP = (int) (this.PV_Origine_Ennemi+(this.PV_Origine_Joueur-this.Entity_Joueur.getVie()));
				this.XP_string.setText(String.valueOf(XP));
				if (!online) {
					this.Entity_Joueur.addXp(XP);
				}
				this.XP_string.setVisible(true);
				this.XP_string.setOpacity(0);
				FadeTransition fond = new FadeTransition(Duration.millis(1000), this.XP_string);
				fond.setFromValue(0);
				fond.setToValue(1.0);
				fond.play();
				// supprimer le mob dans le donjon
			});
        });
	}
	
	// on va à chaque fois, vérifier que les conditions pour la fin de la partie n'est pas atteinte
	public boolean verifierFin() {
		this.actualiserInfos();
		boolean fin=false;
		if ((this.Entity_Joueur.getVie()<=0)||(this.Entity_Ennemi.getVie()<=0)) {
			if (this.heart!=null) {
				this.heart_transition.stop();
				this.heart.stop();	
			}
			this.PV_Joueur.setVisible(false);
			this.PVH_Joueur.setVisible(false);
			this.PV_Ennemi.setVisible(false);
			this.PVH_Ennemi.setVisible(false);
			this.Item_Utilise.setDisable(true);
			this.Item_Utilise.setVisible(false);
			this.PA_Joueur.setVisible(false);
			this.PAI_Joueur.setVisible(false);
			this.PA_Ennemi.setVisible(false);
			this.PAI_Ennemi.setVisible(false);
			this.PotionO.setDisable(true);
			this.PotionR.setDisable(true);
			this.PotionV.setDisable(true);
			this.actionDisabled(true);
			this.Terminer.getStyleClass().clear();
			this.Terminer.getStyleClass().add("hand");
			this.Terminer.setImage(new Image(Main.getClass().getResourceAsStream("bib/general/terminer.png")));
			FadeTransition fondu = new FadeTransition(Duration.millis(2000), this.Terminer);
			fondu.setFromValue(0.1);
			fondu.setToValue(1.0);
			fondu.setCycleCount(Timeline.INDEFINITE);
			fondu.setAutoReverse(true);
			fondu.play();
			fin=true;
			if (this.Entity_Joueur.getVie()<=0) {
				// Notre joueur a perdu son combat
				this.Entity_Joueur.setVie(0);
				this.status(this.Entity_Ennemi.getName() + " a remporté le combat.");
				Tooltip.install(Terminer, Main.popup("Retourner au donjon"));
				this.perdu();
				Timeline timeline = new Timeline(
		        	    new KeyFrame(Duration.seconds(4),
		        	        new KeyValue(Main.music_background.volumeProperty(), 0.05)));
		        timeline.play();
			} else {
				// Notre joueur a gagné son combat
				this.Entity_Ennemi.setVie(0);
				this.status(this.Entity_Joueur.getName() + " a remporté le combat.");
				Tooltip.install(Terminer, Main.popup("Récupérer mes bonus"));
				this.victoire();
			}	
		}
		return fin;
	}
	
	// on désactive/active la possibilité au joueur de lancer une attaque
	public void actionDisabled(boolean value) {
		for (int i=1; i<11; i++) {
			Actions[i-1].setDisable(value);
			if (value) {
				Actions[i-1].setOpacity(0.2);
				this.Passer.setOpacity(0.2);
			} else {
				Actions[i-1].setOpacity(1);
				this.Passer.setOpacity(0.7);
			}
		}
		this.Passer.setDisable(value);
		this.PotionR.setDisable(value);
		this.PotionO.setDisable(value);
		this.PotionV.setDisable(value);
		if (value) {
			this.PotionR.setOpacity(0.2);
			this.PotionO.setOpacity(0.2);
			this.PotionV.setOpacity(0.2);
		} else {
			this.PotionR.setOpacity(1);
			this.PotionO.setOpacity(1);
			this.PotionV.setOpacity(1);
		}
 		if (this.Entity_Joueur.getMana()<2) {
			this.Action1.setDisable(true);
			this.Action1.setOpacity(0.2);
		}
		if (this.Entity_Joueur.getMana()<3) {
			this.Action2.setDisable(true);
			this.Action2.setOpacity(0.2);
		}
		if (this.Entity_Joueur.getMana()<4) {
			this.Action3.setDisable(true);
			this.Action3.setOpacity(0.2);
		}
		if (this.Entity_Joueur.getMana()<5) {
			this.Action4.setDisable(true);
			this.Action4.setOpacity(0.2);
		}
		if (!value) {
			this.verifierPotions();
		}
		if (!this.PV_Ennemi.isVisible()) {
			this.Passer.setVisible(false);
			this.PotionO.setVisible(false);
			this.PotionR.setVisible(false);
			this.PotionV.setVisible(false);
			this.PotionO_qty.setVisible(false);
			this.PotionR_qty.setVisible(false);
			this.PotionV_qty.setVisible(false);
		}
		actions_desactive=value;
	}
	
	// on laisse une trace écrite de ce qui se passe dans le combat aux deux joueurs
	public void status(String text) {
		Date maintenant = new Date();
	    DateFormat format_fr = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		this.Evenements.setText(this.Evenements.getText() + "\n[" + format_fr.format(maintenant) + "]\n" + text + "\n");
		this.Evenements.selectPositionCaret(this.Evenements.getLength());
		this.Evenements.deselect();
	}
	
	// L'animation d'arrivée des deux adversaires
	public void animationArrivee() {
		this.Pierre_Ennemi.setTranslateX(350);
		this.Pierre_Ennemi.setTranslateY(-200);
		TranslateTransition translate = new TranslateTransition();   
        translate.setByX(-350);  
        translate.setByY(200);
        translate.setDuration(Duration.millis(1500));  
        translate.setCycleCount(1); 
        translate.setNode(this.Pierre_Ennemi);  
        translate.play();
        this.Ennemi.setTranslateX(350);
        this.Ennemi.setTranslateY(-200);
		TranslateTransition translate2 = new TranslateTransition();   
        translate2.setByX(-350);  
        translate2.setByY(200);
        translate2.setDuration(Duration.millis(1500));  
        translate2.setCycleCount(1); 
        translate2.setNode(this.Ennemi);  
        translate2.play();
        this.Pierre_Joueur.setTranslateX(-350);
		this.Pierre_Joueur.setTranslateY(200);
		TranslateTransition translate3 = new TranslateTransition();   
        translate3.setByX(350);  
        translate3.setByY(-200);
        translate3.setDuration(Duration.millis(1500));  
        translate3.setCycleCount(1); 
        translate3.setNode(this.Pierre_Joueur);  
        translate3.play();
        this.Joueur.setTranslateX(-350);
        this.Joueur.setTranslateY(200);
		TranslateTransition translate4 = new TranslateTransition();   
        translate4.setByX(350);  
        translate4.setByY(-200);
        translate4.setDuration(Duration.millis(1500));  
        translate4.setCycleCount(1); 
        translate4.setNode(this.Joueur);  
        translate4.play();
        translate4.setOnFinished(event -> {
        	this.verifierPotions();
        	this.actionDisabled(false);
        });
	}
	
	// une flèche qui bouge pour indiquer au joueur qu'il a terminé son tour
	public void animationTerminerTour() {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream("bib/general/arrow.png")));
		this.Item_Utilise.setVisible(true);
		this.Item_Utilise.setRotate(0);
		this.Item_Utilise.setTranslateX(100);
		this.Item_Utilise.setTranslateY(220);
        this.arrow = new TranslateTransition();
        this.arrow.setByX(-20);
        this.arrow.setByY(-20);
        this.arrow.setDuration(Duration.millis(1500));
        this.arrow.setAutoReverse(true);
        this.arrow.setCycleCount(Timeline.INDEFINITE);
        this.arrow.setNode(this.Item_Utilise);
        this.arrow.play();
        this.Passer.setDisable(false);
	}
	
	// ici le joueur donne la main à son adversaire
	public void passerTour() throws SQLException {
		// on sépare la version En ligne de la version Locale
		if (!online) {
			this.verifierPotions();
			if (this.arrow!=null) {
				this.arrow.stop();
				this.Item_Utilise.setVisible(false);
				this.Item_Utilise.setTranslateX(0);
				this.Item_Utilise.setTranslateY(0);
			}
			this.actionDisabled(true);
			this.tourEnnemi();
			this.Entity_Joueur.setMana(this.PA_Origine_Joueur);
			this.actualiserInfos();
			this.ActionsJ.clear();
		} else {
			this.verifierPotions();
			if (this.arrow!=null) {
				this.arrow.stop();
				this.Item_Utilise.setVisible(false);
				this.Item_Utilise.setTranslateX(0);
				this.Item_Utilise.setTranslateY(0);
			}
			this.actionDisabled(true);
			this.Entity_Joueur.setMana(this.PA_Origine_Joueur);
			this.actualiserInfos();
			this.ActionsJ.clear();
			this.passerTourStatut=true;
		}
	}
	
	// si c'est le tour d'un Mob qui doit jouer de façon automatique
	public void tourEnnemi() {
		this.ActionsE.clear();
		this.status("C'est au tour de " + this.Entity_Ennemi.getName() + ".");
		this.attaquant = "auto";
		this.attaqueEnnemi();
		this.Entity_Ennemi.setMana(this.PA_Origine_Ennemi);
		this.actualiserInfos();
	}

	public Label getTitre() {
		return Titre;
	}

	public void setTitre(String string) {
		this.Titre.setText(string);
	}
	
	/* LA MAJORITE DES FONCTIONS QUI SUIVENT SONT LES MÊMES POUR LE JOUEUR
	 * ET POUR SON ENNEMI, NOUS CHANGEONS JUSTE PARFOIS CERTAIN DETAILS TELS QUE
	 * LES ANIMATIONS, OU ENCORE A QUEL ENTITE APPLIQUE UNE METHODE.
	 * 
	 * ON COMMENTERA DONC UNIQUEMENT LES METHODES DU JOUEUR, ET SI NECESSAIRE
	 * DES EXPLICATIONS SERONT DONNEES POUR L'ADVERSAIRE.
	 */
	
	// on vérifie les PA du joueur
	public void verifyManaJoueur() {
		boolean fin = this.verifierFin();
		// on vérifie la condition de fin
		if (!fin) {
			if (this.Entity_Joueur.getMana()<=1) {
				// s'il a plus de PA on ne laisse plus utiliser ses attaques
				this.verifierPotions();
				this.actionDisabled(true);
				this.Passer.setDisable(false);
				// on lui invite à passer son tour
				this.animationTerminerTour();
			} else {
				this.verifierPotions();
				this.actionDisabled(false);
				// on sauvegarde l'action qu'il vient de faire
				// une action peut s'exécuter que 2 fois dans un même tour
				for (int i=1; i<=10; i++) {
					int nb_action = Collections.frequency(this.ActionsJ, "A" + String.valueOf(i));
					if (nb_action==2) {
						Actions[i-1].setDisable(true);
						Actions[i-1].setOpacity(0.2);
					}
				}
			}
		}
	}
	
	public void verifyManaEnnemi() {
		boolean fin = this.verifierFin();
		if (!fin) {
			if ((this.Entity_Ennemi.getMana()>1)&&!fin) {
				this.attaqueEnnemi();
			} else {
				if (!online) {
					this.Entity_Ennemi.setMana(this.PA_Origine_Ennemi);
					this.actionDisabled(false);
				}
				this.actualiserInfos();
			}
		}
	}
	
	// bouton pour quitter la partie, soit en fin de partie, soit au milieu
	public void btnTerminer() throws Exception {
		Main.buttonClick();
		this.Entity_Joueur.setMana(this.PA_Origine_Joueur);
		if (this.heart!=null) {
			this.heart.stop();
		}
		if (online) {
			Main.backgroundMusicInit("bib/menu/sounds/menu_sound.mp3");
			DraaksOnline online = (DraaksOnline) Main.changerFenetre("view/DraaksOnline.fxml", new DraaksOnline());
			online.setParentController(Main);
			online.setIdentifiant(this.identifiant);
			online.initialiser();
		} else {
//			Menu menu = (Menu) Main.changerFenetre("view/Menu.fxml", new Menu());
//			menu.setParentController(Main);
//			Main.backgroundMusicInit("bib/menu/sounds/menu_sound.mp3");
			if (this.Entity_Ennemi.getVie()<=0) {
				this.dungeon.clearMobs();
				LancerAventure lancer = new LancerAventure();
				lancer.setParentController(Main);
				lancer.setStage(Main.primaryStage);
				lancer.runDungeon(this.dungeon, this.Entity_Joueur, this.gc);
			} else {
				this.Entity_Joueur.setVie(1);
				LancerAventure lancer = new LancerAventure();
				lancer.setParentController(Main);
				lancer.setStage(Main.primaryStage);
				lancer.runVillage(this.Entity_Joueur, this.gc);
			}
		}
	}
	
	public void animationDegatEnnemi() {
		Main.playBruitage("bib/general/sounds/attaque_sound.mp3");
		Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(this.Ennemi.translateXProperty(), -10)),
            new KeyFrame(new Duration(80), new KeyValue(this.Ennemi.translateXProperty(), 10))
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(10);
        timeline.play();
        timeline.setOnFinished(event -> {
        	this.actionDisabled(false);
        	this.verifyManaJoueur();
        });
	}
	
	// L'animation de dégat du joueur, bruitages + une transition de son X pour un style d'attaque
	public void animationDegatJoueur() {
		Main.playBruitage("bib/general/sounds/attaque_sound.mp3");
		Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(this.Joueur.translateXProperty(), -10)),
            new KeyFrame(new Duration(80), new KeyValue(this.Joueur.translateXProperty(), 10))
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(10);
        timeline.play();
        timeline.setOnFinished(event -> 
								        { 
									        if (this.attaquant.equals("auto")) {
									        	// si c'est un mob qui l'attaque
									        	// on invite au mob à rejouer
									        	this.verifyManaEnnemi();
									        } else {
									        	boolean fin = this.verifierFin();
									    		if (!fin) {
									    			if ((this.Entity_Ennemi.getMana()>1)&&!fin) {
									    				
									    			} else {
									    				this.Entity_Ennemi.setMana(this.PA_Origine_Ennemi);
									    				this.actualiserInfos();
									    				this.actionDisabled(false);
									    			}
									    		}
									        }
				        		});
        
	}
	
	// on va vérifier le nombre de potions encore utilisable au cours de cette partie
	public void verifierPotions() {
		this.PotionR.setDisable(true);
		this.PotionO.setDisable(true);
		this.PotionV.setDisable(true);
		this.PotionR.setOpacity(0.2);
		this.PotionO.setOpacity(0.2);
		this.PotionV.setOpacity(0.2);
		ArrayList<Items> items = this.Entity_Joueur.getInventaire();
		ArrayList<String> recherche = new ArrayList<String>();
		for (int i=0; i<items.size(); i++) {
			for (int j=0; j<items.get(i).getQuantity(); j++) {
				recherche.add(items.get(i).getName());
			}
		}
		this.PotionR_qty.setText(String.valueOf(Collections.frequency(recherche, "Potion'R")));
		this.PotionO_qty.setText(String.valueOf(Collections.frequency(recherche, "Potion'O")));
		this.PotionV_qty.setText(String.valueOf(Collections.frequency(recherche, "Potion'V")));
		if (Collections.frequency(recherche, "Potion'R")!=0) {
			this.PotionR.setDisable(false);
			this.PotionR.setOpacity(1);
		}
		if (Collections.frequency(recherche, "Potion'O")!=0) {
			this.PotionO.setDisable(false);
			this.PotionO.setOpacity(1);
		}
		if (Collections.frequency(recherche, "Potion'V")!=0) {
			this.PotionV.setDisable(false);
			this.PotionV.setOpacity(1);
		}
	}
	
	// lorsqu'une potion est utilisée on la supprime
	public void supprimerPotion(String potion) {
		ArrayList<Items> items = this.Entity_Joueur.getInventaire();
		for (int i=0; i<items.size(); i++) {
			if (items.get(i).getName().equals(potion)&&(this.Entity_Joueur.getInventaire().get(i).getQuantity()!=0)) {
				this.Entity_Joueur.getInventaire().get(i).setQuantity(this.Entity_Joueur.getInventaire().get(i).getQuantity()-1);
			}
		}
		this.verifierPotions();
	}
	
	// on va actualiser les infos des deux joueurs (infos visibles sur l'interface)
	public void actualiserInfos() {
		this.PV_Ennemi.setText(String.valueOf(this.Entity_Ennemi.getVie()));
		this.PA_Ennemi.setText(String.valueOf(this.Entity_Ennemi.getMana()));
		this.PV_Joueur.setText(String.valueOf(this.Entity_Joueur.getVie()));
		this.PA_Joueur.setText(String.valueOf(this.Entity_Joueur.getMana()));
		this.PA_Ennemi.setTextFill(Color.WHITE);
		this.PA_Joueur.setTextFill(Color.WHITE);
		// on va lancer un bruitage de coeur qui s'accélère en fonction de la vie restante au joueur
		if (Main.isBruitages()&&this.Entity_Joueur.getVie()<=30) {
			if (this.Entity_Joueur.getVie()<=20) {
				// le coeur bas très très très rapidement
		        Timeline timeline = new Timeline(
		        	    new KeyFrame(Duration.seconds(2),
		        	        new KeyValue(Main.music_background.volumeProperty(), 0.1)));
		        timeline.play();
				if (this.heart!=null) {
					this.heart.stop();
				}
				String path = MainRoot.class.getResource("bib/items/sounds/Heart2.mp3").toString();
				Media media = new Media(path);
				this.heart = new MediaPlayer(media);
				this.heart.play();
				this.heart.setCycleCount(MediaPlayer.INDEFINITE);
				this.PV_Joueur.setTextFill(Color.RED);
				this.heart_transition = new FadeTransition(Duration.millis(100), this.PVH_Joueur);
				this.heart_transition.setFromValue(0.1);
				this.heart_transition.setToValue(1.0);
				this.heart_transition.setCycleCount(Timeline.INDEFINITE);
				this.heart_transition.setAutoReverse(true);
				this.heart_transition.play();
			} else {
				// le coeur bas rapidement
		        Timeline timeline = new Timeline(
		        	    new KeyFrame(Duration.seconds(2),
		        	        new KeyValue(Main.music_background.volumeProperty(), 0.3)));
		        timeline.play();
				if (this.heart!=null) {
					this.heart.stop();
				}
				this.heart_transition = new FadeTransition(Duration.millis(250), this.PVH_Joueur);
				this.heart_transition.setFromValue(0.1);
				this.heart_transition.setToValue(1.0);
				this.heart_transition.setCycleCount(Timeline.INDEFINITE);
				this.heart_transition.setAutoReverse(true);
				this.heart_transition.play();
				String path = MainRoot.class.getResource("bib/items/sounds/Heart1.mp3").toString();
				Media media = new Media(path);
				this.heart = new MediaPlayer(media);
				this.heart.play();
				this.heart.setCycleCount(MediaPlayer.INDEFINITE);
				this.PV_Joueur.setTextFill(Color.ORANGE);
			}
		} else {
			// si sa vie n'est pas en périle, son coeur ne bat pas
			if (this.heart!=null) {
				this.heart.stop();
				this.heart_transition.stop();
				this.PVH_Joueur.setOpacity(1);
		        Timeline timeline = new Timeline(
		        	    new KeyFrame(Duration.seconds(2),
		        	        new KeyValue(Main.music_background.volumeProperty(), 1)));
		        timeline.play();
			}
		}
	}
	
	public void pertePVEnnemi(int PV) throws SQLException {	
		if (!online) {
			if ((this.Entity_Ennemi.getVie()-PV)<0) {
				this.Entity_Ennemi.setVie(0);
			} else {
				this.Entity_Ennemi.setVie(this.Entity_Ennemi.getVie() - PV);
			}
			this.actualiserInfos();
			FadeTransition ft = new FadeTransition(Duration.millis(250), this.PVH_Ennemi);
			ft.setFromValue(0.1);
			ft.setToValue(1.0);
			ft.setCycleCount(7);
			ft.setAutoReverse(true);
			ft.play();
			if (this.Entity_Ennemi.getVie()<=20) {
				this.PV_Ennemi.setTextFill(Color.RED);
			} else {
				this.PV_Ennemi.setTextFill(Color.WHITE);
			}
		} else {
			this.PVPerte = PV;
			this.attaque = true;
			System.out.println(PVPerte);
			this.actualiserInfos();
			FadeTransition ft = new FadeTransition(Duration.millis(250), this.PVH_Ennemi);
			ft.setFromValue(0.1);
			ft.setToValue(1.0);
			ft.setCycleCount(7);
			ft.setAutoReverse(true);
			ft.play();
			if (this.Entity_Ennemi.getVie()<=20) {
				this.PV_Ennemi.setTextFill(Color.RED);
			} else {
				this.PV_Ennemi.setTextFill(Color.WHITE);
			}
		}
	}
	
	// on enlève des PV au joueur
	public void pertePVJoueur(int PV) {
		if (!online) {
			if ((this.Entity_Joueur.getVie()-PV)<0) {
				this.Entity_Joueur.setVie(0);
			} else {
				this.Entity_Joueur.setVie(this.Entity_Joueur.getVie() - PV);
			}
			this.actualiserInfos();
			FadeTransition ft = new FadeTransition(Duration.millis(250), this.PVH_Joueur);
			ft.setFromValue(0.1);
			ft.setToValue(1.0);
			ft.setCycleCount(7);
			ft.setAutoReverse(true);
			ft.play();
			if (this.Entity_Joueur.getVie()<=20) {
				this.PV_Joueur.setTextFill(Color.RED);
			} else {
				this.PV_Joueur.setTextFill(Color.WHITE);
			}
		}
	}
	
	public void bonusPVEnnemi(int PV) {
		this.Entity_Ennemi.setVie(this.Entity_Ennemi.getVie() + PV);
		this.actualiserInfos();
		FadeTransition ft = new FadeTransition(Duration.millis(150), this.PVH_Ennemi);
		ft.setFromValue(0.1);
		ft.setToValue(1.0);
		ft.setCycleCount(7);
		ft.setAutoReverse(true);
		ft.play();
		this.PV_Ennemi.setTextFill(Color.GREEN);
	}
	
	// on ajoute un bonus de PV au joueur
	public void bonusPVJoueur(int PV) {
		this.Entity_Joueur.setVie(this.Entity_Joueur.getVie() + PV);
		this.actualiserInfos();
		FadeTransition ft = new FadeTransition(Duration.millis(150), this.PVH_Joueur);
		ft.setFromValue(0.1);
		ft.setToValue(1.0);
		ft.setCycleCount(7);
		ft.setAutoReverse(true);
		ft.play();
		this.PV_Joueur.setTextFill(Color.GREEN);
	}
	
	public void pertePAEnnemi(int PA) {
		this.Entity_Ennemi.setMana(this.Entity_Ennemi.getMana() - PA);
		this.actualiserInfos();
		FadeTransition ft = new FadeTransition(Duration.millis(150), this.PAI_Ennemi);
		ft.setFromValue(0.1);
		ft.setToValue(1.0);
		ft.setCycleCount(3);
		ft.setAutoReverse(true);
		ft.play();
		if (this.Entity_Ennemi.getMana()<=2) {
			this.PA_Ennemi.setTextFill(Color.RED);
		} else {
			this.PA_Ennemi.setTextFill(Color.WHITE);
		}
	}
	
	// on enlève des PA au joueur
	public void pertePAJoueur(int PA) {
		this.Entity_Joueur.setMana(this.Entity_Joueur.getMana() - PA);
		this.actualiserInfos();
		FadeTransition ft = new FadeTransition(Duration.millis(150), this.PAI_Joueur);
		ft.setFromValue(0.1);
		ft.setToValue(1.0);
		ft.setCycleCount(3);
		ft.setAutoReverse(true);
		ft.play();
		if (this.Entity_Joueur.getMana()<=2) {
			this.PA_Joueur.setTextFill(Color.RED);
		} else {
			this.PA_Joueur.setTextFill(Color.WHITE);
		}
	}
	
	public void bonusPAEnnemi(int PA) {
		this.Entity_Ennemi.setMana(this.Entity_Ennemi.getMana() + PA);
		this.actualiserInfos();
		FadeTransition ft = new FadeTransition(Duration.millis(150), this.PAI_Ennemi);
		ft.setFromValue(0.1);
		ft.setToValue(1.0);
		ft.setCycleCount(7);
		ft.setAutoReverse(true);
		ft.play();
		this.PA_Ennemi.setTextFill(Color.GREEN);
		this.verifyManaEnnemi();
	}
	
	// on ajoute des PA au joueur
	public void bonusPAJoueur(int PA) {
		this.Entity_Joueur.setMana(this.Entity_Joueur.getMana() + PA);
		this.actualiserInfos();
		FadeTransition ft = new FadeTransition(Duration.millis(150), this.PAI_Joueur);
		ft.setFromValue(0.1);
		ft.setToValue(1.0);
		ft.setCycleCount(7);
		ft.setAutoReverse(true);
		ft.play();
		this.PA_Joueur.setTextFill(Color.GREEN);
		this.verifyManaJoueur();
	}
	
	// DEBUT DES METHODES POUR LES ANIMATIONS //
	
	public void bonusJoueur(int min_pv, int max_pv, int pa) {
		this.bonusPAJoueur(pa);
		this.bonusPVJoueur((int)(Math.random()*(max_pv - min_pv)) + min_pv);
		this.Item_Utilise.setVisible(false);
		this.Item_Utilise.setTranslateX(0);
		this.Item_Utilise.setTranslateY(0);
		this.Item_Utilise.setRotate(0);
	}
	
	// utilisation de potions
	public FadeTransition bonusJoueurAnimation(String bib_image, String bib_sound) {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
		this.Item_Utilise.setVisible(true);
		Main.playBruitage(bib_sound);
		this.Item_Utilise.setTranslateX(-300);
		this.Item_Utilise.setTranslateY(80);
		FadeTransition ft = new FadeTransition(Duration.millis(150), this.Item_Utilise);
		ft.setFromValue(0.1);
		ft.setToValue(1.0);
		ft.setCycleCount(7);
		ft.setAutoReverse(true);
		ft.play();
        return ft;
	}
	
	public FadeTransition bonusEnnemiAnimation(String bib_image, String bib_sound) {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
		this.Item_Utilise.setVisible(true);
		Main.playBruitage(bib_sound);
		this.Item_Utilise.setTranslateX(300);
		this.Item_Utilise.setTranslateY(-60);
		FadeTransition ft = new FadeTransition(Duration.millis(150), this.Item_Utilise);
		ft.setFromValue(0.1);
		ft.setToValue(1.0);
		ft.setCycleCount(7);
		ft.setAutoReverse(true);
		ft.play();
        return ft;
	}
	
	public void bonusEnnemi(int min_pv, int max_pv, int pa) {
		this.bonusPAEnnemi(pa);
		this.bonusPVEnnemi((int)(Math.random()*(max_pv - min_pv)) + min_pv);
		this.Item_Utilise.setVisible(false);
		this.Item_Utilise.setTranslateX(0);
		this.Item_Utilise.setTranslateY(0);
		this.Item_Utilise.setRotate(0);
	}
	
	// animation d'attaque du joueur
	public void attaqueJoueur2(int min_pv, int max_pv, int pa) throws SQLException {
		if (!online) {
			this.animationDegatJoueur();
			this.pertePVJoueur((int)(Math.random()*(max_pv - min_pv+1) + min_pv));
			this.pertePAEnnemi(pa);
			this.Item_Utilise.setVisible(false);
			this.Item_Utilise.setTranslateX(0);
			this.Item_Utilise.setTranslateY(0);
			this.Item_Utilise.setRotate(0);
		} else {
			// si le jeu est en ligne, on mets à jour au serveur l'attaque effectuée
			resultat2 = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".salle_draaks_online WHERE salle='" + this.getSalle() + "';");
	        while (resultat2.next()) {
            	PVJ1 = resultat2.getString("PVJ1");
	            PVJ2 = resultat2.getString("PVJ2");
	            if (monReady.equals("J1")) {
		            this.Entity_Joueur.setVie(Integer.parseInt(PVJ1));
		            this.Entity_Ennemi.setVie(Integer.parseInt(PVJ2));
	            } else {
		            this.Entity_Joueur.setVie(Integer.parseInt(PVJ2));
		            this.Entity_Ennemi.setVie(Integer.parseInt(PVJ1));
	            }
	            this.actualiserInfos();
	        }
	        this.animationDegatJoueur();
			this.pertePAEnnemi(pa);
			this.Item_Utilise.setVisible(false);
			this.Item_Utilise.setTranslateX(0);
			this.Item_Utilise.setTranslateY(0);
			this.Item_Utilise.setRotate(0);
		}
	}
	
	public void attaqueEnnemi2(int min_pv, int max_pv, int pa) throws SQLException {
		if (!online) {
			this.animationDegatEnnemi();
			this.pertePVEnnemi((int)(Math.random()*(max_pv - min_pv+1) + min_pv));
			this.pertePAJoueur(pa);
			this.Item_Utilise.setVisible(false);
			this.Item_Utilise.setTranslateX(0);
			this.Item_Utilise.setTranslateY(0);
			this.Item_Utilise.setRotate(0);
		} else {
			this.pertePVEnnemi((int) (Math.random()*(max_pv - min_pv+1) + min_pv));
	        this.animationDegatEnnemi();
			this.pertePAJoueur(pa);
			this.Item_Utilise.setVisible(false);
			this.Item_Utilise.setTranslateX(0);
			this.Item_Utilise.setTranslateY(0);
			this.Item_Utilise.setRotate(0);
		}
	}
	
	/* ON VA RETROUVER CI-DESSOUS DES METHODES D'ANIMATIONS CODEES PAR NOUS MÊME
	 * POUR DONNER DE LA VITALITE AU COMBAT.
	 * AINSI, EN FONCTION DE L'ATTAQUE, ON ATTRIBUE UNE ANIMATION PREDEFINIE.
	 */
	
	public RotateTransition rotation(int angle) {
		RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(angle);
        rotate.setDuration(Duration.millis(300));
        rotate.setAutoReverse(true);
        rotate.setNode(this.Item_Utilise);
        rotate.play();
        return rotate;
	}
	
	public TranslateTransition attaqueJoueurTranslate1(String bib_image, String bib_sound, int rotation) {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
		this.Item_Utilise.setVisible(true);
		this.Item_Utilise.setRotate(rotation);
		Main.playBruitage(bib_sound);
		RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360);
        rotate.setDuration(Duration.millis(500));
        rotate.setAutoReverse(true);
        rotate.setNode(this.Item_Utilise);
        rotate.play();
        TranslateTransition translate = new TranslateTransition();   
        translate.setByX(-300);
        translate.setDuration(Duration.millis(500));
        translate.setNode(this.Item_Utilise);
        translate.play();
        return translate;
	}
	
	public TranslateTransition attaqueEnnemiTranslate1(String bib_image, String bib_sound, int rotation) {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
		this.Item_Utilise.setVisible(true);
		this.Item_Utilise.setRotate(rotation);
		Main.playBruitage(bib_sound);
		RotateTransition rotate = new RotateTransition(); 
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360);
        rotate.setDuration(Duration.millis(500));
        rotate.setAutoReverse(true);
        rotate.setNode(this.Item_Utilise);
        rotate.play();
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(300);
        translate.setByY(-100);
        translate.setDuration(Duration.millis(500));
        translate.setNode(this.Item_Utilise);
        translate.play();
        return translate;
	}
	
	public void attaqueJoueurTranslate2(String bib_image, String bib_sound, int rotation) {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
		this.Item_Utilise.setVisible(true);
        this.Item_Utilise.setTranslateX(300);
        this.Item_Utilise.setTranslateY(-100);
        TranslateTransition translate0 = new TranslateTransition();
        translate0.setByX(-300);
        translate0.setByY(100);
        translate0.setDuration(Duration.millis(750));
        translate0.setNode(this.Item_Utilise);
        translate0.play();
        translate0.setOnFinished(event -> this.attaqueJoueurTranslate1(bib_image, bib_sound, rotation));
	}
	
	public void attaqueEnnemiTranslate2(String bib_image, String bib_sound, int rotation) {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
		this.Item_Utilise.setVisible(true);
        this.Item_Utilise.setTranslateX(-300);
        TranslateTransition translate0 = new TranslateTransition();
        translate0.setByX(300);
        translate0.setDuration(Duration.millis(750));
        translate0.setNode(this.Item_Utilise);
        translate0.play();
        translate0.setOnFinished(event -> this.attaqueEnnemiTranslate1(bib_image, bib_sound, rotation));
	}
	
	public TranslateTransition attaqueJoueurTranslate3(String bib_image, String bib_sound) {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
		Main.playBruitage(bib_sound);
		this.Item_Utilise.setVisible(true);
        this.Item_Utilise.setTranslateX(300);
        this.Item_Utilise.setTranslateY(-100);
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(-600);
        translate.setByY(100);
        translate.setDuration(Duration.millis(500));
        translate.setNode(this.Item_Utilise);
        translate.play();
        return translate;
	}
	
	public TranslateTransition attaqueEnnemiTranslate3(String bib_image, String bib_sound) {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
		Main.playBruitage(bib_sound);
		this.Item_Utilise.setVisible(true);
        this.Item_Utilise.setTranslateX(-300);
        this.Item_Utilise.setTranslateY(0);
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(600);
        translate.setByY(-100);
        translate.setDuration(Duration.millis(500));
        translate.setNode(this.Item_Utilise);
        translate.play();
        return translate;
	}
	
	public TranslateTransition attaqueJoueurTranslateRotate(String bib_image, String bib_sound, int rotation) {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
		Main.playBruitage(bib_sound);
		this.Item_Utilise.setRotate(rotation);
		this.Item_Utilise.setVisible(true);
        this.Item_Utilise.setTranslateX(300);
        this.Item_Utilise.setTranslateY(-100);
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(-600);
        translate.setByY(100);
        translate.setDuration(Duration.millis(400));
        translate.setNode(this.Item_Utilise);
        translate.play();
        return translate;
	}
	
	public TranslateTransition attaqueEnnemiTranslateRotate(String bib_image, String bib_sound, int rotation) {
		this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
		Main.playBruitage(bib_sound);
		this.Item_Utilise.setRotate(rotation);
		this.Item_Utilise.setVisible(true);
		this.Item_Utilise.setTranslateX(-300);
        this.Item_Utilise.setTranslateY(0);
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(600);
        translate.setByY(-100);
        translate.setDuration(Duration.millis(400));
        translate.setNode(this.Item_Utilise);
        translate.play();
        return translate;
	}
	
	public void cacEtoJ1(String bib_sound) {
		TranslateTransition translate = new TranslateTransition();   
        translate.setByX(-500);  
        translate.setByY(100);
        translate.setDuration(Duration.millis(750));  
        translate.setCycleCount(1); 
        translate.setNode(this.Pierre_Ennemi);  
        translate.play();
        TranslateTransition translate2 = new TranslateTransition();   
        translate2.setByX(-500);  
        translate2.setByY(100);
        translate2.setDuration(Duration.millis(750));  
        translate2.setCycleCount(1); 
        translate2.setNode(this.Ennemi);  
        translate2.play();
        translate2.setOnFinished(event -> this.cacEtoJ2(bib_sound));
	}
	
	public void cacEtoJ2(String bib_sound) {
		Main.playBruitage(bib_sound);
		this.cacEtoJ3();
	}
	
	public void cacEtoJ3() {
		TranslateTransition translate = new TranslateTransition();   
        translate.setByX(500);  
        translate.setByY(-100);
        translate.setDuration(Duration.millis(750));  
        translate.setCycleCount(1); 
        translate.setNode(this.Pierre_Ennemi);  
        translate.play();
        TranslateTransition translate2 = new TranslateTransition();   
        translate2.setByX(500);  
        translate2.setByY(-100);
        translate2.setDuration(Duration.millis(750));  
        translate2.setCycleCount(1); 
        translate2.setNode(this.Ennemi);  
        translate2.play();
        translate2.setOnFinished(event -> {
			try {
				this.attaqueJoueur2(1, 5, 2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void cacJtoE1(String bib_sound) {
		TranslateTransition translate = new TranslateTransition();   
        translate.setByX(500);  
        translate.setByY(-100);
        translate.setDuration(Duration.millis(750));  
        translate.setCycleCount(1); 
        translate.setNode(this.Pierre_Joueur);  
        translate.play();
        TranslateTransition translate2 = new TranslateTransition();   
        translate2.setByX(500);  
        translate2.setByY(-100);
        translate2.setDuration(Duration.millis(750));  
        translate2.setCycleCount(1); 
        translate2.setNode(this.Joueur);  
        translate2.play();
        translate2.setOnFinished(event -> this.cacJtoE2(bib_sound));
	}
	
	public void cacJtoE2(String bib_sound) {
		Main.playBruitage(bib_sound);
		this.cacJtoE3();
	}
	
	public void cacJtoE3() {
		TranslateTransition translate = new TranslateTransition();   
        translate.setByX(-500);  
        translate.setByY(100);
        translate.setDuration(Duration.millis(750));  
        translate.setCycleCount(1); 
        translate.setNode(this.Pierre_Joueur);  
        translate.play();
        TranslateTransition translate2 = new TranslateTransition();   
        translate2.setByX(-500);  
        translate2.setByY(100);
        translate2.setDuration(Duration.millis(750));  
        translate2.setCycleCount(1); 
        translate2.setNode(this.Joueur);  
        translate2.play();
        translate2.setOnFinished(event -> {
			try {
				this.attaqueEnnemi2(1, 9, 2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	// FIN DES METHODES POUR LES ANIMATIONS //
	
	/* ON DEFINIT ICI LES DIFFERENTES ATTAQUES POSSIBLES AU JOUEUR.
	 * CHAQUE ATTAQUE A SA PROPRE METHODE.
	 * ON LIE CES ATTAQUES A NOS ANIMATIONS CI-DESSUS.
	 */
	
	// DEBUT DES METHODES POUR CHAQUE ACTION POSSIBLE //
	
	public void attaqueFleche(Entity attaquant) {
		String bib_image = "bib/items/Fleche.png";
		String bib_sound = "bib/items/sounds/Fleche_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, -10);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(5, 15, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, 170);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(5, 15, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void attaqueFleche2(Entity attaquant) {
		String bib_image = "bib/items/Fleche_2.png";
		String bib_sound = "bib/items/sounds/Fleche_2_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, 115);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(9, 16, 4);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, -55);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(9, 16, 4);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void attaqueFleche3(Entity attaquant) {
		String bib_image = "bib/items/Fleche_3.png";
		String bib_sound = "bib/items/sounds/Fleche_3_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslate1(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(10, 20, 5);
					this.Item_Utilise.setVisible(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslate1(bib_image, bib_sound, -145);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(10, 20, 5);
					this.Item_Utilise.setVisible(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void lancerCouteau(Entity attaquant) {
		String bib_image = "bib/items/Couteau.png";
		String bib_sound = "bib/items/sounds/Couteau_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, 30);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(5, 15, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, -145);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(5, 15, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void coupDeSabre(Entity attaquant) {
		String bib_image = "bib/items/Sabre.png";
		String bib_sound = "bib/items/sounds/Sabre_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
        	Main.playBruitage(bib_sound);
        	this.Item_Utilise.setVisible(true);
        	this.Item_Utilise.setScaleX(-1);
        	this.Item_Utilise.setTranslateX(300);
			this.Item_Utilise.setTranslateY(-100);
			RotateTransition rotate = this.rotation(60);
			rotate.setOnFinished(event -> {
				this.Item_Utilise.setVisible(false);
				this.Item_Utilise.setTranslateX(0);
				this.Item_Utilise.setTranslateY(0);
				this.Item_Utilise.setRotate(0);
				try {
					this.attaqueEnnemi2(9, 16, 4);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.Item_Utilise.setScaleX(1);
			});
		} else {
			this.Item_Utilise.setImage(new Image(Main.getClass().getResourceAsStream(bib_image)));
        	Main.playBruitage(bib_sound);
        	this.Item_Utilise.setVisible(true);
        	this.Item_Utilise.setTranslateX(-280);
			this.Item_Utilise.setTranslateY(0);
			RotateTransition rotate = this.rotation(-60);
			rotate.setOnFinished(event -> {
				this.Item_Utilise.setVisible(false);
				this.Item_Utilise.setTranslateX(0);
				this.Item_Utilise.setTranslateY(0);
				this.Item_Utilise.setRotate(0);
				try {
					this.attaqueJoueur2(9, 16, 4);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void epeeFlamme(Entity attaquant) {
		String bib_image = "bib/items/EpeeFlamme.png";
		String bib_sound = "bib/items/sounds/EpeeFlamme_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslate1(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(10, 20, 5);
					this.Item_Utilise.setVisible(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslate1(bib_image, bib_sound, -145);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(10, 20, 5);
					this.Item_Utilise.setVisible(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void lancerSort(Entity attaquant) {
		String bib_image = "bib/items/Sort.png";
		String bib_sound = "bib/items/sounds/Sort_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslate1(bib_image, bib_sound, -160);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(5, 15, 3);
					this.Item_Utilise.setVisible(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslate1(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(5, 15, 3);
					this.Item_Utilise.setVisible(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void jeterFiole(Entity attaquant) {
		String bib_image = "bib/items/Fiole.png";
		String bib_sound = "bib/items/sounds/Fiole_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(9, 16, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(9, 16, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}

	
	public void lancerFireball(Entity attaquant) {
		String bib_image = "bib/items/Fireball.png";
		String bib_sound = "bib/items/sounds/Fireball_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, -160);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(10, 20, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(10, 20, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void corpsACorps(Entity attaquant) {
		String bib_sound = "bib/items/sounds/poing_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			this.cacJtoE1(bib_sound);
		} else {
			this.cacEtoJ1(bib_sound);
		}
	}
	
	public void bonusPotion1(Entity attaquant) {
		this.supprimerPotion("Potion'R");
		this.PotionR.setDisable(true);
		this.PotionO.setDisable(true);
		this.PotionV.setDisable(true);
		String bib_image = "bib/items/Potion-R.png";
		String bib_sound = "bib/items/sounds/Potion-R_sound.mp3";
		this.status(this.Entity_Joueur.getName() + " a utilisé une Potion'R.");
		if (attaquant.equals(this.Entity_Joueur)) {
			FadeTransition animation = this.bonusJoueurAnimation(bib_image, bib_sound);
			animation.setOnFinished(event -> {
				this.bonusJoueur(5, 15, 0);
				this.actionDisabled(true);
				this.animationTerminerTour();
			});
		} else {
			FadeTransition animation = this.bonusEnnemiAnimation(bib_image, bib_sound);
			animation.setOnFinished(event -> this.bonusEnnemi(5, 15, 0));
		}
	}
	
	public void bonusPotion2(Entity attaquant) {
		this.supprimerPotion("Potion'O");
		this.PotionR.setDisable(true);
		this.PotionO.setDisable(true);
		this.PotionV.setDisable(true);
		String bib_image = "bib/items/Potion-O.png";
		String bib_sound = "bib/items/sounds/Potion-O_sound.mp3";
		this.status(this.Entity_Joueur.getName() + " a utilisé une Potion'O.");
		if (attaquant.equals(this.Entity_Joueur)) {
			FadeTransition animation = this.bonusJoueurAnimation(bib_image, bib_sound);
			animation.setOnFinished(event -> {
				this.bonusJoueur(5, 15, 0);
				this.actionDisabled(true);
				this.animationTerminerTour();
			});
		} else {
			FadeTransition animation = this.bonusEnnemiAnimation(bib_image, bib_sound);
			animation.setOnFinished(event -> this.bonusEnnemi(5, 15, 0));
		}
	}
	
	public void bonusPotion3(Entity attaquant) {
		this.supprimerPotion("Potion'V");
		this.PotionR.setDisable(true);
		this.PotionO.setDisable(true);
		this.PotionV.setDisable(true);
		String bib_image = "bib/items/Potion-V.png";
		String bib_sound = "bib/items/sounds/Potion-V_sound.mp3";
		this.status(this.Entity_Joueur.getName() + " a utilisé une Potion'V.");
		if (attaquant.equals(this.Entity_Joueur)) {
			FadeTransition animation = this.bonusJoueurAnimation(bib_image, bib_sound);
			animation.setOnFinished(event -> {
				this.bonusJoueur(5, 15, 0);
				this.actionDisabled(true);
				this.animationTerminerTour();
			});
		} else {
			FadeTransition animation = this.bonusEnnemiAnimation(bib_image, bib_sound);
			animation.setOnFinished(event -> this.bonusEnnemi(5, 15, 0));
		}
	}
	
	// FIN DES METHODES POUR CHAQUE ACTION POSSIBLE //
	
	// LES MEMES EXPLICATIONS QUE CI-DESSUS MAIS POUR NOS ENNEMIS.
	
	// DEBUT DES METHODES POUR LES ATTAQUES DES ENNEMIS //
	
	public void lancerOS(Entity attaquant) {
		String bib_image = "bib/items/OS.png";
		String bib_sound = "bib/items/sounds/OS_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, -160);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(5, 15, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(5, 15, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	
	public void lancerDoubleOS(Entity attaquant) {
		String bib_image = "bib/items/DoubleOS.png";
		String bib_sound = "bib/items/sounds/DoubleOS_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, -160);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(9, 16, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(9, 16, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void lancerCraneDino(Entity attaquant) {
		String bib_image = "bib/items/CraneDino.png";
		String bib_sound = "bib/items/sounds/CraneDino_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslate1(bib_image, bib_sound, -160);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(10, 20, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslate1(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(10, 20, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}

	public void lancerBois(Entity attaquant) {
		String bib_image = "bib/items/Bois.png";
		String bib_sound = "bib/items/sounds/Bois_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, -160);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(5, 15, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(5, 15, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	
	public void lancerPierre(Entity attaquant) {
		String bib_image = "bib/items/Pierre.png";
		String bib_sound = "bib/items/sounds/Pierre_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, -160);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(9, 16, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(9, 16, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void lancerDent(Entity attaquant) {
		String bib_image = "bib/items/Dent.png";
		String bib_sound = "bib/items/sounds/Dent_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslate1(bib_image, bib_sound, -160);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(10, 20, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			TranslateTransition attaque = this.attaqueJoueurTranslate1(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(10, 20, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void lancerArbre(Entity attaquant) {
		String bib_image = "bib/items/Arbre.png";
		String bib_sound = "bib/items/sounds/Arbre_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, -160);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(5, 15, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			this.Item_Utilise.setFitHeight(200);
			this.Item_Utilise.setFitWidth(200);
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(5, 15, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.Item_Utilise.setFitHeight(150);
				this.Item_Utilise.setFitWidth(100);
			});
		}
	}
	
	
	public void lancerCorne(Entity attaquant) {
		String bib_image = "bib/items/Corne.png";
		String bib_sound = "bib/items/sounds/Corne_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, -160);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(9, 16, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			this.Item_Utilise.setFitHeight(125);
			this.Item_Utilise.setFitWidth(125);
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, 20);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(9, 16, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.Item_Utilise.setFitHeight(150);
				this.Item_Utilise.setFitWidth(100);
			});
		}
	}
	
	public void lancerGargouille(Entity attaquant) {
		String bib_image = "bib/items/Gargouille.png";
		String bib_sound = "bib/items/sounds/Gargouille_sound.mp3";
		if (attaquant.equals(this.Entity_Joueur)) {
			TranslateTransition attaque = this.attaqueEnnemiTranslateRotate(bib_image, bib_sound, 0);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueEnnemi2(10, 20, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			this.Item_Utilise.setFitHeight(200);
			this.Item_Utilise.setFitWidth(200);
			TranslateTransition attaque = this.attaqueJoueurTranslateRotate(bib_image, bib_sound, 0);
			attaque.setOnFinished(event -> {
				try {
					this.attaqueJoueur2(10, 20, 3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.Item_Utilise.setFitHeight(150);
				this.Item_Utilise.setFitWidth(100);
			});
		}
	}
	
	// FIN DES METHODES POUR LES ATTAQUES DES ENNEMIS //
	
	// DEBUT DES METHODES POUR LES ACTIONS DE NOTRE JOUEUR //
	
	public void Action1() throws SQLException {
		this.actionDisabled(true);
		if (lancer) {
			Main.buttonClick();
			this.corpsACorps(this.Entity_Joueur);
			this.status(this.Entity_Joueur.getName() + " a lancé une attaque de corps à corps contre " + this.Entity_Ennemi.getName() + ".");
			this.ActionsJ.add("A1");
		} else {
			this.actionJ1 = "A1";
		}
	}
	
	public void Action1E() {
		this.corpsACorps(this.Entity_Ennemi);
		this.status(this.Entity_Ennemi.getName() + " a lancé une attaque de corps à corps contre " + this.Entity_Joueur.getName() + ".");
		this.ActionsE.add("A1");
	}
	
	public void Action2() {
		Main.buttonClick();
		this.actionDisabled(true);
		if (lancer) {
			if (this.type.equals("Mage")) {
				this.lancerSort(this.Entity_Joueur);
				this.status(this.Entity_Joueur.getName() + " a jeté un sort à " + this.Entity_Ennemi.getName() + ".");
			}
			if (this.type.equals("Archer")) {
				this.attaqueFleche(this.Entity_Joueur);
				this.status(this.Entity_Joueur.getName() + " a lancé une flèche simple contre " + this.Entity_Ennemi.getName() + ".");
			}
			if (this.type.equals("Guerrier")) {
				this.lancerCouteau(this.Entity_Joueur);
				this.status(this.Entity_Joueur.getName() + " a lancé un couteau contre " + this.Entity_Ennemi.getName() + ".");
			}
			this.ActionsJ.add("A2");	
		} else {
			this.actionJ1 = "A2";
		}
	}
	
	public void Action2E() {
		if (this.typeEnnemi.equals("Mage")) {
			this.lancerSort(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a jeté un sort à " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Archer")) {
			this.attaqueFleche(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé une flèche simple contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Guerrier")) {
			this.lancerCouteau(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé un couteau contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Squelette")) {
			this.lancerOS(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé un OS contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Gobelin")) {
			this.lancerBois(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé du bois contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Geant")) {
			this.lancerArbre(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé un arbre sur " + this.Entity_Joueur.getName() + ".");
		}
		this.ActionsE.add("A2");
	}
	
	public void Action3() {
		Main.buttonClick();
		this.actionDisabled(true);
		if (lancer) {
			if (this.type.equals("Mage")) {
				this.jeterFiole(this.Entity_Joueur);
				this.status(this.Entity_Joueur.getName() + " a lancé une fiole empoisonnée contre " + this.Entity_Ennemi.getName() + ".");
			}
			if (this.type.equals("Archer")) {
				this.attaqueFleche2(this.Entity_Joueur);
				this.status(this.Entity_Joueur.getName() + " a lancé une flèche taillée contre " + this.Entity_Ennemi.getName() + ".");
			}
			if (this.type.equals("Guerrier")) {
				this.coupDeSabre(this.Entity_Joueur);
				this.status(this.Entity_Joueur.getName() + " a utilisé son sabre contre " + this.Entity_Ennemi.getName() + ".");
			}
			this.ActionsJ.add("A3");	
		} else {
			this.actionJ1 = "A3";
		}
	}
	
	public void Action3E() {
		Main.buttonClick();
		this.actionDisabled(true);
		if (this.typeEnnemi.equals("Mage")) {
			this.jeterFiole(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé une fiole empoisonnée contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Archer")) {
			this.attaqueFleche2(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé une flèche taillée contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Guerrier")) {
			this.coupDeSabre(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a utilisé son sabre contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Squelette")) {
			this.lancerDoubleOS(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé OS doublé contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Gobelin")) {
			this.lancerPierre(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a jeté de la pierre contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Geant")) {
			this.lancerCorne(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé une corne contre " + this.Entity_Joueur.getName() + ".");
		}
		this.ActionsE.add("A3");
	}
	
	public void Action4() {
		Main.buttonClick();
		this.actionDisabled(true);
		if (lancer) {
			if (this.type.equals("Mage")) {
				this.lancerFireball(this.Entity_Joueur);
				this.status(this.Entity_Joueur.getName() + " a lancé une Fireball contre " + this.Entity_Ennemi.getName() + ".");
			}
			if (this.type.equals("Archer")) {
				this.attaqueFleche3(this.Entity_Joueur);
				this.status(this.Entity_Joueur.getName() + " a lancé une flèche ensorcolée contre " + this.Entity_Ennemi.getName() + ".");
			}
			if (this.type.equals("Guerrier")) {
				this.epeeFlamme(this.Entity_Joueur);
				this.status(this.Entity_Joueur.getName() + " a jeté son épée enflammée contre " + this.Entity_Ennemi.getName() + ".");
			}
			this.ActionsJ.add("A4");	
		} else {
			this.actionJ1 = "A4";
		}
	}
	
	public void Action4E() {
		Main.buttonClick();
		this.actionDisabled(true);
		if (this.typeEnnemi.equals("Mage")) {
			this.lancerFireball(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé une Fireball contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Archer")) {
			this.attaqueFleche3(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a lancé une flèche ensorcolée contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Guerrier")) {
			this.epeeFlamme(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a jeté son épée enflammée contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Squelette")) {
			this.lancerCraneDino(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a invoqué un squelette de dinosaure contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Gobelin")) {
			this.lancerDent(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " s'est arraché une dent pour la jeter contre " + this.Entity_Joueur.getName() + ".");
		}
		if (this.typeEnnemi.equals("Geant")) {
			this.lancerGargouille(this.Entity_Ennemi);
			this.status(this.Entity_Ennemi.getName() + " a invoqué une Gargouille contre " + this.Entity_Joueur.getName() + ".");
		}
		this.ActionsE.add("A4");
	}
	
	public void utiliserPotionR() {
		this.bonusPotion1(this.Entity_Joueur);
	}
	
	public void utiliserPotionO() {
		this.bonusPotion2(this.Entity_Joueur);
	}
	
	public void utiliserPotionV() {
		this.bonusPotion3(this.Entity_Joueur);
	}
	
	// FIN DES METHODES POUR LES ACTIONS DE NOTRE JOUEUR //
	
	// Ceci est une méthode de test pour notre débugging, elle n'est pas utilisé dans l'interface
	public void attaqueJoueur() {
        this.bonusPotion1(this.Entity_Joueur);
	}
	
	// si le joueur est face à un mob, le mob va jouer seul
	public void attaqueEnnemi() {
		int action = (int) (Math.random()*4+1);
		// le mob va choisir une action au hasard
		int nb_action = Collections.frequency(this.ActionsE, "A" + String.valueOf(action));
		// on enregistre l'action qu'il a faîtes, pour qu'il ait les mêmes contraintes que le joueur :
		// une action ne peut pas donc être lancée +2 fois dans un même tour
		if ((nb_action)==2) {
			if (this.Entity_Ennemi.getMana()<2) {
				this.Entity_Ennemi.setMana(this.PA_Origine_Ennemi);
				this.actualiserInfos();
				this.actionDisabled(false);
			} else {
				if (this.PA_Origine_Ennemi<=6) {
					this.Entity_Ennemi.setMana(this.PA_Origine_Ennemi);
					this.actualiserInfos();
					this.actionDisabled(false);
				} else {
					this.attaqueEnnemi();
				}
			}
		} else {
			// il fera donc tout pour utiliser le plus de PA possibles lors d'un tour
			switch (action) {
			case 1:
				if (this.Entity_Ennemi.getMana()>=2) {
					this.Action1E();
				}
				break;
			case 2:
				if (this.Entity_Ennemi.getMana()>=3) {
					this.Action2E();
				} else {
					this.attaqueEnnemi();
				}
				break;
			case 3:
				if (this.Entity_Ennemi.getMana()>=4) {
					this.Action3E();
				} else {
					this.attaqueEnnemi();
				}
				break;
			case 4:
				if (this.Entity_Ennemi.getMana()>=5) {
					this.Action4E();
				} else {
					this.attaqueEnnemi();
				}
				break;
			default:
				break;
			}
		}
	}
	
	public String getSalle() {
		return Salle;
	}

	public void setSalle(String salle) {
		Salle = salle;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	// le service permettant de vérifier en arrière plan, l'état du jeu lors d'une partie
	// en multijoueur.
	public Service<Void> verifierTour = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {
						System.out.println("Vérification du tour du joueur...");
						System.out.println(getSalle());
						resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".salle_draaks_online WHERE salle='" + getSalle() + "';");
					    
					    System.out.println("R2 = " + resultat.toString());
					    
				        while (resultat.next()) {
				            String position = resultat.getString("J1");
				            String actionJ1_n = resultat.getString("actionJ1");
				            String actionJ2_n = resultat.getString("actionJ2");
				            String dateJ1_n = resultat.getString("dateJ1");
				            String dateJ2_n = resultat.getString("dateJ2");
					        String connecte = resultat.getString("connecte");
				            String PVJ1_n = resultat.getString("PVJ1");
					        String PVJ2_n = resultat.getString("PVJ2");
					        // on vérifie à quelle position se trouve le joueur au niveau du serveur
				            if (position.equals(getIdentifiant())) {
				            	monReady = "J1"; // il est en position 1
				            } else {
				            	monReady = "J2"; // il est en position 2
				            }
				            String monTour = resultat.getString("ready" + monReady);
				            if (monReady.equals("J1")) {
				            	statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".salle_draaks_online SET PVJ1='" + PV_Origine_Joueur + "', PVJ2='" + PV_Origine_Ennemi + "' WHERE salle='" + getSalle() + "';");
				            } else {
				            	statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".salle_draaks_online SET PVJ2='" + PV_Origine_Joueur + "', PVJ1='" + PV_Origine_Ennemi + "' WHERE salle='" + getSalle() + "';");
				            }
						    System.out.println("ready" + monReady);
					        while (connecte.equals("1")) {
					        	Thread.sleep(500); // on définit la latence de rechargement du jeu
					        	if (monTour.equals("0")) {
					        		if (!actions_desactive) {
					        			actionDisabled(true);
					        		}
					        	} else {
					        		if ((actions_desactive)&&(actionJ1.equals(""))) {
					        			actionDisabled(false);
					        		}
					        	}
					        	if (!actionJ1.equals("")) {
					        		// on envoie l'action au serveur
					        		statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".salle_draaks_online SET action" + monReady + "='" + actionJ1 + "', date" + monReady + "='" + new Date() + "' WHERE salle='" + getSalle() + "';");
					        		actionJ1 = "";
					        	}
					        	if (passerTourStatut) { // on passe le tour au serveur
					        		if (monReady.equals("J1")) {
					    				statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".salle_draaks_online SET readyJ1='0', readyJ2='1' WHERE salle='" + getSalle() + "';");
					    			} else {
					    				statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".salle_draaks_online SET readyJ1='1', readyJ2='0' WHERE salle='" + getSalle() + "';");
					    			}
					        		passerTourStatut = false;
					        	}
					        	if (!XP_string.getText().equals("0")) { // on modifie les XP du gagnant
					        		statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".personnages SET xp=(xp+" + XP_string.getText() + ") WHERE identifiant='" + identifiant + "';");
					        	}
					        	if ((PVPerte!=0)) { // on récupère le nombre de PV perdu depuis le serveur
					    			if (monReady.equals("J1")) {
					    				statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".salle_draaks_online SET PVJ2='" + String.valueOf(Entity_Ennemi.getVie()-PVPerte) + "' WHERE salle='" + getSalle() + "';");
					    			} else {
					    				statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".salle_draaks_online SET PVJ1='" + String.valueOf(Entity_Ennemi.getVie()-PVPerte) + "' WHERE salle='" + getSalle() + "';");
					    			}
					    			if ((Entity_Ennemi.getVie()-PVPerte)<0) {
					    				Entity_Ennemi.setVie(0);
					    			} else {
					    				Entity_Ennemi.setVie(Entity_Ennemi.getVie() - PVPerte);
					    				System.out.println("PV Ennemi : " + (Entity_Ennemi.getVie() - PVPerte));
					    			}
					    			PVPerte = 0;
					        	}
//					        	if (monReady.equals("J1")) {
//					        		PV_Joueur.setText(PVJ1_n);
//					        		PV_Ennemi.setText(PVJ2_n);
//					        		System.out.println("Vos PVs : " + PVJ1_n);
//					        		System.out.println("PVs Ennemi : " + PVJ2_n);
//					        	} else {
//					        		PV_Joueur.setText(PVJ2_n);
//					        		PV_Ennemi.setText(PVJ1_n);
//					        		System.out.println("Vos PVs : " + PVJ1_n);
//					        		System.out.println("PVs Ennemi : " + PVJ2_n);
//					        	}
					        	if (!dateJ1_n.equals(dateJ1)) {
					        		System.out.println("Date différente.");
					        		dateJ1 = dateJ1_n;
					        		switch (actionJ1_n) {
					        		case "A1":
					        			lancer=true;
					        			if (monReady.equals("J1")) {
					        				Action1();
					        			} else {
					        				Action1E();
					        			}
					        			lancer=false;
					        			break;
					        		case "A2":
					        			lancer=true;
					        			if (monReady.equals("J1")) {
					        				Action2();
					        			} else {
					        				Action2E();
					        			}
					        			lancer=false;
					        			break;
					        		case "A3":
					        			lancer=true;
					        			if (monReady.equals("J1")) {
					        				Action3();
					        			} else {
					        				Action3E();
					        			}
					        			lancer=false;
					        			break;
					        		case "A4":
					        			lancer=true;
					        			if (monReady.equals("J1")) {
					        				Action4();
					        			} else {
					        				Action4E();
					        			}
					        			lancer=false;
					        			break;
					        		default :
					        			break;
					        		}
					        		verifierFin();
					        	}
					        	if (!dateJ2_n.equals(dateJ2)) {
					        		System.out.println("Date différente.");
					        		dateJ2 = dateJ2_n;
					        		switch (actionJ2_n) {
					        		case "A1":
					        			lancer=true;
					        			if (!monReady.equals("J1")) {
					        				Action1();
					        			} else {
					        				Action1E();
					        			}
					        			lancer=false;
					        			break;
					        		case "A2":
					        			lancer=true;
					        			if (!monReady.equals("J1")) {
					        				Action2();
					        			} else {
					        				Action2E();
					        			}
					        			lancer=false;
					        			break;
					        		case "A3":
					        			lancer=true;
					        			if (!monReady.equals("J1")) {
					        				Action3();
					        			} else {
					        				Action3E();
					        			}
					        			lancer=false;
					        			break;
					        		case "A4":
					        			lancer=true;
					        			if (!monReady.equals("J1")) {
					        				Action4();
					        			} else {
					        				Action4E();
					        			}
					        			lancer=false;
					        			break;
					        		default :
					        			break;
					        		}
					        		verifierFin();
					        	}
					            resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".salle_draaks_online WHERE salle='" + getSalle() + "';");
					            while (resultat.next()) {
					            	connecte = resultat.getString("connecte");
					            	monTour = resultat.getString("ready" + monReady);
					            	actionJ1_n = resultat.getString("actionJ1");
						            actionJ2_n = resultat.getString("actionJ2");
						            dateJ1_n = resultat.getString("dateJ1");
						            dateJ2_n = resultat.getString("dateJ2");
						            PVJ1_n = resultat.getString("PVJ1");
						            PVJ2_n = resultat.getString("PVJ2");
						            if (monReady.equals("J1")) {
						            	Entity_Joueur.setVie(Integer.parseInt(PVJ1_n));
						            	Entity_Ennemi.setVie(Integer.parseInt(PVJ2_n));
						            } else {
						            	Entity_Joueur.setVie(Integer.parseInt(PVJ2_n));
						            	Entity_Ennemi.setVie(Integer.parseInt(PVJ1_n));
						            }
					            }
					        }
				        }
				        
					    return null;
				}
				
			};
		}
		
	};
	
	// on vérifie que les joueurs sont connectés à internet
	public Service<Void> connecte = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					if (Main.getInfos_bdd()[0] == null) {
						Main.defautBDD();
					}
					try {
				        Class.forName("com.mysql.cj.jdbc.Driver");
				    } catch ( ClassNotFoundException e ) {
				    	System.out.println( "Erreur lors du chargement du driver \t"
				                + e.getMessage() );
				    	this.updateMessage("Veuillez réparer les fichiers de votre jeu.");
				    }
					String url = "jdbc:mysql://" + Main.getInfos_bdd()[0] + ":" + Main.getInfos_bdd()[1] + "/" + Main.getInfos_bdd()[2] + "?connectTimeout=3000&useUnicode=true&characterEncoding=UTF-8";
					String utilisateur = Main.getInfos_bdd()[3];
					String motDePasse = Main.getInfos_bdd()[4];
					Connection connexion = null;
					try {
					    connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
					    System.out.println("\nRécupération des données utilisateurs...");

				        statement = connexion.createStatement();
				        
				        online=true;
				        lancer=false;
				        
					} catch (SQLException e) {
						System.out.println("\nErreur avec la BDD.");
						System.err.println("\nSQLState: " +
			                    ((SQLException)e).getSQLState());

			                System.err.println("Error Code: " +
			                    ((SQLException)e).getErrorCode());

			                System.err.println("Message: " + e.getMessage());
			                this.updateMessage("Le serveur ne répond pas...");
					}
					return null;
				}
				
			};
		}
		
	};

}
