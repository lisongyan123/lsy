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
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by José
 */
public class ShiftingWindowCollectTest {

    @Test
    public void should_collect_an_empty_stream_into_a_stream_of__an_empty_stream() {
        // Given
        // a trick to create an empty ORDERED stream
        Stream<String> strings = Stream.of("one").filter(s -> s.isEmpty());
        int groupingFactor = 3;

        // When
        Stream<String> stream = StreamsUtils.shiftingWindowCollect(strings, groupingFactor, joining());
        long numberOfRolledStreams = stream.count();

        // Then
        assertThat(numberOfRolledStreams).isEqualTo(1);
    }

    @Test
    public void should_collect_a_non_empty_stream_with_correct_substreams_content() {
        // Given
        Stream<String> strings = Stream.of("1", "2", "3", "4", "5", "6", "7");
        int groupingFactor = 3;

        // When
        Stream<String> collectedStream = StreamsUtils.shiftingWindowCollect(strings, groupingFactor, joining());
        List<String> result = collectedStream.collect(toList());

        // When
        assertThat(result.size()).isEqualTo(5);
        assertThat(result.get(0)).isEqualTo("123");
        assertThat(result.get(1)).isEqualTo("234");
        assertThat(result.get(2)).isEqualTo("345");
        assertThat(result.get(3)).isEqualTo("456");
        assertThat(result.get(4)).isEqualTo("567");
    }

    @Test
    public void should_process_a_sorted_stream_correctly_and_in_an_unsorted_stream() {
        // Given
        SortedSet<String> sortedSet = new TreeSet<>(Arrays.asList("2", "4", "2", "4", "2", "4", "2"));
        int groupingFactor = 2;

        // When
        Stream<String> stream = StreamsUtils.shiftingWindowCollect(sortedSet.stream(), groupingFactor, joining());

        // Then
        assertThat(stream.spliterator().characteristics() & Spliterator.SORTED).isEqualTo(0);
    }

    @Test
    public void should_conform_to_specified_trySplit_behavior() {
        // Given
        Stream<String> strings = Stream.of("2", "4", "2", "4", "2", "4", "2");
        int groupingFactor = 3;

        Stream<String> testedStream = StreamsUtils.shiftingWindowCollect(strings, groupingFactor, joining());
        TryAdvanceCheckingSpliterator<String> spliterator = new TryAdvanceCheckingSpliterator<>(testedStream.spliterator());
        Stream<String> monitoredStream = StreamSupport.stream(spliterator, false);

        // When
        long count = monitoredStream.count();

        // Then
        assertThat(count).isEqualTo(5L);
    }

    @Test
    public void should_correctly_call_the_onClose_callbacks_of_the_underlying_streams() {
        // Given
        AtomicBoolean b = new AtomicBoolean(false);
        Stream<String> strings = Stream.of("2", "4", "2", "4", "2", "4", "2").onClose(() -> b.set(true));
        int groupingFactor = 3;

        // When
        StreamsUtils.shiftingWindowCollect(strings, groupingFactor, joining()).close();

        // Then
        assertThat(b.get()).isEqualTo(true);
    }

    @Test
    public void should_correctly_count_the_elements_of_a_sized_stream() {
        // Given
        Stream<String> strings = Stream.of("1", "2", "3", "4", "5", "6", "7");
        int groupingFactor = 3;
        Stream<String> stream = StreamsUtils.shiftingWindowCollect(strings, groupingFactor, joining());

        // When
        long count = stream.count();

        // Then
        assertThat(count).isEqualTo(5L);
    }

    @Test
    public void should_not_build_a_rolling_spliterator_on_a_null_spliterator() {
        // Then
        assertThatNullPointerException().isThrownBy(() -> StreamsUtils.shiftingWindowCollect(null, 3, joining()));
    }

    @Test
    public void should_not_build_a_rolling_spliterator_with_a_grouping_factor_of_1() {
        // Given
        Stream<String> strings = Stream.of("1", "2", "3", "4", "5", "6", "7");
        int groupingFactor = 1;

        // Then
        assertThatIllegalArgumentException().isThrownBy(() -> StreamsUtils.shiftingWindowCollect(strings, groupingFactor, joining()));
    }
}
