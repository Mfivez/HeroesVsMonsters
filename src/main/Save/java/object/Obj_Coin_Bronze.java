package object;

import entity.Entity;
import main.GamePanel;
import myenum.E_EntityType;
import myenum.E_Sound;

public class Obj_Coin_Bronze extends Entity {

    public Obj_Coin_Bronze(GamePanel gp) {
        super(gp);
        setDefaultValue();
        setSprites();
    }

    public Obj_Coin_Bronze(GamePanel gp, int col, int row) {
        super(gp);
        setDefaultValue();
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        }

    public void setDefaultValue() {
        type = E_EntityType.PICKUPONLY;
        name = "Bronze Coin";
        value = 1;
        setSprites();
    }
    public void use(Entity entity) {

        gp.playSE(E_Sound.COIN);
        gp.ui.addMessage("Coin +" +value);
        gp.player.coin += value;

    }

}
