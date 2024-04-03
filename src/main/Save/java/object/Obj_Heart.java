package object;

import entity.Entity;
import main.GamePanel;
import myenum.E_EntityType;
import myenum.E_Sound;

public class Obj_Heart extends Entity {

    public Obj_Heart(GamePanel gp) {
        super(gp);
        setDefaultValue();
    }

    public Obj_Heart(GamePanel gp, int col, int row) {
        super(gp);
        setDefaultValue();
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }

    public void setDefaultValue() {
        type = E_EntityType.PICKUPONLY;
        name = "Heart";
        value = 1;
        setSprites();
        image = setup("Obj_Heart_down_1", "Object", gp.tileSize, gp.tileSize);
        image2 = setup("heart_half", "Object", gp.tileSize, gp.tileSize);
        image3 = setup("heart_blank", "Object", gp.tileSize, gp.tileSize);
    }

    public void use(Entity entity) {
        gp.playSE(E_Sound.POWERUP);
        gp.ui.addMessage("Life +" + value);
        entity.life += value;
    }
}
