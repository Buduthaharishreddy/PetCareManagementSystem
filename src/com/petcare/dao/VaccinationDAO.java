package com.petcare.dao;
import com.petcare.model.Vaccination; import com.petcare.util.DBConnection;
import java.sql.*; import java.util.*;

public class VaccinationDAO {
 public List<Vaccination> listForOwner(int owner)throws SQLException{
  List<Vaccination> a=new ArrayList<Vaccination>();String sql="SELECT v.*,p.pet_name FROM vaccinations v JOIN pets p ON v.pet_id=p.pet_id WHERE p.owner_id=? ORDER BY v.vaccination_date DESC";
  try(Connection c=DBConnection.getConnection();PreparedStatement q=c.prepareStatement(sql)){q.setInt(1,owner);try(ResultSet r=q.executeQuery()){while(r.next()){Vaccination v=new Vaccination();v.setVaccinationId(r.getInt("vaccination_id"));v.setPetId(r.getInt("pet_id"));v.setPetName(r.getString("pet_name"));v.setVaccineName(r.getString("vaccine_name"));v.setVaccinationDate(r.getDate("vaccination_date"));v.setNextDueDate(r.getDate("next_due_date"));v.setNotes(r.getString("notes"));a.add(v);}}}return a;
 }
 public boolean add(int petId,int staffId,String name,java.sql.Date date,java.sql.Date next,String notes)throws SQLException{
  String sql="INSERT INTO vaccinations(pet_id,staff_user_id,vaccine_name,vaccination_date,next_due_date,notes) VALUES(?,?,?,?,?,?)";
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,petId);p.setInt(2,staffId);p.setString(3,name);p.setDate(4,date);if(next==null)p.setNull(5,Types.DATE);else p.setDate(5,next);p.setString(6,notes);return p.executeUpdate()==1;}
 }
}
