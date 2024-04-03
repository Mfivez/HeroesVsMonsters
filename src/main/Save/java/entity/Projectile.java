package entity;

import main.GamePanel;
import myenum.E_Direction;
import myenum.E_ActualSprite;
import myenum.E_MagicalNumber;

public abstract class Projectile extends Entity {
    Entity user;
    public Projectile(GamePanel gp) {
        super(gp);
    }

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
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                if (monsterIndex != 999) {
                    gp.player.damageMonster(monsterIndex, attack);
                    generateParticle(user.projectile, gp.monster[monsterIndex]);
                    alive = false;
                }

            }
            if (user != gp.player) {
                boolean contactPlayer = gp.cChecker.checkPlayer(this);
                if(!gp.player.invicible && contactPlayer) {
                    damagePlayer(attack);
                    generateParticle(user.projectile, gp.player);
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
