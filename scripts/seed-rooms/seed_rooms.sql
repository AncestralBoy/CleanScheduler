-- Popola la tabella clean_scheduler_schema.room con le stanze previste da RoomType.
-- Idempotente: inserisce una stanza solo se non esiste gia' una riga con lo stesso nome.

INSERT INTO clean_scheduler_schema.room (name, score, is_assigned_this_week)
SELECT v.name, v.score, false
FROM (VALUES
    ('Bagno 1', 1),
    ('Bagno 2', 1),
    ('Corridoio', 1),
    ('Balcone', 1),
    ('Cucina', 1)
) AS v(name, score)
WHERE NOT EXISTS (
    SELECT 1 FROM clean_scheduler_schema.room r WHERE r.name = v.name
);
