package main;

import entity.Player;
import object.OBJ_Base;
import object.obj_Chest;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    final int originalTileSize = 16; //16x16 Tiles
    final int scale = 4;
    public final int tileSize = originalTileSize * scale; // Default: 64px
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // Default: 1024px
    public final int screenHeight = tileSize * maxScreenRow; // Default: 768px

    //World Settings
    public final int maxWorldCol = 96; // Max World Y - Tiles
    public final int maxWorldRow = 96; // Max World X - Tiles

    // Game Objects
    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    public TileManager tm = new TileManager(this);
    public CollisionHandler cHandler = new CollisionHandler(this);
    public OBJ_Base[] objects = new  OBJ_Base[16];
    public EventHandler evH = new EventHandler(this);
    public UI ui;
    //Player
    public Player player = new Player(this,keyH);

    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;

    public final int FPS = 30;
    public boolean debugOn = false;
    public GamePanel() {
        // Configure Panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        ui = new UI(this);
        setupGame();
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void setupGame() {
        setObjects();
        gameState = titleState;
    }
    public void setObjects() {
        objects[0] = new obj_Chest();
        objects[0].worldX = 59*tileSize;
        objects[0].worldY = 31*tileSize;
    }
    public void drawObjects(Graphics2D g2d) {
        for(OBJ_Base obj : objects) {
            if(obj != null) {
            obj.draw(g2d,this);
        }}
    }
    @Override
    public void run() {
        // FPS limiter, Main Game loop
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            timer += (currentTime - lastTime);
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;
            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                if(debugOn) {
                    System.out.println("FPS: " + drawCount + "Expected: " + FPS);
                }
                timer = 0;
                drawCount = 0;
            }
        }
    }
    public void update() {
        // Update Object States
        if(gameState == playState) {
            player.update();
        }
        if(gameState == pauseState) {}
        if(gameState == titleState) {}
    }
    public void paintComponent(Graphics g) {
        // Draw the screen
        long drawStart = System.nanoTime();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        if(gameState == playState) {
            tm.draw(g2d);
            drawObjects(g2d);
            player.draw(g2d);
        }
            ui.draw(g2d);
        if(debugOn) {
            //Debug Draw Time
            long passed = System.nanoTime() - drawStart;
            System.out.println("Draw Time: " + passed);
            g2d.drawString("Draw Time: " + passed, 10, 400);
        }
        g2d.dispose();
    }
}
