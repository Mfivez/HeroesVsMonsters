package object;

import entity.Entity;
import main.GamePanel;
import myenum.E_EntityType;

public class Obj_Axe  extends Entity {

    public Obj_Axe(GamePanel gp, int col, int row) {
        super(gp);
        setSprites();

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        type = E_EntityType.AXE;
        name = "Woodcutter's Axe";
        attackValue = 2;
        description = "[" + name + "]\nPEPPERONI ON THE WALL.";
        attackArea.width = 30;
        attackArea.height = 30;
    }
}
