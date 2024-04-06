package collision_checker;

import collision_checker.coll_checker_tools.cCheck_Tools;
import entity.Entity;
import collision_checker.coll_checker_lvl0.*;
import entity.entity_lvl0.Solid_Entity;
import main.GamePanel;

public class Collision_Checker {
    private final GamePanel gp;
    private final cCheck_Alive_Entity aliveEntity;
    private final cCheck_Player player;
    private final cCheck_Object object;
    private final cCheck_Tile tile;

    public Collision_Checker(GamePanel gp, cCheck_Tools tools) {
        this.gp = gp;
        aliveEntity = new cCheck_Alive_Entity(gp, tools);
        player = new cCheck_Player(gp, tools);
        object = new cCheck_Object(gp, tools);
        tile = new cCheck_Tile(gp, tools);
    }


    public void check(Entity entity) {
        tile.check(entity);
        object.check((Solid_Entity)entity, false);
        aliveEntity.check((Solid_Entity)entity, gp.npc);
        aliveEntity.check((Solid_Entity)entity, gp.monster);
        aliveEntity.check((Solid_Entity)entity, gp.iTile);
    }


    public cCheck_Alive_Entity aliveEntity() {
        return aliveEntity;
    }

    public cCheck_Player player() {
        return player;
    }

    public cCheck_Object object() {
        return object;
    }

    public cCheck_Tile tile() {
        return tile;
    }
}


