package object;


import entity.Entity;
import main.GamePanel;

public class Obj_Boots extends Entity {

    public Obj_Boots(GamePanel gp, int col, int row) {
        super(gp);
        setSprites();

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        name = "Boots";
        description = "[" + name + "]\nPEPPERONI ON THE WALL.";

    }
}