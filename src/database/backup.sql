PRAGMA foreign_keys=ON;
BEGIN TRANSACTION;
DROP TABLE IF EXISTS "Container";
CREATE TABLE Container (
   ContainerID INT PRIMARY KEY,
   Capacity DECIMAL(10, 2),
   ContainerType VARCHAR(50),
   Location VARCHAR(255)
);

DROP TABLE IF EXISTS "Transport";
CREATE TABLE Transport (
   TransportID INT PRIMARY KEY,
   TransportType VARCHAR(50),
   Capacity DECIMAL(10, 2),
   AvailabilityStatus VARCHAR(50)
);

DROP TABLE IF EXISTS "CargoContainerLink";
CREATE TABLE CargoContainerLink (
   CargoID INT,
   ContainerID INT,
   PRIMARY KEY (CargoID, ContainerID),
   FOREIGN KEY (CargoID) REFERENCES Cargo(CargoID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (ContainerID) REFERENCES Container(ContainerID) ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS "Employee";
CREATE TABLE Employee (
   EmployeeID INT PRIMARY KEY,
   Name VARCHAR(255),
   Role VARCHAR(50),
   ContactDetails VARCHAR(255),
    Privilege TEXT
);
DROP TABLE IF EXISTS "Tracking";
CREATE TABLE Tracking (
   TrackingID INT PRIMARY KEY,
   CargoID INT,
   Date DATE,
   Location VARCHAR(255),
   StatusUpdate VARCHAR(255),
   FOREIGN KEY (CargoID) REFERENCES Cargo(CargoID) ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS "EmployeeAccount";
CREATE TABLE EmployeeAccount (
   EmployeeID INT PRIMARY KEY,
   Username VARCHAR(255) UNIQUE NOT NULL,
   Password VARCHAR(255) NOT NULL,
    Privilege TEXT NOT NULL,
   FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Privilege) REFERENCES Employee(Privilege) ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS "City";
CREATE TABLE City
(
    City_ID integer not null
        constraint City_pk
            primary key
, City_Name TEXT not null, isRepository integer);
DROP TABLE IF EXISTS "Edges";
CREATE TABLE IF NOT EXISTS "Edges"
(
    Edge_ID  integer not null
        constraint Edges_pk
            primary key,
    "From"   integer
        constraint Edges_City_City_ID_fk
            references City
            on update cascade on delete cascade,
    "To"     integer not null
        constraint Edges___fk
            references City
            on update cascade on delete cascade,
    Distance double  not null
);
DROP TABLE IF EXISTS "Customer";
CREATE TABLE IF NOT EXISTS "Customer"
(
    CustomerID     INT
        primary key,
    Name           VARCHAR(255),
    ContactDetails VARCHAR(255),
    Address        VARCHAR(255)
        constraint Customer_City_City_Name_fk
            references City (City_Name)
);
DROP TABLE IF EXISTS "Cargo";
CREATE TABLE IF NOT EXISTS "Cargo"
(
    CargoID     INT
        primary key,
    Description VARCHAR(255),
    Weight      DECIMAL(10, 2),
    Volume      DECIMAL(10, 2),
    Type        VARCHAR(50),
    Status      Varchar,
    Trade_Name  Varchar
, Original_Price double);
DROP TABLE IF EXISTS "AdminAccount";
CREATE TABLE AdminAccount
(
    AdminUsername TEXT
        constraint AdminAccount_pk
            primary key,
    AdminPassword TEXT
);
DROP TABLE IF EXISTS "Invoice";
CREATE TABLE IF NOT EXISTS "Invoice"
(
    InvoiceID     INTEGER
        primary key autoincrement,
    CustomerID    INT
        references Customer
            on update cascade on delete cascade,
    ShipmentID    INT
        references Shipment
            on update cascade on delete cascade,
    Amount        DECIMAL(10, 2),
    DateIssued    DATE,
    PaymentStatus VARCHAR(50)
);
DROP TABLE IF EXISTS "Shipment";
CREATE TABLE IF NOT EXISTS "Shipment"
(
    ShipmentID    INTEGER
        primary key autoincrement,
    DepartureDate DATE,
    ArrivalDate   DATE,
    Origin        VARCHAR(255),
    Destination   VARCHAR(255),
    TransportID   INT
        references Transport
            on update cascade on delete cascade,
    Location      VARCHAR(255),
    CargoID       integer
        constraint cargo_link
            references Cargo,
    ItemID        integer
);
DROP TABLE IF EXISTS "Laptop";
CREATE TABLE IF NOT EXISTS "Laptop"
(
    Laptop_ID        integer
        constraint Laptop_pk
            primary key autoincrement,
    Price            double,
    Returned         boolean,
    TimeInStorage    date,
    TimeOutOfStorage date,
    Status           text
);
DROP TABLE IF EXISTS "Sofa";
CREATE TABLE IF NOT EXISTS "Sofa"
(
    Sofa_ID  integer not null
        constraint Sofa_pk
            primary key autoincrement,
    Price    Double,
    Returned boolean not null,
    Status   TEXT
, TimeInStorage DATETIME, TimeOutOfStorage DATETIME);INSERT INTO Container VALUES(1,1000,'Standard','Warehouse 1');
INSERT INTO Container VALUES(2,2000,'Refrigerated','Warehouse 2');
INSERT INTO Transport VALUES(1,'Truck',20000,'Available');
INSERT INTO Transport VALUES(2,'Ship',50000,'In Use');

INSERT INTO Employee VALUES(6,'Alice Johnson','Manager','alice.johnson@example.com','LOGISTICS_MANAGER');
INSERT INTO Employee VALUES(2,'Bob Brown','Warehouse Clerk','bob.brown@example.com','WAREHOUSE_MANAGER');

INSERT INTO City VALUES(0,'New York',1);
INSERT INTO City VALUES(1,'Boston',0);
INSERT INTO City VALUES(2,'San Francisco',0);
INSERT INTO City VALUES(3,'Seattle',0);
INSERT INTO City VALUES(4,'Chicago',0);
INSERT INTO City VALUES(5,'San Jose',1);
INSERT INTO Edges VALUES(1,0,1,100.0);
INSERT INTO Edges VALUES(2,0,2,1200.0);
INSERT INTO Edges VALUES(3,1,2,900.0);
INSERT INTO Edges VALUES(4,1,3,300.0);
INSERT INTO Edges VALUES(5,3,2,400.0);
INSERT INTO Edges VALUES(6,2,4,500.0);
INSERT INTO Edges VALUES(7,3,4,1300.0);
INSERT INTO Edges VALUES(8,3,5,1500.0);
INSERT INTO Edges VALUES(9,4,5,400.0);
INSERT INTO Edges VALUES(10,1,0,100.0);
INSERT INTO Edges VALUES(11,2,0,1200.0);
INSERT INTO Edges VALUES(12,2,1,900.0);
INSERT INTO Edges VALUES(13,3,1,300.0);
INSERT INTO Edges VALUES(14,2,3,400.0);
INSERT INTO Edges VALUES(15,4,2,500.0);
INSERT INTO Edges VALUES(16,4,3,1300.0);
INSERT INTO Edges VALUES(17,5,3,1500.0);
INSERT INTO Edges VALUES(18,5,4,400.0);

INSERT INTO Cargo VALUES(1,'Electronics',500,3,'Fragile','Low','Laptop',1500.0);
INSERT INTO Cargo VALUES(2,'Furniture',1500,11,'Heavy','In Stock','Sofa',200.0);
INSERT INTO AdminAccount VALUES('admin','admin123');

INSERT INTO Shipment VALUES(1,'2023-07-01','2023-07-03','New York','New York',1,'San Francisco',1,1);
INSERT INTO Shipment VALUES(2,'2023-07-05','2023-07-20','San Jose','Chicago',2,'Chicago',2,1);
INSERT INTO Shipment VALUES(3,NULL,NULL,'New York','Boston',NULL,NULL,2,1);
INSERT INTO Shipment VALUES(4,NULL,NULL,'San Jose','San Francisco',NULL,NULL,1,3);
INSERT INTO Shipment VALUES(5,NULL,NULL,'New York','Boston',NULL,NULL,2,2);
INSERT INTO Laptop VALUES(1,1500.0,0,1701388800000,NULL,'In Stock');
INSERT INTO Laptop VALUES(2,1500.0,0,1701388800000,NULL,'In Stock');
INSERT INTO Laptop VALUES(3,1200.0,1,1701388800000,'2023-12-01','Sold');
INSERT INTO Laptop VALUES(4,1200.0,1,'2023-12-01',NULL,'In Stock');
INSERT INTO Sofa VALUES(1,160.0,1,'Sold',1701467767000,1701467771000);
INSERT INTO Sofa VALUES(2,160.0,1,'Sold',1701467772000,1701467773000);
INSERT INTO Sofa VALUES(3,200.0,0,'In Stock',1698875779000,NULL);
INSERT INTO Sofa VALUES(4,200.0,0,'In Stock',1698875775000,1701467778000);
INSERT INTO Sofa VALUES(5,200.0,0,'In Stock',1701467785000,NULL);
INSERT INTO Sofa VALUES(6,200.0,0,'In Stock',1701467786000,NULL);
INSERT INTO Sofa VALUES(7,200.0,0,'In Stock',1701726987000,NULL);
INSERT INTO Sofa VALUES(8,200.0,0,'In Stock',1701899790000,NULL);
INSERT INTO Sofa VALUES(9,200.0,0,'In Stock',1701899792000,NULL);
INSERT INTO Sofa VALUES(10,200.0,0,'In Stock',1701726995000,NULL);
INSERT INTO CargoContainerLink VALUES(1,1);
INSERT INTO CargoContainerLink VALUES(2,2);
INSERT INTO Tracking VALUES(1,1,'2023-07-01','New York','Departed');
INSERT INTO Tracking VALUES(2,1,'2023-07-03','Los Angeles','Arrived');
INSERT INTO EmployeeAccount VALUES(6,'alice.johnson','123');
INSERT INTO EmployeeAccount VALUES(2,'bob.brown','456');
INSERT INTO Customer VALUES(1,'John Doe','john.doe@example.com','San Francisco');
INSERT INTO Customer VALUES(2,'Jane Smith','jane.smith@example.com','Boston');
INSERT INTO Invoice VALUES(1,1,1,2000,'2023-07-01','Unpaid');
INSERT INTO Invoice VALUES(2,2,2,8000,'2023-07-05','Paid');
INSERT INTO Invoice VALUES(3,2,1,160,'2023-12-01','Paid');
INSERT INTO Invoice VALUES(4,1,4,1200,'2023-12-01','Paid');
INSERT INTO Invoice VALUES(5,2,5,160,'2023-12-01','Paid');
DELETE FROM sqlite_sequence;
INSERT INTO sqlite_sequence VALUES('Invoice',5);
INSERT INTO sqlite_sequence VALUES('Shipment',5);
INSERT INTO sqlite_sequence VALUES('Laptop',4);
INSERT INTO sqlite_sequence VALUES('Sofa',10);
CREATE VIEW user_account AS
SELECT ea.EmployeeID, ea.Username, e.Name, ea.Password, e.Role, e.ContactDetails
       FROM EmployeeAccount ea JOIN Employee e
           ON ea.EmployeeID == e.EmployeeID;
COMMIT;
