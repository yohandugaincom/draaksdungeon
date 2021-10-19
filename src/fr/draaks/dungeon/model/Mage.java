package fr.draaks.dungeon.model;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Mage extends Personnage {
//    private static final String armureType = "Legere";
//    private static final String[] armeType = {"Baton", "Livre", "Dague"};
//    private final Image img = new Image("file:src/fr/draaks/dungeon/bib/persos/Mage-D.png", tileSize+10, tileSize+10, true, false);
    
    // Constructor
    public Mage(Integer x, Integer y, String name, boolean isSaved) {
        super(x, y, name, 80, 11, 0, 0, 1, 0.10, 0.90, 0.05,
                new ArrayList<>(),
                null, null);
        this.img = new Image("file:src/fr/draaks/dungeon/bib/persos/Mage-D.png", tileSize+10, tileSize+10, true, false);
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
    public Mage(String nom, int pv, int pa, int xp) {
    	super(null, null, nom, pv, pa, 0, xp, 1, 0.10, 0.90, 0.05, null, null, null);
        this.img = new Image("file:src/fr/draaks/dungeon/bib/persos/Mage-D.png", tileSize+10, tileSize+10, true, false);
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
