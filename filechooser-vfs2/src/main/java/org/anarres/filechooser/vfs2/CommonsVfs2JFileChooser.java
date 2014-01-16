package org.anarres.filechooser.vfs2;

import com.googlecode.vfsjfilechooser2.VFSJFileChooser;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;

/**
 *
 * @author shevek
 */
public class CommonsVfs2JFileChooser extends VFSJFileChooser<FileObject> {

    public CommonsVfs2JFileChooser() throws FileSystemException {
        super(new CommonsVfs2FileSystemView());
    }

}
