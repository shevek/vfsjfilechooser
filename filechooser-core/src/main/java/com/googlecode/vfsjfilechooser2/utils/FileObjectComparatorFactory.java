/*
 * File comparators factory
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
package com.googlecode.vfsjfilechooser2.utils;

import com.googlecode.vfsjfilechooser2.filechooser.VFSFileSystemView;
import java.util.Comparator;

/**
 *
 * File comparators factory
 * @author Yves Zoundi <yveszoundi at users dot sf dot net>
 * @version 0.0.1
 *
 */
public final class FileObjectComparatorFactory {

    private FileObjectComparatorFactory() {
        throw new AssertionError(
                "Trying to instanciate FileObjectComparatorFactory");
    }

    /**
     * Return a new filename comparator
     * @param isSortAsc ascendant sorting
     * @return a new comparator
     */
    public static <FileObject> Comparator<FileObject> newFileNameComparator(
            VFSFileSystemView<FileObject> fileSystemView,
            boolean isSortAsc) {
        Comparator<FileObject> comparator = new FileNameComparator<FileObject>(fileSystemView, isSortAsc);
        return new DirectoriesFirstComparatorWrapper<FileObject>(fileSystemView, comparator);
    }

    /**
     * Return a new size comparator
     * @param isSortAsc ascendant sorting
     * @return a new comparator
     */
    public static <FileObject> Comparator<FileObject> newSizeComparator(
            VFSFileSystemView<FileObject> fileSystemView,
            boolean isSortAsc) {
        Comparator<FileObject> comparator = new SizeComparator<FileObject>(fileSystemView, isSortAsc);
        return new DirectoriesFirstComparatorWrapper<FileObject>(fileSystemView, comparator);
    }

    /**
     * Return a new date comparator
     * @param isSortAsc ascendant sorting
     * @return a new comparator
     */
    public static <FileObject> Comparator<FileObject> newDateComparator(
            VFSFileSystemView<FileObject> fileSystemView,
            boolean isSortAsc) {
        Comparator<FileObject> comparator = new DateComparator<FileObject>(fileSystemView, isSortAsc);
        return new DirectoriesFirstComparatorWrapper<FileObject>(fileSystemView, comparator);
    }

    private static class FileNameComparator<FileObject> implements Comparator<FileObject> {

        private final VFSFileSystemView<FileObject> fileSystemView;
        private final boolean isSortAsc;

        public FileNameComparator(VFSFileSystemView<FileObject> fileSystemView, boolean isSortAsc) {
            this.fileSystemView = fileSystemView;
            this.isSortAsc = isSortAsc;
        }

        @Override
        public int compare(FileObject a, FileObject b) {
            try {
                String sa = fileSystemView.getName(a);
                String sb = fileSystemView.getName(b);
                int result = sa.toLowerCase()
                        .compareTo(sb.toLowerCase());

                if (!isSortAsc) {
                    result = -result;
                }

                return result;
            } catch (Exception err) {
                return -1;
            }
        }
    }

    /**
     * This class sorts directories before files, comparing directory to
     * directory and file to file using the wrapped comparator.
     */
    private static class DirectoriesFirstComparatorWrapper<FileObject> implements Comparator<FileObject> {

        private final VFSFileSystemView<FileObject> fileSystemView;
        private final Comparator<FileObject> delegate;

        public DirectoriesFirstComparatorWrapper(
                VFSFileSystemView<FileObject> fileSystemView,
                Comparator<FileObject> delegate) {
            this.fileSystemView = fileSystemView;
            this.delegate = delegate;
        }

        @Override
        public int compare(FileObject f1, FileObject f2) {
            if ((f1 != null) && (f2 != null)) {
                boolean traversable1 = fileSystemView.isTraversable(f1);
                boolean traversable2 = fileSystemView.isTraversable(f2);

                // directories go first
                if (traversable1 && !traversable2) {
                    return -1;
                }

                if (!traversable1 && traversable2) {
                    return 1;
                }
            }

            return delegate.compare(f1, f2);
        }
    }

    private static class SizeComparator<FileObject> implements Comparator<FileObject> {

        private final VFSFileSystemView<FileObject> fileSystemView;
        private final boolean isSortAsc;

        public SizeComparator(VFSFileSystemView<FileObject> fileSystemView, boolean isSortAsc) {
            this.fileSystemView = fileSystemView;
            this.isSortAsc = isSortAsc;
        }

        @Override
        public int compare(FileObject a, FileObject b) {
            try {
                long sa = fileSystemView.getSize(a);
                long sb = fileSystemView.getSize(b);
                int result = (sa < sb) ? -1 : ((sa == sb) ? 0 : 1);

                if (!isSortAsc) {
                    result = -result;
                }

                return result;
            } catch (Exception err) {
                return -1;
            }
        }
    }

    private static class DateComparator<FileObject> implements Comparator<FileObject> {

        private final VFSFileSystemView<FileObject> fileSystemView;
        private final boolean isSortAsc;

        public DateComparator(VFSFileSystemView<FileObject> fileSystemView, boolean isSortAsc) {
            this.fileSystemView = fileSystemView;
            this.isSortAsc = isSortAsc;
        }

        @Override
        public int compare(FileObject a, FileObject b) {
            try {
                long sa = fileSystemView.getLastModifiedTime(a);
                long sb = fileSystemView.getLastModifiedTime(b);
                int result = (sa < sb) ? -1 : ((sa == sb) ? 0 : 1);

                if (!isSortAsc) {
                    result = -result;
                }

                return result;
            } catch (Exception err) {
                return -1;
            }
        }
    }
}
