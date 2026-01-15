DROP DATABASE IF EXISTS  OrderDeliveryDB; ;
CREATE DATABASE IF NOT EXISTS OrderDeliveryDB;
USE OrderDeliveryDB;


CREATE TABLE Customer (
    CustomerID INT PRIMARY KEY,
    Name VARCHAR(100),
    Address TEXT,
    Phone VARCHAR(15),
    Email VARCHAR(100)
);

CREATE TABLE DeliveryPerson (
    PersonID INT PRIMARY KEY,
    Type VARCHAR(20) CHECK (Type IN ('Employee', 'Freelancer'))
);

CREATE TABLE Warehouse (
    WarehouseID INT PRIMARY KEY,
    Location VARCHAR(100),
    Capacity INT
);
-- CREATE TABLE Vendor (
--     VendorID INT PRIMARY KEY,
--     Name VARCHAR(100),
--     Contact VARCHAR(50),
--     Location VARCHAR(100),
--     Rating FLOAT
-- );

-- CREATE TABLE Product (
--     ProductID INT PRIMARY KEY,
--     Name VARCHAR(100),
--     Category VARCHAR(100),
--     Price DECIMAL(10,2),
--     Stock INT,
--     WarehouseID INT,
--     VendorID INT,
--     OrderID INT,
--     FOREIGN KEY (WarehouseID) REFERENCES Warehouse(WarehouseID),
--     FOREIGN KEY (VendorID) REFERENCES Vendor(VendorID),
--     FOREIGN KEY (OrderID) REFERENCES ORDERS (OrderID)
-- );
-- CREATE TABLE Perishable (
--     ProductID INT PRIMARY KEY,
--     ExpirationDate DATE,
--     StorageTemperature VARCHAR(50),
--     PackagingType VARCHAR(50),
--     SpecialHandling TEXT,
--     FragilityLevel VARCHAR(50),
--     FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
-- );
-- CREATE TABLE NonPerishable (
--     ProductID INT PRIMARY KEY,
--     WarrantyPeriod VARCHAR(50),
--     ReturnPolicy TEXT,
--     FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
-- );

