package com.datascience.bigmovie.base.UserInterface;

import com.datascience.bigmovie.base.Database.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class DatabaseInterface {
    private JPanel content_panel;
    private JPanel buttons_panel;
    private JButton button_build;
    private JButton button_cancel;
    private JProgressBar progressBar;
    private JPanel rootPanel;

    private JFrame parserInterfaceFrame = new JFrame("Project DataScience - Groep 4 - Database Builder");

    public DatabaseInterface() {
        createInterfaceElements();

        button_build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runDatabaseBuilder();
            }
        });
        button_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exitPage();
            }
        });
    }

    /**
     * Main function that contains all of the base UI settings
     */
    private void createInterfaceElements() {
        parserInterfaceFrame.setContentPane(rootPanel);
        parserInterfaceFrame.setSize(720,280);
        parserInterfaceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parserInterfaceFrame.setVisible(true);
    }

    /**
     * Return to the main UI view
     */
    private void exitPage() {
        parserInterfaceFrame.setVisible(false);
        new UserInterface();
        parserInterfaceFrame.dispose();
    }

    /**
     * Run the Database builder, this disables the buttons until its done
     */
    private void runDatabaseBuilder() {
        button_build.setEnabled(false);
        button_cancel.setEnabled(false);
        progressBar.setIndeterminate(true);

        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread JDBC1 = new Thread(new JDBCUtil()::main);
                Thread JDBC2 = new Thread(new JDBCUtil2()::main);
                Thread JDBC3 = new Thread(new JDBCUtil3()::main);
                Thread JDBC4 = new Thread(new JDBCUtil4()::main);
                Thread JDBC5 = new Thread(new JDBCUtil5()::main);
                Thread JDBC6 = new Thread(new JDBCUtil6()::main);
                Thread JDBC7 = new Thread(new JDBCUtil7()::main);

                JDBC1.start();
                JDBC2.start();
                JDBC3.start();
                JDBC4.start();
                JDBC5.start();
                JDBC6.start();
                JDBC7.start();

                JDBC1.join();
                JDBC2.join();
                JDBC3.join();
                JDBC4.join();
                JDBC5.join();
                JDBC6.join();
                JDBC7.join();

                done();
                return null;
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                button_build.setEnabled(true);
                button_cancel.setEnabled(true);
            }
        }.execute();
    }
}
