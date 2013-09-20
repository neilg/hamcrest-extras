package com.melessoftware.hamcrest.extras;

import static com.melessoftware.hamcrest.extras.PresentWithValue.present;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class PresentWithValueTest {

    @Test
    public void shouldMatchPresentIfValueMatches() {
        final Matcher<Object> matcher = any(Object.class);

        assertTrue(present(matcher).matches(Optional.of(new Object())));
    }

    @Test
    public void shouldNotMatchPresentIfValueDoesntMatche() {
        final Matcher<Object> matcher = not(any(Object.class));

        assertFalse(present(matcher).matches(Optional.of(new Object())));
    }

    @Test
    public void shouldNotMatchAbsent() {
        assertFalse(present(any(Object.class)).matches(Optional.absent()));
    }

    @Test
    public void shouldHaveMeaningfulDescription() {
        final Matcher<Object> matcher = mock(Matcher.class);
        final StringDescription description = new StringDescription();

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                final Description description = (Description) invocation.getArguments()[0];
                description.appendText("THIS IS THE MATCHER");
                return null;
            }
        }).when(matcher).describeTo(Matchers.<Description>any());

        present(matcher).describeTo(description);

        assertThat(description.toString(), is(equalTo("is present with value THIS IS THE MATCHER")));
    }

    @Test
    public void shouldHaveMeaningfulMismatchDescriptionIfAbsent() {
        final StringDescription description = new StringDescription();
        present(any(Object.class)).describeMismatch(Optional.absent(), description);

        assertThat(description.toString(), is(equalTo("is absent")));
    }

    @Test
    public void shouldHaveMeaningfulMismatchDescriptionValueDoesntMatch() {
        final Object object = new Object();
        final StringDescription description = new StringDescription();
        final Matcher<Object> matcher = not(any(Object.class));
        final String valueMismatch = valueMismatch(matcher, object);

        present(matcher).describeMismatch(Optional.of(object), description);

        assertThat(description.toString(), is(equalTo("is present but " + valueMismatch)));
    }

    private <X> String valueMismatch(Matcher<X> matcher, Object value) {
        final StringDescription description = new StringDescription();
        matcher.describeMismatch(value, description);
        return description.toString();
    }
}
