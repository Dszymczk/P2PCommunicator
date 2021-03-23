import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerWindow extends Frame {
    P2PCommunicator communicator;

    public ServerWindow(P2PCommunicator communicator) {
        super("Server window");
        this.communicator = communicator;

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
                System.exit(0);
            }
        });

        Button newConnectionButton = new Button("Start new connection");
        add(newConnectionButton);
        newConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicator.openNewConnectionWindow();
            }
        });
        pack();
        setSize(300, 300);
        setVisible(true);
    }

}
