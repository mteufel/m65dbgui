package org.mega65.m65dbgui.ui.controls.memory.editor;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.services.FontService;
import org.mega65.m65dbgui.util.ColorHolder;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.security.Key;
import java.util.EventObject;

public class ByteCellEditor extends AbstractCellEditor implements TableCellEditor {

    Logger logger = Util.getLogger(ByteCellEditor.class);
    private final State state;
    protected JTextField textField;
    protected EditorDelegate delegate;
    protected int clickCountToStart = 1;


    @Inject
    public ByteCellEditor(State state, FontService fontService) {
        this.state = state;
        this.textField = new JTextField();

        textField.setFont(fontService.getMonoSmallFont());
        textField.setForeground(ColorHolder.getColor(ColorHolder.lightGrey));
        textField.setCaretColor(ColorHolder.getColor(ColorHolder.lightGrey));
        textField.setBorder(BorderFactory.createLineBorder(ColorHolder.getColor(ColorHolder.darkGrey), 2));

        this.clickCountToStart = 2;
        this.delegate = new EditorDelegate() {
            public void setValue(Object value) {
                textField.setText(value != null ? value.toString() : "");
            }

            public Object getCellEditorValue() {
                return textField.getText();
            }
        };

        textField.addFocusListener( new FocusAdapter()
        {
            public void focusGained( final FocusEvent e )
            {
                textField.selectAll();
            }
        } );
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (textField.getSelectedText() != null) {
                    if (textField.getText().length() == 2 && textField.getSelectedText().equals(textField.getText())) {
                        textField.setText(""+ evt.getKeyChar());
                        evt.consume();
                    }
                }

                if(textField.getText().length() >= 2 &&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        state.setPause(true);
        ToolTipManager.sharedInstance().setEnabled(false);
        Byte by = (Byte) value;
        this.delegate.setValue(Util.toHex(by.getValue()));
        this.textField.setOpaque(false);
        table.setSelectionBackground(table.getBackground());
        textField.addActionListener(this.delegate);
        return this.textField;
    }

    @Override
    public Object getCellEditorValue() {
        //state.setPause(false);
        ToolTipManager.sharedInstance().setEnabled(true);
        return this.delegate.getCellEditorValue();
    }


    public boolean isCellEditable(EventObject anEvent) {
        return this.delegate.isCellEditable(anEvent);
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        return this.delegate.shouldSelectCell(anEvent);
    }

    public boolean stopCellEditing() {
        return this.delegate.stopCellEditing();
    }

    public void cancelCellEditing() {
        this.delegate.cancelCellEditing();
    }



    protected class EditorDelegate implements ActionListener, ItemListener, Serializable {
        protected Object value;

        protected EditorDelegate() {
        }

        public Object getCellEditorValue() {
            return this.value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public boolean isCellEditable(EventObject anEvent) {
            if (anEvent instanceof ActionEvent) {
                return true;
            }
            if (anEvent instanceof MouseEvent) {
                return ((MouseEvent)anEvent).getClickCount() >= ByteCellEditor.this.clickCountToStart;
            } else {
                return false;
            }
        }

        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

        public boolean startCellEditing(EventObject anEvent) {
            return true;
        }

        public boolean stopCellEditing() {
            ByteCellEditor.this.fireEditingStopped();
            return true;
        }

        public void cancelCellEditing() {
            ByteCellEditor.this.fireEditingCanceled();
        }

        public void actionPerformed(ActionEvent e) {
            ByteCellEditor.this.stopCellEditing();
        }

        public void itemStateChanged(ItemEvent e) {
            ByteCellEditor.this.stopCellEditing();
        }
    }


}
