/*
 * Created on Jul 9, 2007
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
 * Copyright @2007-2010 the original author or authors.
 */
package org.fest.swing.fixture;

import com.googlecode.vfsjfilechooser2.VFSJFileChooser;

import org.fest.swing.core.*;
import org.fest.swing.driver.JFileChooserDriver;
import org.fest.swing.driver.VFSJFileChooserDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.timing.Timeout;

/**
 * Understands functional testing of <code>{@link VFSJFileChooser}</code>s:
 * <ul>
 * <li>user input simulation</li>
 * <li>state verification</li>
 * <li>property value query</li>
 * </ul>
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class VFSJFileChooserFixture<FileObject> extends ComponentFixture<VFSJFileChooser<FileObject>> implements CommonComponentFixture {

    private VFSJFileChooserDriver<FileObject> driver;

    /**
     * Creates a new <code>{@link VFSJFileChooserFixture}</code>.
     * @param robot performs simulation of user events on a <code>VFSJFileChooser</code>.
     * @throws NullPointerException if <code>robot</code> is <code>null</code>.
     * @throws ComponentLookupException if a matching <code>VFSJFileChooser</code> could not be found.
     * @throws ComponentLookupException if more than one matching <code>VFSJFileChooser</code> is found.
     */
    @SuppressWarnings("unchecked")
    public VFSJFileChooserFixture(Robot robot) {
        super(robot, (Class) VFSJFileChooser.class);
        createDriver();
    }

    /**
     * Creates a new <code>{@link VFSJFileChooserFixture}</code>.
     * @param robot performs simulation of user events on the given <code>VFSJFileChooser</code>.
     * @param target the <code>VFSJFileChooser</code> to be managed by this fixture.
     * @throws NullPointerException if <code>robot</code> is <code>null</code>.
     * @throws NullPointerException if <code>target</code> is <code>null</code>.
     */
    public VFSJFileChooserFixture(Robot robot, VFSJFileChooser<FileObject> target) {
        super(robot, target);
        createDriver();
    }

    /**
     * Creates a new <code>{@link VFSJFileChooserFixture}</code>.
     * @param robot performs simulation of user events on a <code>VFSJFileChooser</code>.
     * @param fileChooserName the name of the <code>VFSJFileChooser</code> to find using the given <code>RobotFixture</code>.
     * @throws NullPointerException if <code>robot</code> is <code>null</code>.
     * @throws ComponentLookupException if a matching <code>VFSJFileChooser</code> could not be found.
     * @throws ComponentLookupException if more than one matching <code>VFSJFileChooser</code> is found.
     */
    @SuppressWarnings("unchecked")
    public VFSJFileChooserFixture(Robot robot, String fileChooserName) {
        super(robot, fileChooserName, (Class) VFSJFileChooser.class);
        createDriver();
    }

    private void createDriver() {
        driver(new VFSJFileChooserDriver<FileObject>(robot));
    }

    /**
     * Sets the <code>{@link JFileChooserDriver}</code> to be used by this fixture.
     * @param newDriver the new <code>JFileChooserDriver</code>.
     * @throws NullPointerException if the given driver is <code>null</code>.
     */
    protected final void driver(VFSJFileChooserDriver<FileObject> newDriver) {
        validateNotNull(newDriver);
        driver = newDriver;
    }

    /**
     * Simulates a user pressing the "Approve" button in this fixture's <code>{@link VFSJFileChooser}</code>.
     * @throws ComponentLookupException if the "Approve" button cannot be found.
     * @throws IllegalStateException if the "Approve" button is disabled.
     * @throws IllegalStateException if the "Approve" button is not showing on the screen.
     */
    public void approve() {
        driver.clickApproveButton(target);
    }

    /**
     * Finds the "Approve" button in this fixture's <code>{@link VFSJFileChooser}</code>.
     * @return the found "Approve" button.
     * @throws ComponentLookupException if the "Approve" button cannot be found.
     */
    public JButtonFixture approveButton() {
        return new JButtonFixture(robot, driver.approveButton(target));
    }

    /**
     * Simulates a user pressing the "Cancel" button in this fixture's <code>{@link VFSJFileChooser}</code>.
     * @throws ComponentLookupException if the "Cancel" button cannot be found.
     * @throws IllegalStateException if the "Cancel" button is disabled.
     * @throws IllegalStateException if the "Cancel" button is not showing on the screen.
     */
    public void cancel() {
        driver.clickCancelButton(target);
    }

    /**
     * Finds the "Cancel" button in this fixture's <code>{@link VFSJFileChooser}</code>.
     * @return the found "Cancel" button.
     * @throws ComponentLookupException if the "Cancel" button cannot be found.
     */
    public JButtonFixture cancelButton() {
        return new JButtonFixture(robot, driver.cancelButton(target));
    }

    /**
     * Returns a fixture that manages the field where the user can enter the name of the file to select in this fixture's
     * <code>{@link VFSJFileChooser}</code>.
     * @return the created fixture.
     * @throws ComponentLookupException if a matching textToMatch field could not be found.
     */
    public JTextComponentFixture fileNameTextBox() {
        return new JTextComponentFixture(robot, driver.fileNameTextBox(target));
    }

    /**
     * Selects the given file in this fixture's <code>{@link VFSJFileChooser}</code>.
     * @param file the file to select.
     * @return this fixture.
     * @throws NullPointerException if the given file is <code>null</code>.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     * @throws IllegalArgumentException if this fixture's <code>VFSJFileChooser</code> can select directories only and the
     * file to select is not a directory.
     * @throws IllegalArgumentException if this fixture's <code>VFSJFileChooser</code> cannot select directories and the file
     * to select is a directory.
     */
    public VFSJFileChooserFixture<FileObject> selectFile(final FileObject file) {
        driver.selectFile(target, file);
        return this;
    }

    /**
     * Selects the given files in this fixture's <code>{@link VFSJFileChooser}</code>.
     * @param files the files to select.
     * @return this fixture.
     * @throws NullPointerException if the given array of files is <code>null</code>.
     * @throws IllegalArgumentException if the given array of files is empty.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> does not support multiple selection and
     * there is more than one file to select.
     * @throws IllegalArgumentException if this fixture's <code>VFSJFileChooser</code> can select directories only and any of
     * the files to select is not a directory.
     * @throws IllegalArgumentException if this fixture's <code>VFSJFileChooser</code> cannot select directories and any of
     * the files to select is a directory.
     */
    public VFSJFileChooserFixture<FileObject> selectFiles(FileObject... files) {
        driver.selectFiles(target, files);
        return this;
    }

    /**
     * Sets the current directory of this fixture's <code>{@link VFSJFileChooser}</code> to the given one.
     * @param dir the directory to set as current.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     * @return this fixture.
     */
    public VFSJFileChooserFixture<FileObject> setCurrentDirectory(final FileObject dir) {
        driver.setCurrentDirectory(target, dir);
        return this;
    }

    /**
     * Simulates a user clicking this fixture's <code>{@link VFSJFileChooser}</code>.
     * @return this fixture.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     */
    public VFSJFileChooserFixture<FileObject> click() {
        driver.click(target);
        return this;
    }

    /**
     * Simulates a user clicking this fixture's <code>{@link VFSJFileChooser}</code>.
     * @param button the button to click.
     * @return this fixture.
     * @throws NullPointerException if the given <code>MouseButton</code> is <code>null</code>.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     */
    public VFSJFileChooserFixture<FileObject> click(MouseButton button) {
        driver.click(target, button);
        return this;
    }

    /**
     * Simulates a user clicking this fixture's <code>{@link VFSJFileChooser}</code>.
     * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
     * @return this fixture.
     * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     */
    public VFSJFileChooserFixture<FileObject> click(MouseClickInfo mouseClickInfo) {
        driver.click(target, mouseClickInfo);
        return this;
    }

    /**
     * Simulates a user double-clicking this fixture's <code>{@link VFSJFileChooser}</code>.
     * @return this fixture.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     */
    public VFSJFileChooserFixture<FileObject> doubleClick() {
        driver.doubleClick(target);
        return this;
    }

    /**
     * Simulates a user right-clicking this fixture's <code>{@link VFSJFileChooser}</code>.
     * @return this fixture.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     */
    public VFSJFileChooserFixture<FileObject> rightClick() {
        driver.rightClick(target);
        return this;
    }

    /**
     * Gives input focus to this fixture's <code>{@link VFSJFileChooser}</code>.
     * @return this fixture.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     */
    public VFSJFileChooserFixture<FileObject> focus() {
        driver.focus(target);
        return this;
    }

    /**
     * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link VFSJFileChooser}</code>.
     * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
     * @param keyPressInfo specifies the key and modifiers to press.
     * @return this fixture.
     * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
     * @throws IllegalArgumentException if the given code is not a valid key code.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     * @see KeyPressInfo
     */
    public VFSJFileChooserFixture<FileObject> pressAndReleaseKey(KeyPressInfo keyPressInfo) {
        driver.pressAndReleaseKey(target, keyPressInfo);
        return this;
    }

    /**
     * Simulates a user pressing and releasing the given keys on the <code>{@link VFSJFileChooser}</code> managed by this
     * fixture.
     * @param keyCodes one or more codes of the keys to press.
     * @return this fixture.
     * @throws NullPointerException if the given array of codes is <code>null</code>.
     * @throws IllegalArgumentException if any of the given code is not a valid key code.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     * @see java.awt.event.KeyEvent
     */
    public VFSJFileChooserFixture<FileObject> pressAndReleaseKeys(int... keyCodes) {
        driver.pressAndReleaseKeys(target, keyCodes);
        return this;
    }

    /**
     * Simulates a user pressing the given key on this fixture's <code>{@link VFSJFileChooser}</code>.
     * @param keyCode the code of the key to press.
     * @return this fixture.
     * @throws IllegalArgumentException if any of the given code is not a valid key code.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     * @see java.awt.event.KeyEvent
     */
    public VFSJFileChooserFixture<FileObject> pressKey(int keyCode) {
        driver.pressKey(target, keyCode);
        return this;
    }

    /**
     * Simulates a user releasing the given key on this fixture's <code>{@link VFSJFileChooser}</code>.
     * @param keyCode the code of the key to release.
     * @return this fixture.
     * @throws IllegalArgumentException if any of the given code is not a valid key code.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is disabled.
     * @throws IllegalStateException if this fixture's <code>VFSJFileChooser</code> is not showing on the screen.
     * @see java.awt.event.KeyEvent
     */
    public VFSJFileChooserFixture<FileObject> releaseKey(int keyCode) {
        driver.releaseKey(target, keyCode);
        return this;
    }

    /**
     * Asserts that this fixture's <code>{@link VFSJFileChooser}</code> has input focus.
     * @return this fixture.
     * @throws AssertionError if this fixture's <code>VFSJFileChooser</code> does not have input focus.
     */
    public VFSJFileChooserFixture<FileObject> requireFocused() {
        driver.requireFocused(target);
        return this;
    }

    /**
     * Asserts that this fixture's <code>{@link VFSJFileChooser}</code> is enabled.
     * @return this fixture.
     * @throws AssertionError if this fixture's <code>VFSJFileChooser</code> is disabled.
     */
    public VFSJFileChooserFixture<FileObject> requireEnabled() {
        driver.requireEnabled(target);
        return this;
    }

    /**
     * Asserts that this fixture's <code>{@link VFSJFileChooser}</code> is enabled.
     * @param timeout the time this fixture will wait for the component to be enabled.
     * @return this fixture.
     * @throws WaitTimedOutError if this fixture's <code>VFSJFileChooser</code> is never enabled.
     */
    public VFSJFileChooserFixture<FileObject> requireEnabled(Timeout timeout) {
        driver.requireEnabled(target, timeout);
        return this;
    }

    /**
     * Asserts that this fixture's <code>{@link VFSJFileChooser}</code> is disabled.
     * @return this fixture.
     * @throws AssertionError if this fixture's <code>VFSJFileChooser</code> is enabled.
     */
    public VFSJFileChooserFixture<FileObject> requireDisabled() {
        driver.requireDisabled(target);
        return this;
    }

    /**
     * Asserts that this fixture's <code>{@link VFSJFileChooser}</code> is visible.
     * @return this fixture.
     * @throws AssertionError if this fixture's <code>VFSJFileChooser</code> is not visible.
     */
    public VFSJFileChooserFixture<FileObject> requireVisible() {
        driver.requireVisible(target);
        return this;
    }

    /**
     * Asserts that this fixture's <code>{@link VFSJFileChooser}</code> is not visible.
     * @return this fixture.
     * @throws AssertionError if this fixture's <code>VFSJFileChooser</code> is visible.
     */
    public VFSJFileChooserFixture<FileObject> requireNotVisible() {
        driver.requireNotVisible(target);
        return this;
    }
}
