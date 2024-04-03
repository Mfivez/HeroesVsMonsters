package main;

import annotation.Draw;
import enums.E_GameState;
import enums.E_Main_Class;
import enums.E_RGB_COLOR;
import object.Objects;
import projectile.Obj_Rock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Cette classe représente l'interface utilisateur (UI) du jeu.
 * Elle gère l'affichage des différents éléments graphiques et des interfaces utilisateur.
 */
@Draw
public class UI {
    // region ATTRIBUTS
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public E_GameState gameState = E_GameState.TITLE; //0= 1st screen, 1=2nd screen,...
    public int slotCol = 0;
    public int slotRow = 0;

    // endregion


    /**
     * Constructeur de la classe UI.
     *
     * @param gp Le GamePanel associé à cette interface utilisateur.
     */
    public UI(GamePanel gp) {
        this.gp = gp;
        // CREATE HUD OBJECT
        /*
        arial_40 = new Font("Cambria", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        Objects heart = new Obj_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
         */
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


    /**
     * Dessine les éléments graphiques de l'interface utilisateur.
     *
     * @param g2 Le contexte graphique utilisé pour dessiner.
     */
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        switch (gp.gameState) {
            case TITLE -> drawTitleScreen();
            case PLAY -> {  drawMessage(); drawPlayerLife(); }
            case PAUSE -> {  drawPlayerLife(); drawPauseScreen();  }
            case DIALOGUE -> {    drawPlayerLife(); drawDialogueScreen();  }
            case CHARACTER -> {  drawCharacterScreen(); drawInventory();  }
        }

    }


    // region PLAYER HP & AMMO || MANA
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
        drawBar(hpBarValue, E_RGB_COLOR.RED_OF_MONSTER_HP_BARRE.getColor(), x, y, i);

