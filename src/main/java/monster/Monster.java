package monster;

import entity.entity_lvl2.Aggressive_Entity;
import enums.E_MagicalNumber;
import enums.E_RGB_COLOR;
import main.GamePanel;

import java.awt.*;

public abstract class Monster extends Aggressive_Entity {
    public Monster(GamePanel gp) {
        super(gp);
    }


    public void drawExtra(Graphics2D g2, int screenX, int screenY) {
        super.drawExtra(g2, screenX, screenY);

        // region MONSTER HP BAR
        if (hpBarOn) {

            double oneScale = (double)gp.tileSize/((Monster)this).maxLife;
            double hpBarValue = oneScale*life;

            g2.setColor(E_RGB_COLOR.GRAY_OF_MONSTER_HP_BARRE.getColor());
            g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);

            //Vie restante
            g2.setColor(E_RGB_COLOR.RED_OF_MONSTER_HP_BARRE.getColor());
            g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

            hpBarCounter ++;

            if(hpBarCounter > E_MagicalNumber.HP_BAR_TIME_TO_DISAPPEAR.Value()) {
                hpBarCounter = E_MagicalNumber.RESET_COUNTER.Value();
                hpBarOn = false;
            }
        }
        // endregion
    }

}
