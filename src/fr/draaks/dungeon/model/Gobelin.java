package fr.draaks.dungeon.model;

import javafx.scene.image.Image;

public class Gobelin extends Mobs {
    //private final Image img = new Image("file:src/fr/draaks/dungeon/bib/persos/Gobelin-D.png", tileSize+10, tileSize+10, true, false);

    // Constructor
    public Gobelin(Integer x, Integer y, String name) {
    	super(x, y, name, 85, 7, 1, null);
        this.img = new Image("file:src/fr/draaks/dungeon/bib/persos/Gobelin-D.png", tileSize+10, tileSize+10, true, false);
    }

    public Gobelin(Integer x, Integer y, String name, int vie, int mana, int lvl) {
        super(x, y, name, vie, mana, lvl, (int)(lvl*Math.random()*10)+95);
        this.img = new Image("file:src/fr/draaks/dungeon/bib/persos/Gobelin-D.png", tileSize+10, tileSize+10, true, false);
        this.argent = lvl + ((Math.random()-0.5)/3)*lvl;
    }

	public Image getImg() {
		return img;
	}
}
