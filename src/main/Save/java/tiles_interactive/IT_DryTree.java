package tiles_interactive;

import entity.Entity;
import main.GamePanel;
import myenum.E_EntityType;
import myenum.E_RGB_COLOR;
import myenum.E_Sound;

import java.awt.*;

public class IT_DryTree extends InteractiveTile {
    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        destructible = true;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon.type == E_EntityType.AXE;
    }

    public void playSE() {
        gp.playSE(E_Sound.CUTTREE);
    }

    public InteractiveTile getDestroyedForm() {
        return new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
    }

    public Color getParticleColor() {
        return E_RGB_COLOR.BROWN_OF_DRYTREE.getColor();
    }
    public int getParticleSize() {
        return 6;
    }
    public int getParticleSpeed() {
        return 1;
    }
    public int getParticleMaxLife() {
        return 20;
    }

}
