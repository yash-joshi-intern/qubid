-- =============================================================================
-- V4: INITIAL DATA SEED (POST STRUCTURE REFACTOR)
-- =============================================================================

-- NOTE: Order is VERY IMPORTANT because of FK constraints
-- Applies after: V1 (base schema) + V2 (refactor) + V3 (status fix + new fields)

-- -----------------------------------------------------------------------------
-- 1. SKILLS
-- -----------------------------------------------------------------------------
INSERT INTO skills (created_at, updated_at, name, rating, years_of_exp, expertise_level)
VALUES (NOW(), NULL, 'Batting', 9, 10, 'EXPERT'),
       (NOW(), NULL, 'Bowling', 8, 8, 'EXPERT'),
       (NOW(), NULL, 'Fielding', 7, 6, 'INTERMEDIATE'),
       (NOW(), NULL, 'Wicket Keeping', 9, 7, 'EXPERT'),
       (NOW(), NULL, 'All Rounder', 8, 9, 'EXPERT');


-- -----------------------------------------------------------------------------
-- 2. PLAYERS
-- -----------------------------------------------------------------------------
INSERT INTO players (created_at, updated_at, first_name, last_name, dob, country, phone, email, address)
VALUES (NOW(), NULL, 'Rohit', 'Sharma', '1987-04-30', 'India', '9000000001', 'rohit@qubid.com', 'Mumbai'),
       (NOW(), NULL, 'Virat', 'Kohli', '1988-11-05', 'India', '9000000002', 'virat@qubid.com', 'Delhi'),
       (NOW(), NULL, 'Jasprit', 'Bumrah', '1993-12-06', 'India', '9000000003', 'bumrah@qubid.com', 'Ahmedabad'),
       (NOW(), NULL, 'MS', 'Dhoni', '1981-07-07', 'India', '9000000004', 'dhoni@qubid.com', 'Ranchi'),
       (NOW(), NULL, 'Hardik', 'Pandya', '1993-10-11', 'India', '9000000005', 'hardik@qubid.com', 'Surat'),
       (NOW(), NULL, 'KL', 'Rahul', '1992-04-18', 'India', '9000000006', 'klrahul@qubid.com', 'Bengaluru'),
       (NOW(), NULL, 'Ravindra', 'Jadeja', '1988-12-06', 'India', '9000000007', 'jadeja@qubid.com', 'Jamnagar'),
       (NOW(), NULL, 'Shubman', 'Gill', '1999-09-08', 'India', '9000000008', 'gill@qubid.com', 'Fazilka'),
       (NOW(), NULL, 'Pat', 'Cummins', '1993-05-08', 'Australia', '9000000009', 'cummins@qubid.com', 'Sydney'),
       (NOW(), NULL, 'Jos', 'Buttler', '1990-09-08', 'England', '9000000010', 'buttler@qubid.com', 'Taunton');


-- -----------------------------------------------------------------------------
-- 3. STATS
-- -----------------------------------------------------------------------------
INSERT INTO stats (created_at, updated_at, cricket_format, matches_played,
                   runs_scored, balls_played,
                   wickets_taken, balls_bowled, runs_conceded,
                   highest_level, player_id)
VALUES (NOW(), NULL, 'T20', 243, 9100, 6800, 2, 60, 80, 'INTERNATIONAL', 1),
       (NOW(), NULL, 'T20', 120, 4000, 3000, 1, 30, 40, 'INTERNATIONAL', 2),
       (NOW(), NULL, 'T20', 90, 150, 220, 130, 1950, 2200, 'INTERNATIONAL', 3),
       (NOW(), NULL, 'T20', 350, 5000, 4200, 20, 480, 580, 'INTERNATIONAL', 4),
       (NOW(), NULL, 'T20', 110, 2500, 1800, 55, 900, 1050, 'INTERNATIONAL', 5),
       (NOW(), NULL, 'T20', 100, 3200, 2500, 5, 120, 150, 'INTERNATIONAL', 6),
       (NOW(), NULL, 'T20', 220, 2800, 2200, 120, 2100, 2400, 'INTERNATIONAL', 7),
       (NOW(), NULL, 'T20', 60, 2200, 1600, 3, 60, 75, 'INTERNATIONAL', 8),
       (NOW(), NULL, 'T20', 80, 400, 520, 110, 1800, 1950, 'INTERNATIONAL', 9),
       (NOW(), NULL, 'T20', 180, 4800, 3200, 5, 90, 110, 'INTERNATIONAL', 10);


-- -----------------------------------------------------------------------------
-- 4. PLAYER <-> SKILL
-- -----------------------------------------------------------------------------
INSERT INTO player_skill (player_id, skill_id)
VALUES (1, 1),
       (1, 3),
       (2, 1),
       (2, 3),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 4),
       (5, 5),
       (5, 3),
       (6, 1),
       (6, 4),
       (7, 5),
       (7, 2),
       (8, 1),
       (8, 3),
       (9, 2),
       (9, 3),
       (10, 1),
       (10, 4);


