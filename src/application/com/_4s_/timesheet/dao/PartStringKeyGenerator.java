package com._4s_.timesheet.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class PartStringKeyGenerator implements IdentifierGenerator {

	private String fieldName = "code";
	private String tableSeq = "time_sheet_part_seq";
	
	
    public Serializable generate(SharedSessionContractImplementor session, Object collection) throws HibernateException {
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
                result = String.format("%010d", pk);
            }
        } catch (SQLException e) {
            throw new HibernateException("Unable to generate Primary Key");
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