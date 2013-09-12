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

import static com.melessoftware.hamcrest.extras.HasPropertyPath.hasPropertyPath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;

public class HasPropertyPathTest {

    @Test
    public void description() {
        Description description = new StringDescription();
        hasPropertyPath("asdf.qwerty.foo").describeTo(description);
        assertThat(description.toString(), is("has property path \"asdf.qwerty.foo\""));
    }

    @Test
    public void matchesExtantProperty() {
        Foo foo = foo();

        assertTrue(hasPropertyPath("bar.baz.value").matches(foo));
    }

    @Test
    public void doesntMatchMissingProperty() {
        Foo foo = foo();

        assertFalse(hasPropertyPath("bar.bob.bib").matches(foo));
    }

    @Test
    public void mismatchDescriptionHighlightsMissingProperty() {
        Foo foo = foo();
        Description description = new StringDescription();

        hasPropertyPath("bar.bob.bib").describeMismatch(foo, description);

        assertThat(description.toString(), is("property path \"bar.bob\" does not exist"));
    }

    @Test
    public void xmismatchDescriptionHighlightsMissingProperty() {
        Foo foo = foo();
        Description description = new StringDescription();

        hasPropertyPath("bar.baz.bib").describeMismatch(foo, description);

        assertThat(description.toString(), is("property path \"bar.baz.bib\" does not exist"));
    }

    private Foo foo() {
        Baz baz = new Baz();
        baz.setValue("asdf");

        Bar bar = new Bar();
        bar.setBaz(baz);

        Foo foo = new Foo();
        foo.setBar(bar);
        return foo;
    }

    public static class Foo {

        private Bar bar;

        public Bar getBar() {
            return bar;
        }

        public void setBar(Bar bar) {
            this.bar = bar;
        }
    }

    public static class Bar {

        private Baz baz;

        public Baz getBaz() {
            return baz;
        }

        public void setBaz(Baz baz) {
            this.baz = baz;
        }
    }

    public static class Baz {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