-- -----------------------------------------------------------------------------
-- 5. TOURNAMENT
-- -----------------------------------------------------------------------------
INSERT INTO tournaments (created_at, updated_at, name, location, start_date, end_date, alloted_purse)
VALUES (NOW(), NULL, 'IPL 2025', 'India', '2025-03-22', '2025-05-25', 1000000000.00);


-- -----------------------------------------------------------------------------
-- 6. FRANCHISES
--    Uses 'franchises' table (V2 renamed from 'franchiese')
-- -----------------------------------------------------------------------------
INSERT INTO franchises (created_at, updated_at, name, city, country, phone, email, address)
VALUES (NOW(), NULL, 'Mumbai Indians', 'Mumbai', 'India', '9100000001', 'mi@ipl.com', 'Wankhede Stadium'),
       (NOW(), NULL, 'Chennai Super Kings', 'Chennai', 'India', '9100000002', 'csk@ipl.com', 'Chepauk Stadium'),
       (NOW(), NULL, 'Royal Challengers', 'Bengaluru', 'India', '9100000003', 'rcb@ipl.com', 'Chinnaswamy Stadium'),
       (NOW(), NULL, 'Kolkata Knight Riders', 'Kolkata', 'India', '9100000004', 'kkr@ipl.com', 'Eden Gardens');


-- -----------------------------------------------------------------------------
-- 7. TEAMS
--    Uses V2's new 'teams' table (franchise_id + tournament_id FK)
-- -----------------------------------------------------------------------------
INSERT INTO teams (created_at, updated_at, name,
                   min_players, max_players, remaining_purse,
                   franchise_id, tournament_id,
                   phone, email, address)
VALUES (NOW(), NULL, 'MI Squad 2025', 11, 25, 0, 1, 1, '9200000001', 'misquad@ipl.com', 'Mumbai'),
       (NOW(), NULL, 'CSK Squad 2025', 11, 25, 0, 2, 1, '9200000002', 'csksquad@ipl.com', 'Chennai'),
       (NOW(), NULL, 'RCB Squad 2025', 11, 25, 0, 3, 1, '9200000003', 'rcbsquad@ipl.com', 'Bengaluru'),
       (NOW(), NULL, 'KKR Squad 2025', 11, 25, 0, 4, 1, '9200000004', 'kkrsquad@ipl.com', 'Kolkata');


-- -----------------------------------------------------------------------------
-- 8. FRANCHISE <-> TOURNAMENT
-- -----------------------------------------------------------------------------
INSERT INTO franchise_tournament (franchise_id, tournament_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1);


-- -----------------------------------------------------------------------------
-- 9. PLAYER <-> TOURNAMENT
-- -----------------------------------------------------------------------------
INSERT INTO player_tournament (player_id, tournament_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1);


-- -----------------------------------------------------------------------------
-- 10. BASE PRICE
-- -----------------------------------------------------------------------------
INSERT INTO base_price (created_at, updated_at, player_id, tournament_id, base_price)
VALUES (NOW(), NULL, 1, 1, 20000000),
       (NOW(), NULL, 2, 1, 20000000),
       (NOW(), NULL, 3, 1, 20000000),
       (NOW(), NULL, 4, 1, 20000000),
       (NOW(), NULL, 5, 1, 15000000),
       (NOW(), NULL, 6, 1, 15000000),
       (NOW(), NULL, 7, 1, 15000000),
       (NOW(), NULL, 8, 1, 10000000),
       (NOW(), NULL, 9, 1, 20000000),
       (NOW(), NULL, 10, 1, 15000000);


-- -----------------------------------------------------------------------------
-- 11. AUCTION
--    status = VARCHAR (V3 converted from SMALLINT)
--    tournament_id = unique FK (added in V2)
-- -----------------------------------------------------------------------------
INSERT INTO auctions (created_at, updated_at, venue, status, event_date, title, tournament_id)
VALUES (NOW(), NULL, 'Narendra Modi Stadium, Ahmedabad', 'NOT_STARTED', '2025-01-15', 'IPL 2025 Player Auction', 1);


-- -----------------------------------------------------------------------------
-- 12. AUCTION PLAYERS
--    Now includes: status, sold_to_franchise_id, final_sold_price (V3 added)
-- -----------------------------------------------------------------------------
INSERT INTO auction_players (created_at, updated_at,
                             player_id, base_price_id, auction_id,
                             status, sold_to_franchise_id, final_sold_price)
VALUES (NOW(), NULL, 1, 1, 1, 'PENDING', NULL, NULL),
       (NOW(), NULL, 2, 2, 1, 'PENDING', NULL, NULL),
       (NOW(), NULL, 3, 3, 1, 'PENDING', NULL, NULL),
       (NOW(), NULL, 4, 4, 1, 'PENDING', NULL, NULL),
       (NOW(), NULL, 5, 5, 1, 'PENDING', NULL, NULL),
       (NOW(), NULL, 6, 6, 1, 'PENDING', NULL, NULL),
       (NOW(), NULL, 7, 7, 1, 'PENDING', NULL, NULL),
       (NOW(), NULL, 8, 8, 1, 'PENDING', NULL, NULL),
       (NOW(), NULL, 9, 9, 1, 'PENDING', NULL, NULL),
       (NOW(), NULL, 10, 10, 1, 'PENDING', NULL, NULL);