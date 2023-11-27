package GUI_Pages;

import javax.swing.*;

public class TestPage extends MyPanelBase {
    public TestPage(JPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    protected void addItems() {
        add(new JLabel("On Test page"));
    }
}
