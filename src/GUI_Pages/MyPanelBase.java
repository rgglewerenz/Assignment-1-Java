package GUI_Pages;

import javax.swing.*;
import java.awt.*;

public abstract class MyPanelBase extends JPanel {

    protected JPanel mainPanel;

    public MyPanelBase(JPanel mainPanel){
        this.mainPanel = mainPanel;
        addItems();
    }

    protected abstract void addItems();

    protected void showPanel(Class<? extends MyPanelBase> type){
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, type.getName());
    }
}
