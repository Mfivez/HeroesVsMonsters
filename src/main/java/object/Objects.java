package object;


import entity.entity_lvl0.Solid_Entity;
import main.GamePanel;

////////////////////////////////TO DO //////////////////////////////////////////
//  1. Abstract class objet ramassable & non ramassable
//
////////////////////////////////////////////////////////////////////////////////
public abstract class Objects extends Solid_Entity {
    public int value;
    public String description = "";

    /**
     * --- constructeur ----
     *
     * @param gp instance du GamePanel
     */
    public Objects(GamePanel gp) {
        super(gp);
        name = getClass().getSimpleName().replace("Obj_", "");
        description = "[" + name + "]\nPEPPERONI ON THE WALL.";
    }

}
