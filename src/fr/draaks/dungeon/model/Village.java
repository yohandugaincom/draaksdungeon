package fr.draaks.dungeon.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Village {
//    private ArrayList<String> pnjs;
    private ArrayList<TownRoom> space;
    private static final int tileSize = 100;
    private int xMapOffset = 150;
    private int yMapOffset = 25;

    private TownRoom Camp;
    private TownRoom Sorc;
    private TownRoom Bank;
    private TownRoom Forg;
    private final Image imgCamp = new Image("file:src/fr/draaks/dungeon/bib/general/village/Maison.png", 300, 300 ,true, false);
    private final Image imgSorc = new Image("file:src/fr/draaks/dungeon/bib/general/village/Store.png", 300, 300 ,true, false);
    private final Image imgBank = new Image("file:src/fr/draaks/dungeon/bib/general/village/Bank.png", 500, 300 ,true, false);
    private final Image imgForg = new Image("file:src/fr/draaks/dungeon/bib/general/village/Forge.png", 400, 300 ,true, false);

    public Village() {
        this.space = new ArrayList<>();

        /*
            Instanciations des salles "salles" pour le village. CE CODE A ETE GENERE EN PYTHON, IL DOIT FAIRE DANS LES 600 LIGNES !!! (Code généré grâce à une image de 30*20px et un code couleur)
         */
        this.space.add(new TownRoom(0, 0, "mur", null));
        this.space.add(new TownRoom(0, 1, "mur", null));
        this.space.add(new TownRoom(0, 2, "mur", null));
        this.space.add(new TownRoom(0, 3, "mur", null));
        this.space.add(new TownRoom(0, 4, "mur", null));
        this.space.add(new TownRoom(0, 5, "mur", null));
        this.space.add(new TownRoom(0, 6, "mur", null));
        this.space.add(new TownRoom(0, 7, "mur", null));
        this.space.add(new TownRoom(0, 8, "mur", null));
        this.space.add(new TownRoom(0, 9, "mur", null));
        this.space.add(new TownRoom(0, 10, "mur", null));
        this.space.add(new TownRoom(0, 11, "mur", null));
        this.space.add(new TownRoom(0, 12, "mur", null));
        this.space.add(new TownRoom(0, 13, "mur", null));
        this.space.add(new TownRoom(0, 14, "mur", null));
        this.space.add(new TownRoom(0, 15, "mur", null));
        this.space.add(new TownRoom(0, 16, "mur", null));
        this.space.add(new TownRoom(0, 17, "mur", null));
        this.space.add(new TownRoom(0, 18, "mur", null));
        this.space.add(new TownRoom(0, 19, "mur", null));
        this.space.add(new TownRoom(1, 0, "mur", null));
        this.space.add(new TownRoom(1, 1, "herbe", null));
        this.space.add(new TownRoom(1, 2, "herbe", null));
        this.space.add(new TownRoom(1, 3, "herbe", null));
        this.space.add(new TownRoom(1, 4, "herbe", null));
        this.space.add(new TownRoom(1, 5, "herbe", null));
        this.space.add(new TownRoom(1, 6, "herbe", null));
        this.space.add(new TownRoom(1, 7, "herbe", null));
        this.space.add(new TownRoom(1, 8, "herbe", null));
        this.space.add(new TownRoom(1, 9, "herbe", null));
        this.space.add(new TownRoom(1, 10, "herbe", null));
        this.space.add(new TownRoom(1, 11, "herbe", null));
        this.space.add(new TownRoom(1, 12, "herbe", null));
        this.space.add(new TownRoom(1, 13, "herbe", null));
        this.space.add(new TownRoom(1, 14, "herbe", null));
        this.space.add(new TownRoom(1, 15, "herbe", null));
        this.space.add(new TownRoom(1, 16, "herbe", null));
        this.space.add(new TownRoom(1, 17, "herbe", null));
        this.space.add(new TownRoom(1, 18, "herbe", null));
        this.space.add(new TownRoom(1, 19, "mur", null));
        this.space.add(new TownRoom(2, 0, "mur", null));
        this.space.add(new TownRoom(2, 1, "herbe", null));
        this.space.add(new TownRoom(2, 2, "herbe", null));
        this.Camp = new TownRoom(2, 3, "pont", "campement");
        this.space.add(this.Camp);
        this.space.add(new TownRoom(2, 4, "herbe", null));
        this.space.add(new TownRoom(2, 5, "herbe", null));
        this.space.add(new TownRoom(2, 6, "herbe", null));
        this.space.add(new TownRoom(2, 7, "herbe", null));
        this.space.add(new TownRoom(2, 8, false, "path", null));
        this.space.add(new TownRoom(2, 9, false, "path", null));
        this.space.add(new TownRoom(2, 10, false, "path", null));
        this.space.add(new TownRoom(2, 11, false, "path", null));
        this.space.add(new TownRoom(2, 12, "herbe", null));
        this.space.add(new TownRoom(2, 13, "herbe", null));
        this.space.add(new TownRoom(2, 14, "herbe", null));
        this.space.add(new TownRoom(2, 15, "herbe", null));
        this.space.add(new TownRoom(2, 16, "herbe", null));
        this.space.add(new TownRoom(2, 17, "herbe", null));
        this.space.add(new TownRoom(2, 18, "herbe", null));
        this.space.add(new TownRoom(2, 19, "mur", null));
        this.space.add(new TownRoom(3, 0, "mur", null));
        this.space.add(new TownRoom(3, 1, "herbe", null));
        this.space.add(new TownRoom(3, 2, "herbe", null));
        this.space.add(new TownRoom(3, 3, false, "path", null));
        this.space.add(new TownRoom(3, 4, false, "path", null));
        this.space.add(new TownRoom(3, 5, false, "path", null));
        this.space.add(new TownRoom(3, 6, false, "path", null));
        this.space.add(new TownRoom(3, 7, false, "path", null));
        this.space.add(new TownRoom(3, 8, false, "path", null));
        this.space.add(new TownRoom(3, 9, false, "path", null));
        this.space.add(new TownRoom(3, 10, false, "path", null));
        this.space.add(new TownRoom(3, 11, false, "path", null));
        this.space.add(new TownRoom(3, 12, false, "path", null));
        this.space.add(new TownRoom(3, 13, false, "path", null));
        this.space.add(new TownRoom(3, 14, false, "path", null));
        this.space.add(new TownRoom(3, 15, "herbe", null));
        this.space.add(new TownRoom(3, 16, "herbe", null));
        this.space.add(new TownRoom(3, 17, "herbe", null));
        this.space.add(new TownRoom(3, 18, "herbe", null));
        this.space.add(new TownRoom(3, 19, "mur", null));
        this.space.add(new TownRoom(4, 0, "mur", null));
        this.space.add(new TownRoom(4, 1, "herbe", null));
        this.space.add(new TownRoom(4, 2, false, "path", null));
        this.space.add(new TownRoom(4, 3, false, "path", null));
        this.space.add(new TownRoom(4, 4, false, "path", null));
        this.space.add(new TownRoom(4, 5, false, "path", null));
        this.space.add(new TownRoom(4, 6, false, "path", null));
        this.space.add(new TownRoom(4, 7, false, "path", null));
        this.space.add(new TownRoom(4, 8, false, "path", null));
        this.space.add(new TownRoom(4, 9, false, "path", null));
        this.space.add(new TownRoom(4, 10, false, "path", null));
        this.space.add(new TownRoom(4, 11, false, "path", null));
        this.space.add(new TownRoom(4, 12, false, "path", null));
        this.space.add(new TownRoom(4, 13, false, "path", null));
        this.space.add(new TownRoom(4, 14, false, "path", null));
        this.space.add(new TownRoom(4, 15, false, "path", null));
        this.space.add(new TownRoom(4, 16, "herbe", null));
        this.space.add(new TownRoom(4, 17, "herbe", null));
        this.space.add(new TownRoom(4, 18, "herbe", null));
        this.space.add(new TownRoom(4, 19, "mur", null));
        this.space.add(new TownRoom(5, 0, true, "path", null));
        this.space.add(new TownRoom(5, 1, false, "path", null));
        this.space.add(new TownRoom(5, 2, false, "path", null));
        this.space.add(new TownRoom(5, 3, false, "path", null));
        this.space.add(new TownRoom(5, 4, false, "path", null));
        this.space.add(new TownRoom(5, 5, false, "path", null));
        this.space.add(new TownRoom(5, 6, false, "path", null));
        this.space.add(new TownRoom(5, 7, false, "path", null));
        this.space.add(new TownRoom(5, 8, false, "path", null));
        this.space.add(new TownRoom(5, 9, false, "path", null));
        this.space.add(new TownRoom(5, 10, false, "path", null));
        this.space.add(new TownRoom(5, 11, false, "path", null));
        this.space.add(new TownRoom(5, 12, false, "path", null));
        this.space.add(new TownRoom(5, 13, false, "path", null));
        this.space.add(new TownRoom(5, 14, false, "path", null));
        this.space.add(new TownRoom(5, 15, false, "path", null));
        this.space.add(new TownRoom(5, 16, false, "path", null));
        this.space.add(new TownRoom(5, 17, "herbe", null));
        this.space.add(new TownRoom(5, 18, "herbe", null));
        this.space.add(new TownRoom(5, 19, "mur", null));
        this.space.add(new TownRoom(6, 0, true, "path", null));
        this.space.add(new TownRoom(6, 1, false, "path", null));
        this.space.add(new TownRoom(6, 2, false, "path", null));
        this.space.add(new TownRoom(6, 3, false, "path", null));
        this.space.add(new TownRoom(6, 4, false, "path", null));
        this.space.add(new TownRoom(6, 5, false, "path", null));
        this.space.add(new TownRoom(6, 6, false, "path", null));
        this.space.add(new TownRoom(6, 7, false, "path", null));
        this.space.add(new TownRoom(6, 8, false, "path", null));
        this.space.add(new TownRoom(6, 9, false, "path", null));
        this.space.add(new TownRoom(6, 10, false, "path", null));
        this.space.add(new TownRoom(6, 11, false, "path", null));
        this.space.add(new TownRoom(6, 12, false, "path", null));
        this.space.add(new TownRoom(6, 13, false, "path", null));
        this.space.add(new TownRoom(6, 14, false, "path", null));
        this.space.add(new TownRoom(6, 15, false, "path", null));
        this.space.add(new TownRoom(6, 16, false, "path", null));
        this.space.add(new TownRoom(6, 17, "herbe", null));
        this.space.add(new TownRoom(6, 18, "herbe", null));
        this.space.add(new TownRoom(6, 19, "mur", null));
        this.space.add(new TownRoom(7, 0, true, "path", null));
        this.space.add(new TownRoom(7, 1, false, "path", null));
        this.space.add(new TownRoom(7, 2, false, "path", null));
        this.space.add(new TownRoom(7, 3, false, "path", null));
        this.space.add(new TownRoom(7, 4, false, "path", null));
        this.space.add(new TownRoom(7, 5, false, "path", null));
        this.space.add(new TownRoom(7, 6, false, "path", null));
        this.space.add(new TownRoom(7, 7, false, "path", null));
        this.space.add(new TownRoom(7, 8, false, "path", null));
        this.space.add(new TownRoom(7, 9, false, "path", null));
        this.space.add(new TownRoom(7, 10, false, "path", null));
        this.space.add(new TownRoom(7, 11, false, "path", null));
        this.space.add(new TownRoom(7, 12, false, "path", null));
        this.space.add(new TownRoom(7, 13, false, "path", null));
        this.space.add(new TownRoom(7, 14, false, "path", null));
        this.space.add(new TownRoom(7, 15, false, "path", null));
        this.space.add(new TownRoom(7, 16, false, "path", null));
        this.space.add(new TownRoom(7, 17, "herbe", null));
        this.space.add(new TownRoom(7, 18, "herbe", null));
        this.space.add(new TownRoom(7, 19, "mur", null));
        this.space.add(new TownRoom(8, 0, "mur", null));
        this.space.add(new TownRoom(8, 1, "herbe", null));
        this.space.add(new TownRoom(8, 2, false, "path", null));
        this.space.add(new TownRoom(8, 3, false, "path", null));
        this.space.add(new TownRoom(8, 4, false, "path", null));
        this.space.add(new TownRoom(8, 5, false, "path", null));
        this.space.add(new TownRoom(8, 6, false, "path", null));
        this.space.add(new TownRoom(8, 7, false, "path", null));
        this.space.add(new TownRoom(8, 8, false, "path", null));
        this.space.add(new TownRoom(8, 9, false, "path", null));
        this.space.add(new TownRoom(8, 10, false, "path", null));
        this.space.add(new TownRoom(8, 11, false, "path", null));
        this.space.add(new TownRoom(8, 12, false, "path", null));
        this.space.add(new TownRoom(8, 13, false, "path", null));
        this.space.add(new TownRoom(8, 14, false, "path", null));
        this.space.add(new TownRoom(8, 15, false, "path", null));
        this.space.add(new TownRoom(8, 16, false, "path", null));
        this.space.add(new TownRoom(8, 17, "herbe", null));
        this.space.add(new TownRoom(8, 18, "herbe", null));
        this.space.add(new TownRoom(8, 19, "mur", null));
        this.space.add(new TownRoom(9, 0, "mur", null));
        this.space.add(new TownRoom(9, 1, "herbe", null));
        this.space.add(new TownRoom(9, 2, false, "path", null));
        this.space.add(new TownRoom(9, 3, false, "path", null));
        this.space.add(new TownRoom(9, 4, false, "path", null));
        this.space.add(new TownRoom(9, 5, false, "path", null));
        this.space.add(new TownRoom(9, 6, false, "path", null));
        this.space.add(new TownRoom(9, 7, false, "path", null));
        this.space.add(new TownRoom(9, 8, false, "path", null));
        this.space.add(new TownRoom(9, 9, false, "path", null));
        this.space.add(new TownRoom(9, 10, false, "path", null));
        this.space.add(new TownRoom(9, 11, false, "path", null));
        this.space.add(new TownRoom(9, 12, false, "path", null));
        this.space.add(new TownRoom(9, 13, false, "path", null));
        this.space.add(new TownRoom(9, 14, false, "path", null));
        this.space.add(new TownRoom(9, 15, false, "path", null));
        this.space.add(new TownRoom(9, 16, false, "path", null));
        this.space.add(new TownRoom(9, 17, "herbe", null));
        this.space.add(new TownRoom(9, 18, "herbe", null));
        this.space.add(new TownRoom(9, 19, "mur", null));
        this.space.add(new TownRoom(10, 0, "mur", null));
        this.space.add(new TownRoom(10, 1, "herbe", null));
        this.space.add(new TownRoom(10, 2, "herbe", null));
        this.space.add(new TownRoom(10, 3, false, "path", null));
        this.space.add(new TownRoom(10, 4, false, "path", null));
        this.space.add(new TownRoom(10, 5, false, "path", null));
        this.space.add(new TownRoom(10, 6, false, "path", null));
        this.space.add(new TownRoom(10, 7, false, "path", null));
        this.space.add(new TownRoom(10, 8, false, "path", null));
        this.space.add(new TownRoom(10, 9, false, "path", null));
        this.space.add(new TownRoom(10, 10, false, "path", null));
        this.space.add(new TownRoom(10, 11, false, "path", null));
        this.space.add(new TownRoom(10, 12, false, "path", null));
        this.space.add(new TownRoom(10, 13, false, "path", null));
        this.space.add(new TownRoom(10, 14, false, "path", null));
        this.space.add(new TownRoom(10, 15, false, "path", null));
        this.space.add(new TownRoom(10, 16, false, "path", null));
        this.space.add(new TownRoom(10, 17, "herbe", null));
        this.space.add(new TownRoom(10, 18, "herbe", null));
        this.space.add(new TownRoom(10, 19, "mur", null));
        this.space.add(new TownRoom(11, 0, "mur", null));
        this.space.add(new TownRoom(11, 1, "herbe", null));
        this.space.add(new TownRoom(11, 2, "herbe", null));
        this.space.add(new TownRoom(11, 3, false, "path", null));
        this.space.add(new TownRoom(11, 4, false, "path", null));
        this.space.add(new TownRoom(11, 5, false, "path", null));
        this.space.add(new TownRoom(11, 6, false, "path", null));
        this.space.add(new TownRoom(11, 7, false, "path", null));
        this.space.add(new TownRoom(11, 8, false, "path", null));
        this.space.add(new TownRoom(11, 9, false, "path", null));
        this.space.add(new TownRoom(11, 10, false, "path", null));
        this.space.add(new TownRoom(11, 11, false, "path", null));
        this.space.add(new TownRoom(11, 12, false, "path", null));
        this.space.add(new TownRoom(11, 13, false, "path", null));
        this.space.add(new TownRoom(11, 14, false, "path", null));
        this.space.add(new TownRoom(11, 15, false, "path", null));
        this.space.add(new TownRoom(11, 16, "herbe", null));
        this.space.add(new TownRoom(11, 17, "herbe", null));
        this.space.add(new TownRoom(11, 18, "herbe", null));
        this.space.add(new TownRoom(11, 19, "mur", null));
        this.space.add(new TownRoom(12, 0, "mur", null));
        this.space.add(new TownRoom(12, 1, "herbe", null));
        this.space.add(new TownRoom(12, 2, "herbe", null));
        this.Sorc = new TownRoom(12, 3, "pont", "sorcier");
        this.space.add(this.Sorc);
        this.space.add(new TownRoom(12, 4, false, "path", null));
        this.space.add(new TownRoom(12, 5, false, "path", null));
        this.space.add(new TownRoom(12, 6, false, "path", null));
        this.space.add(new TownRoom(12, 7, false, "path", null));
        this.space.add(new TownRoom(12, 8, false, "path", null));
        this.space.add(new TownRoom(12, 9, false, "path", null));
        this.space.add(new TownRoom(12, 10, false, "path", null));
        this.space.add(new TownRoom(12, 11, false, "path", null));
        this.space.add(new TownRoom(12, 12, false, "path", null));
        this.space.add(new TownRoom(12, 13, false, "path", null));
        this.space.add(new TownRoom(12, 14, false, "path", null));
        this.space.add(new TownRoom(12, 15, false, "path", null));
        this.space.add(new TownRoom(12, 16, "herbe", null));
        this.space.add(new TownRoom(12, 17, "herbe", null));
        this.space.add(new TownRoom(12, 18, "herbe", null));
        this.space.add(new TownRoom(12, 19, "mur", null));
        this.space.add(new TownRoom(13, 0, "mur", null));
        this.space.add(new TownRoom(13, 1, "herbe", null));
        this.space.add(new TownRoom(13, 2, "herbe", null));
        this.space.add(new TownRoom(13, 3, false, "path", null));
        this.space.add(new TownRoom(13, 4, false, "path", null));
        this.space.add(new TownRoom(13, 5, false, "path", null));
        this.space.add(new TownRoom(13, 6, false, "path", null));
        this.space.add(new TownRoom(13, 7, false, "path", null));
        this.space.add(new TownRoom(13, 8, false, "path", null));
        this.space.add(new TownRoom(13, 9, false, "path", null));
        this.space.add(new TownRoom(13, 10, false, "path", null));
        this.space.add(new TownRoom(13, 11, false, "path", null));
        this.space.add(new TownRoom(13, 12, false, "path", null));
        this.space.add(new TownRoom(13, 13, false, "path", null));
        this.space.add(new TownRoom(13, 14, false, "path", null));
        this.space.add(new TownRoom(13, 15, false, "path", null));
        this.space.add(new TownRoom(13, 16, "herbe", null));
        this.space.add(new TownRoom(13, 17, "eau", null));
        this.space.add(new TownRoom(13, 18, "herbe", null));
        this.space.add(new TownRoom(13, 19, "mur", null));
        this.space.add(new TownRoom(14, 0, "mur", null));
        this.space.add(new TownRoom(14, 1, "herbe", null));
        this.space.add(new TownRoom(14, 2, "herbe", null));
        this.space.add(new TownRoom(14, 3, false, "path", null));
        this.space.add(new TownRoom(14, 4, false, "path", null));
        this.space.add(new TownRoom(14, 5, false, "path", null));
        this.space.add(new TownRoom(14, 6, false, "path", null));
        this.space.add(new TownRoom(14, 7, false, "path", null));
        this.space.add(new TownRoom(14, 8, false, "path", null));
        this.space.add(new TownRoom(14, 9, false, "path", null));
        this.space.add(new TownRoom(14, 10, false, "path", null));
        this.space.add(new TownRoom(14, 11, false, "path", null));
        this.space.add(new TownRoom(14, 12, false, "path", null));
        this.space.add(new TownRoom(14, 13, false, "path", null));
        this.space.add(new TownRoom(14, 14, false, "path", null));
        this.space.add(new TownRoom(14, 15, false, "path", null));
        this.space.add(new TownRoom(14, 16, "herbe", null));
        this.space.add(new TownRoom(14, 17, "eau", null));
        this.space.add(new TownRoom(14, 18, "herbe", null));
        this.space.add(new TownRoom(14, 19, "mur", null));
        this.space.add(new TownRoom(15, 0, "mur", null));
        this.space.add(new TownRoom(15, 1, "herbe", null));
        this.space.add(new TownRoom(15, 2, "herbe", null));
        this.space.add(new TownRoom(15, 3, false, "path", null));
        this.space.add(new TownRoom(15, 4, false, "path", null));
        this.space.add(new TownRoom(15, 5, false, "path", null));
        this.space.add(new TownRoom(15, 6, false, "path", null));
        this.space.add(new TownRoom(15, 7, false, "path", null));
        this.space.add(new TownRoom(15, 8, false, "path", null));
        this.space.add(new TownRoom(15, 9, false, "path", null));
        this.space.add(new TownRoom(15, 10, false, "path", null));
        this.space.add(new TownRoom(15, 11, false, "path", null));
        this.space.add(new TownRoom(15, 12, false, "path", null));
        this.space.add(new TownRoom(15, 13, false, "path", null));
        this.space.add(new TownRoom(15, 14, false, "path", null));
        this.space.add(new TownRoom(15, 15, "herbe", null));
        this.space.add(new TownRoom(15, 16, "herbe", null));
        this.space.add(new TownRoom(15, 17, "eau", null));
        this.space.add(new TownRoom(15, 18, "herbe", null));
        this.space.add(new TownRoom(15, 19, "mur", null));
        this.space.add(new TownRoom(16, 0, "mur", null));
        this.space.add(new TownRoom(16, 1, "herbe", null));
        this.space.add(new TownRoom(16, 2, "herbe", null));
        this.Bank = new TownRoom(16, 3, "pont", "bank");
        this.space.add(this.Bank);
        this.space.add(new TownRoom(16, 4, false, "path", null));
        this.space.add(new TownRoom(16, 5, false, "path", null));
        this.space.add(new TownRoom(16, 6, false, "path", null));
        this.space.add(new TownRoom(16, 7, false, "path", null));
        this.space.add(new TownRoom(16, 8, false, "path", null));
        this.space.add(new TownRoom(16, 9, false, "path", null));
        this.space.add(new TownRoom(16, 10, false, "path", null));
        this.space.add(new TownRoom(16, 11, false, "path", null));
        this.space.add(new TownRoom(16, 12, false, "path", null));
        this.space.add(new TownRoom(16, 13, false, "path", null));
        this.space.add(new TownRoom(16, 14, false, "path", null));
        this.space.add(new TownRoom(16, 15, "herbe", null));
        this.space.add(new TownRoom(16, 16, "herbe", null));
        this.space.add(new TownRoom(16, 17, "eau", null));
        this.space.add(new TownRoom(16, 18, "herbe", null));
        this.space.add(new TownRoom(16, 19, "mur", null));
        this.space.add(new TownRoom(17, 0, "mur", null));
        this.space.add(new TownRoom(17, 1, "herbe", null));
        this.space.add(new TownRoom(17, 2, "herbe", null));
        this.space.add(new TownRoom(17, 3, false, "path", null));
        this.space.add(new TownRoom(17, 4, false, "path", null));
        this.space.add(new TownRoom(17, 5, false, "path", null));
        this.space.add(new TownRoom(17, 6, false, "path", null));
        this.space.add(new TownRoom(17, 7, false, "path", null));
        this.space.add(new TownRoom(17, 8, false, "path", null));
        this.space.add(new TownRoom(17, 9, false, "path", null));
        this.space.add(new TownRoom(17, 10, false, "path", null));
        this.space.add(new TownRoom(17, 11, false, "path", null));
        this.space.add(new TownRoom(17, 12, false, "path", null));
        this.space.add(new TownRoom(17, 13, false, "path", null));
        this.space.add(new TownRoom(17, 14, "herbe", null));
        this.space.add(new TownRoom(17, 15, "herbe", null));
        this.space.add(new TownRoom(17, 16, "eau", null));
        this.space.add(new TownRoom(17, 17, "eau", null));
        this.space.add(new TownRoom(17, 18, "herbe", null));
        this.space.add(new TownRoom(17, 19, "mur", null));
        this.space.add(new TownRoom(18, 0, "mur", null));
        this.space.add(new TownRoom(18, 1, "herbe", null));
        this.space.add(new TownRoom(18, 2, "herbe", null));
        this.space.add(new TownRoom(18, 3, false, "path", null));
        this.space.add(new TownRoom(18, 4, false, "path", null));
        this.space.add(new TownRoom(18, 5, false, "path", null));
        this.space.add(new TownRoom(18, 6, false, "path", null));
        this.space.add(new TownRoom(18, 7, false, "path", null));
        this.space.add(new TownRoom(18, 8, false, "path", null));
        this.space.add(new TownRoom(18, 9, false, "path", null));
        this.space.add(new TownRoom(18, 10, false, "path", null));
        this.space.add(new TownRoom(18, 11, false, "path", null));
        this.space.add(new TownRoom(18, 12, false, "path", null));
        this.space.add(new TownRoom(18, 13, false, "path", null));
        this.space.add(new TownRoom(18, 14, "herbe", null));
        this.space.add(new TownRoom(18, 15, "herbe", null));
        this.space.add(new TownRoom(18, 16, "eau", null));
        this.space.add(new TownRoom(18, 17, "eau", null));
        this.space.add(new TownRoom(18, 18, "herbe", null));
        this.space.add(new TownRoom(18, 19, "mur", null));
        this.space.add(new TownRoom(19, 0, "mur", null));
        this.space.add(new TownRoom(19, 1, "herbe", null));
        this.space.add(new TownRoom(19, 2, "herbe", null));
        this.space.add(new TownRoom(19, 3, false, "path", null));
        this.space.add(new TownRoom(19, 4, false, "path", null));
        this.space.add(new TownRoom(19, 5, false, "path", null));
        this.space.add(new TownRoom(19, 6, false, "path", null));
        this.space.add(new TownRoom(19, 7, false, "path", null));
        this.space.add(new TownRoom(19, 8, false, "path", null));
        this.space.add(new TownRoom(19, 9, false, "path", null));
        this.space.add(new TownRoom(19, 10, false, "path", null));
        this.space.add(new TownRoom(19, 11, false, "path", null));
        this.space.add(new TownRoom(19, 12, false, "path", null));
        this.space.add(new TownRoom(19, 13, "herbe", null));
        this.space.add(new TownRoom(19, 14, "herbe", null));
        this.space.add(new TownRoom(19, 15, "herbe", null));
        this.space.add(new TownRoom(19, 16, "eau", null));
        this.space.add(new TownRoom(19, 17, "eau", null));
        this.space.add(new TownRoom(19, 18, "herbe", null));
        this.space.add(new TownRoom(19, 19, "mur", null));
        this.space.add(new TownRoom(20, 0, "mur", null));
        this.space.add(new TownRoom(20, 1, "herbe", null));
        this.space.add(new TownRoom(20, 2, "herbe", null));
        this.Forg = new TownRoom(20, 3, "pont", "forge");
        this.space.add(this.Forg);
        this.space.add(new TownRoom(20, 4, false, "path", null));
        this.space.add(new TownRoom(20, 5, false, "path", null));
        this.space.add(new TownRoom(20, 6, false, "path", null));
        this.space.add(new TownRoom(20, 7, false, "path", null));
        this.space.add(new TownRoom(20, 8, false, "path", null));
        this.space.add(new TownRoom(20, 9, false, "path", null));
        this.space.add(new TownRoom(20, 10, false, "path", null));
        this.space.add(new TownRoom(20, 11, false, "path", null));
        this.space.add(new TownRoom(20, 12, "herbe", null));
        this.space.add(new TownRoom(20, 13, "herbe", null));
        this.space.add(new TownRoom(20, 14, "herbe", null));
        this.space.add(new TownRoom(20, 15, "pont", null));
        this.space.add(new TownRoom(20, 16, "eau", null));
        this.space.add(new TownRoom(20, 17, "eau", null));
        this.space.add(new TownRoom(20, 18, "herbe", null));
        this.space.add(new TownRoom(20, 19, "mur", null));
        this.space.add(new TownRoom(21, 0, "mur", null));
        this.space.add(new TownRoom(21, 1, "herbe", null));
        this.space.add(new TownRoom(21, 2, "herbe", null));
        this.space.add(new TownRoom(21, 3, false, "path", null));
        this.space.add(new TownRoom(21, 4, false, "path", null));
        this.space.add(new TownRoom(21, 5, false, "path", null));
        this.space.add(new TownRoom(21, 6, false, "path", null));
        this.space.add(new TownRoom(21, 7, false, "path", null));
        this.space.add(new TownRoom(21, 8, false, "path", null));
        this.space.add(new TownRoom(21, 9, false, "path", null));
        this.space.add(new TownRoom(21, 10, false, "path", null));
        this.space.add(new TownRoom(21, 11, "herbe", null));
        this.space.add(new TownRoom(21, 12, "herbe", null));
        this.space.add(new TownRoom(21, 13, "herbe", null));
        this.space.add(new TownRoom(21, 14, "pont", null));
        this.space.add(new TownRoom(21, 15, "pont", null));
        this.space.add(new TownRoom(21, 16, "eau", null));
        this.space.add(new TownRoom(21, 17, "eau", null));
        this.space.add(new TownRoom(21, 18, "herbe", null));
        this.space.add(new TownRoom(21, 19, "mur", null));
        this.space.add(new TownRoom(22, 0, "mur", null));
        this.space.add(new TownRoom(22, 1, "herbe", null));
        this.space.add(new TownRoom(22, 2, false, "path", null));
        this.space.add(new TownRoom(22, 3, false, "path", null));
        this.space.add(new TownRoom(22, 4, false, "path", null));
        this.space.add(new TownRoom(22, 5, false, "path", null));
        this.space.add(new TownRoom(22, 6, false, "path", null));
        this.space.add(new TownRoom(22, 7, false, "path", null));
        this.space.add(new TownRoom(22, 8, false, "path", null));
        this.space.add(new TownRoom(22, 9, false, "path", null));
        this.space.add(new TownRoom(22, 10, false, "path", null));
        this.space.add(new TownRoom(22, 11, "herbe", null));
        this.space.add(new TownRoom(22, 12, "eau", null));
        this.space.add(new TownRoom(22, 13, "eau", null));
        this.space.add(new TownRoom(22, 14, "pont", null));
        this.space.add(new TownRoom(22, 15, "pont", null));
        this.space.add(new TownRoom(22, 16, "eau", null));
        this.space.add(new TownRoom(22, 17, "eau", null));
        this.space.add(new TownRoom(22, 18, "herbe", null));
        this.space.add(new TownRoom(22, 19, "mur", null));
        this.space.add(new TownRoom(23, 0, "mur", null));
        this.space.add(new TownRoom(23, 1, "herbe", null));
        this.space.add(new TownRoom(23, 2, false, "path", null));
        this.space.add(new TownRoom(23, 3, false, "path", null));
        this.space.add(new TownRoom(23, 4, false, "path", null));
        this.space.add(new TownRoom(23, 5, false, "path", null));
        this.space.add(new TownRoom(23, 6, false, "path", null));
        this.space.add(new TownRoom(23, 7, false, "path", null));
        this.space.add(new TownRoom(23, 8, false, "path", null));
        this.space.add(new TownRoom(23, 9, false, "path", null));
        this.space.add(new TownRoom(23, 10, "herbe", null));
        this.space.add(new TownRoom(23, 11, "eau", null));
        this.space.add(new TownRoom(23, 12, "eau", null));
        this.space.add(new TownRoom(23, 13, "eau", null));
        this.space.add(new TownRoom(23, 14, "pont", null));
        this.space.add(new TownRoom(23, 15, "pont", null));
        this.space.add(new TownRoom(23, 16, "eau", null));
        this.space.add(new TownRoom(23, 17, "eau", null));
        this.space.add(new TownRoom(23, 18, "herbe", null));
        this.space.add(new TownRoom(23, 19, "mur", null));
        this.space.add(new TownRoom(24, 0, "mur", null));
        this.space.add(new TownRoom(24, 1, "herbe", null));
        this.space.add(new TownRoom(24, 2, false, "path", null));
        this.space.add(new TownRoom(24, 3, false, "path", null));
        this.space.add(new TownRoom(24, 4, false, "path", null));
        this.space.add(new TownRoom(24, 5, false, "path", null));
        this.space.add(new TownRoom(24, 6, false, "path", null));
        this.space.add(new TownRoom(24, 7, false, "path", null));
        this.space.add(new TownRoom(24, 8, false, "path", null));
        this.space.add(new TownRoom(24, 9, "herbe", null));
        this.space.add(new TownRoom(24, 10, "herbe", null));
        this.space.add(new TownRoom(24, 11, "eau", null));
        this.space.add(new TownRoom(24, 12, "eau", null));
        this.space.add(new TownRoom(24, 13, "eau", null));
        this.space.add(new TownRoom(24, 14, "eau", null));
        this.space.add(new TownRoom(24, 15, "eau", null));
        this.space.add(new TownRoom(24, 16, "eau", null));
        this.space.add(new TownRoom(24, 17, "eau", null));
        this.space.add(new TownRoom(24, 18, "herbe", null));
        this.space.add(new TownRoom(24, 19, "mur", null));
        this.space.add(new TownRoom(25, 0, "mur", null));
        this.space.add(new TownRoom(25, 1, "herbe", null));
        this.space.add(new TownRoom(25, 2, "herbe", null));
        this.space.add(new TownRoom(25, 3, false, "path", null));
        this.space.add(new TownRoom(25, 4, false, "path", null));
        this.space.add(new TownRoom(25, 5, false, "path", null));
        this.space.add(new TownRoom(25, 6, false, "path", null));
        this.space.add(new TownRoom(25, 7, false, "path", null));
        this.space.add(new TownRoom(25, 8, false, "path", null));
        this.space.add(new TownRoom(25, 9, "herbe", null));
        this.space.add(new TownRoom(25, 10, "herbe", null));
        this.space.add(new TownRoom(25, 11, "eau", null));
        this.space.add(new TownRoom(25, 12, "eau", null));
        this.space.add(new TownRoom(25, 13, "eau", null));
        this.space.add(new TownRoom(25, 14, "eau", null));
        this.space.add(new TownRoom(25, 15, "eau", null));
        this.space.add(new TownRoom(25, 16, "eau", null));
        this.space.add(new TownRoom(25, 17, "eau", null));
        this.space.add(new TownRoom(25, 18, "herbe", null));
        this.space.add(new TownRoom(25, 19, "mur", null));
        this.space.add(new TownRoom(26, 0, "mur", null));
        this.space.add(new TownRoom(26, 1, "herbe", null));
        this.space.add(new TownRoom(26, 2, "herbe", null));
        this.space.add(new TownRoom(26, 3, "herbe", null));
        this.space.add(new TownRoom(26, 4, "herbe", null));
        this.space.add(new TownRoom(26, 5, "herbe", null));
        this.space.add(new TownRoom(26, 6, false, "path", null));
        this.space.add(new TownRoom(26, 7, false, "path", null));
        this.space.add(new TownRoom(26, 8, "herbe", null));
        this.space.add(new TownRoom(26, 9, "herbe", null));
        this.space.add(new TownRoom(26, 10, "herbe", null));
        this.space.add(new TownRoom(26, 11, "eau", null));
        this.space.add(new TownRoom(26, 12, "eau", null));
        this.space.add(new TownRoom(26, 13, "eau", null));
        this.space.add(new TownRoom(26, 14, "eau", null));
        this.space.add(new TownRoom(26, 15, "eau", null));
        this.space.add(new TownRoom(26, 16, "eau", null));
        this.space.add(new TownRoom(26, 17, "eau", null));
        this.space.add(new TownRoom(26, 18, "herbe", null));
        this.space.add(new TownRoom(26, 19, "mur", null));
        this.space.add(new TownRoom(27, 0, "mur", null));
        this.space.add(new TownRoom(27, 1, "herbe", null));
        this.space.add(new TownRoom(27, 2, "herbe", null));
        this.space.add(new TownRoom(27, 3, "herbe", null));
        this.space.add(new TownRoom(27, 4, "herbe", null));
        this.space.add(new TownRoom(27, 5, "herbe", null));
        this.space.add(new TownRoom(27, 6, "herbe", null));
        this.space.add(new TownRoom(27, 7, "herbe", null));
        this.space.add(new TownRoom(27, 8, "herbe", null));
        this.space.add(new TownRoom(27, 9, "herbe", null));
        this.space.add(new TownRoom(27, 10, "herbe", null));
        this.space.add(new TownRoom(27, 11, "herbe", null));
        this.space.add(new TownRoom(27, 12, "eau", null));
        this.space.add(new TownRoom(27, 13, "eau", null));
        this.space.add(new TownRoom(27, 14, "eau", null));
        this.space.add(new TownRoom(27, 15, "eau", null));
        this.space.add(new TownRoom(27, 16, "eau", null));
        this.space.add(new TownRoom(27, 17, "eau", null));
        this.space.add(new TownRoom(27, 18, "herbe", null));
        this.space.add(new TownRoom(27, 19, "mur", null));
        this.space.add(new TownRoom(28, 0, "mur", null));
        this.space.add(new TownRoom(28, 1, "herbe", null));
        this.space.add(new TownRoom(28, 2, "herbe", null));
        this.space.add(new TownRoom(28, 3, "herbe", null));
        this.space.add(new TownRoom(28, 4, "herbe", null));
        this.space.add(new TownRoom(28, 5, "herbe", null));
        this.space.add(new TownRoom(28, 6, "herbe", null));
        this.space.add(new TownRoom(28, 7, "herbe", null));
        this.space.add(new TownRoom(28, 8, "herbe", null));
        this.space.add(new TownRoom(28, 9, "herbe", null));
        this.space.add(new TownRoom(28, 10, "herbe", null));
        this.space.add(new TownRoom(28, 11, "herbe", null));
        this.space.add(new TownRoom(28, 12, "herbe", null));
        this.space.add(new TownRoom(28, 13, "herbe", null));
        this.space.add(new TownRoom(28, 14, "herbe", null));
        this.space.add(new TownRoom(28, 15, "herbe", null));
        this.space.add(new TownRoom(28, 16, "herbe", null));
        this.space.add(new TownRoom(28, 17, "herbe", null));
        this.space.add(new TownRoom(28, 18, "herbe", null));
        this.space.add(new TownRoom(28, 19, "mur", null));
        this.space.add(new TownRoom(29, 0, "mur", null));
        this.space.add(new TownRoom(29, 1, "mur", null));
        this.space.add(new TownRoom(29, 2, "mur", null));
        this.space.add(new TownRoom(29, 3, "mur", null));
        this.space.add(new TownRoom(29, 4, "mur", null));
        this.space.add(new TownRoom(29, 5, "mur", null));
        this.space.add(new TownRoom(29, 6, "mur", null));
        this.space.add(new TownRoom(29, 7, "mur", null));
        this.space.add(new TownRoom(29, 8, "mur", null));
        this.space.add(new TownRoom(29, 9, "mur", null));
        this.space.add(new TownRoom(29, 10, "mur", null));
        this.space.add(new TownRoom(29, 11, "mur", null));
        this.space.add(new TownRoom(29, 12, "mur", null));
        this.space.add(new TownRoom(29, 13, "mur", null));
        this.space.add(new TownRoom(29, 14, "mur", null));
        this.space.add(new TownRoom(29, 15, "mur", null));
        this.space.add(new TownRoom(29, 16, "mur", null));
        this.space.add(new TownRoom(29, 17, "mur", null));
        this.space.add(new TownRoom(29, 18, "mur", null));
        this.space.add(new TownRoom(29, 19, "mur", null));


        /*
        BLOCAGE DES TUILES SUR LES BATIMENTS
         */
        int index;
        for(int j = 1; j<3; j++) {
            for(int i = 11; i<23; i++) {
                index = this.space.indexOf(new TownRoom(i, j, false));
                this.space.get(index).setWalkable(false);
            }
        }

        for(int j = 1; j<3; j++) {
            for(int i = 1; i<4; i++) {
                index = this.space.indexOf(new TownRoom(i, j, false));
                this.space.get(index).setWalkable(false);
            }
        }

    }

    public boolean canMove(int x, int y) { // Check si on peut bouger sur la salle de coordonnées x et y dans le vilage.
        int getIndex = this.space.indexOf(new TownRoom(x, y, false));
        if (getIndex == -1)
            getIndex = this.space.indexOf(new TownRoom(x, y, true));
        if(getIndex == -1)
            return false;
        else return this.space.get(getIndex).getWalkable();

    }

    /*
    Offset de map pour le village.
 */
    public void addXOffset() {
        this.xMapOffset += tileSize;
    }

    public void remXOffset() {
        this.xMapOffset -= tileSize;
    }

    public void addYOffset() {
        this.yMapOffset += tileSize;
    }

    public void remYOffset() {
        this.yMapOffset -= tileSize;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.web("#525251"));
        gc.fillRect(0, 0, 1200, 700);

        for(TownRoom room : this.space)
            gc.drawImage(room.getImg(), room.getX()*tileSize + xMapOffset, room.getY()*tileSize + yMapOffset);
        gc.drawImage(imgCamp, this.Camp.getX()*tileSize - 100 + xMapOffset, this.Camp.getY()*tileSize-250 + yMapOffset);
        gc.drawImage(imgBank, this.Bank.getX()*tileSize - 150 + xMapOffset, this.Bank.getY()*tileSize-300 + yMapOffset);
        gc.drawImage(imgForg, this.Forg.getX()*tileSize - 125 + xMapOffset, this.Forg.getY()*tileSize-245 + yMapOffset);
        gc.drawImage(imgSorc, this.Sorc.getX()*tileSize - 100 + xMapOffset, this.Sorc.getY()*tileSize-300 + yMapOffset);
    }

    public boolean isOnExit(int x, int y) {
        return this.space.contains(new TownRoom(x, y, true, "path", null));
    }


    public TownRoom getCamp() {
        return Camp;
    }

    public TownRoom getSorc() {
        return Sorc;
    }

    public TownRoom getBank() {
        return Bank;
    }

    public TownRoom getForg() {
        return Forg;
    }
    
    public void setOffset(int x, int y) {
        this.xMapOffset = x;
        this.yMapOffset = y;
    }
}
