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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class Cast<T> extends TypeSafeMatcher<T> {

    private final Matcher<?> matcher;

    public Cast(final Matcher<?> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(final T item) {
        return matcher.matches(item);
    }

    @Override
    protected void describeMismatchSafely(final T item, final Description mismatchDescription) {
        matcher.describeMismatch(item, mismatchDescription);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendDescriptionOf(matcher);
    }

    public static <X> Matcher<X> cast(final Matcher<?> matcher) {
        return new Cast<X>(matcher);
    }
}
