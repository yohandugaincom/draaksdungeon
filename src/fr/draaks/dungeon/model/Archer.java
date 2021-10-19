package fr.draaks.dungeon.model;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Archer extends Personnage{
//    private static final String armureType = "Legere";
//    private static final String[] armeType = {"Arc", "Arbalete", "Dague"};

    // Constructor
    public Archer(Integer x, Integer y, String name, boolean isSaved) {
    	super(x, y, name, 95, 10, 0, 0, 1,0.15, 0.85, 0.025,
                new ArrayList<>(),
                null, null);
        this.img = new Image("file:src/fr/draaks/dungeon/bib/persos/Archer-D.png", tileSize+10, tileSize+10, true, false);
        if (isSaved){
            //CHARGEMENT DE LA SAUVEGARDE. (a faire)
            this.x = 0;
            this.y = 0;
            this.argent = 0;
            this.xp = 0;
            this.lvl = 1;
            this.inventaire = new ArrayList<>();
            this.armure = null;
            this.arme = null;
        }
    }
    
  //Online
    public Archer(String nom, int pv, int pa, int xp) {
    	super(null, null, nom, pv, pa, 0, xp, 1, 0.10, 0.90, 0.05, null, null, null);
        this.img = new Image("file:src/fr/draaks/dungeon/bib/persos/Archer-D.png", tileSize+10, tileSize+10, true, false);
    }
    
    /*
        CONSTRUCTOR SURCHARGEE FULL
     */
    public Archer(Integer x, Integer y, String name, Integer vie, Integer mana, long argent, Integer xp, Integer lvl, double critChance, double precChance, double dodgeChance, ArrayList<Items> inventaire, Items armure, Items arme) {
        super(x, y, name, vie, mana, argent, xp, lvl, critChance, precChance, dodgeChance, inventaire, armure, arme);
    }

//	public static String getArmuretype() {
//		return armureType;
//	}
//	public static String[] getArmetype() {
//		return armeType;
//	}

	public Image getImg() {
		return img;
	}
	
}
