create table vnf(
id varchar(255) not null primary key,
createdat timestamp not null,
modifiedat timestamp,
name varchar(45),
username varchar(255),
constraint fkvnf_username foreign key (username) references users(username)
);

