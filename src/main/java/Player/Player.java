package Player;

import entity.Entity;
import entity.entity_lvl0.Solid_Entity;
import entity.entity_lvl2.Alive_Entity;
import entity.entity_lvl3.Aggressive_Entity;
import enums.*;
import main.GamePanel;
import KeyHandler.KeyHandler;


import monster.Monster;
import object.object_lvl0.Weapon;
import object.object_lvl0.Consumable;
import object.object_lvl0.PickUpOnly;
import object.object_lvl0.Shield;
import object.object_lvl2.equipable.Obj_Sword_Normal;
import object.object_lvl2.equipable.Obj_shield_Wood;
import projectile.projectile_lvl0.Obj_Fireball;
import projectile.projectile_lvl0.Obj_Rock;

import java.awt.*;
import java.util.ArrayList;


/**
 * La classe Player représente l'entité contrôlée par le joueur.
 * Elle gère :
 *  1. Mouvements du joueur
 *  2. Focus de la caméra sur le joueur
 *  3. Collisions tiles & objects
 *  4. Interactions avec les éléments du jeu
 */
public class Player extends Aggressive_Entity {
    private final KeyHandler keyH; //Détection entrées clavier

    //Positions de la caméra
    private final int screenX;
    private final int screenY;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int spawnMobCounter = E_MagicalNumber.RESET_COUNTER.Value();
    public int nextLvlExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;

    /**
     * Ini attributs PLAYER & load sprites.
     * @param gp Référence GamePanel.
     * @param keyH Référence KeyHandler.
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        if (keyH == null) {
            throw new IllegalArgumentException("GamePanel and KeyHandler must not be null.");
        }
        this.keyH = keyH;

        // Position initiale de la caméra joueur au centre de l'écran
        screenX = gp.screenWidth/2 -(gp.tileSize/2);
        screenY = gp.screenHeight/2 -(gp.tileSize/2);

        // Rectangle de collision joueur
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;

        // Load sprites joueur
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        setItems();
    }


    /**
     * Initialise les valeurs par défaut du joueur.
     */
    public void setDefaultValues() {
        worldX = gp.tileSize*3;
        worldY = gp.tileSize*3;
        speed = 4;
        direction = E_Direction.RIGHT;
        //PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        // // TEST POUR PROJECTILES
        strenght = getStrenght(); // More this, more damage give
        dexterity = getdexterity(); // More this, less damage receive
        exp = 0;
        nextLvlExp = 5;
        coin = 0;
        currentWeapon = new Obj_Sword_Normal(gp);
        currentShield = new Obj_shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public int getPlayerAttackSprite() {
        return (currentWeapon.type == E_EntityType.SWORD) ? 1 : 2;
    }


    public int getStrenght() {
        int i = 1;
        if (specie != null) {
            if (specie == E_species.HUMAN) {i+=1;}
        }
        return i;
    }

    public int getdexterity() {
        int i = 1;
        if (specie != null) {
            if (specie == E_species.HUMAN) {i+=1;}
        }
        if (specie != null) {
            if (specie == E_species.DWARF) {i+=2;}
        }
        return i;
    }

    public void GetClassAttributs() {
        if (mainClass == E_Main_Class.SORCIER) {
            maxMana = 1000;
            mana = maxMana;
            projectile = new Obj_Fireball(gp);
        }
        else if (mainClass == E_Main_Class.LANCE_PIERRE) {
            ammo = 10;
            projectile = new Obj_Rock(gp);
        }
    }

    public void setItems() {
        inventory.add(currentShield);
        inventory.add(currentWeapon);
    }

    public int getAttack(){
        attackArea = ((Solid_Entity)currentWeapon).attackArea;
        return (strenght * ((Weapon)currentWeapon).attackValue);
    }

    public int getDefense() {
        return (dexterity* ((Shield)currentShield).defenseValue);
    }


    /**
     * Met à jour l'état du joueur en fonction des entrées utilisateur et des interactions avec le jeu.
     * Cette méthode gère
     *          le mouvement du joueur,
     *          les collisions avec les tuiles et les objets.
     */
    public void update() {

        if (attacking) {attacking();}

        else if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed || keyH.enterPressed){

            if (keyH.upPressed) {
                direction = E_Direction.UP;
            }
            else if (keyH.downPressed) {
                direction = E_Direction.DOWN;
            }
            else if (keyH.rightPressed) {
                direction = E_Direction.RIGHT;
            }
            else if (keyH.leftPressed) {
                direction = E_Direction.LEFT;
            }
            spawnMobCounter++;

            if (spawnMobCounter >= E_MagicalNumber.MOB_TIME_TO_SPAWN.Value()) {
                gp.aSetter.setMonster();
                spawnMobCounter = E_MagicalNumber.RESET_COUNTER.Value();
            }


            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.tile().check(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.object().check(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.aliveEntity().check(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.aliveEntity().check(this, gp.monster);
            contactMonster(monsterIndex);

            //CHECK ITILES COLLISION
            int iTilesIndex = gp.cChecker.aliveEntity().check(this, gp.iTile);



            //CHECK EVENT
            gp.eHandler.checkEvent();


            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case UP -> worldY -= speed;
                    case DOWN -> worldY += speed;
                    case RIGHT -> worldX += speed;
                    case LEFT -> worldX -= speed;
                }
            }


            if (keyH.enterPressed && !attackCanceled) {
                gp.playSE(E_Sound.SWINGWEAPON);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;

            gp.keyH.enterPressed = false;

            /*
              Cette partie de fonction gère la vitesse de switch entre 2 sprites de déplacement
             */
            spriteCounter++;
            if(spriteCounter > E_MagicalNumber.SPRITE_TIME_TO_CHANGE.Value()) {
                if(spriteNum == E_ActualSprite.SPRITE1) {
                    spriteNum = E_ActualSprite.SPRITE2;
                } else if (spriteNum == E_ActualSprite.SPRITE2) {
                    spriteNum = E_ActualSprite.SPRITE1;
                }
                spriteCounter = E_MagicalNumber.RESET_COUNTER.Value();
            }
        }

        if(gp.keyH.shotKeyPressed && !projectile.alive
                && shotAvailableCounter == E_MagicalNumber.SHOT_AVAILABLE_DURATION.Value()
                && projectile.haveResource(this))
        {
            // SET DEFAULT COORDINATES, DIRECTION && USER
            projectile.set(worldX, worldY, direction, true, this);

            // SUBTRACT THE COST (MANA, AMMO,...)
            projectile.subtractResource(this);
            ammoRegen = true;
            // ADD TO LIST
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;

            gp.playSE(E_Sound.BURNING);
        }


        // OUTSIDE BECAUSE PLAYER DON T NEED TO MOVE TO LOSE INVICIBLE STATUS
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > E_MagicalNumber.PLAYER_INVINCIBILITY_DURATION.Value()) {
                invincible = false;
                invincibleCounter = E_MagicalNumber.RESET_COUNTER.Value();
            }
        }

        if (shotAvailableCounter < E_MagicalNumber.SHOT_AVAILABLE_DURATION.Value()) {
            shotAvailableCounter++;
        }
        if(life > maxLife) {
            life = maxLife;
        }
        if(mana > maxMana) {
            mana = maxMana;
        }

        if (ammoRegen) {
            ammoRegeneration();
        }
    }

