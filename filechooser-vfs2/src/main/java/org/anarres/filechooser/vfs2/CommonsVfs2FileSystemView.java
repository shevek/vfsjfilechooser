package org.anarres.filechooser.vfs2;

import com.googlecode.vfsjfilechooser2.VFSException;
import com.googlecode.vfsjfilechooser2.filechooser.AbstractVFSFileSystemView;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;
import org.apache.commons.vfs2.CacheStrategy;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.impl.DecoratedFileObject;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.local.LocalFile;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

/**
 *
 * @author shevek
 */
public class CommonsVfs2FileSystemView extends AbstractVFSFileSystemView<FileObject> {

    private static FileSystemManager newFileSystemManager() throws FileSystemException {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        manager.setCacheStrategy(CacheStrategy.MANUAL);
        manager.init();
        return manager;
    }

    private static FileSystemOptions newFileSystemOptions() throws FileSystemException {
        FileSystemOptions opts = new FileSystemOptions();
        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
        return opts;
    }

    private static final FileObject[] EMPTY = new FileObject[0];
    private final FileSystemManager fileSystemManager;
    private final FileSystemOptions fileSystemOptions;

    public CommonsVfs2FileSystemView(FileSystemManager fileSystemManager, FileSystemOptions fileSystemOptions) {
        this.fileSystemManager = fileSystemManager;
        this.fileSystemOptions = fileSystemOptions;
    }

    public CommonsVfs2FileSystemView() throws FileSystemException {
        this(newFileSystemManager(), newFileSystemOptions());
    }

    @Override
    public Class<FileObject> getFileObjectType() {
        return FileObject.class;
    }

    @Override
    public FileObject[] newFileObjectArray(Object... files) {
        if (files.length == 0)
            return EMPTY;
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
        if (fo instanceof DecoratedFileObject)
            fo = ((DecoratedFileObject) fo).getDecoratedFileObject();

        try {
            if (fo instanceof LocalFile) {
                File[] roots = File.listRoots();
                FileObject[] localRoots = new FileObject[roots.length];
                for (int i = 0; i < roots.length; i++)
                    localRoots[i] = fileSystemManager.toFileObject(roots[i]);
                return localRoots;
            } // Don't cache this array, because filesystem might change
            else {
                FileObject p = fo.getFileSystem().getRoot();
                return new FileObject[]{p};
            }
        } catch (FileSystemException e) {
            return EMPTY;
        }
    }

    /**
     * Returns a File object constructed from the given path string.
     * @param path
     * @return
     */
    @Override
    public FileObject createFileObject(String path) {
        try {
            return fileSystemManager.resolveFile(path, fileSystemOptions);
        } catch (FileSystemException ex) {
            return null;
        }
    }

    /**
     * Returns a File object constructed in dir from the given filename.
     * @param dir
     * @param filename
     * @return
     */
    @Override
    public FileObject createFileObject(FileObject dir, String filename) {
        if (dir == null) {
            return createFileObject(filename);
        } else {
            try {
                return dir.resolveFile(filename);
            } catch (FileSystemException ex) {
                return null;
            }
        }
    }

    /**
     * Gets the list of shown (i.e. not hidden) files.
     * @param dir
     * @param useFileHiding
     * @return
     */
    @Override
    public FileObject[] getChildren(FileObject dir, boolean useFileHiding) {
        try {
            FileObject[] children = dir.getChildren();
            if (!useFileHiding) {
                List<FileObject> files = new ArrayList<FileObject>(children.length);
                for (FileObject child : files)
                    if (!isHiddenFile(child))
                        files.add(child);
                children = files.toArray(EMPTY);
            }
            return children;
        } catch (FileSystemException e) {
            return EMPTY;
        }
    }

    /**
     * Returns the parent directory of <code>dir</code>.
     * @param dir the <code>File</code> being queried
     * @return the parent directory of <code>dir</code>, or
     *   <code>null</code> if <code>dir</code> is <code>null</code>
     */
    @Override
    public FileObject getParentDirectory(FileObject dir) {
        try {
            return dir.getParent();
        } catch (FileSystemException e) {
            return null;
        }
    }

    /**
     * Creates a new folder with a default folder name.
     */
    @Override
    public FileObject createNewFolder(FileObject containingDir)
            throws VFSException {
        if (containingDir == null) {
            throw new VFSException(
                    "Trying to create a new folder into a non existing folder");
        }

        FileObject newFolder = null;

        // Using NT's default folder name
        newFolder = createFileObject(containingDir, newFolderString);

        // avoid creating a folder called New Folder so we loop as in the 
        // Windows FileSystemView
        for (int i = 1; exists(newFolder); i++) {
            newFolder = createFileObject(containingDir,
                    MessageFormat.format(newFolderNextString, new Object[]{i}));
        }

        // if the folder already exists throw an exception
        if (exists(newFolder))
            throw new VFSException("Directory already exists:" + getUrl(newFolder));
        try {
            // create the folder 
            newFolder.createFolder();
        } catch (FileSystemException e) {
            throw new VFSException(e);
        }

        // return the created folder if no exception is thrown
        return newFolder;
    }

    @Override
    public long getSize(FileObject f) {
        try {
            if (!isFile(f))
                return 0;
            return f.getContent().getSize();
        } catch (FileSystemException ex) {
            return 0;
        }
    }

    @Override
    public long getLastModifiedTime(FileObject f) {
        try {
            if (!isFile(f))
                return 0;
            return f.getContent().getLastModifiedTime();
        } catch (FileSystemException e) {
            return 0;
        }
    }

    @Override
    public boolean isWritable(FileObject f) {
        try {
            return f.isWriteable();
        } catch (FileSystemException ex) {
            return false;
        }
    }

    public boolean isFile(FileObject f) {
        try {
            return f.getType().hasContent();
        } catch (FileSystemException ex) {
            return false;
        }
    }

    public boolean isDirectory(FileObject f) {
        try {
            return f.getType().hasChildren();
        } catch (FileSystemException ex) {
            return false;
        }
    }

    public boolean exists(FileObject file) {
        try {
            return file.exists();
        } catch (FileSystemException ex) {
            return false;
        }
    }

    public void rename(FileObject from, FileObject to) throws VFSException {
        try {
            from.moveTo(to);
        } catch (FileSystemException e) {
            throw new VFSException(e);
        }
    }

    public String getUrl(FileObject f) {
        return f.getName().getURI();
    }

    public String getName(FileObject f) {
        return f.getName().getBaseName();
    }

    @Override
    public FileObject getDefaultDirectory() {
        FileSystemView fw = FileSystemView.getFileSystemView();
        try {
            return fileSystemManager.toFileObject(fw.getDefaultDirectory());
        } catch (FileSystemException e) {
            return createFileObject(System.getProperty("user.dir"));
        }
    }

    @Override
    public FileObject getHomeDirectory() {
        FileSystemView fw = FileSystemView.getFileSystemView();
        try {
            return fileSystemManager.toFileObject(fw.getHomeDirectory());
        } catch (FileSystemException e) {
            return createFileObject(System.getProperty("user.home"));
        }
    }

}
