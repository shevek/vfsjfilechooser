package com.googlecode.vfsjfilechooser2.filechooser;

import javax.swing.Icon;

/**
 * The L&F overrideable parts of VFSFileSystemView.
 *
 * @author shevek
 */
public interface VFSFileView<FileObject> {

    /**
     * A human readable description of the file. For example,
     * a file named <i>jag.jpg</i> might have a description that read:
     * "A JPEG image file of James Gosling's face".
     * @param f
     * @return
     */
    String getDescription(FileObject f);

    /**
     * The icon that represents this file in the <code>JFileChooser</code>.
     * @param f
     * @return
     */
    Icon getIcon(FileObject f);

    /**
     * The name of the file. Normally this would be simply
     * <code>f.getName()</code>.
     * @param f
     * @return
     */
    String getName(FileObject f);

    /**
     * A human readable description of the type of the file. For
     * example, a <code>jpg</code> file might have a type description of:
     * "A JPEG Compressed Image File"
     * @param f
     * @return
     */
    String getTypeDescription(FileObject f);

    /**
     * Whether the directory is traversable or not. This might be
     * useful, for example, if you want a directory to represent
     * a compound document and don't want the user to descend into it.
     * @param f
     * @return
     */
    Boolean isTraversable(FileObject f);

}
