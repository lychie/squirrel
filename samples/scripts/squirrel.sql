DROP TABLE IF EXISTS Dept;
CREATE TABLE Dept (
  id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  name varchar(32) NOT NULL COMMENT '部门名称',
  location varchar(64) NOT NULL COMMENT '部门所在地',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS Emp;
CREATE TABLE Emp (
  id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '员工编号',
  deptId int(10) unsigned NOT NULL COMMENT '部门编号',
  name varchar(32) NOT NULL COMMENT '员工名称',
  sex char(1) NOT NULL COMMENT '员工性别',
  job varchar(32) NOT NULL COMMENT '员工职位名称',
  salary int(11) NOT NULL COMMENT '员工薪资',
  hiredate date NOT NULL COMMENT '员工入职日期',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

INSERT INTO Dept VALUES ('1', '研发部', '广东深圳');
INSERT INTO Dept VALUES ('2', '测试部', '广东广州');
INSERT INTO Dept VALUES ('3', '人事部', '广东广州');
INSERT INTO Dept VALUES ('4', '运营部', '广东深圳');

INSERT INTO Emp VALUES ('1', '1', '李文豪', '男', 'Java研发', '8000', '2013-10-01');
INSERT INTO Emp VALUES ('2', '1', '周校民', '男', 'Java研发', '7000', '2013-10-05');
INSERT INTO Emp VALUES ('3', '1', '吴嘉宁', '男', 'Java研发', '7500', '2013-10-08');
INSERT INTO Emp VALUES ('4', '1', '郑宇乐', '男', 'C++研发', '9000', '2013-10-08');
INSERT INTO Emp VALUES ('5', '1', '王世杰', '男', 'C++研发', '8000', '2013-10-10');
INSERT INTO Emp VALUES ('6', '2', '赵晓妍', '女', '软件测试', '5000', '2014-10-10');
INSERT INTO Emp VALUES ('7', '2', '孙凯文', '男', '软件测试', '5000', '2014-10-10');
INSERT INTO Emp VALUES ('8', '2', '孙舒然', '女', '测试开发', '6000', '2014-10-15');
INSERT INTO Emp VALUES ('9', '2', '周子轩', '女', '测试开发', '6500', '2014-10-15');
INSERT INTO Emp VALUES ('10', '3', '郑羽纯', '女', '人事专员', '5000', '2013-10-15');
INSERT INTO Emp VALUES ('11', '3', '王雅琪', '女', '人事专员', '4000', '2013-10-15');
INSERT INTO Emp VALUES ('12', '3', '陈子浩', '男', '人事专员', '4500', '2013-10-18');
INSERT INTO Emp VALUES ('13', '3', '李佳文', '女', '人事专员', '5000', '2013-10-20');
INSERT INTO Emp VALUES ('14', '3', '吴诗琪', '女', '人事助理', '4500', '2013-10-21');
INSERT INTO Emp VALUES ('15', '3', '郑诗颖', '女', '人事助理', '4000', '2013-10-21');
INSERT INTO Emp VALUES ('16', '4', '沈文锋', '男', '运营总监', '8000', '2014-10-25');
INSERT INTO Emp VALUES ('17', '4', '韩泽宇', '男', '运营经理', '6000', '2014-10-25');
INSERT INTO Emp VALUES ('18', '4', '杨雨涵', '女', '运营专员', '5000', '2014-11-05');
INSERT INTO Emp VALUES ('19', '4', '秦诗媛', '女', '运营专员', '5000', '2014-11-05');
INSERT INTO Emp VALUES ('20', '4', '张哲文', '男', '运营专员', '5000', '2014-11-05');