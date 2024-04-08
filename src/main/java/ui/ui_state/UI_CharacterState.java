package ui.ui_state;


import enums.E_Main_Class;
import enums.E_RGB_COLOR;
import main.GamePanel;
import object.Objects;
import projectile.projectile_lvl0.Obj_Rock;
import ui.I_UI;
import ui.ui_tools.UI_Tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI_CharacterState implements I_UI {

    // region ATTRIBUTS
    private final UI_Tools tools;
    private final GamePanel gp;
    private Graphics2D g2;
    public int slotCol = 0;
    public int slotRow = 0;
    // endregion


    // region METHODS

    public UI_CharacterState(GamePanel gp, UI_Tools tools) {
        this.gp = gp;
        this.tools = tools;
    }


    public void draw(Graphics2D g2) {
        this.g2 = g2;

        drawInventory();
        drawStatsMenu();
    }


    /**
     * Dessine les statistiques du personnage sur l'écran de sélection du personnage.
     * Dessine aussi l'inventaire.
     */
    public void drawStatsMenu(){
        // CREATE A FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        tools.drawSubWindow(g2, frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        // NAMES
        drawNameStats(new String[]{
                        "Level",
                        "Life",
                        "Mana",
                        "Strength",
                        "Dexterity",
                        "Attack"
                        ,"Defense",
                        "Exp",
                        "Coin",
                        "Weapon",
                        "Shield"}
                , textX, textY, lineHeight);

        // VALUES
        int tailX = (frameX + frameWidth) - 30;

        textY = drawStats(
                new String[]{String.valueOf(gp.player.level)
                        , gp.player.life + "/" + gp.player.maxLife
                        , gp.player.mana + "/" + gp.player.maxMana
                        , String.valueOf(gp.player.strenght)
                        , String.valueOf(gp.player.dexterity)
                        , String.valueOf(gp.player.attack)
                        , String.valueOf(gp.player.defense)
                        , gp.player.exp + "/" + gp.player.nextLvlExp
                        , String.valueOf(gp.player.coin)} , tailX, textY, lineHeight);


        drawStatsImage(
                new BufferedImage[]{
                        gp.player.currentWeapon.down1,
                        gp.player.currentShield.down1 }
                , tailX, textY);

    }


    /**
     * Dessine les noms des statistiques du personnage.
     *
     * @param stats             Les noms des statistiques.
     * @param x                 La position horizontale de départ.
     * @param y                 La position verticale de départ.
     * @param spaceBetweenStats L'espace vertical entre chaque nom de statistique.
     */
    public void drawNameStats(String[] stats, int x, int y, int spaceBetweenStats) {
        for (int i = 0; i < stats.length; i++) {
            if (i ==stats.length-1 || i == stats.length-2) {y += 20;}
            g2.drawString(stats[i], x, y);
            y += spaceBetweenStats;
        }
    }


    /**
     * Dessine les valeurs des statistiques du personnage.
     *
     * @param values            Les valeurs des statistiques.
     * @param tailX             La position horizontale de la fin de la ligne.
     * @param y                 La position verticale de départ.
     * @param spaceBetweenStats L'espace vertical entre chaque valeur de statistique.
     * @return La nouvelle position verticale après le dessin des valeurs.
     */
    public int drawStats(String[] values, int tailX, int y, int spaceBetweenStats) {
        for (String value : values) {
            g2.drawString(value, tools.getXforAlignToRight(g2, value, tailX), y);
            y += spaceBetweenStats;
        }
        return y;
    }


    /**
     * Dessine les images des objets équipés par le personnage.
     *
     * @param images Les images des objets équipés.
     * @param tailX  La position horizontale de la fin de la ligne.
     * @param y      La position verticale de départ.
     */
    public void drawStatsImage(BufferedImage[] images, int tailX, int y) {
        for (BufferedImage image : images) {
            g2.drawImage(image, tailX - gp.tileSize, y - 14, null);
            y += gp.tileSize;
        }
    }


    /**
     * Dessine l'inventaire du joueur.
     */
    public void drawInventory() {

        // FRAME
        int frameX = gp.tileSize*9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        tools.drawSubWindow(g2, frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        // DRAW PLAYER'S ITEMS
        for (int i = 0 ; i < gp.player.inventory.size(); i++) {

            // EQUIP CURSOR
            if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
                    gp.player.inventory.get(i) ==gp.player.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }


            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        // DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //DISPLAY ITEM DESCRIPTION
        int dFrameY = frameY + frameHeight;
        int dFrameHeight = gp.tileSize*3;

        //DRAW DESCR TEXT
        int textX = frameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(20F));

        int itemIndex = getItemIndexOnSlot();

        if(itemIndex < gp.player.inventory.size()) {
            tools.drawSubWindow(g2, frameX, dFrameY, frameWidth, dFrameHeight); //Draw descr window
            for(String line : ((Objects)gp.player.inventory.get(itemIndex)).description.split(("\n"))) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }

    }


    /**
     * Calcule l'indice de l'objet dans l'inventaire en fonction de la position actuelle du curseur.
     *
     * @return L'indice de l'objet dans l'inventaire correspondant à la position actuelle du curseur.
     */
    public int getItemIndexOnSlot() {
        return slotCol + (slotRow*5);
    }


    /**
     * Dessine la barre de vie et la barre de Mana || Munition.
     */
    public void drawPlayerLife() {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2-15;
        int i = 2 ;

        // PV
        double oneScale = (double)gp.tileSize*4/gp.player.maxLife;
        double hpBarValue = oneScale*gp.player.life;
        tools.drawBar(g2, hpBarValue, E_RGB_COLOR.RED_OF_MONSTER_HP_BARRE.getColor(), x, y, i);

        // MANA
        if (gp.player.mainClass == E_Main_Class.SORCIER) {

            oneScale = (double) gp.tileSize * 4 / gp.player.maxMana;
            double ManaBarValue = oneScale * gp.player.mana;
            tools.drawBar(g2, ManaBarValue, E_RGB_COLOR.BLUE_OF_MANA_BARRE.getColor(), x, y + 25, i);
        }

        //AMMO
        else if (gp.player.mainClass == E_Main_Class.LANCE_PIERRE) {

            Obj_Rock rock = new Obj_Rock(gp);
            g2.drawImage(rock.image, x, y + 25,(int)(gp.tileSize*1.5), (int)(gp.tileSize*1.5), null);
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(28F));
            g2.drawString("x " + gp.player.ammo , x+ (int)(gp.tileSize*1.3), y + (int)(gp.tileSize*1.5));
        }


    }

    // endregion


    // region GETTERS & SETTERS

    public int getSlotCol() {
        return slotCol;
    }

    public int getSlotRow() {
        return slotRow;
    }

    //endregion

}
