package com.example.lsy.userful.exception;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
/**
 * 代码包装成Either类型
 * @param <L>
 * @param <R>
 */
public class Either<L, R> {
    private final L left;
    private final R right;
    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }
    public static <L,R> Either<L,R> Left( L value) {
        return new Either(value, null);
    }
    public static <L,R> Either<L,R> Right( R value) {
        return new Either(null, value);
    }
    public Optional<L> getLeft() {
        return Optional.ofNullable(left);
    }
    public Optional<R> getRight() {
        return Optional.ofNullable(right);
    }
    public boolean isLeft() {
        return left != null;
    }
    public boolean isRight() {
        return right != null;
    }
    public <T> Optional<T> mapLeft(Function<? super L, T> mapper) {
        if (isLeft()) {
            return Optional.of(mapper.apply(left));
        }
        return Optional.empty();
    }
    public <T> Optional<T> mapRight(Function<? super R, T> mapper) {
        if (isRight()) {
            return Optional.of(mapper.apply(right));
        }
        return Optional.empty();
    }
    public String toString() {
        if (isLeft()) {
            return "Left(" + left +")";
        }
        return "Right(" + right +")";
    }

    /**发生异常 流的处理立即终止*/
    public static <T,R> Function<T,R> warp(CheckedFunction<T,R> function){
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**你也可以不返回Exception类型*/
    public static <T,K> Function<T, Either> lift(CheckedFunction<T,K> function) {
        return t -> {
            try {
                return Either.Right(function.apply(t));
            } catch (Exception ex) {
                return Either.Left(ex);
            }
        };
    }

    /**解析**/
    public <T> T disposeResponse(Optional<Either> either) throws Exception {
        if (either.isPresent()){
            Either entity = either.get();
            if (entity.isLeft()){
                Optional<Exception> optional = entity.mapLeft(x -> x);
                log.error("调用天润接口异常："+optional.get().getMessage(),optional.get());
                throw new Exception(optional.get().getMessage());
            }else {
                Optional<T> optional = entity.mapRight(x -> x);
                return optional.get();
            }
        }
        return null;
    }

    /**异常和原始值保存在左侧*/
    public static <T,R> Function<T, Either> liftWithValue(CheckedFunction<T,R> function) {
        return t -> {
            try {
                return Either.Right(function.apply(t));
            } catch (Exception ex) {
                return Either.Left(Pair.of(ex,t));
            }
        };
    }

    /**
     * 原接口是不支持抛弃异常的 现在我就弄一个支持抛弃异常的接口
     * @param <T>
     * @param <R>
     */
    @FunctionalInterface
    interface CheckedFunction<T,R> {
        R apply(T t) throws Exception;
    }
}

