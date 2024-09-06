DELETE FROM LOG;
DELETE FROM Inventory;
DELETE FROM Staff;
DELETE FROM Retailer;
DELETE FROM Transfer;
DELETE FROM Warehouse;
DELETE FROM Purchase;
DELETE FROM Product;
DELETE FROM Supplier;
DELETE FROM Payment;
DELETE FROM Report;
DELETE FROM Storage;

DROP TABLE IF EXISTS LOG;
DROP TABLE IF EXISTS Inventory;
DROP TABLE IF EXISTS Staff;
DROP TABLE IF EXISTS Storage;
DROP TABLE IF EXISTS Retailer;
DROP TABLE IF EXISTS Transfer;
DROP TABLE IF EXISTS Warehouse;
DROP TABLE IF EXISTS Purchase;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Supplier;
DROP TABLE IF EXISTS Report;


CREATE TABLE Staff
(
    StaffID        VARCHAR(10)   NOT NULL,
    StaffUsername  VARCHAR(20)   NOT NULL,
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
    AdminPrivilege DECIMAL(1)    ,
    PRIMARY KEY (StaffID)
);

CREATE TABLE Retailer
(
    RetailerID      VARCHAR(10)  NOT NULL,
    RetailerName    VARCHAR(100) NOT NULL,
    RetailerAddress VARCHAR(100) NOT NULL,
    RetailerPhone   VARCHAR(20)  NOT NULL,
    RetailerEmail   VARCHAR(100) NOT NULL,
    PRIMARY KEY (RetailerID)
);

CREATE TABLE Warehouse
(
    WarehouseID   VARCHAR(10)  NOT NULL,
    WarehouseName VARCHAR(100) NOT NULL,
    WarehouseAddress VARCHAR(100) NOT NULL,
    WarehousePhone VARCHAR(20)  NOT NULL,
    WarehouseEmail VARCHAR(100) NOT NULL,
    PRIMARY KEY (WarehouseID)
);

CREATE TABLE Product
(
    ProductUPC       INT            NOT NULL AUTO_INCREMENT,
    ProductName      VARCHAR(100)   NOT NULL,
    ProductDesc      VARCHAR(200)   NOT NULL,
    ProductCategory  VARCHAR(100)   NOT NULL,
    ProductPrice     DECIMAL(10, 2) NOT NULL,
    ProductWeight    DECIMAL(10, 2) NOT NULL,
    ProductWidth     DECIMAL(10, 2) NOT NULL,
    ProductLength    DECIMAL(10, 2) NOT NULL,
    ProductHeight    DECIMAL(10, 2) NOT NULL,
    ProductQuantity  DECIMAL(10)    NOT NULL,
    ProductUpdatedAt TIMESTAMP      NOT NULL,
    PRIMARY KEY (ProductUPC)
);

CREATE TABLE Transfer
(
    TransferID     VARCHAR(10)  NOT NULL,
    FromWarehouse  VARCHAR(10)  NOT NULL,
    ToWarehouse    VARCHAR(10)  NOT NULL,
    ProductUPC      INT  NOT NULL,
    Quantity       DECIMAL(10)  NOT NULL,
    TransferDate   DATE         NOT NULL,
    TransferStatus VARCHAR(100) NOT NULL,
    PRIMARY KEY (TransferID),
    FOREIGN KEY (FromWarehouse) REFERENCES Warehouse (WarehouseID),
    FOREIGN KEY (ToWarehouse) REFERENCES Warehouse (WarehouseID),
    FOREIGN KEY (ProductUPC) REFERENCES Product (ProductUPC)
);

CREATE TABLE LOG
(
    Time    TIMESTAMP   NOT NULL,
    StaffID VARCHAR(10) NOT NULL,
    PRIMARY KEY (time),
    FOREIGN KEY (StaffID) REFERENCES Staff (StaffID)
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

CREATE TABLE Report
(
    Date                     TIMESTAMP      NOT NULL,
    TotalSales               DECIMAL(10, 2) NOT NULL,
    TopSalesProduct          VARCHAR(100)   NOT NULL,
    TopSalesProductQuantity  DECIMAL(10)    NOT NULL,
    TopSalesStaff            VARCHAR(100)   NOT NULL,
    TopSalesStaffPerformance DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (Date)
);

CREATE TABLE Inventory
(
    InventoryID   VARCHAR(10)  NOT NULL,
    ProductUPC    INT  NOT NULL,
    Quantity      DECIMAL(10)  NOT NULL,
    Price         DECIMAL(10,2)  NOT NULL,
    SupplierID    VARCHAR(10),
    RetailerID    VARCHAR(10),
    InventoryType VARCHAR(100) NOT NULL,
    InventoryTime TIMESTAMP    NOT NULL,
    expiryDate    DATE,
    remarks       VARCHAR(200),
    receivedBy    VARCHAR(100) NOT NULL,
    PRIMARY KEY (InventoryID),
    FOREIGN KEY (ProductUPC) REFERENCES Product (ProductUPC),
    FOREIGN KEY (receivedBy) REFERENCES Staff (StaffID),
    FOREIGN KEY (SupplierID) REFERENCES Supplier (SupplierID),
    FOREIGN KEY (RetailerID) REFERENCES Retailer (RetailerID)
);

CREATE TABLE Storage
(
    StorageID   VARCHAR(10) NOT NULL,
    RetailerID  VARCHAR(10),
    ProductUPC  INT NOT NULL,
    WarehouseID VARCHAR(10),
    Quantity    DECIMAL(10) NOT NULL,
    PRIMARY KEY (StorageID),
    FOREIGN KEY (ProductUPC) REFERENCES Product (ProductUPC),
    FOREIGN KEY (RetailerID) REFERENCES Retailer (RetailerID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouse (WarehouseID)
);