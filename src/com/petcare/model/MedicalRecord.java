package com.petcare.model;
import java.sql.Date;
public class MedicalRecord {
 private int recordId, petId; private String petName, diagnosis, treatment, notes; private Date recordDate;
 public int getRecordId(){return recordId;} public void setRecordId(int v){recordId=v;}
 public int getPetId(){return petId;} public void setPetId(int v){petId=v;}
 public String getPetName(){return petName;} public void setPetName(String v){petName=v;}
 public String getDiagnosis(){return diagnosis;} public void setDiagnosis(String v){diagnosis=v;}
 public String getTreatment(){return treatment;} public void setTreatment(String v){treatment=v;}
 public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
 public Date getRecordDate(){return recordDate;} public void setRecordDate(Date v){recordDate=v;}
}
