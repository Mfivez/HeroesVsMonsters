package object.object_lvl2.consumable;

import Player.Player;
import annotation.Items;
import main.GamePanel;
import enums.E_GameState;
import enums.E_Sound;
import object.object_lvl0.Consumable;

@Items
public class Obj_Potion_Red extends Consumable {
    public Obj_Potion_Red(GamePanel gp, int col, int row) {
        super(gp);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        value = 5;
    }

    @Override
    public void use(Player player) {
        gp.gameState = E_GameState.DIALOGUE;

        if(gp.player.life + value > gp.player.maxLife) {
            value = gp.player.maxLife - gp.player.life;}


        gp.ui.currentDialogue = "You drink the " + name + "!\n"
                + "Your life has been recovered by " + value + ".";
        player.life += value;

        gp.playSE(E_Sound.POWERUP);

    }
}
