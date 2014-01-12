package org.anarres.filechooser.vfs2;

import com.googlecode.vfsjfilechooser2.VFSException;
import com.googlecode.vfsjfilechooser2.filechooser.AbstractVFSFileSystemView;
import com.googlecode.vfsjfilechooser2.utils.VFSUtils;
import java.io.File;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.impl.DecoratedFileObject;
import org.apache.commons.vfs2.provider.local.LocalFile;

/**
 *
 * @author shevek
 */
public class CommonsVfs2FileSystemView extends AbstractVFSFileSystemView<FileObject> {

    private final FileObject[] EMPTY = new FileObject[0];

    @Override
    public Class<FileObject> getFileObjectType() {
        return FileObject.class;
    }

    @Override
    public FileObject[] newFileObjectArray(Object... files) {
        FileObject[] out = new FileObject[files.length];
        for (int i = 0; i < files.length; i++)
            out[i] = (FileObject) files[i];
        return out;
    }

    /**
     * Returns all root partitions on this system. For example, on
     * Windows, this would be the "Desktop" folder, while on DOS this
     * would be the A: through Z: drives.
     * @param fo
     * @return
     */
    @Override
    public FileObject[] getRoots(FileObject fo) {
        if (fo instanceof DecoratedFileObject) {
            fo = ((DecoratedFileObject) fo).getDecoratedFileObject();
        }

        if (fo instanceof LocalFile) {
            File[] roots = File.listRoots();
            final int count = roots.length;
            FileObject[] localRoots = new FileObject[roots.length];

            for (int i = 0; i < count; i++) {
                localRoots[i] = CommonsVfs2Utils.toFileObject(roots[i]);
            }

            return localRoots.clone();
        } // Don't cache this array, because filesystem might change
        else {
            FileObject p = CommonsVfs2Utils.getRootFileSystem(fo);

            return new FileObject[]{p};
        }
    }

    /**
     * Returns a File object constructed from the given path string.
     * @param path
     * @return
     */
    @Override
    public FileObject createFileObject(String path) {
        return CommonsVfs2Utils.resolveFileObject(path);
    }

    /**
     * Gets the list of shown (i.e. not hidden) files.
     * @param dir
     * @param useFileHiding
     * @return
     */
    @Override
    public FileObject[] getFiles(FileObject dir, boolean useFileHiding) {
        return CommonsVfs2Utils.getFiles(dir, useFileHiding);
    }

    @Override
    public FileObject createNewFolder(FileObject containingDir) throws VFSException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
