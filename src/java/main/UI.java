package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static java.lang.Math.round;

public class UI {
    GamePanel panel;
    Font defaultFont;
    Font titleFont;
    Font subtitleFont;

    Color titleColor;
    Color subtitleColor;

    BufferedImage pauseBackground;
    public int optNum = 0;
    public HashMap<Integer,Runnable> pauseOptions = new HashMap<>();
    public HashMap<Integer,Runnable> titleOptions = new HashMap<>();
    public UI(GamePanel panel) {
        this.panel = panel;
        defaultFont = new Font("Agency FB", Font.PLAIN, 40);
        subtitleFont = new Font("Agency FB",Font.BOLD,40);
        titleFont = new Font("Yoster", Font.BOLD, 60);
        titleColor = new Color(45,95,175);
        subtitleColor = new Color(220,200,30);
        initResources();

    }
    public void draw(Graphics2D g2d) {
        g2d.setFont(defaultFont);
        if(panel.gameState == panel.titleState) {
            drawTitleScreen(g2d);
        }if(panel.gameState == panel.pauseState) {
            drawPauseScreen(g2d);
        }if(panel.gameState == panel.playState) {
            g2d.drawString(Main.VERSION,panel.screenWidth-round(1.25*panel.tileSize), panel.tileSize/2);
        }
    }

    public void initResources() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/yoster.ttf")));
            pauseBackground = ImageIO.read(getClass().getResourceAsStream("/ui/background.jpeg"));
        } catch (IOException|FontFormatException e) {
            System.err.println(e.getMessage());
        }
    }
    private void drawPauseScreen(Graphics2D g2d) {
        panel.keyH.resetInput();
        if(panel.player.isSprinting) {
            panel.player.sprintOff();
        }
        g2d.drawImage(pauseBackground, 0, 0, null);
        drawTitleText(g2d);
        g2d.setFont(subtitleFont);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Paused", centerX("Paused",g2d), panel.tileSize*4);
        g2d.setFont(defaultFont);
        g2d.setColor(Color.GRAY);
        drawMenuOption(g2d,0,pauseOptions,"Continue",6,this::pauseContinue);
        drawMenuOption(g2d,1,pauseOptions,"Save",7,this::pauseSave);
        drawMenuOption(g2d,2,pauseOptions,"Settings",8,this::settingsMenu);
        drawMenuOption(g2d,3,pauseOptions,"Exit",9,this::exitGame);
    }
    private void drawMenuOption(Graphics2D g2d,int opt, HashMap<Integer,Runnable> menu, String name,int yOffset,Runnable func) {
        if(!menu.containsKey(opt)) {
            menu.put(opt, func);
        }
        g2d.drawString(name, centerX(name,g2d), yOffset*panel.tileSize);
        if(panel.ui.optNum == opt) {
            g2d.drawString(">",centerX(name,g2d)-panel.tileSize/2,panel.tileSize*yOffset);
        }
    }
    private void drawTitleScreen(Graphics2D g2d) {
        panel.keyH.resetInput();
        g2d.drawImage(pauseBackground, 0, 0, null);
        drawTitleText(g2d);
        //Menu
        g2d.setFont(defaultFont);
        g2d.setColor(Color.LIGHT_GRAY);
        drawMenuOption(g2d,0,titleOptions,"Start Adventure",6,this::titleStart);
        drawMenuOption(g2d,1,titleOptions,"Load Game",7,this::titleLoad);
        drawMenuOption(g2d,2,titleOptions,"Settings",8,this::settingsMenu);
        drawMenuOption(g2d,3,titleOptions,"Credits",9,this::titleCredits);
        drawMenuOption(g2d,4,titleOptions,"Exit",10,this::exitGame);
    }
    private void drawTitleText(Graphics2D g2d) {
        g2d.setColor(titleColor);
        g2d.setFont(titleFont);
        g2d.drawString("Tales of Time:", centerX("Tales of Time", g2d), panel.tileSize*2);
        g2d.setFont(subtitleFont);
        g2d.setColor(subtitleColor);
        g2d.drawString("Elementals", centerX("Elementals", g2d), round(panel.tileSize*2.75));
    }
    public int centerX(String text,Graphics2D g2d) {
        int length = (int)g2d.getFontMetrics().getStringBounds(text,g2d).getWidth();
        int x = panel.screenWidth/2 - length/2;
        return x;
    }
    private void pauseContinue() {
        panel.gameState = panel.playState;
    }
    private void pauseSave() {} //To-Do
    private void titleStart() {
        panel.gameState = panel.playState;
    }
    private void titleLoad() {}
    private void titleCredits() {}
    private void settingsMenu() {} //To-Do
    private void exitGame() {
        if(panel.gameState == panel.titleState) {
        System.exit(0);
        }else{
            panel.gameState = panel.titleState;
        }
    }
}
