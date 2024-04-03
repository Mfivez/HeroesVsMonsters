package entity;

import annotation.*;
import interfaces.*;
import main.GamePanel;
import myenum.*;
import tools.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


/////////////////////////////////A FAIRE//////////////////////////////////////////////
// 1. S'occuper de la gestion des récupérations de sprites d'attaques pour le joueur afin de pouvoir supprimer la
//      méthode setup()
//


//////////////////////////////////////////////////////////////////////////////////////

/** Type d'{@link Entity} actuel :<p>
 *1. ABSTRATC NPC <p>
 *2. ABSTRACT MONSTER <p>
 *3. ABSTRACT OBJECT <p>
 *4. ABSTRACT PROJECTILE <p>
 *5. PARTICLE <p>
 *6. PLAYER <p>
 */
public abstract class Entity implements
        I_SpriteProvider // gère les sprites pour toutes les entités
        , I_NoPlayerIA  // gère les actions de tous (except player)
        , I_Usable_Items  // gère le comportement de drop.
        , I_Particles // gère ce qui s'apparente aux particules
        , I_drawable // gère le dessin sur l'application
{

    //region GENERAL
    public GamePanel gp;
    public BufferedImage
            // region MOVE
              up1, up2
            , down1, down2
            , left1, left2
            , right1, right2
            // endregion

            // region ATTACK
            , upAttack1, upAttack2
            , downAttack1, downAttack2
            , leftAttack1, leftAttack2
            , rightAttack1, rightAttack2
            // endregion

            // region OTHER
            , image, image2, image3;
            // endregion


    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collision = false;

    public String[] dialogues = new String[20];

    // endregion

    // region STATE

    public int worldX, worldY;

    public E_Direction direction = E_Direction.DOWN; // Etat initial d'affichage d'un sprite
    public E_ActualSprite spriteNum = E_ActualSprite.SPRITE1; // Etat initial d'affichage d'un sprite
    public int dialogueIndex = E_MagicalNumber.RESET_DIALOGUE_INDEX.Value(); // Etat initial de l'index de dialogue

    public boolean collisionOn; // Collision en cours ?
    public boolean invicible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean ammoRegen = false; //gère la régène mana & munition
    // endregion

    // region COUNTER
    public int spriteCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int invicibleCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int actionLockCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int shotAvailableCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int dyingCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int hpBarCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int spawnMobCounter = E_MagicalNumber.RESET_COUNTER.Value();


    // endregion

    // region CHARACTER ATTRIBUTES
    public String name;
    public E_species specie;
    public E_Main_Class mainClass;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strenght;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLvlExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    // endregion


    // region ITEM ATTRIBUTES
    public int attackValue;
    public int defenseValue;
    public int value;

    public String description = "";
    public int useCost;
    // endregion

    // region ENUM
    public E_EntityType type; // 0 = player, 1 = npc...
    // endregion


    /** --- constructeur ----
     *
     * @param gp instance du GamePanel
     */
    public Entity(GamePanel gp) {
        this.gp = gp;
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
    @Sprite
    public void setSprites() {
        BufferedImage[] image = gp.uToolSpriteSetter.getSpritesImages(this.getClass(), gp.tileSize, gp.tileSize);

        // region 0 SPRITE
        if (image.length == 0) {
            throw new IllegalStateException("Aucune image chargée pour " + this.getClass().getSimpleName());
        }
        // endregion

        // region 1 SPRITE
        if (image.length == E_MagicalNumber.LIST_OF_1_SPRITE.Value()) {
            int i = E_MagicalNumber.LIST_OF_1_SPRITE.Value()-1;
            up1 = image[i];
            down1 = image[i];
            left1 = image[i];
            right1 = image[i];
            up2 = image[i];
            down2 = image[i];
            left2 = image[i];
            right2 = image[i];
        }
        // endregion

        //region 2 SPRITES
        else if (image.length == E_MagicalNumber.LIST_OF_2_SPRITES.Value()) {
            for(int i = 0; i <image.length; i++) {
                if (i ==0) {
                    up1 = image[i];
                    down1 = image[i];
                    left1 = image[i];
                    right1 = image[i];
                }
                else {
                    up2 = image[i];
                    down2 = image[i];
                    left2 = image[i];
                    right2 = image[i];
                }
            }
        }
        // endregion

        //region 8 SPRITES
        else if (image.length == E_MagicalNumber.LIST_OF_8_SPRITES.Value()) {
            up1 = image[0];
            up2 = image[1];
            down1 = image[2];
            down2 = image[3];
            left1 = image[4];
            left2 = image[5];
            right1 = image[6];
            right2 = image[7];
        }
        //endregion
    }


    /**
     * Charge une image de sprite spécifique du joueur à partir d'un fichier d'image.
     * Cette méthode prend en charge :
     *      la lecture,
     *      le redimensionnement
     *      le chargement de l'image.
     *
     * @param imageName Le nom de l'image à charger.
     * @return L'image chargée et redimensionnée.
     */
    @Sprite
    public BufferedImage setup(String imageName, String folderName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image;

        try (InputStream inputStream = getClass().getResourceAsStream("/" + folderName + "/" + imageName + ".png")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Image not found: " + imageName);
            }
            image = ImageIO.read(inputStream);
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + imageName, e);
        }

        return image;
    }


    /** ---- setAction() ---- <p>
     * Cette fonction offre aux classes enfant d'entité l'accès à cette méthode permettant de définir le comportement de l'entité
     */
    @IA
    public void setAction() {}


    /** ---- damageReaction() ---- <p>
     * Cette fonction offre aux classes enfant d'entité l'accès à cette méthode permettant de définir le comportement de l'entité
     * lors de la réception de dégâts
     */
    @IA
    public void damageReaction() {}


    /** ---- speak() ----<p>
     * Méthode pour afficher le dialogue de l'entité.<p>
     * Lors de la rencontre du 1er index == null, la liste de dialogues recommence à l'index 0.<p>
     * Elle force l'entité avec laquelle l'entité joueur entre en communication à se tourner vers lui.
     */
    @IA
    public void speak() {

        // region DIALOGUE
        if(dialogues[dialogueIndex] == null) { //Recommencer la liste de dialogues
            dialogueIndex = E_MagicalNumber.RESET_DIALOGUE_INDEX.Value();
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        // endregion

        // region DIRECTION ENTITY ON DIALOGUE
        switch (gp.player.direction) {
            case UP -> direction = E_Direction.DOWN;
            case DOWN -> direction = E_Direction.UP;
            case RIGHT -> direction = E_Direction.LEFT;
            case LEFT -> direction = E_Direction.RIGHT;
        }
        // endregion

    }


    /** ---- damagePlayer()  ----- <p>
     * Cette fonction permet à une {@link Entity} d'infliger des dégats à l'instance joueur si il n'est pas dans un
     *  état d'invincibilité.
     *  L'instance joueur est ensuite passé en état d'invincibilité.
     *
     * @param attack dégâts infligés au joueur
     */
    @IA
    public void damagePlayer(int attack) {

        if(!gp.player.invicible) {
            //DAMAGE IS GIVABLE
            gp.playSE(E_Sound.HITMONSTER);

            int damage = attack - gp.player.defense;
            if(damage < E_MagicalNumber.RESET_COUNTER.Value()) {
                damage = E_MagicalNumber.RESET_COUNTER.Value();
            }
            gp.player.life -= damage;
            gp.player.invicible = true;

        }

    }


    /** ---- use() ---- <p>
     * Cette fonction offre aux classes enfants d'entités l'accès à cette méthode permettant de définir le comportement
     * de l'instance lors de son utilisation (au ramassage ou à l'utilisation).
     */
    @Items
    public void use(Entity entity){}


    /** ---- dropItem() ---- <p>
     *  Cette fonction est utilisé sur les classes enfants pour définir les objets droppable de l'entité
     *
     * @param droppedItem instance de l'objet drop
     */
    @Items
    public void dropItem(Entity droppedItem) {

        for(int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == null) {
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX;
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }


    /** ---- checkDrop() ---- <p>
     *  Cette fonction est utilisé sur les classes enfants pour leur permettre de définir quand un item peut être droppé
     *  par une instance de la classe enfant d'entité
     */
    @Items
    public void checkDrop() {}


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

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
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
    @Maj
    public void update() {

        // region ACTION
        setAction();
        // endregion

        // region COLLISION
        collisionOn = false;
        gp.cChecker.checkAll(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        // endregion

        // region DAMAGE MONSTER TO PLAYER
        if(this.type == E_EntityType.MONSTER && contactPlayer) { damagePlayer(attack);}
        // endregion

        //region MOVE
        if(!collisionOn) {
            switch (direction) {
                case UP -> worldY -= speed;
                case DOWN -> worldY += speed;
                case RIGHT -> worldX += speed;
                case LEFT -> worldX -= speed;
            }
        }
        //endregion

        // region SPRITE ANIMATION
        spriteCounter++;
        if(spriteCounter > E_MagicalNumber.SPRITE_TIME_TO_CHANGE.Value()) {
            if(spriteNum == E_ActualSprite.SPRITE1) {
                spriteNum = E_ActualSprite.SPRITE2;
            } else if (spriteNum == E_ActualSprite.SPRITE2) {
                spriteNum = E_ActualSprite.SPRITE1;
            }
            spriteCounter = E_MagicalNumber.RESET_COUNTER.Value();
        }
        // endregion

        // region INVINCIBLE
        if (invicible) {
            invicibleCounter++;

            if (invicibleCounter > E_MagicalNumber.MONSTER_INVINCIBILITY_DURATION.Value()) {
                invicible = false;
                invicibleCounter = E_MagicalNumber.RESET_COUNTER.Value();
            }

            if (shotAvailableCounter < E_MagicalNumber.SHOT_AVAILABLE_DURATION.Value()) {
                shotAvailableCounter++;
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
    @Draw
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.getScreenX();
        int screenY = worldY - gp.player.worldY + gp.player.getScreenY();

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // region ATTACK & MOVE
        switch (direction) {
            case UP -> {
                if (!attacking) {
                    if (spriteNum == E_ActualSprite.SPRITE1) {
                        image = up1;
                    }
                    if (spriteNum == E_ActualSprite.SPRITE2) {
                        image = up2;
                    }
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == E_ActualSprite.SPRITE1) {
                        image = upAttack1;
                    }
                    if (spriteNum == E_ActualSprite.SPRITE2) {
                        image = upAttack2;
                    }
                }
            }
            case DOWN -> {
                if (!attacking) {
                    if (spriteNum == E_ActualSprite.SPRITE1) {
                        image = down1;
                    }
                    if (spriteNum == E_ActualSprite.SPRITE2) {
                        image = down2;
                    }
                }
                if (attacking) {
                    if (spriteNum == E_ActualSprite.SPRITE1) {
                        image = downAttack1;
                    }
                    if (spriteNum == E_ActualSprite.SPRITE2) {
                        image = downAttack2;
                    }
                }
            }
            case LEFT -> {
                if (!attacking) {
                    if (spriteNum == E_ActualSprite.SPRITE1) {
                        image = left1;
                    }
                    if (spriteNum == E_ActualSprite.SPRITE2) {
                        image = left2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == E_ActualSprite.SPRITE1) {
                        image = leftAttack1;
                    }
                    if (spriteNum == E_ActualSprite.SPRITE2) {
                        image = leftAttack2;
                    }
                }
            }
            case RIGHT -> {
                if (!attacking) {
                    if (spriteNum == E_ActualSprite.SPRITE1) {
                        image = right1;
                    }
                    if (spriteNum == E_ActualSprite.SPRITE2) {
                        image = right2;
                    }
                }
                if (attacking) {
                    if (spriteNum == E_ActualSprite.SPRITE1) {
                        image = rightAttack1;
                    }
                    if (spriteNum == E_ActualSprite.SPRITE2) {
                        image = rightAttack2;
                    }
                }
            }
        }
        // endregion

        // region MONSTER HP BAR
        if (type == E_EntityType.MONSTER && hpBarOn) {

            double oneScale = (double)gp.tileSize/maxLife;
            double hpBarValue = oneScale*life;


            g2.setColor(E_RGB_COLOR.GRAY_OF_MONSTER_HP_BARRE.getColor());
            g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);

            //Vie restante
            g2.setColor(E_RGB_COLOR.RED_OF_MONSTER_HP_BARRE.getColor());
            g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

            hpBarCounter ++;

            if(hpBarCounter > E_MagicalNumber.HP_BAR_TIME_TO_DISAPPEAR.Value()) {
                hpBarCounter = E_MagicalNumber.RESET_COUNTER.Value();
                hpBarOn = false;
            }
        }
        // endregion

        //region INVINCIBILITY
        if (invicible) {
            hpBarOn = true;
            hpBarCounter = E_MagicalNumber.RESET_COUNTER.Value();
            changeAlpha(g2, 0.4f);
        }
        // endregion

        //region DYING
        if (dying) {dyingAnimation(g2);}
        // endregion

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //RESET ALPHA FOR DYING
        changeAlpha(g2, 1f);
    }


    /** ---- dyingAnimation() ---- <p>
     * Anime la particule lors de sa mort en faisant clignoter son opacité.
     * L'opacité de la particule est modifiée à intervalles réguliers pour simuler un clignotement.
     * Une fois que le compteur de mort atteint une certaine valeur, la particule est marquée comme "non vivante" et disparaît.
     *
     * @param g2 L'objet Graphics2D utilisé pour dessiner.
     */
    @Draw
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int remainder = dyingCounter % 10; // Calcule le reste de la division entière du compteur de mort par 10
        float alpha = (remainder <= 5) ? 0f : 1f; // Détermine l'opacité en fonction du reste calculé
        changeAlpha(g2, alpha);// Modifie l'opacité

        if (dyingCounter > E_MagicalNumber.DYING_DURATION.Value()) {
            alive = false;
        }
    }


    /** ---- changeAlpha() <p>
     * Méthode pour modifier l'opacité de l'entité.
     *
     * @param g2 L'objet Graphics2D utilisé pour dessiner.
     * @param alphaValue La valeur d'opacité à définir.
     */
    @Draw
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
}
