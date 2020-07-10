SELECT u.id, u.id as userId, u.user_alias userAlias, u.NAME userName, u.origin userOrigin, u.STATUS, u.email
, dud.department
, role_name_table.id 'roleId', role_name_table.NAME 'role'
, group_role_name_table.NAME groupRole
 FROM sys_user u

 LEFT JOIN sys_user_role_group urg
 ON urg.user_id = u.id

 LEFT JOIN (
	 SELECT du.user_id, d.NAME department
	 FROM sys_department_user du
	 LEFT JOIN sys_department d
	 ON du.department_id = d.id
 ) dud
 ON u.id = dud.user_id

 LEFT JOIN (

	 SELECT
	 surg.user_id,
	 GROUP_CONCAT( if((sg.name="" or sg.name is null),"无",sg.name), "-",if((sr.name="" or sr.name is null),"无",sr.name) ) NAME
	 FROM sys_user_role_group surg

	 LEFT JOIN sys_group sg
	 ON surg.group_id = sg.id

	 LEFT JOIN sys_role sr
	 ON surg.role_id = sr.id
	 WHERE (surg.group_id != 2 or surg.group_id is null) and sr.type != 3 GROUP BY surg.user_id
 ) group_role_name_table
 ON group_role_name_table.user_id = u.id

 LEFT JOIN (
	 SELECT surg.user_id, GROUP_CONCAT( sr.id ) id, GROUP_CONCAT( sr.NAME ) NAME
	 FROM sys_user_role_group surg
	 LEFT JOIN sys_role sr
	 ON surg.role_id = sr.id
	 WHERE surg.group_id = 2 AND sr.type!=3 GROUP BY surg.user_id
 ) role_name_table
 ON role_name_table.user_id = u.id

 WHERE 1=1
 and u.account_id = 110
 and urg.group_id = 2
 AND u.admin_type !=3
 GROUP BY u.id, u.user_alias, u.NAME, u.origin, u.STATUS, u.email
 , dud.department, role_name_table.id, role_name_table.NAME, group_role_name_table.NAME
 ORDER BY u.id
 LIMIT 10 OFFSET 0;

