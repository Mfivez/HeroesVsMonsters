package ui;


import enums.E_GameState;
import main.GamePanel;
import ui.ui_state.*;
import ui.ui_tools.UI_Tools;

import java.awt.*;

public class UI {
    private UI_Tools tools;

    public E_GameState gameState = E_GameState.TITLE;
    private UI_CharacterState character;
    private UI_DialogueState dialogue;
    private UI_PauseState pause;
    private UI_PlayState play;
    private UI_TitleState title;
    private GamePanel gp;
    private Graphics2D g2;
    Font arial_40, arial_80B;


    public UI(GamePanel gp, UI_Tools tools) {
        this.gp = gp;
        this.tools = tools;
        title = new UI_TitleState(gp, tools);
        play = new UI_PlayState(gp, tools);
        pause = new UI_PauseState(gp, tools);
        dialogue = new UI_DialogueState(gp, tools);
        character = new UI_CharacterState(gp, tools);
    }


    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        switch (gp.gameState) {
            case TITLE -> title.draw(g2, gameState);
            case PLAY -> play.draw(g2);
            case PAUSE -> pause.draw(g2);
            case DIALOGUE -> dialogue.draw(g2);
            case CHARACTER -> character.draw(g2);
        }

    }

    public E_GameState GameState() {
        return gameState;
    }

    public void setGameState(E_GameState gameState) {
        this.gameState = gameState;
    }


    public UI_TitleState TitleState() {
        return title;
    }

    public UI_PlayState PlayState() {
        return play;
    }

    public UI_PauseState PauseState() {
        return pause;
    }

    public UI_DialogueState DialogueState() {
        return dialogue;
    }

    public UI_CharacterState CharacterState() {
        return character;
    }
}
