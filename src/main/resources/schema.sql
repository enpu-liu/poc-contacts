-- CREATE TABLE "contacts" -------------------------------------
CREATE TABLE `contacts`(
	`id` VarChar( 32 ) NOT NULL,
	`avatar` LongBlob NULL,
	`email` VarChar( 255 ) NOT NULL,
	`first_name` VarChar( 255 ) NOT NULL,
	`last_name` VarChar( 255 ) NOT NULL,
	`phone_no` VarChar( 255 ) NOT NULL,
	`remark` VarChar( 255 ) NULL,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `UK_728mksvqr0n907kujew6p3jc0` UNIQUE( `email` ) );
-- -------------------------------------------------------------
