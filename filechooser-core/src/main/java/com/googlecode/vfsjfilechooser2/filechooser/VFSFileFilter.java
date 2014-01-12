package com.googlecode.vfsjfilechooser2.filechooser;

/**
 *
 * @author shevek
 */
public interface VFSFileFilter<FileObject> {

    /**
     * Whether the given file is accepted by this filter.
     * @param f
     * @return
     */
    boolean accept(FileObject f);

    /**
     * The description of this filter. For example: "JPG and GIF Images"
     * @return
     * @see FileView#getName
     */
    String getDescription();

}
