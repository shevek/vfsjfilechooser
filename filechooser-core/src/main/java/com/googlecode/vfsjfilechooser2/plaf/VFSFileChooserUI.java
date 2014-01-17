package com.googlecode.vfsjfilechooser2.plaf;

import com.googlecode.vfsjfilechooser2.VFSJFileChooser;
import com.googlecode.vfsjfilechooser2.filechooser.VFSFileFilter;
import com.googlecode.vfsjfilechooser2.filechooser.VFSFileView;

/**
 *
 * @author shevek
 */
public interface VFSFileChooserUI<FileObject> {

    /**
     *
     * @param fc The fileChooser
     * @param f The fileobject
     */
    void ensureFileIsVisible(VFSJFileChooser<FileObject> fc, FileObject f);

    /**
     *
     * @param fc
     * @return
     */
    VFSFileFilter<? super FileObject> getAcceptAllFileFilter(VFSJFileChooser<FileObject> fc);

    /**
     *
     * @param fc
     * @return
     */
    String getApproveButtonText(VFSJFileChooser<?> fc);

    /**
     *
     * @param fc
     * @return
     */
    String getDialogTitle(VFSJFileChooser<?> fc);

    /**
     *
     * @param fc
     * @return
     */
    VFSFileView<FileObject> getFileView(VFSJFileChooser<FileObject> fc);

    /**
     *
     * @param fc
     */
    void rescanCurrentDirectory(VFSJFileChooser<FileObject> fc);

}
