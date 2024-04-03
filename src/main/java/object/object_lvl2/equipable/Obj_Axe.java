package object.object_lvl2.equipable;

import main.GamePanel;
import object.object_lvl1.Axe;

public class Obj_Axe extends Axe {

    public Obj_Axe(GamePanel gp, int col, int row) {
        super(gp);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }
}
