package Tiles.tiles_interactive.iTile_lvl0;

import main.GamePanel;
import Tiles.tiles_interactive.InteractiveTile;

public class IT_Trunk extends InteractiveTile {
    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

}
