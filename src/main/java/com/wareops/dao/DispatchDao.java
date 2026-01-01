package com.wareops.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.wareops.entity.Dispatch;
import com.wareops.entity.OrderItem;
import com.wareops.entity.StockLedger;
import com.wareops.util.HibernateUtil;

public class DispatchDao {

    public void dispatchOrder(Dispatch dispatch) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // 1️⃣ Save Dispatch
            session.persist(dispatch);

            // 2️⃣ Update SalesOrder status
            Query<?> updateOrder =
                    session.createQuery(
                        "update SalesOrder o set o.status = 'DISPATCHED' where o.orderId = :oid");

            updateOrder.setParameter("oid", dispatch.getOrderId());
            updateOrder.executeUpdate();

            // 3️⃣ Get Order Items
            Query<OrderItem> itemQuery =
                    session.createQuery(
                        "from OrderItem where orderId = :oid", OrderItem.class);

            itemQuery.setParameter("oid", dispatch.getOrderId());
            List<OrderItem> items = itemQuery.list();

            // 4️⃣ Stock OUT entries
            for (OrderItem item : items) {

                StockLedger ledger = new StockLedger();
                ledger.setLedgerId((int) (System.currentTimeMillis() % 100000));
                ledger.setWarehouseId(dispatch.getWarehouseId());
                ledger.setProductId(item.getProductId());
                ledger.setMovementDate(LocalDate.now());
                ledger.setMovementType("OUT");
                ledger.setQty(item.getQty());
                ledger.setRefType("ORDER");
                ledger.setRefId(dispatch.getOrderId());

                session.persist(ledger);
            }

            tx.commit();
            System.out.println("✅ Order dispatched successfully");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Dispatch failed");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}