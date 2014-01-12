package com.googlecode.vfsjfilechooser2.filechooser;

import org.apache.commons.io.FilenameUtils;

/* A file filter which accepts file patterns containing
 * the special wildcards *? on Windows and *?[] on Unix.
 */
public class GlobFileFilter<FileObject> extends AbstractVFSFileFilter<FileObject> {

    private final VFSFileSystemView<FileObject> fileSystemView;
    private final String globPattern;

    public GlobFileFilter(VFSFileSystemView<FileObject> fileSystemView, String globPattern) {
        this.fileSystemView = fileSystemView;
        this.globPattern = globPattern;
    }

    @Override
    public boolean accept(FileObject f) {
        if (f == null) {
            return false;
        }
        if (fileSystemView.isTraversable(f)) {
            return true;
        }
        return FilenameUtils.wildcardMatch(fileSystemView.getName(f), globPattern);
    }

    @Override
    public String getDescription() {
        return globPattern;
    }

}
