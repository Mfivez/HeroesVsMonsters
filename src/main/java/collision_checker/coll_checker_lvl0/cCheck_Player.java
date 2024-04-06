package collision_checker.coll_checker_lvl0;

import collision_checker.coll_checker_tools.cCheck_Tools;
import entity.entity_lvl0.Solid_Entity;
import main.GamePanel;

public class cCheck_Player {
    private final GamePanel gp;

    public cCheck_Player(GamePanel gp, cCheck_Tools tools) {
        this.gp = gp;
    }

    public boolean check(Solid_Entity entity) {
        boolean contactPlayer = false;

        cCheck_Tools.moveSolidArea(entity);// Get entity's solid area position
        cCheck_Tools.moveSolidArea(gp.player);// Get player's solid area position
        cCheck_Tools.updatePosition(entity);
        cCheck_Tools.checkCollisionWithPlayer(entity, gp.player, contactPlayer);
        cCheck_Tools.resetSolidArea(entity, gp.player);

        return contactPlayer;
    }
}
