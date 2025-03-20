CREATE DATABASE MEDIAMARTA;
USE MEDIAMARTA;

CREATE TABLE USER(CODUSER VARCHAR(20) PRIMARY KEY ,
USERNAME VARCHAR(30),
PSW VARCHAR(15),
TYPE_U ENUM ("Client","Admin"));

CREATE TABLE BRAND (CODBRAND INT AUTO_INCREMENT PRIMARY KEY,
NAMEBRAND VARCHAR(15));

CREATE TABLE PRODUCT (CODPRODUCT INT AUTO_INCREMENT PRIMARY KEY,
NAMEP VARCHAR(50),
TYPEP ENUM ("Mobile","Computer"),
PRICE DOUBLE,
STOCKPRODUCT INT,
CODBRAND INT,
FOREIGN KEY (CODBRAND) REFERENCES BRAND (CODBRAND));

CREATE TABLE PURCHASE (CODPRODUCT INT,
CODUSER VARCHAR (20),
PRIMARY KEY (CODPRODUCT,CODUSER),
QUANTITY DOUBLE,
DATEP DATE,
FOREIGN KEY (CODPRODUCT) REFERENCES PRODUCT (CODPRODUCT),
FOREIGN KEY (CODUSER) REFERENCES USER (CODUSER));

CREATE TABLE COMPONENT (CODCOMPONENT INT AUTO_INCREMENT PRIMARY KEY,
NAMECOMP VARCHAR(50),
TYPEC ENUM("Graphics","RAM","Processor"),
CODBRAND INT,
STOCKCOMPONENT INT,
PRICECOMP DOUBLE,
FOREIGN KEY (CODBRAND) REFERENCES BRAND (CODBRAND));

CREATE TABLE CONTAIN (CODPRODUCT INT,
CODCOMPONENT INT,
PRIMARY KEY(CODPRODUCT,CODCOMPONENT),
FOREIGN KEY (CODPRODUCT) REFERENCES PRODUCT (CODPRODUCT),
FOREIGN KEY (CODCOMPONENT) REFERENCES COMPONENT (CODCOMPONENT));

INSERT INTO USER VALUES 
("Pakete7","Paco","1234","Client"),
("Jago128","Jagoba","4321","Admin"),
("BoliBick","Victor","4321","Admin"),
("Xabitxu","Xabi","4321","Admin"),
("Mineralex","Alex","4321","Admin"),
("PepGuardiola","Pepe","1234","Client"),
("Joao10","Felix","1234","Client");
      
INSERT INTO BRAND (NAMEBRAND) VALUES 
("Apple"),
("Samsung"),
("Lenovo"),
("Huawei"),
("ASUS"),
("INTEL");
    
INSERT INTO PRODUCT (NAMEP,TYPEP,PRICE,STOCKPRODUCT,CODBRAND) VALUES
("Iphone X","Mobile",500,150,1),
("Samsung Galaxy Book 4","Computer",399,70,2),
("Lenovo IdeaPad Slim 3","Computer",700,300,3),
("Samsung Galaxy S24","Mobile",550,244,2),
("HUAWEI Pura 70 Pro","Mobile",1000,700,4);

INSERT INTO PURCHASE VALUES 
(5,'Pakete7',5,'2025-02-20'),
(1,'PepGuardiola',12,'2025-01-04'),
(3,'Joao10',1,'2025-03-03'),
(2,'Pakete7',2,'2025-02-25');

INSERT INTO COMPONENT (NAMECOMP,TYPEC,CODBRAND,STOCKCOMPONENT,PRICECOMP) VALUES 
("Asus GT710","Graphics",5,4,81.99),
("Intel Core i5-13400","Processor",6,5,170),
("ESC 4000 G4X","RAM",5,10,110.99),
("OFFTEK 8GB","RAM",5,12,30),
("ASUS Dual RTX 4060 TI","Graphics",5,3,81.99),
("Ultra 9 285K","Processor",6,4,665);

INSERT INTO CONTAIN VALUES 
(4,2),
(3,5);

Delimiter //    
CREATE PROCEDURE InsertProduct(NAMEP VARCHAR(50), TYPEP ENUM ("Mobile","Computer"), PRICE DOUBLE, STOCKPRODUCT DOUBLE, CODBRAND INT)
BEGIN 
INSERT INTO Product (NAMEP,TYPEP,PRICE,STOCKPRODUCT,CODBRAND) VALUES
(NAMEP,TYPEP,PRICE,STOCKPRODUCT,CODBRAND);
SELECT 'Producto añadido' AS Mensaje;        
END //
Delimiter ;

