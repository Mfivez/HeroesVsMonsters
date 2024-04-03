package entity.entity_interface;

import entity.Entity;

import java.awt.image.BufferedImage;

public interface I_EntitySpriteProvider {

    /** ---- setSprites() ---- <p>
     * Cette méthode gère tous les cas d'associations de sprites possibles des classes enfants d' {@link Entity}<p>
     *
     * Cas gérés :<p>
     * 0. 0 sprite en ressource<p>
     * 1. 1 sprite en ressources<p>
     * 2. 2 sprites en ressources<p>
     * 3. 8 sprites en ressources<p>
     *
     * @author MFivez
     * @LastUpdate 31-03-2024
     */
    public void setSprites();


    /**
     * Charge une image de sprite spécifique à partir d'un fichier d'image.
     * Cette méthode prend en charge :
     *      la lecture,
     *      le redimensionnement
     *      le chargement de l'image.
     *
     * @param imageName Le nom de l'image à charger.
     * @return L'image chargée et redimensionnée.
     */
    public BufferedImage setup(String imageName, String folderName, int width, int height);

}
