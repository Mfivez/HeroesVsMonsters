package object.object_lvl2.withCollision;

import main.GamePanel;
import object.Objects;


public class Obj_Door extends Objects {

    public Obj_Door(GamePanel gp, int col, int row) {
        super(gp);

        collision = true;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


    }
}

