package fr.draaks.dungeon.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class Mobs extends Entity {
    protected int lastDir = 0;
    protected double argent;

    public Mobs(Integer x, Integer y, String name, Integer vie, Integer mana, Integer lvl, Integer xp) {
        super(x, y, name, vie, mana, lvl, xp);
    }

    public void move(Nodes nextStep, int range) {
        this.x = nextStep.getX() - range;
        this.y = nextStep.getY() - range;
    }

    public void draw(GraphicsContext gc, Personnage joueur) { // Pour dessiner le joueur.
        int xDecals = -5;
        int yDecals = -5;
        if (this.lastDir == 2)
            gc.drawImage(this.img, this.x*tileSize + 550 + joueur.getMapXOffset() + xDecals, this.y*tileSize + 325 + joueur.getMapYOffset() + yDecals);
        else
            gc.drawImage(this.img, this.x*tileSize + 550 + joueur.getMapXOffset() + xDecals + this.img.getWidth(), this.y*tileSize + 325 + joueur.getMapYOffset() + yDecals, -this.img.getWidth(), this.img.getHeight());
    }


//    protected void setImg(String s) {
//        this.img = new Image(s, tileSize, tileSize, true, false);
//    }
}