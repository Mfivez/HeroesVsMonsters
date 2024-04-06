package ui.ui_state;

import main.GamePanel;
import ui.I_UI;
import ui.ui_tools.UI_Tools;

import java.awt.*;

public class UI_PauseState implements I_UI {
    private UI_Tools tools;
    private Graphics2D g2;
    private final GamePanel gp;

    public UI_PauseState(GamePanel gp, UI_Tools tools) {
        this.gp = gp;
        this.tools = tools;
    }

    /**
     * Dessine l'écran de pause.
     */
    public void draw(Graphics2D g2) {
        this.g2 = g2;


        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = tools.getXforCenteredText(g2, text);
        int y= gp.screenHeight/2;
        g2.drawString(text, x, y);
        tools.drawPlayerLife(g2);
    }

}
