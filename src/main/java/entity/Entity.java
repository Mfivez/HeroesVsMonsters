package entity;
import annotation.*;
import entity.entity_interface.I_Entity_Drawer;
import entity.entity_interface.I_Entity_updater;
import entity.entity_lvl0.Solid_Entity;
import entity.entity_lvl1.Mobile_Entity;
import entity.entity_lvl2.Alive_Entity;
import entity.entity_lvl3.Aggressive_Entity;
import entity.entity_tool.eTool_SpriteSetter;
import enums.*;
import main.GamePanel;
import main.Particle_Constructor;
import object.Objects;
import projectile.Projectile;

import java.awt.*;
import java.awt.image.BufferedImage;


/////////////////////////////////A FAIRE//////////////////////////////////////////////
// 1. Refactoriser Entity en le divisant en sous objet -40%
// 2. GENERECITE SUR CLASS <>
// 3. FAIRE DES LISTES AVEC LES SPRITES
// 4. GERER LA GENERATION DES PARTICULES DANS UN OUTIL eTool
// 5. CREER UNE CLASSE QUI GERE LES METHODES LIE AU DRAW DANS L UI DES ENTITES


//////////////////////////////////////////////////////////////////////////////////////

/** Type d'{@link Entity} actuel :<p>
 *1. ABSTRATC NPC <p>
 *2. ABSTRACT MONSTER <p>
 *3. ABSTRACT OBJECT <p>
 *4. ABSTRACT PROJECTILE <p>
 *5. PARTICLE <p>
 *6. PLAYER <p>
 */
public abstract class Entity implements   I_Entity_Drawer, I_Entity_updater
{
    //region ENTITY VALIDATE
    public GamePanel gp;
    public eTool_SpriteSetter uToolSpriteSetter = new eTool_SpriteSetter(gp);
    public BufferedImage[] entitySprites;
    public String name;
    public BufferedImage image, down1;
    public E_Direction direction = E_Direction.DOWN; // Etat initial d'affichage d'un sprite
    public int worldX, worldY; // Toutes les entités ont des positions
    public E_EntityType type; // 0 = player, 1 = npc...

    // endregion


    /** --- constructeur ----
     *
     * @param gp instance du GamePanel
     */
    public Entity(GamePanel gp) {
        this.gp = gp;
        entitySprites = setSprites();
    }


    /** ---- setSprites() ---- <p>
     * Cette méthode gère tous les cas d'associations de sprites possibles des classes enfants d' {@link Entity}<p>
     *
     * Cas gérés :<p>
     * 0. 0 sprite en ressource<p>
     * 1. 1 sprite en ressources<p>
     * 2. 2 sprites en ressources<p>
     * 3. 8 sprites en ressources<p>
     *
     * @author MFivez
     * @LastUpdate 31-03-2024
     */
    public BufferedImage[] setSprites() {
        BufferedImage[] image = uToolSpriteSetter.getSpritesImages(this.getClass(), gp.tileSize, gp.tileSize);

        // region 0 SPRITE
        if (!(this instanceof Particle_Constructor) && image.length == 0) {
            throw new IllegalStateException("Aucune image chargée pour " + this.getClass().getSimpleName());
        }
        // endregion

        // region 1 SPRITE
        if (image.length == E_MagicalNumber.LIST_OF_1_SPRITE.Value()) {
            int i = E_MagicalNumber.LIST_OF_1_SPRITE.Value()-1;
            if (this instanceof Projectile) {
                ((Projectile)this).up1 = image[i];
                ((Projectile)this).up2 = image[i];
                ((Projectile)this).down1 = image[i];
                ((Projectile)this).down2 = image[i];
                ((Projectile)this).left1 = image[i];
                ((Projectile)this).left2 = image[i];
                ((Projectile)this).right1 = image[i];
                ((Projectile)this).right2 = image[i];
            } else down1 = image[i];
        }
        // endregion

        //region 2 SPRITES
        else if (image.length == E_MagicalNumber.LIST_OF_2_SPRITES.Value()) {
            for(int i = 0; i <image.length; i++) {
                if (i ==0) {
                    ((Alive_Entity)this).up1 = image[i];
                    down1 = image[i];
                    ((Alive_Entity)this).left1 = image[i];
                    ((Alive_Entity)this).right1 = image[i];
                }
                else {
                    ((Alive_Entity)this).up2 = image[i];
                    ((Alive_Entity)this).down2 = image[i];
                    ((Alive_Entity)this).left2 = image[i];
                    ((Alive_Entity)this).right2 = image[i];
                }
            }
        }
        // endregion

        //region 8 SPRITES
        else if (image.length == E_MagicalNumber.LIST_OF_8_SPRITES.Value()) {
            int i = 0;
            ((Alive_Entity)this).up1 = image[i];i++;
            ((Alive_Entity)this).up2 = image[i];i++;
            ((Alive_Entity)this).down1 = image[i];i++;
            ((Alive_Entity)this).down2 = image[i];i++;
            ((Alive_Entity)this).left1 = image[i];i++;
            ((Alive_Entity)this).left2 = image[i];i++;
            ((Alive_Entity)this).right1 = image[i];i++;
            ((Alive_Entity)this).right2 = image[i];
        }
        //endregion

        //region 24 SPRITES
        else if (image.length == E_MagicalNumber.LIST_OF_24_SPRITES.Value()) {
            int i = 0;
            ((Alive_Entity)this).up1 = image[i];i++;
            ((Alive_Entity)this).up2 = image[i];i++;
            ((Alive_Entity)this).down1 = image[i];i++;
            ((Alive_Entity)this).down2 = image[i];i++;
            ((Alive_Entity)this).left1 = image[i];i++;
            ((Alive_Entity)this).left2 = image[i];i++;
            ((Alive_Entity)this).right1 = image[i];i++;
            ((Alive_Entity)this).right2 = image[i];
        }
        //endregion

        return image;
    }


