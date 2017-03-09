insert into settings(id_settings,uid,keystone_endpoint,celiometer_endpoint,os_username,os_password) 
values(0,'0','http://8.43.86.2:5000/v2.0','http://8.43.86.2:8777','facebook147125119093958','null');

insert into snmp_settings(id_snmp,community,target) 
values('0','public','localhost');

insert into event(id_event,name,action,priority,description) values('0','testEvent',1,'Medium','test'); 
insert into meters(id_meters,type,unit,description,name) values('0','Cumulative','instance','test','testMeter'); 
insert into users(uid,username,passwd) values('0','testUser','testUser'); 
insert into policy_list(id_policylist,enabled,name,uid) values('0',true,'testList','0'); 
insert into policy(id_policy,id_meter,id_event,users,resource,treshhold,sign,enabled,id_policylist) values('0','0','0','0','0',5,'>',true,'0');

