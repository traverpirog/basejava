package com.javaops.webapp.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@SelectClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapByUuidStorageTest.class,
        MapByFullNameStorageTest.class
})
@Suite
public class AllStorageTest {
}
