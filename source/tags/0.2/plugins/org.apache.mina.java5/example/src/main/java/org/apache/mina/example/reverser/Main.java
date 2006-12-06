/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.mina.example.reverser;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.filter.LoggingFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

/**
 * (<b>Entry point</b>) Reverser server which reverses all text lines from
 * clients.
 * 
 * @author The Apache Directory Project (mina-dev@directory.apache.org)
 * @version $Rev: 451854 $, $Date: 2006-10-02 11:30:11 +0900 (월, 02 10월 2006) $,
 */
public class Main
{
    private static final int PORT = 8080;

    public static void main( String[] args ) throws Exception
    {
        IoAcceptor acceptor = new SocketAcceptor();

        // Prepare the configuration
        SocketAcceptorConfig cfg = new SocketAcceptorConfig();
        cfg.setReuseAddress( true );
        cfg.getFilterChain().addLast( "logger", new LoggingFilter() );
        cfg.getFilterChain().addLast(
                "codec",
                new ProtocolCodecFilter(
                        new TextLineCodecFactory( Charset.forName( "UTF-8" ) ) ) );

        // Bind
        acceptor.bind(
                new InetSocketAddress( PORT ),
                new ReverseProtocolHandler(), cfg );

        System.out.println( "Listening on port " + PORT );
    }
}
