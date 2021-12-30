package database;

public class CreateTableQuery {

	public static String ADMIN = "CREATE TABLE `admin`("
			+ "	`id` int AUTO_INCREMENT PRIMARY KEY,"
			+ "	`password` VARCHAR(20),"
			+ "	`store_name` VARCHAR(100),"
			+ "	`address` VARCHAR(500),"
			+ "	`phone_number` VARCHAR(20),"
			+ "	`email` VARCHAR(100),"
			+ "	`type` VARCHAR(1000)"
			+ "	);";
	public static String CONSTRUCTION = "CREATE TABLE `construction`("
			+ "	`id` int PRIMARY KEY AUTO_INCREMENT,"
			+ "	`name` VARCHAR(50),"
			+ "	`phone_number` VARCHAR(20),"
			+ "	`address` VARCHAR(200)"
			+ "	);";
	public static String DEBT = "CREATE TABLE `debt_customer`("
			+ "	`id` int PRIMARY KEY AUTO_INCREMENT,"
			+ "	`name` VARCHAR(50),"
			+ "	`phone_number` VARCHAR(20),"
			+ "	`address` VARCHAR(200),"
			+ "	`paid` int"
			+ "	);";
	public static String STAFF = "CREATE TABLE `staff`("
			+ "	`cccd` VARCHAR(50) PRIMARY KEY,"
			+ "	`name` VARCHAR(50),"
			+ "	`phone_number` VARCHAR(20),"
			+ "	`address` VARCHAR(200),"
			+ "	`salary_per_hour` int"
			+ "	);";
	public static String SUPPLIER = "CREATE TABLE `supplier`("
			+ "	`id` int PRIMARY KEY AUTO_INCREMENT,"
			+ "	`name` VARCHAR(100),"
			+ "	`phone_number` VARCHAR(20),"
			+ "	`address` VARCHAR(200),"
			+ "	`type` VARCHAR(500)"
			+ "	);";
	public static String PRODUCT = "CREATE TABLE product("
			+ "	`id` varchar(20) PRIMARY KEY,"
			+ "	`product_name` VARCHAR(100),"
			+ "	`quantity` float,"
			+ "	`unit` VARCHAR(20),"
			+ "	`date` VARCHAR(20),"
			+ "	`import_price` int,"
			+ "	`export_price` int,"
			+ "	`supplier_id` int"
			+ "	);";
	public static String FK_PRODUCT_SUPPLIER = "ALTER TABLE `product` ADD FOREIGN KEY (`supplier_id`) REFERENCES `supplier`(`id`);";
	public static String STAFF_HOUR = "CREATE TABLE `staff_hour`("
			+ "	`cccd` VARCHAR(50),"
			+ "	`date` VARCHAR(20),"
			+ "	`hour` int"
			+ "	);";
	public static String FK_STAFF_HOUR_STAFF = "ALTER TABLE `staff_hour` ADD FOREIGN KEY (`cccd`) REFERENCES `staff`(`cccd`);";
	public static String DEBT_BILL = "CREATE TABLE `debt_bill`("
			+ "	`id` int PRIMARY KEY AUTO_INCREMENT,"
			+ "	`debt_id` int,"
			+ "	`product_id` VARCHAR(20),"
			+ "	`quantity` float,"
			+ "	`price` int,"
			+ "	`date` VARCHAR(20)"
			+ "	);";
	public static String FK_DEBT_BILL_DEBT_CUSTOMER = "ALTER TABLE `debt_bill` ADD FOREIGN KEY (`debt_id`) REFERENCES `debt_customer`(`id`);";
	public static String FK_DEBT_BILL_DEBT_PRODUCT = "ALTER TABLE `debt_bill` ADD FOREIGN KEY (`product_id`) REFERENCES `product`(`id`);";
	public static String CONSTRUCTION_BILL = "CREATE TABLE `construction_bill`("
			+ "	`id` int PRIMARY KEY AUTO_INCREMENT,"
			+ "	`construction_id` int,"
			+ "	`product_id` VARCHAR(20),"
			+ "	`quantity` float,"
			+ "	`price` int"
			+ "	);";
	public static String FK_CONSTRUCTION_BILL_CONSTRUCTION = "ALTER TABLE `construction_bill` ADD FOREIGN KEY (`construction_id`) REFERENCES `construction`(`id`);";
	public static String FK_CONSTRUCTION_BILL_PRODUCT = "ALTER TABLE `construction_bill` ADD FOREIGN KEY (`product_id`) REFERENCES `product`(`id`);";
	public static String WARRANTY = "CREATE TABLE `warranty`("
			+ "    	`id` int PRIMARY KEY AUTO_INCREMENT,"
			+ "    	`name` VARCHAR(50),"
			+ "    	`phone_number` VARCHAR(20),"
			+ "    	`product_id` VARCHAR(20),"
			+ "    	`warranty_status` VARCHAR(200),"
			+ "    	`product_status` VARCHAR(500)"
			+ "    	);";
	public static String FK_WARRANTY_PRODUCT = "ALTER TABLE `warranty` ADD FOREIGN KEY (`product_id`) REFERENCES `product`(`id`);";
}
