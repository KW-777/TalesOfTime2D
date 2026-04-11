package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyHandler implements KeyListener {
    public boolean upPressed,downPressed,leftPressed,rightPressed = false;
    public boolean setDebug = false;
    GamePanel panel;
    public KeyHandler(GamePanel panel) {
        this.panel = panel;
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(panel.gameState == panel.playState) {
            switch(code) {
                case (KeyEvent.VK_W) -> {
                    upPressed = true;
                }
                case KeyEvent.VK_S -> {
                    downPressed = true;
                }
                case KeyEvent.VK_A -> {
                    leftPressed = true;
                }
                case KeyEvent.VK_D -> {
                    rightPressed = true;
                }
                case KeyEvent.VK_BRACELEFT -> {
                    panel.debugOn = !panel.debugOn;
                }
                case KeyEvent.VK_ESCAPE -> {
                    panel.gameState = panel.pauseState;
                }
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
            switch(code) {
                case KeyEvent.VK_W:
                    upPressed = false;
                    break;
                case KeyEvent.VK_S:
                    downPressed = false;
                break;
                case KeyEvent.VK_A:
                    leftPressed = false;
                    break;
                case KeyEvent.VK_D:
                    rightPressed = false;
                    break;
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
