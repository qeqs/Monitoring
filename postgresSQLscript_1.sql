CREATE TABLE Meters
(
id_meters character(100),
type character(100),
unit character(100),
name character(100),
oid character(100),
description character(100),
gather_type character(100), 
constraint PK_Meters primary key (id_meters)
);

create table Profile
(
id_profile character(100) primary key,
id_settings character(100),
id_vnf character(100),
id_policy_list character(100),
id_snmp character(100),
name character(200),
constraint FK_Policy_List foreign key (id_policy_list) references Policy_List(id_policyList),
constraint FK_Settings foreign key (id_settings) references Settings(id_settings),
constraint FK_Snmp foreign key (id_snmp) references Snmp_Settings(id_snmp),
constraint FK_Vnf foreign key (id_vnf) references vnf(id)
);
CREATE TABLE Measure
(
id_Measure character(100),
value float,
tstamp timestamp,
id_meter character(100),
user_id character(100),
resource character(100),
id_profile character(100),
source character(100),
constraint PK_Measure primary key (id_Measure),
constraint FK_Meters foreign key (id_meter) references Meters(id_meters),
constraint FK_MeasuresProfile foreign key (id_profile) references Profile(id_profile)
);

CREATE TABLE Event
(
id_event character(100),
name character(100),
action character(255),
priority character(100),
description character(100),
constraint PK_Event primary key (id_event)
);


CREATE TABLE Policy_List
(
id_policyList character(100),
enabled boolean,
name character(100),
constraint PK_Policy_List primary key (id_policyList)
);

CREATE TABLE Policy
(
id_policy character(100),
id_meter character(100),
id_event character(100),
users character(100),
resource character(100),
treshhold float,
sign character(100),
enabled boolean,
id_policyList character(100),
groups character(100),
constraint PK_Policy primary key (id_policy),
constraint FK_Meters foreign key (id_meter) references Meters(id_meters),
constraint FK_Event foreign key (id_event) references Event(id_event),
constraint FK_PolicyList foreign key (id_policyList) references Policy_List(id_policyList)
);

create table vnf(
id varchar(255) primary key,
createdat timestamp not null,
modifiedat timestamp,
name varchar(45),
username varchar(255)
);

CREATE TABLE Settings
(
id_settings character(100),
keystone_endpoint character(100),
celiometer_endpoint character(100),
tenant_name character(100),
os_username character(100),
os_password character(100),
constraint PK_SETTINGS primary key(id_settings)
);
CREATE TABLE Snmp_Settings
(
id_snmp character(100),
community character(100),
target character(100),
constraint PK_SNMP primary key(id_snmp)
);

CREATE TABLE Users(
uid VARCHAR(255) PRIMARY KEY,
username VARCHAR(255) UNIQUE NOT NULL, 
profiles integer,
passwd VARCHAR(255));

create table users_profiles
(
id_profile character(100),
uid varchar(255),
primary key (id_profile,uid),
constraint FK_users foreign key (uid) references Users(uid),
constraint FK_profiles foreign key (id_profile) references Profile(id_profile)
);



CREATE TABLE UserRoles(
username VARCHAR(255), 
role VARCHAR(32),
primary key (username,role)); 
