package com.zona.recreativacr;

import java.util.List;

public interface DataStatus<T> {
    void DataIsLoaded(List<T> list);
    void DataIsInserted();
    void DataIsUpdated();
    void DataIsDeleted();
}
