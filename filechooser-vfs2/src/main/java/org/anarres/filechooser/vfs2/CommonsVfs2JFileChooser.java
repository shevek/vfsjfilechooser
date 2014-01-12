package org.anarres.filechooser.vfs2;

import com.googlecode.vfsjfilechooser2.VFSJFileChooser;
import org.apache.commons.vfs2.FileObject;

/**
 *
 * @author shevek
 */
public class CommonsVfs2JFileChooser extends VFSJFileChooser<FileObject> {

    public CommonsVfs2JFileChooser() {
        super(new CommonsVfs2FileSystemView());
    }

}
