package com.melessoftware.hamcrest.extras;

import static org.hamcrest.CoreMatchers.is;

import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class PresentWithValue<T> extends TypeSafeDiagnosingMatcher<Optional<T>> {

    private final Matcher<? super T> matcher;

    public PresentWithValue(Matcher<? super T> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(Optional<T> item, Description mismatchDescription) {
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
    public void describeTo(Description description) {
        description.appendText("is present with value ").appendDescriptionOf(matcher);
    }

    @Factory
    public static <X> Matcher<Optional<X>> present(Matcher<? super X> matcher) {
        return new PresentWithValue<X>(matcher);
    }

    @Factory
    public static <X> Matcher<Optional<X>> present(X value) {
        return new PresentWithValue<X>(is(value));
    }
}
