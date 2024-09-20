SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE StockRequest;
TRUNCATE TABLE LOG;
TRUNCATE TABLE Inventory;
TRUNCATE TABLE Staff;
TRUNCATE TABLE Storage;
TRUNCATE TABLE Retailer;
TRUNCATE TABLE Transfer;
TRUNCATE TABLE Warehouse;
TRUNCATE TABLE Purchase;
TRUNCATE TABLE Product;
TRUNCATE TABLE Supplier;

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS StockRequest;
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
    AdminPrivilege DECIMAL(1),
    WarehouseID    VARCHAR(10) NOT NULL,
    PRIMARY KEY (StaffID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouse (WarehouseID)
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
    PRIMARY KEY (TransferID),
    FOREIGN KEY (FromWarehouse) REFERENCES Warehouse (WarehouseID),
    FOREIGN KEY (ToWarehouse) REFERENCES Warehouse (WarehouseID),
    FOREIGN KEY (ProductUPC) REFERENCES Product (ProductUPC)
);

CREATE TABLE LOG
(
    Time    TIMESTAMP   NOT NULL,
    StaffID VARCHAR(10) NOT NULL,
    PRIMARY KEY (Time),
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

CREATE TABLE Purchase
(
    PurchaseID   VARCHAR(10)    NOT NULL,
    PurchaseDate DATE           NOT NULL,
    ProductUPC   INT    NOT NULL,
    ItemQuantity DECIMAL(10)    NOT NULL,
    ItemPrice    DECIMAL(10, 2) NOT NULL,
    SupplierID   VARCHAR(10)    NOT NULL,
    PRIMARY KEY (PurchaseID),
    FOREIGN KEY (ProductUPC) REFERENCES Product (ProductUPC),
    FOREIGN KEY (SupplierID) REFERENCES Supplier (SupplierID)
);

CREATE TABLE Inventory
(
    InventoryID   VARCHAR(10)  NOT NULL,
    ProductUPC    INT  NOT NULL,
    Quantity      DECIMAL(10)  NOT NULL,
    Price         DECIMAL(10,2)  NOT NULL,
    SupplierID    VARCHAR(10),
    RetailerID    VARCHAR(10),
    WarehouseID   VARCHAR(10),
    InventoryType VARCHAR(100) NOT NULL,
    InventoryTime TIMESTAMP    NOT NULL,
    remarks       VARCHAR(200),
    receivedBy    VARCHAR(100),
    PRIMARY KEY (InventoryID),
    FOREIGN KEY (ProductUPC) REFERENCES Product (ProductUPC),
    FOREIGN KEY (receivedBy) REFERENCES Staff (StaffID),
    FOREIGN KEY (SupplierID) REFERENCES Supplier (SupplierID),
    FOREIGN KEY (RetailerID) REFERENCES Retailer (RetailerID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouse (WarehouseID)
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

CREATE TABLE StockRequest
(
    RequestID   VARCHAR(10)  NOT NULL,
    ProductUPC      INT  NOT NULL,
    Quantity INT NOT NULL,
    requestedBy VARCHAR(10) NOT NULL,
    warehouseID VARCHAR(10) NOT NULL,
    requestDate DATE NOT NULL,
    PRIMARY KEY (RequestID),
    FOREIGN KEY (ProductUPC) REFERENCES Product (ProductUPC),
    FOREIGN KEY (warehouseID) REFERENCES Warehouse (WarehouseID),
    FOREIGN KEY (requestedBy) REFERENCES Staff (StaffID)
);

INSERT INTO Warehouse (WarehouseID, WarehouseName, WarehouseAddress, WarehousePhone, WarehouseEmail) VALUES
                                                                                                         ('W001', 'Central Warehouse', '123 Central Ave, Citytown', '012-3456789', 'central@warehouse.com'),
                                                                                                         ('W002', 'East Coast Hub', '456 East Blvd, Coastal City', '013-4567890', 'eastcoast@warehouse.com'),
                                                                                                         ('W003', 'West Storage', '789 West Road, Mountainview', '014-5678901', 'west@warehouse.com'),
                                                                                                         ('W004', 'North Depot', '101 North St, Riverside', '015-6789012', 'north@warehouse.com'),
                                                                                                         ('W005', 'South Facility', '202 South Dr, Plainsville', '016-7890123', 'south@warehouse.com');

INSERT INTO Product (ProductName, ProductDesc, ProductCategory, ProductPrice, ProductWeight, ProductWidth, ProductLength, ProductHeight, ProductUpdatedAt) VALUES
                                                                                                                                                               ('Whole Milk', '1-liter whole milk', 'Dairy', 2.50, 1.00, 10.0, 10.0, 25.0, NOW()),
                                                                                                                                                               ('Skim Milk', '1-liter skim milk', 'Dairy', 2.30, 1.00, 10.0, 10.0, 25.0, NOW()),
                                                                                                                                                               ('White Bread', '500g white bread loaf', 'Bakery', 1.99, 0.50, 15.0, 25.0, 10.0, NOW()),
                                                                                                                                                               ('Whole Wheat Bread', '500g whole wheat bread loaf', 'Bakery', 2.20, 0.50, 15.0, 25.0, 10.0, NOW()),
                                                                                                                                                               ('Butter', '250g salted butter', 'Dairy', 3.50, 0.25, 5.0, 10.0, 3.0, NOW()),
                                                                                                                                                               ('Cheddar Cheese', '200g cheddar cheese block', 'Dairy', 4.99, 0.20, 5.0, 10.0, 3.0, NOW()),
                                                                                                                                                               ('Eggs', '12-pack large eggs', 'Poultry', 3.20, 0.60, 12.0, 18.0, 6.0, NOW()),
                                                                                                                                                               ('Yogurt', '500g plain yogurt', 'Dairy', 3.00, 0.50, 10.0, 10.0, 12.0, NOW()),
                                                                                                                                                               ('Bagels', '6-pack bagels', 'Bakery', 4.50, 0.60, 20.0, 20.0, 8.0, NOW()),
                                                                                                                                                               ('Croissants', '4-pack croissants', 'Bakery', 5.00, 0.40, 15.0, 15.0, 6.0, NOW());

INSERT INTO Staff (StaffID, StaffUsername, Password, StaffIC, StaffName, StaffAge, StaffHireDate, StaffBirthDate, StaffSalary, StaffEmail, StaffPhone, StaffAddress, AdminPrivilege, WarehouseID) VALUES
                                                                                                                                                                                                      ('S001', 'john_doe', 'password123', '901234-56-7890', 'John Doe', 30, '2015-01-15', '1990-12-12', 3500.00, 'john@warehouse.com', '012-3456789', '123 Main St', 1, 'W001'),
                                                                                                                                                                                                      ('S002', 'jane_smith', 'password456', '851234-56-1234', 'Jane Smith', 35, '2018-06-10', '1985-07-23', 4200.00, 'jane@warehouse.com', '013-4567890', '456 Elm St', 0, 'W002'),
                                                                                                                                                                                                      ('S003', 'peter_parker', 'password789', '950102-08-7654', 'Peter Parker', 28, '2020-03-25', '1995-01-02', 3900.00, 'peter@warehouse.com', '014-5678901', '789 Maple St', 0, 'W003'),
                                                                                                                                                                                                      ('S004', 'mary_jones', 'password321', '890512-14-4567', 'Mary Jones', 40, '2012-11-01', '1980-05-12', 4500.00, 'mary@warehouse.com', '015-6789012', '101 Oak St', 1, 'W001'),
                                                                                                                                                                                                      ('S005', 'alice_cooper', 'password654', '911212-01-5678', 'Alice Cooper', 32, '2019-08-22', '1991-12-12', 4000.00, 'alice@warehouse.com', '016-7890123', '202 Pine St', 0, 'W002');

INSERT INTO Supplier (SupplierID, SupplierName, SupplierAddress, SupplierPhone, SupplierEmail) VALUES
                                                                                                   ('SUP001', 'Dairy Suppliers Ltd', '123 Dairy St, Cityville', '012-3456789', 'contact@dairysuppliers.com'),
                                                                                                   ('SUP002', 'Bakery Goods Co.', '456 Bread Ave, Townland', '013-4567890', 'support@bakerygoods.com'),
                                                                                                   ('SUP003', 'Fresh Produce Distributors', '789 Veggie Blvd, Farmland', '014-5678901', 'info@freshproduce.com'),
                                                                                                   ('SUP004', 'Beverage Wholesalers', '101 Drink St, Watercity', '015-6789012', 'sales@beveragewholesalers.com'),
                                                                                                   ('SUP005', 'Meat & Poultry Inc.', '202 Meat Dr, Ranchville', '016-7890123', 'orders@meatpoultry.com');