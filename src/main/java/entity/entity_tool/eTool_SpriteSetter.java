package entity.entity_tool;

import main.GamePanel;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class eTool_SpriteSetter {
    GamePanel gp;

    /** Constructeur
     *
     * @param gp Instance du gamePanel
     */
    public eTool_SpriteSetter(GamePanel gp) {
        this.gp = gp;
    }


    /**  *---------------------------------------------* getSpritesImages *------------------------------------------------*
     * Récupère les images nécessaires à partir des fichiers disponibles et les redimensionne aux dimensions spécifiées.
     * Cette fonction utilise les méthodes setup() et getFileNamesFromStream() comme fonctions intermédiaires
     * pour obtenir la liste des noms de fichiers d'images et charger les images correspondantes.
     *
     * @param clazz La classe de l'instance appelante.
     * @param width La largeur désirée des images.
     * @param height La hauteur désirée des images.
     * @return Un tableau d'images résultantes.
     */
    public BufferedImage[] getSpritesImages(Class<?> clazz, int width, int height) {
        // region ATTRIBUTS
        //System.out.println(clazz);

        // FICHIER & DOSSIER
        String className = clazz.getSimpleName(); // nom class
        String folderName = clazz.getPackageName(); // nom folder
        String[] parts = folderName.split("\\.");
        folderName = parts[0];

        InputStream inputStream = eTool_SpriteSetter.class.getResourceAsStream("/" + folderName); // rechercher dans le dossier ressource
        List<String> fileNames = getFileNamesFromStream(inputStream, className); // Sortir seulement les ressources possédant la str nom class
        //for (String fileName : fileNames) {
        //    System.out.println(fileName);
        //}

        // NOMINATION VARIABLES
        String[] directions = {"_down", "_up",  "_left", "_right"};
        String[] states = {"_1", "_2"};
        String[] uses = {"", "_attack", "_axe"};
        boolean canUse = false;

        // STOCKAGE SORTIES
        List<String> allSpriteDirection = new ArrayList<>();

        // endregion

        // region 1 SPRITE
        if (fileNames.size() == 1) {
            allSpriteDirection.add(className + directions[0] + states[0]);
        }
        // endregion

        // region 2 SPRITES
        else if (fileNames.size() == 2) {
            for (int i = 0; i < 2; i++) {
                allSpriteDirection.add(className + directions[0] + states[i]);
            }
        }
        // endregion

        // region 8 SPRITES
        else if (fileNames.size() == 8) {
            for (String direction : directions) {
                for (String state : states) {
                    String variableName = direction + state;
                    allSpriteDirection.add(className + variableName);
                }
            }
        }
        // endregion

        // region 24 SPRITES
        else if (fileNames.size() == 24) {
             canUse = true;
            for (String use : uses) {
                for (String direction : directions) {
                    for (String state : states) {
                        String variableName = use + direction + state;
                        allSpriteDirection.add(className + variableName);
                    }
                }
            }
        }
        return setup(allSpriteDirection, folderName, width, height, canUse);
    }


    /** ---- setup ---- <p>
     * Cette fonction lit les images dans le dossier folder fournit en utilisant leur nom stocké dans spriteDirection.
     * Elle utilise ensuite les paramètres de tailles pour appeler la fonction scaleImage pour
     * scale à la taille voulue les images sorties.
     *
     * @param spriteDirection Liste des directions trouvées grâce à l'inputStream
     * @param folder Nom du dossier ou l'inputStream a cherché
     * @param width Taille de l'image désirée
     * @param height Taille de l'image désirée
     * @return Sortie des images résultantes
     */
    private BufferedImage[] setup(List<String> spriteDirection, String folder, int width, int height, boolean canUse) {
        BufferedImage[] images = new BufferedImage[spriteDirection.size()];
        int ini_width = width, ini_height = height;

        for (int i = 0; i < spriteDirection.size(); i++) {
            try {
                System.out.println(spriteDirection.get(i));
                BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + folder + "/" + spriteDirection.get(i) + ".png")));
                if (canUse && (spriteDirection.get(i).contains("attack") || spriteDirection.get(i).contains("axe")) ) {
                    if (spriteDirection.get(i).contains("left") ||spriteDirection.get(i).contains("right")) {
                        width = ini_width*2;}
                    if (spriteDirection.get(i).contains("up") ||spriteDirection.get(i).contains("down")) {
                        height = ini_height*2;}
                }
                images[i] = scaleImage(image, width, height);
                height = ini_height;
                width = ini_width;
            } catch (IOException e) {
                throw new RuntimeException("Failed to load image: " + spriteDirection.get(i), e);
            }
        }

        return images;
    }


    /** --- getFileNamesFromStream --- <p>
     * Cette fonction analyse tous les noms de fichiers se trouvant dans l'inputStream donné.
     * Elle filtre ensuite chaque élément en ne gardant que les .png et ceux dont le pattern className compose le nom
     * et les stocke dans une liste qu'elle renvoie.
     *
     * @param inputStream Lieu à scanner
     * @param className Pattern à vérifier
     * @return -> sortie de fileNames
     */
    public static List<String> getFileNamesFromStream(InputStream inputStream, String className) {

        List<String> fileNames = new ArrayList<>();
        BufferedReader reader = null;

        try { reader = new BufferedReader(new InputStreamReader(inputStream)); String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(className) && line.contains(".png")) {fileNames.add(line);}
            }

        }
        catch (IOException e) {e.printStackTrace();}

        finally { if (reader != null) {
            try {reader.close();} catch (IOException e) {e.printStackTrace();}}}
        return fileNames;
    }


    /**
     * Faire le scaling d'image lors de la lecture des tiles pour éviter de devoir recalculer le tiles à chaque fois
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();

        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }


}