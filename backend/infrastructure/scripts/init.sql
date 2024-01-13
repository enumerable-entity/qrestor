-- Postgres user initialization script fro each microservice

create user qresolver with encrypted password 'qresolver';
create schema if not exists qresolver;
grant all privileges on schema qresolver to qresolver;
