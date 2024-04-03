package tools;

import main.GamePanel;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class uTool_SpriteSetter {
    GamePanel gp;

    /** Constructeur
     *
     * @param gp Instance du gamePanel
     */
    public uTool_SpriteSetter(GamePanel gp) {
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
        InputStream inputStream = uTool_SpriteSetter.class.getResourceAsStream("/" + folderName); // rechercher dans le dossier ressource
        List<String> fileNames = getFileNamesFromStream(inputStream, className); // Sortir seulement les ressources possédant la str nom class
        //for (String fileName : fileNames) {
        //    System.out.println(fileName);
        //}

        // NOMINATION VARIABLES
        String[] directions = {"_up", "_down", "_left", "_right"};
        String[] states = {"_1", "_2"};

        // STOCKAGE SORTIES
        List<String> allSpriteDirection = new ArrayList<>();

        // endregion

        // region 1 SPRITE
        if (fileNames.size() == 1) {
            allSpriteDirection.add(className + directions[1] + states[0]);
        }
        // endregion

        // region 2 SPRITES
        else if (fileNames.size() == 2) {
            for (int i = 0; i < 2; i++) {
                allSpriteDirection.add(className + directions[1] + states[i]);
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

        // region 32 SPRITES
        else if (fileNames.size() == 32) {
            for (String direction : directions) {
                for (String state : states) {
                    String variableName = direction + state;
                    allSpriteDirection.add(className + variableName);
                }
            }
        }
        // endregion



        return setup(allSpriteDirection, folderName, width, height);
    }

    /** *------------------------------------------------------* setup *------------------------------------------------------*
     * Cette fonction lit les images dans le dossier folder fournit en utilisant leur nom stocké dans spriteDirection.
     * Elle utilise ensuite les paramètres de tailles pour appeler la fonction scaleImage de la class {@link UtilityTool} pour
     * scale à la taille voulue les images sorties.
     *
     * @param spriteDirection Liste des directions trouvées grâce à l'inputStream
     * @param folder Nom du dossier ou l'inputStream a cherché
     * @param width Taille de l'image désirée
     * @param height Taille de l'image désirée
     * @return Sortie des images résultantes
     */
    private BufferedImage[] setup(List<String> spriteDirection, String folder, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage[] images = new BufferedImage[spriteDirection.size()];

        for (int i = 0; i < spriteDirection.size(); i++) {
            try {
                BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + folder + "/" + spriteDirection.get(i) + ".png")));
                images[i] = uTool.scaleImage(image, width, height);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load image: " + spriteDirection.get(i), e);
            }
        }

        return images;
    }


    /** *------------------------------------------* getFileNamesFromStream *------------------------------------------*
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

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(className) && line.contains(".png")) {
                    fileNames.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileNames;
    }
}