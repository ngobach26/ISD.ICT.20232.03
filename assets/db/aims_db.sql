-- Table: User
CREATE TABLE "USER"(
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "name" VARCHAR(45) NOT NULL,
    "email" VARCHAR(45) NOT NULL,
    "address" VARCHAR(45) NOT NULL,
    "phone" VARCHAR(45) NOT NULL,
    "user_type" INTEGER NOT NULL,
    "password" VARCHAR(20) NOT NULL,
    "status" INT NOT NULL
);
-- Media-related tables
CREATE TABLE "MEDIA"(
   "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
   "type" VARCHAR(45) NOT NULL,
   "category" VARCHAR(45) NOT NULL,
   "price" INTEGER NOT NULL,
   "quantity" INTEGER NOT NULL,
   "title" VARCHAR(45) NOT NULL,
   "value" INTEGER NOT NULL,
   "imageUrl" VARCHAR(45) NOT NULL,
   "support_for_rush_delivery" INTEGER NOT NULL
);

CREATE TABLE "CD"(
    "id" INTEGER PRIMARY KEY NOT NULL,
    "artist" VARCHAR(45) NOT NULL,
    "recordLabel" VARCHAR(45) NOT NULL,
    "musicType" VARCHAR(45) NOT NULL,
    "releasedDate" DATE,
    CONSTRAINT "fk_cd_media" FOREIGN KEY("id") REFERENCES "MEDIA"("id")
);

CREATE TABLE "BOOKS"(
    "id" INTEGER PRIMARY KEY NOT NULL,
    "author" VARCHAR(45) NOT NULL,
    "coverType" VARCHAR(45) NOT NULL,
    "publisher" VARCHAR(45) NOT NULL,
    "publishDate" DATETIME NOT NULL,
    "numOfPages" INTEGER NOT NULL,
    "language" VARCHAR(45) NOT NULL,
    "bookCategory" VARCHAR(45) NOT NULL,
    CONSTRAINT "fk_book_media" FOREIGN KEY("id") REFERENCES "MEDIA"("id")
);

CREATE TABLE "DVD"(
    "id" INTEGER PRIMARY KEY NOT NULL,
    "discType" VARCHAR(45) NOT NULL,
    "director" VARCHAR(45) NOT NULL,
    "runtime" INTEGER NOT NULL,
    "studio" VARCHAR(45) NOT NULL,
    "subtitle" VARCHAR(45) NOT NULL,
    "language" VARCHAR(20),
    "releasedDate" DATETIME,
    "filmType" VARCHAR(45) NOT NULL,
    CONSTRAINT "fk_dvd_media" FOREIGN KEY("id") REFERENCES "MEDIA"("id")
);
CREATE TABLE "LP_RECORD"(
    "id" INTEGER PRIMARY KEY NOT NULL,
    "artist" VARCHAR(45) NOT NULL,
    "recordLabel" VARCHAR(45) NOT NULL,
    "genre" VARCHAR(45) NOT NULL,
    "releasedDate" DATE,
    CONSTRAINT "fk_lp_media" FOREIGN KEY("id") REFERENCES "MEDIA"("id")
);

-- Table: Cart
CREATE TABLE CART (
    cartID INT NOT NULL PRIMARY KEY,
    userID INT NOT NULL,
    CONSTRAINT "fk_cart_user" FOREIGN KEY("userID") REFERENCES "USER"("id")
);

-- Table: Cart_Media
CREATE TABLE "CART_MEDIA" (
    cartID INTEGER NOT NULL,
    mediaID INTEGER NOT NULL,
    number_of_products INTEGER,
    CONSTRAINT "fk_cart_cartmedia" FOREIGN KEY ("cartID") REFERENCES "CART"("cartID"),
    CONSTRAINT "fk_media_cartmedia" FOREIGN KEY ("mediaID") REFERENCES "MEDIA"("ID")
);


-- Table: Delivery_information
CREATE TABLE "DELIVERY_INFORMATION" (
    deliveryID INTEGER NOT NULL PRIMARY KEY,
    userID INTEGER NOT NULL,
    province_city VARCHAR(255),
    delivery_address VARCHAR(255),
    recipient_name VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(20),
    support_for_rush_delivery INTEGER,
    CONSTRAINT "fk_delivery_user" FOREIGN KEY ("userID") REFERENCES USER("id")
);

-- Table: Order
CREATE TABLE "ORDER" (
    orderID INTEGER NOT NULL PRIMARY KEY,
    total DECIMAL(10, 2),
    total_shipping_fee DECIMAL(10,2),
    deliveryID INTEGER NOT NULL,
    FOREIGN KEY (deliveryID) REFERENCES Delivery_information(deliveryID)
);

-- Table: Order_Media
CREATE TABLE "ORDER_MEDIA" (
    orderID INTEGER NOT NULL,
    mediaID INTEGER NOT NULL,
    number_of_products INTEGER,
    CONSTRAINT "fk_order_cartmedia" FOREIGN KEY ("orderID") REFERENCES "ORDER"("orderID"),
    CONSTRAINT "fk_media_cartmedia" FOREIGN KEY ("mediaID") REFERENCES "MEDIA"("ID")
);

-- Table: Rush_delivery
CREATE TABLE RUSH_DELIVERY (
    rush_shipping_fee DECIMAL(10, 2),
    delivery_time DATETIME,
    delivery_instructions VARCHAR(255),
    orderID INT NOT NULL,
    FOREIGN KEY (orderID) REFERENCES "ORDER"(orderID)
);

-- Table: Transaction
CREATE TABLE [TRANSACTION] (
    transactionID INT NOT NULL PRIMARY KEY,
    time TIME,
    date DATE,
    transaction_content TEXT,
    orderID INT NOT NULL,
    CONSTRAINT "fk_order_transaction" FOREIGN KEY ("orderID") REFERENCES "ORDER"(orderID)
);
