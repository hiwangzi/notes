
-- --------------------------------------------------
-- Entity Designer DDL Script for SQL Server 2005, 2008, 2012 and Azure
-- --------------------------------------------------
-- Date Created: 03/24/2017 18:52:26
-- Generated from EDMX file: c:\Users\zill\documents\visual studio 2017\Projects\Sstudent\Model\Sstudent.edmx
-- --------------------------------------------------

SET QUOTED_IDENTIFIER OFF;
GO
USE [Sstudent];
GO
IF SCHEMA_ID(N'dbo') IS NULL EXECUTE(N'CREATE SCHEMA [dbo]');
GO

-- --------------------------------------------------
-- Dropping existing FOREIGN KEY constraints
-- --------------------------------------------------


-- --------------------------------------------------
-- Dropping existing tables
-- --------------------------------------------------


-- --------------------------------------------------
-- Creating all tables
-- --------------------------------------------------

-- Creating table 'UserSet'
CREATE TABLE [dbo].[UserSet] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [Userame] nvarchar(max)  NOT NULL,
    [Password] nvarchar(max)  NOT NULL,
    [Profile_Id] int  NOT NULL
);
GO

-- Creating table 'ProfileSet'
CREATE TABLE [dbo].[ProfileSet] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [StudentNumber] nvarchar(max)  NOT NULL,
    [姓名] nvarchar(max)  NOT NULL,
    [Sex] nvarchar(max)  NOT NULL,
    [PhoneNumber] nvarchar(max)  NOT NULL,
    [Address] nvarchar(max)  NOT NULL,
    [Hobby] nvarchar(max)  NOT NULL
);
GO

-- --------------------------------------------------
-- Creating all PRIMARY KEY constraints
-- --------------------------------------------------

-- Creating primary key on [Id] in table 'UserSet'
ALTER TABLE [dbo].[UserSet]
ADD CONSTRAINT [PK_UserSet]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'ProfileSet'
ALTER TABLE [dbo].[ProfileSet]
ADD CONSTRAINT [PK_ProfileSet]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- --------------------------------------------------
-- Creating all FOREIGN KEY constraints
-- --------------------------------------------------

-- Creating foreign key on [Profile_Id] in table 'UserSet'
ALTER TABLE [dbo].[UserSet]
ADD CONSTRAINT [FK_UserProfile]
    FOREIGN KEY ([Profile_Id])
    REFERENCES [dbo].[ProfileSet]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_UserProfile'
CREATE INDEX [IX_FK_UserProfile]
ON [dbo].[UserSet]
    ([Profile_Id]);
GO

-- --------------------------------------------------
-- Script has ended
-- --------------------------------------------------