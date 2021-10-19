package fr.draaks.dungeon.view;

import fr.draaks.dungeon.MainRoot;
import fr.draaks.dungeon.model.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static java.lang.Math.random;

public class LancerAventure {
	
	private Stage stage;
	MainRoot Main;
	Donjon dungeon;
	Personnage joueur;
	GraphicsContext gc;
	
	// on donne l'objet de la fenêtre parente pour conserver le contrôle
		public void setParentController(MainRoot parentController) {
		    this.Main = parentController;
		}
	
	public void commencer(Stage stage, Personnage joueur) {
		this.stage = stage; // Fenêtre du jeu
        this.stage.setTitle("Draak's Dungeon"); // Titre
        this.stage.setWidth(1100); // Largeur (DISCLAIMER : Je ne sais pas pourquoi mais il faut 16 pixels en plus)
        this.stage.setHeight(675); // Hauteur (DISCLAIMER : Je ne sais pas pourquoi mais il faut 39 pixels en plus)
        this.stage.show(); // Affichage de la fenêtre

        Canvas canvas = new Canvas(1100, 675); // Utilisé pour afficher les images du donjons sur l'écran
        gc = canvas.getGraphicsContext2D(); // Via le context
        gc.setStroke(Color.RED);
        
//        runVillage(damien, gc);
        runDungeon(joueur, gc);

        stage.show();
	}

	/*
	FONCTION POUR JOUER DANS LE DONJON (GENERATION DU DONJON, DES MOBS, ET DES ITEMS)
	 */
	public void runDungeon(Personnage joueur, GraphicsContext gc) {
		this.gc = gc;
		Main.backgroundMusicInit("bib/menu/sounds/menu_sound.mp3");
	    joueur.setInDungeon();
	    joueur.setLocation(0, 0, 550, 325);

        gc.setFill(Color.web("0x201815")); // On définit la couleur utilisé comme fond sur l'écran comme la couleur des cases vides sans murs

	    // CHANGER CETTE VALEUR POUR CHANGER LA TAILLE DU DONJON.
//        int range = 13;
//        int difficulty = -1;
        double difficulty = 0.1*Math.pow(joueur.getLvl(), 2) - 0.1*joueur.getLvl() + 0.2;
        double range = Math.log(Math.pow(difficulty, 2)*40)*2+4;


        dungeon = new Donjon(difficulty, range, joueur.getLvl()); // Création du donjon si la difficulté est à -1 alors le donjon d'intro est chargé.

//        Skeleton enemy = new Skeleton(2, 1, "Enemy");
//        int[] randRoom = dungeon.getRandomRoom();
//        Skeleton enemy;
//        if (dungeon.getDifficulty() < 0.3)
//            enemy = new Skeleton(2, 1, "Enemy");
//        else
//            enemy = new Skeleton(randRoom[0], randRoom[1], "Enemy");

        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); // Remplissage de l'écran
        dungeon.afficher(gc, joueur.getX(), joueur.getY()); // Affichage du donjon à l'écran
        dungeon.drawEnemy(gc, joueur);
        joueur.draw(gc); // Affichage du perso à l'écran
//        enemy.draw(gc, joueur.getMapXOffset(), joueur.getMapYOffset());

        //Création de la scène pour utilisé le canvas + keyEvents
        Scene scene = new Scene(new Group(gc.getCanvas()));
        stage.setScene(scene);

//         Si on clic sur l'écran.
//        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
//            if(e.getButton() == MouseButton.PRIMARY) {
//                joueur.attack(gc, e.getSceneX(), e.getSceneY());
//            }
//        });
//        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
//            if (e.getButton() == MouseButton.PRIMARY) {
//                gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); // Actualisation de la fenêtre.
//                dungeon.afficher(gc, joueur.getX(), joueur.getY()); // Affichage du donjon
////                enemy.draw(gc, joueur.getMapXOffset(), joueur.getMapYOffset());
//                joueur.draw(gc); // Affichage du joueur
//            }
//        });

