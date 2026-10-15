package com.petcare.model;
import java.sql.Date; import java.sql.Time;
public class Appointment {
 private int appointmentId, petId, serviceId; private String petName, serviceName, status, notes; private Date appointmentDate; private Time appointmentTime;
 public int getAppointmentId(){return appointmentId;} public void setAppointmentId(int v){appointmentId=v;}
 public int getPetId(){return petId;} public void setPetId(int v){petId=v;}
 public int getServiceId(){return serviceId;} public void setServiceId(int v){serviceId=v;}
 public String getPetName(){return petName;} public void setPetName(String v){petName=v;}
 public String getServiceName(){return serviceName;} public void setServiceName(String v){serviceName=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
 public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
 public Date getAppointmentDate(){return appointmentDate;} public void setAppointmentDate(Date v){appointmentDate=v;}
 public Time getAppointmentTime(){return appointmentTime;} public void setAppointmentTime(Time v){appointmentTime=v;}
}
