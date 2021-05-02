package com.sdk.swingui.dialogs;

import com.sdk.data.structures.StringList;
import com.sdk.swingui.Swing;
import javax.swing.*;
import java.awt.*;

public class MessageBox extends Swing {

    private Font buttonFont;
    private Font messageFont;

    private Color backgroundColor;

    private String title;
    private String message;

    private String yesButtonText;
    private String noButtonText;
    private String cancelButtonText;

    private int messageType;

    private String[] buttons;
    private StringList list;

    public MessageBox(String[] buttons, String message) {
        super();

        this.buttons = buttons;
        this.message = message;

        initDefaultValues();
    }

    /**
     * Initialize dialog values for start.
     *
     */
    private void initDefaultValues() {
        title = "No title";

        buttonFont = new Font("Tahoma", Font.PLAIN, 16);
        messageFont = new Font("Tahoma", Font.PLAIN, 16);

        backgroundColor = Color.WHITE;

        yesButtonText = "Yes";
        noButtonText = "No";
        cancelButtonText = "Cancel";

        messageType = JOptionPane.WARNING_MESSAGE;
        list = new StringList(true).add(buttons);
    }

    /**
     * Get message box buttons font.
     *
     * @return Message box buttons font.
     */
    public Font getButtonFont() {
        return buttonFont;
    }

    /**
     * Set message box buttons font.
     *
     * @param buttonFont Message box button font
     */
    public void setButtonFont(Font buttonFont) {
        this.buttonFont = buttonFont;
    }

    /**
     * Get message box label font.
     *
     * @return Message box label font.
     */
    public Font getMessageFont() {
        return messageFont;
    }

    /**
     * Set message box label font.
     *
     * @param messageFont Message box label font
     */
    public void setMessageFont(Font messageFont) {
        this.messageFont = messageFont;
    }

    /**
     * Get message background color.
     *
     * @return Message box background color.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Set message box background color.
     *
     * @param backgroundColor Message box background color.
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Get message box window title.
     *
     * @return Message box window title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set message box window title.
     *
     * @param title Message box window title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get message box message.
     *
     * @return Message box message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message box message.
     *
     * @param message Message box message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get message box Yes/No buttons text.
     *
     * @return Message box Yes/No buttons text.
     */
    public String getYesButtonText() {
        return yesButtonText;
    }

    /**
     * Set message box Yes/No buttons text.
     *
     * @param yesButtonText Message box Yes/No buttons text.
     */
    public void setYesButtonText(String yesButtonText) {
        this.yesButtonText = yesButtonText;
    }

    /**
     * Get message box No button text.
     *
     * @return Message box No button text.
     */
    public String getNoButtonText() {
        return noButtonText;
    }

    /**
     * Set message box No button font.
     *
     * @param noButtonText Message box No button font.
     */
    public void setNoButtonText(String noButtonText) {
        this.noButtonText = noButtonText;
    }

    /**
     * Get message box cancel button text.
     *
     * @return Message box cancel button text.
     */
    public String getCancelButtonText() {
        return cancelButtonText;
    }

    /**
     * Set message box cancel button text.
     *
     * @param cancelButtonText Message box cancel button text.
     */
    public void setCancelButtonText(String cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
    }

    /**
     * Get message box message type.
     *
     * @return Message box message type.
     */
    public int getMessageType() {
        return messageType;
    }

    /**
     * Set message box message type.
     *
     * @param messageType Message box message type.
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    /**
     * Adds new button to message dialog.
     *
     * @param value New button value.
     * @return Message box object
     */
    public MessageBox addButton(String value) {
        list.add(value).toArray();
        return this;
    }

    /**
     * Remove a button from message dialog.
     *
     * @param value Value to remove button.
     * @return Message box object
     */
    public MessageBox removeButton(String value) {
        list.remove(value);
        return this;
    }

    /**
     * Show the message box with configs.
     *
     * @return The result of selection button (0,1,2,3,4,5,...)
     */
    public int show() {
        UIManager.put("OptionPane.buttonFont", buttonFont);
        UIManager.put("OptionPane.messageFont", messageFont);
        UIManager.put("OptionPane.yesButtonText", yesButtonText);
        UIManager.put("OptionPane.noButtonText", noButtonText);
        UIManager.put("OptionPane.cancelButtonText", cancelButtonText);

        return JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.NO_OPTION, messageType, null, list.toArray(), null);
    }
}
