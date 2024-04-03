package npc;


import entity.Entity;
import main.GamePanel;
import myenum.E_Direction;

import java.util.Random;

public class Npc_OldMan extends Entity {

    public Npc_OldMan(GamePanel gp, int col, int row) {
        super(gp);
        setDefaultValue();
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }

    public void setDefaultValue() {
        direction = E_Direction.DOWN;
        speed = 1;
        setSprites();
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0] = "You should not pass !";
        dialogues[1] = "ARGH BLBL ? !";
        dialogues[2] = "Wha u'want cav'man brain !?";
        dialogues[3] = "Ya, ik spreek nerderlands, \n ik ben peekes !";
    }

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

    public void speak() {

        // Le code fonctionne très bien sans cette méthode pour le moment
        // Mais plus tard ça sera utile pour les character qui ont des choses spécifiques à faire
        super.speak();
    }
}
