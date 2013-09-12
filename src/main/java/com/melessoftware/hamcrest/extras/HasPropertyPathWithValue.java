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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HasPropertyPathWithValue<T> extends TypeSafeDiagnosingMatcher<T> {

    private final String propertyPath;
    private final Matcher<?> valueMatcher;

    public HasPropertyPathWithValue(String propertyPath, Matcher<?> valueMatcher) {
        this.propertyPath = propertyPath;
        this.valueMatcher = valueMatcher;
    }

    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription) {
        final String[] pathParts = propertyPath.split("\\.");
        Object currentItem = item;
        mismatchDescription.appendText("property path \"");
        try {
            for (int i = 0; i < pathParts.length; i++) {
                String pathPart = pathParts[i];
                if (i > 0) {
                    mismatchDescription.appendText(".");
                }
                mismatchDescription.appendText(pathPart);
                PropertyDescriptor pd = new PropertyDescriptor(pathPart, currentItem.getClass());
                Method readMethod = pd.getReadMethod();
                if (readMethod == null) {
                    mismatchDescription.appendText(" is not readable");
                    return false;
                }
                currentItem = readMethod.invoke(currentItem);
            }
        } catch (IntrospectionException ie) {
            mismatchDescription.appendText("\" does not exist");
            return false;
        } catch (InvocationTargetException e) {
            mismatchDescription.appendText("\" ").appendText(e.getMessage());
            return false;
        } catch (IllegalAccessException e) {
            mismatchDescription.appendText("\" ").appendText(e.getMessage());
            return false;
        }
        mismatchDescription.appendText("\" ");
        valueMatcher.describeMismatch(currentItem, mismatchDescription);
        return valueMatcher.matches(currentItem);
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("has property path ")
                .appendValue(propertyPath)
                .appendText(" with value ");
        valueMatcher.describeTo(description);
    }

    public static <X> Matcher<X> hasPropertyPathWithValue(String propertyPath, Matcher<?> matcher) {
        return new HasPropertyPathWithValue<X>(propertyPath, matcher);
    }
}
