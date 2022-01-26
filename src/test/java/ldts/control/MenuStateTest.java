package ldts.control;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import ldts.control.States.MenuState;
import ldts.view.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class MenuStateTest {
    private MenuState menuState;
    private MenuView menuView;
    private Screen screen;
    private final PlayerView playerView = Mockito.mock(PlayerView.class);
    private final BackgroundView backgroundView = Mockito.mock(BackgroundView.class);
    private final CoinView coinView = Mockito.mock(CoinView.class);
    private final LaserView laserView = Mockito.mock(LaserView.class);

    @BeforeEach
    void setUp(){
        screen = Mockito.mock(Screen.class);
        Mockito.when(screen.getTerminalSize()).thenReturn(new TerminalSize(50, 30));
        View.setScreen(screen);
        View.setGraphics(Mockito.mock(TextGraphics.class));

        menuView = Mockito.mock(MenuView.class);
        menuState = new MenuState(playerView);
        menuState.setMenuView(menuView);
        menuState.setBackgroundView(backgroundView);
        menuState.setLaserView(laserView);
        menuState.setCoinView(coinView);
    }

    @Test
    void step() throws IOException {
        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowLeft));
        menuState.step();
        Mockito.verify(menuView, Mockito.times(2)).draw(playerView, backgroundView, laserView, coinView);
        Assertions.assertFalse(menuState.isKeepRunning());
    }

    @Test
    void inputArrowUp() throws IOException, URISyntaxException, InterruptedException, FontFormatException {
        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        menuState.input(new KeyStroke(KeyType.ArrowUp));
        Mockito.verify(menuView, Mockito.times(1)).moveSelected(-1);
    }

    @Test
    void inputArrowDown() throws IOException, URISyntaxException, InterruptedException, FontFormatException {
        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        menuState.input(new KeyStroke(KeyType.ArrowDown));
        Mockito.verify(menuView, Mockito.times(1)).moveSelected(1);
    }

    @Test
    void inputEnter() throws IOException, URISyntaxException, InterruptedException, FontFormatException {
        Controller.setSingleton(Mockito.mock(Controller.class));
        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        menuState.input(new KeyStroke(KeyType.Enter));
        Mockito.verify(menuView, Mockito.times(0)).moveSelected(Mockito.anyInt());
        Assertions.assertFalse(menuState.isKeepRunning());
    }
}