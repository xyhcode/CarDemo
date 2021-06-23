package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Car;
import com.util.JDBCTool;
import com.util.JsonTool;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int hu=1;
	private int lis=10;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		String type=request.getParameter("type");
		if(type==null || type=="") {
			type="search";
		}
		switch(type) {
		case "search":
			//搜索
			String sname=request.getParameter("sename");
			//sql语句
			String seall2="select * from autos where 1=1 ";
			//得到当前页数
			if(request.getParameter("page")!=null && request.getParameter("page")!="") {
				hu=Integer.valueOf(request.getParameter("page"));
			}
			//得到每页的数据
			if(request.getParameter("limit")!=null && request.getParameter("limit")!="") {
				lis=Integer.valueOf(request.getParameter("limit"));
			}
			
			String count2="select count(*) from autos where 1=1 ";
			if(sname!=null || sname!="") {
				hu=1;
				seall2+=" and cnumber like'%"+sname+"%'";
				count2+=" and cnumber like'%"+sname+"%'";//查询相关品牌的数量
			}
			
			long xd2=(hu-1)*lis;
			if(lis!=-1) {
				seall2+=" limit "+xd2+","+lis;
			}
			
			ResultSet res2=JDBCTool.SQuery(seall2);
			List<Car> CarList2=PutCar(res2);
			//2、把ListCar变成JSON字符串
			String json=JsonTool.jsontool(CarList2);
			
			Connection con2=JDBCTool.openconn();
			//统计总条数
			ResultSet re2=JDBCTool.SQuery(count2);
			int h2=0;
			try {
				while(re2.next()) {
					h2=re2.getInt(1);
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			//得到起点
			
			
			//查询语句
			//执行查询
			String heu2="{\"code\":\"0\",\"msg\":\"\",\"count\":"+h2+",\"data\":"+json+"}";
			JDBCTool.colse(res2, con2);
			response.getWriter().write(heu2);
			System.out.println(heu2);
			break;
		}
	}
	
	private List<Car> PutCar(ResultSet res) {
		// TODO Auto-generated method stub
		List<Car> Carall=new ArrayList<Car>();
		try {
			while(res.next()) {
				int id=res.getInt(1);
				String name=res.getString(2);
				String number=res.getString(3);
				String color=res.getString(4);
				Car newcar=new Car(id,name,number,color);
				Carall.add(newcar);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Carall;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
