package object.object_lvl2.equipable;


import main.GamePanel;
import object.Objects;

public class Obj_Boots extends Objects {

    public Obj_Boots(GamePanel gp, int col, int row) {
        super(gp);

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;


    }
}