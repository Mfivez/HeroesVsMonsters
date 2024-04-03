package object.object_lvl2.equipable;

import main.GamePanel;
import object.object_lvl0.Shield;

public class Obj_shield_blue extends Shield {

    public Obj_shield_blue(GamePanel gp, int col, int row) {
        super(gp);

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        defenseValue += 1;

    }
}