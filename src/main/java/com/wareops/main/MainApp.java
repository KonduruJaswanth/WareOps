package com.wareops.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.wareops.dao.PurchaseDao;
import com.wareops.dao.SalesOrderDao;
import com.wareops.dao.DispatchDao;
import com.wareops.dao.ReportDao;   

import com.wareops.entity.PurchaseItem;
import com.wareops.entity.PurchaseReceipt;
import com.wareops.entity.SalesOrder;
import com.wareops.entity.OrderItem;
import com.wareops.entity.Dispatch;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PurchaseDao purchaseDao = new PurchaseDao();
        SalesOrderDao salesOrderDao = new SalesOrderDao();
        DispatchDao dispatchDao = new DispatchDao();
        ReportDao reportDao = new ReportDao();

        while (true) {

            System.out.println("\n==== WareOps Lite ====");
            System.out.println("1. Create Purchase Receipt");
            System.out.println("2. Create Sales Order");
            System.out.println("3. Dispatch Order");
            System.out.println("4. Monthly Sales Revenue (RPT-01)");
            System.out.println("5. Top Selling Products (RPT-02)");
            System.out.println("6. Low Stock Alert (RPT-03)");
            System.out.println("7. Supplier Spend (RPT-04)");
            System.out.println("8. Inbound vs Outbound Stock (RPT-05)");
            System.out.println("9. Late Deliveries (RPT-06)");
            System.out.println("10. Return Rate by Product (RPT-07)");
            System.out.println("11. Customer Revenue Ranking (RPT-08)");
            System.out.println("12. Customer Revenue (Names) (RPT-09)");
            System.out.println("13. Paged Order Listing (RPT-10)");
            System.out.println("14. Bulk Close Old Returns (RPT-11)");
            System.out.println("15. Bulk Delete Cancelled Orders (RPT-12)");
            System.out.println("0. Exit");

            int choice = sc.nextInt();

            switch (choice) {

                // ================== UC-05 ==================
                case 1:
                    PurchaseReceipt receipt = new PurchaseReceipt();

                    System.out.print("Receipt ID: ");
                    receipt.setReceiptId(sc.nextInt());

                    System.out.print("Warehouse ID: ");
                    receipt.setWarehouseId(sc.nextInt());

                    System.out.print("Supplier ID: ");
                    receipt.setSupplierId(sc.nextInt());

                    receipt.setReceiptDate(LocalDate.now());

                    System.out.print("Invoice No: ");
                    receipt.setInvoiceNo(sc.next());

                    System.out.print("Total Amount: ");
                    receipt.setTotalAmount(sc.nextDouble());

                    System.out.print("No of Items: ");
                    int n = sc.nextInt();

                    List<PurchaseItem> items = new ArrayList<>();

                    for (int i = 1; i <= n; i++) {
                        System.out.println("\n--- Item " + i + " ---");

                        PurchaseItem item = new PurchaseItem();

                        System.out.print("PurchaseItem ID: ");
                        item.setPurchaseItemId(sc.nextInt());

                        item.setReceiptId(receipt.getReceiptId());

                        System.out.print("Product ID: ");
                        item.setProductId(sc.nextInt());

                        System.out.print("Quantity: ");
                        item.setQty(sc.nextInt());

                        System.out.print("Unit Cost: ");
                        item.setUnitCost(sc.nextDouble());

                        items.add(item);
                    }

                    purchaseDao.createPurchaseReceipt(receipt, items);
                    break;

                // ================== UC-06 ==================
                case 2:
                    SalesOrder order = new SalesOrder();

                    System.out.print("Order ID: ");
                    order.setOrderId(sc.nextInt());

                    System.out.print("Warehouse ID: ");
                    order.setWarehouseId(sc.nextInt());

                    System.out.print("Customer ID: ");
                    order.setCustomerId(sc.nextInt());

                    order.setOrderDate(LocalDate.now());

                    System.out.print("Promised Date (yyyy-mm-dd): ");
                    order.setPromisedDate(LocalDate.parse(sc.next()));

                    order.setStatus("NEW");

                    System.out.print("No of Items: ");
                    int count = sc.nextInt();

                    List<OrderItem> orderItems = new ArrayList<>();

                    for (int i = 1; i <= count; i++) {
                        System.out.println("\n--- Item " + i + " ---");

                        OrderItem oi = new OrderItem();

                        System.out.print("OrderItem ID: ");
                        oi.setOrderItemId(sc.nextInt());

                        oi.setOrderId(order.getOrderId());

                        System.out.print("Product ID: ");
                        oi.setProductId(sc.nextInt());

                        System.out.print("Quantity: ");
                        oi.setQty(sc.nextInt());

                        System.out.print("Selling Price: ");
                        oi.setSellingPrice(sc.nextDouble());

                        orderItems.add(oi);
                    }

                    salesOrderDao.createSalesOrder(order, orderItems);
                    break;

                // ================== UC-07 ==================
                case 3:
                    Dispatch dispatch = new Dispatch();

                    System.out.print("Dispatch ID: ");
                    dispatch.setDispatchId(sc.nextInt());

                    System.out.print("Warehouse ID: ");
                    dispatch.setWarehouseId(sc.nextInt());

                    System.out.print("Order ID: ");
                    dispatch.setOrderId(sc.nextInt());

                    dispatch.setDispatchDate(LocalDate.now());

                    System.out.print("Courier: ");
                    dispatch.setCourier(sc.next());

                    System.out.print("Tracking No: ");
                    dispatch.setTrackingNo(sc.next());

                    dispatch.setStatus("IN_TRANSIT");

                    dispatchDao.dispatchOrder(dispatch);
                    break;

                // ================== RPT-01 ==================
                case 4:
                    System.out.print("Enter Month (1-12): ");
                    int m1 = sc.nextInt();
                    System.out.print("Enter Year: ");
                    int y1 = sc.nextInt();
                    reportDao.monthlySalesByWarehouse(m1, y1);
                    break;

                // ================== RPT-02 ==================
                case 5:
                    System.out.print("Enter Month (1-12): ");
                    int m2 = sc.nextInt();
                    System.out.print("Enter Year: ");
                    int y2 = sc.nextInt();
                    reportDao.topSellingProductsByQty(m2, y2);
                    break;

                // ================== RPT-03 ==================
                case 6:
                    System.out.print("Enter Warehouse ID: ");
                    int wid = sc.nextInt();
                    reportDao.lowStockAlert(wid);
                    break;

                // ================== RPT-04 ==================
                case 7:
                    System.out.print("Enter Month (1-12): ");
                    int m4 = sc.nextInt();
                    System.out.print("Enter Year: ");
                    int y4 = sc.nextInt();
                    reportDao.supplierSpendMonthly(m4, y4);
                    break;

                // ================== RPT-05 ==================
                case 8:
                    System.out.print("Enter Warehouse ID: ");
                    int wid5 = sc.nextInt();
                    System.out.print("From Date (yyyy-mm-dd): ");
                    LocalDate from = LocalDate.parse(sc.next());
                    System.out.print("To Date (yyyy-mm-dd): ");
                    LocalDate to = LocalDate.parse(sc.next());
                    reportDao.inboundOutboundTrend(wid5, from, to);
                    break;

                // ================== RPT-06 ==================
               case 9:
                    System.out.print("Enter Warehouse ID: ");
                    int wid6 = sc.nextInt();
                    System.out.print("From Date (yyyy-mm-dd): ");
                    LocalDate from6 = LocalDate.parse(sc.next());
                    System.out.print("To Date (yyyy-mm-dd): ");
                    LocalDate to6 = LocalDate.parse(sc.next());
                    reportDao.lateDeliveries(wid6, from6, to6);
                    break;

                // ================== RPT-07 ==================
               case 10:
                    System.out.print("Enter Warehouse ID: ");
                    int wid7 = sc.nextInt();
                    System.out.print("From Date (yyyy-mm-dd): ");
                    LocalDate from7 = LocalDate.parse(sc.next());
                    System.out.print("To Date (yyyy-mm-dd): ");
                    LocalDate to7 = LocalDate.parse(sc.next());
                    reportDao.returnRateByProduct(wid7, from7, to7);
                    break;
                    
                 // ================== RPT-08 ==================
               case 11:
                    System.out.print("Enter Month (1-12): ");
                    int m8 = sc.nextInt();

                    System.out.print("Enter Year: ");
                    int y8 = sc.nextInt();

                    reportDao.customerRevenueRanking(m8, y8);
                    break;
                    
                 // ================== RPT-09 ==================
               case 12:
                    System.out.print("Enter Month (1-12): ");
                    int m9 = sc.nextInt();

                    System.out.print("Enter Year: ");
                    int y9 = sc.nextInt();

                    reportDao.readableCustomerRevenue(m9, y9);
                    break;
                    
                 // ================== RPT-10 ==================
               case 13:
                    System.out.print("Enter Warehouse ID: ");
                    int wid10 = sc.nextInt();

                    System.out.print("Enter Page Number: ");
                    int pageNo = sc.nextInt();

                    System.out.print("Enter Page Size: ");
                    int pageSize = sc.nextInt();

                    reportDao.pagedOrders(wid10, pageNo, pageSize);
                    break;
                    
                 // ================== RPT-11 ==================
               case 14:
                    System.out.print("Enter Cutoff Date (yyyy-mm-dd): ");
                    LocalDate cutoff = LocalDate.parse(sc.next());

                    reportDao.bulkCloseOldReturns(cutoff);
                    break;
                    
                 // ================== RPT-12 ==================
               case 15:
                   System.out.print("Enter Cutoff Date (yyyy-mm-dd): ");
                   LocalDate cutoff12 = LocalDate.parse(sc.next());

                   reportDao.bulkDeleteOldCancelledOrders(cutoff12);
                   break;

               case 0:
                    System.exit(0);

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}