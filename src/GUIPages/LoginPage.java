package GUIPages;

import utils.ArrayHelper;
import utils.CartHandler;
import utils.FileReader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPage extends MyPanelBase {
    private final CartHandler handler;
    public LoginPage(JPanel mainPanel, CartHandler handler, JFrame mainFrame) throws Exception {
        super(mainPanel, mainFrame);
        this.handler = handler;
    }

    @Override
    protected void addItems() throws Exception {
        var customers = FileReader.readCustomersFromFile("data/customers.items");

        var innerFrame = new JPanel(new FlowLayout(FlowLayout.CENTER));

        innerFrame.setBorder(new LineBorder(Color.BLACK, 2));

        innerFrame.setPreferredSize(new Dimension(300, 100));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0; // Set weighty to 1 for vertical centering
        gbc.anchor = GridBagConstraints.CENTER;

        var textInput = new JTextField();
        textInput.setColumns(10);
        textInput.setText("0");

        var prompt = new Label("Enter CustomerID:");
        prompt.setSize(new Dimension(100, 50));

        var button = new JButton("submit");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var item = textInput.getText();
                int customerID = 0;

                try{
                    var i = Integer.parseInt(item);
                    customerID = i;
                }catch (Exception ex){
                    showErrorMessage(item + " is not a valid customer ID");
                    return;
                }

                int finalCustomerID = customerID;
                if(ArrayHelper.Find(customers, x -> { return x.customerID == finalCustomerID; }).isEmpty()){
                    showErrorMessage("unable to find customer with the ID: " + customerID);
                    return;
                }
                handler.setCustomer(ArrayHelper.Find(customers, x -> { return x.customerID == finalCustomerID; }).get(0));
                showPanel(ShopPage.class);
            }
        });


        innerFrame.add(prompt);
        innerFrame.add(textInput);
        innerFrame.add(button);

        add(innerFrame);
    }
}
