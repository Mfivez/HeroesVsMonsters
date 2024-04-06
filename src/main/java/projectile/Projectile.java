package projectile;

import entity.Entity;
import entity.entity_lvl0.Solid_Entity;
import entity.entity_lvl3.Aggressive_Entity;
import main.GamePanel;
import enums.E_Direction;
import enums.E_ActualSprite;
import enums.E_MagicalNumber;

public abstract class Projectile extends Aggressive_Entity {
    Entity user;
    public Projectile(GamePanel gp) {
        super(gp);
    }
    public int useCost;

    public void set(int worldX, int worldY, E_Direction direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    public void update(){

            if (user == gp.player) {
                int monsterIndex = gp.cChecker.aliveEntity().check(this, gp.monster);
                if (monsterIndex != 999) {
                    gp.player.damageMonster(monsterIndex, attack);
                    generateParticle(gp.player.projectile, gp.monster[monsterIndex]);
                    alive = false;
                }

            }
            if (user != gp.player) {
                boolean contactPlayer = gp.cChecker.player().check(this);
                if(!gp.player.invincible && contactPlayer) {
                    damagePlayer(attack);
                    generateParticle(((Aggressive_Entity)user).projectile, gp.player);
                    alive = false;
                }
            }

        switch (direction) {
            case UP -> worldY -= speed;
            case DOWN -> worldY += speed;
            case RIGHT -> worldX += speed;
            case LEFT -> worldX -= speed;
        }

            life--;
            if(life <= 0) {
                alive = false;
            }

            spriteCounter++;
            if(spriteCounter > E_MagicalNumber.SPRITE_TIME_TO_CHANGE.Value()) {
                if(spriteNum == E_ActualSprite.SPRITE1) {
                    spriteNum = E_ActualSprite.SPRITE2;
                }
                else if (spriteNum == E_ActualSprite.SPRITE2) {
                    spriteNum = E_ActualSprite.SPRITE1;
                }
                spriteCounter = E_MagicalNumber.RESET_COUNTER.Value();
            }
        }

    public boolean haveResource(Entity user) {
        return false;
    }

    public void subtractResource(Entity user) {
    }

}
