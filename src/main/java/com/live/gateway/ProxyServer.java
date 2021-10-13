package com.live.gateway;

import com.live.gateway.config.ProxyConfig;
import com.live.gateway.config.ProxyServerConfig;
import com.live.gateway.config.RemotingClientConfig;
import com.live.gateway.config.RemotingServerConfig;
import com.live.gateway.initializer.ProxyChannelInitializer;
import com.live.gateway.model.NetAddress;
import com.live.gateway.util.PropertiesUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.util.Properties;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/11 13:57
 */
@Slf4j
public final class ProxyServer {

    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption(Option.builder("c").longOpt("config").hasArg().argName("configLocation").desc("Config file location").build());

        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException ex) {
            new HelpFormatter().printHelp("proxy", options, true);
            System.exit(-1);
        };

        Properties configProperties;
        if (commandLine.hasOption("c")) {
            configProperties = PropertiesUtil.loadProperties(commandLine.getOptionValue("c"));
        } else {
            configProperties = PropertiesUtil.loadResourceProperties("config.properties");
        }

        final ProxyServerConfig serverConfig = new ProxyServerConfig();
        final RemotingServerConfig remotingServerConfig = new RemotingServerConfig();
        final RemotingClientConfig remotingClientConfig = new RemotingClientConfig();

        PropertiesUtil.properties2Config(configProperties, serverConfig);
        PropertiesUtil.properties2Config(configProperties, remotingServerConfig);
        PropertiesUtil.properties2Config(configProperties, remotingClientConfig);

        ProxyConfig.init(serverConfig, remotingServerConfig, remotingClientConfig);

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ProxyChannelInitializer(new NetAddress(remotingServerConfig.getHost(), remotingServerConfig.getPort())))
                    .childOption(ChannelOption.AUTO_READ, false)
                    .bind(serverConfig.getListenPort()).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
