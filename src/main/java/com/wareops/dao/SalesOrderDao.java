package com.wareops.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wareops.entity.OrderItem;
import com.wareops.entity.SalesOrder;
import com.wareops.util.HibernateUtil;

public class SalesOrderDao {

    public void createSalesOrder(SalesOrder order, List<OrderItem> items) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // 1️⃣ Save SalesOrder
            session.persist(order);

            // 2️⃣ Save OrderItems
            for (OrderItem item : items) {
                session.persist(item);
            }

            tx.commit();
            System.out.println("✅ Sales Order created successfully");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Error while creating sales order");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}