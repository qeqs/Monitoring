create table vnf(
id varchar(255) not null primary key,
createdat timestamp not null,
modifiedat timestamp,
name varchar(45),
username varchar(255)
);
create table network(
id varchar(255) not null primary key,
createdat timestamp not null,
modifiedat timestamp,
name varchar(45),
id_openstack varchar(100),
project_id varchar(100), 
top_position varchar(255),
left_position varchar(255),
username varchar(255),
vnf_id varchar(255),
constraint fkcreate_network_id_vnf foreign key (vnf_id) references vnf(id) on delete cascade on update cascade
);

create table router(
id varchar(255) not null primary key,
createdat timestamp not null,
modifiedat timestamp,
name varchar(45),
id_openstack varchar(100),
project_id varchar(100),
top_position varchar(255),
left_position varchar(255),
id_network varchar(255),
username varchar(255),
vnf_id varchar(255),
constraint fkrouter_id_network foreign key (id_network) references network(id) on delete cascade on update cascade,
constraint fkcreate_router_id_vnf foreign key (vnf_id) references vnf(id) on delete cascade on update cascade
);

create table subnet(
id varchar(255) not null primary key,
createdat timestamp not null,
modifiedat timestamp,
name varchar(45),
id_openstack varchar(100),
ip_version varchar(10),
cidr varchar(45),
id_network varchar(255) unique,
username varchar(255),
vnf_id varchar(255),
constraint fksubnet_id_network foreign key (id_network) references network(id) on delete cascade on update cascade,
constraint fkcreate_subnet_id_vnf foreign key (vnf_id) references vnf(id) on delete cascade on update cascade
);

create table router_interface(
id varchar(255) not null primary key,
createdat timestamp not null,
modifiedat timestamp,
name varchar(45),
id_openstack varchar(100),
project_id varchar(100),
id_subnet varchar(255),
id_router varchar(255),
username varchar(255),
constraint fkrouter_interface_id_subnet foreign key (id_subnet) references subnet(id) on delete cascade on update cascade,
constraint fkrouter_interface_id_router foreign key (id_router) references router(id) on delete cascade on update cascade
);

create table instance(
id varchar(255) not null primary key,
createdat timestamp not null,
modifiedat timestamp,
name varchar(45),
id_openstack varchar(100),
project_id varchar(100),
ip varchar(45),
date date,
top_position varchar(255),
left_position varchar(255),
id_flavor varchar(255),
id_image varchar(255),
username varchar(255),
vnf_id varchar(255),
constraint fkcreate_instance_id_vnf foreign key (vnf_id) references vnf(id)  on delete cascade on update cascade
);

