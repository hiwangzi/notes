## 问题

使用Windows Terminal 打开 WSL2 Debian，执行 `eval "$(ssh-agent -s)"`，关闭 Tab 或者 Ctrl-D 退出 Shell，会发现 `ssh-agent` 仍然在运行。

不知道这是不是正常现象，GPT告诉我这不是正常现象：
> 如果你直接运行 `ssh-agent` 作为后台进程，那么它不会在你注销时自动结束。你需要显式地杀掉它。
> 
> 如果你在一个 shell 会话中运行 `eval "$(ssh-agent -s)"`，这个 `ssh-agent` 进程会在该 shell 会话结束时自动结束。这是因为 `ssh-agent` 在这种情况下被启动为子进程，当父进程（即 shell）结束时，所有子进程也会被结束。
> 
> 所以，这两种情况下 `ssh-agent` 的行为是不同的。你的说法在第一种情况下是正确的，但在第二种情况下不适用。

但我搜到了以下信息：
[6.11. Terminating an SSH Agent on Logout - Linux Security Cookbook [Book]](https://www.oreilly.com/library/view/linux-security-cookbook/0596003919/ch06s11.html#:~:text=SSH%20agents%20you%20invoke%20yourself,agent%20with%20the%20%2Dk%20option.) 看这里感觉似乎 `ssh-agent` 不自动关闭是个已知事件。

## 解决方案

在 `.zprofile` 中增加如下指令：

```zsh
trap 'test -n "$SSH_AGENT_PID" && eval `/usr/bin/ssh-agent -k`' 0
```

解释：`trap` 用于在接收到指定信号时做事，`0`表示shell退出，所以以上命令会在检测到shell退出时，检查是否存在 `$SSH_AGENT_PID` 环境变量，存在即执行 `/usr/bin/ssh-agent -k` 结束该 `ssh-agent`
