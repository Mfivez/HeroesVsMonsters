package entity.entity_lvl2;


import entity.entity_lvl1.Alive_Entity;
import enums.E_MagicalNumber;
import main.GamePanel;

public class Passive_Entity extends Alive_Entity {
    public String[] dialogues = new String[20];
    public int dialogueIndex = E_MagicalNumber.RESET_DIALOGUE_INDEX.Value(); // Etat initial de l'index de dialogu

    public Passive_Entity(GamePanel gp) {
        super(gp);
    }
}
