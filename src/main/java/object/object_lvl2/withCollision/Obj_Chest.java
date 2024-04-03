package object.object_lvl2.withCollision;


import main.GamePanel;
import object.Objects;

public class Obj_Chest extends Objects {

    public Obj_Chest(GamePanel gp, int col, int row) {
        super(gp);

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;



    }
}
