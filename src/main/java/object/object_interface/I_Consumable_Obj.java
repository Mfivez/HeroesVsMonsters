package object.object_interface;

import Player.Player;
import entity.Entity;

public interface I_Consumable_Obj {

    /** ---- use() ---- <p>
     * Cette fonction offre aux classes enfants d'entités l'accès à cette méthode permettant de définir le comportement
     * de l'instance lors de son utilisation (au ramassage ou à l'utilisation).
     */
    void use(Player player);
}
