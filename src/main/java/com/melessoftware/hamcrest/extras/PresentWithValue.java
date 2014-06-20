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

import static org.hamcrest.CoreMatchers.is;

import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class PresentWithValue<T> extends TypeSafeDiagnosingMatcher<Optional<T>> {

    private final Matcher<? super T> matcher;

    public PresentWithValue(final Matcher<? super T> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(final Optional<T> item, final Description mismatchDescription) {
        if (item.isPresent()) {
            final T itemValue = item.get();
            if (matcher.matches(itemValue)) {
                return true;
            } else {
                mismatchDescription.appendText("is present but ");
                matcher.describeMismatch(itemValue, mismatchDescription);
                return false;
            }
        } else {
            mismatchDescription.appendText("is absent");
            return false;
        }
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("is present with value ").appendDescriptionOf(matcher);
    }

    @Factory
    public static <X> Matcher<Optional<X>> present(final Matcher<? super X> matcher) {
        return new PresentWithValue<X>(matcher);
    }

    @Factory
    public static <X> Matcher<Optional<X>> present(final X value) {
        return new PresentWithValue<X>(is(value));
    }
}
