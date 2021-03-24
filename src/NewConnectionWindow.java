import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ConnectionWindow allows to create new connection session. After slosing create new ConnectionSession
 * @see ConnectionSession
 * @author Damian Szymczyk
 * @version 1.0
 */
public class NewConnectionWindow extends Frame {
    /** Parent communicator application */
    P2PCommunicator communicator;
    /** Text filed that accepts host server address */
    TextField hostAddress;
    /** Text filed that accepts host server port */
    TextField hostPort;
    /** Button that allows to create new connection */
    Button newConnectionButton;

    /**
     * Constructs new connection window
      * @param communicator Parent communicator
     */
    public NewConnectionWindow(P2PCommunicator communicator) {
        super("New connection");
        this.communicator = communicator;

        this.setBackground(Color.gray);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeWindow();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
            }
        });

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Label addressLabel = new Label("Host address:");
        gbl.setConstraints(addressLabel, gbc);
        add(addressLabel);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 4;
        TextField addressTextField = new TextField();
        gbl.setConstraints(addressTextField, gbc);
        add(addressTextField);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        Label portLabel = new Label("Host port:");
        gbl.setConstraints(portLabel, gbc);
        add(portLabel);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 4;
        TextField portTextField = new TextField();
        gbl.setConstraints(portTextField, gbc);
        add(portTextField);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        newConnectionButton = new Button("New connection");
        newConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String addressTextFieldContent = addressTextField.getText();
                String portTextFieldContent = portTextField.getText();
                if(isNumeric(portTextFieldContent)){
                    communicator.newConnection(addressTextFieldContent, Integer.parseInt(portTextFieldContent));
                    closeWindow();
                }
            }
        });
        gbl.setConstraints(newConnectionButton, gbc);
        add(newConnectionButton);

        pack();
        setSize(300, 300);
        setVisible(true);
    }

    /** Closes NewConnectionWindow */
    void closeWindow() {
        this.dispose();
    }

    /**
     * Checks if provided stirng represents numeric value
     * @param strNum String to check
     * @return boolean value informing if provided string represents numeric(integer) value
     */
    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
