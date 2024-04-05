package monster;

import entity.entity_lvl3.Aggressive_Entity;
import enums.E_MagicalNumber;
import enums.E_RGB_COLOR;
import main.GamePanel;

import java.awt.*;

public abstract class Monster extends Aggressive_Entity {
    public Monster(GamePanel gp) {
        super(gp);
    }


    public void draw(Graphics2D g2) {
        super.draw(g2);

        // region MONSTER HP BAR
        if (hpBarOn) {

            double oneScale = (double)gp.tileSize/ this.maxLife;
            double hpBarValue = oneScale*life;

            g2.setColor(E_RGB_COLOR.GRAY_OF_MONSTER_HP_BARRE.getColor());
            g2.fillRect(getScreenCord("X")-1, getScreenCord("Y")-16, gp.tileSize+2, 12);

            //Vie restante
            g2.setColor(E_RGB_COLOR.RED_OF_MONSTER_HP_BARRE.getColor());
            g2.fillRect(getScreenCord("X"), getScreenCord("Y") - 15, (int)hpBarValue, 10);

            hpBarCounter ++;

            if(hpBarCounter > E_MagicalNumber.HP_BAR_TIME_TO_DISAPPEAR.Value()) {
                hpBarCounter = E_MagicalNumber.RESET_COUNTER.Value();
                hpBarOn = false;
            }
        }
        drawSprite(g2);
        // endregion
    }
    public void drawSprite(Graphics2D g2) {
        g2.drawImage(image, getScreenCord("X"), getScreenCord("Y"), null);
    }
}
