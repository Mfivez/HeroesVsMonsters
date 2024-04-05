package entity.entity_lvl3;

import Player.Player;
import entity.Entity;
import entity.entity_lvl2.Alive_Entity;
import enums.E_Direction;
import enums.E_MagicalNumber;
import enums.E_Sound;
import main.GamePanel;
import projectile.Projectile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Aggressive_Entity extends Alive_Entity {
    public Projectile projectile;
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
    }



    /** ---- dyingAnimation() ---- <p>
     * Anime l'entité lors de sa mort en faisant clignoter son opacité.
     * L'opacité de l'entité est modifiée à intervalles réguliers pour simuler un clignotement.
     * Une fois que le compteur de mort atteint une certaine valeur, l'entité est marquée comme "non vivante" et disparaît.
     *
     * @param g2 L'objet Graphics2D utilisé pour dessiner.
     */
    public void dyingAnimation(Graphics2D g2) {
        this.dyingCounter++;
        int remainder = this.dyingCounter % 10; // Calcule le reste de la division entière du compteur de mort par 10
        float alpha = (remainder <= 5) ? 0f : 1f; // Détermine l'opacité en fonction du reste calculé
        changeAlpha(g2, alpha);// Modifie l'opacité

        if (this.dyingCounter > E_MagicalNumber.DYING_DURATION.Value()) {
            this.alive = false;
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

    public boolean getAttackSprite(int spriteNumber, E_Direction direction) {
        boolean needChange = false;
        int coefPlayerWeapon = 1;

        if (this instanceof Player) { coefPlayerWeapon = ((Player)this).getPlayerAttackSprite();}



        if (!attacking && entitySprites.length != 1){;image= entitySprites[spriteNumber+getActualSprite()];}

        if(attacking){image= ((Aggressive_Entity)this).entitySprites[(spriteNumber+(8*coefPlayerWeapon))+getActualSprite()]; needChange = true;}

        return needChange && (direction == E_Direction.UP || direction == E_Direction.LEFT);
    }
}




