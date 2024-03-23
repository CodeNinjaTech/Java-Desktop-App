/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking;

import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import javax.swing.SpinnerDateModel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicMenuBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DateFormatter;

/**
 *
 * @author Lefteris Souflas
 */
public class ParkingDisplay extends JFrame {

    private Cars currentEntry;
    private ParkingQueries queries;
    private List<Cars> results;
    private int numberOfEntries = 0;
    private int currentEntryIndex = 0;

    private static final Dimension preferredSize = new Dimension(230, 60);
    private static final Insets margin = new Insets(5, 5, 5, 5);
    private static final DateFormat dateFormat = new SimpleDateFormat("E, dd MMMM yyyy");
    private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private Calendar calendar;

    private JOptionPane newDayOptionPane;
    private JDialog searchDialog;
    private JPanel searchFrame;
    private JPanel plateSearchPanel;
    private JLabel plateSearchLabel;
    private JTextField plateSearchTextField;
    private JLabel invisibleLabel6;
    private JButton plateSearchSubmitButton;
    private JPanel dateSearchPanel;
    private JLabel dateSearchLabel;
    private SpinnerDateModel model3;
    private JSpinner spinner3;
    private DateEditor editor3;
    private DateFormatter formatter3;
    private DefaultEditor spinnerEditor3;
    private JLabel invisibleLabel7;
    private JButton dateSearchSubmitButton;

    private EditListener l;
    private JMenu optionsMenu;
    private JMenuItem newDay;
    private JMenuItem search;
    private JMenuItem exit;
    private JLabel dateTimeLabel;
    private JMenu earnings;
    private JLabel invisibleLabel;
    private JMenuBar bar;

    private JPanel displayPanel;
    private JLabel presentCarsLabel;
    private JTextField presentCarsTextField;
    private JLabel freeLotsLabel;
    private JTextField freeLotsTextField;

    private JPanel editingPanel;

    private JPanel carArrivalPanel;
    private JPanel timeArrivingPanel;
    private JLabel timeArrivingPlateLabel;
    private JTextField timeArrivingPlateTextField;
    private JLabel timeArrivingLabel;
    private SpinnerDateModel model;
    private JSpinner spinner;
    private DateEditor editor;
    private DateFormatter formatter;
    private DefaultEditor spinnerEditor;
    private JLabel invisibleLabel2;
    private JButton timeArrivingSubmitButton;
    private JPanel nowArrivingPanel;
    private JLabel nowArrivingPlateLabel;
    private JTextField nowArrivingPlateTextField;
    private JLabel invisibleLabel3;
    private JButton nowArrivingSubmitButton;

    private JPanel historyPanel;
    private JTextArea historyTextArea;
    private JButton saveButton;
    private JFileChooser chooser;
    private File f;
    private FileWriter fw;

    private JPanel carDeparturePanel;
    private JPanel timeDepartingPanel;
    private JLabel timeDepartingPlateLabel;
    private JTextField timeDepartingPlateTextField;
    private JLabel timeDepartingLabel;
    private SpinnerDateModel model2;
    private JSpinner spinner2;
    private DateEditor editor2;
    private DateFormatter formatter2;
    private DefaultEditor spinnerEditor2;
    private JLabel invisibleLabel4;
    private JButton timeDepartingSubmitButton;
    private JPanel nowDepartingPanel;
    private JLabel nowDepartingPlateLabel;
    private JTextField nowDepartingPlateTextField;
    private JLabel invisibleLabel5;
    private JButton nowDepartingSubmitButton;

