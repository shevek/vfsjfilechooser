/*
 * The implementation using commons-vfs based on Swing FileSystemView
 *
 * Copyright (C) 2005-2008 Yves Zoundi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * under the License.
 */
package com.googlecode.vfsjfilechooser2.filechooser;

import com.googlecode.vfsjfilechooser2.utils.VFSResources;
import com.googlecode.vfsjfilechooser2.utils.VFSUtils;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/**
 * The implementation using commons-vfs based on Swing FileSystemView
 * @author Yves Zoundi <yveszoundi at users dot sf dot net>
 * @version 0.0.1
 */
public abstract class AbstractVFSFileSystemView<FileObject> implements VFSFileSystemView<FileObject> {

    protected static final String newFolderString = VFSResources.getMessage(
            "VFSJFileChooser.other.newFolder");
    protected static final String newFolderNextString = VFSResources.getMessage(
            "VFSJFileChooser.other.newFolder.subsequent");

    /*
     public FileObject[] newArray(FileObject... args) {
     return null;
     }
     */

    /*
     //static FileSystemView macFileSystemView = null;
     static AbstractVFSFileSystemView genericFileSystemView = null;
     static boolean useSystemExtensionsHiding = false;

     public static AbstractVFSFileSystemView getFileSystemView() {
     useSystemExtensionsHiding = UIManager.getDefaults()
     .getBoolean("FileChooser.useSystemExtensionHiding");
     UIManager.addPropertyChangeListener(new PropertyChangeListener() {
     @Override
     public void propertyChange(PropertyChangeEvent e) {
     if (e.getPropertyName().equals("lookAndFeel")) {
     useSystemExtensionsHiding = UIManager.getDefaults()
     .getBoolean("FileChooser.useSystemExtensionHiding");
     }
     }
     });

     if (genericFileSystemView == null) {
     genericFileSystemView = new GenericFileSystemView();
     }

     return genericFileSystemView;
     }
     */
    /**
     * Determines if the given file is a root in the navigatable tree(s).
     * Examples: Windows 98 has one root, the Desktop folder. DOS has one root
     * per drive letter, <code>C:\</code>, <code>D:\</code>, etc. Unix has one root,
     * the <code>"/"</code> directory.
     *
     * The default implementation gets information from the <code>ShellFolder</code> class.
     *
     * @param f a <code>File</code> object representing a directory
     * @return <code>true</code> if <code>f</code> is a root in the navigatable tree.
     * @see #isFileSystemRoot
     */
    @Override
    public boolean isRoot(FileObject f) {
        return getParentDirectory(f) == null;
    }

    /**
     * Name of a file, directory, or folder as it would be displayed in
     * a system file browser. Example from Windows: the "M:\" directory
     * displays as "CD-ROM (M:)"
     *
     * The default implementation gets information from the ShellFolder class.
     *
     * @param f a <code>File</code> object
     * @return the file name as it would be displayed by a native file chooser
     * @see JFileChooser#getName
     * @since 1.4
     */
    @Override
    public String getSystemDisplayName(FileObject f) {
        String name = null;

        if (f != null) {
            name = getName(f);

            if (!name.trim().equals("")) {
                name = VFSUtils.getFriendlyName(getUrl(f));
            }
        }

        return name;
    }

    /**
     * Type description for a file, directory, or folder as it would be displayed in
     * a system file browser. Example from Windows: the "Desktop" folder
     * is desribed as "Desktop".
     *
     * Override for platforms with native ShellFolder implementations.
     *
     * @param f a <code>File</code> object
     * @return the file type description as it would be displayed by a native file chooser
     * or null if no native information is available.
     * @see JFileChooser#getTypeDescription
     * @since 1.4
     */
    @Override
    public String getSystemTypeDescription(FileObject f) {
        return VFSUtils.getFriendlyName(getUrl(f));
    }

    /**
     * Icon for a file, directory, or folder as it would be displayed in
     * a system file browser. Example from Windows: the "M:\" directory
     * displays a CD-ROM icon.
     *
     * The default implementation gets information from the ShellFolder class.
     *
     * @param f a <code>File</code> object
     * @return an icon as it would be displayed by a native file chooser
     * @see JFileChooser#getIcon
     * @since 1.4
     */
    @Override
    public Icon getSystemIcon(FileObject f) {
        if (f != null) {
            return UIManager.getIcon(isDirectory(f)
                    ? "FileView.directoryIcon" : "FileView.fileIcon");
        } else {
            return null;
        }
    }

