package dev.vianpyro.paperworld;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.vianpyro.paperworld.display.Display;
import dev.vianpyro.paperworld.graphics.Assets;
import dev.vianpyro.paperworld.graphics.GameCamera;
import dev.vianpyro.paperworld.inputs.KeyManager;
import dev.vianpyro.paperworld.inputs.MouseManager;
import dev.vianpyro.paperworld.states.GameState;
import dev.vianpyro.paperworld.states.MenuState;
import dev.vianpyro.paperworld.states.SettingsState;
import dev.vianpyro.paperworld.states.State;

public class Game implements Runnable { //"implements Runnable" pour permettre é cette classe de faire tourner un programme en boucle

	public String title; //Initialisation de la variable du titre de la fenétre

	private int width, height; //Initialisation des variables de hauteur et largeur de la fenétre
	private Display display; //Création de l'objet d'affichage
	private Thread thread; //Création d'un mini programme autonome qui fonctionne séparemment de la calsse principale (main)
	private boolean running = false; //Initialisation de l'état de jeu comme arrété
	private BufferStrategy bs; //Initialisation d'une fonction permettant de dire é l'ordinateur comment afficher des choses é l'écran, genre d' "écran invisible" qui permet d'éviter l'effet de clignotement
	private Graphics g; //Initialisation d'une fonction permettant de dessiner

	//états
	public State gameState, menuState, settingsState;

	//Camera & Handler
	private GameCamera gameCamera;
	private Handler handler;

	//Inputs
	private KeyManager keyManager;
	private MouseManager mouseManager;

	public Game(String title, int width, int height) {
		this.width = width; //Définir la variable largeur de la méthode égale é celle de la classe
		this.height = height; //Définir la variable hauteur de la méthode égale é celle de la classe
		this.title = title; //Définir la variable du titre de la fenétre de la méthode égale é celle de la classe
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}

	public void initialization() { //Création de la méthode d'initilaisation du jeu
		display = new Display(title, width, height); //Fonction qui appelle la construction de la fenétre
		display.getFrame().addKeyListener(keyManager); //On donne l'accés au clavier é la fenétre
		display.getFrame().addMouseListener(mouseManager); //On donne l'accés é la souris é la fenétre
		display.getFrame().addMouseMotionListener(mouseManager); //On donne l'accés é la detection du mouvement de la souris é la fenétre
		display.getCanvas().addMouseListener(mouseManager); //On donne l'accés é la souris é la fenétre
		display.getCanvas().addMouseMotionListener(mouseManager); //On donne l'accés é la detection du mouvement de la souris é la fenétre

		Assets.initialisation(); //Initialise les textures du jeu et tous les objets qui en ont une

		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);

		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		settingsState = new SettingsState(handler);
		State.setCurrentState(menuState); //Défini l'état du jeu comme "en jeu"
	}

	public void run() { //Création de la méthode principal du jeu

		initialization(); //Appelle la méthode d'initialisation principale du jeu

		final int maxFramesPerSecond = 60; //Création de la limite d'images par secondes affichées dans le jeu ; le nombre de fois qu'on veut lancer le code par seconde
		double timePerTick = 1000000000 / maxFramesPerSecond; //Définition du temps é attendre en nano secondes entre chaque execution du code
		double delta = 0; //Définition du temps avant le rappel des méthodes "tick" et "render"
		long now;
		long lastTime = System.nanoTime(); //Donne le nombre de nano secondes écoulées depuis le début du jeu
		long timer = 0; //Temps en nano secondes qui permet de savoir combien de temps s'est écoulé
		int ticks = 0; //Compteur d'images par secondes

		while(running) { //Création de la boucle de jeu
			now = System.nanoTime(); //Défini la valeur de maintenant égale au nombre de nano secondes écoulées depuis le début du jeu
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now; //Défini la valeur du dernier temps égale a maintenant car la derniére execution vient de se produire

			if(delta >= 1) { //Quand delta est superieur ou égal é un, il faut executer le programme
				tick(); //Execution de la méthode "tick"
				render(); //Execution de la méthode "render"
				ticks++;
				delta--; //On enléve un é delta pour ne pas rendre ce code innutile
			}

			if(timer >= 1000000000) {
				System.out.println("Ticks : " + ticks);
				ticks = 0;
				timer = 0;
			}
		}

		stop(); //Arrét au cas oé running est faux
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void tick() { //Création de la méthode "tick"
		keyManager.tick();

		if(State.getCurrentState() != null) {State.getCurrentState().tick();} //Si l'état acctuel du jeu est non-nul on execute la méthode "tick" de la classe "State"
	}

	private void render() { //Création de la méthode "render"
		bs = display.getCanvas().getBufferStrategy(); //Initialise le(s) "buffers"
		if(bs == null) { //Si il n'y a pas de "buffers"
			display.getCanvas().createBufferStrategy(3); //3 est le nombre de "buffers" maximum
			return;
		}

		g = bs.getDrawGraphics(); //Choix de l'emplacement oé dessiner en initialisant "g" qui sera le "pinceau"
		g.clearRect(0, 0, width, height); //Efface tout ce qui est affiché sur la fenétredu jeu
		//Draw under!

		if(State.getCurrentState() != null) {State.getCurrentState().render(g);} //Si l'état acctuel du jeu est non-nul on execute la méthode "render" de la classe "State"

		//End drawing!
		bs.show(); //Afficher le resultat du(es) dessin(s) é l'écran
		g.dispose(); //Sert é arréter é coup sér le(s) dessin(s)
	}

	public synchronized void start() { //Création de la méthode synchronized (en lien directe avec un thread) de commencement du jeu
		if(running) { //Ne rien faire si le jeu est déjé défini comme lancé
			Logger.log("Game already started");
			return;
		}
		running = true; //Définir le jeu comme lancé
		thread = new Thread(this); //Initialisation du mini programme autonome qui fonctionne séparemment de la calsse principale (main), de cette classe (this)
		thread.start(); //Activation du mini programme autonome qui fonctionne séparemment de la calsse principale (main) qui appelle la méthode "run"
		Logger.log("Game started");
	}

	public synchronized void stop() { //Création de la méthode synchronized (en lien directe avec un thread) d'arrét du jeu
		if(!running) { //Ne rien faire si le jeu est déjé arrété
			Logger.log("Game already stoped");
			return;
		}
		running = false; //Définir le jeu commme arrété
		try {
			Logger.log("Gamed stoped");
			thread.join(); //Arréte simplement le mini programme autonome qui fonctionne séparemment de la calsse principale (main)
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}
	}
}
