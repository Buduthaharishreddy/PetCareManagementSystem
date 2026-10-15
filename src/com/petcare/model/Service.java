package com.petcare.model;
import java.math.BigDecimal;
public class Service {
 private int serviceId; private String serviceName, description; private BigDecimal price; private boolean active;
 public int getServiceId(){return serviceId;} public void setServiceId(int v){serviceId=v;}
 public String getServiceName(){return serviceName;} public void setServiceName(String v){serviceName=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
 public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
