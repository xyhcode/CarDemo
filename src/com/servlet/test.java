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
 * Servlet implementation class test
 */
@WebServlet("/test")
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int hu=1;
	private int lis=10;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
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
		String json="";
		if(type==null || type=="") {
			type="final";
		}
		
		switch(type) {
		//��ҳ
		case "final":
			Connection con=JDBCTool.openconn();
			//ͳ��������
			String count="select count(*) from autos";
			ResultSet re=JDBCTool.SQuery(count);
			int h=0;
			try {
				while(re.next()) {
					h=re.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//sql���
			String seall="select * from autos";
			//�õ���ǰҳ��
			if(request.getParameter("page")!=null && request.getParameter("page")!="") {
				hu=Integer.valueOf(request.getParameter("page"));
			}
			//�õ�ÿҳ������
			if(request.getParameter("limit")!=null && request.getParameter("limit")!="") {
				lis=Integer.valueOf(request.getParameter("limit"));
			}
			
			//�õ����
			long xd=(hu-1)*lis;
			if(lis!=-1) {
				seall+=" limit "+xd+","+lis;
			}
			//��ѯ���
			//ִ�в�ѯ
			ResultSet res=JDBCTool.SQuery(seall);
			List<Car> CarList=PutCar(res);
			//2����ListCar���JSON�ַ���
			json=JsonTool.jsontool(CarList);
			String heu="{\"code\":\"0\",\"msg\":\"\",\"count\":"+h+",\"data\":"+json+"}";
			JDBCTool.colse(res, con);
			response.getWriter().write(heu);
			break;
		case "del":
			//ɾ��
			String cn=request.getParameter("id");
			String de="delete from autos where id="+Integer.valueOf(cn);
			Connection cm=JDBCTool.openconn();
			int d=JDBCTool.SQLUPdate(de);
			if(d>0) {
				json="u2";
				response.getWriter().write(json);
			}
			break;
		case "edit":
			//�༭
			String cid=request.getParameter("id");
			String cna=request.getParameter("cna");
			String cum=request.getParameter("cum");
			String co=request.getParameter("co");
			String edi="update autos set cname='"+cna+"',cnumber='"+cum+"',ccolor='"+co+"' where id='"+cid+"' ";
			Connection cone=JDBCTool.openconn();
			int e=JDBCTool.SQLUPdate(edi);
			if(e>0) {
				json="u3";
				response.getWriter().write(json);
			}
			break;
		case "add":
			//���
			String na=request.getParameter("acna");
			String num=request.getParameter("acum");
			String col=request.getParameter("aco");
			String add="insert into autos(cname,cnumber,ccolor) value ('"+na+"','"+num+"','"+col+"')";
			Connection cio=JDBCTool.openconn();
			int a=JDBCTool.SQLUPdate(add);
			if(a>0) {
				json="u4";
				response.getWriter().write(json);
			}
			break;
		case "search":
			System.out.println(type);
			//����
			String sname=request.getParameter("sename");
			//sql���
			String sear="select * from autos where 1=1 ";
			//�õ���ǰҳ��
			if(request.getParameter("page")!=null && request.getParameter("page")!="") {
				hu=Integer.valueOf(request.getParameter("page"));
			}
			//�õ�ÿҳ������
			if(request.getParameter("limit")!=null && request.getParameter("limit")!="") {
				lis=Integer.valueOf(request.getParameter("limit"));
			}
			
			String secount="select count(*) from autos where 1=1 ";
			if(sname!=null || sname!="") {
				hu=1;
				sear+=" and cnumber like'%"+sname+"%'";
				secount+=" and cnumber like'%"+sname+"%'";//��ѯ���Ʒ�Ƶ�����
			}
			
			Connection con2=JDBCTool.openconn();
			//ͳ��������
			
			ResultSet re2=JDBCTool.SQuery(secount);
			int h2=0;
			try {
				while(re2.next()) {
					h2=re2.getInt(1);
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			//�õ����
			long xd2=(hu-1)*lis;
			if(lis!=-1) {
				sear+=" limit "+xd2+","+lis;
			}
			
			//��ѯ���
			//ִ�в�ѯ
			ResultSet res2=JDBCTool.SQuery(sear);
			List<Car> CarList2=PutCar(res2);
			//2����ListCar���JSON�ַ���
			json=JsonTool.jsontool(CarList2);
			String heu2="{\"code\":\"0\",\"msg\":\"\",\"count\":"+h2+",\"data\":"+json+"}";
			JDBCTool.colse(res2, con2);
			response.getWriter().write(heu2);
			System.out.println(heu2);
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
