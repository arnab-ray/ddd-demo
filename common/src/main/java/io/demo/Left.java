package io.demo;

public class Left<T, U> extends Either<T, U> {

    private final T value;

    public Left(T value) {
        super();
        this.value = value;
    }

    @Override
    public T left() {
        return this.value;
    }

    @Override
    public U right() {
        throw new IllegalArgumentException("right() called on Left");
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }
}
