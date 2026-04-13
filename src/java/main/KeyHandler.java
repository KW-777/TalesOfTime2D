package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyHandler implements KeyListener {
    public boolean upPressed,downPressed,leftPressed,rightPressed,interactPressed = false;
    public boolean setDebug = false;
    GamePanel panel;
    public KeyHandler(GamePanel panel) {
        this.panel = panel;
    }
    public void resetInput() {
        upPressed = false;downPressed = false;leftPressed = false;rightPressed= false;interactPressed = false;
    }
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(panel.gameState == panel.playState) {
                if(code == KeyEvent.VK_W) {
                    upPressed = true;
                }if(code == KeyEvent.VK_S){
                    downPressed = true;
                }if(code == KeyEvent.VK_A) {
                    leftPressed = true;
                }if(code ==KeyEvent.VK_D) {
                    rightPressed = true;
                }if(code == KeyEvent.VK_E) {
                    interactPressed = true;
                }
                if(code == KeyEvent.VK_SHIFT) {
                    panel.player.sprintOn();
                }
                if(code == KeyEvent.VK_BACK_SLASH) {
                    panel.debugOn = !panel.debugOn;
                }if( code == KeyEvent.VK_ESCAPE) {
                    panel.gameState = panel.pauseState;
                }
            }else if(panel.gameState == panel.pauseState) {
            switch (code) {
                case KeyEvent.VK_ESCAPE -> {
                    panel.gameState = panel.playState;
                }
            }
            optionSelection(code,panel.ui.pauseOptions);
        }else if(panel.gameState == panel.titleState) {
            optionSelection(code,panel.ui.titleOptions);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(panel.gameState == panel.playState) {
            if(code == KeyEvent.VK_W) {
                upPressed = false;
            }if(code == KeyEvent.VK_S){
                downPressed = false;
            }if(code == KeyEvent.VK_A) {
                leftPressed = false;
            }if(code ==KeyEvent.VK_D) {
                rightPressed = false;
            }if(code == KeyEvent.VK_E) {
                interactPressed = false;
            }if(code == KeyEvent.VK_SHIFT) {
                panel.player.sprintOff();
            }
        }
    }
    private void optionSelection(int code, HashMap<Integer,Runnable> options) {
        switch(code) {
            case KeyEvent.VK_UP,KeyEvent.VK_W -> {
                panel.ui.optNum--;
                if(panel.ui.optNum < 0) {
                    panel.ui.optNum = options.size() - 1;
                }
            }
            case KeyEvent.VK_DOWN,KeyEvent.VK_S -> {
                panel.ui.optNum++;
                if (panel.ui.optNum == options.size()) {
                    panel.ui.optNum = 0;
                }
            }
            case KeyEvent.VK_ENTER -> {
                options.get(panel.ui.optNum).run();
            }
        }
    }
}
