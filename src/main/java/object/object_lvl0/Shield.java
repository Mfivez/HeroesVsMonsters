package object.object_lvl0;

import main.GamePanel;
import enums.E_EntityType;
import object.Objects;

public abstract class Shield extends Objects {
    public int defenseValue;
    public Shield(GamePanel gp) {
        super(gp);
        type = E_EntityType.SHIELD;
        defenseValue = 1;
    }
}
