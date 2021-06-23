package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 数据库操作类
 * @author 林沐
 *
 */
public class JDBCTool {
	static String dbname="root";//账号
	static String dbpass="root";//密码
	static String url="jdbc:mysql://localhost:3306/cctv";//地址
	static String driver="com.mysql.jdbc.Driver";//驱动
	static Connection conn=null;
	static Statement sta=null;
	static ResultSet rs= null;
	
	//连接方法
	public static Connection openconn() {
		try {
			Class.forName(driver);
			try {
				conn=DriverManager.getConnection(url,dbname,dbpass);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	//查询方法
	public static ResultSet SQuery(String sql) {
		Connection conn=openconn();
		try {
			sta=conn.createStatement();
			rs=sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	//增删改方法
	
	public static int SQLUPdate(String sql) {
		int t=0;
		Connection con=openconn();
		try {
			sta=con.createStatement();
			t=sta.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	
	//关闭的方法
	public static void colse(ResultSet rs,Statement st,Connection con) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(st!=null) {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}

	public static void colse(ResultSet rs,Connection con) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
