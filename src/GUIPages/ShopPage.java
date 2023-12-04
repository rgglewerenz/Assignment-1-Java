package GUIPages;

import DataObjects.Product;
import utils.CartHandler;
import utils.FileHelper;
import utils.FileReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ShopPage extends MyPanelBase {
    private ArrayList<Product> products;

    private final CartHandler handler;

    public ShopPage(JPanel mainPanel, CartHandler handler, JFrame mainFrame) throws Exception {
        super(mainPanel, mainFrame, ShopPage.class);
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
            gridContainer.add(row);
        }
        for(var product : products){
            var row = new JPanel();
            row.setLayout(new GridLayout(1, 4));

            row.add(new JLabel(product.name));
            row.add(new JLabel(String.format("$%.2f", product.price)));
            row.add(new JLabel(product.description));

            var container = new JPanel(new GridLayout(1, 3));

            var label = new JLabel("0");
            var buttonPlus = new JButton("+");
            var buttonMinus = new JButton("-");

            buttonMinus.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        handler.getCart().removeProduct(product, 1);
                        var productCount = handler.getCart().getProductCounts().get(product);
                        label.setText(productCount == null ? "0" : productCount.toString());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            buttonPlus.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        handler.getCart().addProduct(product, 1);
                        var productCount = handler.getCart().getProductCounts().get(product);
                        label.setText(productCount == null ? "0" : productCount.toString());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });


            container.add(buttonMinus);
            container.add(label);
            container.add(buttonPlus);

            row.add(container);

            gridContainer.add(row);
        }


        JScrollPane scrollPane = new JScrollPane(gridContainer);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JPanel checkoutButtonContainer = new JPanel();

        checkoutButtonContainer.setPreferredSize(new Dimension(500, 100));

        var checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showPanel(CheckoutPage.class);
                    handler.finalizeCart();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        checkoutButtonContainer.add(checkoutButton);

        add(checkoutButtonContainer);
        add(scrollPane);
    }

    @Override
    protected void addItems() throws Exception {
        products = FileReader.readProductsFromFile(FileHelper.PRODUCTS_PATH);
        addProducts();
    }

}




