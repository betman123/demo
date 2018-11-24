package Biz;

import java.util.ArrayList;
import java.util.List;

import com.yc.bean.User;
import com.yc.dao.DBHelper2;

public class UserBiz {
	
	/**
	 *	��¼���� 
	 * @param username
	 * @param userpwd
	 * @return ���ص�¼�ɹ����û�����
	 * @throws BizException 
	 */
	
	public User login(String username,String userpwd) throws BizException {
		if(username==null || username.trim().isEmpty()) {
			throw new BizException("�������û�����");
		}
		
		if(userpwd==null || userpwd.trim().isEmpty()) {
			throw new BizException("�������û�����");
		}
		
		/*DBHelper helper=new DBHelper();
		List<Object> list=new ArrayList<Object>();
		list.add(username);
		list.add(userpwd);
		String sql="select * from user where account=? and pwd=?";
		Map<String,String> user=helper.findMap(sql, list);
		return user;*/
		
		String sql="select * from user where account=? and pwd=?";
		return DBHelper2.unique(sql, User.class, username,userpwd);
		
	}

	public List<User> findAll() {
		return DBHelper2.select("select * from user", User.class);
	}

	public void add(User user,String repwd) throws BizException {
		if(user.getName()==null || user.getName().trim().isEmpty()) {
			throw new BizException("����д������");
		}
		if(user.getAccount()==null || user.getAccount().trim().isEmpty()) {
			throw new BizException("����д�û�����");
		}
		if(user.getTel()==null || user.getTel().trim().isEmpty()) {
			throw new BizException("����д�绰��");
		}
		if(user.getPwd()==null || user.getPwd().trim().isEmpty()) {
			throw new BizException("����д���룡");
		}
		if(repwd==null) {
			throw new BizException("��ȷ�����룡");
		}
		if(! repwd.equals(user.getPwd())) {
			throw new BizException("�������벻һ�£�");
		}
		
		String sql="insert into user(name,account,tel,pwd) values(?,?,?,?)";
		DBHelper2.update(sql, user.getName(),user.getAccount(),user.getTel(),user.getPwd());
	}

	public Object select(User user) {
		String sql="select * from user where 1=1 ";
		ArrayList<Object> params=new ArrayList<Object>();
		if(user.getAccount()!=null && user.getAccount().trim().isEmpty()==false) {
			sql+=" and account like concat('%',?,'%') ";   //mysqlƴ�ӷ���
			params.add(user.getAccount());
		}
		if(user.getName()!=null && user.getName().trim().isEmpty()==false) {
			sql+=" and name like ? ";
			params.add("%"+user.getName()+"%");
		}
		if(user.getTel()!=null && ! user.getTel().trim().isEmpty()==false) {
			sql+=" and tel like ? ";
			params.add("%"+user.getTel()+"%");
		}
		return DBHelper2.select(sql, params);
	}

	public User findByid(String id) {
		return DBHelper2.unique("select * from user where id=? ", User.class, id);
	}

	public void save(User user) throws BizException {
		if(user.getAccount()==null ||user.getAccount().trim().isEmpty()) {
			throw new BizException("����д�û���");
		}
		if(user.getName()==null || user.getName().trim().isEmpty()) {
			throw new BizException("����д����");
		}
		DBHelper2.update("update user set name=?, account=?, tel=? where id=? ", 
				user.getName(),user.getAccount(),user.getTel(),user.getId());
	}
	
}
