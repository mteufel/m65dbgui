package org.mega65.m65dbgui.util;

import org.mega65.m65dbgui.services.IconService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class UiUtil {

    public static void setColumnWidths(JTable table, int... widths) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length; i++) {
            if (i < columnModel.getColumnCount()) {
                columnModel.getColumn(i).setMaxWidth(widths[i]+1);
                columnModel.getColumn(i).setPreferredWidth(widths[i]+1);
            }
            else break;
        }
    }

    public static void showOnScreen( int screen, Window frame ) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        GraphicsDevice graphicsDevice;
        if( screen > -1 && screen < gd.length ) {
            graphicsDevice = gd[screen];
        } else if( gd.length > 0 ) {
            graphicsDevice = gd[0];
        } else {
            throw new RuntimeException( "No Screens Found" );
        }
        Rectangle bounds = graphicsDevice.getDefaultConfiguration().getBounds();
        int screenWidth = graphicsDevice.getDisplayMode().getWidth();
        int screenHeight = graphicsDevice.getDisplayMode().getHeight();
        frame.setLocation(bounds.x + (screenWidth - frame.getWidth()) / 2,
                bounds.y + (screenHeight - frame.getHeight()) / 2);
        frame.setVisible(true);
    }

    public static void createClosableTab(String title, Component component, JTabbedPane pane, ActionListener actionListener) {

        IconService iconService = (IconService) DiUtil.create(IconService.class);

        pane.addTab(title, component);
        int index = pane.indexOfTab(title);

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        JButton closeButton = new JButton(iconService.getToolbarIcon("close.svg"));
        Border emptyBorder = BorderFactory.createEmptyBorder();
        closeButton.setBorder(emptyBorder);
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);

        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setIcon(iconService.getToolbarIcon("close-hovered.svg"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setIcon(iconService.getToolbarIcon("close.svg"));
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,5,0,0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        headerPanel.add(titleLabel, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        headerPanel.add(closeButton, gbc);

        pane.setTabComponentAt(index, headerPanel);
        closeButton.addActionListener( e -> {
            pane.remove(component);
        });
        if (actionListener != null) {
            closeButton.addActionListener(actionListener);
        }

    }

}
