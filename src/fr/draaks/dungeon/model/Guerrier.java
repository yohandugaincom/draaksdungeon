package fr.draaks.dungeon.model;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Guerrier extends Personnage{
//    private static final String armureType = "Lourde";
//    private static final String[] armeType = {"Epee", "Espadon"};

    // Constructor
    public Guerrier(Integer x, Integer y, String name, boolean isSaved) {
        super(x, y, name, 120, 8, 0, 0, 1,0.15, 0.85, 0.025,
                new ArrayList<>(),
                null, null);
        this.img = new Image("file:src/fr/draaks/dungeon/bib/persos/Guerrier-D.png", tileSize+10, tileSize+10, true, false);
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
    public Guerrier(String nom, int pv, int pa, int xp) {
    	super(null, null, nom, pv, pa, 0, xp, 1, 0.10, 0.90, 0.05, null, null, null);
    	this.img = new Image("file:src/fr/draaks/dungeon/bib/persos/Guerrier-D.png", tileSize+10, tileSize+10, true, false);
    }

//	public static String getArmuretype() {
//		return armureType;
//	}
//
//	public static String[] getArmetype() {
//		return armeType;
//	}

	public Image getImg() {
		return img;
	}


}
