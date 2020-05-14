* 使用 [`parallel`](https://savannah.gnu.org/projects/parallel/) 命令
* 例子：
  ```sh
  (
    echo 'date'; 
    echo 'sleep 10'; 
    echo 'date'
  ) | parallel
  ```
  ```sh
  (
    echo 'jstack -l 1 > $(date +%Y-%m-%d-%H-%M-%S).txt'; 
    echo 'echo q | htop | aha --black --line-fix > $(date +%Y-%m-%d-%H-%M-%S).html'
  ) | parallel
  ```
