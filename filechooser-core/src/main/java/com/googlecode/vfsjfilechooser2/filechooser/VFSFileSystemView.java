package com.googlecode.vfsjfilechooser2.filechooser;

import com.googlecode.vfsjfilechooser2.VFSException;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileView;

/**
 *
 * @author shevek
 */
public interface VFSFileSystemView<FileObject> {

    Class<FileObject> getFileObjectType();

    public FileObject[] newFileObjectArray(Object... files);

    /**
     * Returns a File object constructed in dir from the given filename.
     * @param dir
     * @param filename
     * @return
     */
    FileObject createFileObject(@CheckForNull FileObject dir, @Nonnull String filename);

    /**
     * Returns a File object constructed from the given path string.
     * @param path
     * @return
     */
    FileObject createFileObject(String path);

    /**
     * Creates a new folder with a default folder name.
     * @param containingDir
     * @return
     * @throws org.apache.commons.vfs.FileSystemException
     */
    FileObject createNewFolder(FileObject containingDir) throws VFSException;

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
    FileObject getChild(FileObject parent, String fileName);

    /**
     * Return the user's default starting directory for the file chooser.
     *
     * @return a <code>File</code> object representing the default
     *         starting folder
     * @since 1.4
     */
    FileObject getDefaultDirectory();

    /**
     * Gets the list of shown (i.e. not hidden) files.
     * @param dir
     * @param useFileHiding
     * @return
     */
    // TODO: -> getChildren()
    FileObject[] getFiles(FileObject dir, boolean useFileHiding);

    /**
     *
     * @return
     */
    FileObject getHomeDirectory();

    /**
     * Returns the parent directory of <code>dir</code>.
     * @param dir the <code>File</code> being queried
     * @return the parent directory of <code>dir</code>, or
     *   <code>null</code> if <code>dir</code> is <code>null</code>
     */
    // TODO: Default impl returns self if no parent. Make -> null and audit.
    @CheckForNull
    FileObject getParentDirectory(FileObject dir);

    /**
     * Returns all root partitions on this system. For example, on
     * Windows, this would be the "Desktop" folder, while on DOS this
     * would be the A: through Z: drives.
     * @param fo
     * @return
     */
    // TODO: This needs to be user-settable.
    FileObject[] getRoots(FileObject fo);

    /** Full path. */
    String getUrl(FileObject f);

    /** Base-name only. */
    String getName(FileObject f);

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
    String getSystemDisplayName(FileObject f);

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
    Icon getSystemIcon(FileObject f);

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
    String getSystemTypeDescription(FileObject f);

    boolean exists(FileObject file);

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
    // TODO: Should this come from getSystemIcon() ?
    boolean isComputerNode(FileObject dir);

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
    // TODO: Should this come from getSystemIcon() ?
    boolean isDrive(FileObject dir);

    /**
     * Checks if <code>f</code> represents a real directory or file as opposed to a
     * special folder such as <code>"Desktop"</code>. Used by UI classes to decide if
     * a folder is selectable when doing directory choosing.
     *
     * @param f a <code>File</code> object
     * @return <code>true</code> if <code>f</code> is a real file or directory.
     * @since 1.4
     */
    // TODO: isVirtual() ?
    boolean isFileSystem(FileObject f);

    /**
     * Is dir the root of a tree in the file system, such as a drive
     * or partition. Example: Returns true for "C:\" on Windows 98.
     *
     * @param dir a <code>File</code> object representing a directory
     * @return <code>true</code> if <code>f</code> is a root of a filesystem
     * @see #isRoot
     * @since 1.4
     */
    // TODO: Should this come from getSystemIcon() ?
    // TODO: This differs from isRoot() somehow.
    boolean isFileSystemRoot(FileObject dir);

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
    // TODO: Should this come from getSystemIcon() ?
    boolean isFloppyDrive(FileObject dir);

    /**
     * Returns whether a file is hidden or not.
     * @param f
     * @return
     */
    boolean isHiddenFile(FileObject f);

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
    boolean isParent(@Nonnull FileObject folder, @Nonnull FileObject file);

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
    // TODO: I think these roots are allowed to be virtual.
    boolean isRoot(FileObject f);

    /**
     * Returns true if the file (directory) can be visited.
     * Returns false if the directory cannot be traversed.
     *
     * @param f the <code>File</code>
     * @return <code>true</code> if the file/directory can be traversed, otherwise <code>false</code>
     * @see JFileChooser#isTraversable
     * @see FileView#isTraversable
     * @since 1.4
     */
    boolean isTraversable(FileObject f);
    // return VFSUtils.isDirectory(f);

}
