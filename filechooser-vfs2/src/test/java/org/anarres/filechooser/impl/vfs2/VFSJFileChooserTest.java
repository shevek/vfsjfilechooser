/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.filechooser.impl.vfs2;

import com.googlecode.vfsjfilechooser2.VFSJFileChooser;
import java.awt.EventQueue;
import java.util.Arrays;
import javax.annotation.Nonnull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.fest.swing.annotation.GUITest;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.finder.VFSJFileChooserFinder;
import org.fest.swing.fixture.VFSJFileChooserFixture;
import org.fest.swing.format.Formatting;
import org.fest.swing.format.VFSJFileChooserFormatter;
import org.fest.swing.junit.v4_5.runner.GUITestRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author shevek
 */
@GUITest
@RunWith(GUITestRunner.class)
public class VFSJFileChooserTest {

    private static final Log LOG = LogFactory.getLog(VFSJFileChooserTest.class);

    @BeforeClass
    public static void setUpClass() {
        FailOnThreadViolationRepaintManager.install();
        Formatting.register(new VFSJFileChooserFormatter());
    }

    private static void print(@Nonnull FileObject file) {
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

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
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
                } catch (FileSystemException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        VFSJFileChooserFixture<FileObject> chooser = VFSJFileChooserFinder.<FileObject>findFileChooser().using(robot);
        chooser.approve();
    }

}
