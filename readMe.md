https://github.com/chhsiao90/nitmproxy
https://zhuanlan.zhihu.com/p/362362169
https://www.codenong.com/cs105326322/
https://medium.com/@irunika/how-to-write-a-http-websocket-server-using-netty-f3c136adcba9
https://zhuanlan.zhihu.com/p/356167533
https://blog.csdn.net/abc123lzf/article/details/90724591

关于Cookie
https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Cookies#set-cookie%E5%93%8D%E5%BA%94%E5%A4%B4%E9%83%A8%E5%92%8Ccookie%E8%AF%B7%E6%B1%82%E5%A4%B4%E9%83%A8

连接超时
https://beautyboss.farbox.com/post/netty/nettylian-jie-chao-shi-fen-xi

http://112.91.118.47:8082/live?app=mylive&stream=IPC-HDW1320C

502 Bad Gateway(网关错误)
含义：服务器作为网关或者代理时,为了完成请求访问下一个服务器,但该服务器返回了非法的应答.

解释: 作为网关或者代理工作的服务器尝试执行请求时,从上游服务器接收到无效的响应. 502 错误通常不是客户端能够修复的,而是需要由途径的Web服务器或者代理服务器对其进行修复.

使用场景： web 服务重启的时候. 举个例子：启动nginx,不启动php-fpm, 这时候 nginx服务器就会返回 502. 原因：nginx无法连接php-fpm,在达到nginx最大的超时时间的时候,nginx还没有获取到响应就会返回502

