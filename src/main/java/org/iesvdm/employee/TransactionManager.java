package org.iesvdm.employee;

public interface TransactionManager {

	<T> T doInTransaction(TransactionCode<T> code);

}
