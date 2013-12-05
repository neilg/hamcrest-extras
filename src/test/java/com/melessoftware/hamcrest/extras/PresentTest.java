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

import static com.melessoftware.hamcrest.extras.Present.present;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.google.common.base.Optional;
import org.hamcrest.StringDescription;
import org.junit.Test;

public class PresentTest {

    @Test
    public void shouldMatchPresent() {
        assertTrue(present().matches(Optional.of(new Object())));
    }

    @Test
    public void shouldNotMatchAbsent() {

        assertFalse(present().matches(Optional.absent()));
    }

    @Test
    public void shouldHaveMeaningfulDescription() {
        final StringDescription description = new StringDescription();
        present().describeTo(description);

        assertThat(description.toString(), is(equalTo("is present")));
    }

    @Test
    public void shouldHaveMeaningfulMismatchDescription() {
        final StringDescription description = new StringDescription();
        present().describeMismatch(Optional.absent(), description);

        assertThat(description.toString(), is(equalTo("is absent")));
    }
}
