-- =============================================================================
-- V5: FIX FK CONSTRAINTS FOR SAFE DELETION
-- Tournament, Team, Player delete operations
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1. teams.tournament_id → NULLABLE + ON DELETE SET NULL
--    Reason: Tournament delete Team Survives
--    Current constraint name from V2: FK_TEAMS_ON_TOURNAMENT
-- -----------------------------------------------------------------------------
ALTER TABLE teams
DROP CONSTRAINT FK_TEAMS_ON_TOURNAMENT;

ALTER TABLE teams
    ALTER COLUMN tournament_id DROP NOT NULL;

ALTER TABLE teams
    ADD CONSTRAINT FK_TEAMS_ON_TOURNAMENT
        FOREIGN KEY (tournament_id)
            REFERENCES tournaments (id)
            ON DELETE SET NULL;


-- -----------------------------------------------------------------------------
-- 2. auction_players.player_id → ON DELETE CASCADE
--    Reason: AuctionPlayer cannot exist without Player
--    Current constraint name from V1: FK_AUCTION_PLAYERS_ON_PLAYER
-- -----------------------------------------------------------------------------
ALTER TABLE auction_players
DROP CONSTRAINT FK_AUCTION_PLAYERS_ON_PLAYER;

ALTER TABLE auction_players
    ADD CONSTRAINT FK_AUCTION_PLAYERS_ON_PLAYER
        FOREIGN KEY (player_id)
            REFERENCES players (id)
            ON DELETE CASCADE;


-- -----------------------------------------------------------------------------
-- 3. player_team → ON DELETE CASCADE both sides
--    Player deleted → join rows gone
--    Team deleted   → join rows gone
--    Current constraint names from V2: fk_platea_on_player, fk_platea_on_team
-- -----------------------------------------------------------------------------
ALTER TABLE player_team
DROP CONSTRAINT fk_platea_on_player;

ALTER TABLE player_team
    ADD CONSTRAINT fk_platea_on_player
        FOREIGN KEY (player_id)
            REFERENCES players (id)
            ON DELETE CASCADE;

ALTER TABLE player_team
DROP CONSTRAINT fk_platea_on_team;

ALTER TABLE player_team
    ADD CONSTRAINT fk_platea_on_team
        FOREIGN KEY (team_id)
            REFERENCES teams (id)
            ON DELETE CASCADE;


-- -----------------------------------------------------------------------------
-- 4. player_tournament → ON DELETE CASCADE both sides
--    Player deleted    → join rows gone
--    Tournament deleted → join rows gone
--    Current constraint names from V1: fk_platou_on_player, fk_platou_on_tournament
-- -----------------------------------------------------------------------------
ALTER TABLE player_tournament
DROP CONSTRAINT fk_platou_on_player;

ALTER TABLE player_tournament
    ADD CONSTRAINT fk_platou_on_player
        FOREIGN KEY (player_id)
            REFERENCES players (id)
            ON DELETE CASCADE;

ALTER TABLE player_tournament
DROP CONSTRAINT fk_platou_on_tournament;

ALTER TABLE player_tournament
    ADD CONSTRAINT fk_platou_on_tournament
        FOREIGN KEY (tournament_id)
            REFERENCES tournaments (id)
            ON DELETE CASCADE;


-- -----------------------------------------------------------------------------
-- 5. franchise_tournament → ON DELETE CASCADE both sides
--    Franchise deleted  → join rows gone
--    Tournament deleted → join rows gone
--    Current constraint names from V2: fk_fratou_on_franchise, fk_fratou_on_tournament (V1)
-- -----------------------------------------------------------------------------
ALTER TABLE franchise_tournament
DROP CONSTRAINT fk_fratou_on_franchise;

ALTER TABLE franchise_tournament
    ADD CONSTRAINT fk_fratou_on_franchise
        FOREIGN KEY (franchise_id)
            REFERENCES franchises (id)
            ON DELETE CASCADE;

ALTER TABLE franchise_tournament
DROP CONSTRAINT fk_fratou_on_tournament;

ALTER TABLE franchise_tournament
    ADD CONSTRAINT fk_fratou_on_tournament
        FOREIGN KEY (tournament_id)
            REFERENCES tournaments (id)
            ON DELETE CASCADE;


-- -----------------------------------------------------------------------------
-- 6. base_price.player_id → ON DELETE CASCADE
--    Reason: BasePrice cannot exist without Player
--    Current constraint name from V1: FK_BASE_PRICE_ON_PLAYER
-- -----------------------------------------------------------------------------
ALTER TABLE base_price
DROP CONSTRAINT FK_BASE_PRICE_ON_PLAYER;

ALTER TABLE base_price
    ADD CONSTRAINT FK_BASE_PRICE_ON_PLAYER
        FOREIGN KEY (player_id)
            REFERENCES players (id)
            ON DELETE CASCADE;


-- -----------------------------------------------------------------------------
-- 7. stats.player_id → ON DELETE CASCADE
--    Reason: Stats cannot exist without Player
--    Current constraint name from V2: FK_STATS_ON_PLAYER
-- -----------------------------------------------------------------------------
ALTER TABLE stats
DROP CONSTRAINT FK_STATS_ON_PLAYER;

ALTER TABLE stats
    ADD CONSTRAINT FK_STATS_ON_PLAYER
        FOREIGN KEY (player_id)
            REFERENCES players (id)
            ON DELETE CASCADE;
