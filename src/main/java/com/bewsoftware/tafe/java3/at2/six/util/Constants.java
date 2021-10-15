/*
 *  File Name:    Constants.java
 *  Project Name: Common
 *
 *  Copyright (c) 2021 Bradley Willcott
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * ****************************************************************
 * Name: Bradley Willcott
 * ID:   M198449
 * Date: 11 Oct 2021
 * ****************************************************************
 */

package com.bewsoftware.tafe.java3.at2.six.util;

/**
 * Constants is a utility class containing only common constants.
 *
 * @author <a href="mailto:bw.opensource@yahoo.com">Bradley Willcott</a>
 *
 * @since 1.0
 * @version 1.0
 */
public class Constants
{

    /**
     * Build date of the project.
     */
    public static final String BUILD_DATE = "15 October 2021";

    /**
     * Title separator line.
     */
    public static final String DOUBLE_LINE;

    /**
     * Session separator line.
     */
    public static final String LINE;

    /**
     * Build date of the project.
     */
    public static final String PRODUCT_TITLE = "Java3 AT2 Six";

    /**
     * Title indent.
     */
    public static final String TITLE_INDENT;

    /**
     * Project version number.
     */
    public static final String VERSION = "v1.0";

    static
    {
        DOUBLE_LINE = "=".repeat(80);
        LINE = "-".repeat(80);
        TITLE_INDENT = " ".repeat(20);
    }

    /**
     * Log messages to the standard console.
     *
     * @implNote
     * Uses {@code System#out#prinf(String, Object...)}.
     *
     * @param format format string
     * @param args   optional arguments
     */
    public static void log(String format, Object... args)
    {
        System.out.printf(format + '\n', args);
    }

    /**
     * Not meant to be instantiated.
     */
    private Constants()
    {
    }
}
