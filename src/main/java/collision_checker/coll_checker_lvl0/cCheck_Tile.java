package collision_checker.coll_checker_lvl0;


import collision_checker.coll_checker_tools.cCheck_Tools;
import entity.Entity;
import entity.entity_lvl0.Solid_Entity;
import entity.entity_lvl2.Alive_Entity;
import enums.E_MagicalNumber;
import main.GamePanel;

public class cCheck_Tile {
    private final GamePanel gp;

    public cCheck_Tile(GamePanel gp, cCheck_Tools tools) {
        this.gp = gp;
    }


    public void check(Entity entity) {
        int entityLeftWorldX = entity.worldX + ((Solid_Entity)entity).solidArea.x;
        int entityRightWorldX = entity.worldX + ((Solid_Entity)entity).solidArea.x + ((Solid_Entity)entity).solidArea.width;
        int entityTopWorldY = entity.worldY + ((Solid_Entity)entity).solidArea.y;
        int entityBottomWorldY = entity.worldY + ((Solid_Entity)entity).solidArea.y + ((Solid_Entity)entity).solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        switch (entity.direction) {
            case UP -> checkCollision(entityTopWorldY, entityLeftCol, entityRightCol, ((Alive_Entity)entity),  "Y", "-");
            case DOWN -> checkCollision(entityBottomWorldY, entityLeftCol, entityRightCol, ((Alive_Entity)entity),  "Y", "+");
            case RIGHT -> checkCollision(entityRightWorldX, entityTopRow, entityBottomRow, ((Alive_Entity)entity), "X", "+");
            case LEFT -> checkCollision(entityLeftWorldX, entityTopRow, entityBottomRow, ((Alive_Entity)entity), "X", "-");
        }
    }

    public void checkCollision(int entityColOrRow, int b, int c, Alive_Entity entity, String DoubleAxe, String signe) {
        int DoubleA = getEntityColOrRow(entityColOrRow, entity, signe);
        int tileNum1 = E_MagicalNumber.NOTHING.Value(), tileNum2 = E_MagicalNumber.NOTHING.Value();
        if (DoubleAxe.equals("X")) {
            tileNum1 = getTileNum(DoubleA, b);
            tileNum2 = getTileNum(DoubleA, c);
        }
        else if (DoubleAxe.equals("Y")) {
            tileNum1 = getTileNum(b, DoubleA);
            tileNum2 = getTileNum(c, DoubleA);
        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    public int getTileNum(int col, int row) {
        return gp.tileM.mapTileNum[col][row];
    }

    public int getEntityColOrRow(int entityColOrRow , Alive_Entity entity, String signe) {
        if (signe.equals("-")) { return (entityColOrRow - entity.speed)/gp.tileSize;}
        else { return (entityColOrRow + entity.speed)/gp.tileSize;}
        }
}
