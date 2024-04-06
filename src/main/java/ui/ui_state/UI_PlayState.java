package ui.ui_state;

import main.GamePanel;
import ui.I_UI;
import ui.ui_tools.UI_Tools;

import java.awt.*;
import java.util.ArrayList;

public class UI_PlayState implements I_UI {
    private UI_Tools tools;
    private Graphics2D g2;
    private final GamePanel gp;
    public boolean gameFinished = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();


    public UI_PlayState(GamePanel gp, UI_Tools tools) {
        this.gp = gp;
        this.tools = tools;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        drawMessage();
        tools.drawPlayerLife(g2);
    }



    /**
     * Dessine les messages affichés à l'écran lors du kill d'un mob, d'un gain de stat ou de la
     * récupération d'un objet par le joueur
     */
    public void drawMessage() {

        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                tools.drawMessageText(g2, new Color[]{Color.black, Color.white}, message.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }


    /**
     * Ajoute un message à afficher dans l'interface utilisateur.
     *
     * @param text Le texte du message à ajouter.
     */
    public void addMessage(String text) {
        //ADD TO ARRAY
        message.add(text);
        messageCounter.add(0);
    }

}