    public ParkingDisplay() {
        super("SOUFLAS PAYSTATION");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* ΚΑΤΑΣΚΕΥΗ JMENUBAR */
        l = new EditListener();
        /* ΚΑΤΑΣΚΕΥΗ ΤΟΥ MENU */
        optionsMenu = new JMenu("<html><p style='text-align:center;'>SOUFLAS<br>PAYSTATION</p></html>");
        newDay = new JMenuItem("Start New Day...");
        newDay.setMnemonic('n');
        newDay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        newDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newDayActionPerformed(e);
            }
        });
        optionsMenu.add(newDay);
        search = new JMenuItem("Car Search...");
        search.setMnemonic('s');
        search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchActionPerformed(e);
            }
        });
        optionsMenu.add(search);
        exit = new JMenuItem("Exit...");
        exit.setMnemonic('x');
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitActionPerformed(e);
            }
        });
        optionsMenu.add(exit);

        /* ΚΑΤΑΣΚΕΥΗ ΡΟΛΟΓΙΟΥ */
        calendar = Calendar.getInstance();
        dateTimeLabel = new JLabel();
        dateTimeLabel.setText("<html><p style='text-align:center;'>" + dateFormat.format(calendar.getTime()) + "<br>" + timeFormat.format(calendar.getTime()) + "</p></html>");
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar now = Calendar.getInstance();
                dateTimeLabel.setText("<html><p style='text-align:center;'>" + dateFormat.format(now.getTime()) + "<br>" + timeFormat.format(now.getTime()) + "</p></html>");
            }
        }).start();

        /* ΚΑΤΑΣΚΕΥΗ ΠΛΗΡΟΦΟΡΙΩΝ ΓΙΑ ΕΣΟΔΑ ΗΜΕΡΑΣ (ΚΑΘΗΜΕΡΙΝΑ ΑΠΟ 00:00:00 ΕΩΣ 23:59:59) */
        queries = new ParkingQueries();
        earnings = new JMenu("<html><p style='text-align:center;'><u>Today's earnings</u>:<br>$" + queries.getCurrentEarnings() + "</p></html>");
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                earnings.setText("<html><p style='text-align:center;'><u>Today's earnings</u>:<br>$" + queries.getCurrentEarnings() + "</p></html>");
            }
        }).start();

        invisibleLabel = new JLabel(" ") {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, 1000);
            }
        };

        bar = new JMenuBar();
        bar.setMargin(margin);
        bar.add(optionsMenu);
        bar.add(invisibleLabel);
        bar.add(dateTimeLabel);
        bar.add(Box.createHorizontalGlue());
        bar.add(earnings);
        setJMenuBar(bar);
