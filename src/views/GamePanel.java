package views;


import engine.*;
import models.entities.Entity;
import utilities.Logger;

import javax.swing.*;
import java.awt.*;



/**
 *The game panel functions as direct in-/output for the user.
 */
public class GamePanel extends JPanel {

    private static final GamePanel SINGLETON = new GamePanel();

    private final InputHandler keyListener;


    private GamePanel() {
        this.setPreferredSize(new Dimension(Core.PANEL_WIDTH,Core.PANEL_HEIGHT));
        this.setBackground(Color.lightGray);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.keyListener = InputHandler.getInstance();

        this.addKeyListener(keyListener);

        Logger.toFile("panel loaded");
    }

    public static GamePanel getInstance() {
        return SINGLETON;
    }


    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

                    //DEBUG
                    long debugStart = 0;
                    if (keyListener.pressedF12) debugStart = System.nanoTime();

        Core.map.draw(graphics2D);

        for (Entity entity : Core.getInstance().entities) entity.draw(graphics2D);

                    //DEBUG
                    if (keyListener.pressedF12) {
                    long debugEnd = System.nanoTime();
                    long debugTime = debugEnd - debugStart;
                    graphics2D.setColor(Color.MAGENTA);
                    graphics2D.setFont(new Font("Dialog",Font.BOLD,35));
                    graphics2D.drawString("DRAW TIME (ms): " + debugTime / 1e+6, 5, 30);
                    }

        graphics2D.dispose();

    }


}

