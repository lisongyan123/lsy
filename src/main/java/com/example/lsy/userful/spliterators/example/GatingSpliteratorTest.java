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
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by José
 */
public class GatingSpliteratorTest {

    @Test
    public void should_gate_empty_streams_into_an_empty_stream() {
        // Given
        Stream<String> strings = Stream.empty();
        Predicate<String> validator = String::isEmpty;

        // When
        Stream<String> gatingStream = StreamsUtils.gate(strings, validator);
        long count = gatingStream.count();

        // Then
        assertThat(count).isEqualTo(0);
    }

    @Test
    public void should_gate_a_stream_correctly() {
        // Given
        Stream<String> strings = Stream.of("", "", "", "", "one", "two", "three");

        // When
        Stream<String> gatingStream = StreamsUtils.gate(strings, s -> !s.isEmpty());
        List<String> list = gatingStream.collect(toList());

        // Then
        assertThat(list.size()).isEqualTo(3);
        assertThat(list).containsExactly("one", "two", "three");
    }

    @Test
    public void should_gate_a_stream_correctly_when_the_gate_opens_on_the_first_element() {
        // Given
        Stream<String> strings = Stream.of("one", "two", "three");

        // When
        Stream<String> gatingStream = StreamsUtils.gate(strings, s -> !s.isEmpty());
        List<String> list = gatingStream.collect(toList());

        // Then
        assertThat(list.size()).isEqualTo(3);
        assertThat(list).containsExactly("one", "two", "three");
    }

    @Test
    public void should_gate_a_stream_correctly_when_the_gate_does_not_open() {
        // Given
        Stream<String> strings = Stream.of("", "", "", "");

        // When
        Stream<String> gatingStream = StreamsUtils.gate(strings, s -> !s.isEmpty());
        List<String> list = gatingStream.collect(toList());

        // Then
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    public void should_gate_a_sorted_stream_correctly_and_in_a_sorted_stream() {
        // Given
        SortedSet<String> sortedSet = new TreeSet<>(Arrays.asList("", "", "", "", "one", "two", "three"));

        // When
        Stream<String> gatingStream = StreamsUtils.gate(sortedSet.stream(), s -> !s.isEmpty());

        // Then
        assertThat(gatingStream.spliterator().characteristics() & Spliterator.SORTED).isEqualTo(Spliterator.SORTED);
    }

    @Test
    public void should_conform_to_specified_trySplit_behavior_with_a_normal_stream() {
        // Given
        Stream<String> strings = Stream.of("", "", "", "", "one", "two", "three");
        Stream<String> testedStream = StreamsUtils.gate(strings, s -> !s.isEmpty());
        TryAdvanceCheckingSpliterator<String> spliterator = new TryAdvanceCheckingSpliterator<>(testedStream.spliterator());
        Stream<String> monitoredStream = StreamSupport.stream(spliterator, false);

        // When
        long count = monitoredStream.count();

        // Then
        assertThat(count).isEqualTo(3L);
    }

    @Test
    public void should_conform_to_specified_trySplit_behavior_with_a_fully_valid__stream() {
        // Given
        Stream<String> strings = Stream.of("one", "two", "three");
        Stream<String> testedStream = StreamsUtils.gate(strings, s -> !s.isEmpty());
        TryAdvanceCheckingSpliterator<String> spliterator = new TryAdvanceCheckingSpliterator<>(testedStream.spliterator());
        Stream<String> monitoredStream = StreamSupport.stream(spliterator, false);

        // When
        long count = monitoredStream.count();

        // Then
        assertThat(count).isEqualTo(3L);
    }

    @Test
    public void should_conform_to_specified_trySplit_behavior_with_a_fully_invalid__stream() {
        // Given
        Stream<String> strings = Stream.of("", "", "");
        Stream<String> testedStream = StreamsUtils.gate(strings, s -> !s.isEmpty());
        TryAdvanceCheckingSpliterator<String> spliterator = new TryAdvanceCheckingSpliterator<>(testedStream.spliterator());
        Stream<String> monitoredStream = StreamSupport.stream(spliterator, false);

        // When
        long count = monitoredStream.count();

        // Then
        assertThat(count).isEqualTo(0L);
    }

    @Test
    public void should_conform_to_specified_trySplit_behavior_with_an_empty_stream() {
        // Given
        Stream<String> strings = Stream.empty();
        Stream<String> testedStream = StreamsUtils.gate(strings, s -> !s.isEmpty());
        TryAdvanceCheckingSpliterator<String> spliterator = new TryAdvanceCheckingSpliterator<>(testedStream.spliterator());
        Stream<String> monitoredStream = StreamSupport.stream(spliterator, false);

        // When
        long count = monitoredStream.count();

        // Then
        assertThat(count).isEqualTo(0L);
    }

    @Test
    public void should_correctly_call_the_onClose_callbacks_of_the_underlying_streams() {
        // Given
        AtomicBoolean b = new AtomicBoolean(false);
        Stream<String> strings = Stream.of("", "", "", "", "one", "two", "three").onClose(() -> b.set(true));

        // When
        StreamsUtils.gate(strings, s -> !s.isEmpty()).close();

        // Then
        assertThat(b.get()).isEqualTo(true);
    }

    @Test
    public void should_correctly_count_the_elements_of_a_sized_stream() {
        // Given
        Stream<String> strings = Arrays.asList("", "", "", "one", "two", "three", "four").stream();
        Stream<String> stream = StreamsUtils.gate(strings, s -> !s.isEmpty());

        // When
        long count = stream.count();

        // Then
        assertThat(count).isEqualTo(4L);
    }
}