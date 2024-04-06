package collision_checker.coll_checker_lvl0;

import collision_checker.coll_checker_tools.cCheck_Tools;
import entity.Entity;
import entity.entity_lvl0.Solid_Entity;
import entity.entity_lvl2.Alive_Entity;
import enums.E_MagicalNumber;
import main.GamePanel;

public class cCheck_Alive_Entity {
    private final GamePanel gp;
    private final cCheck_Tools tools;

    public cCheck_Alive_Entity(GamePanel gp, cCheck_Tools tools) {
        this.gp = gp;
        this.tools = tools;
    }



    public int check(Solid_Entity entity, Entity[] target) {
        int index = E_MagicalNumber.NOTHING.Value();

        for (int i = 0; i < target.length; i++) {
            if(target[i] != null) {
                cCheck_Tools.moveSolidArea(entity);// Get entity's solid area position
                cCheck_Tools.moveSolidArea(target[i]);// Get the object's solid area position
                cCheck_Tools.updatePosition(entity); //Bouge l'entité selon gauche, haut,...
                index = cCheck_Tools.checkCollisionEntities(entity, target[i], i, index); // Vérifie la collision
                System.out.println(index);
                cCheck_Tools.resetSolidArea(entity, target[i]);
            }
        }
        return index;
    }


}
