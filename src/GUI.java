import GUI_Pages.MainPage;
import GUI_Pages.TestPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public static void main(String[] args){
        JFrame frame = new JFrame("CardLayout Example");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a main panel with CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());

        mainPanel.add(new MainPage(mainPanel), MainPage.class.getName());
        mainPanel.add(new TestPage(mainPanel), TestPage.class.getName());


        // Add the main panel and control panel to the frame
        frame.add(mainPanel);

        frame.setVisible(true);
    }
}
