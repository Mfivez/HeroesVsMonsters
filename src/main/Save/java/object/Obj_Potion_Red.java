package object;

import annotation.Items;
import entity.Entity;
import main.GamePanel;
import myenum.E_EntityType;
import myenum.E_GameState;
import myenum.E_Sound;

@Items
public class Obj_Potion_Red extends Entity {

    public Obj_Potion_Red(GamePanel gp, int col, int row) {
        super(gp);

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        type = E_EntityType.CONSUMABLE;
        name = "potion_red";
        value = 5;
        setSprites();
        description = "[" + name + "]\nPEPPERONI ON THE WALL.";

    }

    public void use(Entity entity) {

        gp.gameState = E_GameState.DIALOGUE;

        if(gp.player.life + value > gp.player.maxLife) {
            value = gp.player.maxLife - gp.player.life;
        }
        gp.ui.currentDialogue = "You drink the " + name + "!\n"
                + "Your life has been recovered by " + value + ".";
        entity.life += value;

        gp.playSE(E_Sound.POWERUP);

    }
}
