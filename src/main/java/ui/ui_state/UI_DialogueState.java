package ui.ui_state;


import main.GamePanel;
import ui.ui_tools.UI_Tools;

import java.awt.*;

public class UI_DialogueState {
    private UI_Tools tools;
    private String currentDialogue = "";
    private final GamePanel gp;
    private Graphics2D g2;


    public UI_DialogueState(GamePanel gp, UI_Tools tools) {
        this.gp = gp;
        this.tools = tools;
    }

    /**
     * Dessine l'écran de dialogue.
     */
    public void draw(Graphics2D g2) {
        this.g2 = g2;


        // WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        tools.drawSubWindow(g2, x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
        tools.drawPlayerLife(g2);
    }

    public String currentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }
}
