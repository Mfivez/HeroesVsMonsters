package ui.ui_tools;


import enums.E_Main_Class;
import enums.E_RGB_COLOR;
import main.GamePanel;
import projectile.Obj_Rock;
import ui.UI;

import java.awt.*;

public class UI_Tools{
    GamePanel gp;

    //0= 1st screen, 1=2nd screen,...

    /**
     * Constructeur de la classe UI.
     *
     * @param gp Le GamePanel associé à cette interface utilisateur.
     */
    public UI_Tools(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Dessine une fenêtre rectangulaire avec des coins arrondis.
     *
     * @param x      La position horizontale du coin supérieur gauche de la fenêtre.
     * @param y      La position verticale du coin supérieur gauche de la fenêtre.
     * @param width  La largeur de la fenêtre.
     * @param height La hauteur de la fenêtre.
     */
    public void drawSubWindow(Graphics2D g2, int x, int y , int width, int height) {
        Color c = new Color(0,0,0, 100);
        g2.setColor(c);
        g2.fillRoundRect(x, y , width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);


    }


    /**
     * Calcule la position horizontale pour centrer un texte sur l'écran.
     *
     * @param text Le texte à centrer.
     * @return La position horizontale pour centrer le texte.
     */
    public int getXforCenteredText(Graphics2D g2, String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }


    /**
     * Calcule la position horizontale pour aligner un texte à droite par rapport à une position donnée.
     *
     * @param text   Le texte à aligner à droite.
     * @param tailX  La position horizontale de référence.
     * @return La position horizontale pour aligner le texte à droite.
     */
    public int getXforAlignToRight(Graphics2D g2, String text, int tailX) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }





    /**
     * Dessine une barre de progression à l'écran.
     *
     * @param barValue   La valeur de remplissage de la barre.
     * @param barColor   La couleur de la barre.
     * @param posBarX    La position horizontale de départ de la barre.
     * @param posBarY    La position verticale de départ de la barre.
     * @param centerBarValue  La taille du centre de la barre (pour l'effet visuel).
     */
    public void drawBar(Graphics2D g2, double barValue, Color barColor,  int posBarX, int posBarY, int centerBarValue) {
        g2.setColor(E_RGB_COLOR.GRAY_OF_MONSTER_HP_BARRE.getColor());
        g2.fillRect(posBarX-(centerBarValue/2), posBarY-(centerBarValue/2)
                , (gp.tileSize*4)+centerBarValue, 24);

        //Vie restante
        g2.setColor(barColor);
        g2.fillRect(posBarX, posBarY, (int)barValue, 24-centerBarValue);
    }


    /**
     * Dessine la barre de vie et la barre de Mana || Munition.
     */
    public void drawPlayerLife(Graphics2D g2) {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2 - 15;
        int i = 2;

        // PV
        double oneScale = (double) gp.tileSize * 4 / gp.player.maxLife;
        double hpBarValue = oneScale * gp.player.life;
        drawBar(g2, hpBarValue, E_RGB_COLOR.RED_OF_MONSTER_HP_BARRE.getColor(), x, y, i);

        // MANA
        if (gp.player.mainClass == E_Main_Class.SORCIER) {

            oneScale = (double) gp.tileSize * 4 / gp.player.maxMana;
            double ManaBarValue = oneScale * gp.player.mana;
            drawBar(g2, ManaBarValue, E_RGB_COLOR.BLUE_OF_MANA_BARRE.getColor(), x, y + 25, i);
        }

        //AMMO
        else if (gp.player.mainClass == E_Main_Class.LANCE_PIERRE) {

            Obj_Rock rock = new Obj_Rock(gp);
            g2.drawImage(rock.image, x, y + 25, (int) (gp.tileSize * 1.5), (int) (gp.tileSize * 1.5), null);
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(28F));
            g2.drawString("x " + gp.player.ammo, x + (int) (gp.tileSize * 1.3), y + (int) (gp.tileSize * 1.5));
        }


    }


    /**
     * Dessine un texte avec une ombre à l'écran.
     *
     * @param colors  Un tableau contenant deux couleurs pour le texte et son ombre.
     * @param message Le message à dessiner.
     * @param x       La position horizontale du texte.
     * @param y       La position verticale du texte.
     */
    public void drawMessageText(Graphics2D g2, Color[] colors, String message, int x, int y) {
        int j = 0;
        for (int i = 0; i <2;i++) {
            if (i == 0) {j = 2;}
            g2.setColor(colors[i]);
            g2.drawString(message, x+j, y+j);
        }
    }
}

