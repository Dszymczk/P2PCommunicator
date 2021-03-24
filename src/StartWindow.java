import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** Public class representing start window.
 *  Allows to provide port number for the server that is being started after pressing "Start sever" button.
 *  After pressing "Start server" causes serverWindow open.
 *  Provided port have to be numeric value.
 * @author Damian Szymczyk
 * @version 1.0
 */
public class StartWindow extends Frame {
    /** Start server button - start server if @see portTextField contains numeric value */
    Button startServerButton;
    /** Text field that accepts port number for server - has to be numeric */
    TextField portTextField;
    /** Parent communicator application */
    P2PCommunicator communicator;

    /** Create Start window.
     * @param  communicator parent communicator
     */
    public StartWindow(P2PCommunicator communicator) {
        super("Start window");

        this.setBackground(Color.gray);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
            }
        });

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);

        Label label = new Label("Please provide port number");
        gbc.gridy = 0;
        gbc.ipady = 10;
        gbc.insets = new Insets(0,0,5,0);
        gbl.setConstraints(label, gbc);
        add(label);

        TextField portTextField = new TextField();
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        portTextField.setSize(100,100);
        gbl.setConstraints(portTextField, gbc);
        add(portTextField);

        startServerButton = new Button("Start server");
        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String portTextFieldContent = portTextField.getText();
                if (isNumeric(portTextFieldContent)) {
                    communicator.startServer(Integer.parseInt(portTextFieldContent));
                } else return;
            }
        });
        gbc.gridy = 2;
        gbl.setConstraints(startServerButton, gbc);
        add(startServerButton);

        pack();
        setSize(200, 200);
        setVisible(true);
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
