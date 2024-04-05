package entity.entity_lvl2;


import annotation.IA;
import annotation.Items;
import entity.Entity;
import entity.entity_lvl1.Mobile_Entity;
import entity.entity_lvl3.Passive_Entity;
import enums.*;
import main.GamePanel;


import java.awt.image.BufferedImage;
import java.util.Random;

// Peut-être séparer les classes Alive_entity pour celles qui peuvent bouger et celles qui ne peuvent pas
public abstract class Alive_Entity extends Mobile_Entity {
    public E_species specie; // Espèces
    public E_Main_Class mainClass; // Classes principales

    public BufferedImage up1, up2, down2, left1, left2, right1, right2; // Mouvement


    public boolean invincible = false;
    public int maxLife;
    public int life;
    public boolean alive = true;

    public int spriteCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int invincibleCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int actionLockCounter = E_MagicalNumber.RESET_COUNTER.Value();

    public Alive_Entity(GamePanel gp) {
        super(gp);
    }


    /** ---- damageReaction() ---- <p>
     * Cette fonction offre aux classes enfant d'entité l'accès à cette méthode permettant de définir le comportement de l'entité
     * lors de la réception de dégâts
     */
    @IA
    public void damageReaction() {}

    /** ---- speak() ----<p>
     * Méthode pour afficher le dialogue de l'entité.<p>
     * Lors de la rencontre du 1er index == null, la liste de dialogues recommence à l'index 0.<p>
     * Elle force l'entité avec laquelle l'entité joueur entre en communication à se tourner vers lui.
     */
    @IA
    public void speak() {

        // region DIALOGUE
        if(((Passive_Entity)this).dialogues[((Passive_Entity)this).dialogueIndex] == null) { //Recommencer la liste de dialogues
            ((Passive_Entity)this).dialogueIndex = E_MagicalNumber.RESET_DIALOGUE_INDEX.Value();
        }
        gp.ui.currentDialogue = ((Passive_Entity)this).dialogues[((Passive_Entity)this).dialogueIndex];
        ((Passive_Entity)this).dialogueIndex++;
        // endregion

        // region DIRECTION ENTITY ON DIALOGUE
        switch (gp.player.direction) {
            case UP -> direction = E_Direction.DOWN;
            case DOWN -> direction = E_Direction.UP;
            case RIGHT -> direction = E_Direction.LEFT;
            case LEFT -> direction = E_Direction.RIGHT;
        }
        // endregion
    }


    /** ---- dropItem() ---- <p>
     *  Cette fonction est utilisé sur les classes enfants pour définir les objets droppable de l'entité
     *
     * @param droppedItem instance de l'objet drop
     */
    public void dropItem(Entity droppedItem) {

        for(int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == null) {
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX;
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }


    /** ---- checkDrop() ---- <p>
     *  Cette fonction est utilisé sur les classes enfants pour leur permettre de définir quand un item peut être droppé
     *  par une instance de la classe enfant d'entité
     */
    @Items
    public void checkDrop() {}


    /** ---- setAction() ---- <p>
     * Cette fonction offre aux classes enfant d'entité l'accès à cette méthode permettant de définir le comportement de l'entité
     */


    public void update() {
        super.update();
        setAction();

    }
    @IA
    public void setAction() {

        actionLockCounter ++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if (i<= 25) {
                direction = E_Direction.UP;
            }
            if (i > 25 && i<= 50) {
                direction = E_Direction.DOWN;
            }
            if (i > 50 && i<= 75) {
                direction = E_Direction.LEFT;
            }
            if (i > 75) {
                direction = E_Direction.RIGHT;
            }
            actionLockCounter = 0;
        }
    }

}