        // Si on appuie sur une touche.
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
//            int CurRange = dungeon.getRange();
            boolean offset = false;
            boolean fatig = false;
            boolean pHasMoved = false; // POur savoir si le joueur à bouger (ou devait bouger s'il n'y avait pas de fatigue)
            switch (e.getCode()) {
                case TAB:
                    System.out.println("AFFICHAGE DE L'INVENTAIRE");
				try {
					Main.lancerHUDHistoire(this.stage, dungeon, joueur, gc);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
                    break;
                // Pour aller à gauche.
                case Q :
                case LEFT:
                    if(dungeon.canMove(joueur.getX()-1, joueur.getY())) {
                        if (random() > dungeon.getFatigue()) {
                            offset = joueur.moveLeft(); // Renvoie vrai si le joueur atteint un bord de l'écran (à gauche ici)
                        }
                        else {
                            dungeon.resetFatigue();
                            fatig = true;
                        }
                        pHasMoved = true;
                        if(dungeon.getDifficulty() >= 0)
                            dungeon.setFatigue();
                    }
                    if (offset)
                        dungeon.addXOffset(); // Comme on a atteint un bord, on créer une déplacement de la map, le joueur se déplace aussi mais subit aussi le déplacement. (du coup il reste à la même position sur l'écran)
                    break;
                // Pour aller à droite
                case D:
                case RIGHT:
                    if(dungeon.canMove(joueur.getX()+1, joueur.getY())) {
                        if (random() > dungeon.getFatigue()) {
                            offset = joueur.moveRight();
                        }
                        else {
                            dungeon.resetFatigue();
                            fatig = true;
                        }
                        pHasMoved = true;
                        if(dungeon.getDifficulty() >= 0)
                            dungeon.setFatigue();
                    }
                    if (offset)
                        dungeon.remXOffset();
                    break;
                // Pour aller en haut
                case Z:
                case UP:
                    if(dungeon.canMove(joueur.getX(), joueur.getY()-1)) {
                        if (random() > dungeon.getFatigue()) {
                            offset = joueur.moveUp();
                        }
                        else {
                            dungeon.resetFatigue();
                            fatig = true;
                        }
                        pHasMoved = true;
                        if(dungeon.getDifficulty() >= 0)
                            dungeon.setFatigue();
                    }
                    if (offset)
                        dungeon.addYOffset();
                    break;
                // Pour aller en bas
                case S:
                case DOWN:
                    if(dungeon.canMove(joueur.getX(), joueur.getY()+1)) {
                        if (random() > dungeon.getFatigue()) {
                            offset = joueur.moveDown();
                        }
                        else {
                            dungeon.resetFatigue();
                            fatig = true;
                        }
                        pHasMoved = true;
                        if(dungeon.getDifficulty() >= 0)
                            dungeon.setFatigue();
                    }
                    if (offset)
                        dungeon.remYOffset();
                    break;
                default:
                    break;
            }
            // Si le joueur à bouger, alors on fait bouger tous les mobs qu'il y a autour d elui
            if(pHasMoved) {
                dungeon.run(joueur);
                Room CurrRoom = this.dungeon.getRoom(joueur.getX(), joueur.getY());
                if (!CurrRoom.getVisited()){
                    CurrRoom.setVisited();
                    joueur.setVie(joueur.getVie() + 1);
                }
            }
            // Affichage du donjon
            gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); // Actualisation de la fenêtre.
            dungeon.afficher(gc, joueur.getX(), joueur.getY()); // Affichage du donjon
            dungeon.drawEnemy(gc, joueur);
            // On check pour savoir si le joueur se trouve sur une case avec un item ou un mob.
            try {
				this.RoomEvent(joueur, dungeon);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            joueur.draw(gc); // Affichage du joueur.

            // Affichage du textes de la fatigue.
            if(fatig) {
                gc.strokeText("Je suis fatigué !", joueur.getFullX() + 10 + 550, joueur.getFullY() - 20 + 325);
                System.out.println("("+ (joueur.getFullX()+550) + "," + (joueur.getFullY() +325) +")");
            }

            // Check si le joueur est sur la sortie.
            if(dungeon.isOnExit(joueur.getX(), joueur.getY())) {
                joueur.addXp(1500);
                runVillage(joueur, gc);
            }
//            damien.printCoord(); // Debugg (voir plus haut)
        });
    }

    /*
    Utile pour savoir si oui ou non le joueur se trouve sur une case avec un item, ou avec un mob.
     */
    public void RoomEvent(Personnage joueur, Donjon dungeon) throws Exception {
	    int currX, currY;
	    currX = joueur.getX();
	    currY = joueur.getY();

	    // Retourne un item si le joueur se trouve sur la même case que celui ci
	    Items item = dungeon.getItems(currX, currY);
	    if (item != null) {
	    	this.joueur.getInventaire().add(item);
	    	try {
				Main.lancerHUDHistoire(this.stage, dungeon, this.joueur, this.gc);
				dungeon.delItem(currX, currY);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	    }

	    // Retourne un mob si ce dernier se trouve sur la même case qu ele joueur.
	    Mobs mob = dungeon.getMobs(currX, currY);
	    if (mob != null)
	        Main.lancerCombatHistoire(this.stage, dungeon, joueur, this.gc, mob);
    }

    /*
    FONCTION QUI PERMET DE JOUER DANS LE VILLAGE
     */
    public void runVillage(Personnage joueur, GraphicsContext gc) {
    	this.gc = gc;
    	Village town = new Village();
        
        if(joueur.isInDungeon()) {
            joueur.setInDungeon(); // Le joueur référence pour savoir si le joueur est dans un donjon ou non. Gère l'affichage
            joueur.setLocation(6, 1, 750, 125); // Changement de l'emplacement du joueur.
        	Main.backgroundMusicInit("bib/menu/sounds/Village_sound.mp3");
        } else {
            town.setOffset(joueur.getMapXOffset() + 150, joueur.getMapYOffset() + 25);
        }
            
        town.draw(gc);
        joueur.draw(gc);

        Scene scene = new Scene(new Group(gc.getCanvas()));

        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            boolean offset = false;
            switch (e.getCode()) {
                case TAB:
                    System.out.println("AFFICHAGE DE L'INVENTAIRE");
                    try {
    					Main.lancerHUDHistoire(this.stage, null, joueur, this.gc);
    				} catch (Exception e2) {
    					e2.printStackTrace();
    				}
                    break;
                // Pour aller à gauche.
                case Q :
                case LEFT:
                    if(town.canMove(joueur.getX()-1, joueur.getY()))
                        offset = joueur.moveLeft(); // Renvoie vrai si le joueur atteint un bord de l'écran (à gauche ici)
                    if (offset)
                        town.addXOffset(); // Comme on a atteint un bord, on créer une déplacement de la map, le joueur se déplace aussi mais subit aussi le déplacement. (du coup il reste à la même position sur l'écran)
                    break;
                // Pour aller à droite
                case D:
                case RIGHT:
                    if(town.canMove(joueur.getX()+1, joueur.getY()))
                        offset = joueur.moveRight();
                    if (offset)
                        town.remXOffset();
                    break;
                // Pour aller en haut
                case Z:
                case UP:
                    if(town.canMove(joueur.getX(), joueur.getY()-1))
                        offset = joueur.moveUp();
                    if (offset)
                        town.addYOffset();
                    break;
                // Pour aller en bas
                case S:
                case DOWN:
                    if(town.canMove(joueur.getX(), joueur.getY()+1))
                        offset = joueur.moveDown();
                    if (offset)
                        town.remYOffset();
                    break;
                default:
                    break;
            }
//            damien.printCoord(); // Debugg -> Affichage d'info sur la salle occupé / l'emplacement sur l'écran / l'offset joueur.
            town.draw(gc); // Affichage du donjon
            joueur.draw(gc); // Affichage du joueur.

            // Si le joueur se trouve sur la case de la banque.
            if(joueur.getX() == town.getBank().getX() && joueur.getY() == town.getBank().getY()) {
                System.out.println("APPELEZ LA FONCTION POUR LE BANQUIER");
                Main.playBruitage("bib/menu/sounds/Bank_sound.mp3");
                try {
					Main.patienter(this.stage, null, joueur, this.gc);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
            // Sinon si c'est sur la case du campement/maison
            else if(joueur.getX() == town.getCamp().getX() && joueur.getY() == town.getCamp().getY()) {
                System.out.println("APPELEZ LA FONCTION POUR LE CAMPEMENT");
                Main.playBruitage("bib/menu/sounds/Campement_sound.mp3");
                try {
					Main.patienter(this.stage, null, joueur, this.gc);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
            // Sinon si c'est sur la case forge.
            else if(joueur.getX() == town.getForg().getX() && joueur.getY() == town.getForg().getY()) {
                System.out.println("APPELEZ LA FONCTION POUR LA FORGE");
                Main.playBruitage("bib/menu/sounds/Forge_sound.mp3");
                try {
					Main.patienter(this.stage, null, joueur, this.gc);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
            // Sinon si c'est sur la case du shop !
            else if(joueur.getX() == town.getSorc().getX() && joueur.getY() == town.getSorc().getY()) {
                System.out.println("APPELEZ LA FONCTION POUR LE STORE");
                Main.playBruitage("bib/menu/sounds/Shop_sound.mp3");
                try {
					Main.patienter(this.stage, null, joueur, this.gc);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }

            if(town.isOnExit(joueur.getX(), joueur.getY())) // Si on sort de la vile, on va dans le prochain donjon.
                runDungeon(joueur, gc);
//            damien.printCoord(); // Debugg (voir plus haut)
        });

        stage.setScene(scene);
    }
    
    public Donjon getDungeon() {
        return this.dungeon;
    }

    public Personnage getJoueur() {
        return this.joueur;
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public void runDungeon(Donjon dungeon, Personnage joueur, GraphicsContext gc) {
    	this.gc = gc;
    	this.Main.music_background.setVolume(0.5);
    	gc.setStroke(Color.RED);
        gc.setFill(Color.web("0x201815")); // On définit la couleur utilisé comme fond sur l'écran comme la couleur des cases vides sans murs

        this.dungeon = dungeon; // Création du donjon si la difficulté est à -1 alors le donjon d'intro est chargé.
        this.joueur = joueur;
        
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); // Remplissage de l'écran
        dungeon.afficher(gc, joueur.getX(), joueur.getY()); // Affichage du donjon à l'écran
        dungeon.drawEnemy(gc, joueur);
        joueur.draw(gc); // Affichage du perso à l'écran
//        enemy.draw(gc, joueur.getMapXOffset(), joueur.getMapYOffset());

        //Création de la scène pour utilisé le canvas + keyEvents
        Scene scene = new Scene(new Group(gc.getCanvas()));
        stage.setScene(scene);

        // Si on appuie sur une touche.
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
//            int CurRange = dungeon.getRange();
            boolean offset = false;
            boolean fatig = false;
            boolean pHasMoved = false; // POur savoir si le joueur à bouger (ou devait bouger s'il n'y avait pas de fatigue)
            switch (e.getCode()) {
                case TAB:
                    System.out.println("AFFICHAGE DE L'INVENTAIRE");
                    try {
    					Main.lancerHUDHistoire(this.stage, dungeon, joueur, gc);
    				} catch (Exception e2) {
    					e2.printStackTrace();
    				}
                    break;
                // Pour aller à gauche.
                case Q :
                case LEFT:
                    if(dungeon.canMove(joueur.getX()-1, joueur.getY())) {
                        if (random() > dungeon.getFatigue()) {
                            offset = joueur.moveLeft(); // Renvoie vrai si le joueur atteint un bord de l'écran (à gauche ici)
                        }
                        else {
                            dungeon.resetFatigue();
                            fatig = true;
                        }
                        pHasMoved = true;
                        if(dungeon.getDifficulty() >= 0)
                            dungeon.setFatigue();
                    }
                    if (offset)
                        dungeon.addXOffset(); // Comme on a atteint un bord, on créer une déplacement de la map, le joueur se déplace aussi mais subit aussi le déplacement. (du coup il reste à la même position sur l'écran)
                    break;
                // Pour aller à droite
                case D:
                case RIGHT:
                    if(dungeon.canMove(joueur.getX()+1, joueur.getY())) {
                        if (random() > dungeon.getFatigue()) {
                            offset = joueur.moveRight();
                        }
                        else {
                            dungeon.resetFatigue();
                            fatig = true;
                        }
                        pHasMoved = true;
                        if(dungeon.getDifficulty() >= 0)
                            dungeon.setFatigue();
                    }
                    if (offset)
                        dungeon.remXOffset();
                    break;
                // Pour aller en haut
                case Z:
                case UP:
                    if(dungeon.canMove(joueur.getX(), joueur.getY()-1)) {
                        if (random() > dungeon.getFatigue()) {
                            offset = joueur.moveUp();
                        }
                        else {
                            dungeon.resetFatigue();
                            fatig = true;
                        }
                        pHasMoved = true;
                        if(dungeon.getDifficulty() >= 0)
                            dungeon.setFatigue();
                    }
                    if (offset)
                        dungeon.addYOffset();
                    break;
                // Pour aller en bas
                case S:
                case DOWN:
                    if(dungeon.canMove(joueur.getX(), joueur.getY()+1)) {
                        if (random() > dungeon.getFatigue()) {
                            offset = joueur.moveDown();
                        }
                        else {
                            dungeon.resetFatigue();
                            fatig = true;
                        }
                        pHasMoved = true;
                        if(dungeon.getDifficulty() >= 0)
                            dungeon.setFatigue();
                    }
                    if (offset)
                        dungeon.remYOffset();
                    break;
                default:
                    break;
            }
            // Si le joueur à bouger, alors on fait bouger tous les mobs qu'il y a autour d elui
            if(pHasMoved) {
                dungeon.run(joueur);
                Room CurrRoom = this.dungeon.getRoom(joueur.getX(), joueur.getY());
                if (!CurrRoom.getVisited()){
                    CurrRoom.setVisited();
                    joueur.setVie(joueur.getVie() + 2);
                }
            }
            // Affichage du donjon
            gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); // Actualisation de la fenêtre.
            dungeon.afficher(gc, joueur.getX(), joueur.getY()); // Affichage du donjon
            dungeon.drawEnemy(gc, joueur);
            // On check pour savoir si le joueur se trouve sur une case avec un item ou un mob.
            try {
				this.RoomEvent(joueur, dungeon);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            joueur.draw(gc); // Affichage du joueur.

            // Affichage du textes de la fatigue.
            if(fatig) {
                gc.strokeText("Je suis fatigué !", joueur.getFullX() + 10 + 550, joueur.getFullY() - 20 + 325);
                System.out.println("("+ (joueur.getFullX()+550) + "," + (joueur.getFullY() +325) +")");
            }

            // Check si le joueur est sur la sortie.
            if(dungeon.isOnExit(joueur.getX(), joueur.getY())) {
                joueur.addXp(1500);
                runVillage(joueur, gc);
            }
//            damien.printCoord(); // Debugg (voir plus haut)
        });
    }

}
