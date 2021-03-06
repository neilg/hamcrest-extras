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

import com.google.common.base.Optional;
import org.hamcrest.Matcher;

public class ExtraMatchers {

    public static <X> Matcher<X> cast(final Matcher<?> matcher) {
        return Cast.cast(matcher);
    }

    public static <X> Matcher<X> hasPropertyPath(String propertyPath) {
        return HasPropertyPath.hasPropertyPath(propertyPath);
    }

    public static <X> Matcher<X> hasPropertyPath(String propertyPath, Matcher<?> matcher) {
        return HasPropertyPathWithValue.hasPropertyPath(propertyPath, matcher);
    }

    public static <X> Matcher<Optional<X>> present() {
        return Present.present();
    }

    public static <X> Matcher<Optional<X>> present(Matcher<? super X> matcher) {
        return PresentWithValue.present(matcher);
    }

    public static <X> Matcher<Optional<X>> absent() {
        return Absent.absent();
    }
}
