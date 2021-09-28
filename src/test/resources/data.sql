DROP table if exists transactions;
create table transactions
(
    id               varchar(255)   not null
        primary key,
    account_id       varchar(255)   not null,
    amount           decimal(64, 30) not null,
    currency         varchar(255)   not null,
    description      varchar(255)   not null,
    transaction_type varchar(6)   not null
);

insert into transactions(id, account_id, currency, amount, description, transaction_type)  values ('f1fb5da5-5418-4ac7',
                                                                                                   'd4fb5ta7-8028-5cf8', 'GBP', 234.95, 'Tesco Holborn Station', 'CREDIT');

insert into transactions(id, account_id, currency, amount, description, transaction_type)  values ('f1fb5da5-5418-4ac7qa',
                                                                                                   'd4fb5ta7-8028-5cf8Az', 'GBP', 234.95123, 'Tesco Holborn Station', 'DEBIT');