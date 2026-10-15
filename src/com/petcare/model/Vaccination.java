package com.petcare.model;
import java.sql.Date;
public class Vaccination {
 private int vaccinationId, petId; private String petName, vaccineName, notes; private Date vaccinationDate, nextDueDate;
 public int getVaccinationId(){return vaccinationId;} public void setVaccinationId(int v){vaccinationId=v;}
 public int getPetId(){return petId;} public void setPetId(int v){petId=v;}
 public String getPetName(){return petName;} public void setPetName(String v){petName=v;}
 public String getVaccineName(){return vaccineName;} public void setVaccineName(String v){vaccineName=v;}
 public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
 public Date getVaccinationDate(){return vaccinationDate;} public void setVaccinationDate(Date v){vaccinationDate=v;}
 public Date getNextDueDate(){return nextDueDate;} public void setNextDueDate(Date v){nextDueDate=v;}
}
