create database qresolver;
create database auth;
create database feedback;
create database importer;
create database kitchenboard;
create database mailer;
create database menu;
create database orders;
create database restaurant;
create database paymentor;


create user qresolver with encrypted password 'qresolver';
create user auth with encrypted password 'auth';
create user feedback with encrypted password 'feedback';
create user importer with encrypted password 'importer';
create user kitchenboard with encrypted password 'kitchenboard';
create user mailer with encrypted password 'mailer';
create user menu with encrypted password 'menu';
create user orders with encrypted password 'orders';
create user restaurant with encrypted password 'restaurant';
create user paymentor with encrypted password 'paymentor';

grant all privileges on database qresolver to qresolver;
grant all privileges on database auth to auth;
grant all privileges on database feedback to feedback;
grant all privileges on database importer to importer;
grant all privileges on database kitchenboard to kitchenboard;
grant all privileges on database mailer to mailer;
grant all privileges on database menu to menu;
grant all privileges on database orders to orders;
grant all privileges on database restaurant to restaurant;
grant all privileges on database paymentor to paymentor;


\connect qresolver qresolver;
create schema qresolver;
grant all privileges on schema qresolver to qresolver;

\connect auth auth;
create schema auth;
grant all privileges on schema auth to auth;

\connect feedback feedback;
create schema feedback;
grant all privileges on schema feedback to feedback;

\connect importer importer;
create schema importer;
grant all privileges on schema importer to importer;

\connect kitchenboard kitchenboard;
create schema kitchenboard;
grant all privileges on schema kitchenboard to kitchenboard;

\connect mailer mailer;
create schema mailer;
grant all privileges on schema mailer to mailer;

\connect menu menu;
create schema menu;
grant all privileges on schema menu to menu;

\connect orders orders;
create schema orders;
grant all privileges on schema orders to orders;

\connect restaurant restaurant;
create schema restaurant;
grant all privileges on schema restaurant to restaurant;

\connect paymentor paymentor;
create schema paymentor;
grant all privileges on schema paymentor to paymentor;
