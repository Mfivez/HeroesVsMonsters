package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Door extends Entity {

    public Obj_Door(GamePanel gp, int col, int row) {
        super(gp);



        name = "Door";
        setSprites();
        collision = true;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        description = "[" + name + "]\nPEPPERONI ON THE WALL.";


    }
}

