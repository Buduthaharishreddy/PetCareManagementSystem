package com.petcare.dao;
import com.petcare.model.Appointment; import com.petcare.util.DBConnection;
import java.sql.*; import java.util.*;

public class AppointmentDAO {
 public List<Appointment> listForOwner(int owner)throws SQLException{
  List<Appointment> a=new ArrayList<Appointment>();String sql="SELECT a.*,p.pet_name,s.service_name FROM appointments a JOIN pets p ON a.pet_id=p.pet_id JOIN services s ON a.service_id=s.service_id WHERE p.owner_id=? ORDER BY a.appointment_date DESC,a.appointment_time DESC";
  try(Connection c=DBConnection.getConnection();PreparedStatement q=c.prepareStatement(sql)){q.setInt(1,owner);try(ResultSet r=q.executeQuery()){while(r.next())a.add(map(r));}}return a;
 }
 public boolean hasConflict(int petId,java.sql.Date date,Time time)throws SQLException{
  String sql="SELECT appointment_id FROM appointments WHERE appointment_date=? AND appointment_time=? AND status='BOOKED'";
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setDate(1,date);p.setTime(2,time);try(ResultSet r=p.executeQuery()){return r.next();}}
 }
 public boolean book(int petId,int serviceId,java.sql.Date date,Time time,String notes)throws SQLException{
  String sql="INSERT INTO appointments(pet_id,service_id,appointment_date,appointment_time,notes) VALUES(?,?,?,?,?)";
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,petId);p.setInt(2,serviceId);p.setDate(3,date);p.setTime(4,time);p.setString(5,notes);return p.executeUpdate()==1;}
 }
 public boolean cancel(int id,int owner)throws SQLException{
  String sql="UPDATE appointments a JOIN pets p ON a.pet_id=p.pet_id SET a.status='CANCELLED' WHERE a.appointment_id=? AND p.owner_id=? AND a.status='BOOKED'";
  try(Connection c=DBConnection.getConnection();PreparedStatement q=c.prepareStatement(sql)){q.setInt(1,id);q.setInt(2,owner);return q.executeUpdate()==1;}
 }
 private Appointment map(ResultSet r)throws SQLException{Appointment a=new Appointment();a.setAppointmentId(r.getInt("appointment_id"));a.setPetId(r.getInt("pet_id"));a.setServiceId(r.getInt("service_id"));a.setPetName(r.getString("pet_name"));a.setServiceName(r.getString("service_name"));a.setAppointmentDate(r.getDate("appointment_date"));a.setAppointmentTime(r.getTime("appointment_time"));a.setStatus(r.getString("status"));a.setNotes(r.getString("notes"));return a;}
}
