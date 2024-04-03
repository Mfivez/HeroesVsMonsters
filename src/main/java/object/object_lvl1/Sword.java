package object.object_lvl1;

import main.GamePanel;
import enums.E_EntityType;
import object.object_lvl0.Weapon;

public abstract class Sword extends Weapon {
    public Sword(GamePanel gp) {
        super(gp);
        type = E_EntityType.SWORD;

        attackValue += 1;
        attackArea.width += 6;
        attackArea.height += 6;
    }
}
