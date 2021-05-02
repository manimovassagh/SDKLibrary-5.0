package com.sdk.swingui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public interface GraphicUser {

    /**
     * Set JFrame to the center of screen
     *
     * @param frame The frame want to be in center.
     */
    void setFrameCenter(JFrame frame);

    /**
     * Set JDialog to the center of screen
     *
     * @param dialog The dialog want to be in center.
     */
    void setDialogCenter(JDialog dialog);

    /**
     * Make JFrame sensitive to ESC key and close by ESC.
     *
     * @param frame The frame want to set.
     */
    void setFrameCloseEsc(JFrame frame);

    /**
     * Make JDialog sensitive to ESC key and close by ESC.
     *
     * @param dialog The dialog want to set.
     */
    void setDialogCloseEsc(JDialog dialog);

    /**
     * Make JDialog movable from anywhere on form.
     *
     * @param dialog The dialog want to set.
     */
    void makeDialogMovable(JDialog dialog);

    /**
     * Make JFrame movable from anywhere on form.
     *
     * @param frame The frame want to set.
     */
    void makeFrameMovable(JFrame frame);

    /**
     * Set JTable data model easily.
     *
     * @param model Specified table model.
     * @param table The JTable object.
     */
    void setTableDataModel(DefaultTableModel model, JTable table);

    /**
     * Counts JTable rows.
     *
     * @param table The JTable object.
     * @return Number of table rows.
     */
    int countTableRows(JTable table);

    /**
     * Counts JTable columns.
     *
     * @param table The JTable object.
     * @return Number of table columns.
     */
    int countTableColumns(JTable table);

    /**
     * Clear all JTable object contents including headers(optional).
     *
     * @param table The JTable object.
     * @param columns Clear columns or not.
     */
    void clearTable(JTable table, boolean columns);

    /**
     * Prints the JTable with specified title and format.
     *
     * @param table The JTable object.
     * @param title The page title.
     * @param format Specified format.
     * @return True if print was successful and false if not.
     */
    boolean printTable(JTable table, String title, String format);

    /**
     * Gets component position in ([Width, Height]) format.
     *
     * @param c The component in the form or dialog.
     * @return Component position as String.
     */
    String getComponentPosition(Component c);

    /**
     * Disables a JTable edit option.
     *
     * @param table The JTable object.
     */
    void disableTableEdit(JTable table);

    /**
     * Limit text field with number of characters.
     *
     * @param field JTextField object.
     * @param limit Number of limit characters.
     */
    void limitTextField(JTextField field, int limit);

    /**
     * Set text field input type(String or number).
     *
     * @param field JTextField object.
     * @param type Input type(String or number).
     */
    void setTextFieldInputType(JTextField field, String type);

    /**
     * Prints text area.
     *
     * @param area The JTextArea object.
     * @param title The page title.
     * @param footer The page footer.
     * @return True if print was successful and false if not.
     */
    boolean printTextArea(JTextArea area, String title, String footer);

    /**
     * Clear all components text in form.
     *
     * @param frame The JFrame object.
     * @param component Select a component(field,label,combo).
     * @param setBlackColor Sets text color to black after cleaning.
     */
    void clearComponents(JFrame frame, String component, boolean setBlackColor);

    /**
     * Clear all components text in form.
     *
     * @param dialog The JDialog object.
     * @param component Select a component(field,label,combo).
     * @param setBlackColor Sets text color to black after cleaning.
     */
    void clearComponents(JDialog dialog, String component, boolean setBlackColor);

    /**
     * Clear all components text in form.
     *
     * @param panel The JPanel object.
     * @param component Select a component(field,label,combo).
     * @param setBlackColor Sets text color to black after cleaning.
     */
    void clearComponents(JPanel panel, String component, boolean setBlackColor);

    /**
     * Clears all list items.
     *
     * @param list The JList object.
     */
    void clearList(JList list);

    /**
     * Adds multiple items to the list.
     *
     * @param list The JList object.
     * @param items The items you want to add.
     */
    void addToList(JList list, String[] items);

    /**
     * Adds single item to the list.
     *
     * @param list The JList object.
     * @param item The item you want to add.
     */
    void addToList(JList list, String item);

    /**
     * Show a color dialog and choose a color.
     *
     * @param c Parent component.
     * @param title The title of color dialog window.
     * @param color The first color you want to set after window opens.
     * @return The selected color and null if not.
     */
    Color showColorDialog(Component c, String title, Color color);

    /**
     * Sets component direction.
     *
     * @param c The component you want to set direction.
     * @param direction The direction(right/left).
     */
    void setComponentDirection(Component c, String direction);

    /**
     * Set components direction.
     *
     * @param c The components you want to set direction.
     * @param direction The direction(right/left).
     */
    void setComponentsDirection(Component[] c, String direction);

    /**
     * Set components direction in dialog.
     *
     * @param dialog The JDialog object.
     * @param direction The direction(right/left).
     */
    void setComponentsDirection(JDialog dialog, String direction);

    /**
     * Set components direction in frame.
     *
     * @param frame The JFrame object.
     * @param direction The direction(right/left).
     */
    void setComponentsDirection(JFrame frame, String direction);

    /**
     * Set components direction in panel.
     *
     * @param panel The JPanel object.
     * @param direction The direction(right/left).
     */
    void setComponentsDirection(JPanel panel, String direction);
}
