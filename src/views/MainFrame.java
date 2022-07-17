package views;


import engine.Core;
import utilities.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



/**
 * The main program frame, that supports the game panel.
 */
public class MainFrame extends JFrame {

    private static final MainFrame SINGLETON = new MainFrame();


    private MainFrame() {
        GamePanel panel = GamePanel.getInstance();

        this.setIconImage(loadLogo());
        this.setTitle(" GameStation " + Core.VERSION);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        this.pack();

        this.setVisible(true);

        Logger.toFile("frame built");
    }

    public static MainFrame getInstance() {
        return SINGLETON;
    }


    /**
     * loads logo as image from resources
     * @return BufferedImage
     */
    private BufferedImage loadLogo() {

        try {
            return ImageIO.read(new File("res/img/icons/logo.png"));
        } catch (IOException e) {
            Logger.toStream("failed to load frame logo");
        }

        return null;

    }

}
