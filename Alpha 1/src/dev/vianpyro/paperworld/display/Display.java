package dev.vianpyro.paperworld.display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Display {

	private JFrame frame; //Initialisation de la fenétre
	private Canvas canvas; //Objet permetant d'afficher des graphiques sur l'écran
	private String title; //Variable utilisée pour le nom de la fenétre
	private int width, height; //Variables utilisées pour les dimentions de la fenétre

	public Display(String title, int width, int height) {
		this.title = title; //Définition du titre de la fenétre
		this.width = width; //Définition de la largeur de la fenétre
		this.height = height; //Définition de la hauteur de la fenétre
		createDisplay(); //Utilisation du constructeur
	}

	private void createDisplay() {
		frame = new JFrame(title); //Ajout du titre de la fenétre é la fenétre lors de son initialisation
		frame.setSize(width, height); //Définition des dimentions de la fenétre
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Fonction qui défini que quand la fenétre se ferme le programme s'arréte
		frame.setResizable(false); //Fonction qui empéche de redimsentionner la fenétre
		frame.setLocationRelativeTo(null); //Positionnement de la fenétre au milieu de l'écran
		frame.setVisible(true); //Affichage la fenétre
		ImageIcon img = new ImageIcon("resources/icon.png"); //Initialisation de l'icon de la fenétre
		frame.setIconImage(img.getImage()); //Définition de l'icon de la fenétre

		canvas = new Canvas(); //Initialisation du canvas
		canvas.setPreferredSize(new Dimension(width, height)); //Définition de la taille voulue de la fenétre
		canvas.setMinimumSize(new Dimension(width, height)); //Définition de la taille minimale de la fenétre pour étre certain que la taille du canvas soit la méme que celle de la fenétre
		canvas.setMaximumSize(new Dimension(width, height)); //Définition de la taille maximale de la fenétre pour étre certain que la taille du canvas soit la méme que celle de la fenétre
		canvas.setFocusable(false);

		frame.add(canvas); //Ajout du canvas é la fenétre
		frame.pack(); //Redimentione légérement la fenétre pour permettre de voir entiérement le canvas
	}

	public Canvas getCanvas() { //Création de la méthode "getCanvas" pour permettre é d'autres classes de dessiner dessus (sur le canvas)
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}
}
