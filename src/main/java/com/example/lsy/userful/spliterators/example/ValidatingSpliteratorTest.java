/*
 * Copyright (C) 2015 José Paumard
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.lsy.userful.spliterators.example;

import com.example.lsy.userful.spliterators.utils.StreamsUtils;
import com.example.lsy.userful.spliterators.utils.TryAdvanceCheckingSpliterator;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by José
 */
public class ValidatingSpliteratorTest {

    @Test
    public void should_validate_empty_streams_into_an_empty_stream() {
        // Given
        Stream<String> strings = Stream.empty();
        Predicate<String> validator = String::isEmpty;

        // When
        Stream<String> validateStream =
                StreamsUtils.validate(strings, validator, Function.identity(), Function.identity());
        long count = validateStream.count();

        // Then
        assertThat(count).isEqualTo(0);
    }

    @Test
    public void should_validated_a_stream_correctly_with_one_transformation_for_invalid_elements() {
        // Given
        Stream<String> strings = Stream.of("one", null, "two", null, "three");
        Predicate<String> validator = Objects::nonNull;
        UnaryOperator<String> transformIfNotValid = s -> "";

        // When
        Stream<String> validateStream =
                StreamsUtils.validate(strings, validator, transformIfNotValid);
        List<String> list = validateStream.collect(toList());

        // Then
        assertThat(list.size()).isEqualTo(5);
        assertThat(list).containsExactly("one", "", "two", "", "three");
    }

    @Test
    public void should_validated_a_stream_correctly_with_two_transformations_for_valid_and_invalid_elements() {
        // Given
        Stream<String> strings = Stream.of("one", "two", "three");
        Predicate<String> validator = s -> s.length() == 3;
        Function<String, String> transformIfValid = String::toUpperCase;
        Function<String, String> transformIfNotValid = s -> "-";

        // When
        Stream<String> validateStream =
                StreamsUtils.validate(strings, validator, transformIfValid, transformIfNotValid);
        List<String> list = validateStream.collect(toList());

        // Then
        assertThat(list.size()).isEqualTo(3);
        assertThat(list).containsExactly("ONE", "TWO", "-");
    }

    @Test
    public void should_validate_a_sorted_stream_correctly_and_in_a_sorted_stream() {
        // Given
        Stream<String> sortedStream = new TreeSet<>(Arrays.asList("a", "b", "c")).stream();
        Predicate<String> validator = s -> s.length() == 3;
        Function<String, String> transformIfValid = String::toUpperCase;
        Function<String, String> transformIfNotValid = s -> "-";

        // When
        Stream<String> stream =
                StreamsUtils.validate(sortedStream, validator, transformIfValid, transformIfNotValid);

        // Then
        assertThat(stream.spliterator().characteristics() & Spliterator.SORTED).isEqualTo(0);
    }

    @Test
    public void should_conform_to_specified_trySplit_behavior() {
        // Given
        Stream<String> strings = Stream.of("one", "two", "three");
        Predicate<String> validator = s -> s.length() == 3;
        Function<String, String> transformIfValid = String::toUpperCase;
        Function<String, String> transformIfNotValid = s -> "-";

        Stream<String> testedStream =
                StreamsUtils.validate(strings, validator, transformIfValid, transformIfNotValid);
        TryAdvanceCheckingSpliterator<String> spliterator = new TryAdvanceCheckingSpliterator<>(testedStream.spliterator());
        Stream<String> monitoredStream = StreamSupport.stream(spliterator, false);

        // When
        long count = monitoredStream.count();

        // Then
        assertThat(count).isEqualTo(3L);
    }

    @Test
    public void should_correctly_call_the_onClose_callbacks_of_the_underlying_streams() {
        // Given
        AtomicBoolean b = new AtomicBoolean(false);
        Stream<String> strings = Stream.of("one", "two", "three").onClose(() -> b.set(true));
        Predicate<String> validator = s -> s.length() == 3;
        Function<String, String> transformIfValid = String::toUpperCase;
        Function<String, String> transformIfNotValid = s -> "-";

        // When
        StreamsUtils.validate(strings, validator, transformIfValid, transformIfNotValid).close();

        // Then
        assertThat(b.get()).isEqualTo(true);
    }

    @Test
    public void should_correctly_count_the_elements_of_a_sized_stream() {
        // Given
        Stream<String> strings = Stream.of("one", "two", "three");
        Predicate<String> validator = s -> s.length() == 3;
        Function<String, String> transformIfValid = String::toUpperCase;
        Function<String, String> transformIfNotValid = s -> "-";
        Stream<String> stream =
                StreamsUtils.validate(strings, validator, transformIfValid, transformIfNotValid);

        // When
        long count = stream.count();

        // Then
        assertThat(count).isEqualTo(3L);
    }
}