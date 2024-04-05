package entity.entity_interface;

import java.awt.*;

public interface I_Entity_Drawer {

    /** ---- draw() ----<p>
     * Méthode de dessin de l'entité.<p>
     *Elle prend en compte les dessins :<p>
     *     1. Déplacements<p>
     *     2. Attaques<p>
     *     3. Barre de pv des monstres<p>
     *     4. Etat d'invincibilité<p>
     *     5. Animation de mort via la fonction dyingAnimation()<p>
     * @param g2 L'objet Graphics2D utilisé pour dessiner.
     */
    void draw(Graphics2D g2);

}
