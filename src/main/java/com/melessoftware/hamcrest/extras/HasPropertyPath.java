package com.melessoftware.hamcrest.extras;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HasPropertyPath<T> extends TypeSafeDiagnosingMatcher<T> {

    private final String propertyPath;

    public HasPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription) {
        final String[] pathParts = propertyPath.split("\\.");
        Object currentItem = item;
        mismatchDescription.appendText("property path \"");
        try {
            for (int i = 0; i < pathParts.length; i++) {
                String pathPart = pathParts[i];
                if (i > 0) {
                    mismatchDescription.appendText(".");
                }
                mismatchDescription.appendText(pathPart);
                PropertyDescriptor pd = new PropertyDescriptor(pathPart, currentItem.getClass());
                Method readMethod = pd.getReadMethod();
                if (readMethod == null) {
                    mismatchDescription.appendText(" is not readable");
                    return false;
                }
                if (i < pathParts.length) {
                    currentItem = readMethod.invoke(currentItem);
                }
            }
        } catch (IntrospectionException ie) {
            mismatchDescription.appendText("\" does not exist");
            return false;
        } catch (InvocationTargetException e) {
            mismatchDescription.appendText("\" ").appendText(e.getMessage());
            return false;
        } catch (IllegalAccessException e) {
            mismatchDescription.appendText("\" ").appendText(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("has property path ")
                .appendValue(propertyPath);
    }

    public static <X> Matcher<X> hasPropertyPath(String propertyPath) {
        return new HasPropertyPath<X>(propertyPath);
    }
}
