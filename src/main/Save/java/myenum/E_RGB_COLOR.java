package myenum;


import java.awt.*;

public enum E_RGB_COLOR {
      RED_OF_MONSTER_HP_BARRE(225,0,30)
    , GRAY_OF_MONSTER_HP_BARRE(35, 35, 35)
    , RED_OF_FIRE_BALL(240,50,0)
    , BROWN_OF_ROCK(40,50,30)
    , BROWN_OF_DRYTREE(65,50,30)
    , BLUE_OF_MANA_BARRE(148,210,232)
    ;



    private final int red;
    private final int green;
    private final int blue;

    E_RGB_COLOR(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color getColor() {
        return new Color(red, green, blue);
    }
}

