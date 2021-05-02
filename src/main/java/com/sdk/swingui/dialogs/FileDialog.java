package com.sdk.swingui.dialogs;

import com.sdk.data.types.Strings;
import com.sdk.swingui.Swing;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.plaf.FileChooserUI;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.Arrays;

public class FileDialog extends Swing {

    private String title;
    private String startPath;

    private FileNameExtensionFilter[] filters;
    private JFileChooser fileChooser;

    public FileDialog(String title, String startPath) {
        super();

        this.startPath = startPath;
        this.title = title;
        this.fileChooser = new JFileChooser();

        fileChooser.setDialogTitle(title);
        if (!startPath.isEmpty()) {
            fileChooser.setCurrentDirectory(new File(startPath));
        }
    }

    /**
     * Get dialog title.
     *
     * @return Dialog title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set dialog title.
     *
     * @param title Dialog title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get dialog start path.
     *
     * @return Dialog start path.
     */
    public String getStartPath() {
        return startPath;
    }

    /**
     * Set dialog start path.
     *
     * @param startPath Dialog start path.
     */
    public void setStartPath(String startPath) {
        this.startPath = startPath;
    }

    /**
     * Get dialog filters.
     *
     * @return Dialog title.
     */
    public FileNameExtensionFilter[] getFilters() {
        return filters;
    }

    /**
     * Set dialog filters.
     *
     * @param filters Dialog filters.
     */
    public void setFilters(FileNameExtensionFilter[] filters) {
        this.filters = filters;
    }

    /**
     * Show a save dialog to save a file.
     *
     * @return Selected file.
     */
    public File showSaveDialog() {
        if (!Objects.isNull(filters)) {
            Objects.requireNonNull(fileChooser);
            Arrays.stream(filters).forEach(fileChooser::addChoosableFileFilter);
        }

        if (!new Strings().isNullOrEmpty(startPath)) {
            fileChooser.setCurrentDirectory(new File(startPath));
        }

        return fileChooser.showSaveDialog(null) == 0 ? fileChooser.getSelectedFile() : null;
    }

    /**
     * Show an open dialog to open a file.
     *
     * @return Selected file.
     */
    public File showOpenDialog() {
        if (!Objects.isNull(filters)) {
            Objects.requireNonNull(fileChooser);
            Arrays.stream(filters).forEach(fileChooser::addChoosableFileFilter);
        }

        if (!new Strings().isNullOrEmpty(startPath)) {
            fileChooser.setCurrentDirectory(new File(startPath));
        }

        return fileChooser.showOpenDialog(null) == 0 ? fileChooser.getSelectedFile() : null;
    }

    /**
     * Show a directory select dialog.
     *
     * @return Selected directory file.
     */
    public File selectDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (!new Strings().isNullOrEmpty(startPath)) {
            fileChooser.setCurrentDirectory(new File(startPath));
        }

        return fileChooser.showOpenDialog(null) == 0 ? fileChooser.getSelectedFile() : null;
    }
}
