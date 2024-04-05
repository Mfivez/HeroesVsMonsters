package main;

import Player.Player;
import Tiles.TilesManager;
import ui.UI;
import entity.Entity;
import entity.entity_lvl2.Alive_Entity;
import entity.entity_lvl3.Aggressive_Entity;
import enums.E_GameState;
import enums.E_MagicalNumber;
import enums.E_Sound;
import enums.E_TimeUnit;
import tiles_interactive.InteractiveTile;
import tools.uTool_AssetSetter;
import ui.ui_tools.UI_Tools;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    //region ATTRIBUTS
    // region ENUM
    public E_GameState gameState;
    // endregion

    // region UTILITY TOOLS
    public uTool_AssetSetter uToolAssetSetter = new uTool_AssetSetter(this);
    // endregion

    // region SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize*scale;
    public final int maxScreenCol = 20;
    public final int maxScreenHeight = 12;
    public final int screenWidth = tileSize*maxScreenCol;
    public final int screenHeight = tileSize*maxScreenHeight;
    // endregion

    // region WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // endregion

    // region FPS
    int FPS = 60;
    // endregion

    // region SYSTEM
    TilesManager tileM = new TilesManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public UI_Tools tools = new UI_Tools(this);

    public UI ui = new UI(this, tools);



    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    //endregion

    // region ENTITY & OBJECT
    public Player player;
    //public Player2 player;
    public Entity[] obj = new Entity[20];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[E_MagicalNumber.MAX_MONSTER_ON_MAP.Value()];
    public InteractiveTile[] iTile = new InteractiveTile[50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // endregion
    // endregion

    /** ------ Constructeur de la classe GamePanel. ------
     *<p>
     * Initialise la taille préférée du panneau, sa couleur de fond, <p>
     * et active la double mise en mémoire tampon pour les dessins.<p>
     * Ajoute un écouteur d'événements clavier et rend le panneau <p>
     * focusable pour pouvoir recevoir les événements de clavier.<p>
     * Configure le jeu et démarre le thread de jeu.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //If set = true -> all the drawing from this component will be done in an offscreen painting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setupGame();
        this.startGameThread();
    }


    /** ---- setupGame() ---- <p>
     * Méthode pour configurer le jeu.
     * Appelle la méthode pour initialiser tous les éléments du jeu
     * et définit l'état du jeu sur l'état du titre.
     */
    public void setupGame() {
        aSetter.setAllThings();
        gameState  = E_GameState.TITLE;
    }

    /** ---- startGameThread() ---- <p>
     * 1. Instancie le joueur <p>
     * 2. Instancie le thread principal<p>
     * 3. Démarre le thread
     */
    public void startGameThread() {
        player = new Player(this, keyH);
        gameThread = new Thread(this);
        gameThread.start();
    }


    /** ---- run() ---- <p>
     * Exécute la boucle principale du jeu. Cette méthode est appelée lorsque le thread de jeu est démarré. <p>
     * Elle gère : <p>
     * 1. la mise à jour des informations du jeu<p>
     * 2. Le rendu de l'écran en boucle, en maintenant un nombre constant de FPS.
     */
    @Override
    public void run() {

        // region ATTRIBUTS
        long lastTime = System.nanoTime();
        double ns = (double) E_TimeUnit.SECOND.getTime() / FPS; // Convertit FPS en nanosecondes
        double delta = 0;
        int frames = 0;
        long timer = System.nanoTime();
        // endregion

        //region BOUCLE DE JEU
        while (gameThread != null) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            // region UPDATE & REPAINT
            while (delta >= 1) {
                update(); // Met à jour les informations
                repaint(); // Rendu de l'écran
                frames++;
                delta--;
            }
            //endregion

            // region FPS
            if (System.nanoTime() - timer > E_TimeUnit.SECOND.getTime()) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer = System.nanoTime(); // Réinitialiser le timer
            }
            //endregion

        }
        //endregion
    }


    /** ---- update() ---- <p>
     * Met à jour les éléments du jeu en fonction de l'état actuel du jeu.<p><p>
     *
     * Cette méthode est appelée à chaque itération de la boucle principale du jeu. <p>
     * Elle met à jour les différents éléments tels que :<p>
     * Le joueur<p>
     * Les PNJ<p> les monstres<p> Les projectiles<p> Les particules<p> Les tuiles interactives<p> EN FONCTION DE : Etat
     * du jeu.<p> Les éléments sont mis à jour uniquement lorsque le jeu est en cours (E_GameState.PLAY).
     */
    public void update() {

        // region PLAY
        if (gameState == E_GameState.PLAY){

            // region PLAYER
            player.update();
            //endregion

            // region NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
            //endregion

            // region MONSTER
            for (int i = 0; i<monster.length; i++) {
                if (monster[i] != null) {

                    if(((Alive_Entity)monster[i]).alive && !((Aggressive_Entity)monster[i]).dying) {monster[i].update();}

                    if(!((Alive_Entity)monster[i]).alive) {((Alive_Entity)monster[i]).checkDrop();monster[i] = null;}

                }
            }
            //endregion

            // region PROJECTILE

            for (int i = 0; i<projectileList.size(); i++) {
                if (projectileList.get(i) != null) {

                    if(((Alive_Entity)projectileList.get(i)).alive) {projectileList.get(i).update();}

                    if(!((Alive_Entity)projectileList.get(i)).alive) {projectileList.remove(i);}

                }
            }

            // endregion

            // region PARTICLE

            for (int i = 0; i<particleList.size(); i++) {
                if (particleList.get(i) != null) {

                    if(((Alive_Entity)particleList.get(i)).alive) {particleList.get(i).update();}

                    if(!((Alive_Entity)particleList.get(i)).alive) {particleList.remove(i);}
                }
            }
            // endregion

            // region iTILE
            for (InteractiveTile interactiveTile : iTile) {
                if (interactiveTile != null) {
                    interactiveTile.update();
                }
            }
            // endregion

        }
        // endregion

        // region PAUSE
        if (gameState == E_GameState.PAUSE){
            //nothing for now
        }
        // endregion
    }



    /** ---- paintComponent() ---- <p>
     * Dessine les éléments graphiques du jeu sur le composant graphique spécifié.<p> Cette méthode est responsable
     * des dessins de :<p> L'écran titre<p> Les tuiles<p> Les entités (joueur, PNJ,
     * monstres, etc.)<p> Les particules<p> L'interface utilisateur (UI)<p> Elle est appelée automatiquement chaque
     * fois que le composant graphique doit être redessiné.
     *
     * @param g l'objet Graphics utilisé pour dessiner les éléments graphiques
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // region DEBUG
        long drawStart = 0;
        if(keyH.showDebugText) {
            drawStart = System.nanoTime();
        }
        //endregion

        // region TITLE SCREEN
        if(gameState == E_GameState.TITLE) {
            ui.draw(g2);

        }
        // endregion

        // region OTHER
        else {

            // region TILE
            tileM.draw(g2);
            // endregion

            // region iTILE
            for (InteractiveTile interactiveTile : iTile) {
                if (interactiveTile != null) {
                    interactiveTile.draw(g2);
                }
            }

            // endregion

            // region ENTITY

            // region ADD ENTITY TO THE ARRAYLIST

            // region PLAYER
            entityList.add(player);
            // endregion

            // region NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            // endregion

            // region OBJECT
            for (Entity entity : obj) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            // endregion

            // region MONSTER
            for (Entity entity : monster) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            // endregion

            // region PROJECTILE
            for (Entity entity : projectileList) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            // endregion

            // region PARTICLE
            for (Entity entity : particleList) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            // endregion

            // endregion

            // region SORT
            entityList.sort(Comparator.comparingInt(e -> e.worldY));
            // endregion

            // region DRAW
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            entityList.clear();
            // endregion
            // endregion

            // region UI
            ui.draw(g2);
            // endregion
        }
        // endregion

        // region DEBUG
        if (keyH.showDebugText) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);

            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX : " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY : " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col : " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
            g2.drawString("Row : " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;
            g2.drawString("Draw Time : " + passed, x, y);
        }
        // endregion
        g.dispose();
    }


    /** ---- playMusic() ---- <p>
     * Joue le fichier audio correspondant à l'indice spécifié.
     *
     * @param sound le fichier audio à jouer
     */
    public void playMusic(E_Sound sound) {

        music.setFile(sound);
        music.play();
        music.loop();
    }

    /** ---- stopMusic() ---- <p>
     * Arrête la lecture du fichier audio en cours.
     *
     */
    public void stopMusic() {
        music.stop();
    }

    /** ---- playSE() ---- <p>
     * Joue le fichier audio correspondant à l'indice spécifié.
     *
     * @param sound le fichier audio à jouer
     */
    public void playSE(E_Sound sound) {
        se.setFile(sound);
        se.play();
    }
}

