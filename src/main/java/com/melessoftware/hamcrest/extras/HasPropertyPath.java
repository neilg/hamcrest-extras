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

import static com.melessoftware.hamcrest.extras.PropertyConditions.follow;
import static com.melessoftware.hamcrest.extras.PropertyConditions.property;
import static com.melessoftware.hamcrest.extras.PropertyConditions.propertyExists;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HasPropertyPath<T> extends TypeSafeDiagnosingMatcher<T> {

    private final String propertyPath;

    public HasPropertyPath(final String propertyPath) {
        this.propertyPath = propertyPath;
    }

    @Override
    protected boolean matchesSafely(final T item, final Description mismatchDescription) {
        final String[] pathParts = propertyPath.split("\\.");
        Condition<Object> condition = property(pathParts[0], item, mismatchDescription);
        for (int i = 1; i < pathParts.length - 1; i++) {
            condition = condition.and(follow(pathParts[i]));
        }
        final String lastPart = pathParts[pathParts.length - 1];
        return condition.matching(propertyExists(lastPart));
    }

    @Override
    public void describeTo(final Description description) {
        description
                .appendText("has property path ")
                .appendValue(propertyPath);
    }

    public static <X> Matcher<X> hasPropertyPath(final String propertyPath) {
        return new HasPropertyPath<X>(propertyPath);
    }
}
