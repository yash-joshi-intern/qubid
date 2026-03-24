ALTER TABLE stats
    ADD CONSTRAINT uk_stats_player_format
        UNIQUE (player_id, cricket_format);