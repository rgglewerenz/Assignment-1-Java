import DataObjects.Cart;
import GUIPages.*;
import utils.CartHandler;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public static void main(String[] args) throws Exception {

        var cartHandler = new CartHandler();

        JFrame frame = new JFrame("CardLayout Example");
        //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Create a main panel with CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());

        mainPanel.add(new MainPage(mainPanel, frame), MainPage.class.getName());
        mainPanel.add(new LoginPage(mainPanel, cartHandler, frame), LoginPage.class.getName());
        mainPanel.add(new ShopPage(mainPanel, cartHandler, frame), ShopPage.class.getName());
        mainPanel.add(new TestPage(mainPanel, frame), TestPage.class.getName());
        mainPanel.add(new CheckoutPage(mainPanel, frame), CheckoutPage.class.getName());


        // Add the main panel and control panel to the frame
        frame.add(mainPanel);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
