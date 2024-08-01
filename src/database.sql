DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS StockIn;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS LOG;
DROP TABLE IF EXISTS Purchase;
DROP TABLE IF EXISTS Report;
DROP TABLE IF EXISTS Staff;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Supplier;

CREATE TABLE Staff
(
    StaffID        VARCHAR(10)   NOT NULL,
    Password       VARCHAR(100)  NOT NULL,
    StaffIC        VARCHAR(20)   NOT NULL,
    StaffName      VARCHAR(100)  NOT NULL,
    StaffAge       DECIMAL(3)    NOT NULL,
    StaffHireDate  DATE          NOT NULL,
    StaffBirthDate DATE          NOT NULL,
    StaffSalary    DECIMAL(7, 2) NOT NULL,
    StaffEmail     VARCHAR(100)  NOT NULL,
    StaffPhone     VARCHAR(20)   NOT NULL,
    StaffAddress   VARCHAR(100)  NOT NULL,
    AdminPrivilege DECIMAL(1)    NOT NULL,
    PRIMARY KEY (StaffID)
);

CREATE TABLE LOG
(
    time    TIMESTAMP   NOT NULL,
    staffID VARCHAR(10) NOT NULL,
    PRIMARY KEY (time),
    FOREIGN KEY (staffID) REFERENCES Staff (StaffID)
);

CREATE TABLE Product
(
    ProductSKU       VARCHAR(10)    NOT NULL,
    ProductName      VARCHAR(100)   NOT NULL,
    ProductDesc      VARCHAR(200)   NOT NULL,
    ProductCategory  VARCHAR(100)   NOT NULL,
    ProductPrice     DECIMAL(10, 2) NOT NULL,
    ProductWeight    DECIMAL(10, 2) NOT NULL,
    ProductDimension VARCHAR(100)   NOT NULL,
    ProductQuantity  DECIMAL(10)    NOT NULL,
    ProductExpDate   Date           NOT NULL,
    ProductUpdatedAt TIMESTAMP NULL,
    PRIMARY KEY (ProductSKU)
);

CREATE TABLE Supplier
(
    SupplierID      VARCHAR(10)  NOT NULL,
    SupplierName    VARCHAR(100) NOT NULL,
    SupplierAddress VARCHAR(100) NOT NULL,
    SupplierPhone   VARCHAR(20)  NOT NULL,
    SupplierEmail   VARCHAR(100) NOT NULL,
    PRIMARY KEY (SupplierID)
);

CREATE TABLE Payment
(
    InvoiceDECIMAL VARCHAR(10)    NOT NULL,
    Amount         DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (InvoiceDECIMAL)
);

CREATE TABLE Purchase
(
    PurchaseID   VARCHAR(10)    NOT NULL,
    PurchaseDate DATE           NOT NULL,
    ProductSKU   DECIMAL(10)    NOT NULL,
    ItemQuantity DECIMAL(10)    NOT NULL,
    ItemPrice    DECIMAL(10, 2) NOT NULL,
    SupplierID   VARCHAR(10)    NOT NULL,
    PRIMARY KEY (PurchaseID),
    FOREIGN KEY (ProductSKU) REFERENCES Product (ProductSKU),
    FOREIGN KEY (SupplierID) REFERENCES Supplier (SupplierID)
);

CREATE TABLE Report
(
    date                     TIMESTAMP      NOT NULL,
    totalSales               DECIMAL(10, 2) NOT NULL,
    topSalesProduct          VARCHAR(100)   NOT NULL,
    topSalesProductQuantity  DECIMAL(10)    NOT NULL,
    topSalesStaff            VARCHAR(100)   NOT NULL,
    topSalesStaffPerformance DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (date)
);

CREATE TABLE Discount
(
    DiscountCode VARCHAR(10)    NOT NULL,
    DiscountRate DECIMAL(3, 2)  NOT NULL,
    PRIMARY KEY (DiscountCode)
)

CREATE TABLE Orders
(
    OrderID      DECIMAL(10)    NOT NULL,
    ItemSKU      VARCHAR(10)    NOT NULL,
    ItemQuantity DECIMAL(10)    NOT NULL,
    DISCOUNT     DECIMAL(10, 2) NOT NULL,
    PRICE        DECIMAL(10, 2) NOT NULL,
    OrderDate    DATETIME       NOT NULL,
    StaffID      VARCHAR(10)    NOT NULL,
    PaymentID    VARCHAR(10)    NOT NULL,
    PRIMARY KEY (OrderID),
    FOREIGN KEY (ItemSKU) REFERENCES Product (ProductSKU),
    FOREIGN KEY (StaffID) REFERENCES Staff (StaffID),
    FOREIGN KEY (PaymentID) REFERENCES Payment (InvoiceDECIMAL)
);

CREATE TABLE StockIn
(
    StockInID  VARCHAR(10)    NOT NULL,
    ItemSKU    VARCHAR(10)    NOT NULL,
    Quantity   DECIMAL(10)    NOT NULL,
    unitPrice  DECIMAL(10, 2) NOT NULL,
    totalPrice DECIMAL(10, 2) NOT NULL,
    date       TIMESTAMP      NOT NULL,
    expiryDate DATE           NOT NULL,
    remarks    VARCHAR(200)   NOT NULL,
    receivedBy VARCHAR(100)   NOT NULL,
    PRIMARY KEY (StockInID),
    FOREIGN KEY (ItemSKU) REFERENCES Product (ProductSKU),
    FOREIGN KEY (receivedBy) REFERENCES Staff (StaffID)
);



