package object.object_lvl1;

import main.GamePanel;
import enums.E_EntityType;
import object.object_lvl0.Weapon;

public abstract class Axe extends Weapon {
    public Axe(GamePanel gp) {
        super(gp);

        type = E_EntityType.AXE;
        attackValue += 0;
        attackArea.width += 0;
        attackArea.height += 0;

    }
}