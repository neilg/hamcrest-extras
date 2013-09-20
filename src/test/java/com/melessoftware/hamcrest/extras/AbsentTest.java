package com.melessoftware.hamcrest.extras;

import static com.melessoftware.hamcrest.extras.Absent.absent;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.google.common.base.Optional;
import org.hamcrest.StringDescription;
import org.junit.Test;

public class AbsentTest {

    @Test
    public void shouldMatchAbsent() {
        assertTrue(absent().matches(Optional.absent()));
    }

    @Test
    public void shouldNotMatchPresent() {
        assertFalse(absent().matches(Optional.of(new Object())));
    }

    @Test
    public void shouldHaveMeaningfulDescription() {
        final StringDescription description = new StringDescription();
        absent().describeTo(description);

        assertThat(description.toString(), is(equalTo("is absent")));
    }

    @Test
    public void shouldHaveMeaningfulMismatchDescription() {
        final StringDescription description = new StringDescription();
        absent().describeMismatch(Optional.of(new Object()), description);

        assertThat(description.toString(), is(equalTo("is present")));
    }

}
