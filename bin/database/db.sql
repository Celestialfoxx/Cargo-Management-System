PRAGMA foreign_keys = ON;
-- Cargo Table
DROP TABLE IF EXISTS 'Cargo';
CREATE TABLE Cargo (
   CargoID INT PRIMARY KEY,
   Description VARCHAR(255),
   Weight DECIMAL(10, 2),
   Volume DECIMAL(10, 2),
   Type VARCHAR(50),
   Status VARCHAR(50)
);


-- Container Table
DROP TABLE IF EXISTS 'Container';
CREATE TABLE Container (
   ContainerID INT PRIMARY KEY,
   Capacity DECIMAL(10, 2),
   ContainerType VARCHAR(50),
   Location VARCHAR(255)
);


-- Transport Table
DROP TABLE IF EXISTS 'Transport';
CREATE TABLE Transport (
   TransportID INT PRIMARY KEY,
   TransportType VARCHAR(50),
   Capacity DECIMAL(10, 2),
   AvailabilityStatus VARCHAR(50)
);


-- Shipment Table
DROP TABLE IF EXISTS 'Shipment';
CREATE TABLE Shipment (
   ShipmentID INT PRIMARY KEY,
   DepartureDate DATE,
   ArrivalDate DATE,
   Origin VARCHAR(255),
   Destination VARCHAR(255),
   TransportID INT,
   FOREIGN KEY (TransportID) REFERENCES Transport(TransportID) ON DELETE CASCADE ON UPDATE CASCADE
);


-- CargoContainerLink Table
DROP TABLE IF EXISTS 'CargoContainerLink';
CREATE TABLE CargoContainerLink (
   CargoID INT,
   ContainerID INT,
   PRIMARY KEY (CargoID, ContainerID),
   FOREIGN KEY (CargoID) REFERENCES Cargo(CargoID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (ContainerID) REFERENCES Container(ContainerID) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Customer Table
DROP TABLE IF EXISTS 'Customer';
CREATE TABLE Customer (
   CustomerID INT PRIMARY KEY,
   Name VARCHAR(255),
   ContactDetails VARCHAR(255),
   Address VARCHAR(255)
);


-- Employee Table
DROP TABLE IF EXISTS 'Employee';
CREATE TABLE Employee (
   EmployeeID INT PRIMARY KEY,
   Name VARCHAR(255),
   Role VARCHAR(50),
   ContactDetails VARCHAR(255)
);


-- Tracking Table
DROP TABLE IF EXISTS 'Tracking';
CREATE TABLE Tracking (
   TrackingID INT PRIMARY KEY,
   CargoID INT,
   Date DATE,
   Location VARCHAR(255),
   StatusUpdate VARCHAR(255),
   FOREIGN KEY (CargoID) REFERENCES Cargo(CargoID) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Invoice Table
DROP TABLE IF EXISTS 'Invoice';
CREATE TABLE Invoice (
   InvoiceID INT PRIMARY KEY,
   CustomerID INT,
   ShipmentID INT,
   Amount DECIMAL(10, 2),
   DateIssued DATE,
   PaymentStatus VARCHAR(50),
   FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (ShipmentID) REFERENCES Shipment(ShipmentID) ON DELETE CASCADE ON UPDATE CASCADE
);


-- EmployeeAccount Table
DROP TABLE IF EXISTS 'EmployeeAccount';
CREATE TABLE EmployeeAccount (
   EmployeeID INT PRIMARY KEY,
   Username VARCHAR(255) UNIQUE NOT NULL,
   Password VARCHAR(255) NOT NULL,
   FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE ON UPDATE CASCADE
);






-- Insert data into Cargo
INSERT INTO `Cargo` (`CargoID`, `Description`, `Weight`, `Volume`, `Type`, `Status`) VALUES
(1, 'Electronics', 500.00, 2.50, 'Fragile', 'In Transit'),
(2, 'Furniture', 1500.00, 15.00, 'Heavy', 'Delivered');


-- Insert data into Container
INSERT INTO `Container` (`ContainerID`, `Capacity`, `ContainerType`, `Location`) VALUES
(1, 1000.00, 'Standard', 'Warehouse 1'),
(2, 2000.00, 'Refrigerated', 'Warehouse 2');


-- Insert data into Transport
INSERT INTO `Transport` (`TransportID`, `TransportType`, `Capacity`, `AvailabilityStatus`) VALUES
(1, 'Truck', 20000.00, 'Available'),
(2, 'Ship', 50000.00, 'In Use');


-- Insert data into Shipment
INSERT INTO `Shipment` (`ShipmentID`, `DepartureDate`, `ArrivalDate`, `Origin`, `Destination`, `TransportID`) VALUES
(1, '2023-07-01', '2023-07-03', 'New York', 'Los Angeles', 1),
(2, '2023-07-05', '2023-07-20', 'Shanghai', 'Hamburg', 2);


-- Insert data into CargoContainerLink
INSERT INTO `CargoContainerLink` (`CargoID`, `ContainerID`) VALUES
(1, 1),
(2, 2);


-- Insert data into Customer
INSERT INTO `Customer` (`CustomerID`, `Name`, `ContactDetails`, `Address`) VALUES
(1, 'John Doe', 'john.doe@example.com', '123 Maple Street'),
(2, 'Jane Smith', 'jane.smith@example.com', '456 Oak Avenue');


-- Insert data into Employee
INSERT INTO `Employee` (`EmployeeID`, `Name`, `Role`, `ContactDetails`) VALUES
(1, 'Alice Johnson', 'Manager', 'alice.johnson@example.com'),
(2, 'Bob Brown', 'Warehouse Clerk', 'bob.brown@example.com');


-- Insert data into Tracking
INSERT INTO `Tracking` (`TrackingID`, `CargoID`, `Date`, `Location`, `StatusUpdate`) VALUES
(1, 1, '2023-07-01', 'New York', 'Departed'),
(2, 1, '2023-07-03', 'Los Angeles', 'Arrived');


-- Insert data into Invoice
INSERT INTO `Invoice` (`InvoiceID`, `CustomerID`, `ShipmentID`, `Amount`, `DateIssued`, `PaymentStatus`) VALUES
(1, 1, 1, 2000.00, '2023-07-01', 'Unpaid'),
(2, 2, 2, 8000.00, '2023-07-05', 'Paid');


-- Insert account data into EmployeeAccount
-- This is an example. In a real-world scenario, ensure that the password is hashed.
INSERT INTO EmployeeAccount (EmployeeID, Username, Password) VALUES
(1, 'alice.johnson', 123),
(2, 'bob.brown', 456);