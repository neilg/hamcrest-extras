package com.melessoftware.hamcrest.extras;

import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class Absent<T> extends TypeSafeDiagnosingMatcher<Optional<T>> {

    @Override
    protected boolean matchesSafely(Optional<T> item, Description mismatchDescription) {
        if (item.isPresent()) {
            mismatchDescription.appendText("is present");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is absent");
    }

    @Factory
    public static <X> Matcher<Optional<X>> absent() {
        return new Absent<X>();
    }
}
