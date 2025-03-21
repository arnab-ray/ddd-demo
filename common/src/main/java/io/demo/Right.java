package io.demo;

public class Right<T, U> extends Either<T, U> {

    private final U value;

    public Right(U value) {
        super();
        this.value = value;
    }

    @Override
    public T left() {
        throw new IllegalArgumentException("left() called on Right");
    }

    @Override
    public U right() {
        return this.value;
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }
}
