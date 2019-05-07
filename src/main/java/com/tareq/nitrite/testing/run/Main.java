/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tareq.nitrite.testing.run;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 *
 * @author tareq
 */
public class Main {
  
  public static void main(String[] args) throws Exception{
     Server server = new Server(8080);
     ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
     ctx.setContextPath("/");
        server.setHandler(ctx);
     ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/*");
     serHol.setInitParameter("javax.ws.rs.Application", "com.tareq.nitrite.testing.config.Application");
     server.start();
     server.join();
  }
}
