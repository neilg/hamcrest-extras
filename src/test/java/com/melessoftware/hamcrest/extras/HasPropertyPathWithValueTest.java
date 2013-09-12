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

import static com.melessoftware.hamcrest.extras.HasPropertyPathWithValue.hasPropertyPathWithValue;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;
import org.junit.Test;

public class HasPropertyPathWithValueTest {
    @Test
    public void description() {
        Description description = new StringDescription();
        hasPropertyPathWithValue("asdf.qwerty.foo", Matchers.<Object>equalTo(10)).describeTo(description);
        assertThat(description.toString(), is("has property path \"asdf.qwerty.foo\" with value <10>"));
    }

    @Test
    public void matchesExtantProperty() {
        Foo foo = foo();

        assertTrue(hasPropertyPathWithValue("bar.baz.value", Matchers.<Object>equalTo("asdf")).matches(foo));
    }

    @Test
    public void doesntMatchMissingProperty() {
        Foo foo = foo();

        assertFalse(hasPropertyPathWithValue("bar.bob.bib", any(Object.class)).matches(foo));
    }

    @Test
    public void doesntMatchValueMismatch() {
        Foo foo = foo();

        assertFalse(hasPropertyPathWithValue("bar.baz.value", Matchers.<Object>equalTo("23")).matches(foo));
    }

    @Test
    public void doesntMatchEmptyProperty() {
        Foo foo = foo();

        assertFalse(hasPropertyPathWithValue("", any(Object.class)).matches(foo));
    }

    @Test
    public void mismatchDescriptionHighlightsEmptyProperty() {
        Foo foo = foo();
        Description description = new StringDescription();

        hasPropertyPathWithValue("", any(Object.class)).describeMismatch(foo, description);

        assertThat(description.toString(), is("property path \"\" does not exist"));
    }

    @Test
    public void descriptionMismatchHighlightsValueMismatch() {
        Foo foo = foo();

        Description description = new StringDescription();
        hasPropertyPathWithValue("bar.baz.value", Matchers.<Object>equalTo("23")).describeMismatch(foo, description);

        assertThat(description.toString(), is("property path \"bar.baz.value\" was \"asdf\""));
    }

    @Test
    public void mismatchDescriptionHighlightsMissingProperty() {
        Foo foo = foo();
        Description description = new StringDescription();

        hasPropertyPathWithValue("bar.bob.bib", Matchers.<Object>equalTo("23")).describeMismatch(foo, description);

        assertThat(description.toString(), is("property path \"bar.bob\" does not exist"));
    }

    @Test
    public void mismatchDescriptionHighlightsThrownException() {
        Bob bob = new Bob();
        Description description = new StringDescription();

        hasPropertyPathWithValue("asdf", Matchers.<Object>equalTo("23")).describeMismatch(bob, description);

        assertThat(description.toString(), is("property path \"asdf\" error \"Hello from bob\""));
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

    public static class Bob {
        public String getAsdf() {
            throw new RuntimeException("Hello from bob");
        }

        public void setAsdf(String asdf) {
        }

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
