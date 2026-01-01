package com.wareops.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wareops.entity.PurchaseItem;
import com.wareops.entity.PurchaseReceipt;
import com.wareops.entity.StockLedger;
import com.wareops.util.HibernateUtil;

public class PurchaseDao {

    public void createPurchaseReceipt(
            PurchaseReceipt receipt,
            List<PurchaseItem> items) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // 1️⃣ Save Receipt Header
            session.persist(receipt);

            // 2️⃣ Save Items + Stock Ledger
            for (PurchaseItem item : items) {

                session.persist(item);

                StockLedger ledger = new StockLedger();
                ledger.setLedgerId(generateLedgerId());
                ledger.setWarehouseId(receipt.getWarehouseId());
                ledger.setProductId(item.getProductId());
                ledger.setMovementDate(LocalDate.now());
                ledger.setMovementType("IN");
                ledger.setQty(item.getQty());
                ledger.setRefType("RECEIPT");
                ledger.setRefId(receipt.getReceiptId());

                session.persist(ledger);
            }

            tx.commit();
            System.out.println("✅ Purchase Receipt created successfully");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Error while creating receipt");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Temporary ID generator (for mini project)
    private int generateLedgerId() {
        return (int) (System.currentTimeMillis() % 100000);
    }
}