package engine;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



/**
 * InputHandler listens for key input and translates to game events.
 */
public class InputHandler implements KeyListener {

    private static final InputHandler SINGLETON = new InputHandler();

    public boolean pressedUP,pressedDOWN,pressedLEFT,pressedRIGHT, pressedSHIFT, pressedCTRL;

                //DEBUG
                public boolean pressedF12;


    private InputHandler() {}

    public static InputHandler getInstance() {
        return SINGLETON;
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        //HOLD
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) pressedUP = true;
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) pressedDOWN = true;
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) pressedLEFT = true;
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) pressedRIGHT = true;

        //TOGGLE
        if (keyCode == KeyEvent.VK_SHIFT) pressedSHIFT = !pressedSHIFT && !pressedCTRL;
        if (keyCode == KeyEvent.VK_CONTROL) pressedCTRL = !pressedCTRL && !pressedSHIFT;

                    //DEBUG
                    if (keyCode == KeyEvent.VK_F12) pressedF12 = !pressedF12;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) pressedUP = false;
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) pressedDOWN = false;
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) pressedLEFT = false;
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) pressedRIGHT = false;

    }


}
