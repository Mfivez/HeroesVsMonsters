package object;

import entity.Entity;
import main.GamePanel;


public class Obj_Key extends Entity {

    public Obj_Key(GamePanel gp, int col, int row) {
        super(gp);

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        name = "Key";
        setSprites();
        description = "[" + name + "]\nPEPPERONI ON THE WALL.";


    }
}
