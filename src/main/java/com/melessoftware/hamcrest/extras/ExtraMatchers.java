/*
 * Meles Hamcrest Extras
 * Copyright (C) 2013  Neil Green
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.melessoftware.hamcrest.extras;

import org.hamcrest.Matcher;

public class ExtraMatchers {

    public static Matcher<Object> hasPropertyPath(String propertyPath) {
        return HasPropertyPath.hasPropertyPath(propertyPath);
    }

    public static Matcher<Object> hasPropertyPath(String propertyPath, Matcher<Object> matcher) {
        return HasPropertyPathWithValue.hasPropertyPath(propertyPath, matcher);
    }
}
