package com.sdk.storage.file;

public class SingleFile extends TextFile {

    private ImageFile imageFile;
    private AudioFile audioFile;

    public SingleFile(String path) {
        super(path);

        imageFile = new ImageFile(path);
        audioFile = new AudioFile(path);
    }

    /**
     * Get ImageFile instance.
     *
     * @return The ImageFile object.
     */
    public ImageFile getImageFile() {
        return imageFile;
    }

    /**
     * Get AudioFile instance.
     *
     * @return The AudioFile object.
     */
    public AudioFile getAudioFile() {
        return audioFile;
    }
}
