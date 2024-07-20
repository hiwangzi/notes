* key: 实际用到的索引
* possible_keys：可能用到的索引，过多也不好
* type，性能依次变差：
  * null
  * system: 查询系统表
  * const：使用主键查询
  * eq_ref:主键索引查询或唯一索引查询，返回一条数据
  * ref： 索引范围
  * range: 范围查询
  * index：索引树扫描
  * all：全盘扫描
* extra：优化建议，判断是否回表
  * using where; using index：不需要回表
  * using index condition: 使用了索引，但是需要回表
