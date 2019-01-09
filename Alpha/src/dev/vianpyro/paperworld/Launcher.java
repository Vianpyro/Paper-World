package dev.vianpyro.paperworld;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Launcher {

	public static int SQUARE; //Note pour le futur réglage : taille case (SQUARE) = (largeur fenétre / 25) * zoom

	private static final String versionType = "inDev"; //Variable qui indique si le jeu est en version "inDev", "Alpha", "Beta" ou jouable : ""
	private static final float version = 1.0f; //Variable qui indique le numéro de la version
	public static int WIDTH, HEIGHT;

	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Fonction qui detecte les dimentions de l'écran de l'utilisateur
		WIDTH = Math.round((int)screenSize.getWidth() / 9) * 8; //Définition de la largeur de la fenére en fonction des dimentions de l'écran de l'utilisateur
		HEIGHT = Math.round((int)screenSize.getHeight() / 9) * 8; //Définition de la hauteur de la fenére en fonction des dimentions de l'écran de l'utilisateur
		SQUARE = WIDTH / 25; //Définition de la taille d'une case du jeu en fonction de la largeur de la largeur de la fenétre

		Logger.initialisation();
		Logger.log("Logs initialisated");
		Game game = new Game("Paper world V:" + versionType + "-" + version, WIDTH, HEIGHT); //Fonction qui appelle la construction du jeu
		Logger.log("Game initialisated");
		game.start(); //Appelle la méthode "start" de la classe "game" ; qui lancera le jeu
		Logger.log("Game starting");
	}
}
