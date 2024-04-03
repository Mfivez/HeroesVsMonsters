package monster;

import entity.Entity;
import main.GamePanel;
import myenum.E_Direction;
import myenum.E_EntityType;
import myenum.E_Main_Class;
import object.Obj_Coin_Bronze;
import object.Obj_Heart;
import object.Obj_ManaCrystal;
import projectile.Obj_Rock;

import java.util.Random;

public class MOB_GreenSlim extends Entity {
    public MOB_GreenSlim(GamePanel gp, int col, int row) {
        super(gp);
        setDefaultValue();
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }

    public void setDefaultValue() {
        type = E_EntityType.MONSTER;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new Obj_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setSprites();
    }
    public void setAction() {
        actionLockCounter ++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if (i<= 25) {
                direction = E_Direction.UP;
            }
            if (i > 25 && i<= 50) {
                direction = E_Direction.DOWN;
            }
            if (i > 50 && i<= 75) {
                direction = E_Direction.LEFT;
            }
            if (i > 75) {
                direction = E_Direction.RIGHT;
            }
            actionLockCounter = 0;
        }

        int i = new Random().nextInt(100)+1;
        if (i > 99 && !projectile.alive && shotAvailableCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction; //LA FUITE
    }

    public void checkDrop() {

        int i = new Random().nextInt(100)+1;

        if (i < 50) {
            dropItem(new Obj_Coin_Bronze(gp));
            if (gp.player.mainClass == E_Main_Class.LANCE_PIERRE) {
                dropItem(new Obj_Rock(gp));
            }
        }
        if (i >= 50 && i  <75) {
            dropItem(new Obj_Heart(gp));
        }
        if (i >= 75) {
            dropItem(new Obj_ManaCrystal(gp));
        }
    }
}
