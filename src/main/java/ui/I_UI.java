package ui;

import enums.E_GameState;

import java.awt.*;

public interface I_UI {
    /** ---- draw() ----<p>
     * Cette méthode implémente toutes les UI, elle se charge de prendre en compte le dessin de la catégorie UI dans
     * laquelle est implémentée cette interface
     *
     *
     * @param g2 Contexte graphique provenant du {@link main.GamePanel}
     */
    void draw(Graphics2D g2);
}
