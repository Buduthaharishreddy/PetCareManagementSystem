package com.petcare.dao;

import com.petcare.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE email=?";
        try (Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,email);
            try (ResultSet r=p.executeQuery()) { return r.next(); }
        }
    }

    public int register(String name, String email, String phone, String hash, String salt) throws SQLException {
        String sql="INSERT INTO users(full_name,email,phone,password_hash,password_salt,role) VALUES(?,?,?,?,?,'OWNER')";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            p.setString(1,name); p.setString(2,email); p.setString(3,phone); p.setString(4,hash); p.setString(5,salt);
            p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()){ if(r.next()) return r.getInt(1); }
        }
        return -1;
    }

    public UserAuth authenticate(String email) throws SQLException {
        String sql="SELECT user_id,full_name,password_hash,password_salt,role FROM users WHERE email=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,email);
            try(ResultSet r=p.executeQuery()) {
                if(r.next()) return new UserAuth(r.getInt("user_id"),r.getString("full_name"),r.getString("password_hash"),r.getString("password_salt"),r.getString("role"));
            }
        }
        return null;
    }

    public static class UserAuth {
        public int id; public String name, hash, salt, role;
        public UserAuth(int i,String n,String h,String s,String r){id=i;name=n;hash=h;salt=s;role=r;}
    }
}
