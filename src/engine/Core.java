package engine;


import models.entities.Entity;
import models.entities.Player;
import utilities.Logger;
import views.GamePanel;

import java.util.ArrayList;



/**
 * The core controls the game thread and acts as interface between models and panel.
 *
 */
public class Core implements Runnable{


    private static final Core SINGLETON = new Core();

    public static final String VERSION = "v0.2.0";

    public static final int TILE_SIZE = 64;
    public final static int PANEL_COLUMNS = 16;
    public final static int PANEL_ROWS = 9;
    public static final int PANEL_WIDTH = TILE_SIZE * PANEL_COLUMNS;
    public static final int PANEL_HEIGHT = TILE_SIZE * PANEL_ROWS;
    public static final int FPS = 60;

    public static final int PLAYER_START_TILE_X = TILE_SIZE * 50;
    public static final int PLAYER_START_TILE_Y = TILE_SIZE * 50;

    private final Thread gameThread;
    private final GamePanel panel;

    public static MapGenerator map;
    public ArrayList<Entity> entities = new ArrayList<>();


    private Core() {
        Logger.toFile(getInitialParams());
        this.gameThread = new Thread(this);
        this.panel = GamePanel.getInstance();
        map = MapGenerator.getInstance();
        this.entities.add(Player.getInstance());
        Logger.toFile("core loaded");
    }

    public static Core getInstance() {return SINGLETON;}


    /**
     * updates entities and repaints game panel at specified FPS
     */
    @Override
    public void run() {
        long tickRate = 1000 / FPS;

        while (gameThread != null) {
            update();
            panel.repaint();
            try {
                Thread.sleep(tickRate);
            } catch (InterruptedException e) {
                Logger.toFile("game thread interrupted");
                throw new RuntimeException(e);
            }
        }
    }

    public void startGameThread() {
        gameThread.start();
        Logger.toFile("game thread started");
    }


    /**
     * calls the update-method for all game entities
     */
    private void update() {
        for (Entity entity : entities) {
            entity.update();
        }
    }

    /**
     * creates String from initial parameters
     * @return String
     */
    private String getInitialParams() {
        return "Core-Version: " + VERSION +
                "\n" +
                "Aspect Ration: " + PANEL_COLUMNS + ":" + PANEL_ROWS +
                "\n" +
                "Resolution: " + PANEL_WIDTH + "x" + PANEL_HEIGHT + " (" + TILE_SIZE + "x" + TILE_SIZE + " Tiles)" +
                "\n" +
                "FPS: " + FPS;
    }


}
