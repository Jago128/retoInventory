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
CODUSER VARCHAR(20),
PRIMARY KEY (CODPRODUCT,CODUSER),
QUANTITY INT,
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
CREATE PROCEDURE insertProduct (NAMEP VARCHAR(50), TYPEP ENUM ("Mobile","Computer"), PRICE DOUBLE, STOCKPRODUCT INT, CODBRAND INT)
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
CREATE FUNCTION sellAndSubstract(CODUSER VARCHAR(20), NAMEPROD VARCHAR(50), AMOUNT INT) RETURNS BOOLEAN
BEGIN
	DECLARE CURRENTSTOCK INT DEFAULT 0;
    DECLARE CODPROD INT;
    DECLARE CURRENTDATE DATE;
    DECLARE ERRORCHECK BOOLEAN DEFAULT TRUE;
    # Incorrect integer value error check
	DECLARE CONTINUE HANDLER FOR SQLSTATE "01366" SET ERRORCHECK = FALSE;
    SET CODPROD:=(SELECT CODPRODUCT FROM PRODUCT WHERE NAMEP=NAMEPROD);
	SET CURRENTSTOCK:=((SELECT STOCKPRODUCT FROM PRODUCT WHERE CODPRODUCT=CODPROD)-AMOUNT);
	IF !ERRORCHECK THEN
		SELECT "El parametro introducido es incorrecto." AS ErrorMessage;
        ELSE
        UPDATE PRODUCT SET STOCKPRODUCT=CURRENTSTOCK WHERE CODPRODUCT=CODPROD;
    END IF;
    SET CURRENTDATE:=(SELECT CURDATE());
    INSERT INTO PURCHASE VALUES 
	(CODPROD,CODUSER,AMOUNT,CURRENTDATE);
	RETURN ERRORCHECK;
END //
DELIMITER ;

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
CALL ShowLowStock();

Delimiter //
CREATE PROCEDURE deleteProduct(NomProd VARCHAR(50))
BEGIN
	DECLARE CodigoProd INT;
    DECLARE ENCONTRADO BOOLEAN DEFAULT TRUE;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET ENCONTRADO = FALSE;
	SELECT CODPRODUCT INTO CodigoProd FROM PRODUCT WHERE NAMEP = NomProd;

    IF !ENCONTRADO THEN
		SELECT CONCAT('The product ', NomProd,' has not been found.');
    ELSE 
		SELECT CONCAT('The product ', NomProd,' has been deleted correctly.');
		DELETE FROM PRODUCT WHERE CODPRODUCT = CodigoProd;
    END IF;        
END //
Delimiter ;


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
	WHILE !FIN DO
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
    DECLARE CodBrand INT;
    DECLARE Stock INT;
    DECLARE PriceComp DOUBLE;	
    DECLARE C CURSOR FOR SELECT NAMECOMP,TYPEC,CODBRAND,STOCKCOMPONENT,PRICECOMP FROM COMPONENT; 	
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET FIN = TRUE;
    OPEN C;
	FETCH C INTO NomComp,TipoC,CodBrand,Stock,PriceComp; 
	WHILE !FIN DO
		SELECT CONCAT ('Name: ', NomComp, ' Type: ', TipoC,' CodeBrand: ', CodBrand,' Stock: ', Stock,' Price: ', PriceComp) "Datos pedidos"; 
		FETCH C INTO NomComp,TipoC,CodBrand,Stock,PriceComp; 
    END WHILE; 
    CLOSE C; 
END //
Delimiter ;

Delimiter //
CREATE PROCEDURE ShowProdsAndCompsOfAParticularBrand(brandName VARCHAR(15))
BEGIN
    -- Variables para productos
    DECLARE fin BOOLEAN DEFAULT FALSE;
    DECLARE prodName VARCHAR(50);
    DECLARE prodType ENUM('Mobile', 'Computer');
    DECLARE prodPrice DOUBLE;
    DECLARE prodStock INT;

    -- Variables para componentes
    DECLARE compName VARCHAR(50);
    DECLARE compType ENUM('Graphics', 'RAM', 'Processor');
    DECLARE compPrice DOUBLE;
    DECLARE compStock INT;

    -- Cursor para productos de una marca específica
    DECLARE cur_prod CURSOR FOR
        SELECT P.NAMEP, P.TYPEP, P.PRICE, P.STOCKPRODUCT
        FROM PRODUCT P
        JOIN BRAND B ON P.CODBRAND = B.CODBRAND
        WHERE B.NAMEBRAND = brandName;

    -- Cursor para componentes de una marca específica
    DECLARE cur_comp CURSOR FOR
        SELECT C.NAMECOMP, C.TYPEC, C.PRICECOMP, C.STOCKCOMPONENT
        FROM COMPONENT C
        JOIN BRAND B ON C.CODBRAND = B.CODBRAND
        WHERE B.NAMEBRAND = brandName;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = TRUE;

    
    -- Mostrar productos
    OPEN cur_prod;
    FETCH cur_prod INTO prodName, prodType, prodPrice, prodStock;
    WHILE !fin DO
        SELECT CONCAT('Brand: ', brandName, ' | Product: ', prodName, ' | Type: ', prodType,
                      ' | Price: ', prodPrice, ' | Stock: ', prodStock) AS Product_Info;
        FETCH cur_prod INTO prodName, prodType, prodPrice, prodStock;
    END WHILE;
    CLOSE cur_prod;

	SET fin = FALSE;

    -- Mostrar componentes
    OPEN cur_comp;
    FETCH cur_comp INTO compName, compType, compPrice, compStock;
    WHILE !fin DO
        SELECT CONCAT('Brand: ', brandName, ' | Component: ', compName, ' | Type: ', compType,
                      ' | Price: ', compPrice, ' | Stock: ', compStock) AS Component_Info;
        FETCH cur_comp INTO compName, compType, compPrice, compStock;
    END WHILE;
    CLOSE cur_comp;
END //
Delimiter ;
