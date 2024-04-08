package Tiles.tiles_interactive;

import entity.Entity;
import entity.entity_lvl2.Alive_Entity;
import main.GamePanel;

import java.awt.*;

public abstract class InteractiveTile extends Alive_Entity {

    public boolean destructible = false;
    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }

    public void playSE() {

    }

    public InteractiveTile getDestroyedForm() {
        return null;
    }

    public void update() {
        if(invincible) {
            this.invincibleCounter++;
        }
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
    }


    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.getScreenX();
        int screenY = worldY - gp.player.worldY + gp.player.getScreenY();

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.getScreenX() &&
                worldX - gp.tileSize  < gp.player.worldX + gp.player.getScreenX() &&
                worldY + gp.tileSize  > gp.player.worldY - gp.player.getScreenY() &&
                worldY - gp.tileSize  < gp.player.worldY + gp.player.getScreenY())
        {g2.drawImage(down1, screenX, screenY, null);}

    }

}
