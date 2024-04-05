package npc.npc_lvl1;


import entity.Entity;
import main.GamePanel;
import enums.E_Direction;
import npc.Npc;

import java.util.Random;

public class Npc_OldMan extends Npc {

    public Npc_OldMan(GamePanel gp, int col, int row) {
        super(gp);
        setDefaultValue();
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }

    public void setDefaultValue() {
        direction = E_Direction.DOWN;
        speed = 1;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0] = "You should not pass !";
        dialogues[1] = "ARGH BLBL ? !";
        dialogues[2] = "Wha u'want cav'man brain !?";
        dialogues[3] = "Ya, ik spreek nerderlands, \n ik ben peekes !";
    }

    public void speak() {

        // Le code fonctionne très bien sans cette méthode pour le moment
        // Mais plus tard ça sera utile pour les character qui ont des choses spécifiques à faire
        super.speak();
    }
}
