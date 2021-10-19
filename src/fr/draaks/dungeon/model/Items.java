package fr.draaks.dungeon.model;

public class Items extends Entity {

    /*
        Classe qui désigne tous les objets récupérable dans le jeu (armes/armures/torches/potions). Elle hérite d'Entity.
        Il y a une quantity, sa valeur réelle, sa valeur par rapport à un marchant (null si ce n'est pas le bon marchand)
        Une variable traderName pour identifier le bon vendeur

        Cette classe est abstraire.
     */

    protected int quantity;
    protected int realValue; // Valeur pour tout marchands. La variable value peut changer en fonction du trader.
    protected Integer value; // Long si la valeur est null alors on ne peut pas vendre l'item.
    protected String traderName; // Pour savoir qui achète/vends cet item.

    // Constructor
    public Items(Integer x, Integer y, String name, int quantity, int realValue, String traderName) {
        super(x, y, name, null, null, null, null);
        this.quantity = quantity;
        this.realValue = realValue;
        this.traderName = traderName;
    }

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setValue(String tileMarchand) {
        this.value = (tileMarchand.equals(this.traderName)) ? realValue : null;
    }
    
}