    /**
     * On Windows, a file can appear in multiple folders, other than its
     * parent directory in the filesystem. Folder could for example be the
     * "Desktop" folder which is not the same as file.getParentFile().
     *
     * @param folder a <code>File</code> object repesenting a directory or special folder
     * @param file a <code>File</code> object
     * @return <code>true</code> if <code>folder</code> is a directory or special folder and contains <code>file</code>.
     * @since 1.4
     */
    @Override
    public boolean isParent(FileObject folder, FileObject file) {
        return folder.equals(getParentDirectory(file));
    }

    /**
     *
     * @param parent a <code>File</code> object repesenting a directory or special folder
     * @param fileName a name of a file or folder which exists in <code>parent</code>
     * @return a File object. This is normally constructed with <code>new
     * File(parent, fileName)</code> except when parent and child are both
     * special folders, in which case the <code>File</code> is a wrapper containing
     * a <code>ShellFolder</code> object.
     * @since 1.4
     */
    @Override
    public FileObject getChild(FileObject parent, String fileName) {
        return createFileObject(parent, fileName);
    }

    /**
     * Checks if <code>f</code> represents a real directory or file as opposed to a
     * special folder such as <code>"Desktop"</code>. Used by UI classes to decide if
     * a folder is selectable when doing directory choosing.
     *
     * @param f a <code>File</code> object
     * @return <code>true</code> if <code>f</code> is a real file or directory.
     * @since 1.4
     */
    @Override
    public boolean isFileSystem(FileObject f) {
        return true;
    }

    /**
     * Returns whether a file is hidden or not.
     * @param f
     * @return
     */
    @Override
    public boolean isHiddenFile(FileObject f) {
        String name = getName(f);
        return name.charAt(0) == '.';
    }

    /**
     * Is dir the root of a tree in the file system, such as a drive
     * or partition. Example: Returns true for "C:\" on Windows 98.
     *
     * @param dir a <code>File</code> object representing a directory
     * @return <code>true</code> if <code>f</code> is a root of a filesystem
     * @see #isRoot
     * @since 1.4
     */
    @Override
    public boolean isFileSystemRoot(FileObject dir) {
        return isRoot(dir);
    }

    /**
     * Used by UI classes to decide whether to display a special icon
     * for drives or partitions, e.g. a "hard disk" icon.
     *
     * The default implementation has no way of knowing, so always returns false.
     *
     * @param dir a directory
     * @return <code>false</code> always
     * @since 1.4
     */
    @Override
    public boolean isDrive(FileObject dir) {
        return isRoot(dir);
    }

    /**
     * Used by UI classes to decide whether to display a special icon
     * for a floppy disk. Implies isDrive(dir).
     *
     * The default implementation has no way of knowing, so always returns false.
     *
     * @param dir a directory
     * @return <code>false</code> always
     * @since 1.4
     */
    @Override
    public boolean isFloppyDrive(FileObject dir) {
        return false;
    }

    /**
     * Used by UI classes to decide whether to display a special icon
     * for a computer node, e.g. "My Computer" or a network server.
     *
     * The default implementation has no way of knowing, so always returns false.
     *
     * @param dir a directory
     * @return <code>false</code> always
     * @since 1.4
     */
    @Override
    public boolean isComputerNode(FileObject dir) {
        return false;
    }

    // Providing default implementations for the remaining methods
    // because most OS file systems will likely be able to use this
    // code. If a given OS can't, override these methods in its
    // implementation.
    /**
     *
     * @return
     */
    @Override
    public FileObject getHomeDirectory() {
        return createFileObject(System.getProperty("user.home"));
    }

    /**
     * Return the user's default starting directory for the file chooser.
     *
     * @return a <code>File</code> object representing the default
     *         starting folder
     * @since 1.4
     */
    @Override
    public FileObject getDefaultDirectory() {
        return createFileObject(System.getProperty("user.dir"));
    }

    @Override
    public boolean isTraversable(FileObject f) {
        return isDirectory(f);
    }

}
