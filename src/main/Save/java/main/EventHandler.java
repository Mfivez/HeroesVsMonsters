package main;

import myenum.E_Direction;
import myenum.E_GameState;
import myenum.E_Sound;

public class EventHandler {

    GamePanel gp;
    EventRect[][] eventRect;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;


    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect(23, 23, 23, 23);
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {

        //Check if the player character is more than 1 tile awway frome the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
        if(hit(1, 1, E_Direction.UP)) {damagePit(1,1, E_GameState.DIALOGUE);}
        if(hit(1, 3, E_Direction.ANY)) {damagePit(1,3, E_GameState.DIALOGUE);}
        if(hit(0, 0, E_Direction.UP)) {healingPool(E_GameState.DIALOGUE);}
        }
    }

    public boolean hit(int Col, int Row, E_Direction reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[Col][Row].x = Col*gp.tileSize + eventRect[Col][Row].x;
        eventRect[Col][Row].y = Row*gp.tileSize + eventRect[Col][Row].y;

        if(gp.player.solidArea.intersects(eventRect[Col][Row])
                && !eventRect[Col][Row].eventDone) {
                    if(gp.player.direction.equals(reqDirection) || reqDirection.equals(E_Direction.ANY)) {
                        hit = true;

                        previousEventX = gp.player.worldX;
                        previousEventY = gp.player.worldY;
                }
            }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[Col][Row].x = eventRect[Col][Row].eventRectDefaultX;
        eventRect[Col][Row].y = eventRect[Col][Row].eventRectDefaultY;

        return hit;
    }

    public void teleport(E_GameState gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport!";
        gp.player.worldX = gp.tileSize*37;
        gp.player.worldY = gp.tileSize*10;
    }

    public void damagePit(int col, int row, E_GameState gameState) {
        gp.gameState = gameState;
        gp.playSE(E_Sound.RECEIVEDAMAGE);
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -=1;
        eventRect[col][row].eventDone = true; // Désactiver après une utilisation
        canTouchEvent = false;
    }

    public void healingPool(E_GameState gameState) {

        if (gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(E_Sound.COIN);
            gp.ui.currentDialogue = "incr comment je me heal";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster(); // Fais réapparaitre les monstres quand je me soigne
        }
    }
}
