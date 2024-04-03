package myenum;

public enum E_MagicalNumber {
      MONSTER_INVINCIBILITY_DURATION(40)
    , PLAYER_INVINCIBILITY_DURATION(60)
    , PLAYER_TIME_TO_REGEN(120)
    , DYING_DURATION(40)
    , RESET_COUNTER(0)
    , RESET_DIALOGUE_INDEX(0)
    , MOB_TIME_TO_SPAWN(120)
    , SHOT_AVAILABLE_DURATION(30)
    , SPRITE_TIME_TO_CHANGE(12)
    , HP_BAR_TIME_TO_DISAPPEAR(600)
    , ATTACKING_SPRITE_TIME_TO_CHANGE1(5)
    , ATTACKING_SPRITE_TIME_TO_CHANGE2(25)
    , LIST_OF_1_SPRITE(1)
    , LIST_OF_2_SPRITES(2)
    , LIST_OF_8_SPRITES(8)
    , LIST_OF_32_SPRITES(32)
    , NOTHING(999)
    , MAX_MONSTER_ON_MAP(20)

    ;


    private final int value;

    E_MagicalNumber(int value) {
        this.value = value;
    }

    public int Value() {
        return value;
    }
}

