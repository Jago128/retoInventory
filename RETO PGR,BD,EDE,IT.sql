CREATE DATABASE MEDIAMARTA;
USE MEDIAMARTA;

CREATE TABLE USER (
    CODUSER VARCHAR(20) PRIMARY KEY,
    USERNAME VARCHAR(30),
    PSW VARCHAR(15),
    TYPE_U ENUM('Client', 'Admin')
);

CREATE TABLE BRAND (
    CODBRAND INT AUTO_INCREMENT PRIMARY KEY,
    NAMEBRAND VARCHAR(15)
);

CREATE TABLE PRODUCT (
    CODPRODUCT INT AUTO_INCREMENT PRIMARY KEY,
    NAMEP VARCHAR(50),
    TYPEP ENUM('Mobile', 'Computer'),
    PRICE DOUBLE,
    STOCKPRODUCT INT,
    CODBRAND INT,
    FOREIGN KEY (CODBRAND)
        REFERENCES BRAND (CODBRAND)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE PURCHASE (
    CODPURCHASE INT AUTO_INCREMENT PRIMARY KEY,
    CODPRODUCT INT,
    CODUSER VARCHAR(20),
    QUANTITY INT,
    TOTALPRICE DOUBLE,
    DATEP DATE,
    FOREIGN KEY (CODPRODUCT)
        REFERENCES PRODUCT (CODPRODUCT)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (CODUSER)
        REFERENCES USER (CODUSER)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE COMPONENT (
    CODCOMPONENT INT AUTO_INCREMENT PRIMARY KEY,
    NAMECOMP VARCHAR(50),
    TYPEC ENUM('Graphics', 'RAM', 'Processor'),
    CODBRAND INT,
    STOCKCOMPONENT INT,
    PRICECOMP DOUBLE,
    FOREIGN KEY (CODBRAND)
        REFERENCES BRAND (CODBRAND)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE CONTAIN (
    CODPRODUCT INT,
    CODCOMPONENT INT,
    PRIMARY KEY (CODPRODUCT, CODCOMPONENT),
    FOREIGN KEY (CODPRODUCT)
        REFERENCES PRODUCT (CODPRODUCT)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (CODCOMPONENT)
        REFERENCES COMPONENT (CODCOMPONENT)
        ON UPDATE CASCADE ON DELETE CASCADE
);

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
("Samsung Galaxy S24","Mobile",550,4,2),
("ASUS ExpertBook","Computer",710,300,5),
("HUAWEI Pura 70 Pro","Mobile",1000,700,4);

INSERT INTO PURCHASE VALUES 
(6,'Pakete7',5, 5000,'2025-02-20'),
(1,'PepGuardiola',12, 6000,'2025-01-04'),
(3,'Joao10',1, 700,'2025-03-03'),
(4,'Pakete7',2, 1100,'2025-02-25');

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
CREATE PROCEDURE signIn (UCOD VARCHAR(20), UNAME VARCHAR(30), UPSW VARCHAR(15))
BEGIN
	INSERT INTO USER (CODUSER, USERNAME, PSW, TYPE_U) VALUES(UCOD, UNAME, UPSW, "Client");
    SELECT 'Usuario añadido' AS Mensaje;
END //
Delimiter ;

Delimiter //    
CREATE PROCEDURE insertProduct(NAMEP VARCHAR(50), TYPEP ENUM ("Mobile","Computer"), PRICE DOUBLE, STOCKPRODUCT INT, CODBRAND INT)
BEGIN
INSERT INTO Product (NAMEP,TYPEP,PRICE,STOCKPRODUCT,CODBRAND) VALUES
(NAMEP,TYPEP,PRICE,STOCKPRODUCT,CODBRAND);
SELECT 'Producto añadido' AS Mensaje;
END //
Delimiter ;

Delimiter //    
CREATE PROCEDURE insertComponent(NAMECOMP VARCHAR(50), TYPEC ENUM("Graphics","RAM","Processor"), CODBRAND INT, STOCKCOMPONENT INT,PRICECOMP DOUBLE)
BEGIN
INSERT INTO Product (NAMECOMP,TYPEC,CODBRAND, STOCKCOMPONENT, PRICECOMP) VALUES
(NAMECOMP,TYPEC,CODBRAND, STOCKCOMPONENT, PRICECOMP);
SELECT 'Componente añadido' AS Mensaje;
END //
Delimiter ;

DELIMITER //
CREATE PROCEDURE deleteComp(NomComp VARCHAR(50))
BEGIN
	DECLARE CodigoComp INT;
    DECLARE ENCONTRADO BOOLEAN DEFAULT TRUE;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET ENCONTRADO = FALSE;
    
	SELECT CODCOMPONENT INTO CodigoComp FROM COMPONENT WHERE NAMECOMP = NomComp;

    IF NOT ENCONTRADO THEN
		SELECT CONCAT('The component ', NomComp,' could not be found.') AS DELETE_PRODUCT;
    ELSE 
		SELECT CONCAT('The component ', NomComp,' has been deleted correctly.') AS DELETE_PRODUCT;
		DELETE FROM COMPONENT WHERE CODCOMPONENT = CodigoComp;
    END IF;        
END //
Delimiter ;

Delimiter //
CREATE PROCEDURE showProdsAndCompsOfAParticularBrand(brandName VARCHAR(15))
BEGIN
    DECLARE fin BOOLEAN DEFAULT FALSE;
    DECLARE prodName VARCHAR(50);
    DECLARE prodType ENUM('Mobile', 'Computer');
    DECLARE prodPrice DOUBLE;
    DECLARE prodStock INT;
	DECLARE brandExists INT DEFAULT 0;
    
    DECLARE compName VARCHAR(50);
    DECLARE compType ENUM('Graphics', 'RAM', 'Processor');
    DECLARE compPrice DOUBLE;
    DECLARE compStock INT;

    DECLARE cur_prod CURSOR FOR
        SELECT P.NAMEP, P.TYPEP, P.PRICE, P.STOCKPRODUCT
        FROM PRODUCT P
        JOIN BRAND B ON P.CODBRAND = B.CODBRAND
        WHERE B.NAMEBRAND = brandName;
    DECLARE cur_comp CURSOR FOR
        SELECT C.NAMECOMP, C.TYPEC, C.PRICECOMP, C.STOCKCOMPONENT
        FROM COMPONENT C
        JOIN BRAND B ON C.CODBRAND = B.CODBRAND
        WHERE B.NAMEBRAND = brandName;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = TRUE;
    
    SELECT COUNT(*) INTO brandExists FROM BRAND WHERE NAMEBRAND = brandName;
    
    IF brandExists = 0 THEN
        SELECT CONCAT('No items were found for brand: ', brandName) AS Message;
        
	ELSE
    
    OPEN cur_prod;
    FETCH cur_prod INTO prodName, prodType, prodPrice, prodStock;
    WHILE NOT fin DO
        SELECT CONCAT('Brand: ', brandName, ' | Product: ', prodName, ' | Type: ', prodType, ' | Price: ', prodPrice, ' | Stock: ', prodStock) AS Product_Info;
        FETCH cur_prod INTO prodName, prodType, prodPrice, prodStock;
    END WHILE;
    CLOSE cur_prod;
	SET fin = FALSE;

    OPEN cur_comp;
    FETCH cur_comp INTO compName, compType, compPrice, compStock;
    WHILE NOT fin DO
        SELECT CONCAT('Brand: ', brandName, ' | Component: ', compName, ' | Type: ', compType, ' | Price: ', compPrice, ' | Stock: ', compStock) AS Component_Info;
        FETCH cur_comp INTO compName, compType, compPrice, compStock;
    END WHILE;
    CLOSE cur_comp;
    END IF ;
END //
DELIMITER ;
CALL showProdsAndCompsOfAParticularBrand("Nokia");
DROP PROCEDURE showProdsAndCompsOfAParticularBrand;

Delimiter //
CREATE PROCEDURE showLowStock()  
BEGIN
    DECLARE Fin BOOLEAN DEFAULT FALSE;
    DECLARE CodProd INT;
    DECLARE NomProd VARCHAR(50);
    DECLARE StockP INT;
    DECLARE CodComp INT;
    DECLARE NomComp VARCHAR(50);
    DECLARE StockC INT;
    DECLARE C CURSOR FOR (SELECT P.CODPRODUCT, P.NAMEP, P.STOCKPRODUCT FROM PRODUCT P ORDER BY STOCKPRODUCT);
    DECLARE C2 CURSOR FOR SELECT C.CODCOMPONENT, C.NAMECOMP, C.STOCKCOMPONENT FROM COMPONENT C ORDER BY STOCKCOMPONENT;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET Fin = TRUE;
    
    OPEN C;
		SET FIN = FALSE;
        FETCH C INTO CodProd, NomProd, StockP;
		WHILE FIN = FALSE DO
			IF (StockP <= 5) THEN
				SELECT CONCAT('Code: ', CodProd,' Product: ', NomProd,' Stock: ', StockP) AS PRODUCTS;
			END IF;
            FETCH C INTO CodProd, NomProd, StockP;
		END WHILE;
    CLOSE C;
    
    OPEN C2;
		SET Fin = FALSE;
		FETCH C2 INTO CodComp, NomComp, StockC;
		WHILE FIN = FALSE DO
			IF (StockC <= 5) THEN
				SELECT CONCAT('Code: ', CodComp,' Component: ', NomComp,' Stock: ', StockC) AS COMPONENTS;
			END IF;
            FETCH C2 INTO CodComp, NomComp, StockC;
		END WHILE;
    CLOSE C2;
END //
Delimiter ;
CALL showLowStock();

Delimiter //
CREATE FUNCTION getPrice(Nom VARCHAR(50)) RETURNS DOUBLE
READS SQL DATA
BEGIN
	DECLARE CPrice DOUBLE DEFAULT 0;
    
    SELECT PRICECOMP INTO CPrice FROM COMPONENT WHERE NAMECOMP = Nom;
    RETURN CPrice;
END //

DELIMITER //
CREATE FUNCTION sellAndSubstract(CODUSER VARCHAR(20), NAMEPROD VARCHAR(50), QUANTITY INT, PRICE DOUBLE, PROD BOOLEAN) RETURNS VARCHAR(255)
READS SQL DATA
BEGIN
    DECLARE CURRENTSTOCK INT DEFAULT 0;
    DECLARE STOCKCHECK INT DEFAULT 0;
    DECLARE CODPROD INT;
    DECLARE CODCOMP INT;
    DECLARE CURRENTDATE DATE;
    DECLARE ERROR BOOLEAN DEFAULT FALSE;
    DECLARE MESSAGE VARCHAR(255) DEFAULT NULL;
    DECLARE TOTALPRICE DOUBLE DEFAULT 0;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET ERROR = TRUE;
    
	SET TOTALPRICE = PRICE * QUANTITY;
    IF PROD THEN
        SET CODPROD := (SELECT CODPRODUCT FROM PRODUCT WHERE TRIM(NAMEP) = TRIM(NAMEPROD) LIMIT 1);

        IF CODPROD IS NULL THEN
            SET MESSAGE = "ERROR: Product not found.";
            SET ERROR = TRUE;
        END IF;

        IF ERROR = FALSE THEN
            SET STOCKCHECK := (SELECT STOCKPRODUCT FROM PRODUCT WHERE CODPRODUCT = CODPROD);
            SET CURRENTSTOCK := STOCKCHECK - QUANTITY;
            
            -- Verificar si hay suficiente stock
            IF CURRENTSTOCK < 0 THEN
                SET MESSAGE = "ERROR: Not enough stock available.";
                SET ERROR = TRUE;
            END IF;
        END IF;

        -- Si no hay error, actualizar stock e insertar compra
        IF ERROR = FALSE THEN
            UPDATE PRODUCT SET STOCKPRODUCT = CURRENTSTOCK WHERE CODPRODUCT = CODPROD;
            SET CURRENTDATE = CURDATE();
            INSERT INTO PURCHASE VALUES (CODPROD, CODUSER, QUANTITY, TOTALPRICE, CURRENTDATE);
            SET MESSAGE = "Purchase successful, stock updated.";
        END IF;
    ELSE
        -- Manejo de componentes
        SET CODCOMP := (SELECT CODCOMPONENT FROM COMPONENT WHERE TRIM(NAMECOMP) = TRIM(NAMEPROD) LIMIT 1);
        
        -- Si el componente no existe, devolver error
        IF CODCOMP IS NULL THEN
            SET MESSAGE = "ERROR: Component not found.";
            SET ERROR = TRUE;
        END IF;

        -- Obtener stock actual
        IF ERROR = FALSE THEN
            SET STOCKCHECK := (SELECT STOCKCOMPONENT FROM COMPONENT WHERE CODCOMPONENT = CODCOMP);
            SET CURRENTSTOCK := STOCKCHECK - QUANTITY;
            
            -- Verificar si hay suficiente stock
            IF CURRENTSTOCK < 0 THEN
                SET MESSAGE = "ERROR: Not enough component stock.";
                SET ERROR = TRUE;
            END IF;
        END IF;

        -- Si no hay error, actualizar stock
        IF ERROR = FALSE THEN
            UPDATE COMPONENT SET STOCKCOMPONENT = CURRENTSTOCK WHERE CODCOMPONENT = CODCOMP;
            SET MESSAGE = "Component purchase successful, stock updated.";
        END IF;
    END IF;
    
    RETURN MESSAGE;
END //
DELIMITER ;
SELECT sellAndSubstract('Jago128','Iphone X',1,550,TRUE) AS PRODUCT;
DROP FUNCTION sellAndSubstract;
SELECT * FROM PURCHASE;

Delimiter //
CREATE PROCEDURE showProdsAndCompsOfAParticularBrand(brandName VARCHAR(15))
BEGIN
    DECLARE fin BOOLEAN DEFAULT FALSE;
    DECLARE prodName VARCHAR(50);
    DECLARE prodType ENUM('Mobile', 'Computer');
    DECLARE prodPrice DOUBLE;
    DECLARE prodStock INT;
    
    DECLARE compName VARCHAR(50);
    DECLARE compType ENUM('Graphics', 'RAM', 'Processor');
    DECLARE compPrice DOUBLE;
    DECLARE compStock INT;

    DECLARE cur_prod CURSOR FOR
        SELECT P.NAMEP, P.TYPEP, P.PRICE, P.STOCKPRODUCT
        FROM PRODUCT P
        JOIN BRAND B ON P.CODBRAND = B.CODBRAND
        WHERE B.NAMEBRAND = brandName;
    DECLARE cur_comp CURSOR FOR
        SELECT C.NAMECOMP, C.TYPEC, C.PRICECOMP, C.STOCKCOMPONENT
        FROM COMPONENT C
        JOIN BRAND B ON C.CODBRAND = B.CODBRAND
        WHERE B.NAMEBRAND = brandName;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = TRUE;
    OPEN cur_prod;
    FETCH cur_prod INTO prodName, prodType, prodPrice, prodStock;
    WHILE NOT fin DO
        SELECT CONCAT('Brand: ', brandName, ' | Product: ', prodName, ' | Type: ', prodType, ' | Price: ', prodPrice, ' | Stock: ', prodStock) AS Product_Info;
        FETCH cur_prod INTO prodName, prodType, prodPrice, prodStock;
    END WHILE;
    CLOSE cur_prod;
	SET fin = FALSE;

    OPEN cur_comp;
    FETCH cur_comp INTO compName, compType, compPrice, compStock;
    WHILE NOT fin DO
        SELECT CONCAT('Brand: ', brandName, ' | Component: ', compName, ' | Type: ', compType, ' | Price: ', compPrice, ' | Stock: ', compStock) AS Component_Info;
        FETCH cur_comp INTO compName, compType, compPrice, compStock;
    END WHILE;
    CLOSE cur_comp;
END //
Delimiter ;

Delimiter //
CREATE PROCEDURE ShowLowStock()  
BEGIN
    DECLARE Fin BOOLEAN DEFAULT FALSE;
    DECLARE CodProd INT;
    DECLARE NomProd VARCHAR(50);
    DECLARE StockP INT;
    DECLARE CodComp INT;
    DECLARE NomComp VARCHAR(50);
    DECLARE StockC INT;
    DECLARE C CURSOR FOR (SELECT P.CODPRODUCT, P.NAMEP, P.STOCKPRODUCT FROM PRODUCT P ORDER BY STOCKPRODUCT);
    DECLARE C2 CURSOR FOR SELECT C.CODCOMPONENT, C.NAMECOMP, C.STOCKCOMPONENT FROM COMPONENT C ORDER BY STOCKCOMPONENT;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET Fin = TRUE;
    
    OPEN C;
		SET FIN = FALSE;
        FETCH C INTO CodProd, NomProd, StockP;
		WHILE FIN = FALSE DO
			IF (StockP <= 5) THEN
				SELECT CONCAT('Code: ', CodProd,' Product: ', NomProd,' Stock: ', StockP);
			END IF;
            FETCH C INTO CodProd, NomProd, StockP;
		END WHILE;
    CLOSE C;
    
    OPEN C2;
		SET Fin = FALSE;
		FETCH C2 INTO CodComp, NomComp, StockC;
		WHILE FIN = FALSE DO
			IF (StockC <= 5) THEN
				SELECT CONCAT('Code: ', CodComp,' Component: ', NomComp,' Stock: ', StockC);
			END IF;
            FETCH C2 INTO CodComp, NomComp, StockC;
		END WHILE;
    CLOSE C2;
END //
Delimiter ;

Delimiter //
CREATE PROCEDURE deleteProduct(NomProd VARCHAR(50))
BEGIN
	DECLARE CodigoProd INT;
    DECLARE ENCONTRADO BOOLEAN DEFAULT TRUE;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET ENCONTRADO = FALSE;
    
	SELECT CODPRODUCT INTO CodigoProd FROM PRODUCT WHERE NAMEP = NomProd;

    IF NOT ENCONTRADO THEN
		SELECT CONCAT('The product ', NomProd,' could not be found.') AS DELETE_PRODUCT;
    ELSE 
		SELECT CONCAT('The product ', NomProd,' has been deleted correctly.') AS DELETE_PRODUCT;
		DELETE FROM PRODUCT WHERE CODPRODUCT = CodigoProd;
    END IF;        
END //
Delimiter ;
CALL deleteProduct('Iphone X');


Delimiter //
CREATE PROCEDURE showProducts()
BEGIN
	DECLARE Fin BOOLEAN DEFAULT FALSE;
    DECLARE NomProd VARCHAR(50);
    DECLARE TipoP ENUM ("Mobile","Computer");
    DECLARE PriceP DOUBLE;
    DECLARE StockP INT;
    DECLARE CodBrand INT;
    DECLARE C CURSOR FOR SELECT NAMEP,TYPEP,PRICE,STOCKPRODUCT,CODBRAND FROM PRODUCT;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET FIN = TRUE;
    
    OPEN C;
	FETCH C INTO NomProd,TipoP,PriceP,StockP,CodBrand; 
	WHILE NOT FIN DO
		SELECT CONCAT ('Name: ', NomProd, ' Type: ', TipoP,' Price: ', PriceP,' Stock: ', StockP,' CodeBrand: ', CodBrand) "Datos pedidos"; 
		Fetch c into NomProd,TipoP,PriceP,StockP,CodBrand; 
    END WHILE; 
    CLOSE C; 
END //
Delimiter ;

Delimiter //
CREATE PROCEDURE showComponents()
BEGIN
	DECLARE Fin BOOLEAN DEFAULT FALSE;
    DECLARE NomComp VARCHAR(50);
    DECLARE TipoC ENUM ("Mobile","Computer");
    DECLARE CBrand INT;
    DECLARE Stock INT;
    DECLARE PriceComp DOUBLE;
    DECLARE C CURSOR FOR SELECT NAMECOMP,TYPEC,CODBRAND,STOCKCOMPONENT,PRICECOMP FROM COMPONENT;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET FIN = TRUE;
    
    OPEN C;
	FETCH C INTO NomComp,TipoC,CBrand,Stock,PriceComp; 
	WHILE NOT FIN DO
		SELECT CONCAT ('Name: ', NomComp, ' Type: ', TipoC,' CodeBrand: ', CBrand,' Stock: ', Stock,' Price: ', PriceComp)  "Datos pedidos";
		FETCH C INTO NomComp,TipoC,CBrand,Stock,PriceComp; 
    END WHILE; 
    CLOSE C; 
END //
Delimiter ;

Delimiter //
CREATE PROCEDURE ShowProdsAndCompsOfAParticularBrand(brandName VARCHAR(15))
BEGIN
    DECLARE fin BOOLEAN DEFAULT FALSE;
    DECLARE prodName VARCHAR(50);
    DECLARE prodType ENUM('Mobile', 'Computer');
    DECLARE prodPrice DOUBLE;
    DECLARE prodStock INT;
    
    DECLARE compName VARCHAR(50);
    DECLARE compType ENUM('Graphics', 'RAM', 'Processor');
    DECLARE compPrice DOUBLE;
    DECLARE compStock INT;

    DECLARE cur_prod CURSOR FOR
        SELECT P.NAMEP, P.TYPEP, P.PRICE, P.STOCKPRODUCT
        FROM PRODUCT P
        JOIN BRAND B ON P.CODBRAND = B.CODBRAND
        WHERE B.NAMEBRAND = brandName;
    DECLARE cur_comp CURSOR FOR
        SELECT C.NAMECOMP, C.TYPEC, C.PRICECOMP, C.STOCKCOMPONENT
        FROM COMPONENT C
        JOIN BRAND B ON C.CODBRAND = B.CODBRAND
        WHERE B.NAMEBRAND = brandName;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = TRUE;
    OPEN cur_prod;
    FETCH cur_prod INTO prodName, prodType, prodPrice, prodStock;
    WHILE NOT fin DO
        SELECT CONCAT('Brand: ', brandName, ' | Product: ', prodName, ' | Type: ', prodType, ' | Price: ', prodPrice, ' | Stock: ', prodStock) AS Product_Info;
        FETCH cur_prod INTO prodName, prodType, prodPrice, prodStock;
    END WHILE;
    CLOSE cur_prod;
	SET fin = FALSE;

    OPEN cur_comp;
    FETCH cur_comp INTO compName, compType, compPrice, compStock;
    WHILE NOT fin DO
        SELECT CONCAT('Brand: ', brandName, ' | Component: ', compName, ' | Type: ', compType, ' | Price: ', compPrice, ' | Stock: ', compStock) AS Component_Info;
        FETCH cur_comp INTO compName, compType, compPrice, compStock;
    END WHILE;
    CLOSE cur_comp;
END //
Delimiter ;

Delimiter //
CREATE FUNCTION getStockOfAProduct(NomProd VARCHAR(50))
RETURNS INT
DETERMINISTIC
BEGIN 
	DECLARE PStock INT DEFAULT 0;
    DECLARE NomProd VARCHAR(50);
    
    SELECT STOCKPRODUCT INTO PStock FROM PRODUCT WHERE NAMEP = NomProd;
    RETURN PStock;
END //
Delimiter ;

Delimiter //
CREATE FUNCTION getPrice(Nom VARCHAR(50)) RETURNS DOUBLE
BEGIN
	DECLARE CPrice DOUBLE DEFAULT 0;
    
    SELECT PRICECOMP INTO CPrice FROM COMPONENT WHERE NAMECOMP = Nom;
    RETURN CPrice;
END //

Delimiter //
CREATE FUNCTION GetTotalValueOfAProduct (NomProd VARCHAR(50))
RETURNS INT
DETERMINISTIC
BEGIN 
	DECLARE TotalValue INT DEFAULT 0;
    
    SELECT STOCKPRODUCT * PRICE INTO TotalValue FROM PRODUCT WHERE NAMEP = NomProd;
    RETURN TotalValue;
END //
Delimiter ;