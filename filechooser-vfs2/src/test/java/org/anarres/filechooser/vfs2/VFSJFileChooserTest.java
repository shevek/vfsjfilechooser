/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.filechooser.vfs2;

import com.googlecode.vfsjfilechooser2.VFSJFileChooser;
import java.awt.EventQueue;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author shevek
 */
@Ignore
public class VFSJFileChooserTest {

    private static final Log LOG = LogFactory.getLog(VFSJFileChooserTest.class);

    @Test
    public void testFileChooser() throws Exception {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        manager.init();

        final FileObject root = manager.resolveFile("ram:/");
        FileObject foo = root.resolveFile("foo");
        foo.createFolder();
        foo.resolveFile("foo0").createFile();
        foo.resolveFile("foo1").createFile();
        root.resolveFile("bar").createFile();

        EventQueue.invokeAndWait(new Runnable() {

            private void print(FileObject file) {
                try {
                    LOG.info("Selected file is " + file);
                    if (file == null)
                        return;
                    LOG.info("Selected size is " + file.getContent().getSize());
                    LOG.info("Selected operations is " + file.getFileOperations());
                } catch (FileSystemException e) {
                    LOG.info("Exception is " + e);
                }
            }

            @Override
            public void run() {
                CommonsVfs2JFileChooser chooser = new CommonsVfs2JFileChooser();
                chooser.setMultiSelectionEnabled(true);
                chooser.setCurrentDirectory(root);
                VFSJFileChooser.RETURN_TYPE ret = chooser.showOpenDialog(null);
                LOG.info("RETURN_TYPE = " + ret);
                LOG.info("Selected FO  = " + chooser.getSelectedFile());
                LOG.info("Selected FOs = " + Arrays.toString(chooser.getSelectedFiles()));
                print(chooser.getSelectedFile());
                for (FileObject file : chooser.getSelectedFiles()) {
                    print(file);
                }
            }
        });
    }

}
