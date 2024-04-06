package ui.ui_state;

import main.GamePanel;
import ui.I_UI;
import ui.ui_tools.UI_Tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI_TitleState implements I_UI {
    private final UI_Tools tools;
    private int commandNum = 0;
    private Graphics2D g2;
    private final GamePanel gp;

    public UI_TitleState(GamePanel gp, UI_Tools tools) {
        this.gp = gp;
        this.tools = tools;
    }

    /**
     * Dessine l'écran de titre.
     */
    public void draw(Graphics2D g2) {
        this.g2 = g2;


        switch (gp.ui.gameState) {
            case TITLE -> drawAFullTitleScreenWhitImage(Color.WHITE, 56F, "HEROES VS MONSTERS", true,
                    new String[]{"NEW GAME", "LOAD GAME", "QUIT"}, ">",
                    gp.player.down1, gp.screenWidth / 2 - (gp.tileSize * 2) / 2, gp.tileSize * 2);

            case TITLE2 -> drawAFullTitleScreen(Color.white, 48F, "Choisis ta catégorie !", false,
                    new String[]{"Guerrier", "Lance-Pierre", "Sorcier", "Back"}, ">");

            case TITLE3 -> drawAFullTitleScreen(Color.white, 48F, "Select your race !", false,
                    new String[]{"Dwarf", "Human", "Back"}, ">");
        }
    }


    /**
     * Dessine du texte avec une ombre à l'écran pour l'écran de titre.
     *
     * @param text Le texte à dessiner.
     * @param y    La position verticale du texte.
     * @return La position horizontale du texte dessiné.
     */
    public int drawTitleScreenText(String text, int y) {
        // SHADOW
        g2.setColor(Color.GRAY);
        g2.drawString(text, tools.getXforCenteredText(g2, text)+5, y+5);
        g2.setColor(Color.WHITE);
        g2.drawString(text, tools.getXforCenteredText(g2, text), y);
        return tools.getXforCenteredText(g2, text);
    }


    /**
     * Dessine le sélecteur ">" à côté d'une option sur l'écran de titre.
     *
     * @param text    Le texte à côté duquel le sélecteur doit être dessiné.
     * @param command Le numéro de la commande correspondant à cette option.
     * @param x       La position horizontale du texte.
     * @param y       La position verticale du texte.
     */
    public void drawTitleScreenOptionText(String text, int command, int x, int y) {
        if (commandNum == command) {g2.drawString(text , x - gp.tileSize, y);}}


    /**
     * Dessine un écran de titre textuel complet.
     *
     * @param color     La couleur du texte.
     * @param size      La taille du texte.
     * @param title     Le titre de l'écran.
     * @param asTitle   Indique si le texte doit être affiché en tant que titre principal.
     * @param options   Les options disponibles sur l'écran.
     * @param selector  Le sélecteur pour indiquer l'option sélectionnée.
     */
    public void drawAFullTitleScreen(Color color, float size, String title, boolean asTitle, String[] options, String selector){
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(color);
        int x;
        int y = gp.tileSize * 3;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, size));
        if  (asTitle) {g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));}
        drawTitleScreenText(title, y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, size));

        for (int i = 0; i < options.length; i++) {
            y += gp.tileSize;
            if (i+1 ==options.length) {y+=gp.tileSize;}
            x = drawTitleScreenText(options[i], y);
            drawTitleScreenOptionText(selector, i, x, y);
        }
    }


    /**
     * Dessine un écran de titre complet avec une image en arrière-plan.
     *
     * @param color     La couleur du texte.
     * @param size      La taille du texte.
     * @param title     Le titre de l'écran.
     * @param asTitle   Indique si le texte doit être affiché en tant que titre principal.
     * @param options   Les options disponibles sur l'écran.
     * @param selector  Le sélecteur pour indiquer l'option sélectionnée.
     * @param image     L'image en arrière-plan de l'écran.
     * @param imageX    La position horizontale de l'image.
     * @param imageY    La position verticale de l'image.
     */
    public void drawAFullTitleScreenWhitImage(Color color, float size, String title, boolean asTitle, String[] options, String selector, BufferedImage image, int imageX, int imageY) {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(color);

        int y = gp.tileSize * 3;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, size));
        if  (asTitle) {g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));}
        drawTitleScreenText(title, y);

        g2.drawImage(image, imageX, y+imageY, gp.tileSize*2, gp.tileSize*2, null);
        y += imageY*2.6;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, size));
        int x;
        for (int i = 0; i < options.length; i++) {
            y += gp.tileSize;
            x = drawTitleScreenText(options[i], y);
            drawTitleScreenOptionText(selector, i, x, y);
        }
    }


    public int CommandNum() {return commandNum;}

    public void setCommandNum(int commandNum) {this.commandNum = commandNum;}
}
