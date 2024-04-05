package object.object_lvl0;

import Player.Player;
import object.object_interface.I_Consumable_Obj;
import main.GamePanel;
import enums.E_EntityType;
import object.Objects;

public abstract class PickUpOnly extends Objects implements I_Consumable_Obj {
    public PickUpOnly(GamePanel gp) {
        super(gp);
        type = E_EntityType.PICKUPONLY;
    }

    @Override
    public void use(Player player) {}
}
