package interfaces;


import annotation.IA;

@IA
public interface I_NoPlayerIA {
    void setAction(); // comportement général
    void damageReaction(); // réaction aux dégâts
    void damagePlayer(int attack); // infliger des dégâts au joueur
    void speak(); // dialogue de l'entité
}
