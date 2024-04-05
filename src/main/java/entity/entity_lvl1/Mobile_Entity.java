package entity.entity_lvl1;

import entity.entity_lvl0.Solid_Entity;
import entity.entity_lvl2.Alive_Entity;
import entity.entity_lvl3.Aggressive_Entity;
import enums.E_ActualSprite;
import enums.E_Direction;
import main.GamePanel;

import java.awt.*;

public abstract class Mobile_Entity extends Solid_Entity  {
    public int speed; // Mouvement
    public Mobile_Entity(GamePanel gp) {
        super(gp);
    }


    public boolean drawMoveAndAttack(Graphics2D g2, int spriteNumber, E_Direction direction) {
        boolean changePos = false;
        if (this instanceof Alive_Entity && !(this instanceof Aggressive_Entity)) {
            image = entitySprites[spriteNumber+getActualSprite()];}

        else if (this instanceof Aggressive_Entity) {
            changePos = ((Aggressive_Entity)this).getAttackSprite(spriteNumber, direction);}

        return changePos;
        //else if (isDown){
        //    image = ((Objects)this).entitySprites[0];}

    }

    public int getActualSprite() {
        return ((Solid_Entity)this).spriteNum == E_ActualSprite.SPRITE1 ? 0 : 1;}
}

