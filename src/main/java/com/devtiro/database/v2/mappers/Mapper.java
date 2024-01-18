package com.devtiro.database.v2.mappers;

public interface Mapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);

}
