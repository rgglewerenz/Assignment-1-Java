package GUIPages;

import DataObjects.Cart;
import utils.CartHandler;
import utils.FileWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;



public class CheckoutPage extends MyPanelBase{
    private final CartHandler handler;
    public CheckoutPage(JPanel mainPanel, JFrame mainFrame, CartHandler handler) throws Exception {
        super(mainPanel, mainFrame, CheckoutPage.class);
        this.handler = handler;
        handler.onFinalizedCart(this::renderFinalizedCart);
    }

    @Override
    protected void addItems() throws Exception {

    }


    private int renderFinalizedCart(Cart cart){
        //Render Products
        {
            var productCounts = cart.getProductCounts();


            Object[][] items = new Object[productCounts.size() + 1][4];

            System.out.println(productCounts.size());

            var keys = productCounts.keys();
            int i =0;
            while(keys.hasMoreElements()){
                var product = keys.nextElement();
                items[i] = new Object[]{
                        product.name, String.format("$%.2f", product.price), productCounts.get(product), String.format("$%.2f", cart.calcProductTotal(product, productCounts))
                };
                i++;
            }
            
            items[productCounts.size()] = new Object[]{
              "Total", null, null, String.format("$%.2f" , cart.calcTotal(false))
            };

            String[] columnNames = {"Name", "Price", "Count", "Product Total"};

            // Create a table model
            DefaultTableModel model = new DefaultTableModel(items, columnNames);



            // Create a JTable with the model
            JTable table = new JTable(model);

            // Create a scroll pane and add the table to it
            JScrollPane scrollPane = new JScrollPane(table);


            add(scrollPane);
        }

        // Render payment
        {
            JPanel paymentPanel = new JPanel(new FlowLayout());
            
            JButton paymentButton = new JButton("Pay");

            paymentButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new PayInFullDialog(mainFrame, true, (Boolean payInFull) -> {
                        JOptionPane.showMessageDialog(mainFrame, "Your total will be " + String.format("$%.2f", cart.calcTotal(payInFull)));
                        mainFrame.show(false);
                        System.exit(0);
                        return 0;
                    });
                }
            });

            paymentPanel.add(paymentButton);
            add(paymentPanel);
        }

        // Save for later button
        {
            JPanel savePanel = new JPanel(new FlowLayout());

            JButton saveButton = new JButton("Save cart");

            saveButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int shouldSave = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to save the invoice?");
                        if(shouldSave != JOptionPane.YES_NO_OPTION){
                            return;
                        }
                        FileWriter.writeTransactionToFile(cart, "test.invoice");
                        mainFrame.show(false);
                        System.exit(0);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            savePanel.add(saveButton);
            add(savePanel);
        }

        return 0;
    }
}

class PayInFullDialog extends JDialog {

    PayInFullDialog(Frame parentFrame, boolean isModal, Function<Boolean, Integer> onChoice){
        super(parentFrame, "Pay in Full?", isModal);


        JLabel label = new JLabel("Do you want to pay the balance in full?");
        JButton yesButton = new JButton("yes");
        JButton noButton = new JButton("no");

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
                onChoice.apply(true);
            }
        });

        noButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                onChoice.apply(false);
            }
        });

        JPanel buttonContainer = new JPanel();

        buttonContainer.add(yesButton);
        buttonContainer.add(noButton);


        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(label);
        panel.add(buttonContainer);

        getContentPane().add(panel);
        setSize(300, 150);
        setLocationRelativeTo(parentFrame);
        setVisible(true);

    }
}
