package object;


import entity.Entity;
import main.GamePanel;
import myenum.E_EntityType;

public class Obj_Sword_Normal extends Entity {

    public Obj_Sword_Normal(GamePanel gp) {
        super(gp);
        setDefaultValues();
    }

    public Obj_Sword_Normal(GamePanel gp, int col, int row) {
        super(gp);
        setDefaultValues();
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }

    public void setDefaultValues() {
        type = E_EntityType.SWORD;
        name = "sword_normal";
        setSprites();
        attackValue = 4;
        description = "[" + name + "]\nPEPPERONI ON THE WALL.";
        attackArea.width = 36;
        attackArea.height = 36;
    }

}