    public void ammoRegeneration() {
        if (mainClass == E_Main_Class.SORCIER && maxMana > mana) {
            mana += 1;
            if (mana >= maxMana) {
                mana = maxMana; // Assure que la mana ne dépasse pas maxMana
                ammoRegen = false; // Arrête la régénération une fois que maxMana est atteint
            }
        }
    }

    public void attacking() {
        // ATTACKING ANIMATION
        spriteCounter++;

        if(spriteCounter <= E_MagicalNumber.ATTACKING_SPRITE_TIME_TO_CHANGE1.Value()) {
            spriteNum = E_ActualSprite.SPRITE1;
        }
        if(spriteCounter > E_MagicalNumber.ATTACKING_SPRITE_TIME_TO_CHANGE1.Value()
                && spriteCounter <= E_MagicalNumber.ATTACKING_SPRITE_TIME_TO_CHANGE2.Value()) {
            spriteNum = E_ActualSprite.SPRITE2;

            //save the current worldX/Y, solidarea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX/Y for the attackArea
            switch (direction) {
                case UP -> worldY -= attackArea.height;
                case DOWN -> worldY += attackArea.height;
                case LEFT -> worldX -= attackArea.width;
                case RIGHT -> worldX += attackArea.width;
            }

            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //Check Monster collision with the updated worldX/Y & solidArea
            int monsterIndex = gp.cChecker.aliveEntity().check(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.cChecker.aliveEntity().check(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            //After checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > E_MagicalNumber.ATTACKING_SPRITE_TIME_TO_CHANGE2.Value()) {
            spriteNum = E_ActualSprite.SPRITE1;
            spriteCounter = E_MagicalNumber.RESET_COUNTER.Value();
            attacking = false;
        }
    }


    /**
     * Gère l'interaction du joueur avec un objet spécifique du jeu.
     * Cette méthode est appelée lorsque le joueur entre en collision avec un objet du jeu.
     *
     * @param i L'indice de l'objet avec lequel le joueur est entré en collision.
     */
    public void pickUpObject(int i) {
        if(i != E_MagicalNumber.NOTHING.Value()) {

            // PICKUP ONLY ITEMS
            if (gp.obj[i] instanceof PickUpOnly) {
                ((PickUpOnly) gp.obj[i]).use(this);
            }

            //INVENTORY ITEMS
            else {
                String text;

                if(inventory.size() != maxInventorySize) {

                    inventory.add(gp.obj[i]);
                    gp.playSE(E_Sound.COIN);
                    text = "Got a " + gp.obj[i].name + "!";
                }
                else {
                    text = "Inventory is full !";
                }
                gp.ui.PlayState().addMessage(text);
            }
            gp.obj[i] = null;
        }
    }

    public void interactNPC(int i) {

        if(gp.keyH.enterPressed) {
            if(i != E_MagicalNumber.NOTHING.Value()) {
                attackCanceled = true;
                gp.gameState = E_GameState.DIALOGUE;
                ((Alive_Entity)gp.npc[i]).speak();
            }
        }
    }

    public void contactMonster(int i) {
        if(i != 999) {
            if (!invincible && !((Aggressive_Entity)gp.monster[i]).dying) {
                gp.playSE(E_Sound.RECEIVEDAMAGE);

                int damage = ((Aggressive_Entity)gp.monster[i]).attack - defense;
                if(damage < 0) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack) {

        if(i != 999) {

            if (!((Alive_Entity)gp.monster[i]).invincible) {
                gp.playSE(E_Sound.HITMONSTER);
                int damage = attack - ((Aggressive_Entity)gp.monster[i]).defense;
                if(damage < 0) {
                    damage = 0;
                }

                ((Monster)gp.monster[i]).life -= damage;
                gp.ui.PlayState().addMessage(damage + " damage !");
                ((Alive_Entity)gp.monster[i]).invincible = true;
                ((Alive_Entity)gp.monster[i]).damageReaction();

                if (((Monster)gp.monster[i]).life <=0) {
                    ((Aggressive_Entity)gp.monster[i]).dying = true;
                    gp.ui.PlayState().addMessage(gp.monster[i].name + " is dead !");
                    gp.ui.PlayState().addMessage("Exp + " + ((Aggressive_Entity)gp.monster[i]).exp + " !");
                    exp += ((Aggressive_Entity)gp.monster[i]).exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void damageInteractiveTile(int i) {

        if (i != 999 && gp.iTile[i].destructible
                && gp.iTile[i].isCorrectItem(this) && !gp.iTile[i].invincible) {
            gp.iTile[i].playSE();
            gp.iTile[i].life --;
            gp.iTile[i].invincible = true;

            generateParticle(gp.iTile[i], gp.iTile[i]);

            if(gp.iTile[i].life == 0) {
                gp.iTile[i] = gp.iTile[i].getDestroyedForm();
            }
        }
    }

    public void checkLevelUp() {

        if (exp >= nextLvlExp) {

            level++;
            nextLvlExp = nextLvlExp*2;
            maxLife += 2;
            strenght++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(E_Sound.LEVELUP);
            gp.gameState = E_GameState.DIALOGUE;
            gp.ui.DialogueState().setCurrentDialogue(
                    "You are level " + level + " now !\n"
                    + "You feel a little bit stronger !");


        }
    }

    public void selectItem() {

        int itemIndex = gp.ui.CharacterState().getItemIndexOnSlot();

        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == E_EntityType.SWORD || selectedItem.type == E_EntityType.AXE) {

                currentWeapon = selectedItem;
                attack = getAttack();

            }
            if (selectedItem.type == E_EntityType.SHIELD) {

                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem instanceof Consumable) {
                ((Consumable)selectedItem).use(this);
                inventory.remove(itemIndex);
            }
        }

    }


    /** --- Getters & Setters --- **/
    public int getScreenX() {return screenX;}

    public int getScreenY() {return screenY;}

}
