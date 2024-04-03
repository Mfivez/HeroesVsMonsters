package object.object_lvl2.consumable;

import main.GamePanel;
import object.object_lvl0.Consumable;


public class Obj_Key extends Consumable {
    public Obj_Key(GamePanel gp, int col, int row) {
        super(gp);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }
}
