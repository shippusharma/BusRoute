package bus.dao;

import ECUtils.BaseDAO;
import static ECUtils.BaseDAO.closeCon;
import static ECUtils.BaseDAO.getCon;
import bus.bean.MyUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO extends BaseDAO{
    public static MyUser validate(String email, String pass){
	MyUser res = null;
	Connection con=null;
	try {
		con =getCon();
		String sql = "select * from app_users where email = ? and pass = ? ";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, email);
		st.setString(i++, pass);
		ResultSet rs = st.executeQuery();
		if(rs.next()){
			MyUser p1 = new MyUser();
			p1.setEmail(rs.getString("email"));
			p1.setPass(rs.getString("pass"));
			p1.setId(rs.getString("id"));
			res=p1;
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		closeCon(con);
	}
	return res;
}

}
