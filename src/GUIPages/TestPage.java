package GUIPages;

import javax.swing.*;

public class TestPage extends MyPanelBase {
    public TestPage(JPanel mainPanel, JFrame mainFrame) throws Exception {
        super(mainPanel, mainFrame);
    }

    @Override
    protected void addItems() {
        add(new JLabel("On Test page"));
    }
}
