/*
 * Created on Oct 29, 2007
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
package org.fest.swing.finder;

import com.googlecode.vfsjfilechooser2.VFSJFileChooser;
import java.awt.Component;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.VFSJFileChooserFixture;

/**
 * Understands a finder for <code>{@link JFileChooser}</code>s. Lookups are performed till a file chooser is found,
 * or until the given time to perform the lookup is over. The default lookup time is 5 seconds.
 * </p>
 * <p>
 * This example illustrates finding a <code>{@link JFileChooser}</code> by name, using the default lookup time (5
 * seconds):
 *
 * <pre>
 * JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser().using(robot);
 * </pre>
 *
 * </p>
 * <p>
 * Where <code>robot</code> is an instance of <code>{@link org.fest.swing.core.Robot}</code>.
 * </p>
 * <p>
 * This example shows how to find a <code>{@link JFileChooser}</code> by type using a lookup time of 10 seconds:
 *
 * <pre>
 * JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser().withTimeout(10000).using(robot);
 * </pre>
 *
 * We can also specify the time unit:
 *
 * <pre>
 * JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser().withTimeout(10, {@link java.util.concurrent.TimeUnit#SECONDS SECONDS}).using(robot);
 * </pre>
 *
 * </p>
 * <p>
 * This examples shows how to find a <code>{@link JFileChooser}</code> using a <code>{@link GenericTypeMatcher}</code>:
 *
 * <pre>
 * GenericTypeMatcher&lt;JFileChooser&gt; matcher = new GenericTypeMatcher&lt;JFileChooser&gt;() {
 *   protected boolean isMatching(JFileChooser fileChooser) {
 *     return fileChooser.getCurrentDirectory().getAbsolutePath().equals(&quot;c:\\temp&quot;);
 *   }
 * };
 * JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser(matcher).using(robot);
 * </pre>
 *
 * </p>
 *
 * @author Alex Ruiz
 */
public class VFSJFileChooserFinder<FileObject> extends ComponentFinderTemplate<VFSJFileChooser<FileObject>> {

    /**
     * Creates a new </code>{@link JFileChooserFinder}</code>. This finder looks up a <code>{@link JFileChooser}</code> by
     * type.
     */
    @SuppressWarnings("unchecked")
    protected VFSJFileChooserFinder() {
        super((Class) VFSJFileChooser.class);
    }

    /**
     * Creates a new </code>{@link JFileChooserFinder}</code>.
     * @param name the name of the {@code FileChooser} to look for.
     */
    @SuppressWarnings("unchecked")
    protected VFSJFileChooserFinder(String name) {
        super(name, (Class) VFSJFileChooser.class);
    }

    /**
     * Creates a new </code>{@link JFileChooserFinder}</code>.
     * @param matcher specifies the search criteria to use when looking up a {@code VFSJFileChooser}.
     */
    protected VFSJFileChooserFinder(GenericTypeMatcher<? extends VFSJFileChooser<FileObject>> matcher) {
        super(matcher);
    }

    /**
     * Creates a new <code>{@link JFileChooserFinder}</code> capable of looking up a <code>{@link VFSJFileChooser}</code>.
     * @return the created finder.
     */
    public static <FileObject> VFSJFileChooserFinder<FileObject> findFileChooser() {
        return new VFSJFileChooserFinder<FileObject>();
    }

    /**
     * Creates a new <code>{@link JFileChooserFinder}</code> capable of looking up a <code>{@link VFSJFileChooser}</code> by
     * name.
     * @param name the name of the file chooser to find.
     * @return the created finder.
     */
    public static <FileObject> VFSJFileChooserFinder<FileObject> findFileChooser(String name) {
        return new VFSJFileChooserFinder<FileObject>(name);
    }

    /**
     * Creates a new <code>{@link JFileChooserFinder}</code> capable of looking up a <code>{@link VFSJFileChooser}</code>
     * using the given matcher.
     * @param matcher the given matcher.
     * @return the created finder.
     */
    public static <FileObject> VFSJFileChooserFinder<FileObject> findFileChooser(GenericTypeMatcher<? extends VFSJFileChooser<FileObject>> matcher) {
        return new VFSJFileChooserFinder<FileObject>(matcher);
    }

    /**
     * Finds a <code>{@link JFileChooser}</code> by name or type.
     * @param robot contains the underlying finding to delegate the search to.
     * @return a <code>JFileChooserFixture</code> managing the found {@code JFileChooser}.
     * @throws org.fest.swing.exception.WaitTimedOutError if a {@code JFileChooser} could not be found.
     */
    public VFSJFileChooserFixture<FileObject> using(Robot robot) {
        return new VFSJFileChooserFixture<FileObject>(robot, findComponentWith(robot));
    }

    /**
     * Sets the timeout for this finder. The <code>{@link JFileChooser}</code> to find should be found within the given
     * time period.
     * @param timeout the number of milliseconds before stopping the search.
     * @return this finder.
     * @throws IllegalArgumentException if the timeout is a negative number.
     */
    @Override
    public VFSJFileChooserFinder<FileObject> withTimeout(long timeout) {
        super.withTimeout(timeout);
        return this;
    }

    /**
     * Sets the timeout for this finder. The <code>{@link JFileChooser}</code> to find should be found within the given
     * time period.
     * @param timeout the period of time the search should be performed.
     * @param unit the time unit for <code>timeout</code>.
     * @return this finder.
     * @throws NullPointerException if the time unit is <code>null</code>.
     * @throws IllegalArgumentException if the timeout is a negative number.
     */
    @Override
    public VFSJFileChooserFinder<FileObject> withTimeout(long timeout, TimeUnit unit) {
        super.withTimeout(timeout, unit);
        return this;
    }

    /**
     * Casts the given {@code Component} to <code>{@link JFileChooser}</code>.
     * @return the given {@code Component}, casted to {@code JFileChooser}.
     */
    @SuppressWarnings("unchecked")
    protected VFSJFileChooser<FileObject> cast(Component c) {
        return (VFSJFileChooser<FileObject>) c;
    }
}
