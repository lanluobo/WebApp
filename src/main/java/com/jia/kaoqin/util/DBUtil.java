package com.jia.kaoqin.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jia.kaoqin.po.DaoxiuEntity;
import com.jia.kaoqin.po.DaoxiuUser;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.test.liketry.LikeTry;

public class DBUtil {

	private static DataSource dataSource = null;

	// 数据库连接池应只被初始化一次.
	static {
		dataSource = new ComboPooledDataSource("c3p0");
	}

	public static void main(String[] args) throws Exception {

		System.out.println(getLiketryPhones(0));
	}

	public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	public static void releaseDB(ResultSet resultSet, Statement statement,
			Connection connection) {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("resource")
	public static void saveRecord(DaoxiuEntity entity) {
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=getConnection();
			String sql="insert into daoxiujilu select ?,?,?,?,?,now()";
			ps=con.prepareStatement(sql);
//			删除过去记录，撤销时从该表读取记录
			ps.execute("delete from daoxiujilu where username='"+entity.username+"'");

			ps.setString(1,entity.username);
			ps.setString(2,entity.subject);
			ps.setString(3,entity.summaryId);
			ps.setString(4,entity.affairId);
			ps.setString(5,entity.createTime);
			ps.execute();
			System.out.println(DaoxiuUtil.getSystemTime()+"成功入库"+entity.username);

//			为以后查询倒休记录做准备
			sql="insert into daoxiulog select ?,?,?,?,?,?,?,now(),null";
			ps=con.prepareStatement(sql);
			ps.setString(1,entity.username);
			ps.setString(2,entity.subject);
			ps.setString(3,entity.summaryId);
			ps.setString(4,entity.affairId);
			ps.setString(5,"提交");
			ps.setString(6,"手动");
			ps.setString(7,entity.createTime);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(null,ps,con);
		}


	}
	public static void chexiaoUpdateStatus(DaoxiuEntity entity){
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="update daoxiulog set status='撤销',chexiao_time=now() where summary_id=? and affair_id=?";
			ps=con.prepareStatement(sql);

			ps.setString(1,entity.summaryId);
			ps.setString(2,entity.affairId);
			int code=ps.executeUpdate();
			if(code==1){
				System.out.println(DaoxiuUtil.getSystemTime()+entity.username+": 数据库倒休状态更新成功 summaryId="+entity.summaryId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(rs,ps,con);
		}
	}
	public static void selectRecord(DaoxiuEntity entity) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="select summary_id,affair_id from daoxiujilu where username=?";
			ps=con.prepareStatement(sql);

			ps.setString(1,entity.username);
			rs=ps.executeQuery();
			if(rs.next()){
				entity.summaryId=rs.getString(1);
				entity.affairId=rs.getString(2);
				System.out.println(DaoxiuUtil.getSystemTime()+entity.username+": 数据库获取 summaryId="+entity.summaryId+" affairId="+entity.affairId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(rs,ps,con);
		}
	}
	public static List<DaoxiuUser>selectUser(){
		List<DaoxiuUser>list=new ArrayList<DaoxiuUser>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="select * from daoxiu_user";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				DaoxiuUser user=new DaoxiuUser();
				user.employeeId=rs.getInt(1);
				user.username=rs.getString(2);
				user.password=rs.getString(3);
				user.status=rs.getInt(4);
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(rs,ps,con);
		}
		return list;
	}


	public static List<LikeTry> getLiketryPhones(int shidou){

		List<LikeTry>list=new ArrayList<LikeTry>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="select phone,password,cookie from liketry where shidou = "+shidou;
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			LikeTry likeTry;
			while(rs.next()){
				likeTry=new LikeTry();
				likeTry.setPhone(rs.getString(1));
				likeTry.setPassword(rs.getString(2));
				likeTry.setCookie(rs.getString(3));
				list.add(likeTry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(rs,ps,con);
		}
		return list;
	}

	public static void updateCookie(LikeTry likeTry){
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="update liketry set cookie=?,update_time=now() where phone=? ";
			ps=con.prepareStatement(sql);

			ps.setString(1,likeTry.getCookie());
			ps.setString(2,likeTry.getPhone());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(rs,ps,con);
		}
	}
	public static void updateShidou(LikeTry likeTry){
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="update liketry set shidou=?,update_time=now() where phone=? ";
			ps=con.prepareStatement(sql);

			ps.setInt(1,likeTry.getShidou());
			ps.setString(2,likeTry.getPhone());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(rs,ps,con);
		}
	}
	public static void insertLiketry(LikeTry likeTry) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="insert into liketry(phone,password) values(?,?) ";
			ps=con.prepareStatement(sql);

			ps.setString(1,likeTry.getPhone());
			ps.setString(2,likeTry.getPassword());
			if(ps.executeUpdate()==1){
				System.out.println(likeTry.getPhone()+"，账号已存数据库");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(rs,ps,con);
		}
	}

	public static void insertPhone(List<String> list) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			StringBuffer sql=new StringBuffer("insert into  phones(phone) values ");
			for (String s:list){
				sql.append("("+s+"),");
			}
			ps=con.prepareStatement(sql.substring(0,sql.length()-1));
			System.out.println("成功存入数据库："+ps.executeUpdate()+"条记录");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(rs,ps,con);
		}
	}
}