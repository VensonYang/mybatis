package utils.common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接操作类
 */
public class DBUtil {
	private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>();
	private static String user;
	private static String password;
	private static String driverClass;
	private static String jdbcUrl;

	private DBUtil() {
	}

	// 静态语句块
	static {
		try {
			Properties props = new Properties();
			InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("config/hibernate.properties");
			props.load(in);
			user = props.getProperty("jdbc.user");
			password = props.getProperty("jdbc.password");
			driverClass = "com.mysql.jdbc.Driver";
			jdbcUrl = props.getProperty("jdbc.jdbcUrl");
			// user = "root";
			// password = "fcteam1314";
			// driverClass = props.getProperty("jdbc.driverClass");
			// jdbcUrl = "jdbc:mysql://223.4.173.137:3306/eachnic_db";
			Class.forName(driverClass);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Connection getConnection() throws Exception {
		// 当前线程第一次调用时，新建一个返回
		// 当前线程再次调用，返回第一次创建的
		Connection conn = connLocal.get();
		if (conn == null || conn.isClosed()) {
			conn = DriverManager.getConnection(jdbcUrl, user, password);
			connLocal.set(conn);
		}
		return conn;
	}

	public static void closeConnection() {
		// 清空threadlocal
		// 关闭conn
		Connection conn = connLocal.get();
		connLocal.set(null);
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 放回连接池
		}
	}

	public static void beginTransaction() throws Exception {
		Connection conn = getConnection();
		conn.setAutoCommit(false);
	}

	public static void commit() {
		try {
			Connection conn = getConnection();
			conn.commit();
		} catch (Exception ex) {
		}
	}

	public static void rollback() {
		try {
			Connection conn = getConnection();
			conn.rollback();
		} catch (Exception ex) {
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(getConnection().hashCode());
			System.out.println(getConnection().hashCode());
			System.out.println(getConnection().hashCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
