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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;

public class XmlTheSameAsTest {

    @Test
    public void shouldMatchSimpleOpenClose() {
        final Matcher<String> matcherUnderTest = XmlTheSameAs.theSameAs("<tag></tag>");
        assertTrue(matcherUnderTest.matches("<tag></tag>"));
    }

    @Test
    public void shouldNotMatchDifferentTags() {
        final Matcher<String> matcherUnderTest = XmlTheSameAs.theSameAs("<tag></tag>");
        assertFalse(matcherUnderTest.matches("<otherTag></otherTag>"));
    }

    @Test
    public void shouldHaveMeaningfulDescription() {
        final Matcher<String> matcherUnderTest = XmlTheSameAs.theSameAs("<tag><inner>asdf</inner><another>something</another></tag>");
        final StringDescription description = new StringDescription();
        matcherUnderTest.describeTo(description);
        assertThat(description.toString(), is("XML the same as \"<tag><inner>asdf</inner><another>something</another></tag>\""));
    }

    @Test
    public void shouldProvideAMeaningfulDescriptionForFailure() {
        final Matcher<String> matcherUnderTest = XmlTheSameAs.theSameAs("<tag><inner>asdf</inner><another>something</another></tag>");
        final StringDescription mismatchDescription = new StringDescription();
        matcherUnderTest.describeMismatch("<tag><inner>asdf</inner><another>something</another><third/></tag>", mismatchDescription);
        assertThat(mismatchDescription.toString(), containsString("[different]"));
    }
}
