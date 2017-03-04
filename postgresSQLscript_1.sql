CREATE TABLE Meters
(
id_meters character(40),
type character(40),
unit character(40),
name character(100),
description character(40),
gather_type character(40), 
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
source character(40),
constraint PK_Measure primary key (id_Measure),
constraint FK_Meters foreign key (id_meter) references Meters(id_meters)
);

CREATE TABLE Event
(
id_event character(40),
name character(40),
action character(40)[],
priority character(40),
description character(40),
constraint PK_Event primary key (id_event)
);


CREATE TABLE Policy_List
(
id_policyList character(40),
enabled boolean,
name character(40),
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

create table vnf(
id varchar(255) primary key,
createdat timestamp not null,
modifiedat timestamp,
name varchar(45),
username varchar(255)
);

CREATE TABLE Settings
(
id_settings character(40),
keystone_endpoint character(100),
celiometer_endpoint character(100),
tenant_name character(100),
os_username character(100),
os_password character(100),
constraint PK_SETTINGS primary key(id_settings)
);

create table users_profiles
(
id integer primary key,
id_profile character(40),
uid varchar(255),
constraint FK_users foreign key (uid) references Users(uid),
constraint FK_profiles foreign key (id_profile) references Profile(id_profile)
);

create table Profile
(
id_profile character(40) primary key,
id_settings character(40),
id_vnf character(40),
id_policy_list character(40),
users integer,
constraint FK_Policy_List foreign key (id_policy_list) references Policy_List(id_policyList),
constraint FK_Settings foreign key (id_settings) references Settings(id_settings),
constraint FK_Vnf foreign key (id_vnf) references vnf(id),
constraint FK_Users foreign key (users) references users_profiles(id)
);

CREATE TABLE Users(
uid VARCHAR(255) PRIMARY KEY,
username VARCHAR(255) UNIQUE NOT NULL, 
profiles integer,
passwd VARCHAR(255),
constraint FK_Profiles foreign key (profiles) references users_profiles(id)) NOT NULL;

CREATE TABLE UserRoles(
username VARCHAR(255), 
role VARCHAR(32),
primary key (username,role)); 