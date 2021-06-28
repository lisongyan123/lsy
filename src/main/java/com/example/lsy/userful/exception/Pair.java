package com.example.lsy.userful.exception;

public class Pair<F,S> {
    public final F fst;
    public final S snd;
    private Pair(F fst, S snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public static <F,S> Pair<F,S> of(F fst, S snd) {
        return new Pair<>(fst,snd);
    }

    class TryHandler<Exception, R> {
        private final Exception failure;
        private final R succes;
        public TryHandler(Exception failure, R succes) {
            this.failure = failure;
            this.succes = succes;
        }
    }
}

