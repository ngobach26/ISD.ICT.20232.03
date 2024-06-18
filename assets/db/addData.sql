INSERT INTO USER (name, email, address, phone, user_type, password, status)
VALUES
('Alice Johnson', 'alice@example.com', '123 Maple Street', '555-1234', 1, 'password1', 1),
('Bob Smith', 'bob@example.com', '456 Oak Avenue', '555-5678', 2, 'password2', 1),
('Carol Williams', 'carol@example.com', '789 Pine Road', '555-8765', 1, 'password3', 1),
('David Brown', 'david@example.com', '101 Birch Lane', '555-4321', 2, 'password4', 0),
('Eve Davis', 'eve@example.com', '202 Cedar Street', '555-6789', 1, 'password5', 1),
('Frank Miller', 'frank@example.com', '303 Spruce Drive', '555-9876', 2, 'password6', 0),
('Grace Wilson', 'grace@example.com', '404 Redwood Road', '555-6543', 1, 'password7', 1),
('Hank Moore', 'hank@example.com', '505 Elm Street', '555-3210', 2, 'password8', 0),
('Ivy Taylor', 'ivy@example.com', '606 Maple Avenue', '555-2109', 1, 'password9', 1),
('Jack Anderson', 'jack@example.com', '707 Pine Street', '555-0198', 2, 'password10', 0);

INSERT INTO MEDIA (type, category, price, quantity, title, value, ImageUrl, support_for_rush_delivery)
VALUES
('Book', 'Fiction', 19.99, 10, 'The Great Gatsby', 'A classic novel', 'url1', 1),
('Book', 'Non-Fiction', 24.99, 15, 'Sapiens', 'A brief history of humankind', 'url2', 0),
('DVD', 'Movie', 14.99, 20, 'Inception', 'A mind-bending thriller', 'url3', 1),
('CD', 'Music', 9.99, 25, 'Thriller', 'Michael Jackson album', 'url4', 0),
('LP_RECORD', 'Vinyl', 29.99, 5, 'Abbey Road', 'The Beatles album', 'url5', 1),
('Book', 'Fiction', 18.99, 8, '1984', 'Dystopian novel', 'url6', 0),
('DVD', 'Series', 39.99, 12, 'Game of Thrones Season 1', 'Epic fantasy series', 'url7', 1),
('CD', 'Music', 11.99, 18, 'Back in Black', 'AC/DC album', 'url8', 0),
('LP_RECORD', 'Vinyl', 25.99, 7, 'The Wall', 'Pink Floyd album', 'url9', 1),
('Book', 'Non-Fiction', 22.99, 10, 'Educated', 'A memoir by Tara Westover', 'url10', 0);

INSERT INTO BOOKS (id, author, coverType, publisher, publishDate, numOfPages, language, bookCategory)
VALUES
(1, 'F. Scott Fitzgerald', 'Hardcover', 'Scribner', '1925-04-10', 180, 'English', 'Classic'),
(2, 'Yuval Noah Harari', 'Paperback', 'Harper', '2011-09-04', 464, 'English', 'History'),
(6, 'George Orwell', 'Hardcover', 'Secker & Warburg', '1949-06-08', 328, 'English', 'Dystopian'),
(10, 'Tara Westover', 'Paperback', 'Random House', '2018-02-20', 334, 'English', 'Memoir' );

INSERT INTO DVD (id, discType, director, runtime, studio, subtitle, language, releasedDate, filmType)
VALUES
(3, 'Blu-ray', 'Christopher Nolan', 148, 'Warner Bros.', 'English', 'English', '2010-07-16', 'Thriller'),
(7, 'DVD', 'Peter Jackson', 178, 'New Line Cinema', 'English', 'English', '2001-12-19', 'Fantasy');

INSERT INTO CD (id, artist, recordLabel, musicType, releasedDate)
VALUES
(4, 'Michael Jackson', 'Epic', 'Pop', '1982-11-30'),
(8, 'AC/DC', 'Atlantic', 'Rock', '1980-07-25');

INSERT INTO LP_RECORD (id, artist, recordLabel, genre, releasedDate)
VALUES
(5, 'The Beatles', 'Apple', 'Rock', '1969-09-26'),
(9, 'Pink Floyd', 'Harvest', 'Rock', '1979-11-30');

