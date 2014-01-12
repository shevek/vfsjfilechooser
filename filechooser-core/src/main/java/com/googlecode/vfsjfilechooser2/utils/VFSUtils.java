/*
 * A helper class to deal with commons-vfs file abstractions
 *
 * Copyright (C) 2008-2009 Yves Zoundi
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

import java.text.MessageFormat;

/**
 * A helper class to deal with commons-vfs file abstractions
 * @author Yves Zoundi <yveszoundi at users dot sf dot net>
 * @author Jojada Tirtowidjojo <jojada at users.sourceforge.net> 
 * @author Stephan Schuster <stephanschuster at users.sourceforge.net>
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version 0.0.5
 */
public final class VFSUtils {

    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    /**
     * The number of bytes in a gigabyte.
     */
    public static final long ONE_GB = ONE_KB * ONE_MB;

    private static final String OS_NAME = System.getProperty("os.name")
            .toLowerCase();
    private static final String PROTO_PREFIX = "://";
    private static final String FILE_PREFIX = OS_NAME.startsWith("windows")
            ? "file:///" : "file://";
    private static final int FILE_PREFIX_LEN = FILE_PREFIX.length();

    // File size localized strings
    private static final String kiloByteString = VFSResources.getMessage(
            "VFSJFileChooser.fileSizeKiloBytes");
    private static final String megaByteString = VFSResources.getMessage(
            "VFSJFileChooser.fileSizeMegaBytes");
    private static final String gigaByteString = VFSResources.getMessage(
            "VFSJFileChooser.fileSizeGigaBytes");

    // prevent unnecessary calls
    private VFSUtils() {
        throw new AssertionError("Trying to create a VFSUtils object");
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a human-readable version of the file size, where the input
     * represents a specific number of bytes.
     *
     * @param size  the number of bytes
     * @return a human-readable display value (includes units)
     */
    public static String byteCountToDisplaySize(long size) {
        if ((size / ONE_GB) > 0) {
            //            displaySize = String.valueOf(size / ONE_GB) + " GB";
            return MessageFormat.format(gigaByteString,
                    String.valueOf(size / ONE_GB));
        } else if ((size / ONE_MB) > 0) {
            //            displaySize = String.valueOf(size / ONE_MB) + " MB";
            return MessageFormat.format(megaByteString,
                    String.valueOf(size / ONE_MB));
        } else if ((size / ONE_KB) > 0) {
            return MessageFormat.format(kiloByteString,
                    String.valueOf(size / ONE_KB));

            //String.valueOf(size / ONE_KB) + " KB";
        } else {
            return String.valueOf(size);
        }
    }

    /**
     * Remove user credentials information
     * @param fileName The file name
     * @return The "safe" display name without username and password information
     */
    public static final String getFriendlyName(String fileName) {
        return getFriendlyName(fileName, true);
    }

    public static final String getFriendlyName(String fileName, boolean excludeLocalFilePrefix) {
        StringBuilder filePath = new StringBuilder();

        int pos = fileName.lastIndexOf('@');

        if (pos == -1) {
            filePath.append(fileName);
        } else {
            int pos2 = fileName.indexOf(PROTO_PREFIX);

            if (pos2 == -1) {
                filePath.append(fileName);
            } else {
                String protocol = fileName.substring(0, pos2);

                filePath.append(protocol).append(PROTO_PREFIX)
                        .append(fileName.substring(pos + 1, fileName.length()));
            }
        }

        String returnedString = filePath.toString();

        if (excludeLocalFilePrefix && returnedString.startsWith(FILE_PREFIX)) {
            return filePath.substring(FILE_PREFIX_LEN);
        }

        return returnedString;
    }
}