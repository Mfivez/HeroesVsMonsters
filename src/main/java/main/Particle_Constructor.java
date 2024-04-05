package main;


import entity.Entity;
import entity.entity_lvl2.Alive_Entity;

import java.awt.*;

public class Particle_Constructor extends Alive_Entity {
    Entity generator; // L'entité qui génère cette particule
    Color color; // La couleur de la particule
    int xd, yd, size; // Les directions horizontale et verticale, ainsi que la taille de la particule


    /** ---- Constructeur ----
     * @param gp L'instance du GamePanel.<p>
     * @param generator L'entité qui génère cette particule.<p>
     * @param color La couleur de la particule.<p>
     * @param size La taille de la particule.<p>
     * @param speed La vitesse de la particule.<p>
     * @param maxLife La durée de vie maximale de la particule.<p>
     * @param xd La direction horizontale de la particule.<p>
     * @param yd La direction verticale de la particule.
     */
    public Particle_Constructor(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);
        this.generator  = generator ;
        this.color  = color ;
        this.size  = size ;
        this.speed  = speed;
        this.maxLife  = maxLife ;
        this.xd  =  xd;
        this.yd  =  yd;
        life = maxLife;
        int offset = (gp.tileSize/2) - (size/2);
        worldX = generator.worldX + offset;
        worldY= generator.worldY + offset;
    }


    /** ---- update() ---- <p>
     * Met à jour l'état de la particule.<p>
     * Cette méthode est appelée à chaque cycle de mise à jour pour mettre à jour la position
     * et d'autres attributs de la particule.<p>
     * La particule perd progressivement de la durée de vie et se déplace dans la direction spécifiée par les attributs xd et yd.<p>
     * Si la durée de vie de la particule atteint zéro, elle est marquée comme "non vivante" et sera supprimée lors de la prochaine opération de nettoyage.
     */
    public void update() {

        life--;

        if (life < maxLife/3) {
            yd++;
        }
        worldX += xd*speed;
        worldY += yd*speed;

        if(life ==0) {
            alive = false;
        }

    }

    /** ---- draw() ----<p>
     * Dessine la particule.
     * @param g2 L'objet Graphics2D utilisé pour dessiner.
     */
    public void draw(Graphics2D g2) {

        int screenX = worldX - gp.player.worldX + gp.player.getScreenX();
        int screenY = worldY - gp.player.worldY + gp.player.getScreenY();

        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);
    }
}
