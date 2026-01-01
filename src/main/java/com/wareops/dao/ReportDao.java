package com.wareops.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.wareops.entity.SalesOrder;
import com.wareops.util.HibernateUtil;

public class ReportDao {

    // ================= RPT-01 =================
    // Monthly Sales Revenue by Warehouse
    public void monthlySalesByWarehouse(int month, int year) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        String hql =
            "select o.warehouseId, sum(i.qty * i.sellingPrice) " +
            "from SalesOrder o, OrderItem i " +
            "where o.orderId = i.orderId " +
            "and month(o.orderDate) = :m " +
            "and year(o.orderDate) = :y " +
            "and o.status <> 'CANCELLED' " +
            "group by o.warehouseId " +
            "order by sum(i.qty * i.sellingPrice) desc";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("m", month);
        query.setParameter("y", year);

        List<Object[]> results = query.list();

        System.out.println("\nWarehouseID | Revenue");
        System.out.println("----------------------");

        for (Object[] row : results) {
            System.out.println(row[0] + " | " + row[1]);
        }

        session.close();
    }

    // ================= RPT-02 =================
    // Top Selling Products by Quantity
    public void topSellingProductsByQty(int month, int year) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        String hql =
            "select i.productId, sum(i.qty) " +
            "from SalesOrder o, OrderItem i " +
            "where o.orderId = i.orderId " +
            "and month(o.orderDate) = :m " +
            "and year(o.orderDate) = :y " +
            "and o.status <> 'CANCELLED' " +
            "group by i.productId " +
            "order by sum(i.qty) desc";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("m", month);
        query.setParameter("y", year);

        List<Object[]> results = query.list();

        System.out.println("\nProductID | Total Quantity Sold");
        System.out.println("-------------------------------");

        for (Object[] row : results) {
            System.out.println(row[0] + " | " + row[1]);
        }

        session.close();
    }

    // ================= RPT-03 =================
    // Low Stock Alert (Below Reorder Level)
    public void lowStockAlert(int warehouseId) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        String hql =
            "select p.productId, " +
            "sum(case when l.movementType = 'IN' then l.qty else -l.qty end), " +
            "p.reorderLevel " +
            "from Product p, StockLedger l " +
            "where p.productId = l.productId " +
            "and l.warehouseId = :wid " +
            "group by p.productId, p.reorderLevel " +
            "having sum(case when l.movementType = 'IN' then l.qty else -l.qty end) < p.reorderLevel " +
            "order by p.productId";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("wid", warehouseId);

        List<Object[]> results = query.list();

        System.out.println("\nProductID | CurrentStock | ReorderLevel");
        System.out.println("---------------------------------------");

        for (Object[] row : results) {
            System.out.println(row[0] + " | " + row[1] + " | " + row[2]);
        }

        session.close();
    }
 // ================= RPT-04 =================
 // Supplier Spend (Monthly Procurement)
 public void supplierSpendMonthly(int month, int year) {

     Session session = HibernateUtil.getSessionFactory().openSession();

     String hql =
         "select r.supplierId, sum(pi.qty * pi.unitCost) " +
         "from PurchaseReceipt r, PurchaseItem pi " +
         "where r.receiptId = pi.receiptId " +
         "and month(r.receiptDate) = :m " +
         "and year(r.receiptDate) = :y " +
         "group by r.supplierId " +
         "order by sum(pi.qty * pi.unitCost) desc";

     Query<Object[]> query = session.createQuery(hql, Object[].class);
     query.setParameter("m", month);
     query.setParameter("y", year);

     List<Object[]> results = query.list();

     System.out.println("\nSupplierID | Total Spend");
     System.out.println("------------------------");

     for (Object[] row : results) {
         System.out.println(row[0] + " | " + row[1]);
     }

     session.close();
 }
