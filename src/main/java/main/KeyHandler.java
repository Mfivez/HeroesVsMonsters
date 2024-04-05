package main;


import enums.E_GameState;
import enums.E_Main_Class;
import enums.E_Sound;
import enums.E_species;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, rightPressed, leftPressed
                  ,enterPressed, shotKeyPressed;
    // region DEBUG
    boolean showDebugText = false;
    // endregion

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gp.gameState == E_GameState.TITLE) {titleState(code);} // TITLE
        else if (gp.gameState == E_GameState.PLAY) {playState(code);} // PLAY
        else if (gp.gameState == E_GameState.PAUSE) {pauseState(code);} // PAUSE
        else if (gp.gameState == E_GameState.DIALOGUE) {dialogueState(code);} // DIALOGUE
        else if (gp.gameState == E_GameState.CHARACTER) {characterState(code);} // CHARACTER
    }
    public void titleState(int code) {
        int commandNum = gp.ui.TitleState().CommandNum();

        if (gp.ui.gameState == E_GameState.TITLE) {
            if (code == KeyEvent.VK_Z) {
                if (commandNum  <= 0) {
                    gp.ui.TitleState().setCommandNum(2);
                }
                else gp.ui.TitleState().setCommandNum(commandNum-1);
            }
            if (code == KeyEvent.VK_S) {
                if(commandNum  >= 2){
                    gp.ui.TitleState().setCommandNum(0);
                }
                else gp.ui.TitleState().setCommandNum(commandNum+1);
            }
            if (code == KeyEvent.VK_ENTER) {
                if(commandNum  == 0) {//NEW GAME
                    gp.ui.gameState = E_GameState.TITLE2;
                    //gp.playMusic(0);
                }
                if(commandNum  == 1) {//LOAD GAME
                    //LATER
                }
                if(commandNum == 2) {//QUIT
                    System.exit(0);
                }
            }
        } else if (gp.ui.gameState == E_GameState.TITLE2) {
            if (code == KeyEvent.VK_Z) {
                if (commandNum  <= 0) {
                    gp.ui.TitleState().setCommandNum(3);
                }
                else gp.ui.TitleState().setCommandNum(commandNum-1);
            }
            if (code == KeyEvent.VK_S) {
                if(commandNum  >= 3){
                    gp.ui.TitleState().setCommandNum(0);
                }
                else gp.ui.TitleState().setCommandNum(commandNum+1);
            }
            if (code == KeyEvent.VK_ENTER) {
                if(commandNum  == 0) {
                    gp.player.mainClass = E_Main_Class.GUERRIER;
                    gp.player.GetClassAttributs();
                    gp.ui.gameState = E_GameState.TITLE3;
                }
                if(commandNum  == 1) {
                    gp.player.mainClass = E_Main_Class.LANCE_PIERRE;
                    gp.player.GetClassAttributs();
                    gp.ui.gameState = E_GameState.TITLE3;
                }
                if(commandNum  == 2) {
                    gp.player.mainClass = E_Main_Class.SORCIER;
                    gp.player.GetClassAttributs();
                    gp.ui.gameState = E_GameState.TITLE3;
                }
                if(commandNum  == 3) {
                    gp.ui.gameState = E_GameState.TITLE;
                }
            }
        }
        else if (gp.ui.gameState == E_GameState.TITLE3) {
            if (code == KeyEvent.VK_Z) {
                if (commandNum  <= 0) {
                    gp.ui.TitleState().setCommandNum(2);
                }
                else gp.ui.TitleState().setCommandNum(commandNum-1);
            }
            if (code == KeyEvent.VK_S) {
                if(commandNum  >= 2){
                    gp.ui.TitleState().setCommandNum(0);
                }
                else gp.ui.TitleState().setCommandNum(commandNum+1);
            }
            if (code == KeyEvent.VK_ENTER) {
                if(commandNum  == 0) {
                    gp.player.specie = E_species.DWARF;
                    gp.player.setDefaultValues();
                    gp.gameState = E_GameState.PLAY;
                    gp.playMusic(E_Sound.BLUEBOYADVENTURE);
                }
                if(commandNum  == 1) {
                    gp.player.specie = E_species.HUMAN;
                    gp.player.setDefaultValues();
                    gp.gameState = E_GameState.PLAY;
                    gp.playMusic(E_Sound.BLUEBOYADVENTURE);
                }
                if(commandNum == 2) {
                    gp.ui.gameState = E_GameState.TITLE2;
                }
            }
        }

    }
    public void playState(int code) {

        if(code == KeyEvent.VK_Z) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_Q) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P) {
            gp.gameState = E_GameState.PAUSE;
        }
        if(code == KeyEvent.VK_C) {
            gp.gameState = E_GameState.CHARACTER;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(code == KeyEvent.VK_F && gp.player.mainClass != E_Main_Class.GUERRIER) {
            shotKeyPressed = true;
        }
        // region DEBUG
        if (code == KeyEvent.VK_T) {
            showDebugText = !showDebugText;
        }
        if (code == KeyEvent.VK_F3) {
            gp.tileM.loadMap("/Maps/MAP.txt");
            }
        // endregion

    }
    public void pauseState(int code) {
        if(code == KeyEvent.VK_P) {
            gp.gameState = E_GameState.PLAY;
        }
    }
    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = E_GameState.PLAY;
        }
    }
    public void characterState(int code) {
        if(code == KeyEvent.VK_C) {
            gp.gameState = E_GameState.PLAY;
        }
        if(code == KeyEvent.VK_Z) {
            if(gp.ui.CharacterState().slotRow !=0) {
                gp.ui.CharacterState().slotRow--;
                gp.playSE(E_Sound.CURSOR);
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.CharacterState().slotRow !=3) {
                gp.ui.CharacterState().slotRow++;
                gp.playSE(E_Sound.CURSOR);
            }
        }
        if(code == KeyEvent.VK_Q) {
            if(gp.ui.CharacterState().slotCol !=0) {
                gp.ui.CharacterState().slotCol--;
                gp.playSE(E_Sound.CURSOR);
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.CharacterState().slotCol !=4) {
            gp.ui.CharacterState().slotCol++;
            gp.playSE(E_Sound.CURSOR);
            }
        }
        if(code == KeyEvent.VK_ENTER) { gp.player.selectItem(); }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_Z) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_Q) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
    }
}