CREATE TABLE ORDERS (
    OrderID INT PRIMARY KEY,
    OrderDate DATE,
    Status VARCHAR(50),
    TotalAmount DECIMAL(10,2),
    CustomerID INT,
    DeliveryPersonID INT,
    WarehouseID INT,
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (DeliveryPersonID) REFERENCES DeliveryPerson(PersonID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouse(WarehouseID)
);


CREATE TABLE Payment (
    PaymentID INT PRIMARY KEY,
    OrderID INT,
    PaymentMode VARCHAR(50),
    TransactionDate DATE,
    FOREIGN KEY (OrderID) REFERENCES ORDERS (OrderID)
);
CREATE TABLE ShipmentTracking (
    TrackingID INT PRIMARY KEY,
    OrderID INT,
    Status VARCHAR(50),
    CurrentLocation VARCHAR(100),
    FOREIGN KEY (OrderID) REFERENCES ORDERS (OrderID)
);
CREATE TABLE Product (
    ProductID INT PRIMARY KEY,
    Name VARCHAR(100),
    Category VARCHAR(100),
    Price DECIMAL(10,2),
    Stock INT
);

INSERT INTO Product (ProductID, Name, Category, Price, Stock)
VALUES
(1, 'Amul Milk', 'Dairy', 52.00, 100),
(2, 'Tata Salt', 'Grocery', 20.00, 200),
(3, 'Fortune Oil', 'Grocery', 130.00, 150),
(4, 'Nestle Yogurt', 'Dairy', 40.00, 80);

INSERT INTO Product (ProductID, Name, Category, Price, Stock)
VALUES
(5, 'Parle-G Biscuits', 'Snacks', 10.00, 300),
(6, 'Maggie Noodles', 'Snacks', 14.00, 250),
(7, 'Aashirvaad Atta', 'Grocery', 280.00, 90),
(8, 'Mother Dairy Paneer', 'Dairy', 85.00, 70),
(9, 'Dabur Honey', 'Grocery', 199.00, 60),
(10, 'Britannia Cheese Slices', 'Dairy', 120.00, 50),
(11, 'Haldiram Bhujia', 'Snacks', 50.00, 100),
(12, 'Tropicana Juice', 'Beverage', 110.00, 40),
(13, 'Real Mixed Fruit Juice', 'Beverage', 105.00, 45),
(14, 'Surf Excel Detergent', 'Household', 190.00, 75),
(15, 'Colgate Toothpaste', 'Personal Care', 55.00, 120);

CREATE TABLE Vendor (
    VendorID INT PRIMARY KEY,
    Name VARCHAR(100),
    Contact VARCHAR(50),
    Location VARCHAR(100),
    Rating FLOAT
);

INSERT INTO Vendor (VendorID, Name, Contact, Location, Rating)
VALUES
(1, 'Reliance', '+91-9876543210', 'Mumbai', 4.5),
(2, 'Tata', '+91-9876501234', 'Pune', 4.3),
(3, 'BigBasket', '+91-9123456780', 'Bangalore', 4.7),
(4, 'Metro', '+91-9988776655', 'Delhi', 4.4);

INSERT INTO Vendor (VendorID, Name, Contact, Location, Rating)
VALUES
(5, 'D-Mart', '+91-9090909090', 'Hyderabad', 4.6),
(6, 'Spencer\'s Retail', '+91-9012345678', 'Kolkata', 4.2),
(7, 'Nature\'s Basket', '+91-9087654321', 'Mumbai', 4.5),
(8, 'Amazon Pantry', '+91-9112233445', 'Chennai', 4.8),
(9, 'Flipkart Supermart', '+91-9001122334', 'Bangalore', 4.6),
(10, 'More Retail', '+91-9334455667', 'Ahmedabad', 4.3),
(11, 'Ratnadeep', '+91-9556677889', 'Hyderabad', 4.4),
(12, 'Heritage Fresh', '+91-9778899001', 'Vijayawada', 4.2),
(13, 'JioMart', '+91-9345612390', 'Thane', 4.5),
(14, 'Star Bazaar', '+91-9567123489', 'Navi Mumbai', 4.1),
(15, 'Local Kirana', '+91-9998887776', 'Jaipur', 4.0);


CREATE TABLE Employee (
    EmployeeID INT PRIMARY KEY,
    PersonID INT,
    EmploymentType VARCHAR(50),
    AssignedRegion VARCHAR(50),
    ShiftTimings VARCHAR(50),
    Salary DECIMAL(10,2),
    FOREIGN KEY (PersonID) REFERENCES DeliveryPerson(PersonID)
);
CREATE TABLE Freelancer (
    FreelancerID INT PRIMARY KEY,
    PersonID INT,
    Commission DECIMAL(10,2),
    AvailabilitySchedule VARCHAR(100),
    ContractExpiryDate DATE,
    Rating FLOAT,
    FOREIGN KEY (PersonID) REFERENCES DeliveryPerson(PersonID)
);
CREATE TABLE Vehicle (
    VehicleID INT PRIMARY KEY,
    Type VARCHAR(50),
    Capacity INT,
    Status VARCHAR(50),
    PersonID INT,
    FOREIGN KEY (PersonID) REFERENCES DeliveryPerson(PersonID)
);
CREATE TABLE RouteOptimization (
    RouteID INT PRIMARY KEY,
    Distance FLOAT,
    EstimatedTime TIME,
    VehicleID INT,
    FOREIGN KEY (VehicleID) REFERENCES Vehicle(VehicleID)
);

INSERT INTO Customer (CustomerID, Name, Address, Phone, Email) VALUES 
(1, 'Aarav Mehta', '123 MG Road, Bengaluru, Karnataka', '+91-9876543210', 'aaravmehta@example.com'),
(2, 'Isha Sharma', '56 Connaught Place, New Delhi', '+91-9123456789', 'ishasharma@example.com'),
(3, 'Rohan Verma', '221 Bandra West, Mumbai', '+91-9988776655', 'rohanv@example.com'),
(4, 'Ananya Singh', '12 Salt Lake, Kolkata', '+91-9090909090', 'ananya.singh@example.com'),
(5, 'Kabir Jain', '9 Residency Road, Chennai', '+91-9876501234', 'kabirj@example.com'),
(6, 'Sneha Reddy', '45 Banjara Hills, Hyderabad', '+91-9765432109', 'snehareddy@example.com'),
(7, 'Vivaan Kapoor', '33 Sector 17, Chandigarh', '+91-9345678901', 'vivaan.kapoor@example.com'),
(8, 'Diya Nair', '66 Vyttila, Kochi', '+91-9012345678', 'diyanair@example.com'),
(9, 'Ayaan Deshmukh', '77 Camp Area, Pune', '+91-9234567890', 'ayaan.d@example.com'),
(10, 'Tanya Gupta', '88 Hazratganj, Lucknow', '+91-9356789012', 'tanyag@example.com');



INSERT INTO DeliveryPerson (PersonID, Type) VALUES
(1, 'Employee'),
(2, 'Freelancer'),
(3, 'Employee'),
(4, 'Freelancer'),
(5, 'Employee'),
(6, 'Freelancer'),
(7, 'Employee'),
(8, 'Freelancer'),
(9, 'Employee'),
(10, 'Freelancer');


INSERT INTO Warehouse (WarehouseID, Location, Capacity) VALUES
(1, 'Bhiwandi, Maharashtra', 10000),
(2, 'Patparganj, Delhi', 8500),
(3, 'Whitefield, Bengaluru', 9000),
(4, 'Madhavaram, Chennai', 7500),
(5, 'Barasat, Kolkata', 8000),
(6, 'Manesar, Haryana', 9500),
(7, 'Sanath Nagar, Hyderabad', 7200),
(8, 'Pimpri, Pune', 8800),
(9, 'Panvel, Navi Mumbai', 9200),
(10, 'Aluva, Kochi', 7800);

INSERT INTO ORDERS (OrderID, OrderDate, Status, TotalAmount, CustomerID, DeliveryPersonID, WarehouseID) VALUES
(1, '2024-04-01', 'Delivered', 1250.00, 1, 1, 1),
(2, '2024-04-02', 'Shipped', 2150.50, 2, 2, 2),
(3, '2024-04-03', 'Processing', 1999.99, 3, 3, 3),
(4, '2024-04-04', 'Delivered', 3200.75, 4, 4, 4),
(5, '2024-04-05', 'Cancelled', 850.00, 5, 5, 5),
(6, '2024-04-06', 'Returned', 1500.00, 6, 6, 6),
(7, '2024-04-07', 'Delivered', 780.60, 7, 7, 7),
(8, '2024-04-08', 'Shipped', 1650.40, 8, 8, 8),
(9, '2024-04-09', 'Processing', 2750.90, 9, 9, 9),
(10, '2024-04-10', 'Delivered', 999.00, 10, 10, 10);

INSERT INTO Payment (PaymentID, OrderID, PaymentMode, TransactionDate) VALUES
(1, 1, 'UPI', '2024-04-01'),
(2, 2, 'Credit Card', '2024-04-02'),
(3, 3, 'Debit Card', '2024-04-03'),
(4, 4, 'Cash on Delivery', '2024-04-04'),
(5, 5, 'Net Banking', '2024-04-05'),
(6, 6, 'UPI', '2024-04-06'),
(7, 7, 'Credit Card', '2024-04-07'),
(8, 8, 'Wallet', '2024-04-08'),
(9, 9, 'UPI', '2024-04-09'),
(10, 10, 'Debit Card', '2024-04-10');

INSERT INTO ShipmentTracking (TrackingID, OrderID, Status, CurrentLocation) VALUES
(1, 1, 'Delivered', 'Bengaluru'),
(2, 2, 'In Transit', 'Agra'),
(3, 3, 'Warehouse Scan', 'Pune'),
(4, 4, 'Delivered', 'Chennai'),
(5, 5, 'Cancelled', 'Mumbai'),
(6, 6, 'Return Initiated', 'Hyderabad'),
(7, 7, 'Delivered', 'Delhi'),
(8, 8, 'Shipped', 'Kolkata'),
(9, 9, 'In Transit', 'Ahmedabad'),
(10, 10, 'Delivered', 'Lucknow');

INSERT INTO Employee (EmployeeID, PersonID, EmploymentType, AssignedRegion, ShiftTimings, Salary) VALUES 
(1, 1, 'Full-Time', 'South Zone', '9AM-6PM', 25000.00),
(2, 3, 'Part-Time', 'North Zone', '2PM-8PM', 12000.00),
(3, 5, 'Full-Time', 'East Zone', '10AM-7PM', 23000.00),
(4, 7, 'Full-Time', 'West Zone', '9AM-6PM', 24000.00),
(5, 9, 'Part-Time', 'Central Zone', '4PM-9PM', 13000.00);

INSERT INTO Freelancer (FreelancerID, PersonID, Commission, AvailabilitySchedule, ContractExpiryDate, Rating) VALUES 
(1, 2, 500.00, '10AM-6PM', '2024-12-31', 4.5),
(2, 4, 450.00, '11AM-5PM', '2024-11-30', 4.0),
(3, 6, 400.00, '1PM-8PM', '2024-10-15', 3.8),
(4, 8, 550.00, '9AM-4PM', '2025-01-15', 4.6),
(5, 10, 600.00, '12PM-7PM', '2024-12-01', 4.3);

INSERT INTO Vehicle (VehicleID, Type, Capacity, Status, PersonID) VALUES
(1, 'Bike', 100, 'Active', 1),
(2, 'Van', 500, 'Active', 2),
(3, 'Truck', 1000, 'In Service', 3),
(4, 'Bike', 150, 'Active', 4),
(5, 'Van', 600, 'Under Repair', 5),
(6, 'Truck', 1200, 'Active', 6),
(7, 'Scooter', 90, 'Active', 7),
(8, 'Mini Van', 400, 'In Service', 8),
(9, 'Bike', 100, 'Active', 9),
(10, 'Van', 550, 'Active', 10);

INSERT INTO RouteOptimization (RouteID, Distance, EstimatedTime, VehicleID) VALUES
(1, 12.5, '00:25:00', 1),
(2, 30.0, '00:45:00', 2),
(3, 55.2, '01:10:00', 3),
(4, 10.0, '00:20:00', 4),
(5, 40.7, '01:00:00', 5),
(6, 70.5, '01:30:00', 6),
(7, 8.0, '00:18:00', 7),
(8, 25.4, '00:40:00', 8),
(9, 15.0, '00:30:00', 9),
(10, 33.3, '00:50:00', 10);

-- Customer Table
SELECT * FROM Customer;

-- DeliveryPerson Table
SELECT * FROM DeliveryPerson;

-- Warehouse Table
SELECT * FROM Warehouse;

-- Orders Table
SELECT * FROM ORDERS;

-- Payment Table
SELECT * FROM Payment;

-- ShipmentTracking Table
SELECT * FROM ShipmentTracking;

-- Vendor Table
SELECT * FROM Vendor;

-- Product Table
SELECT * FROM Product;

-- Perishable Table
SELECT * FROM Perishable;

-- NonPerishable Table
SELECT * FROM NonPerishable;

-- Employee Table
SELECT * FROM Employee;

-- Freelancer Table
SELECT * FROM Freelancer;

-- Vehicle Table
SELECT * FROM Vehicle;

-- RouteOptimization Table


-- drop table Login ;
CREATE TABLE Login (
  username VARCHAR(20) NOT NULL,
  password VARCHAR(20) NOT NULL,
  role ENUM('Admin', 'Customer', 'Vendor') NOT NULL,
  PRIMARY KEY (username, role)
);
INSERT INTO Login (username, password, role) VALUES
('admin', 'admin123', 'Admin'),
('customer', 'customer123', 'Customer'),
('vendor', 'vendor123', 'Vendor')
;

select * from  Login;


