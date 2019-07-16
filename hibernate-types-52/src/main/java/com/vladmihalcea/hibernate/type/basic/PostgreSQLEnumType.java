package com.vladmihalcea.hibernate.type.basic;

import com.vladmihalcea.hibernate.type.util.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * Maps an {@link Enum} to a PostgreSQL ENUM column type.
 * <p>
 * For more details about how to use it, check out <a href="https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/">this article</a> on <a href="https://vladmihalcea.com/">vladmihalcea.com</a>.
 *
 * @author Vlad Mihalcea
 */
public class PostgreSQLEnumType extends org.hibernate.type.EnumType {

    public static final PostgreSQLEnumType INSTANCE = new PostgreSQLEnumType();

    private final Configuration configuration;

    /**
     * Initialization constructor taking the default {@link Configuration} object.
     */
    public PostgreSQLEnumType() {
        this.configuration = Configuration.INSTANCE;
    }

    public PostgreSQLEnumType(Class<? extends Enum<?>> klass) {
        this();
        Properties properties = new Properties();
        properties.setProperty(EnumType.ENUM, klass.getName());
        properties.setProperty(EnumType.NAMED, Boolean.TRUE.toString());
        setParameterValues(properties);
    }

    /**
     * Initialization constructor taking the {@link Class} and {@link Configuration} objects.
     *
     * @param configuration custom {@link Configuration} object.
     */
    public PostgreSQLEnumType(Configuration configuration) {
        this.configuration = configuration;
    }

    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.toString(), Types.OTHER);
        }
    }
}
