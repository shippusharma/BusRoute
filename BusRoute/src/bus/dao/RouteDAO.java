package bus.dao;

import ECUtils.BaseDAO;
import static ECUtils.BaseDAO.closeCon;
import static ECUtils.BaseDAO.getCon;
import bus.bean.Route;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class RouteDAO extends BaseDAO {

    public static void insert(Route p1) {
        Connection con = null;
        try {
            con = getCon();
            String sql = "insert into route "
                    + " (route_no, stopage,  stopage_no) "
                    + " values (?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, p1.getRouteNo());
            st.setString(i++, p1.getStopage());
            st.setInt(i++, p1.getStopageNo());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }

    public static void update(Route p1) {
        Connection con = null;
        try {
            con = getCon();
            String sql = "update route "
                    + " set route_no = ? , stopage = ? ,  stopage_no = ? "
                    + " where  id = ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, p1.getRouteNo());
            st.setString(i++, p1.getStopage());
            st.setInt(i++, p1.getStopageNo());
            st.setString(i++, p1.getId());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }

    public static void delete(String id) {
        Connection con = null;
        try {
            con = getCon();
            String sql = "delete from route "
                    + " where id =? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, id);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }

    public static LinkedList<Route> search(String sc, String si) {
        LinkedList<Route> res = new LinkedList<Route>();
        Connection con = null;
        try {
            con = getCon();
            String sql = "select * from route where " + sc + " like ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, "%" + si + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Route p1 = new Route();
                p1.setRouteNo(rs.getString("route_no"));
                p1.setStopage(rs.getString("stopage"));
                p1.setStopageNo(rs.getInt("stopage_no"));
                p1.setId(rs.getString("id"));
                res.add(p1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }

    public static LinkedList<Route> getRouts(String from, String to) {
        LinkedList<Route> res = new LinkedList<Route>();
        Connection con = null;
        try {
            con = getCon();
            String sql = "select * from route where stopage = ? "
                    + " and route_no in (select route_no from route where stopage = ? )";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, to);
            st.setString(i++, from);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Route p1 = new Route();
                p1.setRouteNo(rs.getString("route_no"));
                p1.setStopage(rs.getString("stopage"));
                p1.setStopageNo(rs.getInt("stopage_no"));
                p1.setId(rs.getString("id"));
                res.add(p1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }

    public static LinkedList<String> getConnectingStopages(String from, String to) {
        LinkedList<String> res = new LinkedList<String>();
        Connection con = null;
        try {
            con = getCon();
            String sql = " SELECT distinct stopage from "
                    + " (select r1.stopage from route r1, route r2 "
                    + " where r2.stopage = ?"
                    + " and r1.route_no = r2.route_no) T1"
                    + " INNER JOIN "
                    + " (select r1.stopage from route r1, route r2"
                    + " where r2.stopage = ?"
                    + " and r1.route_no = r2.route_no) T2 "
                    + " USING (stopage)";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, from);
            st.setString(i++, to);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("stopage"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }

    public static LinkedList<String> searchByRouteId(String rid) {
        LinkedList<String> res = new LinkedList<String>();
        Connection con = null;
        try {
            con = getCon();
            String sql = "select * from route where route_no = ? order by stopage_no ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, rid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("stopage"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }

    public static int maxStopageNoByRouteId(String rid) {
        int res = 0;
        Connection con = null;
        try {
            con = getCon();
            String sql = "select max(stopage_no) as mid from route where route_no = ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, rid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                res = rs.getInt("mid");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }

    public static int getStopageNo(String rno, String stopage) {
        int res = 0;
        Connection con = null;
        try {
            con = getCon();
            String sql = "select stopage_no from route where route_no = ? and stopage = ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, rno);
            st.setString(i++, stopage);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                res = rs.getInt("stopage_no");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }

    public static Route findById(String id) {
        Route res = null;
        Connection con = null;
        try {
            con = getCon();
            String sql = "select * from route where id = ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Route p1 = new Route();
                p1.setRouteNo(rs.getString("route_no"));
                p1.setStopage(rs.getString("stopage"));
                p1.setStopageNo(rs.getInt("stopage_no"));
                p1.setId(rs.getString("id"));
                res = p1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }

    public static LinkedList<String> searchStopages() {
        LinkedList<String> res = new LinkedList<String>();
        Connection con = null;
        try {
            con = getCon();
            String sql = "select distinct(stopage) from route order by stopage ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("stopage"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }

    public static void updateStopageNos(String rno, int stopageNo, int upBy) {
        Connection con = null;
        try {
            con = getCon();
            String sql = "update route "
                    + " set stopage_no = stopage_no+" + upBy + "  "
                    + " where  route_no = ? and stopage_no > ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, rno);
            st.setInt(i++, stopageNo);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }

}
