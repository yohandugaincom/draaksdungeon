package fr.draaks.dungeon.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Personnage extends Entity {

    /*
        Classe abstraite qui désigne le personnage en général, avec ses caractéristiques.
     */
    protected int xOffset = 550;
    protected int yOffset = 325;
    protected int mapXOffset = 0;
    protected int mapYOffset = 0;

    protected long argent;

    // STATISTIQUES
    protected double critChance;
    protected double precChance;
    protected double dodgeChance;

    protected ArrayList<Items> inventaire;
    protected Items armure;
    protected Items arme;

    // {strengh 5%; critChance 5%, precChance 5%, dodgeChance 5%}
//    protected int[] curEffect5 = new int[]{0, 0, 0, 0};
    // {strengh 10%; critChance 10%, precChance 10%, dodgeChance 10%}
//    protected int[] curEffect10 = new int[]{0, 0, 0, 0};

//    protected String curEffect; // Pour les effets de potions et autres. Je pense qu'il y a un meilleur moyen de stocké ça.
    protected Image img = new Image("file:src/fr/draaks/dungeon/bib/general/player.png", tileSize, tileSize, true, false);

    /*  lastDir value :
        0 : Left
        1 : Up
        2 : Right
        3 : Down
     */
    protected int lastDir = 0;
    protected boolean inDungeon;

    //CONSTRUCTEUR.
    public Personnage(Integer x, Integer y, String name, Integer vie, Integer mana, long argent, Integer xp, Integer lvl, double critChance, double precChance, double dodgeChance, ArrayList<Items> inventaire, Items armure, Items arme) {
        super(x, y, name, vie, mana, lvl, xp);
        this.argent = argent;

        this.critChance = critChance;
        this.precChance = precChance;
        this.dodgeChance = dodgeChance;

        this.inventaire = inventaire;
        this.armure = armure;
        this.arme = arme;
    }

    @Deprecated
    public void draw_old(GraphicsContext gc) { // Pour dessiner le joueur.
        gc.drawImage(this.img, this.getFullX() + this.xOffset, this.getFullY() + 325);
    }

    public void draw(GraphicsContext gc) { // Pour dessiner le joueur.
        int xDecals = -5;
        int yDecals = -5;
        if(isInDungeon())
            if (this.lastDir == 2 || this.lastDir == 3)
                gc.drawImage(this.img, this.x*tileSize + 550 + mapXOffset + xDecals, this.y*tileSize + 325 + mapYOffset + yDecals);
            else
                gc.drawImage(this.img, this.x*tileSize + 550 + mapXOffset + xDecals + this.img.getWidth(), this.y*tileSize + 325 + mapYOffset + yDecals, -this.img.getWidth(), this.img.getHeight());
        else
        if (this.lastDir == 2 || this.lastDir == 3)
            gc.drawImage(this.img, this.x*tileSize + 150 + mapXOffset + xDecals, this.y*tileSize + 25 + mapYOffset + yDecals);
        else
            gc.drawImage(this.img, this.x*tileSize + 150 + mapXOffset + xDecals + this.img.getWidth(), this.y*tileSize + 25 + mapYOffset + yDecals, -this.img.getWidth(), this.img.getHeight());
    }

    public boolean moveLeft() {
        this.lastDir = 0;
        this.x -= 1; // Changement de la salle
        this.xOffset -= tileSize; // Changement de l'emplacement sur l'écran
        if(this.xOffset < 150) { // Si on est au bord alors
            this.xOffset += tileSize; // on annule l'effet du déplacement
            this.mapXOffset += tileSize; // on déplace l'affichage du joueur.
            return true; // On renvoie vrai parce qu'on était au bord.
        }
        return false;
    }

    public boolean moveRight() {
        this.lastDir = 2;
        this.x += 1;
        this.xOffset += tileSize;
        if(this.xOffset > 800) {
            this.xOffset -= tileSize;
            this.mapXOffset -= tileSize;
            return true;
        }
        return false;
    }

    public boolean moveUp() {
//        this.lastDir = 1;
        this.y -= 1;
        this.yOffset -= tileSize;
        if(this.yOffset < 150) {
            this.yOffset += tileSize;
            this.mapYOffset += tileSize;
            return true;
        }
        return false;
    }

    public boolean moveDown() {
//        this.lastDir = 3;
        this.y += 1;
        this.yOffset += tileSize;
        if(this.yOffset > 450) {
            this.yOffset -= tileSize;
            this.mapYOffset -= tileSize;
            return true;
        }
        return false;
    }

    public boolean isDead() {
        return this.vie <= 0;
    } // Permet de savoir si je personnage est mort

//    public void printCoord()
//    {
//        System.out.println("Voici les coordonnées (" + this.getX().toString() +"," + this.getY().toString() + ")");
//        System.out.println("Voici l'offsets : (" + this.xOffset+ "," + this.yOffset + ")");
//        System.out.println("Voici la map offset : (" + this.mapXOffset + "," + this.mapYOffset + ")");
//    }
	
	public abstract Image getImg();
	
	public int getFullX() {
        return this.x*tileSize + this.mapXOffset;
    }

    public int getFullY() {
        return this.y*tileSize + this.mapYOffset;
    }

    public int getMapXOffset() {
        return this.mapXOffset;
    }

    public int getMapYOffset() {
        return this.mapYOffset;
    }

	public ArrayList<Items> getInventaire() {
		return inventaire;
	}

	public void setInventaire(ArrayList<Items> inventaire) {
		this.inventaire = inventaire;
	}
	
    public boolean isInDungeon() {
	    return this.inDungeon;
    }
    
	public void setInDungeon() {
	    this.inDungeon = !this.inDungeon;
    }

    public void setLocationBis(int x, int y, int xOffset, int yOffset, int mapXOffset, int mapYOffset) {
	    this.x = x;
	    this.y = y;
	    this.xOffset = xOffset;
	    this.yOffset = yOffset;
	    this.mapXOffset = mapXOffset;
	    this.mapYOffset = mapYOffset;
    }
    
    public void setLocation(int x, int y, int xOffset, int yOffset) {
	    this.x = x;
	    this.y = y;
	    this.xOffset = xOffset;
	    this.yOffset = yOffset;
	    this.mapXOffset = 0;
	    this.mapYOffset = 0;
    }

    public void addXp(Integer addXp) {
        if(this.lvl == 1) {
            this.xp += 500; //FAUT CHANGER CA
            addLvl();
        }
        this.xp = this.xp + addXp;
        System.out.println("XP gagné ! " + addXp);
        if (this.xp >= 500+1000*(this.lvl-1))
            this.addLvl();
    }

    private void addLvl() {
	    this.xp -= 500+1000*(this.lvl-1);
	    this.lvl += 1;
	    System.out.println("Level UP !");
    }
    
}
