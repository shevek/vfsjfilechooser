/*
 * Created on Aug 8, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2008-2010 the original author or authors.
 */
package org.fest.swing.driver;

import com.googlecode.vfsjfilechooser2.VFSJFileChooser;
import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Strings.concat;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Understands a task that selects a file in a <code>{@link VFSJFileChooser}</code>. This task is executed in the event
 * dispatch thread.
 * 
 * @author Alex Ruiz
 */
final class VFSJFileChooserSelectFileTask {

    @RunsInEDT
    static <FileObject> void validateAndSelectFile(final VFSJFileChooser<FileObject> fileChooser, final FileObject file) {
        execute(new GuiTask() {
            protected void executeInEDT() {
                validateIsEnabledAndShowing(fileChooser);
                validateFileToChoose(fileChooser, file);
                fileChooser.setSelectedFile(file);
            }
        });
    }

    @RunsInEDT
    static <FileObject> void validateAndSelectFiles(final VFSJFileChooser<FileObject> fileChooser, final FileObject[] files) {
        execute(new GuiTask() {
            protected void executeInEDT() {
                validateIsEnabledAndShowing(fileChooser);
                if (files.length > 1 && !fileChooser.isMultiSelectionEnabled())
                    throw new IllegalStateException(
                            concat("Expecting file chooser ", format(fileChooser), " to handle multiple selection"));
                for (FileObject file : files)
                    validateFileToChoose(fileChooser, file);
                fileChooser.setSelectedFiles(files);
            }
        });
    }

    @RunsInCurrentThread
    private static <FileObject> void validateFileToChoose(VFSJFileChooser<FileObject> fileChooser, FileObject file) {
        VFSJFileChooser.SELECTION_MODE mode = fileChooser.getFileSelectionMode();
        boolean isFolder = fileChooser.getFileSystemView().isDirectory(file);
        if (mode == VFSJFileChooser.SELECTION_MODE.FILES_ONLY && isFolder)
            throw cannotSelectFile(file, "the file chooser cannot open directories");
        if (mode == VFSJFileChooser.SELECTION_MODE.DIRECTORIES_ONLY && !isFolder)
            throw cannotSelectFile(file, "the file chooser can only open directories");
    }

    private static <FileObject> IllegalArgumentException cannotSelectFile(FileObject file, String reason) {
        return new IllegalArgumentException(concat("Unable to select file ", file, ": ", reason));
    }

    private VFSJFileChooserSelectFileTask() {
    }
}