//================= RPT-05 =================
//Inbound vs Outbound Stock Movement (Daily Trend)
public void inboundOutboundTrend(int warehouseId, LocalDate from, LocalDate to) {

  Session session = HibernateUtil.getSessionFactory().openSession();

  String hql =
      "select l.movementDate, " +
      "sum(case when l.movementType='IN' then l.qty else 0 end), " +
      "sum(case when l.movementType='OUT' then l.qty else 0 end) " +
      "from StockLedger l " +
      "where l.warehouseId = :wid " +
      "and l.movementDate between :from and :to " +
      "group by l.movementDate " +
      "order by l.movementDate asc";

  Query<Object[]> query = session.createQuery(hql, Object[].class);
  query.setParameter("wid", warehouseId);
  query.setParameter("from", from);
  query.setParameter("to", to);

  List<Object[]> results = query.list();

  System.out.println("\nDate | IN Qty | OUT Qty");
  System.out.println("-----------------------");

  for (Object[] row : results) {
      System.out.println(row[0] + " | " + row[1] + " | " + row[2]);
  }

  session.close();
}
//================= RPT-06 =================
//Late Deliveries (SLA Breach)
public void lateDeliveries(int warehouseId, LocalDate from, LocalDate to) {

 Session session = HibernateUtil.getSessionFactory().openSession();

 String hql =
     "select d.dispatchId, d.orderId, o.promisedDate, d.deliveredDate, d.courier " +
     "from Dispatch d, SalesOrder o " +
     "where d.orderId = o.orderId " +
     "and d.warehouseId = :wid " +
     "and d.deliveredDate is not null " +
     "and d.deliveredDate > o.promisedDate " +
     "and d.dispatchDate between :from and :to " +
     "order by d.deliveredDate desc";

 Query<Object[]> query = session.createQuery(hql, Object[].class);
 query.setParameter("wid", warehouseId);
 query.setParameter("from", from);
 query.setParameter("to", to);

 List<Object[]> results = query.list();

 System.out.println("\nDispatchID | OrderID | Promised | Delivered | Courier");
 System.out.println("------------------------------------------------------");

 for (Object[] row : results) {
     System.out.println(
         row[0] + " | " +
         row[1] + " | " +
         row[2] + " | " +
         row[3] + " | " +
         row[4]
     );
 }

 session.close();
}
//================= RPT-07 =================
//Return Rate by Product (Period)
public void returnRateByProduct(int warehouseId, LocalDate from, LocalDate to) {

 Session session = HibernateUtil.getSessionFactory().openSession();

 String hql =
     "select r.productId, sum(r.qty) " +
     "from ReturnLog r " +
     "where r.warehouseId = :wid " +
     "and r.returnDate between :from and :to " +
     "group by r.productId " +
     "order by sum(r.qty) desc";

 Query<Object[]> query = session.createQuery(hql, Object[].class);
 query.setParameter("wid", warehouseId);
 query.setParameter("from", from);
 query.setParameter("to", to);

 List<Object[]> results = query.list();

 System.out.println("\nProductID | Returned Qty");
 System.out.println("------------------------");

 for (Object[] row : results) {
     System.out.println(row[0] + " | " + row[1]);
 }

 session.close();
}
//================= RPT-08 =================
//Customer Revenue Ranking (Month)
public void customerRevenueRanking(int month, int year) {

 Session session = HibernateUtil.getSessionFactory().openSession();

 String hql =
     "select o.customerId, sum(i.qty * i.sellingPrice) " +
     "from SalesOrder o, OrderItem i " +
     "where o.orderId = i.orderId " +
     "and month(o.orderDate) = :m " +
     "and year(o.orderDate) = :y " +
     "and o.status <> 'CANCELLED' " +
     "group by o.customerId " +
     "order by sum(i.qty * i.sellingPrice) desc";

 Query<Object[]> query = session.createQuery(hql, Object[].class);
 query.setParameter("m", month);
 query.setParameter("y", year);

 List<Object[]> results = query.list();

 System.out.println("\nCustomerID | Revenue");
 System.out.println("--------------------");

 for (Object[] row : results) {
     System.out.println(row[0] + " | " + row[1]);
 }

 session.close();
}
//================= RPT-09 =================
//Readable Revenue With Customer Names
public void readableCustomerRevenue(int month, int year) {

 Session session = HibernateUtil.getSessionFactory().openSession();

 String hql =
     "select c.name, sum(i.qty * i.sellingPrice) " +
     "from Customer c, SalesOrder o, OrderItem i " +
     "where c.customerId = o.customerId " +
     "and o.orderId = i.orderId " +
     "and month(o.orderDate) = :m " +
     "and year(o.orderDate) = :y " +
     "and o.status <> 'CANCELLED' " +
     "group by c.name " +
     "order by sum(i.qty * i.sellingPrice) desc";

 Query<Object[]> query = session.createQuery(hql, Object[].class);
 query.setParameter("m", month);
 query.setParameter("y", year);

 List<Object[]> results = query.list();

 System.out.println("\nCustomer Name | Revenue");
 System.out.println("----------------------");

 for (Object[] row : results) {
     System.out.println(row[0] + " | " + row[1]);
 }

 session.close();
}
//================= RPT-10 =================
//Paged Order Listing (Operations View)
public void pagedOrders(int warehouseId, int pageNo, int pageSize) {

 Session session = HibernateUtil.getSessionFactory().openSession();

 String hql =
     "from SalesOrder o " +
     "where o.warehouseId = :wid " +
     "order by o.orderDate desc";

 Query<SalesOrder> query = session.createQuery(hql, SalesOrder.class);
 query.setParameter("wid", warehouseId);

 query.setFirstResult((pageNo - 1) * pageSize);
 query.setMaxResults(pageSize);

 List<SalesOrder> orders = query.list();

 System.out.println("\nOrderID | CustomerID | OrderDate | Status");
 System.out.println("-----------------------------------------");

 for (SalesOrder o : orders) {
     System.out.println(
         o.getOrderId() + " | " +
         o.getCustomerId() + " | " +
         o.getOrderDate() + " | " +
         o.getStatus()
     );
 }

 session.close();
}
//================= RPT-11 =================
//Bulk Close Old Returns
public void bulkCloseOldReturns(LocalDate cutoffDate) {

 Session session = HibernateUtil.getSessionFactory().openSession();
 session.beginTransaction();

 String hql =
     "update ReturnLog r " +
     "set r.status = 'CLOSED' " +
     "where r.status = 'OPEN' " +
     "and r.returnDate < :cutoff";

 Query query = session.createQuery(hql);
 query.setParameter("cutoff", cutoffDate);

 int updated = query.executeUpdate();

 session.getTransaction().commit();
 session.close();

 System.out.println("\n✅ Returns closed: " + updated);
}
//================= RPT-12 =================
//Bulk Delete Old Cancelled Orders
public void bulkDeleteOldCancelledOrders(LocalDate cutoffDate) {

 Session session = HibernateUtil.getSessionFactory().openSession();
 session.beginTransaction();

 String hql =
     "delete from SalesOrder o " +
     "where o.status = 'CANCELLED' " +
     "and o.orderDate < :cutoff";

 Query query = session.createQuery(hql);
 query.setParameter("cutoff", cutoffDate);

 int deleted = query.executeUpdate();

 session.getTransaction().commit();
 session.close();

 System.out.println("\n🗑️ Cancelled orders deleted: " + deleted);
}
}