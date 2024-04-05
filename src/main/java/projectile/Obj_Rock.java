package projectile;

import entity.Entity;
import entity.entity_lvl3.Aggressive_Entity;
import main.GamePanel;
import enums.E_EntityType;
import enums.E_Main_Class;
import enums.E_RGB_COLOR;
import enums.E_Sound;

import java.awt.*;

public class Obj_Rock extends Projectile {
    public Obj_Rock(GamePanel gp) {
        super(gp);
        type = E_EntityType.PICKUPONLY;
        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        image = down1;
    }

    public boolean haveResource(Entity user) {
        return ((Aggressive_Entity)user).ammo >= useCost;
    }

    public void subtractResource(Entity user) {
        ((Aggressive_Entity)user).ammo -= useCost;
    }

    public Color getParticleColor() {
        return E_RGB_COLOR.BROWN_OF_ROCK.getColor();
    }
    public int getParticleSize() {
        return 6;
    }
    public int getParticleSpeed() {
        return 1;
    }
    public int getParticleMaxLife() {
        return 20;
    }

    public void use(Entity entity) {
        if (gp.player.mainClass == E_Main_Class.LANCE_PIERRE) {
            gp.playSE(E_Sound.COIN);
            gp.ui.PlayState().addMessage("Ammo +" + useCost);
            ((Aggressive_Entity)entity).ammo += useCost;
        }
    }
}
