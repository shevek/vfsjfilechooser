package org.anarres.filechooser.vfs2;

import com.googlecode.vfsjfilechooser2.VFSJFileChooser;
import javax.annotation.Nonnull;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;

/**
 *
 * @author shevek
 */
public class CommonsVfs2JFileChooser extends VFSJFileChooser<FileObject> {

    public CommonsVfs2JFileChooser(@Nonnull FileSystemManager fileSystemManager, @Nonnull FileSystemOptions fileSystemOptions) {
        super(new CommonsVfs2FileSystemView(fileSystemManager, fileSystemOptions));
    }

    public CommonsVfs2JFileChooser() throws FileSystemException {
        super(new CommonsVfs2FileSystemView());
    }

}
