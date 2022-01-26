package ldts.control.States;


import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import ldts.control.Controller;
import ldts.view.*;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class MenuState extends State {
    private MenuView menuView;
    private PlayerView playerView;
    private BackgroundView backgroundView;
    private CoinView coinView;
    private LaserView laserView;
    private boolean keepRunning;

    public MenuState(PlayerView playerView) {
        this.playerView = playerView;
        this.backgroundView = new BackgroundView("#595959", "#57AAF8", ' ', ' ', 1);
        this.coinView = new CoinView("#57AAF8", "#DEAC4C", "#");
        this.laserView = new LaserView("#fffb54", ' ');
        this.menuView = new MenuView();
        keepRunning = false;
    }

    public void setBackgroundView(BackgroundView backgroundView) {
        this.backgroundView = backgroundView;
    }

    public void setCoinView(CoinView coinView) {
        this.coinView = coinView;
    }

    public void setLaserView(LaserView laserView) {
        this.laserView = laserView;
    }

    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }

    public void step() {
        try {
            keepRunning = false;
            menuView.draw(playerView, backgroundView, laserView, coinView);
            this.input(View.getScreen().readInput());
        } catch (IOException | URISyntaxException | InterruptedException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public boolean isKeepRunning() {
        return keepRunning;
    }

    public void input(KeyStroke input) throws IOException, URISyntaxException, InterruptedException, FontFormatException {
        if (input.getKeyType() == KeyType.ArrowUp) {
            menuView.moveSelected(-1);
        } else if (input.getKeyType() == KeyType.ArrowDown) {
            menuView.moveSelected(1);
        }
        else if (input.getKeyType() == KeyType.Enter) {
            keepRunning = true;
            if (menuView.getSelected() == 0) {
                keepRunning = false;
                Controller.getInstance().run();
            }
            else if (menuView.getSelected() == 1) {
                do {
                    Controller.getInstance().runInstructions();
                }while(View.getScreen().readInput().getKeyType() != KeyType.Enter);
                keepRunning = false;
            }
            else if (menuView.getSelected() == 2)
                System.exit(0);
        }
        menuView.draw(playerView, backgroundView, laserView, coinView);
    }
}