INSERT INTO CART (cartID, userID)
VALUES
(1,1), (2,2), (3,3), (4,4), (5,5), (6,6), (7,7), (8,8);

INSERT INTO CART_MEDIA (cartID, mediaID, number_of_products)
VALUES
(1, 1, 2), (2, 2, 1), (3, 3, 4), (4, 4, 3), (5, 5, 2), (6, 6, 1), (7, 7, 5), (8, 8, 2);

INSERT INTO DELIVERY_INFORMATION (deliveryID, userID, province_city, delivery_address, recipient_name, email, phone_number, support_for_rush_delivery)
VALUES
(1, 1, 'City A', 'Address 1', 'Alice Johnson', 'alice@example.com', '555-1234', 1),
(2, 2, 'City B', 'Address 2', 'Bob Smith', 'bob@example.com', '555-5678', 0),
(3, 3, 'City C', 'Address 3', 'Carol Williams', 'carol@example.com', '555-8765', 1),
(4, 4, 'City D', 'Address 4', 'David Brown', 'david@example.com', '555-4321', 0),
(5, 5, 'City E', 'Address 5', 'Eve Davis', 'eve@example.com', '555-6789', 1),
(6, 6, 'City F', 'Address 6', 'Frank Miller', 'frank@example.com', '555-9876', 0),
(7, 7, 'City G', 'Address 7', 'Grace Wilson', 'grace@example.com', '555-6543', 1),
(8, 8, 'City H', 'Address 8', 'Hank Moore', 'hank@example.com', '555-3210', 0),
(9, 9, 'City I', 'Address 9', 'Ivy Taylor', 'ivy@example.com', '555-2109', 1),
(10, 10, 'City J', 'Address 10', 'Jack Anderson', 'jack@example.com', '555-0198', 0);

INSERT INTO 'ORDER' (orderID, total, total_shipping_fee, deliveryID)
VALUES
(1, 39.98, 5.99, 1),
(2, 24.99, 3.99, 2),
(3, 59.96, 4.99, 3),
(4, 29.97, 2.99, 4),
(5, 59.98, 6.99, 5),
(6, 18.99, 4.99, 6),
(7, 199.95, 5.99, 7),
(8, 23.98, 3.99, 8);

INSERT INTO RUSH_DELIVERY (rush_shipping_fee, delivery_time, delivery_instructions, orderID)
VALUES
(10.99, '2024-06-13 10:00:00', 'Leave at front door', 1),
(10.99, '2024-06-13 11:00:00', 'Leave with neighbor', 3),
(10.99, '2024-06-13 12:00:00', 'Call on arrival', 5),
(10.99, '2024-06-13 13:00:00', 'Ring doorbell', 7);

INSERT INTO 'PAYMENT_TRANSACTION' (transactionID, time, date, transaction_content, orderID)
VALUES
(1, '12:00:00', '2024-06-13', 'Purchase of books', 1),
(2, '13:00:00', '2024-06-13', 'Purchase of DVD', 2),
(3, '14:00:00', '2024-06-13', 'Purchase of CD', 3),
(4, '15:00:00', '2024-06-13', 'Purchase of LP_RECORD', 4),
(5, '16:00:00', '2024-06-13', 'Purchase of books', 5),
(6, '17:00:00', '2024-06-13', 'Purchase of DVD', 6),
(7, '18:00:00', '2024-06-13', 'Purchase of CD', 7),
(8, '19:00:00', '2024-06-13', 'Purchase of LP_RECORD', 8),
(9, '20:00:00', '2024-06-13', 'Purchase of books', 9),
(10, '21:00:00', '2024-06-13', 'Purchase of DVD', 10);

INSERT INTO USER (name, email, address, phone, user_type, password, status)
VALUES
('Katie Thompson', 'katie@example.com', '808 Cedar Road', '555-8888', 1, 'password11', 1);


INSERT INTO USER (name, email, address, phone, user_type, password, status)
VALUES
('Chau', 'exam.ple@gmail.com', '123 Street', '0912345678', 0, '12345678', 1);