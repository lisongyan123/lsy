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

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by José
 */
public class ShiftingWindowSummarizingDoubleTest {

    @Test
    public void should_summarize_an_empty_stream_into_a_stream_of_an_empty_stream() {
        // Given
        // a trick to create an empty ORDERED stream
        Stream<Double> doubles = Stream.of(1d, 2d, 3d).filter(d -> d == 0);
        int groupingFactor = 3;

        // When
        Stream<DoubleSummaryStatistics> stream = StreamsUtils.shiftingWindowSummarizingDouble(doubles, groupingFactor, Double::valueOf);
        long numberOfRolledStreams = stream.count();

        // Then
        assertThat(numberOfRolledStreams).isEqualTo(1);
    }

    @Test
    public void should_summarize_a_non_empty_stream_with_correct_substreams_content() {
        // Given
        Stream<String> strings = Stream.of("2", "4", "2", "4", "2", "4", "2");
        int groupingFactor = 2;
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        stats.accept(2d);
        stats.accept(4d);

        // When
        Stream<DoubleSummaryStatistics> summarizedStream = StreamsUtils.shiftingWindowSummarizingDouble(strings, groupingFactor, Double::parseDouble);
        List<DoubleSummaryStatistics> result = summarizedStream.collect(toList());

        // When
        assertThat(result.size()).isEqualTo(6);
        assertThat(result.get(0).toString()).isEqualTo(stats.toString());
        assertThat(result.get(1).toString()).isEqualTo(stats.toString());
        assertThat(result.get(2).toString()).isEqualTo(stats.toString());
        assertThat(result.get(3).toString()).isEqualTo(stats.toString());
        assertThat(result.get(4).toString()).isEqualTo(stats.toString());
        assertThat(result.get(5).toString()).isEqualTo(stats.toString());
    }

    @Test
    public void should_process_a_sorted_stream_correctly_and_in_an_unsorted_stream() {
        // Given
        SortedSet<String> sortedSet = new TreeSet<>(Arrays.asList("2", "4", "2", "4", "2", "4", "2"));
        int groupingFactor = 2;

        // When
        Stream<DoubleSummaryStatistics> stream = StreamsUtils.shiftingWindowSummarizingDouble(sortedSet.stream(), groupingFactor, Double::parseDouble);

        // Then
        assertThat(stream.spliterator().characteristics() & Spliterator.SORTED).isEqualTo(0);
    }

    @Test
    public void should_conform_to_specified_trySplit_behavior() {
        // Given
        Stream<String> strings = Stream.of("2", "4", "2", "4", "2", "4", "2");
        int groupingFactor = 3;

        Stream<DoubleSummaryStatistics> testedStream = StreamsUtils.shiftingWindowSummarizingDouble(strings, groupingFactor, Double::parseDouble);
        TryAdvanceCheckingSpliterator<DoubleSummaryStatistics> spliterator = new TryAdvanceCheckingSpliterator<>(testedStream.spliterator());
        Stream<DoubleSummaryStatistics> monitoredStream = StreamSupport.stream(spliterator, false);

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
        StreamsUtils.shiftingWindowSummarizingDouble(strings, groupingFactor, Double::parseDouble).close();

        // Then
        assertThat(b.get()).isEqualTo(true);
    }

    @Test
    public void should_correctly_count_the_elements_of_a_sized_stream() {
        // Given
        Stream<String> strings = Stream.of("1", "2", "3", "4", "5", "6", "7");
        int groupingFactor = 3;
        Stream<DoubleSummaryStatistics> stream = StreamsUtils.shiftingWindowSummarizingDouble(strings, groupingFactor, Double::parseDouble);

        // When
        long count = stream.count();

        // Then
        assertThat(count).isEqualTo(5L);
    }

    @Test
    public void should_not_build_a_shifting_stream_on_a_null_stream() {
        // Then
        assertThatNullPointerException().isThrownBy(() -> StreamsUtils.shiftingWindowSummarizingDouble(null, 3, Double::parseDouble));
    }

    @Test
    public void should_not_build_a_shifting_stream_with_a_grouping_factor_of_1() {
        // Given
        Stream<String> strings = Stream.of("1", "2", "3", "4", "5", "6", "7");
        int groupingFactor = 1;

        // Then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> StreamsUtils.shiftingWindowSummarizingDouble(strings, groupingFactor, Double::parseDouble));
    }
}
