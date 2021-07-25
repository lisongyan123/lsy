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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by José
 */
public class RepeatingSpliteratorTest {

    @Test
    public void should_return_an_empty_stream_when_called_with_an_empty_stream() {
        // Given
        Stream<String> stream = Stream.empty();
        int repeating = 2;

        // When
        Stream<String> repeatingStream = StreamsUtils.repeat(stream, repeating);

        // Then
        assertThat(repeatingStream.count()).isEqualTo(0L);
    }

    @Test
    public void should_return_a_repeating_stream_when_called_on_a_non_empty_stream() {
        // Given
        Stream<String> stream = Stream.of("a", "b", "c");
        int repeating = 2;

        // When
        Stream<String> repeatingStream = StreamsUtils.repeat(stream, repeating);
        List<String> result = repeatingStream.collect(Collectors.toList());

        // Then
        assertThat(result).containsExactly("a", "a", "b", "b", "c", "c");
    }

    @Test
    public void should_return_a_repeating_stream_when_called_on_a_non_empty_stream_3x() {
        // Given
        Stream<String> stream = Stream.of("a", "b", "c");
        int repeating = 3;

        // When
        Stream<String> repeatingStream = StreamsUtils.repeat(stream, repeating);
        List<String> result = repeatingStream.collect(Collectors.toList());

        // Then
        assertThat(result).containsExactly("a", "a", "a", "b", "b", "b", "c", "c", "c");
    }

    @Test
    public void should_repeat_a_sorted_stream_correctly_and_in_an_unsorted_stream() {
        // Given
        SortedSet<String> sortedSet = new TreeSet<>(Arrays.asList("a", "b", "c"));
        int repeating = 2;

        // When
        Stream<String> stream = StreamsUtils.repeat(sortedSet.stream(), repeating);

        // Then
        assertThat(stream.spliterator().characteristics() & Spliterator.SORTED).isEqualTo(0);
    }

    @Test
    public void should_conform_to_specified_trySplit_behavior() {
        // Given
        Stream<String> strings = Stream.of("one", "two", "three");
        Stream<String> repeatingStream = StreamsUtils.repeat(strings, 2);
        TryAdvanceCheckingSpliterator<String> spliterator = new TryAdvanceCheckingSpliterator<>(repeatingStream.spliterator());
        Stream<String> monitoredStream = StreamSupport.stream(spliterator, false);

        // When
        long count = monitoredStream.count();

        // Then
        assertThat(count).isEqualTo(6L);
    }

    @Test
    public void should_correctly_call_the_onClose_callbacks_of_the_underlying_streams() {
        // Given
        AtomicBoolean b = new AtomicBoolean(false);
        Stream<String> strings = Stream.of("one", "two", "three").onClose(() -> b.set(true));

        // When
        StreamsUtils.repeat(strings, 2).close();

        // Then
        assertThat(b.get()).isEqualTo(true);
    }

    @Test
    public void should_correctly_count_the_elements_of_a_sized_stream() {
        // Given
        Stream<String> strings = Stream.of("a", "b", "c");
        int repeating = 2;
        Stream<String> stream = StreamsUtils.repeat(strings, repeating);

        // When
        long count = stream.count();

        // Then
        assertThat(count).isEqualTo(6L);
    }

    @Test
    public void should_not_build_a_repeating_stream_on_a_non_sized_stream() {
        // Given
        Stream<String> strings = Stream.iterate("+", s -> s);

        // Then
        assertThatIllegalArgumentException().isThrownBy(() -> StreamsUtils.repeat(strings, 3));
    }

    @Test
    public void should_not_build_a_repeating_spliterator_on_a_null_spliterator() {
        // Then
        assertThatNullPointerException().isThrownBy(() -> StreamsUtils.repeat(null, 3));
    }

    @Test
    public void should_not_build_a_repeating_spliterator_with_a_repeating_factor_of_1() {
        // Given
        Stream<String> stream = Stream.of("a1", "a2");

        // Then
        assertThatIllegalArgumentException().isThrownBy(() -> StreamsUtils.repeat(stream, 1));

    }
}