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

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HasPropertyPathWithValue<T> extends TypeSafeDiagnosingMatcher<T> {

    private final String propertyPath;
    private final Matcher<Object> valueMatcher;

    public HasPropertyPathWithValue(String propertyPath, Matcher<Object> valueMatcher) {
        this.propertyPath = propertyPath;
        this.valueMatcher = valueMatcher;
    }

    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription) {
        final String[] pathParts = propertyPath.split("\\.");
        Condition<Object> x = property(pathParts[0], item, mismatchDescription);
        for (int i = 1; i < pathParts.length; i++) {
            x = x.and(follow(pathParts[i]));
        }
        return x.matching(valueMatcher, "\" ");
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("has property path ")
                .appendValue(propertyPath)
                .appendText(" with value ");
        valueMatcher.describeTo(description);
    }

    public static <X> Matcher<X> hasPropertyPathWithValue(String propertyPath, Matcher<Object> matcher) {
        return new HasPropertyPathWithValue<X>(propertyPath, matcher);
    }
}