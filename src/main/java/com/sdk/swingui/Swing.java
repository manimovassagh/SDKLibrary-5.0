package com.sdk.swingui;

import com.sdk.data.structures.StringList;
import com.sdk.data.types.Strings;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class Swing implements GraphicUser {

    public Swing() {
    }

    public Swing(boolean enableSystemLookAndFeel) {
        if (enableSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setFrameCenter(JFrame frame) {
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void setDialogCenter(JDialog dialog) {
        dialog.pack();
        dialog.setLocationRelativeTo(null);
    }

    @Override
    public void setFrameCloseEsc(JFrame frame) {
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");

        frame.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    @Override
    public void setDialogCloseEsc(JDialog dialog) {
        dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");

        dialog.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }

    @Override
    public void makeDialogMovable(JDialog dialog) {
        Point point = new Point();

        dialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });

        dialog.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = dialog.getLocation();
                dialog.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });
    }

    @Override
    public void makeFrameMovable(JFrame frame) {
        Point point = new Point();

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });
    }

    @Override
    public void setTableDataModel(DefaultTableModel model, JTable table) {
        table.setModel(model);
    }

    @Override
    public int countTableRows(JTable table) {
        return table.getRowCount();
    }

    @Override
    public int countTableColumns(JTable table) {
        return table.getColumnCount();
    }

    @Override
    public void clearTable(JTable table, boolean columns) {
        try {
            if (columns) {
                table.setModel(new DefaultTableModel());
            } else {
                DefaultTableModel dm = (DefaultTableModel) table.getModel();
                while (dm.getRowCount() > 0) {
                    dm.removeRow(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean printTable(JTable table, String title, String format) {
        try {
            if (!new Strings().isNullOrEmpty(format)) {
                MessageFormat footer = new MessageFormat(format);
                return table.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat(title), footer);
            } else {
                return table.print(JTable.PrintMode.FIT_WIDTH);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getComponentPosition(Component c) {
        Dimension dimension = c.getSize();
        return "[" + dimension.getWidth() + "," + dimension.getHeight() + "]";
    }

    @Override
    public void disableTableEdit(JTable table) {
        table.setDefaultEditor(Object.class, null);
    }

    @Override
    public void limitTextField(JTextField field, int limit) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (field.getText().length() == limit) {
                    e.consume();
                } else if (field.getText().length() > limit) {
                    field.setText(new Strings().getEmptyString());
                }
            }
        });
    }

    @Override
    public void setTextFieldInputType(JTextField field, String type) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                Strings strings = new Strings();

                if (type.equalsIgnoreCase("number")) {
                    if (!strings.isNumber(String.valueOf(e.getKeyChar()))) {
                        e.consume();
                    }
                } else if (type.equalsIgnoreCase("string") || type.equalsIgnoreCase("text")) {
                    if (!strings.isText(String.valueOf(e.getKeyChar()))) {
                        e.consume();
                    }
                }
            }
        });
    }

    @Override
    public boolean printTextArea(JTextArea area, String title, String footer) {
        try {
            if (!Objects.isNull(area)) {
                return area.print(new MessageFormat(title), new MessageFormat(footer));
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void clearComponents(JFrame frame, String component, boolean setBlackColor) {
        Arrays.asList(frame.getContentPane().getComponents()).forEach(cmp -> {
            if (component.equals("field")) {
                if (cmp instanceof JTextField) {
                    ((JTextField) cmp).setText(new Strings().getEmptyString());

                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }
            } else if (component.equals("label")) {
                if (cmp instanceof JLabel) {
                    ((JLabel) cmp).setText(new Strings().getEmptyString());

                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }
            } else if (component.equals("combo")) {
                if (cmp instanceof JComboBox) {
                    ((JComboBox) cmp).setSelectedIndex(0);

                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }
            }
        });
    }

    @Override
    public void clearComponents(JDialog dialog, String component, boolean setBlackColor) {
        Arrays.asList(dialog.getContentPane().getComponents()).forEach(cmp -> {
            if (component.equals("field")) {
                if (cmp instanceof JTextField) {
                    ((JTextField) cmp).setText(new Strings().getEmptyString());

                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }
            } else if (component.equals("label")) {
                if (cmp instanceof JLabel) {
                    ((JLabel) cmp).setText(new Strings().getEmptyString());

                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }
            } else if (component.equals("combo")) {
                if (cmp instanceof JComboBox) {
                    ((JComboBox) cmp).setSelectedIndex(0);

                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }
            }
        });
    }

    @Override
    public void clearComponents(JPanel panel, String component, boolean setBlackColor) {
        Arrays.asList(panel.getComponents()).forEach(cmp -> {
            if (component.equals("field")) {
                if (cmp instanceof JTextField) {
                    ((JTextField) cmp).setText(new Strings().getEmptyString());

                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }
            } else if (component.equals("label")) {
                if (cmp instanceof JLabel) {
                    ((JLabel) cmp).setText(new Strings().getEmptyString());

                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }
            } else if (component.equals("combo")) {
                if (cmp instanceof JComboBox) {
                    ((JComboBox) cmp).setSelectedIndex(0);

                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }
            }
        });
    }

    @Override
    public void clearList(JList list) {
        DefaultListModel model = new DefaultListModel();
        model.clear();
        list.setModel(model);
    }

    @Override
    public void addToList(JList list, String[] items) {
        try {
            if (!new Strings().isNullOrEmpty(items)) {
                ListModel listModel = list.getModel();
                DefaultListModel defaultListModel = new DefaultListModel();

                for (int j = 0; j < listModel.getSize(); j++) {
                    defaultListModel.addElement(listModel.getElementAt(j));
                }

                for (int i = 0; i < items.length; ++i) {
                    String item = items[i];
                    defaultListModel.addElement(item);
                }

                list.setModel(defaultListModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToList(JList list, String item) {
        try {
            if (!new Strings().isNullOrEmpty(item)) {
                ListModel listModel = list.getModel();
                DefaultListModel defaultListModel = new DefaultListModel();

                for (int j = 0; j < listModel.getSize(); j++) {
                    defaultListModel.addElement(listModel.getElementAt(j));
                }

                defaultListModel.addElement(item);
                list.setModel(defaultListModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Color showColorDialog(Component c, String title, Color color) {
        return JColorChooser.showDialog(c, title, Color.CYAN);
    }

    @Override
    public void setComponentDirection(Component c, String direction) {
        if (!Objects.isNull(c)) {
            byte var4 = -1;
            switch (direction.hashCode()) {
                case 3317767:
                    if (direction.equals("left")) {
                        var4 = 1;
                    }
                    break;
                case 108511772:
                    if (direction.equals("right")) {
                        var4 = 0;
                    }
            }

            switch (var4) {
                case 0:
                    c.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case 1:
                    c.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }
        }
    }

    @Override
    public void setComponentsDirection(Component[] c, String direction) {
        if (!Objects.isNull(c)) {
            Component[] var3 = c;
            int var4 = c.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Component component = var3[var5];
                byte var8 = -1;
                switch (direction.hashCode()) {
                    case 3317767:
                        if (direction.equals("left")) {
                            var8 = 1;
                        }
                        break;
                    case 108511772:
                        if (direction.equals("right")) {
                            var8 = 0;
                        }
                }

                switch (var8) {
                    case 0:
                        component.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                        break;
                    case 1:
                        component.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                }
            }
        }
    }

    @Override
    public void setComponentsDirection(JDialog dialog, String direction) {
        Arrays.asList(dialog.getComponents()).forEach((cmp) -> {
            byte var3 = -1;
            switch (direction.hashCode()) {
                case 3317767:
                    if (direction.equals("left")) {
                        var3 = 1;
                    }
                    break;
                case 108511772:
                    if (direction.equals("right")) {
                        var3 = 0;
                    }
            }

            switch (var3) {
                case 0:
                    cmp.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case 1:
                    cmp.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }

        });
    }

    @Override
    public void setComponentsDirection(JFrame frame, String direction) {
        Arrays.asList(frame.getComponents()).forEach((cmp) -> {
            byte var3 = -1;
            switch (direction.hashCode()) {
                case 3317767:
                    if (direction.equals("left")) {
                        var3 = 1;
                    }
                    break;
                case 108511772:
                    if (direction.equals("right")) {
                        var3 = 0;
                    }
            }

            switch (var3) {
                case 0:
                    cmp.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case 1:
                    cmp.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }

        });
    }

    @Override
    public void setComponentsDirection(JPanel panel, String direction) {
        Arrays.asList(panel.getComponents()).forEach((cmp) -> {
            byte var3 = -1;
            switch (direction.hashCode()) {
                case 3317767:
                    if (direction.equals("left")) {
                        var3 = 1;
                    }
                    break;
                case 108511772:
                    if (direction.equals("right")) {
                        var3 = 0;
                    }
            }

            switch (var3) {
                case 0:
                    cmp.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case 1:
                    cmp.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }

        });
    }

}
