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

import java.io.IOException;

import org.custommonkey.xmlunit.Diff;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.xml.sax.SAXException;

public class XmlTheSameAs extends TypeSafeDiagnosingMatcher<String> {

    private final String xml;

    public XmlTheSameAs(final String xml) {
        this.xml = xml;
    }

    @Override
    protected boolean matchesSafely(final String item, final Description mismatchDescription) {
        try {
            final Diff diff = new Diff(xml, item);
            final boolean identical = diff.identical();
            if(!identical) {
                mismatchDescription.appendText(diff.toString());
            }
            return identical;
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void describeTo(final Description description) {
        description
                .appendText("XML the same as ")
                .appendValue(xml);
    }

    public static Matcher<String> theSameAs(final String xml) {
        return new XmlTheSameAs(xml);
    }
}
