package GUIPages;

import DataObjects.Product;
import utils.CartHandler;
import utils.FileReader;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ShopPage extends MyPanelBase {
    private ArrayList<Product> products;

    private final CartHandler handler;

    public ShopPage(JPanel mainPanel, CartHandler handler, JFrame mainFrame) throws Exception {
        super(mainPanel, mainFrame);
        this.handler = handler;
    }

    private void addProducts(){


        var gridContainer = new JPanel();

        gridContainer.setLayout(new GridLayout(products.size() + 1, 1));

        {
            var row = new JPanel();
            row.setLayout(new GridLayout(1, 4));
            row.add(new JLabel("Name"));
            row.add(new JLabel("Price"));
            row.add(new JLabel("Description"));
            row.add(new JPanel());
            row.setPreferredSize(new Dimension(100, 100));
            gridContainer.add(row);
        }
        for(var product : products){
            var row = new JPanel();
            row.setLayout(new GridLayout(1, 4));
            row.add(new JLabel(product.name));
            row.add(new JLabel(String.format("$%.2f", product.price)));
            row.add(new JLabel(product.description));
            var button = new JButton("Add to cart");

            button.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        handler.getCart().addProduct(product, 1);
                        handler.getCart().printCart(0.1, true);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            row.add(button);
            row.setPreferredSize(new Dimension(100, 100));
            gridContainer.add(row);
        }


        JScrollPane scrollPane = new JScrollPane(gridContainer);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        add(scrollPane);
    }

    @Override
    protected void addItems() throws Exception {
        products = FileReader.readProductsFromFile("data/products.items");
        addProducts();
    }

}


class MyDialog extends JDialog {
    MyDialog(Frame parentFrame, boolean isModal){
        super(parentFrame, "My Dialog", isModal);


        JLabel label = new JLabel("This is a custom modal dialog");
        JButton closeButton = new JButton("Close Dialog");

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setSize(200, 150);
        setLocationRelativeTo(parentFrame);
        setVisible(true);

    }
}

