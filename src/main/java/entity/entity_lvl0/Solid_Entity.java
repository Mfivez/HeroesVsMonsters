package entity.entity_lvl0;

import entity.Entity;
import enums.E_ActualSprite;
import main.GamePanel;
import main.Particle_Constructor;

import java.awt.*;

public abstract class Solid_Entity extends Entity {
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    public boolean collisionOn; // Collision en cours ?
    public E_ActualSprite spriteNum = E_ActualSprite.SPRITE1; // Etat initial d'affichage d'un sprite

    public Solid_Entity(GamePanel gp) {
        super(gp);
        if (!(this instanceof Particle_Constructor)) {
            image = entitySprites[0];}
        }
}

