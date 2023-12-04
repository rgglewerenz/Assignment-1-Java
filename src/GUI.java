import GUIPages.*;
import utils.CartHandler;

import javax.swing.*;
import java.awt.*;

public class GUI {


    public static void main(String[] args) throws Exception {

        var cartHandler = new CartHandler();

        JFrame frame = new JFrame("Shopping Example");
        //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a main panel with CardLayout
        var layout = new CardLayout();

        JPanel mainPanel = new JPanel(layout);

        mainPanel.add(new MainPage(mainPanel, frame, cartHandler), MainPage.class.getName());
        mainPanel.add(new LoginPage(mainPanel, cartHandler, frame), LoginPage.class.getName());
        mainPanel.add(new ShopPage(mainPanel, cartHandler, frame), ShopPage.class.getName());
        mainPanel.add(new TestPage(mainPanel, frame), TestPage.class.getName());
        mainPanel.add(new CheckoutPage(mainPanel, frame, cartHandler), CheckoutPage.class.getName());

        // Add the main panel and control panel to the frame
        frame.add(mainPanel);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
