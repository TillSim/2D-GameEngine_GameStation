import engine.Core;
import utilities.Logger;
import views.MainFrame;



//TODO implement config interface for custom settings
public class Main {


    public static void main(String[] args) {

        Logger.clearLog();

        Core gameCore = Core.getInstance();

        MainFrame gameFrame = MainFrame.getInstance();

        gameCore.startGameThread();

    }


}
