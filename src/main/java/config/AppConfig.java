package config;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

@Configuration
@EnableJpaRepositories("master.repository")
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/diti4_pring_mvc");
        ds.setUsername("postgres");
        ds.setPassword("Thiara3003."); // adapte selon ton installation

        System.out.println("DATASOURCE");

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean em =
                new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource());
        em.setPackagesToScan("master.entity");
        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);

        // options Hibernate (important pour PostgreSQL)
        java.util.Properties props = new java.util.Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.show_sql", "true");

        em.setJpaProperties(props);
        System.out.println("Entity manager ");
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager( EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
         txManager.setEntityManagerFactory(emf);
        return txManager;
    }
}