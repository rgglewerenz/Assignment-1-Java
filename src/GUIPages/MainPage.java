package GUIPages;
import DataObjects.Product;
import utils.FileReader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MainPage extends MyPanelBase {


    public MainPage(JPanel mainPanel, JFrame mainFrame) throws Exception {
        super(mainPanel, mainFrame);
    }



    @Override
    protected void addItems() throws Exception {
        var button = new JButton("Login");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(LoginPage.class);
            }
        });
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0; // Set weighty to 1 for vertical centering
        gbc.anchor = GridBagConstraints.CENTER;
        add(button, gbc);
        setPreferredSize(new Dimension(100, 100));
    }


}

