package fr.draaks.dungeon.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import fr.draaks.dungeon.MainRoot;
//import fr.draaks.dungeon.model.Geant;
//import fr.draaks.dungeon.model.Gobelin;
import fr.draaks.dungeon.model.Guerrier;
import fr.draaks.dungeon.model.Archer;
import fr.draaks.dungeon.model.Donjon;
import fr.draaks.dungeon.model.Mage;
//import fr.draaks.dungeon.model.Items;
import fr.draaks.dungeon.model.Personnage;
//import fr.draaks.dungeon.model.Squelette;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
//import javafx.animation.KeyFrame;
//import javafx.animation.KeyValue;
//import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//import javafx.util.Duration;
import javafx.util.Duration;

public class Menu extends MainRoot {
	
	@FXML private Button Demarrer;
	@FXML private Button Continuer;
	@FXML private Button Online;
	@FXML private Button Parametres;
	@FXML private Button Retour;
	@FXML private Button Musique;
	@FXML private Button Bruitages;
	@FXML private Button Niveau;
	@FXML private Button Update;
	@FXML private ImageView Loading;
	@FXML Label Status;
	@FXML private ImageView Personnage_Image;
	@FXML private TextField Personnage_Nom;
	@FXML private MainRoot Main;
	@FXML TextField Prenom_Inscription;
	@FXML ImageView Guerrier;
	@FXML ImageView Archer;
	@FXML ImageView Mage;
	Personnage joueur;
	private String Personnage_Type="";
	private String perso = "";
	private boolean prenom = false;
	boolean setdraaksid = false;
	GraphicsContext gc;
	Stage stage;
	Donjon dungeon;
	String identifiant = "";
	
	public Menu() {
		super();
	}
	
	public void initialiser() {
		Main.backgroundMusicInit("bib/menu/sounds/menu_sound.mp3"); // on lance la musique de fond
		// désactivation des boutons du menu
		this.Demarrer.setDisable(true);
		this.Continuer.setDisable(true);
		this.Online.setDisable(true);
		this.Parametres.setDisable(true);
		// le temps de la vérification de mise à jour
		verifierUpdate();
	}
	
	public void verifierUpdate() {
		Main.verifierMaJ.start();
		this.Status.setVisible(true);
		this.Loading.setVisible(true);
		// on vérifie les mises à jour en tâche de fond
		Main.verifierMaJ.setOnSucceeded(event -> {
			this.Loading.setVisible(false);
			if (Main.verifierMaJ.getMessage().equals("Votre jeu est à jour.")) {
				this.Status.setVisible(false);
				this.Online.setDisable(false);
				Main.setUpdate("true");
			} else {
				if (Main.verifierMaJ.getMessage().equals("Le serveur ne répond pas...")) {
					this.Loading.setVisible(true);
					this.Status.setText("Tentative de reconnexion en cours...");
					Main.verifierMaJ.restart();
					Main.setUpdate("unknown");
				} else {
					this.Status.setText(Main.verifierMaJ.getMessage());
					Main.verifierMaJ.cancel();
					Main.setUpdate("false");
				}
			}
			this.Online.setDisable(false);
			this.Demarrer.setDisable(false);
			this.Continuer.setDisable(true);
			this.Parametres.setDisable(false);
		});
	}
	
	public void setParentController(MainRoot parentController) {
	    this.Main = parentController;
	    // on donne à notre fenêtre la fenêtre Main parente
	    // cette méthode est présente dans toutes les controleurs
	}
	
	public void demarrerAventure() throws Exception {
		Main.buttonClick();
		Menu Dem = (Menu) Main.changerFenetre("view/Demarrer.fxml", new Menu());
		Dem.setParentController(Main);
		Dem.selectMage();
        
        // Fondu musique
//        Timeline timeline = new Timeline(
//        	    new KeyFrame(Duration.seconds(3),
//        	        new KeyValue(Main.music_background.volumeProperty(), 0)));
//        timeline.play();
	}
	
