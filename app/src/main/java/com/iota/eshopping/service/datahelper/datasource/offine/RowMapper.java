package com.iota.eshopping.service.datahelper.datasource.offine;

import android.database.Cursor;

/**
 *
 * @param <T>
 */
public interface RowMapper<T> {

	/**
	 * Map row
	 * @param cursor
	 * @return T
	 * @see Cursor
	 */
	T mappedRow(Cursor cursor);
}
