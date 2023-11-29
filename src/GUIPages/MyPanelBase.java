package GUIPages;

import javax.swing.*;
import java.awt.*;

public abstract class MyPanelBase extends JPanel {

    protected JPanel mainPanel;

    protected JFrame mainFrame;

    public MyPanelBase(JPanel mainPanel, JFrame mainFrame) throws Exception {
        this.mainPanel = mainPanel;
        this.mainFrame = mainFrame;
        addItems();
    }

    protected abstract void addItems() throws Exception;

    protected void showPanel(Class<? extends MyPanelBase> type){
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, type.getName());
    }

    protected void showErrorMessage(String message){
        JOptionPane.showMessageDialog(
                mainFrame,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
