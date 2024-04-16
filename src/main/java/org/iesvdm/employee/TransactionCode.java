package org.iesvdm.employee;

import java.util.function.Function;

@FunctionalInterface
public interface TransactionCode<T> extends Function<EmployeeRepository, T> {

}
