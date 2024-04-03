package interfaces;

import annotation.Items;
import entity.Entity;

@Items()
public interface I_Drop_Items {
    void dropItem(Entity droppedItem); // générer une instance d'item à dropper si une condition de checkDrop est vérifiée
    void checkDrop(); // logique de drop
}
