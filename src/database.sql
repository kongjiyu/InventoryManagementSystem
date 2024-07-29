CREATE TABLE Staff(
    StaffID VARCHAR(10) NOT NULL,
    Password VARCHAR(100) NOT NULL,
    StaffIC VARCHAR(20) NOT NULL,
    StaffName VARCHAR(100) NOT NULL,
    StaffAge NUMBER(3) NOT NULL,
    StaffHireDate DATE NOT NULL,
    StaffBirthDate DATE NOT NULL,
    StaffSalary NUMBER(7,2) NOT NULL,
    StaffEmail VARCHAR(100) NOT NULL,
    StaffPhone VARCHAR(20) NOT NULL,
    StaffAddress VARCHAR(100) NOT NULL,
    AdminPrevilege
    PRIMARY KEY(StaffID)
);

CREATE TABLE Product(
    ProductSKU VARCHAR(10) NOT NULL,
    ProductName VARCHAR(100) NOT NULL,
    ProductDesc VARCHAR(200) NOT NULL,
    ProductPrice NUMBER(10,2) NOT NULL,
    ProductWeight NUMBER(10,2) NOT NULL,
    ProductDimension VARCHAR(100) NOT NULL,
    Quantity NUMBER(10) NOT NULL,
    ProductExpDate Date NOT NULL,
    ProductUpdatedAt TIMESTAMPNOT NULL,
    PRIMARY KEY(ItemSKU)
);

CREATE TABLE Supplier(
    SupplierID VARCHAR(10) NOT NULL,
    SupplierName VARCHAR(100) NOT NULL,
    SupplierAddress VARCHAR(100) NOT NULL,
    SupplierPhone VARCHAR(20) NOT NULL,
    SupplierEmail VARCHAR(100) NOT NULL,
    PRIMARY KEY(SupplierID)
);

CREATE TABLE Payment(
    InvoiceNumber VARCHAR(10) NOT NULL,
    Amount NUMBER(10,2) NOT NULL,
    PRIMARY KEY(InvoiceNumber)
);

CREATE TABLE Purchase(
    PurchaseID VARCHAR(10) NOT NULL,
    PurchaseDate DATE NOT NULL,
    ItemSKU NUMBER(10) NOT NULL,
    ItemQuantity NUMBER(10) NOT NULL,
    ItemPrice NUMBER(10,2) NOT NULL,
    SupplierID VARCHAR(10) NOT NULL,
    PRIMARY KEY(PurchaseID),
    FOREIGN KEY(ItemSKU) REFERENCES Item(ItemSKU),
    FOREIGN KEY(SupplierID) REFERENCES Supplier(SupplierID)
);

CREATE TABLE Order(
    OrderID NUMBER(10) NOT NULL,
    ItemSKU VARCHAR(10) NOT NULL,
    ItemQuantity NUMBER(10) NOT NULL,
    DISCOUNT NUMBER(10,2) NOT NULL,
    PRICE NUMBER(10,2) NOT NULL,
    OrderDate DATETIME NOT NULL,
    StaffID VARCHAR(10) NOT NULL,
    PaymentID VARCHAR(10) NOT NULL,
    PRIMARY KEY(OrderID),
    FOREIGN KEY(ItemSKU) REFERENCES Item(ItemSKU),
    FOREIGN KEY(StaffID) REFERENCES Staff(StaffID),
    FOREIGN KEY(PaymentID) REFERENCES Payment(InvoiceNumber)
);



