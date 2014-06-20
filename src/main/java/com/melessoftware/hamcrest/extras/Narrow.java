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
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class Narrow<T> extends TypeSafeDiagnosingMatcher<T> {

    private final Class<T> klass;
    private final Matcher<?> matcher;

    public Narrow(final Class<T> klass, final Matcher<?> matcher) {
        super(klass);
        this.klass = klass;
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(final T item, final Description mismatchDescription) {
        final boolean matches = matcher.matches(item);
        if (!matches) {
            matcher.describeMismatch(item, mismatchDescription);
        }
        return matches;
    }

    @Override
    public void describeTo(final Description description) {
        description
                .appendDescriptionOf(matcher)
                .appendText(" restricted to an instance of ")
                .appendValue(klass);
    }

    public static <X> Matcher<X> narrow(final Class<X> klass, final Matcher<?> matcher) {
        return new Narrow<X>(klass, matcher);
    }

    public static NarrowMatcherBuilder narrow(final Matcher<?> matcher) {
        return new NarrowMatcherBuilder(matcher);
    }

    public static class NarrowMatcherBuilder {

        private final Matcher<?> matcher;

        public NarrowMatcherBuilder(final Matcher<?> matcher) {
            this.matcher = matcher;
        }

        public <X> Matcher<X> to(final Class<X> klass) {
            return narrow(klass, matcher);
        }
    }

}
