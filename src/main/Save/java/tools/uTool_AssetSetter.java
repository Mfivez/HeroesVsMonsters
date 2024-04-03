package tools;
import entity.Entity;
import main.GamePanel;
import myenum.E_EntityCategory;
import tiles_interactive.InteractiveTile;

public class uTool_AssetSetter {

    //region COUNTER
    GamePanel gp;
    static int iObject = 0;
    static int iNpc = 0;
    static int iIntTiles = 0;
    // endregion

    /** Constructeur
     *
     * @param gp Instance du GamePanel
     */
    public uTool_AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /** *-----------------------------------------------* setSomething() *------------------------------------------------*
     * Cette fonction utilise le nom de la fonction dans laquelle elle est appelée pour déterminer quel est/sont le(s) type(s) de/des
     * (l') entité(s) qu'elle doit instancier inGame.
     * Elle utilise la méthode Vargar pour permettre l'instanciation d'une infinité d'entités depuis la même fonction
     * @param functionName Nom de la fonction appelante
     * @param entities Instance(s) d'Entity arrivantes
     * @see E_EntityCategory Enum des catégories d'entitées
     */
    public void setSomething(String functionName, Entity... entities) {

        // region OBJECT
        if (functionName.contains(E_EntityCategory.OBJECT.Name())) {
            // Ajouter les entités à la liste d'objets
            for (Entity entity : entities) {
                gp.obj[iObject] = entity;
                iObject++;
            }
        }
        // endregion

        // region NPC
        if (functionName.contains(E_EntityCategory.NPC.Name())) {
            // Ajouter les entités à la liste des NPC
            for (Entity entity : entities) {
                gp.npc[iNpc] = entity;
                iNpc++;
            }
        }
        // endregion

        // region MONSTER
        if (functionName.contains(E_EntityCategory.MONSTER.Name())) {
            int iMonster = 0;
            for (Entity entity : entities) {
                for (iMonster = 0; iMonster < gp.monster.length; iMonster++) {
                    if (gp.monster[iMonster] == null) {gp.monster[iMonster] = entity;break;
                    }
                }
            }
        }
        // endregion

        // region iTILES
        if (functionName.contains(E_EntityCategory.INTERACTIVE_TILE.Name())) {
            // Ajouter les entités à la liste des tuiles interactives
            for (Entity entity : entities) {
                gp.iTile[iIntTiles] = (InteractiveTile) entity;
                iIntTiles++;
            }
        }
        // endregion

    }
}
