package com.blade.jdbc;

import java.io.Serializable;

import org.sql2o.Connection;

public final class AR {

	private AR() {
	}
	
	private static boolean isCache = (null != DB.cache);
	
	public static ARC executeSQL(String sql) {
		Connection connection = DB.sql2o.beginTransaction();
		ARC brc = new ARC(connection, sql.toLowerCase(), isCache);
		return brc;
	}
	
	public static ARC executeSQL(String sql, Object...args) {
		Connection connection = DB.sql2o.beginTransaction();
		ARC brc = new ARC(connection, sql.toLowerCase(), isCache, args);
		return brc;
	}
	
	public static ARC executeSQL(Connection connection, String sql) {
		ARC brc = new ARC(connection, sql.toLowerCase(), isCache);
		return brc;
	}
	
	public static ARC executeSQL(Connection connection, String sql, Object...args) {
		ARC brc = new ARC(connection, sql.toLowerCase(), isCache, args);
		return brc;
	}
	
	public static ARC find(String sql) {
		return executeSQL(DB.sql2o.open(), sql);
	}
	
	public static ARC find(String sql, Object ... args) {
		return executeSQL(DB.sql2o.open(), sql, args);
	}
	
	public static ARC find(QueryParam queryParam) {
		return executeSQL(DB.sql2o.open(), queryParam.asSql(), queryParam.args());
	}
	
	public static Object[] in(Object... args) {
		return args;
	}
	
	public static <T extends Serializable> T findById(Class<T> type, Serializable pk) {
		String sql = "select * from " + ARKit.tableName(type) + " where " + ARKit.pkName(type) +" = ?";
		return find(sql, pk).first(type);
	}
	
	public static ARC update(String sql) {
		return executeSQL(DB.sql2o.beginTransaction(), sql);
	}
	
	public static ARC update(String sql, Object ... args) {
		return executeSQL(DB.sql2o.beginTransaction(), sql, args);
	}
	
}