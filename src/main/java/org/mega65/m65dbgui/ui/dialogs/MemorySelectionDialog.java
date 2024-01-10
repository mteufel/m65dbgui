package org.mega65.m65dbgui.ui.dialogs;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.events.OpenMemoryViewerEvent;
import org.mega65.m65dbgui.services.AdressspaceService;
import org.mega65.m65dbgui.ui.MainFrame;
import org.mega65.m65dbgui.ui.models.AreaTableModel;
import org.mega65.m65dbgui.util.ColorHolder;
import org.mega65.m65dbgui.util.UiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

public class MemorySelectionDialog extends JDialog {

    private final AdressspaceService adressspaceService;
    private AreaTableModel areaTableModel;
    private JLabel info;

    Logger logger = Util.getLogger(MemorySelectionDialog.class);

    @Inject
    public MemorySelectionDialog(MainFrame mainFrame, AreaTableModel areaTableModel, AdressspaceService adressspaceService) {
        super(mainFrame, ModalityType.APPLICATION_MODAL);
        this.areaTableModel = areaTableModel;
        this.adressspaceService = adressspaceService;
        this.areaTableModel.setAdressspaces(this.adressspaceService.getAdressspaces());
        createDialog();
    }


    private void createDialog() {

        this.setTitle("Add memory tab");

        JScrollPane scrollPane;
        JLabel label = new JLabel("Address");
        JTextField addressField = new JTextField(20);
        JTable areaTable = new JTable();
        info = new JLabel("");
        info.setForeground(ColorHolder.getColor(ColorHolder.red));


        scrollPane = new JScrollPane(areaTable);

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(10,10,10,10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbl.setConstraints(label, gbc);
        add(label);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbl.setConstraints(addressField, gbc);
        add(addressField);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbl.setConstraints(info, gbc);
        add(info);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbl.setConstraints(scrollPane, gbc);
        scrollPane.setPreferredSize(new Dimension(800,600));
        add(scrollPane);

        pack();

        areaTable.setModel(areaTableModel);
        areaTable.setFocusable(false);
        areaTable.setRowSelectionAllowed(false);
        UiUtil.setColumnWidths(areaTable,100,100,90,480);

        areaTable.setDefaultRenderer(String.class, new CellRenederer());


        addressField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                updateInfo();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                updateInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                updateInfo();
            }

            private void updateInfo() {
                String msg = adressspaceService.checkEntry(addressField.getText().trim());
                if (!msg.equals("ok")) {
                    info.setText(msg);
                } else {
                    info.setText("");
                    //String title = ("RAM." + addressField.getText().trim().substring(0, 3) + "." + addressField.getText().trim().substring(3,4)).toUpperCase();
                }
            }


        });
        addressField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    String msg = adressspaceService.checkEntry(addressField.getText().trim());
                    String value = addressField.getText().trim().replace(".", "");
                    if (value.length()==3) {
                        value = value + "0000";
                    }
                    logger.info("value=" + value);
                    if (msg.equals("ok")) {
                        dispose();
                        String title = ("RAM." + value.substring(0, 3) + "." + value.substring(3,4)).toUpperCase();
                        String bank = value.substring(0, 3);
                        String start = value.substring(3, value.length());
                        String end = "ffff";

                        logger.info("title=" + title);
                        logger.info("offset=" + bank);
                        logger.info("start=" + start);
                        logger.info("end=" + end);

                        fireEvent(new OpenMemoryViewerEvent(true, title, Util.fromHex(bank + "0000"), Util.fromHex(start), Util.fromHex(end), Util.fromHex(start), OpenMemoryViewerEvent.MODE_REFRESH_MANUAL));

                    } else {
                        info.setText(msg);
                    }
                }
            }
        });

    }

    private class CellRenederer  extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setForeground(ColorHolder.getColor(ColorHolder.lightGrey));
            String available = (String) table.getValueAt(row,2);
            if (available.equals("NO")) {
                setForeground(ColorHolder.getColor(ColorHolder.darkGrey2));
            }

            if (column == 2 && available.equals("YES")) {
                setForeground(ColorHolder.getColor(ColorHolder.green));
            }

            if (value == null) {
                setText(value.toString());
            } else {
                setText(value.toString());
            }

            return this;
        }
    }
}
