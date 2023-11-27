package GUI_Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends MyPanelBase {

    public MainPage(JPanel mainPanel) {
        super(mainPanel);
    }



    @Override
    protected void addItems() {
        JButton switchButton = new JButton("Switch Panel");
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(TestPage.class);
            }
        });
        add(switchButton);
    }
}
