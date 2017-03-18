insert into settings(id_settings,uid,keystone_endpoint,celiometer_endpoint,os_username,os_password) 
values(0,'0','http://8.43.86.2:5000/v2.0','http://8.43.86.2:8777','facebook147125119093958','null');

insert into snmp_settings(id_snmp,community,target) 
values('0','public','localhost');

insert into event(id_event,name,action,priority,description) values('0','testEvent',{''},'Medium','test'); 
insert into meters(id_meters,type,unit,description,name) values('0','Cumulative','instance','test','testMeter'); 
insert into meters(id_meters,type,unit,description,name,oid) values('1','Cumulative','date','testSnmp','testSnmpMeter','1.3.6.1.2.1.1.8.0'); 
insert into policy_list(id_policylist,enabled,name) values('0',true,'testList'); 
insert into policy(id_policy,id_meter,id_event,users,resource,treshhold,sign,enabled,id_policylist) values('0','0','0','0','localhost',5,'>',true,'0');
insert into policy(id_policy,id_meter,id_event,users,resource,treshhold,sign,enabled,id_policylist) values('1','0','0','0','test',5,'>',true,'0');

INSERT INTO users (username, passwd,issocial) VALUES ('root', '535DC6BE39B8D420:1000:A1AEE5668B080484AE44D6941C523E443094E37C', false);
INSERT INTO userroles (username, role) VALUES ('root', 'ADMIN');


