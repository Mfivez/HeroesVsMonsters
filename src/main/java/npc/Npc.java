package npc;

import entity.entity_lvl3.Passive_Entity;
import enums.E_Direction;
import main.GamePanel;

import java.util.Random;

public abstract class Npc extends Passive_Entity {
    public Npc(GamePanel gp) {
        super(gp);
    }
}
