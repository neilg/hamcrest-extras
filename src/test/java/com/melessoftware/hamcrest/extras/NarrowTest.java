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

import static com.melessoftware.hamcrest.extras.Narrow.narrow;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Matcher;
import org.junit.Test;

public class NarrowTest {

    private final Matcher<Object> alwaysMatch = any(Object.class);
    private final Matcher<Object> neverMatch = not(alwaysMatch);

    @Test
    public void shouldMatchIfClassConformsAndUnderlyingMatcherMatches() {
        Matcher<String> matcherUnderTest = narrow(alwaysMatch).to(String.class);
        assertTrue(matcherUnderTest.matches("asdf"));
    }

    @Test
    public void shouldNotMatchIfClassDoesNotConformAndUnderlyingMatcherMatches() {
        Matcher<String> matcherUnderTest = narrow(alwaysMatch).to(String.class);
        assertFalse(matcherUnderTest.matches(new Object()));
    }

    @Test
    public void shouldNotMatchIfClassConformsAndUnderlyingMatcherDoesntMatch() {
        Matcher<String> matcherUnderTest = narrow(neverMatch).to(String.class);
        assertFalse(matcherUnderTest.matches("asdf"));
    }
}
