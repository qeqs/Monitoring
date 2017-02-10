CREATE TABLE Meters
(
id_meters character(40),
type character(40),
unit character(40),
description character(40),
constraint PK_Meters primary key (id_meters)
);

CREATE TABLE Measure
(
id_Measure character(40),
value float,
tstamp timestamp,
id_meter character(40),
user_id character(40),
resource character(40),
constraint PK_Measure primary key (id_Measure),
constraint FK_Meters foreign key (id_meter) references Meters(id_meters)
);

CREATE TABLE Event
(
id_event character(40),
name character(40),
action int,
priority character(40),
description character(40),
constraint PK_Event primary key (id_event)
);


CREATE TABLE Policy_List
(
id_policyList character(40),
enabled boolean,
name character(40),
uid character(40),
constraint PK_Policy_List primary key (id_policyList)
);

CREATE TABLE Policy
(
id_policy character(40),
id_meter character(40),
id_event character(40),
users character(40),
resource character(40),
treshhold float,
sign character(40),
enabled boolean,
id_policyList character(40),
groups character(40),
constraint PK_Policy primary key (id_policy),
constraint FK_Meters foreign key (id_meter) references Meters(id_meters),
constraint FK_Event foreign key (id_event) references Event(id_event),
constraint FK_PolicyList foreign key (id_policyList) references Policy_List(id_policyList)
);

CREATE TABLE PM
(
id_pm INT,
ip character(40),
date_pm DATE,
constraint PK_PM primary key (id_pm)
);
CREATE TABLE VM
(
id_vm INT,
name character(100),
date_vm DATE,
ip character(40),
image character(100),
ext_id INT,
id_pm INT,
id_profile INT,
constraint PK_VM primary key (id_vm),
constraint FK_PM foreign key (id_pm) references PM(id_pm)
);

CREATE TABLE VNF
(
id_vnf INT,
date_vnf DATE,
type_vnf character(100),
script character(100),
id_vm INT,
constraint PK_VNF primary key (id_vnf),
constraint FK_VM foreign key (id_vm) references VM(id_vm)
);

CREATE TABLE Settings
(
id_settings INT,
uid character(100),
keystone_endpoint character(100),
celiometer_endpoint character(100),
tenant_name character(100),
os_username character(100),
os_password character(100),
constraint PK_SETTINGS primary key(id_settings)
);

CREATE TABLE Users(
uid VARCHAR(255) PRIMARY KEY,
username VARCHAR(255), 
passwd VARCHAR(255));

CREATE TABLE UserRoles(
username VARCHAR(255), 
role VARCHAR(32),
primary key (username,role)); 
