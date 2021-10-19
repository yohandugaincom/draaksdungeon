package fr.draaks.dungeon.model;

import javafx.scene.image.Image;

public abstract class Entity {
    /*
        Classe qui désigne toutes les entity par des coordonnées pour savoir où elle se trouve sur la map
        si (x == null et y == null) alors l'entity n'est pas sur la map.
        Chaque entity à un nom.

        Ceci est une class abstraite.
     */
    protected final static int tileSize = 100;
    protected Integer x;
    protected Integer y;
    protected String name;
    protected Image img = new Image("file:src/fr/draaks/dungeon/bib/general/player.png", tileSize, tileSize, true, false);
    protected Integer vie;
    protected Integer mana;
    protected Integer maxVie;
    protected Integer maxMana;
    protected Integer lvl;
    protected Integer xp;
    protected Image blankImg = new Image("file:src/fr/draaks/dungeon/bib/general/player.png", tileSize, tileSize, true, false);

    // Constructor
    public Entity(Integer x, Integer y, String name, Integer vie, Integer mana, Integer lvl, Integer xp) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.vie = vie;
        this.mana = mana;
        this.lvl = lvl;
        this.xp = xp;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    // JUST ADDED
    public void setX(Integer newX) {
        this.x = newX;
    }

    public void setY(Integer newY) {
            this.y = newY;
    }

//    public void drawBlank(GraphicsContext gc, int x, int y, int mapXOffset, int mapYOffset) {
//        gc.drawImage(this.blankImg, x*tileSize + 550 + mapXOffset, y*tileSize + 325 + mapYOffset);
//    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Image getImg() {
        return img;
    }

    public Integer getVie() {
        return vie;
    }

    public Integer getMana() {
        return mana;
    }
    
    public Integer getLvl() {
        return lvl;
    }

	public Integer getXp() {
		return xp;
	}

	public void setVie(Integer vie) {
		this.vie = vie;
	}

	public void setMana(Integer mana) {
		this.mana = mana;
	}

	public void setXp(Integer xp) {
		this.xp = xp;
	}

}