//        customizeMenuBar(bar); /* ΜΕΘΟΔΟΣ ΓΙΑ ΧΡΩΜΑΤΙΣΜΟ JMENUBAR - ΔΕΝ ΧΡΗΣΙΜΟΠΟΙΗΘΗΚΑΝ - ΠΡΟΤΙΜΗΘΗΚΑΝ ΤΑ ORIGINAL */

        add(Box.createRigidArea(new Dimension(0, 10)));

        /* ΚΑΤΑΣΚΕΥΗ LABEL PARKING INFO */
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(2, 2, 5, 5));
        displayPanel.setBorder(BorderFactory.createTitledBorder("Parking Info"));
        displayPanel.setMaximumSize(preferredSize);
        displayPanel.setPreferredSize(preferredSize);
        displayPanel.setAlignmentY(TOP_ALIGNMENT);
        presentCarsLabel = new JLabel("<html><p style='text-align:center;'><u>Cars Present</u>:</p></html>");
        displayPanel.add(presentCarsLabel);
        presentCarsTextField = new JTextField(2);
        presentCarsTextField.setHorizontalAlignment(JTextField.CENTER);
        presentCarsTextField.setText("" + queries.getParkedCars());
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presentCarsTextField.setText("" + queries.getParkedCars());
            }
        }).start();
        presentCarsTextField.setEditable(false);
        displayPanel.add(presentCarsTextField);
        freeLotsLabel = new JLabel("<html><p style='text-align:center;'><u>Free Parking Lots</u>:</p></html>");
        displayPanel.add(freeLotsLabel);
        freeLotsTextField = new JTextField(2);
        freeLotsTextField.setHorizontalAlignment(JTextField.CENTER);
        freeLotsTextField.setText("" + (50 - queries.getParkedCars()));
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                freeLotsTextField.setText("" + (50 - queries.getParkedCars()));
            }
        }).start();
        freeLotsTextField.setEditable(false);
        displayPanel.add(freeLotsTextField);
        add(displayPanel);
        /* Τέλος κατασκευής γενικών πληροφοριών Parking */

        add(Box.createRigidArea(new Dimension(0, 5)));

        /* ΚΑΤΑΣΚΕΥΗ ΛΕΙΤΟΥΡΓΙΩΝ ΕΠΕΞΕΡΓΑΣΙΑΣ ΕΠΙ ΤΗΣ ΕΦΑΡΜΟΓΗΣ */
        editingPanel = new JPanel();
        /* ΚΑΤΑΣΚΕΥΗ JPANEL ΕΙΣΟΔΟΥ ΑΥΤΟΚΙΝΗΤΟΥ */
        carArrivalPanel = new JPanel();
        carArrivalPanel.setLayout(new BoxLayout(carArrivalPanel, BoxLayout.PAGE_AXIS));
        carArrivalPanel.setBorder(BorderFactory.createTitledBorder("Car Arrival"));
        carArrivalPanel.setPreferredSize(new Dimension(240, 195));
        carArrivalPanel.setMaximumSize(new Dimension(240, 195));

        carArrivalPanel.add(Box.createVerticalStrut(5));
        timeArrivingPanel = new JPanel();
        timeArrivingPanel.setLayout(new GridLayout(3, 2, 5, 5));
        timeArrivingPanel.setMaximumSize(new Dimension(230, 90));
        timeArrivingPanel.setPreferredSize(new Dimension(230, 90));
        timeArrivingPanel.setBorder(BorderFactory.createTitledBorder("a. Arrived at given time"));
        timeArrivingPlateLabel = new JLabel("<html><p style='text-align:left;'><u>Insert Car's plate</u>:</p></html>");
        timeArrivingPlateTextField = new JTextField(2);
        timeArrivingPlateTextField.setSize(7, 1);
        timeArrivingPlateTextField.setHorizontalAlignment(JTextField.CENTER);
        timeArrivingPanel.add(timeArrivingPlateLabel);
        timeArrivingPanel.add(timeArrivingPlateTextField);
        timeArrivingLabel = new JLabel("<html><p style='text-align:left;'><u>Time arrived</u>:</p></html>");
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        model = new SpinnerDateModel();
        model.setValue(calendar.getTime());
        spinner = new JSpinner(model);
        editor = new DateEditor(spinner, "HH:mm");
        formatter = (DateFormatter) editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        spinner.setEditor(editor);
        if (editor instanceof DefaultEditor) {
            spinnerEditor = (DefaultEditor) editor;
            spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        }
        timeArrivingPanel.add(timeArrivingLabel);
        timeArrivingPanel.add(spinner);
        invisibleLabel2 = new JLabel(" ") {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, 1000);
            }
        };
        timeArrivingPanel.add(invisibleLabel2);
        timeArrivingSubmitButton = new JButton("Submit");
        timeArrivingSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeArrivingSubmitButtonActionPerformed(e);
            }
        });
        timeArrivingPanel.add(timeArrivingSubmitButton);
        carArrivalPanel.add(timeArrivingPanel);

        nowArrivingPanel = new JPanel();
        nowArrivingPanel.setLayout(new GridLayout(2, 2, 5, 5));
        nowArrivingPanel.setMaximumSize(new Dimension(230, 70));
        nowArrivingPanel.setPreferredSize(new Dimension(230, 70));
        nowArrivingPanel.setBorder(BorderFactory.createTitledBorder("b. Arriving now"));
        nowArrivingPlateLabel = new JLabel("<html><p style='text-align:left;'><u>Insert Car's plate</u>:</p></html>");
        nowArrivingPlateTextField = new JTextField(2);
        nowArrivingPlateTextField.setSize(7, 1);
        nowArrivingPlateTextField.setHorizontalAlignment(JTextField.CENTER);
        nowArrivingPanel.add(nowArrivingPlateLabel);
        nowArrivingPanel.add(nowArrivingPlateTextField);
        invisibleLabel3 = new JLabel(" ") {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, 1000);
            }
        };
        nowArrivingPanel.add(invisibleLabel3);
        nowArrivingSubmitButton = new JButton("Submit");
        nowArrivingSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowArrivingSubmitButtonActionPerformed(e);
            }
        });
        nowArrivingPanel.add(nowArrivingSubmitButton);
        carArrivalPanel.add(nowArrivingPanel);
        carArrivalPanel.add(Box.createVerticalStrut(5));
        editingPanel.add(carArrivalPanel);

        /* ΚΑΤΑΣΚΕΥΗ JTEXTAREA ΧΡΟΝΙΚΟΥ/ΚΑΤΑΧΩΡΗΣΕΩΝ (LOGS) */
        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.PAGE_AXIS));
        historyPanel.setAlignmentX(CENTER_ALIGNMENT);
        historyPanel.setBorder(BorderFactory.createTitledBorder("Today's chronicle/records"));
        historyPanel.add(Box.createVerticalStrut(10));
        historyPanel.setPreferredSize(new Dimension(410, 195));
        historyPanel.setMaximumSize(new Dimension(410, 2100000000));
        historyTextArea = new JTextArea("Car Plate\tArrival Time\tDeparture Time\tTime Parked\tCost\n");
        historyPanel.add(new JScrollPane(historyTextArea));
        historyPanel.add(Box.createVerticalStrut(10));
        saveButton = new JButton("Save");
        chooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                f = getSelectedFile();
                if (f.exists() && getDialogType() == JFileChooser.SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        case JOptionPane.NO_OPTION:
                            return;
                        case JOptionPane.CLOSED_OPTION:
                            return;
                        case JOptionPane.CANCEL_OPTION:
                            cancelSelection();
                            return;
                    }
                }
                super.approveSelection();
            }
        };
        chooser.setDialogTitle("Save today's chronicles/records");
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Documents (.txt)", "txt"));
        fw = null;
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButtonActionPerformed(e);
            }
        });
        historyPanel.add(saveButton);
        saveButton.setAlignmentX(CENTER_ALIGNMENT);
        editingPanel.add(historyPanel);

        /* ΚΑΤΑΣΚΕΥΗ JPANEL ΕΞΟΔΟΥ ΑΥΤΟΚΙΝΗΤΟΥ */
        carDeparturePanel = new JPanel();
        carDeparturePanel.setLayout(new BoxLayout(carDeparturePanel, BoxLayout.PAGE_AXIS));
        carDeparturePanel.setBorder(BorderFactory.createTitledBorder("Car Departure"));
        carDeparturePanel.setPreferredSize(new Dimension(240, 195));
        carDeparturePanel.setMaximumSize(new Dimension(240, 195));

        carDeparturePanel.add(Box.createVerticalStrut(5));
        timeDepartingPanel = new JPanel();
        timeDepartingPanel.setLayout(new GridLayout(3, 2, 5, 5));
        timeDepartingPanel.setMaximumSize(new Dimension(230, 90));
        timeDepartingPanel.setPreferredSize(new Dimension(230, 90));
        timeDepartingPanel.setBorder(BorderFactory.createTitledBorder("a. Departed at given time"));
        timeDepartingPlateLabel = new JLabel("<html><p style='text-align:left;'><u>Insert Car's plate</u>:</p></html>");
        timeDepartingPlateTextField = new JTextField(2);
        timeDepartingPlateTextField.setSize(7, 1);
        timeDepartingPlateTextField.setHorizontalAlignment(JTextField.CENTER);
        timeDepartingPanel.add(timeDepartingPlateLabel);
        timeDepartingPanel.add(timeDepartingPlateTextField);
        timeDepartingLabel = new JLabel("<html><p style='text-align:left;'><u>Time departed</u>:</p></html>");
        model2 = new SpinnerDateModel();
        model2.setValue(calendar.getTime());
        spinner2 = new JSpinner(model2);
        editor2 = new DateEditor(spinner2, "HH:mm");
        formatter2 = (DateFormatter) editor2.getTextField().getFormatter();
        formatter2.setAllowsInvalid(false);
        formatter2.setOverwriteMode(true);
        spinner2.setEditor(editor2);
        if (editor2 instanceof DefaultEditor) {
            spinnerEditor2 = (DefaultEditor) editor2;
            spinnerEditor2.getTextField().setHorizontalAlignment(JTextField.CENTER);
        }
        timeDepartingPanel.add(timeDepartingLabel);
        timeDepartingPanel.add(spinner2);
        invisibleLabel4 = new JLabel(" ") {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, 1000);
            }
        };
        timeDepartingPanel.add(invisibleLabel4);
        timeDepartingSubmitButton = new JButton("Submit");
        timeDepartingSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeDepartingSubmitButtonActionPerformed(e);
            }
        });
        timeDepartingPanel.add(timeDepartingSubmitButton);
        carDeparturePanel.add(timeDepartingPanel);

        nowDepartingPanel = new JPanel();
        nowDepartingPanel.setLayout(new GridLayout(2, 2, 5, 5));
        nowDepartingPanel.setMaximumSize(new Dimension(230, 70));
        nowDepartingPanel.setPreferredSize(new Dimension(230, 70));
        nowDepartingPanel.setBorder(BorderFactory.createTitledBorder("b. Departing now"));
        nowDepartingPlateLabel = new JLabel("<html><p style='text-align:left;'><u>Insert Car's plate</u>:</p></html>");
        nowDepartingPlateTextField = new JTextField(2);
        nowDepartingPlateTextField.setSize(7, 1);
        nowDepartingPlateTextField.setHorizontalAlignment(JTextField.CENTER);
        nowDepartingPanel.add(nowDepartingPlateLabel);
        nowDepartingPanel.add(nowDepartingPlateTextField);
        invisibleLabel5 = new JLabel(" ") {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, 1000);
            }
        };
        nowDepartingPanel.add(invisibleLabel5);
        nowDepartingSubmitButton = new JButton("Submit");
        nowDepartingSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowDepartingSubmitButtonActionPerformed(e);
            }
        });
        nowDepartingPanel.add(nowDepartingSubmitButton);
        carDeparturePanel.add(nowDepartingPanel);
        carDeparturePanel.add(Box.createVerticalStrut(5));

        editingPanel.add(carDeparturePanel);
        add(editingPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        pack();

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ParkingDisplay.this, "Exiting will erase 'Today's chronicles/records'.\nProceed?", "Exit", JOptionPane.YES_NO_OPTION);
                switch (option) {
                    case JOptionPane.YES_OPTION:
                        ParkingDisplay.this.dispose();
                        queries.close();
                        System.exit(0);
                    case JOptionPane.NO_OPTION:
                        return;
                    case JOptionPane.CLOSED_OPTION:
                        return;
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        setVisible(true);
    }

    private class EditListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    }

    /* ΠΡΟΣΘΗΚΗ ΛΕΙΤΟΥΡΓΙΩΝ ΣΤΑ JBUTTONS */
    private void newDayActionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(this, "Starting a new day will erase 'Today's chronicles/records' "
                + "and exit all parked cars.\nProceed?", "New Day", JOptionPane.YES_NO_OPTION);
        switch (option) {
            case JOptionPane.YES_OPTION:
                int result = queries.startNewDay();
                historyTextArea.setText("Car Plate\tArrival Time\tDeparture Time\tTime Parked\tCost\n");
                return;
            case JOptionPane.NO_OPTION:
                return;
            case JOptionPane.CLOSED_OPTION:
                return;
        }
    }

    private void searchActionPerformed(ActionEvent e) {
        searchDialog = new JDialog(this, "Car Search");
        searchDialog.setSize(256, 220);
        searchFrame = new JPanel();
        searchFrame.setLayout(new BoxLayout(searchFrame, BoxLayout.PAGE_AXIS));
        searchFrame.setBorder(BorderFactory.createTitledBorder("Car Search"));
        searchFrame.setPreferredSize(new Dimension(240, 150));
        searchFrame.setMaximumSize(new Dimension(240, 150));
        searchFrame.add(Box.createVerticalStrut(5));
        plateSearchPanel = new JPanel();
        plateSearchPanel.setLayout(new GridLayout(2, 2, 5, 5));
        plateSearchPanel.setMaximumSize(new Dimension(230, 70));
        plateSearchPanel.setPreferredSize(new Dimension(230, 70));
        plateSearchPanel.setBorder(BorderFactory.createTitledBorder("a. Search car by plate"));
        plateSearchLabel = new JLabel("<html><p style='text-align:left;'><u>Insert Car's plate</u>:</p></html>");
        plateSearchTextField = new JTextField(2);
        plateSearchTextField.setSize(7, 1);
        plateSearchTextField.setHorizontalAlignment(JTextField.CENTER);
        plateSearchPanel.add(plateSearchLabel);
        plateSearchPanel.add(plateSearchTextField);
        invisibleLabel6 = new JLabel(" ") {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, 1000);
            }
        };
        plateSearchPanel.add(invisibleLabel6);
        plateSearchSubmitButton = new JButton("Submit");
        plateSearchSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plateSearchSubmitButtonActionPerformed(e);
            }
        });
        plateSearchPanel.add(plateSearchSubmitButton);
        searchFrame.add(plateSearchPanel);
        dateSearchPanel = new JPanel();
        dateSearchPanel.setLayout(new GridLayout(2, 2, 5, 5));
        dateSearchPanel.setMaximumSize(new Dimension(230, 70));
        dateSearchPanel.setPreferredSize(new Dimension(230, 70));
        dateSearchPanel.setBorder(BorderFactory.createTitledBorder("b. Search car by arrival date"));
        dateSearchLabel = new JLabel("<html><p style='text-align:left;'><u>Insert date</u>:</p></html>");
        calendar = Calendar.getInstance();
        model3 = new SpinnerDateModel();
        model3.setValue(calendar.getTime());
        spinner3 = new JSpinner(model3);
        editor3 = new DateEditor(spinner3, "dd MMMM yyyy");
        formatter3 = (DateFormatter) editor3.getTextField().getFormatter();
        formatter3.setAllowsInvalid(false);
        formatter3.setOverwriteMode(true);
        spinner3.setEditor(editor3);
        if (editor3 instanceof DefaultEditor) {
            spinnerEditor3 = (DefaultEditor) editor3;
            spinnerEditor3.getTextField().setHorizontalAlignment(JTextField.CENTER);
        }
        dateSearchPanel.add(dateSearchLabel);
        dateSearchPanel.add(spinner3);

        invisibleLabel7 = new JLabel(" ") {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, 1000);
            }
        };
        dateSearchPanel.add(invisibleLabel7);
        dateSearchSubmitButton = new JButton("Submit");
        dateSearchSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateSearchSubmitButtonActionPerformed(e);
            }
        });
        dateSearchPanel.add(dateSearchSubmitButton);
        searchFrame.add(dateSearchPanel);
        searchFrame.add(Box.createVerticalStrut(5));