	public void continuerAventure(ActionEvent event) throws Exception {
		Main.buttonClick();
//		Guerrier Yohan = new Guerrier(0, 0, "Yohan", false);
//		Geant geant = new Geant(0, 0, "Géant");
//		Squelette squelette = new Squelette(0, 0, "Squelette");
//		Gobelin gobelin = new Gobelin(0, 0, "Gobelin");
//		ArrayList<Items> inventaire = new ArrayList<Items>();
//		inventaire.add(new Items(0, 0, "Potion'V", 2, 0, null));
//		inventaire.add(new Items(0, 0, "Potion'R", 1, 0, null));
//		inventaire.add(new Items(0, 0, "Potion'O", 1, 0, null));
//		Yohan.setInventaire(inventaire);
//		int rand = (int) ((Math.random()*6)+1);
//		switch (rand) {
//		case 1:
//			Main.lancerCombat(Yohan, geant);
//			break;
//		case 2:
//			Main.lancerCombat(Yohan, squelette);
//			break;
//		case 3:
//			Main.lancerCombat(Yohan, gobelin);
//			break;
//		default:
//			break;
//		}
//		Tableau_De_Bord hud = (Tableau_De_Bord) Main.changerFenetre("view/Tableau_De_Bord.fxml", new Tableau_De_Bord());
//		hud.setParentController(this.Main);
//		hud.setJoueur(Yohan);
//		hud.initialiser();
	}
	
	public void lancer() {
		LancerAventure lancer = new LancerAventure();
		lancer.setParentController(Main);
		switch (this.Personnage_Type) {
		case "Archer":
			this.joueur = new Archer(0, 0, this.Personnage_Nom.getText(), false);;
			break;
		case "Guerrier":
			this.joueur = new Guerrier(0, 0, this.Personnage_Nom.getText(), false);;
			break;
		case "Mage":
			this.joueur = new Mage(0, 0, this.Personnage_Nom.getText(), false);;
			break;
		}
		lancer.commencer(Main.primaryStage, this.joueur);
	}
	
	public void parametres() throws Exception {
		Main.buttonClick();
		Menu Param = (Menu) Main.changerFenetre("view/Parametres.fxml", new Menu());
		Param.setParentController(Main);
		Param.initParametres();
	}
	
