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

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * See the documentation and patterns to be used in this class in the {@link StreamsUtils} factory class.
 *
 * @author José
 */
public class ZippingSpliterator<E1, E2, R> implements Spliterator<R> {

    private final Spliterator<E1> spliterator1;
    private final Spliterator<E2> spliterator2;
    private final BiFunction<? super E1, ? super E2, ? extends R> zipper;

    public static class Builder<E1, E2, R> {

        private Spliterator<E1> spliterator1;
        private Spliterator<E2> spliterator2;
        private BiFunction<? super E1, ? super E2, ? extends R> zipper;

        public Builder() {
        }

        public Builder<E1, E2, R> with(Spliterator<E1> spliterator) {
            this.spliterator1 = Objects.requireNonNull(spliterator);
            return this;
        }

        public Builder<E1, E2, R> and(Spliterator<E2> spliterator) {
            this.spliterator2 = Objects.requireNonNull(spliterator);
            return this;
        }

        public Builder<E1, E2, R> mergedBy(BiFunction<? super E1, ? super E2, ? extends R> zipper) {
            this.zipper = Objects.requireNonNull(zipper);
            return this;
        }

        public ZippingSpliterator<E1, E2, R> build() {
            return new ZippingSpliterator<>(spliterator1, spliterator2, zipper);
        }
    }

    ZippingSpliterator(
            Spliterator<E1> spliterator1, Spliterator<E2> spliterator2,
            BiFunction<? super E1, ? super E2, ? extends R> zipper) {
        this.spliterator1 = Objects.requireNonNull(spliterator1, "spliterator1");
        this.spliterator2 = Objects.requireNonNull(spliterator2, "spliterator2");
        this.zipper = Objects.requireNonNull(zipper, "zipper");
    }

    private static class Wrapper<E> {

        private E e;

        public E get() {
            return this.e;
        }

        public void set(E e) {
            this.e = e;
        }

        static <E> Wrapper<E> empty() {
            return new Wrapper<>();
        }
    }

    @Override
    public boolean tryAdvance(Consumer<? super R> action) {

        Wrapper<E1> wrapper1 = Wrapper.empty();
        boolean advanced1 = spliterator1.tryAdvance(wrapper1::set);
        if (advanced1) {
            Wrapper<E2> wrapper2 = Wrapper.empty();
            boolean advanced2 = spliterator2.tryAdvance(wrapper2::set);

            if (advanced2) {
                action.accept(Objects.requireNonNull(zipper.apply(wrapper1.get(), wrapper2.get())));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Spliterator<R> trySplit() {
        Spliterator<E1> splitedSpliterator1 = spliterator1.trySplit();
        Spliterator<E2> splitedSpliterator2 = spliterator2.trySplit();

        return splitedSpliterator1 == null || splitedSpliterator2 == null ? null :
                new ZippingSpliterator<>(splitedSpliterator1, splitedSpliterator2, zipper);
    }

    @Override
    public long estimateSize() {
        return Long.min(spliterator1.estimateSize(), spliterator2.estimateSize());
    }

    @Override
    public int characteristics() {
        return this.spliterator1.characteristics() & this.spliterator2.characteristics() & ~Spliterator.SORTED;
    }
}