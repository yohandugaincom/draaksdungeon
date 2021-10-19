package fr.draaks.dungeon.model;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class TownRoom extends Room {
    private static final int tileSize = 100;
    private String marchandName;
    private String tileType;
    private boolean walkable;
    private Image img;
    private static final Image[] sprites = new Image[] {
            new Image("file:src/fr/draaks/dungeon/bib/general/village/sable.png", tileSize, tileSize, true, false),
            new Image("file:src/fr/draaks/dungeon/bib/general/village/herbe.png", tileSize, tileSize, true, false),
            new Image("file:src/fr/draaks/dungeon/bib/general/village/eau.png", tileSize, tileSize, true, false),
            new Image("file:src/fr/draaks/dungeon/bib/general/village/bois.png", tileSize, tileSize, true, false),
            new Image("file:src/fr/draaks/dungeon/bib/general/village/mur.png", tileSize, tileSize, true, false),
            new Image("file:src/fr/draaks/dungeon/bib/general/village/pierreRouge.png", tileSize, tileSize, true, false),
    };
    @SuppressWarnings("serial")
	private static final ArrayList<String> walkableType = new ArrayList<String>(){
        {   // Definit les cases sur lesquelles on peut bouger/marcher
            add("herbe");
            add("path");
            add("pont");
        }
    };

    // Construteur de comparaison.
    public TownRoom(int x, int y, boolean exitRoom) {
        super(x, y, exitRoom);
    }

    public TownRoom(int x, int y, String tileType, String marchandName) {
        super(x, y);
        this.setMarchandName(marchandName);
        this.setTileType(tileType);
        this.walkable = walkableType.contains(tileType);
        setImg(tileType);
    }

    public TownRoom(int x, int y, boolean exitRoom, String tileType, String marchandName) {
        super(x, y, exitRoom);
        this.setMarchandName(marchandName);
        this.setTileType(tileType);
        this.walkable = walkableType.contains(tileType);
        setImg(tileType);
    }
    /*
    Sable, Herbe, Eau, Bois, Mur, PierreRouge
     */

    // Setup les images par rapport au type de tuile.
    public void setImg(String tileType) {
        switch (tileType) {
            case "herbe":
                this.img = sprites[1];
                break;
            case "path":
                this.img = sprites[0];
                break;
            case "eau":
                this.img = sprites[2];
                break;
            case "mur":
                this.img = sprites[4];
                break;
            case "pont":
                this.img = sprites[3];
                break;
            case "campement":
                this.img = sprites[5];
                break;
        }

    }

    public Image getImg() {
        return this.img;
    }

    public boolean getWalkable() {
        return this.walkable;
    } // Pour savoir si on peut se déplacer sur la tuile.

    // Permet de change rle comportement d'une tuile (utilisé pour évité de marcher sur les batiments)
    public void setWalkable(boolean isWalkable) {
        this.walkable = isWalkable;
    }

	public String getMarchandName() {
		return marchandName;
	}

	public void setMarchandName(String marchandName) {
		this.marchandName = marchandName;
	}

	public String getTileType() {
		return tileType;
	}

	public void setTileType(String tileType) {
		this.tileType = tileType;
	}

//    public String getMarchand() {
//        return this.marchandName;
//    }
}
