-- ----------------------------
-- SQL SERVER 08
-- BY LYCHIE FAN
-- ----------------------------

CREATE TABLE [dbo].[Dept] (
[id] int NOT NULL IDENTITY(1,1) ,
[name] nvarchar(32) NOT NULL ,
[location] nvarchar(64) NOT NULL 
)
GO

ALTER TABLE [dbo].[Dept] ADD PRIMARY KEY ([id])
GO

CREATE TABLE [dbo].[Emp] (
[id] int NOT NULL IDENTITY(1,1) ,
[deptId] int NOT NULL ,
[name] nvarchar(32) NOT NULL ,
[sex] nchar(1) NOT NULL ,
[job] nvarchar(32) NOT NULL ,
[salary] int NOT NULL ,
[hiredate] date NOT NULL 
)
GO

ALTER TABLE [dbo].[Emp] ADD PRIMARY KEY ([id])
GO

DBCC CHECKIDENT(N'[dbo].[Dept]', RESEED, 4)
GO
SET IDENTITY_INSERT [dbo].[Dept] ON
GO
INSERT INTO [dbo].[Dept] ([id], [name], [location]) VALUES (N'1', N'研发部', N'广东深圳');
INSERT INTO [dbo].[Dept] ([id], [name], [location]) VALUES (N'2', N'测试部', N'广东广州');
INSERT INTO [dbo].[Dept] ([id], [name], [location]) VALUES (N'3', N'人事部', N'广东广州');
INSERT INTO [dbo].[Dept] ([id], [name], [location]) VALUES (N'4', N'运营部', N'广东深圳');
GO
SET IDENTITY_INSERT [dbo].[Dept] OFF
GO

DBCC CHECKIDENT(N'[dbo].[Emp]', RESEED, 20)
GO
SET IDENTITY_INSERT [dbo].[Emp] ON
GO
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'1', N'1', N'李文豪', N'男', N'Java研发', N'8000', N'2013-10-01');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'2', N'1', N'周校民', N'男', N'Java研发', N'7000', N'2013-10-05');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'3', N'1', N'吴嘉宁', N'男', N'Java研发', N'7500', N'2013-10-08');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'4', N'1', N'郑宇乐', N'男', N'C++研发', N'9000', N'2013-10-08');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'5', N'1', N'王世杰', N'男', N'C++研发', N'8000', N'2013-10-10');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'6', N'2', N'赵晓妍', N'女', N'软件测试', N'5000', N'2013-10-10');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'7', N'2', N'孙凯文', N'男', N'软件测试', N'5000', N'2013-10-10');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'8', N'2', N'孙舒然', N'女', N'测试开发', N'6000', N'2014-10-15');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'9', N'2', N'周子轩', N'女', N'测试开发', N'6500', N'2014-10-15');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'10', N'3', N'郑羽纯', N'女', N'人事专员', N'5000', N'2014-10-15');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'11', N'3', N'王雅琪', N'女', N'人事专员', N'4000', N'2014-10-15');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'12', N'3', N'陈子浩', N'男', N'人事专员', N'4500', N'2013-10-18');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'13', N'3', N'李佳文', N'女', N'人事专员', N'5000', N'2013-10-20');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'14', N'3', N'吴诗琪', N'女', N'人事助理', N'4500', N'2013-10-21');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'15', N'3', N'郑诗颖', N'女', N'人事助理', N'4000', N'2013-10-21');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'16', N'4', N'沈文锋', N'男', N'运营总监', N'8000', N'2014-10-25');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'17', N'4', N'韩泽宇', N'男', N'运营经理', N'6000', N'2014-10-25');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'18', N'4', N'杨雨涵', N'女', N'运营专员', N'5000', N'2014-11-05');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'19', N'4', N'秦诗媛', N'女', N'运营专员', N'5000', N'2014-11-05');
INSERT INTO [dbo].[Emp] ([id], [deptId], [name], [sex], [job], [salary], [hiredate]) VALUES (N'20', N'4', N'张哲文', N'男', N'运营专员', N'5000', N'2014-11-05');
GO
SET IDENTITY_INSERT [dbo].[Emp] OFF
GO