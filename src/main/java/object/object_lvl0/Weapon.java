package object.object_lvl0;

import main.GamePanel;
import object.Objects;

public abstract class Weapon extends Objects {
    public boolean attacking = false;
    public int attackValue;
    public Weapon(GamePanel gp) {
        super(gp);

        attackValue = 1;
        attackArea.width = 30;
        attackArea.height = 30;
    }
}