//        searchFrame.pack();
        searchDialog.add(searchFrame);
        searchDialog.setVisible(true);
//        this.add(searchFrame);
    }

    private void plateSearchSubmitButtonActionPerformed(ActionEvent e) {
        String plate = plateSearchTextField.getText();
        if (plate.matches("^[A-Z]{3}\\s\\d{4}")) {
            results = queries.getCarByPlate(plate);
            numberOfEntries = results.size();
            Object[][] rowData = {};
            Object[] columnNames = {"plate", "date", "arrival", "departure", "cost"};
            DefaultTableModel listTableModel;
            listTableModel = new DefaultTableModel(rowData, columnNames);
            System.out.println("results: " + results + " #Entries: " + numberOfEntries);
            if (numberOfEntries != 0) {
                for (int i = 0; i < results.size(); i++) {
                    currentEntry = results.get(i);
                    Date arrivalDate = new Date(currentEntry.getArrival().getTime());
                    if (currentEntry.getDeparture() != null) {
                        Date departureDate = new Date(currentEntry.getDeparture().getTime());
                        listTableModel.addRow(new Object[]{currentEntry.getPlate(), dateFormat.format(arrivalDate), timeFormat.format(arrivalDate),
                            timeFormat.format(departureDate), currentEntry.getCost()});
                    } else {
                        listTableModel.addRow(new Object[]{currentEntry.getPlate(), dateFormat.format(arrivalDate), timeFormat.format(arrivalDate),
                            "-", "-"});
                    }
                }
            }
            JDialog tableDialog = new JDialog(this, "Search Results");
            tableDialog.setSize(640, 340);
            JTable searchTable = new JTable(listTableModel);
            JScrollPane scrollPane = new JScrollPane(searchTable);
            searchTable.setFillsViewportHeight(true);
            tableDialog.add(scrollPane);
            tableDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Check that inserted car's plate is of pattern 'ABC 1234'", "Failure", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dateSearchSubmitButtonActionPerformed(ActionEvent e) {
        Date uDate = (Date) spinnerEditor3.getTextField().getValue();
        java.sql.Date date = new java.sql.Date(uDate.getTime());
        results = queries.getCarByArrival(date);
        numberOfEntries = results.size();
        Object[][] rowData = {};
        Object[] columnNames = {"plate", "date", "arrival", "departure", "cost"};
        DefaultTableModel listTableModel;
        listTableModel = new DefaultTableModel(rowData, columnNames);
        System.out.println("results: " + results + " #Entries: " + numberOfEntries);
        if (numberOfEntries != 0) {
            for (int i = 0; i < results.size(); i++) {
                currentEntry = results.get(i);
                Date arrivalDate = new Date(currentEntry.getArrival().getTime());
                if (currentEntry.getDeparture() != null) {
                    Date departureDate = new Date(currentEntry.getDeparture().getTime());
                    listTableModel.addRow(new Object[]{currentEntry.getPlate(), dateFormat.format(arrivalDate), timeFormat.format(arrivalDate),
                        timeFormat.format(departureDate), currentEntry.getCost()});
                } else {
                    listTableModel.addRow(new Object[]{currentEntry.getPlate(), dateFormat.format(arrivalDate), timeFormat.format(arrivalDate),
                        "-", "-"});
                }
            }
        }
        JDialog tableDialog = new JDialog(this, "Search Results");
        tableDialog.setSize(640, 340);
        JTable searchTable = new JTable(listTableModel);
        JScrollPane scrollPane = new JScrollPane(searchTable);
        searchTable.setFillsViewportHeight(true);
        tableDialog.add(scrollPane);
        tableDialog.setVisible(true);
    }

    private void exitActionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(this, "Exiting will erase 'Today's chronicles/records'.\nProceed?", "Exit", JOptionPane.YES_NO_OPTION);
        switch (option) {
            case JOptionPane.YES_OPTION:
                ParkingDisplay.this.dispose();
                queries.close();
                System.exit(0);
            case JOptionPane.NO_OPTION:
                return;
            case JOptionPane.CLOSED_OPTION:
                return;
        }
    }

    private void timeArrivingSubmitButtonActionPerformed(ActionEvent e) {
        String plate = timeArrivingPlateTextField.getText();
        if (plate.matches("^[A-Z]{3}\\s\\d{4}")) {
            calendar.setTime((Date) spinnerEditor.getTextField().getValue());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int result = queries.addCar(plate, hour, minute);
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Arrived Car added!!!", "Confirmation", JOptionPane.PLAIN_MESSAGE);
                historyTextArea.append(timeArrivingPlateTextField.getText() + "\t" + timeFormat.format(calendar.getTime()) + "\t-\t-\t-" + "\n");
                timeArrivingPlateTextField.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Arrived Car not added...\nCheck that inserted car's plate is of pattern 'ABC 1234'", "Failure", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void nowArrivingSubmitButtonActionPerformed(ActionEvent e) {
        String plate = nowArrivingPlateTextField.getText();
        calendar = Calendar.getInstance();
        if (plate.matches("^[A-Z]{3}\\s\\d{4}")) {
            int result = queries.addCarNow(plate);
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Arrived Car added!!!", "Confirmation", JOptionPane.PLAIN_MESSAGE);
                historyTextArea.append(nowArrivingPlateTextField.getText() + "\t" + timeFormat.format(calendar.getTime()) + "\t-\t-\t-" + "\n");
                nowArrivingPlateTextField.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Arrived Car not added...\nCheck that inserted car's plate is of pattern 'ABC 1234'", "Failure", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveButtonActionPerformed(ActionEvent e) {
        String h = historyTextArea.getText();
        int value = chooser.showSaveDialog(rootPane);
        if (value == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file == null) {
                return;
            }
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getParentFile(), file.getName() + ".txt");
            }
            try ( FileWriter fw2 = new FileWriter(file)) {
                fw2.write(h);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void timeDepartingSubmitButtonActionPerformed(ActionEvent e) {
        String plate = timeDepartingPlateTextField.getText();
        if (plate.matches("^[A-Z]{3}\\s\\d{4}")) {
            calendar.setTime((Date) spinnerEditor2.getTextField().getValue());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int result = queries.exitCar(plate, hour, minute);
            String difference = "";
            float cost = -1;
            results = queries.getCarByPlate(plate);
            numberOfEntries = results.size();
            System.out.println("results = " + results + " number of entries= " + numberOfEntries);
            if (numberOfEntries != 0) {
                int i = results.size() - 1;
                currentEntry = results.get(i);
                Date arrivalDate = new Date(currentEntry.getArrival().getTime());
                Date departureDate = new Date(currentEntry.getDeparture().getTime());
                long millis = departureDate.getTime() - arrivalDate.getTime();
                difference = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                cost = currentEntry.getCost();
            }
            if (result != 0) {
                JOptionPane.showMessageDialog(null, "Departed Car's Info updated!!!", "Confirmation", JOptionPane.PLAIN_MESSAGE);
                historyTextArea.append(timeDepartingPlateTextField.getText() + "\t-\t" + timeFormat.format(calendar.getTime()) + "\t" + difference + "\t" + cost + "\n");
                timeDepartingPlateTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Departed Car's Info not updated...\nCheck that the inserted car's plate exists in parking lot", "Failure", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Arrived Car not added...\nCheck that inserted car's plate is of pattern 'ABC 1234'", "Failure", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void nowDepartingSubmitButtonActionPerformed(ActionEvent e) {
        String plate = nowDepartingPlateTextField.getText();
        if (plate.matches("^[A-Z]{3}\\s\\d{4}")) {
            calendar = Calendar.getInstance();
            int result = queries.exitCarNow(plate);
            String difference = "";
            float cost = -1;
            results = queries.getCarByPlate(plate);
            numberOfEntries = results.size();
            if (numberOfEntries != 0) {
                int i = results.size() - 1;
                currentEntry = results.get(i);
                Date arrivalDate = new Date(currentEntry.getArrival().getTime());
                Date departureDate = new Date(currentEntry.getDeparture().getTime());
                long millis = departureDate.getTime() - arrivalDate.getTime();
                difference = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                cost = currentEntry.getCost();
            }
            if (result != 0) {
                JOptionPane.showMessageDialog(null, "Departed Car's Info updated!!!", "Confirmation", JOptionPane.PLAIN_MESSAGE);
                historyTextArea.append(nowDepartingPlateTextField.getText() + "\t-\t" + timeFormat.format(calendar.getTime()) + "\t" + difference + "\t" + cost + "\n");
                nowDepartingPlateTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Departed Car's Info not updated...\nCheck that the inserted car's plate exists in parking lot", "Failure", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Arrived Car not added...\nCheck that inserted car's plate is of pattern 'ABC 1234'", "Failure", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* ΜΕΘΟΔΟΙ ΓΙΑ ΧΡΩΜΑΤΙΣΜΟ ΤΟΥ JMENUBAR - ΔΕΝ ΧΡΗΣΙΜΟΠΟΙΗΘΗΚΑΝ ΕΝ ΤΕΛΕΙ - ΠΡΟΤΙΜΗΘΗΚΕ ΤΟ ORIGINAL */
//    private void customizeMenuBar(JMenuBar menuBar) {
//
//        menuBar.setUI(new BasicMenuBarUI() {
//            @Override
//            public void paint(Graphics g, JComponent c) {
//                g.setColor(Color.white);
//                g.fillRect(0, 0, c.getWidth(), c.getHeight());
//            }
//        });
//        MenuElement[] menus = menuBar.getSubElements();
//        for (MenuElement menuElement : menus) {
//            JMenu menu = (JMenu) menuElement.getComponent();
//            changeComponentColors(menu);
//            menu.setOpaque(true);
//            MenuElement[] menuElements = menu.getSubElements();
//            for (MenuElement popupMenuElement : menuElements) {
//                JPopupMenu popupMenu = (JPopupMenu) popupMenuElement.getComponent();
//                popupMenu.setBorder(null);
//                MenuElement[] menuItens = popupMenuElement.getSubElements();
//                for (MenuElement menuItemElement : menuItens) {
//                    JMenuItem menuItem = (JMenuItem) menuItemElement.getComponent();
//                    changeComponentColors(menuItem);
//                    menuItem.setOpaque(true);
//                }
//            }
//        }
//    }
//
//    private void changeComponentColors(Component comp) {
//        comp.setBackground(Color.white);
//        comp.setForeground(Color.black);
//    }
    public static void main(String[] args) {

        new ParkingDisplay();
    }
}
