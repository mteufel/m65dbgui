package org.mega65.m65dbgui.ui.toolbars;

import com.formdev.flatlaf.util.SystemInfo;
import jakarta.inject.Inject;
import org.mega65.m65dbgui.services.IconService;
import org.mega65.m65dbgui.ui.listeners.*;

import javax.swing.*;

public class MainToolbar extends JToolBar {

    @Inject
    public MainToolbar(IconService iconService, ConnectDisconnectActionListener connectDisconnectActionListener, RefreshActionListener refreshActionListener,
                       OpenDisassemblyActionListener addDisassemblyActionListener,
                       ResetActionListener resetActionListener,
                       Test1ActionListener test1ActionListener, Test2ActionListener test2ActionListener) {

        if (SystemInfo.isMacFullWindowContentSupported) {
            add( Box.createHorizontalStrut(70),0);
        }

        JButton buttonConnect = new JButton(iconService.getIconDisonneced());
        buttonConnect.addActionListener(connectDisconnectActionListener);
        buttonConnect.setToolTipText("Connect/Disconnect XEMU");
        add(buttonConnect);

        JButton buttonReset = new JButton(iconService.getReset());
        buttonReset.addActionListener(resetActionListener);
        buttonReset.setToolTipText("Reset XEMU");
        add(buttonReset);

        addSeparator();

        JButton buttonRefresh = new JButton(iconService.getRefresh());
        buttonRefresh.addActionListener(refreshActionListener);
        add(buttonRefresh);

        addSeparator();

        JButton buttonAddDisassembly = new JButton(iconService.getDissassebly());
        buttonAddDisassembly.addActionListener(addDisassemblyActionListener);
        add(buttonAddDisassembly);

        addSeparator();

        JButton buttonTest1 = new JButton(iconService.getFirst());
        buttonTest1.addActionListener(test1ActionListener);
        add(buttonTest1);

        JButton buttonTest2 = new JButton(iconService.getLast());
        buttonTest2.addActionListener(test2ActionListener);
        add(buttonTest2);

    }
}
