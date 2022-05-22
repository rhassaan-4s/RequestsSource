package com._4s_.attendance.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class MaritalStringKeyGenerator implements IdentifierGenerator {

	private String fieldName = "maritail_code";
	private String tableSeq = "marital_seq";
    public Serializable generate(SessionImplementor session, Object collection) throws HibernateException {
        Connection connection = session.connection();
        PreparedStatement ps = null;
        String result = "";

        try {
            // Oracle-specific code to query a sequence
            ps = connection.prepareStatement("SELECT "+tableSeq+".nextval AS "+fieldName+" FROM dual");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int pk = rs.getInt(fieldName);

                // Convert to a String
                result = String.format("%01d", pk);
            }
        } catch (SQLException e) {
        	try {
        		ps.close();
        		ps = connection.prepareStatement("SELECT (NEXT VALUE FOR " + tableSeq + ") as " + fieldName);
        		ResultSet rs = ps.executeQuery();

        		if (rs.next()) {
        			int pk = rs.getInt(fieldName);

        			// Convert to a String
        			result = String.format("%01d", pk);
        		}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new HibernateException("Unable to generate Primary Key");
			}   
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new HibernateException("Unable to close prepared statement.");
                }
            }
        }

        return result;
    }
}