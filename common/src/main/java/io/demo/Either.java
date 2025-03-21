package io.demo;

public abstract class Either<T, U> {

    public abstract T left();

    public abstract U right();

    public abstract boolean isLeft();

    public abstract boolean isRight();
}
