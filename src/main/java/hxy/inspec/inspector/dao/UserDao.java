package hxy.inspec.inspector.dao;

import java.io.IOException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hxy.inspec.inspector.datasources.DataConnection;
import hxy.inspec.inspector.po.User;

public class UserDao {
	private final static Logger logger = LoggerFactory.getLogger(UserDao.class);

	public User selectUserByPhone(String phone) {

		SqlSession sqlSession = null;
		try {
			sqlSession = DataConnection.getSqlSession();
		} catch (IOException e) {
			e.printStackTrace();
		}
		User user = sqlSession.selectOne("User.findUserByNumber", phone);
		sqlSession.commit();// 清空缓存
		sqlSession.close();
		return user;
	}

	public int insert(User user) throws IOException {
		SqlSession sqlSession = DataConnection.getSqlSession();
		int flag = sqlSession.insert("User.insert", user);
		sqlSession.commit();
		sqlSession.close();
		logger.info("插入后结果：" + flag);
		return flag;
	}

	public User selectUserById(String id) throws IOException {
		SqlSession sqlSession = null;
		sqlSession = DataConnection.getSqlSession();
		User user = sqlSession.selectOne("User.findUserById", id);
		sqlSession.commit();// 清空缓存
		sqlSession.close();
		logger.info("查询得到的User："+user);
		return user;
	}

}
