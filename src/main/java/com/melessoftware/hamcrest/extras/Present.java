package com.melessoftware.hamcrest.extras;

import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class Present<T> extends TypeSafeDiagnosingMatcher<Optional<T>> {

    @Override
    protected boolean matchesSafely(Optional<T> item, Description mismatchDescription) {
        if (item.isPresent()) {
            return true;
        } else {
            mismatchDescription.appendText("is absent");
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is present");
    }

    @Factory
    public static <X> Matcher<Optional<X>> present() {
        return new Present<X>();
    }
}
