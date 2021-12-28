package database;

public class Query {
	public static String getDelQuery(String table, String col, String id) {
		return "delete from " + table + " where " + col + " = " + "'" + id + "'";
	}

	public static String getSelectAll(String table) {
		return "select * from " + table;
	}
	
	public static String getInsertQuery(String table, String values) {
		return "insert into " + table + " values (" + values + ")"; 
	}
	
	public static String getUpdateQuery(String table, String update, String col, String condition) {
		return "update " + table + " set " + update + " where " + col + " = " + "'" + condition + "'";
	}
	
	public static String getSelectAllWithCondition(String table,String col, String condition) {
		return "select * from " + table + " where " + col + " = " + "'" + condition + "'"; 
	}
	
	public static String getSumAllWithCondition(String table, String col, String condition) {
		return "select sum(" + col + ") from " + table + " where " + condition; 
	}
	
	public static String getLastRow(String table, String col) {
		return "select * from " + table + " ORDER BY " +  col + " DESC LIMIT 1";
	}
}
