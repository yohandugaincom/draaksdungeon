package fr.draaks.dungeon.model;

/*
Cette class est utilisé pour transformé le donjon en tableau à 2 Dimensions pour utiliser l'algorithme A* !
 */

public class Nodes implements Comparable<Nodes>{
    private int x;
    private int y;
    private Integer value;
    private Nodes parents;
    private boolean isRoom;

    public Nodes(int x, int y, boolean isRoom) {
        this.x = x;
        this.y = y;
        this.isRoom = isRoom;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Integer getValue() {
        return value;
    }

    public Nodes getParents() {
        return parents;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setParents(Nodes parents) {
        this.parents = parents;
    }

    public String affichage() {
        return this.isRoom ? "X" : " ";
    }

    public boolean getIsRoom() {
        return this.isRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nodes nodes = (Nodes) o;

        if (x != nodes.x) return false;
        return y == nodes.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public int compareTo(Nodes nodesO){
        return this.value.compareTo(nodesO.getValue());
    }

    public void resetNodes() {
        this.parents = null;
        this.value = null;
    }

}
