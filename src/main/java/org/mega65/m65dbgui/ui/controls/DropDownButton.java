package org.mega65.m65dbgui.ui.controls;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class DropDownButton extends JButton {
    private JPopupMenu menu;
    private boolean popupVisible;

    public DropDownButton() {
        super();
        _init();
    }

    public DropDownButton(String label) {
        super(label);
        _init();
    }

    public DropDownButton(Icon ico) {
        super(ico);
        _init();
    }

    public DropDownButton(String label, Icon ico) {
        super(label, ico);
        _init();
    }

    private void _init() {
        super.setModel(new DefaultButtonModel() {
            public boolean isPressed() {
                return isPopupVisible() || super.isPressed();
            }

            public boolean isArmed() {
                return isPopupVisible() || super.isArmed();
            }

            public boolean isRollover() {
                return isPopupVisible() || super.isRollover();
            }
        });

        popupVisible = false;

        menu = new JPopupMenu();
        menu.setOpaque(true);
        menu.addPopupMenuListener(new PopupListener(this));


        addActionListener(new PopupHandler(this, menu));
    }

    public JPopupMenu getMenu() {
        return menu;
    }

    /**
     * Even if this method is public,
     * don't use it (except you're going to create a
     * UI or something like that...)!
     */
    public void setPopupVisible(boolean isVisible) {
        popupVisible = isVisible;
    }

    public boolean isPopupVisible() {
        return popupVisible;
    }

    private class PopupHandler implements ActionListener {
        private DropDownButton button;
        private JPopupMenu menu;

        public PopupHandler(DropDownButton b, JPopupMenu m) {
            button = b;
            menu = m;
        }

        public void actionPerformed(ActionEvent e) {
            menu.show(button, 0, button.getHeight());
        }
    }

    private class PopupListener implements PopupMenuListener {
        DropDownButton button;
        public PopupListener(DropDownButton b) {
            button = b;
        }

        public void popupMenuCanceled(PopupMenuEvent e) {}
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            button.setPopupVisible(false);
        }
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            button.setPopupVisible(true);
        }
    }
}