package asset_setter;


import asset_setter.Assetter_tool.AssetSetter_Tool;
import enums.E_MagicalNumber;
import main.GamePanel;
import npc.npc_lvl1.Npc_OldMan;
import monster.monster_lvl0.MOB_GreenSlim;
import object.object_lvl2.equipable.Obj_Axe;
import tiles_interactive.iTile_lvl0.IT_DryTree;

import java.util.Random;

/** La classe AssetSetter permet la disposition d'entitées sur la map à un point donné.
 *
 */
public class AssetSetter {

    GamePanel gp;

    /** Constructeur
     *
     * @param gp Import de l'instance gamePanel
     */
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /** ---- setAllThings() ----<p>
     *Cette fonction set tous les types d'entitées settables depuis cette class
     * @Entity Object
     * @Entity Npc
     * @Entity Monster
     * @Entity iTILES
     */
    public void setAllThings() {
        setObject();
        setNPC();
        setMonster();
        setInteractiveTile();
    }

    /** ---- setObject() ----<p>
     *Cette fonction utilise {@link AssetSetter_Tool} pour disposer sur le terrain les entités de catégorie OBJECT
     */
    public void setObject() {
        gp.uToolAssetSetter.setSomething(new Object(){}.getClass().getEnclosingMethod().getName(),
                new Obj_Axe(gp, 11, 3)
        );
    }

    /** ---- setNPC() ----<p>
     *Cette fonction utilise {@link AssetSetter_Tool} pour disposer sur le terrain les entités de catégorie NPC
     */
    public void setNPC() {
        gp.uToolAssetSetter.setSomething(new Object(){}.getClass().getEnclosingMethod().getName(),
                  new Npc_OldMan(gp, 12, 5)
        );
    }

    /**  ---- setMonsters() ---- <p>
     *Cette fonction utilise {@link AssetSetter_Tool} pour disposer sur le terrain les entités de catégorie MONSTER
     */
    public void setMonster() {
        if (gp.player != null){
            int playerX = gp.player.worldX/gp.tileSize;
            int playerY = gp.player.worldY/gp.tileSize;
            if (playerX > 0 && playerX < 17
                    && playerY <= 21 && playerY > 15) {
                if (gp.monster[E_MagicalNumber.MAX_MONSTER_ON_MAP.Value()-1] == null) {
                    int spawnMobRayon = new Random().nextInt(5)+1;
                    int spawnMobDirection = new Random().nextInt(4);
                    int spawnMobX = playerX - spawnMobRayon;
                    int spawnMobY = playerY - spawnMobRayon;
                    if (spawnMobDirection == 0) {
                        spawnMobX = playerX + spawnMobRayon;
                        spawnMobY = playerY + spawnMobRayon;
                    }
                    else if (spawnMobDirection == 1) {
                        spawnMobX = playerX + spawnMobRayon;
                        spawnMobY = playerY - spawnMobRayon;
                    }
                    else if (spawnMobDirection == 2) {
                        spawnMobX = playerX - spawnMobRayon;
                        spawnMobY = playerY - spawnMobRayon;
                    }
                    if (spawnMobX > 16) {spawnMobX = 16;}
                    else if (spawnMobX < 1) {spawnMobX = 1;}
                    if (spawnMobY < 16) {spawnMobY = 16;}
                    else if (spawnMobY > 21) {spawnMobY = 21;}
                    gp.uToolAssetSetter.setSomething(new Object(){}.getClass().getEnclosingMethod().getName(),
                            new MOB_GreenSlim(gp, spawnMobX, spawnMobY)
                    );
                }
            }
        }
    }

    /** ---- setInteractiveTile() ----
     *Cette fonction utilise {@link AssetSetter_Tool} pour disposer sur le terrain les entités de catégorie iTILES
     */
    public void setInteractiveTile() {
        gp.uToolAssetSetter.setSomething(new Object(){}.getClass().getEnclosingMethod().getName(),
                 new IT_DryTree(gp, 7, 5)
                ,new IT_DryTree(gp, 9, 5)
                ,new IT_DryTree(gp, 3, 12)
                ,new IT_DryTree(gp, 5, 15)
                ,new IT_DryTree(gp, 6, 15)
        );
    }

}
