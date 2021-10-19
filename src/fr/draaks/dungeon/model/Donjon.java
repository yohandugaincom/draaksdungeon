package fr.draaks.dungeon.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import fr.draaks.dungeon.MainRoot;

import static java.lang.Math.*;

public class Donjon {
    private static final int tileSize = 100;
    private double difficulty;
    private int range;
    private double fatigue;
    private int xMapOffset = 550;
    private int yMapOffset = 325;
    private ArrayList<Room> rooms = new ArrayList<>();
    MainRoot Main;

    private ArrayList<Mobs> mobsL = new ArrayList<>();
    private ArrayList<Items> itemsL = new ArrayList<>();
    
    private final Image[] dungSprites = new Image[]{
            new Image("file:src/fr/draaks/dungeon/bib/general/textu1.png", tileSize, tileSize, true, false),
            new Image("file:src/fr/draaks/dungeon/bib/general/textu5.png", tileSize, tileSize, true, false),
            new Image("file:src/fr/draaks/dungeon/bib/general/textuExit.png", tileSize, tileSize, true, false)};

    private Nodes[][] nodesMap;
    
    @SuppressWarnings("serial")
	public Donjon(double difficulty, double range, int lvl) {
        this.difficulty = difficulty; // La difficulté évoluera avec le niveau de joueur
        this.range = (int) Math.floor(range); // Définit la taille du donjon, cette taillee dépend elle aussi du niveau du joueur.
        this.fatigue = 0.0;
        // Génération d'un donjon dont au moins 20% de la surfaces sont des salles.
        if (this.difficulty < 0.3) {
        	this.range = 7;
            int[] xRoom = new int[]{0, 0, 0,-1, 0,-1, 0, 1, 2, 2, 2, 1, 3, 2, 2, 3, 3, 3, 2, 1, 3, 3, 4, 5};
            int[] yRoom = new int[]{0,-1,-2,-2, 1, 1, 2, 2, 2, 1, 0, 0, 0,-1,-2,-2,-3,-4,-4,-4,-5,-6,-4,-4};
            for(int i = 0; i<xRoom.length; i++) {
                if(i == xRoom.length-1)
                    this.rooms.add(new Room(xRoom[i], yRoom[i], true));
                else
                    this.rooms.add(new Room(xRoom[i], yRoom[i]));
            }
            mobsL.add(new Gobelin(2, 1, "Gobelino"));
            itemsL.add(new Items(3, -4, "Potion'V", 2, 10, "sorcier"));
        }
        else {
            while (this.len() < pow(this.range * 2, 2) * 0.2) {
                this.rooms.clear(); //Suppression de toutes les salles générées précedemment.
                this.rooms.add(new Room(0, 0)); // Ajout de la salle initiale.
                this.buildDonjonV2(); // Génération du donjon
                System.out.println(this.len()); // Affichage de la longueur du donjon (=nbr de salles)
            }
            Random rand = new Random();
            int randExitRoom = rand.nextInt((int) (this.len() * 0.1 + 1));
            Room exitRoom = this.rooms.get(this.len() - randExitRoom - 2);
            this.rooms.add(new Room(exitRoom.getX(), exitRoom.getY(), true));
            this.rooms.remove(exitRoom);

            rand = new Random();
            int randInt;
            // GEN DES MOBS
            int[] coord;
            while(mobsL.size() < this.range/4) {
                ArrayList<Mobs> mobDB = new ArrayList<Mobs>() {
                    {
                        add(new Squelette(null, null, "Squelette"));
                        add(new Gobelin(null, null, "Gobelin"));
                        add(new Geant(null, null, "Geant"));
                    }
                };
                randInt = rand.nextInt(mobDB.size());
                coord = this.getRandomRoom();
                switch(randInt) {
                    case 0:
                        mobsL.add(new Squelette(coord[0], coord[1], "Skeleton", 115+rand.nextInt((int)difficulty+1)*4, 11+rand.nextInt((int)difficulty+1)*4, lvl + rand.nextInt(lvl)));
                        break;
                    case 1:
                        mobsL.add(new Geant(coord[0], coord[1], "Geantima", 175+rand.nextInt((int)difficulty+1)*4, 6+rand.nextInt((int)difficulty+1)*4, lvl + rand.nextInt(lvl)));
                        break;
                    case 2:
                        mobsL.add(new Gobelin(coord[0], coord[1], "Gobelino", 85+rand.nextInt((int)difficulty+1)*4, 7+rand.nextInt((int)difficulty+1)*4, lvl + rand.nextInt(lvl)));
                        break;
                }
            }

            while(itemsL.size() < (this.range / 2)) {
                ArrayList<Items> itemDB = new ArrayList<Items>() {
                    {
                        add(new Items(null, null, "Potion'V", 1, 10, "sorcier"));
                        add(new Items(null, null, "Potion'O", 1, 10, "sorcier"));
                        add(new Items(null, null, "Potion'R", 1, 10, "sorcier"));
                    }
                };
                randInt = rand.nextInt(itemDB.size());
                coord = this.getRandomRoom();
                switch(randInt) {
                    case 0:
                        itemsL.add(new Items(coord[0], coord[1], "Potion'V", 1, 10, "sorcier"));
                        break;
                    case 1:
                        itemsL.add(new Items(coord[0], coord[1], "Potion'R", 1, 10, "sorcier"));
                        break;
                    case 2:
                        itemsL.add(new Items(coord[0], coord[1], "Potion'O", 1, 10, "sorcier"));
                        break;
                }
            }
        }
        convertMapNodes();
    }

