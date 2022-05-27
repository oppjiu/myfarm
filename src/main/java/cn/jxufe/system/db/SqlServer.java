package cn.jxufe.system.db;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

public class SqlServer extends ImprovedNamingStrategy {

    public static final NamingStrategy INSTANCE = new SqlServer();

    private static final long serialVersionUID = 1383021413247872469L;

    @Override
    public String tableName(String tableName) {
        return tableName;
    }

    @Override
    public String columnName(String columnName) {
        return columnName;
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return propertyName;
    }
	
	/*
	
	@Override
	//public String classToTableName(String className) {
	public String classToTableName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	
	/*
	@Override
	// public String collectionTableName(String ownerEntityTable, String associatedEntityTable, String propertyName) {
	public String collectionTableName(String arg0, String arg1, String arg2,
			String arg3, String arg4) {
		// TODO Auto-generated method stub
		return "OK5";
	}*/


/*
	@Override
	//public String foreignKeyColumnName(String propertyName, String propertyTableName, String referencedColumnName) {
	public String foreignKeyColumnName(String arg0, String arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		return arg0+StringHelper
			    .unqualify(arg3)+"OK";
	}*/

    @Override
    //public String joinKeyColumnName(String joinedColumn, String joinedTable) {
    public String joinKeyColumnName(String arg0, String arg1) {
        return columnName(arg0);
    }
/*
	@Override
	// public String logicalCollectionColumnName(String columnName,  String propertyName, String referencedColumn) {
	public String logicalCollectionColumnName(String arg0, String arg1,
			String arg2) {
		// TODO Auto-generated method stub
		return "OK2";
	}*/

	/*
	@Override
	// public String logicalCollectionTableName(String tableName,   String ownerEntityTable, String associatedEntityTable,   String propertyName) {
	public String logicalCollectionTableName(String arg0, String arg1,
			String arg2, String arg3) {
		// TODO Auto-generated method stub
		return "OK3";
	}*/

	/*
	@Override
	//public String logicalColumnName(String columnName, String propertyName) {
	public String logicalColumnName(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return arg0 + arg1;
	}*/
}
