# MySQL 日志

## 系统日志
主要用于记录 MySQL 服务器的操作和事件，供 DBA 检查和故障排查。

* 错误日志（Error Log）: 记录 MySQL 启动、运行或停止时发生的问题。这包括服务器的启动和运行参数，以及服务器运行过程中遇到的任何问题。
* 常规查询日志（General Query Log）: 记录 MySQL 服务器接收到的所有语句，包括客户端的连接、操作（如查询和更改）以及服务器自身的语句。
* 慢查询日志（Slow Query Log）: 记录执行时间超过特定阈值的查询。这对于找出和优化性能问题非常有用。

## 复制日志
这些日志用于支持 MySQL 的复制功能。

* 二进制日志（Binary Log）: 记录所有修改数据的语句，以便于复制操作和数据恢复。二进制日志是 MySQL 复制的基础，同时也是进行点时间恢复（Point-in-Time Recovery）的关键工具。不仅被系统用于复制和恢复数据，也可以被数据库管理员查看。例如，管理员可能需要查看二进制日志来理解数据库的更改历史，或者手动执行点时间恢复（PITR）。
* 中继日志（Relay Log）: 在MySQL复制过程中，从服务器将主服务器的二进制日志事件写入自己的中继日志。通常只被MySQL系统自动处理，用于实现复制。但在某些情况下，管理员可能需要查看中继日志的状态来调试复制问题。

## 事务日志（Transaction Log）
InnoDB 存储引擎使用的日志，记录所有对 InnoDB 表的修改。这对于恢复和事务的 ACID 性质非常重要。

* Redo日志：是一种物理日志，用于记录对数据库进行的**所有**更改操作，用于在系统崩溃后恢复数据。
  * 
* Undo日志：是一种逻辑日志，用于存储数据的旧版本，用于在事务回滚时恢复数据，以及支持多版本并发控制（MVCC）。

这些日志主要由 InnoDB 存储引擎自动处理，用于支持事务处理和恢复。虽然它们通常不会被人类用户直接访问，但数据库管理员可以通过某些系统视图或命令，查看它们的状态和使用情况。例如，管理员可以查看 `SHOW ENGINE INNODB STATUS` 命令的输出，来获取 Redo日志 和 Undo日志 的相关信息。

### Redo 日志

InnoDB存储引擎在执行写操作（如INSERT、UPDATE、DELETE等）时采用了一种称为"Write-Ahead Logging"（预写日志）的技术。具体的步骤如下：

1. 写Redo日志：当一条修改数据的SQL语句执行时，InnoDB首先会将这个修改操作写入到Redo日志中。这个步骤是在内存中完成的，所以速度很快。这样做的目的是为了在系统崩溃后可以通过重放Redo日志来恢复数据。
2. 执行SQL语句：然后，InnoDB会执行这条SQL语句，修改内存中的数据。注意，这个时候修改还没有被写入到磁盘上的数据文件中。
3. 刷新Redo日志：在某个时间点（例如，事务提交时，或者Redo日志满了需要腾出空间时），InnoDB会将内存中的Redo日志刷新（flush）到磁盘上。
4. 刷新数据文件：最后，InnoDB会在适当的时间将修改的数据从内存刷新到磁盘上的数据文件中。这个步骤可能会在SQL语句执行完很久之后才发生，但是，由于有了Redo日志，即使在数据还没有刷新到磁盘的时候系统崩溃，也可以通过重放Redo日志来恢复数据。

思考：

* 其中只有第3点完成了，才会返回事务提交成功。如果还未完成，系统崩溃了，不违背事务的原子性和持久性。
* 为什么数据库系统通常首先写入Redo日志，然后再异步地将数据写入磁盘？
  * 因为 Redo 日志的数据量比直接将数据写入磁盘数据量小，这样可以在保证系统崩溃时数据完整性的同时提高性能。
  * **Redo日志**：Redo日志是一种物理日志，它记录了如何恢复数据页到特定状态的步骤。它不需要记录数据页的完整状态，只需要记录修改操作的具体细节。例如，如果你更新了一行数据，Redo日志可能只需要记录这个行的位置和新值。这通常比记录完整的数据页要小得多。
  * **数据文件**：当数据被直接写入磁盘时，通常是以数据页为单位的。数据页包含了许多行的数据，以及其他一些元数据。如果你只更新了其中一行数据，你仍然需要写入整个数据页。这通常比写入Redo日志要大得多。

### Undo 日志

Undo日志和Redo日志是两种不同的日志，它们在数据库事务处理中扮演了不同的角色。Undo日志主要用于在事务回滚时恢复旧的数据值，而Redo日志主要用于在系统崩溃恢复后确保修改的持久性。这两种日志都是在执行修改操作时生成的。

在InnoDB中，Undo日志通常存储在表空间中，而Redo日志通常存储在单独的日志文件中。

在MySQL的InnoDB存储引擎中，为了保证数据一致性，它的操作流程一般是这样的：

1. 当一个事务开始执行时，它的所有修改都首先在内存中进行，并且同时生成Undo日志。
2. 在事务提交前，InnoDB会将Undo日志写入磁盘。这确保了如果系统在事务提交之前崩溃，可以使用Undo日志来回滚未完成的事务。
3. 事务提交时，InnoDB会将所有的修改（redo日志）和事务的commit记录一起写入磁盘。这样，即使在写入数据的过程中系统崩溃，也可以在系统恢复后使用redo日志来重做事务。
4. 在事务提交后，InnoDB会异步地将内存中修改的数据页写入磁盘。但是某些场景会在事务提交前就将数据页写回磁盘，但是Undo和Redo日志会在他们之前写入磁盘。