    /*
        Fonction qui check si on salle est plaçable à un endroit donner.
        J'ai fais cette fonction pour éviter de généré deux salles aux mêmes coordonnées, pour éviter de sortir de la range du donjon, et pour éviter que 4 salles forment un carré.
     */
    public boolean canBeRoom(int x, int y) {
        if(abs(x) >= this.range || abs(y) >= this.range) return false; // Vérification qu'on est bien dans la partie restreint du donjon.
        else if(this.rooms.contains(new Room(x, y))) return false; // Sinon on regarde si la salle n'existe pas.
        else { // Sinon on check si cette salle formera un carré ou non.
            if (this.rooms.containsAll(Arrays.asList(new Room(x - 1, y - 1), new Room(x, y - 1), new Room(x - 1, y)))) return false;
            if (this.rooms.containsAll(Arrays.asList(new Room(x + 1, y - 1), new Room(x, y - 1), new Room(x + 1, y)))) return false;
            if (this.rooms.containsAll(Arrays.asList(new Room(x - 1, y + 1), new Room(x, y + 1), new Room(x - 1, y)))) return false;
            if (this.rooms.containsAll(Arrays.asList(new Room(x + 1, y + 1), new Room(x, y + 1), new Room(x + 1, y)))) return false;
        }
        return true; // Renvoie vrai si tous les tests sont passés avec succès.
    }

    /*
        Ancienne version de la gen du donjon.
     */
    @Deprecated
    public void buildDonjon() {
        boolean hasFinished = false;
        while(!hasFinished) {
            hasFinished = true;
            for (int i = 0; i < this.rooms.size(); i++) {
                if (this.rooms.get(i).getState() == 0) {
                    hasFinished = false;
                    if (random() < 0.8)
                        if (this.canBeRoom(this.rooms.get(i).getX() - 1, this.rooms.get(i).getY()))
                            this.rooms.add(new Room(this.rooms.get(i).getX() - 1, this.rooms.get(i).getY()));
                    if(random() < 0.8)
                        if (this.canBeRoom(this.rooms.get(i).getX() + 1, this.rooms.get(i).getY()))
                            this.rooms.add(new Room(this.rooms.get(i).getX() + 1, this.rooms.get(i).getY()));
                    if(random() < 0.8)
                        if (this.canBeRoom(this.rooms.get(i).getX(), this.rooms.get(i).getY() - 1))
                            this.rooms.add(new Room(this.rooms.get(i).getX(), this.rooms.get(i).getY() - 1));
                    if(random() < 0.8)
                        if (this.canBeRoom(this.rooms.get(i).getX(), this.rooms.get(i).getY() + 1))
                            this.rooms.add(new Room(this.rooms.get(i).getX(), this.rooms.get(i).getY() + 1));
                    this.rooms.get(i).setState(1);
                }

            }
        }
    }

