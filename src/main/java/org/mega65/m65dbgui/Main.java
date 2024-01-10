package org.mega65.m65dbgui;

import com.formdev.flatlaf.util.SystemInfo;
import org.mega65.m65dbgui.util.Util;
import org.mega65.m65dbgui.ui.MainFrame;
import org.slf4j.Logger;

import javax.swing.*;

import static org.mega65.m65dbgui.util.DiUtil.create;

public class Main {
    public static void main( String[] args )  {

        Util.configureLogging();
        Logger logger = Util.getLogger(Main.class);

        logger.info("============================== M65DBG-UI START ==============================");

        SwingUtilities.invokeLater( () -> {

            if( SystemInfo.isLinux ) {
                // enable custom window decorations
                JFrame.setDefaultLookAndFeelDecorated( true );
                JDialog.setDefaultLookAndFeelDecorated( true );
            }

            MainFrame mainFrame = (MainFrame) create(MainFrame.class);

            try {
                mainFrame.configureLookAndFeel();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            if( SystemInfo.isMacFullWindowContentSupported ) {
                System.setProperty( "apple.awt.application.appearance", "system" );
                mainFrame.getRootPane().putClientProperty( "apple.awt.fullWindowContent", true );
                mainFrame.getRootPane().putClientProperty( "apple.awt.transparentTitleBar", true );
            }




        } );

    }
}
