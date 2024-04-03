package projectile;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import myenum.E_EntityType;
import myenum.E_Main_Class;
import myenum.E_RGB_COLOR;
import myenum.E_Sound;

import java.awt.*;

public class Obj_Rock extends Projectile {
    public Obj_Rock(GamePanel gp) {
        super(gp);
        type = E_EntityType.PICKUPONLY;
        value = 1;
        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        setSprites();
        image = setup("Obj_Rock_down_1", "Projectile"
                , (int)(gp.tileSize*1.5), (int)(gp.tileSize*1.5));
    }

    public boolean haveResource(Entity user) {
        return user.ammo >= useCost;
    }

    public void subtractResource(Entity user) {
        user.ammo -= useCost;
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
            gp.ui.addMessage("Ammo +" + value);
            entity.ammo += value;
        }
    }
}
