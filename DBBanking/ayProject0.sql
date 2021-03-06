-- creating a schema for my first project
create schema Ay_project0;



-- creating a bank's customers table
create table bank_customers (
	fname varchar(50),
	lname varchar(50),
	email varchar(25) primary key,
	"password" varchar(25) not null,
	dob varchar(50)
    
);




-- creating a table for the acount informations for our customer
create table account_info (
	id serial primary key,
	balance numeric not null,
	account_type varchar(50),
	deposit numeric ,
	withdrawal numeric,
    open_date varchar(15) not null,
    user_email varchar(25) not null unique
);





-- connecting both table though the forign key 
alter table account_info
add constraint fk_user_email
foreign key(user_email) references bank_customers(email) on delete cascade;

truncate table bank_customers;
truncate table account_info ;




-- adding a  few costumers to the bank_costumers table
insert into bank_customers values
('Charles', 'Jester', 'charles.jester@mail.com', 'superPassword1', '2-29-2000');
insert into bank_customers values
('Ay', 'Sebirka', 'aysebirka@mail.com', 'ultimatPassword', '2-29-2000'),
('Mark', 'Cuban', 'crazymark@mail.com', 'superPasswording', '2-29-2000');

insert into account_info values
(default,  100.00, 'checking',0 , 0, '05-02-2022', 'charles.jester@mail.com'),
(default,  90000.00, 'checking',0 , 0, '05-06-2022', 'aysebirka@mail.com');
--(default, 'savings', 1000.00, '05-02-2022', 'charles.jester@mail.com');

insert into account_info values
(default,  100.00,'checking',20, 35,  '05-02-2022', 'crazymark@mail.com');





-- to be able to drop our tables 

drop table if exists  bank_customers cascade;
drop table if exists  account_info cascade;


-- adding 



select * from bank_customers bc ;
select * from account_info ai ;

-- crating a virtual table
create view customer_account as
select bc.fname, bc.lname, ai.account_type, ai.balance, ai.open_date
from bank_customers bc
join account_info  ai ON bc.email = ai.user_email;

delete  from bank_customers  where fname = 'Mark';

-- we are updating the account balance
update account_info set balance = 100000.00 where user_email = 'crazymark@mail.com';


update account_info  set balance = balance + deposite  where user_email = 'aysebirka@mail.com';


update account_info  set balance = balance - withdrawl where user_email = 'charles.jester@mail.com';

select * from bank_customers bc ;
select * from account_info ai ;


INSERT INTO account (id, balance) VALUES (1, 1000), (2, 250)
UPDATE accounts_info SET balance = balance + 100 where id = 22;

-- showing everything in the our virtual table
select * from customer_account ;
