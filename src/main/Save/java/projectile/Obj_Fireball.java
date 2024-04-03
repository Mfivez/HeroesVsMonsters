package projectile;


import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import myenum.E_RGB_COLOR;

import java.awt.*;

public class Obj_Fireball extends Projectile {
    public Obj_Fireball(GamePanel gp) {
        super(gp);

        name = "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 250;
        alive = false;
        setSprites();
    }

    public boolean haveResource(Entity user) {
        return user.mana >= useCost;
    }

    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    public Color getParticleColor() {
        return E_RGB_COLOR.RED_OF_FIRE_BALL.getColor();
    }
    public int getParticleSize() {
        return 10;
    }
    public int getParticleSpeed() {
        return 1;
    }
    public int getParticleMaxLife() {
        return 20;
    }

}
