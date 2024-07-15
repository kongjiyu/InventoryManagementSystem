CREATE TABLE Staff(
    StaffID VARCHAR(10) NOT NULL,
    StaffName VARCHAR(100) NOT NULL,
    StaffPosition VARCHAR(100) NOT NULL,
    PRIMARY KEY(StaffID)
);

CREATE TABLE Item(
    ItemSKU VARCHAR(10) NOT NULL,
    ItemName VARCHAR(100) NOT NULL,
    ItemPrice NUMBER(10,2) NOT NULL,
    Quantity NUMBER(10) NOT NULL,
    PRIMARY KEY(ItemSKU)
);

CREATE TABLE Supplier(
    SupplierID VARCHAR(10) NOT NULL,
    SupplierName VARCHAR(100) NOT NULL,
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
    Quantity NUMBER(10) NOT NULL,
    SupplierID VARCHAR(10) NOT NULL,
    PRIMARY KEY(PurchaseID),
    FOREIGN KEY(ItemSKU) REFERENCES Item(ItemSKU),
    FOREIGN KEY(SupplierID) REFERENCES Supplier(SupplierID)
);

CREATE TABLE Order(
    OrderID NUMBER(10) NOT NULL,
    ItemSKU VARCHAR(10) NOT NULL,
    Quantity NUMBER(10) NOT NULL,
    OrderDate DATETIME NOT NULL,
    StaffID VARCHAR(10) NOT NULL,
    PaymentID VARCHAR(10) NOT NULL,
    PRIMARY KEY(OrderID),
    FOREIGN KEY(ItemSKU) REFERENCES Item(ItemSKU),
    FOREIGN KEY(StaffID) REFERENCES Staff(StaffID),
    FOREIGN KEY(PaymentID) REFERENCES Payment(InvoiceNumber)
);



