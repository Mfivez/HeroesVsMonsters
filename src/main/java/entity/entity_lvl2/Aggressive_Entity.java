package entity.entity_lvl2;

import annotation.IA;
import entity.Entity;
import entity.entity_lvl1.Alive_Entity;
import enums.E_ActualSprite;
import enums.E_EntityType;
import enums.E_MagicalNumber;
import enums.E_Sound;
import main.GamePanel;
import projectile.Projectile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Aggressive_Entity extends Alive_Entity {
    public Projectile projectile;
    public BufferedImage
            upAttack1, upAttack2
            , downAttack1, downAttack2
            , leftAttack1, leftAttack2
            , rightAttack1, rightAttack2;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean ammoRegen = false; //gère la régène mana & munition
    public boolean attacking = false;
    public int shotAvailableCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int dyingCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int hpBarCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strenght;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public Aggressive_Entity(GamePanel gp) {
        super(gp);
        getAttackSprite(8);
    }


    /**
     * Charge les images des sprites du joueur à partir des fichiers d'images.
     * Cette méthode lit les fichiers d'images et les redimensionne selon la taille des tuiles du jeu.
     */
    public void getAttackSprite(int i) {
        if (entitySprites.length > 8) {
            upAttack1 = entitySprites[i];i++;
            upAttack2 = entitySprites[i];i++;
            downAttack1 = entitySprites[i];i++;
            downAttack2 = entitySprites[i];i++;
            leftAttack1 = entitySprites[i];i++;
            leftAttack2 = entitySprites[i];i++;
            rightAttack1 = entitySprites[i];i++;
            rightAttack2 = entitySprites[i];
        }
    }

    /** ---- dyingAnimation() ---- <p>
     * Anime l'entité lors de sa mort en faisant clignoter son opacité.
     * L'opacité de l'entité est modifiée à intervalles réguliers pour simuler un clignotement.
     * Une fois que le compteur de mort atteint une certaine valeur, l'entité est marquée comme "non vivante" et disparaît.
     *
     * @param g2 L'objet Graphics2D utilisé pour dessiner.
     */
    public void dyingAnimation(Graphics2D g2) {
        ((Aggressive_Entity)this).dyingCounter++;
        int remainder = ((Aggressive_Entity)this).dyingCounter % 10; // Calcule le reste de la division entière du compteur de mort par 10
        float alpha = (remainder <= 5) ? 0f : 1f; // Détermine l'opacité en fonction du reste calculé
        changeAlpha(g2, alpha);// Modifie l'opacité

        if (((Aggressive_Entity)this).dyingCounter > E_MagicalNumber.DYING_DURATION.Value()) {
            ((Alive_Entity)this).alive = false;
        }
    }


    /** ---- damagePlayer()  ----- <p>
     * Cette fonction permet à une {@link Entity} d'infliger des dégats à l'instance joueur si il n'est pas dans un
     *  état d'invincibilité.
     *  L'instance joueur est ensuite passé en état d'invincibilité.
     *
     * @param attack dégâts infligés au joueur
     */
    public void damagePlayer(int attack) {

        if(!gp.player.invincible) {
            //DAMAGE IS GIVABLE
            gp.playSE(E_Sound.HITMONSTER);

            int damage = attack - gp.player.defense;
            if(damage < E_MagicalNumber.RESET_COUNTER.Value()) {
                damage = E_MagicalNumber.RESET_COUNTER.Value();
            }
            gp.player.life -= damage;
            gp.player.invincible = true;

        }

    }


    public void drawExtra(Graphics2D g2, int screenX, int screenY) {
        super.drawExtra(g2, screenX, screenY);

        if (dying) {dyingAnimation(g2);}
    }


    public void drawExtraDirection(Graphics2D g2, int tempScreenX, int tempScreenY) {
        super.drawExtraDirection(g2, tempScreenX, tempScreenY);

        int i =(spriteNum == E_ActualSprite.SPRITE1) ? 0:1; // int pour récupérer le sprite actuel

        switch (direction) {
            case UP -> {getLogiqueOfSpriteForDirection(0, i);tempScreenY -= gp.tileSize;}
            case DOWN -> {getLogiqueOfSpriteForDirection(2, i);}
            case LEFT -> {getLogiqueOfSpriteForDirection(4, i);}
            case RIGHT -> {getLogiqueOfSpriteForDirection(6, i);tempScreenY -= gp.tileSize;}
        }
    }


    /** ---- getLogiqueOfDirection
     *  Retourne la logique de récupération de sprites associées à la fonction draw extra direction.
     *  Si l'entité attaque -> donne ce sprite sinon -> celui-là.
     * @param spriteIndex l'index dans la liste des sprites associées à l'entité (EntitySprites)
     * @param actualSprite le sprite de direction actuel appliqué (1 ou 2)
     */
    public void getLogiqueOfSpriteForDirection(int spriteIndex, int actualSprite) {
        if (!attacking) {image = entitySprites[spriteIndex+actualSprite];} spriteIndex += 8;
        if (attacking) {image = entitySprites[spriteIndex+actualSprite];}
    }
}




