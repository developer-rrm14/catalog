package com.rrm14.catalog.constants;

public class Queries {
	
	public static final String  JPQL_PRODUCT =  "SELECT DISTINCT obj "
											  + "FROM Product obj "
											  + "INNER JOIN obj.categories cats "
											  + "WHERE (COALESCE(:categories) IS NULL "
											  + "OR cats IN :categories) "
											  + "AND (UPPER(obj.name) LIKE UPPER(CONCAT('%',:name,'%')))";
	
	public static final String  JPQL_PRODUCTS_WITH_CATEGORIES =  
				"SELECT obj "
			  + "FROM Product obj "
			  + "JOIN FETCH obj.categories "
			  + "WHERE obj IN :products ";
	

}
