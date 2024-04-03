package object.object_lvl2.equipable;


import main.GamePanel;
import object.object_lvl1.Sword;

public class Obj_Sword_Normal extends Sword {
    public Obj_Sword_Normal(GamePanel gp) {
        super(gp);
    }

    public Obj_Sword_Normal(GamePanel gp, int col, int row) {
        super(gp);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }
}