Delimiter //    
CREATE PROCEDURE InsertComponent(NAMECOMP VARCHAR(50), TYPEC ENUM("Graphics","RAM","Processor"), CODBRAND INT, STOCKCOMPONENT INT,PRICECOMP DOUBLE)
BEGIN 
INSERT INTO Product (NAMECOMP,TYPEC,CODBRAND, STOCKCOMPONENT, PRICECOMP) VALUES
(NAMECOMP,TYPEC,CODBRAND, STOCKCOMPONENT, PRICECOMP);
SELECT 'Componente añadido' AS Mensaje;
END //
Delimiter ;

DELIMITER //
CREATE FUNCTION sellAndSubstract(amount DOUBLE, NAMEPROD VARCHAR(50)) RETURNS DOUBLE
BEGIN
	DECLARE CURRENTSTOCK DOUBLE DEFAULT 0;
    DECLARE CODPROD INT;
    SET CODPROD:=(SELECT CODPRODUCT FROM PRODUCT WHERE NAMEP=NAMEPROD);
	SET CURRENTSTOCK:=((SELECT STOCKPRODUCT FROM PRODUCT WHERE CODPRODUCT=CODPROD)-AMOUNT);
    UPDATE PRODUCT SET STOCKPRODUCT=CURRENTSTOCK WHERE CODPRODUCT=CODPROD;
	RETURN AMOUNT;
END //
DELIMITER ;

Delimiter //
CREATE FUNCTION LowStock() RETURNS VARCHAR(50)
BEGIN
	DECLARE Fin BOOLEAN DEFAULT FALSE;
    DECLARE NomProd VARCHAR(50);
    DECLARE TipoP ENUM ("Mobile","Computer");
    DECLARE PriceP DOUBLE;
    DECLARE StockP DOUBLE;
    DECLARE CodBrand INT;
    DECLARE NomComp VARCHAR(50);
    DECLARE TipoC ENUM ("Mobile","Computer");
    DECLARE PriceComp DOUBLE;
    DECLARE StockC INT;
    DECLARE C CURSOR FOR SELECT REFPED, FECPED FROM PEDIDO;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET Fin = TRUE;
    SELECT NAME FROM PRODUCT WHERE STOCKP >= 5;
END //
Delimiter ;

Delimiter //
CREATE PROCEDURE DeleteProduct(NomProd VARCHAR(50))
BEGIN
	DECLARE CodigoProd INT;
    DECLARE ENCONTRADO BOOLEAN DEFAULT TRUE;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET ENCONTRADO = FALSE;
	SELECT CODPRODUCT INTO CodigoProd FROM PRODUCT WHERE NAMEP = NomProd;

    IF !ENCONTRADO THEN
		SELECT CONCAT('El producto ', NomProd,' no se ha encontrado.');
    ELSE 
		SELECT CONCAT('Producto ', NomProd,' se ha borrado correctamente.');
		DELETE FROM PRODUCTO WHERE CODPRODUCT = CodigoProd;
    END IF;
END //
Delimiter ;

Delimiter //
CREATE PROCEDURE ShowProducts()
BEGIN
	DECLARE Fin BOOLEAN DEFAULT FALSE;
    DECLARE NomProd VARCHAR(50);
    DECLARE TipoP ENUM ("Mobile","Computer");
    DECLARE PriceP DOUBLE;
    DECLARE StockP DOUBLE;
    DECLARE CodBrand INT;
    DECLARE C CURSOR FOR SELECT NAMEP,TYPEP,PRICE,STOCK,CODBRAND FROM PRODUCT;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET FIN = TRUE;    
    OPEN C;
	FETCH C INTO NomProd,TipoP,PriceP,StockP,CodBrand; 
	WHILE !FIN DO
		SELECT CONCAT ('Name: ', NomProd, ' Type: ', TipoP,' Price: ', PriceP,' Stock: ', StockP,' CodeBrand: ', CodBrand) "Datos pedidos"; 
		Fetch c into NomProd,TipoP,PriceP,StockP,CodBrand; 
    END WHILE; 
    CLOSE C; 
END //
Delimiter ;

Delimiter //
CREATE PROCEDURE ShowComponents()
BEGIN
	DECLARE Fin BOOLEAN DEFAULT FALSE;
    DECLARE NomComp VARCHAR(50);
    DECLARE TipoC ENUM ("Mobile","Computer");
    DECLARE CodBrand INT;
    DECLARE PriceComp DOUBLE;
    DECLARE C CURSOR FOR SELECT NAMECOMP,TYPEC,CODBRAND,PRICECOMP FROM COMPONENT;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET FIN = TRUE;    
    OPEN C;
	FETCH C INTO NomComp,TipoC,CodBrand,PriceComp; 
	WHILE !FIN DO
		SELECT CONCAT ('Name: ', NomComp, ' Type: ', TipoC,' CodeBrand: ', CodBrand,' Price: ', PriceComp) "Datos pedidos"; 
		Fetch c into NomComp,TipoC,CodBrand,PriceComp;  
    END WHILE; 
    CLOSE C; 
END //
Delimiter ;
