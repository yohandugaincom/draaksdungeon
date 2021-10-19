package fr.draaks.dungeon.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.draaks.dungeon.MainRoot;
import fr.draaks.dungeon.model.Archer;
import fr.draaks.dungeon.model.Guerrier;
import fr.draaks.dungeon.model.Mage;
import fr.draaks.dungeon.model.Personnage;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class DraaksOnline extends MainRoot {
	
	MainRoot Main;
	@FXML ImageView Cercle1;
	@FXML ImageView Cercle2;
	@FXML Label XP;
	@FXML Label XP_string;
	@FXML Label Niveau;
	@FXML Label Niveau_string;
	@FXML Label Statut;
	@FXML Label Nom_J1;
	@FXML Label Nom_J2;
	@FXML ImageView Status_icone;
	@FXML ImageView J1_image;
	@FXML ImageView J2_image;
	@FXML Label Titre;
	@FXML Label RejoindreTexte;
	@FXML TextField Numero_Salle;
	@FXML Button Rejoindre_Salle;
	@FXML ImageView Action1;
	@FXML ImageView Action2;
	@FXML ImageView Action3;
	@FXML ImageView Action4;
	@FXML ImageView Preparation;
	@FXML VBox J1;
	@FXML VBox J2;
	@FXML Label Joueurs_Connectes;
	String recherche = "";
	String salle;
	String identifiant;
	String pseudo;
	String nom_J1 = "...";
	String type_J1 = "";
	String img_J1 = "";
	String xp_J1 = "0";
	String PV_J1;
	String PA_J1;
	String nom_J2 = "...";
	String type_J2 = "";
	String img_J2 = "";
	String xp_J2 = "0";
	String PV_J2;
	String PA_J2;
	String identifiant_J2;
    int connectes_nombre = 0;
	Connection connexion = null;
    Statement statement = null;
    ResultSet resultat = null;
	Personnage perso_J1;
	Personnage perso_J2;
	
	public DraaksOnline() {
		
	}
	
	public void initialiser() {
		this.J1.setTranslateX(-350);
		this.J2.setTranslateX(350);
		this.J2_image.setScaleX(-1);
		this.XP_string.setVisible(false);
		this.Niveau_string.setVisible(false);
		this.actionsDisabled(true);
		FadeTransition fondu_statut = this.statut("Connexion en cours...");
		RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(720);
        rotate.setDuration(Duration.millis(3000));
        rotate.setAutoReverse(true);
        rotate.setNode(this.Cercle1);
        rotate.play();
    	FadeTransition fondu = new FadeTransition(Duration.millis(2000), this.XP);
		fondu.setFromValue(0);
		fondu.setToValue(1.0);
		fondu.play();
		fondu.setOnFinished(e -> {
			this.XP_string.setVisible(true);
			this.XP_string.setOpacity(0);
			FadeTransition fond = new FadeTransition(Duration.millis(1000), this.XP_string);
			fond.setFromValue(0);
			fond.setToValue(1.0);
			fond.play();
		});
		RotateTransition rotate2 = new RotateTransition();
        rotate2.setAxis(Rotate.Z_AXIS);
        rotate2.setByAngle(720);
        rotate2.setDuration(Duration.millis(3000));
        rotate2.setAutoReverse(true);
        rotate2.setNode(this.Cercle2);
        rotate2.play();
    	FadeTransition fondu2 = new FadeTransition(Duration.millis(2000), this.Niveau);
		fondu2.setFromValue(0);
		fondu2.setToValue(1.0);
		fondu2.play();
		fondu2.setOnFinished(e -> {
			this.Niveau_string.setVisible(true);
			this.Niveau_string.setOpacity(0);
			FadeTransition fond = new FadeTransition(Duration.millis(1000), this.Niveau_string);
			fond.setFromValue(0);
			fond.setToValue(1.0);
			fond.play();
			this.Titre.setText("Bonjour " + this.getPseudo());
			this.actionsDisabled(false);
			fondu_statut.stop();
			this.Status_icone.setOpacity(1);
			this.Statut.setText("Vous êtes en ligne.");
			TranslateTransition translate = new TranslateTransition();   
	        translate.setByX(350);
	        translate.setDuration(Duration.millis(1500));  
	        translate.setCycleCount(1); 
	        translate.setNode(this.J1);  
	        translate.play();
	        this.Nom_J1.setText(nom_J1);
	        this.Joueurs_Connectes.setText(String.valueOf(this.connectes_nombre) + " joueurs connectés");
		});
		this.connecte.restart();
		this.connecte.setOnSucceeded(e -> {
            this.XP_string.setText(xp_J1);
            this.J1_image.setImage(new Image(Main.getClass().getResourceAsStream(img_J1)));
            this.Niveau_string.setText(String.valueOf((int)(Integer.parseInt(xp_J1)/1000)+1));
            int size = this.XP_string.getText().length();
            this.Niveau_string.setStyle("-fx-font-size: 64px;-fx-font-weight:bold;");
            this.XP_string.setStyle("-fx-font-size: " + String.valueOf(68-(size*4)) + "px; -fx-font-weight:bold;");
		});
	}
	
	public void actionsDisabled(boolean value) {
		this.Action1.setDisable(value);
		this.Action2.setDisable(value);
		this.Action3.setDisable(value);
		this.Action4.setDisable(value);
	}
	
	public void setParentController(MainRoot parentController) {
	    this.Main = parentController;
	}
	
	public FadeTransition statut(String texte) {
		this.Statut.setText(texte);
		FadeTransition ft = new FadeTransition(Duration.millis(200), this.Status_icone);
		ft.setFromValue(0.5);
		ft.setToValue(1.0);
		ft.setCycleCount(Timeline.INDEFINITE);
		ft.setAutoReverse(true);
		ft.play();
		return ft;
	}
	
	public void seDeconnecter() throws Exception {
		this.deconnecte.restart();
		Main.buttonClick();
		this.Statut.setText("Déconnexion en cours...");
		FadeTransition ft = new FadeTransition(Duration.millis(200), this.Status_icone);
		ft.setFromValue(0.5);
		ft.setToValue(1.0);
		ft.setCycleCount(14);
		ft.setAutoReverse(true);
		ft.play();
		ft.setOnFinished(event -> {
		    String path = MainRoot.class.getResource("bib/menu/sounds/alert.mp3").toString();
			Media media = new Media(path);
			MediaPlayer soundClick = new MediaPlayer(media);
			soundClick.play();
			try {
				Menu cMenu;
				cMenu = (Menu) Main.changerFenetre("view/Menu.fxml", new Menu());
				cMenu.setParentController(Main);
				if (Main.getUpdate().equals("false")) {
					cMenu.Status.setVisible(true);
					cMenu.Status.setText("Une nouvelle version de votre jeu est disponible.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void creerSalle() {
		Main.buttonClick();
		this.Action4.requestFocus();
		this.Action1.setOpacity(0.2);
		this.Action2.setOpacity(0.2);
		this.Action3.setOpacity(0.2);
		this.Action1.setDisable(true);
		this.Action2.setDisable(true);
		this.Action3.setDisable(true);
		this.Statut.setText("Création de la salle...");
		FadeTransition ft = new FadeTransition(Duration.millis(200), this.Status_icone);
		ft.setFromValue(0.5);
		ft.setToValue(1.0);
		ft.setCycleCount(14);
		ft.setAutoReverse(true);
		ft.play();
		ft.setOnFinished(event -> {
		    String path = MainRoot.class.getResource("bib/menu/sounds/alert.mp3").toString();
			Media media = new Media(path);
			MediaPlayer soundClick = new MediaPlayer(media);
			soundClick.play();
			try {
				FadeTransition ft2 = new FadeTransition(Duration.millis(200), this.Status_icone);
				ft2.setFromValue(0.5);
				ft2.setToValue(1.0);
				ft2.setCycleCount(Timeline.INDEFINITE);
				ft2.setAutoReverse(true);
				ft2.play();
				int salle = (int) ((Math.random()*999999)+1);
				this.Statut.setText("En attente d'un joueur...");
				this.Titre.setText("Salle n°" + String.valueOf(salle));
				this.setSalle(String.valueOf(salle));
				this.creerUneSalle.restart();
				this.creerUneSalle.setOnSucceeded(e -> {
				    String path2 = MainRoot.class.getResource("bib/menu/sounds/alert.mp3").toString();
					Media media2 = new Media(path2);
					MediaPlayer soundClick2 = new MediaPlayer(media2);
					soundClick2.play();
					this.Action1.setVisible(false);
					this.Action2.setVisible(false);
					this.Action3.setVisible(false);
					this.Action4.setVisible(false);
					this.Cercle1.setVisible(false);
					this.Cercle2.setVisible(false);
					this.XP.setVisible(false);
					this.Niveau.setVisible(false);
					this.XP_string.setVisible(false);
					this.Niveau_string.setVisible(false);
					this.Statut.setText("Préparation au combat...");
					this.preparationCombat();
					this.Preparation.setVisible(true);
					FadeTransition ft3 = new FadeTransition(Duration.millis(1500), this.Preparation);
					ft3.setFromValue(0.2);
					ft3.setToValue(1.0);
					ft3.setCycleCount(Timeline.INDEFINITE);
					ft3.setAutoReverse(true);
					ft3.play();
        			TranslateTransition translate = new TranslateTransition();   
        	        translate.setByX(-350);
        	        translate.setDuration(Duration.millis(1500));  
        	        translate.setCycleCount(1); 
        	        translate.setNode(this.J2);  
        	        translate.play();
        	        this.Nom_J2.setText(this.nom_J2);
                    this.J2_image.setImage(new Image(Main.getClass().getResourceAsStream(img_J2)));
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void preparationCombat() {
		this.debutCombat.restart();
		this.debutCombat.setOnSucceeded(event -> {
			switch (this.type_J1) {
			case "Mage":
				perso_J1 = new Mage(this.nom_J1, Integer.parseInt(this.PV_J1), Integer.parseInt(this.PA_J1), Integer.parseInt(this.xp_J1));
				break;
			case "Guerrier":
				perso_J1 = new Guerrier(this.nom_J1, Integer.parseInt(this.PV_J1), Integer.parseInt(this.PA_J1), Integer.parseInt(this.xp_J1));
				break;
			case "Archer":
				perso_J1 = new Archer(this.nom_J1, Integer.parseInt(this.PV_J1), Integer.parseInt(this.PA_J1), Integer.parseInt(this.xp_J1));
				break;
			}
			switch (this.type_J2) {
			case "Mage":
				perso_J2 = new Mage(this.nom_J2, Integer.parseInt(this.PV_J2), Integer.parseInt(this.PA_J2), Integer.parseInt(this.xp_J2));
				break;
			case "Guerrier":
				perso_J2 = new Guerrier(this.nom_J2, Integer.parseInt(this.PV_J2), Integer.parseInt(this.PA_J2), Integer.parseInt(this.xp_J2));
				break;
			case "Archer":
				perso_J2 = new Archer(this.nom_J2, Integer.parseInt(this.PV_J2), Integer.parseInt(this.PA_J2), Integer.parseInt(this.xp_J2));
				break;
			}
			try {
				Main.lancerCombatOnline(perso_J1, perso_J2, this.img_J1, this.img_J2, this.getSalle(), this.identifiant);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void rejoindreSalle() {
		if (this.Action1.isVisible()) {
			this.Action1.setVisible(false);
			this.RejoindreTexte.setVisible(true);
			this.Rejoindre_Salle.setVisible(true);
			this.Numero_Salle.setVisible(true);
		} else {
			this.Action1.setVisible(true);
			this.RejoindreTexte.setVisible(false);
			this.Rejoindre_Salle.setVisible(false);
			this.Numero_Salle.setVisible(false);
		}
	}
	
	public void rechercherSalle() throws SQLException {
		resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".salle_draaks_online WHERE salle=\"" + this.Numero_Salle.getText() + "\";");
        while (resultat.next()) {
        	recherche = resultat.getString("connecte");
        }
    	this.setSalle(this.Numero_Salle.getText());
        Main.buttonClick();
		this.Statut.setText("Recherche en cours...");
		FadeTransition ft = new FadeTransition(Duration.millis(200), this.Status_icone);
		ft.setFromValue(0.5);
		ft.setToValue(1.0);
		ft.setCycleCount(14);
		ft.setAutoReverse(true);
		ft.play();
		ft.setOnFinished(event -> {
		    if (recherche.equals("")) {
		    	this.Statut.setText("Aucune salle n'a été trouvée.");
		    } else {
				ft.setCycleCount(14);
				ft.play();
				this.Statut.setText("Configuration de la salle...");
				this.Titre.setText("Salle n°" + this.getSalle());
		    	this.Action1.setVisible(true);
				this.RejoindreTexte.setVisible(false);
				this.Rejoindre_Salle.setVisible(false);
				this.Numero_Salle.setVisible(false);
		    	this.Action4.requestFocus();
				this.Action1.setOpacity(0.2);
				this.Action2.setOpacity(0.2);
				this.Action3.setOpacity(0.2);
				this.Action1.setDisable(true);
				this.Action2.setDisable(true);
				this.Action3.setDisable(true);
				ft.setOnFinished(e -> {
				    String path = MainRoot.class.getResource("bib/menu/sounds/alert.mp3").toString();
					Media media = new Media(path);
					MediaPlayer soundClick = new MediaPlayer(media);
					soundClick.play();
					try {
						FadeTransition ft2 = new FadeTransition(Duration.millis(200), this.Status_icone);
						ft2.setFromValue(0.5);
						ft2.setToValue(1.0);
						ft2.setCycleCount(Timeline.INDEFINITE);
						ft2.setAutoReverse(true);
						ft2.play();
						this.Statut.setText("En attente d'un joueur...");
						this.rejoindreUneSalle.restart();
						this.rejoindreUneSalle.setOnSucceeded(ev -> {
						    String path2 = MainRoot.class.getResource("bib/menu/sounds/alert.mp3").toString();
							Media media2 = new Media(path2);
							MediaPlayer soundClick2 = new MediaPlayer(media2);
							soundClick2.play();
							this.Action1.setVisible(false);
							this.Action2.setVisible(false);
							this.Action3.setVisible(false);
							this.Action4.setVisible(false);
							this.Cercle1.setVisible(false);
							this.Cercle2.setVisible(false);
							this.XP.setVisible(false);
							this.Niveau.setVisible(false);
							this.XP_string.setVisible(false);
							this.Niveau_string.setVisible(false);
							this.Statut.setText("Préparation au combat...");
							this.preparationCombat();
							this.Preparation.setVisible(true);
							FadeTransition ft3 = new FadeTransition(Duration.millis(1500), this.Preparation);
							ft3.setFromValue(0.2);
							ft3.setToValue(1.0);
							ft3.setCycleCount(Timeline.INDEFINITE);
							ft3.setAutoReverse(true);
							ft3.play();
		        			TranslateTransition translate = new TranslateTransition();   
		        	        translate.setByX(-350);
		        	        translate.setDuration(Duration.millis(1500));  
		        	        translate.setCycleCount(1); 
		        	        translate.setNode(this.J2);  
		        	        translate.play();
		        	        this.Nom_J2.setText(this.nom_J2);
		                    this.J2_image.setImage(new Image(Main.getClass().getResourceAsStream(img_J2)));
						});
					} catch (Exception ez) {
						ez.printStackTrace();
					}
				});
		    }
		});
	}
	
	public String getSalle() {
		return salle;
	}

	public void setSalle(String salle) {
		this.salle = salle;
	}

	public String getIdentifiant() {
		return identifiant;
	}
	
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public Service<Void> deconnecte = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".joueurs SET connecte='0' WHERE identifiant='" + getIdentifiant() + "';");
					System.out.println("Déconnexion de l'utilisateur...");
					return null;
				}
				
			};
		}
		
	};
	
	public Service<Void> debutCombat = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					    System.out.println("\nPréparation au combat...");
					    Thread.sleep(10000);
				        statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".salle_draaks_online SET readyJ1='1' WHERE salle='" + getSalle() + "';");
					return null;
				}
				
			};
		}
		
	};

	public Service<Void> creerUneSalle = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					    System.out.println("\nCréation de la salle...");

				        statement.executeUpdate("INSERT INTO " + Main.getInfos_bdd()[2] + ".salle_draaks_online (salle, connecte, J1, readyJ1, readyJ2, J2, dateJ1, dateJ2, actionJ1, actionJ2, PVJ1, PVJ2) VALUES ('" + getSalle() + "', '0', '" + getIdentifiant() + "', '1', '0', '', '', '', '', '', '', '');");
					    
				        resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".salle_draaks_online WHERE salle=\"" + getSalle() + "\";");
				        
				        while (resultat.next()) {
				            String connecte = resultat.getString("connecte");
				            System.out.println("\nEn attente d'un autre joueur...");
				            while (connecte.equals("0")) {
				            	Thread.sleep(250);
				            	resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".salle_draaks_online WHERE salle=\"" + getSalle() + "\";");
				            	while (resultat.next()) {
				            		connecte = resultat.getString("connecte");
				            		identifiant_J2 = resultat.getString("J2");
				            	}
				            }
				        }
				        
				        resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".personnages WHERE identifiant='" + identifiant_J2 + "';");
				        
				        while (resultat.next()) {
				            nom_J2 = resultat.getString("nom");
				            type_J2 = resultat.getString("type");
				            img_J2 = resultat.getString("img");
				            xp_J2 = resultat.getString("xp");
				            PA_J2 = resultat.getString("PA");
				            PV_J2 = resultat.getString("pv");
				        }
					return null;
				}
				
			};
		}
		
	};
	
	public Service<Void> rejoindreUneSalle = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {

				        statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".salle_draaks_online SET connecte='1', J2='" + getIdentifiant() + "' WHERE salle='" + getSalle() + "';");
					    
				        resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".salle_draaks_online WHERE salle=\"" + getSalle() + "\";");
				        
				        while (resultat.next()) {
				            String connecte = resultat.getString("connecte");
				            identifiant_J2 = resultat.getString("J1");
				            System.out.println("\nEn attente d'un autre joueur...");
				            while (connecte.equals("0")) {
				            	Thread.sleep(1000);
				            	resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".salle_draaks_online WHERE salle=\"" + getSalle() + "\";");
				            	while (resultat.next()) {
				            		connecte = resultat.getString("connecte");
				            		identifiant_J2 = resultat.getString("J1");
				            	}
				            }
				        }
				        
				        resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".personnages WHERE identifiant='" + identifiant_J2 + "';");
				        
				        while (resultat.next()) {
				            nom_J2 = resultat.getString("nom");
				            type_J2 = resultat.getString("type");
				            img_J2 = resultat.getString("img");
				            xp_J2 = resultat.getString("xp");
				            PA_J2 = resultat.getString("PA");
				            PV_J2 = resultat.getString("pv");
				        }
					return null;
				}
				
			};
		}
		
	};

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

				        statement.executeUpdate("UPDATE " + Main.getInfos_bdd()[2] + ".joueurs SET connecte='1' WHERE identifiant='" + getIdentifiant() + "';");
					    
				        resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".joueurs WHERE identifiant='" + getIdentifiant() + "';");
				        
				        while (resultat.next()) {
				            pseudo = resultat.getString("pseudo");
				        }
				        
				        resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".joueurs WHERE connecte='1';");
				        
				        while (resultat.next()) {
				            connectes_nombre = connectes_nombre + 1;
				        }
				        
				        resultat = statement.executeQuery("SELECT * FROM " + Main.getInfos_bdd()[2] + ".personnages WHERE identifiant='" + getIdentifiant() + "';");
				        
				        while (resultat.next()) {
				            nom_J1 = resultat.getString("nom");
				            type_J1 = resultat.getString("type");
				            img_J1 = resultat.getString("img");
				            xp_J1 = resultat.getString("xp");
				            PA_J1 = resultat.getString("PA");
				            PV_J1 = resultat.getString("pv");
				        }
				        
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
