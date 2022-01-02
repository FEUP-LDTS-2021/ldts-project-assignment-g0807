package ldts;

import ldts.control.Controller;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, FontFormatException {
        Controller controller = new Controller();
        controller.run();
    }
}
