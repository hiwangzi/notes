Spring Security 的工作原理是基于 FilterChain 的。Spring Security 的大部分功能都是通过一系列的 Servlet Filter 实现的，这些 Filter 通过 FilterChain 连接在一起。每一个 HTTP 请求都会通过这个 FilterChain，每个 Filter 对请求进行特定的处理，如认证、授权等。

在 Spring Security 中，FilterChain 本身是由 Spring Security 的 `FilterChainProxy` 管理的，这个 `FilterChainProxy` 是单例的，它在应用启动时初始化并保持在内存中。`FilterChainProxy` 管理一系列的 `SecurityFilterChain`，每个 `SecurityFilterChain` 对应一个特定的 URL 模式。

在每个 SecurityFilterChain 中，又包含了一组 Filter，这些 Filter 的执行顺序和添加顺序是一致的。对于每个 HTTP 请求，FilterChainProxy 会选择一个匹配请求 URL 的 SecurityFilterChain，然后按顺序执行其中的 Filter。



![delegatingfilterproxy](https://docs.spring.io/spring-security/reference/_images/servlet/architecture/delegatingfilterproxy.png)

`DelegatingFilterProxy` 是 Servlet 容器和 Spring Security 之间的桥梁，它将 HTTP 请求从 Servlet 容器转发到 `FilterChainProxy`；而 `FilterChainProxy` 则是 Spring Security 的**核心**，它负责管理和执行实现 Spring Security 功能的 Filter。