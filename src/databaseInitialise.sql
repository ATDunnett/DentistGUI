CREATE TABLE Dentists(
    userName VARCHAR(20) NOT NULL,
    userAddress VARCHAR(20),
    userPassword varchar(20),
    PRIMARY KEY(userName)
);
CREATE TABLE Patients(
    patientNumber INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    patientName VARCHAR(20),
    patientAddress VARCHAR(20),
    patientDentist VARCHAR(20),
    PRIMARY KEY(patientNumber)
);
CREATE TABLE Invoices(
    invoiceNumber INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    invoiceAmount DOUBLE,
    invoiceDate VARCHAR(50),
    invoicePaid BOOLEAN,
    invoicePatient INT,
    PRIMARY KEY(invoiceNumber)
);
CREATE TABLE Procedures(
    procedureNumber INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    procedureName VARCHAR(20),
    procedureCost DOUBLE,
    procedureInvoice INT,
    PRIMARY KEY(procedureNumber)
);
CREATE TABLE Payments(
    paymentNumber INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    paymentAmount DOUBLE,
    paymentDate VARCHAR(50),
    paymentInvoice INT,
    PRIMARY KEY(paymentNumber)
);
CREATE TABLE ProcedureTypes(
    procedureTypeNumber INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
    typeName VARCHAR(20),
    typeCost DOUBLE,
    PRIMARY KEY(typeName)
);

INSERT INTO Dentists VALUES('dentist00','address00','qwe');
INSERT INTO Dentists VALUES('dentist01','address01','qwe');
INSERT INTO Dentists VALUES('dentist02','address02','qwe');

INSERT INTO ProcedureTypes VALUES('procType00',50.0);
INSERT INTO ProcedureTypes VALUES('procType01',100.0);
INSERT INTO ProcedureTypes VALUES('procType02',150.0);
INSERT INTO ProcedureTypes VALUES('procType03',200.0);
INSERT INTO ProcedureTypes VALUES('procType04',250.0);
INSERT INTO ProcedureTypes VALUES('procType05',300.0);
INSERT INTO ProcedureTypes VALUES('procType06',400.0);
INSERT INTO ProcedureTypes VALUES('procType07',600.0);

SELECT userName FROM Dentists WHERE userName = 'dentist00'