Hamcrest Extras [![Build Status](https://travis-ci.org/neilg/hamcrest-extras.png?branch=master)](https://travis-ci.org/neilg/hamcrest-extras)
===============
## Matchers
### Property paths

Checking the existence of a nested property, or the value of a nested property

    import static com.melessoftware.hamcrest.extras.ExtraMatchers.hasPropertyPath
    ...
    assertThat(someBean, hasPropertyPath("foo.bar.baz"));
    assertThat(someBean, hasPropertyPath("foo.bar.baz", equalTo("someValue")));

### XML

    import static com.melessoftware.hamcrest.extras.Xml.theSameAs
    ...
    assertThat(someXml, is(theSameAs("<tag>asdf <more>blah</more> sdss</tag>")));
