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

package com.example.lsy.userful.spliterators;

import com.example.lsy.userful.spliterators.utils.StreamsUtils;

import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * See the documentation and patterns to be used in this class in the {@link StreamsUtils} factory class.
 *
 * @author José
 */
public class InterruptingSpliterator<E> implements Spliterator<E> {

    private final Spliterator<E> spliterator;
    private final Predicate<? super E> interruptor;
    private boolean hasBeenInterrupted = false;

    public static <E> InterruptingSpliterator<E> of(Spliterator<E> spliterator, Predicate<? super E> interruptor) {
        Objects.requireNonNull(spliterator);
        Objects.requireNonNull(interruptor);

        return new InterruptingSpliterator<E>(spliterator, interruptor);
    }

    private InterruptingSpliterator(Spliterator<E> spliterator, Predicate<? super E> interruptor) {
        this.spliterator = spliterator;
        this.interruptor = interruptor;
    }

    @Override
    public boolean tryAdvance(Consumer<? super E> action) {

        boolean hasMore = spliterator.tryAdvance(e -> {
            if (interruptor.test(e)) {
                hasBeenInterrupted = true;
            } else {
                action.accept(e);
            }
        });

        return hasMore && !hasBeenInterrupted;
    }

    @Override
    public Spliterator<E> trySplit() {
        Spliterator<E> split = this.spliterator.trySplit();
        return split == null ? null : new InterruptingSpliterator<>(split, interruptor);
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return this.spliterator.characteristics() & ~Spliterator.SIZED & ~Spliterator.SUBSIZED;
    }

    @Override
    public Comparator<? super E> getComparator() {
        return this.spliterator.getComparator();
    }
}