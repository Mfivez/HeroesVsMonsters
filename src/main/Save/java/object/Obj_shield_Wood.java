package object;


import entity.Entity;
import main.GamePanel;
import myenum.E_EntityType;

public class Obj_shield_Wood extends Entity {


    public Obj_shield_Wood(GamePanel gp) {
        super(gp);
        setDefaultValue();
    }

    public Obj_shield_Wood(GamePanel gp, int col, int row) {
        super(gp);
        setDefaultValue();
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }

    public void setDefaultValue() {
        type = E_EntityType.SHIELD;
        name = "shield_wood";
        setSprites();
        defenseValue = 1;
        description = "[" + name + "]\nPEPPERONI ON THE WALL.";
    }
}
