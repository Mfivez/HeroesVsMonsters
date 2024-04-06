package collision_checker.coll_checker_lvl0;


import collision_checker.coll_checker_tools.cCheck_Tools;
import entity.entity_lvl0.Solid_Entity;
import enums.E_MagicalNumber;
import main.GamePanel;

public class cCheck_Object {
    private final GamePanel gp;

    public cCheck_Object(GamePanel gp, cCheck_Tools tools) {
        this.gp = gp;
    }

    public int check(Solid_Entity entity, boolean player) {
        int index =  E_MagicalNumber.NOTHING.Value();

        for (int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] != null) {
                cCheck_Tools.moveSolidArea(entity);// Get entity's solid area position
                cCheck_Tools.moveSolidArea(gp.obj[i]);// Get the object's solid area position
                cCheck_Tools.updatePosition(entity); //Bouge l'entité selon gauche, haut,...
                index =cCheck_Tools.checkCollisionWithObject(entity, (Solid_Entity)gp.obj[i], i, player);
                cCheck_Tools.resetSolidArea(entity, gp.obj[i]);
            }
        }
        return index;
    }
}
