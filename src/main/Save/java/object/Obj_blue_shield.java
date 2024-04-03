package object;

import entity.Entity;
import main.GamePanel;
import myenum.E_EntityType;

public class Obj_blue_shield extends Entity {

    public Obj_blue_shield(GamePanel gp, int col, int row) {
        super(gp);
        setSprites();

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        type = E_EntityType.SHIELD;
        name = "shield_blue";
        defenseValue = 2;
        description = "[" + name + "]\nPEPPERONI ON THE WALL.";

    }
}