package com.jia.kaoqin.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KaoqinUtil {
	public static List<String> getList() {
		List<String> list = new ArrayList<String>();
		FileReader fin;
		try {
			fin = new FileReader("C:\\Users\\Administrator\\Desktop\\10000.txt");

			BufferedReader br = new BufferedReader(fin);
			String s = br.readLine();
			while (s != null) {
				list.add(s);
				System.out.println(s);
				s = br.readLine();
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDay(int d) {

		switch (d) {
		case 1:
			return "周一";
		case 2:
			return "周二";
		case 3:
			return "周三";
		case 4:
			return "周四";
		case 5:
			return "周五";
		case 6:
			return "周六";
		case 0:
			return "周日";
		}
		return "";
	}

	/**
	 * 判断是否是同一天
	 */
	@SuppressWarnings("deprecation")
	public static boolean isEqualDate(Date l, Date d) {
		int l_year = l.getYear();
		int l_month = l.getMonth();
		int l_date = l.getDate();

		int d_year = d.getYear();
		int d_month = d.getMonth();
		int d_date = d.getDate();
		if (l_year == d_year && l_month == d_month && l_date == d_date) {
			return true;
		}
		return false;
	}

	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
	public static String getCurrTime(){
		return format.format(new Date());
	}
	
	public static int  getId(String name) {
		
		int id=0;
		Connection con=null;
		Statement st = null;
		ResultSet rs=null;
		try {
			con=DBUtil.getConnection();
			st = con.createStatement();
			rs=st.executeQuery("select employee_id from employees where employee_name='"+name+"'");
			if(rs.next())
				id=rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 DBUtil.releaseDB(rs, st, con);
		}
		return id;
	}
}
