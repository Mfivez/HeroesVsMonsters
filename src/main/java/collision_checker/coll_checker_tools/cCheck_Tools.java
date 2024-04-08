package collision_checker.coll_checker_tools;


import entity.Entity;
import entity.entity_lvl0.Solid_Entity;
import entity.entity_lvl2.Alive_Entity;
import enums.E_MagicalNumber;
import main.GamePanel;

public class cCheck_Tools {
    GamePanel gp;

    //0= 1st screen, 1=2nd screen,...

    /**
     * Constructeur de la classe UI.
     *
     * @param gp Le GamePanel associé à cette interface utilisateur.
     */
    public cCheck_Tools(GamePanel gp) {
        this.gp = gp;
    }


    // Méthode générique pour déplacer l'aire solide d'un objet
    public static void moveSolidArea(Entity entity) {
        if (entity != null && ((Solid_Entity)entity).solidArea != null) {
            ((Solid_Entity)entity).solidArea.x = entity.worldX + ((Solid_Entity)entity).solidArea.x;
            ((Solid_Entity)entity).solidArea.y = entity.worldY + ((Solid_Entity)entity).solidArea.y;
        }}

    public static void updatePosition(Solid_Entity entity) {
        switch (entity.direction) {
            case UP -> entity.solidArea.y -= ((Alive_Entity)entity).speed;
            case DOWN -> entity.solidArea.y += ((Alive_Entity)entity).speed;
            case RIGHT -> entity.solidArea.x += ((Alive_Entity)entity).speed;
            case LEFT -> entity.solidArea.x -= ((Alive_Entity)entity).speed;
        }
    }

    public static int checkCollisionEntities(Solid_Entity entity, Entity entity2, int i, int index) {
        if(entity.solidArea.intersects(((Solid_Entity)entity2).solidArea)) {
            if(entity2 != entity) { entity.collisionOn = true; index = i; }
        }
        return index;
    }

    public static boolean checkCollisionWithPlayer(Solid_Entity entity, Solid_Entity entity2, boolean contactPlayer) {
        if(entity.solidArea.intersects(entity2.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        return contactPlayer;
    }

    public static int checkCollisionWithObject(Solid_Entity entity, Solid_Entity entity2, Integer i, boolean player) {
        int index = E_MagicalNumber.NOTHING.Value();
        if(entity.solidArea.intersects(entity2.solidArea)) {
            if(entity2.collision) { entity.collisionOn = true; }
            if(player) {index = i;}
        }
        return index;
    }

    public static void resetSolidArea(Solid_Entity entity, Entity entity2) {
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        ((Solid_Entity)entity2).solidArea.x = ((Solid_Entity)entity2).solidAreaDefaultX;
        ((Solid_Entity)entity2).solidArea.y = ((Solid_Entity)entity2).solidAreaDefaultY;
    }
}
