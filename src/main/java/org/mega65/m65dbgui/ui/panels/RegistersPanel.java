package org.mega65.m65dbgui.ui.panels;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.events.GenericEvent;
import org.mega65.m65dbgui.operations.M65Registers;
import org.mega65.m65dbgui.operations.Operation;
import org.mega65.m65dbgui.services.OpcodeService;
import org.mega65.m65dbgui.util.ColorHolder;
import org.mega65.m65dbgui.ui.controls.RegisterLabel;
import org.mega65.m65dbgui.util.DiUtil;
import org.mega65.m65dbgui.util.Receiver;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RegistersPanel extends JPanel implements Receiver {

    RegisterLabel registerDataLabel;
    RegisterLabel registerTitleLabel;
    State state;
    OpcodeService opcodeService;

    @Inject
    public RegistersPanel(State state, RegisterLabel registerTitleLabel, RegisterLabel registerDataLabel, OpcodeService opcodeService) {

        this.state = state;
        this.registerTitleLabel = registerTitleLabel;
        this.registerDataLabel = registerDataLabel;
        this.opcodeService = opcodeService;

        Border border = BorderFactory.createTitledBorder("Registers");
        setBorder(border);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.registerTitleLabel.setText("");
        this.registerTitleLabel.setForeground(ColorHolder.getColor(ColorHolder.blue));

        // register colors, use multicolor in order to give the op code a separate color on the ui
        this.registerDataLabel.setText("");
        this.registerDataLabel.setColors(new int[]{0,38,41}, new Color[]{ColorHolder.getColor(ColorHolder.purple), ColorHolder.getColor(ColorHolder.darkGrey), ColorHolder.getColor(ColorHolder.purple) } );

        add(this.registerTitleLabel);
        add(this.registerDataLabel);

        state.addReceiver(this);


    }

    @Override
    public void update(Operation operation) {

        if (operation instanceof M65Registers) {
            M65Registers m65Registers = (M65Registers) operation;

            if (m65Registers.isEmpty())
                return;


            SwingUtilities.invokeLater( () -> {


                String registerContent = "1111 22 33 44 55 66 7777 8888 9999 AA EEE   BB CC DDDDDDDD";
                registerContent = registerContent.replaceAll("1111", m65Registers.getPc());
                registerContent = registerContent.replaceAll("22", m65Registers.getAr());
                registerContent = registerContent.replaceAll("33", m65Registers.getXr());
                registerContent = registerContent.replaceAll("44", m65Registers.getYr());
                registerContent = registerContent.replaceAll("55", m65Registers.getZr());
                registerContent = registerContent.replaceAll("66", m65Registers.getBp());
                registerContent = registerContent.replaceAll("7777", m65Registers.getSp());
                registerContent = registerContent.replaceAll("8888", m65Registers.getMapl());
                registerContent = registerContent.replaceAll("9999", m65Registers.getMaph());
                registerContent = registerContent.replaceAll("AA", m65Registers.getLastOp());
                registerContent = registerContent.replaceAll("BB", m65Registers.getUnknown());
                registerContent = registerContent.replaceAll("CC", m65Registers.getSr());

                String flags = "";
                flags = flags + m65Registers.toNumeralString(m65Registers.isN());
                flags = flags + m65Registers.toNumeralString(m65Registers.isV());
                flags = flags + m65Registers.toNumeralString(m65Registers.isE());
                flags = flags + m65Registers.toNumeralString(m65Registers.isB());
                flags = flags + m65Registers.toNumeralString(m65Registers.isD());
                flags = flags + m65Registers.toNumeralString(m65Registers.isI());
                flags = flags + m65Registers.toNumeralString(m65Registers.isZ());
                flags = flags + m65Registers.toNumeralString(m65Registers.isC());
                registerContent = registerContent.replaceAll("DDDDDDDD", flags);

                registerContent = registerContent.replaceAll("EEE", opcodeService.getOpcodeByCode(m65Registers.getLastOp()).getInstruction());


                registerTitleLabel.setText(m65Registers.getRegisterTitle());
                registerDataLabel.setText(registerContent);
                updateUI();

            });


            // refresh Mapping, this event will be catched in MemoryService which holds a Method
            // to get the actual Mapping
            GenericEvent event = new GenericEvent(GenericEvent.GENERIC_EVENT_MAPPING_REFRESH);
            event.setData(m65Registers);
            DiUtil.fireEvent(event);


        }

    }


}
