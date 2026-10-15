package com.petcare.dao;
import com.petcare.model.MedicalRecord; import com.petcare.util.DBConnection;
import java.sql.*; import java.util.*;

public class MedicalRecordDAO {
 public List<MedicalRecord> listForOwner(int owner)throws SQLException{
  List<MedicalRecord> a=new ArrayList<MedicalRecord>();String sql="SELECT m.*,p.pet_name FROM medical_records m JOIN pets p ON m.pet_id=p.pet_id WHERE p.owner_id=? ORDER BY m.record_date DESC";
  try(Connection c=DBConnection.getConnection();PreparedStatement q=c.prepareStatement(sql)){q.setInt(1,owner);try(ResultSet r=q.executeQuery()){while(r.next()){MedicalRecord m=new MedicalRecord();m.setRecordId(r.getInt("record_id"));m.setPetId(r.getInt("pet_id"));m.setPetName(r.getString("pet_name"));m.setRecordDate(r.getDate("record_date"));m.setDiagnosis(r.getString("diagnosis"));m.setTreatment(r.getString("treatment"));m.setNotes(r.getString("notes"));a.add(m);}}}return a;
 }
 public boolean add(int petId,int staffId,java.sql.Date date,String diagnosis,String treatment,String notes)throws SQLException{
  String sql="INSERT INTO medical_records(pet_id,staff_user_id,record_date,diagnosis,treatment,notes) VALUES(?,?,?,?,?,?)";
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,petId);p.setInt(2,staffId);p.setDate(3,date);p.setString(4,diagnosis);p.setString(5,treatment);p.setString(6,notes);return p.executeUpdate()==1;}
 }
}