	public void initParametres() {
		// on va d'abord vérifier les paramètres qui sont enregistrés dans le fichier txt
		try {
			BufferedReader reader = new BufferedReader(new FileReader(MainRoot.class.getResource("bib/menu/datas/parametres.txt").getFile()));
			String line = reader.readLine();
			int ligne=0;
			while (line != null) {
				if (ligne==0) {
					if (line.equals("Musiques=1")) {
						this.Musique.setText("Désactiver");
					} else {
						this.Musique.setText("Activer");
					}
				}
				if (ligne==1) {
					if (line.equals("Bruitages=1")) {
						this.Bruitages.setText("Désactiver");
					} else {
						this.Bruitages.setText("Activer");
					}
				}
				if (ligne==2) {
					if (line.equals("Niveau=1")) {
						this.Niveau.setText("Difficile");
					} else {
						this.Niveau.setText("Normal");
					}
				}
				ligne=ligne+1;
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Main.getUpdate().equals("true")) {
			this.Update.setVisible(false);
		}
	}
	
	public void revenirAccueil() throws Exception {
		Main.buttonClick();
		Menu cMenu = (Menu) Main.changerFenetre("view/Menu.fxml", new Menu());
		cMenu.setParentController(this.Main);
		// on revérifie si le jeu était à jour lors de l'ouverture
		if (Main.getUpdate().equals("false")) {
			cMenu.Status.setVisible(true);
			cMenu.Status.setText("Une nouvelle version de votre jeu est disponible.");
		}
	}
	
	// on change les paramètres liés à la musique
	public void musiqueParametres(ActionEvent event) throws IOException {
		Main.buttonClick();
		if (this.Musique.getText().equals("Désactiver")) {
			this.Musique.setText("Activer");
			Main.backgroundMusicPause();
			Main.changerParametres("Musiques", 0);
		} else {
			this.Musique.setText("Désactiver");
			Main.backgroundMusicPlay();
			Main.changerParametres("Musiques", 1);
		}
	}
	
	// paramètres liés aux bruitages
	public void bruitagesParametres(ActionEvent event) throws IOException {
		Main.buttonClick();
		if (this.Bruitages.getText().equals("Désactiver")) {
			this.Bruitages.setText("Activer");
			Main.changerParametres("Bruitages", 0);
		} else {
			this.Bruitages.setText("Désactiver");
			Main.changerParametres("Bruitages", 1);
		}
	}
	
	// paramètres liés au niveau
	// Méthode fonctionnelle, mais n'a pour le moment pas d'impact sur le jeu
	public void niveauParametres(ActionEvent event) throws IOException {
		Main.buttonClick();
		if (this.Niveau.getText().equals("Normal")) {
			this.Niveau.setText("Difficile");
			Main.changerParametres("Niveau", 1);
		} else {
			this.Niveau.setText("Normal");
			Main.changerParametres("Niveau", 0);
		}
	}
	
	public void hover(Button bouton) {
		bouton.setOpacity(0.5);
	}
	
	public void draaksOnline() throws Exception {
		Main.buttonClick();
		this.Demarrer.setDisable(true);
		this.Continuer.setDisable(true);
		this.Online.setDisable(true);
		this.Parametres.setDisable(true);
		this.Status.setVisible(true);
		this.Loading.setVisible(true);
		// on va vérifier la connexion de l'utilisateur + les MàJ
		if (Main.getUpdate().equals("false")) {
			this.Status.setText("Vous devez mettre à jour votre jeu pour accéder aux services en ligne.");
			this.Loading.setVisible(false);
			this.Demarrer.setDisable(false);
			this.Continuer.setDisable(false);
			this.Online.setDisable(false);
			this.Parametres.setDisable(false);
			Main.serveur.cancel();
		} else {
			if (Main.getUpdate().equals("unknown")) {
				this.Status.setText("Le serveur n'a pas répondu.");
				this.Loading.setVisible(false);
				this.Demarrer.setDisable(false);
				this.Continuer.setDisable(false);
				this.Online.setDisable(false);
				this.Parametres.setDisable(false);
				Main.serveur.cancel();
			} else {
				this.Status.setText(Main.serveur.getMessage());
				if (Main.serveur.getMessage().equals("Le serveur ne répond pas...")) {
					Main.serveur.restart();
				} else {
					Main.serveur.restart();
				}
			}
		}
		Main.serveur.setOnRunning(ev -> {
			this.Status.setText("En attente du serveur...");
		});
		Main.serveur.setOnSucceeded(ev -> {
			if (Main.getUpdate().equals("false")) {
				this.Status.setText("Vous devez mettre à jour votre jeu pour accéder aux services en ligne.");
				this.Loading.setVisible(false);
				this.Demarrer.setDisable(false);
				this.Continuer.setDisable(false);
				this.Online.setDisable(false);
				this.Parametres.setDisable(false);
				Main.serveur.cancel();
			} else {
				if (Main.getUpdate().equals("unknown")) {
					this.Status.setText("Le serveur n'a pas répondu.");
					this.Loading.setVisible(false);
					this.Demarrer.setDisable(false);
					this.Continuer.setDisable(false);
					this.Online.setDisable(false);
					this.Parametres.setDisable(false);
					Main.serveur.cancel();
				} else {
					try {
						try {
							// on récupère son identifiant s'il est déjà inscrit
							String nom = MainRoot.class.getResource("bib/menu/datas/draaksid.txt").getFile();
							BufferedReader reader = new BufferedReader(new FileReader(nom));
							this.identifiant = reader.readLine();
							if (this.identifiant.length()>=20) {
								setdraaksid=true;
							}
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (this.setdraaksid) {
							DraaksOnline online = (DraaksOnline) Main.changerFenetre("view/DraaksOnline.fxml", new DraaksOnline());
							online.setParentController(Main);
							online.setIdentifiant(this.identifiant);
							online.initialiser();	
						} else {
							Menu bienvenue = (Menu) Main.changerFenetre("view/FirstDraaksOnline.fxml", new Menu());
							bienvenue.setParentController(Main);
						}
						Main.serveur.cancel();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	// on génère un nom aléatoire parmis les 300 présents dans notre fichier de nom de personnage
	public String randNom() {
		ArrayList<String> noms = new ArrayList<String>();
		int ligne=0;
		String nom = MainRoot.class.getResource("bib/menu/datas/noms_personnages.txt").getFile();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(nom));
			String line = reader.readLine();
			while (line != null) {
				ligne=ligne+1;
				noms.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return noms.get((int) (Math.random()*ligne));
	}
	
	// status de mise à jour, ou d'attente du serveur sur le menu principal
	public void setStatus(String status) {
		this.Status.setText(status);
	}
	
	// sélections des personnages
	public void selectMage() {
		selectPerso("Mage");
	}
	
	public void selectArcher() {
		selectPerso("Archer");
	}
	
	public void selectGuerrier() {
		selectPerso("Guerrier");
	}
	
	public Donjon getDungeon() {
		return dungeon;
	}

	public void setDungeon(Donjon dungeon) {
		this.dungeon = dungeon;
	}

	public Personnage getJoueur() {
		return joueur;
	}

	public void setJoueur(Personnage joueur) {
		this.joueur = joueur;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void selectPerso(String personnage) {
		Main.buttonClick();
		if (!this.Personnage_Type.equals(personnage)) {
			// on lance un bruitage différent en fonction du personnage choisi
			Main.playBruitage("bib/menu/sounds/choisir_" + personnage + ".mp3");
		}
		if (this.Personnage_Nom.isVisible()) {
			this.Personnage_Nom.setText(randNom());
			this.Personnage_Image.setImage(new Image(Main.getClass().getResourceAsStream("bib/persos/" + personnage + "-D.png")));
			this.Personnage_Type=personnage;
		}
	}

	// on vérifie que le nom est correcte
	public void verifierNom() {
		if (this.Personnage_Nom.getText().equals(null)||this.Personnage_Nom.getText().equals("")) {
			this.Continuer.setDisable(true);
		} else {
			this.Continuer.setDisable(false);
		}
	}
	
	// on vérifie que le pseudo et le prenom du joueur sont corrects (DraaksOnline)
	public void verifierPrenomPseudo() {
		this.Personnage_Nom.setText(this.Personnage_Nom.getText().replace("'", ""));
		this.Prenom_Inscription.setText(this.Prenom_Inscription.getText().replace("'", ""));
		this.Personnage_Nom.setText(this.Personnage_Nom.getText().replace("\"", ""));
		this.Prenom_Inscription.setText(this.Prenom_Inscription.getText().replace("\"", ""));
		this.Personnage_Nom.positionCaret(this.Personnage_Nom.getLength());
		this.Prenom_Inscription.positionCaret(this.Prenom_Inscription.getLength());
		if (this.Prenom_Inscription.getText().equals(null)||this.Prenom_Inscription.getText().equals("")||this.Personnage_Nom.getText().equals("")||this.Personnage_Nom.getText().equals(null)) {
			this.prenom = false;
		} else {
			this.prenom = true;
		}
		this.verifierTerminer();
	}
	
	// on affiche le formulaire d'inscription
	public void commencerInscription() throws Exception {
		Main.buttonClick();
		Menu inscription = (Menu) Main.changerFenetre("view/SecondDraaksOnline.fxml", new Menu());
		inscription.setParentController(Main);
	}
	
	public void survolePersonnage(MouseEvent event) {
		ImageView personnage_selectionne = (ImageView) event.getSource();
		if (!this.perso.equals(personnage_selectionne.getId())) {
			personnage_selectionne.setOpacity(0.75);
		}
	}
	
	public void quitterSurvolePersonnage(MouseEvent event) {
		ImageView personnage_selectionne = (ImageView) event.getSource();
		if (!this.perso.equals(personnage_selectionne.getId())) {
			personnage_selectionne.setOpacity(0.5);
		}
	}
	
	public void selectionPersonnage(MouseEvent event) {
		Main.buttonClick();
		this.Archer.setOpacity(0.5);
		this.Guerrier.setOpacity(0.5);
		this.Mage.setOpacity(0.5);
		ImageView personnage_selectionne = (ImageView) event.getSource();
		personnage_selectionne.setOpacity(1);
		this.perso = personnage_selectionne.getId();
		this.verifierTerminer();
	}
	
	public void verifierTerminer() {
		if ((this.prenom)&&(!this.perso.equals(""))) {
			this.Continuer.setDisable(false);
		} else {
			this.Continuer.setDisable(true);
		}
	}
	
	public GraphicsContext getGc() {
		return gc;
	}

	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}

	// on va lancer l'inscription en lancant le service en arrière plan
	public void lancerInscription() {
		Main.buttonClick();
		this.Continuer.setText("Enregistrement en cours...");
		this.Continuer.setDisable(true);
		this.Personnage_Nom.setDisable(true);
		this.Prenom_Inscription.setDisable(true);
		this.Archer.setDisable(true);
		this.Guerrier.setDisable(true);
		this.Mage.setDisable(true);
		// des animations pour donner un peu plus de charme à notre jeu
		if (this.Archer.getOpacity()!=1) {
			FadeTransition ft = new FadeTransition(Duration.millis(2000), this.Archer);
			ft.setFromValue(0.5);
			ft.setToValue(0);
			ft.setCycleCount(1);
			ft.play();
		}
		if (this.Guerrier.getOpacity()!=1) {
			FadeTransition ft = new FadeTransition(Duration.millis(2000), this.Guerrier);
			ft.setFromValue(0.5);
			ft.setToValue(0);
			ft.setCycleCount(1);
			ft.play();
		} else {
			TranslateTransition translate = new TranslateTransition();   
	        translate.setByX(330);
	        translate.setDuration(Duration.millis(3000));
	        translate.setNode(this.Guerrier);
	        translate.play();
		}
		if (this.Mage.getOpacity()!=1) {
			FadeTransition ft = new FadeTransition(Duration.millis(2000), this.Mage);
			ft.setFromValue(0.5);
			ft.setToValue(0);
			ft.setCycleCount(1);
			ft.play();
		} else {
			TranslateTransition translate = new TranslateTransition();   
	        translate.setByX(-380);
	        translate.setDuration(Duration.millis(3000));
	        translate.setNode(this.Mage);
	        translate.play();
		}
		Random rand = new Random();
		for(int i = 0 ; i < 20 ; i++){
		  char c = (char)(rand.nextInt(26) + 97);
		  identifiant += c;
		  identifiant = identifiant + String.valueOf(((int) (Math.random()*19)+1));
		}
		// on lance l'inscription
		this.serveurInscription.restart();
		this.serveurInscription.setOnSucceeded(event -> {
			try {
				Menu menu;
				menu = (Menu) Main.changerFenetre("view/Menu.fxml", new Menu());
				menu.setParentController(Main);
				menu.draaksOnline();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	// on va enregistrer des données dans la BDD
	public Service<Void> serveurInscription = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					Thread.sleep(3000);
					if (Main.getInfos_bdd()[0] == null) {
						defautBDD();
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
					    System.out.println("\nCréation du joueur et du personnage");
					    this.updateMessage("Création du joueur et du personnage...");  

				        statement = connexion.createStatement();
				        
				        String PV = "";
				        String PA = "";
				        String img = "bib/persos/";
				        String type = "";
				        
				        switch (perso) {
				        case "Archer":
				        	PV = "95";
					        PA = "10";
					        img = "bib/persos/Archer-D.png";
					        type = "Archer";
				        	break;
				        case "Guerrier":
				        	PV = "120";
					        PA = "8";
					        img = "bib/persos/Guerrier-D.png";
					        type = "Guerrier";
				        	break;
				        case "Mage":
				        	PV = "80";
					        PA = "11";
					        img = "bib/persos/Mage-D.png";
					        type = "Mage";
				        	break;
				        }
				        
				        statement.executeUpdate("INSERT INTO " + Main.getInfos_bdd()[2] + ".joueurs (pseudo, identifiant, connecte) VALUES ('" + Prenom_Inscription.getText() + "', '" + identifiant + "', '0');");
				        statement.executeUpdate("INSERT INTO " + Main.getInfos_bdd()[2] + ".personnages (nom, img, xp, type, identifiant, PA, PV) VALUES ('" + Personnage_Nom.getText() + "', '" + img + "', 0, '" + type + "', '" + identifiant + "', '" + PA + "', '" + PV + "');");
				        
				        Writer fileWriter = new FileWriter(Main.getClass().getResource("bib/menu/datas/draaksid.txt").getFile());
				        fileWriter.write(identifiant);
				        fileWriter.close();
					} catch (SQLException e) {
						System.out.println("\nErreur avec la BDD.");
						System.err.println("\nSQLState: " +
			                    ((SQLException)e).getSQLState());

			                System.err.println("Error Code: " +
			                    ((SQLException)e).getErrorCode());

			                System.err.println("Message: " + e.getMessage());
			                this.updateMessage("Le serveur ne répond pas...");
					} finally {
					    if (connexion != null)
					        try {
					            connexion.close();
					        } catch (SQLException ignore) {
					            
					        }
					}
					return null;
				}
				
			};
		}
		
	};

}