    /** ---- getParticleColor() ---- <p>
     *  Cette fonction offre aux classes enfants l'accès à un getter commun permettant de vérifier la couleur de ses particules, s'il en a.
     *
     * @return la couleur de particule
     */
    @Particles
    public Color getParticleColor() {
        return null;
    }


    /** ---- getParticleSize() ---- <p>
     *  Cette fonction offre aux classes enfants l'accès à un getter commun permettant de vérifier la taille de ses particules, s'il en a.
     *
     * @return la taille de particule
     */
    @Particles
    public int getParticleSize() {
        return 0;
    }


    /** ---- getParticlespeed() ---- <p>
     *  Cette fonction offre aux classes enfants l'accès à un getter commun permettant de vérifier la vitesse de ses particules, s'il en a.
     *
     * @return la vitesse de particule
     */
    @Particles
    public int getParticleSpeed() {
        return 0;
    }


    /** ---- getParticleMaxLife() ---- <p>
     *  Cette fonction offre aux classes enfants l'accès à un getter commun permettant de vérifier le temps de vie maximum
     *  de ses particules, s'il en a.
     *
     * @return la taille de particule
     */
    @Particles
    public int getParticleMaxLife() {
        return 0;
    }


    /** ---- getParticleSize() ---- <p>
     *  Cette fonction offre aux classes enfants l'accès à un setter (generator) commun leur permettant de créer leurs particules.
     *
     * @param generator permet l'accès aux données size, speed, et maxlife de l'entité particle
     * @param target est l'entité ciblée lors de la création les particules
     */
    @Particles
    public void generateParticle(Entity generator, Entity target) {

        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle_Constructor p1 = new Particle_Constructor(gp, target, color, size, speed, maxLife, -2, -1);
        Particle_Constructor p2 = new Particle_Constructor(gp, target, color, size, speed, maxLife, 2, -1);
        Particle_Constructor p3 = new Particle_Constructor(gp, target, color, size, speed, maxLife, -2, 1);
        Particle_Constructor p4 = new Particle_Constructor(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);

    }


    /** ---- update() -----<p>
     * Cette méthode gère la mise à jour des instances {@link Entity} <p>
     *
     * Elle effectue cette gestion pour :<p>
     *  1. LES ACTIONS<p>
     *  2. LES COLLISIONS<p>
     *  3. LES DÉGÂTS MONSTRES VERS JOUEUR via la fonction damagePlayer()<p>
     *  4. LES DÉPLACEMENTS<p>
     *  5. L'ANIMATION DES SPRITES<p>
     *  6. L'ÉTAT D'INVINCIBILITÉ
     */

