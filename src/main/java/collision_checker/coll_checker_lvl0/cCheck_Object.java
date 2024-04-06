package collision_checker.coll_checker_lvl0;


import collision_checker.coll_checker_tools.cCheck_Tools;
import entity.Entity;
import entity.entity_lvl0.Solid_Entity;
import entity.entity_lvl2.Alive_Entity;
import enums.E_MagicalNumber;
import main.GamePanel;

public class cCheck_Object {
    private final GamePanel gp;
    private final cCheck_Tools tools;

    public cCheck_Object(GamePanel gp, cCheck_Tools tools) {
        this.gp = gp;
        this.tools = tools;
    }

    public int check(Solid_Entity entity, boolean player) {
        int index =  E_MagicalNumber.NOTHING.Value();

        for (int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] != null) {
                cCheck_Tools.moveSolidArea(entity);// Get entity's solid area position
                cCheck_Tools.moveSolidArea((Solid_Entity)gp.obj[i]);// Get the object's solid area position
                cCheck_Tools.updatePosition(entity); //Bouge l'entité selon gauche, haut,...
                index =cCheck_Tools.checkCollisionWithObject(entity, (Solid_Entity)gp.obj[i], i, player);
                cCheck_Tools.resetSolidArea(entity, (Solid_Entity)gp.obj[i]);
            }
        }
        return index;
    }
}
