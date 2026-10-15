package com.petcare.model;
public class Pet {
 private int petId, ownerId; private String petName, species, breed, gender, notes; private double ageYears;
 public int getPetId(){return petId;} public void setPetId(int v){petId=v;}
 public int getOwnerId(){return ownerId;} public void setOwnerId(int v){ownerId=v;}
 public String getPetName(){return petName;} public void setPetName(String v){petName=v;}
 public String getSpecies(){return species;} public void setSpecies(String v){species=v;}
 public String getBreed(){return breed;} public void setBreed(String v){breed=v;}
 public String getGender(){return gender;} public void setGender(String v){gender=v;}
 public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
 public double getAgeYears(){return ageYears;} public void setAgeYears(double v){ageYears=v;}
}
