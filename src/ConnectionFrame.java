import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Connection frame creates window that provide:<br>
 *  - Text area to type new message<br>
 *  - Text area showing messages history<br>
 *  - Button to send nawe message
 * @author Damian Szymczyk
 * @version 1.0
 */
public class ConnectionFrame extends Frame {
    /** connectionSesion that is represented by opened window*/
    ConnectionSession connectionSession;
    /** Messages history field*/
    JTextPane messagesField;
    /** New messages text field*/
    JTextArea typeMessageField;
    /** Button to send new message */
    Button sendButton;

    /**
     * Constructor that creates connection window.<br>
     * @param connectionSession connectionSession that is attached to created window
     */
    public ConnectionFrame(ConnectionSession connectionSession) {
        super("Connection frame");

        this.connectionSession = connectionSession;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                System.exit(0);
            }
        });

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);

        // TODO - add scrollbar to text area
        messagesField = new JTextPane();
        JScrollPane jsp = new JScrollPane(messagesField);
        messagesField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 4;
        gbc.weightx = 1;
        gbc.weighty = 3;
        gbc.fill = GridBagConstraints.BOTH;


        gbl.setConstraints(jsp, gbc);
        add(jsp);

        typeMessageField = new JTextArea();
        typeMessageField.setLineWrap(true);
        typeMessageField.setBackground(Color.lightGray);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 3;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbl.setConstraints(typeMessageField, gbc);
        add(typeMessageField);

        sendButton = new Button("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newMessage = typeMessageField.getText();
                connectionSession.sendMessage(newMessage);
                typeMessageField.setText("");
                refreshMessagesField();
            }
        });
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 2;
        gbc.weighty = 1;
        gbl.setConstraints(sendButton, gbc);
        add(sendButton);

        pack();
        setSize(300, 300);
        setVisible(true);
    }

    /**
     * Refreshes messages field
     */
    public void refreshMessagesField() {
        String content = "";
        for( Message message : connectionSession.getMessageList() ) {
//            https://docs.oracle.com/javase/tutorial/uiswing/components/editorpane.html
//            content += "<html><font color=\\\"red\\\">" + message.getSenderAddress() + "</font></html>\n";
//            content += "<html><font color=\\\"black\\\">" + message.getContent() + "</font></html>\n";
            content += message.getSenderAddress() + "\n";
            content += message.getContent()+ "\n";
        }
        messagesField.setText(content);
    }
}
