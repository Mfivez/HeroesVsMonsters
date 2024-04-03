package Tiles;


import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import entity.entity_tool.eTool_SpriteSetter;

public class TilesManager {

    GamePanel gp;
    public Tiles[] tile; // stockage des tuiles

    public int[][] mapTileNum; // map à l'écran

    List<String> spriteNames = new ArrayList<>();




    public TilesManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tiles[40];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImages();
        loadMap("/Maps/MAP.txt");
    }

    public void getTileImages() {
        //System.out.println("----Initialisation des sprites de sol----");
        //System.out.println();
        //System.out.println("Liste des sprites à récupérer :");
        for (int i = 0; i <= 37; i++) {
            String spriteName = String.format("%03d", i); // Formatage du numéro pour obtenir trois chiffres
            spriteNames.add(spriteName);
            //System.out.println(spriteName);
        }

        for (int i = 0; i < spriteNames.size(); i++) {
            tile[i] = new Tiles();
            int[] collisions = {16, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 32};
            boolean col = false;
            for (int collision : collisions) {
                if (i == collision) {
                    col = true;
                }
            }
            setup(i, col);
        }
    }

    public void setup(int index, boolean collision) {
        eTool_SpriteSetter eTool = new eTool_SpriteSetter(gp) ;

        try{
            tile[index] = new Tiles();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/" + spriteNames.get(index) + ".png")));
            tile[index].image = eTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.getScreenX();
            int screenY = worldY - gp.player.worldY + gp.player.getScreenY();

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.getScreenX() &&
                worldX - gp.tileSize  < gp.player.worldX + gp.player.getScreenX() &&
                worldY + gp.tileSize  > gp.player.worldY - gp.player.getScreenY() &&
                worldY - gp.tileSize  < gp.player.worldY + gp.player.getScreenY()) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }
        }


    }


    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            //System.out.println("Starting to read map file...");

            String line;
            while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
                //System.out.println(line);
                String[] numbers = line.split(" ");
                for (String number : numbers) {
                    int num = Integer.parseInt(number);
                    mapTileNum[col][row] = num;
                    col++;
                    // Reset column to 0 if it reaches the maximum column count
                    if (col >= gp.maxWorldCol ) {
                        col = 0;
                        break; // Break out of the inner loop to move to the next row
                    }
                }
                row++;
            }

            br.close();

        } catch (Exception e){
            System.out.println("Error while reading map file: " + e.getMessage());
        }
    }
}