    /*
      Deuxième gen du donjon, qui favorise une étendu plus fiable que la gen 1 qui se contentait de créer des salles autour de la salle actuelle.
     */
    public void buildDonjonV2() {
        double high = 0.9; // Proba pour que la première prochaine salle soit créer.
        double mediumH = 0.50; // 2ième prochaine salle (que les précédentes ont été généré ou non)
        double mediumL = 0.40; // 3ième prochaine salle (que les précédentes ont été généré ou non)
        double low = 0.30; // 4ième prochaine salle (que les précédentes ont été généré ou non)

        boolean hasFinished = false; // Vérif que toutes les salles soient traitées.
        while(!hasFinished) { // Tant que ce n'est pas le cas
            hasFinished = true;
            for (int i = 0; i < this.rooms.size(); i++) {
                if (this.rooms.get(i).getState() == 0) {
                    double rand = random(); // Random pour savoir quelle salle à le plus de chance d'être généré en première.
                    /*
                        Les conditions suivantes sont exactements les mêmes, l'ordre de génération et les probas sont les seules choses qui changent.
                     */
                    if (rand > 0.75) {
                        if (random() < high)
                            if (this.canBeRoom(this.rooms.get(i).getX() - 1, this.rooms.get(i).getY()))
                                this.rooms.add(new Room(this.rooms.get(i).getX() - 1, this.rooms.get(i).getY())); // Placement d'une case à gauche.
                        if (random() < mediumH)
                            if (this.canBeRoom(this.rooms.get(i).getX() + 1, this.rooms.get(i).getY()))
                                this.rooms.add(new Room(this.rooms.get(i).getX() + 1, this.rooms.get(i).getY())); // a droite
                        if (random() < mediumL)
                            if (this.canBeRoom(this.rooms.get(i).getX(), this.rooms.get(i).getY() - 1))
                                this.rooms.add(new Room(this.rooms.get(i).getX(), this.rooms.get(i).getY() - 1)); // en haut
                        if (random() < low)
                            if (this.canBeRoom(this.rooms.get(i).getX(), this.rooms.get(i).getY() + 1))
                                this.rooms.add(new Room(this.rooms.get(i).getX(), this.rooms.get(i).getY() + 1)); // en bas
                    }
                    else if(rand > 0.5) {
                        if (random() < high)
                            if (this.canBeRoom(this.rooms.get(i).getX() + 1, this.rooms.get(i).getY()))
                                this.rooms.add(new Room(this.rooms.get(i).getX() + 1, this.rooms.get(i).getY())); // a droite
                        if (random() < mediumH)
                            if (this.canBeRoom(this.rooms.get(i).getX(), this.rooms.get(i).getY() - 1))
                                this.rooms.add(new Room(this.rooms.get(i).getX(), this.rooms.get(i).getY() - 1));// en haut
                        if (random() < mediumL)
                            if (this.canBeRoom(this.rooms.get(i).getX(), this.rooms.get(i).getY() + 1))
                                this.rooms.add(new Room(this.rooms.get(i).getX(), this.rooms.get(i).getY() + 1));// en bas
                        if (random() < low)
                            if (this.canBeRoom(this.rooms.get(i).getX() - 1, this.rooms.get(i).getY()))
                                this.rooms.add(new Room(this.rooms.get(i).getX() - 1, this.rooms.get(i).getY())); // Placement d'une case à gauche.
                    }
                    else if(rand > 0.25) {
                        if (random() < high)
                            if (this.canBeRoom(this.rooms.get(i).getX(), this.rooms.get(i).getY() - 1))
                                this.rooms.add(new Room(this.rooms.get(i).getX(), this.rooms.get(i).getY() - 1));// en haut
                        if (random() < mediumH)
                            if (this.canBeRoom(this.rooms.get(i).getX(), this.rooms.get(i).getY() + 1))
                                this.rooms.add(new Room(this.rooms.get(i).getX(), this.rooms.get(i).getY() + 1));// en bas
                        if (random() < mediumL)
                            if (this.canBeRoom(this.rooms.get(i).getX() - 1, this.rooms.get(i).getY()))
                                this.rooms.add(new Room(this.rooms.get(i).getX() - 1, this.rooms.get(i).getY())); // Placement d'une case à gauche.
                        if (random() < low)
                            if (this.canBeRoom(this.rooms.get(i).getX() + 1, this.rooms.get(i).getY()))
                                this.rooms.add(new Room(this.rooms.get(i).getX() + 1, this.rooms.get(i).getY())); // a droite
                    }
                    else {
                        if (random() < high)
                            if (this.canBeRoom(this.rooms.get(i).getX(), this.rooms.get(i).getY() + 1))
                                this.rooms.add(new Room(this.rooms.get(i).getX(), this.rooms.get(i).getY() + 1));// en bas
                        if (random() < mediumH)
                            if (this.canBeRoom(this.rooms.get(i).getX() - 1, this.rooms.get(i).getY()))
                                this.rooms.add(new Room(this.rooms.get(i).getX() - 1, this.rooms.get(i).getY())); // Placement d'une case à gauche.
                        if (random() < mediumL)
                            if (this.canBeRoom(this.rooms.get(i).getX() + 1, this.rooms.get(i).getY()))
                                this.rooms.add(new Room(this.rooms.get(i).getX() + 1, this.rooms.get(i).getY())); // a droite
                        if (random() < low)
                            if (this.canBeRoom(this.rooms.get(i).getX(), this.rooms.get(i).getY() - 1))
                                this.rooms.add(new Room(this.rooms.get(i).getX(), this.rooms.get(i).getY() - 1));// en haut
                    }
                }
            }
        }

    }

