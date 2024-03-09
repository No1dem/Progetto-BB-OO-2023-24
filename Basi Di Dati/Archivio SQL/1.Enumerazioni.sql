DROP TYPE IF EXISTS EnumStati CASCADE;
DROP TYPE IF EXISTS EnumTipoNotifica CASCADE;

CREATE TYPE EnumStati AS ENUM ('Accettato','In_attesa','Rifiutato');

CREATE TYPE EnumTipoNotifica AS ENUM ('Accesso','Interazione','Post');

