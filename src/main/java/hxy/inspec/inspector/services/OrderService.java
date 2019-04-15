package hxy.inspec.inspector.services;



import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hxy.inspec.inspector.dao.OrdersDao;
import hxy.inspec.inspector.po.Orders;


public class OrderService {

	private final static Logger logger = LoggerFactory.getLogger(OrderService.class);

	// 插入订单
	public boolean insert(Orders order) {
		OrdersDao orderDao = new OrdersDao();
		try {
			int flag = orderDao.insert(order);
			if (flag == 1) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Orders> selectAllByTel(String tel) throws IOException {
		OrdersDao ordersDao = new OrdersDao();
		List<Orders> list = ordersDao.selectAllByTel(tel);
		return list;
	}

	public List<Orders> selectAll() throws IOException {
		OrdersDao ordersDao = new OrdersDao();
		List<Orders> list = ordersDao.selectAll();
		return list;
	}

	public List<Orders> findUserByQualtelAndStatus(Orders tel) throws IOException {
		OrdersDao ordersDao = new OrdersDao();
		List<Orders> list = ordersDao.findUserByQualtelAndStatus(tel);
		return list;
	}
	
	public List<Orders> findOrdersByStatus(String status) throws IOException {
		OrdersDao ordersDao = new OrdersDao();
		List<Orders> list = ordersDao.findOrdersByStatus(status);
		return list;
	}
	
	public Orders selectAllById(String ordersId) throws IOException {
		OrdersDao ordersDao = new OrdersDao();
		Orders list = ordersDao.selectAllById(ordersId);
		return list;
	}

	public boolean updateInspector(Orders order) {
		OrdersDao ordersDao = new OrdersDao();
		int flag = ordersDao.updateInspector(order);
		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateReport(Orders order) {
		// TODO Auto-generated method stub
		OrdersDao ordersDao = new OrdersDao();
		int flag = ordersDao.updateReport(order);
		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	

}
