package object;


import entity.Entity;
import main.GamePanel;

public class Obj_Chest extends Entity {

    public Obj_Chest(GamePanel gp, int col, int row) {
        super(gp);
        setSprites();

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        name = "Chest";

        description = "[" + name + "]\nPEPPERONI ON THE WALL.";


    }
}
