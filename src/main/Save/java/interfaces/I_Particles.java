package interfaces;

import entity.Entity;

import java.awt.*;

public interface I_Particles {

    void generateParticle(Entity generator, Entity target);
    Color getParticleColor();
    int getParticleSize();
    int getParticleSpeed();
    int getParticleMaxLife();
}
