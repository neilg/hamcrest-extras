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
