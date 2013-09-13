# Meles Software Hamcrest Extras
## Matchers
### Property paths

Checking the existence of a nested property, or the value of a nested property

    import static com.melessoftware.hamcrest.extras.ExtraMatchers.hasPropertyPath
    ...
    assertThat(someBean, hasPropertyPath("foo.bar.baz"));
    assertThat(someBean, hasPropertyPath("foo.bar.baz", equalTo("someValue")));

