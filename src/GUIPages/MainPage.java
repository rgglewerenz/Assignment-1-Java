package GUIPages;

import utils.CartHandler;
import utils.FileHelper;
import utils.FileReader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MainPage extends MyPanelBase {



    CartHandler handler;

    public MainPage(JPanel mainPanel, JFrame mainFrame, CartHandler handler) throws Exception {
        super(mainPanel, mainFrame, MainPage.class);
        this.handler = handler;
    }



    @Override
    protected void addItems() throws Exception {
        var login = new JButton("Login");
        login.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showPanel(LoginPage.class);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        var loadFile = new JButton("Load");

        loadFile.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser
                JFileChooser fileChooser = new JFileChooser();

                // Set the file filter to allow only files with a ".txt" extension
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Invoice Files", "invoice");
                fileChooser.setFileFilter(filter);

                // Show the file chooser dialog
                int returnValue = fileChooser.showOpenDialog(mainFrame);

                if(returnValue != JFileChooser.APPROVE_OPTION){
                    return;
                }


                var file = fileChooser.getSelectedFile().getPath();

                if(!FileHelper.getFileExtension(file).equals("invoice")){
                    JOptionPane.showMessageDialog(mainFrame, "Invalid file type");
                    return;
                }


                try {
                    handler.setCart(
                            FileReader.readInvoiceFromFile(file,
                                FileReader.readProductsFromFile(FileHelper.PRODUCTS_PATH),
                                FileReader.readCustomersFromFile(FileHelper.CUSTOMERS_PATH))
                    );
                    handler.finalizeCart();
                    showPanel(CheckoutPage.class);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0; // Set weighty to 1 for vertical centering
        gbc.anchor = GridBagConstraints.CENTER;

        var buttonContainer = new JPanel(new GridLayout(1, 2));
        buttonContainer.add(login);
        buttonContainer.add(loadFile);

        add(buttonContainer, gbc);
        setPreferredSize(new Dimension(100, 100));
    }


}

