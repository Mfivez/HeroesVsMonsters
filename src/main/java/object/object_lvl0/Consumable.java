package object.object_lvl0;

import Player.Player;
import entity.Entity;
import interfaces.I_Consumable_Obj;
import main.GamePanel;
import enums.E_EntityType;
import object.Objects;

public abstract class Consumable extends Objects implements I_Consumable_Obj {
    public Consumable(GamePanel gp) {
        super(gp);
        type = E_EntityType.CONSUMABLE;
    }
    @Override
    public void use(Player player) {}

}