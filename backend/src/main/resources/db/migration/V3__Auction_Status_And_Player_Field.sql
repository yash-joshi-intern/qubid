-- =============================================================================
-- V3: AUCTION STATUS TYPE CONVERSION + AUCTION_PLAYERS NEW FIELDS
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1. auctions.status: SMALLINT → VARCHAR(255)
--    Required because @Enumerated(EnumType.STRING) on AuctionStatus enum
-- -----------------------------------------------------------------------------
ALTER TABLE auctions
DROP COLUMN status;

ALTER TABLE auctions
    ADD COLUMN status VARCHAR(255) NOT NULL DEFAULT 'NOT_STARTED';

-- -----------------------------------------------------------------------------
-- 2. auction_players: Add status, sold_to_franchise_id, final_sold_price
--    Required by AuctionPlayer entity fields
-- -----------------------------------------------------------------------------
ALTER TABLE auction_players
    ADD COLUMN status VARCHAR(255) NOT NULL DEFAULT 'PENDING';

ALTER TABLE auction_players
    ADD COLUMN sold_to_franchise_id BIGINT;

ALTER TABLE auction_players
    ADD COLUMN final_sold_price DECIMAL;

-- -----------------------------------------------------------------------------
-- 3. FK: auction_players.sold_to_franchise_id → franchises(id)
--    NOTE: references 'franchises' (new table from V2), NOT old 'franchiese'
-- -----------------------------------------------------------------------------
ALTER TABLE auction_players
    ADD CONSTRAINT FK_AUCTION_PLAYERS_ON_SOLD_TO_FRANCHISE
        FOREIGN KEY (sold_to_franchise_id) REFERENCES franchises (id);