
---CREARE TABELE pentru aplicatia FoodDelivery---------
DROP TABLE IF EXISTS OrderStatusTracking, Promotions, ScheduledOrders, PromotionHistory, ClientPointsHistory, Feedback, PaymentHistory, Payments, OrderOutcome, CourierLocation, OrderItems, Orders, PromotionRules, PromotionHistory, ClientPromotionHistory, PopularRestaurants, PopularMenuItems, MenuItems, RestaurantAdmins, Couriers, Clients, Restaurants, Users CASCADE;
-- 1. Tabelul Users
CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_type VARCHAR(20) CHECK (user_type IN ('client', 'courier', 'restaurant_admin')),
    last_login TIMESTAMP
);
-- 2. Tabelul Restaurants
CREATE TABLE Restaurants (
    restaurant_id INT PRIMARY KEY,
    name VARCHAR(255),
    address TEXT,
    phone_number VARCHAR(20),
    category VARCHAR(50),
    rating NUMERIC(2, 1) CHECK (rating BETWEEN 1 AND 5),
    order_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 3. Tabelul Clients
CREATE TABLE Clients (
    client_id INT PRIMARY KEY,
    user_id INT REFERENCES Users(user_id) ON DELETE CASCADE,
    name VARCHAR(255),
    phone_number VARCHAR(20),
    address TEXT,
    loyalty_points INT DEFAULT 0
);
-- 4. Tabelul Couriers
CREATE TABLE Couriers (
    courier_id INT PRIMARY KEY,
    user_id INT REFERENCES Users(user_id) ON DELETE CASCADE,
    name VARCHAR(255),
    phone_number VARCHAR(20),
    vehicle_type VARCHAR(50)
);
-- 5. Tabelul RestaurantAdmins
CREATE TABLE RestaurantAdmins (
    admin_id INT PRIMARY KEY,
    user_id INT REFERENCES Users(user_id) ON DELETE CASCADE,
    restaurant_id INT REFERENCES Restaurants(restaurant_id) ON DELETE CASCADE
);
-- 6. Tabelul MenuItems
CREATE TABLE MenuItems (
    item_id INT PRIMARY KEY,
    restaurant_id INT REFERENCES Restaurants(restaurant_id) ON DELETE CASCADE,
    name VARCHAR(255),
    description TEXT,
    price NUMERIC(10, 2),
    available BOOLEAN DEFAULT TRUE,
    category VARCHAR(50),
    order_count INT DEFAULT 0
);
-- 7. Tabelul Promotions și PromotionRules
CREATE TABLE Promotions (
    promotion_id INT PRIMARY KEY,
    description TEXT,
    expiry_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE PromotionRules (
    rule_id INT PRIMARY KEY,
    promotion_id INT REFERENCES Promotions(promotion_id) ON DELETE CASCADE,
    rule_type VARCHAR(20) CHECK (rule_type IN ('season', 'restaurant', 'menu_item', 'product', 'client', 'order', 'delivery')),
    restaurant_id INT REFERENCES Restaurants(restaurant_id) ON DELETE CASCADE,
    item_id INT REFERENCES MenuItems(item_id) ON DELETE CASCADE,
    min_order_value NUMERIC(10, 2),
    discount_percentage NUMERIC(5, 2) CHECK (discount_percentage BETWEEN 0 AND 100),
    applicable BOOLEAN DEFAULT TRUE
);
-- 8. Tabelul Orders
CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    client_id INT REFERENCES Clients(client_id) ON DELETE CASCADE,
    restaurant_id INT REFERENCES Restaurants(restaurant_id) ON DELETE CASCADE,
    courier_id INT REFERENCES Couriers(courier_id) ON DELETE SET NULL,
    status VARCHAR(20) CHECK (status IN ('placed', 'in_preparation', 'delivered', 'cancelled')),
    total_price NUMERIC(10,2),
    discounted_total_price NUMERIC(10,2),
    scheduled_time TIMESTAMP,
    original_scheduled_time TIMESTAMP,
    pickup_option BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 9. Tabelul OrderItems
CREATE TABLE OrderItems (
    order_item_id INT PRIMARY KEY,
    order_id INT REFERENCES Orders(order_id) ON DELETE CASCADE,
    item_id INT REFERENCES MenuItems(item_id) ON DELETE CASCADE,
    quantity INT CHECK (quantity > 0),
    item_price NUMERIC(10,2)
);

CREATE TABLE PromotionHistory (
    promo_history_id INT PRIMARY KEY,
    promotion_id INT REFERENCES Promotions(promotion_id) ON DELETE CASCADE,
    order_id INT REFERENCES Orders(order_id) ON DELETE SET NULL,
    applied_discount NUMERIC(10, 2),
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 11. Tabelul ClientPromotionHistory
CREATE TABLE ClientPromotionHistory (
    client_promo_history_id INT PRIMARY KEY,
    client_id INT REFERENCES Clients(client_id) ON DELETE CASCADE,
    promotion_id INT REFERENCES Promotions(promotion_id) ON DELETE SET NULL,
    points_used INT DEFAULT 0,
    discount_amount NUMERIC(10, 2),
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 12. Tabelul OrderStatusTracking
CREATE TABLE OrderStatusTracking (
    status_tracking_id INT PRIMARY KEY,
    order_id INT REFERENCES Orders(order_id) ON DELETE CASCADE,
    previous_status VARCHAR(20),
    new_status VARCHAR(20),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed_by VARCHAR(50)
);
-- 13. Tabelul OrderOutcome
CREATE TABLE OrderOutcome (
    outcome_id INT PRIMARY KEY,
    order_id INT REFERENCES Orders(order_id) ON DELETE CASCADE,
    outcome_type VARCHAR(20) CHECK (outcome_type IN ('delivered', 'cancelled', 'undelivered')),
    delivery_rating NUMERIC(2, 1) CHECK (delivery_rating BETWEEN 1 AND 5),
    cancellation_reason VARCHAR(255),
    undelivered_reason VARCHAR(255),
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 14. Tabelul Payments
CREATE TABLE Payments (
    payment_id INT PRIMARY KEY,
    order_id INT REFERENCES Orders(order_id) ON DELETE SET NULL,
    client_id INT REFERENCES Clients(client_id) ON DELETE CASCADE,
    payment_method VARCHAR(20),
    amount NUMERIC(10,2),
    tip_amount NUMERIC(10,2) DEFAULT 0.00,
    status VARCHAR(20) CHECK (status IN ('completed', 'refunded'))
);
-- 15. Tabelul PaymentHistory
CREATE TABLE PaymentHistory (
    payment_history_id INT PRIMARY KEY,
    payment_id INT REFERENCES Payments(payment_id) ON DELETE CASCADE,
    status VARCHAR(20),
    processed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    refund_reason VARCHAR(255)
);
-- 16. Tabelul Feedback
CREATE TABLE Feedback (
    feedback_id INT PRIMARY KEY,
    order_id INT REFERENCES Orders(order_id) ON DELETE CASCADE,
    client_id INT REFERENCES Clients(client_id) ON DELETE CASCADE,
    rating NUMERIC(2, 1) CHECK (rating BETWEEN 1 AND 5),
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 17. Tabelul ClientPointsHistory
CREATE TABLE ClientPointsHistory (
    points_history_id INT PRIMARY KEY,
    client_id INT REFERENCES Clients(client_id) ON DELETE CASCADE,
    points INT,
    transaction_type VARCHAR(20) CHECK (transaction_type IN ('earned', 'redeemed')),
    points_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 18. Tabelele pentru popularitate
CREATE TABLE PopularRestaurants (
    popular_restaurant_id INT PRIMARY KEY,
    restaurant_id INT REFERENCES Restaurants(restaurant_id) ON DELETE CASCADE,
    date DATE,
    order_count INT
);
CREATE TABLE PopularMenuItems (
    popular_menu_item_id INT PRIMARY KEY,
    item_id INT REFERENCES MenuItems(item_id) ON DELETE CASCADE,
    date DATE,
    order_count INT
);
CREATE TABLE OrderCancellationHistory (
    cancellation_id INT PRIMARY KEY,
    order_id INT REFERENCES Orders(order_id) ON DELETE CASCADE,
    cancellation_reason VARCHAR(255),
    cancelled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cancelled_by VARCHAR(50) 
);

--modificari--
ALTER TABLE OrderItems
ADD COLUMN total_item_price NUMERIC(10,2) GENERATED ALWAYS AS (quantity * item_price) STORED;

ALTER TABLE PromotionRules
ADD COLUMN min_items INT CHECK (min_items >= 0),
ADD COLUMN client_loyalty_points_needed INT DEFAULT 0;

ALTER TABLE Feedback ADD COLUMN feedback_type VARCHAR(20) CHECK (feedback_type IN ('order', 'restaurant', 'courier'));

ALTER TABLE Payments ADD CONSTRAINT check_tip_vs_amount CHECK (tip_amount <= amount);

ALTER TABLE PopularRestaurants ADD CONSTRAINT unique_restaurant_date UNIQUE (restaurant_id, date);

ALTER TABLE Clients ADD COLUMN IF NOT EXISTS total_discounts_received NUMERIC(10, 2) DEFAULT 0;

ALTER TABLE Promotions ADD COLUMN promo_type VARCHAR(20) 
CHECK (promo_type IN ('percentage', 'fixed')) DEFAULT 'fixed';

ALTER TABLE Promotions ADD COLUMN discount_percentage NUMERIC(5, 2) CHECK (discount_percentage BETWEEN 0 AND 100);

ALTER TABLE Orders ADD COLUMN promotion_id INT REFERENCES Promotions(promotion_id) ON DELETE SET NULL;
ALTER TABLE Orders ADD COLUMN promotion_applied BOOLEAN DEFAULT FALSE;
ALTER TABLE PopularRestaurants
ADD COLUMN is_current_top BOOLEAN DEFAULT FALSE;

-- Indici suplimentari pentru îmbunătățirea performanței interogărilor
CREATE INDEX idx_order_status ON Orders(status);
CREATE INDEX idx_restaurant_order_count ON Restaurants(order_count);
CREATE INDEX idx_menu_items_order_count ON MenuItems(order_count);
CREATE INDEX idx_promotion_expiry_date ON Promotions(expiry_date);
CREATE UNIQUE INDEX unique_item_date ON PopularMenuItems (item_id, date);


---------------------------------------INSERARI VALORI---------------------------------------

-- 1. Users
INSERT INTO Users (user_id, email, password_hash, user_type)
VALUES
(1, 'client1@example.com', 'hashed_password1', 'client'),
(2, 'courier1@example.com', 'hashed_password2', 'courier'),
(3, 'admin1@example.com', 'hashed_password3', 'restaurant_admin'),
(4, 'client2@example.com', 'hashed_password4', 'client'),
(5, 'client3@example.com', 'hashed_password4', 'client'),
(6, 'client4@example.com', 'hashed_password4', 'client'),
(7, 'client5@example.com', 'hashed_password4', 'client'),
(8, 'client6@example.com', 'hashed_password4', 'client'),
(9, 'courier2@example.com', 'hashed_password5', 'courier');
-- 2. Restaurants
INSERT INTO Restaurants (restaurant_id, name, address, phone_number, category, rating)
VALUES
(1, 'Pizza Palace', 'Str. Unirii 10', '0740123456', 'Italian', 4.5),
(2, 'Burger Bistro', 'Str. Libertății 20', '0740567890', 'Fast Food', 4.2),
(3, 'Sushi Central', 'Str. Păcii 15', '0740987654', 'Japanese', 4.8),
(4, 'Vegan Delights', 'Str. Verde 5', '0740765432', 'Vegan', 4.7),
(5, 'Traditional Taste', 'Str. Națională 25', '0740432109', 'Romanian', 4.3);
-- 3. Clients
INSERT INTO Clients (client_id, user_id, name, phone_number, address)
VALUES
(1, 1, 'Maria Popescu', '0722123456', 'Str. Florilor 12'),
(2, 4, 'Ion Ionescu', '0722234567', 'Str. Lalelelor 34'),
(3, 5, 'Ana Marcu', '0722345678', 'Str. Trandafirilor 56'),
(4, 6, 'George Pavel', '0722456789', 'Str. Viorelelor 78'),
(5, 7, 'Cristina Dima', '0722567890', 'Str. Bujorilor 90'),
(6, 8, 'Cristina Dimac', '0722543290', 'Str. Branduselor 790')
-- 4. Couriers
INSERT INTO Couriers (courier_id, user_id, name, phone_number, vehicle_type)
VALUES
(1, 2, 'Andrei Popa', '0723123456', 'Car'),
(2, 9, 'Radu Georgescu', '0723234567', 'Bike');
-- 5. RestaurantAdmins
INSERT INTO RestaurantAdmins (admin_id, user_id, restaurant_id)
VALUES
(1, 3, 1),
(2, 3, 2),
(3, 3, 3),
(4, 3, 4),
(5, 3, 5);



-------------------------------TRIGGERE ȘI PROCEDURI -----------------------------------------------

--Trigger pentru RESTAURANTE POPULARE

DROP SEQUENCE IF EXISTS popular_restaurant_id_seq CASCADE;
CREATE SEQUENCE IF NOT EXISTS popular_restaurant_id_seq;
ALTER TABLE PopularRestaurants ALTER COLUMN popular_restaurant_id SET DEFAULT nextval('popular_restaurant_id_seq');

DROP FUNCTION IF EXISTS update_popular_restaurants() CASCADE;

CREATE OR REPLACE FUNCTION update_popular_restaurants()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE Restaurants
    SET order_count = COALESCE(order_count, 0) + 1
    WHERE restaurant_id = NEW.restaurant_id;

    INSERT INTO PopularRestaurants (popular_restaurant_id, restaurant_id, date, order_count, is_current_top)
    VALUES (nextval('popular_restaurant_id_seq'), NEW.restaurant_id, CURRENT_DATE, 1, FALSE)
    ON CONFLICT (restaurant_id, date)
    DO UPDATE SET 
        order_count = PopularRestaurants.order_count + 1;

    -- Gestionarea topului de 3 restaurante
    PERFORM manage_top_3_restaurants(date_trunc('month', CURRENT_DATE)::DATE);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS manage_top_3_restaurants(DATE) CASCADE;

-- Funcția care gestionează top 3 restaurante 
CREATE OR REPLACE FUNCTION manage_top_3_restaurants(input_month DATE)
RETURNS VOID AS $$
BEGIN
    -- Marchează restaurantele din top 3 ca active
    UPDATE PopularRestaurants
    SET is_current_top = TRUE
    WHERE restaurant_id IN (
        SELECT restaurant_id
        FROM (
            SELECT 
                restaurant_id,
                RANK() OVER (ORDER BY SUM(order_count) DESC) AS rank
            FROM PopularRestaurants
            WHERE date_trunc('month', date) = input_month
            GROUP BY restaurant_id
        ) AS ranked_restaurants
        WHERE rank <= 3
    )
    AND date_trunc('month', date) = input_month;

    -- Marchează restaurantele care nu mai sunt în top 3 ca inactive
    UPDATE PopularRestaurants
    SET is_current_top = FALSE
    WHERE restaurant_id NOT IN (
        SELECT restaurant_id
        FROM (
            SELECT 
                restaurant_id,
                RANK() OVER (ORDER BY SUM(order_count) DESC) AS rank
            FROM PopularRestaurants
            WHERE date_trunc('month', date) = input_month
            GROUP BY restaurant_id
        ) AS ranked_restaurants
        WHERE rank <= 3
    )
    AND date_trunc('month', date) = input_month;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_update_popular_restaurants ON Orders CASCADE;
CREATE TRIGGER trigger_update_popular_restaurants
AFTER INSERT ON Orders
FOR EACH ROW
EXECUTE FUNCTION update_popular_restaurants();

------------------VERIFICARE TRIGGER--------------

 UPDATE Restaurants SET order_count=0;
 select * from restaurants;

 delete from orders;
 delete from PopularRestaurants;
 
 INSERT INTO Orders (order_id, client_id, restaurant_id, total_price, discounted_total_price, status)
VALUES
(1, 1, 1, 50.00, 45.00, 'placed'),
(2, 2, 2, 30.00, 27.00, 'placed'),
(3, 3, 3, 60.00, 50.00, 'placed'),
(4, 4, 4, 40.00, 35.00, 'placed'),
(5, 5, 5, 70.00, 68.00, 'placed');

SELECT * FROM PopularRestaurants;
select * from restaurants;

INSERT INTO Orders (order_id, client_id, restaurant_id, total_price, discounted_total_price, status)
VALUES
(11, 1,5, 50.00, 45.00, 'placed'),
(6, 1, 1, 50.00, 45.00, 'placed'),
(7, 2, 1, 30.00, 27.00, 'placed'),
(8, 3, 2, 60.00, 50.00, 'placed'),
(9, 4, 2, 40.00, 35.00, 'placed'),
(10, 5, 2, 70.00, 68.00, 'placed');

SELECT * FROM PopularRestaurants;
select * from restaurants;

SELECT restaurant_id, name, order_count FROM Restaurants;


---trigger pentru POPULAR MENU ITEMS------------------------------------
DROP FUNCTION IF EXISTS update_popular_menu_items CASCADE;
DROP FUNCTION IF EXISTS maintain_top_3_menu_items CASCADE;
DROP TRIGGER IF EXISTS trigger_update_popular_menu_items ON OrderItems;
DROP SEQUENCE IF EXISTS popular_menu_item_id_seq CASCADE;

CREATE SEQUENCE IF NOT EXISTS popular_menu_item_id_seq
START WITH 1
INCREMENT BY 1;

ALTER TABLE PopularMenuItems
ALTER COLUMN popular_menu_item_id SET DEFAULT nextval('popular_menu_item_id_seq');

ALTER TABLE PopularMenuItems
ADD COLUMN is_current_top BOOLEAN DEFAULT FALSE;

CREATE OR REPLACE FUNCTION maintain_top_3_menu_items(input_date DATE)
RETURNS VOID AS $$
BEGIN
    -- Marchează produsele din top 3 ca fiind active
    UPDATE PopularMenuItems
    SET is_current_top = TRUE
    WHERE item_id IN (
        SELECT item_id
        FROM (
            SELECT 
                item_id,
                SUM(order_count) AS total_orders,
                RANK() OVER (ORDER BY SUM(order_count) DESC) AS rank
            FROM PopularMenuItems
            WHERE date_trunc('month', date) = date_trunc('month', input_date)
            GROUP BY item_id
        ) AS RankedItems
        WHERE rank <= 3
    )
    AND date_trunc('month', date) = date_trunc('month', input_date);

    -- Marchează toate celelalte produse ca inactive
    UPDATE PopularMenuItems
    SET is_current_top = FALSE
    WHERE item_id NOT IN (
        SELECT item_id
        FROM (
            SELECT 
                item_id,
                SUM(order_count) AS total_orders,
                RANK() OVER (ORDER BY SUM(order_count) DESC) AS rank
            FROM PopularMenuItems
            WHERE date_trunc('month', date) = date_trunc('month', input_date)
            GROUP BY item_id
        ) AS RankedItems
        WHERE rank <= 3
    )
    AND date_trunc('month', date) = date_trunc('month', input_date);
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION update_popular_menu_items()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE MenuItems
    SET 
        order_count = COALESCE(order_count, 0) + NEW.quantity
    WHERE item_id = NEW.item_id;

    INSERT INTO PopularMenuItems (popular_menu_item_id, item_id, date, order_count, is_current_top)
    VALUES (nextval('popular_menu_item_id_seq'), NEW.item_id, CURRENT_DATE, NEW.quantity, FALSE)
    ON CONFLICT (item_id, date)
    DO UPDATE SET 
        order_count = PopularMenuItems.order_count + EXCLUDED.order_count;

    PERFORM maintain_top_3_menu_items(CURRENT_DATE);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_update_popular_menu_items ON OrderItems;
CREATE TRIGGER trigger_update_popular_menu_items
AFTER INSERT ON OrderItems
FOR EACH ROW
EXECUTE FUNCTION update_popular_menu_items();

---------------VALIDARE------------

 UPDATE MenuItems SET order_count=0;
 
delete from menuItems;

INSERT INTO MenuItems (item_id, restaurant_id, name, description, price, category)
VALUES
(1, 1, 'Margherita Pizza', 'Classic Italian pizza', 25.00, 'Pizza'),
(2, 2, 'Classic Burger', 'Juicy beef patty', 15.00, 'Burger'),
(3, 3, 'Salmon Roll', 'Fresh salmon sushi', 30.00, 'Sushi'),
(4, 4, 'Vegan Salad', 'Healthy and fresh', 20.00, 'Salad'),
(5, 5, 'Sarmale', 'Traditional Romanian dish', 22.00, 'Traditional');


delete from orderItems;
delete from popularmenuitems;

INSERT INTO OrderItems (order_item_id, order_id, item_id, quantity, item_price)
VALUES
(1, 1, 1, 7, 25.00),
(2, 2, 2, 1, 15.00),
(3, 3, 3, 3, 30.00),
(4, 4, 4, 2, 20.00),
(5, 5, 5, 9, 22.00);

SELECT * FROM PopularMenuItems;
select * from menuitems;

INSERT INTO OrderItems (order_item_id, order_id, item_id, quantity, item_price)
VALUES
(10, 4, 3, 2, 25.00)

SELECT * FROM PopularMenuItems;
select * from menuitems;
SELECT 
    pmi.date,
    mi.name AS product_name,
    pmi.order_count
FROM 
    PopularMenuItems pmi
JOIN 
    MenuItems mi ON pmi.item_id = mi.item_id
ORDER BY 
    pmi.date DESC, pmi.order_count;

select * from menuitems;
select * from orderitems;
select * from popularmenuitems;


--------Trigger pentru Programări Suprapuse-----------------------
---------Interzice programările care se suprapun pentru același restaurant 
DROP TRIGGER IF EXISTS trigger_validate_no_overlapping_schedule ON Orders;
DROP FUNCTION IF EXISTS validate_no_overlapping_schedule();

CREATE OR REPLACE FUNCTION validate_no_overlapping_schedule()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM Orders
        WHERE restaurant_id = NEW.restaurant_id
          AND scheduled_time BETWEEN NEW.scheduled_time - INTERVAL '30 minutes'
                                AND NEW.scheduled_time + INTERVAL '30 minutes'
          AND NEW.order_id IS DISTINCT FROM order_id 
    ) THEN
        RAISE EXCEPTION 'The scheduled time conflicts with another order.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_validate_no_overlapping_schedule
BEFORE INSERT OR UPDATE ON Orders
FOR EACH ROW
EXECUTE FUNCTION validate_no_overlapping_schedule();

-------verificare trigger-----
DELETE FROM Orders;
select * from orders

INSERT INTO Orders (order_id, client_id, restaurant_id, total_price, discounted_total_price, status, scheduled_time)
VALUES 
    (1, 2, 1, 25.00, 20.00, 'placed', '2024-12-03 14:00:00');

-- Testăm o comandă care se suprapune cu prima (în interval de 30 minute)
DO $$
BEGIN
    BEGIN
        INSERT INTO Orders (order_id, client_id, restaurant_id, total_price, discounted_total_price, status, scheduled_time)
        VALUES 
            (2, 1, 1, 30.00, 25.00, 'placed', '2024-12-03 14:15:00');
    EXCEPTION WHEN OTHERS THEN
        RAISE NOTICE 'Error caught: %', SQLERRM;
    END;
END $$;

-- Testăm o comandă care nu se suprapune (după 30 minute)
INSERT INTO Orders (order_id, client_id, restaurant_id, total_price, discounted_total_price, status, scheduled_time)
VALUES 
    (3, 4, 1, 50.00, 45.00, 'placed', '2024-12-03 14:45:00');

-- Testăm o comandă care se suprapune cu comanda adăugată anterior
DO $$
BEGIN
    BEGIN
        INSERT INTO Orders (order_id, client_id, restaurant_id, total_price, discounted_total_price, status, scheduled_time)
        VALUES 
            (4, 5 , 1, 20.00, 18.00, 'placed', '2024-12-03 14:30:00');
    EXCEPTION WHEN OTHERS THEN
        RAISE NOTICE 'Error caught: %', SQLERRM;
    END;
END $$;

-- Verificăm comenzile acceptate
SELECT * FROM Orders;


---------trigger pentru punctele de loialitate-------------------
--------------Validare pentru puncte de loialitate si inregistrare in istoricul contului clientului

CREATE SEQUENCE clientpointshistory_points_history_id_seq
START WITH 1
INCREMENT BY 1
OWNED BY ClientPointsHistory.points_history_id;

ALTER TABLE ClientPointsHistory
ALTER COLUMN points_history_id SET DEFAULT nextval('clientpointshistory_points_history_id_seq');


CREATE OR REPLACE FUNCTION handle_loyalty_points(
    p_client_id INT,
    p_points INT,
    p_transaction_type VARCHAR(20) 
) RETURNS VOID AS $$
BEGIN
    IF p_transaction_type NOT IN ('earned', 'redeemed') THEN
        RAISE EXCEPTION 'Invalid transaction type: %', p_transaction_type;
    END IF;

    -- Tranzacțiile cu puncte zero nu sunt procesate 
    IF p_points = 0 THEN
        RETURN; 
    END IF;

    -- Validare pentru tranzacții "redeemed"
    IF p_transaction_type = 'redeemed' THEN
        IF p_points > 0 THEN
            RAISE EXCEPTION 'Points for redeemed transactions must be negative.';
        END IF;
        IF ABS(p_points) > (
            SELECT loyalty_points
            FROM Clients
            WHERE client_id = p_client_id
        ) THEN
            RAISE EXCEPTION 'Insufficient loyalty points to complete the transaction.';
        END IF;
    END IF;

    UPDATE Clients
    SET loyalty_points = loyalty_points + p_points
    WHERE client_id = p_client_id;

    INSERT INTO ClientPointsHistory (
        client_id,
        points,
        transaction_type
    ) VALUES (
        p_client_id,
        p_points,
        p_transaction_type
    );
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION reward_points_on_order()
RETURNS TRIGGER AS $$
DECLARE
    earned_points INT;
BEGIN
-- Calculăm punctele câștigate (10% din prețul total)
-- vrem ca la fiecare comanda un client sa primeasca puncte de loialitate
	
    earned_points := CEIL(NEW.total_price * 0.1);
	
    IF earned_points > 0 THEN
        PERFORM handle_loyalty_points(
            NEW.client_id,
            earned_points,
            'earned'
        );
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_reward_points_on_order
AFTER INSERT ON Orders
FOR EACH ROW
WHEN (NEW.status = 'delivered') 
EXECUTE FUNCTION reward_points_on_order();

CREATE OR REPLACE FUNCTION handle_points_on_promotion()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.points_used > 0 THEN
        PERFORM handle_loyalty_points(
            NEW.client_id,
            -NEW.points_used,
            'redeemed'
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_handle_points_on_promotion
BEFORE INSERT ON ClientPromotionHistory
FOR EACH ROW
EXECUTE FUNCTION handle_points_on_promotion();

----------------verificare triggere------------
DELETE FROM clientpointshistory;
delete from orders;
select * from users
select * from promotions 

delete from clients where client_id=8
INSERT INTO Clients (client_id, user_id, name, phone_number, address, loyalty_points)
VALUES
(8, 1, 'Maria Popescu', '0722123456', 'Str. Florilor 12', 80);
DO $$
BEGIN
    BEGIN
        INSERT INTO ClientPromotionHistory (client_promo_history_id, client_id, promotion_id, points_used, discount_amount)
        VALUES 
            (1, 8, 4, 90, 10.00); -- Se încearcă folosirea a 90 de puncte, dar clientul are doar 80
    EXCEPTION WHEN OTHERS THEN
        RAISE NOTICE 'Error caught: %', SQLERRM; -- Mesaj așteptat: Insufficient loyalty points to use this promotion.
    END;
END $$;

--clientul castiga puncte de loialitate
SELECT handle_loyalty_points(8, 50, 'earned');

SELECT * FROM Clients WHERE client_id = 8;
SELECT * FROM ClientPointsHistory WHERE client_id = 8;

---clientul foloseste puncte de loialitate
SELECT handle_loyalty_points(8, -30, 'redeemed');

SELECT * FROM Clients WHERE client_id = 8;
SELECT * FROM ClientPointsHistory WHERE client_id = 8;

---clientul plaseaza o comanda si va castiga puncte de loialitate pe baza acesteia
INSERT INTO Orders (order_id, client_id, restaurant_id, total_price, status)
VALUES (1, 8, 2, 100, 'delivered');

SELECT * FROM Clients WHERE client_id = 8;
SELECT * FROM ClientPointsHistory WHERE client_id = 8;

------trigger pentru Gestionarea istoricului statusurilor comenzilor 
------De fiecare dată când statusul unei comenzi se schimbă, creează o înregistrare în tabelul OrderStatusTracking.

DELETE FROM OrderStatusTracking;
DELETE FROM OrderItems;
delete from orders;

CREATE SEQUENCE status_tracking_id_seq;
 
CREATE OR REPLACE FUNCTION log_order_status_change()
RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM OrderStatusTracking
    WHERE order_id = OLD.order_id;

    INSERT INTO OrderStatusTracking (status_tracking_id, order_id, previous_status, new_status, changed_at, changed_by)
    VALUES (
        nextval('status_tracking_id_seq'), 
        OLD.order_id, 
        OLD.status, 
        NEW.status, 
        CURRENT_TIMESTAMP, 
        SESSION_USER
    );

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_log_order_status_change
AFTER UPDATE OF status ON Orders
FOR EACH ROW
WHEN (OLD.status IS DISTINCT FROM NEW.status)
EXECUTE FUNCTION log_order_status_change();

INSERT INTO Orders (order_id, client_id, restaurant_id, total_price, discounted_total_price, status)
VALUES
(1, 1, 1, 50.00, 45.00, 'placed')

SELECT * FROM Orders;

UPDATE Orders
SET status = 'in_preparation'
WHERE order_id = 1;

SELECT * FROM OrderStatusTracking;

UPDATE Orders
SET status = 'delivered'
WHERE order_id = 1;

SELECT * FROM OrderStatusTracking;


------PROCEDURA PENTRU Adăugarea unui utilizator nou-----------------------------------------

CREATE OR REPLACE PROCEDURE AddUser(
    p_user_id INT,
    p_email VARCHAR(255),
    p_password_hash VARCHAR(255),
    p_user_type VARCHAR(20),
    p_name VARCHAR(255),
    p_phone_number VARCHAR(20),
    p_address TEXT DEFAULT NULL,
    p_vehicle_type VARCHAR(50) DEFAULT NULL,
    p_restaurant_id INT DEFAULT NULL
)
LANGUAGE plpgsql
AS $$
BEGIN
    
    INSERT INTO Users(user_id, email, password_hash, user_type)
    VALUES (p_user_id, p_email, p_password_hash, p_user_type);

    IF p_user_type = 'client' THEN
        INSERT INTO Clients(client_id, user_id, name, phone_number, address)
        VALUES (p_user_id, p_user_id, p_name, p_phone_number, p_address);
    ELSIF p_user_type = 'courier' THEN
        INSERT INTO Couriers(courier_id, user_id, name, phone_number, vehicle_type)
        VALUES (p_user_id, p_user_id, p_name, p_phone_number, p_vehicle_type);
    ELSIF p_user_type = 'restaurant_admin' THEN
        INSERT INTO RestaurantAdmins(admin_id, user_id, restaurant_id)
        VALUES (p_user_id, p_user_id, p_restaurant_id);
    END IF;
END;
$$;

CALL AddUser(
    10,                  
    'client10@example.com',    
    'hashed_password123',     
    'client',                 
    'John Doe',              
    '0712345678',            
    '123 Main Street',       
    NULL,                     
    NULL                      
);

select * from users;
select * from clients;

CALL AddUser(
    11,                  
    'courier11@example.com',   
    'hashed_password456',    
    'courier',                
    'Jane Smith',             
    '0723456789',             
    NULL,                     
    'car',                    
    NULL                     
);

CALL AddUser(
   12,                      
    'admin11@example.com',         
    'hashed_password789',        
    'restaurant_admin',           
    NULL,                        
    NULL,                        
    NULL,                       
    NULL,                        
    1                 
);

SELECT * FROM Users WHERE user_id IN (10,11,12);
SELECT * FROM Clients WHERE client_id = 10;
SELECT * FROM Couriers WHERE courier_id = 11;
SELECT * FROM RestaurantAdmins WHERE admin_id = 12;


----PROCEDURA PENTRU plasarea unei comenzi----
CREATE SEQUENCE order_item_id_seq START 1;

CREATE OR REPLACE PROCEDURE PlaceOrder(
    p_order_id INT,
    p_client_id INT,
    p_restaurant_id INT,
    p_items JSON,
    p_total_price NUMERIC(10, 2),
    p_discounted_total_price NUMERIC(10, 2) DEFAULT NULL,
    p_pickup_option BOOLEAN DEFAULT FALSE
)
LANGUAGE plpgsql
AS $$
DECLARE
    item RECORD;
BEGIN
    INSERT INTO Orders(order_id, client_id, restaurant_id, total_price, discounted_total_price, pickup_option)
    VALUES (p_order_id, p_client_id, p_restaurant_id, p_total_price, p_discounted_total_price, p_pickup_option);
    FOR item IN SELECT * FROM json_to_recordset(p_items) AS (item_id INT, quantity INT, item_price NUMERIC(10, 2)) LOOP
        INSERT INTO OrderItems(order_item_id, order_id, item_id, quantity, item_price)
        VALUES (nextval('order_item_id_seq'), p_order_id, item.item_id, item.quantity, item.item_price);
    END LOOP;
END;
$$;

select * from menuitems;

delete from menuitems;
delete from orders;

INSERT INTO MenuItems (item_id, restaurant_id, name, description, price, category)
VALUES 
    (1, 1, 'Margherita Pizza', 'Classic margherita pizza with fresh basil', 10.50, 'Pizza'),
    (2, 1, 'Pepperoni Pizza', 'Spicy pepperoni and mozzarella', 12.00, 'Pizza'),
    (3, 1, 'Cheeseburger', 'Juicy beef patty with cheese', 15.00, 'Burgers'),
    (4, 2, 'Double Bacon Burger', 'Two beef patties with bacon', 25.00, 'Burgers'),
    (5, 3, 'Salmon Sushi', 'Fresh salmon with sushi rice', 50.00, 'Sushi');

-------Verificare------
CALL PlaceOrder(
    1, 
    10, 
    1, 
    '[{"item_id": 1, "quantity": 2, "item_price": 10.50}, {"item_id": 2, "quantity": 1, "item_price": 20.00}]', -- p_items (JSON)
    41.00, 
    35.00, 
    FALSE 
);
CALL PlaceOrder(
    2, 
    10, 
    2, 
    '[{"item_id": 3, "quantity": 3, "item_price": 15.00}, {"item_id": 4, "quantity": 2, "item_price": 25.00}]', -- p_items (JSON)
    105.00, 
    90.00, 
    TRUE 
);
CALL PlaceOrder(
    3, 
    10, 
    3, 
    '[{"item_id": 5, "quantity": 1, "item_price": 50.00}]', 
    50.00, 
    NULL, 
    FALSE 
);

SELECT * FROM Orders;
SELECT * FROM OrderItems WHERE order_id IN (1, 2, 3);


----PROCEDURA PENTRU actualizarea statusului unei comenzi----

CREATE SEQUENCE IF NOT EXISTS order_status_tracking_id_seq START 1;

CREATE OR REPLACE PROCEDURE UpdateOrderStatus(
    p_order_id INT,            
    p_new_status VARCHAR(20),
    p_changed_by VARCHAR(50)
)
LANGUAGE plpgsql
AS $$
DECLARE
    current_status VARCHAR(20);
BEGIN
  
    SELECT status INTO current_status
    FROM Orders
    WHERE order_id = p_order_id;

    IF current_status IS NULL THEN
        RAISE EXCEPTION 'Comanda cu ID-ul % nu există.', p_order_id;
    END IF;

    UPDATE Orders
    SET status = p_new_status
    WHERE order_id = p_order_id;

    INSERT INTO OrderStatusTracking(status_tracking_id, order_id, previous_status, new_status, changed_by)
    VALUES (nextval('order_status_tracking_id_seq'), p_order_id, current_status, p_new_status, p_changed_by);
END;
$$;

DELETE FROM OrderStatusTracking WHERE order_id IN (1, 2, 3);
DELETE FROM Orders WHERE order_id IN (1, 2, 3);

INSERT INTO Orders (order_id, client_id, restaurant_id, status, total_price, discounted_total_price, pickup_option)
VALUES 
    (1, 10, 1, 'placed', 33.00, 30.00, FALSE),
    (2, 10, 2, 'in_preparation', 105.00, 90.00, TRUE),
    (3, 10, 3, 'delivered', 50.00, 50.00, FALSE);
	
CALL UpdateOrderStatus(
    1,               
    'in_preparation', 
    'admin_user'      
);

CALL UpdateOrderStatus(
    2,
    'delivered',
    'courier_user'
);

CALL UpdateOrderStatus(
    3,
    'cancelled',
    'client_user'
);

SELECT order_id, status 
FROM Orders 
WHERE order_id IN (1, 2, 3);

SELECT * 
FROM OrderStatusTracking 
WHERE order_id IN (1, 2, 3);
CALL UpdateOrderStatus(999, 'cancelled', 'test_user'); 

---------------- Log pentru promoții care expiră în 24 de ore--------

CREATE OR REPLACE FUNCTION warn_promotion_expiry()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.expiry_date <= NOW() + INTERVAL '1 day' THEN
        RAISE NOTICE 'Promoția % expiră în mai puțin de 24 de ore.', NEW.promotion_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_warn_promotion_expiry
AFTER UPDATE ON Promotions
FOR EACH ROW
EXECUTE FUNCTION warn_promotion_expiry();

------------------------verificare-----------------------------------
delete from promotions;
INSERT INTO Promotions (promotion_id, description, expiry_date, created_at)
VALUES
    (1, 'Discount 20%', NOW() + INTERVAL '23 hours', DEFAULT),
    (2, 'Free shipping', NOW() + INTERVAL '2 days', DEFAULT),
    (3, 'Buy 1 Get 1 Free', NOW() + INTERVAL '1 hour', DEFAULT);

SET client_min_messages TO NOTICE;

UPDATE Promotions
SET expiry_date = NOW() + INTERVAL '20 hours'
WHERE promotion_id = 1;

UPDATE Promotions
SET expiry_date = NOW() + INTERVAL '3 days'
WHERE promotion_id = 2;

UPDATE Promotions
SET expiry_date = NOW() + INTERVAL '30 minutes'
WHERE promotion_id = 3;

SELECT promotion_id, description, expiry_date
FROM Promotions
WHERE promotion_id IN (1, 2, 3);



------- actualizarea valorilor în baza de date ori de câte ori este aplicată o promoție nouă.
CREATE OR REPLACE FUNCTION update_client_discounts_and_points()
RETURNS TRIGGER AS $$
DECLARE
    promo_type_local VARCHAR(20);
    discount_amount NUMERIC(10, 2);
    available_points INT;
BEGIN
    -- Obține tipul promoției
    SELECT p.promo_type
    INTO promo_type_local
    FROM Promotions p
    WHERE p.promotion_id = NEW.promotion_id;

    -- Calculează discountul aplicat
    IF promo_type_local = 'percentage' THEN
        SELECT o.total_price * (NEW.applied_discount / 100)
        INTO discount_amount
        FROM Orders o
        WHERE o.order_id = NEW.order_id;
    ELSE
        discount_amount := NEW.applied_discount;
    END IF;

    IF promo_type_local = 'percentage' OR promo_type_local = 'fixed' THEN
        -- Doar actualizează clientul cu discount-ul aplicat
        UPDATE Clients
        SET 
            total_discounts_received = total_discounts_received + discount_amount
        WHERE client_id = (
            SELECT o.client_id
            FROM Orders o
            WHERE o.order_id = NEW.order_id
        );
        
        -- Logare în istoricul promoțiilor
        INSERT INTO ClientPromotionHistory (client_id, promotion_id, points_used, discount_amount, recorded_at)
        VALUES (
            (SELECT o.client_id FROM Orders o WHERE o.order_id = NEW.order_id),
            NEW.promotion_id,
            0,  -- Nu sunt utilizate puncte
            discount_amount,
            CURRENT_TIMESTAMP
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_update_client_discounts_and_points
AFTER INSERT ON PromotionHistory
FOR EACH ROW
EXECUTE FUNCTION update_client_discounts_and_points();


DELETE FROM orders;
DELETE FROM promotions;
DELETE FROM promotionHistory;
DELETE FROM ClientPromotionHistory;

UPDATE Clients SET loyalty_points = 0 WHERE client_id = 10;
delete from clients
INSERT INTO Clients (client_id, user_id, name, loyalty_points, total_discounts_received) 
VALUES (10, 1, 'Test Client', 0, 0);

INSERT INTO Orders (order_id, client_id, restaurant_id, total_price) 
VALUES (1, 10, 1, 100.00);

INSERT INTO Promotions (promotion_id, description, expiry_date, promo_type) 
VALUES (1, '10% off', '2024-12-31', 'percentage'),
       (2, '20 lei off', '2024-12-31', 'fixed');
	   
INSERT INTO PromotionHistory (promo_history_id, promotion_id, order_id, applied_discount) 
VALUES (1, 1, 1, 10.00);

INSERT INTO PromotionHistory (promo_history_id, promotion_id, order_id, applied_discount) 
VALUES (2, 2, 1, 20.00);

SELECT  total_discounts_received 
FROM Clients 
WHERE client_id = 10;

SELECT * 
FROM ClientPromotionHistory 
WHERE client_id = 10;