    public void update() {

        int screenX = worldX - gp.player.worldX + gp.player.getScreenX();
        int screenY = worldY - gp.player.worldY + gp.player.getScreenY();

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // region COLLISION
        ((Solid_Entity)this).collisionOn = false;
        gp.cChecker.checkAll(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        // endregion

        // region DAMAGE MONSTER TO PLAYER
        if( (this instanceof Aggressive_Entity) && contactPlayer) { ((Aggressive_Entity)this).damagePlayer(((Aggressive_Entity)this).attack);}
        // endregion

        //region MOVE
        if(!((Solid_Entity)this).collisionOn) {
            switch (direction) {
                case UP -> worldY -= ((Alive_Entity)this).speed;
                case DOWN -> worldY += ((Alive_Entity)this).speed;
                case RIGHT -> worldX += ((Alive_Entity)this).speed;
                case LEFT -> worldX -= ((Alive_Entity)this).speed;
            }
        }
        //endregion

        // region SPRITE ANIMATION
        ((Alive_Entity)this).spriteCounter++;
        if(((Alive_Entity)this).spriteCounter > E_MagicalNumber.SPRITE_TIME_TO_CHANGE.Value()) {
            if(((Alive_Entity)this).spriteNum == E_ActualSprite.SPRITE1) {
                ((Alive_Entity)this).spriteNum = E_ActualSprite.SPRITE2;
            } else if (((Alive_Entity)this).spriteNum == E_ActualSprite.SPRITE2) {
                ((Alive_Entity)this).spriteNum = E_ActualSprite.SPRITE1;
            }
            ((Alive_Entity)this).spriteCounter = E_MagicalNumber.RESET_COUNTER.Value();
        }
        // endregion

        // region INVINCIBLE
        if (((Alive_Entity)this).invincible) {
            ((Alive_Entity)this).invincibleCounter++;

            if (((Alive_Entity)this).invincibleCounter > E_MagicalNumber.MONSTER_INVINCIBILITY_DURATION.Value()) {
                ((Alive_Entity)this).invincible = false;
                ((Alive_Entity)this).invincibleCounter = E_MagicalNumber.RESET_COUNTER.Value();
            }

            if (((Aggressive_Entity)this).shotAvailableCounter < E_MagicalNumber.SHOT_AVAILABLE_DURATION.Value()) {
                ((Aggressive_Entity)this).shotAvailableCounter++;
            }
        }
        // endregion
    }


    /** ---- draw() ----<p>
     * Méthode de dessin de l'entité.<p>
     *Elle prend en compte les dessins :<p>
     *     1. Déplacements<p>
     *     2. Attaques<p>
     *     3. Barre de pv des monstres<p>
     *     4. Etat d'invincibilité<p>
     *     5. Animation de mort via la fonction dyingAnimation()<p>
     * @param g2 L'objet Graphics2D utilisé pour dessiner.
     */

    public void draw(Graphics2D g2) {
        int screenX = getScreenCord("X"); int tempScreenX = screenX;
        int screenY = getScreenCord("Y"); int tempScreenY = screenY;
        boolean changePos = false;
        if (this instanceof Mobile_Entity) {
            switch(direction){
                case DOWN-> {changePos = ((Mobile_Entity)this).drawMoveAndAttack(g2, 0, E_Direction.DOWN);}
                case UP-> {changePos = ((Mobile_Entity)this).drawMoveAndAttack(g2, 2, E_Direction.UP);}
                case LEFT-> {changePos = ((Mobile_Entity)this).drawMoveAndAttack(g2, 4, E_Direction.LEFT);}
                case RIGHT-> {changePos = ((Mobile_Entity)this).drawMoveAndAttack(g2, 6, E_Direction.RIGHT);}
            }
        }


         if (this instanceof Aggressive_Entity) {
             if (((Aggressive_Entity)this).invincible) {
                 ((Aggressive_Entity)this).hpBarOn = true;
                 ((Aggressive_Entity)this).hpBarCounter = E_MagicalNumber.RESET_COUNTER.Value();
                 changeAlpha(g2, 0.4f);}

             if (((Aggressive_Entity)this).dying) {((Aggressive_Entity)this).dyingAnimation(g2);}
         }
        if (changePos && direction == E_Direction.UP) {tempScreenY -= gp.tileSize;}
        if (changePos && direction == E_Direction.LEFT) {tempScreenX -= gp.tileSize;}

         g2.drawImage(image, tempScreenX, tempScreenY, null);

                  //RESET ALPHA FOR DYING
         changeAlpha(g2,1f);
         }
         
    public int getScreenCord(String XorY) {
        return (XorY.equals("X")) ?  (worldX - gp.player.worldX + gp.player.getScreenX()) : (worldY - gp.player.worldY + gp.player.getScreenY());}


    /** ---- changeAlpha() <p>
     * Méthode pour modifier l'opacité de l'entité.
     *
     * @param g2 L'objet Graphics2D utilisé pour dessiner.
     * @param alphaValue La valeur d'opacité à définir.
     */
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }





}