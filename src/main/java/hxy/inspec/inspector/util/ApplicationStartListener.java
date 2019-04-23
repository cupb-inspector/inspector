package hxy.inspec.inspector.util;





import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hxy.inspec.inspector.datasources.ConnectionUtil;


/**
 * Application Lifecycle Listener implementation class ApplicationStartListener
 *
 */
@WebListener
public class ApplicationStartListener implements ServletContextListener, ServletContextAttributeListener,
		HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener,
		ServletRequestListener, ServletRequestAttributeListener {
	private final static Logger logger = LoggerFactory.getLogger(ApplicationStartListener.class);

	/**
	 * Default constructor.
	 */
	public ApplicationStartListener() {
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent se) {
	}

	/**
	 * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
	 */
	public void attributeRemoved(ServletContextAttributeEvent scab) {
	}

	/**
	 * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
	 */
	public void attributeAdded(ServletRequestAttributeEvent srae) {
	}

	/**
	 * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
	 */
	public void attributeReplaced(HttpSessionBindingEvent se) {
	}

	/**
	 * @see HttpSessionActivationListener#sessionWillPassivate(HttpSessionEvent)
	 */
	public void sessionWillPassivate(HttpSessionEvent se) {
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("应用开始启动，正在新建一个用于保存文件的文件夹，在当前系统用户的目录下");
		//再当前用户的目录下面新建一个文件夹，然后从中获取文件
		String fileSaveRootPath = "file";
		File fileFolder= new File(fileSaveRootPath);
		if (!fileFolder.exists()) {
			fileFolder.mkdirs();
		}
		//获取当前目录的绝对路径
		Configration.FILE_ROOT_DIR=fileFolder.getAbsolutePath();
		
		logger.info("启动应用，开始数据库建表！注意修改java文件的代码，这个是建表的密码");
		Connection connection = ConnectionUtil.getConnection();
		String sql = "create table IF NOT EXISTS  orders (orderid int not null primary key  AUTO_INCREMENT,  `custel` VARCHAR(45) NULL, `qualtel` VARCHAR(45) NULL,  `excedate` VARCHAR(45) NULL,  `date` VARCHAR(45) NULL,\n"
				+ "  `factoryname` VARCHAR(45) NULL,\n" + "  `factoryaddress` VARCHAR(45) NULL,\n"
				+ "  `factoryman` VARCHAR(45) NULL,`factorytel` VARCHAR(45) NULL,`profile` VARCHAR(45) NULL,`file` VARCHAR(45) NULL,`fileuuid` VARCHAR(200) NULL,`reportfile` VARCHAR(45) NULL,`reportfileuuid` VARCHAR(200) NULL,`status` VARCHAR(45) NULL,`fee` VARCHAR(45) NULL,`cost` VARCHAR(45) NULL,`othercost` VARCHAR(45) NULL,`profit` VARCHAR(45) NULL,`goods` VARCHAR(45) NULL)default charset=utf8; ";
		PreparedStatement preparedStatement = ConnectionUtil.getPreparedStatement(connection, sql);
		try {
			int flag= preparedStatement.executeUpdate();
			logger.info("新建表的标记"+flag);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("数据库连接失败！");
		}

		String sql0 = "create table IF NOT EXISTS  inspector (userId int not null primary key  AUTO_INCREMENT, `userName` VARCHAR(45) NULL, `userTel` VARCHAR(45) NULL,`userPasswd` VARCHAR(45) NULL ,`userGrade` VARCHAR(45) NULL ,`province` VARCHAR(45) NULL ,`city` VARCHAR(45) NULL ,`district` VARCHAR(45) NULL ,`address` VARCHAR(55) NULL,`rmb` VARCHAR(55) NULL,`email` VARCHAR(55) NULL  )default charset=utf8; ";
		PreparedStatement preparedStatement0 = ConnectionUtil.getPreparedStatement(connection, sql0);
		try {
			logger.info("尝试新建user表");
			preparedStatement0.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("数据库连接失败！");
		}
/*
		String sql1 = "create table IF NOT EXISTS  net_type_and_province (id int not null primary key  AUTO_INCREMENT, `ttkduserid` VARCHAR(45) NULL, `netType` VARCHAR(45) NULL,`province` VARCHAR(45) NULL,`ceiling` VARCHAR(45) NULL,`floor` VARCHAR(45) NULL,`price` VARCHAR(45) NULL,`added` VARCHAR(45) NULL,`profile` VARCHAR(45) NULL  )default charset=utf8; ";
		PreparedStatement preparedStatement1 = ConnectionUtil.getPreparedStatement(connection, sql1);
		try {
			logger.info("尝试新建netTypeAndProvince表");
			 preparedStatement1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("数据库连接失败！");
		}

		String sql2 = "create table IF NOT EXISTS  transfer_bill (id int not null primary key  AUTO_INCREMENT,`ttkduserid` VARCHAR(45) NOT NULL,\n"
				+ "  `number` VARCHAR(15) NULL,\n" + "  `netName` VARCHAR(120) NULL,\n"
				+ "  `province` VARCHAR(100) NULL,\n" + "  `weight` VARCHAR(100) NULL,\n"
				+ "  `cost` VARCHAR(10) NULL,`date` VARCHAR(20) NULL)";
		PreparedStatement preparedStatement2 = ConnectionUtil.getPreparedStatement(connection, sql2);
		try {
			logger.info("尝试新建transferBill表");
			int resultSet1 = preparedStatement2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String sql3 = "create table IF NOT EXISTS  net_to_type (id int not null primary key  AUTO_INCREMENT, `ttkduserid` VARCHAR(45) NULL, `netType` VARCHAR(45) NULL ,`netProfile` VARCHAR(45) NULL )default charset=utf8; ";
		PreparedStatement preparedStatement3 = ConnectionUtil.getPreparedStatement(connection, sql3);
		try {
			logger.info("尝试新建net_to_type表");
			int resultSet0 = preparedStatement3.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("数据库连接失败！");
		}

		/*
		 * String sql3 =
		 * "create table IF NOT EXISTS  result_bill (id int not null primary key  AUTO_INCREMENT,`ttkduserid` VARCHAR(45) NOT NULL,\n"
		 * + "  `number` VARCHAR(15) NULL,\n" + "  `netName` VARCHAR(120) NULL,\n" +
		 * "  `province` VARCHAR(100) NULL,\n" + "  `weight` VARCHAR(100) NULL,\n" +
		 * "  `cost` VARCHAR(10) NULL, PRIMARY KEY (`id`))"; PreparedStatement
		 * preparedStatement3 = ConnectionUtil.getPreparedStatement(connection, sql3);
		 * try { logger.info("尝试新建transferBill表"); int resultSet1 =
		 * preparedStatement3.executeUpdate(); } catch (SQLException e) {
		 * e.printStackTrace(); }
		 * 
		 */
		finally {
			ConnectionUtil.close(preparedStatement, connection);
		}

	}

	/**
	 * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
	 */
	public void attributeAdded(ServletContextAttributeEvent scab) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
	 */
	public void requestDestroyed(ServletRequestEvent sre) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
	 */
	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)
	 */
	public void valueBound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
	 */
	public void requestInitialized(ServletRequestEvent sre) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpSessionActivationListener#sessionDidActivate(HttpSessionEvent)
	 */
	public void sessionDidActivate(HttpSessionEvent se) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
	 */
	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
	 */
	public void attributeAdded(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
	 */
	public void attributeRemoved(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
	 */
	public void attributeReplaced(ServletContextAttributeEvent scab) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpSessionBindingListener#valueUnbound(HttpSessionBindingEvent)
	 */
	public void valueUnbound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
	}

}
