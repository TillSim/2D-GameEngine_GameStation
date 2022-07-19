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

    public static final int PLAYER_START_X = TILE_SIZE * 50;
    public static final int PLAYER_START_Y = TILE_SIZE * 50;

    private final Thread GAME_THREAD;
    private final GamePanel PANEL;

    public static MapGenerator map;
    public ArrayList<Entity> entities = new ArrayList<>();


    private Core() {
        this.GAME_THREAD = new Thread(this);
        this.PANEL = GamePanel.getInstance();
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
        long currentTime = System.nanoTime();
        double tickRate = 1e+9 / Core.FPS;
        double nextTick = currentTime + tickRate;

        while (GAME_THREAD != null) {
            update();
            PANEL.repaint();
            try {
                Thread.sleep((long) ((nextTick - System.nanoTime()) / 1e+6));
                nextTick += tickRate;
            } catch (InterruptedException e) {
                Logger.toFile("game thread interrupted");
                throw new RuntimeException(e);
            }
        }
    }

    public void startGameThread() {
        GAME_THREAD.start();
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


}
