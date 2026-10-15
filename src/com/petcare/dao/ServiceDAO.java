package com.petcare.dao;
import com.petcare.model.Service;
import com.petcare.util.DBConnection;
import java.sql.*; import java.util.*; import java.math.BigDecimal;

public class ServiceDAO {
 public List<Service> listActive() throws SQLException {return list(false);}
 public List<Service> listAll() throws SQLException {return list(true);}
 private List<Service> list(boolean all)throws SQLException{
  List<Service> a=new ArrayList<Service>();String sql="SELECT * FROM services "+(all?"":"WHERE active=1 ")+"ORDER BY service_name";
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql);ResultSet r=p.executeQuery()){while(r.next()){Service s=new Service();s.setServiceId(r.getInt("service_id"));s.setServiceName(r.getString("service_name"));s.setDescription(r.getString("description"));s.setPrice(r.getBigDecimal("price"));s.setActive(r.getBoolean("active"));a.add(s);}}
  return a;
 }
 public boolean save(Service s)throws SQLException{String sql="INSERT INTO services(service_name,description,price,active) VALUES(?,?,?,?)";try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setString(1,s.getServiceName());p.setString(2,s.getDescription());p.setBigDecimal(3,s.getPrice());p.setBoolean(4,s.isActive());return p.executeUpdate()==1;}}
 public boolean update(Service s)throws SQLException{String sql="UPDATE services SET service_name=?,description=?,price=?,active=? WHERE service_id=?";try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setString(1,s.getServiceName());p.setString(2,s.getDescription());p.setBigDecimal(3,s.getPrice());p.setBoolean(4,s.isActive());p.setInt(5,s.getServiceId());return p.executeUpdate()==1;}}
 public boolean delete(int id)throws SQLException{String sql="DELETE FROM services WHERE service_id=?";try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,id);return p.executeUpdate()==1;}}
}
