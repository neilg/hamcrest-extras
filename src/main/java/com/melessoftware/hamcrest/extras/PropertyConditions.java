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

import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hamcrest.Condition;
import org.hamcrest.Description;

class PropertyConditions {
    static Condition<Object> property(String pathPart, Object item, Description mismatchDescription) {
        mismatchDescription.appendText("property path \"" + pathPart);
        return proceed(pathPart, item, mismatchDescription);
    }

    static Condition.Step<Object, Object> follow(final String pathPart) {
        return new Condition.Step<Object, Object>() {
            @Override
            public Condition<Object> apply(Object item, Description mismatchDescription) {
                mismatchDescription.appendText(".").appendText(pathPart);
                return proceed(pathPart, item, mismatchDescription);
            }
        };
    }

    private static Condition<Object> proceed(String pathPart, Object item, Description mismatchDescription) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(pathPart, item.getClass());
            Method readMethod = pd.getReadMethod();
            if (readMethod == null) {
                mismatchDescription.appendText("\" is not readable");
                return notMatched();
            }
            Object nextItem = readMethod.invoke(item);
            return matched(nextItem, mismatchDescription);
        } catch (IntrospectionException ie) {
            mismatchDescription.appendText("\" does not exist");
            return notMatched();
        } catch (InvocationTargetException e) {
            mismatchDescription.appendText("\" ").appendText(e.getMessage());
            return notMatched();
        } catch (IllegalAccessException e) {
            mismatchDescription.appendText("\" ").appendText(e.getMessage());
            return notMatched();
        }
    }


}
