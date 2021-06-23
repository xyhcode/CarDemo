package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * ���ݿ������
 * @author ����
 *
 */
public class JDBCTool {
	static String dbname="root";//�˺�
	static String dbpass="root";//����
	static String url="jdbc:mysql://localhost:3306/cctv";//��ַ
	static String driver="com.mysql.jdbc.Driver";//����
	static Connection conn=null;
	static Statement sta=null;
	static ResultSet rs= null;
	
	//���ӷ���
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
	
	//��ѯ����
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
	
	//��ɾ�ķ���
	
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
	
	//�رյķ���
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