        // MANA
        if (gp.player.mainClass == E_Main_Class.SORCIER) {

            oneScale = (double) gp.tileSize * 4 / gp.player.maxMana;
            double ManaBarValue = oneScale * gp.player.mana;
            drawBar(ManaBarValue, E_RGB_COLOR.BLUE_OF_MANA_BARRE.getColor(), x, y + 25, i);
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


    /**
     * Dessine une barre de progression à l'écran.
     *
     * @param barValue   La valeur de remplissage de la barre.
     * @param barColor   La couleur de la barre.
     * @param posBarX    La position horizontale de départ de la barre.
     * @param posBarY    La position verticale de départ de la barre.
     * @param centerBarValue  La taille du centre de la barre (pour l'effet visuel).
     */
    public void drawBar(double barValue, Color barColor,  int posBarX, int posBarY, int centerBarValue) {
        g2.setColor(E_RGB_COLOR.GRAY_OF_MONSTER_HP_BARRE.getColor());
        g2.fillRect(posBarX-(centerBarValue/2), posBarY-(centerBarValue/2)
                , (gp.tileSize*4)+centerBarValue, 24);

        //Vie restante
        g2.setColor(barColor);
        g2.fillRect(posBarX, posBarY, (int)barValue, 24-centerBarValue);
    }


    //endregion

    // region MESSAGE

    /**
     * Dessine les messages affichés à l'écran lors du kill d'un mob, d'un gain de stat ou de la
     * récupération d'un objet par le joueur
     */
    public void drawMessage() {

        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size();i++) {
            if(message.get(i) != null) {
                drawMessageText(new Color[] {Color.black, Color.white}, message.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY +=50;

                if(messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
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
    public void drawMessageText(Color[] colors, String message, int x, int y) {
        int j = 0;
        for (int i = 0; i <2;i++) {
            if (i == 0) {j = 2;}
            g2.setColor(colors[i]);
            g2.drawString(message, x+j, y+j);
        }
    }


    //endregion

    // region PAUSE

    /**
     * Dessine l'écran de pause.
     */
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y= gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    // endregion


    // region DIALOGUE

    /**
     * Dessine l'écran de dialogue.
     */
    public void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }


    // endregion

    // region TITLESCREEN

    /**
     * Dessine l'écran de titre.
     */
    public void drawTitleScreen() {
        switch (gameState) {
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
     * Dessine un écran de titre textuel complet.
     *
     * @param color     La couleur du texte.
     * @param size      La taille du texte.
     * @param title     Le titre de l'écran.
     * @param asTitle   Indique si le texte doit être affiché en tant que titre principal.
     * @param options   Les options disponibles sur l'écran.
     * @param selector  Le sélecteur pour indiquer l'option sélectionnée.
     */
    public void drawAFullTitleScreen(Color color, float size, String title,  boolean asTitle, String[] options, String selector){
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
    public void drawAFullTitleScreenWhitImage(Color color, float size, String title,  boolean asTitle, String[] options, String selector, BufferedImage image, int imageX, int imageY) {
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
        g2.drawString(text, getXforCenteredText(text)+5, y+5);
        g2.setColor(Color.WHITE);
        g2.drawString(text, getXforCenteredText(text), y);
        return getXforCenteredText(text);
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


    // endregion

    // region CHARACTER

    /**
     * Dessine les statistiques du personnage sur l'écran de sélection du personnage.
     * Dessine aussi l'inventaire.
     */
    public void drawCharacterScreen() {

        // CREATE A FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;
        // NAMES
        drawNameOfCharacterStat(new String[]{"Level", "Life","Mana","Strength","Dexterity","Attack"
                ,"Defense","Exp","Coin","Weapon","Shield"}, textX, textY, lineHeight);

        // VALUES
        int tailX = (frameX + frameWidth) - 30;

        textY = drawStatsOfCharacterStat(
                new String[]{String.valueOf(gp.player.level)
                , gp.player.life + "/" + gp.player.maxLife
                , gp.player.mana + "/" + gp.player.maxMana
                , String.valueOf(gp.player.strenght)
                , String.valueOf(gp.player.dexterity)
                , String.valueOf(gp.player.attack)
                , String.valueOf(gp.player.defense)
                , gp.player.exp + "/" + gp.player.nextLvlExp
                , String.valueOf(gp.player.coin)} , tailX, textY, lineHeight);


        drawStatsImageOfCharacterStat(
                new BufferedImage[]{gp.player.currentWeapon.down1
                ,gp.player.currentShield.down1 }, tailX, textY);

    }


    //region STATS

    /**
     * Dessine les noms des statistiques du personnage.
     *
     * @param stats             Les noms des statistiques.
     * @param x                 La position horizontale de départ.
     * @param y                 La position verticale de départ.
     * @param spaceBetweenStats L'espace vertical entre chaque nom de statistique.
     */
    public void drawNameOfCharacterStat(String[] stats, int x, int y, int spaceBetweenStats) {
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
    public int drawStatsOfCharacterStat(String[] values, int tailX, int y, int spaceBetweenStats) {
        for (int i = 0; i < values.length; i++) {
            g2.drawString(values[i], getXforAlignToRight(values[i], tailX), y);
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
    public void drawStatsImageOfCharacterStat(BufferedImage[] images, int tailX, int y) {
        for (int i = 0; i < images.length; i++) {
            g2.drawImage(images[i], tailX - gp.tileSize, y-14, null);
            y += gp.tileSize;
        }
    }


    // endregion

    // region INVENTORY

    /**
     * Dessine l'inventaire du joueur.
     */
    public void drawInventory() {

        // FRAME
        int frameX = gp.tileSize*9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

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
            drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight); //Draw descr window
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


    // endregion
    // endregion

    // region GENERAL METHODS

    /**
     * Dessine une fenêtre rectangulaire avec des coins arrondis.
     *
     * @param x      La position horizontale du coin supérieur gauche de la fenêtre.
     * @param y      La position verticale du coin supérieur gauche de la fenêtre.
     * @param width  La largeur de la fenêtre.
     * @param height La hauteur de la fenêtre.
     */
    public void drawSubWindow(int x, int y , int width, int height) {
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
    public int getXforCenteredText(String text) {
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
    public int getXforAlignToRight(String text, int tailX) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

    // endregion

}
