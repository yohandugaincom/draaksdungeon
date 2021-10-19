# draaksdungeon

Jeu réalisé par :
	Maxime BAUCHET, Yohan DUGAIN, Damien HEURET, Bastien MATHIAS

Pré-requis :
	Il est nécessaire d'y ajouter la librairie JDBC pour la connexion avec MySQL.
		- Clique droit sur le projet Draaks
		- Build Path
		- Configure Build Path
		- Libraries
		- Add External Jars
		- Sélectionner le fichier mysql-connector-java-8.0.20.jar (dans fr.draaks.dungeon)
		- Apply and close
	Il est nécessaire d'avoir JavaFX d'installé.
	Il est nécessaire d'utiliser Java 1.8.

Compilation :
	Compiler le projet depuis fr.draaks.dungeon.MainRoot.java
	
Mode histoire :
	Le but du jeu étant de remporter ses combats afin de gagner
	de l’XP et ainsi de pouvoir augmenter de niveau.
	
Mode multijoueur :
	Une connexion internet est requise.
	Au moins 2 PC's ayant le jeu avec la même version est nécessaire.
	Seul des combats entre 2 joueurs en ligne est possible avec notre version.
	L'exploration du monde Draaks en ligne est en développement.
	
Exportation en JAR :
	Nous n'avons pas pu exporter en JAR car ayant appris qu'il était necessaire de
	définir les contrôleurs dans le code Java en lui-même. Or, nous avons définit les
	contrôleurs dans les fichiers FXMl via le paramètre fx:controler.