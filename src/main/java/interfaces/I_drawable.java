package interfaces;


import annotation.Draw;

import java.awt.*;

@Draw
public interface I_drawable {
    void draw(Graphics2D g2); // méthode générale
    void changeAlpha(Graphics2D g2, float alphaValue); // modificateur d'opacité
    void dyingAnimation(Graphics2D g2); // animation de la mort
}
