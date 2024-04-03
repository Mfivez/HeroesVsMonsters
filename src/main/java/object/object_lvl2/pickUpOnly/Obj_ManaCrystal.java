package object.object_lvl2.pickUpOnly;

import Player.Player;
import main.GamePanel;
import enums.E_Sound;
import object.object_lvl0.PickUpOnly;

public class Obj_ManaCrystal extends PickUpOnly {

    public Obj_ManaCrystal(GamePanel gp) {
        super(gp);
        setDefaultValue();
    }
    public Obj_ManaCrystal(GamePanel gp, int col, int row) {
        super(gp);
        setDefaultValue();
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }

    public void setDefaultValue() {
        value = 1;
    }

    public void use(Player player) {
        gp.playSE(E_Sound.POWERUP);
        gp.ui.addMessage("Mana +" + value);
        player.mana += value;
    }
}