    public boolean canMove(int x, int y) {
        return this.rooms.contains(new Room(x, y)) || this.rooms.contains(new Room(x, y, true)); // Pour vérifier si le joueur peut se déplacer vers une coordonnée. (On regarde si la salle existe)
    }

    /*
        Offset de map pour le donjon.
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


    public boolean isOnExit(int x, int y) {
        return this.rooms.contains(new Room(x, y, true));
    }

    // Affichage du donjon
    public void afficher(GraphicsContext gc, int x, int y) {
        // GRAPHIQUE
        int largeur = 15; // Distance de vue
        int hauteur = 15; // Distance de vue
        for(int j = y-largeur; j <= y+largeur; j++)
            for(int i = x-hauteur; i <= x+hauteur; i++) {
                if (this.rooms.contains(new Room(i, j))) {
                    gc.drawImage(this.dungSprites[0], (i * tileSize) + this.xMapOffset, j * tileSize + this.yMapOffset);
                    if (!this.rooms.contains(new Room(i, j - 1)) && !this.rooms.contains(new Room(i, j - 1, true)))
                        gc.drawImage(this.dungSprites[1], (i * tileSize) + this.xMapOffset, (j - 1) * tileSize + this.yMapOffset);}
                if (this.rooms.contains(new Room(i, j, true))) {
                    gc.drawImage(this.dungSprites[2], (i * tileSize) + this.xMapOffset, j * tileSize + this.yMapOffset);
                    }
            }

        // CONSOLE
//        for(int j = -this.range; j<this.range+1; j++) {
//            for (int i = -this.range; i < this.range + 1; i++)
//                if (this.rooms.contains(new Room(i, j)))
//                    System.out.print("X");
//                else
//                    System.out.print(" ");
//            System.out.println("");
//        }
    }

    public int len() {
        return this.rooms.size(); // renvoie le nombre de salle présente dans le donjon.
    }
    
    public void setFatigue() {
        this.fatigue += 0.025;
    }

    public double getDifficulty() {
        return this.difficulty;
    }

    public double getFatigue() {
        System.out.println("fatigue : " + this.fatigue);
        return this.fatigue;
    }

    public void resetFatigue() {
        this.fatigue = 0.0;
//        return true;
    }
    
 // C'EST A PARTIR D'ICI POUR GERER LE PATH FINDING MAIS CEST GRAVE RELOU JPP CE LA MORT

    public void convertMapNodes() {
        this.nodesMap = new Nodes[this.range*2+2][this.range*2+2];
        for(int j = 0; j<=this.range*2+1; j++) {
            for(int i = 0; i<=this.range*2+1; i++) {
                if(this.rooms.contains(new Room(i-this.range, j-this.range)) || this.rooms.contains(new Room(i-this.range, j-this.range, true))) {
                    this.nodesMap[i][j] = new Nodes(i, j, true);
                }
                else {
                    this.nodesMap[i][j] = new Nodes(i, j, false);
                }
            }
        }

        for(int j = 0; j<=this.range*2+1; j++) {
            for (int i = 0; i <= this.range * 2 + 1; i++) {
                System.out.print(this.nodesMap[i][j].affichage());
            }
            System.out.println();
        }

//        for(Nodes[] noeuds : this.nodesMap) {
//            for(Nodes noeud : noeuds) {
//                System.out.print(noeud.affichage());
//            }
//            System.out.println();
//        }
//
    }

    public boolean canMoveNode(int x, int y) {
        if(x < 0 || y < 0 || x > this.range*2 + 1 || y > this.range*2 + 1)
            return false;
        else return this.nodesMap[x][y].getIsRoom();
    }

    public void setupNode(Nodes startN, Nodes currN, Nodes finalN) {
        int costs = getNodeCost(startN, currN, finalN);
        if (currN.getValue() == null || currN.getValue() > costs) {
            currN.setValue(costs);
            currN.setParents(startN);
        }
    }

    public int getNodeCost(Nodes prev, Nodes curr, Nodes goal) {
        int HCost = abs(goal.getX() - curr.getX()) + abs(goal.getY() - curr.getY());
        return (prev.getValue() == null) ? HCost : HCost + prev.getValue();
    }

    public Nodes pathfinder(int startX, int startY, int finalX, int finalY) {
        if (startX == finalX && startY == finalY)
            return null;

        for(Nodes[] nodesList : this.nodesMap)
            for(Nodes node : nodesList)
                node.resetNodes();

        ArrayList<Nodes> openNodes = new ArrayList<>();
        ArrayList<Nodes> closedNodes = new ArrayList<>();

        startX += this.range;
        startY += this.range;
        finalX += this.range;
        finalY += this.range;

        Nodes firstNode = this.nodesMap[startX][startY];
        Nodes finalNode = this.nodesMap[finalX][finalY];

        openNodes.add(firstNode);
        Nodes currNode = openNodes.get(0);
//        System.out.println(this.range);

        while(currNode != finalNode) {
            closedNodes.add(currNode);

//            System.out.println(currNode.getX() == finalNode.getX() && currNode.getY() == finalNode.getY() ? "TRUE" : "");

            //System.out.println("COORD : " + (currNode.getX()-this.range-1) + "|" + (currNode.getY()-this.range-1));

            nodeAllMoveChecker(openNodes, closedNodes, finalNode, currNode, -1, 0);
            nodeAllMoveChecker(openNodes, closedNodes, finalNode, currNode, 1, 0);
            nodeAllMoveChecker(openNodes, closedNodes, finalNode, currNode, 0, -1);
            nodeAllMoveChecker(openNodes, closedNodes, finalNode, currNode, 0, 1);

            openNodes.remove(currNode);
            Collections.sort(openNodes);

//            for(Nodes n : openNodes)
//                System.out.print("|" + n.getValue() + " = (" + (n.getX()-this.range) + "," + (n.getY()-this.range) + ")");
//            System.out.println("|");

            currNode = openNodes.get(0);

        }
        return mobMoveTo(this.nodesMap[startX][startY], this.nodesMap[finalX][finalY]);
    }

    private void nodeAllMoveChecker(ArrayList<Nodes> openNodes, ArrayList<Nodes> closedNodes, Nodes finalNode, Nodes currNode, int dX, int dY) {
        if (canMoveNode(currNode.getX() + dX, currNode.getY() + dY)) {
            setupNode(currNode, this.nodesMap[currNode.getX() + dX][currNode.getY() + dY], finalNode);
            if (!closedNodes.contains(this.nodesMap[currNode.getX() + dX][currNode.getY() + dY]))
                openNodes.add(this.nodesMap[currNode.getX() + dX][currNode.getY() + dY]);
        }
    }

    public Nodes mobMoveTo(Nodes startN, Nodes finalN) {
        Nodes currN = finalN;
        int compteur = 0;
        while(currN.getParents() != startN) {
            currN = currN.getParents();
            compteur += 1;
        }
        return compteur < 8 ? currN : null;
    }

    public int[] getRandomRoom() {
        Random rando = new Random();
        int rand = rando.nextInt(this.len());
        while (this.rooms.get(rand).isExitRoom() || this.getItems(this.rooms.get(rand).getX(), this.rooms.get(rand).getY()) != null || this.getMobs(this.rooms.get(rand).getX(), this.rooms.get(rand).getY()) != null) {
            rand = rando.nextInt(this.len());
        }
        return new int[]{this.rooms.get(rand).getX(), this.rooms.get(rand).getY()};
    }

//    public int getRange() {
//        return this.range;
//    }

    public void run(Personnage joueur) {
        for(Mobs enemy : mobsL) {
            Nodes nextStep = this.pathfinder(enemy.getX(), enemy.getY(), joueur.getX(), joueur.getY()); // pour chaque ennemies on calcul le pathfinding jusqu'au joueur
            if(nextStep != null) { // Si le prochain déplacement n'est pas nul alors on check si la prochaine salle possède un mob (si oui alors le premier ne peut pas se déplacer)
                for (Mobs mob : mobsL)
                    if (mob.getX() == nextStep.getX() && mob.getY() == nextStep.getY())
                        return;
                enemy.move(nextStep, this.range); // Si la prochiane case est libre (pas de mob) alors le mob peut bouger
            }
        }
    }

    public void drawEnemy(GraphicsContext gc, Personnage joueur) {
        for(Mobs enemy : mobsL) // On dessine tous mobs
            enemy.draw(gc, joueur);
    }

    public Items getItems(int x, int y) {
        for(Items item : itemsL) { // permet de vérifié si un item est sur l'emplacmeent du joueur
            if (item.getX() == x && item.getY() == y)
                return item;
        }
        return null;
    }

    public Mobs getMobs(int x, int y) {
        for(Mobs mob : mobsL) { // Vérifie si un mob se touve su rl'emplacement du joueur.
            if(mob.getX() == x && mob.getY() == y)
                return mob;
        }
        return null;
    }
    
    public void clearMobs() {
        mobsL.removeIf(mob -> mob.getVie() <= 0);
    }
    
    public void delItem(int x, int y) {
        itemsL.removeIf(item -> item.getX() == x && item.getY() == y);
    }
    
    public Room getRoom(int x, int y) {
        int index = this.rooms.indexOf(new Room(x, y));
        if (index >= 0)
            return this.rooms.get(index);
        else
            return this.rooms.get(this.rooms.indexOf(new Room(x, y, true)));
    }
    
}
