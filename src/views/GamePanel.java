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


    private GamePanel() {
        this.setPreferredSize(new Dimension(Core.PANEL_WIDTH,Core.PANEL_HEIGHT));
        this.setBackground(Color.lightGray);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.addKeyListener(InputHandler.getInstance());

        Logger.toFile("panel loaded");
    }

    public static GamePanel getInstance() {
        return SINGLETON;
    }


    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        Core.map.draw(graphics2D);

        for (Entity entity : Core.getInstance().entities) {
            entity.draw(graphics2D);
        }

        graphics2D.dispose();

    }


}

