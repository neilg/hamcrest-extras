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

public class Narrow<T> extends TypeSafeMatcher<T> {

    private final Class<T> klass;
    private final Matcher<?> matcher;

    public Narrow(Class<T> klass, Matcher<?> matcher) {
        super(klass);
        this.klass = klass;
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(T item) {
        return matcher.matches(item);
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendDescriptionOf(matcher)
                .appendText(" restricted to an instance of ")
                .appendValue(klass);
    }

    public static <X> Matcher<X> narrow(Class<X> klass, Matcher<?> matcher) {
        return new Narrow<X>(klass, matcher);
    }

    public static NarrowMatcherBuilder narrow(Matcher<?> matcher) {
        return new NarrowMatcherBuilder(matcher);
    }

    public static class NarrowMatcherBuilder {

        private final Matcher<?> matcher;

        public NarrowMatcherBuilder(Matcher<?> matcher) {
            this.matcher = matcher;
        }

        public <X> Matcher<X> to(Class<X> klass) {
            return narrow(klass, matcher);
        }
    }

}
