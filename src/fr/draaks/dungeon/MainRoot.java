/*	SI LA COMPILATION GENERE UNE ERREUR, MERCI DE LIRE LE README.
 * 	EN EFFET, IL EST NECESSAIRE D'Y AJOUTER LA LIBRAIRIE JDBC MANUELLEMENT,
 * 	POUR QUE CELA PUISSE FONCTIONNER.
 */
package fr.draaks.dungeon;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.draaks.dungeon.model.Donjon;
import fr.draaks.dungeon.model.Entity;
import fr.draaks.dungeon.model.Mobs;
import fr.draaks.dungeon.model.Personnage;
import fr.draaks.dungeon.view.Menu;
import fr.draaks.dungeon.view.Tableau_De_Bord;
import fr.draaks.dungeon.view.ZoneCombat;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainRoot extends Application {

	public Stage primaryStage;
	private BorderPane root;
	private String update;
	public MediaPlayer music_background;
	private String[] infos_bdd = new String[6];
	public static String version = "0.0.1";
	public static String texture = "bib";
	private Scene scene;
	Connection connexion = null;
    public Statement statement = null;
    ResultSet resultat = null;

	@Override
	public void start(Stage primaryStage) {
		System.setProperty( "file.encoding", "UTF-8" );
		// on change l'encodage pour accepter les accents sur tous les appareils
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Draak's Dungeon");
		this.primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("bib/general/icone.png")));
		this.primaryStage.setResizable(false);
		initialiser();
		menu();
	}

	public void initialiser() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainRoot.class.getResource("view/rootView.fxml"));
			loader.setController(this);
			root = (BorderPane) loader.load();
			// on va charger le contenu qui se trouve dans le fichier FXMl
			this.scene = new Scene(root);
			primaryStage.setScene(scene);
			// on définit notre scène dans notre stage primaire
			primaryStage.show();
			// et on l'affiche
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// fonctionne comme en haut mais pour charger le contenu dans le centre du BorderPane
	public void menu() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainRoot.class.getResource("view/menu.fxml"));
			loader.setController(new Menu());
			AnchorPane connexion = (AnchorPane) loader.load();
			Menu menu = loader.getController();
			menu.setParentController(this);
			menu.initialiser();
			root.setCenter(connexion);
			// on centre ici le contenu dans le centre du BorderPane
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// la musique est géré par cette classe mère, pour qu'on ait toujours "la main" sur la musique
	public void backgroundMusicInit(String bib_file) {
		if (this.music_background!=null) {
			this.music_background.stop();
			this.music_background.setVolume(1);
		}
		String path = MainRoot.class.getResource(bib_file).toString();
	    Media media = new Media(path);
	    this.music_background = new MediaPlayer(media);
	    this.music_background.setCycleCount(MediaPlayer.INDEFINITE);
	    // la musique se répète en boucle
	    String nom = MainRoot.class.getResource("bib/menu/datas/parametres.txt").getFile();
		try {
			// on vérifie ici que le joueur n'a pas désactivé la musique dans les paramètres
			BufferedReader reader = new BufferedReader(new FileReader(nom));
			String line = reader.readLine();
			int ligne=0;
			while (line != null) {
				if (line.equals("Musiques=1")&&ligne==0) {
					this.music_background.play();
					// si la musique est activé on peut la lancer
				}
				ligne=ligne+1;
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Play la musique
	public void backgroundMusicPlay() {
	    this.music_background.play();
	}
	
	// Pause la musique
	public void backgroundMusicPause() {
		this.music_background.pause();
	}
	
	// on va ici simuler le bruit d'un clique sur le bouton
	public void buttonClick() {
		String path = MainRoot.class.getResource("bib/menu/sounds/click.mp3").toString();
		Media media = new Media(path);
		MediaPlayer soundClick = new MediaPlayer(media);
		String nom = MainRoot.class.getResource("bib/menu/datas/parametres.txt").getFile();
		try {
			// on vérifie comme la musique en haut que le joueur n'a pas désactivé les bruitages
			BufferedReader reader = new BufferedReader(new FileReader(nom));
			String line = reader.readLine();
			int ligne=0;
			while (line != null) {
				if (line.equals("Bruitages=1")&&ligne==1) {
					soundClick.play();
				}
				ligne=ligne+1;
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// on vérifie ici par un booléen si le joueur a désactivé ou non les bruitages
	public boolean isBruitages() {
		boolean bruitage=false;
		String nom = MainRoot.class.getResource("bib/menu/datas/parametres.txt").getFile();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(nom));
			String line = reader.readLine();
			int ligne=0;
			while (line != null) {
				if (line.equals("Bruitages=1")&&ligne==1) {
					bruitage=true;
				}
				ligne=ligne+1;
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bruitage;
	}
	
	// on va faire comme la musique, et laisser le Main gérer les bruitages du jeu
	// bib_file représente un fichier qui se trouve au même niveau du MainRoot dans la source
	public void playBruitage(String bib_file) {
		if (this.isBruitages()) {
			String path = MainRoot.class.getResource(bib_file).toString();
			Media media = new Media(path);
			MediaPlayer bruitage = new MediaPlayer(media);
			bruitage.play();
		}
	}
	
	// on va ici définir le param1 par ce qu'il ya dans le param2
	// comme par exemple savoir si le joueur veut de la musique, ou des bruitages
	public void changerParametres(String param1, int param2) {
		try {
	        BufferedReader file = new BufferedReader(new FileReader(MainRoot.class.getResource("bib/menu/datas/parametres.txt").getFile()));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;
	        while ((line = file.readLine()) != null) {
	            inputBuffer.append(line);
	            inputBuffer.append('\n');
	        }
	        file.close();
	        String inputStr = inputBuffer.toString();
	        if (param2==0) {
	            inputStr = inputStr.replace(param1 + "=1", param1 + "=0"); 
	        } else if (param2==1) {
	            inputStr = inputStr.replace(param1 + "=0", param1 + "=1");
	        }
	        System.out.println("----------------NOUVEAUX PARAMETRES------------------\n" + inputStr);
	        FileOutputStream fileOut = new FileOutputStream(MainRoot.class.getResource("bib/menu/datas/parametres.txt").getFile());
	        fileOut.write(inputStr.getBytes());
	        fileOut.flush();
	        fileOut.close();

	    } catch (Exception e) {
	        System.out.println("Fichiers de paramétrages introuvable.");
	    }
	}
	
	// La base de données utilisée pour le multijoueur, les sauvegardes et les mises à jour du jeu
	public void defautBDD() {
		this.infos_bdd[0] = "remotemysql.com";
		this.infos_bdd[1] = "3306";
		this.infos_bdd[2] = "P2v7B9Brvo";
		this.infos_bdd[3] = "P2v7B9Brvo";
		this.infos_bdd[4] = "lcSq4YD1WF";
	}
	
	public String getVersion() {
		return version;
	}

	public String[] getInfos_bdd() {
		return infos_bdd;
	}

	public void setInfos_bdd(String[] infos_bdd) {
		this.infos_bdd = infos_bdd;
	}
	
	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public static String getTexture() {
		return texture;
	}

	public static void setTexture(String texture) {
		MainRoot.texture = texture;
	}
	
	// si la connexion est perdue cette fenêtre se lance
	// Méthode fonctionnelle mais pas encore implémentée
	public void connexionPerdue() throws Exception {
		// M�thode pas encore termin�e
		this.scene.setFill(Color.BLACK);
		this.primaryStage.setOpacity(0.7);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/Reconnexion.fxml"));
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
	}
	
	// Remplacer le String Ennemi par l'objet Ennemi
	public void lancerCombat(Entity Joueur, Mobs Ennemi) throws Exception {
		this.backgroundMusicInit("bib/general/sounds/zoneCombat_sound.mp3");
        ZoneCombat zCombat = (ZoneCombat) this.changerFenetre("view/ZoneCombat.fxml", new ZoneCombat());
        zCombat.setParentController(this);
        zCombat.initialiser(Joueur, Ennemi);
		zCombat.setTitre(Ennemi.getName() + " vous attaque");
	}

	// Remplacer le String Ennemi par l'objet Ennemi
	public void lancerCombatHistoire(Stage stage, Donjon dungeon, Entity Joueur, GraphicsContext gc, Mobs Ennemi) throws Exception {
		this.backgroundMusicInit("bib/general/sounds/zoneCombat_sound.mp3");
        ZoneCombat zCombat = (ZoneCombat) this.changerFenetreStage(stage, "view/ZoneCombat.fxml", new ZoneCombat());
        zCombat.setParentController(this);
        zCombat.initialiser(Joueur, Ennemi);
        zCombat.setDonjonGC(dungeon, gc);
		zCombat.setTitre(Ennemi.getName() + " vous attaque");
	}
	
	public void lancerHUDHistoire(Stage stage, Donjon dungeon, Personnage Joueur, GraphicsContext gc) throws Exception {
		Tableau_De_Bord hud = (Tableau_De_Bord) this.changerFenetreStage(stage, "view/Tableau_De_Bord.fxml", new Tableau_De_Bord());
		hud.setParentController(this);
		hud.setJoueur(Joueur);
		hud.setStage(stage);
		hud.setDungeon(dungeon);
		hud.initialiser();
		hud.setGc(gc);
	}
	
	public void patienter(Stage stage, Donjon dungeon, Personnage Joueur, GraphicsContext gc) throws Exception {
		Tableau_De_Bord soon = (Tableau_De_Bord) this.changerFenetreStage(stage, "view/BientotDisponible.fxml", new Tableau_De_Bord());
		soon.setParentController(this);
		soon.setJoueur(Joueur);
		soon.setStage(stage);
		soon.setDungeon(dungeon);
		soon.setGc(gc);
	}
	
	// On lance ici la fenêtre d'un combat multijoueur (donc entre 2 joueurs et non pas 1 J et 1 mob)
	public void lancerCombatOnline(Personnage perso_J1, Personnage perso_J2, String image_J1, String image_J2, String Salle, String identifiant) throws Exception {
		this.backgroundMusicInit("bib/general/sounds/zoneCombat_sound.mp3");
        ZoneCombat zCombat = (ZoneCombat) this.changerFenetre("view/ZoneCombat.fxml", new ZoneCombat());
        zCombat.setParentController(this);
        zCombat.setSalle(Salle);
        zCombat.setIdentifiant(identifiant);
        zCombat.initialiserOnline(perso_J1, perso_J2, image_J1, image_J2);
		zCombat.setTitre(perso_J2.getName() + " vous défie");
	}
	
	// une méthode qui permets de changer de fenêtre très rapidement depuis le MainRoot
	// on oubliera pas d'utiliser la méthode setParentController pour toujours laisser la main au Main
	public Object changerFenetre(String fxml_file, Object controller) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml_file));
		loader.setController(controller);
		Scene nouvelleScene = new Scene(loader.load());
        Stage window = (Stage) this.getScene().getWindow();
        window.setScene(nouvelleScene);
        this.scene = nouvelleScene;
        return controller;
	}
	
	public Object changerFenetreStage(Stage stage, String fxml_file, Object controller) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml_file));
		loader.setController(controller);
		Scene nouvelleScene = new Scene(loader.load());
        stage.setScene(nouvelleScene);
        this.scene = nouvelleScene;
        return controller;
	}

	// on lance ici un service en arrière plan
	// on vérifie que le joueur est bien connecté au serveur, et que les bons drivers sont installés
	public Service<Void> serveur = new Service<Void>() {

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					Thread.sleep(1000);
					if (getInfos_bdd()[0] == null) {
						defautBDD();
					}
					try {
				        Class.forName("com.mysql.cj.jdbc.Driver");
				    } catch ( ClassNotFoundException e ) {
				    	System.out.println( "Erreur lors du chargement du driver \t"
				                + e.getMessage() );
				    	this.updateMessage("Veuillez réparer les fichiers de votre jeu.");
				    }
					String url = "jdbc:mysql://" + getInfos_bdd()[0] + ":" + getInfos_bdd()[1] + "/" + getInfos_bdd()[2] + "?connectTimeout=3000&useUnicode=true&characterEncoding=UTF-8";
					String utilisateur = getInfos_bdd()[3];
					String motDePasse = getInfos_bdd()[4];
					Connection connexion = null;
					try {
					    connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
					    System.out.println("\nConnexion réussie avec la BDD.");
					    this.updateMessage("Récupération de vos données...");  
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
	

	// ce service vérifie grâce à la BDD si le joueur a la bonne version du jeu
	public Service<Void> verifierMaJ = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					Thread.sleep(500);
					if (getInfos_bdd()[0] == null) {
						defautBDD();
					}
					try {
				        Class.forName("com.mysql.cj.jdbc.Driver");
				    } catch ( ClassNotFoundException e ) {
				    	System.out.println( "Erreur lors du chargement du driver \t"
				                + e.getMessage() );
				    	this.updateMessage("Veuillez réparer les fichiers de votre jeu.");
				    }
					String url = "jdbc:mysql://" + getInfos_bdd()[0] + ":" + getInfos_bdd()[1] + "/" + getInfos_bdd()[2] + "?connectTimeout=3000&useUnicode=true&characterEncoding=UTF-8";
					String utilisateur = getInfos_bdd()[3];
					String motDePasse = getInfos_bdd()[4];
					Connection connexion = null;
					try {
					    connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
					    System.out.println("\nConnexion réussie avec la BDD.");
					    this.updateMessage("Vérification des mises à jour disponibles...");  

				        statement = connexion.createStatement();
				        System.out.println("SELECT * FROM " + getInfos_bdd()[2] + ".launcher WHERE version=\"" + getVersion() + "\";");

				        resultat = statement.executeQuery("SELECT * FROM " + getInfos_bdd()[2] + ".launcher WHERE version=\"" + getVersion() + "\";");

				        while ( resultat.next() ) {
				            String last_update_version = resultat.getString("last_update_version");
				            if (last_update_version.equals(getVersion())) {
				            	this.updateMessage("Votre jeu est à jour.");
				            } else {
				            	this.updateMessage("Une nouvelle version de votre jeu est disponible.");
				            }
				        }
					    
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
	
	// un tooltip en Java 8, ne peut s'afficher que quelques secondes
	// L'affiche commence aussi au bout d'un certain délai
	public void configurerTooltip(Tooltip tooltip) {
	    try {
	        Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
	        fieldBehavior.setAccessible(true);
	        Object objBehavior = fieldBehavior.get(tooltip);

	        // on va donc ici modifier directement les caractéristiques de notre Toottip pour gérer son temps
	        Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
	        fieldTimer.setAccessible(true);
	        Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);
	     // on modifie ici au bout de combien de temps il se lance => 50ms
	        objTimer.getKeyFrames().clear();
	        objTimer.getKeyFrames().add(new KeyFrame(new Duration(50)));
	        fieldTimer = objBehavior.getClass().getDeclaredField("hideTimer");
	        fieldTimer.setAccessible(true);
	        objTimer = (Timeline) fieldTimer.get(objBehavior);

	        objTimer.getKeyFrames().clear();
	        objTimer.getKeyFrames().add(new KeyFrame(Duration.INDEFINITE));
		     // on modifie ici son temps d'apparition à l'infini
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// cette méthode nous permets juste de simplifier l'affichage d'un popup sur un objet
	// on y applique donc directement la fonction ci-dessus à tous nos Tooltips
	public Tooltip popup(String texte) {
		Tooltip ttp = new Tooltip(texte);
		this.configurerTooltip(ttp);
		ttp.setStyle("-fx-font-size: 18px");
		return ttp;
	}
	
	// même version qu'en haut, mais en ajoutant une image à gauche dans notre Tooltip
	public Tooltip popupImage(String texte, String bib_image) {
		Tooltip ttp = new Tooltip(texte);
		this.configurerTooltip(ttp);
		if (!bib_image.equals("")) {
			Image image = new Image(this.getClass().getResourceAsStream(bib_image), 150, 150, true, true);
			ttp.setGraphic(new ImageView(image));
		}
		ttp.setStyle("-fx-font-size: 15px");
		return ttp;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
}
