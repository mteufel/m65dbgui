package org.mega65.m65dbgui.ui.panels;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.domain.Register;
import org.mega65.m65dbgui.services.MemoryService;
import org.mega65.m65dbgui.services.RegistersService;
import org.mega65.m65dbgui.ui.controls.RegisterLabel;
import org.mega65.m65dbgui.ui.models.RegisterDescriptionModel;
import org.mega65.m65dbgui.util.ColorHolder;
import org.mega65.m65dbgui.util.UiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegistersLookupPanel extends JPanel {

    RegisterLabel labelValue;
    JCheckBox checkCpuContext;
    JTable descriptionTable;
    RegisterDescriptionModel registerDescriptionModel;
    JTextPane paneRegisterDetails;
    JTextField textRegister;
    MemoryService memoryService;
    RegistersService registersService;
    Logger logger = Util.getLogger(RegistersLookupPanel.class);
    String vicFilter = "";

    @Inject
    public RegistersLookupPanel(RegisterDescriptionModel registerDescriptionModel, RegisterLabel labelValue, MemoryService memoryService, RegistersService registersService) {
        this.memoryService = memoryService;
        this.registerDescriptionModel = registerDescriptionModel;
        this.labelValue = labelValue;
        this.registersService = registersService;
        createPanel();
    }

     @Override
     public void setVisible(boolean aFlag) {
         super.setVisible(aFlag);
         textRegister.requestFocus();
     }

     private void createPanel() {



        textRegister = new JTextField("");
        JButton buttonSearch = new JButton("...");
        labelValue.setForeground(ColorHolder.getColor(ColorHolder.purple));

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));
        checkCpuContext = new JCheckBox("CPU Context");
        checkCpuContext.setSelected(true);
        optionsPanel.add(checkCpuContext);
        optionsPanel.add(new JLabel("        "));

        JRadioButton radioVic2 = new JRadioButton(" VIC-II  ");
        JRadioButton radioVic3 = new JRadioButton(" VIC-III  ");
        JRadioButton radioVic4 = new JRadioButton(" VIC-IV  ");
        radioVic2.addActionListener(new VicSelectionActionListener());
        radioVic3.addActionListener(new VicSelectionActionListener());
        radioVic4.addActionListener(new VicSelectionActionListener());
        ButtonGroup vicGroup = new ButtonGroup();
        vicGroup.add(radioVic2);
        vicGroup.add(radioVic3);
        vicGroup.add(radioVic4);
        optionsPanel.add(radioVic2);
        optionsPanel.add(radioVic3);
        optionsPanel.add(radioVic4);



        paneRegisterDetails = new JTextPane();
        paneRegisterDetails.setContentType("text/html");

        descriptionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(descriptionTable);



        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        gbc.weighty = 0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbl.setConstraints(textRegister, gbc);
        add(textRegister);

        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbl.setConstraints(buttonSearch, gbc);
        add(buttonSearch);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbl.setConstraints(optionsPanel, gbc);
        add(optionsPanel);

        gbc.gridx = 0;
        gbc.gridy =  2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbl.setConstraints(labelValue, gbc);
        add(labelValue);

        gbc.gridx = 0;
        gbc.gridy =  3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbl.setConstraints(paneRegisterDetails, gbc);
        add(paneRegisterDetails);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbl.setConstraints(scrollPane, gbc);
        add(scrollPane);

        // this dummy label is just to fill up the remaining space in the ui layout

        JLabel dummy = new JLabel("");
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbl.setConstraints(dummy, gbc);
        add(dummy);


        descriptionTable.setModel(registerDescriptionModel);
        descriptionTable.setFocusable(false);
        descriptionTable.setRowSelectionAllowed(false);
        UiUtil.setColumnWidths(descriptionTable,50,120,800);
        ((DefaultTableCellRenderer)descriptionTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
        descriptionTable.setDefaultRenderer(Long.class, new ValueRenderer());

        radioVic4.setSelected(true);
        this.vicFilter = "VIC-IV";
        registersService.loadRegistersVic4();


        descriptionTable.updateUI();

        textRegister.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String value = textRegister.getText().trim();
                    refreshRegister(value);
                }
            }
        });



    }


    public void refreshRegister(String input) {
        if (Util.isHex(input)) {

            SwingUtilities.invokeLater( () -> {
                String friendly = Util.toHex(Util.fromHex(input),4);
                String my = friendly;
                if (checkCpuContext.isSelected()) {
                    my = "777" + my;
                }
                Long registerDec = memoryService.peek(Util.fromHex(my));
                String registerBinary = "%" + Util.toBinary(registerDec);
                String registerHex = "$" + Util.toHex(registerDec, 2);
                labelValue.setText(registerBinary + "     " + registerHex + "      " + registerDec );

                Register reg = registersService.getRegister(friendly);
                paneRegisterDetails.setText(buildHtml(reg, registerDec));

                registerDescriptionModel.setRegister(reg);
                registerDescriptionModel.setValue(registerDec);
                descriptionTable.setModel(registerDescriptionModel);
                descriptionTable.updateUI();


            });

        } else {
            logger.error("keine Hexeingabe: " + input);
        }

    }

    private String buildHtml(Register reg, Long value) {

        String col;

        String result = """
                <html>
                    <table border=0 style="width: 100%" cellspacing=3 cellpadding=0>
                        <tr>
                            <th>HEX</th>
                            <th>DEC</th>
                            <th>DB7</th>
                            <th>DB6</th>
                            <th>DB5</th>
                            <th>DB4</th>
                            <th>DB3</th>
                            <th>DB2</th>
                            <th>DB1</th>
                            <th>DB0</th>
                        </tr>
                        <tr >
                            <td><font size=3><center>_HEX</font></center></td>
                            <td><font size=3><center>_DEC</font></center></td>
                            <td bgcolor=_7 color=GREY><font size=3><center>_DB7<br><b>_V7</b></font></center></td>
                            <td bgcolor=_6 color=GREY><font size=3><center>_DB6<br><b>_V6</b></font></center></td>
                            <td bgcolor=_5 color=GREY><font size=3><center>_DB5<br><b>_V5</b></font></center></td>
                            <td bgcolor=_4 color=GREY><font size=3><center>_DB4<br><b>_V4</b></font></center></td>
                            <td bgcolor=_3 color=GREY><font size=3><center>_DB3<br><b>_V3</b></font></center></td>
                            <td bgcolor=_2 color=GREY><font size=3><center>_DB2<br><b>_V2</b></font></center></td>
                            <td bgcolor=_1 color=GREY><font size=3><center>_DB1<br><b>_V1</b></font></center></td>
                            <td bgcolor=_0 color=GREY><font size=3><center>_DB0<br><b>_V0</b></font></center></td>
                        </tr>
           
                        
                    </table>
                </html>
                """;

        result = result.replaceAll("GREY", ColorHolder.darkGrey3);

        if (reg.hex().equals("-")) {
            result = result.replaceAll("_HEX", "");
            result = result.replaceAll("_DEC", "");

        } else {
            result = result.replaceAll("_HEX", reg.hex().toUpperCase());
            result = result.replaceAll("_DEC", Long.toString(Util.fromHex(reg.hex())));

        }

        result = result.replaceAll("_DB7", reg.db7());
        result = result.replaceAll("_V7", registersService.getBit(RegistersService.DB7, value));
        result = result.replaceAll("_7", registersService.checkBit(RegistersService.DB7, value) ? ColorHolder.green : ColorHolder.red);

        result = result.replaceAll("_DB6", reg.db6());
        result = result.replaceAll("_V6", registersService.getBit(RegistersService.DB6, value));
        result = result.replaceAll("_6", registersService.checkBit(RegistersService.DB6, value) ? ColorHolder.green : ColorHolder.red);

        result = result.replaceAll("_DB5", reg.db5());
        result = result.replaceAll("_V5", registersService.getBit(RegistersService.DB5, value));
        result = result.replaceAll("_5", registersService.checkBit(RegistersService.DB5, value) ? ColorHolder.green : ColorHolder.red);

        result = result.replaceAll("_DB4", reg.db4());
        result = result.replaceAll("_V4", registersService.getBit(RegistersService.DB4, value));
        result = result.replaceAll("_4", registersService.checkBit(RegistersService.DB4, value) ? ColorHolder.green : ColorHolder.red);

        result = result.replaceAll("_DB3", reg.db3());
        result = result.replaceAll("_V3", registersService.getBit(RegistersService.DB3, value));
        result = result.replaceAll("_3", registersService.checkBit(RegistersService.DB3, value) ? ColorHolder.green : ColorHolder.red);

        result = result.replaceAll("_DB2", reg.db2());
        result = result.replaceAll("_V2", registersService.getBit(RegistersService.DB2, value));
        result = result.replaceAll("_2", registersService.checkBit(RegistersService.DB2, value) ? ColorHolder.green : ColorHolder.red);

        result = result.replaceAll("_DB1", reg.db1());
        result = result.replaceAll("_V1", registersService.getBit(RegistersService.DB1, value));
        result = result.replaceAll("_1", registersService.checkBit(RegistersService.DB1, value) ? ColorHolder.green : ColorHolder.red);

        result = result.replaceAll("_DB0", reg.db0());
        result = result.replaceAll("_V0", registersService.getBit(RegistersService.DB0, value));
        result = result.replaceAll("_0", registersService.checkBit(RegistersService.DB0, value) ? ColorHolder.green : ColorHolder.red);

        return result;

    }

    private class ValueRenderer extends DefaultTableCellRenderer {


        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (null == value) {
                setText(" ");
            } else {
                boolean onOff = false;
                Long v = (Long) value;
                switch (row) {
                    case 0:
                        setText("DB7");
                        onOff = registersService.checkBit(RegistersService.DB7, v);
                        break;
                    case 1:
                        setText("DB6");
                        onOff = registersService.checkBit(RegistersService.DB6, v);
                        break;
                    case 2:
                        setText("DB5");
                        onOff = registersService.checkBit(RegistersService.DB5, v);
                        break;
                    case 3:
                        setText("DB4");
                        onOff = registersService.checkBit(RegistersService.DB4, v);
                        break;
                    case 4:
                        setText("DB3");
                        onOff = registersService.checkBit(RegistersService.DB3, v);
                        break;
                    case 5:
                        setText("DB2");
                        onOff = registersService.checkBit(RegistersService.DB2, v);
                        break;
                    case 6:
                        setText("DB1");
                        onOff = registersService.checkBit(RegistersService.DB1, v);
                        break;
                    case 7:
                        onOff = registersService.checkBit(RegistersService.DB0, v);
                        setText("DB0");
                        break;
                    default:
                        setText("?");
                }
                if (onOff) {
                    setForeground(ColorHolder.getColor(ColorHolder.green));
                } else {
                    setForeground(ColorHolder.getColor(ColorHolder.red));
                }
            }

            return this;
        }

    }

    private class VicSelectionActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton radio = (JRadioButton) e.getSource();
            if (radio.getText().contains("VIC-II")) {
                vicFilter = "VIC-II";
                registersService.loadRegistersVic2();
            }
            if (radio.getText().contains("VIC-III")) {
                vicFilter = "VIC-III";
                registersService.loadRegistersVic3();
            }
            if (radio.getText().contains("VIC-IV")) {
                vicFilter = "VIC-IV";
                registersService.loadRegistersVic4();
            }
            String value = textRegister.getText().trim();
            refreshRegister(value);
            logger.info("Changed VIC-Filter to=" + vicFilter);
        }
    }

}
