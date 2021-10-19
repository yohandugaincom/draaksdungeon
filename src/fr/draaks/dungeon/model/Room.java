package fr.draaks.dungeon.model;

public class Room {
    private int x;
    private int y;
    private int state;
    private boolean exitRoom;
    private boolean isVisited;

    public Room(int x, int y) {
        this.x = x;
        this.y = y;
        this.exitRoom = false;
        this.isVisited = false;
    }

    public Room(int x, int y, boolean exitRoom) {
        this.x = x;
        this.y = y;
        this.exitRoom = exitRoom;
        this.isVisited = false;
    }

    // Permet d'utiliser les fonctions du type contains() ou containsAll() pour les Arraylist etc

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (x != room.x) return false;
        if (y != room.y) return false;
        return exitRoom == room.exitRoom;
    }
    // A faire avec la fonction equals()
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + (exitRoom ? 1 : 0);
        return result;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    } // affichage coord salle

    public int getState() { return state; } // renvoie 0 si la salle n'a pas été traité dans la gen du donjon, revoie 1 sinon.

    public void setState(int state) {
        this.state = state;
    } // Change l'état de la salle.

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isExitRoom() {
        return exitRoom;
    }
    
    public void setVisited() {
        this.isVisited = true;
    }
    
    public boolean getVisited() {
        return this.isVisited;
    }
}
