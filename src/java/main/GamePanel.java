package main;

import entity.Player;
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
    public final int maxWorldCol = 64; // Max World Y - Tiles
    public final int maxWorldRow = 64; // Max World X - Tiles

    // Game Objects
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    public TileManager tm = new TileManager(this);
    public CollisionHandler cHandler = new CollisionHandler(this);

    //Player
    public Player player = new Player(this,keyH);

    public final int FPS = 30;
    public boolean debugOn = false;
    public GamePanel() {
        // Configure Panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void checkInputState() {
        debugOn = keyH.setDebug;
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
        player.update();
        checkInputState();
    }
    public void paintComponent(Graphics g) {
        // Draw the screen
        long drawStart = System.nanoTime();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        tm.draw(g2d);
        player.draw(g2d);

        if(debugOn) {
            //Debug Draw Time
            long passed = System.nanoTime() - drawStart;
            System.out.println("Draw Time: " + passed);
            g2d.drawString("Draw Time: " + passed, 10, 400);
        }
        g2d.dispose();
    }
}
