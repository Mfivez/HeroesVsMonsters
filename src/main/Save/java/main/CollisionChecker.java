package main;


import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }


    public void checkAll(Entity entity) {
        gp.cChecker.checkTile(entity);
        gp.cChecker.checkObject(entity, false);
        gp.cChecker.checkEntity(entity, gp.npc);
        gp.cChecker.checkEntity(entity, gp.monster);
        gp.cChecker.checkEntity(entity, gp.iTile);
    }


    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + ((Solid_Entity)entity).solidArea.x;
        int entityRightWorldX = entity.worldX + ((Solid_Entity)entity).solidArea.x + ((Solid_Entity)entity).solidArea.width;
        int entityTopWorldY = entity.worldY + ((Solid_Entity)entity).solidArea.y;
        int entityBottomWorldY = entity.worldY + ((Solid_Entity)entity).solidArea.y + ((Solid_Entity)entity).solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case UP -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case DOWN -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;

                }
            }
            case RIGHT -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case LEFT -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] != null) {

                // Get entity's solid area position
                ((Solid_Entity)entity).solidArea.x = entity.worldX + ((Solid_Entity)entity).solidArea.x;
                ((Solid_Entity)entity).solidArea.y = entity.worldY + ((Solid_Entity)entity).solidArea.y;

                // Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case UP -> ((Solid_Entity)entity).solidArea.y -= entity.speed;
                    case DOWN -> ((Solid_Entity)entity).solidArea.y += entity.speed;
                    case RIGHT -> ((Solid_Entity)entity).solidArea.x += entity.speed;
                    case LEFT -> ((Solid_Entity)entity).solidArea.x -= entity.speed;
                }
                if(((Solid_Entity)entity).solidArea.intersects(gp.obj[i].solidArea)) {

                    if(gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }

                    if(player) {
                        index = i;
                    }

                }
                ((Solid_Entity)entity).solidArea.x = ((Solid_Entity)entity).solidAreaDefaultX;
                ((Solid_Entity)entity).solidArea.y = ((Solid_Entity)entity).solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    //NPC OR MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if(target[i] != null) {

                // Get entity's solid area position
                ((Solid_Entity)entity).solidArea.x = entity.worldX + ((Solid_Entity)entity).solidArea.x;
                ((Solid_Entity)entity).solidArea.y = entity.worldY + ((Solid_Entity)entity).solidArea.y;

                // Get the object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction) {
                    case UP -> ((Solid_Entity)entity).solidArea.y -= entity.speed;
                    case DOWN -> ((Solid_Entity)entity).solidArea.y += entity.speed;
                    case RIGHT -> ((Solid_Entity)entity).solidArea.x += entity.speed;
                    case LEFT -> ((Solid_Entity)entity).solidArea.x -= entity.speed;
                }
                if(((Solid_Entity)entity).solidArea.intersects(target[i].solidArea)) {
                    if(target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                ((Solid_Entity)entity).solidArea.x = ((Solid_Entity)entity).solidAreaDefaultX;
                ((Solid_Entity)entity).solidArea.y = ((Solid_Entity)entity).solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // Get entity's solid area position
        ((Solid_Entity)entity).solidArea.x = entity.worldX + ((Solid_Entity)entity).solidArea.x;
        ((Solid_Entity)entity).solidArea.y = entity.worldY + ((Solid_Entity)entity).solidArea.y;

        // Get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction) {
            case UP -> ((Solid_Entity)entity).solidArea.y -= entity.speed;
            case DOWN -> ((Solid_Entity)entity).solidArea.y += entity.speed;
            case RIGHT -> ((Solid_Entity)entity).solidArea.x += entity.speed;
            case LEFT -> ((Solid_Entity)entity).solidArea.x -= entity.speed;
        }
        if(((Solid_Entity)entity).solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        ((Solid_Entity)entity).solidArea.x = ((Solid_Entity)entity).solidAreaDefaultX;
        ((Solid_Entity)entity).solidArea.y = ((Solid_Entity)entity).solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }

}
