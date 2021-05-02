package com.sdk.storage.file;

import com.sdk.storage.base.FileOperation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageFile extends FileOperation {

    public ImageFile(String path) {
        super(path);
    }

    /**
     * Get the width of image.
     *
     * @exception IOException File problems.
     * @return The image width.
     */
    public int getWidth() throws IOException {
        BufferedImage bimg = ImageIO.read(getFile());
        return bimg.getWidth();
    }

    /**
     * Get the height of image.
     *
     * @exception IOException File problems.
     * @return The image height.
     */
    public int getHeight() throws IOException {
        BufferedImage bimg = ImageIO.read(getFile());
        return bimg.getHeight();
    }
}
