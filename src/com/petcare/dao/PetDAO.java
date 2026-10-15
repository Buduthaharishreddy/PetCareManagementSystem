package com.petcare.dao;
import com.petcare.model.Pet;
import com.petcare.util.DBConnection;
import java.sql.*; import java.util.*;

public class PetDAO {
 public List<Pet> listByOwner(int ownerId) throws SQLException {
  List<Pet> list=new ArrayList<Pet>(); String sql="SELECT * FROM pets WHERE owner_id=? ORDER BY pet_id DESC";
  try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,ownerId);try(ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}}
  return list;
 }
 public Pet findById(int id,int ownerId) throws SQLException {
  String sql="SELECT * FROM pets WHERE pet_id=? AND owner_id=?";
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,id);p.setInt(2,ownerId);try(ResultSet r=p.executeQuery()){if(r.next())return map(r);}}
  return null;
 }
 public boolean save(Pet pet) throws SQLException {
  String sql="INSERT INTO pets(owner_id,pet_name,species,breed,age_years,gender,notes) VALUES(?,?,?,?,?,?,?)";
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){bind(p,pet);return p.executeUpdate()==1;}
 }
 public boolean update(Pet pet) throws SQLException {
  String sql="UPDATE pets SET pet_name=?,species=?,breed=?,age_years=?,gender=?,notes=? WHERE pet_id=? AND owner_id=?";
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
   p.setString(1,pet.getPetName());p.setString(2,pet.getSpecies());p.setString(3,pet.getBreed());p.setDouble(4,pet.getAgeYears());p.setString(5,pet.getGender());p.setString(6,pet.getNotes());p.setInt(7,pet.getPetId());p.setInt(8,pet.getOwnerId());return p.executeUpdate()==1;
  }
 }
 public boolean delete(int id,int ownerId) throws SQLException {
  String sql="DELETE FROM pets WHERE pet_id=? AND owner_id=?";
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,id);p.setInt(2,ownerId);return p.executeUpdate()==1;}
 }
 private void bind(PreparedStatement p,Pet x)throws SQLException{p.setInt(1,x.getOwnerId());p.setString(2,x.getPetName());p.setString(3,x.getSpecies());p.setString(4,x.getBreed());p.setDouble(5,x.getAgeYears());p.setString(6,x.getGender());p.setString(7,x.getNotes());}
 private Pet map(ResultSet r)throws SQLException{Pet x=new Pet();x.setPetId(r.getInt("pet_id"));x.setOwnerId(r.getInt("owner_id"));x.setPetName(r.getString("pet_name"));x.setSpecies(r.getString("species"));x.setBreed(r.getString("breed"));x.setAgeYears(r.getDouble("age_years"));x.setGender(r.getString("gender"));x.setNotes(r.getString("notes"));return x;}
}
