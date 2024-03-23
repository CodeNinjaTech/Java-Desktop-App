DROP DATABASE IF EXISTS parking;
CREATE DATABASE parking;
USE parking;
DROP TABLE IF EXISTS cars;
CREATE TABLE `parking`.`cars` (
  `idcars` INT NOT NULL AUTO_INCREMENT,
  `plate` VARCHAR(45) NOT NULL,
  `arrival` DATETIME NOT NULL,
  `departure` DATETIME NULL,
  `cost` FLOAT NULL,
  PRIMARY KEY (`idcars`));

DROP TRIGGER IF EXISTS `parking`.`cars_BEFORE_UPDATE`;
DELIMITER $$
CREATE DEFINER = CURRENT_USER TRIGGER `parking`.`cars_BEFORE_UPDATE` BEFORE UPDATE ON `cars` FOR EACH ROW
BEGIN
	IF new.departure < old.arrival THEN
		SET new.departure = NULL;
        SET new.cost = NULL;
	ELSEIF new.departure > old.arrival + INTERVAL 24 HOUR THEN
		SET new.departure = old.arrival + INTERVAL 24 HOUR;
		SET new.cost = parking.cost_function(old.arrival, new.departure);
	ELSE
		SET new.cost = parking.cost_function(old.arrival, new.departure);
	END IF;
END $$
DELIMITER ;

DROP FUNCTION IF EXISTS parking.cost_function;
DELIMITER $$
CREATE FUNCTION parking.cost_function(narrival DATETIME, ndeparture DATETIME)
RETURNS FLOAT
DETERMINISTIC
BEGIN
	DECLARE ncost DECIMAL(3,1);
    DECLARE hours INT;
    DECLARE diff TIME;
    SET diff = TIMEDIFF(ndeparture, narrival);
    IF ((MINUTE(diff) = 0) AND (SECOND(diff) = 0)) THEN
		SET hours = HOUR(diff);
	ELSE 
		SET hours = HOUR(diff) + 1;
	END IF;
    IF (diff <= '00:00:00') THEN
		SET hours = 0;
	END IF;
	SET ncost = 0.5*(hours + 1);
	IF (ncost<=2) THEN
		SET ncost=2;
	END IF;
	IF (ncost>10) THEN
		SET ncost=10;
	END IF;
	RETURN ncost;
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS `parking`.`cars_BEFORE_INSERT`;
DELIMITER $$
CREATE DEFINER = CURRENT_USER TRIGGER `parking`.`cars_BEFORE_INSERT` BEFORE INSERT ON `cars` FOR EACH ROW
BEGIN
IF ((SELECT COUNT(*) FROM parking.cars WHERE departure IS NULL) = 50) THEN
	INSERT INTO cars(idcars,plate,arrival) VALUES (NULL, NULL, NULL);
END IF;
END $$
DELIMITER ;
