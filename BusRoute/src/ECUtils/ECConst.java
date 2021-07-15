package ECUtils;
public class ECConst {
	public static String DB_NAME ="fx_busroute";
	public static String DB_HOST="localhost";
	public static String DB_USER="root";
	public static String DB_PASS ="";
	public static String SQLS[] = 
	{
		"create table route (id INT NOT NULL AUTO_INCREMENT, route_no varchar(40), stopage varchar(50), stopage_no int, PRIMARY KEY (id))",	
		"create table app_users (id INT NOT NULL AUTO_INCREMENT, email varchar(40), pass varchar(50), PRIMARY KEY (id))",	
		"insert into app_users (email, pass) values ('admin@gmail.com', 'admin')",	
	};
}
